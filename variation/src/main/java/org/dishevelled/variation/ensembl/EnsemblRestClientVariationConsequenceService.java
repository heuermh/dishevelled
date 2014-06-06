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
import org.dishevelled.variation.VariationConsequenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensembl REST client variation consequence service.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientVariationConsequenceService
    implements VariationConsequenceService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** Variation service. */
    private final VariationService variationService;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(EnsemblRestClientVariationConsequenceService.class);

    /** Allele string pattern. */
    private static final Pattern ALLELE_STRING = Pattern.compile("^(.*)/(.*)$");


    /**
     * Create a new Ensembl REST client variation consequence service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param variationService variation service, must not be null
     */
    public EnsemblRestClientVariationConsequenceService(final String species,
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
    public List<VariationConsequence> consequences(final Variation variation)
    {
        checkNotNull(variation);
        checkArgument(species.equals(variation.getSpecies()));
        checkArgument(reference.equals(variation.getReference()));

        List<VariationConsequence> consequences = new ArrayList<VariationConsequence>();
        for (String identifier : variation.getIdentifiers())
        {
            try
            {
                for (Transcript transcript : variationService.consequences(species, identifier).getTranscripts())
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
                                String referenceAllele = matcher.group(1);
                                String alternateAllele = matcher.group(2);

                                // todo:  check unique consequence terms?
                                for (String consequenceTerm : allele.getConsequenceTerms())
                                {
                                    consequences.add(new VariationConsequence(variation.getSpecies(),
                                                                              variation.getReference(),
                                                                              variation.getIdentifiers(),
                                                                              referenceAllele,
                                                                              alternateAllele,
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
                    logger.warn("unable to find consequences for {} for species {}, rec'd {} {}", identifier, species, e.getStatus(), e.getReason());
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
        return "Ensembl REST client consequences (" + species + " " + reference + ")";
    }
}
