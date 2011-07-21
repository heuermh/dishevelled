/*

    dsh-piccolo-tilemap  Piccolo2D tile map nodes and supporting classes.
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

import javax.xml.parsers.SAXParserFactory;

import net.sf.stax.SAX2StAXAdaptor;
import net.sf.stax.StAXContentHandlerBase;
import net.sf.stax.StAXContext;
import net.sf.stax.StAXDelegationContext;

import org.dishevelled.piccolo.sprite.Animations;
import org.dishevelled.piccolo.sprite.Sprite;

import org.dishevelled.piccolo.tilemap.AbstractTileMap;
import org.dishevelled.piccolo.tilemap.SparseTileMap;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;

/**
 * TMX Map Format tile map reader.  See
 * <a href="https://github.com/bjorn/tiled/wiki/TMX-Map-Format">https://github.com/bjorn/tiled/wiki/TMX-Map-Format</a>.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class TmxTileMapReader
    extends AbstractTileMapReader
{
    /** XML reader. */
    private XMLReader xmlReader;


    /**
     * Create a new TMX Map Format tile map reader.
     */
    public TmxTileMapReader()
    {
        try
        {
            xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        }
        catch (Exception e)
        {
            // ignore
        }
    }


    /** {@inheritDoc} */
    public AbstractTileMap read(final InputStream inputStream) throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException ("inputStream must not be null");
        }
        InputSource inputSource = new InputSource(inputStream);
        MapHandler mapHandler = new MapHandler();
        ContentHandler contentHandler = new SAX2StAXAdaptor(mapHandler);
        xmlReader.setContentHandler(contentHandler);
        try
        {
            xmlReader.parse(inputSource);
        }
        catch (SAXException e)
        {
            throw new IOException("could not read input stream", e);
        }
        return mapHandler.getTileMap();
   }

    /**
     * Map handler.
     */
    private static final class MapHandler
        extends StAXContentHandlerBase
    {
        private long width;
        private long height;
        private double tileWidth;
        private double tileHeight;
        private SparseTileMap tileMap;
        private List<Sprite> sprites;
        // only works for files containing a single layer,
        // only works for data elements in Tiled XML format (tile child elements)
        private DataHandler dataHandler = new DataHandler();
        // only works for files containing a single tileset
        private TilesetHandler tilesetHandler = new TilesetHandler();


        /** {@inheritDoc} */
        public void startElement(final String nsURI,
                                 final String localName,
                                 final String qName,
                                 final Attributes attrs,
                                 final StAXDelegationContext dctx)
            throws SAXException
        {
            if ("tileset".equals(qName))
            {
                dctx.delegate(tilesetHandler);
            }
            else if ("data".equals(qName))
            {
                dctx.delegate(dataHandler);
            }
            else if ("map".equals(qName))
            {
                width = Long.parseLong(attrs.getValue("width"));
                height = Long.parseLong(attrs.getValue("height"));
                tileWidth = Double.parseDouble(attrs.getValue("tilewidth"));
                tileHeight = Double.parseDouble(attrs.getValue("tileheight"));
                tileMap = new SparseTileMap(width, height, tileWidth, tileHeight);
            }
        }

        /** {@inheritDoc} */
        public void endElement(final String nsURI,
                               final String localName,
                               final String qName,
                               final Object result,
                               final StAXContext context)
            throws SAXException
        {
            if ("tileset".equals(qName))
            {
                sprites = (List<Sprite>) result;
            }
        }

        /**
         * Return the tile map.
         *
         * @return the tile map
         */
        SparseTileMap getTileMap()
        {
            return tileMap;
        }

        /**
         * Data handler.
         */
        private class DataHandler
            extends StAXContentHandlerBase
        {
            private long i;

            /** {@inheritDoc} */
            public void startTree(final StAXContext ctx) throws SAXException
            {
                i = 0;
            }

            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("tile".equals(qName))
                {
                    int gid = Integer.parseInt(attrs.getValue("gid"));
                    if (gid > 0)
                    {
                        long x = i / width;
                        long y = i % height;
                        Sprite sprite = sprites.get(gid - 1);
                        tileMap.setTileXY(x, y, sprite);
                    }
                    i++;
                }
            }
        }
    }

    /**
     * Tileset handler.
     */
    private static final class TilesetHandler
        extends StAXContentHandlerBase
    {
        private String source;
        private int width;
        private int height;
        private int tileWidth;
        private int tileHeight;

        /** {@inheritDoc} */
        public void startElement(final String nsURI,
                                 final String localName,
                                 final String qName,
                                 final Attributes attrs,
                                 final StAXDelegationContext dctx)
            throws SAXException
        {
            if ("image".equals(qName))
            {
                source = attrs.getValue("source");
                width = Integer.parseInt(attrs.getValue("width"));
                height = Integer.parseInt(attrs.getValue("height"));
            }
            else if ("tileset".equals(qName))
            {
                tileWidth = Integer.parseInt(attrs.getValue("tilewidth"));
                tileHeight = Integer.parseInt(attrs.getValue("tileheight"));
            }
        }

        /** {@inheritDoc} */
        public Object endTree(final StAXContext context)
            throws SAXException
        {
            // only works for horizontal tilesets
            try
            {
                List<Image> tiles = Animations.createFrameList(new File(source), 0, 0, tileWidth, tileHeight, width / tileWidth);
                List<Sprite> tileSprites = new ArrayList<Sprite>(tiles.size());
                for (Image tile : tiles)
                {
                    Sprite tileSprite = new Sprite(Animations.createAnimation(tile));
                    tileSprite.setWidth(tileWidth);
                    tileSprite.setHeight(tileHeight);
                    tileSprites.add(tileSprite);
                }
                return tileSprites;
            }
            catch (IOException e)
            {
                throw new SAXException("could not create tileset from source", e);
            }
        }
    }
}
