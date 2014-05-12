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
package org.dishevelled.variation.range.tree;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;

/**
 * Range tree based on delegation to TreeRangeMap.
 *
 * @param <K> range endpoint type
 */
public final class TreeRangeMapRangeTree<K extends Comparable> extends AbstractRangeTree<K> {
    /** Tree range map. */
    private final TreeRangeMap<K, Object> treeRangeMap = TreeRangeMap.create();

    /** Value. */
    private static final Object VALUE = new Object();


    /**
     * Create a new tree range map range tree with the specified ranges.
     *
     * @param ranges ranges, must not be null
     */
    private TreeRangeMapRangeTree(final Iterable<Range<K>> ranges) {
        checkNotNull(ranges);
        for (Range<K> range : ranges) {
            treeRangeMap.put(range, VALUE);
        }
    }


    @Override
    public int size() {
        return treeRangeMap.asMapOfRanges().size();
    }

    @Override
    public boolean isEmpty() {
        return treeRangeMap.asMapOfRanges().isEmpty();
    }

    @Override
    public Iterable<Range<K>> intersect(final Range<K> query) {
        return treeRangeMap.subRangeMap(query).asMapOfRanges().keySet();
    }


    /**
     * Create and return a new range tree from the specified ranges.
     *
     * @param ranges ranges, must not be null
     * @return a new range tree from the specified ranges
     */
    // of, copyOf, from, ?
    public static <K extends Comparable> RangeTree<K> create(Iterable<Range<K>> ranges) {
        return new TreeRangeMapRangeTree<K>(ranges);
    }
}
