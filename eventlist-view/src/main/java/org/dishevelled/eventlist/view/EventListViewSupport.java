/*

    dsh-eventlist-view  Views for event lists.
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
package org.dishevelled.eventlist.view;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;

/**
 * A support class that can be used via delegation to provide event list support.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class EventListViewSupport<E>
{
    /** Model. */
    private final EventList<E> model;

    /** Selection model. */
    private final ListSelection<E> selectionModel;


    /**
     * Create a new event list support class with the specified model.
     *
     * @param model model, must not be null
     */
    public EventListViewSupport(final EventList<E> model)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;
        selectionModel = new ListSelection<E>(this.model);
    }


    /**
     * Return the model for this event list view support class.  The model will not be null.
     *
     * @return the model for this event list view support class
     */
    public final EventList<E> getModel()
    {
        return model;
    }

    /**
     * Return the selection model for this event list view support class.  The selection model will not be null.
     *
     * @return the selection model for this event list view support class
     */
    public final ListSelection<E> getSelectionModel()
    {
        return selectionModel;
    }
}