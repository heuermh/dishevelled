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

import static javax.swing.SwingUtilities.windowForComponent;

import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.wormplot.cytoscape3.internal.WormPlotUtils.installCloseKeyBinding;

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

import org.cytoscape.util.swing.FileUtil;

import org.cytoscape.work.swing.DialogTaskManager;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdToolBar;
import org.dishevelled.identify.ContextMenuListener;

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

    /** File util. */
    private final FileUtil fileUtil;

    /** Worm plot task factory. */
    private final WormPlotTaskFactory wormPlotTaskFactory;


    /**
     * Create a new worm plot action.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     * @param fileUtil file util, must not be null
     * @param wormPlotTaskFactory worm plot task factory, must not be null
     */
    WormPlotAction(final CyApplicationManager applicationManager,
                   final DialogTaskManager dialogTaskManager,
                   final FileUtil fileUtil,
                   final WormPlotTaskFactory wormPlotTaskFactory)
    {
        super("Worm Plot");
        setPreferredMenu("Apps");

        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);
        checkNotNull(fileUtil);
        checkNotNull(wormPlotTaskFactory);
        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;
        this.fileUtil = fileUtil;
        this.wormPlotTaskFactory = wormPlotTaskFactory;
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

        WormPlotApp app = new WormPlotApp(applicationManager, dialogTaskManager, fileUtil, wormPlotTaskFactory);

        IdToolBar toolBar = new IdToolBar();
        toolBar.displayIconsAndText();
        for (IdentifiableAction action : app.getToolBarActions())
        {
            toolBar.add(action);
        }

        JPopupMenu toolBarContextMenu = new JPopupMenu();
        for (Object menuItem : toolBar.getDisplayMenuItems())
        {
            toolBarContextMenu.add((JCheckBoxMenuItem) menuItem);
        }
        toolBar.addMouseListener(new ContextMenuListener(toolBarContextMenu));
        toolBarContextMenu.addSeparator();
        toolBarContextMenu.add(toolBar.createIconSizeMenuItem(TangoProject.EXTRA_SMALL));
        toolBarContextMenu.add(toolBar.createIconSizeMenuItem(TangoProject.SMALL));

        toolBar.setIconSize(TangoProject.MEDIUM);
        JCheckBoxMenuItem mediumMenuItem = toolBar.createIconSizeMenuItem(TangoProject.MEDIUM);
        mediumMenuItem.setEnabled(true);

        toolBarContextMenu.add(mediumMenuItem);

        contentPane.add("North", toolBar);
        contentPane.add("Center", app);

        dialog.setContentPane(contentPane);
        dialog.getRootPane().setDefaultButton(app.getPlotButton());
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        installCloseKeyBinding(dialog);
        dialog.setBounds(200, 200, 647, 400);
        dialog.setVisible(true);
    }
}
