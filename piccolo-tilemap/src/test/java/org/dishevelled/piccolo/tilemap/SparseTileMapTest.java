/*

    dsh-piccolo-tilemap  Piccolo2D tile map nodes and supporting classes.
    Copyright (c) 2006-2010 held jointly by the individual authors.

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
package org.dishevelled.piccolo.tilemap;

import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.image.BufferedImage;

import java.util.Set;
import java.util.Collections;

import org.piccolo2d.util.PPaintContext;

import junit.framework.TestCase;

import org.dishevelled.piccolo.sprite.Animation;
import org.dishevelled.piccolo.sprite.SingleFrameAnimation;
import org.dishevelled.piccolo.sprite.Sprite;

/**
 * Unit test for SparseTileMap.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SparseTileMapTest
    extends TestCase
{

    public void testConstructor()
    {
        new SparseTileMap(1L, 1L, 0.0d, 0.0d);
        new SparseTileMap(1L, 1L, 1.0d, 0.0d);
        new SparseTileMap(1L, 1L, 0.0d, 1.0d);
        new SparseTileMap(1L, 1L, 1.0d, 1.0d);
        new SparseTileMap(1000L, 1L, 1.0d, 1.0d);
        new SparseTileMap(1L, 1000L, 1.0d, 1.0d);
        new SparseTileMap(1000L, 1000L, 1.0d, 1.0d);

        try
        {
            new SparseTileMap(0L, 1L, 1.0d, 1.0d);
            fail("ctr(0,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new SparseTileMap(-1L, 1L, 1.0d, 1.0d);
            fail("ctr(-1,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new SparseTileMap(1L, 0, 1.0d, 1.0d);
            fail("ctr(,0,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new SparseTileMap(1L, -1L, 1.0d, 1.0d);
            fail("ctr(,-1,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new SparseTileMap(1L, 1L, -1.0d, 1.0d);
            fail("ctr(,,-1.0,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new SparseTileMap(1L, 1L, 1.0d, -1.0d);
            fail("ctr(,,,-1.0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSparseTileMap()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);
        assertNotNull("tileMap not null", tileMap);
        assertEquals(100L, tileMap.getMapWidth());
        assertEquals(200L, tileMap.getMapHeight());
        assertEquals(10.0d, tileMap.getTileWidth());
        assertEquals(20.0d, tileMap.getTileHeight());
        assertEquals(1000.0d, tileMap.getWidth(), 0.1d);
        assertEquals(4000.0d, tileMap.getHeight(), 0.1d);
    }

    public void testFill()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);

        tileMap.fill(null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }

        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        tileMap.fill(sprite);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(sprite, tileMap.getTileRowColumn(row, column));
            }
        }

        tileMap.fill(null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }
    }

    public void testFillRow()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);

        tileMap.fillRow(1L, null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }

        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        tileMap.fillRow(1L, sprite);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals((row == 1L) ? sprite : null, tileMap.getTileRowColumn(row, column));
            }
        }

        tileMap.fillRow(1L, null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }

        try
        {
            tileMap.fillRow(-1L, null);
            fail("fillRow(-1L,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRow(tileMap.getMapHeight() + 1, null);
            fail("fillRow(mapHeight + 1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testFillColumn()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);

        tileMap.fillColumn(1L, null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }

        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        tileMap.fillColumn(1L, sprite);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals((column == 1L) ? sprite : null, tileMap.getTileRowColumn(row, column));
            }
        }

        tileMap.fillColumn(1L, null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }

        try
        {
            tileMap.fillColumn(-1L, null);
            fail("fillColumn(-1L,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillColumn(tileMap.getMapWidth() + 1, null);
            fail("fillColumn(mapWidth + 1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testFillRowColumn()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);

        tileMap.fillRowColumn(2L, 3L, 4L, 5L, null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }

        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        tileMap.fillRowColumn(2L, 3L, 4L, 5L, sprite);

        int tiles = 0;
        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                if ((column >= 3) && (column < 8) && (row >= 2) && (row < 6))
                {
                    assertEquals(sprite, tileMap.getTileRowColumn(row, column));
                    tiles++;
                }
                else
                {
                    assertEquals(null, tileMap.getTileRowColumn(row, column));
                }
            }
        }
        assertEquals(20, tiles);

        tileMap.fillRowColumn(2L, 3L, 4L, 5L, null);

        for (long column = 0L, columns = tileMap.getMapWidth(); column < columns; column++)
        {
            for (long row = 0L, rows = tileMap.getMapHeight(); row < rows; row++)
            {
                assertEquals(null, tileMap.getTileRowColumn(row, column));
            }
        }

        try
        {
            tileMap.fillRowColumn(-1L, 1L, 5L, 5L, null);
            fail("fillRowColumn(-1L,,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRowColumn(tileMap.getMapHeight(), 1L, 5L, 5L, null);
            fail("fillRowColumn(mapHeight,,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRowColumn(tileMap.getMapHeight() + 1, 1L, 5L, 5L, null);
            fail("fillRowColumn(mapHeight + 1,,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRowColumn(1L, -1L, 5L, 5L, null);
            fail("fillRowColumn(,-1L,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRowColumn(1L, tileMap.getMapWidth(), 5L, 5L, null);
            fail("fillRowColumn(,mapWidth,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRowColumn(1L, tileMap.getMapWidth() + 1, 5L, 5L, null);
            fail("fillRowColumn(,mapWidth + 1,,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRowColumn(0L, 0L, tileMap.getMapHeight() + 1, 5L, null);
            fail("fillRowColumn(,,mapHeight + 1,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.fillRowColumn(0L, 0L, 5L, tileMap.getMapWidth() + 1, null);
            fail("fillRowColumn(,,,mapWidth + 1,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testSetTile()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);
        assertEquals(null, tileMap.getTileRowColumn(1L, 2L));
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        tileMap.setTileRowColumn(1L, 2L, sprite);
        assertEquals(sprite, tileMap.getTileRowColumn(1L, 2L));
        tileMap.setTileRowColumn(1L, 2L, null);
        assertEquals(null, tileMap.getTileRowColumn(1L, 2L));

        try
        {
            tileMap.setTileRowColumn(-1L, 1L, null);
            fail("setTile(-1L,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.setTileRowColumn(tileMap.getMapHeight(), 1L, null);
            fail("setTile(mapHeight,,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.setTileRowColumn(1L, -1L, null);
            fail("setTile(,-1L,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
        try
        {
            tileMap.setTileRowColumn(1L, tileMap.getMapWidth(), null);
            fail("setTile(,mapWidth,) expected IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e)
        {
            // expected
        }
    }

    public void testAdvance()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);
        tileMap.advance();

        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        tileMap.fill(sprite);
        tileMap.advance();
    }

    public void testPaint()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);
        BufferedImage context = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = context.createGraphics();
        PPaintContext paintContext = new PPaintContext(graphics);
        //tileMap.paint(paintContext);
    }

    public void testLayoutChildren()
    {
        SparseTileMap tileMap = new SparseTileMap(100L, 200L, 10.0d, 20.0d);
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        tileMap.fill(sprite);

        tileMap.invalidateLayout();
        tileMap.getFullBounds();
    }
}