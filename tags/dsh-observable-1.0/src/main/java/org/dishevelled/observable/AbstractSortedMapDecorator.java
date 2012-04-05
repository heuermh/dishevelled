/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2012 held jointly by the individual authors.

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
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Abstract sorted map that decorates an instance of <code>SortedMap</code>.
 *
 * @param <K> sorted map key type
 * @param <V> sorted map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractSortedMapDecorator<K, V>
    implements SortedMap<K, V>
{
    /** Sorted map this decorator decorates. */
    private final SortedMap<K, V> sortedMap;


    /**
     * Create a new abstract sorted map that
     * decorates the specified sorted map.
     *
     * @param sortedMap sorted map to decorate, must not be null
     */
    protected AbstractSortedMapDecorator(final SortedMap<K, V> sortedMap)
    {
        if (sortedMap == null)
        {
            throw new IllegalArgumentException("sortedMap must not be null");
        }
        this.sortedMap = sortedMap;
    }


    /**
     * Return a reference to the sorted map this decorator decorates.
     *
     * @return a reference to the sorted map this decorator decorates
     */
    protected final SortedMap<K, V> getSortedMap()
    {
        return sortedMap;
    }

    /** {@inheritDoc} */
    public Comparator<? super K> comparator()
    {
        return sortedMap.comparator();
    }

    /** {@inheritDoc} */
    public K firstKey()
    {
        return sortedMap.firstKey();
    }

    /** {@inheritDoc} */
    public SortedMap<K, V> headMap(final K toKey)
    {
        return sortedMap.headMap(toKey);
    }

    /** {@inheritDoc} */
    public K lastKey()
    {
        return sortedMap.lastKey();
    }

    /** {@inheritDoc} */
    public SortedMap<K, V> subMap(final K fromKey, final K toKey)
    {
        return sortedMap.subMap(fromKey, toKey);
    }

    /** {@inheritDoc} */
    public SortedMap<K, V> tailMap(final K fromKey)
    {
        return sortedMap.tailMap(fromKey);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        sortedMap.clear();
    }

    /** {@inheritDoc} */
    public boolean containsKey(final Object key)
    {
        return sortedMap.containsKey(key);
    }

    /** {@inheritDoc} */
    public boolean containsValue(final Object value)
    {
        return sortedMap.containsValue(value);
    }

    /** {@inheritDoc} */
    public Set<SortedMap.Entry<K, V>> entrySet()
    {
        return sortedMap.entrySet();
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return sortedMap.equals(o);
    }

    /** {@inheritDoc} */
    public V get(final Object key)
    {
        return sortedMap.get(key);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return sortedMap.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return sortedMap.isEmpty();
    }

    /** {@inheritDoc} */
    public Set<K> keySet()
    {
        return sortedMap.keySet();
    }

    /** {@inheritDoc} */
    public V put(final K key, final V value)
    {
        return sortedMap.put(key, value);
    }

    /** {@inheritDoc} */
    public void putAll(final Map<? extends K, ? extends V> m)
    {
        sortedMap.putAll(m);
    }

    /** {@inheritDoc} */
    public V remove(final Object key)
    {
        return sortedMap.remove(key);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return sortedMap.size();
    }

    /** {@inheritDoc} */
    public Collection<V> values()
    {
        return sortedMap.values();
    }
}
