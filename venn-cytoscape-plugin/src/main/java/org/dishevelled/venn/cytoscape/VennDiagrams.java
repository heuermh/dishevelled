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
import javax.swing.Action;
import javax.swing.JDialog;

import cytoscape.Cytoscape;

import cytoscape.plugin.CytoscapePlugin;

/*

  todos:

  layout dialogs with regards to main Cytoscape frame
  update groups list per listener
  create icons for actions
  update icon/text for actions on selection change
  turn off details selection when union size reaches a certian limit
  additional information in CyGroup list cell renderer, or use table
  additional information in CyNode list cell renderer, or use table
  add select all, select none, zoom in, zoom out actions to context menu
  provide keyboard access to pan & zoom
  test against 2.8-alpha or 2.8 built from svn source
  add dependencies report to site, or list/link dependency licenses explicitly
  implement export or remove action
  update screenshots to show interactivity of diagram view
  cut 0.2 release and find place to host it

 */

/**
 * Cytoscape plugin for venn diagrams.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class VennDiagrams
    extends CytoscapePlugin
{
    /** Venn diagrams action. */
    private final Action vennDiagrams = new AbstractAction("Venn Diagrams...")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                JDialog dialog = new JDialog(Cytoscape.getDesktop(), "Venn Diagrams"); // i18n
                dialog.setContentPane(new GroupsView());
                dialog.setBounds(200, 200, 400, 400);
                dialog.setVisible(true);
            }
        };


    /**
     * Create a new cytoscape plugin for venn diagrams.
     */
    public VennDiagrams()
    {
        // todo:  check if on AWT event thread
        Cytoscape.getDesktop().getCyMenus().getOperationsMenu().add(vennDiagrams);
    }
}