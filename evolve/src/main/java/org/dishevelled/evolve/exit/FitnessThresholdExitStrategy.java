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
package org.dishevelled.evolve.exit;

import org.dishevelled.weighted.WeightedMap;

import org.dishevelled.evolve.ExitStrategy;

/**
 * Fitness threshold exit strategy.  Exits as soon as
 * at least one individual has a fitness score above a
 * set threshold.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessThresholdExitStrategy<I>
    implements ExitStrategy<I>
{
    /** Threshold for this fitness threshold exit strategy. */
    private final double threshold;


    /**
     * Create a new fitness threshold exit strategy with the specified threshold.
     *
     * @param threshold threshold for this fitness threshold exit strategy
     */
    public FitnessThresholdExitStrategy(final double threshold)
    {
        this.threshold = threshold;
    }


    /** {@inheritDoc} */
    public boolean evaluate(final WeightedMap<I> population, final int time)
    {
        for (I i : population.keySet())
        {
            double fitness = population.get(i);

            if (fitness > threshold)
            {
                return true;
            }
        }
        return false;
    }
}
