/*

    dsh-venn-cytoscape-plugin  Cytoscape plugin for venn diagrams.
    Copyright (c) 2010 held jointly by the individual authors.

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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import cytoscape.Cytoscape;

/**
 * Action to display the dialog for the venn diagram Cytoscape plugin.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class VennCytoscapeAction
    extends AbstractAction
{

    /**
     * Create a new action to display the dialog for the venn diagram Cytoscape plugin.
     */
    VennCytoscapeAction()
    {
        super("Venn Diagrams...");  // i18n
    }


    /** {@inheritDoc} */
    public void actionPerformed(final ActionEvent event)
    {
        VennCytoscapeDialog dialog = new VennCytoscapeDialog(Cytoscape.getDesktop());
        // todo:  layout dialog with regards to main frame
        dialog.setBounds(200, 200, 400, 400);
        dialog.setVisible(true);
    }
}