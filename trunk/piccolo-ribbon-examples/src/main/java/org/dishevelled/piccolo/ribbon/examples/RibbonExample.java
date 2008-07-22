/*

    dsh-piccolo-ribbon-examples  Piccolo ribbon node examples.
    Copyright (c) 2006-2008 held jointly by the individual authors.

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
package org.dishevelled.piccolo.ribbon.examples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.swing.Timer;

import javax.imageio.ImageIO;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PRoot;

import edu.umd.cs.piccolo.activities.PActivity;

import edu.umd.cs.piccolox.PFrame;

import org.dishevelled.piccolo.ribbon.VerticalRibbon;
import org.dishevelled.piccolo.ribbon.HorizontalRibbon;

/**
 * Ribbon example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RibbonExample
    extends PFrame
{
    /** Horizontal ribbon. */
    private HorizontalRibbon horizontalRibbon;

    /** Vertical ribbon. */
    private VerticalRibbon verticalRibbon;


    /** {@inheritDoc} */
    public void initialize()
    {
        PCanvas canvas = getCanvas();
        PLayer layer = canvas.getLayer();

        BufferedImage horizontalImage = loadImage("horizontal");
        horizontalRibbon = new HorizontalRibbon(horizontalImage, 0.0d, 4.0d);
        BufferedImage verticalImage = loadImage("vertical");
        verticalRibbon = new VerticalRibbon(verticalImage, 0.0d, 2.0d);

        layer.addChild(horizontalRibbon);
        layer.addChild(verticalRibbon);

        Timer move0 = new Timer(12000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    horizontalRibbon.moveRight();
                    verticalRibbon.moveDown();
                }
            });

        Timer move1 = new Timer(12000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    horizontalRibbon.moveLeft();
                    verticalRibbon.moveUp();
                }
            });

        move0.setRepeats(true);
        move0.start();

        move1.setRepeats(true);
        move1.setInitialDelay(6000);
        move1.start();

        // schedule one using Timer...
        Timer timer = new Timer((int) (1000 / 12), new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    horizontalRibbon.advance();
                }
            });

        timer.setRepeats(true);
        timer.start();

        // and one using PActivity.
        PRoot root = layer.getRoot();
        PActivity activity = new PActivity(-1, (1000L / 12L))
            {
                /** {@inheritDoc} */
                protected void activityStep(final long elapsedTime)
                {
                    verticalRibbon.advance();
                }
            };

        root.addActivity(activity);
    }

    /**
     * Load the image with the specified name.
     *
     * @param name name
     * @return image
     */
    private BufferedImage loadImage(final String name)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(getClass().getResource(name + ".png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        new RibbonExample();
    }
}