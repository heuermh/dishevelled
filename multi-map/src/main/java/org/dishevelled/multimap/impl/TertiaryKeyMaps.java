/*

    dsh-multi-map  Multi-key map interfaces and implementation.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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

import org.dishevelled.multimap.TertiaryKeyMap;

/**
 * Static utility methods for TertiaryKeyMaps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TertiaryKeyMaps
{

    /**
     * Private no-arg constructor.
     */
    private TertiaryKeyMaps()
    {
        // empty
    }


    /**
     * Create and return a new instance of TertiaryKeyMap.
     *
     * @param <K1> first key type
     * @param <K2> second key type
     * @param <K3> third key type
     * @param <V> value type
     * @return a new instance of TertiaryKeyMap
     */
    public static <K1, K2, K3, V> TertiaryKeyMap<K1, K2, K3, V> createTertiaryKeyMap()
    {
        return new HashedTertiaryKeyMap<K1, K2, K3, V>();
    }

    /**
     * Create and return a new instance of TertiaryKeyMap with the specified initial capacity.
     *
     * @param <K1> first key type
     * @param <K2> second key type
     * @param <K3> third key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @return a new instance of TertiaryKeyMap with the specified initial capacity
     */
    public static <K1, K2, K3, V> TertiaryKeyMap<K1, K2, K3, V> createTertiaryKeyMap(final int initialCapacity)
    {
        return new HashedTertiaryKeyMap<K1, K2, K3, V>(initialCapacity,
                                                 HashedTertiaryKeyMap.DEFAULT_LOAD_FACTOR,
                                                 HashedTertiaryKeyMap.DEFAULT_THRESHOLD);
    }

    /**
     * Create and return a new instance of TertiaryKeyMap with the specified initial capacity,
     * load factor, and threshold.
     *
     * @param <K1> first key type
     * @param <K2> second key type
     * @param <K3> third key type
     * @param <V> value type
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @param threshold threshold
     * @return a new instance of TertiaryKeyMap with the specified initial capacity,
     *    load factor, and threshold
     */
    public static <K1, K2, K3, V> TertiaryKeyMap<K1, K2, K3, V> createTertiaryKeyMap(final int initialCapacity,
                                                                         final float loadFactor,
                                                                         final int threshold)
    {
        return new HashedTertiaryKeyMap<K1, K2, K3, V>(initialCapacity, loadFactor, threshold);
    }
}