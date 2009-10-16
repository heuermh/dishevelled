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
 * Quaternary key map iterator.
 *
 * <p>
 * This iterator is a special version designed for quaternary key maps.  It can
 * be more efficient than an entry set iterator where the option is available,
 * and is certianly more convenient.
 * </p>
 * <p>
 * A map that provides this interface may not hold the data internally using
 * Map.Entry or QuaternaryKey objects, thus this interface can avoid lots of object
 * creation.
 * </p>
 * <pre>
 * for (QuaternaryKeyMapIterator&lt;String,Integer,Float,Long,Double> it = map.mapIterator(); it.hasNext(); )
 * {
 *   it.next(); // returns value
 *   String key1 = it.getFirstKey();
 *   Integer key2 = it.getSecondKey();
 *   Float key3 = it.getThirdKey();
 *   Long key4 = it.getFourthKey();
 *   Double value = it.getValue();  // same value as next()
 *   it.setValue(Double.nextUp(value));
 * }
 * </pre>
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <K3> third key type
 * @param <K4> fourth key type
 * @param <V> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface QuaternaryKeyMapIterator<K1, K2, K3, K4, V>
    extends Iterator<V>
{

    /**
     * Return the value mapped to the current quaternary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey(), getFourthKey()</code>}.
     *
     * @return the value mapped to the current quaternary key of {<code>getFirstKey(),
     *    getSecondKey(), getThirdKey(), getFourthKey()</code>}
     * @throws java.util.NoSuchElementException if the iteration is complete
     */
    V next();

    /**
     * Remove the mapping for the current quaternary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey(), getFourthKey()</code>} from the underlying map (optional operation).
     *
     * @throws UnsupportedOperationException if the remove operation is not supported by the
     *    underlying map
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     * @throws IllegalStateException if <code>remove()</code> has already been called
     *    since the last call to <code>next()</code>
     */
    void remove();

    /**
     * Return the first key from the current quaternary key.
     *
     * @return the first key from the current quaternary key
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    K1 getFirstKey();

    /**
     * Return the second key from the current quaternary key.
     *
     * @return the second key from the current quaternary key
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    K2 getSecondKey();

    /**
     * Return the third key from the current quaternary key.
     *
     * @return the third key from the current quaternary key
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    K3 getThirdKey();

    /**
     * Return the fourth key from the current quaternary key.
     *
     * @return the fourth key from the current quaternary key
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    K4 getFourthKey();

    /**
     * Return the value mapped to the current quaternary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey(), getFourthKey()</code>}.
     *
     * @return the value mapped to the current quaternary key of {<code>getFirstKey(),
     *    getSecondKey(), getThirdKey(), getFourthKey()</code>}
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     */
    V getValue();

    /**
     * Set the value mapped to the current quaternary key of {<code>getFirstKey(),
     * getSecondKey(), getThirdKey(), getFourthKey()</code>} to <code>value</code> (optional operation).
     *
     * @param value value
     * @return the value previously mapped to the current quaternary key of {<code>getFirstKey(),
     *    getSecondKey(), getThirdKey(), getFourthKey()</code>}
     * @throws UnsupportedOperationException if the setValue operation is not supported
     *    by the underlying map
     * @throws IllegalStateException if <code>next()</code> has not yet been called
     * @throws IllegalStateException if <code>remove()</code> has been called since the
     *  last call to <code>next()</code>
     */
    V setValue(V value);
}
