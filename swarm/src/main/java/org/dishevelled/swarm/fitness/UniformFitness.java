/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2007 held jointly by the individual authors.

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
package org.dishevelled.swarm.fitness;

import org.dishevelled.swarm.Fitness;

/**
 * Uniform fitness function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UniformFitness
    implements Fitness
{
    /** Score for this uniform fitness function. */
    private final double score;

    /** Default score. */
    static final double DEFAULT_SCORE = 1.0d;


    /**
     * Create a new uniform fitness function with the default score of <code>1.0d</code>.
     */
    public UniformFitness()
    {
        score = DEFAULT_SCORE;
    }


    /**
     * Create a new uniform fitness function with the specified score.
     *
     * @param score score for this uniform fitness function
     */
    public UniformFitness(final double score)
    {
        this.score = score;
    }


    /** {@inheritDoc} */
    public double score(final double[] position)
    {
        return score;
    }
}