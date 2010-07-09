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
package org.dishevelled.matrix.io.impl;

import java.io.IOException;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.impl.SparseMatrix2D;

import org.dishevelled.matrix.io.AbstractMatrix2DWriterTest;
import org.dishevelled.matrix.io.Matrix2DWriter;

/**
 * Unit test for TextMatrix2DWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class TextMatrix2DWriterTest
    extends AbstractMatrix2DWriterTest
{

    /** {@inheritDoc} */
    protected <E> Matrix2DWriter<E> createMatrix2DWriter()
    {
        return new TextMatrix2DWriter<E>();
    }

    public void testEmptyMatrix() throws IOException
    {
        Matrix2D<String> matrix = new SparseMatrix2D(0L, 0L);
        Matrix2DWriter<String> writer = createMatrix2DWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("0\t0\t0\n", appendable.toString());
    }

    public void testOneElementMatrix() throws IOException
    {
        Matrix2D<String> matrix = new SparseMatrix2D(1L, 1L);
        matrix.setQuick(0L, 0L, "foo");
        Matrix2DWriter<String> writer = createMatrix2DWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("1\t1\t1\n0\t0\tfoo\n", appendable.toString());
    }

    public void testTwoElementMatrix() throws IOException
    {
        Matrix2D<String> matrix = new SparseMatrix2D(2L, 2L);
        matrix.setQuick(0L, 0L, "foo");
        matrix.setQuick(0L, 1L, "bar");
        matrix.setQuick(1L, 0L, "baz");
        matrix.setQuick(1L, 1L, "qux");
        Matrix2DWriter<String> writer = createMatrix2DWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("2\t2\t4\n0\t0\tfoo\n0\t1\tbar\n1\t0\tbaz\n1\t1\tqux\n", appendable.toString());
    }

    public void testTwoElementMatrixCardinalityOne() throws IOException
    {
        Matrix2D<String> matrix = new SparseMatrix2D(2L, 2L);
        matrix.setQuick(1L, 1L, "qux");
        Matrix2DWriter<String> writer = createMatrix2DWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("2\t2\t1\n1\t1\tqux\n", appendable.toString());
    }

    public void testOneElementMatrixWithNull() throws IOException
    {
        Matrix2D<String> matrix = new SparseMatrix2D(2L, 2L);
        matrix.setQuick(0L, 0L, null);
        matrix.setQuick(1L, 1L, "qux");
        Matrix2DWriter<String> writer = createMatrix2DWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("2\t2\t1\n1\t1\tqux\n", appendable.toString());
    }
}
