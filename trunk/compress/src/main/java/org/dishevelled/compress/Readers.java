/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014 held jointly by the individual authors.

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

import static org.dishevelled.compress.Compress.isBzip2File;
import static org.dishevelled.compress.Compress.isGzipFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import javax.annotation.Nullable;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * File and input stream readers with support for gzip and bzip2 compression.
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
     * Create and return a new buffered reader with support for gzip or bzip2 compression for the specified file
     * or <code>stdin</code> if the file is null.
     *
     * @param file file, if any
     * @return a new buffered reader with support for gzip or bzip2 compression for the specified file
     *    or <code>stdin</code> if the file is null
     * @throws IOException if an I/O error occurs
     */
    public static BufferedReader reader(@Nullable final File file) throws IOException
    {
        if (file == null)
        {
            return new BufferedReader(new InputStreamReader(System.in));
        }
        else if (isGzipFile(file))
        {
            return new BufferedReader(new InputStreamReader(new GzipCompressorInputStream(new FileInputStream(file))));
        }
        else if (isBzip2File(file))
        {
            return new BufferedReader(new InputStreamReader(new BZip2CompressorInputStream(new FileInputStream(file))));
        }
        return new BufferedReader(new FileReader(file));
    }
}
