/*

    dsh-evolve  Simple framework for evolutionary algorithms.
    Copyright (c) 2005 held jointly by the individual authors.

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
package org.dishevelled.evolve.exit;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.ExitStrategy;
import org.dishevelled.evolve.AbstractExitStrategyTest;

/**
 * Unit test for TimeLimitExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TimeLimitExitStrategyTest
    extends AbstractExitStrategyTest
{
    /** Time limit. */
    private static final int TIME_LIMIT = 10;


    /** @see ExitStrategyTest */
    protected <T> ExitStrategy<T> createExitStrategy()
    {
        return new TimeLimitExitStrategy<T>(TIME_LIMIT);
    }

    /** @see ExitStrategyTest */
    protected <T> WeightedMap<T> getFailConditionPopulation(final T t)
    {
        WeightedMap<T> population = new HashWeightedMap<T>();
        population.put(t, Double.valueOf(0.5d));

        return population;
    }

    /** @see ExitStrategyTest */
    protected int getFailConditionTime()
    {
        return 0;
    }

    /** @see ExitStrategyTest */
    protected <T> WeightedMap<T> getSuccessConditionPopulation(final T t)
    {
        WeightedMap<T> population = new HashWeightedMap<T>();
        population.put(t, Double.valueOf(0.8d));

        return population;
    }

    /** @see ExitStrategyTest */
    protected int getSuccessConditionTime()
    {
        return 10;
    }


    public void testConstructor()
    {
        try
        {
            ExitStrategy<Integer> exit = new TimeLimitExitStrategy<Integer>(0);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ExitStrategy<Integer> exit = new TimeLimitExitStrategy<Integer>(-1);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
