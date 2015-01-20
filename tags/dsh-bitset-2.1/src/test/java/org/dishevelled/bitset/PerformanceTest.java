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

import org.junit.Ignore;
import org.junit.Test;

/**
 * Performance tests for bit set operations.
 */
@Ignore
public final class PerformanceTest {
    private static final int N = 10000;
    private static final long M = 8000L;

    private static <T> void run(final String name, final Factory<? extends AbstractBitSet> factory, final Op<? super AbstractBitSet, T> operation) {
        AbstractBitSet[] bitsets = new AbstractBitSet[N];
        for (int i = 0; i < N; i++) {
            bitsets[i] = factory.create();
        }
        long now = System.nanoTime();
        T result = operation.execute(bitsets);
        long end = System.nanoTime();
        long elapsed = end - now;
        System.out.println(name + "\t" + result + "\t" + elapsed + "\t" + (elapsed/N));
    }

    interface Factory<E> {
        E create();
    }

    interface Op<E, RV> {
        RV execute(E[] values);
    }

    private static Factory<MutableBitSet> DENSE_MUTABLE = new Factory<MutableBitSet>() {
        public MutableBitSet create() {
            MutableBitSet m = new MutableBitSet(M);
            for (long i = 0L; i < M; i += 1L) {
                if ((i % 5) == 0L) {
                    m.set(i);
                }
            }
            return m;
        }
    };

    private static Factory<MutableBitSet> SPARSE_MUTABLE = new Factory<MutableBitSet>() {
        public MutableBitSet create() {
            MutableBitSet m = new MutableBitSet(M);
            for (long i = 0L; i < M; i += 1L) {
                if ((i % 50) == 0L) {
                    m.set(i);
                }
            }
            return m;
        }
    };

    private static Factory<ImmutableBitSet> DENSE_IMMUTABLE = new Factory<ImmutableBitSet>() {
        public ImmutableBitSet create() {
            MutableBitSet m = new MutableBitSet(M);
            for (long i = 0L; i < M; i += 1L) {
                if ((i % 5) == 0L) {
                    m.set(i);
                }
            }
            return m.immutableCopy();
        }
    };

    private static Factory<ImmutableBitSet> SPARSE_IMMUTABLE = new Factory<ImmutableBitSet>() {
        public ImmutableBitSet create() {
            MutableBitSet m = new MutableBitSet(M);
            for (long i = 0L; i < M; i += 1L) {
                if ((i % 50) == 0L) {
                    m.set(i);
                }
            }
            return m.immutableCopy();
        }
    };

    private static final class Count implements Op<AbstractBitSet, Integer> {
        private int count = 0;

        public Integer execute(final AbstractBitSet[] bitsets) {
            for (AbstractBitSet bitset : bitsets) {
                count++;
            }
            return Integer.valueOf(count);
        }
    };

    private static final class And implements Op<AbstractBitSet, MutableBitSet> {
        public MutableBitSet execute(final AbstractBitSet[] bitsets) {
            MutableBitSet result = new MutableBitSet(M);
            for (AbstractBitSet bitset : bitsets) {
                result.and(bitset);
            }
            return result;
        }
    };

    private static final class AndNot implements Op<AbstractBitSet, MutableBitSet> {
        public MutableBitSet execute(final AbstractBitSet[] bitsets) {
            MutableBitSet result = new MutableBitSet(M);
            for (AbstractBitSet bitset : bitsets) {
                result.andNot(bitset);
            }
            return result;
        }
    };

    private static final class Or implements Op<AbstractBitSet, MutableBitSet> {
        public MutableBitSet execute(final AbstractBitSet[] bitsets) {
            MutableBitSet result = new MutableBitSet(M);
            for (AbstractBitSet bitset : bitsets) {
                result.or(bitset);
            }
            return result;
        }
    };

    private static final class Xor implements Op<AbstractBitSet, MutableBitSet> {
        public MutableBitSet execute(final AbstractBitSet[] bitsets) {
            MutableBitSet result = new MutableBitSet(M);
            for (AbstractBitSet bitset : bitsets) {
                result.xor(bitset);
            }
            return result;
        }
    };

    @Test
    public void testSparseMutableCount() {
        run("sparse mutable count", SPARSE_MUTABLE, new Count());
    }

    @Test
    public void testSparseImmutableCount() {
        run("sparse immutable count", SPARSE_IMMUTABLE, new Count());
    }

    @Test
    public void testSparseMutableAnd() {
        run("sparse mutable and", SPARSE_MUTABLE, new And());
    }

    @Test
    public void testSparseMutableAndNot() {
        run("sparse mutable andNot", SPARSE_MUTABLE, new AndNot());
    }

    @Test
    public void testSparseMutableOr() {
        run("sparse mutable or", SPARSE_MUTABLE, new Or());
    }

    @Test
    public void testSparseMutableXor() {
        run("sparse mutable xor", SPARSE_MUTABLE, new Xor());
    }

    @Test
    public void testSparseImmutableAnd() {
        run("sparse immutable and", SPARSE_IMMUTABLE, new And());
    }

    @Test
    public void testSparseImmutableAndNot() {
        run("sparse immutable andNot", SPARSE_IMMUTABLE, new AndNot());
    }

    @Test
    public void testSparseImmutableOr() {
        run("sparse immutable or", SPARSE_IMMUTABLE, new Or());
    }

    @Test
    public void testSparseImmutableXor() {
        run("sparse immutable xor", SPARSE_IMMUTABLE, new Xor());
    }


    @Test
    public void testDenseMutableAnd() {
        run("dense mutable and", DENSE_MUTABLE, new And());
    }

    @Test
    public void testDenseMutableAndNot() {
        run("dense mutable andNot", DENSE_MUTABLE, new AndNot());
    }

    @Test
    public void testDenseMutableOr() {
        run("dense mutable or", DENSE_MUTABLE, new Or());
    }

    @Test
    public void testDenseMutableXor() {
        run("dense mutable xor", DENSE_MUTABLE, new Xor());
    }

    @Test
    public void testDenseImmutableAnd() {
        run("dense immutable and", DENSE_IMMUTABLE, new And());
    }

    @Test
    public void testDenseImmutableAndNot() {
        run("dense immutable andNot", DENSE_IMMUTABLE, new AndNot());
    }

    @Test
    public void testDenseImmutableOr() {
        run("dense immutable or", DENSE_IMMUTABLE, new Or());
    }

    @Test
    public void testDenseImmutableXor() {
        run("dense immutable xor", DENSE_IMMUTABLE, new Xor());
    }
}
