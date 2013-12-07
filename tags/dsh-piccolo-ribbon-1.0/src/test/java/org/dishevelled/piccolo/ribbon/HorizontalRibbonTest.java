/*

    dsh-piccolo-ribbon  Piccolo2D ribbon nodes and supporting classes.
    Copyright (c) 2006-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.ribbon;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import org.piccolo2d.util.PPaintContext;

import junit.framework.TestCase;

/**
 * Unit test for HorizontalRibbon.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class HorizontalRibbonTest
    extends TestCase
{

    public void testConstructor()
    {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        HorizontalRibbon ribbon0 = new HorizontalRibbon(image);
        assertEquals(HorizontalRibbon.DEFAULT_CURSOR, ribbon0.getCursor());
        assertEquals(HorizontalRibbon.DEFAULT_DISTANCE, ribbon0.getDistance());
        HorizontalRibbon ribbon1 = new HorizontalRibbon(image, 1.0d, 2.0d);
        assertEquals(1.0d, ribbon1.getCursor());
        assertEquals(2.0d, ribbon1.getDistance());
        try
        {
            HorizontalRibbon ribbon = new HorizontalRibbon(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            HorizontalRibbon ribbon = new HorizontalRibbon(null, 1.0d, 1.0d);
            fail("ctr(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testState()
    {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        HorizontalRibbon ribbon = new HorizontalRibbon(image);
        assertTrue(ribbon.isNotMoving());
        assertFalse(ribbon.isMovingLeft());
        assertFalse(ribbon.isMovingRight());
        ribbon.pause();
        assertTrue(ribbon.isNotMoving());
        assertFalse(ribbon.isMovingLeft());
        assertFalse(ribbon.isMovingRight());
        ribbon.moveLeft();
        assertFalse(ribbon.isNotMoving());
        assertTrue(ribbon.isMovingLeft());
        assertFalse(ribbon.isMovingRight());
        ribbon.moveRight();
        assertFalse(ribbon.isNotMoving());
        assertFalse(ribbon.isMovingLeft());
        assertTrue(ribbon.isMovingRight());
        ribbon.pause();
        assertTrue(ribbon.isNotMoving());
        assertFalse(ribbon.isMovingLeft());
        assertFalse(ribbon.isMovingRight());
    }

    public void testDistance()
    {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        HorizontalRibbon ribbon = new HorizontalRibbon(image);
        assertEquals(HorizontalRibbon.DEFAULT_DISTANCE, ribbon.getDistance());
        ribbon.setDistance(1.0d);
        assertEquals(1.0d, ribbon.getDistance());
        ribbon.setDistance(HorizontalRibbon.DEFAULT_DISTANCE);
        assertEquals(HorizontalRibbon.DEFAULT_DISTANCE, ribbon.getDistance());
    }

    public void testBounds()
    {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        HorizontalRibbon ribbon = new HorizontalRibbon(image);
        // can only assert that height of ribbon is same height as image
        assertEquals(100.0d, ribbon.getBounds().getHeight(), 0.1d);
    }

    public void testAdvance()
    {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        HorizontalRibbon ribbon = new HorizontalRibbon(image);

        ribbon.pause();
        double cursorBeforePause = ribbon.getCursor();
        for (int i = 0; i < 100; i++)
        {
            ribbon.advance();
        }
        assertEquals(cursorBeforePause, ribbon.getCursor());

        ribbon.moveLeft();
        double cursorBeforeMoveLeft = ribbon.getCursor();
        for (int i = 0; i < 100; i++)
        {
            ribbon.advance();
        }
        assertTrue(cursorBeforeMoveLeft > ribbon.getCursor());

        ribbon.moveRight();
        double cursorBeforeMoveRight = ribbon.getCursor();
        for (int i = 0; i < 100; i++)
        {
            ribbon.advance();
        }
        assertTrue(cursorBeforeMoveRight < ribbon.getCursor());

        ribbon.pause();
        double cursorBeforeSecondPause = ribbon.getCursor();
        for (int i = 0; i < 100; i++)
        {
            ribbon.advance();
        }
        assertEquals(cursorBeforeSecondPause, ribbon.getCursor());
    }

    public void testPaint() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        HorizontalRibbon ribbon = new HorizontalRibbon(image);
        BufferedImage context = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = context.createGraphics();
        PPaintContext paintContext = new PPaintContext(graphics);
        ribbon.paint(paintContext);
    }
}