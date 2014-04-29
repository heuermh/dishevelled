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
package org.dishevelled.variation.ensembl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import com.github.heuermh.ensemblrestclient.EnsemblRestClientException;
import com.github.heuermh.ensemblrestclient.FeatureService;

import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensembl REST client variation service.
 */
public final class EnsemblRestClientVariationService
    implements VariationService
{
    private final String species;
    private final String reference;
    private final FeatureService featureService;
    private final Logger logger = LoggerFactory.getLogger(EnsemblRestClientVariationService.class);


    public EnsemblRestClientVariationService(final String species,
                                             final String reference,
                                             final FeatureService featureService)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(featureService);

        this.species = species;
        this.reference = reference;
        this.featureService = featureService;
    }


    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));
        String region = feature.getRegion() + ":" + feature.getStart() + "-" + feature.getEnd() + ":" + feature.getStrand();

        List<Variation> variations = new ArrayList<Variation>();
        try
        {
            for (com.github.heuermh.ensemblrestclient.Variation variation : featureService.variationFeatures(species, region))
            {
                variations.add(new Variation(species,
                                             reference,
                                             ImmutableList.of(variation.getIdentifier()),
                                             variation.getReferenceAllele(),
                                             variation.getAlternateAlleles(),
                                             variation.getLocation().getName(),
                                             variation.getLocation().getStart(),
                                             variation.getLocation().getEnd()));
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
