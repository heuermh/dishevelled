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
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

import cytoscape.groups.CyGroup;
import cytoscape.groups.CyGroupManager;

import cytoscape.view.CytoscapeDesktop;

import org.dishevelled.layout.ButtonPanel;
import org.dishevelled.layout.LabelFieldPanel;

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
    private EventList<CyGroup> groups;

    /** List of groups. */
    private JList groupList;

    /** Cancel action. */
    private final Action cancel = new AbstractAction("Cancel") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                cancel();
            }
        };

    /** OK action. */
    private final Action ok = new AbstractAction("OK") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                ok();
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

        initComponents();
        layoutComponents();
    }


    /**
     * Initialize components.
     */
    private void initComponents()
    {
        groups = GlazedLists.eventList(CyGroupManager.getGroupList());
        EventListModel<CyGroup> listModel = new EventListModel<CyGroup>(groups);
        EventSelectionModel<CyGroup> selectionModel = new EventSelectionModel<CyGroup>(groups);
        groupList = new JList(listModel);
        groupList.setSelectionModel(selectionModel);
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        LabelFieldPanel mainPanel = new LabelFieldPanel();
        mainPanel.addLabel("Groups:"); // i18n
        mainPanel.addFinalField(new JScrollPane(groupList));

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(24, 0, 0, 0));
        buttonPanel.add(cancel);
        buttonPanel.add(ok);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.add("Center", mainPanel);
        contentPane.add("South", buttonPanel);
        setContentPane(contentPane);
    }

    /**
     * Cancel.
     */
    private void cancel()
    {
        setVisible(false);
    }

    /**
     * OK.
     */
    private void ok()
    {
        // empty
    }
}