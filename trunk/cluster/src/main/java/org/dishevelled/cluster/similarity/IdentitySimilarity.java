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

import org.dishevelled.cluster.Similarity;

/**
 * Identity similarity function.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdentitySimilarity<E>
    implements Similarity<E>
{
    /** Similarity for objects that are identical. */
    private final double identical;

    /** Similarity for objects that are not identical. */
    private final double notIdentical;

    /** Default similarity for objects that are identical. */
    public static final double DEFAULT_IDENTICAL_SIMILARITY = 1.0d;

    /** Default similarity for objects that are not identical. */
    public static final double DEFAULT_NOT_IDENTICAL_SIMILARITY = 0.0d;


    /**
     * Create a new identity similarity function with the default similarities
     * of <code>1.0d</code> for objects that are identical and <code>0.0d</code>
     * for objects that are not identical.
     *
     * @see #DEFAULT_IDENTICAL_SIMILARITY
     * @see #DEFAULT_NOT_IDENTICAL_SIMILARITY
     */
    public IdentitySimilarity()
    {
        identical = DEFAULT_IDENTICAL_SIMILARITY;
        notIdentical = DEFAULT_NOT_IDENTICAL_SIMILARITY;
    }

    /**
     * Create a new identity similarity function with the specified similarities.
     *
     * @param identical similarity for objects that are identical
     * @param notIdentical similarity for objects that are not identical
     */
    public IdentitySimilarity(final double identical, final double notIdentical)
    {
        this.identical = identical;
        this.notIdentical = notIdentical;
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
        return (value1 == value2) ? identical : notIdentical;
    }
}