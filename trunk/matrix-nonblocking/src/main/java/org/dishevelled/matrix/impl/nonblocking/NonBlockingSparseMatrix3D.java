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

import java.util.Map;

//import static org.dishevelled.collect.Maps.*;
import org.dishevelled.collect.Maps;

import org.dishevelled.matrix.impl.SparseMatrix3D;

/**
 * Non-blocking sparse implementation of Matrix3D.
 *
 * @param <E> element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class NonBlockingSparseMatrix3D<E>
    extends SparseMatrix3D<E>
{

    /**
     * Create a new non-blocking sparse 3D matrix with the specified number
     * of slices, rows, and columns, and initial capacity.
     *
     * @param slices slices, must be <code>&gt;= 0</code>
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     */
    public NonBlockingSparseMatrix3D(final long slices,
                                     final long rows,
                                     final long columns,
                                     final int initialCapacity)
    {
        this(slices, rows, columns,
             0L, 0L, 0L, rows * columns, columns, 1L,
             false, Maps.<E>createLongNonBlockingMap(initialCapacity));
    }

    /**
     * Create a new instance of NonBlockingSparseMatrix3D with the specified
     * parameters and map of elements.
     *
     * @param slices slices, must be <code>&gt;= 0</code>
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param sliceZero slice of the first element
     * @param rowZero row of the first element
     * @param columnZero column of the first element
     * @param sliceStride number of slices between two elements
     * @param rowStride number of rows between two elements
     * @param columnStride number of columns between two elements
     * @param isView true if this instance is a view
     * @param elements map of elements
     */
    protected NonBlockingSparseMatrix3D(final long slices,
                                        final long rows,
                                        final long columns,
                                        final long sliceZero,
                                        final long rowZero,
                                        final long columnZero,
                                        final long sliceStride,
                                        final long rowStride,
                                        final long columnStride,
                                        final boolean isView,
                                        final Map<Long, E> elements)
    {
        super(slices, rows, columns,
              sliceZero, rowZero, columnZero,
              sliceStride, rowStride, columnStride, isView, elements);
    }


    /** {@inheritDoc} */
    public Object clone()
    {
        return new NonBlockingSparseMatrix3D<E>(slices(), rows(), columns(),
                                                sliceZero(), rowZero(), columnZero(),
                                                sliceStride(), rowStride(), columnStride(),
                                                isView(), elements());
    }

    // todo:  override to prevent autoboxing where necessary
}