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
 * Binary key.
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryKey<K1, K2>
{
    /** First key. */
    private final K1 key1;

    /** Second key. */
    private final K2 key2;

    /** Cached hash code. */
    private final int hashCode;


    /**
     * Create a new binary key with the specified keys.
     *
     * <p>The keys should be immutable.  If they are not then they must not be
     * modified after adding to this binary key.</p>
     *
     * @param key1 first key
     * @param key2 second key
     */
    public BinaryKey(final K1 key1,
                     final K2 key2)
    {
        this.key1 = key1;
        this.key2 = key2;

        int result = 0;
        result ^= (key1 == null) ? 0 : key1.hashCode();
        result ^= (key2 == null) ? 0 : key2.hashCode();
        hashCode = result;
    }


    /**
     * Return the first key from this binary key.
     *
     * @return the first key from this binary key
     */
    public K1 getFirstKey()
    {
        return key1;
    }

    /**
     * Return the second key from this binary key.
     *
     * @return the second key from this binary key
     */
    public K2 getSecondKey()
    {
        return key2;
    }

    /** {@inheritDoc} */
    public boolean equals(final Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof BinaryKey)
        {
            BinaryKey binaryKey = (BinaryKey) other;
            return (key1 == null ? binaryKey.key1 == null : key1.equals(binaryKey.key1))
                && (key2 == null ? binaryKey.key2 == null : key2.equals(binaryKey.key2));
        }
        return false;
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return hashCode;
    }
}
