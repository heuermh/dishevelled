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

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.compress.compressors.FileNameUtil;

/**
 * Utility code for the Zstandard (zstd) compression format.
 *
 * @since 1.4
 * @author  Michael Heuer
 */
public abstract class ZstdUtils
{
    /** Static FileNameUtil instance. */
    private static final FileNameUtil FILE_NAME_UTIL;

    static
    {
        final Map<String, String> uncompressSuffix = new LinkedHashMap<String, String>();
        uncompressSuffix.put(".zst", "");
        uncompressSuffix.put(".zstd", "");
        FILE_NAME_UTIL = new FileNameUtil(uncompressSuffix, ".zst");
    }


    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ZstdUtils()
    {
        // empty
    }


    /**
     * Detects common Zstandard (zst) suffixes in the given filename.
     *
     * @param filename name of a file
     * @return {@code true} if the filename has a common Zstandard (zstd) suffix,
     *    {@code false} otherwise
     */
    public static boolean isCompressedFilename(final String filename)
    {
        return FILE_NAME_UTIL.isCompressedFilename(filename);
    }

    /**
     * Maps the given name of a Zstandard (zstd) file to the name that the
     * file should have after uncompression. Commonly used file type specific
     * suffixes like ".zst" or ".zstd" are automatically detected and
     * correctly mapped. Any filenames with the generic ".zst" suffix
     * (or any other generic zstd suffix) is mapped to a name without that
     * suffix. If no zstd suffix is detected, then the filename is returned
     * unmapped.
     *
     * @param filename name of a file
     * @return name of the corresponding uncompressed file
     */
    public static String getUncompressedFilename(final String filename)
    {
        return FILE_NAME_UTIL.getUncompressedFilename(filename);
    }

    /**
     * Maps the given filename to the name that the file should have after
     * compression with Zstandard (zstd). Currently this method simply appends the suffix
     * ".zst" to the filename, but a future version may implement a more complex
     * mapping if a new widely used naming pattern emerges.
     *
     * @param filename name of a file
     * @return name of the corresponding compressed file
     */
    public static String getCompressedFilename(final String filename)
    {
        return FILE_NAME_UTIL.getCompressedFilename(filename);
    }
}
