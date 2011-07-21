/*

    dsh-piccolo-tilemap-examples  Piccolo2D tile map node examples.
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
package org.dishevelled.piccolo.tilemap.examples;

import java.awt.BorderLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.InputStream;
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
import org.dishevelled.piccolo.sprite.Animations;
import org.dishevelled.piccolo.sprite.LoopedFramesAnimation;
import org.dishevelled.piccolo.sprite.SingleFrameAnimation;
import org.dishevelled.piccolo.sprite.Sprite;

import org.dishevelled.piccolo.tilemap.AbstractTileMap;
import org.dishevelled.piccolo.tilemap.SparseTileMap;

import org.dishevelled.piccolo.tilemap.io.impl.TmxTileMapReader;

/**
 * TMX Map Format tile map example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TmxTileMapExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new TMX Map Format tile map example.
     */
    public TmxTileMapExample()
    {
        super();

        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        try
        {
            InputStream inputStream = getClass().getResourceAsStream("one-tileset-xml.tmx");
            TmxTileMapReader reader = new TmxTileMapReader();
            AbstractTileMap tileMap = reader.read(inputStream);
            PLayer layer = canvas.getLayer();
            layer.addChild(tileMap);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("TMX Map Format Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 400, 400);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new TmxTileMapExample());
    }
}
