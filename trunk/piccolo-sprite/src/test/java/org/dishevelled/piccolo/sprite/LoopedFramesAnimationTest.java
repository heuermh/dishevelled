/*

    dsh-piccolo-sprite  Piccolo sprite nodes and supporting classes.
    Copyright (c) 2006 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.piccolo.sprite;

import java.awt.Image;

import java.awt.image.BufferedImage;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import junit.framework.TestCase;

/**
 * Unit test for LoopedFramesAnimation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class LoopedFramesAnimationTest
    extends TestCase
{

    public void testConstructor()
    {
        Image image0 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Image image1 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Image image2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Image image3 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        List<Image> images = Arrays.asList(new Image[] { image0, image1, image2, image3 });

        Animation animation0 = new LoopedFramesAnimation(images);

        try
        {
            Animation animation = new LoopedFramesAnimation(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            List<Image> empty = Collections.emptyList();
            Animation animation = new LoopedFramesAnimation(empty);
            fail("ctr(empty) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSingleLoopedFrame()
    {
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        List<Image> images = Arrays.asList(new Image[] { image });

        Animation animation = new LoopedFramesAnimation(images);
        assertNotNull("animation not null", animation);
        assertNotNull("animation currentFrame not null", animation.getCurrentFrame());
        assertEquals("animation currentFrame equals image", image, animation.getCurrentFrame());

        animation.advance();
        assertEquals("animation currentFrame equals image after advance", image, animation.getCurrentFrame());

        animation.advance();
        animation.advance();
        animation.advance();
        animation.advance();
        animation.advance();
        assertEquals("animation currentFrame equals image after several calls to advance",
                     image, animation.getCurrentFrame());
    }

    public void testLoopedFrames()
    {
        Image image0 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Image image1 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Image image2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Image image3 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        List<Image> images = Arrays.asList(new Image[] { image0, image1, image2, image3 });

        Animation animation = new LoopedFramesAnimation(images);
        assertNotNull("animation not null", animation);
        assertNotNull("animation currentFrame not null", animation.getCurrentFrame());
        assertEquals("animation currentFrame == image0", image0, animation.getCurrentFrame());

        animation.advance();
        assertEquals(image1, animation.getCurrentFrame());
        animation.advance();
        assertEquals(image2, animation.getCurrentFrame());
        animation.advance();
        assertEquals(image3, animation.getCurrentFrame());
        animation.advance();
        assertEquals(image0, animation.getCurrentFrame());
        animation.advance();
        assertEquals(image1, animation.getCurrentFrame());
        animation.advance();
        assertEquals(image2, animation.getCurrentFrame());
        animation.advance();
        assertEquals(image3, animation.getCurrentFrame());
        animation.advance();
        assertEquals(image0, animation.getCurrentFrame());
    }
}
