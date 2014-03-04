/*

    dsh-variation  Variation.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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
package org.dishevelled.variation.interval.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.junit.BeforeClass;
import org.junit.Test;

import org.dishevelled.variation.interval.Interval;

/**
 * Unit test for CenteredIntervalTree.
 */
public final class CenteredIntervalTreeTest
{
    private static final int N = 1000;
    private static final Interval empty = Interval.closedOpen(1, 1);
    private static final Interval singleton = Interval.singleton(1);
    private static final Interval miss = Interval.singleton(-1);
    private static final Interval closed = Interval.closed(1, 100);
    private static final Interval open = Interval.open(1, 100);
    private static final Interval closedOpen = Interval.closedOpen(1, 100);
    private static final Interval openClosed = Interval.openClosed(1, 100);
    private static final List<Interval> dense = Lists.newArrayListWithExpectedSize(N);
    private static final List<Interval> sparse = Lists.newArrayListWithExpectedSize(N);

    @BeforeClass
    public static void staticSetUp() {
        for (int i = 0; i < N; i++) {
            dense.add(Interval.closed(i, i + 100));
            sparse.add(Interval.closed(i * 100, i * 100 + 100));
        }
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullRanges() {
        new CenteredIntervalTree((Interval) null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullIterableRanges() {
        new CenteredIntervalTree((Iterable<Interval>) null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullRangeInRanges() {
        new CenteredIntervalTree(empty, null, singleton);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullRangeInIterableRanges() {
        List<Interval> ranges = new ArrayList<Interval>();
        ranges.add(empty);
        ranges.add(null);
        ranges.add(singleton);
        new CenteredIntervalTree(ranges);
    }

    @Test
    public void testConstuctorEmpty() {
        assertNotNull(new CenteredIntervalTree(empty));
    }

    @Test
    public void testConstructorSingleton() {
        assertNotNull(new CenteredIntervalTree(singleton));
    }

    @Test
    public void testConstructorDense() {
        assertNotNull(new CenteredIntervalTree(dense));
    }

    @Test
    public void testConstructorSparse() {
        assertNotNull(new CenteredIntervalTree(sparse));
    }


    @Test
    public void testQueryEmpty() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(empty).query(0)));
    }

    @Test
    public void testQuerySingletonMiss() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(singleton).query(0)));
    }

