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

import static javax.swing.SwingUtilities.windowForComponent;
import static org.dishevelled.venn.cytoscape3.internal.VennDiagramsUtils.installCloseKeyBinding;
import static org.dishevelled.venn.cytoscape3.internal.VennDiagramsUtils.nameOf;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

import com.google.common.base.Joiner;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.group.CyGroup;
import org.cytoscape.group.CyGroupManager;
import org.cytoscape.group.events.GroupAboutToBeDestroyedEvent;
import org.cytoscape.group.events.GroupAboutToBeDestroyedListener;
import org.cytoscape.group.events.GroupAboutToBeRemovedEvent;
import org.cytoscape.group.events.GroupAboutToBeRemovedListener;
import org.cytoscape.group.events.GroupAddedEvent;
import org.cytoscape.group.events.GroupAddedListener;
import org.cytoscape.group.events.GroupAddedToNetworkEvent;
import org.cytoscape.group.events.GroupAddedToNetworkListener;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.service.util.CyServiceRegistrar;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.impl.CachingIconBundle;
import org.dishevelled.iconbundle.impl.PNGIconBundle;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdButton;
import org.dishevelled.identify.IdMenuItem;
import org.dishevelled.identify.IdToolBar;

import org.dishevelled.layout.ButtonPanel;
import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;
import org.dishevelled.piccolo.venn.VennNode;

import org.dishevelled.venn.VennModel;
import org.dishevelled.venn.VennLayout;
import org.dishevelled.venn.VennLayouter;
import org.dishevelled.venn.VennLayouter.PerformanceHint;

import org.dishevelled.venn.model.VennModels;

import org.dishevelled.venn.swing.BinaryVennList;
import org.dishevelled.venn.swing.TernaryVennList;
import org.dishevelled.venn.swing.QuaternaryVennList;

import org.cytoscape.venneuler.VennEulerLayouter;

/**
 * Groups view.
 */
