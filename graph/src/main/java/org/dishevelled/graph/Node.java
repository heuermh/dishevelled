/*

    dsh-graph  Directed graph interface and implementation.
    Copyright (c) 2004-2010 held jointly by the individual authors.

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

import java.util.Set;

/**
 * Typed directed graph node.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Node<N, E>
{

    /**
     * Return the value at this node.
     *
     * @return the value at this node
     */
    N getValue();

    /**
     * Set the value at this node to <code>value</code> (optional operation).
     *
     * @param value value at this node
     * @throws UnsupportedOperationException if the <code>setValue(N)</code>
     *    operation is not supported by this node
     */
    void setValue(N value);

    /**
     * Return the degree of this node.
     *
     * @return the degree of this node
     */
    int degree();

    /**
     * Return a read-only set view of the edges in this
     * graph containing this node as the target.  The view
     * may be empty (if e.g. <code>degree() == 0</code> or
     * this node has only out edges) but will not be null.
     *
     * @return a read-only set view of the edges in this
     *    graph containing this node as the target
     */
    Set<Edge<N, E>> inEdges();

    /**
     * Return a read-only set view of the edges in this
     * graph containing this node as the source.  The view
     * may be empty (if e.g. <code>degree() == 0</code> or
     * this node has only in edges) but will not be null.
     *
     * @return a read-only set view of the edges in this
     *    graph containing this node as the source
     */
    Set<Edge<N, E>> outEdges();
}