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
    protected static final Set<String> SET0 = new HashSet(Arrays.asList(new String[] { "foo", "bar" }));

    /** Second set. */
    protected static final Set<String> SET1 = new HashSet(Arrays.asList(new String[] { "bar", "baz" }));

    /** Third set. */
    protected static final Set<String> SET2 = new HashSet(Arrays.asList(new String[] { "bar", "baz", "qux" }));


    /**
     * Create and return a new instance of an implementation of TertiaryVennModel
     * with the specified sets to test.
     *
     * @param set0 first set
     * @param set1 second set
     * @param set2 third set
     * @return a new instance of an implementation of TertiaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> TertiaryVennModel<T> createTertiaryVennModel(Set<? extends T> set0,
									Set<? extends T> set1,
									Set<? extends T> set2);

    public void testCreateTertiaryVennModel()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);
	assertNotNull(tertiaryVennModel);
    }

    public void testList0()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);
	EventList<String> list0 = tertiaryVennModel.list0();
	assertNotNull(list0);
	assertEquals(2, list0.size());
	assertTrue(list0.contains("foo"));
	assertTrue(list0.contains("bar"));

	list0.add("garply");
	assertEquals(3, list0.size());
	assertTrue(list0.contains("garply"));

	list0.remove("garply");
	assertEquals(2, list0.size());
	assertFalse(list0.contains("garply"));
    }

    public void testList1()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);
	EventList<String> list1 = tertiaryVennModel.list1();
	assertNotNull(list1);
	assertEquals(2, list1.size());
	assertTrue(list1.contains("bar"));
	assertTrue(list1.contains("baz"));

	list1.add("garply");
	assertEquals(3, list1.size());
	assertTrue(list1.contains("garply"));

	list1.remove("garply");
	assertEquals(2, list1.size());
	assertFalse(list1.contains("garply"));
    }

    public void testList2()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);
	EventList<String> list2 = tertiaryVennModel.list2();
	assertNotNull(list2);
	assertEquals(3, list2.size());
	assertTrue(list2.contains("bar"));
	assertTrue(list2.contains("baz"));
	assertTrue(list2.contains("qux"));

	list2.add("garply");
	assertEquals(4, list2.size());
	assertTrue(list2.contains("garply"));

	list2.remove("garply");
	assertEquals(3, list2.size());
	assertFalse(list2.contains("garply"));
    }

    public void testIntersection()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);
	EventList<String> intersection = tertiaryVennModel.intersection();
	assertNotNull(intersection);
	assertEquals(1, intersection.size());
	assertTrue(intersection.contains("bar"));

	tertiaryVennModel.list0().add("garply");
	tertiaryVennModel.list1().add("garply");
	tertiaryVennModel.list2().add("garply");
	assertEquals(2, intersection.size());
	assertTrue(intersection.contains("garply"));

	tertiaryVennModel.list0().remove("garply");
	tertiaryVennModel.list1().remove("garply");
	tertiaryVennModel.list2().remove("garply");
	assertEquals(1, intersection.size());
	assertFalse(intersection.contains("garply"));
   }

    public void testIntersectionIsImmutable()
    {
	// todo
    }

    public void testIntersect()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);

	EventList<String> intersectList0List1 = tertiaryVennModel.intersect(tertiaryVennModel.list0(),
									    tertiaryVennModel.list1());
	assertNotNull(intersectList0List1);
	assertEquals(1, intersectList0List1.size());
	assertTrue(intersectList0List1.contains("bar"));

	tertiaryVennModel.list0().add("garply");
	tertiaryVennModel.list1().add("garply");
	assertEquals(2, intersectList0List1.size());
	assertTrue(intersectList0List1.contains("garply"));

	tertiaryVennModel.list0().remove("garply");
	tertiaryVennModel.list1().remove("garply");
	assertEquals(1, intersectList0List1.size());
	assertFalse(intersectList0List1.contains("garply"));

	EventList<String> intersectList1List2 = tertiaryVennModel.intersect(tertiaryVennModel.list1(),
									    tertiaryVennModel.list2());
	assertNotNull(intersectList1List2);
	assertEquals(2, intersectList1List2.size());
	assertTrue(intersectList1List2.contains("bar"));
	assertTrue(intersectList1List2.contains("baz"));

	tertiaryVennModel.list1().add("garply");
	tertiaryVennModel.list2().add("garply");
	assertEquals(3, intersectList1List2.size());
	assertTrue(intersectList1List2.contains("garply"));

	tertiaryVennModel.list1().remove("garply");
	tertiaryVennModel.list2().remove("garply");
	assertEquals(2, intersectList1List2.size());
	assertFalse(intersectList1List2.contains("garply"));
    }

    public void testIntersectIsImmutable()
    {
	// todo
    }

    public void testIntersectIsCommutative()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);

	EventList<String> intersectList0List1 = tertiaryVennModel.intersect(tertiaryVennModel.list0(),
									    tertiaryVennModel.list1());
	EventList<String> intersectList1List0 = tertiaryVennModel.intersect(tertiaryVennModel.list1(),
									    tertiaryVennModel.list0());
	assertEquals(intersectList0List1, intersectList1List0);
	// todo:  assertSame?

	EventList<String> intersectList1List2 = tertiaryVennModel.intersect(tertiaryVennModel.list1(),
									    tertiaryVennModel.list2());
	EventList<String> intersectList2List1 = tertiaryVennModel.intersect(tertiaryVennModel.list2(),
									    tertiaryVennModel.list1());
	assertEquals(intersectList1List2, intersectList2List1);
    }

    public void testUnion()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);
	EventList<String> union = tertiaryVennModel.union();
	assertNotNull(union);
	assertEquals(7, union.size());
	assertTrue(union.contains("foo"));
	assertTrue(union.contains("bar"));
	assertTrue(union.contains("baz"));
	assertTrue(union.contains("qux"));

	tertiaryVennModel.list0().add("garply");
	assertEquals(8, union.size());
	assertTrue(union.contains("garply"));

	tertiaryVennModel.list1().add("garply");
	assertEquals(9, union.size());
	assertTrue(union.contains("garply"));

	tertiaryVennModel.list2().add("garply");
	assertEquals(10, union.size());
	assertTrue(union.contains("garply"));

	tertiaryVennModel.list0().remove("garply");
	tertiaryVennModel.list1().remove("garply");
	tertiaryVennModel.list2().remove("garply");
	assertEquals(7, union.size());
	assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
	// todo
    }

    public void testUnionAB()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);

	EventList<String> unionList0List1 = tertiaryVennModel.union(tertiaryVennModel.list0(),
								    tertiaryVennModel.list1());
	assertNotNull(unionList0List1);
	assertEquals(4, unionList0List1.size());
	assertTrue(unionList0List1.contains("foo"));
	assertTrue(unionList0List1.contains("bar"));
	assertTrue(unionList0List1.contains("baz"));

	tertiaryVennModel.list0().add("garply");
	assertEquals(5, unionList0List1.size());
	assertTrue(unionList0List1.contains("garply"));

	tertiaryVennModel.list1().add("garply");
	assertEquals(6, unionList0List1.size());
	assertTrue(unionList0List1.contains("garply"));

	tertiaryVennModel.list0().remove("garply");
	tertiaryVennModel.list1().remove("garply");
	assertEquals(4, unionList0List1.size());
	assertFalse(unionList0List1.contains("garply"));

	EventList<String> unionList1List2 = tertiaryVennModel.union(tertiaryVennModel.list1(),
								    tertiaryVennModel.list2());
	assertNotNull(unionList1List2);
	assertEquals(5, unionList1List2.size());
	assertTrue(unionList1List2.contains("bar"));
	assertTrue(unionList1List2.contains("baz"));
	assertTrue(unionList1List2.contains("qux"));

	tertiaryVennModel.list1().add("garply");
	assertEquals(6, unionList1List2.size());
	assertTrue(unionList1List2.contains("garply"));

	tertiaryVennModel.list2().add("garply");
	assertEquals(7, unionList1List2.size());
	assertTrue(unionList1List2.contains("garply"));

	tertiaryVennModel.list1().remove("garply");
	tertiaryVennModel.list2().remove("garply");
	assertEquals(5, unionList1List2.size());
	assertFalse(unionList1List2.contains("garply"));
    }

    public void testUnionABIsImmutable()
    {
	// todo
    }

    public void testUnionABIsCommutative()
    {
	TertiaryVennModel<String> tertiaryVennModel = createTertiaryVennModel(SET0, SET1, SET2);

	EventList<String> unionList0List1 = tertiaryVennModel.union(tertiaryVennModel.list0(),
								    tertiaryVennModel.list1());
	EventList<String> unionList1List0 = tertiaryVennModel.union(tertiaryVennModel.list1(),
								    tertiaryVennModel.list0());
	assertEquals(unionList0List1, unionList1List0);
	// todo:  assertSame?

	EventList<String> unionList1List2 = tertiaryVennModel.union(tertiaryVennModel.list1(),
								    tertiaryVennModel.list2());
	EventList<String> unionList2List1 = tertiaryVennModel.union(tertiaryVennModel.list2(),
								    tertiaryVennModel.list1());
	assertEquals(unionList1List2, unionList2List1);
    }
}