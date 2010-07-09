/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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
package org.dishevelled.matrix.io.impl.sparse;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.net.URL;

import junit.framework.TestCase;

import org.dishevelled.matrix.Matrix2D;

/**
 * Unit test for SparseMatrixMarketReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SparseMatrixMarketReaderTest
    extends TestCase
{

    public void testConstructor()
    {
        SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
        assertNotNull(reader);
    }

    public void testEmpty()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("empty.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("read(empty.mm) expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testEmptyWithHeader()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("emptyWithHeader.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("read(emptyWithHeader.mm) expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testEmptyMatrix() throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("emptyMatrix.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            Matrix2D<Double> matrix = reader.read(inputStream);
            assertNotNull(matrix);
            assertEquals(0, matrix.size());
            assertEquals(0, matrix.rows());
            assertEquals(0, matrix.columns());
            assertTrue(matrix.isEmpty());
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testFourByFourMatrix() throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("fourByFour.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            Matrix2D<Double> matrix = reader.read(inputStream);
            assertNotNull(matrix);
            assertEquals(16, matrix.size());
            assertEquals(4, matrix.rows());
            assertEquals(4, matrix.columns());
            assertFalse(matrix.isEmpty());
            assertEquals(16, matrix.cardinality());
            assertEquals(Double.valueOf(0.1d), matrix.get(0L, 0L), 0.01d);
            assertEquals(Double.valueOf(0.2d), matrix.get(0L, 1L), 0.01d);
            assertEquals(Double.valueOf(0.3d), matrix.get(0L, 2L), 0.01d);
            assertEquals(Double.valueOf(0.4d), matrix.get(0L, 3L), 0.01d);
            assertEquals(Double.valueOf(0.5d), matrix.get(1L, 0L), 0.01d);
            assertEquals(Double.valueOf(0.6d), matrix.get(1L, 1L), 0.01d);
            assertEquals(Double.valueOf(0.7d), matrix.get(1L, 2L), 0.01d);
            assertEquals(Double.valueOf(0.8d), matrix.get(1L, 3L), 0.01d);
            assertEquals(Double.valueOf(0.9d), matrix.get(2L, 0L), 0.01d);
            assertEquals(Double.valueOf(1.0d), matrix.get(2L, 1L), 0.01d);
            assertEquals(Double.valueOf(1.1d), matrix.get(2L, 2L), 0.01d);
            assertEquals(Double.valueOf(1.2d), matrix.get(2L, 3L), 0.01d);
            assertEquals(Double.valueOf(1.3d), matrix.get(3L, 0L), 0.01d);
            assertEquals(Double.valueOf(1.4d), matrix.get(3L, 1L), 0.01d);
            assertEquals(Double.valueOf(1.5d), matrix.get(3L, 2L), 0.01d);
            assertEquals(Double.valueOf(1.6d), matrix.get(3L, 3L), 0.01d);
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testSparseMatrix() throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("sparse.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            Matrix2D<Double> matrix = reader.read(inputStream);
            assertNotNull(matrix);
            assertEquals((998 * 999), matrix.size());
            assertEquals(998, matrix.rows());
            assertEquals(999, matrix.columns());
            assertFalse(matrix.isEmpty());
            assertEquals(4, matrix.cardinality());
            assertEquals(Double.valueOf(0.0d), matrix.get(0L, 0L), 0.01d);
            assertEquals(Double.valueOf(1.0d), matrix.get(2L, 2L), 0.01d);
            assertEquals(Double.valueOf(2.0d), matrix.get(3L, 4L), 0.01d);
            assertEquals(Double.valueOf(3.0d), matrix.get(4L, 3L), 0.01d);
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testSymmetricMatrix() throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("symmetric.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            Matrix2D<Double> matrix = reader.read(inputStream);
            assertNotNull(matrix);
            assertEquals(3 * 3, matrix.size());
            assertEquals(3, matrix.rows());
            assertEquals(3, matrix.columns());
            assertFalse(matrix.isEmpty());
            assertEquals(7, matrix.cardinality());
            assertEquals(Double.valueOf(1.0d), matrix.get(0L, 0L), 0.01d);
            assertEquals(Double.valueOf(4.0d), matrix.get(1L, 1L), 0.01d);
            assertNull(matrix.get(0L, 1L));
            assertNull(matrix.get(1L, 0L));
            assertEquals(Double.valueOf(6.0d), matrix.get(2L, 2L), 0.01d);
            assertEquals(Double.valueOf(3.0d), matrix.get(2L, 0L), 0.01d);
            assertEquals(Double.valueOf(3.0d), matrix.get(0L, 2L), 0.01d);
            assertEquals(Double.valueOf(-5.0d), matrix.get(2L, 1L), 0.01d);
            assertEquals(Double.valueOf(-5.0d), matrix.get(1L, 2L), 0.01d);
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testSkewSymmetricMatrix() throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("skewSymmetric.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            Matrix2D<Double> matrix = reader.read(inputStream);
            assertNotNull(matrix);
            assertEquals(3 * 3, matrix.size());
            assertEquals(3, matrix.rows());
            assertEquals(3, matrix.columns());
            assertFalse(matrix.isEmpty());
            assertEquals(6, matrix.cardinality());
            assertNull(matrix.get(0L, 0L));
            assertNull(matrix.get(1L, 1L));
            assertNull(matrix.get(2L, 2L));
            assertEquals(Double.valueOf(3.0d), matrix.get(1L, 0L), 0.01d);
            assertEquals(Double.valueOf(-3.0d), matrix.get(0L, 1L), 0.01d);
            assertEquals(Double.valueOf(-4.0d), matrix.get(2L, 0L), 0.01d);
            assertEquals(Double.valueOf(4.0d), matrix.get(0L, 2L), 0.01d);
            assertEquals(Double.valueOf(5.0d), matrix.get(2L, 1L), 0.01d);
            assertEquals(Double.valueOf(-5.0d), matrix.get(1L, 2L), 0.01d);
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testHermitianMatrix() throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("hermitian.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            Matrix2D<Double> matrix = reader.read(inputStream);
            assertNotNull(matrix);
            // TODO:  add'l assertions
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testInvalidHeader()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("invalidHeader.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("invalidHeader.mm expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testInvalidHeaderFormat()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("invalidHeaderFormat.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("invalidHeaderFormat.mm expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testInvalidHeaderType()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("invalidHeaderType.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("invalidHeaderType.mm expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testInvalidHeaderSymmetryStructure()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("invalidHeaderSymmetryStructure.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("invalidHeaderSymmetryStructure.mm expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testInvalidRowMatrix()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("invalidRow.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("invalidRow.mm expected IOException due to NumberFormatException");
        }
        catch (IOException e)
        {
            assertTrue(e.getMessage().contains("NumberFormatException"));
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testInvalidColumnMatrix()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("invalidColumn.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("invalidColumn.mm expected IOException due to NumberFormatException");
        }
        catch (IOException e)
        {
            assertTrue(e.getMessage().contains("NumberFormatException"));
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testInvalidValueMatrix()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("invalidValue.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("invalidValue.mm expected IOException due to NumberFormatException");
        }
        catch (IOException e)
        {
            assertTrue(e.getMessage().contains("NumberFormatException"));
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testRowOutOfBoundsMatrix()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("rowOutOfBounds.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("rowOutOfBounds.mm expected IOException due to NumberFormatException");
        }
        catch (IOException e)
        {
            assertTrue(e.getMessage().contains("IndexOutOfBoundsException"));
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testColumnOutOfBoundsMatrix()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("columnOutOfBounds.mm");
            SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
            reader.read(inputStream);
            fail("columnOutOfBounds.mm expected IOException due to NumberFormatException");
        }
        catch (IOException e)
        {
            assertTrue(e.getMessage().contains("IndexOutOfBoundsException"));
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testReadInputStream() throws IOException
    {
        SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
        try
        {
            reader.read((InputStream) null);
            fail("read((InputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testReadFile() throws IOException
    {
        SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
        try
        {
            reader.read((File) null);
            fail("read((File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testReadURL() throws IOException
    {
        SparseMatrixMarketReader reader = new SparseMatrixMarketReader();
        try
        {
            reader.read((URL) null);
            fail("read((URL) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    /**
     * Close the specified input stream quietly.
     *
     * @param inputStream input stream to close
     */
    private void closeQuietly(final InputStream inputStream)
    {
        try
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        catch (IOException e)
        {
            // ignore
        }
    }
}