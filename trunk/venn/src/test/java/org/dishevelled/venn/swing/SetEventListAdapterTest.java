/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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

/**
 * Unit test for SetEventListAdapter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SetEventListAdapterTest extends TestCase
{

    public void testConstructor()
    {
        Set<String> set = new HashSet<String>();
        EventList<String> eventList = new SetEventListAdapter<String>(set);
        assertNotNull(eventList);

        try
        {
            new SetEventListAdapter<String>(null);
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
        SetEventListAdapter<String> adapter = new SetEventListAdapter<String>(set);
        assertEquals(set.size(), adapter.size());
        for (String s : set)
        {
            assertTrue(adapter.contains(s));
        }
        for (String s : adapter)
        {
            assertTrue(set.contains(s));
        }

        set.add("garply");
        adapter.updateEventList();
        assertEquals(set.size(), adapter.size());
        assertTrue(adapter.contains("garply"));
    }

    public void testRemoveFromEventList()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        SetEventListAdapter<String> adapter = new SetEventListAdapter<String>(set);
        assertEquals(set.size(), adapter.size());
        for (String s : set)
        {
            assertTrue(adapter.contains(s));
        }
        for (String s : adapter)
        {
            assertTrue(set.contains(s));
        }

        set.remove("baz");
        adapter.updateEventList();
        assertEquals(set.size(), adapter.size());
        assertFalse(adapter.contains("baz"));
    }

    public void testClear()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        SetEventListAdapter<String> adapter = new SetEventListAdapter<String>(set);
        assertEquals(set.size(), adapter.size());
        for (String s : set)
        {
            assertTrue(adapter.contains(s));
        }
        for (String s : adapter)
        {
            assertTrue(set.contains(s));
        }

        set.clear();
        adapter.updateEventList();
        assertTrue(adapter.isEmpty());
    }

    public void testMultipleAddToEventList()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        SetEventListAdapter<String> adapter = new SetEventListAdapter<String>(set);
        assertEquals(set.size(), adapter.size());
        for (String s : set)
        {
            assertTrue(adapter.contains(s));
        }
        for (String s : adapter)
        {
            assertTrue(set.contains(s));
        }

        for (int i = 0; i < 100; i++)
        {
            set.add("garply" + i);
            adapter.updateEventList();
            assertEquals(set.size(), adapter.size());
            assertTrue(adapter.contains("garply" + i));
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
        SetEventListAdapter<String> adapter = new SetEventListAdapter<String>(set);
        assertEquals(set.size(), adapter.size());
        for (String s : set)
        {
            assertTrue(adapter.contains(s));
        }
        for (String s : adapter)
        {
            assertTrue(set.contains(s));
        }

        for (int i = 0; i < 100; i++)
        {
            set.remove("garply" + i);
            adapter.updateEventList();
            assertEquals(set.size(), adapter.size());
            assertFalse(adapter.contains("garply" + i));
        }
    }

    public void testAddAll()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        SetEventListAdapter<String> adapter = new SetEventListAdapter<String>(set);
        assertEquals(set.size(), adapter.size());
        for (String s : set)
        {
            assertTrue(adapter.contains(s));
        }
        for (String s : adapter)
        {
            assertTrue(set.contains(s));
        }

        List<String> toAdd = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            toAdd.add("garply" + i);
        }
        set.addAll(toAdd);
        adapter.updateEventList();
        assertEquals(set.size(), adapter.size());
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
        SetEventListAdapter<String> adapter = new SetEventListAdapter<String>(set);
        assertEquals(set.size(), adapter.size());
        for (String s : set)
        {
            assertTrue(adapter.contains(s));
        }
        for (String s : adapter)
        {
            assertTrue(set.contains(s));
        }

        List<String> toRemove = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            toRemove.add("garply" + i);
        }
        set.removeAll(toRemove);
        adapter.updateEventList();
        assertEquals(set.size(), adapter.size());
    }

    public void testEventListIsImmutable()
    {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        set.add("baz");
        set.add("qux");
        EventList<String> eventList = new SetEventListAdapter<String>(set);

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
