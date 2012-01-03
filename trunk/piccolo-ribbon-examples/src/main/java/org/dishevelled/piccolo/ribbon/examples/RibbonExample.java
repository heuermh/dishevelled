/*

    dsh-piccolo-ribbon-examples  Piccolo2D ribbon node examples.
    Copyright (c) 2006-2012 held jointly by the individual authors.

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

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import javax.imageio.ImageIO;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PLayer;
import org.piccolo2d.PRoot;

import org.piccolo2d.activities.PActivity;

import org.piccolo2d.util.PPaintContext;

import org.dishevelled.piccolo.ribbon.VerticalRibbon;
import org.dishevelled.piccolo.ribbon.HorizontalRibbon;

/**
 * Ribbon example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RibbonExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new ribbon example.
     */
    public RibbonExample()
    {
        super();

        PCanvas canvas = new PCanvas();
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        BufferedImage horizontalImage = loadImage("horizontal");
        final HorizontalRibbon horizontalRibbon = new HorizontalRibbon(horizontalImage, 0.0d, 4.0d);
        BufferedImage verticalImage = loadImage("vertical");
        final VerticalRibbon verticalRibbon = new VerticalRibbon(verticalImage, 0.0d, 2.0d);

        PLayer layer = canvas.getLayer();
        layer.addChild(horizontalRibbon);
        layer.addChild(verticalRibbon);

        Timer moveRightDown = new Timer(12000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    horizontalRibbon.moveRight();
                    verticalRibbon.moveDown();
                }
            });
        moveRightDown.setRepeats(true);
        moveRightDown.start();

        Timer moveLeftUp = new Timer(12000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    horizontalRibbon.moveLeft();
                    verticalRibbon.moveUp();
                }
            });
        moveLeftUp.setRepeats(true);
        moveLeftUp.setInitialDelay(6000);
        moveLeftUp.start();

        // schedule one refresh using Timer...
        Timer horizontalRefresh = new Timer((int) (1000 / 12), new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    horizontalRibbon.advance();
                }
            });
        horizontalRefresh.setRepeats(true);
        horizontalRefresh.start();

        // and one using PActivity.
        PRoot root = layer.getRoot();
        PActivity verticalRefresh = new PActivity(-1, (1000L / 12L))
            {
                /** {@inheritDoc} */
                protected void activityStep(final long elapsedTime)
                {
                    verticalRibbon.advance();
                }
            };
        root.addActivity(verticalRefresh);

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    /** {@inheritDoc} */
    public void run()
    {
        final JFrame f = new JFrame("Ribbon Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 400, 400);
        f.setVisible(true);
    }

    /**
     * Load the image with the specified name.
     *
     * @param name name
     * @return image
     */
    private static BufferedImage loadImage(final String name)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(RibbonExample.class.getResource(name + ".png"));
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
        SwingUtilities.invokeLater(new RibbonExample());
    }
}