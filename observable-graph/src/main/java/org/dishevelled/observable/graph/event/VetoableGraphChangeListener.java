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

import java.util.EventListener;

/**
 * A listener that receives notification of vetoable changes about to be made to an observable graph.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface VetoableGraphChangeListener<N, E>
    extends EventListener
{

    /**
     * Notify this listener that a vetoable clear change is about to be made to the observable graph.
     *
     * @param e graph change event
     * @throws GraphChangeVetoException if this listener wishes the change about to be made
     *    to be rolled back
     */
    void willClear(VetoableGraphChangeEvent<N, E> e)
        throws GraphChangeVetoException;

    /**
     * Notify this listener that a vetoable create node change is about to be made to the observable graph.
     *
     * @param e graph change event
     * @throws GraphChangeVetoException if this listener wishes the change about to be made
     *    to be rolled back
     */
    void willCreateNode(VetoableGraphChangeEvent<N, E> e)
        throws GraphChangeVetoException;

    /**
     * Notify this listener that a vetoable create edge change is about to be made to the observable graph.
     *
     * @param e graph change event
     * @throws GraphChangeVetoException if this listener wishes the change about to be made
     *    to be rolled back
     */
    void willCreateEdge(VetoableGraphChangeEvent<N, E> e)
        throws GraphChangeVetoException;

    /**
     * Notify this listener that a vetoable remove node change is about to be made to the observable graph.
     *
     * @param e graph change event
     * @throws GraphChangeVetoException if this listener wishes the change about to be made
     *    to be rolled back
     */
    void willRemoveNode(VetoableGraphChangeEvent<N, E> e)
        throws GraphChangeVetoException;

    /**
     * Notify this listener that a vetoable remove edge change is about to be made to the observable graph.
     *
     * @param e graph change event
     * @throws GraphChangeVetoException if this listener wishes the change about to be made
     *    to be rolled back
     */
    void willRemoveEdge(VetoableGraphChangeEvent<N, E> e)
        throws GraphChangeVetoException;
}