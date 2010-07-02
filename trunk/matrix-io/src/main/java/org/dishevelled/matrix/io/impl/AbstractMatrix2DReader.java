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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.io.Matrix2DReader;

/**
 * Abstract reader for matrices of objects in two dimensions.
 *
 * @param <E> 2D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractMatrix2DReader<E>
    implements Matrix2DReader<E>
{

    /**
     * Parse the specified value to an instance of type <code>E</code>.
     *
     * @param value value to parse
     * @return an instance of type <code>E</code>
     * @throws IOException if an IO error occurs
     */
    protected abstract E parse(String value) throws IOException;

    /**
     * Create and return a new instance of an implementation of Matrix2D.
     *
     * @param rows number of rows
     * @param columns number of columns
     * @param cardinality approximate cardinality
     * @return a new instance of an implementation of Matrix2D
     */
    protected abstract Matrix2D<E> createMatrix2D(long rows, long columns, int cardinality);

    /** {@inheritDoc} */
    public final Matrix2D<E> read(final File file) throws IOException
    {
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);
            return read(inputStream);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            MatrixIOUtils.closeQuietly(inputStream);
        }
    }

    /** {@inheritDoc} */
    public final Matrix2D<E> read(final URL url) throws IOException
    {
        if (url == null)
        {
            throw new IllegalArgumentException("url must not be null");
        }
        InputStream inputStream = null;
        try
        {
            inputStream = url.openStream();
            return read(inputStream);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            MatrixIOUtils.closeQuietly(inputStream);
        }
    }
}