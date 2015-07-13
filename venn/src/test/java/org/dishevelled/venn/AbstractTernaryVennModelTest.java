/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2015 held jointly by the individual authors.

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
     * Create and return a new instance of an implementation of TernaryVennModel
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @return a new instance of an implementation of TernaryVennModel with the
     *    specified sets to test
     */
    protected abstract <T> TernaryVennModel<T> createTernaryVennModel(Set<? extends T> first,
                                                                      Set<? extends T> second,
                                                                      Set<? extends T> third);

    public void testCreateTernaryVennModel()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        assertNotNull(ternaryVennModel);

        try
        {
            createTernaryVennModel(null, SECOND, THIRD);
            fail("createTernaryVennModel(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            createTernaryVennModel(FIRST, null, THIRD);
            fail("createTernaryVennModel(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            createTernaryVennModel(FIRST, SECOND, null);
            fail("createTernaryVennModel(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            createTernaryVennModel(null, null, null);
            fail("createTernaryVennModel(null, null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testFirst()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> first = ternaryVennModel.first();
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
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        ObservableSet<String> first = ternaryVennModel.first();
        Listener listener = new Listener();
        first.addSetChangeListener(listener);
        first.add("garply");
        assertTrue(listener.heardChangeEvent());
        first.remove("garply");
    }

    public void testSecond()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> second = ternaryVennModel.second();
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
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        ObservableSet<String> second = ternaryVennModel.second();
        Listener listener = new Listener();
        second.addSetChangeListener(listener);
        second.add("garply");
        assertTrue(listener.heardChangeEvent());
        second.remove("garply");
    }

    public void testThird()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> third = ternaryVennModel.third();
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
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        ObservableSet<String> third = ternaryVennModel.third();
        Listener listener = new Listener();
        third.addSetChangeListener(listener);
        third.add("garply");
        assertTrue(listener.heardChangeEvent());
        third.remove("garply");
    }

    public void testFirstOnly()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> first = ternaryVennModel.first();
        Set<String> firstOnly = ternaryVennModel.firstOnly();
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
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> second = ternaryVennModel.second();
        Set<String> secondOnly = ternaryVennModel.secondOnly();
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
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> third = ternaryVennModel.third();
        Set<String> thirdOnly = ternaryVennModel.thirdOnly();
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

    public void testFirstSecond()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> firstSecond = ternaryVennModel.firstSecond();
        assertNotNull(firstSecond);
        assertTrue(firstSecond.isEmpty());

        ternaryVennModel.first().add("garply");
        ternaryVennModel.second().add("garply");
        assertEquals(1, firstSecond.size());
        assertTrue(firstSecond.contains("garply"));

        ternaryVennModel.first().remove("garply");
        ternaryVennModel.second().remove("garply");
        assertEquals(0, firstSecond.size());
        assertFalse(firstSecond.contains("garply"));
    }

    public void testFirstSecondIsImmutable()
    {
        // todo
    }

    public void testFirstThird()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> firstThird = ternaryVennModel.firstThird();
        assertNotNull(firstThird);
        assertTrue(firstThird.isEmpty());

        ternaryVennModel.first().add("garply");
        ternaryVennModel.third().add("garply");
        assertEquals(1, firstThird.size());
        assertTrue(firstThird.contains("garply"));

        ternaryVennModel.first().remove("garply");
        ternaryVennModel.third().remove("garply");
        assertEquals(0, firstThird.size());
        assertFalse(firstThird.contains("garply"));
    }

    public void testFirstThirdIsImmutable()
    {
        // todo
    }

    public void testSecondThird()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> secondThird = ternaryVennModel.secondThird();
        assertNotNull(secondThird);
        assertTrue(secondThird.contains("baz"));

        ternaryVennModel.second().add("garply");
        ternaryVennModel.third().add("garply");
        assertEquals(2, secondThird.size());
        assertTrue(secondThird.contains("garply"));

        ternaryVennModel.second().remove("garply");
        ternaryVennModel.third().remove("garply");
        assertEquals(1, secondThird.size());
        assertFalse(secondThird.contains("garply"));
    }

    public void testSecondThirdIsImmutable()
    {
        // todo
    }

    public void testIntersection()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> intersection = ternaryVennModel.intersection();
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

    public void testUnion()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> union = ternaryVennModel.union();
        assertNotNull(union);
        assertEquals(4, union.size());
        assertTrue(union.contains("foo"));
        assertTrue(union.contains("bar"));
        assertTrue(union.contains("baz"));
        assertTrue(union.contains("qux"));

        ternaryVennModel.first().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        ternaryVennModel.second().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        ternaryVennModel.third().add("garply");
        assertEquals(5, union.size());
        assertTrue(union.contains("garply"));

        ternaryVennModel.first().remove("garply");
        ternaryVennModel.second().remove("garply");
        ternaryVennModel.third().remove("garply");
        assertEquals(4, union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }

    public void testSelection()
    {
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        Set<String> first = ternaryVennModel.first();
        Set<String> second = ternaryVennModel.second();
        Set<String> third = ternaryVennModel.third();
        Set<String> selection = ternaryVennModel.selection();
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
        TernaryVennModel<String> ternaryVennModel = createTernaryVennModel(FIRST, SECOND, THIRD);
        ObservableSet<String> selection = ternaryVennModel.selection();
        Listener listener = new Listener();
        selection.addSetChangeListener(listener);
        selection.add("foo");
        assertTrue(listener.heardChangeEvent());
    }
}