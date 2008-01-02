/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2008 held jointly by the individual authors.

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

import java.util.Random;

import org.dishevelled.evolve.Fitness;

/**
 * Random fitness function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision: 225 $ $Date: 2007-01-08 23:25:51 -0600 (Mon, 08 Jan 2007) $
 */
public final class RandomFitness<I>
    implements Fitness<I>
{
    /** Source of randomness for this random fitness function. */
    private final Random random;


    /**
     * Create a new random fitness function with a default source of randomness.
     */
    public RandomFitness()
    {
        random = new Random();
    }

    /**
     * Create a new random fitness function with the specified source of randomness.
     *
     * @param random source of randomness for this random fitness function, must not be null
     */
    public RandomFitness(final Random random)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        this.random = random;
    }


    /** {@inheritDoc} */
    public Double score(final I individual)
    {
        return Double.valueOf(random.nextDouble());
    }
}
