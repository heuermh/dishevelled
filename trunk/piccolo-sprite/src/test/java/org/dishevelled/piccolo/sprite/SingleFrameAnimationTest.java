/*

    dsh-piccolo-sprite  Piccolo2D sprite nodes and supporting classes.
    Copyright (c) 2006-2009 held jointly by the individual authors.

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

import java.awt.Image;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

/**
 * Unit test for SingleFrameAnimation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SingleFrameAnimationTest
    extends TestCase
{

    public void testSingleFrameAnimation()
    {
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Animation animation0 = new SingleFrameAnimation(image);

        assertNotNull("animation0 not null", animation0);
        assertNotNull("animation0 currentFrame not null", animation0.getCurrentFrame());
        assertEquals("animation0 currentFrame equals image", image, animation0.getCurrentFrame());

        animation0.advance();
        assertEquals("animation0 currentFrame equals image after advance", image, animation0.getCurrentFrame());

        animation0.advance();
        animation0.advance();
        animation0.advance();
        animation0.advance();
        animation0.advance();
        assertEquals("animation0 currentFrame equals image after several calls to advance",
                     image, animation0.getCurrentFrame());

        try
        {
            Animation animation = new SingleFrameAnimation(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
