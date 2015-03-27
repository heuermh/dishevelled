/*

    dsh-bitset  High performance bit set implementations.
    Copyright (c) 2011-2015 held jointly by the individual authors.

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

import java.io.Serializable;

import org.dishevelled.functor.UnaryProcedure;

/**
 * Immutable bit set.
 */
public final class ImmutableBitSet extends AbstractBitSet implements Serializable {
    private final long[] bits;
    private final long numBits;
    private final int wlen;


    /**
     * Create a new immutable bit set with bits copied from the specified <code>long[]</code>.
     *
     * @param bits bits to copy, stored in <code>long[]</code>
     * @param wlen number of words/elements used in <code>bits</code>
     */
    public ImmutableBitSet(final long[] bits, final int wlen) {
        this(bits.clone(), wlen * 64, wlen);
    }

    /**
     * Create a new immutable bit set from the specified <code>long[]</code>.
     *
     * @param bits bits stored in <code>long[]</code>
     * @param numBits number of bits
     * @param wlen number of words/elements used in <code>bits</code>
     */
    private ImmutableBitSet(final long[] bits, final long numBits, final int wlen) {
        this.bits = bits;
        this.wlen = wlen;
        this.numBits = numBits;
    }


    @Override
    public long capacity() {
        return bits.length << 6;
    }

    @Override
    public long cardinality() {
        return BitUtil.pop_array(bits, 0, wlen);
    }

    @Override
    public boolean isEmpty() {
        return cardinality() == 0L;
    }

    @Override
    public boolean get(final long index) {
        int i = (int) (index >> 6); // div 64
        if (i >= bits.length) {
            return false;
        }
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        return (bits[i] & bitmask) != 0;
    }

    @Override
    public boolean getQuick(final long index) {
        assert index >= 0 && index < numBits;
        int i = (int) (index >> 6); // div 64
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        return (bits[i] & bitmask) != 0;
    }

    @Override
    public long nextClearBit(final long index) {
        if ((int) (index >>> 6) >= wlen) {
            return -1L;
        }
        for (long i = index, last = prevSetBit(capacity()); i < last; i++) {
            if (!get(i)) {
                return i;
            }
        }
        return -1L;
    }

    @Override
    public long prevClearBit(final long index) {
        if ((int) (index >>> 6) >= wlen) {
            return -1L;
        }
        for (long i = Math.min(index, prevSetBit(capacity())); i > 0L; i--) {
            if (!get(i)) {
                return i;
            }
        }
        return -1L;
    }

    @Override
    public void forEachClearBit(final UnaryProcedure<Long> procedure) {
        if (procedure == null) {
            throw new NullPointerException("procedure must not be null");
        }
        for (long i = nextClearBit(0L); i >= 0L; i = nextClearBit(i + 1L)) {
            procedure.run(i);
        }
    }

    @Override
    public long nextSetBit(final long index) {
        int i = (int) (index >>> 6);
        if (i >= wlen) {
            return -1L;
        }
        int subIndex = (int) index & 0x3f; // index within the word
        long word = bits[i] >>> subIndex; // skip all the bits to the right of index

        if (word != 0) {
            return (((long) i) << 6) + (subIndex + BitUtil.ntz(word));
        }

        while (++i < wlen) {
            word = bits[i];
            if (word != 0) {
                return (((long) i) << 6) + BitUtil.ntz(word);
            }
        }
        return -1L;
    }

    @Override
    public long prevSetBit(final long index) {
        int i = (int) (index >> 6);
        final int subIndex;
        long word;
        if (i >= wlen) {
            i = wlen - 1;
            if (i < 0) {
                return -1L;
            }
            subIndex = 63; // last possible bit
            word = bits[i];
        }
        else {
            if (i < 0) {
                return -1L;
            }
            subIndex = (int) index & 0x3f; // index within the word
            word = (bits[i] << (63 - subIndex)); // skip all the bits to the left of index
        }

        if (word != 0) {
            return (((long) i) << 6) + subIndex - Long.numberOfLeadingZeros(word); // See LUCENE-3197
        }

        while (--i >= 0) {
            word = bits[i];
            if (word != 0) {
                return (((long) i) << 6) + 63 - Long.numberOfLeadingZeros(word);
            }
        }
        return -1L;
    }

    @Override
    public void forEachSetBit(final UnaryProcedure<Long> procedure) {
        if (procedure == null) {
            throw new NullPointerException("procedure must not be null");
        }
        for (long i = nextSetBit(0L); i >= 0L; i = nextSetBit(i + 1L)) {
            procedure.run(i);
        }
    }

