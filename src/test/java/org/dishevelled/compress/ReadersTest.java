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

import static org.dishevelled.compress.Readers.bzip2InputStreamReader;
import static org.dishevelled.compress.Readers.gzipInputStreamReader;
import static org.dishevelled.compress.Readers.reader;

import static org.junit.Assert.assertNotNull;

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

    @Test
    public void testReaderNullFile() throws IOException
    {
        try (BufferedReader reader = reader(null))
        {
            assertNotNull(reader);
        }
    }

    @Test
    public void testReaderFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".txt");
        try (BufferedReader reader = reader(file))
        {
            assertNotNull(reader);
        }
        file.delete();
    }

    @Test
    public void testReaderGzipFile() throws IOException
    {
        File file = File.createTempFile("readersTest", ".gz");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.gz"), outputStream);
        }
        try (BufferedReader reader = reader(file))
        {
            assertNotNull(reader);
        }
        file.delete();
    }

    @Test
    public void testReaderBzip2File() throws IOException
    {
        File file = File.createTempFile("readersTest", ".bz2");
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            Resources.copy(SourcesTest.class.getResource("example.txt.bz2"), outputStream);
        }
        try (BufferedReader reader = reader(file))
        {
            assertNotNull(reader);
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
            assertNotNull(reader);
        }
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
            assertNotNull(reader);
        }
    }
}
