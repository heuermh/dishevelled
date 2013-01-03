/*

    dsh-matrix-nonblocking  Non-blocking matrix implementations.
    Copyright (c) 2010-2013 held jointly by the individual authors.

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
package org.dishevelled.matrix.impl.nonblocking;

import org.dishevelled.matrix.Matrix1D;
import org.dishevelled.matrix.Matrix2D;
import org.dishevelled.matrix.Matrix3D;

/**
 * Static utility methods on non-blocking matrices.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NonBlockingMatrixUtils
{

    /**
     * Private no-arg constructor.
     */
    private NonBlockingMatrixUtils()
    {
        // empty
    }


    /**
     * Create and return a new non-blocking sparse 1D matrix with the specified size
     * and initial capacity.
     *
     * @param <T> element type
     * @param size size, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @return a new non-blocking sparse 1D matrix with the specified size
     *    and initial capacity
     */
    public static <T> Matrix1D<T> createNonBlockingSparseMatrix1D(final long size,
                                                                  final int initialCapacity)
    {
        return new NonBlockingSparseMatrix1D<T>(size, initialCapacity);
    }

    /**
     * Create and return a new non-blocking sparse 2D matrix with the specified number
     * of rows and columns and initial capacity.
     *
     * @param <T> element type
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @return a new non-blocking sparse 2D matrix with the specified number
     *    of rows and columns and initial capacity
     */
    public static <T> Matrix2D<T> createNonBlockingSparseMatrix2D(final long rows,
                                                                  final long columns,
                                                                  final int initialCapacity)
    {
        return new NonBlockingSparseMatrix2D<T>(rows, columns, initialCapacity);
    }

    /**
     * Create and return a new non-blocking sparse 3D matrix with the specified number
     * of slices, rows, and columns, and initial capacity.
     *
     * @param <T> element type
     * @param slices slices, must be <code>&gt;= 0</code>
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @return a new non-blocking sparse 3D matrix with the specified number
     *    of slices, rows, and columns, and initial capacity
     */
    public static <T> Matrix3D<T> createNonBlockingSparseMatrix3D(final long slices,
                                                                  final long rows,
                                                                  final long columns,
                                                                  final int initialCapacity)
    {
        return new NonBlockingSparseMatrix3D<T>(slices, rows, columns, initialCapacity);
    }
}