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
package org.dishevelled.thumbnail.swing;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;

import java.net.URI;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.google.common.collect.Maps;

/**
 * Last modified cache.
 *
 * @author  Michael Heuer
 */
final class LastModifiedCache
{
    /** Executor service. */
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    /** File system watcher. */
    private final WatchService watcher;

    /** Watch keys. */
    private final ConcurrentMap<WatchKey, Path> watchKeys = Maps.newConcurrentMap();

    /** Cache of last modified timestamps keyed by URI. */
    private final LoadingCache<Path, Long> cache = CacheBuilder.newBuilder()
        .maximumSize(100000)
        .build(new CacheLoader<Path, Long>()
               {
                   @Override
                   public Long load(final Path path) throws IOException
                   {
                       Path parent = path.getParent();
                       if (parent != null)
                       {
                           watchKeys.putIfAbsent(parent.register(watcher, ENTRY_DELETE, ENTRY_MODIFY), parent);
                       }
                       return(path.toFile().lastModified());
                   }
               });

    /**
     * Create a new last modified cache.
     */
    LastModifiedCache()
    {
        try
        {
            watcher = FileSystems.getDefault().newWatchService();
        }
        catch (Exception e)
        {
            throw new RuntimeException("could not create last modified cache", e);
        }
        executorService.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    processEvents();
                }
            });
    }


    /**
     * Process file system watcher events.
     */
    void processEvents()
    {
        for (;;)
        {
            WatchKey key = null;
            try
            {
                key = watcher.take();
            }
            catch (InterruptedException e)
            {
                return;
            }
            Path dir = watchKeys.get(key);
            for (WatchEvent<?> event : key.pollEvents())
            {
                WatchEvent.Kind kind = event.kind();
                if (kind == OVERFLOW)
                {
                    continue;
                }

                WatchEvent<Path> pathEvent = cast(event);
                Path name = pathEvent.context();
                Path path = dir.resolve(name);
                if (kind == ENTRY_DELETE)
                {
                    deleted(path);
                }
                else if (kind == ENTRY_MODIFY)
                {
                    modified(path);
                }
            }
            if (!key.reset())
            {
                watchKeys.remove(key);
            }
        }
    }

    /**
     * Notify this last modified cache the specified path has been deleted.
     *
     * @param path deleted path
     */
    void deleted(final Path path)
    {
        cache.invalidate(path);
    }

    /**
     * Notify this last modified cache the specified path has been modified.
     *
     * @param path modified path
     */
    void modified(final Path path)
    {
        cache.invalidate(path);
    }

    /**
     * Return the last modified timestamp for the specified URI.
     *
     * @param uri URI
     * @return the last modified timestamp for the specified URI
     */
    long get(final URI uri)
    {
        return get(Paths.get(uri));
    }

    /**
     * Return the last modified timestamp for the specified path.
     *
     * @param path path
     * @return the last modified timestamp for the specified path
     */
    long get(final Path path)
    {
        return cache.getUnchecked(path);
    }

    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(final WatchEvent<?> event)
    {
        return (WatchEvent<T>) event;
    }

    // todo:  consider using lang3.concurrent initializer instead
    /**
     * Return the last modified cache.
     *
     * @return the last modified cache
     */
    static LastModifiedCache getInstance()
    {
        return LastModifiedCacheHolder.lastModifiedCache;
    }

    /**
     * Initialization on demand holder.
     */
    static class LastModifiedCacheHolder
    {
        /** Last modified cache. */
        static LastModifiedCache lastModifiedCache = new LastModifiedCache();
    }
}
