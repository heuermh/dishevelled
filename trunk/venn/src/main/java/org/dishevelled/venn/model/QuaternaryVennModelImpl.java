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
import org.dishevelled.matrix.Matrix3D;

import org.dishevelled.matrix.impl.SparseMatrix2D;
import org.dishevelled.matrix.impl.SparseMatrix3D;

import org.dishevelled.venn.QuaternaryVennModel;

/**
 * Quaternary venn model implementation.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennModelImpl<E> implements QuaternaryVennModel<E>
{
    /** First list view. */
    private final EventList<E> first;

    /** Second list view. */
    private final EventList<E> second;

    /** Third list view. */
    private final EventList<E> third;

    /** Fourth list view. */
    private final EventList<E> fourth;

    /** Array of list views. */
    private final EventList[] lists;

    /** Intersection. */
    private final EventList<E> intersection;

    /** 2D matrix of binary intersections indexed by list view. */
    private final Matrix2D<EventList<E>> binaryIntersections;

    /** 3D matrix of tertiary intersections indexed by list view. */
    private final Matrix3D<EventList<E>> tertiaryIntersections;

    /** Union. */
    private final EventList<E> union;

    /** 2D matrix of binary unions indexed by list view. */
    private final Matrix2D<EventList<E>> binaryUnions;

    /** 3D matrix of tertiary unions indexed by list view. */
    private final Matrix3D<EventList<E>> tertiaryUnions;


    /**
     * Create a new quaternary venn model with the specified sets.
     *
     * @param set0 first set, must not be null
     * @param set1 second set, must not be null
     * @param set2 third set, must not be null
     * @param set3 fourth set, must not be null
     */
    public QuaternaryVennModelImpl(final Set<? extends E> set0,
                                   final Set<? extends E> set1,
                                   final Set<? extends E> set2,
                                   final Set<? extends E> set3)
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
        if (set3 == null)
        {
            throw new IllegalArgumentException("set3 must not be null");
        }

        CompositeList<E> composite = new CompositeList<E>();
        first = composite.createMemberList();
        composite.addMemberList(first);
        second = composite.createMemberList();
        composite.addMemberList(second);
        third = composite.createMemberList();
        composite.addMemberList(third);
        fourth = composite.createMemberList();
        composite.addMemberList(fourth);

        // todo:  refactor into a method
        ListEventPublisher publisher = composite.getPublisher();
        ReadWriteLock readWriteLock = composite.getReadWriteLock();

        CompositeList<E> composite01 = new CompositeList<E>(publisher, readWriteLock);
        composite01.addMemberList(first);
        composite01.addMemberList(second);

        CompositeList<E> composite02 = new CompositeList<E>(publisher, readWriteLock);
        composite02.addMemberList(first);
        composite02.addMemberList(third);

        CompositeList<E> composite03 = new CompositeList<E>(publisher, readWriteLock);
        composite03.addMemberList(first);
        composite03.addMemberList(third);

        CompositeList<E> composite12 = new CompositeList<E>(publisher, readWriteLock);
        composite12.addMemberList(second);
        composite12.addMemberList(third);

        CompositeList<E> composite13 = new CompositeList<E>(publisher, readWriteLock);
        composite13.addMemberList(second);
        composite13.addMemberList(third);

        CompositeList<E> composite23 = new CompositeList<E>(publisher, readWriteLock);
        composite23.addMemberList(second);
        composite23.addMemberList(third);

        CompositeList<E> composite012 = new CompositeList<E>(publisher, readWriteLock);
        composite012.addMemberList(first);
        composite012.addMemberList(second);
        composite012.addMemberList(third);

        CompositeList<E> composite023 = new CompositeList<E>(publisher, readWriteLock);
        composite023.addMemberList(first);
        composite023.addMemberList(third);
        composite023.addMemberList(fourth);

        CompositeList<E> composite013 = new CompositeList<E>(publisher, readWriteLock);
        composite013.addMemberList(first);
        composite013.addMemberList(second);
        composite013.addMemberList(fourth);

        CompositeList<E> composite123 = new CompositeList<E>(publisher, readWriteLock);
        composite123.addMemberList(second);
        composite123.addMemberList(third);
        composite123.addMemberList(fourth);

        first.addAll(set0);
        second.addAll(set1);
        third.addAll(set2);
        fourth.addAll(set3);

        lists = new EventList[] { first, second, third, fourth };

        FilterList<E> filter = new FilterList<E>(composite, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item)
                        && second.contains(item)
                        && third.contains(item)
                        && fourth.contains(item);
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

        FilterList<E> filter03 = new FilterList<E>(composite03, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item) && fourth.contains(item);
                }
            });
        UniqueList<E> unique03 = new UniqueList<E>(filter03);

        EventList<E> union03 = GlazedLists.readOnlyList(composite03);
        EventList<E> intersection03 = GlazedLists.readOnlyList(unique03);

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

        FilterList<E> filter13 = new FilterList<E>(composite13, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return second.contains(item) && fourth.contains(item);
                }
            });
        UniqueList<E> unique13 = new UniqueList<E>(filter13);

        EventList<E> union13 = GlazedLists.readOnlyList(composite13);
        EventList<E> intersection13 = GlazedLists.readOnlyList(unique13);

        FilterList<E> filter23 = new FilterList<E>(composite23, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return third.contains(item) && fourth.contains(item);
                }
            });
        UniqueList<E> unique23 = new UniqueList<E>(filter23);

        EventList<E> union23 = GlazedLists.readOnlyList(composite23);
        EventList<E> intersection23 = GlazedLists.readOnlyList(unique23);

        binaryUnions = new SparseMatrix2D<EventList<E>>(4L, 4L);
        binaryUnions.set(0L, 1L, union01);
        binaryUnions.set(1L, 0L, union01);
        binaryUnions.set(0L, 2L, union02);
        binaryUnions.set(2L, 0L, union02);
        binaryUnions.set(0L, 3L, union03);
        binaryUnions.set(3L, 0L, union03);
        binaryUnions.set(1L, 2L, union12);
        binaryUnions.set(2L, 1L, union12);
        binaryUnions.set(1L, 3L, union13);
        binaryUnions.set(3L, 1L, union13);
        binaryUnions.set(2L, 3L, union23);
        binaryUnions.set(3L, 2L, union23);
        // todo, make immutable?

        binaryIntersections = new SparseMatrix2D<EventList<E>>(4L, 4L);
        binaryIntersections.set(0L, 1L, intersection01);
        binaryIntersections.set(1L, 0L, intersection01);
        binaryIntersections.set(0L, 2L, intersection02);
        binaryIntersections.set(2L, 0L, intersection02);
        binaryIntersections.set(0L, 3L, intersection03);
        binaryIntersections.set(3L, 0L, intersection03);
        binaryIntersections.set(1L, 2L, intersection12);
        binaryIntersections.set(2L, 1L, intersection12);
        binaryIntersections.set(1L, 3L, intersection13);
        binaryIntersections.set(3L, 1L, intersection13);
        binaryIntersections.set(2L, 3L, intersection23);
        binaryIntersections.set(3L, 2L, intersection23);

        FilterList<E> filter012 = new FilterList<E>(composite012, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item) && second.contains(item) && third.contains(item);
                }
            });
        UniqueList<E> unique012 = new UniqueList<E>(filter012);

        EventList<E> union012 = GlazedLists.readOnlyList(composite012);
        EventList<E> intersection012 = GlazedLists.readOnlyList(unique012);

        FilterList<E> filter023 = new FilterList<E>(composite023, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item) && third.contains(item) && fourth.contains(item);
                }
            });
        UniqueList<E> unique023 = new UniqueList<E>(filter023);

        EventList<E> union023 = GlazedLists.readOnlyList(composite023);
        EventList<E> intersection023 = GlazedLists.readOnlyList(unique023);

        FilterList<E> filter013 = new FilterList<E>(composite013, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return first.contains(item) && second.contains(item) && fourth.contains(item);
                }
            });
        UniqueList<E> unique013 = new UniqueList<E>(filter013);

        EventList<E> union013 = GlazedLists.readOnlyList(composite013);
        EventList<E> intersection013 = GlazedLists.readOnlyList(unique013);

        FilterList<E> filter123 = new FilterList<E>(composite123, new Matcher<E>()
            {
                /** {@inheritDoc} */
                public boolean matches(final E item)
                {
                    return second.contains(item) && third.contains(item) && fourth.contains(item);
                }
            });
        UniqueList<E> unique123 = new UniqueList<E>(filter123);

        EventList<E> union123 = GlazedLists.readOnlyList(composite123);
        EventList<E> intersection123 = GlazedLists.readOnlyList(unique123);

        tertiaryUnions = new SparseMatrix3D<EventList<E>>(4L, 4L, 4L);
        tertiaryUnions.set(0L, 1L, 2L, union012);
        tertiaryUnions.set(0L, 2L, 1L, union012);
        tertiaryUnions.set(1L, 0L, 2L, union012);
        tertiaryUnions.set(1L, 2L, 0L, union012);
        tertiaryUnions.set(2L, 0L, 1L, union012);
        tertiaryUnions.set(2L, 1L, 0L, union012);
        tertiaryUnions.set(0L, 2L, 3L, union023);
        tertiaryUnions.set(0L, 3L, 2L, union023);
        tertiaryUnions.set(2L, 0L, 3L, union023);
        tertiaryUnions.set(2L, 3L, 0L, union023);
        tertiaryUnions.set(3L, 0L, 2L, union023);
        tertiaryUnions.set(3L, 2L, 0L, union023);
        tertiaryUnions.set(0L, 1L, 3L, union013);
        tertiaryUnions.set(0L, 3L, 1L, union013);
        tertiaryUnions.set(1L, 0L, 3L, union013);
        tertiaryUnions.set(1L, 3L, 0L, union013);
        tertiaryUnions.set(3L, 0L, 1L, union013);
        tertiaryUnions.set(3L, 1L, 0L, union013);
        tertiaryUnions.set(1L, 2L, 3L, union123);
        tertiaryUnions.set(1L, 3L, 2L, union123);
        tertiaryUnions.set(2L, 1L, 3L, union123);
        tertiaryUnions.set(2L, 3L, 1L, union123);
        tertiaryUnions.set(3L, 1L, 2L, union123);
        tertiaryUnions.set(3L, 2L, 1L, union123);

        tertiaryIntersections = new SparseMatrix3D<EventList<E>>(4L, 4L, 4L);
        tertiaryIntersections.set(0L, 1L, 2L, intersection012);
        tertiaryIntersections.set(0L, 2L, 1L, intersection012);
        tertiaryIntersections.set(1L, 0L, 2L, intersection012);
        tertiaryIntersections.set(1L, 2L, 0L, intersection012);
        tertiaryIntersections.set(2L, 0L, 1L, intersection012);
        tertiaryIntersections.set(2L, 1L, 0L, intersection012);
        tertiaryIntersections.set(0L, 2L, 3L, intersection023);
        tertiaryIntersections.set(0L, 3L, 2L, intersection023);
        tertiaryIntersections.set(2L, 0L, 3L, intersection023);
        tertiaryIntersections.set(2L, 3L, 0L, intersection023);
        tertiaryIntersections.set(3L, 0L, 2L, intersection023);
        tertiaryIntersections.set(3L, 2L, 0L, intersection023);
        tertiaryIntersections.set(0L, 1L, 3L, intersection013);
        tertiaryIntersections.set(0L, 3L, 1L, intersection013);
        tertiaryIntersections.set(1L, 0L, 3L, intersection013);
        tertiaryIntersections.set(1L, 3L, 0L, intersection013);
        tertiaryIntersections.set(3L, 0L, 1L, intersection013);
        tertiaryIntersections.set(3L, 1L, 0L, intersection013);
        tertiaryIntersections.set(1L, 2L, 3L, intersection123);
        tertiaryIntersections.set(1L, 3L, 2L, intersection123);
        tertiaryIntersections.set(2L, 1L, 3L, intersection123);
        tertiaryIntersections.set(2L, 3L, 1L, intersection123);
        tertiaryIntersections.set(3L, 1L, 2L, intersection123);
        tertiaryIntersections.set(3L, 2L, 1L, intersection123);
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
    public EventList<E> fourth()
    {
        return fourth;
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
        return binaryIntersections.get(i, j);
    }

    /** {@inheritDoc} */
    public EventList<E> intersect(final EventList<E> a, final EventList<E> b, final EventList<E> c)
    {
        int i = indexOf(a);
        if (i < 0)
        {
            throw new IllegalArgumentException("a must be one of first(), second(), third(), or fourth()");
        }
        int j = indexOf(b);
        if (j < 0)
        {
            throw new IllegalArgumentException("b must be one of first(), second(), third(), or fourth()");
        }
        int k = indexOf(c);
        if (k < 0)
        {
            throw new IllegalArgumentException("c must be one of first(), second(), third(), or fourth()");
        }
        return tertiaryIntersections.get(i, j, k);
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
            throw new IllegalArgumentException("a must be one of first(), second(), third(), or fourth()");
        }
        int j = indexOf(b);
        if (j < 0)
        {
            throw new IllegalArgumentException("b must be one of first(), second(), third(), or fourth()");
        }
        return binaryUnions.get(i, j);
    }

    /** {@inheritDoc} */
    public EventList<E> union(final EventList<E> a, final EventList<E> b, final EventList<E> c)
    {
        int i = indexOf(a);
        if (i < 0)
        {
            throw new IllegalArgumentException("a must be one of first(), second(), third(), or fourth()");
        }
        int j = indexOf(b);
        if (j < 0)
        {
            throw new IllegalArgumentException("b must be one of first(), second(), third(), or fourth()");
        }
        int k = indexOf(c);
        if (k < 0)
        {
            throw new IllegalArgumentException("b must be one of first(), second(), third(), or fourth()");
        }
        return tertiaryUnions.get(i, j, k);
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