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
 * Abstract unit test for implementations of QuaternaryVennModel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractQuaternaryVennModelTest
    extends TestCase
{
    /** First set. */
    protected static final Set<String> FIRST = new HashSet<String>(Arrays.asList(new String[] { "foo", "bar" }));

    /** Second set. */
    protected static final Set<String> SECOND = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz" }));

    /** Third set. */
    protected static final Set<String> THIRD = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz", "qux" }));

    /** Fourth set. */
    protected static final Set<String> FOURTH = new HashSet<String>(Arrays.asList(new String[] { "bar", "qux" }));


    /**
     * Create and return a new instance of an implementation of QuaternaryVennModel
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @param fourth fourth set, must not be null
     * @return a new instance of an implementation of QuaternaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> QuaternaryVennModel<T> createQuaternaryVennModel(Set<? extends T> first,
                                                                            Set<? extends T> second,
                                                                            Set<? extends T> third,
                                                                            Set<? extends T> fourth);


    public void testCreateQuaternaryVennModel()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);
        assertNotNull(quaternaryVennModel);
    }

    public void testFirst()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);
        EventList<String> first = quaternaryVennModel.first();
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
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);
        EventList<String> second = quaternaryVennModel.second();
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
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);
        EventList<String> third = quaternaryVennModel.third();
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

    public void testFourth()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);
        EventList<String> fourth = quaternaryVennModel.fourth();
        assertNotNull(fourth);
        assertEquals(2, fourth.size());
        assertTrue(fourth.contains("bar"));
        assertTrue(fourth.contains("qux"));

        fourth.add("garply");
        assertEquals(3, fourth.size());
        assertTrue(fourth.contains("garply"));

        fourth.remove("garply");
        assertEquals(2, fourth.size());
        assertFalse(fourth.contains("garply"));
    }

    public void testIntersection()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);
        EventList<String> intersection = quaternaryVennModel.intersection();
        assertNotNull(intersection);
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains("bar"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.second().add("garply");
        quaternaryVennModel.third().add("garply");
        quaternaryVennModel.fourth().add("garply");
        assertEquals(2, intersection.size());
        assertTrue(intersection.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(1, intersection.size());
        assertFalse(intersection.contains("garply"));
    }

    public void testIntersectionIsImmutable()
    {
        // todo
    }

    public void testIntersectAB()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);

        EventList<String> intersectFirstSecond = quaternaryVennModel.intersect(quaternaryVennModel.first(),
                quaternaryVennModel.second());
        assertNotNull(intersectFirstSecond);
        assertEquals(1, intersectFirstSecond.size());
        assertTrue(intersectFirstSecond.contains("bar"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.second().add("garply");
        assertEquals(2, intersectFirstSecond.size());
        assertTrue(intersectFirstSecond.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        assertEquals(1, intersectFirstSecond.size());
        assertFalse(intersectFirstSecond.contains("garply"));

        EventList<String> intersectSecondThird = quaternaryVennModel.intersect(quaternaryVennModel.second(),
                quaternaryVennModel.third());
        assertNotNull(intersectSecondThird);
        assertEquals(2, intersectSecondThird.size());
        assertTrue(intersectSecondThird.contains("bar"));
        assertTrue(intersectSecondThird.contains("baz"));

        quaternaryVennModel.second().add("garply");
        quaternaryVennModel.third().add("garply");
        assertEquals(3, intersectSecondThird.size());
        assertTrue(intersectSecondThird.contains("garply"));

        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        assertEquals(2, intersectSecondThird.size());
        assertFalse(intersectSecondThird.contains("garply"));
    }

    public void testIntersectABIsImmutable()
    {
        // todo
    }

    public void testIntersectABIsCommutative()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);

        EventList<String> intersectFirstSecond = quaternaryVennModel.intersect(quaternaryVennModel.first(),
                quaternaryVennModel.second());
        EventList<String> intersectSecondFirst = quaternaryVennModel.intersect(quaternaryVennModel.second(),
                quaternaryVennModel.first());
        assertEquals(intersectFirstSecond, intersectSecondFirst);
        // todo:  assertSame?

        EventList<String> intersectSecondThird = quaternaryVennModel.intersect(quaternaryVennModel.second(),
                quaternaryVennModel.third());
        EventList<String> intersectThirdSecond = quaternaryVennModel.intersect(quaternaryVennModel.third(),
                quaternaryVennModel.second());
        assertEquals(intersectSecondThird, intersectThirdSecond);
    }

    public void testUnion()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);
        EventList<String> union = quaternaryVennModel.union();
        assertNotNull(union);
        assertEquals(9, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        quaternaryVennModel.first().add("garply");
        assertEquals(10, union.size());
        assertTrue(union.contains("garply"));

        quaternaryVennModel.second().add("garply");
        assertEquals(11, union.size());
        assertTrue(union.contains("garply"));

        quaternaryVennModel.third().add("garply");
        assertEquals(12, union.size());
        assertTrue(union.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        assertEquals(9, union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }

    public void testUnionAB()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);

        EventList<String> unionFirstSecond = quaternaryVennModel.union(quaternaryVennModel.first(),
                quaternaryVennModel.second());
        assertNotNull(unionFirstSecond);
        assertEquals(4, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("foo"));
        assertTrue(unionFirstSecond.contains("bar"));
        assertTrue(unionFirstSecond.contains("baz"));

        quaternaryVennModel.first().add("garply");
        assertEquals(5, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("garply"));

        quaternaryVennModel.second().add("garply");
        assertEquals(6, unionFirstSecond.size());
        assertTrue(unionFirstSecond.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        assertEquals(4, unionFirstSecond.size());
        assertFalse(unionFirstSecond.contains("garply"));

        EventList<String> unionSecondThird = quaternaryVennModel.union(quaternaryVennModel.second(),
                quaternaryVennModel.third());
        assertNotNull(unionSecondThird);
        assertEquals(5, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("bar"));
        assertTrue(unionSecondThird.contains("baz"));
        assertTrue(unionSecondThird.contains("qux"));

        quaternaryVennModel.second().add("garply");
        assertEquals(6, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("garply"));

        quaternaryVennModel.third().add("garply");
        assertEquals(7, unionSecondThird.size());
        assertTrue(unionSecondThird.contains("garply"));

        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        assertEquals(5, unionSecondThird.size());
        assertFalse(unionSecondThird.contains("garply"));
    }

    public void testUnionABIsImmutable()
    {
        // todo
    }

    public void testUnionABIsCommutative()
    {
        QuaternaryVennModel<String> quaternaryVennModel = createQuaternaryVennModel(FIRST, SECOND, THIRD, FOURTH);

        EventList<String> unionFirstSecond = quaternaryVennModel.union(quaternaryVennModel.first(),
                quaternaryVennModel.second());
        EventList<String> unionSecondFirst = quaternaryVennModel.union(quaternaryVennModel.second(),
                quaternaryVennModel.first());
        assertEquals(unionFirstSecond, unionSecondFirst);
        // todo:  assertSame?

        EventList<String> unionSecondThird = quaternaryVennModel.union(quaternaryVennModel.second(),
                quaternaryVennModel.third());
        EventList<String> unionThirdSecond = quaternaryVennModel.union(quaternaryVennModel.third(),
                quaternaryVennModel.second());
        assertEquals(unionSecondThird, unionThirdSecond);
    }
}