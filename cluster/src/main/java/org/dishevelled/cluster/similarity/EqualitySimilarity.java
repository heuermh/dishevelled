/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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

import org.dishevelled.cluster.Similarity;

/**
 * Equality similarity function.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EqualitySimilarity<E>
    implements Similarity<E>
{
    /** Similarity for objects that are equal. */
    private final double equal;

    /** Similarity for objects that are not equal. */
    private final double notEqual;

    /** Default similarity for objects that are equal, <code>1.0d</code>. */
    public static final double DEFAULT_EQUAL_SIMILARITY = 1.0d;

    /** Default similarity for objects that are not equal, <code>0.0d</code>. */
    public static final double DEFAULT_NOT_EQUAL_SIMILARITY = 0.0d;


    /**
     * Create a new equality similarity function with the default similarities
     * of <code>1.0d</code> for objects that are equal and <code>0.0d</code>
     * for objects that are not equal.
     *
     * @see #DEFAULT_EQUAL_SIMILARITY
     * @see #DEFAULT_NOT_EQUAL_SIMILARITY
     */
    public EqualitySimilarity()
    {
        equal = DEFAULT_EQUAL_SIMILARITY;
        notEqual = DEFAULT_NOT_EQUAL_SIMILARITY;
    }

    /**
     * Create a new equality similarity function with the specified similarities.
     *
     * @param equal similarity for objects that are equal
     * @param notEqual similarity for objects that are not equal
     */
    public EqualitySimilarity(final double equal, final double notEqual)
    {
        this.equal = equal;
        this.notEqual = notEqual;
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
        return (value1.equals(value2)) ? equal : notEqual;
    }
}