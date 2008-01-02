/*

    dsh-weighted  Weighted map interface and implementation.
    Copyright (c) 2005-2008 held jointly by the individual authors.

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
package org.dishevelled.weighted;

import java.util.Random;

/**
 * Unit test for HashWeightedMap.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class HashWeightedMapTest
    extends AbstractWeightedMapTest
{

    /** @see WeightedMapTest */
    protected <E> WeightedMap<E> createWeightedMap()
    {
        return new HashWeightedMap<E>();
    }

    public void testConstructor()
    {
        WeightedMap<String> m0 = new HashWeightedMap<String>();
        assertNotNull("m0 not null", m0);

        WeightedMap<String> m1 = new HashWeightedMap<String>(16, 0.75f);
        assertNotNull("m1 not null", m1);

        WeightedMap<String> m2 = new HashWeightedMap<String>(0, 0.75f);
        assertNotNull("m2 not null", m2);

        WeightedMap<String> m3 = new HashWeightedMap<String>(m0);
        assertNotNull("m3 not null", m3);

        try
        {
            WeightedMap<String> m = new HashWeightedMap<String>(-1, 0.75f);
            fail("ctr(-1, 0.75f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            WeightedMap<String> m = new HashWeightedMap<String>(16, 0.0f);
            fail("ctr(16, 0.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            WeightedMap<String> m = new HashWeightedMap<String>(16, -1.0f);
            fail("ctr(16, -1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            WeightedMap<String> m = new HashWeightedMap<String>(null);
            fail("ctr(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testSetRandom()
    {
        HashWeightedMap<String> m = new HashWeightedMap<String>();

        Random random = new Random();
        m.setRandom(random);

        try
        {
            m.setRandom(null);
            fail("setRandom(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
