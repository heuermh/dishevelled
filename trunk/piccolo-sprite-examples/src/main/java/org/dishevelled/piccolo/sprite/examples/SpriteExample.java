/*

    dsh-piccolo-sprite-examples  Piccolo sprite node examples.
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
package org.dishevelled.piccolo.sprite.examples;

import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.geom.Rectangle2D;

import java.io.IOException;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;

import javax.swing.Timer;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;

import edu.umd.cs.piccolo.nodes.PImage;

import edu.umd.cs.piccolo.util.PPaintContext;

import edu.umd.cs.piccolox.PFrame;

import org.dishevelled.piccolo.sprite.Sprite;
import org.dishevelled.piccolo.sprite.Animation;
import org.dishevelled.piccolo.sprite.LoopedFramesAnimation;

/**
 * Sprite example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SpriteExample
    extends PFrame
{

    /** {@inheritDoc} */
    public void initialize()
    {
        PCanvas canvas = getCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.removeInputEventListener(canvas.getPanEventHandler());
        canvas.removeInputEventListener(canvas.getZoomEventHandler());

        List<Image> frontImages = loadImages("front", 8);
        final Animation front = new LoopedFramesAnimation(frontImages);

        List<Image> backImages = loadImages("back", 8);
        final Animation back = new LoopedFramesAnimation(backImages);

        List<Image> leftImages = loadImages("left", 8);
        final Animation left = new LoopedFramesAnimation(leftImages);

        List<Image> rightImages = loadImages("right", 8);
        final Animation right = new LoopedFramesAnimation(rightImages);

        Set<Animation> animations = new HashSet<Animation>();
        animations.add(front);
        animations.add(back);
        animations.add(left);
        animations.add(right);

        final Sprite sprite = new Sprite(front, animations);
        sprite.setHeight(24.0d);
        sprite.setWidth(17.0d);
        sprite.offset(270.0d, 260.0d);

        Image backgroundImage = loadImage("background");
        PImage background = new PImage(backgroundImage);

        PLayer layer = canvas.getLayer();
        layer.addChild(background);
        layer.addChild(sprite);

        PCamera camera = canvas.getCamera();
        camera.setViewBounds(new Rectangle2D.Double(170.0d, 160.0d, 200.0d, 200.0d));

        Timer turn = new Timer(1000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    Animation currentAnimation = sprite.getCurrentAnimation();
                    if (currentAnimation.equals(right))
                    {
                        sprite.setCurrentAnimation(front);
                    }
                    else if (currentAnimation.equals(front))
                    {
                        sprite.setCurrentAnimation(left);
                    }
                    else if (currentAnimation.equals(left))
                    {
                        sprite.setCurrentAnimation(back);
                    }
                    else if (currentAnimation.equals(back))
                    {
                        sprite.setCurrentAnimation(right);
                    }
                }
            });

        turn.setRepeats(true);
        turn.start();

        Timer timer = new Timer(1000/20, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    sprite.advance();

                    Animation currentAnimation = sprite.getCurrentAnimation();
                    if (currentAnimation.equals(right))
                    {
                        sprite.offset(2.0d, 0.0d);
                    }
                    else if (currentAnimation.equals(front))
                    {
                        sprite.offset(0.0d, 2.0d);
                    }
                    else if (currentAnimation.equals(left))
                    {
                        sprite.offset(-2.0d, 0.0d);
                    }
                    else if (currentAnimation.equals(back))
                    {
                        sprite.offset(0.0d, -2.0d);
                    }
                }
            });

        timer.setRepeats(true);
        timer.start();
    }

    /**
     * Load the image with the specified name.
     *
     * @param name name
     * @return image
     */
    private Image loadImage(final String name)
    {
        Image image = null;
        try
        {
            image = ImageIO.read(getClass().getResource(name + ".png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return image;
        }
    }

    /**
     * Load a series of images with the specified name.
     *
     * @param name name
     * @param count number of images
     * @return list of images
     */
    private List<Image> loadImages(final String name, final int count)
    {
        List<Image> images = new ArrayList<Image>(count);
        try
        {
            for (int i = 0; i < count; i++)
            {
                Image image = ImageIO.read(getClass().getResource(name + i + ".png"));
                images.add(image);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return images;
        }
    }

    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        new SpriteExample();
    }
}
