/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2012 held jointly by the individual authors.

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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.QuaternaryPredicate;
import org.dishevelled.functor.QuaternaryProcedure;

import org.dishevelled.matrix.BitMatrix3D;
import org.dishevelled.matrix.Matrix2D;
import org.dishevelled.matrix.Matrix3D;

/**
 * Abstract implementation of Matrix3D.
 *
 * @param <E> type of this abstract 3D matrix
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractMatrix3D<E>
    implements Matrix3D<E>
{
    /** Number of slices. */
    protected long slices;

    /** Number of rows. */
    protected long rows;

    /** Number of columns. */
    protected long columns;

    /** Slice of the first element. */
    protected long sliceZero;

    /** Row of the first element. */
    protected long rowZero;

    /** Column of the first element. */
    protected long columnZero;

    /** Number of slices between elements. */
    protected long sliceStride;

    /** Number of rows between elements. */
    protected long rowStride;

    /** Number of columns between elements. */
    protected long columnStride;

    /** True if this instance is a view. */
    protected boolean isView;


    /**
     * Protected no-arg constructor, to support serialization.
     */
    protected AbstractMatrix3D()
    {
        // empty
    }

    /**
     * Create a new abstract 3D matrix with the specified number of
     * slices, rows, and columns.
     *
     * @param slices slices, must be <code>&gt;= 0</code>
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if any of <code>slices</code>,
     *    <code>rows</code>, or <code>columns</code> are negative
     */
    protected AbstractMatrix3D(final long slices,
                                     final long rows,
                                     final long columns)
    {

        this.slices = slices;
        this.rows = rows;
        this.columns = columns;
        this.sliceZero = 0L;
        this.rowZero = 0L;
        this.columnZero = 0L;
        this.sliceStride = rows * columns;
        this.rowStride = columns;
        this.columnStride = 1L;
        this.isView = false;
    }

    /**
     * Create a new abstract 3D matrix with the specified parameters.
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
     */
    protected AbstractMatrix3D(final long slices,
                                     final long rows,
                                     final long columns,
                                     final long sliceZero,
                                     final long rowZero,
                                     final long columnZero,
                                     final long sliceStride,
                                     final long rowStride,
                                     final long columnStride,
                                     final boolean isView)
    {
        this.slices = slices;
        this.rows = rows;
        this.columns = columns;
        this.sliceZero = sliceZero;
        this.rowZero = rowZero;
        this.columnZero = columnZero;
        this.sliceStride = sliceStride;
        this.rowStride = rowStride;
        this.columnStride = columnStride;
        this.isView = isView;
    }


    /** {@inheritDoc} */
    public long size()
    {
        return (slices * rows * columns);
    }

    /** {@inheritDoc} */
    public long slices()
    {
        return slices;
    }

    /** {@inheritDoc} */
    public long rows()
    {
        return rows;
    }

    /** {@inheritDoc} */
    public long columns()
    {
        return columns;
    }

    /** {@inheritDoc} */
    public long cardinality()
    {
        long cardinality = 0;
        for (E e : this)
        {
            if (e != null)
            {
                cardinality++;
            }
        }
        return cardinality;
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return (0 == cardinality());
    }

    /** {@inheritDoc} */
    public void clear()
    {
        forEach(new QuaternaryProcedure<Long, Long, Long, E>()
            {
                public void run(final Long slice, final Long row, final Long column, final E e)
                    {
                        if (e != null)
                        {
                            set(slice, row, column, null);
                        }
                    }
            });
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return new ObjectMatrix3DIterator();
    }

    /** {@inheritDoc} */
    public E get(final long slice, final long row, final long column)
    {
        if (slice < 0)
        {
            throw new IndexOutOfBoundsException(slice + "< 0");
        }
        if (row < 0)
        {
            throw new IndexOutOfBoundsException(row + "< 0");
        }
        if (column < 0)
        {
            throw new IndexOutOfBoundsException(column + "< 0");
        }
        if (slice >= slices)
        {
            throw new IndexOutOfBoundsException(slice + " >= " + slices);
        }
        if (row >= rows)
        {
            throw new IndexOutOfBoundsException(row + " >= " + rows);
        }
        if (column >= columns)
        {
            throw new IndexOutOfBoundsException(column + " >= " + columns);
        }

        return getQuick(slice, row, column);
    }

    /** {@inheritDoc} */
    public void set(final long slice, final long row, final long column, final E e)
    {
        if (slice < 0)
        {
            throw new IndexOutOfBoundsException(slice + "< 0");
        }
        if (row < 0)
        {
            throw new IndexOutOfBoundsException(row + "< 0");
        }
        if (column < 0)
        {
            throw new IndexOutOfBoundsException(column + "< 0");
        }
        if (slice >= slices)
        {
            throw new IndexOutOfBoundsException(slice + " >= " + slices);
        }
        if (row >= rows)
        {
            throw new IndexOutOfBoundsException(row + " >= " + rows);
        }
        if (column >= columns)
        {
            throw new IndexOutOfBoundsException(column + " >= " + columns);
        }

        setQuick(slice, row, column, e);
    }

    /** {@inheritDoc} */
    public void setQuick(final long slice, final long row, final long column, final E e)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public Matrix3D<E> assign(final E e)
    {
        forEach(new QuaternaryProcedure<Long, Long, Long, E>()
            {
                public void run(final Long slice, final Long row, final Long column, final E ignore)
                    {
                        setQuick(slice, row, column, e);
                    }
            });

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> assign(final UnaryFunction<E, E> function)
    {
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        forEach(new QuaternaryProcedure<Long, Long, Long, E>()
            {
                public void run(final Long slice, final Long row, final Long column, final E e)
                    {
                        setQuick(slice, row, column, function.evaluate(e));
                    }
            });

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> assign(final Matrix3D<? extends E> other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size() != other.size())
            || (slices != other.slices())
            || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("other must have the same dimensions as this");
        }

        forEach(new QuaternaryProcedure<Long, Long, Long, E>()
            {
                public void run(final Long slice, final Long row, final Long column, final E e)
                {
                    setQuick(slice, row, column, other.getQuick(slice, row, column));
                }
            });

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> assign(final Matrix3D<? extends E> other,
                                    final BinaryFunction<E, E, E> function)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size() != other.size())
            || (slices != other.slices())
            || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("other must have the same dimensions as this");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        forEach(new QuaternaryProcedure<Long, Long, Long, E>()
            {
                public void run(final Long slice, final Long row, final Long column, final E e)
                    {
                        E result = function.evaluate(e, other.getQuick(slice, row, column));
                        setQuick(slice, row, column, result);
                    }
            });

        return this;
    }

    /** {@inheritDoc} */
    public E aggregate(final BinaryFunction<E, E, E> aggr, final UnaryFunction<E, E> function)
    {
        if (aggr == null)
        {
            throw new IllegalArgumentException("aggr must not be null");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }
        if (size() == 0)
        {
            return null;
        }
        E a = function.evaluate(getQuick(slices - 1L, rows - 1L, columns - 1L));
        long skip = 1L;
        for (long slice = slices; --slice >= 0;)
        {
            for (long row = rows; --row >= 0;)
            {
                for (long column = (columns - skip); --column >= 0;)
                {
                    a = aggr.evaluate(a, function.evaluate(getQuick(slice, row, column)));
                }
                skip = 0L;
            }
        }
        return a;
    }

    /** {@inheritDoc} */
    public E aggregate(final Matrix3D<? extends E> other,
                       final BinaryFunction<E, E, E> aggr,
                       final BinaryFunction<E, E, E> function)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size() != other.size())
            || (slices != other.slices())
            || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("other must have the same dimensions as this");
        }
        if (aggr == null)
        {
            throw new IllegalArgumentException("aggr must not be null");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }
        if (size() == 0)
        {
            return null;
        }
        long lastSlice = (slices - 1L);
        long lastRow = (rows - 1L);
        long lastColumn = (columns - 1L);
        E a = function.evaluate(getQuick(lastSlice, lastRow, lastColumn),
                                other.getQuick(lastSlice, lastRow, lastColumn));
        long skip = 1L;
        for (long slice = slices; --slice >= 0;)
        {
            for (long row = rows; --row >= 0;)
            {
                for (long column = (columns - skip); --column >= 0;)
                {
                    a = aggr.evaluate(a, function.evaluate(getQuick(slice, row, column),
                                                           other.getQuick(slice, row, column)));
                }
                skip = 0L;
            }
        }
        return a;
    }

    /**
     * Create a new view.
     *
     * @return a new view
     */
    protected AbstractMatrix3D<E> view()
    {
        try
        {
            AbstractMatrix3D<E> m = (AbstractMatrix3D<E>) clone();
            return m;
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Self-modifying version of <code>viewDice(int, int, int)</code>.
     *
     * @param axis0 first axis
     * @param axis1 second axis
     * @param axis2 third axis
     * @return modified version of <code>this</code>
     */
    protected AbstractMatrix3D<E> vDice(final int axis0, final int axis1, final int axis2)
    {
        long[] shape = new long[3];
        shape[0] = slices;
        shape[1] = rows;
        shape[2] = columns;

        slices = shape[axis0];
        rows = shape[axis1];
        columns = shape[axis2];

        long[] strides = new long[3];
        strides[0] = sliceStride;
        strides[1] = rowStride;
        strides[2] = columnStride;

        sliceStride = strides[axis0];
        rowStride = strides[axis1];
        columnStride = strides[axis2];
        isView = true;

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewDice(final int axis0, final int axis1, final int axis2)
    {
        return view().vDice(axis0, axis1, axis2);
    }

    /**
     * Self-modifying version of <code>viewSliceFlip()</code>.
     *
     * @return modified version of <code>this</code>
     */
    protected AbstractMatrix3D<E> vSliceFlip()
    {
        if (slices > 0)
        {
            sliceZero += (slices - 1) * sliceStride;
            sliceStride = -sliceStride;
            isView = true;
        }
        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewSliceFlip()
    {
        return view().vSliceFlip();
    }

    /**
     * Self-modifying version of <code>viewRowFlip()</code>.
     *
     * @return modified version of <code>this</code>
     */
    protected AbstractMatrix3D<E> vRowFlip()
    {
        if (rows > 0)
        {
            rowZero += (rows - 1) * rowStride;
            rowStride = -rowStride;
            isView = true;
        }

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewRowFlip()
    {
        return view().vRowFlip();
    }

    /**
     * Self-modifying version of <code>viewColumnFlip()</code>.
     *
     * @return modified version of <code>this</code>
     */
    protected AbstractMatrix3D<E> vColumnFlip()
    {
        if (columns > 0)
        {
            columnZero += (columns - 1) * columnStride;
            columnStride = -columnStride;
            isView = true;
        }

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewColumnFlip()
    {
        return view().vColumnFlip();
    }

    /**
     * Self-modifying version of <code>viewPart(long, long, long, long, long, long)</code>.
     *
     * @param slice slice
     * @param row row
     * @param column column
     * @param depth depth
     * @param height height
     * @param width width
     * @return modified version of <code>this</code>
     */
    protected AbstractMatrix3D<E> vPart(final long slice, final long row, final long column,
                                              final long depth, final long height, final long width)
    {
        sliceZero += sliceStride * slice;
        rowZero += rowStride * row;
        columnZero += columnStride * column;

        slices = depth;
        rows = height;
        columns = width;

        isView = true;

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewPart(final long slice, final long row, final long column,
                                      final long depth, final long height, final long width)
    {
        return view().vPart(slice, row, column, depth, height, width);
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewSelection(final long[] sliceIndices,
                                           final long[] rowIndices,
                                           final long[] columnIndices)
    {
        return null;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewSelection(final UnaryPredicate<Matrix2D<E>> predicate)
    {
        return null;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewSelection(final BitMatrix3D mask)
    {
        return null;
    }

    /**
     * Self-modifying version of <code>viewStrides(long, long, long)</code>.
     *
     * @param sliceStride slice stride
     * @param rowStride row stride
     * @param columnStride column stride
     * @return modified version of <code>this</code>
     */
    protected AbstractMatrix3D<E> vStrides(final long sliceStride,
                                                 final long rowStride,
                                                 final long columnStride)
    {
        this.sliceStride *= sliceStride;
        this.rowStride *= rowStride;
        this.columnStride *= columnStride;

        if (slices != 0)
        {
            this.slices = (((this.slices - 1L) / sliceStride) + 1L);
        }
        if (rows != 0)
        {
            this.rows = (((this.rows - 1L) / rowStride) + 1L);
        }
        if (columns != 0)
        {
            this.columns = (((this.columns - 1L) / columnStride) + 1L);
        }
        isView = true;

        return this;
    }

    /** {@inheritDoc} */
    public Matrix3D<E> viewStrides(final long sliceStride,
                                         final long rowStride,
                                         final long columnStride)
    {
        return view().vStrides(sliceStride, rowStride, columnStride);
    }

    /** {@inheritDoc} */
    public void forEach(final UnaryProcedure<? super E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (long slice = 0; slice < slices; slice++)
        {
            for (long row = 0; row < rows; row++)
            {
                for (long column = 0; column < columns; column++)
                {
                    E e = getQuick(slice, row, column);
                    procedure.run(e);
                }
            }
        }
    }

    /** {@inheritDoc} */
    public void forEach(final UnaryPredicate<? super E> predicate,
                        final UnaryProcedure<? super E> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (long slice = 0; slice < slices; slice++)
        {
            for (long row = 0; row < rows; row++)
            {
                for (long column = 0; column < columns; column++)
                {
                    E e = getQuick(slice, row, column);
                    if (predicate.test(e))
                    {
                        procedure.run(e);
                    }
                }
            }
        }
    }

    /** {@inheritDoc} */
    public void forEachNonNull(final UnaryProcedure<? super E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        forEach(new UnaryPredicate<E>()
            {
                public boolean test(final E e)
                {
                    return (e != null);
                }
            }, procedure);
    }

    /** {@inheritDoc} */
    public void forEach(final QuaternaryProcedure<Long, Long, Long, ? super E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (long slice = 0; slice < slices; slice++)
        {
            for (long row = 0; row < rows; row++)
            {
                for (long column = 0; column < columns; column++)
                {
                    E e = getQuick(slice, row, column);
                    procedure.run(slice, row, column, e);
                }
            }
        }
    }

    /** {@inheritDoc} */
    public void forEach(final QuaternaryPredicate<Long, Long, Long, ? super E> predicate,
                        final QuaternaryProcedure<Long, Long, Long, ? super E> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }
        for (long slice = 0; slice < slices; slice++)
        {
            for (long row = 0; row < rows; row++)
            {
                for (long column = 0; column < columns; column++)
                {
                    E e = getQuick(slice, row, column);
                    if (predicate.test(slice, row, column, e))
                    {
                        procedure.run(slice, row, column, e);
                    }
                }
            }
        }
    }

    /**
     * Return the slice of the first element.
     *
     * @return the slice of the first element
     */
    protected long sliceZero()
    {
        return sliceZero;
    }

    /**
     * Return the row of the first element.
     *
     * @return the row of the first element
     */
    protected long rowZero()
    {
        return rowZero;
    }

    /**
     * Return the column of the first element.
     *
     * @return the column of this first element
     */
    protected long columnZero()
    {
        return columnZero;
    }

    /**
     * Return the number of slices between two elements.
     *
     * @return the number of slices between two elements
     */
    protected long sliceStride()
    {
        return sliceStride;
    }

    /**
     * Return the number of rows between two elements.
     *
     * @return the number of rows between two elements
     */
    protected long rowStride()
    {
        return rowStride;
    }

    /**
     * Return the number of columns between two elements.
     *
     * @return the number of columns between two elements
     */
    protected long columnStride()
    {
        return columnStride;
    }

    /**
     * Return true if this instance is a view.
     *
     * @return true if this instance is a view
     */
    protected boolean isView()
    {
        return isView;
    }

    /**
     * Matrix3D iterator.
     */
    private class ObjectMatrix3DIterator
        implements Iterator<E>
    {
        /** Slice. */
        private long slice = 0L;

        /** Row. */
        private long row = 0L;

        /** Column. */
        private long column = 0L;


        /** {@inheritDoc} */
        public boolean hasNext()
        {
            return ((slice < slices) && (row < rows) && (column < columns));
        }

        /** {@inheritDoc} */
        public E next()
        {
            if ((slice < slices) && (row < rows) && (column < columns))
            {
                E e = getQuick(slice, row, column);

                column++;
                if (column == columns)
                {
                    column = 0L;
                    row++;
                    if (row == rows)
                    {
                        row = 0L;
                        slice++;
                    }
                }

                return e;
            }
            else
            {
                throw new NoSuchElementException("slice=" + slice + " row=" + row + " column=" + column);
            }
        }

        /** {@inheritDoc} */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
