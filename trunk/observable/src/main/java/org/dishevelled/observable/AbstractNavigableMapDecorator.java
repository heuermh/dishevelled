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

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;

/**
 * Abstract navigable map that decorates an instance of <code>NavigableMap</code>.
 *
 * @param <K> navigable map key type
 * @param <V> navigable map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractNavigableMapDecorator<K,V>
    implements NavigableMap<K,V>
{
    /** Navigable map this decorator decorates. */
    private final NavigableMap<K,V> navigableMap;


    /**
     * Create a new abstract navigable map that
     * decorates the specified navigable map.
     *
     * @param navigableMap navigable map to decorate, must not be null
     */
    protected AbstractNavigableMapDecorator(final NavigableMap<K,V> navigableMap)
    {
        if (navigableMap == null)
        {
            throw new IllegalArgumentException("navigableMap must not be null");
        }
        this.navigableMap = navigableMap;
    }


    /**
     * Return a reference to the navigable map this decorator decorates.
     *
     * @return a reference to the navigable map this decorator decorates
     */
    protected final NavigableMap<K,V> getNavigableMap()
    {
        return navigableMap;
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> ceilingEntry(final K key)
    {
        return navigableMap.ceilingEntry(key);
    }

    /** {@inheritDoc} */
    public K ceilingKey(final K key)
    {
        return navigableMap.ceilingKey(key);
    }

    /** {@inheritDoc} */
    public NavigableSet<K> descendingKeySet()
    {
        return navigableMap.descendingKeySet();
    }

    /** {@inheritDoc} */
    public NavigableMap<K,V> descendingMap()
    {
        return navigableMap.descendingMap();
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> firstEntry()
    {
        return navigableMap.firstEntry();
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> floorEntry(final K key)
    {
        return navigableMap.floorEntry(key);
    }

    /** {@inheritDoc} */
    public K floorKey(final K key)
    {
        return navigableMap.floorKey(key);
    }

    /** {@inheritDoc} */
    public NavigableMap<K,V> headMap(final K toKey, final boolean inclusive)
    {
        return navigableMap.headMap(toKey, inclusive);
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> higherEntry(final K key)
    {
        return navigableMap.higherEntry(key);
    }

    /** {@inheritDoc} */
    public K higherKey(final K key)
    {
        return navigableMap.higherKey(key);
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> lastEntry()
    {
        return navigableMap.lastEntry();
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> lowerEntry(final K key)
    {
        return navigableMap.lowerEntry(key);
    }

    /** {@inheritDoc} */
    public K lowerKey(final K key)
    {
        return navigableMap.lowerKey(key);
    }

    /** {@inheritDoc} */
    public NavigableSet<K> navigableKeySet()
    {
        return navigableMap.navigableKeySet();
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> pollFirstEntry()
    {
        return navigableMap.pollFirstEntry();
    }

    /** {@inheritDoc} */
    public Map.Entry<K,V> pollLastEntry()
    {
        return navigableMap.pollLastEntry();
    }

    /** {@inheritDoc} */
    public NavigableMap<K,V> subMap(final K fromKey, final boolean fromInclusive,
                                    final K toKey, final boolean toInclusive)
    {
        return navigableMap.subMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    /** {@inheritDoc} */
    public NavigableMap<K,V> tailMap(final K fromKey, final boolean inclusive)
    {
        return navigableMap.tailMap(fromKey, inclusive);
    }

    /** {@inheritDoc} */
    public Comparator<? super K> comparator()
    {
        return navigableMap.comparator();
    }

    /** {@inheritDoc} */
    public K firstKey()
    {
        return navigableMap.firstKey();
    }

    /** {@inheritDoc} */
    public SortedMap<K,V> headMap(final K toKey)
    {
        return navigableMap.headMap(toKey);
    }

    /** {@inheritDoc} */
    public K lastKey()
    {
        return navigableMap.lastKey();
    }

    /** {@inheritDoc} */
    public SortedMap<K,V> subMap(final K fromKey, final K toKey)
    {
        return navigableMap.subMap(fromKey, toKey);
    }

    /** {@inheritDoc} */
    public SortedMap<K,V> tailMap(final K fromKey)
    {
        return navigableMap.tailMap(fromKey);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        navigableMap.clear();
    }

    /** {@inheritDoc} */
    public boolean containsKey(final Object key)
    {
        return navigableMap.containsKey(key);
    }

    /** {@inheritDoc} */
    public boolean containsValue(final Object value)
    {
        return navigableMap.containsValue(value);
    }

    /** {@inheritDoc} */
    public Set<NavigableMap.Entry<K,V>> entrySet()
    {
        return navigableMap.entrySet();
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return navigableMap.equals(o);
    }

    /** {@inheritDoc} */
    public V get(final Object key)
    {
        return navigableMap.get(key);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return navigableMap.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return navigableMap.isEmpty();
    }

    /** {@inheritDoc} */
    public Set<K> keySet()
    {
        return navigableMap.keySet();
    }

    /** {@inheritDoc} */
    public V put(final K key, final V value)
    {
        return navigableMap.put(key, value);
    }

    /** {@inheritDoc} */
    public void putAll(final Map<? extends K,? extends V> m)
    {
        navigableMap.putAll(m);
    }

    /** {@inheritDoc} */
    public V remove(final Object key)
    {
        return navigableMap.remove(key);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return navigableMap.size();
    }

    /** {@inheritDoc} */
    public Collection<V> values()
    {
        return navigableMap.values();
    }
}
