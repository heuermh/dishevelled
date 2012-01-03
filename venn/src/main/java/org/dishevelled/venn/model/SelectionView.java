/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
package org.dishevelled.venn.model;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Joiner;

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

import org.dishevelled.observable.impl.ObservableSetImpl;

/**
 * Selection view.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class SelectionView<E>
    extends ObservableSetImpl<E>
{
    /** Default size. */
    private static final int DEFAULT_SIZE = 16;

    /** Union view. */
    private final Set<E> union;

    /** Purge selection. */
    private final SetChangeListener<E> purgeSelection = new SetChangeListener<E>()
        {
            /** {@inheritDoc} */
            public void setChanged(final SetChangeEvent<E> event)
            {
                purgeSelection();
            }
        };


    /**
     * Create a new selection view with the specified views.
     *
     * @param union union view
     * @param views variable number of views
     */
    SelectionView(final Set<E> union, final ObservableSet<E>... views)
    {
        super(new HashSet<E>(Math.max(DEFAULT_SIZE, union == null ? DEFAULT_SIZE : union.size())));
        this.union = union;
        for (ObservableSet<E> view : views)
        {
            view.addSetChangeListener(purgeSelection);
        }
    }


    /**
     * Remove elements from the selection view not present in the union view.
     */
    private void purgeSelection()
    {
        Set<E> toRemove = new HashSet<E>(Math.max(DEFAULT_SIZE, (int) (size() / 10)));
        for (E e : this)
        {
            if (!union.contains(e))
            {
                toRemove.add(e);
            }
        }
        removeAll(toRemove);
    }

    /** {@inheritDoc} */
    protected boolean preAdd(final E e)
    {
        if (!union.contains(e))
        {
            throw new IllegalArgumentException("can not select an element not in union");
        }
        return super.preAdd(e);
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Joiner.on(", ").appendTo(sb, this);
        sb.append("]");
        return sb.toString();
    }
}