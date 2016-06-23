/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2015 held jointly by the individual authors.

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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import javax.annotation.Nullable;

import htsjdk.samtools.util.BlockCompressedInputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * File and input stream readers with support for bgzf, gzip, and bzip2 compression.
 *
 * @author  Michael Heuer
 */
public final class Readers
{

    /**
     * Private no-arg constructor.
     */
    private Readers()
    {
        // empty
    }


    /**
     * Create and return a new buffered reader for the specified input stream.
     *
     * @since 1.1
     * @param inputStream input stream, must not be null
     * @return a new buffered reader for the specified input stream
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader inputStreamReader(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * Create and return a new buffered reader for the specified compressed file,
     * autodetecting the compression type from the first few bytes of the file.
     *
     * @since 1.1
     * @param file file, must not be null
     * @return a new buffered reader for the specified file
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader compressedFileReader(final File file) throws IOException
    {
        checkNotNull(file);
        return compressedInputStreamReader(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered reader for the specified compressed input stream,
     * autodetecting the compression type from the first few bytes of the stream.
     *
     * @since 1.1
     * @param inputStream input stream, must not be null
     * @return a new buffered reader for the specified input stream
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader compressedInputStreamReader(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        BufferedInputStream bufferedInputStream = inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
        try
        {
            if (isBgzfInputStream(bufferedInputStream))
            {
                return bgzfInputStreamReader(bufferedInputStream);
            }
            return new BufferedReader(new InputStreamReader(new CompressorStreamFactory().createCompressorInputStream(bufferedInputStream)));
        }
        catch (CompressorException e)
        {
            // fall back to uncompressed input stream reader
            return inputStreamReader(bufferedInputStream);
        }
    }

    /**
     * Create and return a new buffered reader for the specified bgzf compressed file.
     *
     * @since 1.2
     * @param file bgzf compressed file, must not be null
     * @return a new buffered reader for the specified bgzf compressed file
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader bgzfFileReader(final File file) throws IOException
    {
        checkNotNull(file);
        return new BufferedReader(new InputStreamReader(new BlockCompressedInputStream(file)));
    }

    /**
     * Create and return a new buffered reader for the specified bgzf compressed input stream.
     *
     * @since 1.2
     * @param inputStream bgzf compressed input stream, must not be null
     * @return a new buffered reader for the specified bgzf compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader bgzfInputStreamReader(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedReader(new InputStreamReader(new BlockCompressedInputStream(inputStream)));
    }

    /**
     * Create and return a new buffered reader for the specified gzip compressed file.
     *
     * @since 1.1
     * @param file gzip compressed file, must not be null
     * @return a new buffered reader for the specified gzip compressed file
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader gzipFileReader(final File file) throws IOException
    {
        checkNotNull(file);
        return gzipInputStreamReader(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered reader for the specified gzip compressed input stream.
     *
     * @param inputStream gzip compressed input stream, must not be null
     * @return a new buffered reader for the specified gzip compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader gzipInputStreamReader(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedReader(new InputStreamReader(new GzipCompressorInputStream(inputStream)));
    }

    /**
     * Create and return a new buffered reader for the specified bzip2 compressed file.
     *
     * @since 1.1
     * @param file bzip2 compressed file, must not be null
     * @return a new buffered reader for the specified bzip2 compressed file
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader bzip2FileReader(final File file) throws IOException
    {
        checkNotNull(file);
        return bzip2InputStreamReader(new FileInputStream(file));
    }

    /**
     * Create and return a new buffered reader for the specified bzip2 compressed input stream.
     *
     * @param inputStream bzip2 compressed input stream, must not be null
     * @return a new buffered reader for the specified bzip2 compressed input stream
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader bzip2InputStreamReader(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return new BufferedReader(new InputStreamReader(new BZip2CompressorInputStream(inputStream)));
    }

    /**
     * Create and return a new buffered reader with support for bgzf, gzip, or bzip2 compression for the specified file
     * or <code>stdin</code> if the file is null.
     *
     * @param file file, if any
     * @return a new buffered reader with support for bgzf, gzip, or bzip2 compression for the specified file
     *    or <code>stdin</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader reader(@Nullable final File file) throws IOException
    {
        if (file == null)
        {
            return compressedInputStreamReader(System.in);
        }
        else if (isBgzfFile(file))
        {
            return bgzfFileReader(file);
        }
        else if (isGzipFile(file))
        {
            return gzipFileReader(file);
        }
        else if (isBzip2File(file))
        {
            return bzip2FileReader(file);
        }
        return new BufferedReader(new FileReader(file));
    }
}
