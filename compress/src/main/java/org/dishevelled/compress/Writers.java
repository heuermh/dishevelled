/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2021 held jointly by the individual authors.

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
import static org.dishevelled.compress.Compress.isZstdFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;

import javax.annotation.Nullable;

import htsjdk.samtools.util.BlockCompressedOutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;

/**
 * File and output stream writers with support for bgzf, gzip, and bzip2 compression.
 *
 * @author  Michael Heuer
 */
public final class Writers
{

    /**
     * Private no-arg constructor.
     */
    private Writers()
    {
        // empty
    }


    /**
     * Create and return a new buffered print writer with bgzf compression for the specified output stream.
     *
     * @since 1.3
     * @param outputStream output stream, must not be null
     * @return a new buffered print writer with bgzf compression for the specified output stream
     * @throws IOException if an I/O error occurs
     */
    public static PrintWriter bgzfOutputStreamWriter(final OutputStream outputStream) throws IOException
    {
        checkNotNull(outputStream);
        return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new BlockCompressedOutputStream(outputStream, (File) null))), false);
    }

    /**
     * Create and return a new buffered print writer with gzip compression for the specified output stream.
     *
     * @param outputStream output stream, must not be null
     * @return a new buffered print writer with gzip compression for the specified output stream
     * @throws IOException if an I/O error occurs
     */
    public static PrintWriter gzipOutputStreamWriter(final OutputStream outputStream) throws IOException
    {
        checkNotNull(outputStream);
        return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new GzipCompressorOutputStream(outputStream))), true);
    }

    /**
     * Create and return a new buffered print writer with bzip2 compression for the specified output stream.
     *
     * @param outputStream output stream, must not be null
     * @return a new buffered print writer with bzip2 compression for the specified output stream
     * @throws IOException if an I/O error occurs
     */
    public static PrintWriter bzip2OutputStreamWriter(final OutputStream outputStream) throws IOException
    {
        checkNotNull(outputStream);
        return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new BZip2CompressorOutputStream(outputStream))), true);
    }

    /**
     * Create and return a new buffered print writer with Zstandard (zstd) compression for the specified output stream.
     *
     * @since 1.4
     * @param outputStream output stream, must not be null
     * @return a new buffered print writer with Zstandard (zstd) compression for the specified output stream
     * @throws IOException if an I/O error occurs
     */
    public static PrintWriter zstdOutputStreamWriter(final OutputStream outputStream) throws IOException
    {
        checkNotNull(outputStream);
        return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new ZstdCompressorOutputStream(outputStream))), true);
    }

    /**
     * Create and return a new buffered print writer with support for bgzf, gzip, bzip2, or zstd compression for
     * the specified file or <code>stdout</code> if the file is null.
     *
     * @param file file, if any
     * @return a new buffered print writer with support for bgzf, gzip, bzip2, or zstd compression for
     *    the specified file or <code>stdout</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static PrintWriter writer(@Nullable final File file) throws IOException
    {
        return writer(file, false);
    }

    /**
     * Create and return a new buffered print writer with support for bgzf, gzip, bzip2, or zstd compression for
     * the specified file or <code>stdout</code> if the file is null.
     *
     * @param file file, if any
     * @param append true to append to the specified file
     * @return a new buffered print writer with support for bgzf, gzip, bzip2, or zstd compression for
     *    the specified file or <code>stdout</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static PrintWriter writer(@Nullable final File file, final boolean append) throws IOException
    {
        if (file == null)
        {
            return new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        }
        else if (isZstdFile(file))
        {
            return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new ZstdCompressorOutputStream(new FileOutputStream(file, append)))), true);
        }
        else if (isBgzfFile(file))
        {
            return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new BlockCompressedOutputStream(file))), false);
        }
        else if (isGzipFile(file))
        {
            return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new GzipCompressorOutputStream(new FileOutputStream(file, append)))), true);
        }
        else if (isBzip2File(file))
        {
            return new PrintWriter(new BufferedWriter(new OutputStreamWriter(new BZip2CompressorOutputStream(new FileOutputStream(file, append)))), true);
        }
        return new PrintWriter(new BufferedWriter(new FileWriter(file, append)), true);
    }
}
