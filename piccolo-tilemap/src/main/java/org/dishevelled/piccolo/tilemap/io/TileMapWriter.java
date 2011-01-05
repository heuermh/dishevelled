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
package org.dishevelled.piccolo.tilemap.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.dishevelled.piccolo.tilemap.AbstractTileMap;

/**
 * Writer for tile maps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface TileMapWriter
{

    /**
     * Append the specified tile map to the specified appendable.
     *
     * @param <T> extends Appendable
     * @param tileMap tile map to append, must not be null
     * @param appendable appendable to append the specified tile map to, must not be null
     * @return the specified appendable with the specified tile map appended
     * @throws IOException if an IO error occurs
     */
    <T extends Appendable> T append(AbstractTileMap tileMap, T appendable) throws IOException;

    /**
     * Write the specified tile map to the specified file.
     *
     * @param tileMap tile map to write, must not be null
     * @param file file to write to, must not be null
     * @throws IOException if an IO error occurs
     */
    void write(AbstractTileMap tileMap, File file) throws IOException;

    /**
     * Write the specified tile map to the specified output stream.
     *
     * @param tileMap tile map to write, must not be null
     * @param outputStream output stream to write to, must not be null
     * @throws IOException if an IO error occurs
     */
    void write(AbstractTileMap tileMap, OutputStream outputStream) throws IOException;
}
