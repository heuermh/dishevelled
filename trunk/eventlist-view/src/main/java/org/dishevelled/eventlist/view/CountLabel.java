/*

    dsh-eventlist-view  Views for event lists.
    Copyright (c) 2010-2013 held jointly by the individual authors.

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
package org.dishevelled.eventlist.view;

import java.awt.BorderLayout;

import java.util.List;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import javax.swing.JLabel;

/**
 * Count label.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public final class CountLabel<E>
    extends AbstractEventListView<E>
{
    /** Count label. */
    private final JLabel count;

    /** Listener. */
    private final ListEventListener<E> listener = new ListEventListener<E>()
        {
            @Override
            public void listChanged(final ListEvent<E> event)
            {
                updateLabelText();
            }
        };


    /**
     * Create a new count label with the specified model.
     *
     * @param model model, must not be null
     */
    public CountLabel(final EventList<E> model)
    {
        super(model);
        count = new JLabel();
        updateLabelText();
        addListeners();
        setLayout(new BorderLayout());
        add("West", count);
    }


    /**
     * Return the label for this count label.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the event
     * list backing this count label.
     *
     * @return the label for this count label
     */
    public JLabel getCountLabel()
    {
        return count;
    }

    @Override
    protected void cut(final List<E> toCut)
    {
        // empty
    }

    @Override
    protected void copy(final List<E> toCopy)
    {
        // empty
    }

    @Override
    public void add()
    {
        // empty
    }

    @Override
    public void paste()
    {
        // empty
    }

    /**
     * Update label text.
     */
    private void updateLabelText()
    {
        count.setText(String.valueOf(getModel().size()));
    }

    /**
     * Add listeners.
     */
    private void addListeners()
    {
        getModel().addListEventListener(listener);
    }

    /**
     * Remove listeners.
     */
    private void removeListeners()
    {
        getModel().removeListEventListener(listener);
    }

    /**
     * Release the resources consumed by this count label so that it may eventually be garbage collected.
     */
    public void dispose()
    {
        super.dispose();
        removeListeners();
    }
}