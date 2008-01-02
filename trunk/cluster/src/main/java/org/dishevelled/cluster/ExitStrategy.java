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

import java.util.List;
import java.util.Set;

/**
 * An exit strategy function for clustering algorithms.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ExitStrategy<E>
{

    /**
     * Return <code>true</code> if the specified set of clusters for the
     * specified list of values has met the criteria of this exit strategy function.
     *
     * @param values unmodifiable list of values, must not be null, must
     *    contain at least one value, and must not contain any null values
     * @param clusters unmodifiable set of clusters, must not be null and
     *    must contain at least one cluster
     * @return true if the specified set of clusters has met the criteria of
     *    this exit strategy function
     */
    boolean evaluate(List<? extends E> values, Set<Cluster<E>> clusters);
}
