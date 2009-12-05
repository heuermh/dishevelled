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
    /** First list. */
    private final EventList<E> list0;

    /** Second list. */
    private final EventList<E> list1;

    /** Intersection. */
    private final EventList<E> intersection;

    /** Union. */
    private final EventList<E> union;


    /**
     * Create a new binary venn model with the specified sets.
     *
     * @param set0 first set
     * @param set1 second set
     */
    public BinaryVennModelImpl(final Set<? extends E> set0, final Set<? extends E> set1)
    {
        if (set0 == null)
        {
            throw new IllegalArgumentException("set0 must not be null");
        }
        if (set1 == null)
        {
            throw new IllegalArgumentException("set1 must not be null");
        }

        CompositeList<E> composite = new CompositeList<E>();
        list0 = composite.createMemberList();
        composite.addMemberList(list0);
        list1 = composite.createMemberList();
        composite.addMemberList(list1);

        list0.addAll(set0);
        list1.addAll(set1);

        FilterList<E> filter = new FilterList<E>(composite, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return list0.contains(item) && list1.contains(item);
                }
            });
        UniqueList<E> unique = new UniqueList<E>(filter);

        union = GlazedLists.readOnlyList(composite);
        intersection = GlazedLists.readOnlyList(unique);
    }


    /** {@inheritDoc} */
    public EventList<E> list0()
    {
        return list0;
    }

    /** {@inheritDoc} */
    public EventList<E> list1()
    {
        return list1;
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