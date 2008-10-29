/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
package org.dishevelled.observable;

import java.util.Queue;

import org.dishevelled.observable.event.QueueChangeListener;
import org.dishevelled.observable.event.VetoableQueueChangeListener;

/**
 * Observable extension to the <code>Queue</code> interface.
 *
 * @param <E> queue element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableQueue<E>
    extends Queue<E>
{

    /**
     * Add the specified queue change listener.
     *
     * @param l queue change listener to add
     */
    void addQueueChangeListener(QueueChangeListener<E> l);

    /**
     * Remove the specified queue change listener.
     *
     * @param l queue change listener to remove
     */
    void removeQueueChangeListener(QueueChangeListener<E> l);

    /**
     * Add the specified vetoable queue change listener.
     *
     * @param l vetoable queue change listener to add
     */
    void addVetoableQueueChangeListener(VetoableQueueChangeListener<E> l);

    /**
     * Remove the specified vetoable queue change listener.
     *
     * @param l vetoable queue change listener to remove
     */
    void removeVetoableQueueChangeListener(VetoableQueueChangeListener<E> l);

    /**
     * Return an array of all <code>QueueChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>QueueChangeListener</code>s, or
     *    an empty array if none are registered
     */
    QueueChangeListener<E>[] getQueueChangeListeners();

    /**
     * Return the number of <code>QueueChangeListener</code>s registered
     * to this observable queue.
     *
     * @return the number of <code>QueueChangeListener</code>s registered
     *    to this observable queue
     */
    int getQueueChangeListenerCount();

    /**
     * Return an array of all <code>VetoableQueueChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableQueueChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableQueueChangeListener<E>[] getVetoableQueueChangeListeners();

    /**
     * Return the number of <code>VetoableQueueChangeListener</code>s
     * registered to this observable queue.
     *
     * @return the number of <code>VetoableQueueChangeListener</code>s
     *    registered to this observable queue
     */
    int getVetoableQueueChangeListenerCount();
}
