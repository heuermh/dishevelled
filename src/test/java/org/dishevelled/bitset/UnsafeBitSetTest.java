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
package org.dishevelled.bitset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for UnsafeBitSet.
 */
public class UnsafeBitSetTest extends AbstractBitSetTest {
    private static final long N = 800L;
    private UnsafeBitSet empty;
    private UnsafeBitSet full;
    private UnsafeBitSet partial;
    private UnsafeBitSet half;

    @Override
    protected AbstractBitSet createBitSet() {
        return new UnsafeBitSet(N);
    }

    @Before
    public void setUp() {
        super.setUp();
        empty = new UnsafeBitSet(N);
        partial = new UnsafeBitSet(N);
        partial.set((N / 2L), N);
        full = new UnsafeBitSet(N);
        full.set(0L, N);
        half = new UnsafeBitSet((N / 2L));
        half.set((N / 4L), (N / 2L));
    }

    @Test
    public void testCapacity() {
        assertTrue(empty.capacity() >= N);
        assertTrue(partial.capacity() >= N);
        assertTrue(full.capacity() >= N);
        assertTrue(half.capacity() >= (N / 2L));
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

    @Test
    public void testAndNotCount() {
        assertEquals(0L, UnsafeBitSet.andNotCount(empty, empty));
        assertEquals(0L, UnsafeBitSet.andNotCount(empty, partial));
        assertEquals(0L, UnsafeBitSet.andNotCount(empty, full));
        assertEquals((N / 2L), UnsafeBitSet.andNotCount(partial, empty));
        assertEquals(0L, UnsafeBitSet.andNotCount(partial, partial));
        assertEquals(0L, UnsafeBitSet.andNotCount(partial, full));
        assertEquals(N, UnsafeBitSet.andNotCount(full, empty));
        assertEquals((N / 2L), UnsafeBitSet.andNotCount(full, partial));
        assertEquals(0L, UnsafeBitSet.andNotCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testAndNotCountNullA() {
        UnsafeBitSet.andNotCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testAndNotCountNullB() {
        UnsafeBitSet.andNotCount(empty, null);
    }

    @Test
    public void testAndCount() {
        assertEquals(0L, UnsafeBitSet.andCount(empty, empty));
        assertEquals(0L, UnsafeBitSet.andCount(empty, partial));
        assertEquals(0L, UnsafeBitSet.andCount(empty, full));
        assertEquals(0L, UnsafeBitSet.andCount(partial, empty));
        assertEquals((N / 2L), UnsafeBitSet.andCount(partial, partial));
        assertEquals((N / 2L), UnsafeBitSet.andCount(partial, full));
        assertEquals(0L, UnsafeBitSet.andCount(full, empty));
        assertEquals((N / 2L), UnsafeBitSet.andCount(full, partial));
        assertEquals(N, UnsafeBitSet.andCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testAndCountNullA() {
        UnsafeBitSet.andCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testAndCountNullB() {
        UnsafeBitSet.andCount(empty, null);
    }

    @Test
    public void testOrCount() {
        assertEquals(0L, UnsafeBitSet.orCount(empty, empty));
        assertEquals((N / 2L), UnsafeBitSet.orCount(empty, partial));
        assertEquals(N, UnsafeBitSet.orCount(empty, full));
        assertEquals((N / 2L), UnsafeBitSet.orCount(partial, empty));
        assertEquals((N / 2L), UnsafeBitSet.orCount(partial, partial));
        assertEquals(N, UnsafeBitSet.orCount(partial, full));
        assertEquals(N, UnsafeBitSet.orCount(full, empty));
        assertEquals(N, UnsafeBitSet.orCount(full, partial));
        assertEquals(N, UnsafeBitSet.orCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testOrCountNullA() {
        UnsafeBitSet.orCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testOrCountNullB() {
        UnsafeBitSet.orCount(empty, null);
    }

    @Test
    public void testXorCount() {
        assertEquals(0L, UnsafeBitSet.xorCount(empty, empty));
        assertEquals((N / 2L), UnsafeBitSet.xorCount(empty, partial));
        assertEquals(N, UnsafeBitSet.xorCount(empty, full));
        assertEquals((N / 2L), UnsafeBitSet.xorCount(partial, empty));
        assertEquals(0L, UnsafeBitSet.xorCount(partial, partial));
        assertEquals((N / 2L), UnsafeBitSet.xorCount(partial, full));
        assertEquals(N, UnsafeBitSet.xorCount(full, empty));
        assertEquals((N / 2L), UnsafeBitSet.xorCount(full, partial));
        assertEquals(0L, UnsafeBitSet.xorCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testXorCountNullA() {
        UnsafeBitSet.xorCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testXorCountNullB() {
        UnsafeBitSet.xorCount(empty, null);
    }

    @Test
    public void testXor() {
        AbstractBitSet emptyXor = empty.xor(empty);
        assertSame(empty, emptyXor);
        assertTrue(emptyXor.isEmpty());

        // cannot chain assertions, empty is mutable
        //assertEquals((N / 2L), empty.xor(partial).cardinality());
        //assertEquals(N, empty.xor(full).cardinality());
        //assertEquals(N / 4L, empty.xor(half).cardinality());

        AbstractBitSet partialXor = partial.xor(partial);
        assertSame(partial, partialXor);
        assertTrue(partialXor.isEmpty());

        //assertEquals((N / 2L), partial.xor(empty).cardinality());
        //assertEquals((N / 2L), partial.xor(full).cardinality());
        //assertEquals(3L * N / 4L, partial.xor(half).cardinality());

        AbstractBitSet fullXor = full.xor(full);
        assertSame(full, fullXor);
        assertTrue(fullXor.isEmpty());

        //assertEquals(N, full.xor(empty).cardinality());
        //assertEquals((N / 2L), full.xor(partial).cardinality());
        //assertEquals(3L * N / 4L, full.xor(half).cardinality());

        AbstractBitSet halfXor = half.xor(half);
        assertSame(half, halfXor);
        assertTrue(halfXor.isEmpty());

        //assertEquals(N / 4L, half.xor(empty).cardinality());
        //assertEquals(3L * N / 4L, half.xor(partial).cardinality());
        //assertEquals(3L * N / 4L, half.xor(full).cardinality());
    }

    @Test
    public void testAnd() {
        AbstractBitSet emptyAnd = empty.and(empty);
        assertSame(empty, emptyAnd);
        assertTrue(emptyAnd.isEmpty());

        //assertTrue(empty.and(partial).isEmpty());
        //assertTrue(empty.and(full).isEmpty());
        //assertTrue(empty.and(half).isEmpty());

        AbstractBitSet partialAnd = partial.and(partial);
        assertSame(partial, partialAnd);
        assertEquals((N / 2L), partialAnd.cardinality());

        //assertTrue(partial.and(empty).isEmpty());
        //assertEquals((N / 2L), partial.and(full).cardinality());
        //assertTrue(partial.and(half).isEmpty());

        AbstractBitSet fullAnd = full.and(full);
        assertSame(full, fullAnd);
        assertEquals(N, fullAnd.cardinality());

        //assertTrue(full.and(empty).isEmpty());
        //assertEquals((N / 2L), full.and(partial).cardinality());
        //assertEquals(N / 4L, full.and(half).cardinality());

        AbstractBitSet halfAnd = half.and(half);
        assertSame(half, halfAnd);
        assertEquals(N / 4L, halfAnd.cardinality());

        //assertTrue(half.and(empty).isEmpty());
        //assertTrue(half.and(partial).isEmpty());
        //assertEquals(N / 4L, half.and(full).cardinality());
    }

    @Test
    public void testOr() {
        AbstractBitSet emptyOr = empty.or(empty);
        assertSame(empty, emptyOr);
        assertTrue(emptyOr.isEmpty());

        //assertEquals((N / 2L), empty.or(partial).cardinality());
        //assertEquals(N, empty.or(full).cardinality());
        //assertEquals(N / 4L, empty.or(half).cardinality());

        AbstractBitSet partialOr = partial.or(partial);
        assertSame(partial, partialOr);
        assertEquals((N / 2L), partial.or(partial).cardinality());

        //assertEquals((N / 2L), partial.or(empty).cardinality());
        //assertEquals(N, partial.or(full).cardinality());
        //assertEquals(3L * N / 4L, partial.or(half).cardinality());

        MutableBitSet m = new MutableBitSet(N);
        m.set(0L, (N / 2L));
        assertEquals(N, partial.or(m).cardinality());

        AbstractBitSet fullOr = full.or(full);
        assertSame(full, fullOr);
        assertEquals(N, fullOr.cardinality());

        //assertEquals(N, full.or(empty).cardinality());
        //assertEquals(N, full.or(partial).cardinality());
        //assertEquals(N, full.or(half).cardinality());

        AbstractBitSet halfOr = half.or(half);
        assertSame(half, halfOr);
        assertEquals(N / 4L, halfOr.cardinality());

        //assertEquals(N / 4L, half.or(empty).cardinality());
        //assertEquals(3L * N / 4L, half.or(partial).cardinality());
        //assertEquals(N, half.or(full).cardinality());
    }

    @Test
    public void testAndNot() {
        AbstractBitSet emptyAndNot = empty.andNot(empty);
        assertSame(empty, emptyAndNot);
        assertTrue(emptyAndNot.isEmpty());

        //assertTrue(empty.andNot(partial).isEmpty());
        //assertTrue(empty.andNot(full).isEmpty());
        //assertTrue(empty.andNot(half).isEmpty());

        AbstractBitSet partialAndNot = partial.andNot(partial);
        assertSame(partial, partialAndNot);
        assertTrue(partialAndNot.isEmpty());

        //assertEquals((N / 2L), partial.andNot(empty).cardinality());
        //assertTrue(partial.andNot(full).isEmpty());
        //assertEquals(N / 2L, partial.andNot(half).cardinality());

        AbstractBitSet fullAndNot = full.andNot(full);
        assertSame(full, fullAndNot);
        assertTrue(fullAndNot.isEmpty());

        //assertEquals(N, full.andNot(empty).cardinality());
        //assertEquals((N / 2L), full.andNot(partial).cardinality());
        //assertEquals(3L * N / 4L, full.andNot(half).cardinality());

        AbstractBitSet halfAndNot = half.andNot(half);
        assertSame(half, halfAndNot);
        assertTrue(halfAndNot.isEmpty());

        //assertEquals(N / 4L, half.andNot(empty).cardinality());
        //assertEquals(N / 4L, half.andNot(partial).cardinality());
        //assertTrue(half.andNot(full).isEmpty());
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

        assertEquals(empty, (UnsafeBitSet) dest);
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

        assertEquals(full, (UnsafeBitSet) dest);
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

        assertEquals(partial, (UnsafeBitSet) dest);
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

        assertEquals(half, (UnsafeBitSet) dest);
    }

    // regenerate serialized resources each time serialization format or value of N changes
    //    and copy files to src/test/resources/org/dishevelled/bitset
    public void writeSerializedResources() throws Exception {
        writeSerializedResource("unsafeEmpty.ser", empty);
        writeSerializedResource("unsafeFull.ser", full);
        writeSerializedResource("unsafePartial.ser", partial);
        writeSerializedResource("unsafeHalf.ser", half);
    }

    private static void writeSerializedResource(final String name, final UnsafeBitSet bitset) {
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
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("unsafeEmpty.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(empty, (UnsafeBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityFull() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("unsafeFull.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(full, (UnsafeBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityPartial() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("unsafePartial.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(partial, (UnsafeBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityHalf() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("unsafeHalf.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(half, (UnsafeBitSet) dest);
    }
}