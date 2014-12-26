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
package org.dishevelled.variation.googlegenomics;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import com.google.api.services.genomics.Genomics;

import com.google.api.services.genomics.model.SearchVariantsRequest;
import com.google.api.services.genomics.model.SearchVariantsResponse;
import com.google.api.services.genomics.model.Variant;

import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Google Genomics API variation service.
 *
 * @author  Michael Heuer
 */
public final class GoogleGenomicsVariationService implements VariationService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** DatasetId. */
    private final String datasetId;

    /** Google Genomics API. */
    private final Genomics genomics;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(GoogleGenomicsVariationService.class);


    /**
     * Create a new Google Genomics API variation service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param datasetId datasetId, must not ben null
     * @param genomics Google Genomics API, must not be null
     */
    public GoogleGenomicsVariationService(final String species,
                                          final String reference,
                                          final String datasetId,
                                          final Genomics genomics)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(datasetId);
        checkNotNull(genomics);
        this.species = species;
        this.reference = reference;
        this.datasetId = datasetId;
        this.genomics = genomics;
    }


    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));

        SearchVariantsRequest request = new SearchVariantsRequest()
            .setVariantSetIds(ImmutableList.of(datasetId))
            .setReferenceName(feature.getRegion())
            .setStart(feature.getStart())
            .setEnd(feature.getEnd());

        List<Variation> variations = Lists.newArrayList();
        try
        {
            SearchVariantsResponse response = genomics.variants().search(request).execute();

            for (Variant variant : response.getVariants())
            {
                List<String> identifiers = ImmutableList.copyOf(variant.getNames());
                String ref = variant.getReferenceBases();
                List<String> alt = ImmutableList.copyOf(variant.getAlternateBases());
                String contig = variant.getReferenceName();
                // Google Genomics API is a 0-based coordinate system
                long start = variant.getStart() + 1L;
                long end = variant.getEnd();
                variations.add(new Variation(species, reference, identifiers, ref, alt, contig, start, end));
            }
        }
        catch (IOException e)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("unable to find variations for region {}:{}-{}:{} for species {}, caught {}", feature.getRegion(), feature.getStart(), feature.getEnd(), feature.getStrand(), species, e.getMessage());
            }
        }
        return variations;
    }
}
