/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2019 held jointly by the individual authors.

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
import static org.dishevelled.compress.Compress.isBzip2File;
import static org.dishevelled.compress.Compress.isGzipFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.Writer;

import java.nio.charset.Charset;

import javax.annotation.Nullable;

import com.google.common.io.CharSink;
import com.google.common.io.Files;
import com.google.common.io.FileWriteMode;

import htsjdk.samtools.util.BlockCompressedOutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

/**
 * File and output stream sinks with support for bgzf, gzip, and bzip2 compression.
 *
 * @author  Michael Heuer
 */
public final class Sinks
{

    /**
     * Private no-args constructor.
     */
    private Sinks()
    {
        // empty
    }


    /**
     * Create and return a new char sink for the specified output stream.
     *
     * @since 1.3
     * @param outputStream output stream, must not be null
     * @return a new char sink for the specified output stream
     */
    public static CharSink outputStreamCharSink(final OutputStream outputStream)
    {
        checkNotNull(outputStream);
        return new CharSink()
            {
                @Override
                public Writer openStream() throws IOException
                {
                    return new BufferedWriter(new OutputStreamWriter(outputStream));
                }
            };
    }

    /**
     * Create and return a new block compressed gzip (BGZF) char sink for the specified output stream.
     *
     * @since 1.3
     * @param outputStream output stream, must not be null
     * @return a new block compressed gzip (BGZF) char sink for the specified output stream
     */
    public static CharSink bgzfOutputStreamCharSink(final OutputStream outputStream)
    {
        checkNotNull(outputStream);
        return new CharSink()
            {
                @Override
                public Writer openStream() throws IOException
                {
                    return new BufferedWriter(new OutputStreamWriter(new BlockCompressedOutputStream(outputStream, (File) null)));
                }
            };
    }

    /**
     * Create and return a new gzip compressed char sink for the specified output stream.
     *
     * @param outputStream output stream, must not be null
     * @return a new gzip compressed char sink for the specified output stream
     */
    public static CharSink gzipOutputStreamCharSink(final OutputStream outputStream)
    {
        checkNotNull(outputStream);
        return new CharSink()
            {
                @Override
                public Writer openStream() throws IOException
                {
                    return new BufferedWriter(new OutputStreamWriter(new GzipCompressorOutputStream(outputStream)));
                }
            };
    }

    /**
     * Create and return a new bzip2 compressed char sink for the specified output stream.
     *
     * @param outputStream output stream, must not be null
     * @return a new bzip2 compressed char sink for the specified output stream
     */
    public static CharSink bzip2OutputStreamCharSink(final OutputStream outputStream)
    {
        checkNotNull(outputStream);
        return new CharSink()
            {
                @Override
                public Writer openStream() throws IOException
                {
                    return new BufferedWriter(new OutputStreamWriter(new BZip2CompressorOutputStream(outputStream)));
                }
            };
    }

    /**
     * Create and return a new block compressed gzip (BGZF) char sink for the specified file.
     *
     * @since 1.2
     * @param file file, must not be null
     * @return a new block compressed gzip (BGZF) char sink for the specified file
     */
    public static CharSink bgzfFileCharSink(final File file)
    {
        checkNotNull(file);
        return new CharSink()
            {
                @Override
                public Writer openStream() throws IOException
                {
                    return new BufferedWriter(new OutputStreamWriter(new BlockCompressedOutputStream(file)));
                }
            };
    }

    /**
     * Create and return a new gzip compressed char sink for the specified file.
     *
     * @since 1.3
     * @param file file, must not be null
     * @param append true to append to the specified file
     * @return a new gzip compressed char sink for the specified file
     */
    public static CharSink gzipFileCharSink(final File file, final boolean append)
    {
        checkNotNull(file);
        return new CharSink()
            {
                @Override
                public Writer openStream() throws IOException
                {
                    return new BufferedWriter(new OutputStreamWriter(new GzipCompressorOutputStream(new FileOutputStream(file, append))));
                }
            };
    }

    /**
     * Create and return a new bzip2 compressed char sink for the specified file.
     *
     * @since 1.3
     * @param file file, must not be null
     * @param append true to append to the specified file
     * @return a new bzip2 compressed char sink for the specified file
     */
    public static CharSink bzip2FileCharSink(final File file, final boolean append)
    {
        checkNotNull(file);
        return new CharSink()
            {
                @Override
                public Writer openStream() throws IOException
                {
                    return new BufferedWriter(new OutputStreamWriter(new BZip2CompressorOutputStream(new FileOutputStream(file, append))));
                }
            };
    }

    /**
     * Create and return a new char sink with support for bgzf, gzip, or bzip2 compression for the specified file
     * or <code>stdout</code> if the file is null.  Defaults to <code>UTF-8</code> charset.
     *
     * @param file file, if any
     * @return a new char sink with support for bgzf, gzip, or bzip2 compression for the specified file
     *    or <code>stdout</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static CharSink charSink(@Nullable final File file) throws IOException
    {
        return charSink(file, false);
    }

    /**
     * Create and return a new char sink with support for bgzf, gzip, or bzip2 compression for the specified file
     * or <code>stdout</code> if the file is null.  Defaults to <code>UTF-8</code> charset.
     *
     * @param file file, if any
     * @param append true to append to the specified file
     * @return a new char sink with support for bgzf, gzip, or bzip2 compression for the specified file
     *    or <code>stdout</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static CharSink charSink(@Nullable final File file, final boolean append) throws IOException
    {
        return charSink(file, Charset.forName("UTF-8"), append);
    }

    /**
     * Create and return a new char sink with support for bgzf, gzip, or bzip2 compression for the specified file
     * or <code>stdout</code> if the file is null.
     *
     * @param file file, if any
     * @param charset charset, must not be null
     * @param append true to append to the specified file
     * @return a new char sink with support for bgzf, gzip, or bzip2 compression for the specified file
     *    or <code>stdout</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static CharSink charSink(@Nullable final File file, final Charset charset, final boolean append) throws IOException
    {
        checkNotNull(charset);
        if (file == null)
        {
            return outputStreamCharSink(System.out);
        }
        else if (isBgzfFile(file))
        {
            return bgzfFileCharSink(file);
        }
        else if (isGzipFile(file))
        {
            return gzipFileCharSink(file, append);
        }
        else if (isBzip2File(file))
        {
            return bzip2FileCharSink(file, append);
        }
        return append ? Files.asCharSink(file, charset, FileWriteMode.APPEND) : Files.asCharSink(file, charset);
    }
}
