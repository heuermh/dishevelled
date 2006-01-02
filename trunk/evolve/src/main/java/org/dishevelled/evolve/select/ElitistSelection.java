/*

    dsh-evolve  Simple framework for evolutionary algorithms.
    Copyright (c) 2005-2006 held jointly by the individual authors.

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
package org.dishevelled.evolve.select;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.Selection;
import org.dishevelled.evolve.Individual;

/**
 * Elitist selection function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ElitistSelection<I>
    implements Selection<I>
{
    /** Number of individuals. */
    private int individuals;

    /** Default hash map load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /** WeightedMap entry comparator. */
    private WeightedMapEntryComparator WEIGHTED_MAP_ENTRY_COMPARATOR = new WeightedMapEntryComparator();


    /**
     * Create a new elitist selection function with the specified
     * number of individuals.
     *
     * @param individuals number of individuals for this elitist
     *    selection function
     */
    public ElitistSelection(final int individuals)
    {
        this.individuals = individuals;
    }


    /**
     * Set the number of individuals for this elitist selection
     * function to <code>individuals</code>.
     *
     * @param individuals number of individuals for this elitist
     *    selection function
     */
    public void setIndividuals(final int individuals)
    {
        this.individuals = individuals;
    }

    /**
     * Return the number of individuals for this elitist selection
     * function.
     *
     * @return the number of individuals for this elitist selection
     *    function
     */
    public int getIndividuals()
    {
        return individuals;
    }

    /** @see Selection */
    public WeightedMap<I> select(final WeightedMap<I> parents,
                                 final WeightedMap<I> children)
    {
        int size = children.size();

        // sort keys by score
        List<Map.Entry<I, Double>> sortedKeys = new ArrayList<Map.Entry<I, Double>>(children.entrySet());
        Collections.sort(sortedKeys, WEIGHTED_MAP_ENTRY_COMPARATOR);

        // take the top n individuals
        List<Map.Entry<I, Double>> eliteKeys = sortedKeys.subList(0, individuals);

        // create intermediate map of the top n individuals
        WeightedMap<I> intermediate = new HashWeightedMap<I>(individuals, DEFAULT_LOAD_FACTOR);
        for (Map.Entry<I, Double> e : eliteKeys)
        {
            intermediate.put(e.getKey(), Double.valueOf(0.0d));
        }

        // update fitness based on number of top n individuals
        for (I i : intermediate.keySet())
        {
            intermediate.put(i, Double.valueOf(1.0d/individuals));
        }

        // fitness proportional selection on intermediate map
        HashWeightedMap<I> result = new HashWeightedMap<I>(size, DEFAULT_LOAD_FACTOR);
        for (int i = 0; i < size; i++)
        {
            I individual = intermediate.sample();
            // unsafe cast!
            result.put((I) ((Individual) individual).shallowCopy(), children.get(individual));
        }

        sortedKeys = null;
        eliteKeys = null;
        intermediate = null;

        return result;
    }


    /**
     * WeightedMap entry comparator.
     */
    private class WeightedMapEntryComparator
        implements Comparator<Map.Entry<I, Double>>
    {

        /** @see Comparator */
        public int compare(final Map.Entry<I, Double> e1, final Map.Entry<I, Double> e2)
        {
            return e1.getValue().compareTo(e2.getValue());
        }

        /** @see Comparator */
        public boolean equals(final Object o)
        {
            return super.equals(o);
        }
    }
}