/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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

import org.dishevelled.functor.TernaryPredicate;
import org.dishevelled.functor.TernaryProcedure;

import org.dishevelled.matrix.Matrix2D;

/**
 * Matrix Market format writer for matrices of numbers in two dimensions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class MatrixMarketWriter
    extends AbstractMatrix2DWriter<Number>
{
    // TODO:
    // matrix type (real, complex, integer, pattern) should be {specifiable, automatic}
    // allow for specifying symmetry structure (general, symmetric, skew-symmetric, Hermitian)
    // allow for specifying the number format to use when writing values

    /** Not null predicate. */
    private static final TernaryPredicate<Long, Long, Object> NOT_NULL = new TernaryPredicate<Long, Long, Object>()
        {
            /** {@inheritDoc} */
            public boolean test(final Long row, final Long column, final Object value)
            {
                return (value != null);
            }
        };


    /** {@inheritDoc} */
    public <T extends Appendable> T append(final Matrix2D<? extends Number> matrix, final T appendable)
        throws IOException
    {
        if (matrix == null)
        {
            throw new IllegalArgumentException("matrix must not be null");
        }
        if (appendable == null)
        {
            throw new IllegalArgumentException("appendable must not be null");
        }
        // append Matrix Market header
        appendable.append("%%MatrixMarket matrix coordinate real general\n");
        appendable.append("%\n% Note indices are 1-based.\n%\n");

        // append coordinate format dimensions
        appendable.append(String.valueOf(matrix.rows()));
        appendable.append("\t");
        appendable.append(String.valueOf(matrix.columns()));
        appendable.append("\t");
        appendable.append(String.valueOf(matrix.cardinality()));
        appendable.append("\n");

        // append non-null values
        matrix.forEach(NOT_NULL, new TernaryProcedure<Long, Long, Object>()
            {
                /** {@inheritDoc} */
                public void run(final Long row, final Long column, final Object value)
                {
                    // note:  indices are 1-based
                    try
                    {
                        appendable.append(String.valueOf(row + 1L));
                        appendable.append("\t");
                        appendable.append(String.valueOf(column + 1L));
                        appendable.append("\t");
                        appendable.append(MatrixIOUtils.toCharSequence(value));
                        appendable.append("\n");
                    }
                    catch (IOException e)
                    {
                        // TODO:  have to eat this IOException, unfortunately
                    }
                }
            });

        return appendable;
    }
}