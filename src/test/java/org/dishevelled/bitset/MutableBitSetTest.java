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
 * Unit test for MutableBitSet.
 */
public class MutableBitSetTest extends AbstractBitSetTest {
    private static final long N = 800L;
    private MutableBitSet empty;
    private MutableBitSet full;
    private MutableBitSet partial;
    private MutableBitSet half;

    @Override
    protected AbstractBitSet createBitSet() {
        return new MutableBitSet(N);
    }

    @Before
    public void setUp() {
        super.setUp();
        empty = new MutableBitSet(N);
        partial = new MutableBitSet(N);
        partial.set((N / 2L), N);
        full = new MutableBitSet(N);
        full.set(0L, N);
        half = new MutableBitSet((N / 2L));
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

    @Test
    public void testStaticAndNot() {
        assertEquals(empty, MutableBitSet.andNot(empty, empty));
        assertEquals(empty, MutableBitSet.andNot(empty, partial));
        assertEquals(empty, MutableBitSet.andNot(empty, full));
        assertEquals(partial, MutableBitSet.andNot(partial, empty));
        assertEquals(empty, MutableBitSet.andNot(partial, partial));
        assertEquals(empty, MutableBitSet.andNot(partial, full));
        assertEquals(full, MutableBitSet.andNot(full, empty));

        MutableBitSet m = new MutableBitSet(N);
        m.set(0, (N / 2L));
        assertEquals(m, MutableBitSet.andNot(full, partial));

        assertEquals(empty, MutableBitSet.andNot(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNotNullA() {
        MutableBitSet.andNot(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNotNullB() {
        MutableBitSet.andNot(empty, null);
    }

    @Test
    public void testAndNotCount() {
        assertEquals(0L, MutableBitSet.andNotCount(empty, empty));
        assertEquals(0L, MutableBitSet.andNotCount(empty, partial));
        assertEquals(0L, MutableBitSet.andNotCount(empty, full));
        assertEquals((N / 2L), MutableBitSet.andNotCount(partial, empty));
        assertEquals(0L, MutableBitSet.andNotCount(partial, partial));
        assertEquals(0L, MutableBitSet.andNotCount(partial, full));
        assertEquals(N, MutableBitSet.andNotCount(full, empty));
        assertEquals((N / 2L), MutableBitSet.andNotCount(full, partial));
        assertEquals(0L, MutableBitSet.andNotCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testAndNotCountNullA() {
        MutableBitSet.andNotCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testAndNotCountNullB() {
        MutableBitSet.andNotCount(empty, null);
    }

    @Test
    public void testStaticAnd() {
        assertEquals(empty, MutableBitSet.and(empty, empty));
        assertEquals(empty, MutableBitSet.and(empty, partial));
        assertEquals(empty, MutableBitSet.and(empty, full));
        assertEquals(empty, MutableBitSet.and(partial, empty));
        assertEquals(partial, MutableBitSet.and(partial, partial));
        assertEquals(partial, MutableBitSet.and(partial, full));
        assertEquals(empty, MutableBitSet.and(full, empty));
        assertEquals(partial, MutableBitSet.and(full, partial));
        assertEquals(full, MutableBitSet.and(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNullA() {
        MutableBitSet.and(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticAndNullB() {
        MutableBitSet.and(empty, null);
    }

    @Test
    public void testAndCount() {
        assertEquals(0L, MutableBitSet.andCount(empty, empty));
        assertEquals(0L, MutableBitSet.andCount(empty, partial));
        assertEquals(0L, MutableBitSet.andCount(empty, full));
        assertEquals(0L, MutableBitSet.andCount(partial, empty));
        assertEquals((N / 2L), MutableBitSet.andCount(partial, partial));
        assertEquals((N / 2L), MutableBitSet.andCount(partial, full));
        assertEquals(0L, MutableBitSet.andCount(full, empty));
        assertEquals((N / 2L), MutableBitSet.andCount(full, partial));
        assertEquals(N, MutableBitSet.andCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testAndCountNullA() {
        MutableBitSet.andCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testAndCountNullB() {
        MutableBitSet.andCount(empty, null);
    }

    @Test
    public void testStaticOr() {
        assertEquals(empty, MutableBitSet.or(empty, empty));
        assertEquals(partial, MutableBitSet.or(empty, partial));
        assertEquals(full, MutableBitSet.or(empty, full));
        assertEquals(partial, MutableBitSet.or(partial, empty));
        assertEquals(partial, MutableBitSet.or(partial, partial));
        assertEquals(full, MutableBitSet.or(partial, full));
        assertEquals(full, MutableBitSet.or(full, empty));
        assertEquals(full, MutableBitSet.or(full, partial));
        assertEquals(full, MutableBitSet.or(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticOrCountNullA() {
        MutableBitSet.or(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticOrCountNullB() {
        MutableBitSet.or(empty, null);
    }

    @Test
    public void testOrCount() {
        assertEquals(0L, MutableBitSet.orCount(empty, empty));
        assertEquals((N / 2L), MutableBitSet.orCount(empty, partial));
        assertEquals(N, MutableBitSet.orCount(empty, full));
        assertEquals((N / 2L), MutableBitSet.orCount(partial, empty));
        assertEquals((N / 2L), MutableBitSet.orCount(partial, partial));
        assertEquals(N, MutableBitSet.orCount(partial, full));
        assertEquals(N, MutableBitSet.orCount(full, empty));
        assertEquals(N, MutableBitSet.orCount(full, partial));
        assertEquals(N, MutableBitSet.orCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testOrCountNullA() {
        MutableBitSet.orCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testOrCountNullB() {
        MutableBitSet.orCount(empty, null);
    }

    @Test
    public void testStaticXor() {
        assertEquals(empty, MutableBitSet.xor(empty, empty));
        assertEquals(partial, MutableBitSet.xor(empty, partial));
        assertEquals(full, MutableBitSet.xor(empty, full));
        assertEquals(partial, MutableBitSet.xor(partial, empty));
        assertEquals(empty, MutableBitSet.xor(partial, partial));

        MutableBitSet m = new MutableBitSet(N);
        m.set(0, (N / 2L));

        assertEquals(m, MutableBitSet.xor(partial, full));
        assertEquals(full, MutableBitSet.xor(full, empty));
        assertEquals(m, MutableBitSet.xor(full, partial));
        assertEquals(empty, MutableBitSet.xor(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testStaticXorNullA() {
        MutableBitSet.xor(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testStaticXorNullB() {
        MutableBitSet.xor(empty, null);
    }

    @Test
    public void testXorCount() {
        assertEquals(0L, MutableBitSet.xorCount(empty, empty));
        assertEquals((N / 2L), MutableBitSet.xorCount(empty, partial));
        assertEquals(N, MutableBitSet.xorCount(empty, full));
        assertEquals((N / 2L), MutableBitSet.xorCount(partial, empty));
        assertEquals(0L, MutableBitSet.xorCount(partial, partial));
        assertEquals((N / 2L), MutableBitSet.xorCount(partial, full));
        assertEquals(N, MutableBitSet.xorCount(full, empty));
        assertEquals((N / 2L), MutableBitSet.xorCount(full, partial));
        assertEquals(0L, MutableBitSet.xorCount(full, full));
    }

    @Test(expected=NullPointerException.class)
    public void testXorCountNullA() {
        MutableBitSet.xorCount(null, empty);
    }

    @Test(expected=NullPointerException.class)
    public void testXorCountNullB() {
        MutableBitSet.xorCount(empty, null);
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
    public void testLogicalMethodChaining() {
        MutableBitSet result = empty.or(partial).xor(full);
        assertNotNull(result);
    }

    @Test
    public void testImmutableCopy() {
        ImmutableBitSet immutableEmpty = empty.immutableCopy();
        assertNotNull(immutableEmpty);
        assertTrue(immutableEmpty.isEmpty());

        ImmutableBitSet immutablePartial = partial.immutableCopy();
        assertNotNull(immutablePartial);
        assertEquals((N / 2L), immutablePartial.cardinality());

        ImmutableBitSet immutableFull = full.immutableCopy();
        assertNotNull(immutableFull);
        assertTrue(immutableFull.get(0L));
        assertTrue(immutableFull.get(N - 1L));
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

        assertEquals(empty, (MutableBitSet) dest);
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

        assertEquals(full, (MutableBitSet) dest);
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

        assertEquals(partial, (MutableBitSet) dest);
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

        assertEquals(half, (MutableBitSet) dest);
    }

    // regenerate serialized resources each time serialization format or value of N changes
    //    and copy files to src/test/resources/org/dishevelled/bitset
    public void writeSerializedResources() throws Exception {
        writeSerializedResource("mutableEmpty.ser", empty);
        writeSerializedResource("mutableFull.ser", full);
        writeSerializedResource("mutablePartial.ser", partial);
        writeSerializedResource("mutableHalf.ser", half);
    }

    private static void writeSerializedResource(final String name, final MutableBitSet bitset) {
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
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("mutableEmpty.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(empty, (MutableBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityFull() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("mutableFull.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(full, (MutableBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityPartial() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("mutablePartial.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(partial, (MutableBitSet) dest);
    }

    @Test
    public void testSerializationCompatibilityHalf() throws Exception {
        ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("mutableHalf.ser"));
        Object dest = in.readObject();
        in.close();

        assertEquals(half, (MutableBitSet) dest);
    }
}