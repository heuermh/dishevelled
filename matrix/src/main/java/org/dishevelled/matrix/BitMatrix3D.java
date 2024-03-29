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
package org.dishevelled.matrix;

import org.dishevelled.bitset.MutableBitSet;

import org.dishevelled.functor.TernaryProcedure;

/**
 * Fixed size bit matrix in three dimensions, indexed by <code>long</code>s.
 *
 * @author  Michael Heuer
 */
public final class BitMatrix3D
{
    /** Bit set. */
    private final MutableBitSet bitset;

    /** Number of slices. */
    private final long slices;

    /** Number of rows. */
    private final long rows;

    /** Number of columns. */
    private final long columns;

    /** Size. */
    private final long size;


    /**
     * Create a new 3D bit matrix with the specified number of
     * slices, rows, and columns.
     *
     * @param slices number of slices, must be <code>&gt;= 0</code>
     * @param rows number of rows, must be <code>&gt;= 0</code>
     * @param columns number of columns, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if any of <code>slices</code>,
     *    <code>rows</code>, or <code>columns</code> is negative
     */
    public BitMatrix3D(final long slices, final long rows, final long columns)
    {
        if (slices < 0)
        {
            throw new IllegalArgumentException("slices must be >= 0");
        }
        if (rows < 0)
        {
            throw new IllegalArgumentException("rows must be >= 0");
        }
        if (columns < 0)
        {
            throw new IllegalArgumentException("columns must be >= 0");
        }
        this.slices = slices;
        this.rows = rows;
        this.columns = columns;
        this.size = (slices * rows * columns);
        this.bitset = new MutableBitSet(size);
    }


    /**
     * Return the size of this 3D bit matrix.
     *
     * @return the size of this 3D bit matrix
     */
    public long size()
    {
        return size;
    }

    /**
     * Return the number of slices in this 3D bit matrix.
     *
     * @return the number of slices in this 3D bit matrix
     */
    public long slices()
    {
        return slices;
    }

    /**
     * Return the number of rows in this 3D bit matrix.
     *
     * @return the number of rows in this 3D bit matrix
     */
    public long rows()
    {
        return rows;
    }

    /**
     * Return the number of columns in this 3D bit matrix.
     *
     * @return the number of columns in this 3D bit matrix
     */
    public long columns()
    {
        return columns;
    }

    /**
     * Return the cardinality of this 3D bit matrix, the
     * number of bits set to true.
     *
     * @return the cardinality of this 3D bit matrix
     */
    public long cardinality()
    {
        return bitset.cardinality();
    }

    /**
     * Return true if the cardinality of this 3D bit matrix is zero.
     *
     * @return true if the cardinality of this 3D bit matrix is zero
     */
    public boolean isEmpty()
    {
        return (0 == cardinality());
    }

