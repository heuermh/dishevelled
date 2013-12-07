/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.venn.VennModel;

import static org.dishevelled.venn.model.VennModelUtils.toImmutableBitSet;

/**
 * Implementation of a venn diagram model for an arbitrary number of sets.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public final class VennModelImpl<E> implements VennModel<E>
{
    /** List of sets. */
    private final List<Set<E>> sets;

    /** Intersection view. */
    private final Set<E> intersection;

    /** Union view. */
    private final Set<E> union;

    /** Map of exclusive set views keyed by bit set. */
    private final Map<ImmutableBitSet, Set<E>> exclusives;


    /**
     * Create a new venn model for the specified list of sets.
     *
     * @param sets list of sets, must not be null
     */
    public VennModelImpl(final List<Set<E>> sets)
    {
        if (sets == null)
        {
            throw new IllegalArgumentException("sets must not be null");
        }
        this.sets = ImmutableList.copyOf(sets);

        intersection = createIntersection();
        union = createUnion();
        exclusives = createExclusives();
    }


    /** {@inheritDoc} */
    public int size()
    {
        return sets.size();
    }

    /** {@inheritDoc} */
    public Set<E> get(final int index)
    {
        return sets.get(index);
    }

    /** {@inheritDoc} */
    public Set<E> union()
    {
        return union;
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

    /**
     * Create and return a new intersection view.
     *
     * @return a new intersection view
     */
    private Set<E> createIntersection()
    {
        Set<E> rv = this.sets.get(0);
        for (Set<E> set : sets)
        {
            rv = Sets.intersection(rv, set);
        }
        return rv;
    }

    /**
     * Create and return a new union view.
     *
     * @return a new union view
     */
    private Set<E> createUnion()
    {
        Set<E> rv = sets.get(0);
        for (Set<E> set : sets)
        {
            rv = Sets.union(rv, set);
        }
        return rv;
    }

    /**
     * Create and return the map of exclusive set views keyed by bit set.
     *
     * @return the map of exclusive set views keyed by bit set
     */
    private Map<ImmutableBitSet, Set<E>> createExclusives()
    {
        Map<ImmutableBitSet, Set<E>> map = Maps.newHashMapWithExpectedSize((int) Math.pow(2, this.sets.size()) - 1);

        // construct a set containing 0...n integers
        Set<Integer> input = Sets.newHashSet();
        for (int i = 0, size = size(); i < size; i++)
        {
            input.add(Integer.valueOf(i));
        }

        // calculate the power set (note n > 30 will overflow int)
        Set<Set<Integer>> powerSet = Sets.powerSet(ImmutableSet.copyOf(input));
        for (Set<Integer> set : powerSet)
        {
            if (!set.isEmpty())
            {
                // intersect all in set
                Iterator<Integer> indices = set.iterator();
                Set<E> view = sets.get(indices.next());
                while (indices.hasNext())
                {
                    view = Sets.intersection(view, sets.get(indices.next()));
                }

                // subtract all in input not in set
                for (Integer index : input)
                {
                    if (!set.contains(index))
                    {
                        view = Sets.difference(view, sets.get(index));
                    }
                }

                // add to exclusives map
                map.put(toImmutableBitSet(set), view);
            }
        }

        // make an immutable copy?
        return map;
    }
}
