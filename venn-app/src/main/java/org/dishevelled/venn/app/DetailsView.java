/*

    dsh-venn-app  App for venn and euler diagrams.
    Copyright (c) 2013-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.app;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.border.EmptyBorder;

import org.dishevelled.venn.swing.BinaryVennList;
import org.dishevelled.venn.swing.TernaryVennList;
import org.dishevelled.venn.swing.QuaternaryVennList;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdToolBar;

/**
 * Details view.
 *
 * @author  Michael Heuer
 */
final class DetailsView
    extends JPanel
{
    /** Maximum number of nodes above which selection sync should be disabled for binary details views. */
    private static final int BINARY_SELECTION_SYNC_MAXIMUM = Integer.MAX_VALUE;

    /** Maximum number of nodes above which selection sync should be disabled for ternary details views. */
    private static final int TERNARY_SELECTION_SYNC_MAXIMUM = 2000000;

    /** Maximum number of nodes above which selection sync should be disabled for quaternary details views. */
    private static final int QUATERNARY_SELECTION_SYNC_MAXIMUM = 20000;

    /** Binary venn list. */
    private final BinaryVennList<String> binaryVennList;

    /** Ternary venn list. */
    private final TernaryVennList<String> ternaryVennList;

    /** Quaternary venn list. */
    private final QuaternaryVennList<String> quaternaryVennList;

    // todo: use identifiable actions for these, if a clear selection icon can be found
    //   ...there is EDIT_SELECT_ALL but 24x24 is not one of the tango icon sizes
    /** Select all action. */
    private final Action selectAll = new AbstractAction("Select all")
        {
            @Override
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
            @Override
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


    /**
     * Create a new details view.
     *
     * @param binaryVennList binary venn list
     * @param ternaryVennList ternary venn list
     * @param quaternaryVennList quaternary venn list
     */
    private DetailsView(final BinaryVennList<String> binaryVennList,
                        final TernaryVennList<String> ternaryVennList,
                        final QuaternaryVennList<String> quaternaryVennList)
    {
        super();
        this.binaryVennList = binaryVennList;
        this.ternaryVennList = ternaryVennList;
        this.quaternaryVennList = quaternaryVennList;

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke ctrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, menuKeyMask);
        inputMap.put(ctrlA, "selectAll");
        getActionMap().put("selectAll", selectAll);

        JMenuItem selectAllMenuItem = new JMenuItem(selectAll);
        selectAllMenuItem.setAccelerator(ctrlA);
        JMenuItem clearSelectionMenuItem = new JMenuItem(clearSelection);

        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(selectAllMenuItem);
        contextMenu.add(clearSelectionMenuItem);

        IdToolBar toolBar = new IdToolBar();
        // todo:  odd border decorations on Win L&F
        toolBar.add(selectAll);
        toolBar.add(clearSelection);
        toolBar.displayText();

        setLayout(new BorderLayout());
        add("North", toolBar);
        addMouseListener(new ContextMenuListener(contextMenu));
    }

    /**
     * Create a new details view with the specified binary venn list.
     *
     * @param binaryVennList binary venn list
     */
    DetailsView(final BinaryVennList<String> binaryVennList)
    {
        this(binaryVennList, null, null);
        binaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        if (binaryVennList.getModel().union().size() > BINARY_SELECTION_SYNC_MAXIMUM)
        {
            selectAll.setEnabled(false);
            clearSelection.setEnabled(false);
        }
        add("Center", binaryVennList);
    }

    /**
     * Create a new details view with the specified ternary venn list.
     *
     * @param ternaryVennList ternary venn list
     */
    DetailsView(final TernaryVennList<String> ternaryVennList)
    {
        this(null, ternaryVennList, null);
        ternaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        if (ternaryVennList.getModel().union().size() > TERNARY_SELECTION_SYNC_MAXIMUM)
        {
            selectAll.setEnabled(false);
            clearSelection.setEnabled(false);
        }
        add("Center", ternaryVennList);
    }

    /**
     * Create a new details view with the specified quaternary venn list.
     *
     * @param quaternaryVennList quaternary venn list
     */
    DetailsView(final QuaternaryVennList<String> quaternaryVennList)
    {
        this(null, null, quaternaryVennList);
        quaternaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        if (quaternaryVennList.getModel().union().size() > QUATERNARY_SELECTION_SYNC_MAXIMUM)
        {
            selectAll.setEnabled(false);
            clearSelection.setEnabled(false);
        }
        add("Center", quaternaryVennList);
    }
}
