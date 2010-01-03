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
    protected static final Set<String> FIRST = new HashSet<String>(Arrays.asList(new String[] { "foo", "bar" }));

    /** Second set. */
    protected static final Set<String> SECOND = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz", "qux" }));


    /**
     * Create and return a new instance of an implementation of BinaryVennModel
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @return a new instance of an implementation of BinaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> BinaryVennModel<T> createBinaryVennModel(Set<? extends T> first, Set<? extends T> second);

    public void testCreateBinaryVennModel()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(FIRST, SECOND);
        assertNotNull(binaryVennModel);
    }

    public void testFirst()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(FIRST, SECOND);
        EventList<String> first = binaryVennModel.first();
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
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(FIRST, SECOND);
        EventList<String> second = binaryVennModel.second();
        assertNotNull(second);
        assertEquals(3, second.size());
        assertTrue(second.contains("bar"));
        assertTrue(second.contains("baz"));
        assertTrue(second.contains("qux"));

        second.add("garply");
        assertEquals(4, second.size());
        assertTrue(second.contains("garply"));

        second.remove("garply");
        assertEquals(3, second.size());
        assertFalse(second.contains("garply"));
    }

    public void testIntersection()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(FIRST, SECOND);
        EventList<String> intersection = binaryVennModel.intersection();
        assertNotNull(intersection);
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains("bar"));

        binaryVennModel.first().add("garply");
        binaryVennModel.second().add("garply");
        assertEquals(2, intersection.size());
        assertTrue(intersection.contains("garply"));

        binaryVennModel.first().remove("garply");
        binaryVennModel.second().remove("garply");
        assertEquals(1, intersection.size());
        assertFalse(intersection.contains("garply"));
    }

    public void testIntersectionIsImmutable()
    {
        // todo
    }

    public void testUnion()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel(FIRST, SECOND);
        EventList<String> union = binaryVennModel.union();
        assertNotNull(union);
        assertEquals(5, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        binaryVennModel.first().add("garply");
        assertEquals(6, union.size());
        assertTrue(union.contains("garply"));

        binaryVennModel.second().add("garply");
        assertEquals(7, union.size());
        assertTrue(union.contains("garply"));

        binaryVennModel.first().remove("garply");
        binaryVennModel.second().remove("garply");
        assertEquals(5, union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }
}