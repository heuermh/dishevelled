/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2009 held jointly by the individual authors.

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.impl.SparseMatrix2D;

/**
 * Unit test for MatrixMarketWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class MatrixMarketWriterTest
    extends TestCase
{

    public void testConstructor()
    {
        MatrixMarketWriter writer = new MatrixMarketWriter();
        assertNotNull(writer);
    }

    public void testAppend() throws IOException
    {
        Matrix2D<Double> matrix = new SparseMatrix2D<Double>(16L, 16L);
        matrix.setQuick(0L, 0L, Double.valueOf(0.0d));
        matrix.setQuick(2L, 2L, Double.valueOf(1.0d));
        matrix.setQuick(3L, 4L, Double.valueOf(2.0d));
        matrix.setQuick(4L, 3L, Double.valueOf(3.0d));
        MatrixMarketWriter writer = new MatrixMarketWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertNotNull(appendable);

        try
        {
            writer.append(null, null);
            fail("append(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            writer.append(null, appendable);
            fail("append(null, ) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            writer.append(matrix, null);
            fail("append(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testWriteFile() throws IOException
    {
        Matrix2D<Double> matrix = new SparseMatrix2D<Double>(16L, 16L);
        MatrixMarketWriter writer = new MatrixMarketWriter();
        File file = File.createTempFile("abstractObjectMatrix2DTest", null);
        writer.write(matrix, file);

        try
        {
            writer.write(null, file);
            fail("write(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            writer.write(matrix, (File) null);
            fail("write(, (File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            writer.write(null, (File) null);
            fail("write(null, (File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testWriteOutputStream() throws IOException
    {
        Matrix2D<Double> matrix = new SparseMatrix2D<Double>(16L, 16L);
        MatrixMarketWriter writer = new MatrixMarketWriter();
        OutputStream outputStream = new ByteArrayOutputStream();
        writer.write(matrix, outputStream);

        try
        {
            writer.write(null, outputStream);
            fail("write(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            writer.write(matrix, (OutputStream) null);
            fail("write(, (OutputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            writer.write(null, (OutputStream) null);
            fail("write(null, (OutputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}