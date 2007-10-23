/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2007 held jointly by the individual authors.

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

import java.util.SortedSet;

import org.dishevelled.observable.event.SortedSetChangeListener;
import org.dishevelled.observable.event.VetoableSortedSetChangeListener;

/**
 * Observable extension to the <code>SortedSet</code> interface.
 *
 * @param <E> sorted set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableSortedSet<E>
    extends SortedSet<E>
{

    /**
     * Add the specified sorted set change listener.
     *
     * @param l sorted set change listener to add
     */
    void addSortedSetChangeListener(SortedSetChangeListener<E> l);

    /**
     * Remove the specified sorted set change listener.
     *
     * @param l sorted set change listener to remove
     */
    void removeSortedSetChangeListener(SortedSetChangeListener<E> l);

    /**
     * Add the specified vetoable sorted set change listener.
     *
     * @param l vetoable sorted set change listener to add
     */
    void addVetoableSortedSetChangeListener(VetoableSortedSetChangeListener<E> l);

    /**
     * Remove the specified vetoable sorted set change listener.
     *
     * @param l vetoable sorted set change listener to remove
     */
    void removeVetoableSortedSetChangeListener(VetoableSortedSetChangeListener<E> l);

    /**
     * Return an array of all <code>SortedSetChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>SortedSetChangeListener</code>s, or
     *    an empty array if none are registered
     */
    SortedSetChangeListener<E>[] getSortedSetChangeListeners();

    /**
     * Return the number of <code>SortedSetChangeListener</code>s registered
     * to this observable sorted set.
     *
     * @return the number of <code>SortedSetChangeListener</code>s registered
     *    to this observable sorted set
     */
    int getSortedSetChangeListenerCount();

    /**
     * Return an array of all <code>VetoableSortedSetChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableSortedSetChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableSortedSetChangeListener<E>[] getVetoableSortedSetChangeListeners();

    /**
     * Return the number of <code>VetoableSortedSetChangeListener</code>s
     * registered to this observable sorted set.
     *
     * @return the number of <code>VetoableSortedSetChangeListener</code>s
     *    registered to this observable sorted set
     */
    int getVetoableSortedSetChangeListenerCount();
}
