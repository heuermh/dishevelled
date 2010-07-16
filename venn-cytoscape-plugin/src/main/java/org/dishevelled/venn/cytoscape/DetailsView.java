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

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import javax.swing.border.EmptyBorder;

import cytoscape.CyNode;

import org.dishevelled.venn.swing.BinaryVennList;
import org.dishevelled.venn.swing.TernaryVennList;
import org.dishevelled.venn.swing.QuaternaryVennList;

/**
 * Details view.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class DetailsView
    extends JPanel
{
    // todo:  would be nice if *VennList had a shared superclass or interface

    /** Binary venn list. */
    private final BinaryVennList<CyNode> binaryVennList;

    /** Ternary venn list. */
    private final TernaryVennList<CyNode> ternaryVennList;

    /** Quaternary venn list. */
    private final QuaternaryVennList<CyNode> quaternaryVennList;

    /** Select all action. */
    private final Action selectAll = new AbstractAction("Select all")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                if (binaryVennList != null)
                {
                    binaryVennList.selectAll();
                }
                if (ternaryVennList != null)
                {
                    ternaryVennList.selectAll();
                }
                if (quaternaryVennList != null)
                {
                    quaternaryVennList.selectAll();
                }
            }
        };

    /** Clear selection action. */
    private final Action clearSelection = new AbstractAction("Clear selection")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                if (binaryVennList != null)
                {
                    binaryVennList.clearSelection();
                }
                if (ternaryVennList != null)
                {
                    ternaryVennList.clearSelection();
                }
                if (quaternaryVennList != null)
                {
                    quaternaryVennList.clearSelection();
                }
            }
        };


    private DetailsView(final BinaryVennList<CyNode> binaryVennList,
                        final TernaryVennList<CyNode> ternaryVennList,
                        final QuaternaryVennList<CyNode> quaternaryVennList)
    {
        super();

        this.binaryVennList = binaryVennList;
        this.ternaryVennList = ternaryVennList;
        this.quaternaryVennList = quaternaryVennList;

        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(selectAll);
        contextMenu.add(clearSelection);

        JToolBar toolBar = new JToolBar();
        toolBar.add(selectAll);
        toolBar.add(clearSelection);

        setLayout(new BorderLayout());
        add("North", toolBar);
        addMouseListener(new ContextMenuListener(contextMenu));
    }

    DetailsView(final BinaryVennList<CyNode> binaryVennList)
    {
        this(binaryVennList, null, null);
        binaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        add("Center", binaryVennList);
    }

    DetailsView(final TernaryVennList<CyNode> ternaryVennList)
    {
        this(null, ternaryVennList, null);
        ternaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        add("Center", ternaryVennList);
    }

    DetailsView(final QuaternaryVennList<CyNode> quaternaryVennList)
    {
        this(null, null, quaternaryVennList);
        quaternaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        add("Center", quaternaryVennList);
    }
}