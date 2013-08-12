/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.cytoscape3.internal;

import static javax.swing.SwingUtilities.windowForComponent;

import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.installCloseKeyBinding;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.event.ActionEvent;

import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import javax.swing.border.EmptyBorder;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.swing.DialogTaskManager;

/**
 * Variation action.
 *
 * @author  Michael Heuer
 */
final class VariationAction extends AbstractCyAction
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;


    /**
     * Create a new variation action.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     */
    VariationAction(final CyApplicationManager applicationManager,
                    final DialogTaskManager dialogTaskManager)
    {
        super("Variation");
        setPreferredMenu("Apps");

        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);
        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;
    }


    @Override
    public void actionPerformed(final ActionEvent event)
    {
        if (event == null)
        {
            throw new NullPointerException("event must not be null");
        }
        showDialog(event);
    }


    // would be nice to have a submenu in Apps --> Variation --> etc.
    //

    private void showDialog(final ActionEvent event)
    {
        JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
        JDialog dialog = new JDialog(frame, "Variation");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout());

        /*
        JToolBar toolBar = new JToolBar();
        toolBar.add(new AbstractAction("Annotate Known Variations...")
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    CyNetwork network = applicationManager.getCurrentNetwork();
                    AnnotateKnownVariationsTaskFactory annotateKnownVariationsTaskFactory = new AnnotateKnownVariationsTaskFactory("human",
                                                                                                                                   "GRCh37",
                                                                                                                                   "ensembl",
                                                                                                                                   network,
                                                                                                                                   featureService,
                                                                                                                                   variationService);
                    dialogTaskManager.execute(annotateKnownVariationsTaskFactory.createTaskIterator());
3                }
            });

        toolBar.add(new AbstractAction("Annotate Known Variation Consequences...")
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    CyNetwork network = applicationManager.getCurrentNetwork();
                    AnnotateKnownVariationConsequencesTaskFactory annotateKnownVariationConsequencesTaskFactory = new AnnotateKnownVariationConsequencesTaskFactory("human",
                                                                                                                                                                    "GRCh37",
                                                                                                                                                                    "ensembl",
                                                                                                                                                                    network,
                                                                                                                                                                    featureService,
                                                                                                                                                                    variationService,
                                                                                                                                                                    variationConsequenceService);
                    dialogTaskManager.execute(annotateKnownVariationConsequencesTaskFactory.createTaskIterator());
                }
            });

        toolBar.add(new AbstractAction("Predict Variation Consequences...")
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    CyNetwork network = applicationManager.getCurrentNetwork();
                    List<Variation> variations = Collections.emptyList(); // these need to come from VCF file or similar
                    AnnotateVariationConsequencesTaskFactory annotateVariationConsequencesTaskFactory = new AnnotateVariationConsequencesTaskFactory("human",
                                                                                                                                                     "GRCh37",
                                                                                                                                                     "ensembl",
                                                                                                                                                     network,
                                                                                                                                                     featureService,
                                                                                                                                                     variations,
                                                                                                                                                     variationConsequencePredictionService);
                    dialogTaskManager.execute(annotateVariationConsequencesTaskFactory.createTaskIterator());
                }
            });
        */
        VariationApp app = new VariationApp(dialogTaskManager);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Config", app.configView());
        tabbedPane.add("Features", app.featureView());
        tabbedPane.add("Variations", app.variationView());
        tabbedPane.add("Consequences", app.variationConsequenceView());

        //contentPane.add("North", toolBar);
        contentPane.add("Center", tabbedPane);
        dialog.setContentPane(contentPane);

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        installCloseKeyBinding(dialog);
        dialog.setBounds(200, 200, 888, 500);
        dialog.setVisible(true);
    }
}
