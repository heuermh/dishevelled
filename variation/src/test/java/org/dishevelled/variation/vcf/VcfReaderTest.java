/*

    dsh-variation  Variation.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.vcf;

import static org.dishevelled.variation.vcf.VcfReader.parse;
import static org.dishevelled.variation.vcf.VcfReader.read;
import static org.dishevelled.variation.vcf.VcfReader.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.zip.GZIPInputStream;

import com.google.common.io.InputSupplier;

import org.junit.Test;

/**
 * Unit test for VcfReader.
 */
public final class VcfReaderTest
{
    private InputSupplier<StringReader> inputSupplier = new InputSupplier<StringReader>()
        {
            @Override
            public StringReader getInput() throws IOException
            {
                return new StringReader("foo");
            }
        };
    private VcfParseListener parseListener = new VcfParseAdapter();
    private VcfStreamListener streamListener = new VcfStreamListener()
        {
            @Override
            public void record(final VcfRecord record)
            {
                // empty
            }
        };

    @Test(expected=NullPointerException.class)
    public void testParseNullSupplier() throws IOException
    {
        parse(null, parseListener);
    }

    @Test(expected=NullPointerException.class)
    public void testParseNullListener() throws IOException
    {
        parse(inputSupplier, null);
    }

    @Test(expected=NullPointerException.class)
    public void testStreamNullSupplier() throws IOException
    {
        stream(null, streamListener);
    }

    @Test(expected=NullPointerException.class)
    public void testStreamNullListener() throws IOException
    {
        stream(inputSupplier, null);
    }

    @Test(expected=NullPointerException.class)
    public void testReadNullFile() throws IOException
    {
        read((File) null);
    }

    @Test(expected=NullPointerException.class)
    public void testReadNullURL() throws IOException
    {
        read((URL) null);
    }

    @Test(expected=NullPointerException.class)
    public void testReadNullInputStream() throws IOException
    {
        read((InputStream) null);
    }

    @Test
    public void testReadInputStream() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vcf");
        assertNotNull(inputStream);

        int count = 0;
        for (VcfRecord record : read(inputStream))
        {
            assertNotNull(record);
            count++;
        }
        assertEquals(1, count);
    }

    @Test
    public void testReadGzInputStream() throws IOException
    {
        InputStream gzInputStream = new GZIPInputStream(getClass().getResourceAsStream("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp.vcf.gz"));
        assertNotNull(gzInputStream);

        int count = 0;
        for (VcfRecord record : read(gzInputStream))
        {
            assertNotNull(record);
            count++;
        }
        assertEquals(1, count);
    }
}