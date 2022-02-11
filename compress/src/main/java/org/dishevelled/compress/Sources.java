/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2022 held jointly by the individual authors.

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

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;

import java.nio.charset.Charset;

import javax.annotation.Nullable;

import com.google.common.io.CharSource;
import com.google.common.io.Files;

/**
 * File and input stream sources with support for bgzf, gzip, bzip2, and zstd compression.
 *
 * @author  Michael Heuer
 */
public final class Sources
{

    /**
     * Private no-arg constructor.
     */
    private Sources()
    {
        // empty
    }


    /**
     * Create and return a new char source for the specified input stream.
     *
     * @since 1.1
     * @param inputStream input stream, must not be null
     * @return a new char source for the specified input stream
     */
    public static CharSource inputStreamCharSource(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.inputStreamReader(inputStream);
                }
            };
    }

    /**
     * Create and return a new char source for the specified compressed input stream,
     * autodetecting the compression type from the first few bytes of the stream.
     *
     * @since 1.1
     * @param inputStream input stream, must not be null
     * @return a new char source for the specified input stream
     */
    public static CharSource compressedInputStreamCharSource(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.compressedInputStreamReader(inputStream);
                }
            };
    }

    /**
     * Create and return a new block compressed gzip (BGZF) char source for the specified input stream.
     *
     * @since 1.2
     * @param inputStream input stream, must not be null
     * @return a new block compressed gzip (BGZF) char source for the specified input stream
     */
    public static CharSource bgzfInputStreamCharSource(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.bgzfInputStreamReader(inputStream);
                }
            };
    }

    /**
     * Create and return a new gzip compressed char source for the specified input stream.
     *
     * @since 1.3
     * @param inputStream input stream, must not be null
     * @return a new gzip compressed char source for the specified input stream
     */
    public static CharSource gzipInputStreamCharSource(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.gzipInputStreamReader(inputStream);
                }
            };
    }

    /**
     * Create and return a new bzip2 compressed char source for the specified input stream.
     *
     * @since 1.3
     * @param inputStream input stream, must not be null
     * @return a new bzip2 compressed char source for the specified input stream
     */
    public static CharSource bzip2InputStreamCharSource(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.bzip2InputStreamReader(inputStream);
                }
            };
    }

    /**
     * Create and return a new block compressed gzip (BGZF) char source for the specified file.
     *
     * @since 1.3
     * @param file file, must not be null
     * @return a new block compressed gzip (BGZF) char source for the specified file
     */
    public static CharSource bgzfFileCharSource(final File file)
    {
        checkNotNull(file);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.bgzfFileReader(file);
                }
            };
    }

    /**
     * Create and return a new gzip compressed char source for the specified file.
     *
     * @since 1.3
     * @param file file, must not be null
     * @return a new gzip compressed char source for the specified file
     */
    public static CharSource gzipFileCharSource(final File file)
    {
        checkNotNull(file);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.gzipFileReader(file);
                }
            };
    }

    /**
     * Create and return a new bzip2 compressed char source for the specified file.
     *
     * @since 1.3
     * @param file file, must not be null
     * @return a new bzip2 compressed char source for the specified file
     */
    public static CharSource bzip2FileCharSource(final File file)
    {
        checkNotNull(file);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.bzip2FileReader(file);
                }
            };
    }

    /**
     * Create and return a new Zstandard (zstd) compressed char source for the specified input stream.
     *
     * @since 1.4
     * @param inputStream input stream, must not be null
     * @return a new Zstandard (zstd) compressed char source for the specified input stream
     */
    public static CharSource zstdInputStreamCharSource(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.zstdInputStreamReader(inputStream);
                }
            };
    }

    /**
     * Create and return a new Zstandard (zstd) compressed char source for the specified file.
     *
     * @since 1.4
     * @param file file, must not be null
     * @return a new Zstandard (zstd) compressed char source for the specified file
     */
    public static CharSource zstdFileCharSource(final File file)
    {
        checkNotNull(file);
        return new CharSource()
            {
                @Override
                public Reader openStream() throws IOException
                {
                    return Readers.zstdFileReader(file);
                }
            };
    }

    /**
     * Create and return a new char source with support for bgzf, gzip, bzip2, or zstd compression for
     * the specified file or <code>stdin</code> if the file is null.  Defaults to <code>UTF-8</code> charset.
     *
     * @param file file, if any
     * @return a new char source with support for bgzf, gzip, bzip2, or zstd compression for
     *    the specified file or <code>stdin</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static CharSource charSource(@Nullable final File file) throws IOException
    {
        return charSource(file, Charset.forName("UTF-8"));
    }

    /**
     * Create and return a new char source with support for bgzf, gzip, bzip2, or zstd compression for
     * the specified file or <code>stdin</code> if the file is null.
     *
     * @param file file, if any
     * @param charset charset, must not be null
     * @return a new char source with support for bgzf, gzip, bzip2, or zstd compression for
     *    the specified file or <code>stdin</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static CharSource charSource(@Nullable final File file, final Charset charset) throws IOException
    {
        checkNotNull(charset);
        if (file == null)
        {
            return compressedInputStreamCharSource(System.in);
        }
        else if (isZstdFile(file))
        {
            return zstdFileCharSource(file);
        }
        else if (isBgzfFile(file))
        {
            return bgzfFileCharSource(file);
        }
        else if (isGzipFile(file))
        {
            return gzipFileCharSource(file);
        }
        else if (isBzip2File(file))
        {
            return bzip2FileCharSource(file);
        }
        return Files.asCharSource(file, charset);
    }
}
