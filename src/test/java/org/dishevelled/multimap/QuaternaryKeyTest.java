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
 * Unit test for QuaternaryKey.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryKeyTest
    extends TestCase
{

    public void testConstructor()
    {
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey0 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey1 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey2 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey3 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey4 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey5 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey6 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey7 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey8 = new QuaternaryKey<String,Integer,Float,Double>(null, null, Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey9 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey10 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey11 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey12 = new QuaternaryKey<String,Integer,Float,Double>(null, null, Float.valueOf(0.0f), Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey13 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, Float.valueOf(0.0f), Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey14 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), Float.valueOf(0.0f), Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey15 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), Float.valueOf(0.0f), Double.valueOf(0.0d));
    }

    public void testFirstKey()
    {
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey0 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, null);
        assertEquals(null, quaternaryKey0.getFirstKey());
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey1 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, null, null);
        assertEquals("foo", quaternaryKey1.getFirstKey());
    }

    public void testSecondKey()
    {
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey0 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, null);
        assertEquals(null, quaternaryKey0.getSecondKey());
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey1 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), null, null);
        assertEquals(Integer.valueOf(0), quaternaryKey1.getSecondKey());
    }

    public void testThirdKey()
    {
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey0 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, null);
        assertEquals(null, quaternaryKey0.getThirdKey());
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey1 = new QuaternaryKey<String,Integer,Float,Double>(null, null, Float.valueOf(0.0f), null);
        assertEquals(Float.valueOf(0.0f), quaternaryKey1.getThirdKey());
    }

    public void testFourthKey()
    {
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey0 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, null);
        assertEquals(null, quaternaryKey0.getFourthKey());
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey1 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, Double.valueOf(0.0d));
        assertEquals(Double.valueOf(0.0d), quaternaryKey1.getFourthKey());
    }

    public void testEquals()
    {
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey0 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey1 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey2 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey3 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), null, null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey4 = new QuaternaryKey<String,Integer,Float,Double>(null, null, null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey5 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey6 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey7 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), null, Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey8 = new QuaternaryKey<String,Integer,Float,Double>(null, null, Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey9 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey10 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey11 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), Float.valueOf(0.0f), null);
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey12 = new QuaternaryKey<String,Integer,Float,Double>(null, null, Float.valueOf(0.0f), Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey13 = new QuaternaryKey<String,Integer,Float,Double>("foo", null, Float.valueOf(0.0f), Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey14 = new QuaternaryKey<String,Integer,Float,Double>(null, Integer.valueOf(0), Float.valueOf(0.0f), Double.valueOf(0.0d));
        QuaternaryKey<String,Integer,Float,Double> quaternaryKey15 = new QuaternaryKey<String,Integer,Float,Double>("foo", Integer.valueOf(0), Float.valueOf(0.0f), Double.valueOf(0.0d));

        List<QuaternaryKey<String, Integer, Float, Double>> quaternaryKeys = new ArrayList<QuaternaryKey<String, Integer, Float, Double>>();
        quaternaryKeys.add(quaternaryKey0);
        quaternaryKeys.add(quaternaryKey1);
        quaternaryKeys.add(quaternaryKey2);
        quaternaryKeys.add(quaternaryKey3);
        quaternaryKeys.add(quaternaryKey4);
        quaternaryKeys.add(quaternaryKey5);
        quaternaryKeys.add(quaternaryKey6);
        quaternaryKeys.add(quaternaryKey7);
        quaternaryKeys.add(quaternaryKey8);
        quaternaryKeys.add(quaternaryKey9);
        quaternaryKeys.add(quaternaryKey10);
        quaternaryKeys.add(quaternaryKey11);
        quaternaryKeys.add(quaternaryKey12);
        quaternaryKeys.add(quaternaryKey13);
        quaternaryKeys.add(quaternaryKey14);
        quaternaryKeys.add(quaternaryKey15);

        for (QuaternaryKey<String, Integer, Float, Double> first : quaternaryKeys)
        {
            for (QuaternaryKey<String, Integer, Float, Double> second : quaternaryKeys)
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
