/*

    dsh-venn-cytoscape3-app  Cytoscape3 app for venn diagrams.
    Copyright (c) 2012 held jointly by the individual authors.

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

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import static javax.swing.SwingUtilities.windowForComponent;

import javax.swing.border.EmptyBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

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

import org.dishevelled.venn.swing.BinaryVennList;
import org.dishevelled.venn.swing.TernaryVennList;
import org.dishevelled.venn.swing.QuaternaryVennList;

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
                if (selected.size() > 2)
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

    /** Group manager. */
    private final CyGroupManager groupManager;


    /**
     * Create a new groups view.
     *
     * @param applicationManager application manager, must not be null
     * @param groupManager group manager, must not be null
     */
    GroupsView(final CyApplicationManager applicationManager, final CyGroupManager groupManager)
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
        this.applicationManager = applicationManager;
        this.groupManager = groupManager;

        Set<CyGroup> groupSet = groupManager.getGroupSet(applicationManager.getCurrentNetwork());
        groups = GlazedLists.eventList(new ArrayList<CyGroup>(groupSet));
        EventListModel<CyGroup> listModel = new EventListModel<CyGroup>(groups);
        EventSelectionModel<CyGroup> selectionModel = new EventSelectionModel<CyGroup>(groups);
        selected = selectionModel.getSelected();
        selectionModel.addListSelectionListener(listSelectionListener); // or use event list listener
        groupList = new JList(listModel);
        groupList.setSelectionModel(selectionModel);
        contextMenu = new JPopupMenu();
        contextMenu.add(new IdMenuItem(eulerDiagram));
        contextMenu.add(new IdMenuItem(vennDiagram));
        contextMenu.add(new IdMenuItem(details));
        groupList.addMouseListener(new ContextMenuListener(contextMenu));

        eulerDiagram.setEnabled(false);
        vennDiagram.setEnabled(false);
        details.setEnabled(false);

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
    }

    /**
     * Show a euler diagram.
     */
    private void eulerDiagram()
    {
    }

    /**
     * Show a binary venn diagram.
     */
    private void binaryDiagram()
    {
    }

    /**
     * Show a ternary venn diagram.
     */
    private void ternaryDiagram()
    {
    }

    /**
     * Show a quaternary venn diagram.
     */
    private void quaternaryDiagram()
    {
    }

    /**
     * Show binary venn details.
     */
    private void binaryDetails()
    {
    }

    /**
     * Show ternary venn details.
     */
    private void ternaryDetails()
    {
    }

    /**
     * Show quaternary venn details.
     */
    private void quaternaryDetails()
    {
    }

    @Override
    public void handleEvent(final GroupAboutToBeDestroyedEvent event)
    {
        groups.remove(event.getGroup());
    }

    @Override
    public void handleEvent(final GroupAboutToBeRemovedEvent event)
    {
        groups.remove(event.getGroup());
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
