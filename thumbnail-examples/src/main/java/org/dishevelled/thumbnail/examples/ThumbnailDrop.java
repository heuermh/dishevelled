/*

    dsh-thumbnail-examples  Examples for the thumbnail library.
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
package org.dishevelled.thumbnail.examples;

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

    /** Match JPEG file extensions. */
    private static final Pattern JPEG_FILE_EXTENSIONS = Pattern.compile("^.+\\.(?i)(?:jpe?g)$");

    /** Main thread timeout, in ms. */
    private static final long TIMEOUT = 1000L;

    /** Usage string. */
    private static final String USAGE = "thumbnail-drop -w . -d ~/images &";


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
        // sucks
        // String mimeType = Files.probeContentType(path);
        Matcher matcher = JPEG_FILE_EXTENSIONS.matcher(path.toString());
        if (matcher.matches())
        {
            try
            {
                File destinationFile = new File(destinationDirectory.toFile(), fileName(path));
                if (destinationFile.exists())
                {
                    // if destination file exists, delete newly created path
                    Files.delete(path);
                }
                else
                {
                    // else move newly created path to destination directory
                    Files.move(path, destinationFile.toPath());
                }

                // trigger thumbnail creation
                thumbnailManager.createThumbnail(destinationFile.toURI(), 0L);
                thumbnailManager.createLargeThumbnail(destinationFile.toURI(), 0L);
            }
            catch (IOException e)
            {
                // ignore
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
        created(path);
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
        Switch help = new Switch("h", "help", "display help message");
        FileArgument watchDirectory = new FileArgument("w", "watch-directory", "watch directory", true);
        FileArgument destinationDirectory = new FileArgument("d", "destination-directory", "destination directory", true);

        ArgumentList arguments = new ArgumentList(help, watchDirectory, destinationDirectory);
        CommandLine commandLine = new CommandLine(args);

        ThumbnailDrop thumbnailDrop = null;
        try
        {
            CommandLineParser.parse(commandLine, arguments);
            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
                System.exit(0);
            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            thumbnailDrop = new ThumbnailDrop(watchDirectory.getValue(), destinationDirectory.getValue(), executorService);
        }
        catch (CommandLineParseException e)
        {
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