    @Test
    public void testQuerySingletonHit() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(singleton).query(1)));
    }

    @Test
    public void testQueryDense() {
        assertEquals(5, Iterables.size(new CenteredIntervalTree(dense).query(4)));
    }

    @Test
    public void testQuerySparse() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(sparse).query(4)));
    }


    @Test
    public void testCountEmpty() {
        assertEquals(0, new CenteredIntervalTree(empty).count(0));
    }

    @Test
    public void testCountSingletonMiss() {
        assertEquals(0, new CenteredIntervalTree(singleton).count(0));
    }

    @Test
    public void testCountSingletonHit() {
        assertEquals(1, new CenteredIntervalTree(singleton).count(1));
    }

    @Test
    public void testCountDense() {
        assertEquals(5, new CenteredIntervalTree(dense).count(4));
    }

    @Test
    public void testCountSparse() {
        assertEquals(1, new CenteredIntervalTree(sparse).count(4));
    }


    @Test(expected=NullPointerException.class)
    public void testIntersectNullRange() {
        new CenteredIntervalTree(empty).intersect(null);
    }

    @Test
    public void testIntersectEmptyEmptyRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(empty).intersect(empty)));
    }

    @Test
    public void testIntersectEmptySingletonRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(empty).intersect(singleton)));
    }

    @Test
    public void testIntersectEmptyClosedRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(empty).intersect(closed)));
    }

    @Test
    public void testIntersectEmptyOpenRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(empty).intersect(open)));
    }

    @Test
    public void testIntersectEmptyClosedOpenRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(empty).intersect(closedOpen)));
    }

    @Test
    public void testIntersectEmptyOpenClosedRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(empty).intersect(openClosed)));
    }

    @Test
    public void testIntersectSingletonEmptyRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(singleton).intersect(empty)));
    }

    @Test
    public void testIntersectSingletonSingletonRangeMiss() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(singleton).intersect(miss)));
    }

    @Test
    public void testIntersectSingletonSingletonRangeHit() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(singleton).intersect(singleton)));
    }

    @Test
    public void testIntersectSingletonClosedRange() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(singleton).intersect(closed)));
    }

    @Test
    public void testIntersectSingletonOpenRange() {
        assertEquals(0, Iterables.size(new CenteredIntervalTree(singleton).intersect(open)));
    }

    @Test
    public void testIntersectSingletonClosedOpenRange() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(singleton).intersect(closedOpen)));
    }

    @Test
    public void testIntersectSingletonOpenClosedRange() {
        assertEquals(0, Iterables.size(new CenteredIntervalTree(singleton).intersect(openClosed)));
    }


    @Test
    public void testIntersectSparseEmptyRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(sparse).intersect(empty)));
    }

    @Test
    public void testIntersectSparseSingletonRangeMiss() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(sparse).intersect(miss)));
    }

    @Test
    public void testIntersectSparseSingletonRangeHit() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(sparse).intersect(singleton)));
    }

    @Test
    public void testIntersectSparseClosedRange() {
        assertEquals(2, Iterables.size(new CenteredIntervalTree(sparse).intersect(closed)));
    }

    @Test
    public void testIntersectSparseOpenRange() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(sparse).intersect(open)));
    }

    @Test
    public void testIntersectSparseClosedOpenRange() {
        assertEquals(1, Iterables.size(new CenteredIntervalTree(sparse).intersect(closedOpen)));
    }

    @Test
    public void testIntersectSparseOpenClosedRange() {
        assertEquals(2, Iterables.size(new CenteredIntervalTree(sparse).intersect(openClosed)));
    }


    @Test
    public void testIntersectDenseEmptyRange() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(dense).intersect(empty)));
    }

    @Test
    public void testIntersectDenseSingletonRangeMiss() {
        assertTrue(Iterables.isEmpty(new CenteredIntervalTree(dense).intersect(miss)));
    }

    @Test
    public void testIntersectDenseSingletonRangeHit() {
        assertEquals(2, Iterables.size(new CenteredIntervalTree(dense).intersect(singleton)));
    }

    @Test
    public void testIntersectDenseClosedRange() {
        assertEquals(101, Iterables.size(new CenteredIntervalTree(dense).intersect(closed)));
    }

    @Test
    public void testIntersectDenseOpenRange() {
        assertEquals(100, Iterables.size(new CenteredIntervalTree(dense).intersect(open)));
    }

    @Test
    public void testIntersectDenseClosedOpenRange() {
        assertEquals(100, Iterables.size(new CenteredIntervalTree(dense).intersect(closedOpen)));
    }

    @Test
    public void testIntersectDenseOpenClosedRange() {
        assertEquals(101, Iterables.size(new CenteredIntervalTree(dense).intersect(openClosed)));
    }


    @Test(expected=NullPointerException.class)
    public void testCountNullRange() {
        new CenteredIntervalTree(empty).count(null);
    }

    @Test
    public void testCountEmptyEmptyRange() {
        assertEquals(0, new CenteredIntervalTree(empty).count(empty));
    }

    @Test
    public void testCountEmptySingletonRange() {
        assertEquals(0, new CenteredIntervalTree(empty).count(singleton));
    }

    @Test
    public void testCountEmptyClosedRange() {
        assertEquals(0, new CenteredIntervalTree(empty).count(closed));
    }

    @Test
    public void testCountEmptyOpenRange() {
        assertEquals(0, new CenteredIntervalTree(empty).count(open));
    }

    @Test
    public void testCountEmptyClosedOpenRange() {
        assertEquals(0, new CenteredIntervalTree(empty).count(closedOpen));
    }

    @Test
    public void testCountEmptyOpenClosedRange() {
        assertEquals(0, new CenteredIntervalTree(empty).count(openClosed));
    }

    @Test
    public void testCountSingletonEmptyRange() {
        assertEquals(0, new CenteredIntervalTree(singleton).count(empty));
    }

    @Test
    public void testCountSingletonSingletonRangeMiss() {
        assertEquals(0, new CenteredIntervalTree(singleton).count(miss));
    }

    @Test
    public void testCountSingletonSingletonRangeHit() {
        assertEquals(1, new CenteredIntervalTree(singleton).count(singleton));
    }

    @Test
    public void testCountSingletonClosedRange() {
        assertEquals(1, new CenteredIntervalTree(singleton).count(closed));
    }

    @Test
    public void testCountSingletonOpenRange() {
        assertEquals(0, new CenteredIntervalTree(singleton).count(open));
    }

    @Test
    public void testCountSingletonClosedOpenRange() {
        assertEquals(1, new CenteredIntervalTree(singleton).count(closedOpen));
    }

    @Test
    public void testCountSingletonOpenClosedRange() {
        assertEquals(0, new CenteredIntervalTree(singleton).count(openClosed));
    }


    @Test
    public void testCountSparseEmptyRange() {
        assertEquals(0, new CenteredIntervalTree(sparse).count(empty));
    }

    @Test
    public void testCountSparseSingletonRangeMiss() {
        assertEquals(0, new CenteredIntervalTree(sparse).count(miss));
    }

    @Test
    public void testCountSparseSingletonRangeHit() {
        assertEquals(1, new CenteredIntervalTree(sparse).count(singleton));
    }

    @Test
    public void testCountSparseClosedRange() {
        assertEquals(2, new CenteredIntervalTree(sparse).count(closed));
    }

    @Test
    public void testCountSparseOpenRange() {
        assertEquals(1, new CenteredIntervalTree(sparse).count(open));
    }

    @Test
    public void testCountSparseClosedOpenRange() {
        assertEquals(1, new CenteredIntervalTree(sparse).count(closedOpen));
    }

    @Test
    public void testCountSparseOpenClosedRange() {
        assertEquals(2, new CenteredIntervalTree(sparse).count(openClosed));
    }


    @Test
    public void testCountDenseEmptyRange() {
        assertEquals(0, new CenteredIntervalTree(dense).count(empty));
    }

    @Test
    public void testCountDenseSingletonRangeMiss() {
        assertEquals(0, new CenteredIntervalTree(dense).count(miss));
    }

    @Test
    public void testCountDenseSingletonRangeHit() {
        assertEquals(2, new CenteredIntervalTree(dense).count(singleton));
    }

    @Test
    public void testCountDenseClosedRange() {
        assertEquals(101, new CenteredIntervalTree(dense).count(closed));
    }

    @Test
    public void testCountDenseOpenRange() {
        assertEquals(100, new CenteredIntervalTree(dense).count(open));
    }

    @Test
    public void testCountDenseClosedOpenRange() {
        assertEquals(100, new CenteredIntervalTree(dense).count(closedOpen));
    }

    @Test
    public void testCountDenseOpenClosedRange() {
        assertEquals(101, new CenteredIntervalTree(dense).count(openClosed));
    }
}
