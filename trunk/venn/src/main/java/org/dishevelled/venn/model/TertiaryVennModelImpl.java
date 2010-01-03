/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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

import ca.odell.glazedlists.event.ListEventPublisher;

import ca.odell.glazedlists.matchers.Matcher;

import ca.odell.glazedlists.util.concurrent.ReadWriteLock;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.impl.SparseMatrix2D;

import org.dishevelled.venn.TertiaryVennModel;

/**
 * Tertiary venn model implementation.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TertiaryVennModelImpl<E> implements TertiaryVennModel<E>
{
    /** First list view. */
    private final EventList<E> first;

    /** Second list view. */
    private final EventList<E> second;

    /** Third list view. */
    private final EventList<E> third;

    /** Array of list views. */
    private final EventList[] lists;

    /** Intersection. */
    private final EventList<E> intersection;

    /** 2D matrix of intersections indexed by list view. */
    private final Matrix2D<EventList<E>> intersections;

    /** Union. */
    private final EventList<E> union;

    /** 2D matrix of unions indexed by list view. */
    private final Matrix2D<EventList<E>> unions;


    /**
     * Create a new tertiary venn model with the specified sets.
     *
     * @param set0 first set, must not be null
     * @param set1 second set, must not be null
     * @param set2 third set, must not be null
     */
    public TertiaryVennModelImpl(final Set<? extends E> set0, final Set<? extends E> set1, final Set<? extends E> set2)
    {
        if (set0 == null)
        {
            throw new IllegalArgumentException("set0 must not be null");
        }
        if (set1 == null)
        {
            throw new IllegalArgumentException("set1 must not be null");
        }
        if (set2 == null)
        {
            throw new IllegalArgumentException("set2 must not be null");
        }

        CompositeList<E> composite = new CompositeList<E>();
        first = composite.createMemberList();
        composite.addMemberList(first);
        second = composite.createMemberList();
        composite.addMemberList(second);
        third = composite.createMemberList();
        composite.addMemberList(third);

        // todo:  refactor into a method
        ListEventPublisher publisher = composite.getPublisher();
        ReadWriteLock readWriteLock = composite.getReadWriteLock();

        CompositeList<E> composite01 = new CompositeList<E>(publisher, readWriteLock);
        composite01.addMemberList(first);
        composite01.addMemberList(second);

        CompositeList<E> composite02 = new CompositeList<E>(publisher, readWriteLock);
        composite02.addMemberList(first);
        composite02.addMemberList(third);

        CompositeList<E> composite12 = new CompositeList<E>(publisher, readWriteLock);
        composite12.addMemberList(second);
        composite12.addMemberList(third);

        first.addAll(set0);
        second.addAll(set1);
        third.addAll(set2);

        lists = new EventList[] { first, second, third };

        FilterList<E> filter = new FilterList<E>(composite, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item) && second.contains(item) && third.contains(item);
                }
            });
        UniqueList<E> unique = new UniqueList<E>(filter);

        union = GlazedLists.readOnlyList(composite);
        intersection = GlazedLists.readOnlyList(unique);

        // todo:  refactor into a method
        FilterList<E> filter01 = new FilterList<E>(composite01, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item) && second.contains(item);
                }
            });
        UniqueList<E> unique01 = new UniqueList<E>(filter01);

        EventList<E> union01 = GlazedLists.readOnlyList(composite01);
        EventList<E> intersection01 = GlazedLists.readOnlyList(unique01);

        FilterList<E> filter02 = new FilterList<E>(composite02, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item) && third.contains(item);
                }
            });
        UniqueList<E> unique02 = new UniqueList<E>(filter02);

        EventList<E> union02 = GlazedLists.readOnlyList(composite02);
        EventList<E> intersection02 = GlazedLists.readOnlyList(unique02);

        FilterList<E> filter12 = new FilterList<E>(composite12, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return second.contains(item) && third.contains(item);
                }
            });
        UniqueList<E> unique12 = new UniqueList<E>(filter12);

        EventList<E> union12 = GlazedLists.readOnlyList(composite12);
        EventList<E> intersection12 = GlazedLists.readOnlyList(unique12);

        unions = new SparseMatrix2D<EventList<E>>(3L, 3L);
        unions.set(0L, 1L, union01);
        unions.set(1L, 0L, union01);
        unions.set(0L, 2L, union02);
        unions.set(2L, 0L, union02);
        unions.set(1L, 2L, union12);
        unions.set(2L, 1L, union12);
        // todo, make immutable?

        intersections = new SparseMatrix2D<EventList<E>>(3L, 3L);
        intersections.set(0L, 1L, intersection01);
        intersections.set(1L, 0L, intersection01);
        intersections.set(0L, 2L, intersection02);
        intersections.set(2L, 0L, intersection02);
        intersections.set(1L, 2L, intersection12);
        intersections.set(2L, 1L, intersection12);
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
    public EventList<E> third()
    {
        return third;
    }

    /** {@inheritDoc} */
    public EventList<E> intersection()
    {
        return intersection;
    }

    /** {@inheritDoc} */
    public EventList<E> intersect(final EventList<E> a, final EventList<E> b)
    {
        int i = indexOf(a);
        if (i < 0)
        {
            throw new IllegalArgumentException("a must be one of first(), second(), or third()");
        }
        int j = indexOf(b);
        if (j < 0)
        {
            throw new IllegalArgumentException("b must be one of first(), second(), or third()");
        }
        return intersections.get(i, j);
    }

    /** {@inheritDoc} */
    public EventList<E> union()
    {
        return union;
    }

    /** {@inheritDoc} */
    public EventList<E> union(final EventList<E> a, final EventList<E> b)
    {
        int i = indexOf(a);
        if (i < 0)
        {
            throw new IllegalArgumentException("a must be one of first(), second(), or third()");
        }
        int j = indexOf(b);
        if (j < 0)
        {
            throw new IllegalArgumentException("b must be one of first(), second(), or third()");
        }
        return unions.get(i, j);
    }

    /**
     * Return the index of the specified list view.
     *
     * @param list list view
     * @return the index of the specified list
     */
    private int indexOf(final EventList<E> list)
    {
        for (int i = 0; i < lists.length; i++)
        {
            if (lists[i] == list)
            {
                return i;
            }
        }
        return -1;
    }
}