/*

    dsh-piccolo-sprite  Piccolo2D sprite nodes and supporting classes.
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
package org.dishevelled.piccolo.sprite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;

import java.awt.geom.GeneralPath;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

import static org.dishevelled.piccolo.sprite.Animations.*;

/**
 * Unit test for Animations.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class AnimationsTest
    extends TestCase
{
    /** File to test. */
    private File file;

    /** URL to test. */
    private URL url;


    @Override
    protected void setUp() throws Exception
    {
        file = File.createTempFile("animationsTest", ".png");
        ImageIO.write(createTestSprite(10, 20), "png", file);
        url = file.toURL();
    }

    @Override
    protected void tearDown() throws Exception
    {
        file.delete();
    }


    /**
     * Create and return a test sprite image for test.
     *
     * @param width width
     * @param height height
     * @return a test sprite image to test
     */
    private BufferedImage createTestSprite(final int width, final int height)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(Color.BLACK);
        graphics.setStroke(new BasicStroke(2.0f));
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        GeneralPath path = new GeneralPath();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(width, width);
        path.lineTo(0.0f, height);
        graphics.draw(path);
        graphics.dispose();
        return image;
    }

    /**
     * Create and return a list of frame images to test.
     *
     * @return a list of frame images to test
     */
    public List<Image> createTestFrameImages()
    {
        List<Image> frameImages = new ArrayList<Image>();
        for (int i = 0; i < 10; i++)
        {
            frameImages.add(createTestSprite(10 + (i % 2), 20 + (i % 2)));
        }
        return frameImages;
    }

    public void testCreateAnimationInputStream() throws Exception
    {
        SingleFrameAnimation animation = createAnimation(new FileInputStream(file));
        assertNotNull(animation);

        try
        {
            createAnimation((InputStream) null);
            fail("createAnimation((InputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationFile() throws Exception
    {
        SingleFrameAnimation animation = createAnimation(file);
        assertNotNull(animation);

        try
        {
            createAnimation((File) null);
            fail("createAnimation((File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationURL() throws Exception
    {
        SingleFrameAnimation animation = createAnimation(url);
        assertNotNull(animation);

        try
        {
            createAnimation((URL) null);
            fail("createAnimation((URL) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationImage()
    {
        SingleFrameAnimation animation = createAnimation(createTestSprite(10, 20));
        assertNotNull(animation);
    }


    // multiple frame animations from base image

    public void testCreateAnimationBaseImageString() throws Exception
    {
        try
        {
            createAnimation((String) null, ".png", 10);
            fail("createAnimation((String) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationBaseImageFile() throws Exception
    {
        try
        {
            createAnimation((File) null, ".png", 10);
            fail("createAnimation((File) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationBaseImageURL() throws Exception
    {
        try
        {
            createAnimation((URL) null, ".png", 10);
            fail("createAnimation((URL) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    // looped frame animations from base image

    public void testCreateLoopedAnimationBaseImageString() throws Exception
    {
        try
        {
            createLoopedAnimation((String) null, ".png", 10);
            fail("createLoopedAnimation((String) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateLoopedAnimationBaseImageFile() throws Exception
    {
        try
        {
            createLoopedAnimation((File) null, ".png", 10);
            fail("createLoopedAnimation((File) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateLoopedAnimationBaseImageURL() throws Exception
    {
        try
        {
            createLoopedAnimation((URL) null, ".png", 10);
            fail("createLoopedAnimation((URL) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    // multiple frame animations from sprite sheet

    public void testCreateAnimationSpriteSheetInputStream() throws Exception
    {
        MultipleFramesAnimation animation = createAnimation(new FileInputStream(file), 0, 0, 1, 20, 10);
        assertNotNull(animation);

        try
        {
            createAnimation((InputStream) null, 0, 0, 10, 20, 50);
            fail("createAnimation((InputStream) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationSpriteSheetFile() throws Exception
    {
        MultipleFramesAnimation animation = createAnimation(file, 0, 0, 1, 20, 10);
        assertNotNull(animation);

        try
        {
            createAnimation((File) null, 0, 0, 10, 20, 50);
            fail("createAnimation((File) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationSpriteSheetURL() throws Exception
    {
        MultipleFramesAnimation animation = createAnimation(url, 0, 0, 1, 20, 10);
        assertNotNull(animation);

        try
        {
            createAnimation((URL) null, 0, 0, 10, 20, 50);
            fail("createAnimation((URL) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAnimationSpriteSheetImage()
    {
        MultipleFramesAnimation animation = createAnimation(createTestSprite(500, 20), 0, 0, 10, 20, 50);
        assertNotNull(animation);
    }


    // looped frame animations from sprite sheet

    public void testCreateLoopedAnimationSpriteSheetInputStream() throws Exception
    {
        LoopedFramesAnimation animation = createLoopedAnimation(new FileInputStream(file), 0, 0, 1, 20, 10);
        assertNotNull(animation);

        try
        {
            createLoopedAnimation((InputStream) null, 0, 0, 10, 20, 50);
            fail("createLoopedAnimation((InputStream) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateLoopedAnimationSpriteSheetFile() throws Exception
    {
        LoopedFramesAnimation animation = createLoopedAnimation(file, 0, 0, 1, 20, 10);
        assertNotNull(animation);

        try
        {
            createLoopedAnimation((File) null, 0, 0, 10, 20, 50);
            fail("createLoopedAnimation((File) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateLoopedAnimationSpriteSheetURL() throws Exception
    {
        LoopedFramesAnimation animation = createLoopedAnimation(url, 0, 0, 1, 20, 10);
        assertNotNull(animation);

        try
        {
            createLoopedAnimation((URL) null, 0, 0, 10, 20, 50);
            fail("createLoopedAnimation((URL) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateLoopedAnimationSpriteSheetImage()
    {
        LoopedFramesAnimation animation = createLoopedAnimation(createTestSprite(500, 20), 0, 0, 10, 20, 50);
        assertNotNull(animation);
    }


    // multiple frame animations from frame list

    public void testCreateAnimationFrameList()
    {
        MultipleFramesAnimation animation = createAnimation(createTestFrameImages());
        assertNotNull(animation);

        try
        {
            createAnimation((List<Image>) null);
            fail("createAnimation((List<Image>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    // looped frame animations from frame list

    public void testCreateLoopedAnimationFrameList()
    {
        LoopedFramesAnimation animation = createLoopedAnimation(createTestFrameImages());
        assertNotNull(animation);

        try
        {
            createLoopedAnimation((List<Image>) null);
            fail("createLoopedAnimation((List<Image>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    // create frame lists

    public void testCreateFrameList()
    {
        try
        {
            createFrameList((BufferedImage) null, 0, 0, 10, 20, 50);
            fail("createFrameList((BufferedImage) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    // sprite sheet utility methods

    public void testCreateSpriteSheet()
    {
        BufferedImage spriteSheet = createSpriteSheet(createTestFrameImages());
        assertNotNull(spriteSheet);

        try
        {
            createSpriteSheet(null);
            fail("createSpriteSheet(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateSpriteSheetString() throws Exception
    {
        try
        {
            createSpriteSheet((String) null, ".png", 10);
            fail("createSpriteSheet((String) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateSpriteSheetFile() throws Exception
    {
        try
        {
            createSpriteSheet((File) null, ".png", 10);
            fail("createSpriteSheet((File) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateSpriteSheetURL() throws Exception
    {
        try
        {
            createSpriteSheet((URL) null, ".png", 10);
            fail("createSpriteSheet((URL) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testFlipHorizontally() throws Exception
    {
        List<Image> flippedHorizontally = flipHorizontally(createTestFrameImages());
        assertNotNull(flippedHorizontally);
        assertEquals(10, flippedHorizontally.size());

        try
        {
            flipHorizontally(null);
            fail("flipHorizontally(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testFlipVertically() throws Exception
    {
        List<Image> flippedVertically = flipVertically(createTestFrameImages());
        assertNotNull(flippedVertically);
        assertEquals(10, flippedVertically.size());

        try
        {
            flipVertically(null);
            fail("flipVertically(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testRotate() throws Exception
    {
        List<Image> rotated = rotate(createTestSprite(10, 20), 50);
        assertNotNull(rotated);
        assertEquals(50, rotated.size());

        try
        {
            rotate(null, 50);
            fail("rotate(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}