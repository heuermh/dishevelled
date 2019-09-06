/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.swing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import ca.odell.glazedlists.EventList;

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.impl.ObservableSetImpl;

/**
 * Unit test for ObservableSetEventListAdapter.
 *
 * @author  Michael Heuer
 */
public class ObservableSetEventListAdapterTest extends TestCase
{

    public void testConstructor()
    {
        Set<String> set = new HashSet<String>();
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertNotNull(eventList);

        try
        {
            new ObservableSetEventListAdapter<String>(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAddToEventList()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertEquals(observableSet.size(), eventList.size());
        for (String s : observableSet)
        {
            assertTrue(eventList.contains(s));
        }
        for (String s : eventList)
        {
            assertTrue(observableSet.contains(s));
        }

        observableSet.add("garply");
        assertEquals(observableSet.size(), eventList.size());
        assertTrue(eventList.contains("garply"));
    }

    public void testRemoveFromEventList()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertEquals(observableSet.size(), eventList.size());
        for (String s : observableSet)
        {
            assertTrue(eventList.contains(s));
        }
        for (String s : eventList)
        {
            assertTrue(observableSet.contains(s));
        }

        observableSet.remove("baz");
        assertEquals(observableSet.size(), eventList.size());
        assertFalse(eventList.contains("baz"));
    }

    public void testClear()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertEquals(observableSet.size(), eventList.size());
        for (String s : observableSet)
        {
            assertTrue(eventList.contains(s));
        }
        for (String s : eventList)
        {
            assertTrue(observableSet.contains(s));
        }

        observableSet.clear();
        assertTrue(eventList.isEmpty());
    }

    public void testMultipleAddToEventList()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertEquals(observableSet.size(), eventList.size());
        for (String s : observableSet)
        {
            assertTrue(eventList.contains(s));
        }
        for (String s : eventList)
        {
            assertTrue(observableSet.contains(s));
        }

        for (int i = 0; i < 100; i++)
        {
            observableSet.add("garply" + i);
            assertEquals(observableSet.size(), eventList.size());
            assertTrue(eventList.contains("garply" + i));
        }
    }

    public void testMultipleRemoveFromEventList()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        for (int i = 0; i < 100; i++)
        {
            set.add("garply" + i);
        }
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertEquals(observableSet.size(), eventList.size());
        for (String s : observableSet)
        {
            assertTrue(eventList.contains(s));
        }
        for (String s : eventList)
        {
            assertTrue(observableSet.contains(s));
        }

        for (int i = 0; i < 100; i++)
        {
            observableSet.remove("garply" + i);
            assertEquals(observableSet.size(), eventList.size());
            assertFalse(eventList.contains("garply" + i));
        }
    }

    public void testAddAll()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertEquals(observableSet.size(), eventList.size());
        for (String s : observableSet)
        {
            assertTrue(eventList.contains(s));
        }
        for (String s : eventList)
        {
            assertTrue(observableSet.contains(s));
        }

        List<String> toAdd = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            toAdd.add("garply" + i);
        }
        observableSet.addAll(toAdd);
        assertEquals(observableSet.size(), eventList.size());
    }

    public void testRemoveAll()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        for (int i = 0; i < 100; i++)
        {
            set.add("garply" + i);
        }
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);
        assertEquals(observableSet.size(), eventList.size());
        for (String s : observableSet)
        {
            assertTrue(eventList.contains(s));
        }
        for (String s : eventList)
        {
            assertTrue(observableSet.contains(s));
        }

        List<String> toRemove = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            toRemove.add("garply" + i);
        }
        observableSet.removeAll(toRemove);
        assertEquals(observableSet.size(), eventList.size());
    }

    public void testEventListIsImmutable()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        ObservableSet<String> observableSet = new ObservableSetImpl<String>(set);
        EventList<String> eventList = new ObservableSetEventListAdapter<String>(observableSet);

        try
        {
            eventList.clear();
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.add("garply");
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.add(0, "garply");
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.remove("foo");
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.remove(0);
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.addAll(Collections.singleton("garply"));
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.addAll(0, Collections.singleton("garply"));
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.removeAll(Collections.singleton("foo"));
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.retainAll(Collections.singleton("foo"));
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            eventList.set(0, "garply");
            fail("clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        // todo  iterator remove, listIterator remove, subList remove,
        //    subList iterator remove, subList listIterator remove, etc.
    }
}
