/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2008 held jointly by the individual authors.

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

import java.util.EventObject;

/**
 * An event representing progress during execution of a clustering algorithm.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ClusteringAlgorithmEvent<E>
    extends EventObject
{
    /** First value, for similarity calculated events. */
    private final E value1;

    /** Second value, for similarity calculated events. */
    private final E value2;

    /** Similarity, for similarity calculated events. */
    private final double similarity;


    /**
     * Create a new clustering algorithm event with the specified source.
     *
     * @param clusteringAlgorithm source of this event, must not be null
     */
    public ClusteringAlgorithmEvent(final ClusteringAlgorithm<E> clusteringAlgorithm)
    {
        super(clusteringAlgorithm);
        value1 = null;
        value2 = null;
        similarity = Double.NaN;
    }

    /**
     * Create a new clustering algorithm event with the specified source.
     *
     * @param clusteringAlgorithm source of this event, must not be null
     * @param value1 first value, for similarity calculated events
     * @param value2 second value, for similarity calculated events
     * @param similarity similarity, for similarity calculated events
     */
    public ClusteringAlgorithmEvent(final ClusteringAlgorithm<E> clusteringAlgorithm,
                                    final E value1,
                                    final E value2,
                                    final double similarity)
    {
        super(clusteringAlgorithm);
        this.value1 = value1;
        this.value2 = value2;
        this.similarity = similarity;
    }

    /**
     * Return the source of this event as a clustering algorithm.  The source
     * will not be null.
     *
     * @return the source of this event as a clustering algorithm
     */
    public final ClusteringAlgorithm<E> getClusteringAlgorithm()
    {
        return (ClusteringAlgorithm<E>) super.getSource();
    }

    /**
     * Return the first value, for similarity calculated events.  May be null.
     *
     * @return the first value, for similarity calculated events
     */
    public final E getValue1()
    {
        return value1;
    }

    /**
     * Return the second value, for similarity calculated events.  May be null.
     *
     * @return the second value, for similarity calculated events
     */
    public final E getValue2()
    {
        return value2;
    }

    /**
     * Return the similarity, for similarity calculated events.  Defaults to <code>Double.NaN</code>.
     *
     * @return the similarity, for similarity calculated events
     */
    public final double getSimilarity()
    {
        return similarity;
    }
}