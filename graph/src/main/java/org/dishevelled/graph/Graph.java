/*

    dsh-graph  Directed graph interface and implementation.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;

/**
 * Directed graph with typed values on nodes and edges.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Graph<N, E>
{

    /**
     * Return true if this graph is empty.
     *
     * @return true if this graph is empty
     */
    boolean isEmpty();

    /**
     * Return the number of nodes in this graph.
     *
     * @return the number of nodes in this graph
     */
    int nodeCount();

    /**
     * Return a read-only set view of the nodes in this graph.  The view
     * may be empty (if <code>nodeCount() == 0</code>) but will not be null.
     *
     * @return a read-only set view of the nodes in this graph
     */
    Set<Node<N, E>> nodes();

    /**
     * Return a read-only collection view of the node values in this graph.  The
     * view may be empty (if <code>nodeCount() == 0</code>) but will not be null.
     *
     * @return a read-only collection view of the node values in this graph
     */
    Collection<N> nodeValues();

    /**
     * Return a map of type <code>&lt;Node&lt;N, E&gt;, T&gt;</code> with
     * the nodes in this graph as keys.  The keys in the returned map
     * reference the nodes in this graph and are read-only.  The map
     * may be empty (if <code>nodeCount() == 0</code>) but will not be null.
     *
     * @param <T> node map value type
     * @return a map of type <code>&lt;Node&lt;N, E&gt;, T&gt;</code> with
     *    the nodes in this graph as keys
     */
    <T> Map<Node<N, E>, T> nodeMap();

    /**
     * Apply the specified procedure to each node in this graph.
     *
     * @param procedure procedure to apply, must not be null
     */
    void forEachNode(UnaryProcedure<Node<N, E>> procedure);

    /**
     * Apply the specified procedure to each node in this graph
     * accepted by the specified predicate.
     *
     * @param predicate node predicate, must not be null
     * @param procedure procedure to apply, must not be null
     */
    void forEachNode(UnaryPredicate<Node<N, E>> predicate,
                     UnaryProcedure<Node<N, E>> procedure);

    /**
     * Apply the specified procedure to each node value in this graph.
     *
     * @param procedure procedure to apply, must not be null
     */
    void forEachNodeValue(UnaryProcedure<N> procedure);

    /**
     * Apply the specified procedure to each node value in this graph
     * accepted by the specified predicate.
     *
     * @param predicate node value predicate, must not be null
     * @param procedure procedure to apply, must not be null
     */
    void forEachNodeValue(UnaryPredicate<N> predicate, UnaryProcedure<N> procedure);

    /**
     * Return the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    int edgeCount();

    /**
     * Return a read-only set view of the edges in this graph.  The view
     * may be empty (if <code>edgeCount() == 0</code>) but will not be null.
     *
     * @return a read-only set view of the edges in this graph
     */
    Set<Edge<N, E>> edges();

    /**
     * Return a read-only collection view of the edge values in this graph.  The
     * view may be empty (if <code>edgeCount() == 0</code>) but will not be null.
     *
     * @return a read-only collection view of the edge values in this graph
     */
    Collection<E> edgeValues();

    /**
     * Return a map of type <code>&lt;Edge&lt;N, E&gt;, T&gt;</code> with
     * the edges in this graph as keys.  The keys in the returned map
     * reference the edges in this graph and are read-only.  The map
     * may be empty (if <code>edgeCount() == 0</code>) but will not be null.
     *
     * @param <T> edge map value type
     * @return a map of type <code>&lt;Edge&lt;N, E&gt;, T&gt;</code> with
     *    the edges in this graph as keys
     */
    <T> Map<Edge<N, E>, T> edgeMap();

    /**
     * Apply the specified procedure to each edge in this graph.
     *
     * @param procedure procedure to apply, must not be null
     */
    void forEachEdge(UnaryProcedure<Edge<N, E>> procedure);

    /**
     * Apply the specified procedure to each edge in this graph
     * accepted by the specified predicate.
     *
     * @param predicate edge predicate, must not be null
     * @param procedure procedure to apply, must not be null
     */
    void forEachEdge(UnaryPredicate<Edge<N, E>> predicate,
                     UnaryProcedure<Edge<N, E>> procedure);

    /**
     * Apply the specified procedure to each edge value in this graph.
     *
     * @param procedure procedure to apply, must not be null
     */
    void forEachEdgeValue(UnaryProcedure<E> procedure);

    /**
     * Apply the specified procedure to each edge value in this graph
     * accepted by the specified predicate.
     *
     * @param predicate edge value predicate, must not be null
     * @param procedure procedure to apply, must not be null
     */
    void forEachEdgeValue(UnaryPredicate<E> predicate, UnaryProcedure<E> procedure);

    /**
     * Clear the nodes and edges in this graph (optional operation).
     *
     * @throws UnsupportedOperationException if the <code>clear</code>
     *    operation is not supported by this graph
     */
    void clear();

    /**
     * Create and return a new node in this graph with the specified value (optional operation).
     *
     * @param value value
     * @return a new node in this graph with the specified value
     * @throws UnsupportedOperationException if the <code>createNode</code>
     *    operation is not supported by this graph
     */
    Node createNode(N value);

    /**
     * Remove the specified node and any edges connecting the
     * node from this graph (optional operation).
     *
     * @param node node to remove, must not be null and must be
     *    contained in this graph
     * @throws UnsupportedOperationException if the <code>remove(Node)</code>
     *    operation is not supported by this graph
     */
    void remove(Node<N, E> node);

    /**
     * Create and return a new edge in this graph with the specified value
     * connecting the specified source and target nodes (optional operation).
     *
     * @param value value
     * @param source source node, must not be null and must be
     *    contained in this graph
     * @param target target node, must not be null and must be
     *    contained in this graph
     * @return a new edge in this graph with the specified value
     *    connecting the specified source and target nodes
     * @throws UnsupportedOperationException if the <code>createEdge</code>
     *    operation is not supported by this graph
     */
    Edge<N, E> createEdge(Node<N, E> source, Node<N, E> target, E value);

    /**
     * Remove the specified edge from this graph (optional operation).
     *
     * @param edge edge to remove, must not be null and must be
     *    contained in this graph
     * @throws UnsupportedOperationException if the <code>remove(Edge)</code>
     *    operation is not supported by this graph
     */
    void remove(Edge<N, E> edge);
}