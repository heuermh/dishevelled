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
package org.dishevelled.graph.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

/**
 * Directed graph implementation based on two HashSets.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphImpl<N, E>
    implements Graph<N, E>
{
    /** Set of nodes. */
    private final Set<Node<N, E>> nodes;

    /** Set of edges. */
    private final Set<Edge<N, E>> edges;


    /**
     * Create a new directed graph.
     */
    public GraphImpl()
    {
        nodes = new HashSet<Node<N, E>>();
        edges = new HashSet<Edge<N, E>>();
    }

    /**
     * Create a new directed graph with the specified initial node
     * and edge capacities.
     *
     * @param nodeCapacity initial node capacity, must be <code>&gt;= 0</code>
     * @param edgeCapacity initial edge capacity, must be <code>&gt;= 0</code>
     */
    public GraphImpl(final int nodeCapacity, final int edgeCapacity)
    {
        nodes = new HashSet<Node<N, E>>(nodeCapacity);
        edges = new HashSet<Edge<N, E>>(edgeCapacity);
    }

    /**
     * Create a new directed graph with the same structure and same node
     * and edge values as the specified graph (copy constructor).
     *
     * @param graph graph to copy, must not be null
     */
    public GraphImpl(final Graph<N, E> graph)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        if (graph.isEmpty())
        {
            nodes = new HashSet<Node<N, E>>();
            edges = new HashSet<Edge<N, E>>();
        }
        else
        {
            nodes = new HashSet<Node<N, E>>(graph.nodeCount());
            edges = new HashSet<Edge<N, E>>(graph.edgeCount());
            Map<Node<N, E>, Node<N, E>> nodeMap = graph.nodeMap(null);
            for (Map.Entry<Node<N, E>, Node<N, E>> entry : nodeMap.entrySet())
            {
                Node<N, E> node = createNode(entry.getKey().getValue());
                entry.setValue(node);
            }
            for (Edge<? extends N, ? extends E> edge : graph.edges())
            {
                Node<N, E> source = nodeMap.get(edge.source());
                Node<N, E> target = nodeMap.get(edge.target());
                createEdge(source, target, edge.getValue());
            }
        }
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return (nodes.isEmpty() && edges.isEmpty());
    }

    /** {@inheritDoc} */
    public int nodeCount()
    {
        return nodes.size();
    }

    /** {@inheritDoc} */
    public Set<Node<N, E>> nodes()
    {
        return Collections.unmodifiableSet(nodes);
    }

    /** {@inheritDoc} */
    public Collection<N> nodeValues()
    {
        List<N> values = new ArrayList<N>(nodeCount());
        for (Node<N, E> node : nodes)
        {
            values.add(node.getValue());
        }
        return Collections.unmodifiableList(values);
    }

    /** {@inheritDoc} */
    public <T> Map<Node<N, E>, T> nodeMap(final T defaultValue)
    {
        Map<Node<N, E>, T> map = new HashMap<Node<N, E>, T>(nodeCount());
        for (Node<N, E> node : nodes)
        {
            map.put(node, defaultValue);
        }
        return new MapDecorator<Node<N, E>, T>(map);
    }

    /** {@inheritDoc} */
    public void forEachNode(final UnaryProcedure<Node<N, E>> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (Node<N, E> node : nodes)
        {
            procedure.run(node);
        }
    }

    /** {@inheritDoc} */
    public void forEachNode(final UnaryPredicate<Node<N, E>> predicate,
                            final UnaryProcedure<Node<N, E>> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (Node<N, E> node : nodes)
        {
            if (predicate.test(node))
            {
                procedure.run(node);
            }
        }
    }

    /** {@inheritDoc} */
    public void forEachNodeValue(final UnaryProcedure<? super N> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (N value : nodeValues())
        {
            procedure.run(value);
        }
    }

    /** {@inheritDoc} */
    public void forEachNodeValue(final UnaryPredicate<N> predicate, final UnaryProcedure<N> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (N value : nodeValues())
        {
            if (predicate.test(value))
            {
                procedure.run(value);
            }
        }
    }

    /** {@inheritDoc} */
    public int edgeCount()
    {
        return edges.size();
    }

    /** {@inheritDoc} */
    public Set<Edge<N, E>> edges()
    {
        return Collections.unmodifiableSet(edges);
    }

    /** {@inheritDoc} */
    public Collection<E> edgeValues()
    {
        List<E> values = new ArrayList<E>(edgeCount());
        for (Edge<N, E> edge : edges())
        {
            values.add(edge.getValue());
        }
        return Collections.unmodifiableList(values);
    }

    /** {@inheritDoc} */
    public <T> Map<Edge<N, E>, T> edgeMap(final T defaultValue)
    {
        Map<Edge<N, E>, T> map = new HashMap<Edge<N, E>, T>(edgeCount());
        for (Edge<N, E> edge : edges())
        {
            map.put(edge, defaultValue);
        }
        return new MapDecorator<Edge<N, E>, T>(map);
    }

    /** {@inheritDoc} */
    public void forEachEdge(final UnaryProcedure<Edge<N, E>> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (Edge<N, E> edge : edges)
        {
            procedure.run(edge);
        }
    }

    /** {@inheritDoc} */
    public void forEachEdge(final UnaryPredicate<Edge<N, E>> predicate,
                            final UnaryProcedure<Edge<N, E>> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (Edge<N, E> edge : edges)
        {
            if (predicate.test(edge))
            {
                procedure.run(edge);
            }
        }
    }

    /** {@inheritDoc} */
    public void forEachEdgeValue(final UnaryProcedure<? super E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (E value : edgeValues())
        {
            procedure.run(value);
        }
    }

    /** {@inheritDoc} */
    public void forEachEdgeValue(final UnaryPredicate<E> predicate, final UnaryProcedure<E> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (E value : edgeValues())
        {
            if (predicate.test(value))
            {
                procedure.run(value);
            }
        }
    }

    /** {@inheritDoc} */
    public void clear()
    {
        nodes.clear();
        edges.clear();
    }

    /** {@inheritDoc} */
    public Node<N, E> createNode(final N value)
    {
        Node<N, E> node = new NodeImpl(value);
        nodes.add(node);
        return node;
    }

    /** {@inheritDoc} */
    public void remove(final Node<N, E> node)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if (!nodes.contains(node))
        {
            throw new IllegalArgumentException("node must be contained in this graph");
        }
        nodes.remove(node);
        for (Edge<N, E> edge : edges)
        {
            if (edge.source().equals(node) || edge.target().equals(node))
            {
                remove(edge);
            }
        }
    }

    /** {@inheritDoc} */
    public Edge<N, E> createEdge(final Node<N, E> source, final Node<N, E> target, final E value)
    {
        Edge<N, E> edge = new EdgeImpl(source, target, value);
        edges.add(edge);
        // safe cast since nodes must be created by this graph impl
        ((NodeImpl) source).addOutEdge(edge);
        ((NodeImpl) target).addInEdge(edge);
        return edge;
    }

    /** {@inheritDoc} */
    public void remove(final Edge<N, E> edge)
    {
        if (edge == null)
        {
            throw new IllegalArgumentException("edge must not be null");
        }
        if (!edges.contains(edge))
        {
            throw new IllegalArgumentException("edge must be contained in this graph");
        }
        edges.remove(edge);
        for (Node<N, E> node : nodes)
        {
            if (node.inEdges().contains(edge))
            {
                // safe cast since nodes must be created by this graph impl
                ((NodeImpl) node).removeInEdge(edge);
            }
            if (node.outEdges().contains(edge))
            {
                // safe cast since nodes must be created by this graph impl
                ((NodeImpl) node).removeOutEdge(edge);
            }
        }
    }

    /**
     * Node implementation.
     */
    private final class NodeImpl
        implements Node<N, E>
    {
        /** Value for this node. */
        private N value;

        /** Set of in edges. */
        private final Set<Edge<N, E>> inEdges = new HashSet<Edge<N, E>>();

        /** Set of out edges. */
        private final Set<Edge<N, E>> outEdges = new HashSet<Edge<N, E>>();


        /**
         * Create a new node with the specified value.
         *
         * @param value value
         */
        private NodeImpl(final N value)
        {
            this.value = value;
        }


        /** {@inheritDoc} */
        public N getValue()
        {
            return value;
        }

        /** {@inheritDoc} */
        public void setValue(final N value)
        {
            this.value = value;
        }

        /** {@inheritDoc} */
        public int degree()
        {
            return (inEdges.size() + outEdges.size());
        }

        /** {@inheritDoc} */
        public Set<Edge<N, E>> inEdges()
        {
            return Collections.unmodifiableSet(inEdges);
        }

        /** {@inheritDoc} */
        public Set<Edge<N, E>> outEdges()
        {
            return Collections.unmodifiableSet(outEdges);
        }

        /**
         * Add the specified edge to the set of in edges for this node.
         *
         * @param edge edge to add
         */
        private void addInEdge(final Edge<N, E> edge)
        {
            inEdges.add(edge);
        }

        /**
         * Remove the specified edge from the set of in edges for this node.
         *
         * @param edge edge to remove
         */
        private void removeInEdge(final Edge<N, E> edge)
        {
            inEdges.remove(edge);
        }

        /**
         * Add the specified edge to the set of out edges for this node.
         *
         * @param edge edge to add
         */
        private void addOutEdge(final Edge<N, E> edge)
        {
            outEdges.add(edge);
        }

        /**
         * Remove the specified edge from the set of out edges for this node.
         *
         * @param edge edge to remove
         */
        private void removeOutEdge(final Edge<N, E> edge)
        {
            outEdges.remove(edge);
        }
    }

    /**
     * Edge implementation.
     */
    private final class EdgeImpl
        implements Edge<N, E>
    {
        /** Value for this edge. */
        private E value;

        /** Source node. */
        private final Node<N, E> source;

        /** Target node. */
        private final Node<N, E> target;


        /**
         * Create a new edge with the specified source and target nodes and
         * the specified value.
         *
         * @param source source node, must not be null
         * @param target target node, must not be null
         * @param value value for this edge
         */
        private EdgeImpl(final Node<N, E> source, final Node<N, E> target, final E value)
        {
            if (source == null)
            {
                throw new IllegalArgumentException("source must not be null");
            }
            if (target == null)
            {
                throw new IllegalArgumentException("target must not be null");
            }
            if (!nodes.contains(source))
            {
                throw new IllegalArgumentException("source must be contained in this graph");
            }
            if (!nodes.contains(target))
            {
                throw new IllegalArgumentException("target must be contained in this graph");
            }
            this.value = value;
            this.source = source;
            this.target = target;
        }


        /** {@inheritDoc} */
        public E getValue()
        {
            return value;
        }

        /** {@inheritDoc} */
        public void setValue(final E value)
        {
            this.value = value;
        }

        /** {@inheritDoc} */
        public Node<N, E> source()
        {
            return source;
        }

        /** {@inheritDoc} */
        public Node<N, E> target()
        {
            return target;
        }
    }

    /**
     * Map decorator.
     */
    private static class MapDecorator<K, V>
        extends AbstractMapDecorator<K, V>
    {

        /**
         * Create a new map that decorates the specified map.
         *
         * @param map map to decorate, must not be null
         */
        MapDecorator(final Map<K, V> map)
        {
            super(map);
        }


        /** {@inheritDoc} */
        public Set<K> keySet()
        {
            return Collections.unmodifiableSet(super.keySet());
        }
    }
}