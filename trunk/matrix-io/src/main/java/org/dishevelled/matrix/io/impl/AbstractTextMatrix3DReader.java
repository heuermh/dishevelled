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

import org.dishevelled.matrix.Matrix3D;

/**
 * Abstract tab-delimited text reader for matrices of objects in three dimensions.
 * The first line should be the size of the matrix as <code>slices\trows\tcolumns\tcardinality</code>
 * and each additional line should be formatted as <code>slice\trow\tcolumn\tvalue</code>.
 *
 * @param <E> 3D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTextMatrix3DReader<E>
    extends AbstractMatrix3DReader<E>
{

    /** {@inheritDoc} */
    public final Matrix3D<E> read(final InputStream inputStream) throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException ("inputStream must not be null");
        }
        int lineNumber = 0;
        BufferedReader reader = null;
        Matrix3D<E> matrix = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready())
            {
                String[] tokens = reader.readLine().split("\t");
                if (matrix == null)
                {
                    long slices = Long.parseLong(tokens[0]);
                    long rows = Long.parseLong(tokens[1]);
                    long columns = Long.parseLong(tokens[2]);
                    int cardinality = Integer.parseInt(tokens[3]);
                    matrix = createMatrix3D(slices, rows, columns, cardinality);
                }
                else
                {
                    long slice = Long.parseLong(tokens[0]);
                    long row = Long.parseLong(tokens[1]);
                    long column = Long.parseLong(tokens[2]);
                    E e = parse(tokens[3]);
                    matrix.set(slice, row, column, e);
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
            throw new IOException("could not create create matrix, first line should contain rows/\tcolumns/\tcardinality");
        }
        return matrix;
    }
}
