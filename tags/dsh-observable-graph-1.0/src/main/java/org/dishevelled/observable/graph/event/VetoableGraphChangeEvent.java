/*

    dsh-observable-graph  Observable decorators for graph interfaces.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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
package org.dishevelled.observable.graph.event;

import java.util.EventObject;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Node;

import org.dishevelled.observable.graph.ObservableGraph;

/**
 * An event object representing a vetoable change about to be made to an observable graph.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class VetoableGraphChangeEvent<N, E>
    extends EventObject
{
    /** Node about to be removed, if any. */
    private final Node<N, E> node;

    /** Edge about to be removed if any. */
    private final Edge<N, E> edge;

    /** Node value for an about to be created node, if any. */
    private final N nodeValue;

    /** Source node for an about to be created edge, if any. */
    private final Node<N, E> sourceNode;

    /** Target node for an about to be created edge, if any. */
    private final Node<N, E> targetNode;

    /** Edge value for an about to be created edge, if any. */
    private final E edgeValue;


    /**
     * Create a new vetoable graph change event with the
     * specified observable graph as the event source.
     *
     * @param source source of the event, must not be null
     */
    public VetoableGraphChangeEvent(final ObservableGraph<N, E> source)
    {
        super(source);
        node = null;
        edge = null;
        nodeValue = null;
        sourceNode = null;
        targetNode = null;
        edgeValue = null;
    }

    /**
     * Create a new vetoable graph change event with the
     * specified observable graph as the event source and the specified
     * about to be removed node.
     *
     * @param source source of the event, must not be null
     * @param node about to be removed node
     */
    public VetoableGraphChangeEvent(final ObservableGraph<N, E> source, final Node<N, E> node)
    {
        super(source);
        this.node = node;
        edge = null;
        nodeValue = null;
        sourceNode = null;
        targetNode = null;
        edgeValue = null;
    }

    /**
     * Create a new vetoable graph change event with the
     * specified observable graph as the event source and the
     * specified about to be removed edge.
     *
     * @param source source of the event, must not be null
     * @param edge about to be removed edge
     */
    public VetoableGraphChangeEvent(final ObservableGraph<N, E> source, final Edge<N, E> edge)
    {
        super(source);
        node = null;
        this.edge = edge;
        nodeValue = null;
        sourceNode = null;
        targetNode = null;
        edgeValue = null;
    }

    /**
     * Create a new vetoable graph change event with the
     * specified observable graph as the event source and the specified
     * node value for an about to be created node.
     *
     * @param source source of the event, must not be null
     * @param nodeValue node value for an about to be created node
     */
    public VetoableGraphChangeEvent(final ObservableGraph<N, E> source, final N nodeValue)
    {
        super(source);
        node = null;
        edge = null;
        this.nodeValue = nodeValue;
        sourceNode = null;
        targetNode = null;
        edgeValue = null;
    }

    /**
     * Create a new vetoable graph change event with the
     * specified observable graph as the event source and specified
     * source node, target node, and edge value for an about to be
     * created edge.
     *
     * @param source source of the event, must not be null
     * @param sourceNode source node for an about to be created edge
     * @param targetNode target node for an about to be created edge
     * @param edgeValue edge value for an about to be created edge
     */
    public VetoableGraphChangeEvent(final ObservableGraph<N, E> source,
            final Node<N, E> sourceNode,
            final Node<N, E> targetNode,
            final E edgeValue)
    {
        super(source);
        node = null;
        edge = null;
        nodeValue = null;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.edgeValue = edgeValue;
    }


    /**
     * Return the source of this vetoable graph change event as an
     * <code>ObservableGraph</code>.
     *
     * @return the source of this vetoable graph change event as an
     *    <code>ObservableGraph</code>
     */
    public final ObservableGraph<N, E> getObservableGraph()
    {
        return (ObservableGraph<N, E>) super.getSource();
    }

    /**
     * Return the about to be removed node, if any.
     *
     * @return the about to be removed node, if any
     */
    public final Node<N, E> getNode()
    {
        return node;
    }

    /**
     * Return the about to be removed edge, if any.
     *
     * @return the about to be removed edge, if any
     */
    public final Edge<N, E> getEdge()
    {
        return edge;
    }

    /**
     * Return the node value for the about to be created node, if any.
     *
     * @return the node value for the about to be created node, if any
     */
    public final N getNodeValue()
    {
        return nodeValue;
    }

    /**
     * Return the source node for the about to be created edge, if any.
     *
     * @return the source node for the about to be created edge, if any
     */
    public final Node<N, E> getSourceNode()
    {
        return sourceNode;
    }

    /**
     * Return the target node for the about to be created edge, if any.
     *
     * @return the target node for the about to be created edge, if any
     */
    public final Node<N, E> getTargetNode()
    {
        return targetNode;
    }

    /**
     * Return the edge value for the about to be created edge, if any.
     *
     * @return the edge value for the about to be created edge, if any
     */
    public final E getEdgeValue()
    {
        return edgeValue;
    }
}