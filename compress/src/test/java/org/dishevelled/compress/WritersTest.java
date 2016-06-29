/*

    dsh-compress  Compression utility classes.
    Copyright (c) 2014-2015 held jointly by the individual authors.

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

import static org.dishevelled.compress.Writers.bzip2OutputStreamWriter;
import static org.dishevelled.compress.Writers.gzipOutputStreamWriter;
import static org.dishevelled.compress.Writers.writer;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.junit.Test;

/**
 * Unit test for Writers.
 *
 * @author  Michael Heuer
 */
public final class WritersTest
{

    @Test
    public void testWriterNullFile() throws IOException
    {
        try (PrintWriter writer = writer(null))
        {
            assertNotNull(writer);
        }
    }

    @Test
    public void testWriterFile() throws IOException
    {
        File file = File.createTempFile("writersTest", ".txt");
        try (PrintWriter writer = writer(file))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterBgzFile() throws IOException
    {
        File file = File.createTempFile("writersTest", ".bgz");
        try (PrintWriter writer = writer(file))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterBgzfFile() throws IOException
    {
        File file = File.createTempFile("writersTest", ".bgzf");
        try (PrintWriter writer = writer(file))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterGzipFile() throws IOException
    {
        File file = File.createTempFile("writersTest", ".gz");
        try (PrintWriter writer = writer(file))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterBzip2File() throws IOException
    {
        File file = File.createTempFile("writersTest", ".bz2");
        try (PrintWriter writer = writer(file))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterNullFileAppend() throws IOException
    {
        try (PrintWriter writer = writer(null, true))
        {
            assertNotNull(writer);
        }
    }

    @Test
    public void testWriterFileAppend() throws IOException
    {
        File file = File.createTempFile("writersTest", ".txt");
        try (PrintWriter writer = writer(file, true))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterBgzfFileAppend() throws IOException
    {
        File file = File.createTempFile("writersTest", ".bgzf");
        try (PrintWriter writer = writer(file, true))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterGzipFileAppend() throws IOException
    {
        File file = File.createTempFile("writersTest", ".gz");
        try (PrintWriter writer = writer(file, true))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testWriterBzip2FileAppend() throws IOException
    {
        File file = File.createTempFile("writersTest", ".bz2");
        try (PrintWriter writer = writer(file, true))
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testGzipOutputStreamWriterNullOutputStream() throws IOException
    {
        gzipOutputStreamWriter(null);
    }

    @Test
    public void testGzipOutputStreamWriter() throws IOException
    {
        try (OutputStream outputStream = new ByteArrayOutputStream(); PrintWriter writer = gzipOutputStreamWriter(outputStream))
        {
            assertNotNull(writer);
        }
    }

    @Test(expected=NullPointerException.class)
    public void testBzip2OutputStreamWriterNullOutputStream() throws IOException
    {
        bzip2OutputStreamWriter(null);
    }

    @Test
    public void testBzip2OutputStreamWriter() throws IOException
    {
        try (OutputStream outputStream = new ByteArrayOutputStream(); PrintWriter writer = bzip2OutputStreamWriter(outputStream))
        {
            assertNotNull(writer);
        }
    }
}
