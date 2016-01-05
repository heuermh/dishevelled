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

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;

import java.nio.file.attribute.PosixFilePermission;

import java.util.EnumSet;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.apache.commons.codec.digest.DigestUtils;

import org.imgscalr.Scalr;

/**
 * Abstract thumbnail manager.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractThumbnailManager implements ThumbnailManager
{
    /** Root thumbnail directory. */
    private final File directory;

    /** Directory for normal size (128x128 pixel) thumbnails. */
    private final File normalDirectory;

    /** Directory for large size (256x256 pixel) thumbnails. */
    private final File largeDirectory;

    /** Directory for metadata about failed thumbnail images. */
    private final File failDirectory;

    /** Maximum cache size. */
    private static final int MAXIMUM_SIZE = 1000;

    /** Cache expire after write, in seconds. */
    private static final int EXPIRE = 30;

    /** Cache of thumbnails keyed by thumbnail file. */
    private final LoadingCache<File, Thumbnail> cache = CacheBuilder.newBuilder()
        .maximumSize(MAXIMUM_SIZE)
        .expireAfterWrite(EXPIRE, TimeUnit.SECONDS)
        .build(new CacheLoader<File, Thumbnail>()
               {
                   @Override
                   public Thumbnail load(final File key) throws IOException
                   {
                       return Thumbnail.read(key);
                   }
               });

    /** Normal (128x128 pixel) size. */
    private static final int NORMAL_SIZE = 128;

    /** Large (256x256 pixel) size. */
    private static final int LARGE_SIZE = 256;

    /** Directory permissions. */
    private static final Set<PosixFilePermission> DIRECTORY_PERMISSIONS = EnumSet.of(PosixFilePermission.OWNER_READ,
                                                                                     PosixFilePermission.OWNER_WRITE,
                                                                                     PosixFilePermission.OWNER_EXECUTE);
    /** File permissions. */
    private static final Set<PosixFilePermission> FILE_PERMISSIONS = EnumSet.of(PosixFilePermission.OWNER_READ,
                                                                                PosixFilePermission.OWNER_WRITE);


    /**
     * Create a new abstract thumbnail manager with the specified root thumbnail directory.
     *
     * @param directory root thumbnail directory, must not be null
     */
    protected AbstractThumbnailManager(final File directory)
    {
        if (directory == null)
        {
            throw new IllegalArgumentException("directory must not be null");
        }
        this.directory = directory;
        normalDirectory = new File(this.directory, "normal");
        largeDirectory = new File(this.directory, "large");
        // todo: fail directory should be application specific; e.g. fail/dsh-thumbnail-1.0/
        failDirectory = new File(this.directory, "fail");
        normalDirectory.mkdirs();
        largeDirectory.mkdirs();
        failDirectory.mkdirs();

        fixDirectoryPermissions(this.directory);
        fixDirectoryPermissions(normalDirectory);
        fixDirectoryPermissions(largeDirectory);
        fixDirectoryPermissions(failDirectory);
    }


    /**
     * Fix the permissions of the specified file.
     *
     * @param file file
     */
    private void fixPermissions(final File file)
    {
        try
        {
            // set to 600
            Files.setPosixFilePermissions(file.toPath(), FILE_PERMISSIONS);
        }
        catch (Exception e)
        {
            // ignore
        }
    }

    /**
     * Fix the permissions of the specified directory.
     *
     * @param directory directory
     */
    private void fixDirectoryPermissions(final File directory)
    {
        try
        {
            // set to 700
            Files.setPosixFilePermissions(directory.toPath(), DIRECTORY_PERMISSIONS);
        }
        catch (Exception e)
        {
            // ignore
        }
    }

    /**
     * Attempt to create an empty fail file.
     *
     * @param failFile fail file to create
     */
    private void createFailFile(final File failFile)
    {
        try
        {
            failFile.createNewFile();
        }
        catch (IOException e)
        {
            // ignore
        }
    }

    /**
     * Create and return a thumbnail image for the specified URI.
     *
     * @param uri URI for the original image, must not be null
     * @param modificationTime modification time for the original image
     * @param thumbnailDirectory thumbnail directory
     * @param size size
     * @return a thumbnail image for the specified URI
     * @throws IOException if an I/O error occurs
     */
    private BufferedImage createThumbnail(final URI uri,
                                          final long modificationTime,
                                          final File thumbnailDirectory,
                                          final int size) throws IOException
    {
        if (uri == null)
        {
            throw new IllegalArgumentException("uri must not be null");
        }
        String fileName = DigestUtils.md5Hex(uri.toString()) + ".png";

        // fail fast if fail file exists
        File failFile = new File(failDirectory, fileName);
        if (failFile.exists())
        {
            throw new IOException("cannot create a thumbnail image for " + uri);
        }

        // check if thumbnail exists
        File thumbnailFile = new File(thumbnailDirectory, fileName);
        if (thumbnailFile.exists())
        {
            Thumbnail thumbnail = cache.getUnchecked(thumbnailFile);
            if (thumbnail.getModificationTime() == modificationTime)
            {
                return thumbnail.getImage();
            }
        }

        // load the image and create new thumbnail
        URL url = uri.toURL();
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(url);
        }
        catch (IOException e)
        {
            createFailFile(failFile);
            throw new IOException("cannot create a thumbnail image for " + uri, e);
        }
        if (image == null)
        {
            createFailFile(failFile);
            throw new IOException("cannot create a thumbnail image for " + uri);
        }
        Thumbnail thumbnail = new Thumbnail(uri, modificationTime, image.getWidth(), image.getHeight(),
                                            Scalr.resize(image, size));

        File tmp = File.createTempFile("tmp", ".png", thumbnailDirectory);
        fixPermissions(tmp);
        thumbnail.write(tmp);
        com.google.common.io.Files.move(tmp, thumbnailFile);

        return thumbnail.getImage();
    }

    @Override
    public final BufferedImage createThumbnail(final URI uri, final long modificationTime) throws IOException
    {
        return createThumbnail(uri, modificationTime, normalDirectory, NORMAL_SIZE);
    }

    @Override
    public final BufferedImage createLargeThumbnail(final URI uri, final long modificationTime) throws IOException
    {
        return createThumbnail(uri, modificationTime, largeDirectory, LARGE_SIZE);
    }
}
