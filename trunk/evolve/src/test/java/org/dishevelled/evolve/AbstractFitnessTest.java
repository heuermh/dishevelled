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
package org.dishevelled.evolve;

import junit.framework.TestCase;

import org.dishevelled.weighted.WeightedMap;

/**
 * Abstract unit test for implementations of Fitness.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractFitnessTest
    extends TestCase
{

    /**
     * Create and return a new instance of Fitness to test.
     *
     * @return a new instance of Fitness to test
     */
    protected abstract <T> Fitness<T> createFitness();

    /**
     * Return a weighted map of expected fitness values.
     *
     * @param t instance of generic type T
     * @return a weighted map of expected fitness values
     */
    protected abstract <T> WeightedMap<T> getExpectedValues(T t);


    public void testIntegerFitness()
    {
        Fitness<Integer> fitness = createFitness();
        Integer individual = Integer.valueOf(0);
        WeightedMap<Integer> expectedValues = getExpectedValues(individual);

        for (Integer i : expectedValues.keySet())
        {
            assertEquals(expectedValues.get(i), fitness.score(i));
        }
    }

    public void testDoubleFitness()
    {
        Fitness<Double> fitness = createFitness();
        Double individual = Double.valueOf(0.0d);
        WeightedMap<Double> expectedValues = getExpectedValues(individual);

        for (Double d : expectedValues.keySet())
        {
            assertEquals(expectedValues.get(d), fitness.score(d));
        }
    }

    public void testStringFitness()
    {
        Fitness<String> fitness = createFitness();
        String individual = new String("foo");
        WeightedMap<String> expectedValues = getExpectedValues(individual);

        for (String s : expectedValues.keySet())
        {
            assertEquals(expectedValues.get(s), fitness.score(s));
        }
    }
}