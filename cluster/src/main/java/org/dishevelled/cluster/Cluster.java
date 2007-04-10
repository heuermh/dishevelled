/*

    dsh-cluster  Framework for cluster algorithms.
    Copyright (c) 2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.cluster;

import java.util.Set;

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
     * Return the exemplar or cluster center value for this cluster.  The
     * exemplar will not be null and will be contained in the set of member
     * values for this cluster.
     *
     * @return the exemplar or cluster center value for this cluster
     */
    E exemplar();

    /**
     * Return an unmodifiable set of member values for this cluster.  The set of
     * member values will not be null and will contain at least one value, the
     * exemplar or cluster center value.
     *
     * @return an unmodifiable set of member values for this cluster
     */
    Set<E> members();

    // ... stats, provenance, ?
    // ... not all clusterings have an exemplar
    // ... only if the cluster center is a data point is it called an examplar
    // ... is it safe to specify that all values in this cluster were present in the original list of values?
}