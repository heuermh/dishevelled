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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

import org.dishevelled.matrix.ObjectMatrix2D;

import org.dishevelled.matrix.impl.SparseObjectMatrix2D;

import org.dishevelled.matrix.io.ObjectMatrix2DReader;

/**
 * Matrix Market format reader for sparse matrices of doubles in two dimensions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class MatrixMarketReader
    implements ObjectMatrix2DReader<Double>
{

    /** {@inheritDoc} */
    public ObjectMatrix2D<Double> read(final File file) throws IOException
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
            closeQuietly(inputStream);
        }
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<Double> read(final URL url) throws IOException
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
            closeQuietly(inputStream);
        }
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<Double> read(final InputStream inputStream) throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException ("inputStream must not be null");
        }
        int lineNumber = 0;
        BufferedReader reader = null;
        ObjectMatrix2D<Double> matrix = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready())
            {
                String line = reader.readLine();
                // TODO:  parse Matrix Market header values
                if (!line.startsWith("%"))
                {
                    String[] tokens = line.split("\\s+");
                    if (matrix == null)
                    {
                        long rows = Long.parseLong(tokens[0]);
                        long columns = Long.parseLong(tokens[1]);
                        int cardinality = Integer.parseInt(tokens[2]);
                        matrix = new SparseObjectMatrix2D<Double>(rows, columns, cardinality, 0.75f);
                    }
                    else
                    {
                        long row = Long.parseLong(tokens[0]);
                        long column = Long.parseLong(tokens[1]);
                        double value = Double.parseDouble(tokens[2]);
                        // note:  indices in the file are 1-based
                        matrix.set(row - 1, column - 1, value);
                    }
                }
                lineNumber++;
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException("caught NumberFormatException at line number " + lineNumber, e);
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new IOException("caught IndexOutOfBoundsException at line number " + lineNumber, e);
        }
        finally
        {
            closeQuietly(reader);
        }
        if (matrix == null)
        {
            throw new IOException("could not create create matrix, check header and first non-comment line");
        }
        return matrix;
    }

    /**
     * Close the specified input stream quietly.
     *
     * @param inputStream input stream to close
     */
    private void closeQuietly(final InputStream inputStream)
    {
        try
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        catch (IOException e)
        {
            // ignore
        }
    }

    /**
     * Close the specified reader quietly.
     *
     * @param reader reader to close
     */
    private void closeQuietly(final Reader reader)
    {
        try
        {
            if (reader != null)
            {
                reader.close();
            }
        }
        catch (IOException e)
        {
            // ignore
        }
    }
}