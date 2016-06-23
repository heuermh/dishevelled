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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Unit test for TernaryKey.
 *
 * @author  Michael Heuer
 */
public final class TernaryKeyTest
    extends TestCase
{

    public void testConstructor()
    {
        TernaryKey<String,Integer,Double> ternaryKey0 = new TernaryKey<String,Integer,Double>(null, null, null);
        TernaryKey<String,Integer,Double> ternaryKey1 = new TernaryKey<String,Integer,Double>("foo", null, null);
        TernaryKey<String,Integer,Double> ternaryKey2 = new TernaryKey<String,Integer,Double>(null, Integer.valueOf(0), null);
        TernaryKey<String,Integer,Double> ternaryKey3 = new TernaryKey<String,Integer,Double>("foo", Integer.valueOf(0), null);
        TernaryKey<String,Integer,Double> ternaryKey4 = new TernaryKey<String,Integer,Double>(null, null, Double.valueOf(0.0d));
        TernaryKey<String,Integer,Double> ternaryKey5 = new TernaryKey<String,Integer,Double>("foo", null, Double.valueOf(0.0d));
        TernaryKey<String,Integer,Double> ternaryKey6 = new TernaryKey<String,Integer,Double>(null, Integer.valueOf(0), Double.valueOf(0.0d));
        TernaryKey<String,Integer,Double> ternaryKey7 = new TernaryKey<String,Integer,Double>("foo", Integer.valueOf(0), Double.valueOf(0.0d));
    }

    public void testFirstKey()
    {
        TernaryKey<String,Integer,Double> ternaryKey0 = new TernaryKey<String,Integer,Double>(null, null, null);
        assertEquals(null, ternaryKey0.getFirstKey());
        TernaryKey<String,Integer,Double> ternaryKey1 = new TernaryKey<String,Integer,Double>("foo", null, null);
        assertEquals("foo", ternaryKey1.getFirstKey());
    }

    public void testSecondKey()
    {
        TernaryKey<String,Integer,Double> ternaryKey0 = new TernaryKey<String,Integer,Double>(null, null, null);
        assertEquals(null, ternaryKey0.getSecondKey());
        TernaryKey<String,Integer,Double> ternaryKey1 = new TernaryKey<String,Integer,Double>(null, Integer.valueOf(0), null);
        assertEquals(Integer.valueOf(0), ternaryKey1.getSecondKey());
    }

    public void testThirdKey()
    {
        TernaryKey<String,Integer,Double> ternaryKey0 = new TernaryKey<String,Integer,Double>(null, null, null);
        assertEquals(null, ternaryKey0.getThirdKey());
        TernaryKey<String,Integer,Double> ternaryKey1 = new TernaryKey<String,Integer,Double>(null, null, Double.valueOf(0.0d));
        assertEquals(Double.valueOf(0.0d), ternaryKey1.getThirdKey());
    }

    public void testEquals()
    {
        TernaryKey<String,Integer,Double> ternaryKey0 = new TernaryKey<String,Integer,Double>(null, null, null);
        TernaryKey<String,Integer,Double> ternaryKey1 = new TernaryKey<String,Integer,Double>("foo", null, null);
        TernaryKey<String,Integer,Double> ternaryKey2 = new TernaryKey<String,Integer,Double>(null, Integer.valueOf(0), null);
        TernaryKey<String,Integer,Double> ternaryKey3 = new TernaryKey<String,Integer,Double>("foo", Integer.valueOf(0), null);
        TernaryKey<String,Integer,Double> ternaryKey4 = new TernaryKey<String,Integer,Double>(null, null, Double.valueOf(0.0d));
        TernaryKey<String,Integer,Double> ternaryKey5 = new TernaryKey<String,Integer,Double>("foo", null, Double.valueOf(0.0d));
        TernaryKey<String,Integer,Double> ternaryKey6 = new TernaryKey<String,Integer,Double>(null, Integer.valueOf(0), Double.valueOf(0.0d));
        TernaryKey<String,Integer,Double> ternaryKey7 = new TernaryKey<String,Integer,Double>("foo", Integer.valueOf(0), Double.valueOf(0.0d));

        List<TernaryKey<String, Integer, Double>> ternaryKeys = new ArrayList<TernaryKey<String, Integer, Double>>();
        ternaryKeys.add(ternaryKey0);
        ternaryKeys.add(ternaryKey1);
        ternaryKeys.add(ternaryKey2);
        ternaryKeys.add(ternaryKey3);
        ternaryKeys.add(ternaryKey4);
        ternaryKeys.add(ternaryKey5);
        ternaryKeys.add(ternaryKey6);
        ternaryKeys.add(ternaryKey7);

        for (TernaryKey<String, Integer, Double> first : ternaryKeys)
        {
            for (TernaryKey<String, Integer, Double> second : ternaryKeys)
            {
                assertEquals(first, first);
                assertEquals(second, second);

                if (first == second)
                {
                    assertEquals(first, second);
                }
                else
                {
                    assertFalse(first.equals(second));
                }

                assertFalse(first.equals(null));
                assertFalse(first.equals(new Object()));
                assertFalse(first.equals("foo"));

                assertFalse(second.equals(null));
                assertFalse(second.equals(new Object()));
                assertFalse(second.equals("foo"));
            }
        }
    }
}
