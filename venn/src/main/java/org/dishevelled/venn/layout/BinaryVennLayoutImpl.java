/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.layout;

import java.awt.Shape;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.Map;

import com.google.common.collect.Maps;

import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.venn.BinaryVennLayout;

import static org.dishevelled.venn.layout.VennLayoutUtils.toImmutableBitSet;

/**
 * Immutable implementation of BinaryVennLayout.
 *
 * @author  Michael Heuer
 */
public final class BinaryVennLayoutImpl
    implements BinaryVennLayout
{
    /** Shape for the first set. */
    private final Shape firstShape;

    /** Shape for the second set. */
    private final Shape secondShape;

    /** Lune center of the first only area. */
    private final Point2D firstOnlyLuneCenter;

    /** Lune center of the second only area. */
    private final Point2D secondOnlyLuneCenter;

    /** Lune center of the intersection area. */
    private final Point2D intersectionLuneCenter;

    /** Map of lune centers keyed by bit set. */
    private final Map<ImmutableBitSet, Point2D> luneCenters;

    /** Bounding rectangle. */
    private final Rectangle2D boundingRectangle;


    /**
     * Create a new binary venn layout with the specified parameters.
     *
     * @param firstShape shape for the first set, must not be null
     * @param secondShape shape for the second set, must not be null
     * @param firstOnlyLuneCenter lune center for the first only area
     * @param secondOnlyLuneCenter lune center for the second only area
     * @param intersectionLuneCenter lune center for the intersection area
     * @param boundingRectangle bounding rectangle, must not be null
     */
    public BinaryVennLayoutImpl(final Shape firstShape,
                                final Shape secondShape,
                                final Point2D firstOnlyLuneCenter,
                                final Point2D secondOnlyLuneCenter,
                                final Point2D intersectionLuneCenter,
                                final Rectangle2D boundingRectangle)
    {
        if (firstShape == null)
        {
            throw new IllegalArgumentException("firstShape must not be null");
        }
        if (secondShape == null)
        {
            throw new IllegalArgumentException("secondShape must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        // todo: defensive copy?
        this.firstShape = firstShape;
        this.secondShape = secondShape;
        this.firstOnlyLuneCenter = firstOnlyLuneCenter;
        this.secondOnlyLuneCenter = secondOnlyLuneCenter;
        this.intersectionLuneCenter = intersectionLuneCenter;
        this.boundingRectangle = boundingRectangle;

        luneCenters = Maps.newHashMapWithExpectedSize(3);

        luneCenters.put(toImmutableBitSet(0), this.firstOnlyLuneCenter);
        luneCenters.put(toImmutableBitSet(1), this.secondOnlyLuneCenter);

        luneCenters.put(toImmutableBitSet(0, 1), this.intersectionLuneCenter);
        // copy to immutable map?
    }


    /** {@inheritDoc} */
    public Shape firstShape()
    {
        // todo:  defensive copy?
        return firstShape;
    }

    /** {@inheritDoc} */
    public Shape secondShape()
    {
        return secondShape;
    }

    /** {@inheritDoc} */
    public Point2D firstOnlyLuneCenter()
    {
        return firstOnlyLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D secondOnlyLuneCenter()
    {
        return secondOnlyLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D intersectionLuneCenter()
    {
        return intersectionLuneCenter;
    }

    /** {@inheritDoc} */
    public int size()
    {
        return 2;
    }

    /** {@inheritDoc} */
    public Shape get(final int index)
    {
        if (index < 0 || index > 1)
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        switch (index)
        {
        case 0:
            return firstShape;
        case 1:
            return secondShape;
        default:
            break;
        }
        throw new IllegalStateException("invalid index " + index);
    }

    /** {@inheritDoc} */
    public Point2D luneCenter(final int index, final int... additional)
    {
        int maxIndex = size() - 1;
        if (index < 0 || index > maxIndex)
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        if (additional != null && additional.length > 0)
        {
            if (additional.length > maxIndex)
            {
                throw new IndexOutOfBoundsException("too many indices provided");
            }
            for (int i = 0, size = additional.length; i < size; i++)
            {
                if (additional[i] < 0 || additional[i] > maxIndex)
                {
                    throw new IndexOutOfBoundsException("additional index [" + i + "] out of bounds");
                }
            }
        }
        return luneCenters.get(toImmutableBitSet(index, additional));
    }

    /** {@inheritDoc} */
    public Rectangle2D boundingRectangle()
    {
        return boundingRectangle;
    }
}
