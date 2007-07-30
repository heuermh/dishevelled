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
package org.dishevelled.observable.simple;

import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import org.dishevelled.observable.ObservableCollection;
import org.dishevelled.observable.ObservableDeque;
import org.dishevelled.observable.ObservableList;
import org.dishevelled.observable.ObservableMap;
import org.dishevelled.observable.ObservableNavigableMap;
import org.dishevelled.observable.ObservableNavigableSet;
import org.dishevelled.observable.ObservableQueue;
import org.dishevelled.observable.ObservableSet;
import org.dishevelled.observable.ObservableSortedMap;
import org.dishevelled.observable.ObservableSortedSet;

/**
 * Utility methods for creating simple observable collection and map
 * interface decorators.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SimpleObservableUtils
{

    /**
     * Private constructor.
     */
    private SimpleObservableUtils()
    {
        // empty
    }


    /**
     * Create and return a new simple observable decorator for the specified collection.
     *
     * @param <T> collection element type
     * @param collection collection to decorate, must not be null
     * @return a new simple observable decorator for the specified collection
     */
    public static <T> ObservableCollection<T> observableCollection(final Collection<T> collection)
    {
        return new SimpleObservableCollection<T>(collection);
    }

    /**
     * Create and return a new simple observable decorator for the specified deque.
     *
     * @param <T> deque element type
     * @param deque deque to decorate, must not be null
     * @return a new simple observable decorator for the specified deque
     */
    public static <T> ObservableDeque<T> observableDeque(final Deque<T> deque)
    {
        return new SimpleObservableDeque<T>(deque);
    }

    /**
     * Create and return a new simple observable decorator for the specified list.
     *
     * @param <T> list element type
     * @param list list to decorate, must not be null
     * @return a new simple observable decorator for the specified list
     */
    public static <T> ObservableList<T> observableList(final List<T> list)
    {
        return new SimpleObservableList<T>(list);
    }

    /**
     * Create and return a new simple observable decorator for the specified map.
     *
     * @param <K> map key type
     * @param <V> map value type
     * @param map map to decorate, must not be null
     * @return a new simple observable decorator for the specified map
     */
    public static <K,V> ObservableMap<K,V> observableMap(final Map<K,V> map)
    {
        return new SimpleObservableMap<K,V>(map);
    }

    /**
     * Create and return a new simple observable decorator for the specified navigable map.
     *
     * @param <K> navigable map key type
     * @param <V> navigable map value type
     * @param navigableMap navigable map to decorate, must not be null
     * @return a new simple observable decorator for the specified navigable map
     */
    public static <K,V> ObservableNavigableMap<K,V> observableNavigableMap(final NavigableMap<K,V> navigableMap)
    {
        return new SimpleObservableNavigableMap<K,V>(navigableMap);
    }

    /**
     * Create and return a new simple observable decorator for the specified navigable set.
     *
     * @param <T> navigable set element type
     * @param navigableSet navigable set to decorate, must not be null
     * @return a new simple observable decorator for the specified navigable set
     */
    public static <T> ObservableNavigableSet<T> observableNavigableSet(final NavigableSet<T> navigableSet)
    {
        return new SimpleObservableNavigableSet<T>(navigableSet);
    }

    /**
     * Create and return a new simple observable decorator for the specified queue.
     *
     * @param <T> queue element type
     * @param queue queue to decorate, must not be null
     * @return a new simple observable decorator for the specified queue
     */
    public static <T> ObservableQueue<T> observableQueue(final Queue<T> queue)
    {
        return new SimpleObservableQueue<T>(queue);
    }

    /**
     * Create and return a new simple observable decorator for the specified set.
     *
     * @param <T> set element type
     * @param set set to decorate, must not be null
     * @return a new simple observable decorator for the specified set
     */
    public static <T> ObservableSet<T> observableSet(final Set<T> set)
    {
        return new SimpleObservableSet<T>(set);
    }

    /**
     * Create and return a new simple observable decorator for the specified sorted map.
     *
     * @param <K> sorted map key type
     * @param <V> sorted map value type
     * @param sortedMap sorted map to decorate, must not be null
     * @return a new simple observable decorator for the specified sorted map
     */
    public static <K,V> ObservableSortedMap<K,V> observableSortedMap(final SortedMap<K,V> sortedMap)
    {
        return new SimpleObservableSortedMap<K,V>(sortedMap);
    }

    /**
     * Create and return a new simple observable decorator for the specified sorted set.
     *
     * @param <T> sorted set element type
     * @param sortedSet sorted set to decorate, must not be null
     * @return a new simple observable decorator for the specified sorted set
     */
    public static <T> ObservableSortedSet<T> observableSortedSet(final SortedSet<T> sortedSet)
    {
        return new SimpleObservableSortedSet<T>(sortedSet);
    }
}
