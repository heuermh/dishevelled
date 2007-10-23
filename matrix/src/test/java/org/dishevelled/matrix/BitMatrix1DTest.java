/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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

/**
 * Unit test for BitMatrix1D.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BitMatrix1DTest
    extends TestCase
{

    public void testConstructor()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(1000);

        try
        {
            BitMatrix1D bm = new BitMatrix1D(-1);
            fail("ctr(-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSize()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(100);
        BitMatrix1D bm3 = new BitMatrix1D(1000);

        assertEquals("bm0 size == 0", 0, bm0.size());
        assertEquals("bm1 size == 1", 1, bm1.size());
        assertEquals("bm2 size == 100", 100, bm2.size());
        assertEquals("bm3 size == 1000", 1000, bm3.size());

        bm0.assign(true);
        bm1.assign(true);
        bm2.assign(true);
        bm3.assign(true);

        assertEquals("bm0 size == 0", 0, bm0.size());
        assertEquals("bm1 size == 1", 1, bm1.size());
        assertEquals("bm2 size == 100", 100, bm2.size());
        assertEquals("bm3 size == 1000", 1000, bm3.size());

        bm0.assign(false);
        bm1.assign(false);
        bm2.assign(false);
        bm3.assign(false);

        assertEquals("bm0 size == 0", 0, bm0.size());
        assertEquals("bm1 size == 1", 1, bm1.size());
        assertEquals("bm2 size == 100", 100, bm2.size());
        assertEquals("bm3 size == 1000", 1000, bm3.size());
    }

    public void testCardinality()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(100);
        BitMatrix1D bm3 = new BitMatrix1D(1000);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 0", 0, bm1.cardinality());
        assertEquals("bm2 cardinality == 0", 0, bm2.cardinality());
        assertEquals("bm3 cardinality == 0", 0, bm3.cardinality());

        bm0.assign(true);
        bm1.assign(true);
        bm2.assign(true);
        bm3.assign(true);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 1", 1, bm1.cardinality());
        assertEquals("bm2 cardinality == 100", 100, bm2.cardinality());
        assertEquals("bm3 cardinality == 1000", 1000, bm3.cardinality());

        bm0.assign(false);
        bm1.assign(false);
        bm2.assign(false);
        bm3.assign(false);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 0", 0, bm1.cardinality());
        assertEquals("bm2 cardinality == 0", 0, bm2.cardinality());
        assertEquals("bm3 cardinality == 0", 0, bm3.cardinality());

        bm1.set(0, true);
        bm2.set(0, true);
        bm3.set(0, true);

        assertEquals("bm1 cardinality == 1", 1, bm1.cardinality());
        assertEquals("bm2 cardinality == 1", 1, bm2.cardinality());
        assertEquals("bm3 cardinality == 1", 1, bm3.cardinality());

        bm2.set(1, true);
        bm3.set(1, true);

        assertEquals("bm2 cardinality == 2", 2, bm2.cardinality());
        assertEquals("bm3 cardinality == 2", 2, bm3.cardinality());

        bm0.assign(false);
        bm1.assign(false);
        bm2.assign(false);
        bm3.assign(false);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 0", 0, bm1.cardinality());
        assertEquals("bm2 cardinality == 0", 0, bm2.cardinality());
        assertEquals("bm3 cardinality == 0", 0, bm3.cardinality());
    }

    public void testGetSetClearFlip()
    {
        BitMatrix1D bm = new BitMatrix1D(10);

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertFalse(bm.get(i));
        }
        assertEquals(0, bm.cardinality());

        bm.assign(true);

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertTrue(bm.get(i));
        }
        assertEquals(10, bm.cardinality());

        bm.assign(false);

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertFalse(bm.get(i));
        }
        assertEquals(0, bm.cardinality());

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            bm.set(i, true);
        }

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertTrue(bm.get(i));
        }
        assertEquals(10, bm.cardinality());

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            bm.set(i, false);
        }

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertFalse(bm.get(i));
        }
        assertEquals(0, bm.cardinality());

        bm.assign(true);

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertTrue(bm.get(i));
        }
        assertEquals(10, bm.cardinality());

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            bm.set(i, false);
        }

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertFalse(bm.get(i));
        }
        assertEquals(0, bm.cardinality());

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            bm.flip(i);
        }

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertTrue(bm.get(i));
        }
        assertEquals(10, bm.cardinality());

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            bm.flip(i);
        }

        for (long i = 0, size = bm.size(); i < size; i++)
        {
            assertFalse(bm.get(i));
        }
        assertEquals(0, bm.cardinality());

        try
        {
            bm.get(-1);
            fail("get(-1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.get(10);
            fail("get(10) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.get(11);
            fail("get(11) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.set(-1, true);
            fail("set(-1, true) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.set(10, true);
            fail("set(10, true) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.set(11, true);
            fail("set(11, true) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.flip(-1);
            fail("flip(-1) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.flip(10);
            fail("flip(10) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.flip(11);
            fail("flip(11) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testIntersects()
    {
        BitMatrix1D bm0 = new BitMatrix1D(10);
        BitMatrix1D bm1 = new BitMatrix1D(10);

        assertFalse(bm0.intersects(bm1));
        assertFalse(bm1.intersects(bm0));

        bm1.assign(true);

        assertFalse(bm0.intersects(bm1));
        assertFalse(bm1.intersects(bm0));

        bm0.assign(true);
        bm1.assign(true);

        assertTrue(bm0.intersects(bm1));
        assertTrue(bm1.intersects(bm0));

        bm0.assign(false);
        bm1.assign(false);
        bm1.set(0, true);

        assertFalse(bm0.intersects(bm1));
        assertFalse(bm1.intersects(bm0));

        bm0.set(0, true);

        assertTrue(bm0.intersects(bm1));
        assertTrue(bm1.intersects(bm0));

        bm0.assign(false);
        bm1.assign(false);
        bm0.set(0, true);
        bm1.set(1, true);

        assertFalse(bm0.intersects(bm1));
        assertFalse(bm1.intersects(bm0));

        bm1.set(0, true);

        assertTrue(bm0.intersects(bm1));
        assertTrue(bm1.intersects(bm0));
    }

    public void testIntersectsParams()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(100);
        BitMatrix1D bm3 = new BitMatrix1D(1000);

        try
        {
            bm0.intersects(null);
            fail("intersects(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.intersects(bm1);
            fail("bm0.intersects(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.intersects(bm2);
            fail("bm0.intersects(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.intersects(bm3);
            fail("bm0.intersects(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm1.intersects(bm0);
            fail("bm1.intersects(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.intersects(bm2);
            fail("bm1.intersects(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.intersects(bm3);
            fail("bm1.intersects(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm2.intersects(bm0);
            fail("bm2.intersects(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.intersects(bm1);
            fail("bm2.intersects(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.intersects(bm3);
            fail("bm2.intersects(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm3.intersects(bm0);
            fail("bm3.intersects(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.intersects(bm1);
            fail("bm3.intersects(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.intersects(bm2);
            fail("bm3.intersects(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAnd()
    {
        BitMatrix1D bm = new BitMatrix1D(10);
        BitMatrix1D empty = new BitMatrix1D(10);
        BitMatrix1D full = new BitMatrix1D(10);
        BitMatrix1D single = new BitMatrix1D(10);

        empty.assign(false);
        full.assign(true);
        single.set(0, true);

        bm.assign(false);

        bm.and(empty);
        assertEquals(empty, bm);

        bm.and(full);
        assertEquals(empty, bm);

        bm.and(single);
        assertEquals(empty, bm);

        bm.assign(true);

        bm.and(empty);
        assertEquals(empty, bm);

        bm.assign(true);

        bm.and(full);
        assertEquals(full, bm);

        bm.assign(true);

        bm.and(single);
        assertEquals(single, bm);

        bm.assign(false);
        bm.set(0, true);

        bm.and(empty);
        assertEquals(empty, bm);

        bm.assign(false);
        bm.set(0, true);

        bm.and(full);
        assertEquals(single, bm);

        bm.assign(false);
        bm.set(0, true);

        bm.and(single);
        assertEquals(single, bm);
    }

    public void testAndParams()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(100);
        BitMatrix1D bm3 = new BitMatrix1D(1000);

        try
        {
            bm0.and(null);
            fail("and(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.and(bm1);
            fail("bm0.and(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.and(bm2);
            fail("bm0.and(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.and(bm3);
            fail("bm0.and(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm1.and(bm0);
            fail("bm1.and(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.and(bm2);
            fail("bm1.and(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.and(bm3);
            fail("bm1.and(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm2.and(bm0);
            fail("bm2.and(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.and(bm1);
            fail("bm2.and(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.and(bm3);
            fail("bm2.and(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm3.and(bm0);
            fail("bm3.and(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.and(bm1);
            fail("bm3.and(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.and(bm2);
            fail("bm3.and(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAndNot()
    {
        // TODO
    }

    public void testAndNotParams()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(100);
        BitMatrix1D bm3 = new BitMatrix1D(1000);

        try
        {
            bm0.andNot(null);
            fail("andNot(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.andNot(bm1);
            fail("bm0.andNot(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.andNot(bm2);
            fail("bm0.andNot(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.andNot(bm3);
            fail("bm0.andNot(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm1.andNot(bm0);
            fail("bm1.andNot(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.andNot(bm2);
            fail("bm1.andNot(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.andNot(bm3);
            fail("bm1.andNot(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm2.andNot(bm0);
            fail("bm2.andNot(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.andNot(bm1);
            fail("bm2.andNot(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.andNot(bm3);
            fail("bm2.andNot(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm3.andNot(bm0);
            fail("bm3.andNot(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.andNot(bm1);
            fail("bm3.andNot(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.andNot(bm2);
            fail("bm3.andNot(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testOr()
    {
        BitMatrix1D bm = new BitMatrix1D(10);
        BitMatrix1D empty = new BitMatrix1D(10);
        BitMatrix1D full = new BitMatrix1D(10);
        BitMatrix1D zero = new BitMatrix1D(10);
        BitMatrix1D one = new BitMatrix1D(10);
        BitMatrix1D zeroAndOne = new BitMatrix1D(10);

        empty.assign(false);
        full.assign(true);
        zero.set(0, true);
        one.set(1, true);
        zeroAndOne.set(0, true);
        zeroAndOne.set(1, true);

        bm.assign(false);

        bm.or(empty);
        assertEquals(empty, bm);

        bm.or(full);
        assertEquals(full, bm);

        bm.assign(false);

        bm.or(zero);
        assertEquals(zero, bm);

        bm.assign(false);

        bm.or(one);
        assertEquals(one, bm);

        bm.assign(false);

        bm.or(zeroAndOne);
        assertEquals(zeroAndOne, bm);

        bm.assign(true);

        bm.or(empty);
        assertEquals(full, bm);

        bm.or(full);
        assertEquals(full, bm);

        bm.or(zero);
        assertEquals(full, bm);

        bm.or(one);
        assertEquals(full, bm);

        bm.or(zeroAndOne);
        assertEquals(full, bm);

        bm.assign(false);
        bm.set(0, true);

        bm.or(empty);
        assertEquals(zero, bm);

        bm.or(full);
        assertEquals(full, bm);

        bm.assign(false);
        bm.set(0, true);

        bm.or(zero);
        assertEquals(zero, bm);

        bm.or(one);
        assertEquals(zeroAndOne, bm);

        bm.assign(false);
        bm.set(0, true);

        bm.or(zeroAndOne);
        assertEquals(zeroAndOne, bm);
    }

    public void testOrParams()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(100);
        BitMatrix1D bm3 = new BitMatrix1D(1000);

        try
        {
            bm0.or(null);
            fail("or(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.or(bm1);
            fail("bm0.or(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.or(bm2);
            fail("bm0.or(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.or(bm3);
            fail("bm0.or(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm1.or(bm0);
            fail("bm1.or(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.or(bm2);
            fail("bm1.or(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.or(bm3);
            fail("bm1.or(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm2.or(bm0);
            fail("bm2.or(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.or(bm1);
            fail("bm2.or(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.or(bm3);
            fail("bm2.or(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm3.or(bm0);
            fail("bm3.or(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.or(bm1);
            fail("bm3.or(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.or(bm2);
            fail("bm3.or(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testXor()
    {
        // TODO
    }

    public void testXorParams()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(1);
        BitMatrix1D bm2 = new BitMatrix1D(100);
        BitMatrix1D bm3 = new BitMatrix1D(1000);

        try
        {
            bm0.xor(null);
            fail("xor(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.xor(bm1);
            fail("bm0.xor(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.xor(bm2);
            fail("bm0.xor(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm0.xor(bm3);
            fail("bm0.xor(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm1.xor(bm0);
            fail("bm1.xor(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.xor(bm2);
            fail("bm1.xor(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm1.xor(bm3);
            fail("bm1.xor(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm2.xor(bm0);
            fail("bm2.xor(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.xor(bm1);
            fail("bm2.xor(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm2.xor(bm3);
            fail("bm2.xor(bm3) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        try
        {
            bm3.xor(bm0);
            fail("bm3.xor(bm0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.xor(bm1);
            fail("bm3.xor(bm1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            bm3.xor(bm2);
            fail("bm3.xor(bm2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testBitMatrix1D()
    {
        BitMatrix1D bm0 = new BitMatrix1D(0);
        BitMatrix1D bm1 = new BitMatrix1D(0);

        BitMatrix1D bm2 = new BitMatrix1D(10);
        BitMatrix1D bm3 = new BitMatrix1D(10);

        BitMatrix1D bm4 = new BitMatrix1D(100);
        BitMatrix1D bm5 = new BitMatrix1D(100);

        assertFalse("bm0 not equals null", bm0.equals(null));
        assertFalse("bm0 not equals foo", bm0.equals("foo"));

        assertEquals("bm0 equals bm1", bm0, bm1);
        assertEquals("bm1 equals bm0", bm1, bm0);
        assertNotSame("bm0 not same as bm1", bm0, bm1);
        assertEquals("bm0 hashCode == bm1 hashCode", bm1.hashCode(), bm0.hashCode());

        assertEquals("bm2 equals bm3", bm2, bm3);
        assertEquals("bm3 equals bm2", bm3, bm2);
        assertNotSame("bm2 not same as bm3", bm2, bm3);
        assertEquals("bm2 hashCode == bm3 hashCode", bm3.hashCode(), bm2.hashCode());

        assertEquals("bm4 equals bm5", bm4, bm5);
        assertEquals("bm5 equals bm4", bm5, bm4);
        assertNotSame("bm4 not same as bm5", bm4, bm5);
        assertEquals("bm4 hashCode == bm5 hashCode", bm5.hashCode(), bm4.hashCode());

        assertFalse("bm0 not equals bm2", bm0.equals(bm2));
        assertFalse("bm0 not equals bm3", bm0.equals(bm3));
        assertFalse("bm0 not equals bm4", bm0.equals(bm4));
        assertFalse("bm0 not equals bm5", bm0.equals(bm5));

        assertFalse("bm1 not equals bm2", bm1.equals(bm2));
        assertFalse("bm1 not equals bm3", bm1.equals(bm3));
        assertFalse("bm1 not equals bm4", bm1.equals(bm4));
        assertFalse("bm1 not equals bm5", bm1.equals(bm5));

        assertFalse("bm2 not equals bm0", bm2.equals(bm0));
        assertFalse("bm2 not equals bm1", bm2.equals(bm1));
        assertFalse("bm2 not equals bm4", bm2.equals(bm4));
        assertFalse("bm2 not equals bm5", bm2.equals(bm5));

        assertFalse("bm3 not equals bm0", bm3.equals(bm0));
        assertFalse("bm3 not equals bm1", bm3.equals(bm1));
        assertFalse("bm3 not equals bm4", bm3.equals(bm4));
        assertFalse("bm3 not equals bm5", bm3.equals(bm5));

        assertFalse("bm4 not equals bm0", bm4.equals(bm0));
        assertFalse("bm4 not equals bm1", bm4.equals(bm1));
        assertFalse("bm4 not equals bm2", bm4.equals(bm2));
        assertFalse("bm4 not equals bm3", bm4.equals(bm3));

        assertFalse("bm5 not equals bm0", bm5.equals(bm0));
        assertFalse("bm5 not equals bm1", bm5.equals(bm1));
        assertFalse("bm5 not equals bm2", bm5.equals(bm2));
        assertFalse("bm5 not equals bm3", bm5.equals(bm3));

        bm3.set(0, true);

        assertFalse("modified bm3 not equals bm2", bm3.equals(bm2));
        assertFalse("bm2 not equals modified bm3", bm2.equals(bm3));

        bm4.set(0, true);

        assertFalse("modified bm5 not equals bm4", bm5.equals(bm4));
        assertFalse("bm4 not equals modified bm5", bm4.equals(bm5));
    }

    public void testLargeSizes()
    {
        // TODO
        //BitMatrix1D bm0 = new BitMatrix1D((long) (Integer.MAX_VALUE));
        //BitMatrix1D bm1 = new BitMatrix1D((long) (Integer.MAX_VALUE + 1l));
        //BitMatrix1D bm2 = new BitMatrix1D((long) (((long) Integer.MAX_VALUE) * 2l));
        //BitMatrix1D bm3 = new BitMatrix1D((long) (((long) Integer.MAX_VALUE) * 2l + 1l));
    }
}
