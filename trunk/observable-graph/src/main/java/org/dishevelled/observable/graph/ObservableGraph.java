/*

    dsh-observable-graph  Observable decorators for graph interfaces.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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

import org.dishevelled.graph.Graph;

// introduces a circular package dependency, not sure how to break this
import org.dishevelled.observable.graph.event.GraphChangeListener;
import org.dishevelled.observable.graph.event.VetoableGraphChangeListener;

/**
 * Observable extension to the <code>Graph</code> interface.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableGraph<N, E>
    extends Graph<N, E>
{

    /**
     * Add the specified graph change listener.
     *
     * @param l graph change listener to add
     */
    void addGraphChangeListener(GraphChangeListener<N, E> l);

    /**
     * Remove the specified graph change listener.
     *
     * @param l graph change listener to remove
     */
    void removeGraphChangeListener(GraphChangeListener<N, E> l);

    /**
     * Add the specified vetoable graph change listener.
     *
     * @param l vetoable graph change listener to add
     */
    void addVetoableGraphChangeListener(VetoableGraphChangeListener<N, E> l);

    /**
     * Remove the specified vetoable graph change listener.
     *
     * @param l vetoable graph change listener to remove
     */
    void removeVetoableGraphChangeListener(VetoableGraphChangeListener<N, E> l);

    /**
     * Return an array of all <code>GraphChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>GraphChangeListener</code>s, or
     *    an empty array if none are registered
     */
    GraphChangeListener<N, E>[] getGraphChangeListeners();

    /**
     * Return the number of <code>GraphChangeListener</code>s registered
     * to this observable graph.
     *
     * @return the number of <code>GraphChangeListener</code>s registered
     *    to this observable graph
     */
    int getGraphChangeListenerCount();

    /**
     * Return an array of all <code>VetoableGraphChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableGraphChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableGraphChangeListener<N, E>[] getVetoableGraphChangeListeners();

    /**
     * Return the number of <code>VetoableGraphChangeListener</code>s
     * registered to this observable graph.
     *
     * @return the number of <code>VetoableGraphChangeListener</code>s
     *    registered to this observable graph
     */
    int getVetoableGraphChangeListenerCount();
}