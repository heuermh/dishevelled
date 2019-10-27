/*

    dsh-thumbnail-drop  Thumbnail image directory watcher.
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
package org.dishevelled.thumbnail.drop;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.nio.file.attribute.BasicFileAttributes;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

import org.dishevelled.thumbnail.ThumbnailManager;
import org.dishevelled.thumbnail.XdgThumbnailManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image directory watcher thumbnail example.
 *
 * @author  Michael Heuer
 */
public final class ThumbnailDrop implements Callable<Integer>
{
    /** Watch directory. */
    private final Path watchDirectory;

    /** Destination directory. */
    private final Path destinationDirectory;

    /** Executor service. */
    private final ExecutorService executorService;

    /** File system watcher. */
    private final WatchService watcher;

    /** Thumbnail manager. */
    private final ThumbnailManager thumbnailManager = new XdgThumbnailManager();

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(ThumbnailDrop.class);

    /** Match JPEG file extensions. */
    private static final Pattern JPEG_FILE_EXTENSIONS = Pattern.compile("^.+\\.(?i)(?:jpe?g)$");

    /** Main thread timeout, in ms. */
    private static final long TIMEOUT = 1000L;

    /** Empty file initial retry, in ms. */
    private static final long INITIAL_RETRY = 50L;

    /** Empty file maximum retry, in ms. */
    private static final long MAXIMUM_RETRY = 2000L;

    /** Usage string. */
    private static final String USAGE = "thumbnail-drop -w . -d ~/images";


    /**
     * Create a new image directory watcher thumbnail example with the specified watch and destination directories.
     *
     * @param watchDirectory watch directory, must not be null
     * @param destinationDirectory destination directory, must not be null
     * @param executorService executor service, must not be null
     */
    public ThumbnailDrop(final File watchDirectory,
                         final File destinationDirectory,
                         final ExecutorService executorService)
    {
        if (watchDirectory == null)
        {
            throw new IllegalArgumentException("watchDirectory must not be null");
        }
        if (destinationDirectory == null)
        {
            throw new IllegalArgumentException("destinationDirectory must not be null");
        }
        if (executorService == null)
        {
            throw new IllegalArgumentException("executorService must not be null");
        }
        this.watchDirectory = watchDirectory.toPath();
        this.destinationDirectory = destinationDirectory.toPath();
        this.executorService = executorService;

        try
        {
            watcher = this.watchDirectory.getFileSystem().newWatchService();
            this.watchDirectory.register(watcher, ENTRY_CREATE);
        }
        catch (Exception e)
        {
            throw new RuntimeException("could not create file system watch service", e);
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
            for (WatchEvent<?> event : key.pollEvents())
            {
                WatchEvent.Kind kind = event.kind();
                if (kind == OVERFLOW)
                {
                    continue;
                }

                WatchEvent<Path> pathEvent = cast(event);
                Path name = pathEvent.context();
                Path path = watchDirectory.resolve(name);
                if (kind == ENTRY_CREATE)
                {
                    created(path);
                }
                else if (kind == ENTRY_MODIFY)
                {
                    modified(path);
                }
            }
            if (!key.reset())
            {
                key.cancel();
                try
                {
                    watcher.close();
                }
                catch (IOException e)
                {
                    // ignore
                }
                break;
            }
        }
    }

    /**
     * Notify this the specified path has been created.
     *
     * @param path created path
     */
    void created(final Path path)
    {
        logger.trace("heard " + path + " created");

        // sucks
        // String mimeType = Files.probeContentType(path);
        Matcher matcher = JPEG_FILE_EXTENSIONS.matcher(path.toString());
        if (matcher.matches())
        {
            try
            {
                // block until file is non-empty
                waitForNonEmptyFile(path);

                File destinationFile = new File(destinationDirectory.toFile(), fileName(path));
                if (destinationFile.exists())
                {
                    // if destination file exists, delete newly created path
                    logger.info(destinationFile + " exists, deleting " + path);
                    Files.delete(path);
                }
                else
                {
                    // else move newly created path to destination directory
                    logger.info("moving " + path + " to " + destinationFile);
                    Files.move(path, destinationFile.toPath());
                }

                // trigger thumbnail creation
                logger.trace("creating thumbnails for " + destinationFile);
                thumbnailManager.createThumbnail(destinationFile.toURI(), 0L);
                thumbnailManager.createLargeThumbnail(destinationFile.toURI(), 0L);
            }
            catch (IOException e)
            {
                logger.warn("unable to create thumbnails, " + e.getMessage());

                try
                {
                    // delete problematic file from watch directory
                    logger.info("deleting " + path);
                    Files.delete(path);
                }
                catch (IOException ioe)
                {
                    // ignore
                }
            }
        }
    }

