/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2025 held jointly by the individual authors.

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
package org.dishevelled.compress;

import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.compress.Compress.isBgzfFile;
import static org.dishevelled.compress.Compress.isBgzfInputStream;
import static org.dishevelled.compress.Compress.isBzip2File;
import static org.dishevelled.compress.Compress.isGzipFile;
import static org.dishevelled.compress.Compress.isXzFile;
import static org.dishevelled.compress.Compress.isZstdFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nullable;

import htsjdk.samtools.util.BlockCompressedInputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;

import org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream;

/**
 * File and path input streams with support for bgzf, gzip, bzip2, xz, and zstd compression.
 *
 * @since 1.7
 * @author  Michael Heuer
 */
public final class InputStreams
{

    /**
     * Private no-arg constructor.
     */
    private InputStreams()
    {
        // empty
    }


    /**
     * Create and return a new buffered input stream for the specified compressed file,
     * autodetecting the compression type from the first few bytes of the file.
     *
     * @param file file, must not be null
     * @return a new buffered input stream for the specified file
     * @throws IOException if an I/O error occurs
     */
    public static InputStream compressedFileInputStream(final File file) throws IOException
    {
        checkNotNull(file);
        return compressedInputStream(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered input stream for the specified compressed input stream,
     * autodetecting the compression type from the first few bytes of the stream.
     *
     * @param inputStream input stream, must not be null
     * @return a new buffered input stream for the specified input stream
     * @throws IOException if an I/O error occurs
     */
    public static InputStream compressedInputStream(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        BufferedInputStream bufferedInputStream = inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
        try
        {
            if (isBgzfInputStream(bufferedInputStream))
            {
                return bgzfInputStream(bufferedInputStream);
            }
            return new BufferedInputStream(new CompressorStreamFactory().createCompressorInputStream(bufferedInputStream));
        }
        catch (CompressorException e)
        {
            // fall back to uncompressed input stream
            return bufferedInputStream;
        }
    }

    /**
     * Create and return a new buffered input stream for the specified block compressed gzip (BGZF) compressed file.
     *
     * @param file bgzf compressed file, must not be null
     * @return a new buffered input stream for the specified block compressed gzip (BGZF) compressed file
     * @throws IOException if an I/O error occurs
     */
    public static InputStream bgzfFileInputStream(final File file) throws IOException
    {
        checkNotNull(file);
        return new BufferedInputStream(new BlockCompressedInputStream(file));
    }

    /**
     * Create and return a new buffered input stream for the specified block compressed gzip (BGZF) compressed input stream.
     *
     * @param inputStream bgzf compressed input stream, must not be null
     * @return a new buffered input stream for the specified block compressed gzip (BGZF) compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static InputStream bgzfInputStream(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedInputStream(new BlockCompressedInputStream(inputStream));
    }

    /**
     * Create and return a new buffered input stream for the specified gzip compressed file.
     *
     * @param file gzip compressed file, must not be null
     * @return a new buffered input stream for the specified gzip compressed file
     * @throws IOException if an I/O error occurs
     */
    public static InputStream gzipFileInputStream(final File file) throws IOException
    {
        checkNotNull(file);
        return gzipInputStream(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered input stream for the specified gzip compressed input stream.
     *
     * @param inputStream gzip compressed input stream, must not be null
     * @return a new buffered input stream for the specified gzip compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static InputStream gzipInputStream(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedInputStream(new GzipCompressorInputStream(inputStream));
    }

    /**
     * Create and return a new buffered input stream for the specified bzip2 compressed file.
     *
     * @param file bzip2 compressed file, must not be null
     * @return a new buffered input stream for the specified bzip2 compressed file
     * @throws IOException if an I/O error occurs
     */
    public static InputStream bzip2FileInputStream(final File file) throws IOException
    {
        checkNotNull(file);
        return bzip2InputStream(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered input stream for the specified bzip2 compressed input stream.
     *
     * @param inputStream bzip2 compressed input stream, must not be null
     * @return a new buffered input stream for the specified bzip2 compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static InputStream bzip2InputStream(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedInputStream(new BZip2CompressorInputStream(inputStream));
    }

    /**
     * Create and return a new buffered input stream for the specified XZ compressed file.
     *
     * @param file XZ compressed file, must not be null
     * @return a new buffered input stream for the specified XZ compressed file
     * @throws IOException if an I/O error occurs
     */
    public static InputStream xzFileInputStream(final File file) throws IOException
    {
        checkNotNull(file);
        return xzInputStream(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered input stream for the specified XZ compressed input stream.
     *
     * @param inputStream XZ compressed input stream, must not be null
     * @return a new buffered input sream for the specified XZ compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static InputStream xzInputStream(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedInputStream(new XZCompressorInputStream(inputStream));
    }

    /**
     * Create and return a new buffered input stream for the specified Zstandard (zstd) compressed file.
     *
     * @param file zstd compressed file, must not be null
     * @return a new buffered input stream for the specified Zstandard (zstd) compressed file
     * @throws IOException if an I/O error occurs
     */
    public static InputStream zstdFileInputStream(final File file) throws IOException
    {
        checkNotNull(file);
        return zstdInputStream(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered input stream for the specified Zstandard (zstd) compressed input stream.
     *
     * @param inputStream zstd compressed input stream, must not be null
     * @return a new buffered input stream for the specified Zstandard (zstd) compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static InputStream zstdInputStream(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedInputStream(new ZstdCompressorInputStream(inputStream));
    }

    /**
     * Create and return a new buffered input stream with support for bgzf, gzip, bzip2, xz, or zstd compression
     * for the specified path name or <code>stdin</code> if the path name is null or <code>-</code>.
     *
     * @param pathName path name, if any
     * @param options options specifying how the path is opened, if any
     * @return a new buffered input stream with support for bgzf, gzip, bzip2, xz, or zstd compression for the
     *    specified path name or <code>stdin</code> if the path name is null or <code>-</code>
     * @throws IOException if an I/O error occurs
     */
    public static InputStream inputStream(@Nullable final String pathName, @Nullable final OpenOption... options) throws IOException {
        if (pathName == null || "-".equals(pathName))
        {
            return inputStream((File) null);
        }
        URI uri = URI.create(pathName);

        // default to file: if scheme is missing
        String scheme = uri.getScheme();
        if (scheme == null || "".equals(scheme))
        {
            return inputStream(new File(pathName).toPath());
        }

        // create path from URI, allowing custom providers, e.g. hdfs, s3, etc.
        return inputStream(Paths.get(uri), options);
    }

    /**
     * Create and return a new buffered input stream with support for bgzf, gzip, bzip2, xz, or zstd compression
     * for the specified path or <code>stdin</code> if the path is null or <code>-</code>.
     *
     * @param path path, if any
     * @param options options specifying how the path is opened, if any
     * @return a new buffered input stream with support for bgzf, gzip, bzip2, xz, or zstd compression for the
     *    specified path or <code>stdin</code> if the path is null or <code>-</code>
     * @throws IOException if an I/O error occurs
     */
    public static InputStream inputStream(@Nullable final Path path, @Nullable final OpenOption... options) throws IOException
    {
        try
        {
            // try file API first
            return inputStream(path == null ? (File) null : path.toFile());
        }
        catch (UnsupportedOperationException e)
        {
            // expected from some providers, e.g. s3 on path.toFile()
        }
        return compressedInputStream(Files.newInputStream(path, options));
    }

    /**
     * Create and return a new buffered input stream with support for bgzf, gzip, bzip2, xz, or zstd compression
     * for the specified file or <code>stdin</code> if the file is null or <code>-</code>.
     *
     * @param file file, if any
     * @return a new buffered input stream with support for bgzf, gzip, bzip2, xz, or zstd compression for the
     *    specified file or <code>stdin</code> if the file is null or <code>-</code>
     * @throws IOException if an I/O error occurs
     */
    public static InputStream inputStream(@Nullable final File file) throws IOException
    {
        if (file == null || "-".equals(file.getName()))
        {
            return compressedInputStream(System.in);
        }
        else if (isZstdFile(file))
        {
            return zstdFileInputStream(file);
        }
        else if (isBgzfFile(file))
        {
            return bgzfFileInputStream(file);
        }
        else if (isGzipFile(file))
        {
            return gzipFileInputStream(file);
        }
        else if (isBzip2File(file))
        {
            return bzip2FileInputStream(file);
        }
        else if (isXzFile(file))
        {
            return xzFileInputStream(file);
        }
        return new BufferedInputStream(new FileInputStream(file));
    }
}
