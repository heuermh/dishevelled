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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.heuermh.ensemblrestclient.EnsemblRestClientException;
import com.github.heuermh.ensemblrestclient.TranscriptConsequences;
import com.github.heuermh.ensemblrestclient.VariationService;
import com.github.heuermh.ensemblrestclient.VariationConsequences;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequencePredictionService;

import org.dishevelled.variation.ensembl.EnsemblRestClientUtils.Remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensembl REST client variation consequence prediction service.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientVariationConsequencePredictionService
    implements VariationConsequencePredictionService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** Variation service. */
    private final VariationService variationService;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(EnsemblRestClientVariationConsequencePredictionService.class);

    /** Allele string pattern. */
    private static final Pattern ALLELE_STRING = Pattern.compile("^(.*)/(.*)$");


    /**
     * Create a new Ensembl REST client variation consequence prediction service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param variationService variation service, must not be null
     */
    public EnsemblRestClientVariationConsequencePredictionService(final String species,
                                                                  final String reference,
                                                                  final VariationService variationService)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(variationService);

        this.species = species;
        this.reference = reference;
        this.variationService = variationService;
    }


    @Override
    public List<VariationConsequence> predictConsequences(final Variation variation)
    {
        checkNotNull(variation);
        checkArgument(this.species.equals(species));
        checkArgument(this.reference.equals(reference));
        final String region = variation.getRegion() + ":" + (variation.getStart() + 1L) + "-" + variation.getEnd();

        throttle();

        List<VariationConsequence> consequences = new ArrayList<VariationConsequence>();
        for (String alternateAllele : variation.getAlternateAlleles())
        {
            try
            {
                // todo:  not sure how to represent indels this way . . .
                final String aa = alternateAllele;
                VariationConsequences vc = retryIfNecessary(new Remote<VariationConsequences>()
                    {
                        @Override
                        public VariationConsequences remote() throws EnsemblRestClientException
                        {
                            return variationService.consequences(species, region, aa);
                        }
                    });

                String referenceAllele = vc.getReferenceAllele();
                for (TranscriptConsequences tc : vc.getTranscriptConsequences()) {
                    // only consider canonical transcripts
                    if (tc.isCanonical() && alternateAllele.equals(tc.getAlternateAllele())) {
                        for (String consequenceTerm : tc.getConsequenceTerms())
                        {
                            consequences.add(new VariationConsequence(variation.getSpecies(),
                                                                      variation.getReference(),
                                                                      variation.getIdentifiers(),
                                                                      referenceAllele,
                                                                      alternateAllele,
                                                                      variation.getRegion(),
                                                                      variation.getStart(),
                                                                      variation.getEnd(),
                                                                      consequenceTerm));
                        }
                    }
                }
            }
            catch (EnsemblRestClientException e)
            {
                if (logger.isWarnEnabled())
                {
                    logger.warn("unable to predict consequences for {} {} for species {}, rec'd {} {}", region, alternateAllele, species, e.getStatus(), e.getReason());
                }
            }
        }
        return consequences;
    }

    @Override
    public String toString()
    {
        return "Ensembl REST client VEP (" + species + " " + reference + ")";
    }
}
