/*

    dsh-venn-cytoscape-plugin  Cytoscape plugin for venn diagrams.
    Copyright (c) 2010-2012 held jointly by the individual authors.

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
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
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

import cytoscape.CyNode;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdToolBar;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

import org.dishevelled.venn.cytoscape.CyNodeListCellRenderer;
import org.dishevelled.venn.swing.BinaryVennList;
import org.dishevelled.venn.swing.TernaryVennList;
import org.dishevelled.venn.swing.QuaternaryVennList;

/**
 * Details view.
 *
 * @author  Michael Heuer
 */
final class DetailsView
    extends JPanel
{
    /** Maximum number of nodes above which selection sync should be disabled for binary details views. */
    private final int BINARY_SELECTION_SYNC_MAXIMUM = Integer.MAX_VALUE;

    /** Maximum number of nodes above which selection sync should be disabled for ternary details views. */
    private final int TERNARY_SELECTION_SYNC_MAXIMUM = 2000000;

    /** Maximum number of nodes above which selection sync should be disabled for quaternary details views. */
    private final int QUATERNARY_SELECTION_SYNC_MAXIMUM = 20000;

    /** Binary venn list. */
    private final BinaryVennList<CyNode> binaryVennList;

    /** Ternary venn list. */
    private final TernaryVennList<CyNode> ternaryVennList;

    /** Quaternary venn list. */
    private final QuaternaryVennList<CyNode> quaternaryVennList;

    /** Update Cytoscape selection. */
    private final SetChangeListener<CyNode> updateCytoscapeSelection = new SetChangeListener<CyNode>()
        {
            /** {@inheritDoc} */
            public void setChanged(final SetChangeEvent<CyNode> event)
            {
                // ick.  wholesale update every time
                CyNetwork currentNetwork = Cytoscape.getCurrentNetwork();
                currentNetwork.unselectAllNodes();
                currentNetwork.setSelectedNodeState(event.getObservableSet(), true);
                Cytoscape.getCurrentNetworkView().updateView();
            }
        };

    // todo: use identifiable actions for these, if a clear selection icon can be found
    //   ...there is EDIT_SELECT_ALL but 24x24 is not one of the tango icon sizes
    /** Select all action. */
    private final Action selectAll = new AbstractAction("Select all") // i18n
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
    private final Action clearSelection = new AbstractAction("Clear selection") // i18n
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


    /**
     * Create a new details view.
     *
     * @param binaryVennList binary venn list
     * @param ternaryVennList ternary venn list
     * @param quaternaryVennList quaternary venn list
     */
    private DetailsView(final BinaryVennList<CyNode> binaryVennList,
                        final TernaryVennList<CyNode> ternaryVennList,
                        final QuaternaryVennList<CyNode> quaternaryVennList)
    {
        super();
        this.binaryVennList = binaryVennList;
        this.ternaryVennList = ternaryVennList;
        this.quaternaryVennList = quaternaryVennList;

        // suck.
        if (this.binaryVennList != null)
        {
            this.binaryVennList.getFirst().setCellRenderer(new CyNodeListCellRenderer());
            this.binaryVennList.getFirstOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.binaryVennList.getIntersection().setCellRenderer(new CyNodeListCellRenderer());
            this.binaryVennList.getSecond().setCellRenderer(new CyNodeListCellRenderer());
            this.binaryVennList.getSecondOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.binaryVennList.getUnion().setCellRenderer(new CyNodeListCellRenderer());
        }
        if (this.ternaryVennList != null)
        {
            this.ternaryVennList.getFirst().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getFirstOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getFirstSecond().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getFirstThird().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getIntersection().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getSecond().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getSecondOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getSecondThird().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getThird().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getThirdOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.ternaryVennList.getUnion().setCellRenderer(new CyNodeListCellRenderer());
        }
        if (this.quaternaryVennList != null)
        {
            this.quaternaryVennList.getFirst().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFirstFourth().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFirstOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFirstSecond().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFirstSecondFourth().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFirstSecondThird().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFirstThird().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFirstThirdFourth().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFourth().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getFourthOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getIntersection().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getSecond().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getSecondFourth().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getSecondOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getSecondThird().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getSecondThirdFourth().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getThird().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getThirdFourth().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getThirdOnly().setCellRenderer(new CyNodeListCellRenderer());
            this.quaternaryVennList.getUnion().setCellRenderer(new CyNodeListCellRenderer());
        }

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke ctrlShiftA = KeyStroke.getKeyStroke(KeyEvent.VK_A, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftC = KeyStroke.getKeyStroke(KeyEvent.VK_C, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        inputMap.put(ctrlShiftA, "selectAll");
        inputMap.put(ctrlShiftC, "clearSelection");
        getActionMap().put("selectAll", selectAll);
        getActionMap().put("clearSelection", clearSelection);

        JMenuItem selectAllMenuItem = new JMenuItem(selectAll);
        selectAllMenuItem.setAccelerator(ctrlShiftA);
        JMenuItem clearSelectionMenuItem = new JMenuItem(clearSelection);
        clearSelectionMenuItem.setAccelerator(ctrlShiftC);

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
    DetailsView(final BinaryVennList<CyNode> binaryVennList)
    {
        this(binaryVennList, null, null);
        binaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        if (binaryVennList.getModel().union().size() > BINARY_SELECTION_SYNC_MAXIMUM)
        {
            selectAll.setEnabled(false);
            clearSelection.setEnabled(false);
        }
        else
        {
            binaryVennList.getModel().selection().addSetChangeListener(updateCytoscapeSelection);
        }
        add("Center", binaryVennList);
    }

    /**
     * Create a new details view with the specified ternary venn list.
     *
     * @param ternaryVennList ternary venn list
     */
    DetailsView(final TernaryVennList<CyNode> ternaryVennList)
    {
        this(null, ternaryVennList, null);
        ternaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        if (ternaryVennList.getModel().union().size() > TERNARY_SELECTION_SYNC_MAXIMUM)
        {
            selectAll.setEnabled(false);
            clearSelection.setEnabled(false);
        }
        else
        {
            ternaryVennList.getModel().selection().addSetChangeListener(updateCytoscapeSelection);
        }
        add("Center", ternaryVennList);
    }

    /**
     * Create a new details view with the specified quaternary venn list.
     *
     * @param quaternaryVennList quaternary venn list
     */
    DetailsView(final QuaternaryVennList<CyNode> quaternaryVennList)
    {
        this(null, null, quaternaryVennList);
        quaternaryVennList.setBorder(new EmptyBorder(12, 12, 12, 12));
        if (quaternaryVennList.getModel().union().size() > QUATERNARY_SELECTION_SYNC_MAXIMUM)
        {
            selectAll.setEnabled(false);
            clearSelection.setEnabled(false);
        }
        else
        {
            quaternaryVennList.getModel().selection().addSetChangeListener(updateCytoscapeSelection);
        }
        add("Center", quaternaryVennList);
    }
}