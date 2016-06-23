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
package org.dishevelled.multimap;

import java.util.Map;

/**
 * Binary key map.
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <V> value type
 * @author  Michael Heuer
 */
public interface BinaryKeyMap<K1, K2, V>
    extends Map<BinaryKey<K1, K2>, V>
{

    /**
     * Return true if this map contains a binary key of {<code>key1, key2</code>}.
     *
     * @param key1 first key
     * @param key2 second key
     * @return true if this map contains a binary key of {<code>key1, key2</code>}
     */
    boolean containsKey(K1 key1, K2 key2);

    /**
     * Return the value mapped to a binary key of {<code>key1, key2</code>}, if any.
     *
     * @param key1 first key
     * @param key2 second key
     * @return the value mapped to a binary key of {<code>key1, key2</code>}, or
     *    <code>null</code> if no such mapping exists
     */
    V get(K1 key1, K2 key2);

    /**
     * Remove the mapping for a binary key of {<code>key1, key2</code>} from this map
     * if one exists (optional operation).
     *
     * @since 2.0
     * @param key1 first key
     * @param key2 second key
     * @return the value previously mapped to a binary key of {<code>key1, key2</code>},
     *    or <code>null</code> if no such mapping exists
     * @throws UnsupportedOperationException if the remove operation is not supported by this map
     */
    V removeKey(K1 key1, K2 key2);

    /**
     * Map a binary key of {<code>key1, key2</code>} to the specified value (optional
     * operation).
     *
     * @param key1 first key
     * @param key2 second key
     * @param value value
     * @return the value previously mapped to a binary key of {<code>key1, key2</code>},
     *    or <code>null</code> if no such mapping exists
     * @throws UnsupportedOperationException if the put operation is not supported by this map
     */
    V put(K1 key1, K2 key2, V value);
}
