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
package org.dishevelled.variation.ensembl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.variation.ensembl.EnsemblRestClientUtils.retryIfNecessary;
import static org.dishevelled.variation.ensembl.EnsemblRestClientUtils.throttle;

import java.util.ArrayList;
import java.util.List;

import com.github.heuermh.ensemblrestclient.EnsemblRestClientException;
import com.github.heuermh.ensemblrestclient.OverlapService;

import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

import org.dishevelled.variation.ensembl.EnsemblRestClientUtils.Remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensembl REST client variation service.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientVariationService
    implements VariationService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** Overlap service. */
    private final OverlapService overlapService;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(EnsemblRestClientVariationService.class);


    /**
     * Create a new Ensembl REST client variation service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param overlapService overlap service, must not be null
     */
    public EnsemblRestClientVariationService(final String species,
                                             final String reference,
                                             final OverlapService overlapService)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(overlapService);

        this.species = species;
        this.reference = reference;
        this.overlapService = overlapService;
    }


    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));
        final String region = feature.getRegion() + ":" + (feature.getStart() + 1L) + "-" + feature.getEnd() + ":" + feature.getStrand();

        throttle();

        List<Variation> variations = new ArrayList<Variation>();
        try
        {
            for (com.github.heuermh.ensemblrestclient.Variation variation : retryIfNecessary(new Remote<List<com.github.heuermh.ensemblrestclient.Variation>>()
                    {
                        @Override
                        public List<com.github.heuermh.ensemblrestclient.Variation> remote() throws EnsemblRestClientException
                        {
                            return overlapService.variations(species, region);
                        }
                    }))
            {
                // convert from 1-based fully closed to 0-based closed open
                long start = variation.getLocation().getStart() - 1L;
                long end = variation.getLocation().getEnd();
                variations.add(new Variation(species,
                                             reference,
                                             ImmutableList.of(variation.getIdentifier()),
                                             variation.getReferenceAllele(),
                                             variation.getAlternateAlleles(),
                                             variation.getLocation().getName(),
                                             start,
                                             end));
            }
        }
        catch (EnsemblRestClientException e)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("unable to find variations for region {} for species {}, rec'd {} {}", region, species, e.getStatus(), e.getReason());
            }
        }
        return variations;
    }

    @Override
    public String toString()
    {
        return "Ensembl REST client variations (" + species + " " + reference + ")";
    }
}
