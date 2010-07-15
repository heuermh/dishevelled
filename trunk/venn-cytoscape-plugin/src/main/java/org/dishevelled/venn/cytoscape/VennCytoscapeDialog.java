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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import javax.swing.border.EmptyBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

import cytoscape.CyNode;

import cytoscape.groups.CyGroup;
import cytoscape.groups.CyGroupManager;

import cytoscape.view.CytoscapeDesktop;

import org.dishevelled.layout.ButtonPanel;
import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;

import org.dishevelled.venn.swing.BinaryVennList;
import org.dishevelled.venn.swing.TernaryVennList;
import org.dishevelled.venn.swing.QuaternaryVennList;

import org.piccolo2d.PCanvas;

import org.piccolo2d.util.PPaintContext;

/*

  todos:

  update groups list per listener
  diagram pan should require holding space bar
  diagram zoom should use mouse wheel
  tooltips on diagram with label text
  add zoom to center bounds with invokeLater, or recenter binary via offset
  add mouse click listeners to area nodes to update selection
  create icons for actions
  create export action
  change icon/text for actions on selection change
  export details panel selection to cytoscape selection
  add Select all and Clear selection context menu to details
  turn off details selection when union size reaches a certian limit
  additional information in CyGroup list cell renderer, or use table
  additional information in CyNode list cell renderer, or use table
  extract code from dialog to view class

 */

