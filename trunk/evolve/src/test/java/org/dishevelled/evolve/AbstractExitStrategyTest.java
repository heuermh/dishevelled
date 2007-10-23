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
package org.dishevelled.evolve;

import junit.framework.TestCase;

import org.dishevelled.weighted.WeightedMap;

/**
 * Abstract unit test for implementations of ExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractExitStrategyTest
    extends TestCase
{

    /**
     * Create and return a new instance of ExitStrategy to test.
     *
     * @return a new instance of ExitStrategy to test
     */
    protected abstract <T> ExitStrategy<T> createExitStrategy();

    /**
     * Return a time value for a fail condition.
     *
     * @return a time value for a fail condition
     */
    protected abstract int getFailConditionTime();

    /**
     * Return a population value for a fail condition.
     *
     * @param t instance of generic type T
     * @return a population value for a fail condition
     */
    protected abstract <T> WeightedMap<T> getFailConditionPopulation(T t);

    /**
     * Return a time value for a success condition.
     *
     * @return a time value for a success condition
     */
    protected abstract int getSuccessConditionTime();

    /**
     * Return a population value for a success condition.
     *
     * @param t instance of generic type T
     * @return a population value for a success condition
     */
    protected abstract <T> WeightedMap<T> getSuccessConditionPopulation(T t);


    public void testExitStrategy()
    {
        ExitStrategy<Integer> exit = createExitStrategy();

        assertNotNull(exit);

        Integer individual = Integer.valueOf(0);
        WeightedMap<Integer> failPopulation = getFailConditionPopulation(individual);
        WeightedMap<Integer> successPopulation = getSuccessConditionPopulation(individual);

        assertFalse(exit.evaluate(failPopulation, getFailConditionTime()));
        assertTrue(exit.evaluate(successPopulation, getSuccessConditionTime()));
    }
}
