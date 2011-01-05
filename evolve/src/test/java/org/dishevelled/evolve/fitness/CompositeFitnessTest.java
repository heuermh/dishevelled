/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2011 held jointly by the individual authors.

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
package org.dishevelled.evolve.fitness;

import java.util.Arrays;
import java.util.Collections;

import org.dishevelled.functor.BinaryFunction;

import org.dishevelled.evolve.AbstractFitnessTest;
import org.dishevelled.evolve.Fitness;

/**
 * Unit test for CompositeFitness.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CompositeFitnessTest
    extends AbstractFitnessTest
{

    /** {@inheritDoc} */
    protected <T> Fitness<T> createFitness()
    {
        Fitness<T> child = new UniformFitness<T>();
        BinaryFunction<Double, Double, Double> aggr = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Double.valueOf(1.0d);
                }
            };
        return new CompositeFitness(Collections.singletonList(child), aggr);
    }

    public void testConstructor()
    {
        Fitness<String> child0 = new UniformFitness<String>();
        Fitness<String> child1 = new UniformFitness<String>();
        Fitness<String> child2 = new UniformFitness<String>();
        BinaryFunction<Double, Double, Double> aggr = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Double.valueOf(1.0d);
                }
            };

        CompositeFitness<String> compositeFitness0 = new CompositeFitness(Collections.singletonList(child0), aggr);
        CompositeFitness<String> compositeFitness1 = new CompositeFitness(Arrays.asList(new Fitness[] { child0, child1, child2 }), aggr);

        try
        {
            CompositeFitness<String> compositeFitness = new CompositeFitness(null, aggr);
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositeFitness<String> compositeFitness = new CompositeFitness(Collections.singletonList(child0), null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositeFitness<String> compositeFitness = new CompositeFitness(Collections.<Fitness<String>>emptyList(), aggr);
            fail("ctr(emptyList(),) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testFitness()
    {
        Fitness<String> zero = new UniformFitness<String>(0.0d);
        Fitness<String> one = new UniformFitness<String>(1.0d);
        Fitness<String> two = new UniformFitness<String>(2.0d);
        BinaryFunction<Double, Double, Double> min = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Math.min(d1, d2);
                }
            };
        BinaryFunction<Double, Double, Double> max = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Math.max(d1, d2);
                }
            };

        CompositeFitness<String> compositeFitness1 = new CompositeFitness(Collections.singletonList(zero), min);
        assertEquals(0.0d, compositeFitness1.score("foo"), 0.1d);
        assertEquals(0.0d, compositeFitness1.score("bar"), 0.1d);

        CompositeFitness<String> compositeFitness2 = new CompositeFitness(Collections.singletonList(zero), max);
        assertEquals(0.0d, compositeFitness2.score("foo"), 0.1d);
        assertEquals(0.0d, compositeFitness2.score("bar"), 0.1d);

        CompositeFitness<String> compositeFitness4 = new CompositeFitness(Collections.singletonList(one), min);
        assertEquals(1.0d, compositeFitness4.score("foo"), 0.1d);
        assertEquals(1.0d, compositeFitness4.score("bar"), 0.1d);

        CompositeFitness<String> compositeFitness5 = new CompositeFitness(Collections.singletonList(one), max);
        assertEquals(1.0d, compositeFitness5.score("foo"), 0.1d);
        assertEquals(1.0d, compositeFitness5.score("bar"), 0.1d);

        CompositeFitness<String> compositeFitness7 = new CompositeFitness(Collections.singletonList(two), min);
        assertEquals(2.0d, compositeFitness7.score("foo"), 0.1d);
        assertEquals(2.0d, compositeFitness7.score("bar"), 0.1d);

        CompositeFitness<String> compositeFitness8 = new CompositeFitness(Collections.singletonList(two), max);
        assertEquals(2.0d, compositeFitness8.score("foo"), 0.1d);
        assertEquals(2.0d, compositeFitness8.score("bar"), 0.1d);

        CompositeFitness<String> compositeFitness10 = new CompositeFitness(Arrays.asList(new Fitness[] { zero, one, two }), min);
        assertEquals(0.0d, compositeFitness10.score("foo"), 0.1d);
        assertEquals(0.0d, compositeFitness10.score("bar"), 0.1d);

        CompositeFitness<String> compositeFitness11 = new CompositeFitness(Arrays.asList(new Fitness[] { zero, one, two }), max);
        assertEquals(2.0d, compositeFitness11.score("foo"), 0.1d);
        assertEquals(2.0d, compositeFitness11.score("bar"), 0.1d);
    }
}