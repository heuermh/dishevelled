/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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

import java.util.HashMap;
import java.util.Map;

import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.venn.QuaternaryVennLayout;

import static org.dishevelled.venn.layout.VennLayoutUtils.toImmutableBitSet;

/**
 * Immutable implementation of QuaternaryVennLayout.
 *
 * @author  Michael Heuer
 */
public final class QuaternaryVennLayoutImpl
    implements QuaternaryVennLayout
{
    /** Shape for the first set. */
    private final Shape firstShape;

    /** Shape for the second set. */
    private final Shape secondShape;

    /** Shape for the third set. */
    private final Shape thirdShape;

    /** Shape for the fourth set. */
    private final Shape fourthShape;

    /** Lune center of the first only area. */
    private final Point2D firstOnlyLuneCenter;

    /** Lune center of the second only area. */
    private final Point2D secondOnlyLuneCenter;

    /** Lune center of the third only area. */
    private final Point2D thirdOnlyLuneCenter;

    /** Lune center of the fourth only area. */
    private final Point2D fourthOnlyLuneCenter;

    /** Lune center of the first second area. */
    private final Point2D firstSecondLuneCenter;

    /** Lune center of the first third area. */
    private final Point2D firstThirdLuneCenter;

    /** Lune center of the second third area. */
    private final Point2D secondThirdLuneCenter;

    /** Lune center of the first fourth area. */
    private final Point2D firstFourthLuneCenter;

    /** Lune center of the second fourth area. */
    private final Point2D secondFourthLuneCenter;

    /** Lune center of the third fourth area. */
    private final Point2D thirdFourthLuneCenter;

    /** Lune center of the first second third area. */
    private final Point2D firstSecondThirdLuneCenter;

    /** Lune center of the first second fourth area. */
    private final Point2D firstSecondFourthLuneCenter;

    /** Lune center of the first third fourth area. */
    private final Point2D firstThirdFourthLuneCenter;

    /** Lune center of the second third fourth area. */
    private final Point2D secondThirdFourthLuneCenter;

    /** Lune center of the intersection area. */
    private final Point2D intersectionLuneCenter;

    /** Map of lune centers keyed by bit set. */
    private final Map<ImmutableBitSet, Point2D> luneCenters;

    /** Bounding rectangle. */
    private final Rectangle2D boundingRectangle;


    /**
     * Create a new quaternary venn layout with the specified parameters.
     *
     * @param firstShape shape for the first set, must not be null
     * @param secondShape shape for the second set, must not be null
     * @param thirdShape shape for the third set, must not be null
     * @param fourthShape shape for the fourth set, must not be null
     * @param firstOnlyLuneCenter lune center for the first only area
     * @param secondOnlyLuneCenter lune center for the second only area
     * @param thirdOnlyLuneCenter lune center for the third only area
     * @param fourthOnlyLuneCenter lune center for the fourth only area
     * @param firstSecondLuneCenter lune center for the first second area
     * @param firstThirdLuneCenter lune center for the first third area
     * @param secondThirdLuneCenter lune center for the second third area
     * @param firstFourthLuneCenter lune center for the first fourth area
     * @param secondFourthLuneCenter lune center for the second fourth area
     * @param thirdFourthLuneCenter lune center for the third fourth area
     * @param firstSecondThirdLuneCenter lune center for the first second third area
     * @param firstSecondFourthLuneCenter lune center for the first second fourth area
     * @param firstThirdFourthLuneCenter lune center for the first third fourth area
     * @param secondThirdFourthLuneCenter lune center for the second third fourth area
     * @param intersectionLuneCenter lune center for the intersection area
     * @param boundingRectangle bounding rectangle, must not be null
     */
    public QuaternaryVennLayoutImpl(final Shape firstShape,
                                    final Shape secondShape,
                                    final Shape thirdShape,
                                    final Shape fourthShape,
                                    final Point2D firstOnlyLuneCenter,
                                    final Point2D secondOnlyLuneCenter,
                                    final Point2D thirdOnlyLuneCenter,
                                    final Point2D fourthOnlyLuneCenter,
                                    final Point2D firstSecondLuneCenter,
                                    final Point2D firstThirdLuneCenter,
                                    final Point2D secondThirdLuneCenter,
                                    final Point2D firstFourthLuneCenter,
                                    final Point2D secondFourthLuneCenter,
                                    final Point2D thirdFourthLuneCenter,
                                    final Point2D firstSecondThirdLuneCenter,
                                    final Point2D firstSecondFourthLuneCenter,
                                    final Point2D firstThirdFourthLuneCenter,
                                    final Point2D secondThirdFourthLuneCenter,
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
        if (thirdShape == null)
        {
            throw new IllegalArgumentException("thirdShape must not be null");
        }
        if (fourthShape == null)
        {
            throw new IllegalArgumentException("fourthShape must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        this.firstShape = firstShape;
        this.secondShape = secondShape;
        this.thirdShape = thirdShape;
        this.fourthShape = fourthShape;
        this.firstOnlyLuneCenter = firstOnlyLuneCenter;
        this.secondOnlyLuneCenter = secondOnlyLuneCenter;
        this.thirdOnlyLuneCenter = thirdOnlyLuneCenter;
        this.fourthOnlyLuneCenter = fourthOnlyLuneCenter;
        this.firstSecondLuneCenter = firstSecondLuneCenter;
        this.firstThirdLuneCenter = firstThirdLuneCenter;
        this.secondThirdLuneCenter = secondThirdLuneCenter;
        this.firstFourthLuneCenter = firstFourthLuneCenter;
        this.secondFourthLuneCenter = secondFourthLuneCenter;
        this.thirdFourthLuneCenter = thirdFourthLuneCenter;
        this.firstSecondThirdLuneCenter = firstSecondThirdLuneCenter;
        this.firstSecondFourthLuneCenter = firstSecondFourthLuneCenter;
        this.firstThirdFourthLuneCenter = firstThirdFourthLuneCenter;
        this.secondThirdFourthLuneCenter = secondThirdFourthLuneCenter;
        this.intersectionLuneCenter = intersectionLuneCenter;
        this.boundingRectangle = boundingRectangle;

        luneCenters = new HashMap<ImmutableBitSet, Point2D>(15);

        luneCenters.put(toImmutableBitSet(0), this.firstOnlyLuneCenter);
        luneCenters.put(toImmutableBitSet(1), this.secondOnlyLuneCenter);
        luneCenters.put(toImmutableBitSet(2), this.thirdOnlyLuneCenter);
        luneCenters.put(toImmutableBitSet(3), this.fourthOnlyLuneCenter);

        luneCenters.put(toImmutableBitSet(0, 1), this.firstSecondLuneCenter);
        luneCenters.put(toImmutableBitSet(0, 2), this.firstThirdLuneCenter);
        luneCenters.put(toImmutableBitSet(0, 3), this.firstFourthLuneCenter);
        luneCenters.put(toImmutableBitSet(1, 2), this.secondThirdLuneCenter);
        luneCenters.put(toImmutableBitSet(1, 3), this.secondFourthLuneCenter);
        luneCenters.put(toImmutableBitSet(2, 3), this.thirdFourthLuneCenter);

        luneCenters.put(toImmutableBitSet(0, 1, 2), this.firstSecondThirdLuneCenter);
        luneCenters.put(toImmutableBitSet(0, 1, 3), this.firstSecondFourthLuneCenter);
        luneCenters.put(toImmutableBitSet(0, 2, 3), this.firstThirdFourthLuneCenter);
        luneCenters.put(toImmutableBitSet(1, 2, 3), this.secondThirdFourthLuneCenter);

        luneCenters.put(toImmutableBitSet(0, 1, 2, 3), this.intersectionLuneCenter);
    }


    /** {@inheritDoc} */
    public Shape firstShape()
    {
        return firstShape;
    }

    /** {@inheritDoc} */
    public Shape secondShape()
    {
        return secondShape;
    }

    /** {@inheritDoc} */
    public Shape thirdShape()
    {
        return thirdShape;
    }

    /** {@inheritDoc} */
    public Shape fourthShape()
    {
        return fourthShape;
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
    public Point2D thirdOnlyLuneCenter()
    {
        return thirdOnlyLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D fourthOnlyLuneCenter()
    {
        return fourthOnlyLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D firstSecondLuneCenter()
    {
        return firstSecondLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D firstThirdLuneCenter()
    {
        return firstThirdLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D secondThirdLuneCenter()
    {
        return secondThirdLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D firstFourthLuneCenter()
    {
        return firstFourthLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D secondFourthLuneCenter()
    {
        return secondFourthLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D thirdFourthLuneCenter()
    {
        return thirdFourthLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D firstSecondThirdLuneCenter()
    {
        return firstSecondThirdLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D firstSecondFourthLuneCenter()
    {
        return firstSecondFourthLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D firstThirdFourthLuneCenter()
    {
        return firstThirdFourthLuneCenter;
    }

    /** {@inheritDoc} */
    public Point2D secondThirdFourthLuneCenter()
    {
        return secondThirdFourthLuneCenter;
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
        case 2:
            return thirdShape;
        case 3:
            return fourthShape;
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