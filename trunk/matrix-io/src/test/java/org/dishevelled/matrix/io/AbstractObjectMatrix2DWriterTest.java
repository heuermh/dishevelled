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

import java.io.IOException;

import junit.framework.TestCase;

import org.dishevelled.matrix.ObjectMatrix2D;

import org.dishevelled.matrix.impl.SparseObjectMatrix2D;

/**
 * Abstract unit test for implementations of ObjectMatrix2DWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObjectMatrix2DWriterTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of ObjectMatrix2DWriter to test.
     *
     * @param <E> 2D matrix element type
     * @return a new instance of an implementation of ObjectMatrix2DWriter to test
     */
    protected abstract <E> ObjectMatrix2DWriter<E> createObjectMatrix2DWriter();

    public void testCreateObjectMatrix2DWriter()
    {
        ObjectMatrix2DWriter<String> writer = createObjectMatrix2DWriter();
        assertNotNull(writer);
    }

    public void testAppend() throws IOException
    {
        ObjectMatrix2D<String> matrix = new SparseObjectMatrix2D<String>(16L, 16L);
        ObjectMatrix2DWriter<String> writer = createObjectMatrix2DWriter();
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
}
