/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2015 held jointly by the individual authors.

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

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Sets;

import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.impl.ObservableSetImpl;

import org.dishevelled.venn.TernaryVennModel;

import static org.dishevelled.venn.model.VennModelUtils.toImmutableBitSet;

/**
 * Immutable implementation of TernaryVennModel.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public final class TernaryVennModelImpl<E>
    implements TernaryVennModel<E>
{
    /** First view. */
    private final ObservableSet<E> first;

    /** Second view. */
    private final ObservableSet<E> second;

    /** Third view. */
    private final ObservableSet<E> third;

    /** First only view. */
    private final Set<E> firstOnly;

    /** Second only view. */
    private final Set<E> secondOnly;

    /** Third only view. */
    private final Set<E> thirdOnly;

    /** First second view. */
    private final Set<E> firstSecond;

    /** First third view. */
    private final Set<E> firstThird;

    /** Second third view. */
    private final Set<E> secondThird;

    /** Intersection view. */
    private final Set<E> intersection;

    /** Union view. */
    private final Set<E> union;

    /** Selection view. */
    private final ObservableSet<E> selection;

    /** Map of exclusive set views keyed by bit set. */
    private final Map<ImmutableBitSet, Set<E>> exclusives;


    /**
     * Create a new empty ternary venn model.
     */
    public TernaryVennModelImpl()
    {
        this(new HashSet<E>(), new HashSet<E>(), new HashSet<E>());
    }

    /**
     * Create a new ternary venn model with the specified sets.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     */
    public TernaryVennModelImpl(final Set<? extends E> first,
                                final Set<? extends E> second,
                                final Set<? extends E> third)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first must not be null");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second must not be null");
        }
        if (third == null)
        {
            throw new IllegalArgumentException("third must not be null");
        }

        // todo  defensive copy?
        this.first = new ObservableSetImpl(first);
        this.second = new ObservableSetImpl(second);
        this.third = new ObservableSetImpl(third);

        // alias
        ObservableSet<E> f = this.first;
        ObservableSet<E> s = this.second;
        ObservableSet<E> t = this.third;
        firstOnly = Sets.difference(Sets.difference(f, s), t); // f - s - t
        secondOnly = Sets.difference(Sets.difference(s, f), t); // s - f - t
        thirdOnly = Sets.difference(Sets.difference(t, f), s); // t - f - s
        firstSecond = Sets.difference(Sets.intersection(f, s), t); // f n s - t
        firstThird = Sets.difference(Sets.intersection(f, t), s); // f n t - s
        secondThird = Sets.difference(Sets.intersection(s, t), f); // s n t - f
        intersection = Sets.intersection(f, Sets.intersection(s, t)); // f n s n t
        union = Sets.union(f, Sets.union(s, t)); // f u s u t
        selection = new SelectionView<E>(union, f, s, t);

        exclusives = new HashMap<ImmutableBitSet, Set<E>>(7);

        exclusives.put(toImmutableBitSet(0), firstOnly);
        exclusives.put(toImmutableBitSet(1), secondOnly);
        exclusives.put(toImmutableBitSet(2), thirdOnly);

        exclusives.put(toImmutableBitSet(0, 1), firstSecond);
        exclusives.put(toImmutableBitSet(0, 2), firstThird);
        exclusives.put(toImmutableBitSet(1, 2), secondThird);

        exclusives.put(toImmutableBitSet(0, 1, 2), intersection);
    }


    /** {@inheritDoc} */
    public int size()
    {
        return 3;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> first()
    {
        return first;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> second()
    {
        return second;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> third()
    {
        return third;
    }

    /** {@inheritDoc} */
    public Set<E> get(final int index)
    {
        if (index < 0 || index > 2)
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        switch (index)
        {
        case 0:
            return first;
        case 1:
            return second;
        case 2:
            return third;
        default:
            break;
        }
        throw new IllegalStateException("invalid index " + index);
    }

    /** {@inheritDoc} */
    public Set<E> firstOnly()
    {
        return firstOnly;
    }

    /** {@inheritDoc} */
    public Set<E> secondOnly()
    {
        return secondOnly;
    }

    /** {@inheritDoc} */
    public Set<E> thirdOnly()
    {
        return thirdOnly;
    }

    /** {@inheritDoc} */
    public Set<E> firstSecond()
    {
        return firstSecond;
    }

    /** {@inheritDoc} */
    public Set<E> firstThird()
    {
        return firstThird;
    }

    /** {@inheritDoc} */
    public Set<E> secondThird()
    {
        return secondThird;
    }

    /** {@inheritDoc} */
    public Set<E> intersection()
    {
        return intersection;
    }

    /** {@inheritDoc} */
    public Set<E> exclusiveTo(final int index, final int... additional)
    {
        int maxIndex = size() - 1;
        if (index < 0 || index > maxIndex)
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        if (additional != null && additional.length > 0)
        {
            if (additional.length > maxIndex)
            {
                throw new IndexOutOfBoundsException("too many indices provided");
            }
            for (int i = 0, size = additional.length; i < size; i++)
            {
                if (additional[i] < 0 || additional[i] > maxIndex)
                {
                    throw new IndexOutOfBoundsException("additional index [" + i + "] out of bounds");
                }
            }
        }
        return exclusives.get(toImmutableBitSet(index, additional));
    }

    /** {@inheritDoc} */
    public Set<E> union()
    {
        return union;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> selection()
    {
        return selection;
    }
}