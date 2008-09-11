/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008 held jointly by the individual authors.

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
package org.dishevelled.matrix.io.impl;

import java.io.InputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.dishevelled.matrix.ObjectMatrix2D;

/**
 * Unit test for MatrixMarketReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class MatrixMarketReaderTest
    extends TestCase
{

    public void testConstructor()
    {
        MatrixMarketReader reader = new MatrixMarketReader();
        assertNotNull(reader);
    }

    public void testEmpty()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("empty.mm");
            MatrixMarketReader reader = new MatrixMarketReader();
            ObjectMatrix2D<Double> matrix = reader.read(inputStream);
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
            MatrixMarketReader reader = new MatrixMarketReader();
            ObjectMatrix2D<Double> matrix = reader.read(inputStream);
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
            MatrixMarketReader reader = new MatrixMarketReader();
            ObjectMatrix2D<Double> matrix = reader.read(inputStream);
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