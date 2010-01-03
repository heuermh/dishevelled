/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2010 held jointly by the individual authors.

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

import org.dishevelled.evolve.AbstractFitnessTest;
import org.dishevelled.evolve.Fitness;

import org.dishevelled.functor.UnaryFunction;

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
    protected <T> Fitness<T> createFitness()
    {
        Fitness<T> fitness = new UniformFitness<T>();
        UnaryFunction<Double, Double> transform = new UnaryFunction<Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return 1.0d;
                }
            };
        return new FitnessTransform<T>(fitness, transform);
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

    public void testFitness()
    {
        Fitness<String> positiveFitness = new UniformFitness<String>(1.0d);
        Fitness<String> negativeFitness = new UniformFitness<String>(-1.0d);
        UnaryFunction<Double, Double> inverseTransform = new UnaryFunction<Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return (-1.0d * value);
                }
            };

        Fitness<String> fitnessTransform0 = new FitnessTransform<String>(positiveFitness, inverseTransform);
        assertEquals(-1.0d, fitnessTransform0.score("foo"));

        Fitness<String> fitnessTransform1 = new FitnessTransform<String>(negativeFitness, inverseTransform);
        assertEquals(1.0d, fitnessTransform1.score("bar"));
    }
}