/*

    dsh-collect  Collection and map utility classes.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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
package org.dishevelled.collect;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import junit.framework.TestCase;

import static org.dishevelled.collect.Maps.*;

/**
 * Unit test for Maps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class MapsTest
    extends TestCase
{

    public void testEmptyMap()
    {
        Map<String, Double> empty = emptyMap();
        assertNotNull(empty);
        assertTrue(empty.isEmpty());
        assertEquals(0, empty.size());

        empty.clear();
        try
        {
            empty.put("foo", Double.valueOf(1.0d));
            fail("empty put expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        // TODO:  add'l assertions
    }

    public void testCreateMap()
    {
        Map<String, Double> map0 = createMap();
        assertNotNull(map0);

        Map<String, Double> map1 = createMap(10);
        assertNotNull(map1);

        Map<String, Double> map2 = createMap(10, 0.80f);
        assertNotNull(map2);

        Map<String, Double> toCopy = createMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        Map<String, Double> map3 = createMap(toCopy);
        assertNotNull(map3);
    }

    public void testCreateSynchronizedMap()
    {
        Map<String, Double> map0 = createSynchronizedMap();
        assertNotNull(map0);

        Map<String, Double> map1 = createSynchronizedMap(10);
        assertNotNull(map1);

        Map<String, Double> map2 = createSynchronizedMap(10, 0.80f);
        assertNotNull(map2);

        Map<String, Double> toCopy = createMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        Map<String, Double> map3 = createSynchronizedMap(toCopy);
        assertNotNull(map3);
    }

    public void testCreateConcurrentMap()
    {
        ConcurrentMap<String, Double> concurrentMap0 = createConcurrentMap();
        assertNotNull(concurrentMap0);

        ConcurrentMap<String, Double> concurrentMap1 = createConcurrentMap(10);
        assertNotNull(concurrentMap1);

        ConcurrentMap<String, Double> concurrentMap2 = createConcurrentMap(10, 0.80f);
        assertNotNull(concurrentMap2);

        ConcurrentMap<String, Double> concurrentMap3 = createConcurrentMap(10, 0.80f, 5);
        assertNotNull(concurrentMap3);

        Map<String, Double> toCopy = createMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        ConcurrentMap<String, Double> concurrentMap4 = createConcurrentMap(toCopy);
        assertNotNull(concurrentMap4);
    }

    public void testCreateNonBlockingMap()
    {
        ConcurrentMap<String, Double> concurrentMap0 = createNonBlockingMap();
        assertNotNull(concurrentMap0);

        ConcurrentMap<String, Double> concurrentMap1 = createNonBlockingMap(10);
        assertNotNull(concurrentMap1);
    }

    public void testCreateLongNonBlockingMap()
    {
        ConcurrentMap<Long, Double> concurrentMap0 = createLongNonBlockingMap();
        assertNotNull(concurrentMap0);

        ConcurrentMap<Long, Double> concurrentMap1 = createLongNonBlockingMap(true);
        assertNotNull(concurrentMap1);

        ConcurrentMap<Long, Double> concurrentMap2 = createLongNonBlockingMap(false);
        assertNotNull(concurrentMap2);

        ConcurrentMap<Long, Double> concurrentMap3 = createLongNonBlockingMap(10);
        assertNotNull(concurrentMap3);

        ConcurrentMap<Long, Double> concurrentMap4 = createLongNonBlockingMap(10, true);
        assertNotNull(concurrentMap4);

        ConcurrentMap<Long, Double> concurrentMap5 = createLongNonBlockingMap(10, false);
        assertNotNull(concurrentMap5);
    }

    public void testCreateConcurrentSkipListMap()
    {
        ConcurrentSkipListMap<String, Double> concurrentSkipListMap0 = createConcurrentSkipListMap();
        assertNotNull(concurrentSkipListMap0);

        Comparator<String> comparator = new Comparator<String>()
            {
                /** {@inheritDoc} */
                public int compare(final String value0, final String value1)
                {
                    return value0.compareTo(value1);
                }
            };

        ConcurrentSkipListMap<String, Double> concurrentSkipListMap1 = createConcurrentSkipListMap(comparator);
        assertNotNull(concurrentSkipListMap1);

        Map<String, Double> toCopy = createMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        ConcurrentSkipListMap<String, Double> concurrentSkipListMap2 = createConcurrentSkipListMap(toCopy);
        assertNotNull(concurrentSkipListMap2);

        SortedMap<String, Double> sortedToCopy = createSortedMap();
        sortedToCopy.put("foo", Double.valueOf(1.0d));
        sortedToCopy.put("bar", Double.valueOf(2.0d));

        ConcurrentSkipListMap<String, Double> concurrentSkipListMap3 = createConcurrentSkipListMap(sortedToCopy);
        assertNotNull(concurrentSkipListMap3);
    }

    public void testCreateSortedMap()
    {
        SortedMap<String, Double> sortedMap0 = createSortedMap();
        assertNotNull(sortedMap0);

        Comparator<String> comparator = new Comparator<String>()
            {
                /** {@inheritDoc} */
                public int compare(final String value0, final String value1)
                {
                    return value0.compareTo(value1);
                }
            };

        SortedMap<String, Double> sortedMap1 = createSortedMap(comparator);
        assertNotNull(sortedMap1);

        SortedMap<String, Double> sortedMap2 = createSortedMap((Comparator<String>) null);
        assertNotNull(sortedMap2);

        Map<String, Double> toCopy = createMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        SortedMap<String, Double> sortedMap3 = createSortedMap(toCopy);
        assertNotNull(sortedMap3);

        SortedMap<String, Double> sortedToCopy = createSortedMap();
        sortedToCopy.put("foo", Double.valueOf(1.0d));
        sortedToCopy.put("bar", Double.valueOf(2.0d));

        SortedMap<String, Double> sortedMap4 = createSortedMap(sortedToCopy);
        assertNotNull(sortedMap4);

        try
        {
            createSortedMap((Map<String, Double>) null);
            fail("newSortedMap((Map<String, Double>) null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }

        try
        {
            createSortedMap((SortedMap<String, Double>) null);
            fail("newSortedMap((SortedMap<String, Double>) null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testUnmodifiableMap()
    {
        Map<String,  Double> toView = createMap();
        toView.put("foo", Double.valueOf(1.0d));
        toView.put("bar", Double.valueOf(2.0d));

        Map<String, Double> unmodifiableMap = unmodifiableMap(toView);
        assertNotNull(unmodifiableMap);

        try
        {
            unmodifiableMap(null);
            fail("unmodifiableMap(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testUnmodifiableSortedMap()
    {
        SortedMap<String, Double> toView = createSortedMap();
        toView.put("foo", Double.valueOf(1.0d));
        toView.put("bar", Double.valueOf(2.0d));

        SortedMap<String, Double> unmodifiableSortedMap = unmodifiableSortedMap(toView);
        assertNotNull(unmodifiableSortedMap);

        try
        {
            unmodifiableSortedMap(null);
            fail("unmodifiableSortedMap(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testImmutableMap()
    {
        Map<String,  Double> toCopy = createMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        Map<String, Double> immutableMap = immutableMap(toCopy);
        assertNotNull(immutableMap);

        try
        {
            immutableMap(null);
            fail("immutableMap(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testImmutableSortedMap()
    {
        SortedMap<String, Double> toCopy = createSortedMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        SortedMap<String, Double> immutableSortedMap = immutableSortedMap(toCopy);
        assertNotNull(immutableSortedMap);

        try
        {
            immutableSortedMap(null);
            fail("immutableSortedMap(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateNavigableMap()
    {
        NavigableMap<String, Double> navigableMap0 = createNavigableMap();
        assertNotNull(navigableMap0);

        Comparator<String> comparator = new Comparator<String>()
            {
                /** {@inheritDoc} */
                public int compare(final String value0, final String value1)
                {
                    return value0.compareTo(value1);
                }
            };

        NavigableMap<String, Double> navigableMap1 = createNavigableMap(comparator);
        assertNotNull(navigableMap1);

        NavigableMap<String, Double> navigableMap2 = createNavigableMap((Comparator<String>) null);
        assertNotNull(navigableMap2);

        Map<String, Double> toCopy = createMap();
        toCopy.put("foo", Double.valueOf(1.0d));
        toCopy.put("bar", Double.valueOf(2.0d));

        NavigableMap<String, Double> navigableMap3 = createNavigableMap(toCopy);
        assertNotNull(navigableMap3);

        NavigableMap<String, Double> navigableToCopy = createNavigableMap();
        navigableToCopy.put("foo", Double.valueOf(1.0d));
        navigableToCopy.put("bar", Double.valueOf(2.0d));

        NavigableMap<String, Double> navigableMap4 = createNavigableMap(navigableToCopy);
        assertNotNull(navigableMap4);

        try
        {
            createNavigableMap((Map<String, Double>) null);
            fail("newNavigableMap((Map<String, Double>) null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }

        try
        {
            createNavigableMap((NavigableMap<String, Double>) null);
            fail("newNavigableMap((NavigableMap<String, Double>) null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }
}