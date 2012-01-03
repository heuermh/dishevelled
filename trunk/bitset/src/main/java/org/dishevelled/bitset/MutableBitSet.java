/*

    dsh-bitset  High performance bit set implementations.
    Copyright (c) 2011-2012 held jointly by the individual authors.

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

import java.util.Arrays;

/**
 * Mutable bit set.
 */
public class MutableBitSet extends AbstractBitSet implements Serializable /*, Cloneable */ {
    /** Default number of bits, <code>64</code>. */
    public static final long DEFAULT_NUM_BITS = 64;

    protected long[] bits;
    protected long numBits;
    protected int wlen;


    /**
     * Create a new mutable bit set with the default number of bits.
     *
     * @see #DEFAULT_NUM_BITS
     */
    public MutableBitSet() {
        this(DEFAULT_NUM_BITS);
    }

    /**
     * Create a new mutable bit set with the specified number of bits.
     *
     * @param numBits number of bits
     */
    public MutableBitSet(final long numBits) {
        this.numBits = numBits;
        bits = new long[bits2words(numBits)];
        wlen = bits.length;
    }

    /**
     * Create a new mutable bit set from the specified <code>long[]</code>.
     *
     * @param bits bits stored in <code>long[]</code>
     * @param wlen number of words/elements used in <code>bits</code>
     */
    public MutableBitSet(final long[] bits, final int wlen) {
        this(bits.clone(), wlen * 64, wlen);
    }

