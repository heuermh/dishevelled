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
package org.dishevelled.matrix;

import org.dishevelled.bitset.MutableBitSet;

import org.dishevelled.functor.BinaryProcedure;

/**
 * Fixed size bit matrix in two dimensions, indexed by <code>long</code>s.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BitMatrix2D
{
    /** Bit set. */
    private final MutableBitSet bitset;

    /** Number of rows. */
    private final long rows;

    /** Number of columns. */
    private final long columns;

    /** Size. */
    private final long size;


    /**
     * Create a new 2D bit matrix with the specified number of rows and columns.
     *
     * @param rows number of rows, must be <code>&gt;= 0</code>
     * @param columns number of columns, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if either <code>rows</code> or
     *    <code>columns</code> is negative
     */
    public BitMatrix2D(final long rows, final long columns)
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
        this.size = (rows * columns);
        this.bitset = new MutableBitSet(this.size);
    }


    /**
     * Return the size of this 2D bit matrix.
     *
     * @return the size of this 2D bit matrix
     */
    public long size()
    {
        return size;
    }

    /**
     * Return the number of rows in this 2D bit matrix.
     *
     * @return the number of rows in this 2D bit matrix
     */
    public long rows()
    {
        return rows;
    }

    /**
     * Return the number of columns in this 2D bit matrix.
     *
     * @return the number of columns in this 2D bit matrix
     */
    public long columns()
    {
        return columns;
    }

    /**
     * Return the cardinality of this 2D bit matrix, the
     * number of bits set to true.
     *
     * @return the cardinality of this 2D bit matrix
     */
    public long cardinality()
    {
        return bitset.cardinality();
    }

    /**
     * Return true if the cardinality of this 2D bit matrix is zero.
     *
     * @return true if the cardinality of this 2D bit matrix is zero
     */
    public boolean isEmpty()
    {
        return (0 == cardinality());
    }

    /**
     * Clear all the values in this 2D bit matrix.
     */
    public void clear()
    {
        for (long i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i + 1))
        {
            bitset.clear(i);
        }
    }

    /**
     * Return the bit value at the specified row and column.
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @return the bit value at the specified row and column
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code>
     *    is negative or if <code>row</code> or <code>column</code> is greater than
     *    or equal to <code>rows()</code> or <code>columns()</code>, respectively
     */
    public boolean get(final long row, final long column)
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
        return getQuick(row, column);
    }

    /**
     * Return the bit value at the specified row and column without checking bounds.
     *
     * @param row row index
     * @param column column index
     * @return the bit value at the specified row and column
     */
    public boolean getQuick(final long row, final long column)
    {
        return bitset.getQuick(index(row, column));
    }

    /**
     * Set the bit value at the specified row and column to <code>value</code>.
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param value value
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code>
     *    is negative or if <code>row</code> or <code>column</code> is greater than
     *    or equal to <code>rows()</code> or <code>columns()</code>, respectively
     */
    public void set(final long row, final long column, final boolean value)
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
        setQuick(row, column, value);
    }

    /**
     * Set the bit value at the specified row and column to <code>value</code> without checking bounds.
     *
     * @param row row index
     * @param column column index
     * @param value value
     */
    public void setQuick(final long row, final long column, final boolean value)
    {
        long index = index(row, column);
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
     * Set all of the bit values from (<code>row0, column0</code>), inclusive,
     * to (<code>row1, column1</code>), exclusive, to the specified value.
     *
     * @param row0 first row, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column0 first column, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param row1 second row, must be <code>&gt;= 0</code>, <code>&lt;= rows()</code>
     *    and <code>&gt;= row0</code>
     * @param column1 second column, must be <code>&gt;= 0</code>, <code>&lt;= columns()</code>
     *    and <code>&gt;= column0</code>
     * @param value value
     * @throws IllegalArgumentException if either <code>row1</code> or <code>column1</code>
     *    are less than <code>row0</code> or <code>column0</code>, respectively
     * @throws IndexOutOfBoundsException if any of <code>row0</code>, <code>column0</code>,
     *    <code>row1</code>, or <code>column1</code> are negative or if either <code>row0</code>
     *    or <code>column0</code> are greater than or equal to <code>rows()</code> or
     *    <code>columns()</code>, respectively, or if either <code>row1</code> or
     *    <code>column1</code> are strictly greater than <code>rows()</code> or <code>columns()</code>,
     *    respectively
     */
    public void set(final long row0, final long column0,
                    final long row1, final long column1, final boolean value)
    {
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

        for (long row = row0; row < row1; row++)
        {
            for (long column = column0; column < column1; column++)
            {
                setQuick(row, column, value);
            }
        }
    }

    /**
     * Set the bit value at the specified row and column to the complement
     * of its current bit value.
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code>
     *    is negative or if <code>row</code> or <code>column</code> is greater than
     *    or equal to <code>rows()</code> or <code>columns()</code>, respectively
     */
    public void flip(final long row, final long column)
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
        flipQuick(row, column);
    }

    /**
     * Set the bit value at the specified row and column to the complement
     * of its current bit value without checking bounds.
     *
     * @param row row index
     * @param column column index
     */
    public void flipQuick(final long row, final long column)
    {
        bitset.flipQuick(index(row, column));
    }

    /**
     * Set all of the bit values from (<code>row0, column0</code>), inclusive,
     * to (<code>row1, column1</code>), exclusive, to the complement of their
     * current bit values.
     *
     * @param row0 first row, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column0 first column, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param row1 second row, must be <code>&gt;= 0</code>, <code>&lt;= rows()</code>
     *    and <code>&gt;= row0</code>
     * @param column1 second column, must be <code>&gt;= 0</code>, <code>&lt;= columns()</code>
     *    and <code>&gt;= column0</code>
     * @throws IllegalArgumentException if either <code>row1</code> or <code>column1</code>
     *    are less than <code>row0</code> or <code>column0</code>, respectively
     * @throws IndexOutOfBoundsException if any of <code>row0</code>, <code>column0</code>,
     *    <code>row1</code>, or <code>column1</code> are negative or if either <code>row0</code>
     *    or <code>column0</code> are greater than or equal to <code>rows()</code> or
     *    <code>columns()</code>, respectively, or if either <code>row1</code> or
     *    <code>column1</code> are strictly greater than <code>rows()</code> or <code>columns()</code>,
     *    respectively
     */
    public void flip(final long row0, final long column0,
                     final long row1, final long column1)
    {
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

        for (long row = row0; row < row1; row++)
        {
            for (long column = column0; column < column1; column++)
            {
                flipQuick(row, column);
            }
        }
    }

    /**
     * Assign all values in this 2D bit matrix to <code>value</code>.
     *
     * @param value value
     * @return this 2D bit matrix, for convenience
     */
    public BitMatrix2D assign(final boolean value)
    {
        if (size > 0)
        {
            set(0, 0, rows, columns, value);
        }
        return this;
    }

    /**
     * Return true if the specified 2D bit matrix has any bits set
     * to true that are also set to true in this 2D bit matrix.
     *
     * @param other other 2D bit matrix, must not be null and must
     *    have the same dimensions as this 2D bit matrix
     * @return true if the specified 2D bit matrix has any bits set
     *    to true that are also set to true in this 2D bit matrix
     */
    public boolean intersects(final BitMatrix2D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        return bitset.intersects(other.bitset);
    }

    /**
     * Perform a logical <b>AND</b> of this 2D bit matrix
     * and the specified 2D bit matrix.
     *
     * @param other other 2D bit matrix, must not be null and must
     *    have the same dimensions as this 2D bit matrix
     * @return this 2D bit matrix, for convenience
     */
    public BitMatrix2D and(final BitMatrix2D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.and(other.bitset);
        return this;
    }

    /**
     * Clear all the bits in this 2D bit matrix whose corresponding
     * bit is set in the specified 2D bit matrix.
     *
     * @param other other 2D bit matrix, must not be null and must
     *    have the same dimensions as this 2D bit matrix
     * @return this 2D bit matrix, for convenience
     */
    public BitMatrix2D andNot(final BitMatrix2D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.andNot(other.bitset);
        return this;
    }

    /**
     * Perform a logical <b>OR</b> of this 2D bit matrix
     * and the specified 2D bit matrix.
     *
     * @param other other 2D bit matrix, must not be null and must
     *    have the same dimensions as this 2D bit matrix
     * @return this 2D bit matrix, for convenience
     */
    public BitMatrix2D or(final BitMatrix2D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.or(other.bitset);
        return this;
    }

    /**
     * Perform a logical <b>XOR</b> of this 2D bit matrix
     * and the specified 2D bit matrix.
     *
     * @param other other 2D bit matrix, must not be null and must
     *    have the same dimensions as this 2D bit matrix
     * @return this 2D bit matrix, for convenience
     */
    public BitMatrix2D xor(final BitMatrix2D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if ((size != other.size()) || (rows != other.rows()) || (columns != other.columns()))
        {
            throw new IllegalArgumentException("this and other must have the same dimensions");
        }
        bitset.xor(other.bitset);
        return this;
    }

    /**
     * Apply the specified procedure to each row and column
     * in this 2D bit matrix with a bit equal to the specified value.
     *
     * @param value value
     * @param procedure procedure, must not be null
     */
    public void forEach(final boolean value, final BinaryProcedure<Long, Long> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long row = 0; row < rows; row++)
        {
            for (long column = 0; column < columns; column++)
            {
                if (getQuick(row, column) == value)
                {
                    procedure.run(Long.valueOf(row), Long.valueOf(column));
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
        if (!(o instanceof BitMatrix2D))
        {
            return false;
        }

        BitMatrix2D bm = (BitMatrix2D) o;

        if ((size != bm.size()) || (rows != bm.rows()) || (columns != bm.columns()))
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
        hashCode = 17 * hashCode + (int) (rows ^ (rows >>> 32));
        hashCode = 17 * hashCode + (int) (columns ^ (columns >>> 32));
        hashCode = 17 * hashCode + bitset.hashCode();
        return hashCode;
    }

    /**
     * Return the index for the specified row and column.
     *
     * @param row row index
     * @param column column index
     * @return the index for the specified row and column
     */
    private long index(final long row, final long column)
    {
        return (columns * row) + column;
    }
}
