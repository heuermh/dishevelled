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
 * Abstract unit test for implementations of BinaryVennModel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractBinaryVennModelTest
    extends TestCase
{
    /** First set. */
    protected static final Set<String> SET0 = new HashSet<String>(Arrays.asList(new String[] { "foo", "bar" }));

    /** Second set. */
    protected static final Set<String> SET1 = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz", "qux" }));


    /**
     * Create and return a new instance of an implementation of BinaryVennModel
     * with the specified sets to test.
     *
     * @param set0 first set
     * @param set1 second set
     * @return a new instance of an implementation of BinaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> BinaryVennModel<T> createBinaryVennModel(Set<? extends T> set0, Set<? extends T> set1);

    public void testCreateBinaryVennModel()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(SET0, SET1);
        assertNotNull(binaryVennModel);
    }

    public void testList0()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(SET0, SET1);
        EventList<String> list0 = binaryVennModel.list0();
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
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(SET0, SET1);
        EventList<String> list1 = binaryVennModel.list1();
        assertNotNull(list1);
        assertEquals(3, list1.size());
        assertTrue(list1.contains("bar"));
        assertTrue(list1.contains("baz"));
        assertTrue(list1.contains("qux"));

        list1.add("garply");
        assertEquals(4, list1.size());
        assertTrue(list1.contains("garply"));

        list1.remove("garply");
        assertEquals(3, list1.size());
        assertFalse(list1.contains("garply"));
    }

    public void testIntersection()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(SET0, SET1);
        EventList<String> intersection = binaryVennModel.intersection();
        assertNotNull(intersection);
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains("bar"));

        binaryVennModel.list0().add("garply");
        binaryVennModel.list1().add("garply");
        assertEquals(2, intersection.size());
        assertTrue(intersection.contains("garply"));

        binaryVennModel.list0().remove("garply");
        binaryVennModel.list1().remove("garply");
        assertEquals(1, intersection.size());
        assertFalse(intersection.contains("garply"));
    }

    public void testIntersectionIsImmutable()
    {
        // todo
    }

    public void testUnion()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(SET0, SET1);
        EventList<String> union = binaryVennModel.union();
        assertNotNull(union);
        assertEquals(5, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        binaryVennModel.list0().add("garply");
        assertEquals(6, union.size());
        assertTrue(union.contains("garply"));

        binaryVennModel.list1().add("garply");
        assertEquals(7, union.size());
        assertTrue(union.contains("garply"));

        binaryVennModel.list0().remove("garply");
        binaryVennModel.list1().remove("garply");
        assertEquals(5, union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }
}