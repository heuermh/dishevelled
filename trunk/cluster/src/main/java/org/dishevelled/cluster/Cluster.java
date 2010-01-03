/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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
 * Cluster.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Cluster<E>
    extends Iterable<E>
{

    /**
     * Return the size of this cluster in number of member values.
     * The size of this cluster will be at least one.
     *
     * @return the size of this cluster in number of member values
     */
    int size();

    /**
     * Return true if this cluster is a singleton cluster, that is if this cluster
     * has only one member value.
     *
     * @return true if this cluster is a singleton cluster
     */
    boolean isSingleton();

    /**
     * Return the exemplar or cluster center value for this cluster, if any.
     *
     * @return the exemplar or cluster center value for this cluster, or null
     *    if this cluster does not have an exemplar or cluster center value
     */
    E exemplar();

    /**
     * Return an unmodifiable list of member values for this cluster.  The list of
     * member values will not be null and will contain at least one value.
     *
     * @return an unmodifiable list of member values for this cluster
     */
    List<E> members();

    // ... stats, provenance, ?
    // ... not all clusterings have an exemplar
    // ... only if the cluster center is a data point is it called an examplar
    // ... is it safe to specify that all values in this cluster were present in the original list of values?
}