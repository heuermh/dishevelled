/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2011 held jointly by the individual authors.

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

import org.dishevelled.matrix.Matrix1D;

import org.dishevelled.matrix.impl.SparseMatrix1D;

import org.dishevelled.matrix.io.AbstractMatrix1DWriterTest;
import org.dishevelled.matrix.io.Matrix1DWriter;

/**
 * Unit test for ValuesMatrix1DWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ValuesMatrix1DWriterTest
    extends AbstractMatrix1DWriterTest
{

    /** {@inheritDoc} */
    protected <E> Matrix1DWriter<E> createMatrix1DWriter()
    {
        return new ValuesMatrix1DWriter<E>();
    }

    public void testEmptyMatrix() throws IOException
    {
        Matrix1D<String> matrix = new SparseMatrix1D<String>(0L);
        Matrix1DWriter<String> writer = new ValuesMatrix1DWriter<String>();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("[]", appendable.toString());
    }

    public void testOneElementMatrix() throws IOException
    {
        Matrix1D<String> matrix = new SparseMatrix1D<String>(1L);
        matrix.setQuick(0L, "foo");
        Matrix1DWriter<String> writer = new ValuesMatrix1DWriter<String>();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("[foo]", appendable.toString());
    }

    public void testTwoElementMatrix() throws IOException
    {
        Matrix1D<String> matrix = new SparseMatrix1D<String>(2L);
        matrix.setQuick(0L, "foo");
        matrix.setQuick(1L, "bar");
        Matrix1DWriter<String> writer = new ValuesMatrix1DWriter<String>();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("[foo,bar]", appendable.toString());
    }
}