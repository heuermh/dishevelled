/*

    dsh-matrix-nonblocking  Non-blocking matrix implementations.
    Copyright (c) 2010-2012 held jointly by the individual authors.

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

import org.dishevelled.matrix.impl.SparseMatrix2D;

/**
 * Non-blocking sparse implementation of Matrix2D.
 *
 * @param <E> element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class NonBlockingSparseMatrix2D<E>
    extends SparseMatrix2D<E>
{

    /**
     * Create a new non-blocking sparse 2D matrix with the specified number
     * of rows and columns and initial capacity.
     *
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     */
    public NonBlockingSparseMatrix2D(final long rows, final long columns, final int initialCapacity)
    {
        this(rows, columns, 0L, 0L, columns, 1L, false, Maps.<E>createLongNonBlockingMap(initialCapacity));
    }

    /**
     * Create a new instance of NonBlockingSparseMatrix2D with the specified
     * parameters and map of elements.
     *
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param rowZero row of the first element
     * @param columnZero column of the first element
     * @param rowStride number of rows between two elements
     * @param columnStride number of columns between two elements
     * @param isView true if this instance is a view
     * @param elements map of elements
     */
    protected NonBlockingSparseMatrix2D(final long rows,
                                        final long columns,
                                        final long rowZero,
                                        final long columnZero,
                                        final long rowStride,
                                        final long columnStride,
                                        final boolean isView,
                                        final Map<Long, E> elements)
    {
        super(rows, columns,
              rowZero, columnZero,
              rowStride, columnStride,
              isView, elements);
    }


    /** {@inheritDoc} */
    public Object clone()
    {
        return new NonBlockingSparseMatrix2D<E>(rows(), columns(),
                                                rowZero(), columnZero(),
                                                rowStride(), columnStride(),
                                                isView(), elements());
    }

    // todo:  override to prevent autoboxing where necessary
}