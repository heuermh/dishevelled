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
package org.dishevelled.graph.impl;

import java.util.Map;
import java.util.Queue;

import java.util.concurrent.ArrayBlockingQueue;

import org.dishevelled.functor.UnaryProcedure;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

/**
 * Static utility methods on directed graphs.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphUtils
{

    /**
     * Private no-arg constructor.
     */
    private GraphUtils()
    {
        // empty
    }


    /**
     * Create and return a new directed graph.  The graph will not be null.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @return a new directed graph
     */
    public static <N, E> Graph<N, E> createGraph()
    {
        return new GraphImpl<N, E>();
    }

    /**
     * Create and return a new directed graph with the specified initial node
     * and edge capacities.  The graph will not be null.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param nodeCapacity initial node capacity, must be <code>&gt;= 0</code>
     * @param edgeCapacity initial edge capacity, must be <code>&gt;= 0</code>
     * @return a new directed graph with the specified initial node and edge capacities
     */
    public static <N, E> Graph<N, E> createGraph(final int nodeCapacity, final int edgeCapacity)
    {
        return new GraphImpl<N, E>(nodeCapacity, edgeCapacity);
    }

    /**
     * Depth-first search.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param graph graph to search, must not be null
     * @param node node to start from, must not be null and must be contained in
     *    the specified graph
     * @param procedure procedure to run when visiting each node, must not be null
     */
    public static <N, E> void dfs(final Graph<N, E> graph,
                                  final Node<N, E> node,
                                  final UnaryProcedure<Node<N, E>> procedure)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if (!graph.nodes().contains(node))
        {
            throw new IllegalArgumentException("node must be contained in the specified graph");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        Map<Node<N, E>, Boolean> visited = graph.nodeMap(Boolean.FALSE);
        recursiveDfs(node, visited, procedure);
    }

    /**
     * Recursive depth-first search implementation.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param node node
     * @param visited map of visited state keyed by node
     * @param procedure procedure
     */
    private static <N, E> void recursiveDfs(final Node<N, E> node,
                                    final Map<Node<N, E>, Boolean> visited,
                                    final UnaryProcedure<Node<N, E>> procedure)
    {
        if (visited.get(node))
        {
            return;
        }
        for (Edge<N, E> edge : node.outEdges())
        {
            recursiveDfs(edge.target(), visited, procedure);
        }
        procedure.run(node);
        visited.put(node, true);
    }

    /**
     * Breadth-first search.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param graph graph to search, must not be null
     * @param node node to start from, must not be null and must be contained in
     *    the specified graph
     * @param procedure procedure to run when visiting each node, must not be null
     */
    public static <N, E> void bfs(final Graph<N, E> graph,
                                  final Node<N, E> node,
                                  final UnaryProcedure<Node<N, E>> procedure)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if (!graph.nodes().contains(node))
        {
            throw new IllegalArgumentException("node must be contained in the specified graph");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        Map<Node<N, E>, Boolean> visited = graph.nodeMap(Boolean.FALSE);
        Queue<Node<N, E>> queue = new ArrayBlockingQueue<Node<N, E>>(graph.nodeCount());
        queue.offer(node);
        while (!queue.isEmpty())
        {
            Node<N, E> next = queue.poll();
            if (!visited.get(next))
            {
                procedure.run(next);
                visited.put(next, Boolean.TRUE);
            }
            for (Edge<N, E> out : next.outEdges())
            {
                queue.offer(out.target());
            }
        }
    }

    /**
     * Undirected breadth-first search.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param graph graph to search, must not be null
     * @param node node to start from, must not be null and must be contained in
     *    the specified graph
     * @param procedure procedure to run when visiting each node, must not be null
     */
    public static <N, E> void undirectedBfs(final Graph<N, E> graph,
                                            final Node<N, E> node,
                                            final UnaryProcedure<Node<N, E>> procedure)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if (!graph.nodes().contains(node))
        {
            throw new IllegalArgumentException("node must be contained in the specified graph");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        Map<Node<N, E>, Boolean> visited = graph.nodeMap(Boolean.FALSE);
        Queue<Node<N, E>> queue = new ArrayBlockingQueue<Node<N, E>>(graph.nodeCount());
        queue.offer(node);
        while (!queue.isEmpty())
        {
            Node<N, E> next = queue.poll();
            if (!visited.get(next))
            {
                procedure.run(next);
                visited.put(next, Boolean.TRUE);
            }
            for (Edge<N, E> in : next.inEdges())
            {
                Node<N, E> source = in.source();
                if (!visited.get(source))
                {
                    queue.offer(source);
                }
            }
            for (Edge<N, E> out : next.outEdges())
            {
                Node<N, E> target = out.target();
                if (!visited.get(target))
                {
                    queue.offer(target);
                }
            }
        }
    }

    /**
     * Create and return an unmodifiable graph decorator that decorates the specified graph.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param graph graph to decorate, must not be null
     * @return an unmodifiable graph decorator that decorates the specified graph
     */
    public static <N, E> Graph<N, E> unmodifiableGraph(final Graph<N, E> graph)
    {
        return new UnmodifiableGraph<N, E>(graph);
    }

    /**
     * Unmodifiable graph decorator.
     */
    private static final class UnmodifiableGraph<N, E>
        extends AbstractGraphDecorator<N, E>
    {

        /**
         * Create a unmodifiable graph decorator that decorates the specified graph.
         *
         * @param graph graph to decorate, must not be null
         */
        private UnmodifiableGraph(final Graph<N, E> graph)
        {
            super(graph);
        }


        /** {@inheritDoc} */
        public void clear()
        {
            throw new UnsupportedOperationException("clear operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public Node<N, E> createNode(final N value)
        {
            throw new UnsupportedOperationException("createNode operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public void remove(final Node<N, E> node)
        {
            throw new UnsupportedOperationException("remove(Node) operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public Edge<N, E> createEdge(final Node<N, E> source, final Node<N, E> target, final E value)
        {
            throw new UnsupportedOperationException("createEdge operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public void remove(final Edge<N, E> edge)
        {
            throw new UnsupportedOperationException("remove(Edge) operation not supported by this graph");
        }
    }
}