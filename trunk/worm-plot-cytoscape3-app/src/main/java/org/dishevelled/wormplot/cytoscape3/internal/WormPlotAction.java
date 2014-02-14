/*

    dsh-worm-plot-cytoscape3-app  Worm plot Cytoscape 3 app.
    Copyright (c) 2014 held jointly by the individual authors.

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

import static javax.swing.SwingUtilities.windowForComponent;

import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.wormplot.cytoscape3.internal.WormPlotUtils.installCloseKeyBinding;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import javax.swing.border.EmptyBorder;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.application.swing.AbstractCyAction;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.cytoscape.work.swing.DialogTaskManager;

/**
 * Worm plot action.
 *
 * @author  Michael Heuer
 */
final class WormPlotAction extends AbstractCyAction
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;

    /** Discrete mapping factory. */
    private final VisualMappingFunctionFactory discreteMappingFactory;


    /**
     * Create a new worm plot action.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     * @param discreteMappingFactory discrete mapping factory, must not be null
     */
    WormPlotAction(final CyApplicationManager applicationManager,
                   final DialogTaskManager dialogTaskManager,
                   final VisualMappingManager visualMappingManager,
                   final VisualMappingFunctionFactory continuousMappingFactory,
                   final VisualMappingFunctionFactory discreteMappingFactory)
    {
        super("Worm Plot");
        setPreferredMenu("Apps");

        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        checkNotNull(discreteMappingFactory);
        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
        this.discreteMappingFactory = discreteMappingFactory;
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

    /**
     * Show dialog.
     *
     * @param event event
     */
    private void showDialog(final ActionEvent event)
    {
        JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
        JDialog dialog = new JDialog(frame, "Worm Plot");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout());

        WormPlotApp app = new WormPlotApp(applicationManager, dialogTaskManager);
        contentPane.add("Center", app);

        dialog.setContentPane(contentPane);
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        installCloseKeyBinding(dialog);
        dialog.setBounds(200, 200, 600, 372);
        dialog.setVisible(true);
    }
}