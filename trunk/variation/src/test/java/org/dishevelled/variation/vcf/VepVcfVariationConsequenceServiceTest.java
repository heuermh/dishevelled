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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import com.google.common.collect.ImmutableList;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for VepVcfVariationConsequenceService.
 */
public final class VepVcfVariationConsequenceServiceTest
{
    private String species;
    private String reference;
    private File file;
    private VepVcfVariationConsequenceService consequenceService;

    @Before
    public void setUp() throws Exception
    {
        species = "human";
        reference = "GRCh37.73";
        file = File.createTempFile("vepVcfVariationServiceTest", ".vcf");

        consequenceService = new VepVcfVariationConsequenceService(species, reference, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullSpecies()
    {
        new VepVcfVariationConsequenceService(null, reference, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullReference()
    {
        new VepVcfVariationConsequenceService(species, null, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullFile()
    {
        new VepVcfVariationConsequenceService(species, reference, null);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(consequenceService);
    }

    @Test(expected=NullPointerException.class)
    public void testConsequencesNullVariation()
    {
        consequenceService.consequences(null);
    }

    @Test
    public void testConsequences() throws Exception
    {
        Files.write(Resources.toByteArray(getClass().getResource("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vep.vcf")), file);

        Variation variation = new Variation(species, reference, ImmutableList.of("rs193189309"), "C", ImmutableList.of("T"), "22", 17452052 - 1, 17452052);
        int count = 0;
        for (VariationConsequence consequence : consequenceService.consequences(variation))
        {
            /*
              the canonical transcript is ENST00000400588 and it has only one consequence term
                but note that neither sample has the alt allele

22	17452052	rs193189309	C	T	100	PASS	CSQ=T|ENSG00000215568|ENST00000520505|Transcript|upstream_gene_variant|||||||2779|,T|ENSG00000215568|ENST00000465611|Transcript|intron_variant&NMD_transcript_variant||||||||,T|ENSG00000215568|ENST00000400588|Transcript|intron_variant||||||||YES,T|ENSG00000215568|ENST00000523144|Transcript|intron_variant&nc_transcript_variant||||||||	GT:DS:GL	0|0:0.000:-0.11,-0.64,-4.10	0|0:0.000:-0.03,-1.17,-5.00
             */
            assertEquals(species, consequence.getSpecies());
            assertEquals(reference, consequence.getReference());
            assertNotNull(consequence.getIdentifiers());
            assertTrue(consequence.getIdentifiers().contains("rs193189309"));
            assertEquals("C", consequence.getReferenceAllele());
            assertEquals("T", consequence.getAlternateAllele());
            assertTrue("intron_variant".equals(consequence.getSequenceOntologyTerm()));
            assertEquals("22", consequence.getRegion());
            assertEquals(17452052 - 1, consequence.getStart());
            assertEquals(17452052, consequence.getEnd());

            count++;
        }
        assertEquals(1, count);
    }

    //@Test
    @Ignore
    public void testConsequences_vep_for_gemini() throws Exception
    {
        Files.write(Resources.toByteArray(getClass().getResource("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vep-for-gemini.vcf")), file);

        Variation variation = new Variation(species, reference, ImmutableList.of("rs193189309"), "C", ImmutableList.of("T"), "22", 17452052 - 1, 17452052);
        int count = 0;
        for (VariationConsequence consequence : consequenceService.consequences(variation))
        {
            /*

              this is missing the canonical attribute

22	17452052	rs193189309	C	T	100	PASS	CSQ=upstream_gene_variant|||ENSG00000215568||ENST00000520505|||,intron_variant&NMD_transcript_variant|||ENSG00000215568||ENST00000465611|||,intron_variant|||ENSG00000215568||ENST00000400588|||,intron_variant&nc_transcript_variant|||ENSG00000215568||ENST00000523144|||	GT:DS:GL	0|0:0.000:-0.11,-0.64,-4.10	0|0:0.000:-0.03,-1.17,-5.00

             */
            assertEquals(species, consequence.getSpecies());
            assertEquals(reference, consequence.getReference());
            assertNotNull(consequence.getIdentifiers());
            assertTrue(consequence.getIdentifiers().contains("rs193189309"));
            assertEquals("C", consequence.getReferenceAllele());
            assertEquals("T", consequence.getAlternateAllele());
            assertTrue("intron_variant".equals(consequence.getSequenceOntologyTerm()));
            assertEquals("22", consequence.getRegion());
            assertEquals(17452052 - 1, consequence.getStart());
            assertEquals(17452052, consequence.getEnd());

            count++;
        }
        assertEquals(1, count);
    }
}