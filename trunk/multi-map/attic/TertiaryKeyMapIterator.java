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
package org.dishevelled.multimap;

import java.util.Iterator;

/**
 * Tertiary key map iterator.
 *
 * <p>
 * This iterator is a special version designed for tertiary key maps.  It can
 * be more efficient than an entry set iterator where the option is available,
 * and is certianly more convenient.
 * </p>
 * <p>
 * A map that provides this interface may not hold the data internally using
 * Map.Entry or TertiaryKey objects, thus this interface can avoid lots of object
 * creation.
 * </p>
 * <pre>
 * for (TertiaryKeyMapIterator&lt;String,Integer,Float,Double> it = map.mapIterator(); it.hasNext(); )
 * {
 *   it.next(); // returns value
 *   String key1 = it.getFirstKey();
 *   Integer key2 = it.getSecondKey();
 *   Float key3 = it.getThirdKey();
 *   Double value = it.getValue();  // same value as next()
 *   it.setValue(Double.nextUp(value));
 * }
 * </pre>
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <K3> third key type
 * @param <V> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface TertiaryKeyMapIterator<K1, K2, K3, V>
    extends Iterator<V>
{

    /**
     * Return the value mapped to the current tertiary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey()</code>}.
     *
     * @return the value mapped to the current tertiary key of {<code>getFirstKey(),
     *    getSecondKey(), getThirdKey()</code>}
     * @throws java.util.NoSuchElementException if the iteration is complete
     */
    V next();

    /**
     * Remove the mapping for the current tertiary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey()</code>} from the underlying map (optional operation).
     *
     * @throws UnsupportedOperationException if the remove operation is not supported by the
     *    underlying map
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     * @throws IllegalStateException if <code>remove()</code> has already been called
     *    since the last call to <code>next()</code>
     */
    void remove();

    /**
     * Return the first key from the current tertiary key.
     *
     * @return the first key from the current tertiary key
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    K1 getFirstKey();

    /**
     * Return the second key from the current tertiary key.
     *
     * @return the second key from the current tertiary key
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    K2 getSecondKey();

    /**
     * Return the third key from the current tertiary key.
     *
     * @return the third key from the current tertiary key
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    K3 getThirdKey();

    /**
     * Return the value mapped to the current tertiary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey()</code>}.
     *
     * @return the value mapped to the current tertiary key of {<code>getFirstKey(),
     *    getSecondKey(), getThirdKey()</code>}
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    V getValue();

    /**
     * Set the value mapped to the current tertiary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey()</code>} to <code>value</code> (optional operation).
     *
     * @param value value
     * @return the value previously mapped to the current tertiary key of {<code>getFirstKey(),
     *    getSecondKey(), getThirdKey()</code>}
     * @throws UnsupportedOperationException if the setValue operation is not supported
     *    by the underlying map
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     * @throws IllegalStateException if <code>remove()</code> has been called since the
     *  last call to <code>next()</code>
     */
    V setValue(V value);
}
