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
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

/**
 * Abstract unit test for implementations of QuaternaryVennModel3.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractQuaternaryVennModel3Test
    extends TestCase
{
    /** Union of test values. */
    private static final List<String> UNION = Arrays.asList(new String[]
        {
            "f - s - t - r",
            "s - f - t - r",
            "t - f - s - r",
            "r - f - s - t",
            "f n s - t - r",
            "f n t - s - r",
            "f n r - s - t",
            "s n t - f - r",
            "s n r - f - t",
            "t n r - f - s",
            "f u s u t - r",
            "f u s u r - t",
            "f u t u r - s",
            "s u t u r - f",
            "f n s n t n r"
        });

    /** First set. */
    protected static final Set<String> FIRST = new HashSet<String>(Arrays.asList(new String[]
        {
            "f - s - t - r",
            "f n s - t - r",
            "f n t - s - r",
            "f n r - s - t",
            "f u s u t - r",
            "f u s u r - t",
            "f u t u r - s",
            "f n s n t n r",
        }));

    /** Second set. */
    protected static final Set<String> SECOND = new HashSet<String>(Arrays.asList(new String[]
        {
            "s - f - t - r",
            "f n s - t - r",
            "s n t - f - r",
            "s n r - f - t",
            "f u s u t - r",
            "f u s u r - t",
            "s u t u r - f",
            "f n s n t n r",
        }));

    /** Third set. */
    protected static final Set<String> THIRD = new HashSet<String>(Arrays.asList(new String[]
        {
            "t - f - s - r",
            "f n t - s - r",
            "s n t - f - r",
            "t n r - f - s",
            "f u s u t - r",
            "f u t u r - s",
            "s u t u r - f",
            "f n s n t n r",
        }));

    /** Fourth set. */
    protected static final Set<String> FOURTH = new HashSet<String>(Arrays.asList(new String[]
        {
            "r - f - s - t",
            "f n r - s - t",
            "s n r - f - t",
            "t n r - f - s",
            "f u s u r - t",
            "f u t u r - s",
            "s u t u r - f",
            "f n s n t n r"
        }));

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
     * Create and return a new instance of an implementation of QuaternaryVennModel3
     * with the specified sets to test.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @param fourth fourth set, must not be null
     * @return a new instance of an implementation of QuaternaryVennModel3 with the
     *    specified sets to test
     */
    protected abstract <T> QuaternaryVennModel3<T> createQuaternaryVennModel3(Set<? extends T> first,
                                                                            Set<? extends T> second,
                                                                            Set<? extends T> third,
                                                                            Set<? extends T> fourth);


    public void testCreateQuaternaryVennModel3()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        assertNotNull(quaternaryVennModel);
    }

    public void testFirst()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> first = quaternaryVennModel.first();
        assertNotNull(first);
        assertEquals(8, first.size());
        assertTrue(first.contains("f - s - t - r"));

        first.add("garply");
        assertEquals(9, first.size());
        assertTrue(first.contains("garply"));

        first.remove("garply");
        assertEquals(8, first.size());
        assertFalse(first.contains("garply"));
    }

    public void testFirstListener()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        ObservableSet<String> first = quaternaryVennModel.first();
        Listener listener = new Listener();
        first.addSetChangeListener(listener);
        first.add("garply");
        assertTrue(listener.heardChangeEvent());
        first.remove("garply");
    }

    public void testSecond()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> second = quaternaryVennModel.second();
        assertNotNull(second);
        assertEquals(8, second.size());
        assertTrue(second.contains("s - f - t - r"));

        second.add("garply");
        assertEquals(9, second.size());
        assertTrue(second.contains("garply"));

        second.remove("garply");
        assertEquals(8, second.size());
        assertFalse(second.contains("garply"));
    }

    public void testSecondListener()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        ObservableSet<String> second = quaternaryVennModel.second();
        Listener listener = new Listener();
        second.addSetChangeListener(listener);
        second.add("garply");
        assertTrue(listener.heardChangeEvent());
        second.remove("garply");
    }

    public void testThird()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> third = quaternaryVennModel.third();
        assertNotNull(third);
        assertEquals(8, third.size());
        assertTrue(third.contains("t - f - s - r"));

        third.add("garply");
        assertEquals(9, third.size());
        assertTrue(third.contains("garply"));

        third.remove("garply");
        assertEquals(8, third.size());
        assertFalse(third.contains("garply"));
    }

    public void testThirdListener()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        ObservableSet<String> third = quaternaryVennModel.third();
        Listener listener = new Listener();
        third.addSetChangeListener(listener);
        third.add("garply");
        assertTrue(listener.heardChangeEvent());
        third.remove("garply");
    }

    public void testFourth()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> fourth = quaternaryVennModel.fourth();
        assertNotNull(fourth);
        assertEquals(8, fourth.size());
        assertTrue(fourth.contains("r - f - s - t"));

        fourth.add("garply");
        assertEquals(9, fourth.size());
        assertTrue(fourth.contains("garply"));

        fourth.remove("garply");
        assertEquals(8, fourth.size());
        assertFalse(fourth.contains("garply"));
    }

    public void testFourthListener()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        ObservableSet<String> third = quaternaryVennModel.third();
        Listener listener = new Listener();
        third.addSetChangeListener(listener);
        third.add("garply");
        assertTrue(listener.heardChangeEvent());
        third.remove("garply");
    }

    public void testFirstOnly()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> first = quaternaryVennModel.first();
        Set<String> firstOnly = quaternaryVennModel.firstOnly();
        assertNotNull(firstOnly);
        assertEquals(1, firstOnly.size());
        assertTrue(firstOnly.contains("f - s - t - r"));

        first.add("garply");
        assertEquals(2, firstOnly.size());
        assertTrue(firstOnly.contains("f - s - t - r"));
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
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> second = quaternaryVennModel.second();
        Set<String> secondOnly = quaternaryVennModel.secondOnly();
        assertNotNull(secondOnly);
        assertEquals(1, secondOnly.size());
        assertTrue(secondOnly.contains("s - f - t - r"));

        second.add("garply");
        assertEquals(2, secondOnly.size());
        assertTrue(secondOnly.contains("s - f - t - r"));
        assertTrue(secondOnly.contains("garply"));

        second.remove("garply");
        assertEquals(1, secondOnly.size());
        assertFalse(secondOnly.contains("garply"));
    }

    public void testSecondOnlyIsImmutable()
    {
        // todo
    }

    public void testThirdOnly()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> third = quaternaryVennModel.third();
        Set<String> thirdOnly = quaternaryVennModel.thirdOnly();
        assertNotNull(thirdOnly);
        assertEquals(1, thirdOnly.size());
        assertTrue(thirdOnly.contains("t - f - s - r"));

        third.add("garply");
        assertEquals(2, thirdOnly.size());
        assertTrue(thirdOnly.contains("t - f - s - r"));
        assertTrue(thirdOnly.contains("garply"));

        third.remove("garply");
        assertEquals(1, thirdOnly.size());
        assertFalse(thirdOnly.contains("garply"));
    }

    public void testThirdOnlyIsImmutable()
    {
        // todo
    }

    public void testFourthOnly()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> fourth = quaternaryVennModel.fourth();
        Set<String> fourthOnly = quaternaryVennModel.fourthOnly();
        assertNotNull(fourthOnly);
        assertEquals(1, fourthOnly.size());
        assertTrue(fourthOnly.contains("r - f - s - t"));

        fourth.add("garply");
        assertEquals(2, fourthOnly.size());
        assertTrue(fourthOnly.contains("r - f - s - t"));
        assertTrue(fourthOnly.contains("garply"));

        fourth.remove("garply");
        assertEquals(1, fourthOnly.size());
        assertFalse(fourthOnly.contains("garply"));
    }

    public void testFourthOnlyIsImmutable()
    {
        // todo
    }

    public void testFirstSecond()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> firstSecond = quaternaryVennModel.firstSecond();
        assertNotNull(firstSecond);
        assertTrue(firstSecond.contains("f n s - t - r"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.second().add("garply");
        assertEquals(2, firstSecond.size());
        assertTrue(firstSecond.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        assertEquals(1, firstSecond.size());
        assertFalse(firstSecond.contains("garply"));
    }

    public void testFirstSecondIsImmutable()
    {
        // todo
    }

    public void testFirstThird()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> firstThird = quaternaryVennModel.firstThird();
        assertNotNull(firstThird);
        assertTrue(firstThird.contains("f n t - s - r"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.third().add("garply");
        assertEquals(2, firstThird.size());
        assertTrue(firstThird.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.third().remove("garply");
        assertEquals(1, firstThird.size());
        assertFalse(firstThird.contains("garply"));
    }

    public void testFirstThirdIsImmutable()
    {
        // todo
    }

    public void testFirstFourth()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> firstFourth = quaternaryVennModel.firstFourth();
        assertNotNull(firstFourth);
        assertTrue(firstFourth.contains("f n r - s - t"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.fourth().add("garply");
        assertEquals(2, firstFourth.size());
        assertTrue(firstFourth.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(1, firstFourth.size());
        assertFalse(firstFourth.contains("garply"));
    }

    public void testFirstFourthIsImmutable()
    {
        // todo
    }

    public void testSecondThird()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> secondThird = quaternaryVennModel.secondThird();
        assertNotNull(secondThird);
        assertTrue(secondThird.contains("s n t - f - r"));

        quaternaryVennModel.second().add("garply");
        quaternaryVennModel.third().add("garply");
        assertEquals(2, secondThird.size());
        assertTrue(secondThird.contains("garply"));

        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        assertEquals(1, secondThird.size());
        assertFalse(secondThird.contains("garply"));
    }

    public void testSecondThirdIsImmutable()
    {
        // todo
    }

    public void testSecondFourth()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> secondFourth = quaternaryVennModel.secondFourth();
        assertNotNull(secondFourth);
        assertTrue(secondFourth.contains("s n r - f - t"));

        quaternaryVennModel.second().add("garply");
        quaternaryVennModel.fourth().add("garply");
        assertEquals(2, secondFourth.size());
        assertTrue(secondFourth.contains("garply"));

        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(1, secondFourth.size());
        assertFalse(secondFourth.contains("garply"));
    }

    public void testSecondFourthIsImmutable()
    {
        // todo
    }

    public void testThirdFourth()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> thirdFourth = quaternaryVennModel.thirdFourth();
        assertNotNull(thirdFourth);
        assertTrue(thirdFourth.contains("t n r - f - s"));

        quaternaryVennModel.third().add("garply");
        quaternaryVennModel.fourth().add("garply");
        assertEquals(2, thirdFourth.size());
        assertTrue(thirdFourth.contains("garply"));

        quaternaryVennModel.third().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(1, thirdFourth.size());
        assertFalse(thirdFourth.contains("garply"));
    }

    public void testThirdFourthIsImmutable()
    {
        // todo
    }

    public void testFirstSecondThird()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> firstSecondThird = quaternaryVennModel.firstSecondThird();
        assertNotNull(firstSecondThird);
        assertTrue(firstSecondThird.contains("f u s u t - r"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.second().add("garply");
        quaternaryVennModel.third().add("garply");
        assertEquals(2, firstSecondThird.size());
        assertTrue(firstSecondThird.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        assertEquals(1, firstSecondThird.size());
        assertFalse(firstSecondThird.contains("garply"));
    }

    public void testFirstSecondThirdIsImmutable()
    {
        // todo
    }

    public void testFirstSecondFourth()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> firstSecondFourth = quaternaryVennModel.firstSecondFourth();
        assertNotNull(firstSecondFourth);
        assertTrue(firstSecondFourth.contains("f u s u r - t"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.second().add("garply");
        quaternaryVennModel.fourth().add("garply");
        assertEquals(2, firstSecondFourth.size());
        assertTrue(firstSecondFourth.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(1, firstSecondFourth.size());
        assertFalse(firstSecondFourth.contains("garply"));
    }

    public void testFirstSecondFourthIsImmutable()
    {
        // todo
    }

    public void testSecondThirdFourth()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> secondThirdFourth = quaternaryVennModel.secondThirdFourth();
        assertNotNull(secondThirdFourth);
        assertTrue(secondThirdFourth.contains("s u t u r - f"));

        quaternaryVennModel.second().add("garply");
        quaternaryVennModel.third().add("garply");
        quaternaryVennModel.fourth().add("garply");
        assertEquals(2, secondThirdFourth.size());
        assertTrue(secondThirdFourth.contains("garply"));

        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(1, secondThirdFourth.size());
        assertFalse(secondThirdFourth.contains("garply"));
    }

    public void testSecondThirdFourthIsImmutable()
    {
        // todo
    }

    public void testFirstThirdFourth()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> firstThirdFourth = quaternaryVennModel.firstThirdFourth();
        assertNotNull(firstThirdFourth);
        assertTrue(firstThirdFourth.contains("f u t u r - s"));

        quaternaryVennModel.first().add("garply");
        quaternaryVennModel.third().add("garply");
        quaternaryVennModel.fourth().add("garply");
        assertEquals(2, firstThirdFourth.size());
        assertTrue(firstThirdFourth.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.third().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(1, firstThirdFourth.size());
        assertFalse(firstThirdFourth.contains("garply"));
    }

    public void testFirstThirdFourthIsImmutable()
    {
        // todo
    }

    public void testIntersection()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> intersection = quaternaryVennModel.intersection();
        assertNotNull(intersection);
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains("f n s n t n r"));

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

    public void testUnion()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> union = quaternaryVennModel.union();
        assertNotNull(union);
        assertEquals(UNION.size(), union.size());
        for (String value : UNION)
        {
            assertTrue("union contains " + value, union.contains(value));
        }

        quaternaryVennModel.first().add("garply");
        assertEquals(UNION.size() + 1, union.size());
        assertTrue(union.contains("garply"));

        quaternaryVennModel.second().add("garply");
        assertEquals(UNION.size() + 1, union.size());
        assertTrue(union.contains("garply"));

        quaternaryVennModel.third().add("garply");
        assertEquals(UNION.size() + 1, union.size());
        assertTrue(union.contains("garply"));

        quaternaryVennModel.fourth().add("garply");
        assertEquals(UNION.size() + 1, union.size());
        assertTrue(union.contains("garply"));

        quaternaryVennModel.first().remove("garply");
        quaternaryVennModel.second().remove("garply");
        quaternaryVennModel.third().remove("garply");
        quaternaryVennModel.fourth().remove("garply");
        assertEquals(UNION.size(), union.size());
        assertFalse(union.contains("garply"));
    }

    public void testUnionIsImmutable()
    {
        // todo
    }

    public void testSelection()
    {
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        Set<String> first = quaternaryVennModel.first();
        Set<String> second = quaternaryVennModel.second();
        Set<String> third = quaternaryVennModel.third();
        Set<String> fourth = quaternaryVennModel.fourth();
        Set<String> selection = quaternaryVennModel.selection();
        assertNotNull(selection);
        assertTrue(selection.isEmpty());

        selection.add("f - s - t - r");
        assertEquals(1, selection.size());
        assertTrue(selection.contains("f - s - t - r"));

        selection.remove("f - s - t - r");
        assertEquals(0, selection.size());
        assertFalse(selection.contains("f - s - t - r"));

        selection.add("f - s - t - r");
        selection.add("s - f - t - r");
        selection.add("t - f - s - r");
        selection.add("r - f - s - t");
        assertEquals(4, selection.size());
        assertTrue(selection.contains("f - s - t - r"));
        assertTrue(selection.contains("s - f - t - r"));
        assertTrue(selection.contains("t - f - s - r"));
        assertTrue(selection.contains("r - f - s - t"));

        // selection reflects changes to first, second, third, and fourth views
        first.remove("f - s - t - r");
        assertEquals(3, selection.size());
        assertFalse(selection.contains("f - s - t - r"));

        second.remove("s - f - t - r");
        assertEquals(2, selection.size());
        assertFalse(selection.contains("s - f - t - r"));

        third.remove("t - f - s - r");
        assertEquals(1, selection.size());
        assertFalse(selection.contains("t - f - s - r"));

        fourth.remove("r - f - s - t");
        assertEquals(0, selection.size());
        assertFalse(selection.contains("r - f - s - t"));

        first.add("f - s - t - r");
        second.add("s - f - t - r");
        third.add("t - f - s - r");
        fourth.add("r - f - s - t");

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
        QuaternaryVennModel3<String> quaternaryVennModel = createQuaternaryVennModel3(FIRST, SECOND, THIRD, FOURTH);
        ObservableSet<String> selection = quaternaryVennModel.selection();
        Listener listener = new Listener();
        selection.addSetChangeListener(listener);
        selection.add("f - s - t - r");
        assertTrue(listener.heardChangeEvent());
    }
}