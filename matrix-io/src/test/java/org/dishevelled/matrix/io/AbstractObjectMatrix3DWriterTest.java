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

import org.dishevelled.matrix.ObjectMatrix3D;

import org.dishevelled.matrix.impl.SparseObjectMatrix3D;

/**
 * Abstract unit test for implementations of ObjectMatrix3DWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObjectMatrix3DWriterTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of ObjectMatrix3DWriter to test.
     *
     * @param <E> 3D matrix element type
     * @return a new instance of an implementation of ObjectMatrix3DWriter to test
     */
    protected abstract <E> ObjectMatrix3DWriter<E> createObjectMatrix3DWriter();

    public void testCreateObjectMatrix3DWriter()
    {
        ObjectMatrix3DWriter<String> writer = createObjectMatrix3DWriter();
        assertNotNull(writer);
    }

    public void testAppend() throws IOException
    {
        ObjectMatrix3D<String> matrix = new SparseObjectMatrix3D<String>(16L, 16L, 16L);
        ObjectMatrix3DWriter<String> writer = createObjectMatrix3DWriter();
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
