/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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
package org.dishevelled.matrix.impl;

import org.dishevelled.matrix.Matrix1D;
import org.dishevelled.matrix.Matrix2D;
import org.dishevelled.matrix.Matrix3D;

/**
 * Static utility methods on sparse matrices.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SparseMatrixUtils
{

    /**
     * Private no-arg constructor.
     */
    private SparseMatrixUtils()
    {
        // empty
    }


    /**
     * Create and return a new sparse 1D matrix with the specified size.
     *
     * @param <T> 1D matrix type
     * @param size size, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if <code>size</code> is negative
     * @return a new sparse 1D matrix with the specified size
     */
    public static <T> Matrix1D<T> createSparseMatrix1D(final long size)
    {
        return new SparseMatrix1D<T>(size);
    }

    /**
     * Create and return a new sparse 1D matrix with the specified size,
     * initial capacity, and load factor.
     *
     * @param <T> 1D matrix type
     * @param size size, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @param loadFactor load factor, must be <code>&gt; 0</code>
     * @return a new sparse 1D matrix with the specified size,
     *    initial capacity, and load factor
     */
    public static <T> Matrix1D<T> createSparseMatrix1D(final long size,
                                                       final int initialCapacity,
                                                       final float loadFactor)
    {
        return new SparseMatrix1D<T>(size, initialCapacity, loadFactor);
    }

    /**
     * Create and return a new sparse 2D matrix with the specified number
     * of rows and columns.
     *
     * @param <T> 2D matrix type
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if either <code>rows</code>
     *    or <code>columns</code> is negative
     * @return a new sparse 2D matrix with the specified number
     *    of rows and columns
     */
    public static <T> Matrix2D<T> createSparseMatrix2D(final long rows, final long columns)
    {
        return new SparseMatrix2D<T>(rows, columns);
    }

    /**
     * Create and return a new sparse 2D matrix with the specified number
     * of rows and columns, initial capacity, and load factor.
     *
     * @param <T> 2D matrix type
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @param loadFactor load factor, must be <code>&gt; 0</code>
     * @return a new sparse 2D matrix with the specified number
     *    of rows and columns, initial capacity, and load factor
     */
    public static <T> Matrix2D<T> createSparseMatrix2D(final long rows,
                                                       final long columns,
                                                       final int initialCapacity,
                                                       final float loadFactor)
    {
        return new SparseMatrix2D<T>(rows, columns, initialCapacity, loadFactor);
    }

    /**
     * Create and return a new sparse 3D matrix with the specified number
     * of slices, rows, and columns.
     *
     * @param <T> 3D matrix type
     * @param slices slices, must be <code>&gt;= 0</code>
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if any of <code>slices</code>,
     *    <code>rows</code>, or <code>columns</code> is negative
     * @return a new sparse 3D matrix with the specified number
     *    of slices, rows, and columns
     */
    public static <T> Matrix3D<T> createSparseMatrix3D(final long slices, final long rows, final long columns)
    {
        return new SparseMatrix3D<T>(slices, rows, columns);
    }

    /**
     * Create and return a new sparse 3D matrix with the specified number
     * of slices, rows, and columns, initial capacity, and load factor.
     *
     * @param <T> 3D matrix type
     * @param slices slices, must be <code>&gt;= 0</code>
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @param loadFactor load factor, must be <code>&gt; 0</code>
     * @return a new sparse 3D matrix with the specified number
     *    of slices, rows, and columns, initial capacity, and load factor
     */
    public static <T> Matrix3D<T> createSparseMatrix3D(final long slices,
                                final long rows,
                                final long columns,
                                final int initialCapacity,
                                final float loadFactor)
    {
        return new SparseMatrix3D<T>(slices, rows, columns, initialCapacity, loadFactor);
    }
}
