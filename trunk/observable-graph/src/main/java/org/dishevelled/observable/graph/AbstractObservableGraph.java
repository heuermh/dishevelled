/*

    dsh-observable-graph  Observable decorators for graph interfaces.
    Copyright (c) 2008 held jointly by the individual authors.

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
package org.dishevelled.observable.graph;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.AbstractGraphDecorator;

import org.dishevelled.observable.graph.event.GraphChangeEvent;
import org.dishevelled.observable.graph.event.GraphChangeListener;
import org.dishevelled.observable.graph.event.GraphChangeVetoException;
import org.dishevelled.observable.graph.event.ObservableGraphChangeSupport;
import org.dishevelled.observable.graph.event.VetoableGraphChangeEvent;
import org.dishevelled.observable.graph.event.VetoableGraphChangeListener;

/**
 * Abstract implementation of an observable graph that decorates an
 * instance of <code>Graph</code>.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableGraph<N, E>
    extends AbstractGraphDecorator<N, E>
    implements ObservableGraph<N, E>
{
    /** Observable graph change support. */
    private final ObservableGraphChangeSupport<N, E> support;


    /**
     * Create a new abstract observable graph that
     * decorates the specified graph.
     *
     * @param graph graph to decorate
     */
    protected AbstractObservableGraph(final Graph<N, E> graph)
    {
        super(graph);
        support = new ObservableGraphChangeSupport<N, E>(this);
    }


    /**
     * Return the <code>ObservableGraphChangeSupport</code>
     * class backing this abstract observable graph.
     *
     * @return the <code>ObservableGraphChangeSupport</code>
     *    class backing this abstract observable graph
     */
    protected final ObservableGraphChangeSupport<N, E> getObservableGraphChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addGraphChangeListener(final GraphChangeListener<N, E> l)
    {
        support.addGraphChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeGraphChangeListener(final GraphChangeListener<N, E> l)
    {
        support.removeGraphChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableGraphChangeListener(final VetoableGraphChangeListener<N, E> l)
    {
        support.addVetoableGraphChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableGraphChangeListener(final VetoableGraphChangeListener<N, E> l)
    {
        support.removeVetoableGraphChangeListener(l);
    }

    /** {@inheritDoc} */
    public final GraphChangeListener<N, E>[] getGraphChangeListeners()
    {
        return support.getGraphChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getGraphChangeListenerCount()
    {
        return support.getGraphChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableGraphChangeListener<N, E>[] getVetoableGraphChangeListeners()
    {
        return support.getVetoableGraphChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableGraphChangeListenerCount()
    {
        return support.getVetoableGraphChangeListenerCount();
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableGraphChangeListener</code>s.
     *
     * @throws GraphChangeVetoException if any of the listeners veto the change
     */
    public void fireGraphWillChange()
        throws GraphChangeVetoException
    {
        support.fireGraphWillChange();
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableGraphChangeListener</code>s.
     *
     * @param e will change event
     * @throws GraphChangeVetoException if any of the listeners veto the change
     */
    public void fireGraphWillChange(final VetoableGraphChangeEvent<N, E> e)
        throws GraphChangeVetoException
    {
        support.fireGraphWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>GraphChangeListener</code>s.
     */
    public void fireGraphChanged()
    {
        support.fireGraphChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>GraphChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireGraphChanged(final GraphChangeEvent<N, E> e)
    {
        support.fireGraphChanged(e);
    }

    /**
     * Fire a node created change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param node newly created node
     */
    protected void fireNodeCreated(final Node<N, E> node)
    {
        support.fireNodeCreated(node);
    }

    /**
     * Fire a node removed change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param node removed node
     */
    protected void fireNodeRemoved(final Node<N, E> node)
    {
        support.fireNodeRemoved(node);
    }

    /**
     * Fire an edge created change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param edge newly created edge
     */
    protected void fireEdgeCreated(final Edge<N, E> edge)
    {
        support.fireEdgeCreated(edge);
    }

    /**
     * Fire an edge removed change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param edge removed edge
     */
    protected void fireEdgeRemoved(final Edge<N, E> edge)
    {
        support.fireEdgeRemoved(edge);
    }

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped graph.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped graph.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>createNode</code> method is about to
     * be called on the wrapped graph with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param value <code>createNode</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preCreateNode(N value);

    /**
     * Notify subclasses the <code>createNode</code> method has just been
     * called on the wrapped graph with the specified parameter.
     *
     * @param value <code>createNode</code> method parameter
     * @param node newly created node
     */
    protected abstract void postCreateNode(N value, Node<N, E> node);

    /**
     * Notify subclasses the <code>remove(Node&lt;N, E&gt;)</code> method is about to
     * be called on the wrapped graph with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param node <code>remove(Node&lt;N, E&gt;)</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Node<N, E> node);

    /**
     * Notify subclasses the <code>remove(Node&lt;N, E&gt;)</code> method has just been
     * called on the wrapped graph with the specified parameter.
     *
     * @param node <code>remove(Node&lt;N, E&gt;)</code> method parameter
     */
    protected abstract void postRemove(Node<N, E> node);

    /**
     * Notify subclasses the <code>createEdge</code> method is about to
     * be called on the wrapped graph with the specified parameters.
     * Return <code>true</code> to proceed with the change.
     *
     * @param source <code>createEdge</code> method parameter
     * @param target <code>createEdge</code> method parameter
     * @param value <code>createEdge</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preCreateEdge(Node<N, E> source, Node<N, E> target, E value);

    /**
     * Notify subclasses the <code>createEdge</code> method has just been
     * called on the wrapped graph with the specified parameters.
     *
     * @param source <code>createEdge</code> method parameter
     * @param target <code>createEdge</code> method parameter
     * @param value <code>createEdge</code> method parameter
     * @param edge newly created edge
     */
    protected abstract void postCreateEdge(Node<N, E> source, Node<N, E> target, E value, Edge<N, E> edge);

    /**
     * Notify subclasses the <code>remove(Edge&lt;N, E&gt;)</code> method is about to
     * be called on the wrapped graph with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param edge <code>remove(Edge&lt;N, E&gt;)</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Edge<N, E> edge);

    /**
     * Notify subclasses the <code>remove(Edge&lt;N, E&gt;)</code> method has just been
     * called on the wrapped graph with the specified parameter.
     *
     * @param edge <code>remove(Edge&lt;N, E&gt;)</code> method parameter
     */
    protected abstract void postRemove(Edge<N, E> edge);

    /** {@inheritDoc} */
    public void clear()
    {
        if (preClear())
        {
            super.clear();
            postClear();
        }
    }

    /** {@inheritDoc} */
    public Node<N, E> createNode(N value)
    {
        if (preCreateNode(value))
        {
            Node<N, E> node = super.createNode(value);
            postCreateNode(value, node);
            return node;
        }
        // TODO:  explicitly document this behaviour
        return null;
    }

    /** {@inheritDoc} */
    public void remove(Node<N, E> node)
    {
        if (preRemove(node))
        {
            super.remove(node);
            postRemove(node);
        }
    }

    /** {@inheritDoc} */
    public Edge<N, E> createEdge(Node<N, E> source, Node<N, E> target, E value)
    {
        if (preCreateEdge(source, target, value))
        {
            Edge<N, E> edge = super.createEdge(source, target, value);
            postCreateEdge(source, target, value, edge);
            return edge;
        }
        // TODO:  explicitly document this behaviour
        return null;
    }

    /** {@inheritDoc} */
    public void remove(Edge<N, E> edge)
    {
        if (preRemove(edge))
        {
            super.remove(edge);
            postRemove(edge);
        }
    }
}