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
package org.dishevelled.cluster;

import java.util.List;
import java.util.Set;

/**
 * Clustering algorithm.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ClusteringAlgorithm<E>
{

    /**
     * Cluster the specified list of values after applying the specified
     * similarity measure until the criteria of the specified exit strategy
     * are met.  A clustering algorithm exception is thrown if an error occurs.
     *
     * <p>
     * Any additional parameters required to execute this clustering algorithm
     * might be specified in this clustering algorithm's constructor or by setting
     * property values after construction.
     * </p>
     *
     * <p>
     * Implementations must not modify the specified list of values or any
     * of the values in the specified list of values.  Rather they should
     * make a defensive copy of the list of values if necessary.
     * </p>
     *
     * <p>
     * Clients interested in receiving notification of progress made during the
     * execution of this clustering algorithm may register as cluster algorithm listeners.
     * </p>
     *
     * <p>
     * The result of this clustering algorithm is returned as an unmodifiable set of
     * clusters.  The set of clusters will not be null and will contain at least one cluster.
     * </p>
     *
     * @param values list of values to cluster, must not be null, must
     *    contain at least one value, and must not contain any null values
     * @param similarity similarity measure to apply, must not be null
     * @param exitStrategy exit strategy, must not be null
     * @return an unmodifiable set of clusters
     * @throws ClusteringAlgorithmException if an error occurs
     */
    Set<Cluster<E>> cluster(List<? extends E> values,
                            Similarity<E> similarity,
                            ExitStrategy<E> exitStrategy)
        throws ClusteringAlgorithmException;

    /**
     * Add the specified clustering algorithm listener.
     *
     * @param listener clustering algorithm listener to add
     */
    void addClusteringAlgorithmListener(ClusteringAlgorithmListener<E> listener);

    /**
     * Remove the specified clustering algorithm listener.
     *
     * @param listener clustering algorithm listener to remove
     */
    void removeClusteringAlgorithmListener(ClusteringAlgorithmListener<E> listener);
}