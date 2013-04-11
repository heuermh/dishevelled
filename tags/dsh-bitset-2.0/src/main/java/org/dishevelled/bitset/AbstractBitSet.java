/*

    dsh-bitset  High performance bit set implementations.
    Copyright (c) 2011-2013 held jointly by the individual authors.

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

// derived from org.apache.lucene.util.OpenBitSet.java, original license header
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dishevelled.bitset;

/**
 * Abstract bit set.
 */
public abstract class AbstractBitSet {

    /**
     * Return the capacity of this bit set.
     *
     * @return the capacity of this bit set
     */
    public abstract long capacity(); // 1 greater than the index of the last bit

    /**
     * Return the cardinality of this bit set, that is the number of bits set to true.
     *
     * @return  the cardinality of this bit set, that is the number of bits set to true
     */
    public abstract long cardinality();

    /**
     * Return true if the cardinality of this bit set is zero.
     *
     * @return true if the cardinality of this bit set is zero
     */
    public abstract boolean isEmpty();

    /**
     * Return true if the bit at the specified index is set.
     *
     * @param index index
     * @return true if the bit at the specified index is set
     */
    public abstract boolean get(long index);

    /**
     * Return true if the bit at the specified index is set, without checking bounds.
     *
     * @param index index
     * @return true if the bit at the specified index is set, without checking bounds
     */
    public abstract boolean getQuick(long index);

    /**
     * Return the index of the next bit set to false on or after the specified index, or
     * <code>-1</code> if no such bit exists.
     *
     * @param index index
     * @return the index of the next bit set to false on or after the specified index, or
     *    <code>-1</code> if no such bit exists
     */
    public abstract long nextClearBit(long index);

    /**
     * Return the index of the previous bit set to false on or before the specified index, or
     * <code>-1</code> if no such bit exists.
     *
     * @param index index
     * @return the index of the previous bit set to false on or before the specified index, or
     *    <code>-1</code> if no such bit exists
     */
    public abstract long prevClearBit(long index);

    /**
     * Return the index of the next bit set to true on or after the specified index, or
     * <code>-1</code> if no such bit exists.
     *
     * @param index index
     * @return the index of the next bit set to true on or after the specified index, or
     *    <code>-1</code> if no such bit exists
     */
    public abstract long nextSetBit(long index);

    /**
     * Return the index of the previous bit set to true on or before the specified index, or
     * <code>-1</code> if no such bit exists.
     *
     * @param index index
     * @return the index of the previous bit set to true on or before the specified index, or
     *    <code>-1</code> if no such bit exists
     */
    public abstract long prevSetBit(long index);

    /**
     * Return true if the specified bit set has any bits set to true that are also set to true
     * in this bit set.
     *
     * @param other bit set to compare, must not be null
     * @return true if the specified bit set has any bits set to true that are also set to true
     *    in this bit set
     */
    public abstract boolean intersects(AbstractBitSet other);


    //public abstract void set(Predicate<Long> predicate); ?
    //public abstract void forEachSetBit(Procedure<Long> procedure);
    //public abstract void forEachUnsetBit(Procedure<Long> procedure);


    // optional operations

    /**
     * Set the bit at the specified index in this bit set to true (optional operation).
     *
     * @param index index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void set(long index);

    /**
     * Set the bits from the specified range <code>startIndex</code> (inclusive) to <code>endIndex</code> (exclusive)
     * in this bit set to true (optional operation).
     *
     * @param startIndex start index
     * @param endIndex end index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void set(long startIndex, long endIndex);

    /**
     * Set the bit at the specified index in this bit set to true, without checking bounds (optional operation).
     *
     * @param index index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void setQuick(long index);

    /**
     * Set the bit at the specified index in this bit set to false (optional operation).
     *
     * @param index index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void clear(long index);

    /**
     * Set the bits from the specified range <code>startIndex</code> (inclusive) to <code>endIndex</code> (exclusive)
     * in this bit set to false (optional operation).
     *
     * @param startIndex start index
     * @param endIndex end index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void clear(long startIndex, long endIndex);

    /**
     * Set the bit at the specified index in this bit set to false, without checking bounds (optional operation).
     *
     * @param index index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void clearQuick(long index);

    /**
     * Set the bit at the specified index in this bit set to true and return its previous value (optional operation).
     *
     * @param index index
     * @return  the previous value of the bit at the specified index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract boolean getAndSet(long index);

    /**
     * Set the bit at the specified index in this bit set to the compliment of its current value (optional operation).
     *
     * @param index index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void flip(long index);

    /**
     * Set each bit from the specified range <code>startIndex</code> (inclusive) to <code>endIndex</code> (exclusive)
     * in this bit set to the compliment of its current value (optional operation).
     *
     * @param startIndex start index
     * @param endIndex end index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void flip(long startIndex, long endIndex);

    /**
     * Set the bit at the specified index in this bit set to the compliment of its current value, without checking bounds (optional operation).
     *
     * @param index index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void flipQuick(long index);

    /**
     * Set the bit at the specified index in this bit set to the complement of its current value and return its previous value (optional operation).
     *
     * @param index index
     * @return  the previous value of the bit at the specified index
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract boolean flipAndGet(long index);

    /**
     * Ensure capacity in this bit set for the specified number of bits (optional operation).
     *
     * @param numBits number of bits
     * @throws UnsupportedOperationException if this operation is not supported by this bit set
     */
    public abstract void ensureCapacity(long numBits);

