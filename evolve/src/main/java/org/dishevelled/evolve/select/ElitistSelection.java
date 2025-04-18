/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2013 held jointly by the individual authors.

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

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.Selection;

/**
 * Elitist selection function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 */
public final class ElitistSelection<I>
    implements Selection<I>
{
    /** Number of individuals. */
    private final int individuals;

    /** Default hash map load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /** WeightedMap entry comparator. */
    private final WeightedMapEntryComparator weightedMapEntryComparator = new WeightedMapEntryComparator();


    /**
     * Create a new elitist selection function with the specified
     * number of individuals.
     *
     * @param individuals number of individuals for this elitist
     *    selection function, must be <code>&gt;= 0</code>
     */
    public ElitistSelection(final int individuals)
    {
        if (individuals < 0)
        {
            throw new IllegalArgumentException("individuals must be greater than or equal to zero");
        }
        this.individuals = individuals;
    }


    /**
     * Return the number of individuals for this elitist selection function.
     *
     * @return the number of individuals for this elitist selection function
     */
    public int getIndividuals()
    {
        return individuals;
    }

    /** {@inheritDoc} */
    public Collection<I> select(final Collection<I> population,
                                final WeightedMap<I> scores)
    {
        if (scores.totalWeight() == 0.0d)
        {
            throw new IllegalStateException("scores total weight must be greater than zero");
        }
        int size = population.size();
        List<I> selected = new ArrayList<I>(size);

        // sort keys by score
        List<Map.Entry<I, Double>> sortedKeys = new ArrayList<Map.Entry<I, Double>>(scores.entrySet());
        Collections.sort(sortedKeys, weightedMapEntryComparator);

        // take the top n individuals
        List<Map.Entry<I, Double>> eliteKeys = sortedKeys.subList(0, Math.min(individuals, size));

        // create intermediate map of the top n individuals
        WeightedMap<I> intermediate = new HashWeightedMap<I>(individuals, DEFAULT_LOAD_FACTOR);
        for (Map.Entry<I, Double> e : eliteKeys)
        {
            intermediate.put(e.getKey(), Double.valueOf(0.0d));
        }

        // update fitness based on number of top n individuals
        for (I i : intermediate.keySet())
        {
            intermediate.put(i, Double.valueOf(1.0d / individuals));
        }

        // fitness proportional selection on intermediate map
        for (int i = 0; i < size; i++)
        {
            selected.add(intermediate.sample());
        }

        sortedKeys = null;
        eliteKeys = null;
        intermediate = null;
        return selected;
    }


    /**
     * WeightedMap entry comparator.
     */
    private class WeightedMapEntryComparator
        implements Comparator<Map.Entry<I, Double>>
    {

        /** {@inheritDoc} */
        public int compare(final Map.Entry<I, Double> e1, final Map.Entry<I, Double> e2)
        {
            // reverse order of comparison to get decreasing sort
            return e2.getValue().compareTo(e1.getValue());
        }
    }
}
