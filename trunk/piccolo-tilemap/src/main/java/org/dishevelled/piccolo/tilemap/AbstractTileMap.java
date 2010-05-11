/*

    dsh-piccolo-tilemap  Piccolo2D tile map nodes and supporting classes.
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
package org.dishevelled.piccolo.tilemap;

import org.piccolo2d.PNode;

import org.piccolo2d.util.PPaintContext;

import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.TernaryProcedure;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.piccolo.sprite.Sprite;

/**
 * Abstract Piccolo2D tile map node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTileMap
    extends PNode
{
    /** Map width, in number of tiles. */
    private final long mapWidth;

    /** Map height, in number of tiles. */
    private final long mapHeight;

    /** Tile width. */
    private final double tileWidth;

    /** Tile height. */
    private final double tileHeight;

    /** Map of tiles. */
    private final Matrix2D<Sprite> tileMap;

    /** Flag for validating proxies. */
    private boolean proxiesInvalid;

    /** Advance procedure. */
    private static final UnaryProcedure<Sprite> ADVANCE_PROCEDURE = new UnaryProcedure<Sprite>()
        {
            /** {@inheritDoc} */
            public void run(final Sprite tile)
            {
                tile.advance();
            }
        };

    /** Create proxy procedure. */
    private final TernaryProcedure<Long, Long, Sprite> createProxyProcedure =
        new TernaryProcedure<Long, Long, Sprite>()
            {
                /** {@inheritDoc} */
                public void run(final Long row, final Long column, final Sprite tile)
                {
                    if (tile != null)
                    {
                        double x = column * getTileWidth();
                        double y = row * getTileHeight();
                        createProxy(x, y, tile);
                    }
                }
            };


    /**
     * Create a new abstract tile map node.
     *
     * @param mapWidth map width in number of tiles, must be at least one
     * @param mapHeight map height in number of tiles, must be at least one
     * @param tileWidth tile width, must be greater than or equal to zero
     * @param tileHeight tile height, must be greater than or equal to zero
     * @param tileMap map of tiles, must not be null and must have dimensions
     *    <code>mapHeight x mapWidth</code>
     */
    protected AbstractTileMap(final long mapWidth,
            final long mapHeight,
            final double tileWidth,
            final double tileHeight,
            final Matrix2D<Sprite> tileMap)
    {
        super();

        if (mapWidth < 1L)
        {
            throw new IllegalArgumentException("mapWidth must be >= 1");
        }
        if (mapHeight < 1L)
        {
            throw new IllegalArgumentException("mapHeight must be >= 1");
        }
        if (tileWidth < 0.0d)
        {
            throw new IllegalArgumentException("tileWidth must be >= 0.0d");
        }
        if (tileHeight < 0.0d)
        {
            throw new IllegalArgumentException("tileHeight must be >= 0.0d");
        }
        if (tileMap == null)
        {
            throw new IllegalArgumentException("tileMap must not be null");
        }
        if (tileMap.rows() != mapHeight)
        {
            throw new IllegalArgumentException("tileMap rows must be equal to mapHeight");
        }
        if (tileMap.columns() != mapWidth)
        {
            throw new IllegalArgumentException("tileMap columns must be equal to mapWidth");
        }
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileMap = tileMap;
        proxiesInvalid = true;

        setBounds(0.0d, 0.0d, mapWidth * tileWidth, mapHeight * tileHeight);
    }


    /**
     * Return the map width in number of tiles for this tile map node.  The map
     * width is also the number of columns in this tile map node.
     *
     * @return the map width for this tile map node
     */
    public final long getMapWidth()
    {
        return mapWidth;
    }

    /**
     * Return the number of columns for this tile map node.
     *
     * @return the number of columns for this tile map node
     */
    public final long columns()
    {
        return getMapWidth();
    }

    /**
     * Return the map height in number of tiles for this tile map node.  The map
     * height is also the number of rows in this tile map node.
     *
     * @return the map height for this tile map node
     */
    public final long getMapHeight()
    {
        return mapHeight;
    }

    /**
     * Return the number of rows for this tile map node.
     *
     * @return the number of rows for this tile map node
     */
    public final long rows()
    {
        return getMapHeight();
    }

    /**
     * Return the tile width for this tile map node.
     *
     * @return the tile width for this tile map node
     */
    public final double getTileWidth()
    {
        return tileWidth;
    }

    /**
     * Return the tile height for this tile map node.
     *
     * @return the tile height for this tile map node
     */
    public final double getTileHeight()
    {
        return tileHeight;
    }

    /**
     * Fill this tile map node with the specified tile.
     *
     * @param tile tile to fill with
     */
    public final void fill(final Sprite tile)
    {
        tileMap.assign(tile);
        invalidateProxies();
    }

    /**
     * Fill the specified row in this tile map node with the specified tile.
     *
     * @param row row in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param tile tile to fill with
     */
    public final void fillY(final long y, final Sprite tile)
    {
        fillRow(y, tile);
    }

    /**
     * Fill the specified row in this tile map node with the specified tile.
     *
     * @param row row in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param tile tile to fill with
     */
    public final void fillRow(final long row, final Sprite tile)
    {
        tileMap.viewRow(row).assign(tile);
        invalidateProxies();
    }

    /**
     * Fill the specified column in this tile map node with the specified tile.
     *
     * @param column column in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param tile tile to fill with
     */
    public final void fillX(final long x, final Sprite tile)
    {
        fillColumn(x, tile);
    }

    /**
     * Fill the specified column in this tile map node with the specified tile.
     *
     * @param column column in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapWidth()</code>
     * @param tile tile to fill with
     */
    public final void fillColumn(final long column, final Sprite tile)
    {
        tileMap.viewColumn(column).assign(tile);
        invalidateProxies();
    }

    /**
     * Fill the specified part of this tile map node with the specified tile.
     *
     * @param x x in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapWidth()</code>
     * @param y y in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param width width in number of tiles
     * @param height height in number of tiles
     * @param tile tile to fill with
     */
    public final void fillXY(final long x, final long y, final long width, final long height, final Sprite tile)
    {
        fillRowColumn(y, x, height, width, tile);
    }

    /**
     * Fill the specified part of this tile map node with the specified tile.
     *
     * @param row row in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param column column in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapWidth()</code>
     * @param height height in number of tiles
     * @param width width in number of tiles
     * @param tile tile to fill with
     */
    public final void fillRowColumn(final long row, final long column, final long height, final long width, final Sprite tile)
    {
        tileMap.viewPart(row, column, height, width).assign(tile);
        invalidateProxies();
    }

    /**
     * Set the tile at the specified x and y in this tile map node to <code>tile</code>.
     *
     * @param x x in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapWidth()</code>
     * @param y y in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param tile tile
     */
    public final void setTileXY(final long x, final long y, final Sprite tile)
    {
        setTileRowColumn(y, x, tile);
    }

    /**
     * Set the tile at the specified row and column in this tile map node to <code>tile</code>.
     *
     * @param row row in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param column column in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapWidth()</code>
     * @param tile tile
     */
    public final void setTileRowColumn(final long row, final long column, final Sprite tile)
    {
        tileMap.set(row, column, tile);
        invalidateProxies();
    }

    /**
     * Return the tile at the specified x and y in this tile map node, if any.
     *
     * @param x x in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapWidth()</code>
     * @param y y in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @return the tile at the specified x and y in this tile map node, or <code>null</code>
     *    if one does not exist
     */
    public final Sprite getTileXY(final long x, final long y)
    {
        return getTileRowColumn(y, x);
    }

    /**
     * Return the tile at the specified row and column in this tile map node, if any.
     *
     * @param row row in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapHeight()</code>
     * @param column column in this tile map node, must be <code>&gt;= 0</code> and <code>&lt; getMapWidth()</code>
     * @return the tile at the specified row and column in this tile map node, or <code>null</code>
     *    if one does not exist
     */
    public final Sprite getTileRowColumn(final long row, final long column)
    {
        return tileMap.get(row, column);
    }

    /**
     * Advance this tile map node one frame.
     */
    public final void advance()
    {
        tileMap.forEachNonNull(ADVANCE_PROCEDURE);
        repaint();
    }

    /**
     * Invalidate proxies.
     */
    private void invalidateProxies()
    {
        proxiesInvalid = true;
    }

    /**
     * Validate proxies, creating and laying them out where necessary.
     */
    private void validateProxies()
    {
        removeAllChildren();
        tileMap.forEach(createProxyProcedure);

        proxiesInvalid = false;
    }

    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        if (proxiesInvalid)
        {
            validateProxies();
        }
    }

    /**
     * Create and add a new proxy with the specified offset and tile.
     *
     * @param x x offset
     * @param y y offset
     * @param tile tile
     */
    private void createProxy(final double x, final double y, final Sprite tile)
    {
        addChild(new Proxy(x, y, tileWidth, tileHeight, tile));
    }

    // todo:  protect addChild and similar


    /**
     * Proxy.
     */
    private static class Proxy
        extends PNode
    {
        /** Tile. */
        private final Sprite tile;


        /**
         * Create a new proxy with the specified offset and tile.
         *
         * @param x x offset
         * @param y y offset
         * @param width width
         * @param height height
         * @param tile tile
         */
        Proxy(final double x, final double y, final double width, final double height, final Sprite tile)
        {
            super();
            offset(x, y);
            setBounds(0.0d, 0.0d, width, height);
            this.tile = tile;
        }


        /** {@inheritDoc} */
        protected void paint(final PPaintContext paintContext)
        {
            tile.paint(paintContext);
        }

        // todo:  might need to override other paint-related methods
    }
}
