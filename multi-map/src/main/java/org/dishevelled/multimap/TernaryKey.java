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
package org.dishevelled.multimap;

/**
 * Ternary key.
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <K3> third key type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TernaryKey<K1, K2, K3>
{
    /** First key. */
    private final K1 key1;

    /** Second key. */
    private final K2 key2;

    /** Third key. */
    private final K3 key3;

    /** Cached hash code. */
    private final int hashCode;


    /**
     * Create a new ternary key with the specified keys.
     *
     * <p>The keys should be immutable.  If they are not then they must not be
     * modified after adding to this ternary key.</p>
     *
     * @param key1 first key
     * @param key2 second key
     * @param key3 third key
     */
    public TernaryKey(final K1 key1,
                       final K2 key2,
                       final K3 key3)
    {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;

        int result = 0;
        result ^= (key1 == null) ? 0 : key1.hashCode();
        result ^= (key2 == null) ? 0 : key2.hashCode();
        result ^= (key3 == null) ? 0 : key3.hashCode();
        hashCode = result;
    }


    /**
     * Return the first key from this ternary key.
     *
     * @return the first key from this ternary key
     */
    public K1 getFirstKey()
    {
        return key1;
    }

    /**
     * Return the second key from this ternary key.
     *
     * @return the second key from this ternary key
     */
    public K2 getSecondKey()
    {
        return key2;
    }

    /**
     * Return the third key from this ternary key.
     *
     * @return the third key from this ternary key
     */
    public K3 getThirdKey()
    {
        return key3;
    }

    /** {@inheritDoc} */
    public boolean equals(final Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof TernaryKey)
        {
            TernaryKey<?, ?, ?> ternaryKey = (TernaryKey<?, ?, ?>) other;
            return (key1 == null ? ternaryKey.key1 == null : key1.equals(ternaryKey.key1))
                    && (key2 == null ? ternaryKey.key2 == null : key2.equals(ternaryKey.key2))
                    && (key3 == null ? ternaryKey.key3 == null : key3.equals(ternaryKey.key3));
        }
        return false;
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return hashCode;
    }
}