    /**
     * Clear all the values in this 3D bit matrix.
     */
    public void clear()
    {
        for (long i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i + 1))
        {
            bitset.clear(i);
        }
    }

    /**
     * Return the bit value at the specified slice, row, and column.
     *
     * @param slice slice index, must be <code>&gt; 0</code> and <code>&lt; slices()</code>
     * @param row row index, must be <code>&gt; 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt; 0</code> and <code>&lt; columns()</code>
     * @return the bit value at the specified slice, row, and column
     * @throws IndexOutOfBoundsException if any of <code>slice</code>, <code>row</code>, or
     *    <code>column</code> are negative or if any of <code>slice</code>, <code>row</code>,
     *    or <code>column</code> are greater than or equal to <code>slices()</code>,
     *    <code>rows()</code>, or <code>columns()</code>, respectively
     */
    public boolean get(final long slice, final long row, final long column)
    {
        if (slice < 0)
        {
            throw new IndexOutOfBoundsException(slice + " < 0");
        }
        if (slice >= slices)
        {
            throw new IndexOutOfBoundsException(slice + " >= " + slices);
        }
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
        return getQuick(slice, row, column);
    }

    /**
     * Return the bit value at the specified slice, row, and column without checking bounds.
     *
     * @param slice slice index
     * @param row row index
     * @param column column index
     * @return the bit value at the specified slice, row, and column
     */
    public boolean getQuick(final long slice, final long row, final long column)
    {
        return bitset.getQuick(index(slice, row, column));
    }

    /**
     * Set the bit value at the specified slice, row, and column to <code>value</code>.
     *
     * @param slice slice index, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param value value
     * @throws IndexOutOfBoundsException if any of <code>slice</code>, <code>row</code>, or
     *    <code>column</code> are negative or if any of <code>slice</code>, <code>row</code>,
     *    or <code>column</code> are greater than or equal to <code>slices()</code>,
     *    <code>rows()</code>, or <code>columns()</code>, respectively
     */
    public void set(final long slice, final long row, final long column, final boolean value)
    {
        if (slice < 0)
        {
            throw new IndexOutOfBoundsException(slice + " < 0");
        }
        if (slice >= slices)
        {
            throw new IndexOutOfBoundsException(slice + " >= " + slices);
        }
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
        setQuick(slice, row, column, value);
    }

    /**
     * Set the bit value at the specified slice, row, and column to <code>value</code> without checking bounds.
     *
     * @param slice slice index
     * @param row row index
     * @param column column index
     * @param value value
     */
    public void setQuick(final long slice, final long row, final long column, final boolean value)
    {
        long index = index(slice, row, column);
        if (value)
        {
            bitset.setQuick(index);
        }
        else
        {
            bitset.clearQuick(index);
        }
    }

    /**
     * Set all of the bit values from (<code>slice0, row0, column0</code>), inclusive,
     * to (<code>slice1, row1, column1</code>), exclusive, to the specified value.
     *
     * @param slice0 first slice, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @param row0 first row, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column0 first column, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param slice1 second slice, must be <code>&gt;= 0</code>, <code>&lt;= slices()</code>
     *    and <code>&gt;= slice0</code>
     * @param row1 second row, must be <code>&gt;= 0</code>, <code>&lt;= rows()</code>
     *    and <code>&gt;= row0</code>
     * @param column1 second column, must be <code>&gt;= 0</code>, <code>&lt;= columns()</code>
     *    and <code>&gt;= column0</code>
     * @param value value
     * @throws IllegalArgumentException if any of <code>slice1</code>, <code>row1</code>, or
     *    <code>column1</code> are less than <code>slice0</code>, <code>row0</code>, or
     *    <code>column0</code>, respectively
     * @throws IndexOutOfBoundsException if any of <code>slice0</code>, <code>row0</code>,
     *    <code>column0</code>, <code>slice1</code>, <code>row1</code>, or <code>column1</code>
     *    are negative or if any of <code>slice0</code>, <code>row0</code>, or <code>column0</code>
     *    are greater than or equal to <code>slices()</code>, <code>rows()</code>, or
     *    <code>columns()</code>, respectively, or if any of <code>slice1</code>, <code>row1</code>, or
     *    <code>column1</code> are strictly greater than <code>slices()</code>, <code>rows()</code>,
     *    or <code>columns()</code>, respectively
     */
    public void set(final long slice0, final long row0, final long column0,
                    final long slice1, final long row1, final long column1, final boolean value)
    {
        if (slice0 < 0)
        {
            throw new IndexOutOfBoundsException(slice0 + " < 0");
        }
        if (slice0 >= slices)
        {
            throw new IndexOutOfBoundsException(slice0 + " >= " + slices);
        }
        if (row0 < 0)
        {
            throw new IndexOutOfBoundsException(row0 + " < 0");
        }
        if (row0 >= rows)
        {
            throw new IndexOutOfBoundsException(row0 + " >= " + rows);
        }
        if (column0 < 0)
        {
            throw new IndexOutOfBoundsException(column0 + " < 0");
        }
        if (column0 >= columns)
        {
            throw new IndexOutOfBoundsException(column0 + " >= " + columns);
        }
        if (slice1 < 0)
        {
            throw new IndexOutOfBoundsException(slice1 + " < 0");
        }
        if (slice1 > slices)
        {
            throw new IndexOutOfBoundsException(slice1 + " > " + slices);
        }
        if (row1 < 0)
        {
            throw new IndexOutOfBoundsException(row1 + " < 0");
        }
        if (row1 > rows)
        {
            throw new IndexOutOfBoundsException(row1 + " > " + rows);
        }
        if (column1 < 0)
        {
            throw new IndexOutOfBoundsException(column1 + " < 0");
        }
        if (column1 > columns)
        {
            throw new IndexOutOfBoundsException(column1 + " > " + columns);
        }

        for (long slice = slice0; slice < slice1; slice++)
        {
            for (long row = row0; row < row1; row++)
            {
                for (long column = column0; column < column1; column++)
                {
                    setQuick(slice, row, column, value);
                }
            }
        }
    }

    /**
     * Set the bit value at the specified slice, row, and column to the complement
     * of its current bit value.
     *
     * @param slice slice index, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @throws IndexOutOfBoundsException if any of <code>slice</code>, <code>row</code>, or
     *    <code>column</code> are negative or if any of <code>slice</code>, <code>row</code>,
     *    or <code>column</code> are greater than or equal to <code>slices()</code>,
     *    <code>rows()</code>, or <code>columns()</code>, respectively
     */
    public void flip(final long slice, final long row, final long column)
    {
        if (slice < 0)
        {
            throw new IndexOutOfBoundsException(slice + " < 0");
        }
        if (slice >= slices)
        {
            throw new IndexOutOfBoundsException(slice + " >= " + slices);
        }
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
        flipQuick(slice, row, column);
    }

    /**
     * Set the bit value at the specified slice, row, and column to the complement
     * of its current bit value without checking bounds.
     *
     * @param slice slice index
     * @param row row index
     * @param column column index
     */
    public void flipQuick(final long slice, final long row, final long column)
    {
        bitset.flipQuick(index(slice, row, column));
    }

    /**
     * Set all of the bit values from (<code>slice0, row0, column0</code>), inclusive,
     * to (<code>slice1, row1, column1</code>), exclusive, to the complement of their
     * current bit values.
     *
     * @param slice0 first slice, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @param row0 first row, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column0 first column, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param slice1 second slice, must be <code>&gt;= 0</code>, <code>&lt;= slices()</code>
     *    and <code>&gt;= slice0</code>
     * @param row1 second row, must be <code>&gt;= 0</code>, <code>&lt;= rows()</code>
     *    and <code>&gt;= row0</code>
     * @param column1 second column, must be <code>&gt;= 0</code>, <code>&lt;= columns()</code>
     *    and <code>&gt;= column0</code>
     * @throws IllegalArgumentException if any of <code>slice1</code>, <code>row1</code>, or
     *    <code>column1</code> are less than <code>slice0</code>, <code>row0</code>, or
     *    <code>column0</code>, respectively
     * @throws IndexOutOfBoundsException if any of <code>slice0</code>, <code>row0</code>,
     *    <code>column0</code>, <code>slice1</code>, <code>row1</code>, or <code>column1</code>
     *    are negative or if any of <code>slice0</code>, <code>row0</code>, or <code>column0</code>
     *    are greater than or equal to <code>slices()</code>, <code>rows()</code>, or
     *    <code>columns()</code>, respectively, or if any of <code>slice1</code>, <code>row1</code>, or
     *    <code>column1</code> are strictly greater than <code>slices()</code>, <code>rows()</code>,
     *    or <code>columns()</code>, respectively
     */
    public void flip(final long slice0, final long row0, final long column0,
                     final long slice1, final long row1, final long column1)
    {
        if (slice0 < 0)
        {
            throw new IndexOutOfBoundsException(slice0 + " < 0");
        }
        if (slice0 >= slices)
        {
            throw new IndexOutOfBoundsException(slice0 + " >= " + slices);
        }
        if (row0 < 0)
        {
            throw new IndexOutOfBoundsException(row0 + " < 0");
        }
        if (row0 >= rows)
        {
            throw new IndexOutOfBoundsException(row0 + " >= " + rows);
        }
        if (column0 < 0)
        {
            throw new IndexOutOfBoundsException(column0 + " < 0");
        }
        if (column0 >= columns)
        {
            throw new IndexOutOfBoundsException(column0 + " >= " + columns);
        }
        if (slice1 < 0)
        {
            throw new IndexOutOfBoundsException(slice1 + " < 0");
        }
        if (slice1 > slices)
        {
            throw new IndexOutOfBoundsException(slice1 + " > " + slices);
        }
        if (row1 < 0)
        {
            throw new IndexOutOfBoundsException(row1 + " < 0");
        }
        if (row1 > rows)
        {
            throw new IndexOutOfBoundsException(row1 + " > " + rows);
        }
        if (column1 < 0)
        {
            throw new IndexOutOfBoundsException(column1 + " < 0");
        }
        if (column1 > columns)
        {
            throw new IndexOutOfBoundsException(column1 + " > " + columns);
        }

        for (long slice = slice0; slice < slice1; slice++)
        {
            for (long row = row0; row < row1; row++)
            {
                for (long column = column0; column < column1; column++)
                {
                    flipQuick(slice, row, column);
                }
            }
        }
    }

    /**
     * Assign all values in this 3D bit matrix to <code>value</code>.
     *
     * @param value value
     * @return this 3D bit matrix, for convenience
     */
    public BitMatrix3D assign(final boolean value)
    {
        if (size > 0)
        {
            set(0, 0, 0, slices, rows, columns, value);
        }
        return this;
    }

    /**
     * Return true if the specified 3D bit matrix has any bits set
     * to true that are also set to true in this 3D bit matrix.
     *
     * @param other other 3D bit matrix, must not be null and must
     *    have the same dimensions as this 3D bit matrix
     * @return true if the specified 3D bit matrix has any bits set
     *    to true that are also set to true in this 3D bit matrix
     */
    public boolean intersects(final BitMatrix3D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (slices != other.slices()) || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        return bitset.intersects(other.bitset);
    }

    /**
     * Perform a logical <b>AND</b> of this 3D bit matrix
     * and the specified 3D bit matrix.
     *
     * @param other other 3D bit matrix, must not be null and must
     *    have the same dimensions as this 3D bit matrix
     * @return this 3D bit matrix, for convenience
     */
    public BitMatrix3D and(final BitMatrix3D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (slices != other.slices()) || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.and(other.bitset);
        return this;

    }

    /**
     * Clear all the bits in this 3D bit matrix whose corresponding
     * bit is set in the specified 3D bit matrix.
     *
     * @param other other 3D bit matrix, must not be null and must
     *    have the same dimensions as this 3D bit matrix
     * @return this 3D bit matrix, for convenience
     */
    public BitMatrix3D andNot(final BitMatrix3D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (slices != other.slices()) || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.andNot(other.bitset);
        return this;
    }

    /**
     * Perform a logical <b>OR</b> of this 3D bit matrix
     * and the specified 3D bit matrix.
     *
     * @param other other 3D bit matrix, must not be null and must
     *    have the same dimensions as this 3D bit matrix
     * @return this 3D bit matrix, for convenience
     */
    public BitMatrix3D or(final BitMatrix3D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (slices != other.slices()) || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.or(other.bitset);
        return this;
    }

    /**
     * Perform a logical <b>XOR</b> of this 3D bit matrix
     * and the specified 3D bit matrix.
     *
     * @param other other 3D bit matrix, must not be null and must
     *    have the same dimensions as this 3D bit matrix
     * @return this 3D bit matrix, for convenience
     */
    public BitMatrix3D xor(final BitMatrix3D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (slices != other.slices()) || (rows != other.rows())
            || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.xor(other.bitset);
        return this;
    }

    /**
     * Apply the specified procedure to each slice, row, and column
     * in this 3D bit matrix with a bit equal to the specified value.
     *
     * @param value value
     * @param procedure procedure, must not be null
     */
    public void forEach(final boolean value,
                        final TernaryProcedure<Long, Long, Long> procedure)
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
                    if (getQuick(slice, row, column) == value)
                    {
                        procedure.run(Long.valueOf(slice), Long.valueOf(row), Long.valueOf(column));
                    }
                }
            }
        }
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (!(o instanceof BitMatrix3D))
        {
            return false;
        }

        BitMatrix3D bm = (BitMatrix3D) o;

        if ((size != bm.size()) || (slices != bm.slices()) || (rows != bm.rows()) || (columns != bm.columns()))
        {
            return false;
        }
        return bitset.equals(bm.bitset);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        int hashCode = 37;
        hashCode = 17 * hashCode + (int) (size ^ (size >>> 32));
        hashCode = 17 * hashCode + (int) (slices ^ (slices >>> 32));
        hashCode = 17 * hashCode + (int) (rows ^ (rows >>> 32));
        hashCode = 17 * hashCode + (int) (columns ^ (columns >>> 32));
        hashCode = 17 * hashCode + bitset.hashCode();
        return hashCode;
    }


    /**
     * Return the index for the specified slice, row, and column.
     *
     * @param slice slice inddex
     * @param row row index
     * @param column column index
     * @return the index for the specified slice, row, and column
     */
    private long index(final long slice, final long row, final long column)
    {
        return (rows * columns * slice) + (columns * row) + column;
    }
}
