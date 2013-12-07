/*

    dsh-piccolo-tilemap  Piccolo2D tile map nodes and supporting classes.
    Copyright (c) 2006-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.tilemap.io.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.dishevelled.piccolo.sprite.Animations;
import org.dishevelled.piccolo.sprite.Sprite;

import org.dishevelled.piccolo.tilemap.AbstractTileMap;
import org.dishevelled.piccolo.tilemap.SparseTileMap;

/**
 * Flixel tile set + CSV file format tile map reader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class FlixelTileMapReader
    extends AbstractTileMapReader
{
    /** Tile width in pixels. */
    private final int tileWidth;

    /** Tile height in pixels. */
    private final int tileHeight;

    /** List of tile images. */
    private final List<Image> tiles;


    /**
     * Create a new flixel tile map reader with the specified tile set image.
     *
     * @param tileSet tile set image, must not be null
     * @param tileWidth tile width in pixels
     */
    public FlixelTileMapReader(final BufferedImage tileSet, final int tileWidth)
    {
        this.tileWidth = tileWidth;
        this.tileHeight = tileSet.getHeight();
        tiles = Animations.createFrameList(tileSet, 0, 0, tileWidth, tileSet.getHeight(), tileSet.getWidth() / tileWidth);
    }

    /**
     * Create a new flixel tile map reader with the specified tile set image input stream.
     *
     * @param tileSet tile set image input stream, must not be null
     * @param tileWidth tile width in pixels
     * @param tileHeight tile height in pixels
     * @param frames number of frames
     */
    public FlixelTileMapReader(final InputStream tileSet, final int tileWidth, final int tileHeight, final int frames)
    {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        try
        {
            tiles = Animations.createFrameList(tileSet, 0, 0, tileWidth, tileHeight, frames);
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not read from " + tileSet);
        }
    }

    /**
     * Create a new flixel tile map reader with the specified tile set image file.
     *
     * @param tileSet tile set image file, must not be null
     * @param tileWidth tile width in pixels
     * @param tileHeight tile height in pixels
     * @param frames number of frames
     */
    public FlixelTileMapReader(final File tileSet, final int tileWidth, final int tileHeight, final int frames)
    {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        try
        {
            tiles = Animations.createFrameList(tileSet, 0, 0, tileWidth, tileHeight, frames);
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not read from " + tileSet);
        }
    }

    /**
     * Create a new flixel tile map reader with the specified tile set image URL.
     *
     * @param tileSet tile set image URL, must not be null
     * @param tileWidth tile width in pixels
     * @param tileHeight tile height in pixels
     * @param frames number of frames
     */
    public FlixelTileMapReader(final URL tileSet, final int tileWidth, final int tileHeight, final int frames)
    {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        try
        {
            tiles = Animations.createFrameList(tileSet, 0, 0, tileWidth, tileHeight, frames);
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not read from " + tileSet);
        }
    }


    @Override
    public AbstractTileMap read(final InputStream inputStream) throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException ("inputStream must not be null");
        }

        // create separate sprites for each tile map
        List<Sprite> tileSprites = new ArrayList<Sprite>(tiles.size());
        for (Image tile : tiles)
        {
            // create new single frame animation for each sprite
            tileSprites.add(new Sprite(Animations.createAnimation(tile)));
        }
        // this means that each copy of a sprite in the same tile map
        //    will be in sync
        // but those in different tile maps will not necessarily be in sync
        // ...though not sure how to specify multiple-frame sprites in this format

        int rows = 0;
        BufferedReader reader = null;
        // too bad about this...
        List<List<Sprite>> tmp = new ArrayList<List<Sprite>>();
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready())
            {
                String line = reader.readLine();
                List<Sprite> row = new ArrayList<Sprite>();
                for (String value : line.split(","))
                {
                    int index = Integer.valueOf(value);
                    if (index >= 0 && index < tileSprites.size())
                    {
                        row.add(tileSprites.get(index));
                    }
                    else
                    {
                        row.add(null);
                    }
                }
                tmp.add(row);
                rows++;
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException("caught NumberFormatException at line number " + rows, e);
        }

        SparseTileMap tileMap = new SparseTileMap(tmp.get(0).size(), tmp.size(), tileWidth, tileHeight);
        for (int row = 0; row < tileHeight; row++)
        {
            for (int column = 0; column < tileWidth; column++)
            {
                Sprite sprite = tmp.get(row).get(column);
                if (sprite != null)
                {
                    tileMap.setTileRowColumn(row, column, sprite);
                }
            }
        }
        return tileMap;
   }
}
