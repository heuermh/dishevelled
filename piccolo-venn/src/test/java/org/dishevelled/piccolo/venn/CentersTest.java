/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
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
package org.dishevelled.piccolo.venn;

import java.awt.Shape;

import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import junit.framework.TestCase;

import static org.dishevelled.piccolo.venn.Centers.*;

/**
 * Unit test for Centers.
 *
 * @author  Michael Heuer
 */
public final class CentersTest
    extends TestCase
{
    /** Default tolerance for double comparisons. */
    private static final double DEFAULT_TOLERANCE = 0.001d;

    /** Tolerance for centroid double comparisons. */
    private static final double CENTROID_TOLERANCE = 1.0d;

    public void testCenterOfShape()
    {
        Shape shape = new Rectangle2D.Double(0.0d, 0.0d, 10.0d, 20.0d);
        assertNotNull(centerOf(shape));
        assertEquals(5.0d, centerOf(shape).getX(), DEFAULT_TOLERANCE);
        assertEquals(10.0d, centerOf(shape).getY(), DEFAULT_TOLERANCE);

        Point2D center = new Point2D.Double();
        assertNotNull(centerOf(shape, center));
        assertSame(center, centerOf(shape, center));
        assertEquals(5.0d, centerOf(shape, center).getX(), DEFAULT_TOLERANCE);
        assertEquals(10.0d, centerOf(shape, center).getY(), DEFAULT_TOLERANCE);

        try
        {
            centerOf((Shape) null);
            fail("centerOf((Shape) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centerOf((Shape) null, center);
            fail("centerOf((Shape) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centerOf(shape, null);
            fail("centerOf(shape, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCenterOfRectangle()
    {
        Rectangle2D rectangle = new Rectangle2D.Double(0.0d, 0.0d, 10.0d, 20.0d);
        assertNotNull(centerOf(rectangle));
        assertEquals(5.0d, centerOf(rectangle).getX(), DEFAULT_TOLERANCE);
        assertEquals(10.0d, centerOf(rectangle).getY(), DEFAULT_TOLERANCE);

        Point2D center = new Point2D.Double();
        assertNotNull(centerOf(rectangle, center));
        assertSame(center, centerOf(rectangle, center));
        assertEquals(5.0d, centerOf(rectangle, center).getX(), DEFAULT_TOLERANCE);
        assertEquals(10.0d, centerOf(rectangle, center).getY(), DEFAULT_TOLERANCE);

        try
        {
            centerOf((Rectangle2D) null);
            fail("centerOf((Rectangle2D) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centerOf((Rectangle2D) null, center);
            fail("centerOf((Rectangle2D) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centerOf(rectangle, null);
            fail("centerOf(rectangle, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCenterOfArea()
    {
        Area area = new Area(new Rectangle2D.Double(0.0d, 0.0d, 10.0d, 20.0d));
        assertNotNull(centerOf(area));
        assertEquals(5.0d, centerOf(area).getX(), DEFAULT_TOLERANCE);
        assertEquals(10.0d, centerOf(area).getY(), DEFAULT_TOLERANCE);

        Point2D center = new Point2D.Double();
        assertNotNull(centerOf(area, center));
        assertSame(center, centerOf(area, center));
        assertEquals(5.0d, centerOf(area, center).getX(), DEFAULT_TOLERANCE);
        assertEquals(10.0d, centerOf(area, center).getY(), DEFAULT_TOLERANCE);

        try
        {
            centerOf((Area) null);
            fail("centerOf((Area) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centerOf((Area) null, center);
            fail("centerOf((Area) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centerOf(area, null);
            fail("centerOf(area, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCentroidOfRect()
    {
        Area area = new Area(new Rectangle2D.Double(0.0d, 0.0d, 10.0d, 20.0d));
        Point2D centroid = centroidOf(area);

        assertNotNull(centroid);
        assertEquals(5.0d, centroid.getX(), CENTROID_TOLERANCE);
        assertEquals(10.0d, centroid.getY(), CENTROID_TOLERANCE);
    }

    public void testCentroidOfRectPoint2D()
    {
        Area area = new Area(new Rectangle2D.Double(0.0d, 0.0d, 10.0d, 20.0d));
        Point2D point = new Point2D.Double();
        Point2D centroid = centroidOf(area, point);

        assertNotNull(centroid);
        assertSame(centroid, point);
        assertEquals(5.0d, centroid.getX(), CENTROID_TOLERANCE);
        assertEquals(10.0d, centroid.getY(), CENTROID_TOLERANCE);
    }

    public void testCentroidOfEllipse()
    {
        Area area = new Area(new Ellipse2D.Double(0.0d, 0.0d, 10.0d, 20.0d));
        Point2D centroid = centroidOf(area);

        assertNotNull(centroid);
        assertEquals(5.0d, centroid.getX(), CENTROID_TOLERANCE);
        assertEquals(10.0d, centroid.getY(), CENTROID_TOLERANCE);
    }

    public void testCentroidOfCAG()
    {
        // copied from QuaternaryVennNode:
        Ellipse2D ellipse = new Ellipse2D.Double(0.0d, 0.0d, 376.0d, 234.0d);
        Point2D center = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());

        Area firstArea = new Area(ellipse);
        firstArea = firstArea.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));

        Area secondArea = new Area(ellipse);
        secondArea = secondArea.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        secondArea = secondArea.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));

        Area thirdArea = new Area(ellipse);
        thirdArea = thirdArea.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        thirdArea = thirdArea.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));

        Area fourthArea = new Area(ellipse);
        fourthArea = fourthArea.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        fourthArea = fourthArea.createTransformedArea(AffineTransform.getTranslateInstance(144.0d, 0.0d));

        fourthArea.subtract(firstArea);
        fourthArea.subtract(secondArea);
        fourthArea.subtract(thirdArea);

        Point2D centroid = centroidOf(fourthArea);

        assertNotNull(centroid);
        //assertEquals(5.0d, centroid.getX(), CENTROID_TOLERANCE);
        //assertEquals(10.0d, centroid.getY(), CENTROID_TOLERANCE);        
    }

    public void testCentroidOfCAGSuppliedRandom()
    {
        // copied from QuaternaryVennNode:
        Ellipse2D ellipse = new Ellipse2D.Double(0.0d, 0.0d, 376.0d, 234.0d);
        Point2D center = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());

        Area firstArea = new Area(ellipse);
        firstArea = firstArea.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));

        Area secondArea = new Area(ellipse);
        secondArea = secondArea.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        secondArea = secondArea.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));

        Area thirdArea = new Area(ellipse);
        thirdArea = thirdArea.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        thirdArea = thirdArea.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));

        Area fourthArea = new Area(ellipse);
        fourthArea = fourthArea.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        fourthArea = fourthArea.createTransformedArea(AffineTransform.getTranslateInstance(144.0d, 0.0d));

        fourthArea.subtract(firstArea);
        fourthArea.subtract(secondArea);
        fourthArea.subtract(thirdArea);

        Point2D centroid = centroidOf(fourthArea, new Point2D.Double(), new Random());

        assertNotNull(centroid);
        //assertEquals(5.0d, centroid.getX(), CENTROID_TOLERANCE);
        //assertEquals(10.0d, centroid.getY(), CENTROID_TOLERANCE);        
    }

    public void testCentroidOfIllegalArguments()
    {
        Area area = new Area(new Rectangle2D.Double(0.0d, 0.0d, 10.0d, 20.0d));
        Point2D centroid = new Point2D.Double();

        try
        {
            centroidOf((Area) null);
            fail("centroidOf((Area) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centroidOf((Area) null, centroid);
            fail("centroidOf((Area) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centroidOf(area, null);
            fail("centroidOf(area, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            centroidOf(area, centroid, null);
            fail("centroidOf(area, centroid, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
