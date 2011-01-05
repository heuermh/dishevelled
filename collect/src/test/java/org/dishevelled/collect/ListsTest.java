/*

    dsh-collect  Collection and map utility classes.
    Copyright (c) 2008-2011 held jointly by the individual authors.

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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;

import junit.framework.TestCase;

import static org.dishevelled.collect.Lists.*;

/**
 * Unit test for Lists.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ListsTest
    extends TestCase
{

    public void testEmptyList()
    {
        List<String> empty = emptyList();
        assertNotNull(empty);
        assertTrue(empty.isEmpty());
        assertEquals(0, empty.size());

        empty.clear();
        try
        {
            empty.add("foo");
            fail("empty add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        // TODO:  add'l assertions
    }

    public void testAsList()
    {
        List<String> list0 = asList("foo");
        assertNotNull(list0);

        List<String> list1 = asList("foo", "bar");
        assertNotNull(list1);

        List<String> list2 = asList("foo", "bar", "baz");
        assertNotNull(list2);

        List<String> list3 = asList((String) null);
        assertNotNull(list3);
        assertTrue(list3.contains(null));

        List<String> list4 = asList(new String[] { "foo", "bar", "baz" });
        assertNotNull(list4);

        List<String> list5 = asList(list4.iterator());
        assertNotNull(list5);

        List<String> list6 = asList(list4);
        assertNotNull(list6);

        try
        {
            asList((Iterator<String>) null);
            fail("asList((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asList((Iterable<String>) null);
            fail("asList((Iterable<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAsImmutableList()
    {
        List<String> immutableList0 = asImmutableList("foo");
        assertNotNull(immutableList0);

        List<String> immutableList1 = asImmutableList("foo", "bar");
        assertNotNull(immutableList1);

        List<String> immutableList2 = asImmutableList("foo", "bar", "baz");
        assertNotNull(immutableList2);

        List<String> immutableList4 = asImmutableList(new String[] { "foo", "bar", "baz" });
        assertNotNull(immutableList4);

        List<String> immutableList5 = asImmutableList(immutableList4.iterator());
        assertNotNull(immutableList5);

        List<String> immutableList6 = asImmutableList(immutableList4);
        assertNotNull(immutableList6);

        try
        {
            asImmutableList((String) null);
            fail("asImmutableList((String) null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected, ImmutableList does not accept null elements
        }
        try
        {
            asImmutableList((Iterator<String>) null);
            fail("asImmutableList((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asImmutableList((Iterable<String>) null);
            fail("asImmutableList((Iterable<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateList()
    {
        List<String> list0 = createList();
        assertNotNull(list0);

        Collection<String> elements = asList("foo", "bar", "baz");
        List<String> list1 = createList(elements);
        assertNotNull(list1);

        List<String> list2 = createList(10);
        assertNotNull(list2);

        List<String> list3 = createList(1000);
        assertNotNull(list3);

        try
        {
            createList((Collection<String>) null);
            fail("newList((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateLinkedList()
    {
        LinkedList<String> list0 = createLinkedList();
        assertNotNull(list0);

        Collection<String> elements = asList("foo", "bar", "baz");
        LinkedList<String> list1 = createLinkedList(elements);
        assertNotNull(list1);

        try
        {
            createLinkedList((Collection<String>) null);
            fail("newLinkedList((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateCopyOnWriteArrayList()
    {
        CopyOnWriteArrayList<String> list0 = createCopyOnWriteArrayList();
        assertNotNull(list0);

        Collection<String> elements = asList("foo", "bar", "baz");
        CopyOnWriteArrayList<String> list1 = createCopyOnWriteArrayList(elements);
        assertNotNull(list1);

        String[] elementArray = new String[] { "foo", "bar", "baz" };
        CopyOnWriteArrayList<String> list2 = createCopyOnWriteArrayList(elementArray);
        assertNotNull(list2);

        try
        {
            createCopyOnWriteArrayList((Collection<String>) null);
            fail("newCopyOnWriteArrayList((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createCopyOnWriteArrayList((String[]) null);
            fail("newCopyOnWriteArrayList((String[]) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testUnmodifiableList()
    {
        List<String> toView = asList("foo", "bar", "baz");
        List<String> unmodifiableList = unmodifiableList(toView);
        assertNotNull(unmodifiableList);
        // TODO:  add'l assertions

        try
        {
            unmodifiableList((List<String>) null);
            fail("unmodifiableList((List<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testImmutableList()
    {
        List<String> toCopy = asList("foo", "bar", "baz");
        List<String> immutableList = immutableList(toCopy);
        assertNotNull(immutableList);
        // TODO:  add'l assertions

        try
        {
            immutableList((List<String>) null);
            fail("immutableList((List<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }
}