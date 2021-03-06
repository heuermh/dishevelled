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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.dishevelled.piccolo.tilemap.AbstractTileMap;

import org.dishevelled.piccolo.tilemap.io.TileMapReader;

/**
 * Abstract tile map reader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTileMapReader
    implements TileMapReader
{

    @Override
    public final AbstractTileMap read(final File file) throws IOException
    {
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);
            return read(inputStream);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    @Override
    public final AbstractTileMap read(final URL url) throws IOException
    {
        if (url == null)
        {
            throw new IllegalArgumentException("url must not be null");
        }
        InputStream inputStream = null;
        try
        {
            inputStream = url.openStream();
            return read(inputStream);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    /**
     * Close the specified input stream quietly.
     *
     * @param inputStream input stream to close
     */
    protected static final void closeQuietly(final InputStream inputStream)
    {
        try
        {
            inputStream.close();
        }
        catch (Exception e)
        {
            // ignore
        }
    }
}
