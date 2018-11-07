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

import static org.dishevelled.compress.Readers.bgzfFileReader;
import static org.dishevelled.compress.Readers.bgzfInputStreamReader;
import static org.dishevelled.compress.Readers.bzip2FileReader;
import static org.dishevelled.compress.Readers.bzip2InputStreamReader;
import static org.dishevelled.compress.Readers.compressedFileReader;
import static org.dishevelled.compress.Readers.compressedInputStreamReader;
import static org.dishevelled.compress.Readers.gzipFileReader;
import static org.dishevelled.compress.Readers.gzipInputStreamReader;
import static org.dishevelled.compress.Readers.inputStreamReader;
import static org.dishevelled.compress.Readers.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

import com.google.common.io.Resources;

import org.junit.Test;

/**
 * Unit test for Readers.
 *
 * @author  Michael Heuer
 */
public final class ReadersTest
{

    /* hangs waiting on System.in
    @Test
    public void testReaderNullFile() throws IOException
    {
        try (BufferedReader reader = reader(null))
        {
            assertNotNull(reader);
        }
    }
    */

    @Test
    public void testReaderFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        try (BufferedReader reader = reader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testReaderBgzFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".bgz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bgz"), outputStream);
        }
        try (BufferedReader reader = reader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testReaderBgzfFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".bgzf");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bgzf"), outputStream);
        }
        try (BufferedReader reader = reader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testReaderBgzfFileWithGzFileExtension() throws IOException
    {
        File file = File.createTempFile("readersTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bgzf.gz"), outputStream);
        }
        try (BufferedReader reader = reader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testReaderBzip2File() throws IOException
    {
        File file = File.createTempFile("readersTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (BufferedReader reader = reader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testGzipFileReaderNullFile() throws IOException
    {
        gzipFileReader(null);
    }

    @Test
    public void testGzipFileReader() throws IOException
    {
        File file = File.createTempFile("readersTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (BufferedReader reader = gzipFileReader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testGzipInputStreamReaderNullInputStream() throws IOException
    {
        gzipInputStreamReader(null);
    }

    @Test
    public void testGzipInputStreamReader() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt.gz"); BufferedReader reader = gzipInputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    @Test(expected=NullPointerException.class)
    public void testBzip2FileReaderNullFile() throws IOException
    {
        bzip2FileReader(null);
    }

    @Test
    public void testBzip2FileReader() throws IOException
    {
        File file = File.createTempFile("readersTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (BufferedReader reader = bzip2FileReader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testBzip2InputStreamReaderNullInputStream() throws IOException
    {
        bzip2InputStreamReader(null);
    }

    @Test
    public void testBzip2InputStreamReader() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt.bz2"); BufferedReader reader = bzip2InputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    @Test(expected=NullPointerException.class)
    public void testInputStreamReaderNullInputStream() throws IOException
    {
        inputStreamReader(null);
    }

    @Test
    public void testInputStreamReader() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt"); BufferedReader reader = inputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    @Test(expected=NullPointerException.class)
    public void testCompressedFileReaderNullFile() throws IOException
    {
        compressedFileReader(null);
    }

    @Test
    public void testCompressedFileReaderPlainText() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt"), outputStream);
        }
        try (BufferedReader reader = compressedFileReader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedFileReaderBgzf() throws IOException
    {
        File file = File.createTempFile("readersTest", ".bgzf");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bgzf"), outputStream);
        }
        try (BufferedReader reader = compressedFileReader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedFileReaderBgzfWithGzFileExtension() throws IOException
    {
        File file = File.createTempFile("readersTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bgzf.gz"), outputStream);
        }
        try (BufferedReader reader = compressedFileReader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedFileReaderGzip() throws IOException
    {
        File file = File.createTempFile("readersTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (BufferedReader reader = compressedFileReader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedFileReaderBzip2() throws IOException
    {
        File file = File.createTempFile("readersTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(ReadersTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (BufferedReader reader = bzip2FileReader(file))
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testCompressedInputStreamReaderNullInputStream() throws IOException
    {
        compressedInputStreamReader(null);
    }

    @Test
    public void testCompressedInputStreamReaderPlainText() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt"); BufferedReader reader = compressedInputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    @Test
    public void testCompressedInputStreamReaderBgzf() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt.bgzf"); BufferedReader reader = compressedInputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    @Test
    public void testCompressedInputStreamReaderBgzfWithGzFileExtension() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt.bgzf.gz"); BufferedReader reader = compressedInputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    @Test
    public void testCompressedInputStreamReaderGzip() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt.gz"); BufferedReader reader = compressedInputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    @Test
    public void testCompressedInputStreamReaderBzip2() throws IOException
    {
        try (InputStream inputStream = ReadersTest.class.getResourceAsStream("example.txt.bz2"); BufferedReader reader = compressedInputStreamReader(inputStream))
        {
            assertValidReader(reader);
        }
    }

    static void assertValidReader(final BufferedReader reader) throws IOException {
        assertNotNull(reader);

        int lineNumber = 0;
        for (String line = null; (line = reader.readLine()) != null; ) {
            assertTrue(line.startsWith("example"));
            lineNumber++;
        }
        assertEquals(5, lineNumber);
    }
}