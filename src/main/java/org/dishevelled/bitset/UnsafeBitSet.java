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
     * Return a new mutable copy of this unsafe bit set.
     *
     * @return a new mutable copy of this unsafe bit set
     */
    public final MutableBitSet mutableCopy() {
        return new MutableBitSet(bits.clone(), wlen);
    }
}
