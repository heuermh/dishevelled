/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;

import javax.swing.border.EmptyBorder;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.application.swing.AbstractCyAction;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.cytoscape.work.swing.DialogTaskManager;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdToolBar;
import org.dishevelled.identify.ContextMenuListener;

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

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;

    /** Discrete mapping factory. */
    private final VisualMappingFunctionFactory discreteMappingFactory;

    /** Passthrough mapping factory. */
    private final VisualMappingFunctionFactory passthroughMappingFactory;


    /**
     * Create a new variation action.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     * @param discreteMappingFactory discrete mapping factory, must not be null
     * @param passthroughMappingFactory passthrough mapping factory, must not be null
     */
    VariationAction(final CyApplicationManager applicationManager,
                    final DialogTaskManager dialogTaskManager,
                    final VisualMappingManager visualMappingManager,
                    final VisualMappingFunctionFactory continuousMappingFactory,
                    final VisualMappingFunctionFactory discreteMappingFactory,
                    final VisualMappingFunctionFactory passthroughMappingFactory)
    {
        super("Variation");
        setPreferredMenu("Apps");

        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        checkNotNull(discreteMappingFactory);
        checkNotNull(passthroughMappingFactory);
        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
        this.discreteMappingFactory = discreteMappingFactory;
        this.passthroughMappingFactory = passthroughMappingFactory;
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
        JDialog dialog = new JDialog(frame, "Variation");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout());

        VariationApp app = new VariationApp(applicationManager,
                                            dialogTaskManager,
                                            visualMappingManager,
                                            continuousMappingFactory,
                                            discreteMappingFactory,
                                            passthroughMappingFactory);

        IdToolBar toolBar = new IdToolBar();
        toolBar.displayIconsAndText();
        toolBar.setIconSize(TangoProject.MEDIUM);
        for (IdentifiableAction action : app.getToolBarActions())
        {
            toolBar.add(action);
        }

        JPopupMenu toolBarContextMenu = new JPopupMenu();
        for (Object menuItem : toolBar.getDisplayMenuItems())
        {
            toolBarContextMenu.add((JCheckBoxMenuItem) menuItem);
        }
        /*

          wait for other size icons to be complete

        toolBarContextMenu.addSeparator();
        for (Object iconSize : TangoProject.SIZES)
        {
            toolBarContextMenu.add(toolBar.createIconSizeMenuItem((IconSize) iconSize));
        }
        */
        toolBar.addMouseListener(new ContextMenuListener(toolBarContextMenu));

        // todo:  would be nice to have a submenu in Apps --> Variation --> Retrieve Features... etc.
        contentPane.add("North", toolBar);
        contentPane.add("Center", app);
        dialog.setContentPane(contentPane);

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        installCloseKeyBinding(dialog);
        dialog.setBounds(200, 200, 1016, 628);
        dialog.setVisible(true);
    }
}
