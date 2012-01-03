/*

    dsh-piccolo-tilemap-examples  Piccolo2D tile map node examples.
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
package org.dishevelled.piccolo.tilemap.examples;

import java.awt.BorderLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.piccolo2d.PLayer;
import org.piccolo2d.PCanvas;

import org.piccolo2d.util.PPaintContext;

import org.dishevelled.piccolo.sprite.Animation;
import org.dishevelled.piccolo.sprite.LoopedFramesAnimation;
import org.dishevelled.piccolo.sprite.SingleFrameAnimation;
import org.dishevelled.piccolo.sprite.Sprite;

import org.dishevelled.piccolo.tilemap.SparseTileMap;

/**
 * Sparse tile map example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SparseTileMapExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new sparse tile map example.
     */
    public SparseTileMapExample()
    {
        super();

        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        Image f = loadImage("f");
        Animation fAnimation = new SingleFrameAnimation(f);
        Sprite fSprite = new Sprite(fAnimation, Collections.singleton(fAnimation));
        fSprite.setBounds(0.0d, 0.0d, 64.0d, 64.0d);

        Image b = loadImage("b");
        Animation bAnimation = new SingleFrameAnimation(b);
        Sprite bSprite = new Sprite(bAnimation, Collections.singleton(bAnimation));
        bSprite.setBounds(0.0d, 0.0d, 64.0d, 64.0d);

        Image a = loadImage("a");
        Image r = loadImage("r");
        List<Image> bar = Arrays.asList(new Image[] { b, a, r });
        Animation barAnimation = new LoopedFramesAnimation(bar);
        Sprite barSprite = new Sprite(barAnimation, Collections.singleton(barAnimation));
        barSprite.setBounds(0.0d, 0.0d, 64.0d, 64.0d);

        final SparseTileMap tileMap = new SparseTileMap(10, 10, 64.0d, 64.0d);
        tileMap.fillRow(0, fSprite);
        tileMap.fillRow(1, bSprite);
        tileMap.fillRow(2, barSprite);
        tileMap.fillRowColumn(4, 2, 6, 6, barSprite);
        tileMap.setTileRowColumn(9, 0, fSprite);
        tileMap.setTileRowColumn(9, 9, bSprite);
        tileMap.offset(20.0d, 20.d);

        PLayer layer = canvas.getLayer();
        layer.addChild(tileMap);

        Timer timer = new Timer(1000 / 3, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    tileMap.advance();
                }
            });

        timer.setRepeats(true);
        timer.start();

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Sparse Tile Map Example");
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
    private static Image loadImage(final String name)
    {
        Image image = null;
        try
        {
            image = ImageIO.read(SparseTileMapExample.class.getResource(name + ".png"));
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
        SwingUtilities.invokeLater(new SparseTileMapExample());
    }
}
