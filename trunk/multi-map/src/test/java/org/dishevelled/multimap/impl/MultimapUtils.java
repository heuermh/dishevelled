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

import org.dishevelled.multimap.BinaryKeyMap;
import org.dishevelled.multimap.QuaternaryKeyMap;
import org.dishevelled.multimap.TertiaryKeyMap;

/**
 * Static utility methods for multimaps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class MultimapUtils
{

    /**
     * Private no-arg constructor.
     */
    private MultimapUtils()
    {
        // empty
    }


    /**
     * Create and return a new instance of BinaryKeyMap.
     *
     * @param <K1> first key type
     * @param <K2> second key type
     * @param <V> value type
     * @return a new instance of BinaryKeyMap
     */
    public static <K1, K2, V> BinaryKeyMap<K1, K2, V> createBinaryKeyMap()
    {
        return new HashedBinaryKeyMap<K1, K2, V>();
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
     * Create and return a new instance of QuaternaryKeyMap.
     *
     * @param <K1> first key type
     * @param <K2> second key type
     * @param <K3> third key type
     * @param <K4> fourth key type
     * @param <V> value type
     * @return a new instance of QuaternaryKeyMap
     */
    public static <K1, K2, K3, K4, V> QuaternaryKeyMap<K1, K2, K3, K4, V> createQuaternaryKeyMap()
    {
        return new HashedQuaternaryKeyMap<K1, K2, K3, K4, V>();
    }

    // todo:  initialCapacity/loadFactor and copy constructors for all
}