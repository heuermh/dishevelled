/*

    dsh-collect  Collection and map utility classes.
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
package org.dishevelled.collect;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.cliffc.high_scale_lib.NonBlockingHashMapLong;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

/**
 * Static utility methods for Maps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Maps
{

    /**
     * Private no-arg constructor.
     */
    private Maps()
    {
        // empty
    }


    /**
     * Create and return a new empty map.  Delegates to <code>Collections.emptyMap()</code>.
     *
     * @return a new empty map
     */
    public static <K, V> Map<K, V> emptyMap()
    {
        return Collections.<K, V>emptyMap();
    }

    /**
     * Create and return a new instance of Map.
     *
     * @param <K> key type
     * @param <V> value type
     * @return a new instance of Map
     */
    public static <K, V> Map<K, V> createMap()
    {
        return new HashMap<K, V>();
    }

    /**
     * Create and return a new instance of Map with the specified initial capacity.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @return a new instance of Map with the specified initial capacity
     */
    public static <K, V> Map<K, V> createMap(final int initialCapacity)
    {
        return new HashMap<K, V>(initialCapacity);
    }

    /**
     * Create and return a new instance of Map with the specified initial capacity
     * and load factor.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @return a new instance of Map with the specified initial capacity
     *    and load factor
     */
    public static <K, V> Map<K, V> createMap(final int initialCapacity, final float loadFactor)
    {
        return new HashMap<K, V>(initialCapacity, loadFactor);
    }

    /**
     * Create and return a new instance of Map with the same mappings as the
     * specified map. The map is created with default load factor (0.75) and an
     * initial capacity sufficient to hold the mappings in the specified map.
     *
     * @param <K> key type
     * @param <V> value type
     * @param map map to copy mappings from
     * @return a new instance of Map with the same mappings as the
     *    specified map
     */
    public static <K, V> Map<K, V> createMap(final Map<? extends K, ? extends V> map)
    {
        return new HashMap<K, V>(map);
    }

    /**
     * Create and return a new synchronized (thread-safe) instance of Map.  It is
     * imperative that the user manually synchronize on the returned map when iterating
     * over any of its collection views:
     * <pre>
     * Map m = Maps.newSynchronizedMap();
     * ...
     * Set s = m.keySet();  // Needn't be in synchronized block
     * ...
     * synchronized(m)  // Synchronizing on m, not s!
     * {
     *   Iterator i = s.iterator(); // Must be in synchronized block
     *   while (i.hasNext())
     *   {
     *      foo(i.next());
     *   }
     * }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * @param <K> key type
     * @param <V> value type
     * @return a new synchronized (thread-safe) instance of Map
     */
    public static <K, V> Map<K, V> createSynchronizedMap()
    {
        return Collections.synchronizedMap(new HashMap<K, V>());
    }

    /**
     * Create and return a new synchronized (thread-safe) instance of Map with
     * the specified initial capacity.  It is imperative that the user manually
     * synchronize on the returned map when iterating over any of its collection
     * views:
     * <pre>
     * Map m = Maps.newSynchronizedMap(10);
     * ...
     * Set s = m.keySet();  // Needn't be in synchronized block
     * ...
     * synchronized(m)  // Synchronizing on m, not s!
     * {
     *   Iterator i = s.iterator(); // Must be in synchronized block
     *   while (i.hasNext())
     *   {
     *      foo(i.next());
     *   }
     * }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @return a new synchronized (thread-safe) instance of Map with
     *    the specified initial capacity
     */
    public static <K, V> Map<K, V> createSynchronizedMap(final int initialCapacity)
    {
        return Collections.synchronizedMap(new HashMap<K, V>(initialCapacity));
    }

    /**
     * Create and return a new synchronized (thread-safe) instance of Map with
     * the specified initial capacity and load factor.  It is imperative that the
     * user manually synchronize on the returned map when iterating over any of
     * its collection views:
     * <pre>
     * Map m = Maps.newSynchronizedMap(10, 0.80);
     * ...
     * Set s = m.keySet();  // Needn't be in synchronized block
     * ...
     * synchronized(m)  // Synchronizing on m, not s!
     * {
     *   Iterator i = s.iterator(); // Must be in synchronized block
     *   while (i.hasNext())
     *   {
     *      foo(i.next());
     *   }
     * }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @return a new synchronized (thread-safe) instance of Map with
     *    the specified intial capacity and load factor
     */
    public static <K, V> Map<K, V> createSynchronizedMap(final int initialCapacity, final float loadFactor)
    {
        return Collections.synchronizedMap(new HashMap<K, V>(initialCapacity, loadFactor));
    }

    /**
     * Create and return a new synchronized (thread-safe) instance of Map with
     * the same mappings as the specified map. The map is created with default load
     * factor (0.75) and an initial capacity sufficient to hold the mappings in the
     * specified map.  It is imperative that the user manually synchronize on the
     * returned map when iterating over any of its collection views:
     * <pre>
     * Map m = Maps.newSynchronizedMap(map);
     * ...
     * Set s = m.keySet();  // Needn't be in synchronized block
     * ...
     * synchronized(m)  // Synchronizing on m, not s!
     * {
     *   Iterator i = s.iterator(); // Must be in synchronized block
     *   while (i.hasNext())
     *   {
     *      foo(i.next());
     *   }
     * }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * @param <K> key type
     * @param <V> value type
     * @param map map to copy mappings from
     * @return a new synchronized (thread-safe) instance of Map with
     *    the same mappings as the specified map
     */
    public static <K, V> Map<K, V> createSynchronizedMap(final Map<? extends K, ? extends V> map)
    {
        return Collections.synchronizedMap(new HashMap<K, V>(map));
    }

    /**
     * Create and return a new instance of ConcurrentMap.
     *
     * @param <K> key type
     * @param <V> value type
     * @return a new instance of ConcurrentMap
     */
    public static <K, V> ConcurrentMap<K, V> createConcurrentMap()
    {
        return new ConcurrentHashMap<K, V>();
    }

    /**
     * Create and return a new instance of ConcurrentMap with the specified initial capacity.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @return a new instance of ConcurrentMap with the specified initial capacity
     */
    public static <K, V> ConcurrentMap<K, V> createConcurrentMap(final int initialCapacity)
    {
        return new ConcurrentHashMap<K, V>(initialCapacity);
    }

    /**
     * Create and return a new instance of ConcurrentMap with the specified initial capacity
     * and load factor.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @return a new instance of ConcurrentMap with the specified initial capacity
     *    and load factor
     */
    public static <K, V> ConcurrentMap<K, V> createConcurrentMap(final int initialCapacity,
                                                              final float loadFactor)
    {
        return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor);
    }

    /**
     * Create and return a new instance of ConcurrentMap with the specified initial capacity,
     * load factor, and concurrency level.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @param concurrencyLevel concurrency level
     * @return a new instance of ConcurrentMap with the specified initial capacity
     *    load factor, and concurrency level
     */
    public static <K, V> ConcurrentMap<K, V> createConcurrentMap(final int initialCapacity,
                                                              final float loadFactor,
                                                              final int concurrencyLevel)
    {
        return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor, concurrencyLevel);
    }

    /**
     * Create and return a new instance of ConcurrentMap with the same mappings as the
     * specified map. The concurrent map is created with default load factor (0.75) and an
     * initial capacity sufficient to hold the mappings in the specified map.
     *
     * @param <K> key type
     * @param <V> value type
     * @param map map to copy mappings from, must not be null
     * @return a new instance of ConcurrentMap with the same mappings as the
     *    specified map
     */
    public static <K, V> ConcurrentMap<K, V> createConcurrentMap(final Map<? extends K, ? extends V> map)
    {
        return new ConcurrentHashMap<K, V>(map);
    }

    /**
     * Create and return a new non-blocking implementation of ConcurrentMap.
     *
     * @param <K> key type
     * @param <V> value type
     * @return a new non-blocking implementation of ConcurrentMap
     */
    public static <K, V> ConcurrentMap<K, V> createNonBlockingMap()
    {
        return new NonBlockingHashMap<K, V>();
    }

    /**
     * Create and return a new non-blocking implementation of ConcurrentMap with the
     * specified initial capacity.
     *
     * @param <K> key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @return a new non-blocking implementation of ConcurrentMap with the
     *    specified initial capacity
     */
    public static <K, V> ConcurrentMap<K, V> createNonBlockingMap(final int initialCapacity)
    {
        return new NonBlockingHashMap<K, V>(initialCapacity);
    }

    /**
     * Create and return a new non-blocking implementation of ConcurrentMap with
     * <code>Long</code>s as keys.
     *
     * @param <V> value type
     * @return a new non-blocking implementation of ConcurrentMap with
     *    <code>Long</code>s as keys
     */
    public static <V> ConcurrentMap<Long, V> createLongNonBlockingMap()
    {
        return new NonBlockingHashMapLong<V>();
    }

    /**
     * Create and return a new non-blocking implementation of ConcurrentMap with
     * <code>Long</code>s as keys.
     *
     * @param <V> value type
     * @param optimizeForSpace true if the implementation should optimize for space
     * @return a new non-blocking implementation of ConcurrentMap with
     *    <code>Long</code>s as keys
     */
    public static <V> ConcurrentMap<Long, V> createLongNonBlockingMap(final boolean optimizeForSpace)
    {
        return new NonBlockingHashMapLong<V>(optimizeForSpace);
    }

    /**
     * Create and return a new non-blocking implementation of ConcurrentMap with
     * the specified initial capacity and <code>Long</code>s as keys.
     *
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @return a new non-blocking implementation of ConcurrentMap with
     *    the specified initial capacity and <code>Long</code>s as keys
     */
    public static <V> ConcurrentMap<Long, V> createLongNonBlockingMap(final int initialCapacity)
    {
        return new NonBlockingHashMapLong<V>(initialCapacity);
    }

    /**
     * Create and return a new non-blocking implementation of ConcurrentMap with
     * the specified initial capacity and <code>Long</code>s as keys.
     *
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @param optimizeForSpace true if this implementation should optimize for space
     * @return a new non-blocking implementation of ConcurrentMap with
     *    the specified initial capacity and <code>Long</code>s as keys
     */
    public static <V> ConcurrentMap<Long, V> createLongNonBlockingMap(final int initialCapacity,
                                                                   final boolean optimizeForSpace)
    {
        return new NonBlockingHashMapLong<V>(initialCapacity, optimizeForSpace);
    }

    /**
     * Create and return a new instance of ConcurrentSkipListMap sorted according
     * to the natural ordering of its keys.
     *
     * @param <K> key type
     * @param <V> value type
     * @return a new instance of ConcurrentSkipListMap sorted according
     *    to the natural ordering of its keys
     */
    public static <K, V> ConcurrentSkipListMap<K, V> createConcurrentSkipListMap()
    {
        return new ConcurrentSkipListMap<K, V>();
    }

    /**
     * Create and return a new instance of ConcurrentSkipListMap sorted according
     * to the specified comparator.
     *
     * @param <K> key type
     * @param <V> value type
     * @param comparator comparator that will be used to order this map, or
     *    <code>null</code> to use the natural ordering of its keys
     * @return a new instance of ConcurrentSkipListMap sorted according
     *    to the specified comparator
     */
    public static <K, V> ConcurrentSkipListMap<K, V> createConcurrentSkipListMap(final Comparator<? super K> comparator)
    {
        return new ConcurrentSkipListMap<K, V>(comparator);
    }

    /**
     * Create and return a new instance of ConcurrentSkipListMap containing the
     * same mappings as the specified map and sorted according to the natural ordering of
     * its keys.
     *
     * @param <K> key type
     * @param <V> value type
     * @param map map to copy mappings from, must not be null
     * @return a new instance of ConcurrentSkipListMap containing the
     *    same mappings as the specified map and sorted according to the natural ordering of
     *    its keys
     */
    public static <K, V> ConcurrentSkipListMap<K, V> createConcurrentSkipListMap(final Map<? extends K, ? extends V> map)
    {
        return new ConcurrentSkipListMap<K, V>(map);
    }

    /**
     * Create and return a new instance of ConcurrentSkipListMap containing the
     * same mappings and using the same ordering as the specified sorted map.
     *
     * @param <K> key type
     * @param <V> value type
     * @param sortedMap sorted map to copy mappings and ordering from, must not be null
     * @return a new instance of ConcurrentSkipListMap containing the
     *    same mappings and using the same ordering as the specified sorted map
     */
    public static <K, V> ConcurrentSkipListMap<K, V> createConcurrentSkipListMap(final SortedMap<K, ? extends V> sortedMap)
    {
        return new ConcurrentSkipListMap<K, V>(sortedMap);
    }

    /**
     * Create and return a new instance of SortedMap using the natural ordering of its keys.
     *
     * @param <K> key type
     * @param <V> value type
     * @return a new instance of SortedMap using the natural ordering of its keys
     */
    public static <K, V> SortedMap<K, V> createSortedMap()
    {
        return new TreeMap<K, V>();
    }

    /**
     * Create and return a new instance of SortedMap ordered according to the specified comparator.
     *
     * @param <K> key type
     * @param <V> value type
     * @param comparator comparator used to order this map, or <code>null</code> to use the
     *    natural ordering of its keys
     * @return a new instance of SortedMap ordered according to the specified comparator
     */
    public static <K, V> SortedMap<K, V> createSortedMap(final Comparator<? super K> comparator)
    {
        return new TreeMap<K, V>(comparator);
    }

    /**
     * Create and return a new instance of SortedMap containing the same mappings as the specified
     * map ordered according to the natural ordering of its keys.
     *
     * @param <K> key type
     * @param <V> value type
     * @param map map to copy mappings from, must not be null
     * @return a new instance of SortedMap containing the same mappings as the specified
     *    map ordered according to the natural ordering of its keys
     */
    public static <K, V> SortedMap<K, V> createSortedMap(final Map<? extends K, ? extends V> map)
    {
        return new TreeMap<K, V>(map);
    }

    /**
     * Create and return a new instance of SortedMap containing the same mappings and the same
     * ordering as the specified sorted map.
     *
     * @param <K> key type
     * @param <V> value type
     * @param sortedMap sorted map to copy mappings and comparator from, must not be null
     * @return a new instance of SortedMap containing the same mappings and the same
     *    ordering as the specified sorted map
     */
    public static <K, V> SortedMap<K, V> createSortedMap(final SortedMap<K, ? extends V> sortedMap)
    {
        return new TreeMap<K, V>(sortedMap);
    }

    /**
     * Create and return a new unmodifiable view of the specified map.  Query operations on
     * the returned map "read through" to the specified map.  Attempts to modify the returned map,
     * whether direct or via its collection views, result in an <code>UnsupportedOperationException<code>.
     *
     * @param <K> key type
     * @param <V> value type
     * @param map map to view, must not be null
     * @return a new unmodifiable view of the specified map
     */
    public static <K, V> Map<K, V> unmodifiableMap(final Map<? extends K, ? extends V> map)
    {
        return Collections.unmodifiableMap(map);
    }

    /**
     * Create and return a new unmodifiable view of the specified sorted map.  Query operations on
     * the returned sorted map "read through" to the specified sorted map. Attempts to modify the
     * returned sorted map, whether direct, via its collection views, or via its <code>subMap</code>,
     * <code>headMap</code>, or <code>tailMap</code> views, result in an
     * <code>UnsupportedOperationException</code>.
     *
     * @param <K> key type
     * @param <V> value type
     * @param sortedMap sorted map to view, must not be null
     * @return a new unmodifiable view of the specified sorted map
     */
    public static <K, V> SortedMap<K, V> unmodifiableSortedMap(final SortedMap<K, ? extends V> sortedMap)
    {
        return Collections.unmodifiableSortedMap(sortedMap);
    }

    /**
     * Create and return an immutable map containing the same mappings as the specified map.
     *
     * @param <K> key type
     * @param <V> value type
     * @param map map to copy, must not be null
     * @return an immutable map containing the same mappings as the specified map
     */
    public static <K, V> ConcurrentMap<K, V> immutableMap(final Map<? extends K, ? extends V> map)
    {
        return ImmutableMap.copyOf(map);
    }

    /**
     * Create and return an immutable sorted map containing the same mappings and the same
     * ordering as the specified sorted map.
     *
     * @param <K> key type
     * @param <V> value type
     * @param sortedMap sorted map to copy, must not be null
     * @return an immutable sorted map containing the same mappings and the same
     *    ordering as the specified sorted map
     */
    public static <K, V> SortedMap<K, V> immutableSortedMap(final SortedMap<K, ? extends V> sortedMap)
    {
        return ImmutableSortedMap.copyOfSorted(sortedMap);
    }
}