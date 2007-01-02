/*

    dsh-evolve  Simple framework for evolutionary algorithms.
    Copyright (c) 2005-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.evolve.mutate;

import java.util.Set;
import java.util.HashSet;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.Mutation;

/**
 * Proportional mutation function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ProportionalMutation<I>
    implements Mutation<I>
{
    /** Default hash map load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /** Weighted map of individual-wise mutation functions. */
    private WeightedMap<IndividualWiseMutation<I>> mutations;

    /** Default null individual-wise mutation function, in case the map is empty. */
    private final IndividualWiseMutation<I> nullMutation = new NullIndividualWiseMutation<I>();


    /**
     * Create a new proportional mutation function.
     */
    public ProportionalMutation()
    {
        mutations = new HashWeightedMap<IndividualWiseMutation<I>>();
    }


    /**
     * Add the specified individual-wise mutation function to this
     * proportional mutation function at the specified weight.
     *
     * @param mutation individual-wise mutation function, must not be null
     * @param weight weight
     */
    public void add(final IndividualWiseMutation<I> mutation, final double weight)
    {
        if (mutation == null)
        {
            throw new IllegalArgumentException("mutation must not be null");
        }

        mutations.put(mutation, Double.valueOf(weight));
    }

    /** {@inheritDoc} */
    public Set<I> mutate(final Set<I> recombined)
    {
        Set<I> mutated = new HashSet<I>(recombined.size(), DEFAULT_LOAD_FACTOR);

        for (I i : recombined)
        {
            IndividualWiseMutation<I> mutation = mutations.sample();

            if (mutation == null)
            {
                mutation = nullMutation;
            }
            mutated.add(mutation.mutate(i));
        }
        return mutated;
    }
}