/**
 * Dialog for the venn diagram Cytoscape plugin.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class VennCytoscapeDialog
    extends JDialog
{
    /** List of groups. */
    private final EventList<CyGroup> groups;

    /** List selection. */
    private final EventList<CyGroup> selected;

    /** List of groups. */
    private final JList groupList;

    /** Context menu. */
    private final JPopupMenu contextMenu;

    /** Diagram action. */
    private final Action diagram = new AbstractAction("Diagram...")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                switch (selected.size())
                {
                    case 2:
                        binaryDiagram();
                        break;
                    case 3:
                        ternaryDiagram();
                        break;
                    case 4:
                        quaternaryDiagram();
                        break;
                    default:
                        break;
                }
            }
        };

    /** Details action. */
    private final Action details = new AbstractAction("Details...")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                switch (selected.size())
                {
                    case 2:
                        binaryDetails();
                        break;
                    case 3:
                        ternaryDetails();
                        break;
                    case 4:
                        quaternaryDetails();
                        break;
                    default:
                        break;
                }
            }
        };

    /** Export action. */
    private final Action export = new AbstractAction("Export...")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                switch (selected.size())
                {
                    case 2:
                        binaryExport();
                        break;
                    case 3:
                        ternaryExport();
                        break;
                    case 4:
                        quaternaryExport();
                        break;
                    default:
                        break;
                }
            }
        };

    /** Done action. */
    private final Action done = new AbstractAction("Done") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                done();
            }
        };

    /** List selection listener. */
    private final ListSelectionListener listSelectionListener = new ListSelectionListener()
        {
            /** {@inheritDoc} */
            public void valueChanged(final ListSelectionEvent event)
            {
                int size = selected.size();
                diagram.setEnabled(size > 1 && size < 5);
                details.setEnabled(size > 1 && size < 5);
                export.setEnabled(size > 1 && size < 5);
            }
        };

    /** Context menu listener. */
    private final MouseListener contextMenuListener = new MouseAdapter()
        {
            /** {@inheritDoc} */
            public void mousePressed(final MouseEvent event)
            {
                if (event.isPopupTrigger())
                {
                    showContextMenu(event);
                }
            }

            /** {@inheritDoc} */
            public void mouseReleased(final MouseEvent event)
            {
                if (event.isPopupTrigger())
                {
                    showContextMenu(event);
                }
            }

            /**
             * Show context menu.
             */
            private void showContextMenu(final MouseEvent event)
            {
                contextMenu.show(event.getComponent(), event.getX(), event.getY());
            }
        };


    /**
     * Create a new dialog for the venn diagram Cytoscape plugin
     * with the specified Cytoscape desktop.
     *
     * @param desktop Cytoscape desktop
     */
    VennCytoscapeDialog(final CytoscapeDesktop desktop)
    {
        super(desktop, "Venn Diagrams"); // i18n

        groups = GlazedLists.eventList(CyGroupManager.getGroupList());
        EventListModel<CyGroup> listModel = new EventListModel<CyGroup>(groups);
        EventSelectionModel<CyGroup> selectionModel = new EventSelectionModel<CyGroup>(groups);
        selected = selectionModel.getSelected();
        selectionModel.addListSelectionListener(listSelectionListener); // or use event list listener
        groupList = new JList(listModel);
        groupList.setSelectionModel(selectionModel);
        groupList.addMouseListener(contextMenuListener);

        contextMenu = new JPopupMenu();
        contextMenu.add(diagram);
        contextMenu.add(details);
        contextMenu.add(export);

        diagram.setEnabled(false);
        details.setEnabled(false);
        export.setEnabled(false);

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        LabelFieldPanel mainPanel = new LabelFieldPanel();
        mainPanel.setBorder(new EmptyBorder(12, 12, 0, 12));
        mainPanel.addLabel("Groups:"); // i18n
        mainPanel.addFinalField(new JScrollPane(groupList));

        JToolBar toolBar = new JToolBar();
        toolBar.add(diagram);
        toolBar.add(details);
        toolBar.add(export);

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(24, 12, 12, 12));
        buttonPanel.add(done);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add("North", toolBar);
        contentPane.add("Center", mainPanel);
        contentPane.add("South", buttonPanel);
        setContentPane(contentPane);
    }

    /**
     * Done.
     */
    private void done()
    {
        setVisible(false);
    }

    /**
     * Show a binary venn diagram.
     */
    private void binaryDiagram()
    {
        String firstLabel = selected.get(0).getGroupName();
        String secondLabel = selected.get(1).getGroupName();

        JDialog dialog = new JDialog(this, firstLabel + ", " + secondLabel + " Diagram");

        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodes());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodes());
        BinaryVennNode<CyNode> node = new BinaryVennNode<CyNode>(firstLabel, first, secondLabel, second);
        node.offset(150.0d, 150.0d);
        canvas.getLayer().addChild(node);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add("Center", canvas);
        dialog.setContentPane(contentPane);
        // offset per this
        dialog.setBounds(100, 100, 400, 400);
        dialog.setVisible(true);
    }

    /**
     * Show a ternary venn diagram.
     */
    private void ternaryDiagram()
    {
        String firstLabel = selected.get(0).getGroupName();
        String secondLabel = selected.get(1).getGroupName();
        String thirdLabel = selected.get(2).getGroupName();

        JDialog dialog = new JDialog(this, firstLabel + ", " + secondLabel + ", " + thirdLabel + " Diagram");

        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodes());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodes());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodes());
        TernaryVennNode<CyNode> node = new TernaryVennNode<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third);
        node.offset(75.0d, 75.0d);
        canvas.getLayer().addChild(node);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add("Center", canvas);
        dialog.setContentPane(contentPane);
        // offset per this
        dialog.setBounds(100, 100, 400, 400);
        dialog.setVisible(true);
    }

    /**
     * Show a quaternary venn diagram.
     */
    private void quaternaryDiagram()
    {
        String firstLabel = selected.get(0).getGroupName();
        String secondLabel = selected.get(1).getGroupName();
        String thirdLabel = selected.get(2).getGroupName();
        String fourthLabel = selected.get(3).getGroupName();

        JDialog dialog = new JDialog(this, firstLabel + ", " + secondLabel + ", " + thirdLabel + ", " + fourthLabel + " Diagram");

        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodes());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodes());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodes());
        Set<CyNode> fourth = new HashSet<CyNode>(selected.get(3).getNodes());
        QuaternaryVennNode<CyNode> node = new QuaternaryVennNode<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third, fourthLabel, fourth);
        node.offset(40.0d, 235.0d);
        canvas.getLayer().addChild(node);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add("Center", canvas);
        dialog.setContentPane(contentPane);
        // offset per this
        dialog.setBounds(100, 100, 600, 600);
        dialog.setVisible(true);
    }

    /**
     * Show binary venn details.
     */
    private void binaryDetails()
    {
        String firstLabel = selected.get(0).getGroupName();
        String secondLabel = selected.get(1).getGroupName();

        JDialog dialog = new JDialog(this, firstLabel + ", " + secondLabel + " Details");

        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodes());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodes());
        BinaryVennList<CyNode> list = new BinaryVennList<CyNode>(firstLabel, first, secondLabel, second);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout());
        contentPane.add("Center", list);
        dialog.setContentPane(contentPane);
        // offset per this
        dialog.setBounds(100, 100, 600, 450);
        dialog.setVisible(true);
    }

    /**
     * Show ternary venn details.
     */
    private void ternaryDetails()
    {
        String firstLabel = selected.get(0).getGroupName();
        String secondLabel = selected.get(1).getGroupName();
        String thirdLabel = selected.get(2).getGroupName();

        JDialog dialog = new JDialog(this, firstLabel + ", " + secondLabel + ", " + thirdLabel + " Details");

        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodes());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodes());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodes());
        TernaryVennList<CyNode> list = new TernaryVennList<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout());
        contentPane.add("Center", list);
        dialog.setContentPane(contentPane);
        // offset per this
        dialog.setBounds(100, 100, 747, 669);
        dialog.setVisible(true);
    }

    /**
     * Show quaternary venn details.
     */
    private void quaternaryDetails()
    {
        String firstLabel = selected.get(0).getGroupName();
        String secondLabel = selected.get(1).getGroupName();
        String thirdLabel = selected.get(2).getGroupName();
        String fourthLabel = selected.get(3).getGroupName();

        JDialog dialog = new JDialog(this, firstLabel + ", " + secondLabel + ", " + thirdLabel + ", " + fourthLabel + " Details");

        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodes());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodes());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodes());
        Set<CyNode> fourth = new HashSet<CyNode>(selected.get(3).getNodes());
        QuaternaryVennList<CyNode> list = new QuaternaryVennList<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third, fourthLabel, fourth);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout());
        contentPane.add("Center", list);
        dialog.setContentPane(contentPane);
        // offset per this
        dialog.setBounds(100, 100, 894, 888);
        dialog.setVisible(true);
    }

    /**
     * Export binary venn results.
     */
    private void binaryExport()
    {
        // a group of checkboxes then select a directory file chooser?
    }

    /**
     * Export ternary venn results.
     */
    private void ternaryExport()
    {
    }

    /**
     * Export quaternary venn results.
     */
    private void quaternaryExport()
    {
    }
}