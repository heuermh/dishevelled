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

import java.util.Map;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collections;

import junit.framework.TestCase;

/**
 * Abstract unit test for instances of WeightedMap.
 *
 * @author  Michael Heuer
 * @author  Mark Schreiber
 * @version $Revision$ $Date$
 */
public abstract class AbstractWeightedMapTest
    extends TestCase
{

    /**
     * Create a new instance of WeightedMap to test.
     */
    protected abstract <E> WeightedMap<E> createWeightedMap();

    public void testEmptyWeightedMap()
    {
        WeightedMap<String> m = createWeightedMap();

        assertNotNull("m not null", m);
        assertNotNull("m keySet not null", m.keySet());
        assertNotNull("m values not null", m.values());
        assertNotNull("m entrySet not null", m.entrySet());

        assertEquals("m size == 0", 0, m.size());
        assertTrue("m isEmpty", m.isEmpty());
        assertFalse("m containsKey foo", m.containsKey("foo"));
        assertFalse("m containsValue 1.0d", m.containsValue(1.0d));

        assertEquals("m keySet size == 0", 0, m.keySet().size());
        assertEquals("m values size == 0", 0, m.values().size());
        assertEquals("m entrySet size == 0", 0, m.entrySet().size());

        assertEquals("m sample == null", null, m.sample());
        assertEquals("m totalWeight == 0.0d", 0.0d, m.totalWeight());
        assertEquals("m get(foo) == null", null, m.get("foo"));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m maximumRank == -1", -1, m.maximumRank());
    }

    public void testZeroWeightedMap()
    {
        WeightedMap<String> m = createWeightedMap();

        m.put("foo", 0.0d);

        assertEquals("m size == 1", 1, m.size());
        assertFalse("m isEmpty == false", m.isEmpty());
        assertTrue("m containsKey foo", m.containsKey("foo"));
        assertTrue("m containsValue 0.0d", m.containsValue(0.0d));

        assertEquals("m keySet size == 1", 1, m.keySet().size());
        assertEquals("m values size == 1", 1, m.values().size());
        assertEquals("m entrySet size == 1", 1, m.entrySet().size());

        assertEquals("m sample == null", null, m.sample());
        assertEquals("m totalWeight == 0.0d", 0.0d, m.totalWeight());
        assertEquals("m get(foo) == 0.0d", 0.0d, m.get("foo"));
        assertEquals("m weight(foo) == 0.0d", 0.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.0d", 0.0d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 1", 1, m.rank("foo"));
        assertEquals("m maximumRank == 1", 1, m.maximumRank());        
    }

    public void testFullWeightedMap()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0);
        m.put("bar", 1.0);
        m.put("baz", 2.0);

        assertEquals("m size == 3", 3, m.size());
        assertFalse("m isEmpty == false", m.isEmpty());
        assertTrue("m containsKey foo", m.containsKey("foo"));
        assertTrue("m containsKey bar", m.containsKey("bar"));
        assertTrue("m containsKey baz", m.containsKey("baz"));
        assertTrue("m containsValue 1.0d", m.containsValue(1.0d));
        assertTrue("m containsValue 2.0d", m.containsValue(2.0d));

        assertEquals("m keySet size == 3", 3, m.keySet().size());
        assertEquals("m values size == 3", 3, m.values().size());
        assertEquals("m entrySet size == 3", 3, m.entrySet().size());
        assertTrue("m keySet contains foo", m.keySet().contains("foo"));
        assertTrue("m keySet contains bar", m.keySet().contains("bar"));
        assertTrue("m keySet contains baz", m.keySet().contains("baz"));
        assertFalse("m keySet contains not a key == false", m.keySet().contains("not a key"));
        assertTrue("m values contains 1.0d", m.values().contains(1.0d));
        assertTrue("m values contains 2.0d", m.values().contains(2.0d));
        assertFalse("m values contains 99.0d == false", m.values().contains(99.0d));

        boolean foundFooEntry = false;
        boolean foundBarEntry = false;
        boolean foundBazEntry = false;
        boolean foundNotAKeyEntry = false;
        for (Map.Entry<String, Double> e : m.entrySet())
        {
            if (e.getKey().equals("foo") && e.getValue().equals(1.0d))
            {
                foundFooEntry = true;
            }
            if (e.getKey().equals("bar") && e.getValue().equals(1.0d))
            {
                foundBarEntry = true;
            }
            if (e.getKey().equals("baz") && e.getValue().equals(2.0d))
            {
                foundBazEntry = true;
            }
            if (e.getKey().equals("not a key"))
            {
                foundNotAKeyEntry = true;
            }
        }
        assertTrue("m entrySet contains foo=>1.0d", foundFooEntry);
        assertTrue("m entrySet contains bar=>1.0d", foundBarEntry);
        assertTrue("m entrySet contains baz=>2.0d", foundBazEntry);
        assertFalse("m entrySet contains notAKey=>* == false", foundNotAKeyEntry);

        assertEquals("m get(foo) == 1.0d", 1.0d, m.get("foo"));
        assertEquals("m get(bar) == 1.0d", 1.0d, m.get("bar"));
        assertEquals("m get(baz) == 2.0d", 2.0d, m.get("baz"));
        assertEquals("m get(not a key) == null", null, m.get("not a key"));

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m weight(bar) == 1.0d", 1.0d, m.weight("bar"));
        assertEquals("m weight(baz) == 2.0d", 2.0d, m.weight("baz"));
        assertEquals("m weight(not a key) == null", null, m.weight("not a key"));

        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m normalizedWeight(bar) == 0.25d", 0.25d, m.normalizedWeight("bar"));
        assertEquals("m normalizedWeight(baz) == 0.50d", 0.50d, m.normalizedWeight("baz"));
        assertEquals("m normalizedWeight(not a key) == null", null, m.normalizedWeight("not a key"));

        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));
        assertEquals("m rank(not a key) = -1", -1, m.rank("not a key"));

        assertEquals("m maximumRank == 2", 2, m.maximumRank());
    }

    public void testMultiplePuts()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.put("foo", 2.0d);
        assertEquals("m weight(foo) == 2.0d", 2.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.4d", 0.4d, m.normalizedWeight("foo"));
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(foo) == 1", 1, m.rank("foo"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 5.0d", 5.0d, m.totalWeight());

        m.put("foo", 3.0d);
        assertEquals("m weight(foo) == 3.0d", 3.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.5d", 0.5d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 1", 1, m.rank("foo"));
        assertEquals("m rank(baz) == 2", 2, m.rank("baz"));
        assertEquals("m rank(bar) == 3", 3, m.rank("bar"));        
        assertEquals("m maximumRank == 3", 3, m.maximumRank());
        assertEquals("m totalWeight == 6.0d", 6.0d, m.totalWeight());

        m.put("foo", 0.0d);
        assertEquals("m weight(foo) == 0.0d", 0.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.0d", 0.0d, m.normalizedWeight("foo"));
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
        assertEquals("m rank(foo) == 3", 3, m.rank("foo"));
        assertEquals("m maximumRank == 3", 3, m.maximumRank());
        assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
    }

    public void testRemove()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.remove("foo");
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
    }

    public void testClear()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        m.clear();

        assertEquals("m size == 0", 0, m.size());
        assertTrue("m isEmpty", m.isEmpty());
        assertFalse("m containsKey foo", m.containsKey("foo"));
        assertFalse("m containsValue 1.0d", m.containsValue(1.0d));

        assertEquals("m keySet size == 0", 0, m.keySet().size());
        assertEquals("m values size == 0", 0, m.values().size());
        assertEquals("m entrySet size == 0", 0, m.entrySet().size());

        assertEquals("m sample == null", null, m.sample());
        assertEquals("m totalWeight == 0.0d", 0.0d, m.totalWeight());
        assertEquals("m get(foo) == null", null, m.get("foo"));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m maximumRank == -1", -1, m.maximumRank());
    }

    public void testPutAll()
    {
        WeightedMap<String> m = createWeightedMap();
        WeightedMap<String> emptyMap = createWeightedMap();
        WeightedMap<String> fullMap = createWeightedMap();

        fullMap.put("foo", 1.0d);
        fullMap.put("bar", 1.0d);
        fullMap.put("baz", 2.0d);

        assertEquals("m size == 0", 0, m.size());

        m.putAll(emptyMap);

        assertEquals("m size == 0", 0, m.size());

        m.putAll(fullMap);

        assertEquals("m size == 3", 3, m.size());
    }

    public void testSample()
    {
        WeightedMap<String> m = createWeightedMap();

        assertEquals("m sample == null", null, m.sample());        

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        int sampledFoo = 0;
        int sampledBar = 0;
        int sampledBaz = 0;
        for (int i = 0; i < 10000; i++)
        {
            String s = m.sample();
            assertNotNull("s not null", s);

            if ("foo".equals(s))
            {
                sampledFoo++;
            }
            if ("bar".equals(s))
            {
                sampledBar++;
            }
            if ("baz".equals(s))
            {
                sampledBaz++;
            }
        }

        // are deltas here appropriate?  don't want the test to fail randomly
        assertEquals("foo sampled 2500 times +/- 500 from 10000", 2500, sampledFoo, 500);
        assertEquals("bar sampled 2500 times +/- 500 from 10000", 2500, sampledBar, 500);
        assertEquals("baz sampled 5000 times +/- 1000 from 10000", 5000, sampledBaz, 1000);
    }

    public void testKeySet()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        // supports Iterator.remove, Set.remove, Set.removeAll, Set.retainAll, Set.clear

        m.keySet().clear();

        assertEquals("m size == 0", 0, m.size());
        assertTrue("m isEmpty", m.isEmpty());
        assertFalse("m containsKey foo", m.containsKey("foo"));
        assertFalse("m containsValue 1.0d", m.containsValue(1.0d));

        assertEquals("m keySet size == 0", 0, m.keySet().size());
        assertEquals("m values size == 0", 0, m.values().size());
        assertEquals("m entrySet size == 0", 0, m.entrySet().size());

        assertEquals("m sample == null", null, m.sample());
        assertEquals("m totalWeight == 0.0d", 0.0d, m.totalWeight());
        assertEquals("m get(foo) == null", null, m.get("foo"));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m maximumRank == -1", -1, m.maximumRank());

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.keySet().remove("foo");
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());

        m.put("foo", 1.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.keySet().removeAll(Arrays.asList(new String[] { "foo" }));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());

        m.put("foo", 1.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.keySet().removeAll(Arrays.asList(new String[] { "foo", "bar" }));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m weight(bar) == null", null, m.weight("bar"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(bar) == -1", -1, m.rank("bar"));        
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m maximumRank == 1", 1, m.maximumRank());
        assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.keySet().retainAll(Arrays.asList(new String[] { "bar", "baz" }));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());

        m.put("foo", 1.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.keySet().retainAll(Arrays.asList(new String[] { "baz" }));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m weight(bar) == null", null, m.weight("bar"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(bar) == -1", -1, m.rank("bar"));        
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m maximumRank == 1", 1, m.maximumRank());
        assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);

        Iterator<String> i = m.keySet().iterator();
        String s = i.next();
        i.remove();
        if ("foo".equals(s))
        {
            assertEquals("m weight(foo) == null", null, m.weight("foo"));
            assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
            assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
            assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
            assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
            assertEquals("m maximumRank == 2", 2, m.maximumRank());
            assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());            
        }
        else if ("bar".equals(s))
        {
            assertEquals("m weight(bar) == null", null, m.weight("bar"));
            assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
            assertEquals("m rank(bar) == -1", -1, m.rank("bar"));
            assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
            assertEquals("m rank(foo) == 2", 2, m.rank("foo"));        
            assertEquals("m maximumRank == 2", 2, m.maximumRank());
            assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
        }
        else if ("baz".equals(s))
        {
            assertEquals("m weight(baz) == null", null, m.weight("baz"));
            assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
            assertEquals("m rank(baz) == -1", -1, m.rank("baz"));
            assertEquals("m rank(foo) == 1", 1, m.rank("foo"));        
            assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
            assertEquals("m maximumRank == 1", 1, m.maximumRank());
            assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());
        }

        // does not support Set.add or Set.addAll
        try
        {
            m.keySet().add("foo");
            fail("keySet add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            m.keySet().addAll(Arrays.asList(new String[] { "foo", "bar" }));
            fail("ketSet addAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testValues()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        // supports Iterator.remove, Collection.remove, Collection.removeAll, Collection.retainAll, Collection.clear

        m.values().clear();

        assertEquals("m size == 0", 0, m.size());
        assertTrue("m isEmpty", m.isEmpty());
        assertFalse("m containsKey foo", m.containsKey("foo"));
        assertFalse("m containsValue 1.0d", m.containsValue(1.0d));

        assertEquals("m keySet size == 0", 0, m.keySet().size());
        assertEquals("m values size == 0", 0, m.values().size());
        assertEquals("m entrySet size == 0", 0, m.entrySet().size());

        assertEquals("m sample == null", null, m.sample());
        assertEquals("m totalWeight == 0.0d", 0.0d, m.totalWeight());
        assertEquals("m get(foo) == null", null, m.get("foo"));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m maximumRank == -1", -1, m.maximumRank());

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.values().remove(1.0d);  // assume that it removes foo
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());

        m.put("foo", 1.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.values().removeAll(Arrays.asList(new Double[] { 1.0d }));  // removes foo and bar
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m weight(bar) == null", null, m.weight("bar"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(bar) == -1", -1, m.rank("bar"));        
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m maximumRank == 1", 1, m.maximumRank());
        assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.values().retainAll(Arrays.asList(new Double[] { 2.0d }));  // removes foo and bar
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m weight(bar) == null", null, m.weight("bar"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m rank(bar) == -1", -1, m.rank("bar"));        
        assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
        assertEquals("m maximumRank == 1", 1, m.maximumRank());
        assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        m.values().retainAll(Arrays.asList(new Double[] { 1.0d }));  // removes baz
        assertEquals("m weight(baz) == null", null, m.weight("baz"));
        assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
        assertEquals("m rank(baz) == -1", -1, m.rank("baz"));
        assertEquals("m rank(foo) == 1", 1, m.rank("foo"));        
        assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
        assertEquals("m maximumRank == 1", 1, m.maximumRank());
        assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());

        m.put("baz", 2.0d);

        Iterator<Double> i = m.values().iterator();
        Double w = i.next();
        i.remove();
        if (w.equals(1.0d))  // assume that it removes foo
        {
            assertEquals("m weight(foo) == null", null, m.weight("foo"));
            assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
            assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
            assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
            assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
            assertEquals("m maximumRank == 2", 2, m.maximumRank());
            assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
        }
        else if (w.equals(2.0d))
        {
            assertEquals("m weight(baz) == null", null, m.weight("baz"));
            assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
            assertEquals("m rank(baz) == -1", -1, m.rank("baz"));
            assertEquals("m rank(foo) == 1", 1, m.rank("foo"));        
            assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
            assertEquals("m maximumRank == 1", 1, m.maximumRank());
            assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());
        }

        // does not support Collection.add or Collection.addAll
        try
        {
            m.values().add(1.0d);
            fail("values add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            m.values().addAll(Arrays.asList(new Double[] { 1.0d, 2.0d }));
            fail("values addAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testEntrySet()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 1.0d);

        // supports Iterator.remove, Set.remove, Set.removeAll, Set.retainAll, Set.clear

        m.entrySet().clear();

        assertEquals("m size == 0", 0, m.size());
        assertTrue("m isEmpty", m.isEmpty());
        assertFalse("m containsKey foo", m.containsKey("foo"));
        assertFalse("m containsValue 1.0d", m.containsValue(1.0d));

        assertEquals("m keySet size == 0", 0, m.keySet().size());
        assertEquals("m values size == 0", 0, m.values().size());
        assertEquals("m entrySet size == 0", 0, m.entrySet().size());

        assertEquals("m sample == null", null, m.sample());
        assertEquals("m totalWeight == 0.0d", 0.0d, m.totalWeight());
        assertEquals("m get(foo) == null", null, m.get("foo"));
        assertEquals("m weight(foo) == null", null, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
        assertEquals("m maximumRank == -1", -1, m.maximumRank());

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m maximumRank == 2", 2, m.maximumRank());
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        {
            Iterator<Map.Entry<String, Double>> i = m.entrySet().iterator();
            Map.Entry<String, Double> e = i.next();
            String s = e.getKey();

            m.entrySet().remove(e);
            if ("foo".equals(s))
            {
                assertEquals("m weight(foo) == null", null, m.weight("foo"));
                assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
                assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
                assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
                assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
                assertEquals("m maximumRank == 2", 2, m.maximumRank());
                assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
            }
            else if ("bar".equals(s))
            {
                assertEquals("m weight(bar) == null", null, m.weight("bar"));
                assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
                assertEquals("m rank(bar) == -1", -1, m.rank("bar"));
                assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
                assertEquals("m rank(foo) == 2", 2, m.rank("foo"));        
                assertEquals("m maximumRank == 2", 2, m.maximumRank());
                assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
            }
            else if ("baz".equals(s))
            {
                assertEquals("m weight(baz) == null", null, m.weight("baz"));
                assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
                assertEquals("m rank(baz) == -1", -1, m.rank("baz"));
                assertEquals("m rank(foo) == 1", 1, m.rank("foo"));        
                assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
                assertEquals("m maximumRank == 1", 1, m.maximumRank());
                assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());
            }
        }

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        {
            Iterator<Map.Entry<String, Double>> i = m.entrySet().iterator();
            Map.Entry<String, Double> e = i.next();
            String s = e.getKey();

            m.entrySet().removeAll(Collections.singleton(e));
            if ("foo".equals(s))
            {
                assertEquals("m weight(foo) == null", null, m.weight("foo"));
                assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
                assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
                assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
                assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
                assertEquals("m maximumRank == 2", 2, m.maximumRank());
                assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
            }
            else if ("bar".equals(s))
            {
                assertEquals("m weight(bar) == null", null, m.weight("bar"));
                assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
                assertEquals("m rank(bar) == -1", -1, m.rank("bar"));
                assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
                assertEquals("m rank(foo) == 2", 2, m.rank("foo"));        
                assertEquals("m maximumRank == 2", 2, m.maximumRank());
                assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
            }
            else if ("baz".equals(s))
            {
                assertEquals("m weight(baz) == null", null, m.weight("baz"));
                assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
                assertEquals("m rank(baz) == -1", -1, m.rank("baz"));
                assertEquals("m rank(foo) == 1", 1, m.rank("foo"));        
                assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
                assertEquals("m maximumRank == 1", 1, m.maximumRank());
                assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());
            }
        }

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        assertEquals("m weight(foo) == 1.0d", 1.0d, m.weight("foo"));
        assertEquals("m normalizedWeight(foo) == 0.25d", 0.25d, m.normalizedWeight("foo"));
        assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
        assertEquals("m totalWeight == 4.0d", 4.0d, m.totalWeight());

        {
            Iterator<Map.Entry<String, Double>> i = m.entrySet().iterator();
            Map.Entry<String, Double> e = i.next();
            String s = e.getKey();

            m.entrySet().retainAll(Collections.singleton(e));
            if ("foo".equals(s))
            {
                assertEquals("m weight(bar) == null", null, m.weight("bar"));
                assertEquals("m weight(baz) == null", null, m.weight("baz"));
                assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
                assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
                assertEquals("m rank(bar) == -1", -1, m.rank("bar"));
                assertEquals("m rank(baz) == -1", -1, m.rank("baz"));        
                assertEquals("m rank(foo) == 1", 1, m.rank("foo"));
                assertEquals("m maximumRank == 1", 1, m.maximumRank());
                assertEquals("m totalWeight == 1.0d", 1.0d, m.totalWeight());
            }
            else if ("bar".equals(s))
            {
                assertEquals("m weight(foo) == null", null, m.weight("foo"));
                assertEquals("m weight(baz) == null", null, m.weight("baz"));
                assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
                assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
                assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
                assertEquals("m rank(baz) == -1", -1, m.rank("baz"));        
                assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
                assertEquals("m maximumRank == 1", 1, m.maximumRank());
                assertEquals("m totalWeight == 1.0d", 1.0d, m.totalWeight());
            }
            else if ("baz".equals(s))
            {
                assertEquals("m weight(foo) == null", null, m.weight("foo"));
                assertEquals("m weight(bar) == null", null, m.weight("bar"));
                assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
                assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
                assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
                assertEquals("m rank(bar) == -1", -1, m.rank("bar"));        
                assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
                assertEquals("m maximumRank == 1", 1, m.maximumRank());
                assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());
            }
        }

        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        {
            Iterator<Map.Entry<String, Double>> i = m.entrySet().iterator();
            Map.Entry<String, Double> e = i.next();
            String s = e.getKey();

            i.remove();
            if ("foo".equals(s))
            {
                assertEquals("m weight(foo) == null", null, m.weight("foo"));
                assertEquals("m normalizedWeight(foo) == null", null, m.normalizedWeight("foo"));
                assertEquals("m rank(foo) == -1", -1, m.rank("foo"));
                assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
                assertEquals("m rank(bar) == 2", 2, m.rank("bar"));        
                assertEquals("m maximumRank == 2", 2, m.maximumRank());
                assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
            }
            else if ("bar".equals(s))
            {
                assertEquals("m weight(bar) == null", null, m.weight("bar"));
                assertEquals("m normalizedWeight(bar) == null", null, m.normalizedWeight("bar"));
                assertEquals("m rank(bar) == -1", -1, m.rank("bar"));
                assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
                assertEquals("m rank(foo) == 2", 2, m.rank("foo"));        
                assertEquals("m maximumRank == 2", 2, m.maximumRank());
                assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
            }
            else if ("baz".equals(s))
            {
                assertEquals("m weight(baz) == null", null, m.weight("baz"));
                assertEquals("m normalizedWeight(baz) == null", null, m.normalizedWeight("baz"));
                assertEquals("m rank(baz) == -1", -1, m.rank("baz"));
                assertEquals("m rank(foo) == 1", 1, m.rank("foo"));        
                assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
                assertEquals("m maximumRank == 1", 1, m.maximumRank());
                assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());
            }
        }

        // does not support Set.add or Set.addAll
        try
        {
            Iterator<Map.Entry<String, Double>> i = m.entrySet().iterator();
            Map.Entry<String, Double> e = i.next();

            m.entrySet().add(e);
            fail("entrySet add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            Iterator<Map.Entry<String, Double>> i = m.entrySet().iterator();
            Map.Entry<String,Double> e = i.next();

            m.entrySet().addAll(Collections.singleton(e));
            fail("entrySet addAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testEntrySetSetValue()
    {
        WeightedMap<String> m = createWeightedMap();
        m.put("foo", 1.0d);
        m.put("bar", 1.0d);
        m.put("baz", 2.0d);

        Iterator<Map.Entry<String, Double>> i = m.entrySet().iterator();
        Map.Entry<String, Double> e = i.next();
        String s = e.getKey();
        Double w = e.setValue(0.0d);

        if ("foo".equals(s))
        {
            assertEquals("foo, w == 1.0d", 1.0d, w);
            assertEquals("m weight(foo) == 0.0d", 0.0d, m.weight("foo"));
            assertEquals("m normalizedWeight(foo) == 0.0d", 0.0d, m.normalizedWeight("foo"));
            assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
            assertEquals("m rank(bar) == 2", 2, m.rank("bar"));
            assertEquals("m rank(foo) == 3", 3, m.rank("foo"));
            assertEquals("m maximumRank == 3", 3, m.maximumRank());
            assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
        }
        else if ("bar".equals(s))
        {
            assertEquals("bar, w == 1.0d", 1.0d, w);
            assertEquals("m weight(bar) == 0.0d", 0.0d, m.weight("bar"));
            assertEquals("m normalizedWeight(bar) == 0.0d", 0.0d, m.normalizedWeight("bar"));
            assertEquals("m rank(baz) == 1", 1, m.rank("baz"));
            assertEquals("m rank(foo) == 2", 2, m.rank("foo"));
            assertEquals("m rank(bar) == 3", 3, m.rank("bar"));
            assertEquals("m maximumRank == 3", 3, m.maximumRank());
            assertEquals("m totalWeight == 3.0d", 3.0d, m.totalWeight());
        }
        else if ("baz".equals(s))
        {
            assertEquals("baz, w == 2.0d", 2.0d, w);
            assertEquals("m weight(baz) == 0.0d", 0.0d, m.weight("baz"));
            assertEquals("m normalizedWeight(baz) == 0.0d", 0.0d, m.normalizedWeight("baz"));
            assertEquals("m rank(foo) == 1", 1, m.rank("foo"));
            assertEquals("m rank(bar) == 1", 1, m.rank("bar"));
            assertEquals("m rank(baz) == 2", 2, m.rank("baz"));
            assertEquals("m maximumRank == 2", 2, m.maximumRank());
            assertEquals("m totalWeight == 2.0d", 2.0d, m.totalWeight());
        }
    }
}
