/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2008 held jointly by the individual authors.

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

import java.util.Random;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.swarm.Fitness;
import org.dishevelled.swarm.AbstractFitnessTest;

/**
 * Unit test for FitnessTransform.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessTransformTest
    extends AbstractFitnessTest
{

    /** {@inheritDoc} */
    protected Fitness createFitness()
    {
        Fitness fitness = new UniformFitness();
        UnaryFunction<Double, Double> transform = new UnaryFunction<Double, Double>()
        {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return 1.0d;
                }
            };
        return new FitnessTransform(fitness, transform);
    }

    public void testConstructor()
    {
        Fitness fitness = new UniformFitness();
        UnaryFunction<Double, Double> transform = new UnaryFunction<Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return 1.0d;
                }
            };

        Fitness fitnessTransform0 = new FitnessTransform(fitness, transform);

        try
        {
            Fitness fitnessTransform = new FitnessTransform(null, transform);
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Fitness fitnessTransform = new FitnessTransform(fitness, null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Fitness fitnessTransform = new FitnessTransform(null, null);
            fail("ctr(null,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testFitnessTransform()
    {
        Fitness randomFitness = new RandomFitness();
        UnaryFunction<Double, Double> transform = new UnaryFunction<Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return 1.0d;
                }
            };

        Fitness fitnessTransform = new FitnessTransform(randomFitness, transform);

        Random random = new Random();
        for (int i = 0; i < 100; i++)
        {
            double[] position = new double[] { random.nextDouble(), random.nextDouble() };
            assertTrue(randomFitness.score(position) != fitnessTransform.score(position));
            assertEquals(1.0d, fitnessTransform.score(position));
        }
    }
}
