/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009 held jointly by the individual authors.

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

import java.util.Set;

import ca.odell.glazedlists.CompositeList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.UniqueList;

import ca.odell.glazedlists.matchers.Matcher;

import org.dishevelled.venn.BinaryVennModel;

/**
 * Binary venn model implementation.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennModelImpl<E>
    implements BinaryVennModel<E>
{
    /** First list view. */
    private final EventList<E> first;

    /** Second list view. */
    private final EventList<E> second;

    /** Intersection. */
    private final EventList<E> intersection;

    /** Union. */
    private final EventList<E> union;


    /**
     * Create a new binary venn model with the specified sets.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     */
    public BinaryVennModelImpl(final Set<? extends E> first, final Set<? extends E> second)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first must not be null");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second must not be null");
        }

        CompositeList<E> composite = new CompositeList<E>();
        this.first = composite.createMemberList();
        composite.addMemberList(this.first);
        this.second = composite.createMemberList();
        composite.addMemberList(this.second);

        this.first.addAll(first);
        this.second.addAll(second);

        FilterList<E> filter = new FilterList<E>(composite, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E element)
                {
                    return intersectionContains(element);
                }
            });
        UniqueList<E> unique = new UniqueList<E>(filter);

        union = GlazedLists.readOnlyList(composite);
        intersection = GlazedLists.readOnlyList(unique);
    }


    /**
     * Return true if the specified element should be in the intersection.
     *
     * @param element element
     * @return true if the specified element should be in the intersection
     */
    private boolean intersectionContains(final E element)
    {
        return first.contains(element) && second.contains(element);
    }

    /** {@inheritDoc} */
    public EventList<E> first()
    {
        return first;
    }

    /** {@inheritDoc} */
    public EventList<E> second()
    {
        return second;
    }

    /** {@inheritDoc} */
    public EventList<E> intersection()
    {
        return intersection;
    }

    /** {@inheritDoc} */
    public EventList<E> union()
    {
        return union;
    }
}