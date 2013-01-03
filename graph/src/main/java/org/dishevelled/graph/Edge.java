/*

    dsh-graph  Directed graph interface and implementation.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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
package org.dishevelled.graph;

/**
 * Typed directed graph edge.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Edge<N, E>
{

    /**
     * Return the value at this edge.
     *
     * @return the value at this edge
     */
    E getValue();

    /**
     * Set the value at this edge to <code>value</code> (optional operation).
     *
     * @param value value at this edge
     * @throws UnsupportedOperationException if the <code>setValue(E)</code>
     *    operation is not supported by this edge
     */
    void setValue(E value);

    /**
     * Return the source node for this edge.  The source node will not be null.
     *
     * @return the source node for this edge
     */
    Node<N, E> source();

    /**
     * Return the target node for this edge.  The target node will not be null.
     *
     * @return the target node for this edge
     */
    Node<N, E> target();
}