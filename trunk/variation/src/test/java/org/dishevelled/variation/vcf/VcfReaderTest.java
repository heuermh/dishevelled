/*

    dsh-variation  Variation.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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
import static org.dishevelled.variation.vcf.VcfReader.samples;
import static org.dishevelled.variation.vcf.VcfReader.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.zip.GZIPInputStream;

import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.Resources;

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
        parse((InputSupplier<StringReader>) null, parseListener);
    }

    @Test(expected=NullPointerException.class)
    public void testParseNullListener() throws IOException
    {
        parse(inputSupplier, null);
    }

    @Test(expected=NullPointerException.class)
    public void testStreamNullSupplier() throws IOException
    {
        stream((InputSupplier<StringReader>) null, streamListener);
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
    public void testReadFile() throws IOException
    {
        File file = File.createTempFile("vcfReaderTest", ".vcf");
        Files.write(Resources.toByteArray(getClass().getResource("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vcf")), file);

        int count = 0;
        for (VcfRecord record : read(file))
        {
            assertNotNull(record);

            if (16140370 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs2096606", record.getId()[0]);
                assertEquals("A", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("G", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|1", record.getGt().get("NA19131"));
                assertEquals("1|1", record.getGt().get("NA19223"));
            }
            else if (17512091 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs5992615", record.getId()[0]);
                assertEquals("G", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("A", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|0", record.getGt().get("NA19131"));
                assertEquals("0|0", record.getGt().get("NA19223"));
            }

            count++;
        }
        assertEquals(70, count);
    }

    @Test
    public void testReadURL() throws IOException
    {
        URL url = getClass().getResource("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vcf");

        int count = 0;
        for (VcfRecord record : read(url))
        {
            assertNotNull(record);

            if (16140370 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs2096606", record.getId()[0]);
                assertEquals("A", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("G", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|1", record.getGt().get("NA19131"));
                assertEquals("1|1", record.getGt().get("NA19223"));
            }
            else if (17512091 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs5992615", record.getId()[0]);
                assertEquals("G", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("A", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|0", record.getGt().get("NA19131"));
                assertEquals("0|0", record.getGt().get("NA19223"));
            }

            count++;
        }
        assertEquals(70, count);
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

            if (16140370 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs2096606", record.getId()[0]);
                assertEquals("A", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("G", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|1", record.getGt().get("NA19131"));
                assertEquals("1|1", record.getGt().get("NA19223"));
            }
            else if (17512091 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs5992615", record.getId()[0]);
                assertEquals("G", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("A", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|0", record.getGt().get("NA19131"));
                assertEquals("0|0", record.getGt().get("NA19223"));
            }

            count++;
        }
        assertEquals(70, count);
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

            if (16140370 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs2096606", record.getId()[0]);
                assertEquals("A", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("G", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|1", record.getGt().get("NA19131"));
                assertEquals("1|1", record.getGt().get("NA19223"));
            }
            else if (17512091 == record.getPos())
            {
                assertEquals("22", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("rs5992615", record.getId()[0]);
                assertEquals("G", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("A", record.getAlt()[0]);
                assertEquals(100.0d, record.getQual(), 0.1d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertTrue(record.getInfo().isEmpty());
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("1|0", record.getGt().get("NA19131"));
                assertEquals("0|0", record.getGt().get("NA19223"));
            }

            count++;
        }
        assertEquals(1729, count);
    }

    @Test(expected=NullPointerException.class)
    public void testSamplesNullSupplier() throws IOException
    {
        samples((InputSupplier<StringReader>) null);
    }

    @Test(expected=NullPointerException.class)
    public void testSamplesNullFile() throws IOException
    {
        samples((File) null);
    }

    @Test(expected=NullPointerException.class)
    public void testSamplesNullURL() throws IOException
    {
        samples((URL) null);
    }

    @Test
    public void testSamplesNoMeta() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vcf");
        assertNotNull(inputStream);

        int count = 0;
        for (VcfSample sample : samples(inputStream))
        {
            assertNotNull(sample);
            assertTrue("NA19131".equals(sample.getId()) || "NA19223".equals(sample.getId()));

            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testReadInputStream_gatk_2_6_example_eff() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream("gatk-2.6-example.eff.vcf");
        assertNotNull(inputStream);

        int count = 0;
        for (VcfRecord record : read(inputStream))
        {
            assertNotNull(record);

            if ("rs66469215".equals(record.getId()[0]))
            {
                assertEquals("6", record.getChrom());
                assertEquals(1, record.getId().length);
                assertEquals("C", record.getRef());
                assertEquals(1, record.getAlt().length);
                assertEquals("CA", record.getAlt()[0]);
                assertEquals(3817.73d, record.getQual(), 0.01d);
                assertEquals("PASS", record.getFilter());
                assertNotNull(record.getInfo());
                assertFalse(record.getInfo().isEmpty());
                //assertEquals("true", record.getInfo().get("DB")); // or empty string?
                assertEquals("250", record.getInfo().get("DP"));
                assertNotNull(record.getGt());
                assertFalse(record.getGt().isEmpty());
                assertEquals("0/1", record.getGt().get("Sample1"));
            }
            count++;
        }
        assertEquals(7, count);
    }

    @Test
    public void testSamples() throws IOException
    {
        InputStream inputStream = getClass().getResourceAsStream("samples.vcf");
        assertNotNull(inputStream);

        int count = 0;
        for (VcfSample sample : samples(inputStream))
        {
            assertNotNull(sample);

            if ("Blood".equals(sample.getId()))
            {
                assertNotNull(sample.getGenomes());
                assertEquals(1, sample.getGenomes().length);
                VcfGenome genome = sample.getGenomes()[0];
                assertEquals("Germline", genome.getId());
                assertEquals(1.0d, genome.getMixture(), 0.1d);
                assertEquals("Patient germline genome", genome.getDescription());
            }
            else if ("TissueSample".equals(sample.getId()))
            {
                assertNotNull(sample.getGenomes());
                assertEquals(2, sample.getGenomes().length);
                VcfGenome germline = sample.getGenomes()[0];
                assertEquals("Germline", germline.getId());
                assertEquals(0.3d, germline.getMixture(), 0.01d);
                assertEquals("Patient germline genome", germline.getDescription());
                VcfGenome tumor = sample.getGenomes()[1];
                assertEquals("Tumor", tumor.getId());
                assertEquals(0.7d, tumor.getMixture(), 0.01d);
                assertEquals("Patient tumor genome", tumor.getDescription());
            }

            count++;
        }
        assertEquals(2, count);
    }
}