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

import org.dishevelled.multimap.QuaternaryKey;
import org.dishevelled.multimap.QuaternaryKeyMap;

/**
 * Hashed implementation of QuaternaryKeyMap.
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <K3> third key type
 * @param <K4> fourth key type
 * @param <V> value type
 * @author  Michael Heuer
 */
public final class HashedQuaternaryKeyMap<K1, K2, K3, K4, V>
    extends AbstractHashedMap<QuaternaryKey<K1, K2, K3, K4>, V>
    implements QuaternaryKeyMap<K1, K2, K3, K4, V>
{

    /**
     * Create a new empty HashedQuaternaryKeyMap.
     */
    public HashedQuaternaryKeyMap()
    {
        super(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_THRESHOLD);
    }

    /**
     * Create a new empty HashedQuaternaryKeyMap with the specified
     * initial capacity, load factor, and threshold.
     *
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @param threshold threshold
     */
    public HashedQuaternaryKeyMap(final int initialCapacity,
                                  final float loadFactor,
                                  final int threshold)
    {
        super(initialCapacity, loadFactor, threshold);
    }

    /**
     * Create a new HashedQuaternaryKeyMap with the same mappings as
     * the specified map (copy constructor).
     *
     * @param map map, must not be null
     */
    public HashedQuaternaryKeyMap(final Map<? extends QuaternaryKey<K1, K2, K3, K4>, ? extends V> map)
    {
        super(map);
    }


    /**
     * Hash the specified keys.
     *
     * @param key1 first key
     * @param key2 second key
     * @param key3 third key
     * @param key4 fourth key
     * @return hash code for the specified keys
     */
    private int hash(final K1 key1, final K2 key2, final K3 key3, final K4 key4)
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
        if (key4 != null)
        {
            h ^= key4.hashCode();
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
     * @param key4 fourth key
     * @return true if the keys in the specified entry are equal to the specified keys
     */
    private boolean isEqualKey(final HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> entry,
                               final K1 key1,
                               final K2 key2,
                               final K3 key3,
                               final K4 key4)
    {
        QuaternaryKey<K1, K2, K3, K4> key = entry.getKey();
        return (key1 == null ? key.getFirstKey() == null : key1.equals(key.getFirstKey()))
            && (key2 == null ? key.getSecondKey() == null : key2.equals(key.getSecondKey()))
            && (key3 == null ? key.getThirdKey() == null : key3.equals(key.getThirdKey()))
            && (key4 == null ? key.getFourthKey() == null : key4.equals(key.getFourthKey()));
    }

    @Override
    public boolean containsKey(final K1 key1, final K2 key2, final K3 key3, final K4 key4)
    {
        int hashCode = hash(key1, key2, key3, key4);
        HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> entry = data[hashIndex(hashCode, data.length)];
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3, key4))
            {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    @Override
    public V get(final K1 key1, final K2 key2, final K3 key3, final K4 key4)
    {
        int hashCode = hash(key1, key2, key3, key4);
        HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> entry = data[hashIndex(hashCode, data.length)];
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3, key4))
            {
                return entry.getValue();
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    public V put(final K1 key1, final K2 key2, final K3 key3, final K4 key4, final V value)
    {
        int hashCode = hash(key1, key2, key3, key4);
        int index = hashIndex(hashCode, data.length);
        HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> entry = data[index];
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3, key4))
            {
                V oldValue = entry.getValue();
                updateEntry(entry, value);
                return oldValue;
            }
            entry = entry.next;
        }
        addMapping(index, hashCode, new QuaternaryKey<K1, K2, K3, K4>(key1, key2, key3, key4), value);
        return null;
    }

    @Override
    public V put(final QuaternaryKey<K1, K2, K3, K4> key, final V value)
    {
        if (key == null)
        {
            throw new NullPointerException("key must not be null");
        }
        return super.put(key, value);
    }

    @Override
    public V removeKey(final K1 key1, final K2 key2, final K3 key3, final K4 key4)
    {
        int hashCode = hash(key1, key2, key3, key4);
        int index = hashIndex(hashCode, data.length);
        HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> entry = data[index];
        HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> previous = null;
        while (entry != null)
        {
            if (entry.hashCode == hashCode && isEqualKey(entry, key1, key2, key3, key4))
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
