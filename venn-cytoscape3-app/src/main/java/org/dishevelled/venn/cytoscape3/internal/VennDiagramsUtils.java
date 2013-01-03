/*

    dsh-venn-cytoscape3-app  Cytoscape3 app for venn and euler diagrams.
    Copyright (c) 2012-2013 held jointly by the individual authors.

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
package org.dishevelled.venn.cytoscape3.internal;

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;

import org.cytoscape.group.CyGroup;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

/**
 * Utility methods.
 *
 * @author  Michael Heuer
 */
final class VennDiagramsUtils
{

    /**
     * Private no-arg constructor.
     */
    private VennDiagramsUtils()
    {
        // empty
    }


    /**
     * Return the name of the specified group in the specified network.
     *
     * @param group group
     * @param network network
     * @return the name of the specified group in the specified network
     */
    static String nameOf(final CyGroup group, final CyNetwork network)
    {
        CyTable nodeTable = network.getDefaultNodeTable();
        CyRow nodeRow = nodeTable.getRow(group.getGroupNode().getSUID());
        String name = nodeRow.get(CyNetwork.NAME, String.class);
        return (name == null) ? group.toString() : name;
    }

    /**
     * Return the name of the specified node in the specified network.
     *
     * @param node node
     * @param network network
     * @return the name of the specified node in the specified network
     */
    static String nameOf(final CyNode node, final CyNetwork network)
    {
        CyTable nodeTable = network.getDefaultNodeTable();
        CyRow nodeRow = nodeTable.getRow(node.getSUID());
        String name = nodeRow.get(CyNetwork.NAME, String.class);
        return (name == null) ? node.toString() : name;
    }

    /**
     * Install a close action binding to <code>Ctrl-C</code>/<code>Command-C</code> for the specified dialog.
     *
     * @param dialog dialog, must not be null
     */
    static void installCloseKeyBinding(final JDialog dialog)
    {
        Action close = new AbstractAction()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
                }
            };
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke closeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, menuKeyMask);
        JRootPane rootPane = dialog.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(closeStroke, "close");
        rootPane.getActionMap().put("close", close);
    }
}