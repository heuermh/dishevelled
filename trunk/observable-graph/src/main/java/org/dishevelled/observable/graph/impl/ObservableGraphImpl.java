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
package org.dishevelled.observable.graph.impl;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.observable.graph.AbstractObservableGraph;

import org.dishevelled.observable.graph.event.GraphChangeVetoException;
import org.dishevelled.observable.graph.event.VetoableGraphChangeEvent;

/**
 * Observable graph decorator that fires vetoable graph change events
 * in <code>preXxx</code> methods and graph change events in
 * <code>postXxx</code> methods.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ObservableGraphImpl<N, E>
    extends AbstractObservableGraph<N, E>
{

    /**
     * Create a new observable decorator for the specified graph.
     *
     * @param graph graph to decorate, must not be null
     */
    public ObservableGraphImpl(final Graph<N, E> graph)
    {
        super(graph);
    }


    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireWillClear();
            return true;
        }
        catch (GraphChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireCleared();
    }

    /** {@inheritDoc} */
    protected boolean preCreateNode(final N value)
    {
        try
        {
            fireWillCreateNode(value);
            return true;
        }
        catch (GraphChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postCreateNode(final N value, final Node<N, E> node)
    {
        fireNodeCreated(node);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Node<N, E> node)
    {
        try
        {
            fireWillRemoveNode(node);
            return true;
        }
        catch (GraphChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Node<N, E> node)
    {
        fireNodeRemoved(node);
    }

    /** {@inheritDoc} */
    protected boolean preCreateEdge(final Node<N, E> source,
                                    final Node<N, E> target,
                                    final E value)
    {
        try
        {
            fireWillCreateEdge(source, target, value);
            return true;
        }
        catch (GraphChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postCreateEdge(final Node<N, E> source,
                                  final Node<N, E> target,
                                  final E value,
                                  final Edge<N, E> edge)
    {
        fireEdgeCreated(edge);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Edge<N, E> edge)
    {
        try
        {
            fireWillRemoveEdge(edge);
            return true;
        }
        catch (GraphChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Edge<N, E> edge)
    {
        fireEdgeRemoved(edge);
    }
}