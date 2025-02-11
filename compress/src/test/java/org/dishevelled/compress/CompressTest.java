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

import static org.dishevelled.compress.Compress.isBgzfFilename;
import static org.dishevelled.compress.Compress.isBgzfFile;
import static org.dishevelled.compress.Compress.isBgzfInputStream;
import static org.dishevelled.compress.Compress.isBzip2Filename;
import static org.dishevelled.compress.Compress.isBzip2File;
import static org.dishevelled.compress.Compress.isBzip2InputStream;
import static org.dishevelled.compress.Compress.isGzipFilename;
import static org.dishevelled.compress.Compress.isGzipFile;
import static org.dishevelled.compress.Compress.isGzipInputStream;
import static org.dishevelled.compress.Compress.isXzFilename;
import static org.dishevelled.compress.Compress.isXzFile;
import static org.dishevelled.compress.Compress.isXzInputStream;
import static org.dishevelled.compress.Compress.isZstdFilename;
import static org.dishevelled.compress.Compress.isZstdFile;
import static org.dishevelled.compress.Compress.isZstdInputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.common.io.Resources;

import org.junit.Test;

/**
 * Unit test for Compress.
 *
 * @author  Michael Heuer
 */
public final class CompressTest
{

    @Test
    public void testIsBgzfFilename()
    {
        assertTrue(isBgzfFilename("example.txt.bgz"));
        assertTrue(isBgzfFilename("example.txt.bgzf"));
        assertFalse(isBgzfFilename("example.txt"));
    }

    @Test
    public void testIsBgzfFile()
    {
        assertFalse(isBgzfFile(null));
        assertTrue(isBgzfFile(new File("example.txt.bgz")));
        assertTrue(isBgzfFile(new File("example.txt.bgzf")));
    }

    @Test
    public void testIsBgzfFileTextFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        assertFalse(isBgzfFile(file));
        file.delete();
    }

    @Test
    public void testIsBgzfFileGzipFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.gz"), outputStream);
        }
        assertFalse(isBgzfFile(file));
        file.delete();
    }

    @Test
    public void testIsBgzfFileBgzfFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.bgz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bgz"), outputStream);
        }
        assertTrue(isBgzfFile(file));
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testIsBgzfInputStreamNullInputStream()
    {
        isBgzfInputStream(null);
    }

    @Test
    public void testIsBgzfInputStream() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.bgz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bgz"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertTrue(isBgzfInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsBgzfInputStreamNoCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isBgzfInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsBgzfInputStreamWrongCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isBgzfInputStream(inputStream));
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testIsGzipInputStreamNullInputStream()
    {
        isGzipInputStream(null);
    }

    @Test
    public void testIsGzipInputStream() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertTrue(isGzipInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsGzipInputStreamNoCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isGzipInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsGzipInputStreamWrongCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isGzipInputStream(inputStream));
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testIsBzip2InputStreamNullInputStream()
    {
        isBzip2InputStream(null);
    }

    @Test
    public void testIsBzip2InputStream() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertTrue(isBzip2InputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsBzip2InputStreamNoCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isBzip2InputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsBzip2InputStreamWrongCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isBzip2InputStream(inputStream));
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testIsZstdInputStreamNullInputStream()
    {
        isZstdInputStream(null);
    }

    @Test
    public void testIsZstdInputStream() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.zst");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.zst"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertTrue(isZstdInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsZstdInputStreamNoCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isZstdInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsZstdInputStreamWrongCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isZstdInputStream(inputStream));
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testIsXzInputStreamNullInputStream()
    {
        isXzInputStream(null);
    }

    @Test
    public void testIsXzInputStream() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.xz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.xz"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertTrue(isXzInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsXzInputStreamNoCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isXzInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsXzInputStreamWrongCompression() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt.bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (FileInputStream inputStream = new FileInputStream(file))
        {
            assertFalse(isXzInputStream(inputStream));
        }
        file.delete();
    }

    @Test
    public void testIsGzipFilename()
    {
        assertTrue(isGzipFilename("example.txt.gz"));
        assertFalse(isGzipFilename("example.txt"));
    }

    @Test
    public void testIsGzipFile()
    {
        assertFalse(isGzipFile(null));
        assertTrue(isGzipFile(new File("example.txt.gz")));
        assertFalse(isGzipFile(new File("example.txt")));
    }

    @Test
    public void testIsBzip2Filename()
    {
        assertTrue(isBzip2Filename("example.txt.bz2"));
        assertFalse(isBzip2Filename("example.txt"));
    }

    @Test
    public void testIsBzip2File()
    {
        assertFalse(isBzip2File(null));
        assertTrue(isBzip2File(new File("example.txt.bz2")));
        assertFalse(isBzip2File(new File("example.txt")));
    }

    @Test
    public void testIsXzFilename()
    {
        assertTrue(isXzFilename("example.txt.xz"));
        assertFalse(isXzFilename("example.txt"));
    }

    @Test
    public void testIsXzFile()
    {
        assertFalse(isXzFile(null));
        assertTrue(isXzFile(new File("example.txt.xz")));
        assertFalse(isXzFile(new File("example.txt")));
    }

    @Test
    public void testIsZstdFilename()
    {
        assertTrue(isZstdFilename("example.txt.zst"));
        assertTrue(isZstdFilename("example.txt.zstd"));
        assertFalse(isZstdFilename("example.txt"));
    }

    @Test
    public void testIsZstdFile()
    {
        assertFalse(isZstdFile(null));
        assertTrue(isZstdFile(new File("example.txt.zst")));
        assertTrue(isZstdFile(new File("example.txt.zstd")));
        assertFalse(isZstdFile(new File("example.txt")));
    }
}
