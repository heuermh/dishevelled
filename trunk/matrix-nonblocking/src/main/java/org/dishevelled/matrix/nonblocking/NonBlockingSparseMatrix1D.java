/*

    dsh-matrix-nonblocking  Non-blocking matrix implementations.
    Copyright (c) 2010 held jointly by the individual authors.

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
package org.dishevelled.matrix.nonblocking;

import java.util.Map;

//import static org.dishevelled.collect.Maps.*;
import org.dishevelled.collect.Maps;

import org.dishevelled.matrix.impl.SparseMatrix1D;

/**
 * Non-blocking sparse implementation of Matrix1D.
 *
 * @param <E> element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class NonBlockingSparseMatrix1D<E>
    extends SparseMatrix1D<E>
{

    /**
     * Create a new non-blocking sparse 1D matrix with the specified size
     * and initial capacity.
     *
     * @param size size, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     */
    public NonBlockingSparseMatrix1D(final long size, final int initialCapacity)
    {
        this(size, 0L, 1L, false, Maps.<E>createLongNonBlockingMap(initialCapacity));
    }

    /**
     * Create a new instance of NonBlockingSparseMatrix1D with the specified
     * parameters and map of elements.
     *
     * @param size size, must be <code>&gt;= 0</code>
     * @param zero index of the first element
     * @param stride number of indices between any two elements
     * @param isView true if this instance is a view
     * @param elements map of elements
     */
    protected NonBlockingSparseMatrix1D(final long size,
                                        final long zero,
                                        final long stride,
                                        final boolean isView,
                                        final Map<Long, E> elements)
    {
        super(size, zero, stride, isView, elements);
    }


    /** {@inheritDoc} */
    public Object clone()
    {
        return new NonBlockingSparseMatrix1D<E>(size(), zero(), stride(), isView(), elements());
    }

    // todo:  override to prevent autoboxing where necessary
}