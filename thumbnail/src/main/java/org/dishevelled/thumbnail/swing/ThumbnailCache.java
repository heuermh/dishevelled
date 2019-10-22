/*

    dsh-thumbnail  Implementation of the freedesktop.org thumbnail specification.
    Copyright (c) 2013-2019 held jointly by the individual authors.

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
package org.dishevelled.thumbnail.swing;

import java.awt.Component;

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.net.URI;

import java.util.List;

import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.Futures;

import org.dishevelled.thumbnail.ThumbnailManager;

/**
 * Thumbnail cache.
 *
 * @author  Michael Heuer
 */
public final class ThumbnailCache
{
    /** Empty image. */
    private static final BufferedImage EMPTY_IMAGE = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);

    /** Empty large image. */
    private static final BufferedImage EMPTY_LARGE_IMAGE = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

    /** Thumbnail manager. */
    private final ThumbnailManager thumbnailManager;

    /** List of components to repaint. */
    private final List<Component> repaint;

    /** Executor service. */
    private final ExecutorService executorService;

    /** Last modified cache. */
    private final LastModifiedCache lastModifiedCache;

    /** Maximum cache size. */
    private static final int MAXIMUM_SIZE = 100000;

    /** Cache refresh after write, in ms. */
    private static final int REFRESH = 10;

    /** Cache of thumbnails keyed by URI, allowing for asynchronous refresh. */
    private final LoadingCache<URI, BufferedImage> cache = CacheBuilder.newBuilder()
        .maximumSize(MAXIMUM_SIZE)
        .refreshAfterWrite(REFRESH, TimeUnit.MILLISECONDS)
        .build(new CacheLoader<URI, BufferedImage>()
               {
                   @Override
                   public BufferedImage load(final URI uri)
                   {
                       return EMPTY_IMAGE;
                   }

                   @Override
                   public ListenableFuture<BufferedImage> reload(final URI uri, final BufferedImage image)
                   {
                       if (image.equals(EMPTY_IMAGE))
                       {
                           ListenableFutureTask<BufferedImage> task = ListenableFutureTask.create(new Callable<BufferedImage>()
                               {
                                   @Override
                                   public BufferedImage call() throws IOException
                                   {
                                       return thumbnailManager.createThumbnail(uri, lastModifiedCache.get(uri));
                                   }
                               });
                           task.addListener(new Runnable()
                               {
                                   @Override
                                   public void run()
                                   {
                                       SwingUtilities.invokeLater(new Runnable()
                                           {
                                               @Override
                                               public void run()
                                               {
                                                   for (Component component : repaint)
                                                   {
                                                       component.repaint();
                                                   }
                                               }
                                           });
                                   }
                               }, executorService);
                           executorService.execute(task);
                           return task;
                       }
                       return Futures.immediateFuture(image);
                   }
               });

    /** Cache of large thumbnails keyed by URI, allowing for asynchronous refresh. */
    private final LoadingCache<URI, BufferedImage> largeCache = CacheBuilder.newBuilder()
        .maximumSize(MAXIMUM_SIZE)
        .refreshAfterWrite(REFRESH, TimeUnit.MILLISECONDS)
        .build(new CacheLoader<URI, BufferedImage>()
               {
                   @Override
                   public BufferedImage load(final URI uri)
                   {
                       return EMPTY_IMAGE;
                   }

                   @Override
                   public ListenableFuture<BufferedImage> reload(final URI uri, final BufferedImage image)
                   {
                       if (image.equals(EMPTY_IMAGE))
                       {
                           ListenableFutureTask<BufferedImage> task = ListenableFutureTask.create(new Callable<BufferedImage>()
                               {
                                   @Override
                                   public BufferedImage call() throws IOException
                                   {
                                       return thumbnailManager.createLargeThumbnail(uri, lastModifiedCache.get(uri));
                                   }
                               });
                           executorService.execute(task);
                           return task;
                       }
                       return Futures.immediateFuture(image);
                   }
               });

    /**
     * Create a new thumbnail cache with the specified thumbnail manager.
     *
     * @param thumbnailManager thumbnail manager, must not be null
     */
    public ThumbnailCache(final ThumbnailManager thumbnailManager)
    {
        if (thumbnailManager == null)
        {
            throw new IllegalArgumentException("thumbnailManager must not be null");
        }
        this.thumbnailManager = thumbnailManager;

        repaint = new CopyOnWriteArrayList<Component>();
        executorService = Executors.newCachedThreadPool();
        lastModifiedCache = new LastModifiedCache(executorService);
    }


    /**
     * Repaint the specified component on reload of thumbnail images.
     *
     * @param component component to repaint on reload of thumbnail images, must not be null
     */
    public void add(final Component component)
    {
        if (component == null)
        {
            throw new IllegalArgumentException("component must not be null");
        }
        repaint.add(component);
    }

    /**
     * Remove the specified component from the list of components to repaint on reload of thumbnail images.
     *
     * @param component component to remove from the list of components to repaint on reload of thumbnail images
     */
    public void remove(final Component component)
    {
        repaint.remove(component);
    }

    /**
     * Return a normal size (128x128 pixel) thumbnail image for the specified URI.  The thumbnail
     * image will not be null but may be an empty image; call again after repaint.
     *
     * @param uri URI for the original image, must not be null
     * @return a normal size (128x128 pixel) thumbnail image for the specified URI
     */
    public BufferedImage getThumbnail(final URI uri)
    {
        return cache.getUnchecked(uri);
    }

    /**
     * Return a large size (256x256 pixel) thumbnail image for the specified URI.  The thumbnail
     * image will not be null but may be an empty image; call again after repaint.
     *
     * @param uri URI for the original image, must not be null
     * @return a large size (256x256 pixel) thumbnail image for the specified URI
     */
    public BufferedImage getLargeThumbnail(final URI uri)
    {
        return largeCache.getUnchecked(uri);
    }
}
