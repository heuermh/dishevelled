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

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

/**
 * Abstract unit test for implementations of TertiaryVennModel3.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTertiaryVennModel3Test
    extends TestCase
{
    /** First set. */
    protected static final Set<String> FIRST = new HashSet<String>(Arrays.asList(new String[] { "foo", "bar" }));

    /** Second set. */
    protected static final Set<String> SECOND = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz" }));

    /** Third set. */
    protected static final Set<String> THIRD = new HashSet<String>(Arrays.asList(new String[] { "bar", "baz", "qux" }));

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
        public void setChanged(final SetChangeEvent event)
        {
            this.heardChangeEvent = true;
        }
    }


    /**
     * Create and return a new instance of an implementation of TertiaryVennModel3
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @return a new instance of an implementation of TertiaryVennModel3 with the
     *    specified sets to test
     */
    protected abstract <T> TertiaryVennModel3<T> createTertiaryVennModel3(Set<? extends T> first,
            Set<? extends T> second,
            Set<? extends T> third);

    public void testCreateTertiaryVennModel3()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        assertNotNull(tertiaryVennModel);
    }

    public void testFirst()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> first = tertiaryVennModel.first();
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
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        ObservableSet<String> first = tertiaryVennModel.first();
        Listener listener = new Listener();
        first.addSetChangeListener(listener);
        first.add("garply");
        assertTrue(listener.heardChangeEvent());
        first.remove("garply");
    }

    public void testSecond()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> second = tertiaryVennModel.second();
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

    public void testSecondListener()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        ObservableSet<String> second = tertiaryVennModel.second();
        Listener listener = new Listener();
        second.addSetChangeListener(listener);
        second.add("garply");
        assertTrue(listener.heardChangeEvent());
        second.remove("garply");
    }

    public void testThird()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> third = tertiaryVennModel.third();
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

    public void testThirdListener()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        ObservableSet<String> third = tertiaryVennModel.third();
        Listener listener = new Listener();
        third.addSetChangeListener(listener);
        third.add("garply");
        assertTrue(listener.heardChangeEvent());
        third.remove("garply");
    }

    public void testFirstOnly()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> first = tertiaryVennModel.first();
        Set<String> firstOnly = tertiaryVennModel.firstOnly();
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
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> second = tertiaryVennModel.second();
        Set<String> secondOnly = tertiaryVennModel.secondOnly();
        assertNotNull(secondOnly);
        assertEquals(0, secondOnly.size());

        second.add("garply");
        assertEquals(1, secondOnly.size());
        assertTrue(secondOnly.contains("garply"));

        second.remove("garply");
        assertEquals(0, secondOnly.size());
        assertFalse(secondOnly.contains("garply"));
    }

    public void testSecondOnlyIsImmutable()
    {
        // todo
    }

    public void testThirdOnly()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> third = tertiaryVennModel.third();
        Set<String> thirdOnly = tertiaryVennModel.thirdOnly();
        assertNotNull(thirdOnly);
        assertEquals(1, thirdOnly.size());
        assertTrue(thirdOnly.contains("qux"));

        third.add("garply");
        assertEquals(2, thirdOnly.size());
        assertTrue(thirdOnly.contains("qux"));
        assertTrue(thirdOnly.contains("garply"));

        third.remove("garply");
        assertEquals(1, thirdOnly.size());
        assertTrue(thirdOnly.contains("qux"));
        assertFalse(thirdOnly.contains("garply"));
    }

    public void testThirdOnlyIsImmutable()
    {
        // todo
    }

    public void testIntersection()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> intersection = tertiaryVennModel.intersection();
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

    public void testUnion()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> union = tertiaryVennModel.union();
        assertNotNull(union);
        assertEquals(4, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        tertiaryVennModel.first().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        tertiaryVennModel.second().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        tertiaryVennModel.third().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        tertiaryVennModel.first().remove("garply");
        tertiaryVennModel.second().remove("garply");
        tertiaryVennModel.third().remove("garply");
        assertEquals(4, union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }

    public void testSelection()
    {
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        Set<String> first = tertiaryVennModel.first();
        Set<String> second = tertiaryVennModel.second();
        Set<String> third = tertiaryVennModel.third();
        Set<String> selection = tertiaryVennModel.selection();
        assertNotNull(selection);
        assertTrue(selection.isEmpty());

        selection.add("foo");
        assertEquals(1, selection.size());
        assertTrue(selection.contains("foo"));

        selection.remove("foo");
        assertEquals(0, selection.size());
        assertFalse(selection.contains("foo"));

        selection.add("foo");
        selection.add("bar");
        selection.add("qux");
        assertEquals(3, selection.size());
        assertTrue(selection.contains("foo"));
        assertTrue(selection.contains("bar"));
        assertTrue(selection.contains("qux"));

        // selection reflects changes to first, second, third views
        first.remove("foo");
        assertEquals(2, selection.size());
        assertFalse(selection.contains("foo"));

        first.remove("bar");
        second.remove("bar");
        third.remove("bar");
        assertEquals(1, selection.size());
        assertFalse(selection.contains("bar"));

        third.remove("qux");
        assertEquals(0, selection.size());
        assertFalse(selection.contains("qux"));

        first.add("foo");
        first.add("bar");
        second.add("bar");
        third.add("bar");
        third.add("qux");

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
        TertiaryVennModel3<String> tertiaryVennModel = createTertiaryVennModel3(FIRST, SECOND, THIRD);
        ObservableSet<String> selection = tertiaryVennModel.selection();
        Listener listener = new Listener();
        selection.addSetChangeListener(listener);
        selection.add("foo");
        assertTrue(listener.heardChangeEvent());
    }
}