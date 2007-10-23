/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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

import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Map;
import java.util.HashMap;

import org.dishevelled.matrix.ObjectMatrix1D;

/**
 * Sparse implementation of ObjectMatrix2D based on
 * a hash map whose keys are <code>Long</code>s.
 *
 * <p>The cardinality of this sparse object matrix is limited
 * by the <code>HashMap</code> underlying this implementation
 * to something less than <code>Integer.MAX_VALUE</code>.  The
 * addressable size, on the other hand, is limited to
 * <code>(rows * columns) &lt; Long.MAX_VALUE</code>.</p>
 *
 * @param <E> type of this sparse 2D matrix
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SparseObjectMatrix2D<E>
    extends AbstractObjectMatrix2D<E>
    implements Serializable
{
    /** Map of elements keyed by a <code>Long</code> index. */
    private Map<Long, E> elements;

    /** Default load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;


    /**
     * Private no-arg constructor, to support serialization.
     */
    private SparseObjectMatrix2D()
    {
        elements = null;
    }

    /**
     * Create a new sparse 2D matrix with the specified number
     * of rows and columns.
     *
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if either <code>rows</code>
     *    or <code>columns</code> is negative
     */
    public SparseObjectMatrix2D(final long rows, final long columns)
    {
        this(rows,
             columns,
             (int) Math.min(Integer.MAX_VALUE, ((rows * columns) * DEFAULT_LOAD_FACTOR)),
             DEFAULT_LOAD_FACTOR);
    }

    /**
     * Create a new sparse 2D matrix with the specified number
     * of rows and columns, initial capacity, and load factor.
     *
     * @param rows rows, must be <code>&gt;= 0</code>
     * @param columns columns, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @param loadFactor load factor, must be <code>&gt; 0</code>
     */
    public SparseObjectMatrix2D(final long rows, final long columns, final int initialCapacity, final float loadFactor)
    {
        super(rows, columns);
        elements = new HashMap<Long, E>(initialCapacity, loadFactor);
    }

    /**
     * Create a new instance of SparseObjectMatrix2D with the specified
     * parameters and map of elements.  Used exclusively by the
     * <code>clone()</code> method.
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
    protected SparseObjectMatrix2D(final long rows,
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
              rowStride, columnStride, isView);
        this.elements = elements;
    }


    /** {@inheritDoc} */
    public Object clone()
    {
        return new SparseObjectMatrix2D<E>(rows, columns,
                                           rowZero, columnZero,
                                           rowStride, columnStride,
                                           isView, elements);
    }

    /** {@inheritDoc} */
    public E getQuick(final long row, final long column)
    {
        long index = rowZero + (row * rowStride) + columnZero + (column * columnStride);
        return elements.get(index);
    }

    /** {@inheritDoc} */
    public void setQuick(final long row, final long column, final E e)
    {
        long index = rowZero + (row * rowStride) + columnZero + (column * columnStride);
        if (e == null)
        {
            elements.remove(index);
        }
        else
        {
            elements.put(index, e);
        }
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> viewRow(final long row)
    {
        if (row < 0)
        {
            throw new IndexOutOfBoundsException(row + " < 0");
        }
        if (row >= rows)
        {
            throw new IndexOutOfBoundsException(row + " >= " + rows);
        }
        return new RowView(row);
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> viewColumn(final long column)
    {
        if (column < 0)
        {
            throw new IndexOutOfBoundsException(column + " < 0");
        }
        if (column >= columns)
        {
            throw new IndexOutOfBoundsException(column + " >= " + columns);
        }
        return new ColumnView(column);
    }

    /**
     * Write this 2D object matrix to the specified object output stream.
     *
     * @see java.io.ObjectOutputStream
     * @param out object output stream
     * @throws IOException if an IO error occurs
     */
    private void writeObject(final ObjectOutputStream out)
        throws IOException
    {
        out.writeLong(rows);
        out.writeLong(columns);
        out.writeLong(rowZero);
        out.writeLong(columnZero);
        out.writeLong(rowStride);
        out.writeLong(columnStride);
        out.writeBoolean(isView);
        out.writeObject(elements);
    }

    /**
     * Read this 2D object matrix in from the specified object input stream.
     *
     * @see java.io.ObjectInputStream
     * @param in object input stream
     * @throws IOException if an IO error occurs
     * @throws ClassNotFoundException if a classloading error occurs
     */
    private void readObject(final ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        super.rows = in.readLong();
        super.columns = in.readLong();
        super.rowZero = in.readLong();
        super.columnZero = in.readLong();
        super.rowStride = in.readLong();
        super.columnStride = in.readLong();
        super.isView = in.readBoolean();
        this.elements = (Map<Long, E>) in.readObject();
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("\n   rows=");
        sb.append(rows);
        sb.append("   columns=");
        sb.append(columns);
        sb.append("   rowZero=");
        sb.append(rowZero);
        sb.append("   columnZero=");
        sb.append(columnZero);
        sb.append("   rowStride=");
        sb.append(rowStride);
        sb.append("   columnStride=");
        sb.append(columnStride);
        sb.append("\n   elements=");
        sb.append(elements.toString());
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Row view.
     */
    private class RowView
        extends SparseObjectMatrix1D<E>
    {

        /**
         * Create a new row view for the specified row.
         *
         * @param row row
         */
        RowView(final long row)
        {
            super(SparseObjectMatrix2D.this.columns,
                  SparseObjectMatrix2D.this.rowZero
                      + (row * SparseObjectMatrix2D.this.rowStride)
                      + SparseObjectMatrix2D.this.columnZero,
                  SparseObjectMatrix2D.this.columnStride,
                  true,
                  elements);
        }
    }

    /**
     * Column view.
     */
    private class ColumnView
        extends SparseObjectMatrix1D<E>
    {

        /**
         * Create a new column view for the specified column.
         *
         * @param column column
         */
        ColumnView(final long column)
        {
            super(SparseObjectMatrix2D.this.rows,
                  SparseObjectMatrix2D.this.rowZero
                      + (column * SparseObjectMatrix2D.this.columnStride)
                      + SparseObjectMatrix2D.this.columnZero,
                  SparseObjectMatrix2D.this.rowStride,
                  true,
                  elements);
        }
    }
}
