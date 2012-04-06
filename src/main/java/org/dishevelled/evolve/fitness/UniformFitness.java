/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2012 held jointly by the individual authors.

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

/**
 * Uniform fitness function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UniformFitness<I>
    implements Fitness<I>
{
    /** Uniform fitness score. */
    private final double score;

    /** Default score, <code>1.0d</code>. */
    static final double DEFAULT_SCORE = 1.0d;


    /**
     * Create a new uniform fitness function with
     * the default score of <code>1.0d</code>.
     */
    public UniformFitness()
    {
        this(DEFAULT_SCORE);
    }

    /**
     * Create a new uniform fitness function with
     * the specified score.
     *
     * @param score uniform fitness score
     */
    public UniformFitness(final double score)
    {
        this.score = score;
    }


    /** {@inheritDoc} */
    public Double score(final I individual)
    {
        return Double.valueOf(score);
    }
}
