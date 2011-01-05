/*

    dsh-matrix-io-nonblocking  Non-blocking sparse matrix readers.
    Copyright (c) 2010-2011 held jointly by the individual authors.

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
package org.dishevelled.matrix.io.impl.nonblocking;

import java.io.File;
import java.io.IOException;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.matrix.Matrix3D;

import org.dishevelled.matrix.io.AbstractMatrix3DReaderTest;
import org.dishevelled.matrix.io.Matrix3DReader;

/**
 * Unit test for NonBlockingSparseTextMatrix3DReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NonBlockingSparseTextMatrix3DReaderTest
    extends AbstractMatrix3DReaderTest
{
    /** String parser. */
    private static UnaryFunction<String, String> PARSER = new UnaryFunction<String, String>()
    {
        /** {@inheritDoc} */
        public String evaluate(final String value)
        {
            return value;
        }
    };

    /** {@inheritDoc} */
    protected <T> Matrix3DReader<T> createMatrix3DReader()
    {
        return new NonBlockingSparseTextMatrix3DReader<T>(new UnaryFunction<String, T>()
            {
                /** {@inheritDoc} */
                public T evaluate(final String value)
                {
                    return null;
                }
            });
    }

    public void testConstructor()
    {
        assertNotNull(new NonBlockingSparseTextMatrix3DReader<String>(PARSER));

        try
        {
            new NonBlockingSparseTextMatrix3DReader<String>(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testEmptyURL() throws IOException
    {
        try
        {
            Matrix3DReader<String> reader = new NonBlockingSparseTextMatrix3DReader<String>(PARSER);
            reader.read(getClass().getResource("empty.tsv"));
            fail("empty.tsv expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
    }

    public void testEmptyFile() throws IOException
    {
        try
        {
            Matrix3DReader<String> reader = new NonBlockingSparseTextMatrix3DReader<String>(PARSER);
            File empty = File.createTempFile("sparseTextMatrix3DReaderTest", "tsv");
            reader.read(empty);
            fail("empty.tsv file expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
    }

    public void testEmptyMatrix() throws IOException
    {
        Matrix3DReader<String> reader = new NonBlockingSparseTextMatrix3DReader<String>(PARSER);
        Matrix3D matrix = reader.read(getClass().getResource("empty3d.tsv"));
        assertEquals(0, matrix.slices());
        assertEquals(0, matrix.rows());
        assertEquals(0, matrix.columns());
        assertEquals(0, matrix.cardinality());
    }

    public void testOneElement() throws IOException
    {
        Matrix3DReader<String> reader = new NonBlockingSparseTextMatrix3DReader<String>(PARSER);
        Matrix3D matrix = reader.read(getClass().getResource("oneElement3d.tsv"));
        assertEquals(1, matrix.slices());
        assertEquals(1, matrix.rows());
        assertEquals(1, matrix.columns());
        assertEquals(1, matrix.cardinality());
        assertEquals("foo", matrix.get(0, 0, 0));
    }

    public void testTwentySevenElement() throws IOException
    {
        Matrix3DReader<String> reader = new NonBlockingSparseTextMatrix3DReader<String>(PARSER);
        Matrix3D matrix = reader.read(getClass().getResource("twentySevenElement3d.tsv"));
        assertEquals(3, matrix.slices());
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(27, matrix.cardinality());
        assertEquals("foo", matrix.get(0, 0, 0));
        assertEquals("foo", matrix.get(1, 0, 0));
        assertEquals("foo", matrix.get(2, 0, 0));
        assertEquals("foo", matrix.get(0, 1, 0));
        assertEquals("foo", matrix.get(1, 1, 0));
        assertEquals("foo", matrix.get(2, 1, 0));
        assertEquals("foo", matrix.get(0, 2, 0));
        assertEquals("foo", matrix.get(1, 2, 0));
        assertEquals("foo", matrix.get(2, 2, 0));

        assertEquals("bar", matrix.get(0, 0, 1));
        assertEquals("bar", matrix.get(1, 0, 1));
        assertEquals("bar", matrix.get(2, 0, 1));
        assertEquals("bar", matrix.get(0, 1, 1));
        assertEquals("bar", matrix.get(1, 1, 1));
        assertEquals("bar", matrix.get(2, 1, 1));
        assertEquals("bar", matrix.get(0, 2, 1));
        assertEquals("bar", matrix.get(1, 2, 1));
        assertEquals("bar", matrix.get(2, 2, 1));

        assertEquals("baz", matrix.get(0, 0, 2));
        assertEquals("baz", matrix.get(1, 0, 2));
        assertEquals("baz", matrix.get(2, 0, 2));
        assertEquals("baz", matrix.get(0, 1, 2));
        assertEquals("baz", matrix.get(1, 1, 2));
        assertEquals("baz", matrix.get(2, 1, 2));
        assertEquals("baz", matrix.get(0, 2, 2));
        assertEquals("baz", matrix.get(1, 2, 2));
        assertEquals("baz", matrix.get(2, 2, 2));

    }

    public void testTwentySevenElementCardinalityOne() throws IOException
    {
        Matrix3DReader<String> reader = new NonBlockingSparseTextMatrix3DReader<String>(PARSER);
        Matrix3D matrix = reader.read(getClass().getResource("twentySevenElementCardinalityOne3d.tsv"));
        assertEquals(3, matrix.slices());
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(1, matrix.cardinality());
        assertEquals("foo", matrix.get(0, 0, 0));
        assertEquals(null, matrix.get(1, 0, 0));
        assertEquals(null, matrix.get(0, 1, 0));
        assertEquals(null, matrix.get(1, 1, 0));
    }
}