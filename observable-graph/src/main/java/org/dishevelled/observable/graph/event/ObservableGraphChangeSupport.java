/*

    dsh-observable-graph  Observable decorators for graph interfaces.
    Copyright (c) 2008-2009 held jointly by the individual authors.

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

import java.io.Serializable;

import javax.swing.event.EventListenerList;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Node;
import org.dishevelled.observable.graph.ObservableGraph;

/**
 * A support class that can be used via delegation to provide <code>GraphChangeListener</code>
 * and <code>VetoableGraphChangeListener</code> management.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableGraphChangeSupport<N, E>
    implements Serializable
{
    /** Event source. */
    private ObservableGraph<N, E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableGraphChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires graph change and
     * vetoable graph change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableGraphChangeSupport(final ObservableGraph<N, E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of graph change and vetoable graph change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableGraph<N, E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable graph support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable graph support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified graph change listener.
     *
     * @param l graph change listener to add
     */
    public final void addGraphChangeListener(final GraphChangeListener<N, E> l)
    {
        listenerList.add(GraphChangeListener.class, l);
    }

    /**
     * Remove the specified graph change listener.
     *
     * @param l graph change listener to remove
     */
    public final void removeGraphChangeListener(final GraphChangeListener<N, E> l)
    {
        listenerList.remove(GraphChangeListener.class, l);
    }

    /**
     * Add the specified vetoable graph change listener.
     *
     * @param l vetoable graph change listener to add
     */
    public final void addVetoableGraphChangeListener(final VetoableGraphChangeListener<N, E> l)
    {
        listenerList.add(VetoableGraphChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable graph change listener.
     *
     * @param l vetoable graph change listener to remove
     */
    public final void removeVetoableGraphChangeListener(final VetoableGraphChangeListener<N, E> l)
    {
        listenerList.remove(VetoableGraphChangeListener.class, l);
    }

    /**
     * Return an array of all <code>GraphChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>GraphChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final GraphChangeListener<N, E>[] getGraphChangeListeners()
    {
        return (GraphChangeListener<N, E>[]) listenerList.getListeners(GraphChangeListener.class);
    }

    /**
     * Return the number of <code>GraphChangeListener</code>s registered
     * to this observable graph support class.
     *
     * @return the number of <code>GraphChangeListener</code>s registered
     *    to this observable graph support class
     */
    public final int getGraphChangeListenerCount()
    {
        return listenerList.getListenerCount(GraphChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableGraphChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableGraphChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableGraphChangeListener<N, E>[] getVetoableGraphChangeListeners()
    {
        return (VetoableGraphChangeListener<N, E>[])
            listenerList.getListeners(VetoableGraphChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableGraphChangeListener</code>s
     * registered to this observable graph support class.
     *
     * @return the number of <code>VetoableGraphChangeListener</code>s
     *    registered to this observable graph support class
     */
    public final int getVetoableGraphChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableGraphChangeListener.class);
    }

    /**
     * Fire a will clear change event to all registered
     * <code>VetoableGraphChangeListener</code>s.
     *
     * @throws GraphChangeVetoException if any of the listeners veto the change
     */
    public void fireWillClear()
        throws GraphChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableGraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableGraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableGraphChangeEvent<N, E>(source);
                }
                ((VetoableGraphChangeListener<N, E>) listeners[i + 1]).willClear(e);
            }
        }
    }

    /**
     * Fire a cleared change event to all registered <code>GraphChangeListener</code>s.
     */
    public void fireCleared()
    {
        Object[] listeners = listenerList.getListenerList();
        GraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == GraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new GraphChangeEvent<N, E>(source);
                }
                ((GraphChangeListener<N, E>) listeners[i + 1]).cleared(e);
            }
        }
    }

    /**
     * Fire a node created change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param node newly created node
     */
    public void fireNodeCreated(final Node<N, E> node)
    {
        Object[] listeners = listenerList.getListenerList();
        GraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == GraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new GraphChangeEvent<N, E>(source, node);
                }
                ((GraphChangeListener<N, E>) listeners[i + 1]).nodeCreated(e);
            }
        }
    }

    /**
     * Fire a node removed change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param node removed node
     */
    public void fireNodeRemoved(final Node<N, E> node)
    {
        Object[] listeners = listenerList.getListenerList();
        GraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == GraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new GraphChangeEvent<N, E>(source, node);
                }
                ((GraphChangeListener<N, E>) listeners[i + 1]).nodeRemoved(e);
            }
        }
    }

    /**
     * Fire an edge created change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param edge newly created edge
     */
    public void fireEdgeCreated(final Edge<N, E> edge)
    {
        Object[] listeners = listenerList.getListenerList();
        GraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == GraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new GraphChangeEvent<N, E>(source, edge);
                }
                ((GraphChangeListener<N, E>) listeners[i + 1]).edgeCreated(e);
            }
        }
    }

    /**
     * Fire an edge removed change event to all registered <code>GraphChangeListener</code>s.
     *
     * @param edge removed edge
     */
    public void fireEdgeRemoved(final Edge<N, E> edge)
    {
        Object[] listeners = listenerList.getListenerList();
        GraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == GraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new GraphChangeEvent<N, E>(source, edge);
                }
                ((GraphChangeListener<N, E>) listeners[i + 1]).edgeRemoved(e);
            }
        }
    }

    /**
     * Fire a will create node create event to all registered
     * <code>VetoableGraphChangeListener</code>s.
     *
     * @param nodeValue node value for an about to be created node
     * @throws GraphChangeVetoException if any of the listeners veto the change
     */
    public void fireWillCreateNode(final N nodeValue)
        throws GraphChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableGraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableGraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableGraphChangeEvent<N, E>(source, nodeValue);
                }
                ((VetoableGraphChangeListener<N, E>) listeners[i + 1]).willCreateNode(e);
            }
        }
    }

    /**
     * Fire a will create edge change event to all registered
     * <code>VetoableGraphChangeListener</code>s.
     *
     * @param sourceNode source node for an about to be created edge
     * @param targetNode target node for an about to be created edge
     * @param edgeValue edge value for an about to be created edge
     * @throws GraphChangeVetoException if any of the listeners veto the change
     */
    public void fireWillCreateEdge(final Node<N, E> sourceNode,
            final Node<N, E> targetNode,
            final E edgeValue)
        throws GraphChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableGraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableGraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableGraphChangeEvent<N, E>(source, sourceNode, targetNode, edgeValue);
                }
                ((VetoableGraphChangeListener<N, E>) listeners[i + 1]).willCreateEdge(e);
            }
        }
    }

    /**
     * Fire a will remove node change event to all registered
     * <code>VetoableGraphChangeListener</code>s.
     *
     * @param node node about to be removed
     * @throws GraphChangeVetoException if any of the listeners veto the change
     */
    public void fireWillRemoveNode(final Node<N, E> node)
        throws GraphChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableGraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableGraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableGraphChangeEvent<N, E>(source, node);
                }
                ((VetoableGraphChangeListener<N, E>) listeners[i + 1]).willRemoveNode(e);
            }
        }
    }

    /**
     * Fire a will remove edge change event to all registered
     * <code>VetoableGraphChangeListener</code>s.
     *
     * @param edge edge about to be removed
     * @throws GraphChangeVetoException if any of the listeners veto the change
     */
    public void fireWillRemoveEdge(final Edge<N, E> edge)
        throws GraphChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableGraphChangeEvent<N, E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableGraphChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableGraphChangeEvent<N, E>(source, edge);
                }
                ((VetoableGraphChangeListener<N, E>) listeners[i + 1]).willRemoveEdge(e);
            }
        }
    }
}