/*

    dsh-curate  Interfaces for curating collections quickly.
    Copyright (c) 2007 held jointly by the individual authors.

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
package org.dishevelled.curate.impl;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import ca.odell.glazedlists.swing.EventListModel;

import org.dishevelled.curate.CullView;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Cull panel.
 *
 * @param <E> input and result collections element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CullPanel<E>
    extends JPanel
    implements CullView<E>
{
    /** Input collection. */
    private Collection<E> input;

    /** List of remaining elements. */
    private EventList<E> remaining;

    /** Swing list of remaining elements. */
    private JList remainingList;

    /** Label for count of remaining elements. */
    private JLabel remainingLabel;

    /** List event listener for updating remaining label. */
    private ListEventListener<E> remainingLabelListener;

    /** List of removed elements. */
    private EventList<E> removed;

    /** Swing list of removed elements. */
    private JList removedList;

    /** Label for number of removed elements. */
    private JLabel removedLabel;

    /** List event listener for updating removed label. */
    private ListEventListener<E> removedLabelListener;


    /**
     * Create a new cull panel.
     */
    public CullPanel()
    {
        super();
        input = Collections.emptyList();
        remaining = GlazedLists.eventList(input);
        removed = GlazedLists.eventList(input);
    }

    /**
     * Initialize components.
     */
    private void initComponents()
    {
        remainingLabel = new JLabel(" - ");
        remainingLabelListener = new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append(remaining.size());
                    sb.append(" of ");
                    sb.append(input.size());
                    remainingLabel.setText(sb.toString());
                }
            };
        remainingList = new JList(new EventListModel(remaining));

        removedLabel = new JLabel(" - ");
        removedLabelListener = new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append(removed.size());
                    sb.append(" of ");
                    sb.append(input.size());
                    removedLabel.setText(sb.toString());
                }
            };
        removedList = new JList(new EventListModel(removed));
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        LabelFieldPanel west = new LabelFieldPanel();
        west.addField("Remaining", remainingLabel);
        west.addFinalField(new JScrollPane(remainingList));

        LabelFieldPanel east = new LabelFieldPanel();
        east.addField("Removed", removedLabel);
        east.addFinalField(new JScrollPane(removedList));

        setLayout(new BorderLayout());
        add("East", east);
        //add("Center", new user supplied "selection details..." panel
        add("West", west);
    }

    /** {@inheritDoc} */
    public void setInput(final Collection<E> input)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("input must not be null");
        }
        this.input = input;

        remaining.removeListEventListener(remainingLabelListener);
        removed.removeListEventListener(removedLabelListener);

        remaining = GlazedLists.eventList(input);
        removed = GlazedLists.eventList(Collections.<E>emptyList());

        remainingList.setModel(new EventListModel(remaining));
        removedList.setModel(new EventListModel(removed));
        remaining.addListEventListener(remainingLabelListener);
        removed.addListEventListener(removedLabelListener);

        // mock change event to refresh labels
        remainingLabelListener.listChanged(null);
        removedLabelListener.listChanged(null);
    }

    /** {@inheritDoc} */
    public Collection<E> getRemaining()
    {
        return Collections.unmodifiableList(remaining);
    }

    /** {@inheritDoc} */
    public Collection<E> getRemoved()
    {
        return Collections.unmodifiableList(removed);
    }
}