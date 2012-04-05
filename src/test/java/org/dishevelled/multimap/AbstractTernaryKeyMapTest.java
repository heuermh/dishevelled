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

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of TernaryKeyMap.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTernaryKeyMapTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of TernaryKeyMap to test.
     *
     * @param <K1> first key type
     * @param <K2> second key type
     * @param <K3> third key type
     * @param <V> valute type
     * @return a new instance of an implementation of TernaryKeyMap to test
     */
    protected abstract <K1, K2, K3, V> TernaryKeyMap<K1, K2, K3, V> createTernaryKeyMap();


    public void testCreateTernaryKeyMap()
    {
        TernaryKeyMap<Float, Integer, String, Double> map0 = createTernaryKeyMap();
        assertNotNull(map0);

        TernaryKeyMap<Float, Integer, String, Double> map1 = createTernaryKeyMap();
        assertNotNull(map1);

        assertTrue(map0 != map1);
    }
}