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

    /**
     * Create a new clustering algorithm event with the specified source.
     *
     * @param clusteringAlgorithm source of this event, must not be null
     */
    public ClusteringAlgorithmEvent(final ClusteringAlgorithm<E> clusteringAlgorithm)
    {
        super(clusteringAlgorithm);
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
}