/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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
package org.dishevelled.evolve.select;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.Selection;
import org.dishevelled.evolve.Individual;

/**
 * Rank-based selection function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RankBasedSelection<I>
    implements Selection<I>
{
    /** Rank. */
    private int rank;

    /** Default hash map load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;


    /**
     * Create a new rank based selection function with
     * the specified rank.
     *
     * @param rank rank
     */
    public RankBasedSelection(final int rank)
    {
        this.rank = rank;
    }


    /**
     * Set the rank for this rank based selection function to <code>rank</code>.
     *
     * @param rank the rank for this rank based selection function
     */
    public void setRank(final int rank)
    {
        this.rank = rank;
    }

    /**
     * Return the rank for this rank based selection function.
     *
     * @return the rank for this rank based selection function
     */
    public int getRank()
    {
        return rank;
    }

    /** {@inheritDoc} */
    public WeightedMap<I> select(final WeightedMap<I> parents,
                                 final WeightedMap<I> children)
    {
        if (parents == null)
        {
            throw new IllegalArgumentException("parents must not be null");
        }
        if (children == null)
        {
            throw new IllegalArgumentException("children must not be null");
        }

        int size = children.size();

        // create intermediate map of those ranked at or above rank
        WeightedMap<I> intermediate = new HashWeightedMap<I>();
        for (I i : children.keySet())
        {
            if (children.rank(i) <= rank)
            {
                // temporarily set fitness score to 0.0d
                intermediate.put(i, Double.valueOf(0.0d));
            }
        }

        // update fitness based on size of intermediate map
        int intermediateSize = intermediate.size();
        for (I i : intermediate.keySet())
        {
            intermediate.put(i, Double.valueOf(rank / intermediateSize));
        }

        // fitness proportional selection on intermediate map
        HashWeightedMap<I> result = new HashWeightedMap<I>(size, DEFAULT_LOAD_FACTOR);
        for (int i = 0; i < size; i++)
        {
            I individual = intermediate.sample();
            // unsafe cast!
            result.put((I) ((Individual) individual).shallowCopy(), children.get(individual));
        }
        intermediate = null;
        return result;
    }
}
