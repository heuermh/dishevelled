/*

    dsh-piccolo-sprite  Piccolo2D sprite nodes and supporting classes.
    Copyright (c) 2006-2011 held jointly by the individual authors.

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

import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.image.BufferedImage;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.piccolo2d.util.PPaintContext;

import junit.framework.TestCase;

/**
 * Unit test for Sprite.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SpriteTest
    extends TestCase
{

    public void testConstructor()
    {
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite0 = new Sprite(animation);
        assertNotNull(sprite0);
        Sprite sprite1 = new Sprite(animation, animations);
        assertNotNull(sprite1);
        Sprite sprite2 = new Sprite(animation, animations, 0);
        assertNotNull(sprite2);
        Sprite sprite3 = new Sprite(animation, animations, 100);
        assertNotNull(sprite3);
        Sprite sprite4 = new Sprite(animation, animations, -1);
        assertNotNull(sprite4);

        try
        {
            new Sprite(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new Sprite(null, animations);
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new Sprite(animation, null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Set<Animation> empty = Collections.emptySet();
            new Sprite(animation, empty);
            fail("ctr(,empty) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Animation animation1 = new SingleFrameAnimation(image);
        Animation animation2 = new SingleFrameAnimation(image);
        Set<Animation> animations1 = new HashSet<Animation>();

        animations1.add(animation1);
        animations1.add(animation2);
        Sprite sprite5 = new Sprite(animation1, animations1);
        assertNotNull(sprite5);

        try
        {
            new Sprite(animation, animations1);
            fail("animation not in animations expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSprite()
    {
        Image image0 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Image image1 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        List<Image> images = Arrays.asList(new Image[] { image0, image1 });
        Animation animation = new LoopedFramesAnimation(images);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);

        assertNotNull("sprite not null", sprite);
        assertNotNull("currentAnimation not null", sprite.getCurrentAnimation());
        assertNotNull("animations not null", sprite.getAnimations());
        assertEquals("currentAnimation equals animation", animation, sprite.getCurrentAnimation());
        assertEquals("animations size == 1", 1, sprite.getAnimations().size());
        assertTrue("animations contains animation", sprite.getAnimations().contains(animation));

        sprite.advance();
        assertEquals("currentAnimation equals animation after advance",
                     animation, sprite.getCurrentAnimation());

        sprite.advance();
        sprite.advance();
        sprite.advance();
        sprite.advance();
        sprite.advance();
        assertEquals("currentAnimation equals animation after several calls to advance",
                     animation, sprite.getCurrentAnimation());

        try
        {
            // attempt to remove the only animation in animations
            sprite.removeAnimation(animation);
            fail("removeAnimation(animation) expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }

        try
        {
            Animation invalidAnimation = new SingleFrameAnimation(image0);
            sprite.setCurrentAnimation(invalidAnimation);
            fail("setCurrentAnimation(invalidAnimation) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Animation animation1 = new SingleFrameAnimation(image0);
        sprite.addAnimation(animation1);
        sprite.setCurrentAnimation(animation1);

        assertEquals("currentAnimation equals animation1", animation1, sprite.getCurrentAnimation());
        assertEquals("animations size == 2", 2, sprite.getAnimations().size());
        assertTrue("animations contains animation", sprite.getAnimations().contains(animation));
        assertTrue("animations contains animation1", sprite.getAnimations().contains(animation1));

        sprite.advance();
        assertEquals("currentAnimation equals animation after advance",
                     animation1, sprite.getCurrentAnimation());

        sprite.advance();
        sprite.advance();
        sprite.advance();
        sprite.advance();
        sprite.advance();
        assertEquals("currentAnimation equals animation1 after several calls to advance",
                     animation1, sprite.getCurrentAnimation());

        try
        {
            // attempt to remove the current animation
            sprite.removeAnimation(animation1);
            fail("removeAnimation(animation1) expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }

        sprite.setCurrentAnimation(animation);
        sprite.removeAnimation(animation1);

        assertEquals("currentAnimation equals animation", animation, sprite.getCurrentAnimation());
        assertEquals("animations size == 1", 1, sprite.getAnimations().size());
        assertTrue("animations contains animation", sprite.getAnimations().contains(animation));

        sprite.advance();
        assertEquals("currentAnimation equals animation after advance",
                     animation, sprite.getCurrentAnimation());

        sprite.advance();
        sprite.advance();
        sprite.advance();
        sprite.advance();
        sprite.advance();
        assertEquals("currentAnimation equals animation after several calls to advance",
                     animation, sprite.getCurrentAnimation());
    }

    public void testSetCurrentAnimation()
    {
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);

        try
        {
            sprite.setCurrentAnimation(null);
            fail("setCurrentAnimation(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testPaint() {
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation = new SingleFrameAnimation(image);
        Set<Animation> animations = Collections.singleton(animation);
        Sprite sprite = new Sprite(animation, animations);
        BufferedImage context = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = context.createGraphics();
        PPaintContext paintContext = new PPaintContext(graphics);
        sprite.paint(paintContext);
    }
}
