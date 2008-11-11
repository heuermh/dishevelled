/*

    dsh-collect  Collection and map utility classes.
    Copyright (c) 2008 held jointly by the individual authors.

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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import junit.framework.TestCase;

import static org.dishevelled.collect.Sets.*;

/**
 * Unit test for Sets.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SetsTest
    extends TestCase
{
    /** String comparator. */
    private static final Comparator<String> COMPARATOR = new Comparator<String>()
        {
            /** {@inheritDoc} */
            public int compare(final String s0, final String s1)
            {
                if ((s0 == null) && (s1 == null))
                {
                    return 0;
                }
                if ((s0 == null) && (s1 != null))
                {
                    return -1;
                }
                if ((s0 != null) && (s1 == null))
                {
                    return 1;
                }
                return s0.compareTo(s1);
            }
        };

    public void testAsSet()
    {
        Set<String> set0 = asSet("foo", "foo");
        assertNotNull(set0);
        assertEquals(1, set0.size());

        Set<String> set1 = asSet("foo", "bar", "foo");
        assertNotNull(set1);
        assertEquals(2, set1.size());

        Set<String> set2 = asSet("foo", "bar", "baz", "foo");
        assertNotNull(set2);
        assertEquals(3, set2.size());

        Set<String> set3 = asSet((String) null);
        assertNotNull(set3);
        assertTrue(set3.contains(null));

        Set<String> set4 = asSet(new String[] { "foo", "bar", "baz", "foo" });
        assertNotNull(set4);
        assertEquals(3, set4.size());

        Set<String> set5 = asSet(set4.iterator());
        assertNotNull(set5);

        Set<String> set6 = asSet(set4);
        assertNotNull(set6);

        try
        {
            Set<String> set = asSet((Iterator<String>) null);
            fail("asSet((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Set<String> set = asSet((Iterable<String>) null);
            fail("asSet((Iterable<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAsSortedSet()
    {
        SortedSet<String> set0 = asSortedSet("foo", "foo");
        assertNotNull(set0);
        assertEquals(1, set0.size());

        SortedSet<String> set1 = asSortedSet("foo", "bar", "foo");
        assertNotNull(set1);
        assertEquals(2, set1.size());

        SortedSet<String> set2 = asSortedSet("foo", "bar", "baz", "foo");
        assertNotNull(set2);
        assertEquals(3, set2.size());

        SortedSet<String> set4 = asSortedSet(new String[] { "foo", "bar", "baz", "foo" });
        assertNotNull(set4);
        assertEquals(3, set4.size());

        SortedSet<String> set5 = asSortedSet(set4.iterator());
        assertNotNull(set5);

        SortedSet<String> set6 = asSortedSet(set4);
        assertNotNull(set6);

        SortedSet<String> set7 = asSortedSet(set4.iterator(), COMPARATOR);
        assertNotNull(set7);

        SortedSet<String> set8 = asSortedSet(set4.iterator(), null);
        assertNotNull(set8);

        SortedSet<String> set9 = asSortedSet(set4, COMPARATOR);
        assertNotNull(set9);

        SortedSet<String> set10 = asSortedSet(set4, null);
        assertNotNull(set10);

        try
        {
            SortedSet<String> set = asSortedSet((Iterator<String>) null);
            fail("asSortedSet((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            SortedSet<String> set = asSortedSet((Iterable<String>) null);
            fail("asSortedSet((Iterable<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            SortedSet<String> set = asSortedSet((Iterator<String>) null, COMPARATOR);
            fail("asSortedSet((Iterator<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            SortedSet<String> set = asSortedSet((Iterable<String>) null, COMPARATOR);
            fail("asSortedSet((Iterable<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testNewSet()
    {
        Set<String> set0 = newSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        Set<String> set1 = newSet(elements);
        assertNotNull(set1);

        Set<String> set2 = newSet(10);
        assertNotNull(set2);

        Set<String> set3 = newSet(1000);
        assertNotNull(set3);

        Set<String> set4 = newSet(10, 0.90f);
        assertNotNull(set4);

        Set<String> set5 = newSet(1000, 0.50f);
        assertNotNull(set5);

        try
        {
            Set<String> set = newSet((Collection<String>) null);
            fail("newSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testNewSortedSet()
    {
        SortedSet<String> set0 = newSortedSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        SortedSet<String> set1 = newSortedSet(elements);
        assertNotNull(set1);

        SortedSet<String> set2 = newSortedSet(COMPARATOR);
        assertNotNull(set2);

        SortedSet<String> set3 = newSortedSet((Comparator<String>) null);
        assertNotNull(set3);

        SortedSet<String> set4 = newSortedSet(elements, COMPARATOR);
        assertNotNull(set4);

        SortedSet<String> set5 = newSortedSet(elements, null);
        assertNotNull(set5);

        SortedSet<String> set6 = newSortedSet(set1);
        assertNotNull(set6);

        try
        {
            SortedSet<String> set = newSortedSet((Collection<String>) null);
            fail("newSortedSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            SortedSet<String> set = newSortedSet((SortedSet<String>) null);
            fail("newSortedSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            SortedSet<String> set = newSortedSet((Collection<String>) null, COMPARATOR);
            fail("newSortedSet((Collection<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            SortedSet<String> set = newSortedSet((SortedSet<String>) null, COMPARATOR);
            fail("newSortedSet((SortedSet<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testNewNavigableSet()
    {
        NavigableSet<String> set0 = newNavigableSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        NavigableSet<String> set1 = newNavigableSet(elements);
        assertNotNull(set1);

        NavigableSet<String> set2 = newNavigableSet(COMPARATOR);
        assertNotNull(set2);

        NavigableSet<String> set3 = newNavigableSet((Comparator<String>) null);
        assertNotNull(set3);

        NavigableSet<String> set4 = newNavigableSet(elements, COMPARATOR);
        assertNotNull(set4);

        NavigableSet<String> set5 = newNavigableSet(elements, null);
        assertNotNull(set5);

        SortedSet<String> sortedSet = asSortedSet("foo", "bar", "baz");
        NavigableSet<String> set6 = newNavigableSet(sortedSet);
        assertNotNull(set6);

        try
        {
            NavigableSet<String> set = newNavigableSet((Collection<String>) null);
            fail("newNavigableSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            NavigableSet<String> set = newNavigableSet((SortedSet<String>) null);
            fail("newNavigableSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            NavigableSet<String> set = newNavigableSet((Collection<String>) null, COMPARATOR);
            fail("newNavigableSet((Collection<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            NavigableSet<String> set = newNavigableSet((SortedSet<String>) null, COMPARATOR);
            fail("newNavigableSet((SortedSet<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testNewConcurrentSkipListSet()
    {
        ConcurrentSkipListSet<String> set0 = newConcurrentSkipListSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        ConcurrentSkipListSet<String> set1 = newConcurrentSkipListSet(elements);
        assertNotNull(set1);

        ConcurrentSkipListSet<String> set2 = newConcurrentSkipListSet(COMPARATOR);
        assertNotNull(set2);

        ConcurrentSkipListSet<String> set3 = newConcurrentSkipListSet((Comparator<String>) null);
        assertNotNull(set3);

        ConcurrentSkipListSet<String> set4 = newConcurrentSkipListSet(elements, COMPARATOR);
        assertNotNull(set4);

        ConcurrentSkipListSet<String> set5 = newConcurrentSkipListSet(elements, null);
        assertNotNull(set5);

        SortedSet<String> sortedSet = asSortedSet("foo", "bar", "baz");
        ConcurrentSkipListSet<String> set6 = newConcurrentSkipListSet(sortedSet);
        assertNotNull(set6);

        try
        {
            ConcurrentSkipListSet<String> set = newConcurrentSkipListSet((Collection<String>) null);
            fail("newConcurrentSkipListSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            ConcurrentSkipListSet<String> set = newConcurrentSkipListSet((SortedSet<String>) null);
            fail("newConcurrentSkipListSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            ConcurrentSkipListSet<String> set = newConcurrentSkipListSet((Collection<String>) null, COMPARATOR);
            fail("newConcurrentSkipListSet((Collection<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            ConcurrentSkipListSet<String> set = newConcurrentSkipListSet((SortedSet<String>) null, COMPARATOR);
            fail("newConcurrentSkipListSet((SortedSet<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testNewCopyOnWriteArraySet()
    {
        CopyOnWriteArraySet<String> set0 = newCopyOnWriteArraySet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        CopyOnWriteArraySet<String> set1 = newCopyOnWriteArraySet(elements);
        assertNotNull(set1);

        try
        {
            CopyOnWriteArraySet<String> set = newCopyOnWriteArraySet((Collection<String>) null);
            fail("newCopyOnWriteArraySet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testNewNonBlockingSet()
    {
        Set<String> set0 = newNonBlockingSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        Set<String> set1 = newNonBlockingSet(elements);
        assertNotNull(set1);

        try
        {
            Set<String> set = newNonBlockingSet((Collection<String>) null);
            fail("newNonBlockingSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testSingleton()
    {
        Set<String> set0 = singleton("foo");
        assertNotNull(set0);

        Set<String> set1 = singleton(null);
        assertNotNull(set1);
    }

    public void testSingletonSet()
    {
        Set<String> set0 = singletonSet("foo");
        assertNotNull(set0);

        Set<String> set1 = singletonSet(null);
        assertNotNull(set1);
    }

    public void testUnmodifiableSet()
    {
        Set<String> toView = asSet("foo", "bar", "baz");
        Set<String> unmodifiableSet = unmodifiableSet(toView);
        assertNotNull(unmodifiableSet);
        // TODO:  add'l assertions

        try
        {
            Set<String> set = unmodifiableSet((Set<String>) null);
            fail("unmodifiableSet((Set<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testUnmodifiableSortedSet()
    {
        SortedSet<String> toView = asSortedSet("foo", "bar", "baz");
        SortedSet<String> unmodifiableSortedSet = unmodifiableSortedSet(toView);
        assertNotNull(unmodifiableSortedSet);
        // TODO:  add'l assertions

        try
        {
            SortedSet<String> sortedSet = unmodifiableSortedSet((SortedSet<String>) null);
            fail("unmodifiableSortedSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }
}