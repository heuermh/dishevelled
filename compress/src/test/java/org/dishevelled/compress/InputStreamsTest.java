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

import static org.dishevelled.compress.InputStreams.compressedFileInputStream;
import static org.dishevelled.compress.InputStreams.compressedInputStream;
import static org.dishevelled.compress.InputStreams.bgzfFileInputStream;
import static org.dishevelled.compress.InputStreams.bgzfInputStream;
import static org.dishevelled.compress.InputStreams.gzipFileInputStream;
import static org.dishevelled.compress.InputStreams.gzipInputStream;
import static org.dishevelled.compress.InputStreams.bzip2FileInputStream;
import static org.dishevelled.compress.InputStreams.bzip2InputStream;
import static org.dishevelled.compress.InputStreams.xzFileInputStream;
import static org.dishevelled.compress.InputStreams.xzInputStream;
import static org.dishevelled.compress.InputStreams.zstdFileInputStream;
import static org.dishevelled.compress.InputStreams.zstdInputStream;
import static org.dishevelled.compress.InputStreams.inputStream;

import org.junit.Test;

/**
 * Unit test for InputStreams.
 *
 * @author  Michael Heuer
 */
public final class InputStreamsTest
{

    @Test(expected=NullPointerException.class)
    public void testCompressedFileInputStreamNullFile() throws Exception
    {
        compressedFileInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testCompressedInputStreamNullInputStream() throws Exception
    {
        compressedInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testBgzfFileInputStreamNullFile() throws Exception
    {
        bgzfFileInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testBgzfInputStreamNullInputStream() throws Exception
    {
        bgzfInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testGzipFileInputStreamNullFile() throws Exception
    {
        gzipFileInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testGzipInputStreamNullInputStream() throws Exception
    {
        compressedInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testBzip2FileInputStreamNullFile() throws Exception
    {
        bzip2FileInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testBzip2InputStreamNullInputStream() throws Exception
    {
        bzip2InputStream(null);
    }
    
    @Test(expected=NullPointerException.class)
    public void testXzFileInputStreamNullFile() throws Exception
    {
        xzFileInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testXzInputStreamNullInputStream() throws Exception
    {
        xzInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testZstdFileInputStreamNullFile() throws Exception
    {
        zstdFileInputStream(null);
    }

    @Test(expected=NullPointerException.class)
    public void testZstdInputStreamNullInputStream() throws Exception
    {
        zstdInputStream(null);
    }
}
