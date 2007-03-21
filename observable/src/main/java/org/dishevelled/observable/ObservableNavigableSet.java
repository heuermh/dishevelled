/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.observable;

import java.util.NavigableSet;

import org.dishevelled.observable.event.NavigableSetChangeListener;
import org.dishevelled.observable.event.VetoableNavigableSetChangeListener;

/**
 * Observable extension to the <code>NavigableSet</code> interface.
 *
 * @param <E> navigable set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableNavigableSet<E>
    extends NavigableSet<E>
{

    /**
     * Add the specified navigable set change listener.
     *
     * @param l navigable set change listener to add
     */
    void addNavigableSetChangeListener(NavigableSetChangeListener<E> l);

    /**
     * Remove the specified navigable set change listener.
     *
     * @param l navigable set change listener to remove
     */
    void removeNavigableSetChangeListener(NavigableSetChangeListener<E> l);

    /**
     * Add the specified vetoable navigable set change listener.
     *
     * @param l vetoable navigable set change listener to add
     */
    void addVetoableNavigableSetChangeListener(VetoableNavigableSetChangeListener<E> l);

    /**
     * Remove the specified vetoable navigable set change listener.
     *
     * @param l vetoable navigable set change listener to remove
     */
    void removeVetoableNavigableSetChangeListener(VetoableNavigableSetChangeListener<E> l);

    /**
     * Return an array of all <code>NavigableSetChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>NavigableSetChangeListener</code>s, or
     *    an empty array if none are registered
     */
    NavigableSetChangeListener<E>[] getNavigableSetChangeListeners();

    /**
     * Return the number of <code>NavigableSetChangeListener</code>s registered
     * to this observable navigable set.
     *
     * @return the number of <code>NavigableSetChangeListener</code>s registered
     *    to this observable navigable set
     */
    int getNavigableSetChangeListenerCount();

    /**
     * Return an array of all <code>VetoableNavigableSetChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableNavigableSetChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableNavigableSetChangeListener<E>[] getVetoableNavigableSetChangeListeners();

    /**
     * Return the number of <code>VetoableNavigableSetChangeListener</code>s
     * registered to this observable navigable set.
     *
     * @return the number of <code>VetoableNavigableSetChangeListener</code>s
     *    registered to this observable navigable set
     */
    int getVetoableNavigableSetChangeListenerCount();
}
