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
 * Unit test for TertiaryKey.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TertiaryKeyTest
    extends TestCase
{

    public void testConstructor()
    {
        TertiaryKey<String,Integer,Double> tertiaryKey0 = new TertiaryKey<String,Integer,Double>(null, null, null);
        TertiaryKey<String,Integer,Double> tertiaryKey1 = new TertiaryKey<String,Integer,Double>("foo", null, null);
        TertiaryKey<String,Integer,Double> tertiaryKey2 = new TertiaryKey<String,Integer,Double>(null, Integer.valueOf(0), null);
        TertiaryKey<String,Integer,Double> tertiaryKey3 = new TertiaryKey<String,Integer,Double>("foo", Integer.valueOf(0), null);
        TertiaryKey<String,Integer,Double> tertiaryKey4 = new TertiaryKey<String,Integer,Double>(null, null, Double.valueOf(0.0d));
        TertiaryKey<String,Integer,Double> tertiaryKey5 = new TertiaryKey<String,Integer,Double>("foo", null, Double.valueOf(0.0d));
        TertiaryKey<String,Integer,Double> tertiaryKey6 = new TertiaryKey<String,Integer,Double>(null, Integer.valueOf(0), Double.valueOf(0.0d));
        TertiaryKey<String,Integer,Double> tertiaryKey7 = new TertiaryKey<String,Integer,Double>("foo", Integer.valueOf(0), Double.valueOf(0.0d));
    }

    public void testFirstKey()
    {
        TertiaryKey<String,Integer,Double> tertiaryKey0 = new TertiaryKey<String,Integer,Double>(null, null, null);
        assertEquals(null, tertiaryKey0.getFirstKey());
        TertiaryKey<String,Integer,Double> tertiaryKey1 = new TertiaryKey<String,Integer,Double>("foo", null, null);
        assertEquals("foo", tertiaryKey1.getFirstKey());
    }

    public void testSecondKey()
    {
        TertiaryKey<String,Integer,Double> tertiaryKey0 = new TertiaryKey<String,Integer,Double>(null, null, null);
        assertEquals(null, tertiaryKey0.getSecondKey());
        TertiaryKey<String,Integer,Double> tertiaryKey1 = new TertiaryKey<String,Integer,Double>(null, Integer.valueOf(0), null);
        assertEquals(Integer.valueOf(0), tertiaryKey1.getSecondKey());
    }

    public void testThirdKey()
    {
        TertiaryKey<String,Integer,Double> tertiaryKey0 = new TertiaryKey<String,Integer,Double>(null, null, null);
        assertEquals(null, tertiaryKey0.getThirdKey());
        TertiaryKey<String,Integer,Double> tertiaryKey1 = new TertiaryKey<String,Integer,Double>(null, null, Double.valueOf(0.0d));
        assertEquals(Double.valueOf(0.0d), tertiaryKey1.getThirdKey());
    }

    public void testEquals()
    {
        TertiaryKey<String,Integer,Double> tertiaryKey0 = new TertiaryKey<String,Integer,Double>(null, null, null);
        TertiaryKey<String,Integer,Double> tertiaryKey1 = new TertiaryKey<String,Integer,Double>("foo", null, null);
        TertiaryKey<String,Integer,Double> tertiaryKey2 = new TertiaryKey<String,Integer,Double>(null, Integer.valueOf(0), null);
        TertiaryKey<String,Integer,Double> tertiaryKey3 = new TertiaryKey<String,Integer,Double>("foo", Integer.valueOf(0), null);
        TertiaryKey<String,Integer,Double> tertiaryKey4 = new TertiaryKey<String,Integer,Double>(null, null, Double.valueOf(0.0d));
        TertiaryKey<String,Integer,Double> tertiaryKey5 = new TertiaryKey<String,Integer,Double>("foo", null, Double.valueOf(0.0d));
        TertiaryKey<String,Integer,Double> tertiaryKey6 = new TertiaryKey<String,Integer,Double>(null, Integer.valueOf(0), Double.valueOf(0.0d));
        TertiaryKey<String,Integer,Double> tertiaryKey7 = new TertiaryKey<String,Integer,Double>("foo", Integer.valueOf(0), Double.valueOf(0.0d));

        List<TertiaryKey<String, Integer, Double>> tertiaryKeys = new ArrayList<TertiaryKey<String, Integer, Double>>();
        tertiaryKeys.add(tertiaryKey0);
        tertiaryKeys.add(tertiaryKey1);
        tertiaryKeys.add(tertiaryKey2);
        tertiaryKeys.add(tertiaryKey3);
        tertiaryKeys.add(tertiaryKey4);
        tertiaryKeys.add(tertiaryKey5);
        tertiaryKeys.add(tertiaryKey6);
        tertiaryKeys.add(tertiaryKey7);

        for (TertiaryKey<String, Integer, Double> first : tertiaryKeys)
        {
            for (TertiaryKey<String, Integer, Double> second : tertiaryKeys)
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
