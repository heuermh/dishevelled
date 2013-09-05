/*

    dsh-variation  Variation.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.vcf;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;

import com.google.common.collect.ImmutableList;

import com.google.common.io.Files;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequenceService;

/**
 * SnpEff-annotated VCF file variation consequence service.
 */
public final class SnpEffVcfVariationConsequenceService implements VariationConsequenceService
{
    private final String species;
    private final String reference;
    private final File file;

    //@Inject
    public SnpEffVcfVariationConsequenceService(final String species, final String reference, final File file)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(file);

        this.species = species;
        this.reference = reference;
        this.file = file;
    }


    @Override
    public List<VariationConsequence> consequences(final Variation variation)
    {
        checkNotNull(variation);
        checkArgument(species.equals(variation.getSpecies()));
        checkArgument(reference.equals(variation.getReference()));

        // ick.  re-streams file every time
        final List<VariationConsequence> consequences = new ArrayList<VariationConsequence>();
        try
        {
            VcfReader.stream(Files.newReaderSupplier(file, Charsets.UTF_8), new VcfStreamListener()
                {
                    @Override
                    public void record(final VcfRecord record)
                    {
                        if (sameVariation(variation, record))
                        {
                            // just use first id
                            String identifier = record.getId().length == 0 ? null : record.getId()[0];
                            String ref = record.getRef();
                            List<String> alt = ImmutableList.copyOf(record.getAlt());
                            String region = record.getChrom();
                            int start = Math.max(1, record.getPos() - 1);
                            int end = record.getPos();
                            int strand = 1;

                            // pull SnpEff from info, then for each alt/SnpEff effect pair, add a new variation consequence
                            if (record.getInfo().containsKey("EFF"))
                            {
                                String[] effTokens = record.getInfo().get("EFF").split(",");
                                for (String effToken : effTokens)
                                {
                                    try
                                    {
                                        SnpEffEffect effect = SnpEffEffect.parse(effToken);

                                        String altAllele = alt.get(effect.getGenotype());
                                        // requires SnpEff to have been run with -sequenceOntology command line option
                                        //   or use SnpEffOntology.effectToSequenceOntologyMapping()
                                        String sequenceOntologyTerm = effect.getEffect();
                                        // todo: double-check ref, altAllele, transcript, geneName matches
                                        //   also only include those alt alleles found in samples/individuals of interest

                                        consequences.add(new VariationConsequence(variation.getSpecies(),
                                                                                  variation.getReference(),
                                                                                  variation.getIdentifier(),
                                                                                  variation.getReferenceAllele(),
                                                                                  altAllele,
                                                                                  sequenceOntologyTerm,
                                                                                  variation.getName(),
                                                                                  variation.getStart(),
                                                                                  variation.getEnd(),
                                                                                  variation.getStrand()));
                                    }
                                    catch (IOException e)
                                    {
                                        // todo
                                    }
                                }
                            }
                        }
                    }

                    // todo: not sure this is a valid comparison
                    private boolean sameVariation(final Variation variation, final VcfRecord record)
                    {
                        String region = record.getChrom();
                        int start = Math.max(1, record.getPos() - 1);
                        int end = record.getPos();
                        int strand = 1;

                        return variation.getName().equals(region) && variation.getStart() == start && variation.getEnd() == end && variation.getStrand() == strand;
                    }
                });
        }
        catch (IOException e)
        {
            // todo
        }
        return consequences;
    }
}