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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of BinaryKeyMap.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractBinaryKeyMapTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of BinaryKeyMap to test.
     *
     * @param <K1> first key type
     * @param <K2> second key type
     * @param <V> value type
     * @return a new instance of an implementation of BinaryKeyMap to test
     */
    protected abstract <K1, K2, V> BinaryKeyMap<K1, K2, V> createBinaryKeyMap();

    private List<BinaryKey<Integer, String>> getBinaryKeys()
    {
        List<BinaryKey<Integer, String>> keys = new ArrayList<BinaryKey<Integer, String>>();
        keys.add(new BinaryKey<Integer, String>(Integer.valueOf(0), "key0"));
        keys.add(new BinaryKey<Integer, String>(Integer.valueOf(1), "key1"));
        keys.add(new BinaryKey<Integer, String>(Integer.valueOf(2), "key2"));
        keys.add(new BinaryKey<Integer, String>(Integer.valueOf(3), "key3"));
        return Collections.unmodifiableList(keys);
    }

    private List<Double> getValues()
    {
        return Arrays.asList(new Double[]
        {
            Double.valueOf(0.0d),
            Double.valueOf(1.0d),
            Double.valueOf(2.0d),
            Double.valueOf(3.0d),
        });
    }

    private void fillMap(final BinaryKeyMap<Integer, String, Double> map)
    {
        List<BinaryKey<Integer, String>> keys = getBinaryKeys();
        List<Double> values = getValues();

        for (int i = 0; i < keys.size(); i++)
        {
            map.put(keys.get(i), values.get(i));
        }
    }

    public void testCreateBinaryKeyMap()
    {
        BinaryKeyMap<Integer, String, Double> map0 = createBinaryKeyMap();
        assertNotNull(map0);

        BinaryKeyMap<Integer, String, Double> map1 = createBinaryKeyMap();
        assertNotNull(map1);

        assertTrue(map0 != map1);
    }

    public void testNullHandling()
    {
        BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
        assertEquals(null, map.get(null));
        assertFalse(map.containsKey(null));
        assertFalse(map.containsValue(null));
        assertEquals(null, map.remove(null));
        assertFalse(map.entrySet().contains(null));
        assertFalse(map.values().contains(null));
        assertEquals(null, map.put(new BinaryKey<Integer, String>(null, null), null));
        try
        {
            map.put(null, null);
            fail("put(null, null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            map.put(null, Double.valueOf(0.0d));
            fail("put(null,) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }

        //
        // TODO:  BinaryKey accepts nulls, so these are reasonable, right?

        /*
        try
        {
            map.put(null, null, null);
            fail("put(null, null, null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            map.put(Integer.valueOf(0), null, null);
            fail("put(,null,null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            map.put(null, "foo", null);
            fail("put(null,,null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            map.put(null, null, Double.valueOf(0.0d));
            fail("put(null,,) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
        */
    }

    public void testBinaryKeyGet() {
        BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
        fillMap(map);
        List<BinaryKey<Integer, String>> keys = getBinaryKeys();
        List<Double> values = getValues();

        for (int i = 0; i < keys.size(); i++)
        {
            BinaryKey<Integer, String> key = keys.get(i);
            Double value = values.get(i);
            assertEquals(value, map.get(key.getFirstKey(), key.getSecondKey()));
            assertEquals(null, map.get(null, key.getSecondKey()));
            assertEquals(null, map.get(key.getFirstKey(), null));
            assertEquals(null, map.get(null, null));
        }
    }

    public void testBinaryKeyContainsKey() {
        BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
        fillMap(map);
        List<BinaryKey<Integer, String>> keys = getBinaryKeys();
        List<Double> values = getValues();

        for (int i = 0; i < keys.size(); i++)
        {
            BinaryKey<Integer, String> key = keys.get(i);
            Double value = values.get(i);
            assertTrue(map.containsKey(key.getFirstKey(), key.getSecondKey()));
            assertFalse(map.containsKey(null, key.getSecondKey()));
            assertFalse(map.containsKey(key.getFirstKey(), null));
            assertFalse(map.containsKey(null, null));
        }
    }

    public void testBinaryKeyPut() {
        List<BinaryKey<Integer, String>> keys = getBinaryKeys();
        List<Double> values = getValues();

        for (int i = 0; i < keys.size(); i++)
        {
            BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
            BinaryKey<Integer, String> key = keys.get(i);
            Double value = values.get(i);
            assertEquals(null, map.put(key.getFirstKey(), key.getSecondKey(), value));
            assertEquals(1, map.size());
            assertEquals(value, map.get(key.getFirstKey(), key.getSecondKey()));
            assertTrue(map.containsKey(key.getFirstKey(), key.getSecondKey()));
            assertTrue(map.containsKey(new BinaryKey<Integer, String>(key.getFirstKey(), key.getSecondKey())));
            assertEquals(value, map.put(key.getFirstKey(), key.getSecondKey(), null));
            assertEquals(1, map.size());
            assertEquals(null, map.get(key.getFirstKey(), key.getSecondKey()));
            assertTrue(map.containsKey(key.getFirstKey(), key.getSecondKey()));
        }
    }

    public void testBinaryKeyRemoveKey()
    {
        List<BinaryKey<Integer, String>> keys = getBinaryKeys();
        List<Double> values = getValues();

        for (int i = 0; i < keys.size(); i++)
        {
            BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
            fillMap(map);
            int size = map.size();
            BinaryKey<Integer, String> key = keys.get(i);
            Double value = values.get(i);

            assertTrue(map.containsKey(key.getFirstKey(), key.getSecondKey()));
            assertEquals(value, map.removeKey(key.getFirstKey(), key.getSecondKey()));
            assertFalse(map.containsKey(key.getFirstKey(), key.getSecondKey()));
            assertEquals(size - 1, map.size());
            assertEquals(null, map.removeKey(key.getFirstKey(), key.getSecondKey()));
            assertEquals(false, map.containsKey(key.getFirstKey(), key.getSecondKey()));
        }
    }

    // map removeAll?
    // iterator set
    // iterator remove
    // iterator set remove set
    // iterator remove get key
    // iterator remove get value
}
