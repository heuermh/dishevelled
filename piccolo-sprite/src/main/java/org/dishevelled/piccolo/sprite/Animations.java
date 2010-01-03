/*

    dsh-piccolo-sprite  Piccolo2D sprite nodes and supporting classes.
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
package org.dishevelled.piccolo.sprite;

import java.awt.Image;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Static utility methods for creating animations.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Animations
{

    /**
     * Private no-arg constructor.
     */
    private Animations()
    {
        // empty
    }


    /**
     * Create and return a new single frame animation from the specified image file.
     *
     * @param image image file
     * @return a new single frame animation from the specified image
     * @throws IOException if an IO error occurs
     */
    public static SingleFrameAnimation createAnimation(final File image)
        throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(image);
        return createAnimation(bufferedImage);
    }

    /**
     * Create and return a new single frame animation from the specified image URL.
     *
     * @param image image URL
     * @return a new single frame animation from the specified image
     * @throws IOException if an IO error occurs
     */
    public static SingleFrameAnimation createAnimation(final URL image)
        throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(image);
        return createAnimation(bufferedImage);
    }

    /**
     * Create and return a new single frame animation from the specified image.
     *
     * @param image image
     * @return a new single frame animation from the specified image
     */
    public static SingleFrameAnimation createAnimation(final Image image)
    {
        return new SingleFrameAnimation(image);
    }

    /**
     * Create and return an unmodifiable list of images containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image file
     * @param suffix image suffix
     * @param frames number of frames
     * @return a new unmodifiable list of images containing all the frame images
     *    specified from <code>baseImage</code>
     * @throws IOException if an IO error occurs
     */
    public static List<Image> createFrameList(final File baseImage, final String suffix, final int frames)
        throws IOException
    {
        int leadingZeros = (int) (frames / 10) + 1; // is this math correct?
        String format = "%s%0" + leadingZeros + "d%s";
        List<Image> images = new ArrayList<Image>(frames);
        for (int frame = 0; frame < frames; frame++)
        {
            File file = new File(String.format(format, new Object[] { baseImage.getPath(), frame, suffix }));
            Image image = ImageIO.read(file);
            images.add(image);
        }
        return Collections.unmodifiableList(images);
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image file
     * @param suffix image suffix
     * @param frames number of frames
     * @return a new looped frames animation containing all the frame images
     *    specified from <code>baseImage</code>
     * @throws IOException if an IO error occurs
     */
    public static LoopedFramesAnimation createAnimation(final File baseImage, final String suffix, final int frames)
        throws IOException
    {
        return new LoopedFramesAnimation(createFrameList(baseImage, suffix, frames));
    }

    /**
     * Create and return an unmodifiable list of images containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image URL
     * @param suffix image suffix
     * @param frames number of frames
     * @return a new unmodifiable list of images containing all the frame images
     *    specified from <code>baseImage</code>
     * @throws IOException if an IO error occurs
     */
    public static List<Image> createFrameList(final URL baseImage, final String suffix, final int frames)
        throws IOException
    {
        int leadingZeros = (int) (frames / 10) + 1; // is this math correct?
        String format = "%s%0" + leadingZeros + "d%s";
        List<Image> images = new ArrayList<Image>(frames);
        for (int frame = 0; frame < frames; frame++)
        {
            URL url = new URL(String.format(format, new Object[] { baseImage.getPath(), frame, suffix }));
            Image image = ImageIO.read(url);
            images.add(image);
        }
        return Collections.unmodifiableList(images);
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * specified from <code>baseImage</code>.
     *
     * @param baseImage base image URL
     * @param suffix image suffix
     * @param frames number of frames
     * @return a new looped frames animation containing all the frame images
     *    specified from <code>baseImage</code>
     * @throws IOException if an IO error occurs
     */
    public static LoopedFramesAnimation createAnimation(final URL baseImage, final String suffix, final int frames)
        throws IOException
    {
        return new LoopedFramesAnimation(createFrameList(baseImage, suffix, frames));
    }

    /**
     * Create and return an unmodifiable list of frame images containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet file
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return an unmodifiable list of frame images containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public static List<Image> createFrameList(final File spriteSheet, final int x, final int y,
                                              final int width, final int height, final int frames)
        throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(spriteSheet);
        return createFrameList(bufferedImage, x, y, width, height, frames);
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet file
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new looped frames animation containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     * @throws IOException if an IO error occurs
     */
    public static LoopedFramesAnimation createAnimation(final File spriteSheet, final int x, final int y,
                                                        final int width, final int height, final int frames)
        throws IOException
    {
        return new LoopedFramesAnimation(createFrameList(spriteSheet, x, y, width, height, frames));
    }

    /**
     * Create and return an unmodifiable list of frame images containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet file
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return an unmodifiable list of frame images containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public static List<Image> createFrameList(final URL spriteSheet, final int x, final int y,
                                              final int width, final int height, final int frames)
        throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(spriteSheet);
        return createFrameList(bufferedImage, x, y, width, height, frames);
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet URL
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new looped frames animation containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     * @throws IOException if an IO error occurs
     */
    public static LoopedFramesAnimation createAnimation(final URL spriteSheet, final int x, final int y,
                                                        final int width, final int height, final int frames)
        throws IOException
    {
        return new LoopedFramesAnimation(createFrameList(spriteSheet, x, y, width, height, frames));
    }

    /**
     * Create and return an unmodifiable list of frame images containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return an unmodifiable list of frame images containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public static List<Image> createFrameList(final BufferedImage spriteSheet, final int x, final int y,
                                              final int width, final int height, final int frames)
    {
        if (spriteSheet == null)
        {
            throw new IllegalArgumentException("spriteSheet must not be null");
        }
        List<Image> images = new ArrayList<Image>(frames);
        for (int frame = 0; frame < frames; frame++)
        {
            Image subimage = spriteSheet.getSubimage(x + (frame * width), y, width, height);
            images.add(subimage);
        }
        return Collections.unmodifiableList(images);
    }

    /**
     * Create and return a new looped frames animation containing all the frame images
     * from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     * and read horizontally the specified number of frames.
     *
     * @param spriteSheet sprite sheet image
     * @param x starting location x
     * @param y starting location y
     * @param width frame width
     * @param height frame height
     * @param frames number of frames
     * @return a new looped frames animation containing all the frame images
     *    from <code>spriteSheet</code> as specified by the starting location <code>(x, y)</code>
     *    and read horizontally the specified number of frames
     */
    public static LoopedFramesAnimation createAnimation(final BufferedImage spriteSheet, final int x, final int y,
                                                        final int width, final int height, final int frames)
    {
        return new LoopedFramesAnimation(createFrameList(spriteSheet, x, y, width, height, frames));
    }

    /**
     * Create and return a new looped frames animation containing the specified frame images.
     *
     * @param images list of frame images, must not be null
     * @return a new looped frames animation containing the specified frame images
     */
    public static LoopedFramesAnimation createAnimation(final List<Image> images)
    {
        return new LoopedFramesAnimation(images);
    }

    // vertical reading from sprite sheets?
}