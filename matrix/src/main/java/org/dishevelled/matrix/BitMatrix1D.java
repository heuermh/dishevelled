/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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

import java.util.List;
import java.util.BitSet;
import java.util.ArrayList;

import org.dishevelled.functor.UnaryProcedure;

/**
 * Fixed size bit matrix in one dimension, indexed by
 * <code>long</code>s.
 *
 * <p>The size of this bit matrix is limited by the <code>BitSet</code>s
 * underlying this implementation to
 * <pre>
 * ((long) Integer.MAX_VALUE) * ((long) Integer.MAX_VALUE) - 1L
 * </pre>
 * This value is the public constant <code>MAX_SIZE</code>.</p>
 *
 * @see #MAX_SIZE
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BitMatrix1D
{
    /** List of bit sets. */
    private final List<BitSet> bitSets;

    /** Size. */
    private final long size;

    /** Maximum size. */
    public static final long MAX_SIZE = ((long) Integer.MAX_VALUE) * ((long) Integer.MAX_VALUE) - 1L;


    /**
     * Create a new 1D bit matrix of the specified size.
     *
     * @see #MAX_SIZE
     * @param size size, must be <code>&gt;= 0</code> and <code>&lt; MAX_SIZE</code>
     * @throws IllegalArgumentException if <code>size</code> is negative
     *    or greater than or equal to the maximum size
     */
    public BitMatrix1D(final long size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException("size must be >= 0");
        }
        if (size >= MAX_SIZE)
        {
            throw new IllegalArgumentException("size must be < MAX_SIZE");
        }

        this.size = size;

        int i = (int) (size / Integer.MAX_VALUE);
        int j = (int) (size % Integer.MAX_VALUE);

        bitSets = new ArrayList<BitSet>(i);

        for (int k = 0; k < i; k++)
        {
            bitSets.add(new BitSet());
            //bitSets.add(new BitSet(Integer.MAX_VALUE));
        }
        if (j > 0)
        {
            bitSets.add(new BitSet());
            //bitSets.add(new BitSet(j));
        }
    }


    /**
     * Return the list of bit sets backing this 1D bit matrix.
     *
     * @return the list of bit sets backing this 1D bit matrix
     */
    private List<BitSet> getBitSets()
    {
        return bitSets;
    }

    /**
     * Return the size of this 1D bit matrix.
     *
     * @return the size of this 1D bit matrix
     */
    public long size()
    {
        return size;
    }

    /**
     * Return the cardinality of this 1D bit matrix, the
     * number of bits set to true.
     *
     * @return the cardinality of this 1D bit matrix
     */
    public long cardinality()
    {
        long cardinality = 0;
        for (BitSet bs : bitSets)
        {
            cardinality += bs.cardinality();
        }
        return cardinality;
    }

    /**
     * Return true if the cardinality of this 1D bit matrix is zero.
     *
     * @return true if the cardinality of this 1D bit matrix is zero
     */
    public boolean isEmpty()
    {
        return (0 == cardinality());
    }

    /**
     * Clear all the values in this 1D bit matrix.
     */
    public void clear()
    {
        for (BitSet bs : bitSets)
        {
            bs.clear();
        }
    }

    /**
     * Return the bit value at the specified index.
     *
     * @param index index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @return the bit value at the specified index
     * @throws IndexOutOfBoundsException if <code>index</code> is negative
     *    or if <code>index</code> is greater than or equal to <code>size()</code>
     */
    public boolean get(final long index)
    {
        if (index >= size)
        {
            throw new IndexOutOfBoundsException(index + " >= " + size);
        }

        int i = (int) (index / Integer.MAX_VALUE);
        int j = (int) (index % Integer.MAX_VALUE);

        BitSet bs = bitSets.get(i);
        return bs.get(j);
    }

    /**
     * Set the bit value at the specified index to <code>value</code>.
     *
     * @param index index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @param value value
     * @throws IndexOutOfBoundsException if <code>index</code> is negative
     *    or if <code>index</code> is greater than or equal to <code>size()</code>
     */
    public void set(final long index, final boolean value)
    {
        if (index >= size)
        {
            throw new IndexOutOfBoundsException(index + " >= " + size);
        }

        int i = (int) (index / Integer.MAX_VALUE);
        int j = (int) (index % Integer.MAX_VALUE);

        BitSet bs = bitSets.get(i);
        bs.set(j, value);
    }

    /**
     * Set all of the bit values from <code>index0</code>, inclusive, to
     * <code>index1</code>, exclusive, to the specified value.
     *
     * @param index0 first index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @param index1 second index, must be <code>&gt;= 0</code>, <code>&lt;= size()</code>
     *    and <code>&gt;= index0</code>
     * @param value value
     * @throws IllegalArgumentException if <code>index1</code> is less than <code>index0</code>
     * @throws IndexOutOfBoundsException if either <code>index0</code>
     *    or <code>index1</code> are negative or if <code>index0</code> is greater than
     *    or equal to <code>size()</code> or if <code>index1</code> is strictly greater
     *    than <code>size()</code>
     */
    public void set(final long index0, final long index1, final boolean value)
    {
        if (index0 >= size)
        {
            throw new IndexOutOfBoundsException(index0 + " >= " + size);
        }
        if (index1 > size)
        {
            throw new IndexOutOfBoundsException(index1 + " > " + size);
        }

        for (long i = index0; i < index1; i++)
        {
            set(i, value);
        }
    }

    /**
     * Set the bit value at the specified index to the complement
     * of its current bit value.
     *
     * @param index index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @throws IndexOutOfBoundsException if <code>index</code> is negative
     *    or if <code>index</code> is greater than or equal to <code>size()</code>
     */
    public void flip(final long index)
    {
        if (index >= size)
        {
            throw new IndexOutOfBoundsException(index + " >= " + size);
        }

        int i = (int) (index / Integer.MAX_VALUE);
        int j = (int) (index % Integer.MAX_VALUE);

        BitSet bs = bitSets.get(i);
        bs.flip(j);
    }

    /**
     * Set all of the bit values from <code>index0</code>, inclusive, to
     * <code>index1</code>, exclusive, to the complement of their current
     * bit values.
     *
     * @param index0 first index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @param index1 second index, must be <code>&gt;= 0</code>, <code>&lt;= size()</code>
     *    and <code>&gt;= index0</code>
     * @throws IllegalArgumentException if <code>index1</code> is less than <code>index0</code>
     * @throws IndexOutOfBoundsException if either <code>index0</code>
     *    or <code>index1</code> are negative or if <code>index0</code> is greater than
     *    or equal to <code>size()</code> or if <code>index1</code> is strictly greater
     *    than <code>size()</code>
     */
    public void flip(final long index0, final long index1)
    {
        if (index0 >= size)
        {
            throw new IndexOutOfBoundsException(index0 + " >= " + size);
        }
        if (index1 > size)
        {
            throw new IndexOutOfBoundsException(index1 + " > " + size);
        }

        for (long i = index0; i < index1; i++)
        {
            flip(i);
        }
    }

    /**
     * Assign all values in this 1D bit matrix to <code>value</code>.
     *
     * @param value value
     * @return this 1D bit matrix, for convenience
     */
    public BitMatrix1D assign(final boolean value)
    {
        if (size > 0)
        {
            set(0, size, value);
        }
        return this;
    }

    /**
     * Return true if the specied 1D bit matrix has any bits set
     * to true that are also set to true in this 1D bit matrix.
     *
     * @param other other 1D bit matrix, must not be null and must
     *    be the same size as this 1D bit matrix
     * @return true if the specified 1D bit matrix has any bits set
     *    to true that are also set to true in this 1D bit matrix
     * @throws IllegalArgumentException if <code>other</code> is null
     *    or if <code>other</code> is not the same size as <code>this</code>
     */
    public boolean intersects(final BitMatrix1D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (size != other.size())
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }

        for (int i = 0, j = bitSets.size(); i < j; i++)
        {
            if (bitSets.get(i).intersects(other.getBitSets().get(i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Perform a logical <b>AND</b> of this 1D bit matrix
     * and the specified 1D bit matrix.
     *
     * @param other other 1D bit matrix, must not be null and must
     *    be the same size as this 1D bit matrix
     * @return this 1D bit matrix, for convenience
     * @throws IllegalArgumentException if <code>other</code> is null
     *    or if <code>other</code> is not the same size as <code>this</code>
     */
    public BitMatrix1D and(final BitMatrix1D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (!(size == other.size()))
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }

        for (int i = 0, j = bitSets.size(); i < j; i++)
        {
            bitSets.get(i).and(other.getBitSets().get(i));
        }
        return this;
    }

    /**
     * Clear all of the bits in this 1D bit matrix whose corresponding
     * bit is set in the specified 1D bit matrix.
     *
     * @param other other 1D bit matrix, must not be null and must
     *    be the same size as this 1D bit matrix
     * @return this 1D bit matrix, for convenience
     * @throws IllegalArgumentException if <code>other</code> is null
     *    or if <code>other</code> is not the same size as <code>this</code>
     */
    public BitMatrix1D andNot(final BitMatrix1D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (size != other.size())
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }

        for (int i = 0, j = bitSets.size(); i < j; i++)
        {
            bitSets.get(i).andNot(other.getBitSets().get(i));
        }
        return this;
    }

    /**
     * Perform a logical <b>OR</b> of this 1D bit matrix
     * and the specified 1D bit matrix.
     *
     * @param other other 1D bit matrix, must not be null and must
     *    be the same size as this 1D bit matrix
     * @return this 1D bit matrix, for convenience
     * @throws IllegalArgumentException if <code>other</code> is null
     *    or if <code>other</code> is not the same size as <code>this</code>
     */
    public BitMatrix1D or(final BitMatrix1D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (size != other.size())
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }

        for (int i = 0, j = bitSets.size(); i < j; i++)
        {
            bitSets.get(i).or(other.getBitSets().get(i));
        }
        return this;
    }

    /**
     * Perform a logical <b>XOR</b> of this 1D bit matrix
     * and the specified 1D bit matrix.
     *
     * @param other other 1D bit matrix, must not be null and must
     *    be the same size as this 1D bit matrix
     * @return this 1D bit matrix, for convenience
     * @throws IllegalArgumentException if <code>other</code> is null
     *    or if <code>other</code> is not the same size as <code>this</code>
     */
    public BitMatrix1D xor(final BitMatrix1D other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (size != other.size())
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }

        for (int i = 0, j = bitSets.size(); i < j; i++)
        {
            bitSets.get(i).xor(other.getBitSets().get(i));
        }
        return this;
    }

    /**
     * Apply the specified procedure to each index in this 1D bit
     * matrix with a bit equal to the specified value.
     *
     * @param value value
     * @param procedure procedure, must not be null
     */
    public void forEach(final boolean value, final UnaryProcedure<Long> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long index = 0; index < size; index++)
        {
            if (get(index) == value)
            {
                procedure.run(index);
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
        if (!(o instanceof BitMatrix1D))
        {
            return false;
        }

        BitMatrix1D bm = (BitMatrix1D) o;

        if (size != bm.size())
        {
            return false;
        }

        for (int i = 0, j = bitSets.size(); i < j; i++)
        {
            if (!bitSets.get(i).equals(bm.getBitSets().get(i)))
            {
                return false;
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        // TODO
        return 0;
    }
}
