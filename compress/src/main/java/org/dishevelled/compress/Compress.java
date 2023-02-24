/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2023 held jointly by the individual authors.

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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import htsjdk.samtools.util.BlockCompressedInputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2Utils;

import org.apache.commons.compress.compressors.gzip.GzipUtils;

import org.apache.commons.compress.compressors.xz.XZUtils;

import org.apache.commons.compress.utils.IOUtils;

/**
 * Compression utility methods.
 *
 * @since 1.3
 * @author  Michael Heuer
 */
public final class Compress
{

    /**
     * Private no-arg constructor.
     */
    private Compress()
    {
        // empty
    }


    /**
     * Return true if the specified file is a block compressed gzip (BGZF) file name.
     *
     * @since 1.3
     * @param fileName file name, must not be null
     * @return true if the specified file is a block compressed gzip (BGZF) file name
     */
    public static boolean isBgzfFilename(final String fileName)
    {
        checkNotNull(fileName);
        return BgzfUtils.isCompressedFilename(fileName);
    }

    /**
     * Return true if the specified file is a block compressed gzip (BGZF) file.
     *
     * @param file file, if any
     * @return true if the specified file is a block compressed gzip (BGZF) file
     */
    public static boolean isBgzfFile(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        if (isBgzfFilename(file.getName()))
        {
            return true;
        }
        // block compressed gzip files may also have .gz file extension, check the stream
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file)))
        {
            return isBgzfInputStream(inputStream);
        }
        catch (IOException e)
        {
            return false;
        }
    }

    /**
     * Return true if the specified file is a block compressed gzip (BGZF) input stream.
     *
     * @param inputStream input stream, must not be null
     * @return true if the specified file is a block compressed gzip (BGZF) input stream
     */
    public static boolean isBgzfInputStream(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        BufferedInputStream bufferedInputStream = inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
        try
        {
            return BlockCompressedInputStream.isValidFile(bufferedInputStream);
        }
        catch (IOException e)
        {
            return false;
        }
    }

    /**
     * Return true if the specified file is a gzip file name.
     *
     * @since 1.3
     * @param fileName file name, must not be null
     * @return true if the specified file is a gzip file name
     */
    public static boolean isGzipFilename(final String fileName)
    {
        checkNotNull(fileName);
        return GzipUtils.isCompressedFilename(fileName);
    }

    /**
     * Return true if the specified file is a gzip file.  Block compressed gzip (BGZF)
     * files are also gzip files, so <code>isBgzfFile(File)</code> should be called before
     * this method.
     *
     * @param file file, if any
     * @return true if the specified file is a gzip file
     */
    public static boolean isGzipFile(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        return isGzipFilename(file.getName());
    }

    /**
     * Return true if the specified file is a gzip input stream.
     *
     * @since 1.3
     * @param inputStream input stream, must not be null
     * @return true if the specified file is a gzip input stream
     */
    public static boolean isGzipInputStream(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        InputStream in = inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream);
        try
        {
            in.mark(2);
            return in.read() == 31 && in.read() == 139;
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            try
            {
                in.reset();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }

    /**
     * Return true if the specified file is a bzip2 file name.
     *
     * @since 1.3
     * @param fileName file name, must not be null
     * @return true if the specified file is a bzip2 file name
     */
    public static boolean isBzip2Filename(final String fileName)
    {
        return BZip2Utils.isCompressedFilename(fileName);
    }

    /**
     * Return true if the specified file is a bzip2 file.
     *
     * @param file file, if any
     * @return true if the specified file is a bzip2 file
     */
    public static boolean isBzip2File(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        return isBzip2Filename(file.getName());
    }

    /**
     * Return true if the specified file is a bzip2 input stream.
     *
     * @since 1.3
     * @param inputStream input stream, must not be null
     * @return true if the specified file is a bzip2 input stream
     */
    public static boolean isBzip2InputStream(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        InputStream in = inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream);
        try
        {
            in.mark(3);
            return in.read() == 'B' && in.read() == 'Z' && in.read() == 'h';
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            try
            {
                in.reset();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }

    /**
     * Return true if the specified file is an XZ file name.
     *
     * @since 1.5
     * @param fileName file name, must not be null
     * @return true if the specified file is an XZ file name
     */
    public static boolean isXzFilename(final String fileName)
    {
        checkNotNull(fileName);
        return XZUtils.isCompressedFilename(fileName);
    }

    /**
     * Return true if the specified file is an XZ file.
     *
     * @since 1.5
     * @param file file, if any
     * @return true if the specified file is a XZ file
     */
    public static boolean isXzFile(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        return isXzFilename(file.getName());
    }

    /**
     * Return true if the specified file is an XZ input stream.
     *
     * @since 1.5
     * @param inputStream input stream, must not be null
     * @return true if the specified file is an XZ input stream
     */
    public static boolean isXzInputStream(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        InputStream in = inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream);
        try
        {
            final byte[] signature = new byte[6];
            inputStream.mark(signature.length);
            int signatureLength = IOUtils.readFully(inputStream, signature);
            return XZUtils.matches(signature, signatureLength);
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            try
            {
                in.reset();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }

    /**
     * Return true if the specified file is a Zstandard (zstd) file name.
     *
     * @since 1.4
     * @param fileName file name, must not be null
     * @return true if the specified file is a Zstandard (zstd) file name
     */
    public static boolean isZstdFilename(final String fileName)
    {
        checkNotNull(fileName);
        return ZstdUtils.isCompressedFilename(fileName);
    }

    /**
     * Return true if the specified file is a Zstandard (zstd) file.
     *
     * @since 1.4
     * @param file file, if any
     * @return true if the specified file is a Zstandard (zstd) file
     */
    public static boolean isZstdFile(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        return isZstdFilename(file.getName());
    }

    /**
     * Return true if the specified file is a Zstandard (zstd) input stream.
     *
     * @since 1.4
     * @param inputStream input stream, must not be null
     * @return true if the specified file is a Zstandard (zstd) input stream
     */
    public static boolean isZstdInputStream(final InputStream inputStream)
    {
        checkNotNull(inputStream);
        InputStream in = inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream);
        try
        {
            final byte[] signature = new byte[12];
            inputStream.mark(signature.length);
            int signatureLength = IOUtils.readFully(inputStream, signature);
            return org.apache.commons.compress.compressors.zstandard.ZstdUtils.matches(signature, signatureLength);
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            try
            {
                in.reset();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }
}