    /**
     * Create a new mutable bit set from the specified <code>long[]</code>.
     *
     * @param bits bits stored in <code>long[]</code>
     * @param numBits number of bits
     * @param wlen number of words/elements used in <code>bits</code>
     */
    protected MutableBitSet(final long[] bits, final long numBits, final int wlen) {
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
        return cardinality() == 0;
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
    public long nextSetBit(final long index) {
        int i = (int) (index >>> 6);
        if (i >= wlen) {
            return -1;
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
        return -1;
    }

    @Override
    public long prevSetBit(final long index) {
        int i = (int) (index >> 6);
        final int subIndex;
        long word;
        if (i >= wlen) {
            i = wlen - 1;
            if (i < 0) {
                return -1;
            }
            subIndex = 63; // last possible bit
            word = bits[i];
        } else {
            if (i < 0) {
                return -1;
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
        return -1;
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
        int wordNum = expandingWordNum(index);
        int bit = (int) index & 0x3f;
        long bitmask = 1L << bit;
        bits[wordNum] |= bitmask;
    }

    @Override
    public void set(final long startIndex, final long endIndex) {
        if (endIndex <= startIndex) {
            return;
        }

        int startWord = (int) (startIndex >> 6);

        // since endIndex is one past the end, this is index of the last
        // word to be changed.
        int endWord = expandingWordNum(endIndex - 1);

        long startmask = -1L << startIndex;
        long endmask = -1L >>> -endIndex; // 64-(endIndex&0x3f) is the same as
                                          // -endIndex due to wrap

        if (startWord == endWord) {
            bits[startWord] |= (startmask & endmask);
            return;
        }

        bits[startWord] |= startmask;
        Arrays.fill(bits, startWord + 1, endWord, -1L);
        bits[endWord] |= endmask;
    }

    @Override
    public void setQuick(final long index) {
        assert index >= 0 && index < numBits;
        int wordNum = (int) (index >> 6);
        int bit = (int) index & 0x3f;
        long bitmask = 1L << bit;
        bits[wordNum] |= bitmask;
    }

    @Override
    public void clear(final long index) {
        int wordNum = (int) (index >> 6); // div 64
        if (wordNum >= wlen) {
            return;
        }
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        bits[wordNum] &= ~bitmask;
    }

    @Override
    public void clear(final long startIndex, final long endIndex) {
        if (endIndex <= startIndex) {
            return;
        }

        int startWord = (int) (startIndex >> 6);
        if (startWord >= wlen) {
            return;
        }

        // since endIndex is one past the end, this is index of the last
        // word to be changed.
        int endWord = (int) ((endIndex - 1) >> 6);

        long startmask = -1L << startIndex;
        long endmask = -1L >>> -endIndex; // 64-(endIndex&0x3f) is the same as
                                          // -endIndex due to wrap

        // invert masks since we are clearing
        startmask = ~startmask;
        endmask = ~endmask;

        if (startWord == endWord) {
            bits[startWord] &= (startmask | endmask);
            return;
        }

        bits[startWord] &= startmask;

        int middle = Math.min(wlen, endWord);
        Arrays.fill(bits, startWord + 1, middle, 0L);
        if (endWord < wlen) {
            bits[endWord] &= endmask;
        }
    }

    @Override
    public void clearQuick(final long index) {
        assert index >= 0 && index < numBits;
        int wordNum = (int) (index >> 6); // div 64
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        bits[wordNum] &= ~bitmask;
    }

    @Override
    public boolean getAndSet(final long index) {
        assert index >= 0 && index < numBits;
        int wordNum = (int) (index >> 6); // div 64
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        boolean val = (bits[wordNum] & bitmask) != 0;
        bits[wordNum] |= bitmask;
        return val;
    }

    @Override
    public void flip(final long index) {
        int wordNum = expandingWordNum(index);
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        bits[wordNum] ^= bitmask;
    }

    @Override
    public void flip(final long startIndex, final long endIndex) {
        if (endIndex <= startIndex) {
            return;
        }
        int startWord = (int) (startIndex >> 6);

        // since endIndex is one past the end, this is index of the last
        // word to be changed.
        int endWord = expandingWordNum(endIndex - 1);

        /***
         * Grrr, java shifting wraps around so -1L>>>64 == -1
         * for that reason, make sure not to use endmask if the bits to flip
         * will be zero in the last word (redefine endWord to be the last changed...)
         * long startmask = -1L << (startIndex & 0x3f); // example: 11111...111000
         * long endmask = -1L >>> (64-(endIndex & 0x3f)); // example: 00111...111111
         ***/

        long startmask = -1L << startIndex;
        long endmask = -1L >>> -endIndex; // 64-(endIndex&0x3f) is the same as
                                          // -endIndex due to wrap

        if (startWord == endWord) {
            bits[startWord] ^= (startmask & endmask);
            return;
        }

        bits[startWord] ^= startmask;

        for (int i = startWord + 1; i < endWord; i++) {
            bits[i] = ~bits[i];
        }

        bits[endWord] ^= endmask;
    }

    @Override
    public void flipQuick(final long index) {
        assert index >= 0 && index < numBits;
        int wordNum = (int) (index >> 6); // div 64
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        bits[wordNum] ^= bitmask;
    }

    @Override
    public boolean flipAndGet(final long index) {
        assert index >= 0 && index < numBits;
        int wordNum = (int) (index >> 6); // div 64
        int bit = (int) index & 0x3f; // mod 64
        long bitmask = 1L << bit;
        bits[wordNum] ^= bitmask;
        return (bits[wordNum] & bitmask) != 0;
    }

    @Override
    public void ensureCapacity(final long numBits) {
        ensureCapacityWords(bits2words(numBits));
    }

    @Override
    public void trimTrailingZeros() {
        int idx = wlen - 1;
        while (idx >= 0 && bits[idx] == 0) {
            idx--;
        }
        wlen = idx + 1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This mutable bit set is modified in place and a reference to this is returned for method chaining.</p>
     */
    @Override
    public AbstractBitSet xor(final AbstractBitSet other) {
        int newLen = Math.max(wlen, other.wlen());
        ensureCapacityWords(newLen);
        assert (numBits = Math.max(other.numBits(), numBits)) >= 0;

        long[] thisArr = this.bits;
        long[] otherArr = other.bits();
        int pos = Math.min(wlen, other.wlen());
        while (--pos >= 0) {
            thisArr[pos] ^= otherArr[pos];
        }
        if (this.wlen < newLen) {
            System.arraycopy(otherArr, this.wlen, thisArr, this.wlen, newLen - this.wlen);
        }
        this.wlen = newLen;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This mutable bit set is modified in place and a reference to this is returned for method chaining.</p>
     */
    @Override
    public AbstractBitSet and(final AbstractBitSet other) {
        int newLen = Math.min(this.wlen, other.wlen());
        long[] thisArr = this.bits;
        long[] otherArr = other.bits();
        // testing against zero can be more efficient
        int pos = newLen;
        while (--pos >= 0) {
            thisArr[pos] &= otherArr[pos];
        }
        if (this.wlen > newLen) {
            // fill zeros from the new shorter length to the old length
            Arrays.fill(bits, newLen, this.wlen, 0);
        }
        this.wlen = newLen;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This mutable bit set is modified in place and a reference to this is returned for method chaining.</p>
     */
    @Override
    public AbstractBitSet or(final AbstractBitSet other) {
        int newLen = Math.max(wlen, other.wlen());
        ensureCapacityWords(newLen);
        assert (numBits = Math.max(other.numBits(), numBits)) >= 0;

        long[] thisArr = this.bits;
        long[] otherArr = other.bits();
        int pos = Math.min(wlen, other.wlen());
        while (--pos >= 0) {
            thisArr[pos] |= otherArr[pos];
        }
        if (this.wlen < newLen) {
            System.arraycopy(otherArr, this.wlen, thisArr, this.wlen, newLen - this.wlen);
        }
        this.wlen = newLen;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This mutable bit set is modified in place and a reference to this is returned for method chaining.</p>
     */
    @Override
    public AbstractBitSet andNot(final AbstractBitSet other) {
        int idx = Math.min(wlen, other.wlen());
        long[] thisArr = this.bits;
        long[] otherArr = other.bits();
        while (--idx >= 0) {
            thisArr[idx] &= ~otherArr[idx];
        }
        return this;
    }

    /**
     * Return a new immutable copy of this mutable bit set.
     *
     * @return a new immutable copy of this mutable bit set
     */
    public ImmutableBitSet immutableCopy() {
        return new ImmutableBitSet(bits, wlen); // bits is cloned in ctr
    }

    /**
     * Return a new unsafe copy of this mutable bit set.
     *
     * @return a new unsafe copy of this mutable bit set
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
        if (!(o instanceof MutableBitSet)) {
            return false;
        }
        MutableBitSet a;
        MutableBitSet b = (MutableBitSet) o;
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

    private void ensureCapacityWords(final int numWords) {
        if (bits.length < numWords) {
           bits = grow(bits, numWords);
       }
   }

    private int expandingWordNum(final long index) {
        int wordNum = (int) (index >> 6);
        if (wordNum >= wlen) {
            ensureCapacity(index + 1);
            wlen = wordNum + 1;
        }
        assert (numBits = Math.max(numBits, index + 1)) >= 0;
        return wordNum;
    }

    /**
     * Return the cardinality of the logical NOT followed by AND of the two specified mutable bit sets.  Neither
     * set is modified.
     *
     * @param a first mutable bit set, must not be null
     * @param b second mutable bit set, must not be null
     * @return the cardinality of the logical NOT followed by AND of the two specified mutable bit sets
     */
    public static long andNotCount(final MutableBitSet a, final MutableBitSet b) {
        long tot = BitUtil.pop_andnot(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
        if (a.wlen > b.wlen) {
            tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
        }
        return tot;
    }

    /**
     * Return the cardinality of the logical AND of the two specified mutable bit sets.  Neither set is modified.
     *
     * @param a first mutable bit set, must not be null
     * @param b second mutable bit set, must not be null
     * @return the cardinality of the logical AND of the two specified mutable bit sets
     */
    public static long andCount(final MutableBitSet a, final MutableBitSet b) {
        return BitUtil.pop_intersect(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
    }

    /**
     * Return the cardinality of the logical OR of the two specified mutable bit sets.  Neither set is modified.
     *
     * @param a first mutable bit set, must not be null
     * @param b second mutable bit set, must not be null
     * @return the cardinality of the logical OR of the two specified mutable bit sets
     */
    public static long orCount(final MutableBitSet a, final MutableBitSet b) {
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
     * Return the cardinality of the logical XOR of the two specified mutable bit sets.
     *
     * @param a first mutable bit set, must not be null
     * @param b second mutable bit set, must not be null
     * @return the cardinality of the logical XOR of the two specified mutable bit sets
     */
    public static long xorCount(final MutableBitSet a, final MutableBitSet b) {
        long tot = BitUtil.pop_xor(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
        if (a.wlen < b.wlen) {
            tot += BitUtil.pop_array(b.bits, a.wlen, b.wlen - a.wlen);
        }
        else if (a.wlen > b.wlen) {
            tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
        }
        return tot;
    }

    /**
     * Return the number of 64 bit words it would take to hold the specified number of bits.
     *
     * @param numBits number of bits
     * @return the number of 64 bit words it would take to hold the specified number of bits
     */
    private static int bits2words(final long numBits) {
        return (int) (((numBits - 1) >>> 6) + 1);
    }
}
