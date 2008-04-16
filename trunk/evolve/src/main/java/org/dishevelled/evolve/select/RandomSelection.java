/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2008 held jointly by the individual authors.

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

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;

import org.dishevelled.weighted.WeightedMap;

import org.dishevelled.evolve.Selection;

/**
 * Random selection function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RandomSelection<I>
    implements Selection<I>
{
    /** Source of randomness. */
    private final Random random;


    /**
     * Create a new random selection function with the default
     * source of randomness.
     */
    public RandomSelection()
    {
        random = new Random();
    }

    /**
     * Create a new random selection function with the specified
     * source of randomness.
     *
     * @param random source of randomness, must not be null
     */
    public RandomSelection(final Random random)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        this.random = random;
    }


    /**
     * Return the source of randomness for this random selection function.
     * The source of randomness will not be null.
     *
     * @return the source of randomness for this random selection function
     */
    public Random getRandom()
    {
        return random;
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
        List<I> populationAsList = new ArrayList<I>(population);
        List<I> selected = new ArrayList<I>(size);
        for (int i = 0; i < size; i++)
        {
            I individual = populationAsList.get(random.nextInt(size));
            selected.add(individual);
        }
        populationAsList = null;
        return selected;
    }
}
