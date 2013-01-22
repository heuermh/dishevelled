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

import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;

import java.io.File;

import java.net.URI;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * Unit test for XdgThumbnailManager.
 *
 * @author  Michael Heuer
 */
public final class XdgThumbnailManagerTest extends AbstractThumbnailManagerTest
{
    @Override
    protected ThumbnailManager createThumbnailManager()
    {
        return new XdgThumbnailManager();
    }

    /**

       todo:  turn into unit test

    @Test
    public void functionalTest() throws Exception
    {
        File original = File.createTempFile("xdgThumbnailManagerTest", ".png");
        BufferedImage originalImage = ImageIO.read(getClass().getResource("lena512color.png"));
        ImageIO.write(originalImage, "png", original);

        URI originalUri = original.toURI();
        long lastModified = original.lastModified();

        BufferedImage thumbnailImage = thumbnailManager.createThumbnail(originalUri, lastModified);
        assertNotNull(thumbnailImage);

        BufferedImage largeThumbnailImage = thumbnailManager.createLargeThumbnail(originalUri, lastModified);
        assertNotNull(largeThumbnailImage);
    }
    */
}