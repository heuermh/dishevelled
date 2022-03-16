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

import static org.dishevelled.compress.Sources.bgzfInputStreamCharSource;
import static org.dishevelled.compress.Sources.bzip2InputStreamCharSource;
import static org.dishevelled.compress.Sources.charSource;
import static org.dishevelled.compress.Sources.compressedInputStreamCharSource;
import static org.dishevelled.compress.Sources.inputStreamCharSource;
import static org.dishevelled.compress.Sources.gzipInputStreamCharSource;
import static org.dishevelled.compress.Sources.xzInputStreamCharSource;
import static org.dishevelled.compress.Sources.zstdInputStreamCharSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Resources;

import org.junit.Test;

/**
 * Unit test for Sources.
 *
 * @author  Michael Heuer
 */
public final class SourcesTest
{

    /* hangs waiting on System.in
    @Test
    public void testCharSourceNullFile() throws IOException
    {
        try (Reader reader = charSource(null).openStream())
        {
            assertNotNull(reader);
        }
    }
    */

    @Test
    public void testCharSourceFile() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = charSource(file).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCharSourceBgzfFile() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bgz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bgz"), outputStream);
        }
        try (Reader reader = charSource(file).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCharSourceGzipFile() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (Reader reader = charSource(file).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCharSourceBzip2File() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (Reader reader = charSource(file).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCharSourceXzFile() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".xz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.xz"), outputStream);
        }
        try (Reader reader = charSource(file).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCharSourceZstdFile() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".zst");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.zst"), outputStream);
        }
        try (Reader reader = charSource(file).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testCharSourceNullCharset() throws IOException
    {
        charSource(null, null);
    }

    @Test
    public void testCharSourceCharsetFile() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = charSource(file, StandardCharsets.UTF_8).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testBgzfInputStreamCharSourceNullInputStream() throws IOException
    {
        bgzfInputStreamCharSource(null);
    }

    @Test
    public void testBgzfInputStreamCharSource() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bgz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bgz"), outputStream);
        }
        try (Reader reader = bgzfInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    //@Test(expected=IOException.class)  late thrown exception, unfortunately
    @Test(expected=htsjdk.samtools.FileTruncatedException.class)
    public void testBgzfInputStreamCharSourceNoCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = bgzfInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    //@Test(expected=IOException.class)  late thrown exception, unfortunately
    @Test(expected=htsjdk.samtools.FileTruncatedException.class)
    public void testBgzfInputStreamCharSourceWrongCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (Reader reader = bgzfInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testGzipInputStreamCharSourceNullInputStream() throws IOException
    {
        gzipInputStreamCharSource(null);
    }

    @Test
    public void testGzipInputStreamCharSource() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (Reader reader = gzipInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testGzipInputStreamCharSourceNoCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = gzipInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testGzipInputStreamCharSourceWrongCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (Reader reader = gzipInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testBzip2InputStreamCharSourceNullInputStream() throws IOException
    {
        bzip2InputStreamCharSource(null);
    }

    @Test
    public void testBzip2InputStreamCharSource() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (Reader reader = bzip2InputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testBzip2InputStreamCharSourceNoCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = bzip2InputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testBzip2InputStreamCharSourceWrongCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (Reader reader = bzip2InputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testXzInputStreamCharSourceNullInputStream() throws IOException
    {
        xzInputStreamCharSource(null);
    }

    @Test
    public void testXzInputStreamCharSource() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".xz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.xz"), outputStream);
        }
        try (Reader reader = xzInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testXzInputStreamCharSourceNoCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = xzInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testXzInputStreamCharSourceWrongCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (Reader reader = xzInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testZstdInputStreamCharSourceNullInputStream() throws IOException
    {
        zstdInputStreamCharSource(null);
    }

    @Test
    public void testZstdInputStreamCharSource() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".zst");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.zst"), outputStream);
        }
        try (Reader reader = zstdInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testZstdInputStreamCharSourceNoCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = zstdInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=IOException.class)
    public void testZstdInputStreamCharSourceWrongCompression() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (Reader reader = zstdInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testInputStreamCharSourceNullInputStream() throws IOException
    {
        inputStreamCharSource(null);
    }

    @Test
    public void testInputStreamCharSource() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = inputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testCompressedInputStreamCharSourceNullInputStream() throws IOException
    {
        compressedInputStreamCharSource(null);
    }

    @Test
    public void testCompressedInputStreamCharSourcePlainText() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt"), outputStream);
        }
        try (Reader reader = compressedInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedInputStreamCharSourceBgzf() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt.bgz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bgz"), outputStream);
        }
        try (Reader reader = compressedInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedInputStreamCharSourceGzip() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt.gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (Reader reader = compressedInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedInputStreamCharSourceBzip2() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt.bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (Reader reader = compressedInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedInputStreamCharSourceXz() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt.xz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.xz"), outputStream);
        }
        try (Reader reader = compressedInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    @Test
    public void testCompressedInputStreamCharSourceZstd() throws IOException
    {
        File file = File.createTempFile("charSourcesTest", ".txt.zst");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.zst"), outputStream);
        }
        try (Reader reader = compressedInputStreamCharSource(new FileInputStream(file)).openStream())
        {
            assertValidReader(reader);
        }
        file.delete();
    }

    static void assertValidReader(final Reader reader) throws IOException {
        assertNotNull(reader);

        BufferedReader bufferedReader = new BufferedReader(reader);
        int lineNumber = 0;
        for (String line = null; (line = bufferedReader.readLine()) != null; ) {
            assertTrue(line.startsWith("example"));
            lineNumber++;
        }
        assertEquals(5, lineNumber);
    }
}
