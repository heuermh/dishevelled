/*

    dsh-matrix-io-nonblocking  Non-blocking sparse matrix readers.
    Copyright (c) 2010-2013 held jointly by the individual authors.

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

import java.io.IOException;
import java.io.File;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.matrix.Matrix1D;

import org.dishevelled.matrix.io.AbstractMatrix1DReaderTest;
import org.dishevelled.matrix.io.Matrix1DReader;

/**
 * Unit test for NonBlockingSparseTextMatrix1DReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NonBlockingSparseTextMatrix1DReaderTest
    extends AbstractMatrix1DReaderTest
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
    protected <T> Matrix1DReader<T> createMatrix1DReader()
    {
        return new NonBlockingSparseTextMatrix1DReader<T>(new UnaryFunction<String, T>()
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
        assertNotNull(new NonBlockingSparseTextMatrix1DReader<String>(PARSER));

        try
        {
            new NonBlockingSparseTextMatrix1DReader<String>(null);
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
            Matrix1DReader<String> reader = new NonBlockingSparseTextMatrix1DReader<String>(PARSER);
            reader.read(getClass().getResource("empty.tsv"));
            fail("empty.tsv url expected IOException");
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
            Matrix1DReader<String> reader = new NonBlockingSparseTextMatrix1DReader<String>(PARSER);
            File empty = File.createTempFile("sparseTextMatrix1DReaderTest", "tsv");
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
        Matrix1DReader<String> reader = new NonBlockingSparseTextMatrix1DReader<String>(PARSER);
        Matrix1D matrix = reader.read(getClass().getResource("empty1d.tsv"));
        assertEquals(0, matrix.size());
        assertEquals(0, matrix.cardinality());
    }

    public void testOneElement() throws IOException
    {
        Matrix1DReader<String> reader = new NonBlockingSparseTextMatrix1DReader<String>(PARSER);
        Matrix1D matrix = reader.read(getClass().getResource("oneElement1d.tsv"));
        assertEquals(1, matrix.size());
        assertEquals(1, matrix.cardinality());
        assertEquals("foo", matrix.get(0));
    }

    public void testTwoElement() throws IOException
    {
        Matrix1DReader<String> reader = new NonBlockingSparseTextMatrix1DReader<String>(PARSER);
        Matrix1D matrix = reader.read(getClass().getResource("twoElement1d.tsv"));
        assertEquals(2, matrix.size());
        assertEquals(2, matrix.cardinality());
        assertEquals("foo", matrix.get(0));
        assertEquals("bar", matrix.get(1));
    }

    public void testTwoElementCardinalityOne() throws IOException
    {
        Matrix1DReader<String> reader = new NonBlockingSparseTextMatrix1DReader<String>(PARSER);
        Matrix1D matrix = reader.read(getClass().getResource("twoElementCardinalityOne1d.tsv"));
        assertEquals(2, matrix.size());
        assertEquals(1, matrix.cardinality());
        assertEquals("foo", matrix.get(0));
        assertEquals(null, matrix.get(1));
    }
}