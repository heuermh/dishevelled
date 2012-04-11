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

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.venn.VennLayout;

import static org.dishevelled.venn.layout.VennLayoutUtils.toImmutableBitSet;

/**
 * Implementation of a venn diagram layout for an arbitrary number of sets.
 *
 * @author  Michael Heuer
 */
public final class VennLayoutImpl implements VennLayout
{
    /** List of shapes. */
    private final List<Shape> shapes;

    /** Bounding rectangle. */
    private final Rectangle2D boundingRectangle;

    /** Map of lune centers keyed by bit set. */
    private final Map<ImmutableBitSet, Point2D> luneCenters;


    /**
     * Create a new venn layout with the specified list of shapes and bounding rectangle.
     *
     * @param shapes list of shapes, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     */
    public VennLayoutImpl(final List<? extends Shape> shapes,
                          final Rectangle2D boundingRectangle)
    {
        if (shapes == null)
        {
            throw new IllegalArgumentException("shapes must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        this.shapes = ImmutableList.copyOf(shapes);
        this.boundingRectangle = boundingRectangle;

        luneCenters = Maps.newHashMapWithExpectedSize((int) Math.pow(2, this.shapes.size()) - 1);
    }


    /** {@inheritDoc} */
    public int size()
    {
        return shapes.size();
    }

    /** {@inheritDoc} */
    public Shape get(final int index)
    {
        return shapes.get(index);
    }

    /** {@inheritDoc} */
    public Point2D luneCenter(final int index, final int... additional)
    {
        checkIndices(index, additional);
        return luneCenters.get(toImmutableBitSet(index, additional));
    }

    /** {@inheritDoc} */
    public Rectangle2D boundingRectangle()
    {
        return boundingRectangle;
    }

    /**
     * Add the specified lune center.
     *
     * @param luneCenter lune center to add, must not be null
     * @param index index
     * @param additional variable number of additional indices
     */
    // set, put?
    public void addLuneCenter(final Point2D luneCenter, final int index, final int... additional)
    {
        if (luneCenter == null)
        {
            throw new IllegalArgumentException("luneCenter must not be null");
        }
        checkIndices(index, additional);
        luneCenters.put(toImmutableBitSet(index, additional), luneCenter);
    }

    /**
     * Check the specified indices are valid.
     *
     * @param index index
     * @param additional variable number of additional indices
     */
    private void checkIndices(final int index, final int... additional)
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
    }
}