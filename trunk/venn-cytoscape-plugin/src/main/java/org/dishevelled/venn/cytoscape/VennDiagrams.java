/*

    dsh-venn-cytoscape-plugin  Cytoscape plugin for venn and euler diagrams.
    Copyright (c) 2010-2013 held jointly by the individual authors.

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
package org.dishevelled.venn.cytoscape;

import static javax.swing.SwingUtilities.windowForComponent;
import static org.dishevelled.venn.cytoscape.VennDiagramsUtils.installCloseKeyBinding;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;

import cytoscape.Cytoscape;

import cytoscape.plugin.CytoscapePlugin;

/**
 * Cytoscape plugin for venn and euler diagrams.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class VennDiagrams
    extends CytoscapePlugin
{
    /** Venn diagrams action. */
    private final Action vennDiagrams = new AbstractAction("Venn and Euler Diagrams...")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
                JDialog dialog = new JDialog(frame, "Venn and Euler Diagrams"); // i18n
                dialog.setContentPane(new GroupsView());
                dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                installCloseKeyBinding(dialog);
                dialog.setBounds(200, 200, 400, 400);
                dialog.setVisible(true);
            }
        };


    /**
     * Create a new Cytoscape plugin for venn and euler diagrams.
     */
    public VennDiagrams()
    {
        // todo:  check if on AWT event thread
        Cytoscape.getDesktop().getCyMenus().getOperationsMenu().add(vennDiagrams);
    }
}