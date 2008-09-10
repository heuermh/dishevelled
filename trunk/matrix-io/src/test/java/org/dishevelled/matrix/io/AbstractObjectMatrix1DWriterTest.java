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
package org.dishevelled.matrix.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.dishevelled.matrix.ObjectMatrix1D;

import org.dishevelled.matrix.impl.SparseObjectMatrix1D;

/**
 * Abstract unit test for implementations of ObjectMatrix1DWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObjectMatrix1DWriterTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of ObjectMatrix1DWriter to test.
     *
     * @param <E> 1D matrix element type
     * @return a new instance of an implementation of ObjectMatrix1DWriter to test
     */
    protected abstract <E> ObjectMatrix1DWriter<E> createObjectMatrix1DWriter();

    public void testCreateObjectMatrix1DWriter()
    {
        ObjectMatrix1DWriter<String> writer = createObjectMatrix1DWriter();
        assertNotNull(writer);
    }

    public void testAppend() throws IOException
    {
        ObjectMatrix1D<String> matrix = new SparseObjectMatrix1D<String>(16L);
        ObjectMatrix1DWriter<String> writer = createObjectMatrix1DWriter();
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
        ObjectMatrix1D<String> matrix = new SparseObjectMatrix1D<String>(16L);
        ObjectMatrix1DWriter<String> writer = createObjectMatrix1DWriter();
        File file = File.createTempFile("abstractObjectMatrix1DTest", null);
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
        ObjectMatrix1D<String> matrix = new SparseObjectMatrix1D<String>(16L);
        ObjectMatrix1DWriter<String> writer = createObjectMatrix1DWriter();
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