    /**
     * Notify this the specified path has been modified.
     *
     * @param path modified path
     */
    void modified(final Path path)
    {
        logger.trace("heard " + path + " modified");
        created(path);
    }

    /**
     * Block the current thread until the specified path is non-empty, up to the maximum retry.
     *
     * @see #MAXIMUM_RETRY
     * @param path path
     * @throws IOException if an I/O error occurs
     */
    void waitForNonEmptyFile(final Path path) throws IOException
    {
        for (long retry = INITIAL_RETRY; retry < MAXIMUM_RETRY; retry *= 2)
        {
            if (isNonEmpty(path))
            {
                return;
            }
            try
            {
                logger.trace("  waiting " + retry + "ms for empty file...");
                Thread.currentThread().sleep(retry);
            }
            catch (InterruptedException e)
            {
                // ok
            }
        }
        throw new IOException("path " + path + " is empty");
    }

    /**
     * Return true if the specified path is a non-empty regular file.
     *
     * @param path path
     * @return true if the specified path is a non-empty regular file
     * @throws IOException if an I/O error occurs
     */
    static boolean isNonEmpty(final Path path) throws IOException
    {
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return attr.isRegularFile() && (attr.size() > 0L);
    }

    /**
     * Generate a new file name for the specified path from its MD5 checksum.
     *
     * @param path path
     * @return a new file name for the specified path from its MD5 checksum
     * @throws IOException if an I/O error occurs
     */
    static String fileName(final Path path) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(path.toFile());
            return DigestUtils.md5Hex(inputStream) + ".jpg";
        }
        finally
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

    @Override
    public Integer call() throws Exception
    {
        for (;;)
        {
            try
            {
                Thread.currentThread().sleep(TIMEOUT);
            }
            catch (InterruptedException e)
            {
                return 0;
            }
        }
    }

    /**
     * Cast the specified watch event to <code>WatchEvent&lt;T&gt;</code>.
     *
     * @param <T> type
     * @param event watch event to cast
     * @return the specified watch event cast to <code>WatchEvent&lt;T&gt;</code>
     */
    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(final WatchEvent<?> event)
    {
        return (WatchEvent<T>) event;
    }


    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        Switch about = new Switch("a", "about", "display about message");
        Switch version = new Switch("v", "version", "display about message");
        Switch help = new Switch("h", "help", "display help message");
        FileArgument watchDirectory = new FileArgument("w", "watch-directory", "watch directory", true);
        FileArgument destinationDirectory = new FileArgument("d", "destination-directory", "destination directory", true);
        Switch verbose = new Switch("r", "verbose", "verbose logging output");

        ArgumentList arguments = new ArgumentList(about, version, help, watchDirectory, destinationDirectory, verbose);
        CommandLine commandLine = new CommandLine(args);

        ThumbnailDrop thumbnailDrop = null;
        try
        {
            CommandLineParser.parse(commandLine, arguments);
            if (about.wasFound() || version.wasFound())
            {
                About.about(System.out);
                System.exit(0);
            }
            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
                System.exit(0);
            }

            System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
            System.setProperty("org.slf4j.simpleLogger.showDateTime", "false");
            System.setProperty("org.slf4j.simpleLogger.showThreadName", "false");
            System.setProperty("org.slf4j.simpleLogger.showLogName", "false");
            System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "false");

            if (verbose.wasFound())
            {
                System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
            }
            else
            {
                System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
            }

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            thumbnailDrop = new ThumbnailDrop(watchDirectory.getValue(), destinationDirectory.getValue(), executorService);
        }
        catch (CommandLineParseException e)
        {
            if (about.wasFound() || version.wasFound())
            {
                About.about(System.out);
                System.exit(0);
            }
            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
                System.exit(0);
            }
            Usage.usage(USAGE, e, commandLine, arguments, System.err);
            System.exit(-1);
        }
        try
        {
            System.exit(thumbnailDrop.call());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
