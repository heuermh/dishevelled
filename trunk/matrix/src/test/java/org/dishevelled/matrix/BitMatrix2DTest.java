/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2011 held jointly by the individual authors.

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
 * Unit test for BitMatrix2D.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BitMatrix2DTest
    extends TestCase
{

    public void testConstructor()
    {
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(0, 1);
        BitMatrix2D bm2 = new BitMatrix2D(1, 0);
        BitMatrix2D bm3 = new BitMatrix2D(1, 1);
        BitMatrix2D bm4 = new BitMatrix2D(1000, 1000);

        try
        {
            BitMatrix2D bm = new BitMatrix2D(-1, 0);
            fail("ctr(-1,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            BitMatrix2D bm = new BitMatrix2D(0, -1);
            fail("ctr(,-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSize()
    {
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(1, 0);
        BitMatrix2D bm2 = new BitMatrix2D(0, 1);
        BitMatrix2D bm3 = new BitMatrix2D(1, 1);
        BitMatrix2D bm4 = new BitMatrix2D(100, 10);
        BitMatrix2D bm5 = new BitMatrix2D(100, 100);
        BitMatrix2D bm6 = new BitMatrix2D(1000, 1000);

        assertEquals("bm0 size == 0", 0, bm0.size());
        assertEquals("bm0 rows == 0", 0, bm0.rows());
        assertEquals("bm0 columns == 0", 0, bm0.columns());

        assertEquals("bm1 size == 0", 0, bm1.size());
        assertEquals("bm1 rows == 1", 1, bm1.rows());
        assertEquals("bm1 columns == 0", 0, bm1.columns());

        assertEquals("bm2 size == 0", 0, bm2.size());
        assertEquals("bm2 rows == 0", 0, bm2.rows());
        assertEquals("bm2 columns == 1", 1, bm2.columns());

        assertEquals("bm3 size == 1", 1, bm3.size());
        assertEquals("bm3 rows == 1", 1, bm3.rows());
        assertEquals("bm3 columns == 1", 1, bm3.columns());

        assertEquals("bm4 size == 1000", 1000, bm4.size());
        assertEquals("bm4 rows == 100", 100, bm4.rows());
        assertEquals("bm4 columns == 10", 10, bm4.columns());

        assertEquals("bm5 size == 10000", 10000, bm5.size());
        assertEquals("bm5 rows == 100", 100, bm5.rows());
        assertEquals("bm5 columns == 100", 100, bm5.columns());

        assertEquals("bm6 size == 1000000", 1000000, bm6.size());
        assertEquals("bm6 rows == 1000", 1000, bm6.rows());
        assertEquals("bm6 columns == 1000", 1000, bm6.columns());

        bm0.assign(true);
        bm1.assign(true);
        bm2.assign(true);
        bm3.assign(true);
        bm4.assign(true);
        bm5.assign(true);
        bm6.assign(true);

        assertEquals("bm0 size == 0", 0, bm0.size());
        assertEquals("bm0 rows == 0", 0, bm0.rows());
        assertEquals("bm0 columns == 0", 0, bm0.columns());

        assertEquals("bm1 size == 0", 0, bm1.size());
        assertEquals("bm1 rows == 1", 1, bm1.rows());
        assertEquals("bm1 columns == 0", 0, bm1.columns());

        assertEquals("bm2 size == 0", 0, bm2.size());
        assertEquals("bm2 rows == 0", 0, bm2.rows());
        assertEquals("bm2 columns == 1", 1, bm2.columns());

        assertEquals("bm3 size == 1", 1, bm3.size());
        assertEquals("bm3 rows == 1", 1, bm3.rows());
        assertEquals("bm3 columns == 1", 1, bm3.columns());

        assertEquals("bm4 size == 1000", 1000, bm4.size());
        assertEquals("bm4 rows == 100", 100, bm4.rows());
        assertEquals("bm4 columns == 10", 10, bm4.columns());

        assertEquals("bm5 size == 10000", 10000, bm5.size());
        assertEquals("bm5 rows == 100", 100, bm5.rows());
        assertEquals("bm5 columns == 100", 100, bm5.columns());

        assertEquals("bm6 size == 1000000", 1000000, bm6.size());
        assertEquals("bm6 rows == 1000", 1000, bm6.rows());
        assertEquals("bm6 columns == 1000", 1000, bm6.columns());

        bm0.assign(false);
        bm1.assign(false);
        bm2.assign(false);
        bm3.assign(false);
        bm4.assign(false);
        bm5.assign(false);
        bm6.assign(false);

        assertEquals("bm0 size == 0", 0, bm0.size());
        assertEquals("bm0 rows == 0", 0, bm0.rows());
        assertEquals("bm0 columns == 0", 0, bm0.columns());

        assertEquals("bm1 size == 0", 0, bm1.size());
        assertEquals("bm1 rows == 1", 1, bm1.rows());
        assertEquals("bm1 columns == 0", 0, bm1.columns());

        assertEquals("bm2 size == 0", 0, bm2.size());
        assertEquals("bm2 rows == 0", 0, bm2.rows());
        assertEquals("bm2 columns == 1", 1, bm2.columns());

        assertEquals("bm3 size == 1", 1, bm3.size());
        assertEquals("bm3 rows == 1", 1, bm3.rows());
        assertEquals("bm3 columns == 1", 1, bm3.columns());

        assertEquals("bm4 size == 1000", 1000, bm4.size());
        assertEquals("bm4 rows == 100", 100, bm4.rows());
        assertEquals("bm4 columns == 10", 10, bm4.columns());

        assertEquals("bm5 size == 10000", 10000, bm5.size());
        assertEquals("bm5 rows == 100", 100, bm5.rows());
        assertEquals("bm5 columns == 100", 100, bm5.columns());

        assertEquals("bm6 size == 1000000", 1000000, bm6.size());
        assertEquals("bm6 rows == 1000", 1000, bm6.rows());
        assertEquals("bm6 columns == 1000", 1000, bm6.columns());
    }

    public void testCardinality()
    {
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(1, 0);
        BitMatrix2D bm2 = new BitMatrix2D(0, 1);
        BitMatrix2D bm3 = new BitMatrix2D(1, 1);
        BitMatrix2D bm4 = new BitMatrix2D(100, 10);
        BitMatrix2D bm5 = new BitMatrix2D(100, 100);
        BitMatrix2D bm6 = new BitMatrix2D(1000, 1000);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 0", 0, bm1.cardinality());
        assertEquals("bm2 cardinality == 0", 0, bm2.cardinality());
        assertEquals("bm3 cardinality == 0", 0, bm3.cardinality());
        assertEquals("bm4 cardinality == 0", 0, bm4.cardinality());
        assertEquals("bm5 cardinality == 0", 0, bm5.cardinality());
        assertEquals("bm6 cardinality == 0", 0, bm6.cardinality());

        bm0.assign(true);
        bm1.assign(true);
        bm2.assign(true);
        bm3.assign(true);
        bm4.assign(true);
        bm5.assign(true);
        bm6.assign(true);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 0", 0, bm1.cardinality());
        assertEquals("bm2 cardinality == 0", 0, bm2.cardinality());
        assertEquals("bm3 cardinality == 1", 1, bm3.cardinality());
        assertEquals("bm4 cardinality == 1000", 1000, bm4.cardinality());
        assertEquals("bm5 cardinality == 10000", 10000, bm5.cardinality());
        assertEquals("bm6 cardinality == 1000000", 1000000, bm6.cardinality());

        bm0.assign(false);
        bm1.assign(false);
        bm2.assign(false);
        bm3.assign(false);
        bm4.assign(false);
        bm5.assign(false);
        bm6.assign(false);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 0", 0, bm1.cardinality());
        assertEquals("bm2 cardinality == 0", 0, bm2.cardinality());
        assertEquals("bm3 cardinality == 0", 0, bm3.cardinality());
        assertEquals("bm4 cardinality == 0", 0, bm4.cardinality());
        assertEquals("bm5 cardinality == 0", 0, bm5.cardinality());
        assertEquals("bm6 cardinality == 0", 0, bm6.cardinality());

        bm3.set(0, 0, true);
        bm4.set(0, 0, true);
        bm5.set(0, 0, true);
        bm6.set(0, 0, true);

        assertEquals("bm3 cardinality == 1", 1, bm3.cardinality());
        assertEquals("bm4 cardinality == 1", 1, bm4.cardinality());
        assertEquals("bm5 cardinality == 1", 1, bm5.cardinality());
        assertEquals("bm6 cardinality == 1", 1, bm6.cardinality());

        bm4.set(1, 0, true);
        bm5.set(1, 0, true);
        bm6.set(1, 0, true);

        assertEquals("bm4 cardinality == 2", 2, bm4.cardinality());
        assertEquals("bm5 cardinality == 2", 2, bm5.cardinality());
        assertEquals("bm6 cardinality == 2", 2, bm6.cardinality());

        bm0.assign(false);
        bm1.assign(false);
        bm2.assign(false);
        bm3.assign(false);
        bm4.assign(false);
        bm5.assign(false);
        bm6.assign(false);

        assertEquals("bm0 cardinality == 0", 0, bm0.cardinality());
        assertEquals("bm1 cardinality == 0", 0, bm1.cardinality());
        assertEquals("bm2 cardinality == 0", 0, bm2.cardinality());
        assertEquals("bm3 cardinality == 0", 0, bm3.cardinality());
        assertEquals("bm4 cardinality == 0", 0, bm4.cardinality());
        assertEquals("bm5 cardinality == 0", 0, bm5.cardinality());
        assertEquals("bm6 cardinality == 0", 0, bm6.cardinality());
    }

    public void testGetSetClearFlip()
    {
        BitMatrix2D bm = new BitMatrix2D(10, 10);

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertFalse(bm.get(row, column));
            }
        }
        assertEquals(0, bm.cardinality());

        bm.assign(true);

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertTrue(bm.get(row, column));
            }
        }
        assertEquals(100, bm.cardinality());

        bm.assign(false);

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertFalse(bm.get(row, column));
            }
        }
        assertEquals(0, bm.cardinality());

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                bm.set(row, column, true);
            }
        }

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertTrue(bm.get(row, column));
            }
        }
        assertEquals(100, bm.cardinality());

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                bm.set(row, column, false);
            }
        }

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertFalse(bm.get(row, column));
            }
        }
        assertEquals(0, bm.cardinality());

        bm.assign(true);

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertTrue(bm.get(row, column));
            }
        }
        assertEquals(100, bm.cardinality());

        bm.clear();

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertFalse(bm.get(row, column));
            }
        }
        assertEquals(0, bm.cardinality());

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                bm.flip(row, column);
            }
        }

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertTrue(bm.get(row, column));
            }
        }
        assertEquals(100, bm.cardinality());

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                bm.flip(row, column);
            }
        }

        for (long row = 0, rows = bm.rows(); row < rows; row++)
        {
            for (long column = 0, columns = bm.columns(); column < columns; column++)
            {
                assertFalse(bm.get(row, column));
            }
        }
        assertEquals(0, bm.cardinality());

        try
        {
            bm.get(-1, 0);
            fail("get(-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.get(10, 0);
            fail("get(10,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.set(-1, 0, true);
            fail("set(-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.set(10, 0, true);
            fail("set(10,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.flip(-1, 0);
            fail("flip(-1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }

        try
        {
            bm.flip(10, 0);
            fail("flip(10,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testIntersects()
    {
        // TODO
    }

    public void testIntersectsParams()
    {
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(1, 1);
        BitMatrix2D bm2 = new BitMatrix2D(10, 100);
        BitMatrix2D bm3 = new BitMatrix2D(100, 10);

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
        // TODO
    }

    public void testAndParams()
    {
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(1, 1);
        BitMatrix2D bm2 = new BitMatrix2D(10, 100);
        BitMatrix2D bm3 = new BitMatrix2D(100, 10);

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
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(1, 1);
        BitMatrix2D bm2 = new BitMatrix2D(10, 100);
        BitMatrix2D bm3 = new BitMatrix2D(100, 10);

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
        // TODO
    }

    public void testOrParams()
    {
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(1, 1);
        BitMatrix2D bm2 = new BitMatrix2D(10, 100);
        BitMatrix2D bm3 = new BitMatrix2D(100, 10);

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
        BitMatrix2D bm0 = new BitMatrix2D(0, 0);
        BitMatrix2D bm1 = new BitMatrix2D(1, 1);
        BitMatrix2D bm2 = new BitMatrix2D(10, 100);
        BitMatrix2D bm3 = new BitMatrix2D(100, 10);

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
}
