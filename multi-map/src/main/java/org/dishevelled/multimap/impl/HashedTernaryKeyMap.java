/*

    dsh-multi-map  Multi-key map interfaces and implementation.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.multimap.impl;

import java.util.Map;

import org.dishevelled.multimap.TernaryKey;
import org.dishevelled.multimap.TernaryKeyMap;

/**
 * Hashed implementation of TernaryKeyMap.
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <K3> third key type
 * @param <V> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class HashedTernaryKeyMap<K1, K2, K3, V>
    extends AbstractHashedMap<TernaryKey<K1, K2, K3>, V>
    implements TernaryKeyMap<K1, K2, K3, V>
{

    /**
     * Create a new empty HashedTernaryKeyMap.
     */
    public HashedTernaryKeyMap()
    {
        super(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_THRESHOLD);
    }

    /**
     * Create a new empty HashedTernaryKeyMap with the specified
     * initial capacity, load factor, and threshold.
     *
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @param threshold threshold
     */
    public HashedTernaryKeyMap(final int initialCapacity,
                                final float loadFactor,
                                final int threshold)
    {
        super(initialCapacity, loadFactor, threshold);
    }

    /**
     * Create a new HashedTernaryKeyMap with the same mappings as
     * the specified map (copy constructor).
     *
     * @param map map, must not be null
     */
    public HashedTernaryKeyMap(final Map<? extends TernaryKey<K1, K2, K3>, ? extends V> map)
    {
        super(map);
    }


    /**
     * Hash the specified keys.
     *
     * @param key1 first key
     * @param key2 second key
     * @param key3 third key
     * @return hash code for the specified keys
     */
    private int hash(final K1 key1, final K2 key2, final K3 key3)
    {
        int h = 0;
        if (key1 != null)
        {
            h ^= key1.hashCode();
        }
        if (key2 != null)
        {
            h ^= key2.hashCode();
        }
        if (key3 != null)
        {
            h ^= key3.hashCode();
        }
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h;
    }

    /**
     * Return true if the keys in the specified entry are equal to the specified keys.
     *
     * @param entry entry
     * @param key1 first key
     * @param key2 second key
     * @param key3 third key
     * @return true if the keys in the specified entry are equal to the specified keys
     */
    private boolean isEqualKey(final HashEntry<TernaryKey<K1, K2, K3>, V> entry,
                               final K1 key1,
                               final K2 key2,
                               final K3 key3)
    {
        TernaryKey<K1, K2, K3> key = entry.getKey();
        return (key1 == null ? key.getFirstKey() == null : key1.equals(key.getFirstKey()))
            && (key2 == null ? key.getSecondKey() == null : key2.equals(key.getSecondKey()))
            && (key3 == null ? key.getThirdKey() == null : key3.equals(key.getThirdKey()));
    }

    /** {@inheritDoc} */
    public boolean containsKey(final K1 key1, final K2 key2, final K3 key3)
    {
        int hashCode = hash(key1, key2, key3);
        HashEntry<TernaryKey<K1, K2, K3>, V> entry = data[hashIndex(hashCode, data.length)];
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3))
            {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    /** {@inheritDoc} */
    public V get(final K1 key1, final K2 key2, final K3 key3)
    {
        int hashCode = hash(key1, key2, key3);
        HashEntry<TernaryKey<K1, K2, K3>, V> entry = data[hashIndex(hashCode, data.length)];
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3))
            {
                return entry.getValue();
            }
            entry = entry.next;
        }
        return null;
    }

    /** {@inheritDoc} */
    public V put(final K1 key1, final K2 key2, final K3 key3, final V value)
    {
        int hashCode = hash(key1, key2, key3);
        int index = hashIndex(hashCode, data.length);
        HashEntry<TernaryKey<K1, K2, K3>, V> entry = data[index];
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3))
            {
                V oldValue = entry.getValue();
                updateEntry(entry, value);
                return oldValue;
            }
            entry = entry.next;
        }
        addMapping(index, hashCode, new TernaryKey<K1, K2, K3>(key1, key2, key3), value);
        return null;
    }

    /** {@inheritDoc} */
    public V put(final TernaryKey<K1, K2, K3> key, final V value)
    {
        if (key == null)
        {
            throw new NullPointerException("key must not be null");
        }
        return super.put(key, value);
    }

    /** {@inheritDoc} */
    public V remove(final K1 key1, final K2 key2, final K3 key3)
    {
        int hashCode = hash(key1, key2, key3);
        int index = hashIndex(hashCode, data.length);
        HashEntry<TernaryKey<K1, K2, K3>, V> entry = data[index];
        HashEntry<TernaryKey<K1, K2, K3>, V> previous = null;
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3))
            {
                V oldValue = entry.getValue();
                removeMapping(entry, index, previous);
                return oldValue;
            }
            previous = entry;
            entry = entry.next;
        }
        return null;
    }
}