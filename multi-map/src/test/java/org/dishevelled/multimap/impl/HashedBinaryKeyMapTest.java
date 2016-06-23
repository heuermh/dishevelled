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

import org.dishevelled.multimap.AbstractBinaryKeyMapTest;
import org.dishevelled.multimap.BinaryKeyMap;

/**
 * Unit test for HashedBinaryKeyMap.
 *
 * @author  Michael Heuer
 */
public final class HashedBinaryKeyMapTest
    extends AbstractBinaryKeyMapTest
{

    @Override
    protected <K1, K2, V> BinaryKeyMap<K1, K2, V> createBinaryKeyMap()
    {
        return new HashedBinaryKeyMap<K1, K2, V>();
    }

    public void testConstructors()
    {
        HashedBinaryKeyMap<String, Integer, Double> binaryKeyMap0 = new HashedBinaryKeyMap<String, Integer, Double>();
        HashedBinaryKeyMap<String, Integer, Double> binaryKeyMap1 = new HashedBinaryKeyMap<String, Integer, Double>(16, 0.75f, 12);
        HashedBinaryKeyMap<String, Integer, Double> binaryKeyMap2 = new HashedBinaryKeyMap<String, Integer, Double>(binaryKeyMap0);
        assertNotNull(binaryKeyMap0);
        assertNotNull(binaryKeyMap1);
        assertNotNull(binaryKeyMap2);

        try
        {
            new HashedBinaryKeyMap<String, Integer, Double>(null);
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }
}
