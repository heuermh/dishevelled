/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007 held jointly by the individual authors.

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
package org.dishevelled.cluster.similarity;

import org.dishevelled.cluster.Similarity;

/**
 * Uniform similarity function.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UniformSimilarity<E>
    implements Similarity<E>
{
    /** Similarity for this uniform similarity function. */
    private final double similarity;

    /** Default similarity. */
    static final double DEFAULT_SIMILARITY = 1.0d;


    /**
     * Create a new uniform similarity function with the default similarity of <code>1.0d</code>.
     */
    public UniformSimilarity()
    {
        similarity = DEFAULT_SIMILARITY;
    }


    /**
     * Create a new uniform similarity function with the specified similarity.
     *
     * @param similarity similarity for this uniform similarity function
     */
    public UniformSimilarity(final double similarity)
    {
        this.similarity = similarity;
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
        return similarity;
    }
}