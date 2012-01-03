/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2012 held jointly by the individual authors.

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
package org.dishevelled.cluster.similarity;

import java.util.Random;

import org.dishevelled.cluster.Similarity;

/**
 * Random similarity function.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RandomSimilarity<E>
    implements Similarity<E>
{
    /** Source of randomness for this random similarity function. */
    private final Random random;


    /**
     * Create a new random similarity function with a default source of randomness.
     */
    public RandomSimilarity()
    {
        random = new Random();
    }


    /**
     * Create a new random similarity function with the specified source of randomness.
     *
     * @param random source of randomness for this random similarity funtion, must not be null
     */
    public RandomSimilarity(final Random random)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        this.random = random;
    }


    /** {@inheritDoc} */
    public double similarity(final E value1, final E value2)
    {
        if (value1 == null)
        {
            throw new IllegalArgumentException("value1 must not be null");
        }
        if (value2 == null)
        {
            throw new IllegalArgumentException("value2 must not be null");
        }
        return random.nextDouble();
    }
}