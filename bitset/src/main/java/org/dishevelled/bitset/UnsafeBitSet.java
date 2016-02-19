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

/**
 * Unsafe bit set.
 */
public class UnsafeBitSet extends MutableBitSet {

    /**
     * Create a new unsafe bit set with the default number of bits.
     *
     * @see #DEFAULT_NUM_BITS
     */
    public UnsafeBitSet() {
        super();
    }

    /**
     * Create a new unsafe bit set with the specified number of bits.
     *
     * @param numBits number of bits
     */
    public UnsafeBitSet(final long numBits) {
        super(numBits);
    }

    /**
     * Create a new unsafe bit set from the specified <code>long[]</code>.
     *
     * @param bits bits stored in <code>long[]</code>
     * @param wlen number of words/elements used in <code>bits</code>
     */
    public UnsafeBitSet(final long[] bits, final int wlen) {
        super(bits, wlen * 64, wlen);  // bits are not cloned in ctr
    }


    /**
     * Return the <code>long[]</code> backing this unsafe bit set.
     *
     * @return the <code>long[]</code> backing this unsafe bit set
     */
    public final long[] getBits() {
        return bits;
    }

    /**
     * Return <code>wlen</code> for the bits backing this unsafe bit set.
     *
     * @since 2.1
     * @return <code>wlen</code> for the bits backing this unsafe bit set
     */
    public final int getWlen() {
        return wlen();
    }

    /**
     * Set the <code>long[]</code> backing this unsafe bit set to <code>bits</code>.
     *
     * @param bits bits stored in <code>long[]</code>
     */
    public final void setBits(final long[] bits) {
        // null or any other sanity check?
        this.bits = bits;
    }

    // need a setter for wlen?

    /**
     * Perform a logical XOR of the specified bit set and this bit set.
     *
     * <p>This other bit set is modified in place and a reference to this is returned for method chaining.</p>
     *
     * @param other bit set to XOR with this bit set, must not be null
     * @return this other bit set, for method chaining
     */
    public UnsafeBitSet xor(final AbstractBitSet other) {
        return (UnsafeBitSet) super.xor(other);
    }

    /**
     * Perform a logical AND of the specified bit set and this bit set.  Also known as a union operation.
     *
     * <p>This unsafe bit set is modified in place and a reference to this is returned for method chaining.</p>
     *
     * @param other bit set to AND with this bit set, must not be null
     * @return this unsafe bit set, for method chaining
     */
    public UnsafeBitSet and(final AbstractBitSet other) {
        return (UnsafeBitSet) super.and(other);
    }

    /**
     * Perform a logical OR of the specified bit set and this bit set.  Also known as an intersect operation.
     *
     * <p>This unsafe bit set is modified in place and a reference to this is returned for method chaining.</p>
     *
     * @param other bit set to OR with this bit set, must not be null
     * @return this unsafe bit set, for method chaining
     */
    public UnsafeBitSet or(final AbstractBitSet other) {
        return (UnsafeBitSet) super.or(other);
    }

    /**
     * Perform a logical NOT followed by AND of the specified bit set and this bit set.  Also known as a remove operation.
     *
     * <p>This unsafe bit set is modified in place and a reference to this is returned for method chaining.</p>
     *
     * @param other bit set to NOT followed by AND with this bit set, must not be null
     * @return this unsafe bit set, for method chaining
     */
    public UnsafeBitSet andNot(final AbstractBitSet other) {
        return (UnsafeBitSet) super.andNot(other);
    }

    /**
     * Return a new immutable copy of this unsafe bit set.
     *
     * @since 3.1
     * @return a new immutable copy of this unsafe bit set
     */
    public ImmutableBitSet immutableCopy() {
        return new ImmutableBitSet(bits, wlen); // bits is cloned in ctr
    }

    /**
     * Return a new mutable copy of this unsafe bit set.
     *
     * @return a new mutable copy of this unsafe bit set
     */
    public final MutableBitSet mutableCopy() {
        return new MutableBitSet(bits.clone(), wlen);
    }

    /**
     * Return a new unsafe copy of this unsafe bit set.
     *
     * @since 3.1
     * @return a new unsafe copy of this unsafe bit set
     */
    public UnsafeBitSet unsafeCopy() {
        return new UnsafeBitSet(bits.clone(), wlen);
    }
}
