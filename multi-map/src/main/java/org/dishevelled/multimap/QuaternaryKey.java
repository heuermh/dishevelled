/*

    dsh-multi-map  Multi-key map interfaces and implementation.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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
 * Quaternary key.
 *
 * @param <K1> first key type
 * @param <K2> second key type
 * @param <K3> third key type
 * @param <K4> fourth key type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryKey<K1, K2, K3, K4>
{
    /** First key. */
    private final K1 key1;

    /** Second key. */
    private final K2 key2;

    /** Third key. */
    private final K3 key3;

    /** Fourth key. */
    private final K4 key4;

    /** Cached hash code. */
    private final int hashCode;


    /**
     * Create a new quaternary key with the specified keys.
     *
     * <p>The keys should be immutable.  If they are not then they must not be
     * modified after adding to this quaternary key.</p>
     *
     * @param key1 first key
     * @param key2 second key
     * @param key3 third key
     * @param key4 fourth key
     */
    public QuaternaryKey(final K1 key1,
                         final K2 key2,
                         final K3 key3,
                         final K4 key4)
    {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;

        int result = 0;
        result ^= (key1 == null) ? 0 : key1.hashCode();
        result ^= (key2 == null) ? 0 : key2.hashCode();
        result ^= (key3 == null) ? 0 : key3.hashCode();
        result ^= (key4 == null) ? 0 : key4.hashCode();
        hashCode = result;
    }


    /**
     * Return the first key from this quaternary key.
     *
     * @return the first key from this quaternary key
     */
    public K1 getFirstKey()
    {
        return key1;
    }

    /**
     * Return the second key from this quaternary key.
     *
     * @return the second key from this quaternary key
     */
    public K2 getSecondKey()
    {
        return key2;
    }

    /**
     * Return the third key from this quaternary key.
     *
     * @return the third key from this quaternary key
     */
    public K3 getThirdKey()
    {
        return key3;
    }

    /**
     * Return the fourth key from this quaternary key.
     *
     * @return the fourth key from this quaternary key
     */
    public K4 getFourthKey()
    {
        return key4;
    }

    /** {@inheritDoc} */
    public boolean equals(final Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof QuaternaryKey)
        {
            QuaternaryKey quaternaryKey = (QuaternaryKey) other;
            return (key1 == null ? quaternaryKey.key1 == null : key1.equals(quaternaryKey.key1))
                && (key2 == null ? quaternaryKey.key2 == null : key2.equals(quaternaryKey.key2))
                && (key3 == null ? quaternaryKey.key3 == null : key3.equals(quaternaryKey.key3))
                && (key4 == null ? quaternaryKey.key4 == null : key4.equals(quaternaryKey.key4));
        }
        return false;
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return hashCode;
    }
}
