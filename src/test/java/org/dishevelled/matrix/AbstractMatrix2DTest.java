/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2012 held jointly by the individual authors.

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
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.TernaryPredicate;
import org.dishevelled.functor.TernaryProcedure;

/**
 * Abstract unit test for implementations of Matrix2D.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractMatrix2DTest
    extends TestCase
{

    /**
     * Create a new instance of Matrix2D to test.
     *
     * @param rows rows
     * @param columns columns
     * @return a new instance of Matrix2D to test
     */
    protected abstract <T> Matrix2D<T> createMatrix2D(long rows, long columns);

    public void testSize()
    {
        Matrix2D<String> m0 = createMatrix2D(0, 0);
        assertNotNull("m0 not null", m0);
        assertEquals("m0 size == 0", 0, m0.size());
        assertEquals("m0 rows == 0", 0, m0.rows());
        assertEquals("m0 columns == 0", 0, m0.columns());

        Matrix2D<String> m1 = createMatrix2D(1, 1);
        assertNotNull("m1 not null", m1);
        assertEquals("m1 size == 1", 1, m1.size());
        assertEquals("m1 rows == 1", 1, m1.rows());
        assertEquals("m1 columns == 1", 1, m1.columns());

        Matrix2D<String> m2 = createMatrix2D(10, 10);
        assertNotNull("m2 not null", m2);
        assertEquals("m2 size == 100", 100, m2.size());
        assertEquals("m2 rows == 10", 10, m2.rows());
        assertEquals("m2 columns == 10", 10, m2.columns());

        Matrix2D<String> m3 = createMatrix2D(100, 100);
        assertNotNull("m3 not null", m3);
        assertEquals("m3 size == 10000", 10000, m3.size());
        assertEquals("m3 rows == 100", 100, m3.rows());
        assertEquals("m3 columns == 100", 100, m3.columns());

        Matrix2D<String> m4 = createMatrix2D(1000, 1000);
        assertNotNull("m4 not null", m4);
        assertEquals("m4 size == 1000000", 1000000, m4.size());
        assertEquals("m4 rows == 1000", 1000, m4.rows());
        assertEquals("m4 columns == 1000", 1000, m4.columns());
    }

    public void testCardinality()
    {
        Matrix2D<String> m = createMatrix2D(10, 10);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        m.set(0, 0, "foo");
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 1", 1, m.cardinality());

        m.set(0, 1, "bar");
        m.set(0, 2, "baz");
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 3", 3, m.cardinality());

        m.clear();
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        m.assign("foo");
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 100", 100, m.cardinality());

        m.clear();
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());
    }

    public void testIsEmpty()
    {
        Matrix2D<String> m = createMatrix2D(10, 10);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertTrue("m isEmpty", m.isEmpty());

        m.set(0, 0, "foo");
        assertEquals("m cardinality == 1", 1, m.cardinality());
        assertFalse("m isEmpty == false", m.isEmpty());

        m.clear();
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertTrue("m isEmpty", m.isEmpty());
    }

    public void testGet()
    {
        Matrix2D<String> m = createMatrix2D(10, 10);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());

        assertEquals("m get(0, 0) == null", null, m.get(0, 0));
        assertEquals("m get(9, 9) == null", null, m.get(9, 9));

        m.set(0, 0, "foo");
        assertEquals("m get(0, 0) == foo", "foo", m.get(0, 0));
        assertEquals("m get(9, 9) == null", null, m.get(9, 9));

        m.clear();
        assertEquals("m get(0, 0) == null", null, m.get(0, 0));
        assertEquals("m get(9, 9) == null", null, m.get(9, 9));

        m.set(0, 0, "foo");
        assertEquals("m getQuick(0, 0) == foo", "foo", m.getQuick(0, 0));
        assertEquals("m getQuick(9, 9) == null", null, m.getQuick(9, 9));

        m.clear();
        assertEquals("m getQuick(0, 0) == null", null, m.getQuick(0, 0));
        assertEquals("m getQuick(9, 9) == null", null, m.getQuick(9, 9));

        try
        {
            m.get(-1, 0);
            fail("get(-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(0, -1);
            fail("get(,-1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(10, 0);
            fail("get(10,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(0, 10);
            fail("get(,10) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testSet()
    {
        Matrix2D<String> m = createMatrix2D(10, 10);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());

        m.set(0, 0, "foo");
        assertEquals("m get(0, 0) == foo", "foo", m.get(0, 0));

        m.set(0, 0, null);
        assertEquals("m get(0, 0) == null", null, m.get(0, 0));

        m.setQuick(0, 0, "foo");
        assertEquals("m get(0, 0) == foo", "foo", m.get(0, 0));

        m.setQuick(0, 0, null);
        assertEquals("m get(0, 0) == null", null, m.get(0, 0));

        try
        {
            m.set(-1, 0, "foo");
            fail("set(-1,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(0, -1, "foo");
            fail("set(,-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(10, 0, "foo");
            fail("set(10,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(0, 10, "foo");
            fail("set(,10,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testAssign()
    {
        Matrix2D<String> m = createMatrix2D(10, 10);
        assertNotNull("m not null");

        Matrix2D<String> m0 = m.assign((String) null);
        assertNotNull("m0 not null", m0);
        assertEquals("m0.get(0, 0) == null", null, m0.get(0, 0));
        assertEquals("m0.get(0, 1) == null", null, m0.get(0, 1));
        assertEquals("m0.get(9, 9) == null", null, m0.get(9, 9));
        assertEquals("m0.cardinality() == 0", 0, m0.cardinality());
        assertEquals("m0 == m", m, m0);

        Matrix2D<String> m1 = m.assign("foo");
        assertNotNull("m1 not null", m1);
        assertEquals("m1.get(0, 0) == foo", "foo", m1.get(0, 0));
        assertEquals("m1.get(0, 1) == foo", "foo", m1.get(0, 1));
        assertEquals("m1.get(9, 9) == foo", "foo", m1.get(9, 9));
        assertEquals("m1.cardinality() == 100", 100, m1.cardinality());
        assertEquals("m1 == m", m, m1);

        Matrix2D<String> m2 = m.assign((String) null);
        assertNotNull("m2 not null", m2);
        assertEquals("m2.get(0, 0) == null", null, m2.get(0, 0));
        assertEquals("m2.get(0, 1) == null", null, m2.get(0, 1));
        assertEquals("m2.get(9, 9) == null", null, m2.get(9, 9));
        assertEquals("m2.cardinality() == 0", 0, m2.cardinality());
        assertEquals("m2 == m", m, m2);

        Matrix2D<String> m3 = m.assign(new UnaryFunction<String, String>()
            {
                public String evaluate(final String s)
                {
                    return null;
                }
            });
        assertNotNull("m3 not null", m3);
        assertEquals("m3.get(0, 0) == null", null, m3.get(0, 0));
        assertEquals("m3.get(0, 1) == null", null, m3.get(0, 1));
        assertEquals("m3.get(9, 9) == null", null, m3.get(9, 9));
        assertEquals("m3.cardinality() == 0", 0, m3.cardinality());
        assertEquals("m3 == m", m, m3);

        Matrix2D<String> m4 = m.assign(new UnaryFunction<String, String>()
            {
                public String evaluate(final String s)
                {
                    return "foo";
                }
            });
        assertNotNull("m4 not null", m4);
        assertEquals("m4.get(0, 0) == foo", "foo", m4.get(0, 0));
        assertEquals("m4.get(0, 1) == foo", "foo", m4.get(0, 1));
        assertEquals("m4.get(9, 9) == foo", "foo", m4.get(9, 9));
        assertEquals("m4.cardinality() == 100", 100, m4.cardinality());
        assertEquals("m4 == m", m, m4);

        try
        {
            Matrix2D<String> m5 = m.assign((UnaryFunction<String,String>) null);
            fail("assign((UnaryFunction<String,String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Matrix2D<String> nulls = createMatrix2D(10, 10);
        nulls.assign((String) null);

        Matrix2D<String> bars = createMatrix2D(10, 10);
        bars.assign("bar");

        Matrix2D<String> bazs = createMatrix2D(10, 10);
        bazs.assign("baz");

        Matrix2D<String> m6 = m.assign(nulls);
        assertNotNull("m6 not null", m6);
        assertEquals("m6.get(0, 0) == null", null, m6.get(0, 0));
        assertEquals("m6.get(0, 1) == null", null, m6.get(0, 1));
        assertEquals("m6.get(9, 9) == null", null, m6.get(9, 9));
        assertEquals("m6.cardinality() == 0", 0, m6.cardinality());
        assertEquals("m6 == m", m, m6);

        Matrix2D<String> m7 = m.assign(bars);
        assertNotNull("m7 not null", m7);
        assertEquals("m7.get(0, 0) == bar", "bar", m7.get(0, 0));
        assertEquals("m7.get(0, 1) == bar", "bar", m7.get(0, 1));
        assertEquals("m7.get(9, 9) == bar", "bar", m7.get(9, 9));
        assertEquals("m7.cardinality() == 100", 100, m7.cardinality());
        assertEquals("m7 == m", m, m7);

        Matrix2D<String> m8 = m.assign(bazs);
        assertNotNull("m8 not null", m8);
        assertEquals("m8.get(0, 0) == baz", "baz", m8.get(0, 0));
        assertEquals("m8.get(0, 1) == baz", "baz", m8.get(0, 1));
        assertEquals("m8.get(9, 9) == baz", "baz", m8.get(9, 9));
        assertEquals("m8.cardinality() == 100", 100, m8.cardinality());
        assertEquals("m8 == m", m, m8);

        try
        {
            Matrix2D<String> m9 = m.assign((Matrix2D<String>) null);
            fail("assign((Matrix2D<E>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix2D<String> tooSmall = createMatrix2D(9, 10);
            m.assign(tooSmall);
            fail("assign(tooSmall) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix2D<String> tooBig = createMatrix2D(10, 11);
            m.assign(tooBig);
            fail("assign(tooBig) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        m.clear();
        Matrix2D<String> m10 = m.assign(nulls,
                                              new BinaryFunction<String, String, String>()
            {
                public String evaluate(final String s0, final String s1)
                {
                    return "foo";
                }
            });
        assertNotNull("m10 not null", m10);
        assertEquals("m10.get(0, 0) == foo", "foo", m10.get(0, 0));
        assertEquals("m10.get(0, 1) == foo", "foo", m10.get(0, 1));
        assertEquals("m10.get(9, 9) == foo", "foo", m10.get(9, 9));
        assertEquals("m10.cardinality() == 100", 100, m10.cardinality());
        assertEquals("m10 == m", m, m10);

        Matrix2D<String> m11 = m.assign(bars,
                                              new BinaryFunction<String, String, String>()
            {
                public String evaluate(final String s0, final String s1)
                {
                    return null;
                }
            });
        assertNotNull("m11 not null", m11);
        assertEquals("m11.get(0, 0) == null", null, m11.get(0, 0));
        assertEquals("m11.get(0, 1) == null", null, m11.get(0, 1));
        assertEquals("m11.get(9, 9) == null", null, m11.get(9, 9));
        assertEquals("m11.cardinality() == 0", 0, m11.cardinality());
        assertEquals("m11 == m", m, m11);

        BinaryFunction<String,String,String> ignore = new BinaryFunction<String,String,String>()
        {
            public String evaluate(final String s0, final String s1)
            {
                return null;
            }
        };

        try
        {
            Matrix2D<String> m12 = m.assign((Matrix2D<String>) null, ignore);
            fail("assign((Matrix2D<E>) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix2D<String> tooSmall = createMatrix2D(9, 10);
            m.assign(tooSmall, ignore);
            fail("assign(tooSmall,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix2D<String> tooBig = createMatrix2D(10, 11);
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
        Matrix2D<String> m0 = createMatrix2D(3, 3);
        Matrix2D<String> m1 = createMatrix2D(3, 3);

        m0.assign("foo");

        BinaryFunction<String, String, String> append = new BinaryFunction<String, String, String>()
            {
                public String evaluate(final String s0, final String s1)
                {
                    StringBuffer sb = new StringBuffer(s0);
                    sb.append(" ");
                    sb.append(s1);
                    return sb.toString();
                }
            };

        UnaryFunction<String,String> passThru = new UnaryFunction<String, String>()
            {
                public String evaluate(final String s)
                {
                    return s;
                }
            };

        String result0 = m0.aggregate(append, passThru);
        assertNotNull("result0 not null", result0);
        assertEquals("result0 == foo foo foo foo foo foo foo foo foo",
                     "foo foo foo foo foo foo foo foo foo", result0);

        try
        {
            m0.aggregate(null, passThru);
            fail("aggregate(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m0.aggregate(append, null);
            fail("aggregate(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        BinaryFunction<String, String, String> combine = new BinaryFunction<String, String, String>()
            {
                public String evaluate(final String s0, final String s1)
                {
                    return (s0 + s1);
                }
            };

        m0.assign("foo");
        m1.assign("bar");
        String result1 = m0.aggregate(m1, append, combine);
        assertNotNull("result1 not null", result1);
        assertEquals("result1 == foobar foobar foobar foobar foobar foobar foobar foobar foobar",
                     "foobar foobar foobar foobar foobar foobar foobar foobar foobar", result1);

        try
        {
            m0.aggregate(null, append, combine);
            fail("aggregate(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix2D<String> tooSmall = createMatrix2D(2, 3);
            m0.aggregate(tooSmall, append, combine);
            fail("aggregate(tooSmall,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix2D<String> tooBig = createMatrix2D(3, 4);
            m0.aggregate(tooBig, append, combine);
            fail("aggregate(tooBig,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m0.aggregate(m1, null, combine);
            fail("aggregate(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m0.aggregate(m1, append, null);
            fail("aggregate(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Matrix2D<String> empty0 = createMatrix2D(0, 0);
        Matrix2D<String> empty1 = createMatrix2D(0, 0);
        assertEquals(null, empty0.aggregate(append, passThru));
        assertEquals(null, empty0.aggregate(empty1, append, combine));
    }

    public void testIterator()
    {
        Matrix2D<String> m = createMatrix2D(10, 10);
        assertNotNull("m not null");
        assertEquals("m size == 100", 100, m.size());
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

        m.set(0, 0, "foo");

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

        m.set(0, 0, null);
        m.set(0, 1, null);
        m.set(0, 2, null);

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
        Matrix2D<String> m = createMatrix2D(10, 10);
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
        m.setQuick(0L, 0L, "foo");
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
        assertEquals("count == 100", 100, count.intValue());

        Matrix1D<String> row = m.viewRow(0);
        count.setValue(0);
        row.forEachNonNull(new UnaryProcedure<String>()
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
        Matrix2D<String> m = createMatrix2D(10, 10);

        m.forEach(new UnaryProcedure<String>()
              {
                  public void run(final String s)
                  {
                      assertEquals("s == null", null, s);
                  }
              });

        m.assign("foo");
        m.forEach(new UnaryProcedure<String>()
              {
                  public void run(final String s)
                  {
                      assertEquals("s == foo", "foo", s);
                  }
              });

        m.set(0, 0, "bar");
        m.set(0, 1, null);
        m.forEach(new UnaryPredicate<String>()
                  {
                      public boolean test(final String s)
                          {
                              return ("foo".equals(s));
                          }
                  },
                  new UnaryProcedure<String>()
                  {
                      public void run(final String s)
                          {
                              assertEquals("s == foo", "foo", s);
                      }
                  });

        m.assign((String) null);
        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    assertTrue("row >= 0", row >= 0);
                    assertTrue("row < 10", row < 10);
                    assertTrue("column >= 0", column >= 0);
                    assertTrue("column < 10", column < 10);
                    assertEquals("s == null", null, s);
                }
            });

        m.assign("foo");
        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    assertTrue("row >= 0", row >= 0);
                    assertTrue("row < 10", row < 10);
                    assertTrue("column >= 0", column >= 0);
                    assertTrue("column < 10", column < 10);
                    assertEquals("s == foo", "foo", s);
                }
            });

        m.set(0, 0, "bar");
        m.set(0, 1, null);
        m.forEach(new TernaryPredicate<Long, Long, String>()
                  {
                      public boolean test(final Long row, final Long column, final String s)
                          {
                              return ( ((0L == row.longValue()) && (0L == column.longValue())) || ("foo".equals(s)) ); 
                          }
                  },
                  new TernaryProcedure<Long, Long, String>()
                  {
                      public void run(final Long row, final Long column, final String s)
                          {
                              assertTrue("s == foo or s == bar", ("bar".equals(s)) || ("foo".equals(s)));
                          }
                  });

        try
        {
            m.forEach((UnaryProcedure<String>) null);
            fail("forEach((UnaryProcedure<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.forEach((UnaryPredicate<String>) null, (UnaryProcedure<String>) null);
            fail("forEach((UnaryPredicate<String>) null, (UnaryProcedure<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.forEach(new UnaryPredicate<String>()
                  {
                      public boolean test(final String s)
                      {
                          return ("foo".equals(s));
                      }
                  }, (UnaryProcedure<String>) null);
            fail("forEach(,(UnaryProcedure<String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.forEach((TernaryProcedure<Long, Long, String>) null);
            fail("forEach((TernaryProcedure<Long, Long, String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.forEach((TernaryPredicate<Long, Long, String>) null, (TernaryProcedure<Long, Long, String>) null);
            fail("forEach((TernaryPredicate<Long, Long, String>) null, (TernaryProcedure<Long, Long, String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.forEach(new TernaryPredicate<Long, Long, String>()
                  {
                      public boolean test(final Long row, final Long column, final String s)
                      {
                          return ((0L == row.longValue()) || ("foo".equals(s)));
                      }
                  }, (TernaryProcedure<Long, Long, String>) null);
            fail("forEach(,(TernaryProcedure<Long, Long, String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testViewRow()
    {
        final Matrix2D<String> m = createMatrix2D(10, 10);

        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    m.setQuick(row, column, row + "x" + column);
                }
            });
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 100", 100, m.cardinality());
        assertEquals("m.get(0, 0) == 0x0", "0x0", m.get(0, 0));
        assertEquals("m.get(0, 1) == 0x1", "0x1", m.get(0, 1));
        assertEquals("m.get(1, 0) == 1x0", "1x0", m.get(1, 0));
        assertEquals("m.get(1, 1) == 1x1", "1x1", m.get(1, 1));
        assertEquals("m.get(9, 9) == 9x9", "9x9", m.get(9, 9));

        Matrix1D<String> firstRow = m.viewRow(0);
        assertNotNull("firstRow not null", firstRow);
        assertEquals("firstRow size == 10", 10, firstRow.size());
        assertEquals("firstRow cardinality == 10", 10, firstRow.cardinality());
        assertEquals("firstRow.get(0) == 0x0", "0x0", firstRow.get(0));
        assertEquals("firstRow.get(1) == 0x1", "0x1", firstRow.get(1));
        assertEquals("firstRow.get(8) == 0x8", "0x8", firstRow.get(8));
        assertEquals("firstRow.get(9) == 0x9", "0x9", firstRow.get(9));

        Matrix1D<String> lastRow = m.viewRow(9);
        assertNotNull("lastRow not null", lastRow);
        assertEquals("lastRow size == 10", 10, lastRow.size());
        assertEquals("lastRow cardinality == 10", 10, lastRow.cardinality());
        assertEquals("lastRow.get(0) == 9x0", "9x0", lastRow.get(0));
        assertEquals("lastRow.get(1) == 9x1", "9x1", lastRow.get(1));
        assertEquals("lastRow.get(8) == 9x8", "9x8", lastRow.get(8));
        assertEquals("lastRow.get(9) == 9x9", "9x9", lastRow.get(9));

        try
        {
            m.viewRow(-1);
            fail("viewRow(-1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewRow(10);
            fail("viewRow(10) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testViewColumn()
    {
        final Matrix2D<String> m = createMatrix2D(10, 10);

        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    m.setQuick(row, column, row + "x" + column);
                }
            });
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 100", 100, m.cardinality());
        assertEquals("m.get(0, 0) == 0x0", "0x0", m.get(0, 0));
        assertEquals("m.get(0, 1) == 0x1", "0x1", m.get(0, 1));
        assertEquals("m.get(1, 0) == 1x0", "1x0", m.get(1, 0));
        assertEquals("m.get(1, 1) == 1x1", "1x1", m.get(1, 1));
        assertEquals("m.get(9, 9) == 9x9", "9x9", m.get(9, 9));

        Matrix1D<String> firstColumn = m.viewColumn(0);
        assertNotNull("firstColumn not null", firstColumn);
        assertEquals("firstColumn size == 10", 10, firstColumn.size());
        assertEquals("firstColumn cardinality == 10", 10, firstColumn.cardinality());
        assertEquals("firstColumn.get(0) == 0x0", "0x0", firstColumn.get(0));
        assertEquals("firstColumn.get(1) == 1x0", "1x0", firstColumn.get(1));
        assertEquals("firstColumn.get(8) == 8x0", "8x0", firstColumn.get(8));
        assertEquals("firstColumn.get(9) == 9x0", "9x0", firstColumn.get(9));

        Matrix1D<String> lastColumn = m.viewColumn(9);
        assertNotNull("lastColumn not null", lastColumn);
        assertEquals("lastColumn size == 10", 10, lastColumn.size());
        assertEquals("lastColumn cardinality == 10", 10, lastColumn.cardinality());
        assertEquals("lastColumn.get(0) == 0x9", "0x9", lastColumn.get(0));
        assertEquals("lastColumn.get(1) == 1x9", "1x9", lastColumn.get(1));
        assertEquals("lastColumn.get(8) == 8x9", "8x9", lastColumn.get(8));
        assertEquals("lastColumn.get(9) == 9x9", "9x9", lastColumn.get(9));

        try
        {
            m.viewColumn(-1);
            fail("viewColumn(-1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewColumn(10);
            fail("viewColumn(10) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testViewDice()
    {
        // TODO
    }

    public void testViewRowFlip()
    {
        final Matrix2D<String> m = createMatrix2D(10, 10);

        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    m.setQuick(row, column, row + "x" + column);
                }
            });
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 100", 100, m.cardinality());
        assertEquals("m.get(0, 0) == 0x0", "0x0", m.get(0, 0));
        assertEquals("m.get(0, 1) == 0x1", "0x1", m.get(0, 1));
        assertEquals("m.get(1, 0) == 1x0", "1x0", m.get(1, 0));
        assertEquals("m.get(1, 1) == 1x1", "1x1", m.get(1, 1));
        assertEquals("m.get(9, 9) == 9x9", "9x9", m.get(9, 9));

        Matrix2D<String> flip = m.viewRowFlip();
        assertNotNull("flip not null", flip);
        assertEquals("flip size == 100", 100, flip.size());
        assertEquals("flip cardinality == 100", 100, flip.cardinality());
        assertEquals("flip.get(0, 0) == 9x0", "9x0", flip.get(0, 0));
        assertEquals("flip.get(0, 1) == 9x1", "9x1", flip.get(0, 1));
        assertEquals("flip.get(1, 0) == 8x0", "8x0", flip.get(1, 0));
        assertEquals("flip.get(1, 1) == 8x1", "8x1", flip.get(1, 1));
        assertEquals("flip.get(8, 9) == 1x9", "1x9", flip.get(8, 9));
        assertEquals("flip.get(9, 9) == 0x9", "0x9", flip.get(9, 9));

        Matrix2D<String> flipTwice = flip.viewRowFlip();
        assertNotNull("flipTwice not null", flipTwice);
        assertEquals("flipTwice size == 100", 100, flipTwice.size());
        assertEquals("flipTwice cardinality == 100", 100, flipTwice.cardinality());
        assertEquals("flipTwice.get(0, 0) == 0x0", "0x0", flipTwice.get(0, 0));
        assertEquals("flipTwice.get(0, 1) == 0x1", "0x1", flipTwice.get(0, 1));
        assertEquals("flipTwice.get(1, 0) == 1x0", "1x0", flipTwice.get(1, 0));
        assertEquals("flipTwice.get(1, 1) == 1x1", "1x1", flipTwice.get(1, 1));
        assertEquals("flipTwice.get(8, 9) == 8x9", "8x9", flipTwice.get(8, 9));
        assertEquals("flipTwice.get(9, 9) == 9x9", "9x9", flipTwice.get(9, 9));

        Matrix2D<String> flipThrice = flipTwice.viewRowFlip();
        assertNotNull("flipThrice not null", flipThrice);
        assertEquals("flipThrice size == 100", 100, flipThrice.size());
        assertEquals("flipThrice cardinality == 100", 100, flipThrice.cardinality());
        assertEquals("flipThrice.get(0, 0) == 9x0", "9x0", flipThrice.get(0, 0));
        assertEquals("flipThrice.get(0, 1) == 9x1", "9x1", flipThrice.get(0, 1));
        assertEquals("flipThrice.get(1, 0) == 8x0", "8x0", flipThrice.get(1, 0));
        assertEquals("flipThrice.get(1, 1) == 8x1", "8x1", flipThrice.get(1, 1));
        assertEquals("flipThrice.get(8, 9) == 1x9", "1x9", flipThrice.get(8, 9));
        assertEquals("flipThrice.get(9, 9) == 0x9", "0x9", flipThrice.get(9, 9));

        Matrix2D<String> flipBoth = flip.viewColumnFlip();
        assertEquals("flipBoth size == 100", 100, flipBoth.size());
        assertEquals("flipBoth cardinality == 100", 100, flipBoth.cardinality());
        assertEquals("flipBoth.get(0, 0) == 9x9", "9x9", flipBoth.get(0, 0));
        assertEquals("flipBoth.get(0, 1) == 9x8", "9x8", flipBoth.get(0, 1));
        assertEquals("flipBoth.get(1, 0) == 8x9", "8x9", flipBoth.get(1, 0));
        assertEquals("flipBoth.get(1, 1) == 8x8", "8x8", flipBoth.get(1, 1));
        assertEquals("flipBoth.get(9, 8) == 0x1", "0x1", flipBoth.get(9, 8));
        assertEquals("flipBoth.get(9, 9) == 0x0", "0x0", flipBoth.get(9, 9));
    }

    public void testViewColumnFlip()
    {
        final Matrix2D<String> m = createMatrix2D(10, 10);

        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    m.setQuick(row, column, row + "x" + column);
                }
            });
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 100", 100, m.cardinality());
        assertEquals("m.get(0, 0) == 0x0", "0x0", m.get(0, 0));
        assertEquals("m.get(0, 1) == 0x1", "0x1", m.get(0, 1));
        assertEquals("m.get(1, 0) == 1x0", "1x0", m.get(1, 0));
        assertEquals("m.get(1, 1) == 1x1", "1x1", m.get(1, 1));
        assertEquals("m.get(9, 9) == 9x9", "9x9", m.get(9, 9));

        Matrix2D<String> flip = m.viewColumnFlip();
        assertNotNull("flip not null", flip);
        assertEquals("flip size == 100", 100, flip.size());
        assertEquals("flip cardinality == 100", 100, flip.cardinality());
        assertEquals("flip.get(0, 0) == 0x9", "0x9", flip.get(0, 0));
        assertEquals("flip.get(0, 1) == 0x8", "0x8", flip.get(0, 1));
        assertEquals("flip.get(1, 0) == 1x9", "1x9", flip.get(1, 0));
        assertEquals("flip.get(1, 1) == 1x8", "1x8", flip.get(1, 1));
        assertEquals("flip.get(9, 8) == 9x1", "9x1", flip.get(9, 8));
        assertEquals("flip.get(9, 9) == 9x0", "9x0", flip.get(9, 9));

        Matrix2D<String> flipTwice = flip.viewColumnFlip();
        assertNotNull("flipTwice not null", flipTwice);
        assertEquals("flipTwice size == 100", 100, flipTwice.size());
        assertEquals("flipTwice cardinality == 100", 100, flipTwice.cardinality());
        assertEquals("flipTwice.get(0, 0) == 0x0", "0x0", flipTwice.get(0, 0));
        assertEquals("flipTwice.get(0, 1) == 0x1", "0x1", flipTwice.get(0, 1));
        assertEquals("flipTwice.get(1, 0) == 1x0", "1x0", flipTwice.get(1, 0));
        assertEquals("flipTwice.get(1, 1) == 1x1", "1x1", flipTwice.get(1, 1));
        assertEquals("flipTwice.get(9, 8) == 9x8", "9x8", flipTwice.get(9, 8));
        assertEquals("flipTwice.get(9, 9) == 9x9", "9x9", flipTwice.get(9, 9));

        Matrix2D<String> flipThrice = flipTwice.viewColumnFlip();
        assertNotNull("flipThrice not null", flipThrice);
        assertEquals("flipThrice size == 100", 100, flipThrice.size());
        assertEquals("flipThrice cardinality == 100", 100, flipThrice.cardinality());
        assertEquals("flipThrice.get(0, 0) == 0x9", "0x9", flipThrice.get(0, 0));
        assertEquals("flipThrice.get(0, 1) == 0x8", "0x8", flipThrice.get(0, 1));
        assertEquals("flipThrice.get(1, 0) == 1x9", "1x9", flipThrice.get(1, 0));
        assertEquals("flipThrice.get(1, 1) == 1x8", "1x8", flipThrice.get(1, 1));
        assertEquals("flipThrice.get(9, 8) == 9x1", "9x1", flipThrice.get(9, 8));
        assertEquals("flipThrice.get(9, 9) == 9x0", "9x0", flipThrice.get(9, 9));

        Matrix2D<String> flipBoth = flip.viewRowFlip();
        assertEquals("flipBoth size == 100", 100, flipBoth.size());
        assertEquals("flipBoth cardinality == 100", 100, flipBoth.cardinality());
        assertEquals("flipBoth.get(0, 0) == 9x9", "9x9", flipBoth.get(0, 0));
        assertEquals("flipBoth.get(0, 1) == 9x8", "9x8", flipBoth.get(0, 1));
        assertEquals("flipBoth.get(1, 0) == 8x9", "8x9", flipBoth.get(1, 0));
        assertEquals("flipBoth.get(1, 1) == 8x8", "8x8", flipBoth.get(1, 1));
        assertEquals("flipBoth.get(9, 8) == 0x1", "0x1", flipBoth.get(9, 8));
        assertEquals("flipBoth.get(9, 9) == 0x0", "0x0", flipBoth.get(9, 9));
    }

    public void testViewPart()
    {
        final Matrix2D<String> m = createMatrix2D(10, 10);

        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    m.setQuick(row, column, row + "x" + column);
                }
            });
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 100", 100, m.cardinality());
        assertEquals("m.get(0, 0) == 0x0", "0x0", m.get(0, 0));
        assertEquals("m.get(0, 1) == 0x1", "0x1", m.get(0, 1));
        assertEquals("m.get(1, 0) == 1x0", "1x0", m.get(1, 0));
        assertEquals("m.get(1, 1) == 1x1", "1x1", m.get(1, 1));
        assertEquals("m.get(9, 9) == 9x9", "9x9", m.get(9, 9));

        Matrix2D<String> firstFiveRows = m.viewPart(0, 0, 5, 10);
        assertNotNull("firstFiveRows not null", firstFiveRows);
        assertEquals("firstFiveRows size == 50", 50, firstFiveRows.size());
        assertEquals("firstFiveRows rows == 5", 5, firstFiveRows.rows());
        assertEquals("firstFiveRows columns == 10", 10, firstFiveRows.columns());
        assertEquals("firstFiveRows cardinality == 50", 50, firstFiveRows.size());
        assertEquals("firstFiveRows.get(0, 0) == 0x0", "0x0", firstFiveRows.get(0, 0));
        assertEquals("firstFiveRows.get(0, 1) == 0x1", "0x1", firstFiveRows.get(0, 1));
        assertEquals("firstFiveRows.get(1, 0) == 1x0", "1x0", firstFiveRows.get(1, 0));
        assertEquals("firstFiveRows.get(1, 1) == 1x1", "1x1", firstFiveRows.get(1, 1));
        assertEquals("firstFiveRows.get(4, 9) == 4x9", "4x9", firstFiveRows.get(4, 9));

        Matrix2D<String> firstThreeRows = firstFiveRows.viewPart(0, 0, 3, 10);
        assertNotNull("firstThreeRows not null", firstThreeRows);
        assertEquals("firstThreeRows size == 30", 30, firstThreeRows.size());
        assertEquals("firstThreeRows rows == 3", 3, firstThreeRows.rows());
        assertEquals("firstThreeRows columns == 10", 10, firstThreeRows.columns());
        assertEquals("firstThreeRows cardinality == 30", 30, firstThreeRows.size());
        assertEquals("firstThreeRows.get(0, 0) == 0x0", "0x0", firstThreeRows.get(0, 0));
        assertEquals("firstThreeRows.get(0, 1) == 0x1", "0x1", firstThreeRows.get(0, 1));
        assertEquals("firstThreeRows.get(1, 0) == 1x0", "1x0", firstThreeRows.get(1, 0));
        assertEquals("firstThreeRows.get(1, 1) == 1x1", "1x1", firstThreeRows.get(1, 1));
        assertEquals("firstThreeRows.get(2, 9) == 2x9", "2x9", firstThreeRows.get(2, 9));

        Matrix2D<String> firstFiveColumns = m.viewPart(0, 0, 10, 5);
        assertNotNull("firstFiveColumns not null", firstFiveColumns);
        assertEquals("firstFiveColumns size == 50", 50, firstFiveColumns.size());
        assertEquals("firstFiveColumns rows == 10", 10, firstFiveColumns.rows());
        assertEquals("firstFiveColumns columns == 5", 5, firstFiveColumns.columns());
        assertEquals("firstFiveColumns cardinality == 50", 50, firstFiveColumns.size());
        assertEquals("firstFiveColumns.get(0, 0) == 0x0", "0x0", firstFiveColumns.get(0, 0));
        assertEquals("firstFiveColumns.get(0, 1) == 0x1", "0x1", firstFiveColumns.get(0, 1));
        assertEquals("firstFiveColumns.get(1, 0) == 1x0", "1x0", firstFiveColumns.get(1, 0));
        assertEquals("firstFiveColumns.get(1, 1) == 1x1", "1x1", firstFiveColumns.get(1, 1));
        assertEquals("firstFiveColumns.get(9, 4) == 9x4", "9x4", firstFiveColumns.get(9, 4));

        Matrix2D<String> firstThreeColumns = firstFiveColumns.viewPart(0, 0, 10, 3);
        assertNotNull("firstThreeColumns not null", firstThreeColumns);
        assertEquals("firstThreeColumns size == 30", 30, firstThreeColumns.size());
        assertEquals("firstThreeColumns rows == 10", 10, firstThreeColumns.rows());
        assertEquals("firstThreeColumns columns == 3", 3, firstThreeColumns.columns());
        assertEquals("firstThreeColumns cardinality == 30", 30, firstThreeColumns.size());
        assertEquals("firstThreeColumns.get(0, 0) == 0x0", "0x0", firstThreeColumns.get(0, 0));
        assertEquals("firstThreeColumns.get(0, 1) == 0x1", "0x1", firstThreeColumns.get(0, 1));
        assertEquals("firstThreeColumns.get(1, 0) == 1x0", "1x0", firstThreeColumns.get(1, 0));
        assertEquals("firstThreeColumns.get(1, 1) == 1x1", "1x1", firstThreeColumns.get(1, 1));
        assertEquals("firstThreeColumns.get(9, 2) == 9x2", "9x2", firstThreeColumns.get(9, 2));

        Matrix2D<String> middleFive = m.viewPart(2, 2, 5, 5);
        assertNotNull("middleFive not null", middleFive);
        assertEquals("middleFive size == 25", 25, middleFive.size());
        assertEquals("middleFive rows == 5", 5, middleFive.rows());
        assertEquals("middleFive columns == 5", 5, middleFive.columns());
        assertEquals("middleFive cardinality == 25", 25, middleFive.cardinality());
        assertEquals("middleFive.get(0, 0) == 2x2", "2x2", middleFive.get(0, 0));
        assertEquals("middleFive.get(1, 0) == 3x2", "3x2", middleFive.get(1, 0));
        assertEquals("middleFive.get(0, 1) == 2x3", "2x3", middleFive.get(0, 1));
        assertEquals("middleFive.get(1, 1) == 3x3", "3x3", middleFive.get(1, 1));
        assertEquals("middleFive.get(4, 4) == 6x6", "6x6", middleFive.get(4, 4));

        try
        {
            m.viewPart(-1, 0, 5, 5);
            fail("viewPart(-1,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewPart(m.rows(), 0, 5, 5);
            fail("viewPart(rows,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewPart(m.rows() + 1, 0, 5, 5);
            fail("viewPart(rows + 1,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewPart(0, -1, 5, 5);
            fail("viewPart(,-1,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewPart(0, m.columns(), 5, 5);
            fail("viewPart(,columns,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewPart(0, m.columns() + 1, 5, 5);
            fail("viewPart(,columns + 1,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewPart(0, 0, m.rows() + 1, 5);
            fail("viewPart(,,rows + 1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            m.viewPart(0, 0, 5, m.columns() + 1);
            fail("viewPart(,,,columns + 1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testViewSelection()
    {
        // TODO
    }

    public void testViewStrides()
    {
        final Matrix2D<String> m = createMatrix2D(100, 100);

        m.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String s)
                {
                    m.setQuick(row, column, row + "x" + column);
                }
            });
        assertEquals("m rows == 100", 100, m.rows());
        assertEquals("m columns == 100", 100, m.columns());
        assertEquals("m size == 10000", 10000, m.size());
        assertEquals("m cardinality == 10000", 10000, m.cardinality());
        assertEquals("m.get(0, 0) == 0x0", "0x0", m.get(0, 0));
        assertEquals("m.get(0, 1) == 0x1", "0x1", m.get(0, 1));
        assertEquals("m.get(1, 0) == 1x0", "1x0", m.get(1, 0));
        assertEquals("m.get(1, 1) == 1x1", "1x1", m.get(1, 1));
        assertEquals("m.get(99, 99) == 99x99", "99x99", m.get(99, 99));

        Matrix2D<String> every2ndRow = m.viewStrides(2, 1);
        assertNotNull("every2ndRow not null", every2ndRow);
        assertEquals("every2ndRow rows == 50", 50, every2ndRow.rows());
        assertEquals("every2ndRow columns == 100", 100, every2ndRow.columns());
        assertEquals("every2ndRow size == 5000", 5000, every2ndRow.size());
        assertEquals("every2ndRow cardinality == 5000", 5000, every2ndRow.cardinality());
        assertEquals("every2ndRow.get(0, 0) == 0x0", "0x0", every2ndRow.get(0, 0));
        assertEquals("every2ndRow.get(0, 1) == 0x1", "0x1", every2ndRow.get(0, 1));
        assertEquals("every2ndRow.get(1, 0) == 2x0", "2x0", every2ndRow.get(1, 0));
        assertEquals("every2ndRow.get(1, 1) == 2x1", "2x1", every2ndRow.get(1, 1));
        assertEquals("every2ndRow.get(2, 0) == 4x0", "4x0", every2ndRow.get(2, 0));
        assertEquals("every2ndRow.get(2, 1) == 4x1", "4x1", every2ndRow.get(2, 1));
        assertEquals("every2ndRow.get(0, 2) == 0x2", "0x2", every2ndRow.get(0, 2));
        assertEquals("every2ndRow.get(1, 2) == 2x2", "2x2", every2ndRow.get(1, 2));
        assertEquals("every2ndRow.get(2, 2) == 4x2", "4x2", every2ndRow.get(2, 2));
        assertEquals("every2ndRow.get(49, 99) == 98x99", "98x99", every2ndRow.get(49, 99));

        Matrix2D<String> every10thRow = m.viewStrides(10, 1);
        assertNotNull("every10thRow not null", every10thRow);
        assertEquals("every10thRow rows == 10", 10, every10thRow.rows());
        assertEquals("every10thRow columns == 100", 100, every10thRow.columns());
        assertEquals("every10thRow size == 1000", 1000, every10thRow.size());
        assertEquals("every10thRow cardinality == 1000", 1000, every10thRow.cardinality());
        assertEquals("every10thRow.get(0, 0) == 0x0", "0x0", every10thRow.get(0, 0));
        assertEquals("every10thRow.get(0, 1) == 0x1", "0x1", every10thRow.get(0, 1));
        assertEquals("every10thRow.get(1, 0) == 10x0", "10x0", every10thRow.get(1, 0));
        assertEquals("every10thRow.get(1, 1) == 10x1", "10x1", every10thRow.get(1, 1));
        assertEquals("every10thRow.get(2, 0) == 20x0", "20x0", every10thRow.get(2, 0));
        assertEquals("every10thRow.get(2, 1) == 20x1", "20x1", every10thRow.get(2, 1));
        assertEquals("every10thRow.get(0, 2) == 0x2", "0x2", every10thRow.get(0, 2));
        assertEquals("every10thRow.get(1, 2) == 10x2", "10x2", every10thRow.get(1, 2));
        assertEquals("every10thRow.get(2, 2) == 20x2", "20x2", every10thRow.get(2, 2));
        assertEquals("every10thRow.get(9, 99) == 90x99", "90x99", every10thRow.get(9, 99));

        Matrix2D<String> every2ndOfEvery10thRow = every10thRow.viewStrides(2, 1);
        assertNotNull("every2ndOfEvery10thRow not null", every2ndOfEvery10thRow);
        assertEquals("every2ndOfEvery10thRow rows == 5", 5, every2ndOfEvery10thRow.rows());
        assertEquals("every2ndOfEvery10thRow columns == 100", 100, every2ndOfEvery10thRow.columns());
        assertEquals("every2ndOfEvery10thRow size == 500", 500, every2ndOfEvery10thRow.size());
        assertEquals("every2ndOfEvery10thRow cardinality == 500", 500, every2ndOfEvery10thRow.cardinality());
        assertEquals("every2ndOfEvery10thRow.get(0, 0) == 0x0", "0x0", every2ndOfEvery10thRow.get(0, 0));
        assertEquals("every2ndOfEvery10thRow.get(0, 1) == 0x1", "0x1", every2ndOfEvery10thRow.get(0, 1));
        assertEquals("every2ndOfEvery10thRow.get(1, 0) == 20x0", "20x0", every2ndOfEvery10thRow.get(1, 0));
        assertEquals("every2ndOfEvery10thRow.get(1, 1) == 20x1", "20x1", every2ndOfEvery10thRow.get(1, 1));
        assertEquals("every2ndOfEvery10thRow.get(2, 0) == 40x0", "40x0", every2ndOfEvery10thRow.get(2, 0));
        assertEquals("every2ndOfEvery10thRow.get(2, 1) == 40x1", "40x1", every2ndOfEvery10thRow.get(2, 1));
        assertEquals("every2ndOfEvery10thRow.get(0, 2) == 0x2", "0x2", every2ndOfEvery10thRow.get(0, 2));
        assertEquals("every2ndOfEvery10thRow.get(1, 2) == 20x2", "20x2", every2ndOfEvery10thRow.get(1, 2));
        assertEquals("every2ndOfEvery10thRow.get(2, 2) == 40x2", "40x2", every2ndOfEvery10thRow.get(2, 2));
        assertEquals("every2ndOfEvery10thRow.get(4, 99) == 80x99", "80x99", every2ndOfEvery10thRow.get(4, 99));

        Matrix2D<String> every2ndColumn = m.viewStrides(1, 2);
        assertNotNull("every2ndColumn not null", every2ndColumn);
        assertEquals("every2ndColumn rows == 100", 100, every2ndColumn.rows());
        assertEquals("every2ndColumn columns == 50", 50, every2ndColumn.columns());
        assertEquals("every2ndColumn size == 5000", 5000, every2ndColumn.size());
        assertEquals("every2ndColumn cardinality == 5000", 5000, every2ndColumn.cardinality());
        assertEquals("every2ndColumn.get(0, 0) == 0x0", "0x0", every2ndColumn.get(0, 0));
        assertEquals("every2ndColumn.get(0, 1) == 0x2", "0x2", every2ndColumn.get(0, 1));
        assertEquals("every2ndColumn.get(1, 0) == 1x0", "1x0", every2ndColumn.get(1, 0));
        assertEquals("every2ndColumn.get(1, 1) == 1x2", "1x2", every2ndColumn.get(1, 1));
        assertEquals("every2ndColumn.get(2, 0) == 2x0", "2x0", every2ndColumn.get(2, 0));
        assertEquals("every2ndColumn.get(2, 1) == 2x2", "2x2", every2ndColumn.get(2, 1));
        assertEquals("every2ndColumn.get(0, 2) == 0x4", "0x4", every2ndColumn.get(0, 2));
        assertEquals("every2ndColumn.get(1, 2) == 1x4", "1x4", every2ndColumn.get(1, 2));
        assertEquals("every2ndColumn.get(2, 2) == 2x4", "2x4", every2ndColumn.get(2, 2));
        assertEquals("every2ndColumn.get(99, 49) == 99x98", "99x98", every2ndColumn.get(99, 49));

        Matrix2D<String> every10thColumn = m.viewStrides(1, 10);
        assertNotNull("every10thColumn not null", every10thColumn);
        assertEquals("every10thColumn rows == 100", 100, every10thColumn.rows());
        assertEquals("every10thColumn columns == 10", 10, every10thColumn.columns());
        assertEquals("every10thColumn size == 1000", 1000, every10thColumn.size());
        assertEquals("every10thColumn cardinality == 1000", 1000, every10thColumn.cardinality());
        assertEquals("every10thColumn.get(0, 0) == 0x0", "0x0", every10thColumn.get(0, 0));
        assertEquals("every10thColumn.get(0, 1) == 0x10", "0x10", every10thColumn.get(0, 1));
        assertEquals("every10thColumn.get(1, 0) == 1x0", "1x0", every10thColumn.get(1, 0));
        assertEquals("every10thColumn.get(1, 1) == 1x10", "1x10", every10thColumn.get(1, 1));
        assertEquals("every10thColumn.get(2, 0) == 2x0", "2x0", every10thColumn.get(2, 0));
        assertEquals("every10thColumn.get(2, 1) == 2x10", "2x10", every10thColumn.get(2, 1));
        assertEquals("every10thColumn.get(0, 2) == 0x20", "0x20", every10thColumn.get(0, 2));
        assertEquals("every10thColumn.get(1, 2) == 1x20", "1x20", every10thColumn.get(1, 2));
        assertEquals("every10thColumn.get(2, 2) == 2x20", "2x20", every10thColumn.get(2, 2));
        assertEquals("every10thColumn.get(99, 9) == 99x90", "99x90", every10thColumn.get(99, 9));

        Matrix2D<String> every2ndOfEvery10thColumn = every10thColumn.viewStrides(1, 2);
        assertNotNull("every2ndOfEvery10thColumn not null", every2ndOfEvery10thColumn);
        assertEquals("every2ndOfEvery10thColumn rows == 100", 100, every2ndOfEvery10thColumn.rows());
        assertEquals("every2ndOfEvery10thColumn columns == 5", 5, every2ndOfEvery10thColumn.columns());
        assertEquals("every2ndOfEvery10thColumn size == 500", 500, every2ndOfEvery10thColumn.size());
        assertEquals("every2ndOfEvery10thColumn cardinality == 500", 500, every2ndOfEvery10thColumn.cardinality());
        assertEquals("every2ndOfEvery10thColumn.get(0, 0) == 0x0", "0x0", every2ndOfEvery10thColumn.get(0, 0));
        assertEquals("every2ndOfEvery10thColumn.get(0, 1) == 0x20", "0x20", every2ndOfEvery10thColumn.get(0, 1));
        assertEquals("every2ndOfEvery10thColumn.get(1, 0) == 1x0", "1x0", every2ndOfEvery10thColumn.get(1, 0));
        assertEquals("every2ndOfEvery10thColumn.get(1, 1) == 1x20", "1x20", every2ndOfEvery10thColumn.get(1, 1));
        assertEquals("every2ndOfEvery10thColumn.get(2, 0) == 2x0", "2x0", every2ndOfEvery10thColumn.get(2, 0));
        assertEquals("every2ndOfEvery10thColumn.get(2, 1) == 2x20", "2x20", every2ndOfEvery10thColumn.get(2, 1));
        assertEquals("every2ndOfEvery10thColumn.get(0, 2) == 0x40", "0x40", every2ndOfEvery10thColumn.get(0, 2));
        assertEquals("every2ndOfEvery10thColumn.get(1, 2) == 1x40", "1x40", every2ndOfEvery10thColumn.get(1, 2));
        assertEquals("every2ndOfEvery10thColumn.get(2, 2) == 2x40", "2x40", every2ndOfEvery10thColumn.get(2, 2));
        assertEquals("every2ndOfEvery10thColumn.get(99, 4) == 99x80", "99x80", every2ndOfEvery10thColumn.get(99, 4));
    }

    public void testToString()
    {
        Matrix2D<String> m = createMatrix2D(10, 10);
        m.assign("foo");
        assertNotNull(m.toString());
    }
}
