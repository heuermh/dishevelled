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

import java.util.ArrayList;
import java.util.List;

import org.dishevelled.evolve.Fitness;

import org.dishevelled.functor.BinaryFunction;
//import cern.colt...functions.DoubleDoubleFunction;

/**
 * Composite fitness function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CompositeFitness<I>
    implements Fitness<I>
{
    /** List of child fitness functions. */
    private final List<Fitness<? super I>> children;

    /** Aggregate function. */
    private final BinaryFunction<Double, Double, Double> aggregate;
    //private final DoubleDoubleFunction aggregate;


    /**
     * Create a new composite fitness function with the specified list
     * of child fitness functions and the specified aggregate function.
     *
     * @param children list of child fitness functions, must not be
     *    null and must contain at least one fitness function
     * @param aggregate aggregate function, must not be null
     */
    public CompositeFitness(final List<Fitness<? super I>> children,
                               final BinaryFunction<Double, Double, Double> aggregate)
    //                               final DoubleDoubleFunction aggregate)
    {
        if (children == null)
        {
            throw new IllegalArgumentException("children must not be null");
        }
        if (children.isEmpty())
        {
            throw new IllegalArgumentException("children must contain at least one fitness function");
        }
        if (aggregate == null)
        {
            throw new IllegalArgumentException("aggregate must not be null");
        }
        this.children = new ArrayList<Fitness<? super I>>(children);
        this.aggregate = aggregate;
    }


    /** {@inheritDoc} */
    public Double score(final I individual)
    {
        int last = children.size() - 1;
        double rv = children.get(last).score(individual);
        for (int i = last; --i >= 0;)
        {
            rv = aggregate.evaluate(rv, children.get(i).score(individual));
        }
        return rv;
    }
}