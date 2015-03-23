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
package org.dishevelled.bitset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.dishevelled.functor.UnaryProcedure;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for ImmutableBitSet.
 */
public final class ImmutableBitSetTest extends AbstractBitSetTest {
    private static final long N = 800L;
    private static ImmutableBitSet empty;
    private static ImmutableBitSet full;
    private static ImmutableBitSet partial;
    private static ImmutableBitSet half;

    @Override
    protected AbstractBitSet createBitSet() {
        return empty;
    }

    @BeforeClass
    public static void createBitSets() {
        MutableBitSet m = new MutableBitSet(N);
        empty = m.immutableCopy();

        m.set((N / 2L), N);
        partial = m.immutableCopy();

        m.set(0L, N);
        full = m.immutableCopy(); 

        MutableBitSet h = new MutableBitSet(N / 2L);
        h.set(N / 4L, N / 2L);
        half = h.immutableCopy();
    }

    @Test
    public void testCapacity() {
        assertTrue(empty.capacity() >= N);
        assertTrue(partial.capacity() >= N);
        assertTrue(full.capacity() >= N);
        assertTrue(half.capacity() >= N / 2L);
    }

    @Test
    public void testCardinality() {
        assertEquals(0L, empty.cardinality());
        assertEquals(N / 2L, partial.cardinality());
        assertEquals(N, full.cardinality());
        assertEquals(N / 4L, half.cardinality());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(empty.isEmpty());
        assertFalse(partial.isEmpty());
        assertFalse(full.isEmpty());
        assertFalse(half.isEmpty());
    }

    @Test
    public void testGet() {
        for (long i = 0; i < N; i++) {
            assertFalse(empty.get(i));
            if (i < (N / 2L)) {
                assertFalse(partial.get(i));
            }
            else {
                assertTrue(partial.get(i));
            }
            assertTrue(full.get(i));
        }
    }

    @Test
    public void testGetQuick() {
        for (long i = 0; i < N; i++) {
            assertFalse(empty.getQuick(i));
            if (i < (N / 2L)) {
                assertFalse(partial.getQuick(i));
            }
            else {
                assertTrue(partial.getQuick(i));
            }
            assertTrue(full.getQuick(i));
        }
    }

    @Test
    public void testNextClearBit() {
        assertEquals(-1L, empty.nextClearBit(0L));
        assertEquals(0L, partial.nextClearBit(0L));
        assertEquals(-1L, full.nextClearBit(0L));

        assertEquals(-1L, empty.nextClearBit(N - 1L));
        assertEquals(-1L, partial.nextClearBit(N - 1L));
        assertEquals(-1L, full.nextClearBit(N - 1L));
    }

    @Test
    public void testPrevClearBit() {
        assertEquals(-1L, empty.prevClearBit(0L));
        assertEquals(-1L, partial.prevClearBit(0L));
        assertEquals(-1L, full.prevClearBit(0L));

        assertEquals(-1L, empty.prevClearBit(N - 1L));
        assertEquals((N / 2L) - 1L, partial.prevClearBit(N - 1L));
        assertEquals(-1L, full.prevClearBit(N - 1L));
    }

    @Test(expected=NullPointerException.class)
    public void testForEachClearBitNull() {
        empty.forEachClearBit(null);
    }

    @Test
    public void testForEachClearBitEmpty() {
        Count count = new Count();
        empty.forEachClearBit(count);
        assertEquals(0, count.count());
    }

    @Test
    public void testForEachClearBitPartial() {
        Count count = new Count();
        partial.forEachClearBit(count);
        assertEquals((N / 2L), count.count());
    }

    @Test
    public void testForEachClearBitFull() {
        Count count = new Count();
        full.forEachClearBit(count);
        assertEquals(0, count.count());
    }

    @Test
    public void testNextSetBit() {
        assertEquals(-1L, empty.nextSetBit(0L));
        assertEquals((N / 2L), partial.nextSetBit(0L));
        assertEquals(0L, full.nextSetBit(0L));

        assertEquals(-1L, empty.nextSetBit(N - 1L));
        assertEquals(N - 1L, partial.nextSetBit(N - 1L));
        assertEquals(N - 1L, full.nextSetBit(N - 1L));
    }

