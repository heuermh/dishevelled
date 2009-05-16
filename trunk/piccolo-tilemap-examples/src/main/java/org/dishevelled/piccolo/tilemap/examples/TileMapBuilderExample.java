/*

    dsh-piccolo-tilemap-examples  Piccolo2D tile map node examples.
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
package org.dishevelled.piccolo.tilemap.examples;

import java.awt.BorderLayout;
import java.awt.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;

import edu.umd.cs.piccolo.util.PPaintContext;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

import org.dishevelled.piccolo.sprite.Animation;
import org.dishevelled.piccolo.sprite.SingleFrameAnimation;
import org.dishevelled.piccolo.sprite.Sprite;

import org.dishevelled.piccolo.tilemap.TileMap;

/**
 * Tile map builder example.
 */
public class TileMapBuilderExample
    extends JPanel
    implements Runnable
{
    /** Tile map description. */
    private final File description;


    /**
     * Create a new tile map builder.example.
     *
     * @param description tile map description, must not be null and must exist
     */
    public TileMapBuilderExample(final File description)
    {
        super();

        if (description == null)
        {
            throw new IllegalArgumentException("description must not be null");
        }
        if (!description.exists())
        {
            throw new IllegalArgumentException("description must exist");
        }
        this.description = description;

        BufferedReader reader = null;
        TileMap tileMap = new TileMap(200, 200, 16.0d, 16.0d);
        Map<String, Sprite> tiles = new HashMap<String, Sprite>();
        try
        {
            reader = new BufferedReader(new FileReader(description));
            while (reader.ready())
            {
                String line = reader.readLine();
                String[] split = line.split("\t");
                long column = Long.parseLong(split[0]);
                long row = Long.parseLong(split[1]);
                String tileName = split[2];
                if (!tiles.containsKey(tileName))
                {
                    tiles.put(tileName, createSprite(tileName));
                }
                tileMap.setTile(row, column, tiles.get(tileName));
            }

            PCanvas canvas = new PCanvas();
            canvas.setDefaultRenderQuality(PPaintContext.LOW_QUALITY_RENDERING);
            canvas.setAnimatingRenderQuality(PPaintContext.LOW_QUALITY_RENDERING);
            canvas.setInteractingRenderQuality(PPaintContext.LOW_QUALITY_RENDERING);

            PLayer layer = canvas.getLayer();
            layer.addChild(tileMap);

            Image image = loadImage("link-24x25");
            Animation animation = new SingleFrameAnimation(image);
            Sprite link = new Sprite(animation, Collections.singleton(animation));
            link.setBounds(1300.0d, 900.0d, 24.0d, 25.0d);
            layer.addChild(link);

            setLayout(new BorderLayout());
            add("Center", canvas);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    // ignore
                }
            }
        }
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("TileMap Builder Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 400, 400);
        f.setVisible(true);
    }

    /**
     * Create and return a new single-framed sprite from an image with the specified name.
     *
     * @param name name
     * @return a new single-framed sprite from an image with the specified name
     */
    private Sprite createSprite(final String name)
    {
        Image image = loadImage(name);
        Animation animation = new SingleFrameAnimation(image);
        Sprite sprite = new Sprite(animation, Collections.singleton(animation));
        sprite.setBounds(0.0d, 0.0d, 16.0d, 16.0d);
        return sprite;
    }

    /**
     * Load a PNG image from the classpath with the specified name.
     *
     * @param name name
     * @return a PNG image from the classpath with the specified name
     */
    private static Image loadImage(final String name)
    {
        Image image = null;
        try
        {
            image = ImageIO.read(TileMapBuilderExample.class.getResource(name + ".png"));
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("could not find resource " + name + ".png on classpath");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.err.println("could not find resource " + name + ".png on classpath");
            e.printStackTrace();
        }
        return image;
    }


    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            FileArgument description = new FileArgument("d", "description", "tile map description", true);
            arguments = new ArgumentList(description);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);
            new TileMapBuilderExample(description.getValue()).run();
        }
        catch (CommandLineParseException e)
        {
            Usage.usage("java TileMapBuilderExample [args]", e, commandLine, arguments, System.err);
        }
        catch (IllegalArgumentException e)
        {
            Usage.usage("java TileMapBuilderExample [args]", e, commandLine, arguments, System.err);
        }
    }
}