    /**
     * Trim all trailing zeros from the <code>long[]</code> backing this bit set (optional operation).
     */
    public abstract void trimTrailingZeros();


    // logical operations
    //   is it possible to return the specific subclass?

    /**
     * Perform a logical XOR of the specified bit set and this bit set.
     *
     * @param other bit set to XOR with this bit set, must not be null
     * @return this bit set or a copy of this bit set, for method chaining
     */
    public abstract AbstractBitSet xor(AbstractBitSet other);

    /**
     * Perform a logical AND of the specified bit set and this bit set.  Also known as a union operation.
     *
     * @param other bit set to AND with this bit set, must not be null
     * @return this bit set or a copy of this bit set, for method chaining
     */
    public abstract AbstractBitSet and(AbstractBitSet other);

    /**
     * Perform a logical OR of the specified bit set and this bit set.  Also known as an intersect operation.
     *
     * @param other bit set to OR with this bit set, must not be null
     * @return this bit set or a copy of this bit set, for method chaining
     */
    public abstract AbstractBitSet or(AbstractBitSet other);

    /**
     * Perform a logical NOT followed by AND of the specified bit set and this bit set.  Also known as a remove operation.
     *
     * @param other bit set to NOT followed by AND with this bit set, must not be null
     * @return this bit set or a copy of this bit set, for method chaining
     */
    public abstract AbstractBitSet andNot(AbstractBitSet other);

    /**
     * Return the <code>long[]</code> backing this bit set.
     *
     * @return the <code>long[]</code> backing this bit set
     */
    protected abstract long[] bits();

    /**
     * Return the number of bits in this bit set.
     *
     * @return the number of bits in this bet set
     */
    protected abstract long numBits();

    /**
     * Return the number of words in <code>bits()</code> used for storage.
     *
     * @return the number of words in <code>bits()</code> used for storage
     */
    protected abstract int wlen();

    // from RamUsageEstimator.java
    private final static int NUM_BYTES_LONG = 8;

    // from ArrayUtil.java
    protected static long[] grow(final long[] array, final int minSize) {
        assert minSize >= 0 : "size must be positive (got " + minSize + "): likely integer overflow?";
        if (array.length < minSize) {
            long[] newArray = new long[oversize(minSize, NUM_BYTES_LONG)];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        }
        else {
            return array;
        }
    }

    private static int oversize(final int minTargetSize, final int bytesPerElement) {
        if (minTargetSize < 0) {
            // catch usage that accidentally overflows int
            throw new IllegalArgumentException("invalid array size " + minTargetSize);
        }

        if (minTargetSize == 0) {
            // wait until at least one element is requested
            return 0;
        }

        // asymptotic exponential growth by 1/8th, favors
        // spending a bit more CPU to not tie up too much wasted
        // RAM:
        int extra = minTargetSize >> 3;

        if (extra < 3) {
            // for very small arrays, where constant overhead of
            // realloc is presumably relatively high, we grow
            // faster
            extra = 3;
        }

        int newSize = minTargetSize + extra;

        // add 7 to allow for worst case byte alignment addition below:
        if (newSize + 7 < 0) {
            // int overflowed -- return max allowed array size
            return Integer.MAX_VALUE;
        }

        if (JRE_IS_64BIT) {
            // round up to 8 byte alignment in 64bit env
            switch (bytesPerElement) {
            case 4:
                // round up to multiple of 2
                return (newSize + 1) & 0x7ffffffe;
            case 2:
                // round up to multiple of 4
                return (newSize + 3) & 0x7ffffffc;
            case 1:
                // round up to multiple of 8
                return (newSize + 7) & 0x7ffffff8;
            case 8:
                // no rounding
            default:
                // odd (invalid?) size
                return newSize;
            }
        }
        else {
            // round up to 4 byte alignment in 64bit env
            switch (bytesPerElement) {
            case 2:
                // round up to multiple of 2
                return (newSize + 1) & 0x7ffffffe;
            case 1:
                // round up to multiple of 4
                return (newSize + 3) & 0x7ffffffc;
            case 4:
            case 8:
                // no rounding
            default:
                // odd (invalid?) size
                return newSize;
            }
        }
    }

    // from Constants.java
    private static final String OS_ARCH = System.getProperty("os.arch");
    private static final boolean JRE_IS_64BIT;

    static {
        // NOTE: this logic may not be correct; if you know of a
        // more reliable approach please raise it on java-dev!
        final String x = System.getProperty("sun.arch.data.model");
        if (x != null) {
            JRE_IS_64BIT = x.indexOf("64") != -1;
        } else {
            if (OS_ARCH != null && OS_ARCH.indexOf("64") != -1) {
                JRE_IS_64BIT = true;
            } else {
                JRE_IS_64BIT = false;
            }
        }
    }
}