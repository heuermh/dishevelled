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

import java.util.NavigableMap;

import org.dishevelled.observable.event.NavigableMapChangeListener;
import org.dishevelled.observable.event.VetoableNavigableMapChangeListener;

/**
 * Observable extension to the <code>NavigableMap</code> interface.
 *
 * @param <K> navigable map key type
 * @param <V> navigable map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableNavigableMap<K,V>
    extends NavigableMap<K,V>
{

    /**
     * Add the specified navigable map change listener.
     *
     * @param l navigable map change listener to add
     */
    void addNavigableMapChangeListener(NavigableMapChangeListener<K,V> l);

    /**
     * Remove the specified navigable map change listener.
     *
     * @param l navigable map change listener to remove
     */
    void removeNavigableMapChangeListener(NavigableMapChangeListener<K,V> l);

    /**
     * Add the specified vetoable navigable map change listener.
     *
     * @param l vetoable navigable map change listener to add
     */
    void addVetoableNavigableMapChangeListener(VetoableNavigableMapChangeListener<K,V> l);

    /**
     * Remove the specified vetoable navigable map change listener.
     *
     * @param l vetoable navigable map change listener to remove
     */
    void removeVetoableNavigableMapChangeListener(VetoableNavigableMapChangeListener<K,V> l);

    /**
     * Return an array of all <code>NavigableMapChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>NavigableMapChangeListener</code>s, or
     *    an empty array if none are registered
     */
    NavigableMapChangeListener<K,V>[] getNavigableMapChangeListeners();

    /**
     * Return the number of <code>NavigableMapChangeListener</code>s registered
     * to this observable navigable map.
     *
     * @return the number of <code>NavigableMapChangeListener</code>s registered
     *    to this observable navigable map
     */
    int getNavigableMapChangeListenerCount();

    /**
     * Return an array of all <code>VetoableNavigableMapChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableNavigableMapChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableNavigableMapChangeListener<K,V>[] getVetoableNavigableMapChangeListeners();

    /**
     * Return the number of <code>VetoableNavigableMapChangeListener</code>s
     * registered to this observable navigable map.
     *
     * @return the number of <code>VetoableNavigableMapChangeListener</code>s
     *    registered to this observable navigable map
     */
    int getVetoableNavigableMapChangeListenerCount();
}
