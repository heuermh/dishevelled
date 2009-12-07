/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009 held jointly by the individual authors.

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

/**
 * Abstract unit test for implementations of TertiaryVennModel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTertiaryVennModelTest
    extends TestCase
{
    /** First set. */
    protected static final Set<String> FIRST = new HashSet<String>(Arrays.asList(new String[] { "foo", "bar" }));

    /** Second set. */
    protected static final Set<String> SECOND = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz" }));

    /** Third set. */
    protected static final Set<String> THIRD = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz", "qux" }));

    /**
     * Create and return a new instance of an implementation of TertiaryVennModel
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @return a new instance of an implementation of TertiaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> TertiaryVennModel<T> createTertiaryVennModel(Set<? extends T> first,
            Set<? extends T> second,
            Set<? extends T> third);

    public void testCreateTertiaryVennModel()
    {
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        assertNotNull(tertiaryVennModel);
    }

    public void testFirst()
    {
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> first = tertiaryVennModel.first();
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
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> second = tertiaryVennModel.second();
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
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> third = tertiaryVennModel.third();
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
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> intersection = tertiaryVennModel.intersection();
        assertNotNull(intersection);
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains("bar"));

        tertiaryVennModel.first().add("garply");
        tertiaryVennModel.second().add("garply");
        tertiaryVennModel.third().add("garply");
        assertEquals(2, intersection.size());
        assertTrue(intersection.contains("garply"));

        tertiaryVennModel.first().remove("garply");
        tertiaryVennModel.second().remove("garply");
        tertiaryVennModel.third().remove("garply");
        assertEquals(1, intersection.size());
        assertFalse(intersection.contains("garply"));
    }

    public void testIntersectionIsImmutable()
    {
        // todo
    }

    public void testIntersect()
    {
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> intersectFirstSecond = tertiaryVennModel.intersect(tertiaryVennModel.first(),
                tertiaryVennModel.second());
        assertNotNull(intersectFirstSecond);
        assertEquals(1, intersectFirstSecond.size());
        assertTrue(intersectFirstSecond.contains("bar"));

        tertiaryVennModel.first().add("garply");
        tertiaryVennModel.second().add("garply");
        assertEquals(2, intersectFirstSecond.size());
        assertTrue(intersectFirstSecond.contains("garply"));

        tertiaryVennModel.first().remove("garply");
        tertiaryVennModel.second().remove("garply");
        assertEquals(1, intersectFirstSecond.size());
        assertFalse(intersectFirstSecond.contains("garply"));

        EventList<String> intersectSecondThird = tertiaryVennModel.intersect(tertiaryVennModel.second(),
                tertiaryVennModel.third());
        assertNotNull(intersectSecondThird);
        assertEquals(2, intersectSecondThird.size());
        assertTrue(intersectSecondThird.contains("bar"));
        assertTrue(intersectSecondThird.contains("baz"));

        tertiaryVennModel.second().add("garply");
        tertiaryVennModel.third().add("garply");
        assertEquals(3, intersectSecondThird.size());
        assertTrue(intersectSecondThird.contains("garply"));

        tertiaryVennModel.second().remove("garply");
        tertiaryVennModel.third().remove("garply");
        assertEquals(2, intersectSecondThird.size());
        assertFalse(intersectSecondThird.contains("garply"));
    }

    public void testIntersectIsImmutable()
    {
        // todo
    }

    public void testIntersectIsCommutative()
    {
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> intersectFirstSecond = tertiaryVennModel.intersect(tertiaryVennModel.first(),
                tertiaryVennModel.second());
        EventList<String> intersectSecondFirst = tertiaryVennModel.intersect(tertiaryVennModel.second(),
                tertiaryVennModel.first());
        assertEquals(intersectFirstSecond, intersectSecondFirst);
        // todo:  assertSame?

        EventList<String> intersectSecondThird = tertiaryVennModel.intersect(tertiaryVennModel.second(),
                tertiaryVennModel.third());
        EventList<String> intersectThirdSecond = tertiaryVennModel.intersect(tertiaryVennModel.third(),
                tertiaryVennModel.second());
        assertEquals(intersectSecondThird, intersectThirdSecond);
    }

    public void testUnion()
    {
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);
        EventList<String> union = tertiaryVennModel.union();
        assertNotNull(union);
        assertEquals(7, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        tertiaryVennModel.first().add("garply");
        assertEquals(8, union.size());
        assertTrue(union.contains("garply"));

        tertiaryVennModel.second().add("garply");
        assertEquals(9, union.size());
        assertTrue(union.contains("garply"));

        tertiaryVennModel.third().add("garply");
        assertEquals(10, union.size());
        assertTrue(union.contains("garply"));

        tertiaryVennModel.first().remove("garply");
        tertiaryVennModel.second().remove("garply");
        tertiaryVennModel.third().remove("garply");
        assertEquals(7, union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }

    public void testUnionAB()
    {
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> unionFirstSecond = tertiaryVennModel.union(tertiaryVennModel.first(),
                tertiaryVennModel.second());
        assertNotNull(unionFirstSecond);
        assertEquals(4, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("foo"));
        assertTrue(unionFirstSecond.contains("bar"));
        assertTrue(unionFirstSecond.contains("baz"));

        tertiaryVennModel.first().add("garply");
        assertEquals(5, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("garply"));

        tertiaryVennModel.second().add("garply");
        assertEquals(6, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("garply"));

        tertiaryVennModel.first().remove("garply");
        tertiaryVennModel.second().remove("garply");
        assertEquals(4, unionFirstSecond.size());
        assertFalse(unionFirstSecond.contains("garply"));

        EventList<String> unionSecondThird = tertiaryVennModel.union(tertiaryVennModel.second(),
                tertiaryVennModel.third());
        assertNotNull(unionSecondThird);
        assertEquals(5, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("bar"));
        assertTrue(unionSecondThird.contains("baz"));
        assertTrue(unionSecondThird.contains("qux"));

        tertiaryVennModel.second().add("garply");
        assertEquals(6, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("garply"));

        tertiaryVennModel.third().add("garply");
        assertEquals(7, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("garply"));

        tertiaryVennModel.second().remove("garply");
        tertiaryVennModel.third().remove("garply");
        assertEquals(5, unionSecondThird.size());
        assertFalse(unionSecondThird.contains("garply"));
    }

    public void testUnionABIsImmutable()
    {
        // todo
    }

    public void testUnionABIsCommutative()
    {
        TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(FIRST, SECOND, THIRD);

        EventList<String> unionFirstSecond = tertiaryVennModel.union(tertiaryVennModel.first(),
                tertiaryVennModel.second());
        EventList<String> unionSecondFirst = tertiaryVennModel.union(tertiaryVennModel.second(),
                tertiaryVennModel.first());
        assertEquals(unionFirstSecond, unionSecondFirst);
        // todo:  assertSame?

        EventList<String> unionSecondThird = tertiaryVennModel.union(tertiaryVennModel.second(),
                tertiaryVennModel.third());
        EventList<String> unionThirdSecond = tertiaryVennModel.union(tertiaryVennModel.third(),
                tertiaryVennModel.second());
        assertEquals(unionSecondThird, unionThirdSecond);
    }
}