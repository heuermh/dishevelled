/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.net.URL;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of Matrix3DReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractMatrix3DReaderTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of Matrix3DReader to test.
     *
     * @return a new instance of an implementation of Matrix3DReader to test
     */
    protected abstract <T> Matrix3DReader<T> createMatrix3DReader();


    public void testCreateMatrix3DReader()
    {
        Matrix3DReader<String> reader = createMatrix3DReader();
        assertNotNull(reader);
    }

    public void testReadFile() throws IOException
    {
        Matrix3DReader<String> reader = createMatrix3DReader();
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
        Matrix3DReader<String> reader = createMatrix3DReader();
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

    public void testReadInputStream() throws IOException
    {
        Matrix3DReader<String> reader = createMatrix3DReader();
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
}