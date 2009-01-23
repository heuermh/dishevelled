/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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

import java.util.Map;

import org.dishevelled.observable.event.MapChangeListener;
import org.dishevelled.observable.event.VetoableMapChangeListener;

/**
 * Observable extension to the <code>Map</code> interface.
 *
 * @param <K> map key type
 * @param <V> map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableMap<K, V>
    extends Map<K, V>
{

    /**
     * Add the specified map change listener.
     *
     * @param l map change listener to add
     */
    void addMapChangeListener(MapChangeListener<K, V> l);

    /**
     * Remove the specified map change listener.
     *
     * @param l map change listener to remove
     */
    void removeMapChangeListener(MapChangeListener<K, V> l);

    /**
     * Add the specified vetoable map change listener.
     *
     * @param l vetoable map change listener to add
     */
    void addVetoableMapChangeListener(VetoableMapChangeListener<K, V> l);

    /**
     * Remove the specified vetoable map change listener.
     *
     * @param l vetoable map change listener to remove
     */
    void removeVetoableMapChangeListener(VetoableMapChangeListener<K, V> l);

    /**
     * Return an array of all <code>MapChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>MapChangeListener</code>s, or
     *    an empty array if none are registered
     */
    MapChangeListener<K, V>[] getMapChangeListeners();

    /**
     * Return the number of <code>MapChangeListener</code>s registered
     * to this observable map.
     *
     * @return the number of <code>MapChangeListener</code>s registered
     *    to this observable map
     */
    int getMapChangeListenerCount();

    /**
     * Return an array of all <code>VetoableMapChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableMapChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableMapChangeListener<K, V>[] getVetoableMapChangeListeners();

    /**
     * Return the number of <code>VetoableMapChangeListener</code>s
     * registered to this observable map.
     *
     * @return the number of <code>VetoableMapChangeListener</code>s
     *    registered to this observable map
     */
    int getVetoableMapChangeListenerCount();
}
