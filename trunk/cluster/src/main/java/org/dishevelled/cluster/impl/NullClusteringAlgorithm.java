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
package org.dishevelled.cluster.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.dishevelled.cluster.AbstractClusteringAlgorithm;
import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ExitStrategy;
import org.dishevelled.cluster.Similarity;

/**
 * Null clustering algorithm.  The result of this clustering algorithm
 * will be a set of clusters containing a single cluster containing all the
 * specified values.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NullClusteringAlgorithm<E>
    extends AbstractClusteringAlgorithm<E>
{

    /**
     * Create a new null clustering algorithm.
     */
    public NullClusteringAlgorithm()
    {
        super();
    }


    /** {@inheritDoc} */
    public Set<Cluster<E>> cluster(final List<? extends E> values,
                                   final Similarity<E> similarity,
                                   final ExitStrategy<E> exitStrategy)
    {
        checkClusterParameters(values, similarity, exitStrategy);
        fireExitSucceeded();
        Cluster<E> cluster = new ClusterImpl<E>(values);
        return Collections.singleton(cluster);
    }
}