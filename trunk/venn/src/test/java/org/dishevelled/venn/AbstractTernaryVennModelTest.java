/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.venn;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

/**
 * Abstract unit test for implementations of TernaryVennModel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTernaryVennModelTest
    extends TestCase
{
    /** First set. */
    protected static final Set<String> FIRST = new HashSet<String>(Arrays.asList(new String[] { "foo", "bar" }));

    /** Second set. */
    protected static final Set<String> SECOND = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz" }));

    /** Third set. */
    protected static final Set<String> THIRD = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz", "qux" }));

    /**
     * Create and return a new instance of an implementation of TernaryVennModel
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @return a new instance of an implementation of TernaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> TernaryVennModel<T> createTertiaryVennModel(Set<? extends T> first,
            Set<? extends T> second,
            Set<? extends T> third);

    public void testCreateTertiaryVennModel()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        assertNotNull(ternaryVennModel);
    }

    public void testFirst()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> first = ternaryVennModel.first();
        assertNotNull(first);
        assertEquals(2, first.size());
        assertTrue(first.contains("foo"));
        assertTrue(first.contains("bar"));

        first.add("garply");
        assertEquals(3, first.size());
        assertTrue(first.contains("garply"));

        first.remove("garply");
        assertEquals(2, first.size());
        assertFalse(first.contains("garply"));
    }

    public void testSecond()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> second = ternaryVennModel.second();
        assertNotNull(second);
        assertEquals(2, second.size());
        assertTrue(second.contains("bar"));
        assertTrue(second.contains("baz"));

        second.add("garply");
        assertEquals(3, second.size());
        assertTrue(second.contains("garply"));

        second.remove("garply");
        assertEquals(2, second.size());
        assertFalse(second.contains("garply"));
    }

    public void testThird()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> third = ternaryVennModel.third();
        assertNotNull(third);
        assertEquals(3, third.size());
        assertTrue(third.contains("bar"));
        assertTrue(third.contains("baz"));
        assertTrue(third.contains("qux"));

        third.add("garply");
        assertEquals(4, third.size());
        assertTrue(third.contains("garply"));

        third.remove("garply");
        assertEquals(3, third.size());
        assertFalse(third.contains("garply"));
    }

    public void testIntersection()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> intersection = ternaryVennModel.intersection();
        assertNotNull(intersection);
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains("bar"));

        ternaryVennModel.first().add("garply");
        ternaryVennModel.second().add("garply");
        ternaryVennModel.third().add("garply");
        assertEquals(2, intersection.size());
        assertTrue(intersection.contains("garply"));

        ternaryVennModel.first().remove("garply");
        ternaryVennModel.second().remove("garply");
        ternaryVennModel.third().remove("garply");
        assertEquals(1, intersection.size());
        assertFalse(intersection.contains("garply"));
    }

    public void testIntersectionIsImmutable()
    {
        // todo
    }

    public void testIntersect()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> intersectFirstSecond = ternaryVennModel.intersect(ternaryVennModel.first(),
                ternaryVennModel.second());
        assertNotNull(intersectFirstSecond);
        assertEquals(1, intersectFirstSecond.size());
        assertTrue(intersectFirstSecond.contains("bar"));

        ternaryVennModel.first().add("garply");
        ternaryVennModel.second().add("garply");
        assertEquals(2, intersectFirstSecond.size());
        assertTrue(intersectFirstSecond.contains("garply"));

        ternaryVennModel.first().remove("garply");
        ternaryVennModel.second().remove("garply");
        assertEquals(1, intersectFirstSecond.size());
        assertFalse(intersectFirstSecond.contains("garply"));

        EventList<String> intersectSecondThird = ternaryVennModel.intersect(ternaryVennModel.second(),
                ternaryVennModel.third());
        assertNotNull(intersectSecondThird);
        assertEquals(2, intersectSecondThird.size());
        assertTrue(intersectSecondThird.contains("bar"));
        assertTrue(intersectSecondThird.contains("baz"));

        ternaryVennModel.second().add("garply");
        ternaryVennModel.third().add("garply");
        assertEquals(3, intersectSecondThird.size());
        assertTrue(intersectSecondThird.contains("garply"));

        ternaryVennModel.second().remove("garply");
        ternaryVennModel.third().remove("garply");
        assertEquals(2, intersectSecondThird.size());
        assertFalse(intersectSecondThird.contains("garply"));

        try
        {
            ternaryVennModel.intersect(null, ternaryVennModel.second());
            fail("intersect(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ternaryVennModel.intersect(ternaryVennModel.first(), null);
            fail("intersect(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        EventList<String> invalid = GlazedLists.eventListOf("foo");
        try
        {
            ternaryVennModel.intersect(invalid, ternaryVennModel.second());
            fail("intersect(invalid,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ternaryVennModel.intersect(ternaryVennModel.first(), invalid);
            fail("intersect(,invalid) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIntersectIsImmutable()
    {
        // todo
    }

    public void testIntersectIsCommutative()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> intersectFirstSecond = ternaryVennModel.intersect(ternaryVennModel.first(),
                ternaryVennModel.second());
        EventList<String> intersectSecondFirst = ternaryVennModel.intersect(ternaryVennModel.second(),
                ternaryVennModel.first());
        assertEquals(intersectFirstSecond, intersectSecondFirst);
        // todo:  assertSame?

        EventList<String> intersectSecondThird = ternaryVennModel.intersect(ternaryVennModel.second(),
                ternaryVennModel.third());
        EventList<String> intersectThirdSecond = ternaryVennModel.intersect(ternaryVennModel.third(),
                ternaryVennModel.second());
        assertEquals(intersectSecondThird, intersectThirdSecond);
    }

    public void testUnion()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> union = ternaryVennModel.union();
        assertNotNull(union);
        assertEquals(7, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        ternaryVennModel.first().add("garply");
        assertEquals(8, union.size());
        assertTrue(union.contains("garply"));

        ternaryVennModel.second().add("garply");
        assertEquals(9, union.size());
        assertTrue(union.contains("garply"));

        ternaryVennModel.third().add("garply");
        assertEquals(10, union.size());
        assertTrue(union.contains("garply"));

        ternaryVennModel.first().remove("garply");
        ternaryVennModel.second().remove("garply");
        ternaryVennModel.third().remove("garply");
        assertEquals(7, union.size());
        assertFalse(union.contains("garply"));

        try
        {
            ternaryVennModel.union(null, ternaryVennModel.second());
            fail("union(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ternaryVennModel.union(ternaryVennModel.first(), null);
            fail("union(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        EventList<String> invalid = GlazedLists.eventListOf("foo");
        try
        {
            ternaryVennModel.union(invalid, ternaryVennModel.second());
            fail("union(invalid,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ternaryVennModel.union(ternaryVennModel.first(), invalid);
            fail("union(,invalid) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testUnionIsImmutable()
    {
        // todo
    }

    public void testUnionAB()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> unionFirstSecond = ternaryVennModel.union(ternaryVennModel.first(),
                ternaryVennModel.second());
        assertNotNull(unionFirstSecond);
        assertEquals(4, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("foo"));
        assertTrue(unionFirstSecond.contains("bar"));
        assertTrue(unionFirstSecond.contains("baz"));

        ternaryVennModel.first().add("garply");
        assertEquals(5, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("garply"));

        ternaryVennModel.second().add("garply");
        assertEquals(6, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("garply"));

        ternaryVennModel.first().remove("garply");
        ternaryVennModel.second().remove("garply");
        assertEquals(4, unionFirstSecond.size());
        assertFalse(unionFirstSecond.contains("garply"));

        EventList<String> unionSecondThird = ternaryVennModel.union(ternaryVennModel.second(),
                ternaryVennModel.third());
        assertNotNull(unionSecondThird);
        assertEquals(5, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("bar"));
        assertTrue(unionSecondThird.contains("baz"));
        assertTrue(unionSecondThird.contains("qux"));

        ternaryVennModel.second().add("garply");
        assertEquals(6, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("garply"));

        ternaryVennModel.third().add("garply");
        assertEquals(7, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("garply"));

        ternaryVennModel.second().remove("garply");
        ternaryVennModel.third().remove("garply");
        assertEquals(5, unionSecondThird.size());
        assertFalse(unionSecondThird.contains("garply"));
    }

    public void testUnionABIsImmutable()
    {
        // todo
    }

    public void testUnionABIsCommutative()
    {
        TernaryVennModel<String> ternaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> unionFirstSecond = ternaryVennModel.union(ternaryVennModel.first(),
                ternaryVennModel.second());
        EventList<String> unionSecondFirst = ternaryVennModel.union(ternaryVennModel.second(),
                ternaryVennModel.first());
        assertEquals(unionFirstSecond, unionSecondFirst);
        // todo:  assertSame?

        EventList<String> unionSecondThird = ternaryVennModel.union(ternaryVennModel.second(),
                ternaryVennModel.third());
        EventList<String> unionThirdSecond = ternaryVennModel.union(ternaryVennModel.third(),
                ternaryVennModel.second());
        assertEquals(unionSecondThird, unionThirdSecond);
    }
}