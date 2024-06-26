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

import java.util.concurrent.atomic.AtomicLong;

import org.dishevelled.functor.UnaryProcedure;

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

    private static final class Capacity implements Op<AbstractBitSet, Long> {
        public Long execute(final AbstractBitSet[] bitsets) {
            long result = 0L;
            for (AbstractBitSet bitset : bitsets) {
                result += bitset.capacity();
            }
            return result;
        }
    };

    private static final class Cardinality implements Op<AbstractBitSet, Long> {
        public Long execute(final AbstractBitSet[] bitsets) {
            long result = 0L;
            for (AbstractBitSet bitset : bitsets) {
                result += bitset.cardinality();
            }
            return result;
        }
    };

    private static final class SumGet implements Op<AbstractBitSet, Long> {
        public Long execute(final AbstractBitSet[] bitsets) {
            long result = 0L;
            for (AbstractBitSet bitset : bitsets) {
                for (long i = 0, size = bitset.capacity(); i < size; i++) {
                    if (bitset.get(i)) {
                        result++;
                    }
                }
            }
            return result;
        }
    };

    private static final class SumGetQuick implements Op<AbstractBitSet, Long> {
        public Long execute(final AbstractBitSet[] bitsets) {
            long result = 0L;
            for (AbstractBitSet bitset : bitsets) {
                for (long i = 0, size = bitset.capacity(); i < size; i++) {
                    if (bitset.get(i)) {
                        result++;
                    }
                }
            }
            return result;
        }
    };

    private static final class SumNextSetBit implements Op<AbstractBitSet, Long> {
        public Long execute(final AbstractBitSet[] bitsets) {
            long result = 0L;
            for (AbstractBitSet bitset : bitsets) {
                for (long i = bitset.nextSetBit(0L); i >= 0L; i = bitset.nextSetBit(i + 1L)) {
                    result++;
                }
            }
            return result;
        }
    };

    private static final class SumForEachSetBitAtomicLong implements Op<AbstractBitSet, Long> {
        public Long execute(final AbstractBitSet[] bitsets) {
            final AtomicLong result = new AtomicLong(0L);
            for (AbstractBitSet bitset : bitsets) {
                bitset.forEachSetBit(new UnaryProcedure<Long>() {
                        @Override
                        public void run(final Long index) {
                            result.getAndIncrement();
                        }
                    });
            }
            return result.get();
        }
    };

    private static final class SumForEachSetBitCount implements Op<AbstractBitSet, Long> {
        public Long execute(final AbstractBitSet[] bitsets) {
            long result = 0L;
            for (AbstractBitSet bitset : bitsets) {
                AbstractBitSetTest.Count count = new AbstractBitSetTest.Count();
                bitset.forEachSetBit(count);
                result += count.count();
            }
            return result;
        }
    };

    @Test
    public void testSparseMutableCount() {
        run("sparse mutable count", SPARSE_MUTABLE, new Count());
    }

    @Test
    public void testSparseMutableCapacity() {
        run("sparse mutable capacity", SPARSE_MUTABLE, new Capacity());
    }

    @Test
    public void testSparseMutableCardinality() {
        run("sparse mutable cardinality", SPARSE_MUTABLE, new Cardinality());
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
    public void testSparseMutableSumGet() {
        run("sparse mutable sum get", SPARSE_MUTABLE, new SumGet());
    }

    @Test
    public void testSparseMutableSumGetQuick() {
        run("sparse mutable sum getQuick", SPARSE_MUTABLE, new SumGetQuick());
    }

    @Test
    public void testSparseMutableSumNextSetBit() {
        run("sparse mutable sum nextSetBit", SPARSE_MUTABLE, new SumNextSetBit());
    }

    @Test
    public void testSparseMutableSumForEachSetBitAtomicLong() {
        run("sparse mutable sum forEachSetBit atomic long", SPARSE_MUTABLE, new SumForEachSetBitAtomicLong());
    }

    @Test
    public void testSparseMutableSumForEachSetBitCount() {
        run("sparse mutable sum forEachSetBit count", SPARSE_MUTABLE, new SumForEachSetBitCount());
    }


    @Test
    public void testSparseImmutableCount() {
        run("sparse immutable count", SPARSE_IMMUTABLE, new Count());
    }

    @Test
    public void testSparseImmutableCapacity() {
        run("sparse immutable capacity", SPARSE_IMMUTABLE, new Capacity());
    }

    @Test
    public void testSparseImmutableCardinality() {
        run("sparse immutable cardinality", SPARSE_IMMUTABLE, new Cardinality());
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
    public void testSparseImmutableSumGet() {
        run("sparse immutable sum get", SPARSE_IMMUTABLE, new SumGet());
    }

    @Test
    public void testSparseImmutableSumGetQuick() {
        run("sparse immutable sum getQuick", SPARSE_IMMUTABLE, new SumGetQuick());
    }

    @Test
    public void testSparseImmutableSumNextSetBit() {
        run("sparse immutable sum nextSetBit", SPARSE_IMMUTABLE, new SumNextSetBit());
    }

    @Test
    public void testSparseImmutableSumForEachSetBitAtomicLong() {
        run("sparse immutable sum forEachSetBit atomic long", SPARSE_IMMUTABLE, new SumForEachSetBitAtomicLong());
    }

    @Test
    public void testSparseImmutableSumForEachSetBitCount() {
        run("sparse immutable sum forEachSetBit count", SPARSE_IMMUTABLE, new SumForEachSetBitCount());
    }


    @Test
    public void testDenseMutableCount() {
        run("dense mutable count", DENSE_MUTABLE, new Count());
    }

    @Test
    public void testDenseMutableCapacity() {
        run("dense mutable capacity", DENSE_MUTABLE, new Capacity());
    }

    @Test
    public void testDenseMutableCardinality() {
        run("dense mutable cardinality", DENSE_MUTABLE, new Cardinality());
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
    public void testDenseMutableSumGet() {
        run("dense mutable sum get", DENSE_MUTABLE, new SumGet());
    }

    @Test
    public void testDenseMutableSumGetQuick() {
        run("dense mutable sum getQuick", DENSE_MUTABLE, new SumGetQuick());
    }

    @Test
    public void testDenseMutableSumNextSetBit() {
        run("dense mutable sum nextSetBit", DENSE_MUTABLE, new SumNextSetBit());
    }

    @Test
    public void testDenseMutableSumForEachSetBitAtomicLong() {
        run("dense mutable sum forEachSetBit atomic long", DENSE_MUTABLE, new SumForEachSetBitAtomicLong());
    }

    @Test
    public void testDenseMutableSumForEachSetBitCount() {
        run("dense mutable sum forEachSetBit count", DENSE_MUTABLE, new SumForEachSetBitCount());
    }


    @Test
    public void testDenseImmutableCount() {
        run("dense immutable count", DENSE_IMMUTABLE, new Count());
    }

    @Test
    public void testDenseImmutableCapacity() {
        run("dense immutable capacity", DENSE_IMMUTABLE, new Capacity());
    }

    @Test
    public void testDenseImmutableCardinality() {
        run("dense immutable cardinality", DENSE_IMMUTABLE, new Cardinality());
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

    @Test
    public void testDenseImmutableSumGet() {
        run("dense immutable sum get", DENSE_IMMUTABLE, new SumGet());
    }

    @Test
    public void testDenseImmutableSumGetQuick() {
        run("dense immutable sum getQuick", DENSE_IMMUTABLE, new SumGetQuick());
    }

    @Test
    public void testDenseImmutableSumNextSetBit() {
        run("dense immutable sum nextSetBit", DENSE_IMMUTABLE, new SumNextSetBit());
    }

    @Test
    public void testDenseImmutableSumForEachSetBitAtomicLong() {
        run("dense immutable sum forEachSetBit atomic long", DENSE_IMMUTABLE, new SumForEachSetBitAtomicLong());
    }

    @Test
    public void testDenseImmutableSumForEachSetBitCount() {
        run("dense immutable sum forEachSetBit count", DENSE_IMMUTABLE, new SumForEachSetBitCount());
    }
}
