/*

    dsh-thumbnail  Implementation of the freedesktop.org thumbnail specification.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.thumbnail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;

import java.io.File;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Thumbnail.
 *
 * @author  Michael Heuer
 */
public final class ThumbnailTest
{
    private URI uri;
    private BufferedImage image;

    @Before
    public void setUp() throws Exception
    {
        uri = new URI("http://localhost");
        image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    public void testConstructorNullURI()
    {
        assertNotNull(new Thumbnail(null, 0L, 0, 0, image));
    }

    @Test
    public void testConstructorNullImage()
    {
        assertNotNull(new Thumbnail(uri, 0L, 0, 0, null));
    }

    @Test
    public void testConstructor()
    {
        Thumbnail thumbnail = new Thumbnail(uri, 1L, 10, 100, image);
        assertNotNull(thumbnail);
        assertEquals(uri, thumbnail.getURI());
        assertEquals(1L, thumbnail.getModificationTime());
        assertEquals(10, thumbnail.getWidth());
        assertEquals(100, thumbnail.getHeight());
        assertEquals(image, thumbnail.getImage());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testReadNullFile() throws Exception
    {
        Thumbnail.read(null);
    }

    /*
    public void testReadFileNotReadable() throws Exception
    {
    }
    */

    @Test(expected=IllegalArgumentException.class)
    public void testWriteNullFile() throws Exception
    {
        Thumbnail thumbnail = new Thumbnail(uri, 1L, 10, 100, image);
        thumbnail.write(null);
    }

    /*
    public void testWriteFileNotWriteable() throws Exception
    {
    }
    */

    @Test
    public void testWrite() throws Exception
    {
        Thumbnail thumbnail = new Thumbnail(uri, 1L, 10, 100, image);
        File thumbnailFile = File.createTempFile("thumbnailTest", ".png");
        thumbnail.write(thumbnailFile);
        assertTrue(thumbnailFile.exists());
        assertTrue(thumbnailFile.canRead());
    }

    @Test
    public void testWriteThenRead() throws Exception
    {
        Thumbnail thumbnail = new Thumbnail(uri, 1L, 10, 100, image);
        File thumbnailFile = File.createTempFile("thumbnailTest", ".png");
        thumbnail.write(thumbnailFile);

        Thumbnail thumbnailCopy = Thumbnail.read(thumbnailFile);
        assertNotNull(thumbnailCopy);
        assertEquals(thumbnail.getURI(), thumbnailCopy.getURI());
        assertEquals(thumbnail.getModificationTime(), thumbnailCopy.getModificationTime());
        assertEquals(thumbnail.getWidth(), thumbnailCopy.getWidth());
        assertEquals(thumbnail.getHeight(), thumbnailCopy.getHeight());
        assertNotNull(thumbnailCopy.getImage());
    }
}