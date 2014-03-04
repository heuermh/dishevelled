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

import com.google.common.io.Files;
import com.google.common.io.Resources;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for VcfVariationService.
 */
public final class VcfVariationServiceTest
{
    private String species;
    private String reference;
    private File file;
    private VcfVariationService variationService;

    @Before
    public void setUp() throws Exception
    {
        species = "human";
        reference = "GRCh37";
        file = File.createTempFile("vcfVariationServiceTest", ".vcf");

        variationService = new VcfVariationService(species, reference, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullSpecies()
    {
        new VcfVariationService(null, reference, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullReference()
    {
        new VcfVariationService(species, null, file);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullFile()
    {
        new VcfVariationService(species, reference, null);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(variationService);
    }

    @Test(expected=NullPointerException.class)
    public void testVariationsNullFeature()
    {
        variationService.variations(null);
    }

    @Test
    public void testVariations() throws Exception
    {
        Files.write(Resources.toByteArray(getClass().getResource("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vcf")), file);

        Feature feature = new Feature(species, reference, "ENSG00000206195", "22", 16147979, 16193004, -1);
        boolean found = false;
        for (Variation variation : variationService.variations(feature))
        {
            // should this variation be included?
            // the samples in the test file show only ref allele at this position
            if (variation.getIdentifiers().contains("rs139448371"))
            {
                assertEquals(species, variation.getSpecies());
                assertEquals(reference, variation.getReference());
                assertEquals("C", variation.getReferenceAllele());
                assertEquals(1, variation.getAlternateAlleles().size());
                assertEquals("A", variation.getAlternateAlleles().get(0));
                assertEquals("22", variation.getRegion());
                assertEquals(16162219 - 1, variation.getStart());
                assertEquals(16162219, variation.getEnd());

                found = true;
            }
        }
        assertTrue(found);
    }
}