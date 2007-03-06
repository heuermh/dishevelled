/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.matrix.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.dishevelled.matrix.ObjectMatrix1D;
import org.dishevelled.matrix.ObjectMatrix2D;

import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.TertiaryPredicate;
import org.dishevelled.functor.TertiaryProcedure;

/**
 * Abstract implementation of ObjectMatrix2D.
 *
 * @param <E> type of this abstract 2D matrix
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObjectMatrix2D<E>
    implements ObjectMatrix2D<E>
{
    /** Number of rows. */
    protected long rows;

    /** Number of columns. */
    protected long columns;

    /** Row of the first element. */
    protected long rowZero;

    /** Column of the first element. */
    protected long columnZero;

    /** Number of rows between two elements. */
    protected long rowStride;

    /** Number of columns between two elements. */
    protected long columnStride;

    /** True if this instance is a view. */
    protected boolean isView;


    /**
     * Protected no-arg constructor, to support serialization.
     */
    protected AbstractObjectMatrix2D()
    {
        // empty
    }

    /**
     * Create a new abstract 2D matrix with the specified number of
     * rows and columns.
     *
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if either <code>rows</code> or
     *    <code>columns</code> is negative
     */
    protected AbstractObjectMatrix2D(final long rows, final long columns)
    {
        if (rows < 0)
        {
            throw new IllegalArgumentException("rows must be >= 0");
        }
        if (columns < 0)
        {
            throw new IllegalArgumentException("columns must be >= 0");
        }

        this.rows = rows;
        this.columns = columns;
        this.rowZero = 0L;
        this.columnZero = 0L;
        this.rowStride = columns;
        this.columnStride = 1L;
        this.isView = false;
    }

    /**
     * Create a new abstract 2D matrix with the specified parameters.
     *
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param rowZero row of the first element
     * @param columnZero column of the first element
     * @param rowStride number of rows between two elements
     * @param columnStride number of columns between two elements
     * @param isView true if this instance is a view
     */
    protected AbstractObjectMatrix2D(final long rows,
                                     final long columns,
                                     final long rowZero,
                                     final long columnZero,
                                     final long rowStride,
                                     final long columnStride,
                                     final boolean isView)
    {
        if (rows < 0)
        {
            throw new IllegalArgumentException("rows must be >= 0");
        }
        if (columns < 0)
        {
            throw new IllegalArgumentException("columns must be >= 0");
        }

        this.rows = rows;
        this.columns = columns;
        this.rowZero = rowZero;
        this.columnZero = columnZero;
        this.rowStride = rowStride;
        this.columnStride = columnStride;
        this.isView = isView;
    }


    /** {@inheritDoc} */
    public long size()
    {
        return (rows * columns);
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
        forEach(new TertiaryProcedure<Long, Long, E>()
            {
                public void run(final Long row, final Long column, final E e)
                    {
                        if (e != null)
                        {
                            set(row, column, null);
                        }
                    }
            });
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return new ObjectMatrix2DIterator();
    }

    /** {@inheritDoc} */
    public E get(final long row, final long column)
    {
        if (row < 0)
        {
            throw new IndexOutOfBoundsException(row + "< 0");
        }
        if (column < 0)
        {
            throw new IndexOutOfBoundsException(column + "< 0");
        }
        if (row >= rows)
        {
            throw new IndexOutOfBoundsException(row + " >= " + rows);
        }
        if (column >= columns)
        {
            throw new IndexOutOfBoundsException(column + " >= " + columns);
        }

        return getQuick(row, column);
    }

    /** {@inheritDoc} */
    public void set(final long row, final long column, final E e)
    {
        if (row < 0)
        {
            throw new IndexOutOfBoundsException(row + "< 0");
        }
        if (column < 0)
        {
            throw new IndexOutOfBoundsException(column + "< 0");
        }
        if (row >= rows)
        {
            throw new IndexOutOfBoundsException(row + " >= " + rows);
        }
        if (column >= columns)
        {
            throw new IndexOutOfBoundsException(column + " >= " + columns);
        }

        setQuick(row, column, e);
    }

    /** {@inheritDoc} */
    public void setQuick(final long row, final long column, final E e)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> assign(final E e)
    {
        forEach(new TertiaryProcedure<Long, Long, E>()
            {
                public void run(final Long row, final Long column, final E ignore)
                {
                    setQuick(row, column, e);
                }
            });

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> assign(final UnaryFunction<E, E> function)
    {
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        forEach(new TertiaryProcedure<Long, Long, E>()
            {
                public void run(final Long row, final Long column, final E e)
                {
                    setQuick(row, column, function.evaluate(e));
                }
            });

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> assign(final ObjectMatrix2D<? extends E> other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size() != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }

        forEach(new TertiaryProcedure<Long, Long, E>()
            {
                public void run(final Long row, final Long column, final E ignore)
                {
                    setQuick(row, column, other.getQuick(row, column));
                }
            });
        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> assign(final ObjectMatrix2D<? extends E> other,
                                    final BinaryFunction<E, E, E> function)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size() != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        forEach(new TertiaryProcedure<Long, Long, E>()
            {
                public void run(final Long row, final Long column, final E e)
                {
                    E result = function.evaluate(e, other.getQuick(row, column));
                    setQuick(row, column, result);
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

        long lastRow = (rows - 1L);
        long lastColumn = (columns - 1L);
        E a = function.evaluate(getQuick(lastRow, lastColumn));
        long skip = 1L;
        for (long row = rows; --row >= 0;)
        {
            for (long column = (columns - skip); --column >= 0;)
            {
                a = aggr.evaluate(a, function.evaluate(getQuick(row, column)));
            }
            skip = 0L;
        }
        return a;
    }

    /** {@inheritDoc} */
    public E aggregate(final ObjectMatrix2D<? extends E> other,
                       final BinaryFunction<E, E, E> aggr,
                       final BinaryFunction<E, E, E> function)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size() != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
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

        long lastRow = (rows - 1L);
        long lastColumn = (columns - 1L);
        E a = function.evaluate(getQuick(lastRow, lastColumn), other.getQuick(lastRow, lastColumn));
        long skip = 1L;
        for (long row = rows; --row >= 0;)
        {
            for (long column = (columns - skip); --column >= 0;)
            {
                a = aggr.evaluate(a, function.evaluate(getQuick(row, column), other.getQuick(row, column)));
            }
            skip = 0L;
        }
        return a;
    }

    /**
     * Create a new view.
     *
     * @return a new view
     */
    protected AbstractObjectMatrix2D<E> view()
    {
        try
        {
            AbstractObjectMatrix2D<E> m = (AbstractObjectMatrix2D<E>) clone();
            return m;
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Self-modifying version of <code>viewDice()</code>.
     *
     * @return modified version of <code>this</cod>
     */
    protected AbstractObjectMatrix2D<E> vDice()
    {
        long tmp;

        tmp = rows;
        rows = columns;
        columns = tmp;

        tmp = rowStride;
        rowStride = columnStride;
        columnStride = tmp;

        tmp = rowZero;
        rowZero = columnZero;
        columnZero = tmp;

        isView = true;
        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> viewDice()
    {
        return view().vDice();
    }

    /**
     * Self-modifying version of <code>viewRowFlip()</code>.
     *
     * @return modified version of <code>this</code>
     */
    protected AbstractObjectMatrix2D<E> vRowFlip()
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
    public ObjectMatrix2D<E> viewRowFlip()
    {
        return view().vRowFlip();
    }

    /**
     * Self-modifying version of <code>viewColumnFlip()</code>.
     *
     * @return modified version of <code>this</code>
     */
    protected AbstractObjectMatrix2D<E> vColumnFlip()
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
    public ObjectMatrix2D<E> viewColumnFlip()
    {
        return view().vColumnFlip();
    }

    /**
     * Self-modifying version of <code>viewPart(long, long, long, long)</code>.
     *
     * @param row row
     * @param column column
     * @param height height
     * @param width width
     * @return modified version of <code>this</code>
     */
    protected AbstractObjectMatrix2D<E> vPart(final long row, final long column,
                                              final long height, final long width)
    {
        rowZero += (rowStride * row);
        columnZero += (columnStride * column);
        rows = height;
        columns = width;
        isView = true;

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> viewPart(final long row, final long column,
                                      final long height, final long width)
    {
        if (row < 0)
        {
            throw new IndexOutOfBoundsException(row + " < 0");
        }
        if (row >= rows)
        {
            throw new IndexOutOfBoundsException(row + " >= " + rows);
        }
        if (column < 0)
        {
            throw new IndexOutOfBoundsException(column + " < 0");
        }
        if (column >= columns)
        {
            throw new IndexOutOfBoundsException(column + " >= " + columns);
        }
        if ((row + height) > rows)
        {
            throw new IndexOutOfBoundsException("(row + height), " + (row + height) + " > " + rows);
        }
        if ((column + width) > columns)
        {
            throw new IndexOutOfBoundsException("(column + width), " + (column + width) + " > " + columns);
        }
        return view().vPart(row, column, height, width);
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> viewSelection(final long[] rows, final long[] columns)
    {
        return null;
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> viewSelection(final UnaryPredicate<ObjectMatrix1D<E>> predicate)
    {
        return null;
    }

    /**
     * Self-modifying version of <code>viewStrides(long, long)</code>.
     *
     * @param rowStride row stride
     * @param columnStride column stride
     * @return modified version of <code>this</code>
     */
    protected AbstractObjectMatrix2D<E> vStrides(final long rowStride, final long columnStride)
    {
        this.rowStride *= rowStride;
        this.columnStride *= columnStride;

        if (this.rows != 0)
        {
            this.rows = (((this.rows - 1L) / rowStride) + 1L);
        }
        if (this.columns != 0)
        {
            this.columns = (((this.columns - 1L) / columnStride) + 1L);
        }
        isView = true;

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix2D<E> viewStrides(final long rowStride, final long columnStride)
    {
        return view().vStrides(rowStride, columnStride);
    }

    /** {@inheritDoc} */
    public void forEach(final UnaryProcedure<E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long row = 0; row < rows; row++)
        {
            for (long column = 0; column < columns; column++)
            {
                E e = getQuick(row, column);
                procedure.run(e);
            }
        }
    }

    /** {@inheritDoc} */
    public void forEach(final UnaryPredicate<E> predicate,
                        final UnaryProcedure<E> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long row = 0; row < rows; row++)
        {
            for (long column = 0; column < columns; column++)
            {
                E e = getQuick(row, column);
                if (predicate.test(e))
                {
                    procedure.run(e);
                }
            }
        }
    }

    /** {@inheritDoc} */
    public void forEach(final TertiaryProcedure<Long, Long, E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long row = 0; row < rows; row++)
        {
            for (long column = 0; column < columns; column++)
            {
                E e = getQuick(row, column);
                procedure.run(row, column, e);
            }
        }
    }

    /** {@inheritDoc} */
    public void forEach(final TertiaryPredicate<Long, Long, E> predicate,
                        final TertiaryProcedure<Long, Long, E> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long row = 0; row < rows; row++)
        {
            for (long column = 0; column < columns; column++)
            {
                E e = getQuick(row, column);
                if (predicate.test(row, column, e))
                {
                    procedure.run(row, column, e);
                }
            }
        }
    }

    /** {@inheritDoc} */
    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append(rows);
        sb.append(" x ");
        sb.append(columns);
        sb.append(" matrix\n");

        forEach(new TertiaryProcedure<Long, Long, E>() {
            public void run(final Long row, final Long column, final E e)
                {
                    sb.append((e == null) ? "null" : e.toString());
                    if (column == (columns() - 1))
                    {
                        sb.append("\n");
                    }
                    else
                    {
                        sb.append(" ");
                    }
                }
        });

        return sb.toString();
    }

    /**
     * ObjectMatrix2D iterator.
     */
    private class ObjectMatrix2DIterator
        implements Iterator<E>
    {
        /** Row. */
        private long row = 0L;

        /** Column. */
        private long column = 0L;


        /** {@inheritDoc} */
        public boolean hasNext()
        {
            return ((row < rows) && (column < columns));
        }

        /** {@inheritDoc} */
        public E next()
        {
            if ((row < rows) && (column < columns))
            {
                E e = getQuick(row, column);

                column++;
                if (column == columns)
                {
                    column = 0L;
                    row++;
                }

                return e;
            }
            else
            {
                throw new NoSuchElementException("row=" + row + " column=" + column);
            }
        }

        /** {@inheritDoc} */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
