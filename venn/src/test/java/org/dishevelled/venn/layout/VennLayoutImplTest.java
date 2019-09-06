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

import java.util.List;

import com.google.common.collect.ImmutableList;

import junit.framework.TestCase;

/**
 * Unit test for VennLayoutImpl.
 *
 * @author  Michael Heuer
 */
public final class VennLayoutImplTest
    extends TestCase
{
    private Shape shape = new Rectangle2D.Double(0.0d, 0.0d, 100.0d, 100.0d);
    private Point2D center = new Point2D.Double(50.0d, 50.0);
    private Rectangle2D boundingRectangle = new Rectangle2D.Double(-10.0d, -10.0d, 110.0d, 110.0d);
    private VennLayoutImpl vennLayout;

    public void setUp()
    {
        List<Shape> shapes = ImmutableList.of(shape, shape, shape, shape, shape);
        vennLayout = new VennLayoutImpl(shapes, boundingRectangle);
        vennLayout.addLuneCenter(center, 0);
        vennLayout.addLuneCenter(center, 1);
        vennLayout.addLuneCenter(center, 2);
        vennLayout.addLuneCenter(center, 3);
        vennLayout.addLuneCenter(center, 4);
        vennLayout.addLuneCenter(center, 0, 1);
        vennLayout.addLuneCenter(center, 0, 2);
        vennLayout.addLuneCenter(center, 0, 3);
        vennLayout.addLuneCenter(center, 0, 4);
        vennLayout.addLuneCenter(center, 0, 1, 2, 3, 4);
    }

    public void testConstructorNullShapes()
    {
        try
        {
            new VennLayoutImpl(null, boundingRectangle);
            fail("ctr(null, ) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testConstructorNullBoundingRectangle()
    {
        try
        {
            new VennLayoutImpl(ImmutableList.of(shape, shape), null);
            fail("ctr(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSize()
    {
        assertEquals(5, vennLayout.size());
    }

    public void testBoundingRectangle()
    {
        assertEquals(boundingRectangle, vennLayout.boundingRectangle());
    }

    public void testLuneCenter()
    {
        assertEquals(center, vennLayout.luneCenter(0));
        assertEquals(center, vennLayout.luneCenter(1));
        assertEquals(center, vennLayout.luneCenter(2));
        assertEquals(center, vennLayout.luneCenter(3));
        assertEquals(center, vennLayout.luneCenter(4));
        assertEquals(center, vennLayout.luneCenter(0, 1));
        assertEquals(center, vennLayout.luneCenter(0, 1));
        assertEquals(center, vennLayout.luneCenter(0, 1));
        assertEquals(center, vennLayout.luneCenter(0, 1));
        assertEquals(center, vennLayout.luneCenter(0, 1));
        assertEquals(center, vennLayout.luneCenter(0, 1, 2, 3, 4));
        assertNull(vennLayout.luneCenter(0, 1, 2));
    }

    public void testAddLuneCenterNullLuneCenter()
    {
        try
        {
            vennLayout.addLuneCenter(null, 0);
            fail("addLuneCenter(null, ) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testGetIndexTooSmall()
    {
        try
        {
            vennLayout.get(-1);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // ignore
        }
    }

    public void testGetIndexTooLarge()
    {
        try
        {
            vennLayout.get(99);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // ignore
        }
    }

    public void testLuneCenterIndexToSmall()
    {
        try
        {
            vennLayout.luneCenter(-1);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // ignore
        }
    }

    public void testLuneCenterIndexTooLarge()
    {
        try
        {
            vennLayout.luneCenter(99);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // ignore
        }
    }

    public void testLuneCenterAdditionalTooSmall()
    {
        try
        {
            vennLayout.luneCenter(0, -1);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // ignore
        }
    }

    public void testLuneCenterAdditionalTooLarge()
    {
        try
        {
            vennLayout.luneCenter(0, 99);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // ignore
        }
    }

    public void testLuneCenterTooManyIndices()
    {
        try
        {
            vennLayout.luneCenter(0, 1, 2, 3, 4, 5);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // ignore
        }
    }
}
