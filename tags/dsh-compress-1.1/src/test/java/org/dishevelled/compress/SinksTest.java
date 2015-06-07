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

import static org.dishevelled.compress.Sinks.bzip2OutputStreamCharSink;
import static org.dishevelled.compress.Sinks.charSink;
import static org.dishevelled.compress.Sinks.gzipOutputStreamCharSink;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;

import java.nio.charset.Charset;

import org.junit.Test;

/**
 * Unit test for Sinks.
 *
 * @author  Michael Heuer
 */
public final class SinksTest
{

    @Test
    public void testCharSinkNullFile() throws IOException
    {
        try (Writer writer = charSink(null).openStream())
        {
            assertNotNull(writer);
        }
    }

    @Test
    public void testCharSinkFile() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".txt");
        try (Writer writer = charSink(file).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testCharSinkGzipFile() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".gz");
        try (Writer writer = charSink(file).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testCharSinkBzip2File() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".bz2");
        try (Writer writer = charSink(file).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testCharSinkNullFileAppend() throws IOException
    {
        try (Writer writer = charSink(null, true).openStream())
        {
            assertNotNull(writer);
        }
    }

    @Test
    public void testCharSinkFileAppend() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".txt");
        try (Writer writer = charSink(file, true).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testCharSinkGzipFileAppend() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".gz");
        try (Writer writer = charSink(file, true).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test
    public void testCharSinkBzip2FileAppend() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".bz2");
        try (Writer writer = charSink(file, true).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testCharSinkNullCharset() throws IOException
    {
        charSink(null, null, true);
    }

    @Test
    public void testCharSinkCharsetFileAppend() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".txt");
        try (Writer writer = charSink(file, Charset.forName("UTF-8"), true).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testGzipOutputStreamCharSinkNullOutputStream() throws IOException
    {
        gzipOutputStreamCharSink(null);
    }

    @Test
    public void testGzipOutputStreamCharSink() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".gz");
        try (Writer writer = gzipOutputStreamCharSink(new FileOutputStream(file)).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }

    @Test(expected=NullPointerException.class)
    public void testBzip2OutputStreamCharSinkNullOutputStream() throws IOException
    {
        bzip2OutputStreamCharSink(null);
    }

    @Test
    public void testBzip2OutputStreamCharSink() throws IOException
    {
        File file = File.createTempFile("charSinksTest", ".bz2");
        try (Writer writer = bzip2OutputStreamCharSink(new FileOutputStream(file)).openStream())
        {
            assertNotNull(writer);
        }
        file.delete();
    }
}
