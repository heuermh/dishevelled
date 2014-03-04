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
import org.junit.Test;

/**
 * Unit test for SnpEffVcfVariationConsequenceService.
 */
public final class SnpEffVcfVariationConsequenceServiceTest
{
    private String species;
    private String reference;
    private File file;
    private SnpEffVcfVariationConsequenceService consequenceService;

    @Before
    public void setUp() throws Exception
    {
        species = "human";
        reference = "GRCh37";
        file = File.createTempFile("snpEffVcfVariationServiceTest", ".vcf");

        consequenceService = new SnpEffVcfVariationConsequenceService(species, reference, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullSpecies()
    {
        new SnpEffVcfVariationConsequenceService(null, reference, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullReference()
    {
        new SnpEffVcfVariationConsequenceService(species, null, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullFile()
    {
        new SnpEffVcfVariationConsequenceService(species, reference, null);
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
        Files.write(Resources.toByteArray(getClass().getResource("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.eff.vcf")), file);

        Variation variation = new Variation(species, reference, ImmutableList.of("rs193189309"), "C", ImmutableList.of("T"), "22", 17452052 - 1, 17452052);
        boolean found = false;
        for (VariationConsequence consequence : consequenceService.consequences(variation))
        {
            // SnpEff provides effects for all transcripts, so there are duplicate consequences here
            //   consider using -canon command line option; although the canonical transcript that SnpEff uses might not match that defined by Ensembl
            //System.out.println(consequence.getIdentifier() + "\t" + consequence.getReferenceAllele() + "\t" + consequence.getAlternateAllele() + "\t" + consequence.getSequenceOntologyTerm());
            if (consequence.getIdentifiers().contains("rs193189309"))
            {
                assertEquals(species, consequence.getSpecies());
                assertEquals(reference, consequence.getReference());
                assertEquals("C", consequence.getReferenceAllele());
                assertEquals("T", consequence.getAlternateAllele());
                // SnpEff ontology terms
                //assertTrue("INTRON".equals(consequence.getSequenceOntologyTerm()) || "UPSTREAM".equals(consequence.getSequenceOntologyTerm()));
                // map --> Sequence Ontology terms
                assertTrue("intron_variant".equals(consequence.getSequenceOntologyTerm()) || "upstream_gene_variant".equals(consequence.getSequenceOntologyTerm()));
                assertEquals("22", consequence.getRegion());
                assertEquals(17452052 - 1, consequence.getStart());
                assertEquals(17452052, consequence.getEnd());

                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testConsequencesWithSequenceOntology() throws Exception
    {
        Files.write(Resources.toByteArray(getClass().getResource("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.eff-so.vcf")), file);

        Variation variation = new Variation(species, reference, ImmutableList.of("rs193189309"), "C", ImmutableList.of("T"), "22", 17452052 - 1, 17452052);
        boolean found = false;
        for (VariationConsequence consequence : consequenceService.consequences(variation))
        {
            // SnpEff provides effects for all transcripts, so there are duplicate consequences here
            //   consider using -canon command line option; although the canonical transcript that SnpEff uses might not match that defined by Ensembl
            //System.out.println(consequence.getIdentifier() + "\t" + consequence.getReferenceAllele() + "\t" + consequence.getAlternateAllele() + "\t" + consequence.getSequenceOntologyTerm());
            if (consequence.getIdentifiers().contains("rs193189309"))
            {
                assertEquals(species, consequence.getSpecies());
                assertEquals(reference, consequence.getReference());
                assertEquals("C", consequence.getReferenceAllele());
                assertEquals("T", consequence.getAlternateAllele());
                assertEquals("22", consequence.getRegion());
                assertTrue("intron_variant".equals(consequence.getSequenceOntologyTerm()) || "upstream_gene_variant".equals(consequence.getSequenceOntologyTerm()));
                assertEquals(17452052 - 1, consequence.getStart());
                assertEquals(17452052, consequence.getEnd());

                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testConsequences_gatk_2_6_example_eff() throws Exception
    {
        Files.write(Resources.toByteArray(getClass().getResource("gatk-2.6-example.eff.vcf")), file);

        Variation variation = new Variation(species, reference, ImmutableList.of("rs66469215"), "C", ImmutableList.of("CA"), "6", 7542148 - 1, 7542148);
        int count = 0;
        for (VariationConsequence consequence : consequenceService.consequences(variation))
        {
            assertNotNull(consequence);

            assertEquals(species, consequence.getSpecies());
            assertEquals(reference, consequence.getReference());
            assertNotNull(consequence.getIdentifiers());
            assertTrue(consequence.getIdentifiers().contains("rs66469215"));
            assertEquals("C", consequence.getReferenceAllele());
            assertEquals("CA", consequence.getAlternateAllele());
            assertEquals("6", consequence.getRegion());
            // SnpEff ontology terms
            //assertTrue("FRAME_SHIFT".equals(consequence.getSequenceOntologyTerm()) || "UPSTREAM".equals(consequence.getSequenceOntologyTerm()));
            // map --> Sequence Ontology terms
            assertTrue("frameshift_variant".equals(consequence.getSequenceOntologyTerm()) || "upstream_gene_variant".equals(consequence.getSequenceOntologyTerm()));
            assertEquals(7542148 - 1, consequence.getStart());
            assertEquals(7542148, consequence.getEnd());

            count++;
        }
        assertEquals(2, count);
    }
}