/*

    dsh-weighted  Weighted map interface and implementation.
    Copyright (c) 2005-2010 held jointly by the individual authors.

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
package org.dishevelled.weighted;

/**
 * Static utility methods for WeightedMaps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class WeightedMaps
{

    /**
     * Private no-arg constructor.
     */
    private WeightedMaps()
    {
        // empty
    }


    /**
     * Create and return a new weighted map with the default initial capacity
     * and load factor.
     *
     * @param <E> element type
     * @return a new weighted map with the default initial capacity
     *    and load factor
     */
    public static <E> WeightedMap<E> newWeightedMap()
    {
        return new HashWeightedMap<E>();
    }


    /**
     * Create a new weighted map with the specified initial capacity
     * and load factor.
     *
     * @param <E> element type
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @return a new weighted map with the specified initial capacity
     *    and load factor
     */
    public static <E> WeightedMap<E> newWeightedMap(final int initialCapacity, final float loadFactor)
    {
        return new HashWeightedMap<E>(initialCapacity, loadFactor);
    }

    /**
     * Create and return a new weighted map with the elements and weights
     * in the specified weighted map.
     *
     * @param <E> element type
     * @param weightedMap weighted map to copy, must not be null
     * @return a new weighted map with the elements and weights
     *    in the specified weighted map
     */
    public static <E> WeightedMap<E> newWeightedMap(final WeightedMap<? extends E> weightedMap)
    {
        return new HashWeightedMap<E>(weightedMap);
    }

    // todo:  unmodifiableWeightedMap
}