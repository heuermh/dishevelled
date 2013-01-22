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

import java.io.File;

/**
 * XDG Base Directory Specification thumbnail manager.
 *
 * @author  Michael Heuer
 */
public final class XdgThumbnailManager extends AbstractThumbnailManager
{
    /** Thumbnail directory. */
    private static final File THUMBNAIL_DIRECTORY;

    static
    {
        File thumbnailDirectory = null;
        String xdgCacheHome = System.getenv("XDG_CACHE_HOME");
        if (xdgCacheHome != null)
        {
            thumbnailDirectory = new File(xdgCacheHome, "thumbnails");
        }
        else
        {
            String userHome = System.getProperty("user.home");
            File cache = new File(userHome, ".cache");
            cache.mkdir();
            cache.setReadable(true, true);
            cache.setWritable(true, true);
            cache.setExecutable(true, true);
            thumbnailDirectory = new File(cache, "thumbnails");
        }
        THUMBNAIL_DIRECTORY = thumbnailDirectory;
    }

    /**
     * Create a new XDG Base Directory Specification thumbnail manager.
     */
    public XdgThumbnailManager()
    {
        super(THUMBNAIL_DIRECTORY);
    }
}