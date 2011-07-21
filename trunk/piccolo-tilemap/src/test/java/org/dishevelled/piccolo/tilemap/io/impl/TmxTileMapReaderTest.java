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

import java.net.URL;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.dishevelled.piccolo.tilemap.AbstractTileMap;

/**
 * Unit test for TmxTileMapReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TmxTileMapReaderTest
    extends TestCase
{

    public void testConstructor()
    {
        assertNotNull(new TmxTileMapReader());
    }

    public void testReadNullInputStream() throws Exception
    {
        try
        {
            TmxTileMapReader reader = new TmxTileMapReader();
            reader.read((InputStream) null);
            fail("expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testReadNullURL() throws Exception
    {
        try
        {
            TmxTileMapReader reader = new TmxTileMapReader();
            reader.read((URL) null);
            fail("expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testReadZeroLengthInputStream() throws Exception
    {
        InputStream inputStream = getClass().getResourceAsStream("zero-length.tmx");
        TmxTileMapReader reader = new TmxTileMapReader();
        try
        {
            reader.read(inputStream);
            fail("expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testReadInvalidInputStream() throws Exception
    {
        InputStream inputStream = getClass().getResourceAsStream("invalid.tmx");
        TmxTileMapReader reader = new TmxTileMapReader();
        try
        {
            reader.read(inputStream);
            fail("expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testReadEmpty() throws Exception
    {
        InputStream inputStream = getClass().getResourceAsStream("empty.tmx");
        TmxTileMapReader reader = new TmxTileMapReader();
        AbstractTileMap tileMap = reader.read(inputStream);
        assertNotNull(tileMap);
        assertEquals(100L, tileMap.columns());
        assertEquals(100L, tileMap.rows());
        assertEquals(32.0d, tileMap.getTileWidth(), 0.1d);
        assertEquals(32.0d, tileMap.getTileHeight(), 0.1d);
        for (long x = 0; x < 100L; x++)
        {
            for (long y = 0; y < 100L; y++)
            {
                assertNull(tileMap.getTileXY(x, y));
            }
        }
    }

    public void testReadOneTilesetNoTiles() throws Exception
    {
        InputStream inputStream = getClass().getResourceAsStream("one-tileset-no-tiles.tmx");
        TmxTileMapReader reader = new TmxTileMapReader();
        AbstractTileMap tileMap = reader.read(inputStream);
        assertNotNull(tileMap);
        assertEquals(100L, tileMap.columns());
        assertEquals(100L, tileMap.rows());
        assertEquals(32.0d, tileMap.getTileWidth(), 0.1d);
        assertEquals(32.0d, tileMap.getTileHeight(), 0.1d);
        for (long x = 0; x < 100L; x++)
        {
            for (long y = 0; y < 100L; y++)
            {
                assertNull(tileMap.getTileXY(x, y));
            }
        }
    }

    public void testReadOneTileset() throws Exception
    {
    }

    public void testReadOneTilesetXml() throws Exception
    {
        InputStream inputStream = getClass().getResourceAsStream("one-tileset-xml.tmx");
        TmxTileMapReader reader = new TmxTileMapReader();
        AbstractTileMap tileMap = reader.read(inputStream);
        assertNotNull(tileMap);
        assertEquals(100L, tileMap.columns());
        assertEquals(100L, tileMap.rows());
        assertEquals(32.0d, tileMap.getTileWidth(), 0.1d);
        assertEquals(32.0d, tileMap.getTileHeight(), 0.1d);
        assertNotNull(tileMap.getTileXY(0L, 0L));
        assertFalse(tileMap.getTileXY(0L, 0L).equals(tileMap.getTileXY(0L, 1L)));
        assertFalse(tileMap.getTileXY(0L, 0L).equals(tileMap.getTileXY(1L, 0L)));
        assertEquals(tileMap.getTileXY(0L, 0L), tileMap.getTileXY(1L, 1L));
    }

    public void testReadOneTilesetCsv() throws Exception
    {
    }

    private static void closeQuietly(final InputStream inputStream)
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