    @Test
    public void testPrevSetBit() {
        assertEquals(-1L, empty.prevSetBit(0L));
        assertEquals(-1L, partial.prevSetBit(0L));
        assertEquals(0L, full.prevSetBit(0L));

        assertEquals(-1L, empty.prevSetBit(N - 1L));
        assertEquals(N - 1L, partial.prevSetBit(N - 1L));
        assertEquals(N - 1L, full.prevSetBit(N - 1L));
    }

    @Test(expected=NullPointerException.class)
    public void testForEachSetBitNull() {
        empty.forEachSetBit(null);
    }

    @Test
    public void testForEachSetBitEmpty() {
        Count count = new Count();
        empty.forEachSetBit(count);
        assertEquals(0, count.count());
    }

    @Test
    public void testForEachSetBitPartial() {
        Count count = new Count();
        partial.forEachSetBit(count);
        assertEquals((N / 2L), count.count());
    }

    @Test
    public void testForEachSetBitFull() {
        Count count = new Count();
        full.forEachSetBit(count);
        assertEquals(N, count.count());
    }

    @Test
    public void testIntersects() {
        assertFalse(empty.intersects(empty));
        assertFalse(empty.intersects(partial));
        assertFalse(empty.intersects(full));
        assertFalse(empty.intersects(half));

        assertFalse(partial.intersects(empty));
        assertTrue(partial.intersects(partial));
        assertTrue(partial.intersects(full));
        assertFalse(partial.intersects(half));

        assertFalse(full.intersects(empty));
        assertTrue(full.intersects(partial));
        assertTrue(full.intersects(full));
        assertTrue(full.intersects(half));

        assertFalse(half.intersects(empty));
        assertFalse(half.intersects(partial));
        assertTrue(half.intersects(full));
        assertTrue(half.intersects(half));
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testSetThrowsUnsupportedOperationException() {
        bitset.set(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testSetRangeThrowsUnsupportedOperationException() {
        bitset.set(0L, 1L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testSetQuickThrowsUnsupportedOperationException() {
        bitset.setQuick(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testClearThrowsUnsupportedOperationException() {
        bitset.clear(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testClearRangeThrowsUnsupportedOperationException() {
        bitset.clear(0L, 1L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testClearQuickThrowsUnsupportedOperationException() {
        bitset.clearQuick(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testGetAndSetThrowsUnsupportedOperationException() {
        bitset.getAndSet(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFlipThrowsUnsupportedOperationException() {
        bitset.flip(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFlipRangeThrowsUnsupportedOperationException() {
        bitset.flip(0L, 1L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFlipQuickThrowsUnsupportedOperationException() {
        bitset.flipQuick(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFlipAndGetThrowsUnsupportedOperationException() {
        bitset.flipAndGet(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testEnsureCapacityThrowsUnsupportedOperationException() {
        bitset.ensureCapacity(0L);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testTrimTrailingZerosThrowsUnsupportedOperationException() {
        bitset.trimTrailingZeros();
    }

    @Test
    public void testXor() {
        AbstractBitSet emptyXor = empty.xor(empty);
        assertNotNull(emptyXor);
        assertNotSame(empty, emptyXor);
        assertTrue(emptyXor.isEmpty());

        assertEquals((N / 2L), empty.xor(partial).cardinality());
        assertEquals(N, empty.xor(full).cardinality());
        assertEquals(N / 4L, empty.xor(half).cardinality());

        AbstractBitSet partialXor = partial.xor(partial);
        assertNotNull(partialXor);
        assertNotSame(partial, partialXor);
        assertTrue(partialXor.isEmpty());

        assertEquals((N / 2L), partial.xor(empty).cardinality());
        assertEquals((N / 2L), partial.xor(full).cardinality());
        assertEquals(3L * N / 4L, partial.xor(half).cardinality());

        AbstractBitSet fullXor = full.xor(full);
        assertNotNull(fullXor);
        assertNotSame(full, fullXor);
        assertTrue(fullXor.isEmpty());

        assertEquals(N, full.xor(empty).cardinality());
        assertEquals((N / 2L), full.xor(partial).cardinality());
        assertEquals(3L * N / 4L, full.xor(half).cardinality());

        AbstractBitSet halfXor = half.xor(half);
        assertNotNull(halfXor);
        assertNotSame(half, halfXor);
        assertTrue(halfXor.isEmpty());

        assertEquals(N / 4L, half.xor(empty).cardinality());
        assertEquals(3L * N / 4L, half.xor(partial).cardinality());
        assertEquals(3L * N / 4L, half.xor(full).cardinality());
    }

    @Test
    public void testAnd() {
        AbstractBitSet emptyAnd = empty.and(empty);
        assertNotNull(emptyAnd);
        assertNotSame(empty, emptyAnd);
        assertTrue(emptyAnd.isEmpty());

        assertTrue(empty.and(partial).isEmpty());
        assertTrue(empty.and(full).isEmpty());
        assertTrue(empty.and(half).isEmpty());

        AbstractBitSet partialAnd = partial.and(partial);
        assertNotNull(partialAnd);
        assertNotSame(partial, partialAnd);
        assertEquals((N / 2L), partialAnd.cardinality());

        assertTrue(partial.and(empty).isEmpty());
        assertEquals((N / 2L), partial.and(full).cardinality());
        assertTrue(partial.and(half).isEmpty());

        AbstractBitSet fullAnd = full.and(full);
        assertNotNull(fullAnd);
        assertNotSame(full, fullAnd);
        assertEquals(N, fullAnd.cardinality());

        assertTrue(full.and(empty).isEmpty());
        assertEquals((N / 2L), full.and(partial).cardinality());
        assertEquals(N / 4L, full.and(half).cardinality());

        AbstractBitSet halfAnd = half.and(half);
        assertNotNull(halfAnd);
        assertNotSame(half, halfAnd);
        assertEquals(N / 4L, halfAnd.cardinality());

        assertTrue(half.and(empty).isEmpty());
        assertTrue(half.and(partial).isEmpty());
        assertEquals(N / 4L, half.and(full).cardinality());
    }

    @Test
    public void testOr() {
        AbstractBitSet emptyOr = empty.or(empty);
        assertNotNull(emptyOr);
        assertNotSame(empty, emptyOr);
        assertTrue(emptyOr.isEmpty());

        assertEquals((N / 2L), empty.or(partial).cardinality());
        assertEquals(N, empty.or(full).cardinality());
        assertEquals(N / 4L, empty.or(half).cardinality());

        AbstractBitSet partialOr = partial.or(partial);
        assertNotNull(partialOr);
        assertNotSame(partial, partialOr);
        assertEquals((N / 2L), partial.or(partial).cardinality());

        assertEquals((N / 2L), partial.or(empty).cardinality());
        assertEquals(N, partial.or(full).cardinality());
        assertEquals(3L * N / 4L, partial.or(half).cardinality());

        MutableBitSet m = new MutableBitSet(N);
        m.set(0L, (N / 2L));
        assertEquals(N, partial.or(m).cardinality());

        AbstractBitSet fullOr = full.or(full);
        assertNotNull(fullOr);
        assertNotSame(full, fullOr);
        assertEquals(N, fullOr.cardinality());

        assertEquals(N, full.or(empty).cardinality());
        assertEquals(N, full.or(partial).cardinality());
        assertEquals(N, full.or(half).cardinality());

        AbstractBitSet halfOr = half.or(half);
        assertNotNull(halfOr);
        assertNotSame(half, halfOr);
        assertEquals(N / 4L, halfOr.cardinality());

        assertEquals(N / 4L, half.or(empty).cardinality());
        assertEquals(3L * N / 4L, half.or(partial).cardinality());
        assertEquals(N, half.or(full).cardinality());
    }

    @Test
    public void testAndNot() {
        AbstractBitSet emptyAndNot = empty.andNot(empty);
        assertNotNull(emptyAndNot);
        assertNotSame(empty, emptyAndNot);
        assertTrue(emptyAndNot.isEmpty());

        assertTrue(empty.andNot(partial).isEmpty());
        assertTrue(empty.andNot(full).isEmpty());
        assertTrue(empty.andNot(half).isEmpty());

        AbstractBitSet partialAndNot = partial.andNot(partial);
        assertNotNull(partialAndNot);
        assertNotSame(partial, partialAndNot);
        assertTrue(partialAndNot.isEmpty());

        assertEquals((N / 2L), partial.andNot(empty).cardinality());
        assertTrue(partial.andNot(full).isEmpty());
        assertEquals(N / 2L, partial.andNot(half).cardinality());

        AbstractBitSet fullAndNot = full.andNot(full);
        assertNotNull(fullAndNot);
        assertNotSame(full, fullAndNot);
        assertTrue(fullAndNot.isEmpty());

        assertEquals(N, full.andNot(empty).cardinality());
        assertEquals((N / 2L), full.andNot(partial).cardinality());
        assertEquals(3L * N / 4L, full.andNot(half).cardinality());

        AbstractBitSet halfAndNot = half.andNot(half);
        assertNotNull(halfAndNot);
        assertNotSame(half, halfAndNot);
        assertTrue(halfAndNot.isEmpty());

        assertEquals(N / 4L, half.andNot(empty).cardinality());
        assertEquals(N / 4L, half.andNot(partial).cardinality());
        assertTrue(half.andNot(full).isEmpty());
    }

    @Test
    public void testLogicalMethodChaining() {
        ImmutableBitSet result = empty.or(partial).xor(full);
        assertNotNull(result);
    }

    @Test
    public void testMutableCopy() {
        MutableBitSet mutableEmpty = empty.mutableCopy();
        assertNotNull(mutableEmpty);
        assertTrue(mutableEmpty.isEmpty());

        MutableBitSet mutablePartial = partial.mutableCopy();
        assertNotNull(mutablePartial);
        assertEquals((N / 2L), mutablePartial.cardinality());

        MutableBitSet mutableFull = full.mutableCopy();
        assertNotNull(mutableFull);
        assertTrue(mutableFull.get(0L));
        assertTrue(mutableFull.get(N - 1L));
    }

    @Test
    public void testUnsafeCopy() {
        UnsafeBitSet unsafeEmpty = empty.unsafeCopy();
        assertNotNull(unsafeEmpty);
        assertTrue(unsafeEmpty.isEmpty());

        UnsafeBitSet unsafePartial = partial.unsafeCopy();
        assertNotNull(unsafePartial);
        assertEquals((N / 2L), unsafePartial.cardinality());

        UnsafeBitSet unsafeFull = full.unsafeCopy();
        assertNotNull(unsafeFull);
        assertTrue(unsafeFull.get(0L));
        assertTrue(unsafeFull.get(N - 1L));
    }

    @Test
    public void testStaticAndNot() {
        assertEquals(empty, ImmutableBitSet.andNot(empty, empty));
        assertEquals(empty, ImmutableBitSet.andNot(empty, partial));
        assertEquals(empty, ImmutableBitSet.andNot(empty, full));
        assertEquals(partial, ImmutableBitSet.andNot(partial, empty));
        assertEquals(empty, ImmutableBitSet.andNot(partial, partial));
        assertEquals(empty, ImmutableBitSet.andNot(partial, full));
        assertEquals(full, ImmutableBitSet.andNot(full, empty));

        MutableBitSet m = new MutableBitSet(N);
        m.set(0, (N / 2L));
        assertEquals(m.immutableCopy(), ImmutableBitSet.andNot(full, partial));

        assertEquals(empty, ImmutableBitSet.andNot(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNotNullA() {
        ImmutableBitSet.andNot(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNotNullB() {
        ImmutableBitSet.andNot(empty, null);
    }

    @Test
    public void testAndNotCount() {
        assertEquals(0L, ImmutableBitSet.andNotCount(empty, empty));
        assertEquals(0L, ImmutableBitSet.andNotCount(empty, partial));
        assertEquals(0L, ImmutableBitSet.andNotCount(empty, full));
        assertEquals((N / 2L), ImmutableBitSet.andNotCount(partial, empty));
        assertEquals(0L, ImmutableBitSet.andNotCount(partial, partial));
        assertEquals(0L, ImmutableBitSet.andNotCount(partial, full));
        assertEquals(N, ImmutableBitSet.andNotCount(full, empty));
        assertEquals((N / 2L), ImmutableBitSet.andNotCount(full, partial));
        assertEquals(0L, ImmutableBitSet.andNotCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testAndNotCountNullA() {
        ImmutableBitSet.andNotCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testAndNotCountNullB() {
        ImmutableBitSet.andNotCount(empty, null);
    }

    @Test
    public void testStaticAnd() {
        assertEquals(empty, ImmutableBitSet.and(empty, empty));
        assertEquals(empty, ImmutableBitSet.and(empty, partial));
        assertEquals(empty, ImmutableBitSet.and(empty, full));
        assertEquals(empty, ImmutableBitSet.and(partial, empty));
        assertEquals(partial, ImmutableBitSet.and(partial, partial));
        assertEquals(partial, ImmutableBitSet.and(partial, full));
        assertEquals(empty, ImmutableBitSet.and(full, empty));
        assertEquals(partial, ImmutableBitSet.and(full, partial));
        assertEquals(full, ImmutableBitSet.and(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNullA() {
        ImmutableBitSet.and(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNullB() {
        ImmutableBitSet.and(empty, null);
    }

    @Test
    public void testAndCount() {
        assertEquals(0L, ImmutableBitSet.andCount(empty, empty));
        assertEquals(0L, ImmutableBitSet.andCount(empty, partial));
        assertEquals(0L, ImmutableBitSet.andCount(empty, full));
        assertEquals(0L, ImmutableBitSet.andCount(partial, empty));
        assertEquals((N / 2L), ImmutableBitSet.andCount(partial, partial));
        assertEquals((N / 2L), ImmutableBitSet.andCount(partial, full));
        assertEquals(0L, ImmutableBitSet.andCount(full, empty));
        assertEquals((N / 2L), ImmutableBitSet.andCount(full, partial));
        assertEquals(N, ImmutableBitSet.andCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testAndCountNullA() {
        ImmutableBitSet.andCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testAndCountNullB() {
        ImmutableBitSet.andCount(empty, null);
    }

    @Test
    public void testStaticOr() {
        assertEquals(empty, ImmutableBitSet.or(empty, empty));
        assertEquals(partial, ImmutableBitSet.or(empty, partial));
        assertEquals(full, ImmutableBitSet.or(empty, full));
        assertEquals(partial, ImmutableBitSet.or(partial, empty));
        assertEquals(partial, ImmutableBitSet.or(partial, partial));
        assertEquals(full, ImmutableBitSet.or(partial, full));
        assertEquals(full, ImmutableBitSet.or(full, empty));
        assertEquals(full, ImmutableBitSet.or(full, partial));
        assertEquals(full, ImmutableBitSet.or(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticOrCountNullA() {
        ImmutableBitSet.or(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticOrCountNullB() {
        ImmutableBitSet.or(empty, null);
    }

    @Test
    public void testOrCount() {
        assertEquals(0L, ImmutableBitSet.orCount(empty, empty));
        assertEquals((N / 2L), ImmutableBitSet.orCount(empty, partial));
        assertEquals(N, ImmutableBitSet.orCount(empty, full));
        assertEquals((N / 2L), ImmutableBitSet.orCount(partial, empty));
        assertEquals((N / 2L), ImmutableBitSet.orCount(partial, partial));
        assertEquals(N, ImmutableBitSet.orCount(partial, full));
        assertEquals(N, ImmutableBitSet.orCount(full, empty));
        assertEquals(N, ImmutableBitSet.orCount(full, partial));
        assertEquals(N, ImmutableBitSet.orCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testOrCountNullA() {
        ImmutableBitSet.orCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testOrCountNullB() {
        ImmutableBitSet.orCount(empty, null);
    }

    @Test
    public void testStaticXor() {
        assertEquals(empty, ImmutableBitSet.xor(empty, empty));
        assertEquals(partial, ImmutableBitSet.xor(empty, partial));
        assertEquals(full, ImmutableBitSet.xor(empty, full));
        assertEquals(partial, ImmutableBitSet.xor(partial, empty));
        assertEquals(empty, ImmutableBitSet.xor(partial, partial));

        MutableBitSet m = new MutableBitSet(N);
        m.set(0, (N / 2L));

        assertEquals(m.immutableCopy(), ImmutableBitSet.xor(partial, full));
        assertEquals(full, ImmutableBitSet.xor(full, empty));
        assertEquals(m.immutableCopy(), ImmutableBitSet.xor(full, partial));
        assertEquals(empty, ImmutableBitSet.xor(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticXorNullA() {
        ImmutableBitSet.xor(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticXorNullB() {
        ImmutableBitSet.xor(empty, null);
    }

    @Test
    public void testXorCount() {
        assertEquals(0L, ImmutableBitSet.xorCount(empty, empty));
        assertEquals((N / 2L), ImmutableBitSet.xorCount(empty, partial));
        assertEquals(N, ImmutableBitSet.xorCount(empty, full));
        assertEquals((N / 2L), ImmutableBitSet.xorCount(partial, empty));
        assertEquals(0L, ImmutableBitSet.xorCount(partial, partial));
        assertEquals((N / 2L), ImmutableBitSet.xorCount(partial, full));
        assertEquals(N, ImmutableBitSet.xorCount(full, empty));
        assertEquals((N / 2L), ImmutableBitSet.xorCount(full, partial));
        assertEquals(0L, ImmutableBitSet.xorCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testXorCountNullA() {
        ImmutableBitSet.xorCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testXorCountNullB() {
        ImmutableBitSet.xorCount(empty, null);
    }

    @Test
    public void testSerializable() {
        assertTrue(bitset instanceof Serializable);
    }

    @Test
    public void testSerializeEmpty() throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(empty);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        Object dest = in.readObject();
        in.close();

        assertEquals(empty, (ImmutableBitSet) dest);
    }

    @Test
    public void testSerializeFull() throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(full);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        Object dest = in.readObject();
        in.close();

        assertEquals(full, (ImmutableBitSet) dest);
    }

    @Test
    public void testSerializePartial() throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(partial);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        Object dest = in.readObject();
        in.close();

        assertEquals(partial, (ImmutableBitSet) dest);
    }

    @Test
    public void testSerializeHalf() throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(half);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        Object dest = in.readObject();
        in.close();

        assertEquals(half, (ImmutableBitSet) dest);
    }

    // regenerate serialized resources each time serialization format or value of N changes
    //    and copy files to src/test/resources/org/dishevelled/bitset
    public void writeSerializedResources() throws Exception {
        writeSerializedResource("immutableEmpty.ser", empty);
        writeSerializedResource("immutableFull.ser", full);
        writeSerializedResource("immutablePartial.ser", partial);
        writeSerializedResource("immutableHalf.ser", half);
    }

    private static void writeSerializedResource(final String name, final ImmutableBitSet bitset) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(name)));
            out.writeObject(bitset);
        }
        catch (IOException e) {
            fail(e.getMessage());
        }
        finally {
            try {
                out.close();
            }
            catch (Exception e) {
                // ignore
            }
        }
    }

    @Test
    public void testSerializationCompatibilityEmpty() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("immutableEmpty.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(empty, (ImmutableBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityFull() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("immutableFull.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(full, (ImmutableBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityPartial() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("immutablePartial.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(partial, (ImmutableBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityHalf() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("immutableHalf.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(half, (ImmutableBitSet) dest);
    }
}