final class GroupsView
    extends JPanel
    implements GroupAboutToBeDestroyedListener, GroupAboutToBeRemovedListener, GroupAddedListener, GroupAddedToNetworkListener
{
    /** List of groups. */
    private final EventList<CyGroup> groups;

    /** List selection. */
    private final EventList<CyGroup> selected;

    /** List of groups. */
    private final JList groupList;

    /** Context menu. */
    private final JPopupMenu contextMenu;

    /** Euler diagram action icon bundle. */
    private final IconBundle eulerDiagramIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/venn/cytoscape3/internal/eulerDiagram"));

    /** Euler diagram action. */
    private final IdentifiableAction eulerDiagram = new IdentifiableAction("Euler Diagram...", eulerDiagramIconBundle) // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                if (selected.size() > 1)
                {
                    eulerDiagram();
                }
            }
        };

    /** Venn diagram action icon bundle. */
    private final IconBundle vennDiagramIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/venn/cytoscape3/internal/vennDiagram"));

    /** Venn diagram action. */
    private final IdentifiableAction vennDiagram = new IdentifiableAction("Venn Diagram...", vennDiagramIconBundle) // i18n
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

    /** Details action icon bundle. */
    private final IconBundle detailsIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/venn/cytoscape3/internal/details"));

    /** Details action. */
    private final IdentifiableAction details = new IdentifiableAction("Details...", detailsIconBundle) // i18n
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
                eulerDiagram.setEnabled(size > 1);
                vennDiagram.setEnabled(size > 1 && size < 5);
                details.setEnabled(size > 1 && size < 5);
            }
        };

    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Venn euler layouter. */
    private final VennLayouter<CyNode> vennLayouter = new VennEulerLayouter<CyNode>();


    /**
     * Create a new groups view.
     *
     * @param applicationManager application manager, must not be null
     * @param groupManager group manager, must not be null
     * @param serviceRegistrar service registrar, must not be null
     */
    GroupsView(final CyApplicationManager applicationManager, final CyGroupManager groupManager, final CyServiceRegistrar serviceRegistrar)
    {
        super();
        if (applicationManager == null)
        {
            throw new IllegalArgumentException("applicationManager must not be null");
        }
        if (groupManager == null)
        {
            throw new IllegalArgumentException("groupManager must not be null");
        }
        if (serviceRegistrar == null)
        {
            throw new IllegalArgumentException("serviceRegistrar must not be null");
        }
        this.applicationManager = applicationManager;

        Set<CyGroup> groupSet = groupManager.getGroupSet(applicationManager.getCurrentNetwork());
        groups = GlazedLists.eventList(new ArrayList<CyGroup>(groupSet));
        EventListModel<CyGroup> listModel = new EventListModel<CyGroup>(groups);
        EventSelectionModel<CyGroup> selectionModel = new EventSelectionModel<CyGroup>(groups);
        selected = selectionModel.getSelected();
        selectionModel.addListSelectionListener(listSelectionListener); // or use event list listener
        groupList = new JList(listModel);
        groupList.setSelectionModel(selectionModel);
        groupList.setCellRenderer(new CyGroupListCellRenderer(applicationManager));

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke ctrlShiftE = KeyStroke.getKeyStroke(KeyEvent.VK_E, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftV = KeyStroke.getKeyStroke(KeyEvent.VK_V, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftD = KeyStroke.getKeyStroke(KeyEvent.VK_D, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        inputMap.put(ctrlShiftE, "eulerDiagram");
        inputMap.put(ctrlShiftV, "vennDiagram");
        inputMap.put(ctrlShiftD, "details");
        getActionMap().put("eulerDiagram", eulerDiagram);
        getActionMap().put("vennDiagram", vennDiagram);
        getActionMap().put("details", details);

        IdMenuItem eulerDiagramMenuItem = new IdMenuItem(eulerDiagram);
        eulerDiagramMenuItem.setAccelerator(ctrlShiftE);
        IdMenuItem vennDiagramMenuItem = new IdMenuItem(vennDiagram);
        vennDiagramMenuItem.setAccelerator(ctrlShiftV);
        IdMenuItem detailsMenuItem = new IdMenuItem(details);
        detailsMenuItem.setAccelerator(ctrlShiftD);

        contextMenu = new JPopupMenu();
        contextMenu.add(eulerDiagramMenuItem);
        contextMenu.add(vennDiagramMenuItem);
        contextMenu.add(detailsMenuItem);
        groupList.addMouseListener(new ContextMenuListener(contextMenu));

        eulerDiagram.setEnabled(false);
        vennDiagram.setEnabled(false);
        details.setEnabled(false);

        layoutComponents();

        Properties properties = new Properties();
        serviceRegistrar.registerService(this, GroupAboutToBeDestroyedListener.class, properties);
        serviceRegistrar.registerService(this, GroupAboutToBeRemovedListener.class, properties);
        serviceRegistrar.registerService(this, GroupAddedListener.class, properties);
        serviceRegistrar.registerService(this, GroupAddedToNetworkListener.class, properties);
        // todo: serviceRegistrar.unregisterAllServices(this); when closing
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

        IdToolBar toolBar = new IdToolBar();
        IdButton eulerDiagramButton = toolBar.add(eulerDiagram);
        eulerDiagramButton.setBorderPainted(false);
        eulerDiagramButton.setFocusPainted(false);
        IdButton vennDiagramButton = toolBar.add(vennDiagram);
        vennDiagramButton.setBorderPainted(false);
        vennDiagramButton.setFocusPainted(false);
        IdButton detailsButton = toolBar.add(details);
        detailsButton.setBorderPainted(false);
        detailsButton.setFocusPainted(false);

        toolBar.displayIcons();
        toolBar.setIconSize(IconSize.DEFAULT_24X24);

        JPopupMenu toolBarContextMenu = new JPopupMenu();
        for (Object menuItem : toolBar.getDisplayMenuItems())
        {
            toolBarContextMenu.add((JCheckBoxMenuItem) menuItem);
        }
        toolBar.addMouseListener(new ContextMenuListener(toolBarContextMenu));

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(24, 12, 12, 12));
        buttonPanel.add(done);

        setLayout(new BorderLayout());
        add("North", toolBar);
        add("Center", mainPanel);
        add("South", buttonPanel);
    }

    /**
     * Done.
     */
    private void done()
    {
        windowForComponent(this).setVisible(false);
        // todo:  unregister listeners and dispose?
    }

    /**
     * Show a euler diagram.
     */
    private void eulerDiagram()
    {
        CyNetwork network = applicationManager.getCurrentNetwork();
        List<String> labels = new ArrayList<String>(selected.size());
        List<Set<CyNode>> sets = new ArrayList<Set<CyNode>>(selected.size());
        for (CyGroup selectedGroup : selected)
        {
            labels.add(nameOf(selectedGroup, network));
            sets.add(new HashSet<CyNode>(selectedGroup.getNodeList()));
        }
        final VennModel<CyNode> model = VennModels.createVennModel(sets);
        final VennNode<CyNode> vennNode = new VennNode<CyNode>(model);
        // add ctr that takes List<String> labels as parameter?
        for (int i = 0, size = labels.size(); i < size; i++)
        {
            vennNode.setLabelText(i, labels.get(i));
        }

        JDialog dialog = new JDialog(windowForComponent(this), Joiner.on(", ").join(labels) + " Euler Diagram");
        dialog.setContentPane(new DiagramView(vennNode, applicationManager));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent dialog
        dialog.setBounds(100, 100, 600, 600);
        if (model.size() > 4)
        {
            dialog.setBounds(100, 100, 800, 800);
        }
        dialog.setVisible(true);

        // run in a cytoscape task?
        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    Rectangle2D.Double boundingRectangle = new Rectangle2D.Double(0.0d, 0.0d, 400.0d, 400.0d);
                    if (model.size() > 4)
                    {
                        boundingRectangle.setRect(0.0d, 0.0d, 600.0d, 600.0d);
                    }
                    VennLayout layout = vennLayouter.layout(model, boundingRectangle, PerformanceHint.OPTIMIZE_FOR_SPEED);
                    vennNode.setLayout(layout);
                }
            });
    }

    /**
     * Show a binary venn diagram.
     */
    private void binaryDiagram()
    {
        CyNetwork network = applicationManager.getCurrentNetwork();
        String firstLabel = nameOf(selected.get(0), network);
        String secondLabel = nameOf(selected.get(1), network);
        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodeList());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodeList());
        BinaryVennNode<CyNode> binaryVennNode = new BinaryVennNode<CyNode>(firstLabel, first, secondLabel, second);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + " Venn Diagram");
        dialog.setContentPane(new DiagramView(binaryVennNode, applicationManager));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent dialog
        dialog.setBounds(100, 100, 400, 400);
        dialog.setVisible(true);
    }

    /**
     * Show a ternary venn diagram.
     */
    private void ternaryDiagram()
    {
        CyNetwork network = applicationManager.getCurrentNetwork();
        String firstLabel = nameOf(selected.get(0), network);
        String secondLabel = nameOf(selected.get(1), network);
        String thirdLabel = nameOf(selected.get(2), network);
        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodeList());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodeList());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodeList());
        TernaryVennNode<CyNode> ternaryVennNode = new TernaryVennNode<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + " Venn Diagram");
        dialog.setContentPane(new DiagramView(ternaryVennNode, applicationManager));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent dialog
        dialog.setBounds(100, 100, 400, 400);
        dialog.setVisible(true);
    }

    /**
     * Show a quaternary venn diagram.
     */
    private void quaternaryDiagram()
    {
        CyNetwork network = applicationManager.getCurrentNetwork();
        String firstLabel = nameOf(selected.get(0), network);
        String secondLabel = nameOf(selected.get(1), network);
        String thirdLabel = nameOf(selected.get(2), network);
        String fourthLabel = nameOf(selected.get(3), network);
        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodeList());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodeList());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodeList());
        Set<CyNode> fourth = new HashSet<CyNode>(selected.get(3).getNodeList());
        QuaternaryVennNode<CyNode> quaternaryVennNode = new QuaternaryVennNode<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third, fourthLabel, fourth);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + ", " + fourthLabel + " Venn Diagram");
        dialog.setContentPane(new DiagramView(quaternaryVennNode, applicationManager));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent dialog
        dialog.setBounds(100, 100, 600, 600);
        dialog.setVisible(true);
    }

    /**
     * Show binary venn details.
     */
    private void binaryDetails()
    {
        CyNetwork network = applicationManager.getCurrentNetwork();
        String firstLabel = nameOf(selected.get(0), network);
        String secondLabel = nameOf(selected.get(1), network);
        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodeList());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodeList());
        final BinaryVennList<CyNode> binaryVennList = new BinaryVennList<CyNode>(firstLabel, first, secondLabel, second);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + " Details");
        dialog.setContentPane(new DetailsView(binaryVennList, applicationManager));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent dialog
        dialog.setBounds(100, 100, 600, 450);
        dialog.setVisible(true);
    }

    /**
     * Show ternary venn details.
     */
    private void ternaryDetails()
    {
        CyNetwork network = applicationManager.getCurrentNetwork();
        String firstLabel = nameOf(selected.get(0), network);
        String secondLabel = nameOf(selected.get(1), network);
        String thirdLabel = nameOf(selected.get(2), network);
        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodeList());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodeList());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodeList());
        final TernaryVennList<CyNode> ternaryVennList = new TernaryVennList<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + " Details");
        dialog.setContentPane(new DetailsView(ternaryVennList, applicationManager));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent dialog
        dialog.setBounds(100, 100, 747, 669);
        dialog.setVisible(true);
    }

    /**
     * Show quaternary venn details.
     */
    private void quaternaryDetails()
    {
        CyNetwork network = applicationManager.getCurrentNetwork();
        String firstLabel = nameOf(selected.get(0), network);
        String secondLabel = nameOf(selected.get(1), network);
        String thirdLabel = nameOf(selected.get(2), network);
        String fourthLabel = nameOf(selected.get(3), network);
        Set<CyNode> first = new HashSet<CyNode>(selected.get(0).getNodeList());
        Set<CyNode> second = new HashSet<CyNode>(selected.get(1).getNodeList());
        Set<CyNode> third = new HashSet<CyNode>(selected.get(2).getNodeList());
        Set<CyNode> fourth = new HashSet<CyNode>(selected.get(3).getNodeList());
        final QuaternaryVennList<CyNode> quaternaryVennList = new QuaternaryVennList<CyNode>(firstLabel, first, secondLabel, second, thirdLabel, third, fourthLabel, fourth);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + ", " + fourthLabel + " Details");
        dialog.setContentPane(new DetailsView(quaternaryVennList, applicationManager));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent dialog
        dialog.setBounds(100, 100, 894, 888);
        dialog.setVisible(true);
    }

    @Override
    public void handleEvent(final GroupAboutToBeDestroyedEvent event)
    {
        groups.remove(event.getGroup());
    }

    @Override
    public void handleEvent(final GroupAboutToBeRemovedEvent event)
    {
        if (event.getNetwork().equals(applicationManager.getCurrentNetwork())) {
            groups.remove((CyGroup) event.getSource());
        }
    }

    @Override
    public void handleEvent(final GroupAddedEvent event)
    {
        groups.add(event.getGroup());
    }

    @Override
    public void handleEvent(final GroupAddedToNetworkEvent event)
    {
        if (event.getNetwork().equals(applicationManager.getCurrentNetwork())) {
            groups.add((CyGroup) event.getSource());
        }
    }
}
