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

import java.util.SortedMap;

import org.dishevelled.observable.event.SortedMapChangeListener;
import org.dishevelled.observable.event.VetoableSortedMapChangeListener;

/**
 * Observable extension to the <code>SortedMap</code> interface.
 *
 * @param <K> sorted map key type
 * @param <V> sorted map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableSortedMap<K,V>
    extends SortedMap<K,V>
{

    /**
     * Add the specified sorted map change listener.
     *
     * @param l sorted map change listener to add
     */
    void addSortedMapChangeListener(SortedMapChangeListener<K,V> l);

    /**
     * Remove the specified sorted map change listener.
     *
     * @param l sorted map change listener to remove
     */
    void removeSortedMapChangeListener(SortedMapChangeListener<K,V> l);

    /**
     * Add the specified vetoable sorted map change listener.
     *
     * @param l vetoable sorted map change listener to add
     */
    void addVetoableSortedMapChangeListener(VetoableSortedMapChangeListener<K,V> l);

    /**
     * Remove the specified vetoable sorted map change listener.
     *
     * @param l vetoable sorted map change listener to remove
     */
    void removeVetoableSortedMapChangeListener(VetoableSortedMapChangeListener<K,V> l);

    /**
     * Return an array of all <code>SortedMapChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>SortedMapChangeListener</code>s, or
     *    an empty array if none are registered
     */
    SortedMapChangeListener<K,V>[] getSortedMapChangeListeners();

    /**
     * Return the number of <code>SortedMapChangeListener</code>s registered
     * to this observable sorted map.
     *
     * @return the number of <code>SortedMapChangeListener</code>s registered
     *    to this observable sorted map
     */
    int getSortedMapChangeListenerCount();

    /**
     * Return an array of all <code>VetoableSortedMapChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableSortedMapChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableSortedMapChangeListener<K,V>[] getVetoableSortedMapChangeListeners();

    /**
     * Return the number of <code>VetoableSortedMapChangeListener</code>s
     * registered to this observable sorted map.
     *
     * @return the number of <code>VetoableSortedMapChangeListener</code>s
     *    registered to this observable sorted map
     */
    int getVetoableSortedMapChangeListenerCount();
}
