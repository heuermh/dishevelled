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
import org.dishevelled.variation.VariationConsequenceService;

import org.dishevelled.vocabulary.Concept;

/**
 * Synthetic variation consequence service.
 */
public final class SyntheticVariationConsequenceService
    implements VariationConsequenceService
{
    private final SyntheticGenome genome;
    private final Random random = new Random();
    private final List<Concept> consequenceTerms;

    @Inject
    public SyntheticVariationConsequenceService(final SyntheticGenome genome)
    {
        checkNotNull(genome);
        this.genome = genome;

        consequenceTerms = new ArrayList<Concept>();
        consequenceTerms.addAll(sequenceVariants().getConcepts());
    }


    @Override
    public List<VariationConsequence> consequences(final Variation variation)
    {
        checkNotNull(variation);
        checkArgument(genome.getSpecies().equals(variation.getSpecies()));
        checkArgument(genome.getReference().equals(variation.getReference()));

        List<VariationConsequence> consequences = new ArrayList<VariationConsequence>();
        for (String alternateAllele : variation.getAlternateAlleles())
        {
            String consequenceTerm = sample();
            consequences.add(new VariationConsequence(variation.getSpecies(), variation.getReference(), variation.getIdentifiers(),
                    variation.getReferenceAllele(), alternateAllele, consequenceTerm,
                    variation.getRegion(), variation.getStart(), variation.getEnd()));

            if (random.nextDouble() < 0.25)
            {
                consequences.add(new VariationConsequence(variation.getSpecies(), variation.getReference(), variation.getIdentifiers(),
                        variation.getReferenceAllele(), alternateAllele, sample(consequenceTerm),
                        variation.getRegion(), variation.getStart(), variation.getEnd()));
            }
        }
        return consequences;
    }

    String sample()
    {
        return consequenceTerms.get(random.nextInt(consequenceTerms.size())).getName();
    }

    String sample(final String existing)
    {
        String consequenceTerm = sample();
        while (existing.equals(consequenceTerm))
        {
            consequenceTerm = sample();
        }
        return consequenceTerm;
    }

    @Override
    public String toString()
    {
        return "Synthetic consequences (" + genome.getSpecies() + " " + genome.getReference() + ")";
    }
}