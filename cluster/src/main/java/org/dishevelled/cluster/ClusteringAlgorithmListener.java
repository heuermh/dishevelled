/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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

import java.util.EventListener;

/**
 * A listener that receives notification of progress during execution of a
 * clustering algorithm.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ClusteringAlgorithmListener<E>
    extends EventListener
{

    /**
     * Notify this listener of an exit failed event.
     *
     * @param event exit failed event
     */
    void exitFailed(ClusteringAlgorithmEvent<E> event);

    /**
     * Notify this listener of an exit succeeded event.
     *
     * @param event exit succeeded event
     */
    void exitSucceeded(ClusteringAlgorithmEvent<E> event);

    /**
     * Notify this listener of a similarity calculated event.  The specified event
     * object will provide a reference to the two values and their similarity.
     *
     * @see ClusteringAlgorithmEvent#getValue1
     * @see ClusteringAlgorithmEvent#getValue2
     * @see ClusteringAlgorithmEvent#getSimilarity
     * @param event similarity calculated event
     */
    void similarityCalculated(ClusteringAlgorithmEvent<E> event);
}