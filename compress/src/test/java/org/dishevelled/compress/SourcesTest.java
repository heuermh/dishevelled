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

import static org.dishevelled.compress.Sources.bzip2InputStreamCharSource;
import static org.dishevelled.compress.Sources.charSource;
import static org.dishevelled.compress.Sources.gzipInputStreamCharSource;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;

import java.nio.charset.Charset;

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
        try (Reader reader = charSource(file).openStream())
        {
            assertNotNull(reader);
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
            assertNotNull(reader);
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
            assertNotNull(reader);
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
        try (Reader reader = charSource(file, Charset.forName("UTF-8")).openStream())
        {
            assertNotNull(reader);
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
            assertNotNull(reader);
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
            assertNotNull(reader);
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
            assertNotNull(reader);
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
            assertNotNull(reader);
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
            assertNotNull(reader);
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
            assertNotNull(reader);
        }
        file.delete();
    }
}
