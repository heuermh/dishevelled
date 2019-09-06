/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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

import org.dishevelled.venn.BinaryVennModel;

import static org.dishevelled.venn.model.VennModelUtils.toImmutableBitSet;

/**
 * Immutable implementation of BinaryVennModel.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public final class BinaryVennModelImpl<E>
    implements BinaryVennModel<E>
{
    /** First view. */
    private final ObservableSet<E> first;

    /** Second view. */
    private final ObservableSet<E> second;

    /** First only view. */
    private final Set<E> firstOnly;

    /** Second only view. */
    private final Set<E> secondOnly;

    /** Intersection view. */
    private final Set<E> intersection;

    /** Union view. */
    private final Set<E> union;

    /** Selection view. */
    private final ObservableSet<E> selection;

    /** Map of exclusive set views keyed by bit set. */
    private final Map<ImmutableBitSet, Set<E>> exclusives;


    /**
     * Create a new empty binary venn model.
     */
    public BinaryVennModelImpl()
    {
        this(new HashSet<E>(), new HashSet<E>());
    }

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

        // todo  defensive copy?
        this.first = new ObservableSetImpl(first);
        this.second = new ObservableSetImpl(second);
        firstOnly = Sets.difference(this.first, this.second);
        secondOnly = Sets.difference(this.second, this.first);
        intersection = Sets.intersection(this.first, this.second);
        union = Sets.union(this.first, this.second);
        selection = new SelectionView<E>(union, this.first, this.second);

        exclusives = new HashMap<ImmutableBitSet, Set<E>>(3);

        exclusives.put(toImmutableBitSet(0), firstOnly);
        exclusives.put(toImmutableBitSet(1), secondOnly);

        exclusives.put(toImmutableBitSet(0, 1), intersection);
        // copy to immutable map?
    }


    /** {@inheritDoc} */
    public int size()
    {
        return 2;
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
    public Set<E> get(final int index)
    {
        if (index < 0 || index > 1)
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        switch (index)
        {
        case 0:
            return first;
        case 1:
            return second;
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
