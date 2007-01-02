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
package org.dishevelled.evolve.select;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.Selection;
import org.dishevelled.evolve.Individual;

/**
 * Fitness proportional selection function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessProportionalSelection<I>
    implements Selection<I>
{
    /** Default hash map load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;


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
        WeightedMap<I> result = new HashWeightedMap<I>(size, DEFAULT_LOAD_FACTOR);
        for (int i = 0; i < size; i++)
        {
            I individual = children.sample();
            // unsafe cast!
            result.put((I) ((Individual) individual).shallowCopy(), children.get(individual));
        }
        return result;
    }
}
