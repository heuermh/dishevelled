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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.dishevelled.matrix.Matrix1D;

/**
 * Abstract tab-delimited text reader for matrices of objects in one dimension.
 * The first line should be the size of the matrix as <code>size\tcardinality</code>
 * and each additional line should be formatted as <code>index\tvalue</code>.
 *
 * @param <E> 1D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTextMatrix1DReader<E>
    extends AbstractMatrix1DReader<E>
{

    /** {@inheritDoc} */
    public final Matrix1D<E> read(final InputStream inputStream) throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException ("inputStream must not be null");
        }
        int lineNumber = 0;
        BufferedReader reader = null;
        Matrix1D<E> matrix = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready())
            {
                String[] tokens = reader.readLine().split("\t");
                if (matrix == null)
                {
                    long size = Long.parseLong(tokens[0]);
                    int cardinality = Integer.parseInt(tokens[1]);
                    matrix = createMatrix1D(size, cardinality);
                }
                else
                {
                    long index = Long.parseLong(tokens[0]);
                    E e = parse(tokens[1]);
                    matrix.set(index, e);
                }
                lineNumber++;
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException("caught NumberFormatException at line number " + lineNumber
                                  + "\n" + e.getMessage());
            // jdk 1.6+
            //throw new IOException("caught NumberFormatException at line number " + lineNumber, e);
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new IOException("caught IndexOutOfBoundsException at line number " + lineNumber
                                  + "\n" + e.getMessage());
            // jdk 1.6+
            //throw new IOException("caught IndexOutOfBoundsException at line number " + lineNumber, e);
        }
        finally
        {
            MatrixIOUtils.closeQuietly(reader);
        }
        if (matrix == null)
        {
            throw new IOException("could not create create matrix, first line should contain size/\tcardinality");
        }
        return matrix;
    }
}
