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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.heuermh.ensemblrestclient.Allele;
import com.github.heuermh.ensemblrestclient.EnsemblRestClientException;
import com.github.heuermh.ensemblrestclient.Transcript;
import com.github.heuermh.ensemblrestclient.VariationService;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequencePredictionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensembl REST client variation consequence prediction service.
 */
public final class EnsemblRestClientVariationConsequencePredictionService
    implements VariationConsequencePredictionService
{
    private final String species;
    private final String reference;
    private final VariationService variationService;
    private final Logger logger = LoggerFactory.getLogger(EnsemblRestClientVariationConsequencePredictionService.class);
    private static final Pattern ALLELE_STRING = Pattern.compile("^(.*)/(.*)$");


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
        String region = variation.getRegion() + ":" + variation.getStart() + "-" + variation.getEnd();

        List<VariationConsequence> consequences = new ArrayList<VariationConsequence>();
        for (String alternateAllele : variation.getAlternateAlleles())
        {
            try
            {
                // todo:  not sure how to represent indels this way . . .
                for (Transcript transcript : variationService.consequences(species, region, alternateAllele).getTranscripts())
                {
                    // only use canonical transcript
                    if (transcript.isCanonical())
                    {
                        // todo:  check unique alleles?
                        for (Allele allele : transcript.getAlleles())
                        {
                            // parse allele string
                            Matcher matcher = ALLELE_STRING.matcher(allele.getAlleleString());
                            if (matcher.matches())
                            {
                                String ref = matcher.group(1);
                                String alt = matcher.group(2);

                                // todo:  check unique consequence terms?
                                for (String consequenceTerm : allele.getConsequenceTerms())
                                {
                                    consequences.add(new VariationConsequence(variation.getSpecies(),
                                                                              variation.getReference(),
                                                                              variation.getIdentifiers(),
                                                                              ref,
                                                                              alt,
                                                                              consequenceTerm,
                                                                              variation.getRegion(),
                                                                              variation.getStart(),
                                                                              variation.getEnd()));

                                }
                            }
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
            slowDown();
        }
        return consequences;
    }

    /**
     * Slow down calls to prevent rate limit throttling.
     */
    private static void slowDown()
    {
        try
        {
            Thread.sleep(666L);
        }
        catch (InterruptedException e)
        {
            // ignore
        }
    }

    @Override
    public String toString()
    {
        return "Ensembl REST client VEP (" + species + " " + reference + ")";
    }
}
