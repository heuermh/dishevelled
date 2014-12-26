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
package org.dishevelled.variation.ga4gh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;

import org.ga4gh.models.Variant;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for GlobalAllianceVariationService.
 *
 * @author  Michael Heuer
 */
public final class GlobalAllianceVariationServiceTest
{
    private String species;
    private String reference;
    private GlobalAllianceVariationService variationService;

    @Before
    public void setUp() throws Exception
    {
        species = "human";
        reference = "GRCh37";
        variationService = new GlobalAllianceVariationService(species, reference);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(variationService);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullSpecies()
    {
        new GlobalAllianceVariationService(null, reference);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullReference()
    {
        new GlobalAllianceVariationService(species, null);
    }


    @Test(expected=NullPointerException.class)
    public void testVariationsNullFeature()
    {
        variationService.variations(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testVariationsSpeciesMismatch()
    {
        variationService.variations(new Feature("mouse", reference, "identifier", "region", 1, 2, 1));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testVariationsReferenceMismatch()
    {
        variationService.variations(new Feature(species, "GRCh38", "identifier", "region", 1, 2, 1));
    }


    @Test(expected=NullPointerException.class)
    public void testConvertNullVariant()
    {
        variationService.convert(null);
    }

    @Test
    public void testConvert()
    {
        Variant variant = new Variant();
        variant.setReferenceName("1");
        List<CharSequence> names = Lists.newArrayList();
        names.add("rs1234");
        variant.setNames(names);
        variant.setStart(42L); // 0-based, closed-open interval
        variant.setEnd(43L);
        variant.setReferenceBases("G");
        List<CharSequence> alternateBases = Lists.newArrayList();
        alternateBases.add("A");
        alternateBases.add("C");
        alternateBases.add("T");
        variant.setAlternateBases(alternateBases);

        Variation variation = variationService.convert(variant);
        assertNotNull(variation);
        assertEquals(ImmutableList.of("rs1234"), variation.getIdentifiers());
        assertEquals("1", variation.getRegion());
        assertEquals(43L, variation.getStart()); // 1-based
        assertEquals(44L, variation.getEnd());
        assertEquals("G", variation.getReferenceAllele());
        assertEquals(ImmutableList.of("A", "C", "T"), variation.getAlternateAlleles());
    }
}
