/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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

import java.util.List;

import org.dishevelled.observable.event.ListChangeListener;
import org.dishevelled.observable.event.VetoableListChangeListener;

/**
 * Observable extension to the <code>List</code> interface.
 *
 * @param <E> list element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableList<E>
    extends List<E>
{

    /**
     * Add the specified list change listener.
     *
     * @param l list change listener to add
     */
    void addListChangeListener(ListChangeListener<E> l);

    /**
     * Remove the specified list change listener.
     *
     * @param l list change listener to remove
     */
    void removeListChangeListener(ListChangeListener<E> l);

    /**
     * Add the specified vetoable list change listener.
     *
     * @param l vetoable list change listener to add
     */
    void addVetoableListChangeListener(VetoableListChangeListener<E> l);

    /**
     * Remove the specified vetoable list change listener.
     *
     * @param l vetoable list change listener to remove
     */
    void removeVetoableListChangeListener(VetoableListChangeListener<E> l);

    /**
     * Return an array of all <code>ListChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>ListChangeListener</code>s, or
     *    an empty array if none are registered
     */
    ListChangeListener<E>[] getListChangeListeners();

    /**
     * Return the number of <code>ListChangeListener</code>s registered
     * to this observable list.
     *
     * @return the number of <code>ListChangeListener</code>s registered
     *    to this observable list
     */
    int getListChangeListenerCount();

    /**
     * Return an array of all <code>VetoableListChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableListChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableListChangeListener<E>[] getVetoableListChangeListeners();

    /**
     * Return the number of <code>VetoableListChangeListener</code>s
     * registered to this observable list.
     *
     * @return the number of <code>VetoableListChangeListener</code>s
     *    registered to this observable list
     */
    int getVetoableListChangeListenerCount();
}
