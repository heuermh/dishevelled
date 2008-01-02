/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
import java.util.Map;
import java.util.Set;

/**
 * Abstract map that decorates an instance of <code>Map</code>.
 *
 * @param <K> map key type
 * @param <V> map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractMapDecorator<K, V>
    implements Map<K, V>
{
    /** Map this decorator decorates. */
    private final Map<K, V> map;


    /**
     * Create a new abstract map that
     * decorates the specified map.
     *
     * @param map map to decorate, must not be null
     */
    protected AbstractMapDecorator(final Map<K, V> map)
    {
        if (map == null)
        {
            throw new IllegalArgumentException("map must not be null");
        }
        this.map = map;
    }


    /**
     * Return a reference to the map this decorator decorates.
     *
     * @return a reference to the map this decorator decorates
     */
    protected final Map<K, V> getMap()
    {
        return map;
    }

    /** {@inheritDoc} */
    public void clear()
    {
        map.clear();
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
    public Set<Map.Entry<K, V>> entrySet()
    {
        return map.entrySet();
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return map.equals(o);
    }

    /** {@inheritDoc} */
    public V get(final Object key)
    {
        return map.get(key);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return map.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    /** {@inheritDoc} */
    public Set<K> keySet()
    {
        return map.keySet();
    }

    /** {@inheritDoc} */
    public V put(final K key, final V value)
    {
        return map.put(key, value);
    }

    /** {@inheritDoc} */
    public void putAll(final Map<? extends K, ? extends V> m)
    {
        map.putAll(m);
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
