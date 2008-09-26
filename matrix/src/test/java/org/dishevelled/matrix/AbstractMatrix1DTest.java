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
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.BinaryPredicate;
import org.dishevelled.functor.BinaryProcedure;

/**
 * Abstract unit test for implementations of Matrix1D.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractMatrix1DTest
    extends TestCase
{

    /**
     * Create a new instance of Matrix1D to test.
     *
     * @param size size
     * @return a new instance of Matrix1D to test
     */
    protected abstract <T> Matrix1D<T> createMatrix1D(long size);

    public void testSize()
    {
        Matrix1D<String> m0 = createMatrix1D(0);
        assertNotNull("m0 not null", m0);
        assertEquals("m0 size == 0", 0, m0.size());

        Matrix1D<String> m1 = createMatrix1D(100);
        assertNotNull("m1 not null", m1);
        assertEquals("m1 size == 100", 100, m1.size());

        Matrix1D<String> m2 = createMatrix1D(1000);
        assertNotNull("m2 not null", m2);
        assertEquals("m2 size == 1000", 1000, m2.size());
    }

    public void testCardinality()
    {
        Matrix1D<String> m = createMatrix1D(100);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        m.set(0, "foo");
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 1", 1, m.cardinality());

        m.set(1, "bar");
        m.set(2, "baz");
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
        Matrix1D<String> m = createMatrix1D(100);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertTrue("m isEmpty", m.isEmpty());

        m.set(0, "foo");
        assertEquals("m cardinality == 1", 1, m.cardinality());
        assertFalse("m isEmpty == false", m.isEmpty());

        m.clear();
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertTrue("m isEmpty", m.isEmpty());
    }

    public void testGet()
    {
        Matrix1D<String> m = createMatrix1D(100);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());

        assertEquals("get(0) == null", null, m.get(0));
        assertEquals("get(99) == null", null, m.get(99));

        m.set(0, "foo");
        assertEquals("get(0) == foo", "foo", m.get(0));
        assertEquals("get(99) == null", null, m.get(99));

        m.clear();
        assertEquals("get(0) == null", null, m.get(0));
        assertEquals("get(99) == null", null, m.get(99));

        m.set(0, "foo");
        assertEquals("getQuick(0) == foo", "foo", m.getQuick(0));
        assertEquals("getQuick(99) == null", null, m.getQuick(99));

        m.clear();
        assertEquals("getQuick(0) == null", null, m.getQuick(0));
        assertEquals("getQuick(99) == null", null, m.getQuick(99));

        try
        {
            m.get(-1);
            fail("get(-1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.get(100);
            fail("get(100) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testSet()
    {
        Matrix1D<String> m = createMatrix1D(100);
        assertNotNull("m not null", m);
        assertEquals("m size == 100", 100, m.size());

        m.set(0, "foo");
        assertEquals("get(0) == foo", "foo", m.get(0));

        m.set(0, null);
        assertEquals("get(0) == null", null, m.get(0));

        m.setQuick(0, "foo");
        assertEquals("get(0) == foo", "foo", m.get(0));

        m.setQuick(0, null);
        assertEquals("get(0) == null", null, m.get(0));

        try
        {
            m.set(-1, "foo");
            fail("set(-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            m.set(100, "foo");
            fail("set(100,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testAssign()
    {
        Matrix1D<String> m = createMatrix1D(100);
        assertNotNull("m not null", m);

        Matrix1D<String> m0 = m.assign((String) null);
        assertNotNull("m0 not null", m0);
        assertEquals("m0.get(0) == null", null, m0.get(0));
        assertEquals("m0.get(1) == null", null, m0.get(1));
        assertEquals("m0.get(99) == null", null, m0.get(99));
        assertEquals("m0.cardinality() == 0", 0, m0.cardinality());
        assertEquals("m0 == m", m, m0);

        Matrix1D<String> m1 = m.assign("foo");
        assertNotNull("m1 not null", m1);
        assertEquals("m1.get(0) == foo", "foo", m1.get(0));
        assertEquals("m1.get(1) == foo", "foo", m1.get(1));
        assertEquals("m1.get(99) == foo", "foo", m1.get(99));
        assertEquals("m1.cardinality() == 100", 100, m1.cardinality());
        assertEquals("m1 == m", m, m1);

        Matrix1D<String> m2 = m.assign((String) null);
        assertNotNull("m2 not null", m2);
        assertEquals("m2.get(0) == null", null, m2.get(0));
        assertEquals("m2.get(1) == null", null, m2.get(1));
        assertEquals("m2.get(99) == null", null, m2.get(99));
        assertEquals("m2.cardinality() == 0", 0, m2.cardinality());
        assertEquals("m2 == m", m, m2);

        Matrix1D<String> m3 = m.assign(new UnaryFunction<String,String>()
            {
                public String evaluate(final String s)
                {
                    return null;
                }
            });
        assertNotNull("m3 not null", m3);
        assertEquals("m3.get(0) == null", null, m3.get(0));
        assertEquals("m3.get(1) == null", null, m3.get(1));
        assertEquals("m3.get(99) == null", null, m3.get(99));
        assertEquals("m3.cardinality() == 0", 0, m3.cardinality());
        assertEquals("m3 == m", m, m3);

        Matrix1D<String> m4 = m.assign(new UnaryFunction<String,String>()
            {
                public String evaluate(final String s)
                {
                    return "foo";
                }
            });
        assertNotNull("m4 not null", m4);
        assertEquals("m4.get(0) == foo", "foo", m4.get(0));
        assertEquals("m4.get(1) == foo", "foo", m4.get(1));
        assertEquals("m4.get(99) == foo", "foo", m4.get(99));
        assertEquals("m4.cardinality() == 100", 100, m4.cardinality());
        assertEquals("m4 == m", m, m4);

        try
        {
            Matrix1D<String> m5 = m.assign((UnaryFunction<String,String>) null);
            fail("assign((UnaryFunction<E,E>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Matrix1D<String> nulls = createMatrix1D(100);
        nulls.assign((String) null);

        Matrix1D<String> bars = createMatrix1D(100);
        bars.assign("bar");

        Matrix1D<String> bazs = createMatrix1D(100);
        bazs.assign("baz");

        Matrix1D<String> m6 = m.assign(nulls);
        assertNotNull("m6 not null", m6);
        assertEquals("m6.get(0) == null", null, m6.get(0));
        assertEquals("m6.get(1) == null", null, m6.get(1));
        assertEquals("m6.get(99) == null", null, m6.get(99));
        assertEquals("m6.cardinality() == 0", 0, m6.cardinality());
        assertEquals("m6 == m", m, m6);

        Matrix1D<String> m7 = m.assign(bars);
        assertNotNull("m7 not null", m7);
        assertEquals("m7.get(0) == bar", "bar", m7.get(0));
        assertEquals("m7.get(1) == bar", "bar", m7.get(1));
        assertEquals("m7.get(99) == bar", "bar", m7.get(99));
        assertEquals("m7.cardinality() == 100", 100, m7.cardinality());
        assertEquals("m7 == m", m, m7);

        Matrix1D<String> m8 = m.assign(bazs);
        assertNotNull("m8 not null", m8);
        assertEquals("m8.get(0) == baz", "baz", m8.get(0));
        assertEquals("m8.get(1) == baz", "baz", m8.get(1));
        assertEquals("m8.get(99) == baz", "baz", m8.get(99));
        assertEquals("m8.cardinality() == 100", 100, m8.cardinality());
        assertEquals("m8 == m", m, m8);

        try
        {
            Matrix1D<String> m9 = m.assign((Matrix1D<String>) null);
            fail("assign((Matrix1D<E>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix1D<String> tooSmall = createMatrix1D(99);
            m.assign(tooSmall);
            fail("assign(tooSmall) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix1D<String> tooBig = createMatrix1D(101);
            m.assign(tooBig);
            fail("assign(tooBig) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        m.clear();
        Matrix1D<String> m10 = m.assign(nulls,
                                              new BinaryFunction<String, String, String>()
                                              {
                                                  public String evaluate(final String s0, final String s1)
                                                  {
                                                      return "foo";
                                                  }
                                              });
        assertNotNull("m10 not null", m10);
        assertEquals("m10.get(0) == foo", "foo", m10.get(0));
        assertEquals("m10.get(1) == foo", "foo", m10.get(1));
        assertEquals("m10.get(99) == foo", "foo", m10.get(99));
        assertEquals("m10.cardinality() == 100", 100, m10.cardinality());
        assertEquals("m10 == m", m, m10);

        Matrix1D<String> m11 = m.assign(bars,
                                              new BinaryFunction<String, String, String>()
                                              {
                                                  public String evaluate(final String s0, final String s1)
                                                  {
                                                      return null;
                                                  }
                                              });
        assertNotNull("m11 not null", m11);
        assertEquals("m11.get(0) == null", null, m11.get(0));
        assertEquals("m11.get(1) == null", null, m11.get(1));
        assertEquals("m11.get(99) == null", null, m11.get(99));
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
            Matrix1D<String> m12 = m.assign((Matrix1D<String>) null, ignore);
            fail("assign((Matrix1D<E>) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix1D<String> tooSmall = createMatrix1D(99);
            m.assign(tooSmall, ignore);
            fail("assign(tooSmall,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix1D<String> tooBig = createMatrix1D(101);
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
        Matrix1D<String> m0 = createMatrix1D(10);
        Matrix1D<String> m1 = createMatrix1D(10);

        m0.assign("foo");

        // append aggregate function
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
        assertEquals("result0 == foo foo foo foo foo foo foo foo foo foo foo",
                     "foo foo foo foo foo foo foo foo foo foo", result0);

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
        assertEquals("result1 == foobar foobar foobar foobar foobar foobar foobar foobar foobar foobar foobar",
                     "foobar foobar foobar foobar foobar foobar foobar foobar foobar foobar", result1);

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
            Matrix1D<String> tooSmall = createMatrix1D(9);
            m0.aggregate(tooSmall, append, combine);
            fail("aggregate(tooSmall,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Matrix1D<String> tooBig = createMatrix1D(11);
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

        Matrix1D<String> empty0 = createMatrix1D(0);
        Matrix1D<String> empty1 = createMatrix1D(0);
        assertEquals(null, empty0.aggregate(append, passThru));
        assertEquals(null, empty0.aggregate(empty1, append, combine));
    }

    public void testIterator()
    {
        Matrix1D<String> m = createMatrix1D(100);
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

        m.set(0, "foo");

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

        m.set(0, null);
        m.set(1, null);
        m.set(2, null);

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

        // TODO:
        // test iterator.remove();
    }

    public void testForEachNonNull()
    {
        Matrix1D<String> m = createMatrix1D(100);
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
        m.setQuick(0L, "foo");
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
        Matrix1D<String> m = createMatrix1D(100);

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

        m.set(0, "bar");
        m.set(1, null);
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
        m.forEach(new BinaryProcedure<Long, String>()
                  {
                      public void run(final Long index, final String s)
                      {
                          assertTrue("index >= 0", index >= 0);
                          assertTrue("index < 100", index < 100);
                          assertEquals("s == null", null, s);
                      }
                  });

        m.assign("foo");
        m.forEach(new BinaryProcedure<Long, String>()
                  {
                      public void run(final Long index, final String s)
                      {
                          assertTrue("index >= 0", index >= 0);
                          assertTrue("index < 100", index < 100);
                          assertEquals("s == foo", "foo", s);
                      }
                  });

        m.set(0, "bar");
        m.set(1, null);
        m.forEach(new BinaryPredicate<Long, String>()
                  {
                      public boolean test(final Long index, final String s)
                      {
                          return ((0L == index.longValue()) || ("foo".equals(s)));
                      }
                  },
                  new BinaryProcedure<Long, String>()
                  {
                      public void run(final Long index, final String s)
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
            m.forEach((BinaryProcedure<Long, String>) null);
            fail("forEach((BinaryProcedure<Long, String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.forEach((BinaryPredicate<Long, String>) null, (BinaryProcedure<Long, String>) null);
            fail("forEach((BinaryPredicate<Long, String>) null, (BinaryProcedure<Long, String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            m.forEach(new BinaryPredicate<Long, String>()
                  {
                      public boolean test(final Long index, final String s)
                      {
                          return ((0L == index.longValue()) || ("foo".equals(s)));
                      }
                  }, (BinaryProcedure<Long, String>) null);
            fail("forEach(,(BinaryProcedure<Long, String>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testViewFlip()
    {
        Matrix1D<String> m = createMatrix1D(5);

        m.set(0, "0");
        m.set(1, "1");
        m.set(2, "2");
        m.set(3, "3");
        m.set(4, "4");
        assertEquals("m size == 5", 5, m.size());
        assertEquals("m cardinality == 5", 5, m.cardinality());
        assertEquals("m.get(0) == 0", "0", m.get(0));
        assertEquals("m.get(1) == 1", "1", m.get(1));
        assertEquals("m.get(2) == 2", "2", m.get(2));
        assertEquals("m.get(3) == 3", "3", m.get(3));
        assertEquals("m.get(4) == 4", "4", m.get(4));

        Matrix1D<String> flipOnce = m.viewFlip();
        assertNotNull("flipOnce not null", flipOnce);
        assertEquals("flipOnce size == m size", m.size(), flipOnce.size());
        assertEquals("flipOnce cardinality == m cardinality", m.cardinality(), flipOnce.cardinality());
        assertEquals("flipOnce.get(0) == 4", "4", flipOnce.get(0));
        assertEquals("flipOnce.get(1) == 3", "3", flipOnce.get(1));
        assertEquals("flipOnce.get(2) == 2", "2", flipOnce.get(2));
        assertEquals("flipOnce.get(3) == 1", "1", flipOnce.get(3));
        assertEquals("flipOnce.get(4) == 0", "0", flipOnce.get(4));

        Matrix1D<String> flipTwice = flipOnce.viewFlip();
        assertNotNull("flipTwice not null", flipTwice);
        assertEquals("flipTwice size == m size", m.size(), flipTwice.size());
        assertEquals("flipTwice cardinality == m cardinality", m.cardinality(), flipTwice.cardinality());
        assertEquals("flipTwice.get(0) == 0", "0", flipTwice.get(0));
        assertEquals("flipTwice.get(1) == 1", "1", flipTwice.get(1));
        assertEquals("flipTwice.get(2) == 2", "2", flipTwice.get(2));
        assertEquals("flipTwice.get(3) == 3", "3", flipTwice.get(3));
        assertEquals("flipTwice.get(4) == 4", "4", flipTwice.get(4));

        Matrix1D<String> flipThrice = flipTwice.viewFlip();
        assertNotNull("flipThrice not null", flipThrice);
        assertEquals("flipThrice size == m size", m.size(), flipThrice.size());
        assertEquals("flipThrice cardinality == m cardinality", m.cardinality(), flipThrice.cardinality());
        assertEquals("flipThrice.get(0) == 4", "4", flipThrice.get(0));
        assertEquals("flipThrice.get(1) == 3", "3", flipThrice.get(1));
        assertEquals("flipThrice.get(2) == 2", "2", flipThrice.get(2));
        assertEquals("flipThrice.get(3) == 1", "1", flipThrice.get(3));
        assertEquals("flipThrice.get(4) == 0", "0", flipThrice.get(4));

        flipThrice.set(0, "99");
        assertEquals("flipThrice.get(0) == 99", "99", flipThrice.get(0));
        assertEquals("flipTwice.get(4) == 99", "99", flipTwice.get(4));
        assertEquals("flipOnce.get(0) == 99", "99", flipOnce.get(0));
        assertEquals("m.get(4) == 99", "99", m.get(4));
    }

    public void testViewPart()
    {
        Matrix1D<String> m = createMatrix1D(10);

        m.set(0, "0");
        m.set(1, "1");
        m.set(2, "2");
        m.set(3, "3");
        m.set(4, "4");
        m.set(5, "5");
        m.set(6, "6");
        m.set(7, "7");
        m.set(8, "8");
        m.set(9, "9");
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 10", 10, m.cardinality());
        assertEquals("m.get(0) == 0", "0", m.get(0));
        assertEquals("m.get(1) == 1", "1", m.get(1));
        assertEquals("m.get(2) == 2", "2", m.get(2));
        assertEquals("m.get(3) == 3", "3", m.get(3));
        assertEquals("m.get(4) == 4", "4", m.get(4));
        assertEquals("m.get(5) == 5", "5", m.get(5));
        assertEquals("m.get(6) == 6", "6", m.get(6));
        assertEquals("m.get(7) == 7", "7", m.get(7));
        assertEquals("m.get(8) == 8", "8", m.get(8));
        assertEquals("m.get(9) == 9", "9", m.get(9));

        Matrix1D<String> firstHalf = m.viewPart(0, 5);
        assertNotNull("firstHalf not null", firstHalf);
        assertEquals("firstHalf size == 5", 5, firstHalf.size());
        assertEquals("firstHalf cardinality == 5", 5, firstHalf.size());
        assertEquals("firstHalf.get(0) == 0", "0", firstHalf.get(0));
        assertEquals("firstHalf.get(1) == 1", "1", firstHalf.get(1));
        assertEquals("firstHalf.get(2) == 2", "2", firstHalf.get(2));
        assertEquals("firstHalf.get(3) == 3", "3", firstHalf.get(3));
        assertEquals("firstHalf.get(4) == 4", "4", firstHalf.get(4));

        Matrix1D<String> firstThree = firstHalf.viewPart(0, 3);
        assertNotNull("firstThree not null", firstThree);
        assertEquals("firstThree size == 3", 3, firstThree.size());
        assertEquals("firstThree cardinality == 3", 3, firstThree.size());
        assertEquals("firstThree.get(0) == 0", "0", firstThree.get(0));
        assertEquals("firstThree.get(1) == 1", "1", firstThree.get(1));
        assertEquals("firstThree.get(2) == 2", "2", firstThree.get(2));

        Matrix1D<String> firstHalfFlip = firstHalf.viewFlip();
        assertNotNull("firstHalfFlip not null", firstHalfFlip);
        assertEquals("firstHalfFlip size == 5", 5, firstHalfFlip.size());
        assertEquals("firstHalfFlip cardinality == 5", 5, firstHalfFlip.cardinality());
        assertEquals("firstHalfFlip.get(0) == 4", "4", firstHalfFlip.get(0));
        assertEquals("firstHalfFlip.get(1) == 3", "3", firstHalfFlip.get(1));
        assertEquals("firstHalfFlip.get(2) == 2", "2", firstHalfFlip.get(2));
        assertEquals("firstHalfFlip.get(3) == 1", "1", firstHalfFlip.get(3));
        assertEquals("firstHalfFlip.get(4) == 0", "0", firstHalfFlip.get(4));

        Matrix1D<String> firstThreeOfFirstHalfFlip = firstHalfFlip.viewPart(0, 3);
        assertNotNull("firstThreeOfFirstHalfFlip not null", firstThreeOfFirstHalfFlip);
        assertEquals("firstThreeOfFirstHalfFlip size == 3", 3, firstThreeOfFirstHalfFlip.size());
        assertEquals("firstThreeOfFirstHalfFlip cardinality == 3", 3, firstThreeOfFirstHalfFlip.cardinality());
        assertEquals("firstThreeOfFirstHalfFlip.get(0) == 4", "4", firstThreeOfFirstHalfFlip.get(0));
        assertEquals("firstThreeOfFirstHalfFlip.get(1) == 3", "3", firstThreeOfFirstHalfFlip.get(1));
        assertEquals("firstThreeOfFirstHalfFlip.get(2) == 2", "2", firstThreeOfFirstHalfFlip.get(2));

        firstThreeOfFirstHalfFlip.set(0, "99");
        assertEquals("firstThreeOfFirstHalfFlip.get(0) == 99", "99", firstThreeOfFirstHalfFlip.get(0));
        assertEquals("firstHalfFlip.get(0) == 99", "99", firstHalfFlip.get(0));
        assertEquals("firstHalf.get(4) == 99", "99", firstHalf.get(4));
        assertEquals("m.get(4) == 99", "99", m.get(4));
    }

    public void testViewSelection()
    {
        // TODO
    }

    public void testViewStrides()
    {
        final Matrix1D<String> m = createMatrix1D(100);

        m.forEach(new BinaryProcedure<Long, String>()
            {
                public void run(final Long index, final String s)
                {
                    m.setQuick(index, index.toString());
                }
            });

        assertEquals("m size == 100", 100, m.size());
        assertEquals("m cardinality == 100", 100, m.cardinality());
        assertEquals("m.get(0) == 0", "0", m.get(0));
        assertEquals("m.get(99) == 99", "99", m.get(99));

        Matrix1D<String> everyTwo = m.viewStrides(2);
        assertNotNull("everyTwo not null", everyTwo);
        assertEquals("everyTwo size == 50", 50, everyTwo.size());
        assertEquals("everyTwo cardinality == 50", 50, everyTwo.cardinality());
        assertEquals("everyTwo.get(0) == 0", "0", everyTwo.get(0));
        assertEquals("everyTwo.get(1) == 2", "2", everyTwo.get(1));
        assertEquals("everyTwo.get(2) == 4", "4", everyTwo.get(2));
        assertEquals("everyTwo.get(49) == 98", "98", everyTwo.get(49));

        Matrix1D<String> everyTen = m.viewStrides(10);
        assertNotNull("everyTen not null", everyTen);
        assertEquals("everyTen size == 10", 10, everyTen.size());
        assertEquals("everyTen cardinality == 10", 10, everyTen.cardinality());
        assertEquals("everyTen.get(0) == 0", "0", everyTen.get(0));
        assertEquals("everyTen.get(1) == 10", "10", everyTen.get(1));
        assertEquals("everyTen.get(2) == 20", "20", everyTen.get(2));
        assertEquals("everyTen.get(9) == 90", "90", everyTen.get(9));

        Matrix1D<String> everyTwoOfEveryTen = everyTen.viewStrides(2);
        assertNotNull("everyTwoOfEveryTen not null", everyTwoOfEveryTen);
        assertEquals("everyTwoOfEveryTen size == 5", 5, everyTwoOfEveryTen.size());
        assertEquals("everyTwoOfEveryTen cardinality == 5", 5, everyTwoOfEveryTen.cardinality());
        assertEquals("everyTwoOfEveryTen.get(0) == 0", "0", everyTwoOfEveryTen.get(0));
        assertEquals("everyTwoOfEveryTen.get(1) == 20", "20", everyTwoOfEveryTen.get(1));
        assertEquals("everyTwoOfEveryTen.get(2) == 40", "40", everyTwoOfEveryTen.get(2));
        assertEquals("everyTwoOfEveryTen.get(3) == 60", "60", everyTwoOfEveryTen.get(3));
        assertEquals("everyTwoOfEveryTen.get(4) == 80", "80", everyTwoOfEveryTen.get(4));

        Matrix1D<String> everyTwoOfEveryTenFlip = everyTwoOfEveryTen.viewFlip();
        assertNotNull("everyTwoOfEveryTenFlip not null", everyTwoOfEveryTen);
        assertEquals("everyTwoOfEveryTenFlip size == 5", 5, everyTwoOfEveryTenFlip.size());
        assertEquals("everyTwoOfEveryTenFlip cardinality == 5", 5, everyTwoOfEveryTenFlip.cardinality());
        assertEquals("everyTwoOfEveryTenFlip.get(0) == 80", "80", everyTwoOfEveryTenFlip.get(0));
        assertEquals("everyTwoOfEveryTenFlip.get(1) == 60", "60", everyTwoOfEveryTenFlip.get(1));
        assertEquals("everyTwoOfEveryTenFlip.get(2) == 40", "40", everyTwoOfEveryTenFlip.get(2));
        assertEquals("everyTwoOfEveryTenFlip.get(3) == 20", "20", everyTwoOfEveryTenFlip.get(3));
        assertEquals("everyTwoOfEveryTenFlip.get(4) == 0", "0", everyTwoOfEveryTenFlip.get(4));

        Matrix1D<String> twentyAndZero = everyTwoOfEveryTenFlip.viewPart(3, 2);
        assertNotNull("twentyAndZero not null", twentyAndZero);
        assertEquals("twentyAndZero size == 2", 2, twentyAndZero.size());
        assertEquals("twentyAndZero cardinality == 2", 2, twentyAndZero.cardinality());
        assertEquals("twentyAndZero.get(0) == 20", "20", twentyAndZero.get(0));
        assertEquals("twentyAndZero.get(1) == 0", "0", twentyAndZero.get(1));

        twentyAndZero.set(0, "999");
        assertEquals("twentyAndZero.get(0) == 999", "999", twentyAndZero.get(0));
        assertEquals("everyTwoOfEveryTenFlip.get(3) == 999", "999", everyTwoOfEveryTenFlip.get(3));
        assertEquals("everyTwoOfEveryTen.get(1) == 999", "999", everyTwoOfEveryTen.get(1));
        assertEquals("everyTen.get(2) == 999", "999", everyTen.get(2));
        assertEquals("everyTwo.get(10) == 999", "999", everyTwo.get(10));
        assertEquals("m.get(20) == 999", "999", m.get(20));
    }

    public void testClear()
    {
        Matrix1D<String> m = createMatrix1D(10);

        m.assign("foo");
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 10", 10, m.cardinality());

        m.clear();
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());

        m.assign("foo");
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 10", 10, m.cardinality());

        Matrix1D<String> flip = m.viewFlip();
        assertEquals("flip size == 10", 10, flip.size());
        assertEquals("flip cardinality == 10", 10, flip.cardinality());

        flip.clear();
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertEquals("flip size == 10", 10, flip.size());
        assertEquals("flip cardinality == 0", 0, flip.cardinality());

        m.assign("foo");
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 10", 10, m.cardinality());

        Matrix1D<String> part = m.viewPart(4, 5);
        assertEquals("part size == 5", 5, part.size());
        assertEquals("part cardinality == 5", 5, part.cardinality());

        part.clear();
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 5", 5, m.cardinality());
        assertEquals("part size == 5", 5, part.size());
        assertEquals("part cardinality == 0", 0, part.cardinality());

        m.assign("foo");
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 10", 10, m.cardinality());

        Matrix1D<String> stride = m.viewStrides(2);
        assertEquals("stride size == 5", 5, stride.size());
        assertEquals("stride cardinality == 5", 5, stride.cardinality());

        stride.clear();
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 5", 5, m.cardinality());
        assertEquals("stride size == 5", 5, stride.size());
        assertEquals("stride cardinality == 0", 0, stride.cardinality());

        m.assign("foo");
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 10", 10, m.cardinality());

        m.clear();
        assertEquals("m size == 10", 10, m.size());
        assertEquals("m cardinality == 0", 0, m.cardinality());
        assertEquals("flip size == 10", 10, flip.size());
        assertEquals("flip cardinality == 0", 0, flip.cardinality());
        assertEquals("part size == 5", 5, part.size());
        assertEquals("part cardinality == 0", 0, part.cardinality());
        assertEquals("stride size == 5", 5, stride.size());
        assertEquals("stride cardinality == 0", 0, stride.cardinality());
    }

    public void testToString()
    {
        Matrix1D<String> m = createMatrix1D(10);
        m.assign("foo");
        assertNotNull(m.toString());
    }
}
