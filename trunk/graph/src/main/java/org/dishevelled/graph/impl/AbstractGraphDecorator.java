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
package org.dishevelled.graph.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

/**
 * Abstract graph that decorates an instance of <code>Graph</code>.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractGraphDecorator<N, E>
    implements Graph<N, E>
{
    /** Graph this decorator decorates. */
    private final Graph<N, E> graph;


    /**
     * Create a new abstract graph that decorates the specified graph.
     *
     * @param graph graph to decorate, must not be null
     */
    protected AbstractGraphDecorator(final Graph<N, E> graph)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        this.graph = graph;
    }


    /**
     * Return a reference to the graph this decorator decorates.
     *
     * @return a reference to the graph this decorator decorates
     */
    protected final Graph<N, E> getGraph()
    {
        return graph;
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return graph.isEmpty();
    }

    /** {@inheritDoc} */
    public int nodeCount()
    {
        return graph.nodeCount();
    }

    /** {@inheritDoc} */
    public Set<Node<N, E>> nodes()
    {
        return graph.nodes();
    }

    /** {@inheritDoc} */
    public Collection<N> nodeValues()
    {
        return graph.nodeValues();
    }

    /** {@inheritDoc} */
    public <T> Map<Node<N, E>, T> nodeMap(final T defaultValue)
    {
        return graph.nodeMap(defaultValue);
    }

    /** {@inheritDoc} */
    public void forEachNode(final UnaryProcedure<Node<N, E>> procedure)
    {
        graph.forEachNode(procedure);
    }

    /** {@inheritDoc} */
    public void forEachNode(final UnaryPredicate<Node<N, E>> predicate,
                            final UnaryProcedure<Node<N, E>> procedure)
    {
        graph.forEachNode(predicate, procedure);
    }

    /** {@inheritDoc} */
    public void forEachNodeValue(final UnaryProcedure<? super N> procedure)
    {
        graph.forEachNodeValue(procedure);
    }

    /** {@inheritDoc} */
    public void forEachNodeValue(final UnaryPredicate<N> predicate, final UnaryProcedure<N> procedure)
    {
        graph.forEachNodeValue(predicate, procedure);
    }

    /** {@inheritDoc} */
    public int edgeCount()
    {
        return graph.edgeCount();
    }

    /** {@inheritDoc} */
    public Set<Edge<N, E>> edges()
    {
        return graph.edges();
    }

    /** {@inheritDoc} */
    public Collection<E> edgeValues()
    {
        return graph.edgeValues();
    }

    /** {@inheritDoc} */
    public <T> Map<Edge<N, E>, T> edgeMap(final T defaultValue)
    {
        return graph.edgeMap(defaultValue);
    }

    /** {@inheritDoc} */
    public void forEachEdge(final UnaryProcedure<Edge<N, E>> procedure)
    {
        graph.forEachEdge(procedure);
    }

    /** {@inheritDoc} */
    public void forEachEdge(final UnaryPredicate<Edge<N, E>> predicate,
                            final UnaryProcedure<Edge<N, E>> procedure)
    {
        graph.forEachEdge(predicate, procedure);
    }

    /** {@inheritDoc} */
    public void forEachEdgeValue(final UnaryProcedure<? super E> procedure)
    {
        graph.forEachEdgeValue(procedure);
    }

    /** {@inheritDoc} */
    public void forEachEdgeValue(final UnaryPredicate<E> predicate, final UnaryProcedure<E> procedure)
    {
        graph.forEachEdgeValue(predicate, procedure);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        graph.clear();
    }

    /** {@inheritDoc} */
    public Node<N, E> createNode(final N value)
    {
        return graph.createNode(value);
    }

    /** {@inheritDoc} */
    public void remove(final Node<N, E> node)
    {
        graph.remove(node);
    }

    /** {@inheritDoc} */
    public Edge<N, E> createEdge(final Node<N, E> source, final Node<N, E> target, final E value)
    {
        return graph.createEdge(source, target, value);
    }

    /** {@inheritDoc} */
    public void remove(final Edge<N, E> edge)
    {
        graph.remove(edge);
    }
}