/*

    dsh-venn-euler  Lightweight components for venn/euler diagrams.
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
package org.dishevelled.venn.euler;

import static org.dishevelled.venn.VennLayouter.PerformanceHint.*;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.awt.Shape;

import java.awt.geom.Point2D;

import org.dishevelled.venn.VennModel;
import org.dishevelled.venn.VennLayout;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for VennEulerLayout.
 */
public class VennEulerLayoutTest
{
    private VennEulerLayouter layouter; 
    private Rectangle rect = new Rectangle(100, 100);

    @Before 
    public void setup()
    {
        layouter = new VennEulerLayouter();
    }

    @Test
    public void testLayoutNotNull()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        rect, OPTIMIZE_FOR_SPEED);

        assertNotNull(vl);
    }

    @Test
    public void testCirclesNotNull()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        rect, OPTIMIZE_FOR_SPEED);

        Shape s0 = vl.get(0);
        assertNotNull(s0);

        Shape s1 = vl.get(1);
        assertNotNull(s1);
    }

    @Test
    public void testSize()
    {
        VennLayout vl;

        vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }),
                             rect, OPTIMIZE_FOR_SPEED);
        assertEquals(1,vl.size());


        vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                             rect, OPTIMIZE_FOR_SPEED);
        assertEquals(2,vl.size());


        vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }, new int[] { 1, 3 }),
                             rect, OPTIMIZE_FOR_SPEED);
        assertEquals(3,vl.size());
    }

    @Test 
    public void testBoundingRect()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        rect, OPTIMIZE_FOR_SPEED);
        assertEquals(rect,vl.boundingRectangle());
    }

    @Test 
    public void testLuneCenter()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3, 4 }),
                                        rect, OPTIMIZE_FOR_SPEED);

        Point2D p0 = vl.luneCenter(0);
        assertTrue(vl.get(0).contains(p0));

        Point2D p1 = vl.luneCenter(1);
        assertTrue(vl.get(1).contains(p1));

        Point2D p10 = vl.luneCenter(1,0);
        assertTrue(vl.get(1).contains(p10));
        assertTrue(vl.get(0).contains(p10));
    }

    @Test 
    public void testLuneCenter2()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3, 4 }, new int[] { 4, 5 }),
                                         rect, OPTIMIZE_FOR_SPEED);

        Point2D p0 = vl.luneCenter(0);
        assertTrue(vl.get(0).contains(p0));

        Point2D p1 = vl.luneCenter(1);
        assertTrue(vl.get(1).contains(p1));

        Point2D p2 = vl.luneCenter(2);
        assertTrue(vl.get(2).contains(p2));

        Point2D p10 = vl.luneCenter(1,0);
        assertTrue(vl.get(1).contains(p10));
        assertTrue(vl.get(0).contains(p10));

        Point2D p12 = vl.luneCenter(1,2);
        assertTrue(vl.get(1).contains(p12));
        assertTrue(vl.get(2).contains(p12));

        Point2D p02 = vl.luneCenter(0,2);
        assertTrue(vl.get(0).contains(p02));
        assertTrue(vl.get(2).contains(p02));

        Point2D p012 = vl.luneCenter(0,1,2);
        assertTrue(vl.get(0).contains(p012));
        assertTrue(vl.get(1).contains(p012));
        assertTrue(vl.get(2).contains(p012));
    }

    // TODO is this right?  Or should we throw an exception?
    @Test 
    public void testBadCircleIndex()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        rect, OPTIMIZE_FOR_SPEED);
        Shape s5 = vl.get(5);
        assertNull(s5);
    }

    @Test
    public void testNormalCircleScales()
    {
        testScales(100, 100);
    }

    @Test
    public void testTinyCircleScales()
    {
        testScales(1,1);
    }

    private void testScales(final int width, final int height) {
        Rectangle bound = new Rectangle(width, height);
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        bound, OPTIMIZE_FOR_SPEED);

        Rectangle r0 = vl.get(0).getBounds();
        assertTrue(r0.getWidth() <= bound.getWidth());
        assertTrue(r0.getHeight() <= bound.getHeight());

        Rectangle r1 = vl.get(1).getBounds();
        assertTrue(r1.getWidth() <= bound.getWidth());
        assertTrue(r1.getHeight() <= bound.getHeight());
    }

    @Test
    public void testNormalCircleBounds()
    {
        testBounds(100, 100);
    }

    @Test
    public void testTinyCircleBounds()
    {
        testBounds(1, 1);
    }

    private void testBounds(final int width, final int height)
    {
        Rectangle bound = new Rectangle(width, height);
        VennLayout vl = layouter.layout(new FakeVennModel( new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        bound, OPTIMIZE_FOR_SPEED);

        Rectangle r0 = vl.get(0).getBounds();
        assertTrue(bound.contains(r0));

        Rectangle r1 = vl.get(1).getBounds();
        assertTrue(bound.contains(r1));
    }

    @Test
    public void testNormalCircleSizes()
    {
        testNonZeroSize(100, 100);
    }

    @Test
    public void testTinyCircleSizes()
    {
        testNonZeroSize(1, 1);
    }

    private void testNonZeroSize(final int width, final int height)
    {
        Rectangle bound = new Rectangle(width, height);
        VennLayout vl = layouter.layout(new FakeVennModel( new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        bound, OPTIMIZE_FOR_SPEED);

        Rectangle r0 = vl.get(0).getBounds();
        assertTrue(r0.getWidth() > 0.0);
        assertTrue(r0.getHeight() > 0.0);

        Rectangle r1 = vl.get(1).getBounds();
        assertTrue(r1.getWidth() > 0.0);
        assertTrue(r1.getHeight() > 0.0);
    }
}
