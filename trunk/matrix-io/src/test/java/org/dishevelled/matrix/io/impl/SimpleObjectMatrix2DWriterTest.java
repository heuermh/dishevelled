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

import java.io.IOException;

import org.dishevelled.matrix.ObjectMatrix2D;

import org.dishevelled.matrix.impl.SparseObjectMatrix2D;

import org.dishevelled.matrix.io.AbstractObjectMatrix2DWriterTest;
import org.dishevelled.matrix.io.ObjectMatrix2DWriter;

/**
 * Unit test for SimpleObjectMatrix2DWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SimpleObjectMatrix2DWriterTest
    extends AbstractObjectMatrix2DWriterTest
{

    /** {@inheritDoc} */
    protected <E> ObjectMatrix2DWriter<E> createObjectMatrix2DWriter()
    {
        return new SimpleObjectMatrix2DWriter<E>();
    }

    public void testEmptyMatrix() throws IOException
    {
        ObjectMatrix2D<String> matrix = new SparseObjectMatrix2D(0L, 0L);
        ObjectMatrix2DWriter<String> writer = new SimpleObjectMatrix2DWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("[]", appendable.toString());
    }

    public void testOneElementMatrix() throws IOException
    {
        ObjectMatrix2D<String> matrix = new SparseObjectMatrix2D(1L, 1L);
        matrix.setQuick(0L, 0L, "foo");
        ObjectMatrix2DWriter<String> writer = new SimpleObjectMatrix2DWriter();
        StringBuffer appendable = new StringBuffer();
        appendable = writer.append(matrix, appendable);
        assertEquals("[foo]", appendable.toString());
    }
}