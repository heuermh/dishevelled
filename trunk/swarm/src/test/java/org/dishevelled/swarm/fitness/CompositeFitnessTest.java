/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2013 held jointly by the individual authors.

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
package org.dishevelled.swarm.fitness;

import java.util.Arrays;
import java.util.Collections;

import org.dishevelled.functor.BinaryFunction;

import org.dishevelled.swarm.Fitness;
import org.dishevelled.swarm.AbstractFitnessTest;

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
    protected Fitness createFitness()
    {
        Fitness child = new UniformFitness();
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
        Fitness child0 = new UniformFitness();
        Fitness child1 = new UniformFitness();
        Fitness child2 = new UniformFitness();
        BinaryFunction<Double, Double, Double> aggr = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Double.valueOf(1.0d);
                }
            };

        CompositeFitness compositeFitness0 = new CompositeFitness(Collections.singletonList(child0), aggr);
        CompositeFitness compositeFitness1 = new CompositeFitness(Arrays.asList(new Fitness[] { child0, child1, child2 }), aggr);

        try
        {
            CompositeFitness compositeFitness = new CompositeFitness(null, aggr);
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositeFitness compositeFitness = new CompositeFitness(Collections.singletonList(child0), null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositeFitness compositeFitness = new CompositeFitness(Collections.<Fitness>emptyList(), aggr);
            fail("ctr(emptyList(),) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCompositeFitness()
    {
        Fitness zero = new UniformFitness(0.0d);
        Fitness one = new UniformFitness(1.0d);
        Fitness two = new UniformFitness(2.0d);
        double[] position0 = new double[] { 0.0d, 0.5d };
        double[] position1 = new double[] { 0.5d, 1.0d };
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

        CompositeFitness compositeFitness1 = new CompositeFitness(Collections.singletonList(zero), min);
        assertEquals(0.0d, compositeFitness1.score(position0), 0.1d);
        assertEquals(0.0d, compositeFitness1.score(position1), 0.1d);

        CompositeFitness compositeFitness2 = new CompositeFitness(Collections.singletonList(zero), max);
        assertEquals(0.0d, compositeFitness2.score(position0), 0.1d);
        assertEquals(0.0d, compositeFitness2.score(position1), 0.1d);

        CompositeFitness compositeFitness4 = new CompositeFitness(Collections.singletonList(one), min);
        assertEquals(1.0d, compositeFitness4.score(position0), 0.1d);
        assertEquals(1.0d, compositeFitness4.score(position1), 0.1d);

        CompositeFitness compositeFitness5 = new CompositeFitness(Collections.singletonList(one), max);
        assertEquals(1.0d, compositeFitness5.score(position0), 0.1d);
        assertEquals(1.0d, compositeFitness5.score(position1), 0.1d);

        CompositeFitness compositeFitness7 = new CompositeFitness(Collections.singletonList(two), min);
        assertEquals(2.0d, compositeFitness7.score(position0), 0.1d);
        assertEquals(2.0d, compositeFitness7.score(position1), 0.1d);

        CompositeFitness compositeFitness8 = new CompositeFitness(Collections.singletonList(two), max);
        assertEquals(2.0d, compositeFitness8.score(position0), 0.1d);
        assertEquals(2.0d, compositeFitness8.score(position1), 0.1d);

        CompositeFitness compositeFitness10 = new CompositeFitness(Arrays.asList(new Fitness[] { zero, one, two }), min);
        assertEquals(0.0d, compositeFitness10.score(position0), 0.1d);
        assertEquals(0.0d, compositeFitness10.score(position1), 0.1d);

        CompositeFitness compositeFitness11 = new CompositeFitness(Arrays.asList(new Fitness[] { zero, one, two }), max);
        assertEquals(2.0d, compositeFitness11.score(position0), 0.1d);
        assertEquals(2.0d, compositeFitness11.score(position1), 0.1d);
    }
}
