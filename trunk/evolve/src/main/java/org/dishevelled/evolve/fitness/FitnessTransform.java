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

import org.dishevelled.evolve.Fitness;

import org.dishevelled.functor.UnaryFunction;
//import cern.colt...functions.DoubleFunction;

/**
 * Fitness transform.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessTransform<I>
    implements Fitness<I>
{
    /** Fitness delegate. */
    private final Fitness<I> fitness;

    /** Tranform function. */
    private final UnaryFunction<Double, Double> transform;
    //private final DoubleFunction transform;


    /**
     * Create a new fitness transform with the specified
     * fitness function and transform function.
     *
     * @param fitness fitness function to transform, must not be null
     * @param transform transform function, must not be null
     */
    public FitnessTransform(final Fitness<I> fitness,
                               final UnaryFunction<Double, Double> transform)
    {
        if (fitness == null)
        {
            throw new IllegalArgumentException("fitness must not be null");
        }
        if (transform == null)
        {
            throw new IllegalArgumentException("transform must not be null");
        }
        this.fitness = fitness;
        this.transform = transform;
    }


    /** {@inheritDoc} */
    public Double score(final I individual)
    {
        return transform.evaluate(fitness.score(individual));
    }
}