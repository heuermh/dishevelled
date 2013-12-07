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
package org.dishevelled.piccolo.tilemap;

import org.dishevelled.matrix.impl.SparseMatrix2D;

import org.dishevelled.piccolo.sprite.Sprite;

/**
 * Sparse tile map.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SparseTileMap
    extends AbstractTileMap
{

    /**
     * Create a new sparse tile map node.
     *
     * @param mapWidth map width in number of tiles, must be at least one
     * @param mapHeight map height in number of tiles, must be at least one
     * @param tileWidth tile width, must be greater than or equal to zero
     * @param tileHeight tile height, must be greater than or equal to zero
     */
    public SparseTileMap(final long mapWidth,
            final long mapHeight,
            final double tileWidth,
            final double tileHeight)
    {
        super(mapWidth, mapHeight, tileWidth, tileHeight, new SparseMatrix2D<Sprite>(mapHeight, mapWidth));
    }

    /**
     * Create a new sparse tile map node.
     *
     * @param mapWidth map width in number of tiles, must be at least one
     * @param mapHeight map height in number of tiles, must be at least one
     * @param tileWidth tile width, must be greater than or equal to zero
     * @param tileHeight tile height, must be greater than or equal to zero
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @param loadFactor load factor, must be <code>&gt; 0</code>
     */
    public SparseTileMap(final long mapWidth,
            final long mapHeight,
            final double tileWidth,
            final double tileHeight,
            final int initialCapacity,
            final float loadFactor)
    {
        super(mapWidth, mapHeight, tileWidth, tileHeight, new SparseMatrix2D<Sprite>(mapHeight, mapWidth,
                initialCapacity, loadFactor));
    }
}
