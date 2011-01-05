/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2011 held jointly by the individual authors.

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

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

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

    /** Set change listener. */
    protected class Listener implements SetChangeListener<String>
    {
        /** True if this listener heard a set change event. */
        private boolean heardChangeEvent = false;


        /**
         * Return true if this listener heard a set change event.
         *
         * @return true if this listener heard a set change event
         */
        protected boolean heardChangeEvent()
        {
            return heardChangeEvent;
        }

        /** {@inheritDoc} */
        public void setChanged(final SetChangeEvent<String> event)
        {
            this.heardChangeEvent = true;
        }
    }


    /**
     * Create and return a new instance of an implementation of BinaryVennModel
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @return a new instance of an implementation of BinaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> BinaryVennModel<T> createBinaryVennModel3(Set<? extends T> first, Set<? extends T> second);

    public void testCreateBinaryVennModel3()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        assertNotNull(binaryVennModel);
    }

    public void testFirst()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        Set<String> first = binaryVennModel.first();
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

    public void testFirstListener()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        ObservableSet<String> first = binaryVennModel.first();
        Listener listener = new Listener();
        first.addSetChangeListener(listener);
        first.add("garply");
        assertTrue(listener.heardChangeEvent());
        first.remove("garply");
    }

    public void testSecond()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        Set<String> second = binaryVennModel.second();
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

    public void testSecondListener()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        ObservableSet<String> second = binaryVennModel.second();
        Listener listener = new Listener();
        second.addSetChangeListener(listener);
        second.add("garply");
        assertTrue(listener.heardChangeEvent());
        second.remove("garply");
    }

    public void testFirstOnly()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        Set<String> first = binaryVennModel.first();
        Set<String> firstOnly = binaryVennModel.firstOnly();
        assertNotNull(firstOnly);
        assertEquals(1, firstOnly.size());
        assertTrue(firstOnly.contains("foo"));

        first.add("garply");
        assertEquals(2, firstOnly.size());
        assertTrue(firstOnly.contains("foo"));
        assertTrue(firstOnly.contains("garply"));

        first.remove("garply");
        assertEquals(1, firstOnly.size());
        assertFalse(firstOnly.contains("garply"));
    }

    public void testFirstOnlyIsImmutable()
    {
        // todo
    }

    public void testSecondOnly()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        Set<String> second = binaryVennModel.second();
        Set<String> secondOnly = binaryVennModel.secondOnly();
        assertNotNull(secondOnly);
        assertEquals(2, secondOnly.size());
        assertTrue(secondOnly.contains("baz"));
        assertTrue(secondOnly.contains("qux"));

        second.add("garply");
        assertEquals(3, secondOnly.size());
        assertTrue(secondOnly.contains("baz"));
        assertTrue(secondOnly.contains("qux"));
        assertTrue(secondOnly.contains("garply"));

        second.remove("garply");
        assertEquals(2, secondOnly.size());
        assertTrue(secondOnly.contains("baz"));
        assertTrue(secondOnly.contains("qux"));
        assertFalse(secondOnly.contains("garply"));
    }

    public void testSecondOnlyIsImmutable()
    {
        // todo
    }

    public void testIntersection()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        Set<String> intersection = binaryVennModel.intersection();
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
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        Set<String> union = binaryVennModel.union();
        assertNotNull(union);
        assertEquals(4, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        binaryVennModel.first().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        binaryVennModel.second().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        binaryVennModel.first().remove("garply");
        binaryVennModel.second().remove("garply");
        assertEquals(4, union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }

    public void testSelection()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        Set<String> first = binaryVennModel.first();
        Set<String> second = binaryVennModel.second();
        Set<String> selection = binaryVennModel.selection();
        assertNotNull(selection);
        assertTrue(selection.isEmpty());

        selection.add("foo");
        assertEquals(1, selection.size());
        assertTrue(selection.contains("foo"));

        selection.remove("foo");
        assertEquals(0, selection.size());
        assertFalse(selection.contains("foo"));

        selection.add("foo");
        selection.add("qux");
        assertEquals(2, selection.size());
        assertTrue(selection.contains("foo"));
        assertTrue(selection.contains("qux"));

        // selection reflects changes to first, second views
        first.remove("foo");
        assertEquals(1, selection.size());
        assertFalse(selection.contains("foo"));

        second.remove("qux");
        assertEquals(0, selection.size());
        assertFalse(selection.contains("qux"));

        first.add("foo");
        second.add("qux");

        // cannot add something to selection not in union
        try
        {
            selection.add(null);
            fail("selection.add(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            selection.add("garply");
            fail("selection.add(garply) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSelectionListener()
    {
        BinaryVennModel<String> binaryVennModel = createBinaryVennModel3(FIRST, SECOND);
        ObservableSet<String> selection = binaryVennModel.selection();
        Listener listener = new Listener();
        selection.addSetChangeListener(listener);
        selection.add("foo");
        assertTrue(listener.heardChangeEvent());
    }
}