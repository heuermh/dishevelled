/*

    dsh-variation  Variation.
    Copyright (c) 2013-2015 held jointly by the individual authors.

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
package org.dishevelled.variation.googlegenomics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import com.google.api.services.genomics.Genomics;

import com.google.api.services.genomics.model.SearchVariantsRequest;
import com.google.api.services.genomics.model.SearchVariantsResponse;
import com.google.api.services.genomics.model.Variant;

import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for GoogleGenomicsVariationService.
 */
public final class GoogleGenomicsVariationServiceTest
{
    private String species;
    private String reference;
    private String datasetId;
    private Feature feature;
    private Variant variant;
    private SearchVariantsResponse response;
    private GoogleGenomicsVariationService variationService;

    @Mock
    private Genomics genomics;
    @Mock
    private Genomics.Variants variants;
    @Mock
    private Genomics.Variants.Search search;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        species = "human";
        reference = "GRCh38";
        datasetId = "datasetId";
        feature = new Feature(species, reference, "identifier", "region", 1, 2, 1);
        variant = new Variant();
        variant.setNames(ImmutableList.of("identifier"));
        variant.setReferenceName("region");
        variant.setStart(0L);
        variant.setEnd(1L);
        variant.setReferenceBases("C");
        variant.setAlternateBases(ImmutableList.of("G", "T"));
        response = new SearchVariantsResponse();
        variationService = new GoogleGenomicsVariationService(species, reference, datasetId, genomics);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(variationService);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullSpecies()
    {
        new GoogleGenomicsVariationService(null, reference, datasetId, genomics);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullReference()
    {
        new GoogleGenomicsVariationService(species, null, datasetId, genomics);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullDatasetId()
    {
        new GoogleGenomicsVariationService(species, reference, null, genomics);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullGenomicsClient()
    {
        new GoogleGenomicsVariationService(species, reference, datasetId, null);
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
        variationService.variations(new Feature(species, "GRCh37", "identifier", "region", 1, 2, 1));
    }

    @Test
    public void testVariations() throws Exception
    {
        response.setVariants(ImmutableList.of(variant));

        when(genomics.variants()).thenReturn(variants);
        when(variants.search(any(SearchVariantsRequest.class))).thenReturn(search);
        when(search.execute()).thenReturn(response);

        List<Variation> variations = variationService.variations(feature);
        assertNotNull(variations);
        assertEquals(1, variations.size());
        Variation variation = variations.get(0);
        assertNotNull(variation);
        assertEquals(species, variation.getSpecies());
        assertEquals(reference, variation.getReference());
        assertEquals(ImmutableList.of("identifier"), variation.getIdentifiers());
        assertEquals("C", variation.getReferenceAllele());
        assertEquals(ImmutableList.of("G", "T"), variation.getAlternateAlleles());
        assertEquals(0L, variation.getStart());
        assertEquals(1L, variation.getEnd());
    }

    @Test
    public void testVariationsEmptyResponse() throws Exception
    {
        response.setVariants(Collections.<Variant>emptyList());

        when(genomics.variants()).thenReturn(variants);
        when(variants.search(any(SearchVariantsRequest.class))).thenReturn(search);
        when(search.execute()).thenReturn(response);

        List<Variation> variations = variationService.variations(feature);
        assertNotNull(variations);
        assertEquals(0, variations.size());
    }

    @Test
    public void testVariationsExecuteIOException() throws Exception
    {
        when(genomics.variants()).thenReturn(variants);
        when(variants.search(any(SearchVariantsRequest.class))).thenReturn(search);
        when(search.execute()).thenThrow(new IOException());

        List<Variation> variations = variationService.variations(feature);
        assertNotNull(variations);
        assertEquals(0, variations.size());
    }
}
