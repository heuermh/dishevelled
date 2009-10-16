/*

    dsh-multi-map  Multi-key map interfaces and implementation.
    Copyright (c) 2007 held jointly by the individual authors.

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
package org.dishevelled.multimap.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

//import org.apache.commons.collections.map.AbstractHashedMap;

import org.dishevelled.multimap.BinaryKey;
import org.dishevelled.multimap.BinaryKeyMap;

/**
 * BinaryKeyMap that decorates an existing map.
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <V> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryKeyMapDecorator<K1, K2, V>
    implements BinaryKeyMap<K1, K2, V>
{
    /** Decorated map. */
    private final Map<BinaryKey<K1, K2>, V> map;


    /**
     * Create a new BinaryKeyMap that decorates the specified map.
     *
     * @param map map to decorate, must not be null
     */
    public BinaryKeyMapDecorator(final Map<BinaryKey<K1, K2>, V> map)
    {
        this.map = map;
    }


    /** {@inheritDoc} */
    public boolean containsKey(final K1 key1, final K2 key2)
    {
        return false;
    }

    /** {@inheritDoc} */
    public V get(final K1 key1, final K2 key2)
    {
        return null;
    }

    /** {@inheritDoc} */
    public V put(final K1 key1, final K2 key2, final V value)
    {
        return null;
    }

    /** {@inheritDoc} */
    public V remove(final K1 key1, final K2 key2)
    {
        return null;
    }

    /** {@inheritDoc} */
    public void clear()
    {
        map.clear();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    /** {@inheritDoc} */
    public boolean containsKey(final Object key)
    {
        return map.containsKey(key);
    }

    /** {@inheritDoc} */
    public boolean containsValue(final Object value)
    {
        return map.containsValue(value);
    }

    /** {@inheritDoc} */
    public Set<Map.Entry<BinaryKey<K1, K2>, V>> entrySet()
    {
        return map.entrySet();
    }

    /** {@inheritDoc} */
    public V get(final Object key)
    {
        return map.get(key);
    }

    /** {@inheritDoc} */
    public Set<BinaryKey<K1, K2>> keySet()
    {
        return map.keySet();
    }

    /** {@inheritDoc} */
    public V put(final BinaryKey<K1, K2> key, final V value)
    {
        return map.put(key, value);
    }

    /** {@inheritDoc} */
    public void putAll(final Map<? extends BinaryKey<K1, K2>, ? extends V> map)
    {
        this.map.putAll(map);
    }

    /** {@inheritDoc} */
    public V remove(final Object key)
    {
        return map.remove(key);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return map.size();
    }

    /** {@inheritDoc} */
    public Collection<V> values()
    {
        return map.values();
    }
}