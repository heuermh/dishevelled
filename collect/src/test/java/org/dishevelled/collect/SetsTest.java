/*

    dsh-collect  Collection and map utility classes.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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

    public void testEmptySet()
    {
        Set<String> empty = emptySet();
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

        Set<String> set7 = asSet("foo");
        assertNotNull(set7);
        assertEquals(1, set7.size());

        try
        {
            asSet((Iterator<String>) null);
            fail("asSet((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asSet((Iterable<String>) null);
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

        SortedSet<String> set11 = asSortedSet("foo");
        assertNotNull(set11);
        assertEquals(1, set11.size());

        try
        {
            asSortedSet((Iterator<String>) null);
            fail("asSortedSet((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asSortedSet((Iterable<String>) null);
            fail("asSortedSet((Iterable<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asSortedSet((Iterator<String>) null, COMPARATOR);
            fail("asSortedSet((Iterator<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asSortedSet((Iterable<String>) null, COMPARATOR);
            fail("asSortedSet((Iterable<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateSet()
    {
        Set<String> set0 = createSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        Set<String> set1 = createSet(elements);
        assertNotNull(set1);

        Set<String> set2 = createSet(10);
        assertNotNull(set2);

        Set<String> set3 = createSet(1000);
        assertNotNull(set3);

        Set<String> set4 = createSet(10, 0.90f);
        assertNotNull(set4);

        Set<String> set5 = createSet(1000, 0.50f);
        assertNotNull(set5);

        try
        {
            createSet((Collection<String>) null);
            fail("newSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateSortedSet()
    {
        SortedSet<String> set0 = createSortedSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        SortedSet<String> set1 = createSortedSet(elements);
        assertNotNull(set1);

        SortedSet<String> set2 = createSortedSet(COMPARATOR);
        assertNotNull(set2);

        SortedSet<String> set3 = createSortedSet((Comparator<String>) null);
        assertNotNull(set3);

        SortedSet<String> set4 = createSortedSet(elements, COMPARATOR);
        assertNotNull(set4);

        SortedSet<String> set5 = createSortedSet(elements, null);
        assertNotNull(set5);

        SortedSet<String> set6 = createSortedSet(set1);
        assertNotNull(set6);

        try
        {
            createSortedSet((Collection<String>) null);
            fail("newSortedSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createSortedSet((SortedSet<String>) null);
            fail("newSortedSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createSortedSet((Collection<String>) null, COMPARATOR);
            fail("newSortedSet((Collection<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createSortedSet((SortedSet<String>) null, COMPARATOR);
            fail("newSortedSet((SortedSet<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateNavigableSet()
    {
        NavigableSet<String> set0 = createNavigableSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        NavigableSet<String> set1 = createNavigableSet(elements);
        assertNotNull(set1);

        NavigableSet<String> set2 = createNavigableSet(COMPARATOR);
        assertNotNull(set2);

        NavigableSet<String> set3 = createNavigableSet((Comparator<String>) null);
        assertNotNull(set3);

        NavigableSet<String> set4 = createNavigableSet(elements, COMPARATOR);
        assertNotNull(set4);

        NavigableSet<String> set5 = createNavigableSet(elements, null);
        assertNotNull(set5);

        SortedSet<String> sortedSet = asSortedSet("foo", "bar", "baz");
        NavigableSet<String> set6 = createNavigableSet(sortedSet);
        assertNotNull(set6);

        try
        {
            createNavigableSet((Collection<String>) null);
            fail("newNavigableSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createNavigableSet((SortedSet<String>) null);
            fail("newNavigableSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createNavigableSet((Collection<String>) null, COMPARATOR);
            fail("newNavigableSet((Collection<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createNavigableSet((SortedSet<String>) null, COMPARATOR);
            fail("newNavigableSet((SortedSet<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateConcurrentSkipListSet()
    {
        ConcurrentSkipListSet<String> set0 = createConcurrentSkipListSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        ConcurrentSkipListSet<String> set1 = createConcurrentSkipListSet(elements);
        assertNotNull(set1);

        ConcurrentSkipListSet<String> set2 = createConcurrentSkipListSet(COMPARATOR);
        assertNotNull(set2);

        ConcurrentSkipListSet<String> set3 = createConcurrentSkipListSet((Comparator<String>) null);
        assertNotNull(set3);

        ConcurrentSkipListSet<String> set4 = createConcurrentSkipListSet(elements, COMPARATOR);
        assertNotNull(set4);

        ConcurrentSkipListSet<String> set5 = createConcurrentSkipListSet(elements, null);
        assertNotNull(set5);

        SortedSet<String> sortedSet = asSortedSet("foo", "bar", "baz");
        ConcurrentSkipListSet<String> set6 = createConcurrentSkipListSet(sortedSet);
        assertNotNull(set6);

        try
        {
            createConcurrentSkipListSet((Collection<String>) null);
            fail("newConcurrentSkipListSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createConcurrentSkipListSet((SortedSet<String>) null);
            fail("newConcurrentSkipListSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createConcurrentSkipListSet((Collection<String>) null, COMPARATOR);
            fail("newConcurrentSkipListSet((Collection<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            createConcurrentSkipListSet((SortedSet<String>) null, COMPARATOR);
            fail("newConcurrentSkipListSet((SortedSet<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateCopyOnWriteArraySet()
    {
        CopyOnWriteArraySet<String> set0 = createCopyOnWriteArraySet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        CopyOnWriteArraySet<String> set1 = createCopyOnWriteArraySet(elements);
        assertNotNull(set1);

        try
        {
            createCopyOnWriteArraySet((Collection<String>) null);
            fail("newCopyOnWriteArraySet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testCreateNonBlockingSet()
    {
        Set<String> set0 = createNonBlockingSet();
        assertNotNull(set0);

        Collection<String> elements = asSet("foo", "bar", "baz");
        Set<String> set1 = createNonBlockingSet(elements);
        assertNotNull(set1);

        try
        {
            createNonBlockingSet((Collection<String>) null);
            fail("newNonBlockingSet((Collection<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testUnmodifiableSet()
    {
        Set<String> toView = asSet("foo", "bar", "baz");
        Set<String> unmodifiableSet = unmodifiableSet(toView);
        assertNotNull(unmodifiableSet);
        // TODO:  add'l assertions

        try
        {
            unmodifiableSet((Set<String>) null);
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
            unmodifiableSortedSet((SortedSet<String>) null);
            fail("unmodifiableSortedSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testAsImmutableSet()
    {
        Set<String> set0 = asImmutableSet("foo", "foo");
        assertNotNull(set0);
        assertEquals(1, set0.size());

        Set<String> set1 = asImmutableSet("foo", "bar", "foo");
        assertNotNull(set1);
        assertEquals(2, set1.size());

        Set<String> set2 = asImmutableSet("foo", "bar", "baz", "foo");
        assertNotNull(set2);
        assertEquals(3, set2.size());

        Set<String> set4 = asImmutableSet(new String[] { "foo", "bar", "baz", "foo" });
        assertNotNull(set4);
        assertEquals(3, set4.size());

        Set<String> set5 = asImmutableSet(set4.iterator());
        assertNotNull(set5);

        Set<String> set6 = asImmutableSet(set4);
        assertNotNull(set6);

        Set<String> set7 = asImmutableSet("foo");
        assertNotNull(set7);
        assertEquals(1, set7.size());

        try
        {
            asImmutableSet((String) null);
            fail("asImmutableSet((String) null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected, ImmutableSet does not accept null elements
        }
        try
        {
            asImmutableSet((Iterator<String>) null);
            fail("asImmutableSet((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asImmutableSet((Iterable<String>) null);
            fail("asImmutableSet((Iterable<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAsImmutableSortedSet()
    {
        SortedSet<String> set0 = asImmutableSortedSet("foo", "foo");
        assertNotNull(set0);
        assertEquals(1, set0.size());

        SortedSet<String> set1 = asImmutableSortedSet("foo", "bar", "foo");
        assertNotNull(set1);
        assertEquals(2, set1.size());

        SortedSet<String> set2 = asImmutableSortedSet("foo", "bar", "baz", "foo");
        assertNotNull(set2);
        assertEquals(3, set2.size());

        SortedSet<String> set4 = asImmutableSortedSet(new String[] { "foo", "bar", "baz", "foo" });
        assertNotNull(set4);
        assertEquals(3, set4.size());

        SortedSet<String> set5 = asImmutableSortedSet(set4.iterator());
        assertNotNull(set5);

        SortedSet<String> set6 = asImmutableSortedSet(set4);
        assertNotNull(set6);

        SortedSet<String> set7 = asImmutableSortedSet(set4.iterator(), COMPARATOR);
        assertNotNull(set7);

        SortedSet<String> set8 = asImmutableSortedSet(set4.iterator(), null);
        assertNotNull(set8);

        SortedSet<String> set9 = asImmutableSortedSet(set4, COMPARATOR);
        assertNotNull(set9);

        SortedSet<String> set10 = asImmutableSortedSet(set4, null);
        assertNotNull(set10);

        SortedSet<String> set11 = asImmutableSortedSet("foo");
        assertNotNull(set11);
        assertEquals(1, set11.size());

        try
        {
            asImmutableSortedSet((Iterator<String>) null);
            fail("asImmutableSortedSet((Iterator<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asImmutableSortedSet((Iterable<String>) null);
            fail("asImmutableSortedSet((Iterable<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asImmutableSortedSet((Iterator<String>) null, COMPARATOR);
            fail("asImmutableSortedSet((Iterator<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            asImmutableSortedSet((Iterable<String>) null, COMPARATOR);
            fail("asImmutableSortedSet((Iterable<String>) null, COMPARATOR) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testImmutableSet()
    {
        Set<String> toCopy = asSet("foo", "bar", "baz");
        Set<String> immutableSet = immutableSet(toCopy);
        assertNotNull(immutableSet);
        // TODO:  add'l assertions

        try
        {
            immutableSet((Set<String>) null);
            fail("immutableSet((Set<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testImmutableSortedSet()
    {
        SortedSet<String> toCopy = asSortedSet("foo", "bar", "baz");
        SortedSet<String> immutableSortedSet = immutableSortedSet(toCopy);
        assertNotNull(immutableSortedSet);
        // TODO:  add'l assertions

        try
        {
            immutableSortedSet((SortedSet<String>) null);
            fail("immutableSortedSet((SortedSet<String>) null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
    }
}