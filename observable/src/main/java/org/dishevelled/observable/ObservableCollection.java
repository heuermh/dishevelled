/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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

import java.util.Collection;

import org.dishevelled.observable.event.CollectionChangeListener;
import org.dishevelled.observable.event.VetoableCollectionChangeListener;

/**
 * Observable extension to the <code>Collection</code> interface.
 *
 * @param <E> collection element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableCollection<E>
    extends Collection<E>
{

    /**
     * Add the specified collection change listener.
     *
     * @param l collection change listener to add
     */
    void addCollectionChangeListener(CollectionChangeListener<E> l);

    /**
     * Remove the specified collection change listener.
     *
     * @param l collection change listener to remove
     */
    void removeCollectionChangeListener(CollectionChangeListener<E> l);

    /**
     * Add the specified vetoable collection change listener.
     *
     * @param l vetoable collection change listener to add
     */
    void addVetoableCollectionChangeListener(VetoableCollectionChangeListener<E> l);

    /**
     * Remove the specified vetoable collection change listener.
     *
     * @param l vetoable collection change listener to remove
     */
    void removeVetoableCollectionChangeListener(VetoableCollectionChangeListener<E> l);

    /**
     * Return an array of all <code>CollectionChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>CollectionChangeListener</code>s, or
     *    an empty array if none are registered
     */
    CollectionChangeListener<E>[] getCollectionChangeListeners();

    /**
     * Return the number of <code>CollectionChangeListener</code>s registered
     * to this observable collection.
     *
     * @return the number of <code>CollectionChangeListener</code>s registered
     *    to this observable collection
     */
    int getCollectionChangeListenerCount();

    /**
     * Return an array of all <code>VetoableCollectionChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableCollectionChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableCollectionChangeListener<E>[] getVetoableCollectionChangeListeners();

    /**
     * Return the number of <code>VetoableCollectionChangeListener</code>s
     * registered to this observable collection.
     *
     * @return the number of <code>VetoableCollectionChangeListener</code>s
     *    registered to this observable collection
     */
    int getVetoableCollectionChangeListenerCount();
}