    @Override
    public boolean intersects(final AbstractBitSet other) {
        int pos = Math.min(this.wlen, other.wlen());
        long[] thisArr = this.bits;
        long[] otherArr = other.bits();
        while (--pos >= 0) {
            if ((thisArr[pos] & otherArr[pos]) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void set(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(final long startIndex, final long endIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setQuick(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear(final long startIndex, final long endIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearQuick(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndSet(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flip(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flip(final long startIndex, final long endIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flipQuick(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean flipAndGet(final long index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void ensureCapacity(final long numBits) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trimTrailingZeros() {
        throw new UnsupportedOperationException();
    }

    /**
     * Perform a logical XOR of the specified bit set and this bit set.
     *
     * <p>A reference to a copy of this immutable bit set is returned for method chaining.</p>
     *
     * @param other bit set to XOR with this bit set, must not be null
     * @return a copy of this immutable bit set, for method chaining
     */
    public ImmutableBitSet xor(final AbstractBitSet other) {
        int newLen = Math.max(wlen, other.wlen());
        long[] thisArr = this.bits.clone();
        long[] otherArr = other.bits();
        int pos = Math.min(wlen, other.wlen());
        while (--pos >= 0) {
            thisArr[pos] ^= otherArr[pos];
        }
        if (this.wlen < newLen) {
            thisArr = grow(thisArr, newLen);
            System.arraycopy(otherArr, this.wlen, thisArr, this.wlen, newLen - this.wlen);
        }
        return new ImmutableBitSet(thisArr, newLen * 64, newLen);
    }

    /**
     * Perform a logical AND of the specified bit set and this bit set.  Also known as a union operation.
     *
     * <p>A reference to a copy of this immutable bit set is returned for method chaining.</p>
     *
     * @param other bit set to AND with this bit set, must not be null
     * @return a copy of this immutable bit set, for method chaining
     */
    public ImmutableBitSet and(final AbstractBitSet other) {
        int newLen = Math.min(this.wlen, other.wlen());
        long[] thisArr = this.bits.clone();
        long[] otherArr = other.bits();
        // testing against zero can be more efficient
        int pos = newLen;
        while (--pos >= 0) {
            thisArr[pos] &= otherArr[pos];
        }
        // if (this.wlen > newLen) {
        // fill zeros from the new shorter length to the old length
        // Arrays.fill(bits,newLen,this.wlen,0);
        // }
        //this.wlen = newLen;
        return new ImmutableBitSet(thisArr, newLen * 64, newLen);
    }

    /**
     * Perform a logical OR of the specified bit set and this bit set.  Also known as an intersect operation.
     *
     * <p>A reference to a copy of this immutable bit set is returned for method chaining.</p>
     *
     * @param other bit set to OR with this bit set, must not be null
     * @return a copy of this immutable bit set, for method chaining
     */
    public ImmutableBitSet or(final AbstractBitSet other) {
        int newLen = Math.max(wlen, other.wlen());
        long[] thisArr = this.bits.clone();
        long[] otherArr = other.bits();
        int pos = Math.min(wlen, other.wlen());
        while (--pos >= 0) {
            thisArr[pos] |= otherArr[pos];
        }
        if (this.wlen < newLen) {
            thisArr = grow(thisArr, newLen);
            System.arraycopy(otherArr, this.wlen, thisArr, this.wlen, newLen - this.wlen);
        }
        return new ImmutableBitSet(thisArr, newLen * 64, newLen);
    }

    /**
     * Perform a logical NOT followed by AND of the specified bit set and this bit set.  Also known as a remove operation.
     *
     * <p>A reference to a copy of this immutable bit set is returned for method chaining.</p>
     *
     * @param other bit set to NOT followed by AND with this bit set, must not be null
     * @return a copy of this immutable bit set, for method chaining
     */
    public ImmutableBitSet andNot(final AbstractBitSet other) {
        int idx = Math.min(wlen, other.wlen());
        long[] thisArr = this.bits.clone();
        long[] otherArr = other.bits();
        while (--idx >= 0) {
            thisArr[idx] &= ~otherArr[idx];
        }
        return new ImmutableBitSet(thisArr, wlen * 64, wlen);
    }

    /**
     * Return a new mutable copy of this immutable bit set.
     *
     * @return a new mutable copy of this immutable bit set
     */
    public MutableBitSet mutableCopy() {
        return new MutableBitSet(bits, wlen); // bits is cloned in ctr
    }

    /**
     * Return a new unsafe copy of this immutable bit set.
     *
     * @return a new unsafe copy of this immutable bit set
     */
    public UnsafeBitSet unsafeCopy() {
        return new UnsafeBitSet(bits.clone(), wlen);
    }

    /* no long version, unfortunately
    @Override
    public int getBit(final int index) {
        assert index >= 0 && index < numBits;
        int i = index >> 6; // div 64
        int bit = index & 0x3f; // mod 64
        return ((int) (bits[i] >>> bit)) & 0x01;
    }
    */

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImmutableBitSet)) {
            return false;
        }
        ImmutableBitSet a;
        ImmutableBitSet b = (ImmutableBitSet) o;
        // make a the larger set.
        if (b.wlen > this.wlen) {
            a = b;
            b = this;
        }
        else {
            a = this;
        }

        // check for any set bits out of the range of b
        for (int i = a.wlen - 1; i >= b.wlen; i--) {
            if (a.bits[i] != 0) {
                return false;
            }
        }

        for (int i = b.wlen - 1; i >= 0; i--) {
            if (a.bits[i] != b.bits[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        // Start with a zero hash and use a mix that results in zero if the input is zero.
        // This effectively truncates trailing zeros without an explicit check.
        long h = 0;
        for (int i = bits.length; --i >= 0;) {
            h ^= bits[i];
            h = (h << 1) | (h >>> 63); // rotate left
        }
        // fold leftmost bits into right and add a constant to prevent
        // empty sets from returning 0, which is too common.
        return (int) ((h >> 32) ^ h) + 0x98761234;
    }

    @Override
    protected long[] bits() {
        return bits;
    }

    @Override
    protected long numBits() {
        return numBits;
    }

    @Override
    protected int wlen() {
        return wlen;
    }

    /**
     * Return a new immutable bit set representing the logical NOT followed by AND of the two specified immutable
     * bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return a new immutable bit set representing the logical NOT followed by AND of the two specified immutable
     *    bit sets
     */
    public static ImmutableBitSet andNot(final ImmutableBitSet a, final ImmutableBitSet b) {
        return ((MutableBitSet) new MutableBitSet().or(a).andNot(b)).immutableCopy();
    }

    /**
     * Return the cardinality of the logical NOT followed by AND of the two specified immutable bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return the cardinality of the logical NOT followed by AND of the two specified immutable bit sets
     */
    public static long andNotCount(final ImmutableBitSet a, final ImmutableBitSet b) {
        long tot = BitUtil.pop_andnot(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
        if (a.wlen > b.wlen) {
            tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
        }
        return tot;
    }

    /**
     * Return a new immutable bit set representing the logical AND of the two specified immutable bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return a new immutable bit set representing the logical AND of the two specified immutable bit sets
     */
    public static ImmutableBitSet and(final ImmutableBitSet a, final ImmutableBitSet b) {
        return ((MutableBitSet) new MutableBitSet().or(a).and(b)).immutableCopy();
    }

    /**
     * Return the cardinality of the logical AND of the two specified immutable bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return the cardinality of the logical AND of the two specified immutable bit sets
     */
    public static long andCount(final ImmutableBitSet a, final ImmutableBitSet b) {
        return BitUtil.pop_intersect(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
    }

    /**
     * Return a new immutable bit set representing the logical OR of the two specified immutable bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return a new immutable bit set representing the logical OR of the two specified immutable bit sets
     */
    public static ImmutableBitSet or(final ImmutableBitSet a, final ImmutableBitSet b) {
        return ((MutableBitSet) new MutableBitSet().or(a).or(b)).immutableCopy();
    }

    /**
     * Return the cardinality of the logical OR of the two specified immutable bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return the cardinality of the logical OR of the two specified immutable bit sets
     */
    public static long orCount(final ImmutableBitSet a, final ImmutableBitSet b) {
        long tot = BitUtil.pop_union(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
        if (a.wlen < b.wlen) {
            tot += BitUtil.pop_array(b.bits, a.wlen, b.wlen - a.wlen);
        }
        else if (a.wlen > b.wlen) {
            tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
        }
        return tot;
    }

    /**
     * Return a new immutable bit set representing the logical XOR of the two specified immutable bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return a new immutable bit set representing the logical XOR of the two specified immutable bit sets
     */
    public static ImmutableBitSet xor(final ImmutableBitSet a, final ImmutableBitSet b) {
        return ((MutableBitSet) new MutableBitSet().or(a).xor(b)).immutableCopy();
    }

    /**
     * Return the cardinality of the logical XOR of the two specified immutable bit sets.
     *
     * @param a first immutable bit set, must not be null
     * @param b second immutable bit set, must not be null
     * @return the cardinality of the logical XOR of the two specified immutable bit sets
     */
    public static long xorCount(final ImmutableBitSet a, final ImmutableBitSet b) {
        long tot = BitUtil.pop_xor(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
        if (a.wlen < b.wlen) {
            tot += BitUtil.pop_array(b.bits, a.wlen, b.wlen - a.wlen);
        }
        else if (a.wlen > b.wlen) {
            tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
        }
        return tot;
    }
}
