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

import org.dishevelled.venn.AbstractBinaryVennLayoutTest;
import org.dishevelled.venn.BinaryVennLayout;

/**
 * Unit test for BinaryVennLayoutImpl.
 *
 * @author  Michael Heuer
 */
public final class BinaryVennLayoutImplTest
    extends AbstractBinaryVennLayoutTest
{
    private Shape shape = new Rectangle2D.Double(0.0d, 0.0d, 100.0d, 100.0d);
    private Point2D center = new Point2D.Double(50.0d, 50.0);
    private Rectangle2D boundingRectangle = new Rectangle2D.Double(-10.0d, -10.0d, 110.0d, 110.0d);

    /** {@inheritDoc} */
    protected BinaryVennLayout createBinaryVennLayout()
    {
        return new BinaryVennLayoutImpl(shape, shape, center, center, center, boundingRectangle);
    }

    public void testConstructorNullFirstShape()
    {
        try
        {
            new BinaryVennLayoutImpl(null, shape, center, center, center, boundingRectangle);
            fail("ctr(null,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testConstructorNullSecondShape()
    {
        try
        {
            new BinaryVennLayoutImpl(shape, null, center, center, center, boundingRectangle);
            fail("ctr(,null,,,) expected IllegalArgumentException");
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
            new BinaryVennLayoutImpl(shape, shape, center, center, center, null);
            fail("ctr(,,,,null) expected IllegalArgumentException");
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
            binaryVennLayout.get(-1);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testGetIndexTooLarge()
    {
        try
        {
            binaryVennLayout.get(99);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testLuneCenterIndexToSmall()
    {
        try
        {
            binaryVennLayout.luneCenter(-1);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testLuneCenterIndexTooLarge()
    {
        try
        {
            binaryVennLayout.luneCenter(99);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testLuneCenterAdditionalTooSmall()
    {
        try
        {
            binaryVennLayout.luneCenter(0, -1);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testLuneCenterAdditionalTooLarge()
    {
        try
        {
            binaryVennLayout.luneCenter(0, 99);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testLuneCenterTooManyIndices()
    {
        try
        {
            binaryVennLayout.luneCenter(0, 1, 2);
            fail("expected IllegalArgumentException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }
}