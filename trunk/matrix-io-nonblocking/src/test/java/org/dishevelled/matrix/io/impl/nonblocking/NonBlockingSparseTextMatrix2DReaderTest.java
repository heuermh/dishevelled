/*

    dsh-matrix-io-nonblocking  Non-blocking sparse matrix readers.
    Copyright (c) 2010-2012 held jointly by the individual authors.

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

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.io.AbstractMatrix2DReaderTest;
import org.dishevelled.matrix.io.Matrix2DReader;

/**
 * Unit test for NonBlockingSparseTextMatrix2DReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NonBlockingSparseTextMatrix2DReaderTest
    extends AbstractMatrix2DReaderTest
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
    protected <T> Matrix2DReader<T> createMatrix2DReader()
    {
        return new NonBlockingSparseTextMatrix2DReader<T>(new UnaryFunction<String, T>()
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
        assertNotNull(new NonBlockingSparseTextMatrix2DReader<String>(PARSER));

        try
        {
            new NonBlockingSparseTextMatrix2DReader<String>(null);
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
            Matrix2DReader<String> reader = new NonBlockingSparseTextMatrix2DReader<String>(PARSER);
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
            Matrix2DReader<String> reader = new NonBlockingSparseTextMatrix2DReader<String>(PARSER);
            File empty = File.createTempFile("sparseTextMatrix2DReaderTest", "tsv");
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
        Matrix2DReader<String> reader = new NonBlockingSparseTextMatrix2DReader<String>(PARSER);
        Matrix2D matrix = reader.read(getClass().getResource("empty2d.tsv"));
        assertEquals(0, matrix.rows());
        assertEquals(0, matrix.columns());
        assertEquals(0, matrix.cardinality());
    }

    public void testOneElement() throws IOException
    {
        Matrix2DReader<String> reader = new NonBlockingSparseTextMatrix2DReader<String>(PARSER);
        Matrix2D matrix = reader.read(getClass().getResource("oneElement2d.tsv"));
        assertEquals(1, matrix.rows());
        assertEquals(1, matrix.columns());
        assertEquals(1, matrix.cardinality());
        assertEquals("foo", matrix.get(0, 0));
    }

    public void testTwoElement() throws IOException
    {
        Matrix2DReader<String> reader = new NonBlockingSparseTextMatrix2DReader<String>(PARSER);
        Matrix2D matrix = reader.read(getClass().getResource("twoElement2d.tsv"));
        assertEquals(2, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals("foo", matrix.get(0, 0));
        assertEquals("bar", matrix.get(1, 1));
    }

    public void testTwoElementCardinalityOne() throws IOException
    {
        Matrix2DReader<String> reader = new NonBlockingSparseTextMatrix2DReader<String>(PARSER);
        Matrix2D matrix = reader.read(getClass().getResource("twoElementCardinalityOne2d.tsv"));
        assertEquals(2, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(1, matrix.cardinality());
        assertEquals("foo", matrix.get(0, 0));
        assertEquals(null, matrix.get(1, 0));
        assertEquals(null, matrix.get(0, 1));
        assertEquals(null, matrix.get(1, 1));
    }
}
