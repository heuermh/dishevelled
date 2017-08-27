/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2017 held jointly by the individual authors.

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

import com.google.common.io.Files;

import htsjdk.samtools.util.BlockCompressedInputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2Utils;

import org.apache.commons.compress.compressors.gzip.GzipUtils;

/**
 * Compression utility methods.
 *
 * @author  Michael Heuer
 */
final class Compress
{

    /**
     * Private no-arg constructor.
     */
    private Compress()
    {
        // empty
    }


    /**
     * Return true if the specified file is a block compressed gzip (BGZF) file.
     *
     * @param file file
     * @return true if the specified file is a block compressed gzip (BGZF) file
     */
    static boolean isBgzfFile(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        String fileExtension = Files.getFileExtension(file.getName());
        if (fileExtension.equals("bgzf") || fileExtension.equals("bgz"))
        {
            return true;
        }
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
    static boolean isBgzfInputStream(final InputStream inputStream)
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
     * Return true if the specified file is a gzip file.  Block compressed gzip (BGZF)
     * files are also gzip files, so <code>isBgzfFile(File)</code> should be called before
     * this method.
     *
     * @param file file
     * @return true if the specified file is a gzip file
     */
    static boolean isGzipFile(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        return GzipUtils.isCompressedFilename(file.getName());
    }

    /**
     * Return true if the specified file is a bzip2 file.
     *
     * @param file file
     * @return true if the specified file is a bzip2 file
     */
    static boolean isBzip2File(@Nullable final File file)
    {
        if (file == null)
        {
            return false;
        }
        return BZip2Utils.isCompressedFilename(file.getName());
    }
}
