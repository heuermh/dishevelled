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

import org.dishevelled.functor.UnaryFunction;
//import cern.colt...functions.DoubleFunction;

/**
 * Similarity transform.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SimilarityTransform<E>
    implements Similarity<E>
{
    /** Similarity delegate. */
    private final Similarity<E> similarity;

    /** Tranform function. */
    private final UnaryFunction<Double, Double> transform;
    //private final DoubleFunction transform;


    /**
     * Create a new similarity transform with the specified
     * similarity function and transform function.
     *
     * @param similarity similarity function to transform, must not be null
     * @param transform transform function, must not be null
     */
    public SimilarityTransform(final Similarity<E> similarity,
                               final UnaryFunction<Double, Double> transform)
    {
        if (similarity == null)
        {
            throw new IllegalArgumentException("similarity must not be null");
        }
        if (transform == null)
        {
            throw new IllegalArgumentException("transform must not be null");
        }
        this.similarity = similarity;
        this.transform = transform;
    }


    /** {@inheritDoc} */
    public double similarity(final E e1, final E e2)
    {
        return transform.evaluate(similarity.similarity(e1, e2));
    }
}