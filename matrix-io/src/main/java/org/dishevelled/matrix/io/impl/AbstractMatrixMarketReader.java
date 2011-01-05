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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dishevelled.matrix.Matrix2D;

/**
 * Abstract Matrix Market format reader for matrices of doubles in two dimensions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractMatrixMarketReader
    extends AbstractMatrix2DReader<Double>
{
    /** Map of reader strategies keyed by name. */
    private static final Map<String, ReaderStrategy> STRATEGIES;

    /** Pattern to match Matrix Market header line. */
    private static final Pattern HEADER = Pattern.compile("^%%MatrixMarket matrix\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*$");

    static
    {
        STRATEGIES = new HashMap<String, ReaderStrategy>();
        STRATEGIES.put("general", new GeneralReaderStrategy());
        STRATEGIES.put("symmetric", new SymmetricReaderStrategy());
        STRATEGIES.put("skew-symmetric", new SkewSymmetricReaderStrategy());
        // TODO:  hermitian might only be valid for complex matrices
        STRATEGIES.put("hermitian", new HermitianReaderStrategy());
    }

    /**
     * {@inheritDoc}
     *
     * Not used in this implementation, parse functionality is provided by reader strategy.
     */
    protected Double parse(final String value)
    {
        return null;
    }

    /** {@inheritDoc} */
    public final Matrix2D<Double> read(final InputStream inputStream) throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException ("inputStream must not be null");
        }
        int lineNumber = 0;
        BufferedReader reader = null;
        ReaderStrategy readerStrategy = null;
        Matrix2D<Double> matrix = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready())
            {
                String line = reader.readLine();
                if (line.startsWith("%"))
                {
                    Matcher m = HEADER.matcher(line);
                    if (m.matches())
                    {
                        String format = m.group(1); // coordinate, array
                        if (!("coordinate".equals(format)))
                        {
                            throw new IOException("header line format must be coordinate, was " + format);
                        }
                        String type = m.group(2); // real, complex, integer, pattern
                        if (!("real".equals(type) || "integer".equals(type)))
                        {
                            throw new IOException("header line type must be real or integer, was " + type);
                        }
                        String symmetryStructure = m.group(3); // general, symmetric, skew-symmetric, hermitian
                        readerStrategy = STRATEGIES.get(symmetryStructure);
                        if (readerStrategy == null)
                        {
                            throw new IOException("header line symmetry structure must be one of:  general, symmetric,"
                                                  + "skew-symmetric, or hermitian; was " + symmetryStructure);
                        }
                    }
                }
                else
                {
                    String[] tokens = line.split("\\s+");
                    if (matrix == null)
                    {
                        long rows = Long.parseLong(tokens[0]);
                        long columns = Long.parseLong(tokens[1]);
                        int entries = Integer.parseInt(tokens[2]);
                        if (readerStrategy == null)
                        {
                            throw new IOException("read matrix size definition at line number " + lineNumber
                                                  + " before reading header line");
                        }
                        int cardinality = readerStrategy.cardinality(entries);
                        matrix = createMatrix2D(rows, columns, cardinality);
                    }
                    else
                    {
                        long row = Long.parseLong(tokens[0]);
                        long column = Long.parseLong(tokens[1]);
                        double value = Double.parseDouble(tokens[2]);
                        if (readerStrategy == null)
                        {
                            throw new IOException("read values at line number " + lineNumber
                                                  + " before reading header line");
                        }
                        // note:  indices in the file are 1-based
                        readerStrategy.read(matrix, row - 1, column - 1, value);
                    }
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
            throw new IOException("could not create create matrix, check header and first non-comment line");
        }
        return matrix;
    }

    /**
     * Strategy for handling symmetry structure.
     */
    interface ReaderStrategy
    {

        /**
         * Return the approximate cardinality of the matrix to create for the specified number
         * of entries in the file.
         *
         * @param entries number of entries in the file
         * @return the approximate cardinality of the matrix to create for the specified number
         *    of entries in the file
         */
        int cardinality(int entries);

        /**
         * Notify this reader strategy that the specified value was read for the specified
         * matrix at the specified coordinates.
         *
         * @param matrix matrix
         * @param row row
         * @param column column
         * @param value value
         */
        void read(Matrix2D<Double> matrix, long row, long column, double value);
    }

    /**
     * Reader strategy for <code>general</code> symmetry structure.
     */
    private static class GeneralReaderStrategy implements ReaderStrategy
    {
        /** {@inheritDoc} */
        public int cardinality(final int entries)
        {
            return entries;
        }

        /** {@inheritDoc} */
        public void read(final Matrix2D<Double> matrix, final long row, final long column, final double value)
        {
            matrix.set(row, column, value);
        }
    }

    /**
     * Reader strategy for <code>symmetric</code> symmetry structure.
     */
    private static class SymmetricReaderStrategy implements ReaderStrategy
    {
        /** {@inheritDoc} */
        public int cardinality(final int entries)
        {
            return (2 * entries);
        }

        /** {@inheritDoc} */
        public void read(final Matrix2D<Double> matrix, final long row, final long column, final double value)
        {
            // TODO:  what if an upper-right value is specified?
            matrix.set(row, column, value);
            // only set values on the diagonal once
            if (row != column)
            {
                matrix.set(column, row, value);
            }
        }
    }

    /**
     * Reader strategy for <code>skew-symmetric</code> symmetry structure.
     */
    private static class SkewSymmetricReaderStrategy implements ReaderStrategy
    {
        /** {@inheritDoc} */
        public int cardinality(final int entries)
        {
            return (2 * entries);
        }

        /** {@inheritDoc} */
        public void read(final Matrix2D<Double> matrix, final long row, final long column, final double value)
        {
            // TODO:  what if an upper-right value is specified?
            // diagonal entries are always zero
            if (row != column)
            {
                // lower-left values should be specified
                matrix.set(row, column, value);
                // upper-right values are inverse
                matrix.set(column, row, -1.0d * value);
            }
        }
    }

    /**
     * Reader strategy for <code>hermitian</code> symmetry structure.
     */
    private static class HermitianReaderStrategy implements ReaderStrategy
    {
        /** {@inheritDoc} */
        public int cardinality(final int entries)
        {
            return entries;
        }

        /** {@inheritDoc} */
        public void read(final Matrix2D<Double> matrix, final long row, final long column, final double value)
        {
            matrix.set(row, column, value);
            matrix.set(column, row, value);
        }
    }
}
