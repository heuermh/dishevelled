/*

    dsh-piccolo-eventlist-view  Piccolo2D views for event lists.
    Copyright (c) 2010-2011 held jointly by the individual authors.

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
package org.dishevelled.piccolo.eventlist.view;

import java.util.List;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import org.piccolo2d.nodes.PText;

/**
 * Count label node.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CountLabelNode<E>
    extends AbstractEventListNode<E>
{
    /** Count label. */
    private final PText count;

    /** Listener. */
    private final ListEventListener<E> listener = new ListEventListener<E>()
        {
            /** @{inheritDoc} */
            public void listChanged(final ListEvent<E> event)
            {
                updateLabelText();
            }
        };


    /**
     * Create a new count label node with the specified model.
     *
     * @param model model, must not be null
     */
    public CountLabelNode(final EventList<E> model)
    {
        super(model);
        count = new PText();
        updateLabelText();
        addListeners();
        addChild(count);
    }


    /**
     * Return the label.
     *
     * @return the label
     */
    public PText getLabel()
    {
        return count;
    }

    /** {@inheritDoc} */
    protected void cut(final List<E> toCut)
    {
        // empty
    }

    /** {@inheritDoc} */
    protected void copy(final List<E> toCopy)
    {
        // empty
    }

    /** {@inheritDoc} */
    public void add()
    {
        // empty
    }

    /** {@inheritDoc} */
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
     * Release the resources consumed by this count label node
     * so that it may eventually be garbage collected.
     */
    public void dispose()
    {
        super.dispose();
        removeListeners();
    }
}