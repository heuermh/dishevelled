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
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.ExitStrategy;
import org.dishevelled.evolve.AbstractExitStrategyTest;

/**
 * Unit test for FitnessFloorExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessFloorExitStrategyTest
    extends AbstractExitStrategyTest
{
    /** Fitness lower bound. */
    private static final double LOWER_BOUND = 0.75d;


    /** {@inheritDoc} */
    protected <T> ExitStrategy<T> createExitStrategy()
    {
        return new FitnessFloorExitStrategy<T>(LOWER_BOUND);
    }

    /** {@inheritDoc} */
    protected <T> WeightedMap<T> getFailConditionPopulation(final T t)
    {
        WeightedMap<T> population = new HashWeightedMap<T>();
        population.put(t, Double.valueOf(0.5d));
        return population;
    }

    /** {@inheritDoc} */
    protected int getFailConditionTime()
    {
        return 0;
    }

    /** {@inheritDoc} */
    protected <T> WeightedMap<T> getSuccessConditionPopulation(final T t)
    {
        WeightedMap<T> population = new HashWeightedMap<T>();
        population.put(t, Double.valueOf(0.8d));
        return population;
    }

    /** {@inheritDoc} */
    protected int getSuccessConditionTime()
    {
        return 0;
    }
}
