/*

    dsh-venn-cytoscape3-app  Cytoscape3 app for venn and euler diagrams.
    Copyright (c) 2012-2019 held jointly by the individual authors.

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

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdToolBar;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

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
    private static final int BINARY_SELECTION_SYNC_MAXIMUM = Integer.MAX_VALUE;

    /** Maximum number of nodes above which selection sync should be disabled for ternary details views. */
    private static final int TERNARY_SELECTION_SYNC_MAXIMUM = 2000000;

    /** Maximum number of nodes above which selection sync should be disabled for quaternary details views. */
    private static final int QUATERNARY_SELECTION_SYNC_MAXIMUM = 20000;

    /** Binary venn list. */
    private final BinaryVennList<CyNode> binaryVennList;

    /** Ternary venn list. */
    private final TernaryVennList<CyNode> ternaryVennList;

    /** Quaternary venn list. */
    private final QuaternaryVennList<CyNode> quaternaryVennList;

    /** Update Cytoscape selection. */
    private final SetChangeListener<CyNode> updateCytoscapeSelection = new SetChangeListener<CyNode>()
        {
            @Override
            public void setChanged(final SetChangeEvent<CyNode> event)
            {
                CyNetwork currentNetwork = applicationManager.getCurrentNetwork();
                for (CyNode node : currentNetwork.getNodeList())
                {
                    currentNetwork.getRow(node).set(CyNetwork.SELECTED, event.getObservableSet().contains(node));
                }
                applicationManager.getCurrentNetworkView().updateView();
            }
        };

    // todo: use identifiable actions for these, if a clear selection icon can be found
    //   ...there is EDIT_SELECT_ALL but 24x24 is not one of the tango icon sizes
    /** Select all action. */
    private final Action selectAll = new AbstractAction("Select all") // i18n
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
    private final Action clearSelection = new AbstractAction("Clear selection") // i18n
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

    /** Application manager. */
    private final CyApplicationManager applicationManager;


    /**
     * Create a new details view.
     *
     * @param binaryVennList binary venn list
     * @param ternaryVennList ternary venn list
     * @param quaternaryVennList quaternary venn list
     * @param applicationManager application manager, must not be null
     */
    private DetailsView(final BinaryVennList<CyNode> binaryVennList,
                        final TernaryVennList<CyNode> ternaryVennList,
                        final QuaternaryVennList<CyNode> quaternaryVennList,
                        final CyApplicationManager applicationManager)
    {
        super();
        if (applicationManager == null)
        {
            throw new IllegalArgumentException("applicationManager must not be null");
        }

        this.binaryVennList = binaryVennList;
        this.ternaryVennList = ternaryVennList;
        this.quaternaryVennList = quaternaryVennList;
        this.applicationManager = applicationManager;

        // suck.
        if (this.binaryVennList != null)
        {
            this.binaryVennList.getFirst().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.binaryVennList.getFirstOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.binaryVennList.getIntersection().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.binaryVennList.getSecond().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.binaryVennList.getSecondOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.binaryVennList.getUnion().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
        }
        if (this.ternaryVennList != null)
        {
            this.ternaryVennList.getFirst().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getFirstOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getFirstSecond().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getFirstThird().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getIntersection().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getSecond().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getSecondOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getSecondThird().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getThird().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getThirdOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.ternaryVennList.getUnion().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
        }
        if (this.quaternaryVennList != null)
        {
            this.quaternaryVennList.getFirst().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFirstFourth().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFirstOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFirstSecond().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFirstSecondFourth().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFirstSecondThird().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFirstThird().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFirstThirdFourth().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFourth().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getFourthOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getIntersection().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getSecond().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getSecondFourth().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getSecondOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getSecondThird().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getSecondThirdFourth().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getThird().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getThirdFourth().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getThirdOnly().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
            this.quaternaryVennList.getUnion().setCellRenderer(new CyNodeListCellRenderer(applicationManager));
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
     * @param applicationManager application manager, must not be null
     */
    DetailsView(final BinaryVennList<CyNode> binaryVennList, final CyApplicationManager applicationManager)
    {
        this(binaryVennList, null, null, applicationManager);
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
     * @param applicationManager application manager, must not be null
     */
    DetailsView(final TernaryVennList<CyNode> ternaryVennList, final CyApplicationManager applicationManager)
    {
        this(null, ternaryVennList, null, applicationManager);
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
     * @param applicationManager application manager, must not be null
     */
    DetailsView(final QuaternaryVennList<CyNode> quaternaryVennList, final CyApplicationManager applicationManager)
    {
        this(null, null, quaternaryVennList, applicationManager);
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
