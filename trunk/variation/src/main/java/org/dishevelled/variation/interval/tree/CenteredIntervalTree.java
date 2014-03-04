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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.dishevelled.variation.interval.Interval;

/**
 * Centered interval tree.
 *
 * http://en.wikipedia.org/wiki/Interval_tree#Centered_interval_tree
 */
public final class CenteredIntervalTree
{
    private final Node root;


    /**
     * Create a new centered interval tree with the specified intervals.
     *
     * @param intervals variable number of intervals, must not be null and must
     *    not contain any null intervals
     */
    public CenteredIntervalTree(final Interval... intervals)
    {
        this(ImmutableList.copyOf(intervals));
    }

    /**
     * Create a new centered interval tree with the specified intervals.
     *
     * @param intervals intervals, must not be null and must not contain any null intervals
     */
    public CenteredIntervalTree(final Iterable<Interval> intervals)
    {
        checkNotNull(intervals);
        root = createNode(intervals);
    }


    /**
     * Return the number of intervals in this interval tree at the specified location.
     *
     * @param location location
     * @return the number of intervals in this interval tree at the specified location
     */
    public int count(final int location)
    {
        return Iterables.size(query(location));
    }

    /**
     * Return the intervals in this interval tree at the specified location, if any.
     *
     * @param location location
     * @return the intervals in this interval tree at the specified location, if any
     */
    public Iterable<Interval> query(final int location)
    {
        Interval singleton = Interval.singleton(location);
        return intersect(singleton);
    }

    /**
     * Return the number of intervals in this interval tree that intersect the specified interval.
     *
     * @param interval interval to intersect, must not be null
     * @return the number of intervals in this interval tree that intersect the specified interval
     */
    public int count(final Interval interval)
    {
        return Iterables.size(intersect(interval));
    }

    /**
     * Return the intervals in this interval tree that intersect the specified interval, if any.
     *
     * @param interval interval to intersect, must not be null
     * @return the intervals in this interval tree that intersect the specified interval, if any
     */
    public Iterable<Interval> intersect(final Interval interval)
    {
        checkNotNull(interval);
        Set<Interval> result = Sets.newHashSet();
        Set<Node> visited = Sets.newHashSet();
        depthFirstSearch(interval, root, result, visited);
        return result;
    }

    private Node createNode(final Iterable<Interval> intervals)
    {
        Interval span = first(intervals);
        if (span == null) {
            return null;
        }
        for (Interval interval : intervals) {
            checkNotNull(interval, "ranges must not contain null intervals");
            span = interval.span(span);
        }
        if (span.isEmpty())
        {
            return null;
        }
        int center = span.center();
        List<Interval> left = Lists.newArrayList();
        List<Interval> right = Lists.newArrayList();
        List<Interval> overlap = Lists.newArrayList();
        for (Interval interval : intervals)
        {
            if (interval.isLessThan(center))
            {
                left.add(interval);
            }
            else if (interval.isGreaterThan(center))
            {
                right.add(interval);
            }
            else
            {
                overlap.add(interval);
            }
        }
        return new Node(center, createNode(left), createNode(right), overlap);
    }

    private void depthFirstSearch(final Interval query, final Node node, final Set<Interval> result, final Set<Node> visited)
    {
        if (node == null || visited.contains(node) || query.isEmpty())
        {
            return;
        }
        if (node.left() != null && query.isLessThan(node.center()))
        {
            depthFirstSearch(query, node.left(), result, visited);
        }
        else if (node.right() != null && query.isGreaterThan(node.center()))
        {
            depthFirstSearch(query, node.right(), result, visited);
        }
        if (query.isGreaterThan(node.center()))
        {
            for (Interval interval : node.overlapByUpperEndpoint())
            {
                if (interval.intersects(query))
                {
                    result.add(interval);
                }
                if (query.isGreaterThan(interval.upperEndpoint()))
                {
                    break;
                }
            }
        }
        else if (query.isLessThan(node.center()))
        {
            for (Interval interval : node.overlapByLowerEndpoint())
            {
                if (interval.intersects(query))
                {
                    result.add(interval);
                }
                if (query.isLessThan(interval.lowerEndpoint()))
                {
                    break;
                }
            }
        }
        else
        {
            result.addAll(node.overlapByLowerEndpoint());
        }
        visited.add(node);
    }

    private static Interval first(final Iterable<Interval> intervals) {
        Iterator<Interval> iterator = intervals.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    private static class Node
    {
        private final int center;
        private final Node left;
        private final Node right;
        private final List<Interval> overlapByLowerEndpoint;
        private final List<Interval> overlapByUpperEndpoint;

        Node(final int center, final Node left, final Node right, final List<Interval> overlap)
        {
            this.center = center;
            this.left = left;
            this.right = right;
            overlapByLowerEndpoint = Lists.newArrayList(overlap);
            overlapByUpperEndpoint = Lists.newArrayList(overlap);
            Collections.sort(overlapByLowerEndpoint, Interval.orderingByLowerEndpoint());
            Collections.sort(overlapByUpperEndpoint, Interval.reverseOrderingByUpperEndpoint());
        }

        int center()
        {
            return center;
        }

        Node left()
        {
            return left;
        }

        Node right()
        {
            return right;
        }

        List<Interval> overlapByLowerEndpoint()
        {
            return overlapByLowerEndpoint;
        }

        List<Interval> overlapByUpperEndpoint()
        {
            return overlapByUpperEndpoint;
        }
    }
}
