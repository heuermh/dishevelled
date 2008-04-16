/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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
package org.dishevelled.matrix;

import junit.framework.TestCase;

import org.apache.commons.lang.mutable.MutableInt;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.QuaternaryProcedure;

/**
 * Abstract unit test for implementations of ObjectMatrix3D.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObjectMatrix3DTest
    extends TestCase
{

    /**
     * Create a new instance of ObjectMatrix3D to test.
     *
     * @param slices slices
     * @param rows rows
     * @param columns columns
     * @return a new instance of ObjectMatrix3D to test
     */
    protected abstract <T> ObjectMatrix3D<T> createObjectMatrix3D(long slices, long rows, long columns);

    public void testSize()
    {
        ObjectMatrix3D<String> m0 = createObjectMatrix3D(0, 0, 0);
        assertNotNull("m0 not null", m0);
        assertEquals("m0 size == 0", 0, m0.size());
        assertEquals("m0 slices == 0", 0, m0.slices());
        assertEquals("m0 rows == 0", 0, m0.rows());
        assertEquals("m0 columns == 0", 0, m0.columns());

        ObjectMatrix3D<String> m1 = createObjectMatrix3D(1, 1, 1);
        assertNotNull("m1 not null", m1);
        assertEquals("m1 size == 1", 1, m1.size());
        assertEquals("m1 slices == 1", 1, m1.slices());
        assertEquals("m1 rows == 1", 1, m1.rows());
        assertEquals("m1 columns == 1", 1, m1.columns());

        ObjectMatrix3D<String> m2 = createObjectMatrix3D(10, 10, 10);
        assertNotNull("m2 not null", m2);
        assertEquals("m2 size == 1000", 1000, m2.size());
        assertEquals("m2 slices == 10", 10, m2.slices());
        assertEquals("m2 rows == 10", 10, m2.rows());
        assertEquals("m2 columns == 10", 10, m2.columns());

        ObjectMatrix3D<String> m3 = createObjectMatrix3D(100, 100, 100);
        assertNotNull("m3 not null", m3);
        assertEquals("m3 size == 1000000", 1000000, m3.size());
        assertEquals("m3 slices == 100", 100, m3.slices());
        assertEquals("m3 rows == 100", 100, m3.rows());
        assertEquals("m3 columns == 100", 100, m3.columns());

        ObjectMatrix3D<String> m4 = createObjectMatrix3D(1000, 1000, 10);
        assertNotNull("m4 not null", m4);
        assertEquals("m4 size == 10000000", 10000000, m4.size());
        assertEquals("m4 slices == 1000", 1000, m4.slices());
        assertEquals("m4 rows == 1000", 1000, m4.rows());
        assertEquals("m4 columns == 10", 10, m4.columns());
    }

    public void testCardinality()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        assertNotNull("m not null", m);
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        m.set(0, 0, 0, "foo");
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 1", 1, m.cardinality());

        m.set(0, 0, 1, "bar");
        m.set(0, 0, 2, "baz");
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 3", 3, m.cardinality());

        m.clear();
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        m.assign("foo");
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 1000", 1000, m.cardinality());

        m.clear();
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());
    }

    public void testIsEmpty()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        assertNotNull("m not null");
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertTrue("m isEmpty", m.isEmpty());

        m.set(0, 0, 0, "foo");
        assertEquals("m cardinality == 1", 1, m.cardinality());
        assertFalse("m isEmpty == false", m.isEmpty());

        m.clear();
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertTrue("m isEmpty", m.isEmpty());
    }

    public void testGet()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        assertNotNull("m not null");
        assertEquals("m size == 1000", 1000, m.size());

        assertEquals("m.get(0, 0, 0) == null", null, m.get(0, 0, 0));
        assertEquals("m.get(9, 9, 9) == null", null, m.get(9, 9, 9));

        m.set(0, 0, 0, "foo");
        assertEquals("m.get(0, 0, 0) == foo", "foo", m.get(0, 0, 0));
        assertEquals("m.get(9, 9, 9) == null", null, m.get(9, 9, 9));

        m.clear();
        assertEquals("m.get(0, 0, 0) == null", null, m.get(0, 0, 0));
        assertEquals("m.get(9, 9, 9) == null", null, m.get(9, 9, 9));

        m.set(0, 0, 0, "foo");
        assertEquals("m.getQuick(0, 0, 0) == foo", "foo", m.getQuick(0, 0, 0));
        assertEquals("m.getQuick(9, 9, 9) == null", null, m.getQuick(9, 9, 9));

        m.clear();
        assertEquals("m.getQuick(0, 0, 0) == null", null, m.getQuick(0, 0, 0));
        assertEquals("m.getQuick(9, 9, 9) == null", null, m.getQuick(9, 9, 9));

        try
        {
            m.get(-1, 0, 0);
            fail("get(-1,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(0, -1, 0);
            fail("get(,-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(0, 0, -1);
            fail("get(,,-1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(10, 0, 0);
            fail("get(10,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(0, 10, 0);
            fail("get(,10,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(0, 0, 10);
            fail("get(,,10) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testSet()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        assertNotNull("m not null");
        assertEquals("m size == 1000", 1000, m.size());

        m.set(0, 0, 0, "foo");
        assertEquals("m.get(0, 0, 0) == foo", "foo", m.get(0, 0, 0));

        m.set(0, 0, 0, null);
        assertEquals("m.get(0, 0, 0) == null", null, m.get(0, 0, 0));

        m.setQuick(0, 0, 0, "foo");
        assertEquals("m.get(0, 0, 0) == foo", "foo", m.get(0, 0, 0));

        m.setQuick(0, 0, 0, null);
        assertEquals("m.get(0, 0, 0) == null", null, m.get(0, 0, 0));

        try
        {
            m.set(-1, 0, 0, "foo");
            fail("set(-1,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(0, -1, 0, "foo");
            fail("set(,-1,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(0, 0, -1, "foo");
            fail("set(,,-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(10, 0, 0, "foo");
            fail("set(10,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(0, 10, 0, "foo");
            fail("set(,10,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(0, 0, 10, "foo");
            fail("set(,,10,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testAssign()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        assertNotNull("m not null");
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        ObjectMatrix3D<String> m0 = m.assign((String) null);
        assertNotNull("m0 not null", m0);
        assertEquals("m0.get(0, 0, 0) == null", null, m0.get(0, 0, 0));
        assertEquals("m0.get(0, 1, 0) == null", null, m0.get(0, 1, 0));
        assertEquals("m0.get(9, 9, 9) == null", null, m0.get(9, 9, 9));
        assertEquals("m0.cardinality() == 0", 0, m0.cardinality());
        assertEquals("m0 == m", m, m0);

        ObjectMatrix3D<String> m1 = m.assign("foo");
        assertNotNull("m1 not null", m1);
        assertEquals("m1.get(0, 0, 0) == foo", "foo", m1.get(0, 0, 0));
        assertEquals("m1.get(0, 1, 0) == foo", "foo", m1.get(0, 1, 0));
        assertEquals("m1.get(9, 9, 9) == foo", "foo", m1.get(9, 9, 9));
        assertEquals("m1.cardinality() == 1000", 1000, m1.cardinality());
        assertEquals("m1 == m", m, m1);

        ObjectMatrix3D<String> m2 = m.assign((String) null);
        assertNotNull("m2 not null", m2);
        assertEquals("m2.get(0, 0, 0) == null", null, m2.get(0, 0, 0));
        assertEquals("m2.get(0, 1, 0) == null", null, m2.get(0, 1, 0));
        assertEquals("m2.get(9, 9, 9) == null", null, m2.get(9, 9, 9));
        assertEquals("m2.cardinality() == 0", 0, m2.cardinality());
        assertEquals("m2 == m", m, m2);

        ObjectMatrix3D<String> m3 = m.assign(new UnaryFunction<String, String>()
            {
                public String evaluate(final String s)
                {
                    return null;
                }
            });
        assertNotNull("m3 not null", m3);
        assertEquals("m3.get(0, 0, 0) == null", null, m3.get(0, 0, 0));
        assertEquals("m3.get(0, 1, 0) == null", null, m3.get(0, 1, 0));
        assertEquals("m3.get(9, 9, 9) == null", null, m3.get(9, 9, 9));
        assertEquals("m3.cardinality() == 0", 0, m3.cardinality());
        assertEquals("m3 == m", m, m3);

        ObjectMatrix3D<String> m4 = m.assign(new UnaryFunction<String, String>()
            {
                public String evaluate(final String s)
                {
                    return "foo";
                }
            });
        assertNotNull("m4 not null", m4);
        assertEquals("m4.get(0, 0, 0) == foo", "foo", m4.get(0, 0, 0));
        assertEquals("m4.get(0, 1, 0) == foo", "foo", m4.get(0, 1, 0));
        assertEquals("m4.get(9, 9, 9) == foo", "foo", m4.get(9, 9, 9));
        assertEquals("m4.cardinality() == 1000", 1000, m4.cardinality());
        assertEquals("m4 == m", m, m4);

        try
        {
            ObjectMatrix3D<String> m5 = m.assign((UnaryFunction<String,String>) null);
            fail("assign((UnaryFunction<String,String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        ObjectMatrix3D<String> nulls = createObjectMatrix3D(10, 10, 10);
        nulls.assign((String) null);

        ObjectMatrix3D<String> bars = createObjectMatrix3D(10, 10, 10);
        bars.assign("bar");

        ObjectMatrix3D<String> bazs = createObjectMatrix3D(10, 10, 10);
        bazs.assign("baz");

        ObjectMatrix3D<String> m6 = m.assign(nulls);
        assertNotNull("m6 not null", m6);
        assertEquals("m6.get(0, 0, 0) == null", null, m6.get(0, 0, 0));
        assertEquals("m6.get(0, 1, 0) == null", null, m6.get(0, 1, 0));
        assertEquals("m6.get(9, 9, 9) == null", null, m6.get(9, 9, 9));
        assertEquals("m6.cardinality() == 0", 0, m6.cardinality());
        assertEquals("m6 == m", m, m6);

        ObjectMatrix3D<String> m7 = m.assign(bars);
        assertNotNull("m7 not null", m7);
        assertEquals("m7.get(0, 0, 0) == bar", "bar", m7.get(0, 0, 0));
        assertEquals("m7.get(0, 1, 0) == bar", "bar", m7.get(0, 1, 0));
        assertEquals("m7.get(9, 9, 9) == bar", "bar", m7.get(9, 9, 9));
        assertEquals("m7.cardinality() == 1000", 1000, m7.cardinality());
        assertEquals("m7 == m", m, m7);

        ObjectMatrix3D<String> m8 = m.assign(bazs);
        assertNotNull("m8 not null", m8);
        assertEquals("m8.get(0, 0, 0) == baz", "baz", m8.get(0, 0, 0));
        assertEquals("m8.get(0, 1, 0) == baz", "baz", m8.get(0, 1, 0));
        assertEquals("m8.get(9, 9, 9) == baz", "baz", m8.get(9, 9, 9));
        assertEquals("m8.cardinality() == 1000", 1000, m8.cardinality());
        assertEquals("m8 == m", m, m8);

        try
        {
            ObjectMatrix3D<String> m9 = m.assign((ObjectMatrix3D<String>) null);
            fail("assign((ObjectMatrix3D<E>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ObjectMatrix3D<String> tooSmall = createObjectMatrix3D(9, 10, 10);
            m.assign(tooSmall);
            fail("assign(tooSmall) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ObjectMatrix3D<String> tooBig = createObjectMatrix3D(10, 11, 10);
            m.assign(tooBig);
            fail("assign(tooBig) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        m.clear();
        ObjectMatrix3D<String> m10 = m.assign(nulls,
                                              new BinaryFunction<String, String, String>()
            {
                public String evaluate(final String s0, final String s1)
                {
                    return "foo";
                }
            });
        assertNotNull("m10 not null", m10);
        assertEquals("m10.get(0, 0, 0) == foo", "foo", m10.get(0, 0, 0));
        assertEquals("m10.get(0, 1, 0) == foo", "foo", m10.get(0, 1, 0));
        assertEquals("m10.get(9, 9, 9) == foo", "foo", m10.get(9, 9, 9));
        assertEquals("m10.cardinality() == 1000", 1000, m10.cardinality());
        assertEquals("m10 == m", m, m10);

        ObjectMatrix3D<String> m11 = m.assign(bars,
                                              new BinaryFunction<String, String, String>()
            {
                public String evaluate(final String s0, final String s1)
                {
                    return null;
                }
            });
        assertNotNull("m11 not null", m11);
        assertEquals("m11.get(0, 0, 0) == null", null, m11.get(0, 0, 0));
        assertEquals("m11.get(0, 1, 0) == null", null, m11.get(0, 1, 0));
        assertEquals("m11.get(9, 9, 9) == null", null, m11.get(9, 9, 9));
        assertEquals("m11.cardinality() == 0", 0, m11.cardinality());
        assertEquals("m11 == m", m, m11);

        BinaryFunction<String,String,String> ignore = new BinaryFunction<String, String, String>()
            {
                public String evaluate(final String s0, final String s1)
                {
                    return null;
                }
            };

        try
        {
            ObjectMatrix3D<String> m12 = m.assign((ObjectMatrix3D<String>) null, ignore);
            fail("assign((ObjectMatrix3D<E>) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ObjectMatrix3D<String> tooSmall = createObjectMatrix3D(9, 10, 10);
            m.assign(tooSmall, ignore);
            fail("assign(tooSmall,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ObjectMatrix3D<String> tooBig = createObjectMatrix3D(10, 11, 10);
            m.assign(tooBig, ignore);
            fail("assign(tooBig,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.assign(m, null);
            fail("assign(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAggregate()
    {
        // TODO
    }

    public void testIterator()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        assertNotNull("m not null");
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        long count, nulls, foos;

        count = 0; nulls = 0; foos = 0;
        for (String s : m)
        {
            nulls += (s == null) ? 1 : 0;
            foos += ("foo".equals(s)) ? 1 : 0;
            count++;
        }
        assertEquals("count == size", m.size(), count);
        assertEquals("nulls == (size - cardinality)", (m.size() - m.cardinality()), nulls);
        assertEquals("foos == cardinality", m.cardinality(), foos);

        m.set(0, 0, 0, "foo");

        count = 0; nulls = 0; foos = 0;
        for (String s : m)
        {
            nulls += (s == null) ? 1 : 0;
            foos += ("foo".equals(s)) ? 1 : 0;
            count++;
        }
        assertEquals("count == size", m.size(), count);
        assertEquals("nulls == (size - cardinality)", (m.size() - m.cardinality()), nulls);
        assertEquals("foos == cardinality", m.cardinality(), foos);

        m.clear();

        count = 0; nulls = 0; foos = 0;
        for (String s : m)
        {
            nulls += (s == null) ? 1 : 0;
            foos += ("foo".equals(s)) ? 1 : 0;
            count++;
        }
        assertEquals("count == size", m.size(), count);
        assertEquals("nulls == (size - cardinality)", (m.size() - m.cardinality()), nulls);
        assertEquals("foos == cardinality", m.cardinality(), foos);

        m.assign("foo");

        count = 0; nulls = 0; foos = 0;
        for (String s : m)
        {
            nulls += (s == null) ? 1 : 0;
            foos += ("foo".equals(s)) ? 1 : 0;
            count++;
        }
        assertEquals("count == size", m.size(), count);
        assertEquals("nulls == (size - cardinality)", (m.size() - m.cardinality()), nulls);
        assertEquals("foos == cardinality", m.cardinality(), foos);

        m.set(0, 0, 0, null);
        m.set(0, 0, 1, null);
        m.set(0, 0, 2, null);

        count = 0; nulls = 0; foos = 0;
        for (String s : m)
        {
            nulls += (s == null) ? 1 : 0;
            foos += ("foo".equals(s)) ? 1 : 0;
            count++;
        }
        assertEquals("count == size", m.size(), count);
        assertEquals("nulls == (size - cardinality)", (m.size() - m.cardinality()), nulls);
        assertEquals("foos == cardinality", m.cardinality(), foos);
    }

    public void testForEachNonNull()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        final MutableInt count = new MutableInt();

        count.setValue(0);
        m.forEachNonNull(new UnaryProcedure<String>()
             {
                 public void run(final String s)
                 {
                     count.increment();
                 }
             });
        assertEquals("count == 0", 0, count.intValue());

        count.setValue(0);
        m.setQuick(0L, 0L, 0L, "foo");
        m.forEachNonNull(new UnaryProcedure<String>()
             {
                 public void run(final String s)
                 {
                     count.increment();
                 }
             });
        assertEquals("count == 1", 1, count.intValue());

        count.setValue(0);
        m.assign("foo");
        m.forEachNonNull(new UnaryProcedure<String>()
             {
                 public void run(final String s)
                 {
                     count.increment();
                 }
             });
        assertEquals("count == 1000", 1000, count.intValue());

        ObjectMatrix3D<String> rowFlip = m.viewRowFlip();
        count.setValue(0);
        rowFlip.forEachNonNull(new UnaryProcedure<String>()
             {
                 public void run(final String s)
                 {
                     count.increment();
                 }
             });
        assertEquals("count == 1000", 1000, count.intValue());

        ObjectMatrix2D<String> slice = m.viewSlice(0);
        count.setValue(0);
        slice.forEachNonNull(new UnaryProcedure<String>()
             {
                 public void run(final String s)
                 {
                     count.increment();
                 }
             });
        assertEquals("count == 100", 100, count.intValue());

        ObjectMatrix1D<String> firstRowOfSlice = slice.viewRow(0);
        count.setValue(0);
        firstRowOfSlice.forEachNonNull(new UnaryProcedure<String>()
             {
                 public void run(final String s)
                 {
                     count.increment();
                 }
             });
        assertEquals("count == 10", 10, count.intValue());

        try
        {
            m.forEachNonNull(null);
            fail("forEachNonNull(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testForEach()
    {
        // TODO
    }

    public void testViewSlice()
    {
        final ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        m.forEach(new QuaternaryProcedure<Long, Long, Long, String>()
            {
                public void run(final Long slice, final Long row, final Long column, final String s)
                {
                    m.setQuick(slice, row, column, slice + "x" + row + "x" + column);
                }
            });

        assertNotNull("m not null", m);
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 1000", 1000, m.cardinality());
        assertEquals("m get(0,0,0) == 0x0x0", "0x0x0", m.get(0, 0, 0));
        assertEquals("m get(0,1,0) == 0x1x0", "0x1x0", m.get(0, 1, 0));
        assertEquals("m get(0,0,1) == 0x0x1", "0x0x1", m.get(0, 0, 1));
        assertEquals("m get(9,0,0) == 9x0x0", "9x0x0", m.get(9, 0, 0));
        assertEquals("m get(0,9,0) == 0x9x0", "0x9x0", m.get(0, 9, 0));
        assertEquals("m get(9,9,9) == 9x9x9", "9x9x9", m.get(9, 9, 9));

        ObjectMatrix2D<String> firstSlice = m.viewSlice(0);
        assertNotNull("firstSlice not null", firstSlice);
        assertEquals("firstSlice size == 100", 100, firstSlice.size());
        assertEquals("firstSlice cardinality == 100", 100, firstSlice.cardinality());
        assertEquals("firstSlice.get(0, 0) == 0x0x0", "0x0x0", firstSlice.get(0, 0));
        assertEquals("firstSlice.get(1, 0) == 0x1x0", "0x1x0", firstSlice.get(1, 0));
        assertEquals("firstSlice.get(0, 1) == 0x0x1", "0x0x1", firstSlice.get(0, 1));
        assertEquals("firstSlice.get(1, 1) == 0x1x1", "0x1x1", firstSlice.get(1, 1));
        assertEquals("firstSlice.get(9, 9) == 0x9x9", "0x9x9", firstSlice.get(9, 9));

        ObjectMatrix2D<String> lastSlice = m.viewSlice(9);
        assertNotNull("lastSlice not null", lastSlice);
        assertEquals("lastSlice size == 100", 100, lastSlice.size());
        assertEquals("lastSlice cardinality == 100", 100, lastSlice.cardinality());
        assertEquals("lastSlice.get(0, 0) == 9x0x0", "9x0x0", lastSlice.get(0, 0));
        assertEquals("lastSlice.get(1, 0) == 9x1x0", "9x1x0", lastSlice.get(1, 0));
        assertEquals("lastSlice.get(0, 1) == 9x0x1", "9x0x1", lastSlice.get(0, 1));
        assertEquals("lastSlice.get(1, 1) == 9x1x1", "9x1x1", lastSlice.get(1, 1));
        assertEquals("lastSlice.get(9, 9) == 9x9x9", "9x9x9", lastSlice.get(9, 9));
    }

    public void testViewRow()
    {
        final ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        m.forEach(new QuaternaryProcedure<Long, Long, Long, String>()
            {
                public void run(final Long slice, final Long row, final Long column, final String s)
                {
                    m.setQuick(slice, row, column, slice + "x" + row + "x" + column);
                }
            });

        assertNotNull("m not null", m);
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 1000", 1000, m.cardinality());
        assertEquals("m get(0,0,0) == 0x0x0", "0x0x0", m.get(0, 0, 0));
        assertEquals("m get(0,1,0) == 0x1x0", "0x1x0", m.get(0, 1, 0));
        assertEquals("m get(0,0,1) == 0x0x1", "0x0x1", m.get(0, 0, 1));
        assertEquals("m get(9,0,0) == 9x0x0", "9x0x0", m.get(9, 0, 0));
        assertEquals("m get(0,9,0) == 0x9x0", "0x9x0", m.get(0, 9, 0));
        assertEquals("m get(9,9,9) == 9x9x9", "9x9x9", m.get(9, 9, 9));

        ObjectMatrix2D<String> firstRow = m.viewRow(0);
        assertNotNull("firstRow not null", firstRow);
        assertEquals("firstRow size == 100", 100, firstRow.size());
        assertEquals("firstRow cardinality == 100", 100, firstRow.cardinality());
        assertEquals("firstRow.get(0, 0) == 0x0x0", "0x0x0", firstRow.get(0, 0));
        assertEquals("firstRow.get(1, 0) == 1x0x0", "1x0x0", firstRow.get(1, 0));
        assertEquals("firstRow.get(0, 1) == 0x0x1", "0x0x1", firstRow.get(0, 1));
        assertEquals("firstRow.get(1, 1) == 1x0x1", "1x0x1", firstRow.get(1, 1));
        assertEquals("firstRow.get(9, 9) == 9x0x9", "9x0x9", firstRow.get(9, 9));

        ObjectMatrix2D<String> lastRow = m.viewRow(9);
        assertNotNull("lastRow not null", lastRow);
        assertEquals("lastRow size == 100", 100, lastRow.size());
        assertEquals("lastRow cardinality == 100", 100, lastRow.cardinality());
        assertEquals("lastRow.get(0, 0) == 0x9x0", "0x9x0", lastRow.get(0, 0));
        assertEquals("lastRow.get(1, 0) == 1x9x0", "1x9x0", lastRow.get(1, 0));
        assertEquals("lastRow.get(0, 1) == 0x9x1", "0x9x1", lastRow.get(0, 1));
        assertEquals("lastRow.get(1, 1) == 1x9x1", "1x9x1", lastRow.get(1, 1));
        assertEquals("lastRow.get(9, 9) == 9x9x9", "9x9x9", lastRow.get(9, 9));
    }

    public void testViewColumn()
    {
        final ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        m.forEach(new QuaternaryProcedure<Long, Long, Long, String>()
            {
                public void run(final Long slice, final Long row, final Long column, final String s)
                {
                    m.setQuick(slice, row, column, slice + "x" + row + "x" + column);
                }
            });

        assertNotNull("m not null", m);
        assertEquals("m size == 1000", 1000, m.size());
        assertEquals("m cardinality == 1000", 1000, m.cardinality());
        assertEquals("m get(0,0,0) == 0x0x0", "0x0x0", m.get(0, 0, 0));
        assertEquals("m get(0,1,0) == 0x1x0", "0x1x0", m.get(0, 1, 0));
        assertEquals("m get(0,0,1) == 0x0x1", "0x0x1", m.get(0, 0, 1));
        assertEquals("m get(9,0,0) == 9x0x0", "9x0x0", m.get(9, 0, 0));
        assertEquals("m get(0,9,0) == 0x9x0", "0x9x0", m.get(0, 9, 0));
        assertEquals("m get(9,9,9) == 9x9x9", "9x9x9", m.get(9, 9, 9));

        ObjectMatrix2D<String> firstColumn = m.viewColumn(0);
        assertNotNull("firstColumn not null", firstColumn);
        assertEquals("firstColumn size == 100", 100, firstColumn.size());
        assertEquals("firstColumn cardinality == 100", 100, firstColumn.cardinality());
        assertEquals("firstColumn.get(0, 0) == 0x0x0", "0x0x0", firstColumn.get(0, 0));
        assertEquals("firstColumn.get(1, 0) == 1x0x0", "1x0x0", firstColumn.get(1, 0));
        assertEquals("firstColumn.get(0, 1) == 0x1x0", "0x1x0", firstColumn.get(0, 1));
        assertEquals("firstColumn.get(1, 1) == 1x1x0", "1x1x0", firstColumn.get(1, 1));
        assertEquals("firstColumn.get(9, 9) == 9x9x0", "9x9x0", firstColumn.get(9, 9));
        
        ObjectMatrix2D<String> lastColumn = m.viewColumn(9);
        assertNotNull("lastColumn not null", lastColumn);
        assertEquals("lastColumn size == 100", 100, lastColumn.size());
        assertEquals("lastColumn cardinality == 100", 100, lastColumn.cardinality());
        assertEquals("lastColumn.get(0, 0) == 0x0x9", "0x0x9", lastColumn.get(0, 0));
        assertEquals("lastColumn.get(1, 0) == 1x0x9", "1x0x9", lastColumn.get(1, 0));
        assertEquals("lastColumn.get(0, 1) == 0x1x9", "0x1x9", lastColumn.get(0, 1));
        assertEquals("lastColumn.get(1, 1) == 1x1x9", "1x1x9", lastColumn.get(1, 1));
        assertEquals("lastColumn.get(9, 9) == 9x9x9", "9x9x9", lastColumn.get(9, 9));
    }

    public void testRowFlip()
    {
        ObjectMatrix3D<String> m = createObjectMatrix3D(10, 10, 10);
        ObjectMatrix3D<String> rowFlip = m.viewRowFlip();
        assertNotNull("rowFlip not null", rowFlip);
        assertEquals("rowFlip size == m size", m.size(), rowFlip.size());
        assertEquals("rowFlip cardinality == m cardinality", m.cardinality(), rowFlip.cardinality());

        rowFlip.assign("foo");
        assertEquals("rowFlip size == m size", m.size(), rowFlip.size());
        assertEquals("rowFlip cardinality == m cardinality", m.cardinality(), rowFlip.cardinality());

        rowFlip.clear();
        assertEquals("rowFlip size == m size", m.size(), rowFlip.size());
        assertEquals("rowFlip cardinality == m cardinality", m.cardinality(), rowFlip.cardinality());
    }
}
