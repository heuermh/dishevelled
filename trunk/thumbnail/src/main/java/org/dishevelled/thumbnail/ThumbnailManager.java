/*

    dsh-thumbnail  Implementation of the freedesktop.org thumbnail specification.
    Copyright (c) 2013-2015 held jointly by the individual authors.

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

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.net.URI;

/**
 * Thumbnail manager.
 *
 * @author  Michael Heuer
 */
public interface ThumbnailManager
{

    /**
     * Create and return a normal size (128x128 pixel) thumbnail image for the specified URI.
     *
     * @param uri URI for the original image, must not be null
     * @param modificationTime modification time for the original image
     * @return a normal size (128x128 pixel) thumbnail image for the specified URI
     * @throws IOException if an I/O error occurs
     */
    BufferedImage createThumbnail(final URI uri, final long modificationTime) throws IOException;

    /**
     * Create and return a large size (256x256 pixel) thumbnail image for the specified URI.
     *
     * @param uri URI for the original image, must not be null
     * @param modificationTime modification time for the original image
     * @return a large size (256x256 pixel) thumbnail image for the specified URI
     * @throws IOException if an I/O error occurs
     */
    BufferedImage createLargeThumbnail(final URI uri, final long modificationTime) throws IOException;
}
