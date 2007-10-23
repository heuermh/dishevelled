/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007 held jointly by the individual authors.

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
package org.dishevelled.cluster;

import java.util.List;

/**
 * Abstract implementation of ClusteringAlgorithm.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractClusteringAlgorithm<E>
    extends ClusteringAlgorithmListenerSupport<E>
    implements ClusteringAlgorithm<E>
{

    /**
     * Create a new abstract clustering algorithm.
     */
    protected AbstractClusteringAlgorithm()
    {
        super();
        setSource(this);
    }


    /**
     * Check the specified cluster parameters for validity.
     *
     * @param values list of values to cluster, must not be null, must
     *    contain at least one value, and must not contain any null values
     * @param similarity similarity measure to apply, must not be null
     * @param exitStrategy exit strategy, must not be null
     */
    protected final void checkClusterParameters(final List<? extends E> values,
                                                final Similarity<E> similarity,
                                                final ExitStrategy<E> exitStrategy)
    {
        if (values == null)
        {
            throw new IllegalArgumentException("values must not be null");
        }
        if (values.isEmpty())
        {
            throw new IllegalArgumentException("values must contain at least one value");
        }
        if (values.indexOf(null) != -1)
        {
            throw new IllegalArgumentException("values must not contain null values");
        }
        if (similarity == null)
        {
            throw new IllegalArgumentException("similarity must not be null");
        }
        if (exitStrategy == null)
        {
            throw new IllegalArgumentException("exitStrategy must not be null");
        }
    }
}