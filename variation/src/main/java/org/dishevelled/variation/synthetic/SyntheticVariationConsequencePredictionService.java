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
package org.dishevelled.variation.synthetic;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.variation.so.SequenceOntology.sequenceVariants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.inject.Inject;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequencePredictionService;

import org.dishevelled.vocabulary.Concept;

/**
 * Synthetic variation consequence prediction service.
 *
 * @author  Michael Heuer
 */
public final class SyntheticVariationConsequencePredictionService
    implements VariationConsequencePredictionService
{
    /** Synthetic genome. */
    private final SyntheticGenome genome;

    /** Source of randomness. */
    private final Random random = new Random();

    /** List of consequence terms. */
    private final List<Concept> consequenceTerms;


    /**
     * Create a new synthetic variation consequence prediction service.
     *
     * @param genome synthetic genome, must not be null
     */
    @Inject
    public SyntheticVariationConsequencePredictionService(final SyntheticGenome genome)
    {
        checkNotNull(genome);
        this.genome = genome;

        consequenceTerms = new ArrayList<Concept>();
        consequenceTerms.addAll(sequenceVariants().getConcepts());
    }


    @Override
    public List<VariationConsequence> predictConsequences(final Variation variation)
    {
        checkNotNull(variation);
        checkArgument(genome.getSpecies().equals(variation.getSpecies()));
        checkArgument(genome.getReference().equals(variation.getReference()));

        List<VariationConsequence> consequences = new ArrayList<VariationConsequence>();
        String consequenceTerm = sample();
        for (String alternateAllele : variation.getAlternateAlleles())
        {
            consequences.add(new VariationConsequence(variation.getSpecies(), variation.getReference(), variation.getIdentifiers(),
                                                      variation.getReferenceAllele(), alternateAllele,
                                                      variation.getRegion(), variation.getStart(), variation.getEnd(), consequenceTerm));
        }
        return consequences;
    }


    /**
     * Sample from the list of consequence terms.
     *
     * @return consequence term sampled from the list of consequence terms
     */
    String sample()
    {
        return consequenceTerms.get(random.nextInt(consequenceTerms.size())).getName();
    }

    @Override
    public String toString()
    {
        return "Synthetic consequence predictions (" + genome.getSpecies() + " " + genome.getReference() + ")";
    }
}
