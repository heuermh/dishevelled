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
 * Unit test for BinaryKey.
 *
 * @author  Michael Heuer
 */
public final class BinaryKeyTest
    extends TestCase
{

    public void testConstructor()
    {
        BinaryKey<String,Double> binaryKey0 = new BinaryKey<String,Double>(null, null);
        BinaryKey<String,Double> binaryKey1 = new BinaryKey<String,Double>("foo", null);
        BinaryKey<String,Double> binaryKey2 = new BinaryKey<String,Double>(null, Double.valueOf(0.0d));
        BinaryKey<String,Double> binaryKey3 = new BinaryKey<String,Double>("foo", Double.valueOf(0.0d));
    }

    public void testFirstKey()
    {
        BinaryKey<String,Double> binaryKey0 = new BinaryKey<String,Double>(null, null);
        assertEquals(null, binaryKey0.getFirstKey());
        BinaryKey<String,Double> binaryKey1 = new BinaryKey<String,Double>("foo", null);
        assertEquals("foo", binaryKey1.getFirstKey());
    }

    public void testSecondKey()
    {
        BinaryKey<String,Double> binaryKey0 = new BinaryKey<String,Double>(null, null);
        assertEquals(null, binaryKey0.getSecondKey());
        BinaryKey<String,Double> binaryKey1 = new BinaryKey<String,Double>(null, Double.valueOf(0.0d));
        assertEquals(Double.valueOf(0.0d), binaryKey1.getSecondKey());
    }

    public void testEquals()
    {
        BinaryKey<String,Double> binaryKey0 = new BinaryKey<String,Double>(null, null);
        BinaryKey<String,Double> binaryKey1 = new BinaryKey<String,Double>("foo", null);
        BinaryKey<String,Double> binaryKey2 = new BinaryKey<String,Double>(null, Double.valueOf(0.0d));
        BinaryKey<String,Double> binaryKey3 = new BinaryKey<String,Double>("foo", Double.valueOf(0.0d));

        assertEquals(binaryKey0, binaryKey0);
        assertEquals(binaryKey1, binaryKey1);
        assertEquals(binaryKey2, binaryKey2);
        assertEquals(binaryKey3, binaryKey3);
        assertFalse(binaryKey0.equals(binaryKey1));
        assertFalse(binaryKey0.equals(binaryKey2));
        assertFalse(binaryKey0.equals(binaryKey3));
        assertFalse(binaryKey1.equals(binaryKey0));
        assertFalse(binaryKey1.equals(binaryKey2));
        assertFalse(binaryKey1.equals(binaryKey3));
        assertFalse(binaryKey2.equals(binaryKey0));
        assertFalse(binaryKey2.equals(binaryKey1));
        assertFalse(binaryKey2.equals(binaryKey3));
        assertFalse(binaryKey3.equals(binaryKey0));
        assertFalse(binaryKey3.equals(binaryKey1));
        assertFalse(binaryKey3.equals(binaryKey2));

        assertFalse(binaryKey0.equals(null));
        assertFalse(binaryKey0.equals(new Object()));
        assertFalse(binaryKey0.equals("foo"));
    }
}
