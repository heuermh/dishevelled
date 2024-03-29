/*

    dsh-worm-plot-cytoscape3-app  Worm plot Cytoscape 3 app.
    Copyright (c) 2014-2017 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.wormplot.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.BorderLayout;
import java.awt.FileDialog;

import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

import com.google.common.collect.ImmutableList;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;

import org.cytoscape.work.swing.DialogTaskManager;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.impl.CachingIconBundle;
import org.dishevelled.iconbundle.impl.PNGIconBundle;

import org.dishevelled.identify.IdentifiableAction;

/**
 * Worm plot app.
 *
 * @author  Michael Heuer
 */
final class WormPlotApp extends JPanel
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;

    /** File util. */
    private final FileUtil fileUtil;

    /** Worm plot task factory. */
    private final WormPlotTaskFactory wormPlotTaskFactory;

    /** Worm plot model. */
    private final WormPlotModel model;

    /** Sequence file name. */
    private final JTextField sequenceFileName;

    /** Length. */
    private final JTextField length;

    /** Overlap. */
    private final JTextField overlap;

    /** Plot button. */
    private final JButton plotButton;

    /** True if blastn is available. */
    private final boolean blastnAvailable;

    /** Open file dialog action. */
    private final AbstractAction openFileDialog = new AbstractAction("...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                FileChooserFilter filter = new FileChooserFilter("Sequence file in FASTA format", new String[] { "fa", "fasta", "txt" });
                File sequenceFile = fileUtil.getFile((JDialog) getTopLevelAncestor(), "Select a sequence file in FASTA format", FileUtil.LOAD, ImmutableList.of(filter));
                if (sequenceFile != null) {
                    model.setSequenceFile(sequenceFile);
                }
            }
        };

    /** Cancel action. */
    private final AbstractAction cancel = new AbstractAction("Cancel")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                cancel();
            }
        };

    /** Worm plot icon bundle. */
    private final IconBundle wormPlotIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/wormplot/cytoscape3/internal/wormPlot"));

    /** Worm plot action. */
    private final IdentifiableAction plot = new IdentifiableAction("Worm plot...", wormPlotIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                plot();
            }
        };

    /** Property change listener. */
    private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent event)
            {
                if ("sequenceFile".equals(event.getPropertyName()))
                {
                    sequenceFileName.setText(event.getNewValue() == null ? "" : event.getNewValue().toString());
                }
                else if ("length".equals(event.getPropertyName()))
                {
                    length.setText(String.valueOf((Integer) event.getNewValue()));
                }
                else if ("overlap".equals(event.getPropertyName()))
                {
                    overlap.setText(String.valueOf((Integer) event.getNewValue()));
                }
            }
        };

    /** Requires doc. */
    private static final String REQUIRES = "<html><strong>Note</strong>:  Worm Plot requires "
        + "<a href=\"http://blast.ncbi.nlm.nih.gov/Blast.cgi?PAGE_TYPE=BlastDocs&DOC_TYPE=Download\">blastn</a> "
        + " version 2.2.29 or later to be installed.</html>";

    /** Blastn not available doc. */
    private static final String BLASTN_NOT_AVAILABLE = "<html><font color=\"red\">BLASTN version 2.2.29 or later was not found.</font></html>";


    /**
     * Create a new worm plot app.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     * @param fileUtil file util, must not be null
     * @param wormPlotTaskFactory worm plot task factory, must not be null
     */
    WormPlotApp(final CyApplicationManager applicationManager,
                final DialogTaskManager dialogTaskManager,
                final FileUtil fileUtil,
                final WormPlotTaskFactory wormPlotTaskFactory)
    {
        super();
        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);
        checkNotNull(fileUtil);
        checkNotNull(wormPlotTaskFactory);
        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;
        this.fileUtil = fileUtil;
        this.wormPlotTaskFactory = wormPlotTaskFactory;

        model = new WormPlotModel();
        model.setNetwork(applicationManager.getCurrentNetwork());
        model.setNetworkView(applicationManager.getCurrentNetworkView());
        // todo:  SetCurrentNetworkListener, SetCurrentNetworkViewListener
        model.addPropertyChangeListener(propertyChangeListener);

        sequenceFileName = new JTextField(48);
        sequenceFileName.setText("example.fa");
        // add focus, action listeners to update model?

        length = new JTextField();
        length.setColumns(24);
        length.setText(String.valueOf(WormPlotModel.DEFAULT_LENGTH));

        overlap = new JTextField();
        overlap.setColumns(20);
        overlap.setText(String.valueOf(WormPlotModel.DEFAULT_OVERLAP));

        plotButton = new JButton(plot);
        blastnAvailable = isBlastnAvailable();
        openFileDialog.setEnabled(blastnAvailable);
        plot.setEnabled(blastnAvailable);

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BorderLayout());
        add("Center", createMainPanel());
        add("South", createButtonPanel());
    }

    /**
     * Create and return the main panel.
     *
     * @return the main panel
     */
    private LabelFieldPanel createMainPanel()
    {
        LabelFieldPanel panel = new LabelFieldPanel();
        panel.setBorder(new EmptyBorder(12, 0, 0, 40));
        panel.addField(new JLabel(REQUIRES));
        if (!blastnAvailable)
        {
            panel.addField(new JLabel(BLASTN_NOT_AVAILABLE));
        }
        panel.addSpacing(20);
        panel.addField("Sequence file name:", createSequenceFileNamePanel());
        panel.addField("Length:", createLengthPanel());
        panel.addField("Overlap:", createOverlapPanel());
        panel.addFinalSpacing();
        return panel;
    }

    /**
     * Create and return the button panel.
     *
     * @return the button panel
     */
    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalGlue());
        panel.add(new JButton(cancel));
        panel.add(Box.createHorizontalStrut(12));
        panel.add(plotButton);
        return panel;
    }

    /**
     * Create and return the sequence file name panel.
     *
     * @return the sequence file name panel
     */
    private JPanel createSequenceFileNamePanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(sequenceFileName);
        panel.add(Box.createHorizontalStrut(4));
        panel.add(new JButton(openFileDialog));
        return panel;
    }

    /**
     * Create and return the length panel.
     *
     * @return the length panel
     */
    private JPanel createLengthPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(length);
        panel.add(Box.createHorizontalStrut(12));
        panel.add(new JLabel("base pairs (bp)"));
        panel.add(Box.createHorizontalStrut(60));
        return panel;
    }

    /**
     * Create and return the overlap panel.
     *
     * @return the overlap panel
     */
    private JPanel createOverlapPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(overlap);
        panel.add(Box.createHorizontalStrut(12));
        panel.add(new JLabel("base pairs (bp)"));
        panel.add(Box.createHorizontalStrut(80));
        return panel;
    }

    /**
     * Cancel.
     */
    private void cancel()
    {
        getTopLevelAncestor().setVisible(false);
    }

    /**
     * Plot.
     */
    private void plot()
    {
        model.setLength(Integer.parseInt(length.getText()));
        model.setOverlap(Integer.parseInt(overlap.getText()));

        dialogTaskManager.execute(wormPlotTaskFactory.createTaskIterator(model));

        getTopLevelAncestor().setVisible(false);
    }

    /**
     * Return the plot button.
     *
     * @return the plot button
     */
    JButton getPlotButton()
    {
        return plotButton;
    }

    /**
     * Return the tool bar actions.
     *
     * @return the tool bar actions
     */
    Iterable<IdentifiableAction> getToolBarActions()
    {
        return ImmutableList.of(plot);
    }

    /**
     * Return true if <code>blastn</code> is callable via ProcessBuilder.
     *
     * @return true if <code>blastn</code> is callable via ProcessBuilder
     */
    private static boolean isBlastnAvailable()
    {
        try
        {
            ProcessBuilder blastn = new ProcessBuilder("blastn", "-h");
            Process blastnProcess = blastn.start();
            try
            {
                blastnProcess.waitFor();
            }
            catch (InterruptedException e)
            {
                // ignore
            }
            return true;
        }
        catch (IOException e)
        {
            // ignore
        }
        return false;
    }
}
