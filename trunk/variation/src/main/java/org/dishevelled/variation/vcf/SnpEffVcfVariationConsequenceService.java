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
package org.dishevelled.variation.vcf;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Charsets;

import com.google.common.collect.ImmutableList;

import com.google.common.io.Files;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequenceService;

import org.dishevelled.variation.snpeff.SnpEffOntology;

import org.dishevelled.variation.so.SequenceOntology;

import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Mapping;
import org.dishevelled.vocabulary.Projection;

/**
 * SnpEff-annotated VCF file variation consequence service.
 */
public final class SnpEffVcfVariationConsequenceService implements VariationConsequenceService
{
    private final String species;
    private final String reference;
    private final File file;
    private final Map<String, Concept> sequenceVariants;
    private final Map<String, Concept> effects;
    private final Map<Concept, Projection> effectProjections;
    private final Map<String, Concept> regions;
    private final Map<Concept, Projection> regionProjections;


    public SnpEffVcfVariationConsequenceService(final String species, final String reference, final File file)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(file);

        this.species = species;
        this.reference = reference;
        this.file = file;

        // Sequence Ontology
        sequenceVariants = SequenceOntology.indexByName(SequenceOntology.sequenceVariants());

        // SnpEff ontologies
        Mapping effectToSequenceOntology = SnpEffOntology.effectToSequenceOntologyMapping();
        effects = SnpEffOntology.indexByName(effectToSequenceOntology.getSource());
        effectProjections = SnpEffOntology.indexBySourceConcept(effectToSequenceOntology);

        Mapping regionToSequenceOntology = SnpEffOntology.regionToSequenceOntologyMapping();
        regions = SnpEffOntology.indexByName(regionToSequenceOntology.getSource());
        regionProjections = SnpEffOntology.indexBySourceConcept(regionToSequenceOntology);
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
                            List<String> identifiers = ImmutableList.copyOf(record.getId());
                            String ref = record.getRef();
                            List<String> alt = ImmutableList.copyOf(record.getAlt());
                            String region = record.getChrom();
                            int position = record.getPos();
                            int start = position - 1;
                            int end = start + ref.length();

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

                                        // map from SnpEff ontology to Sequence Ontology if necessary
                                        String sequenceOntologyTerm = null;
                                        if (sequenceVariants.containsKey(effect.getEffect())) {
                                            sequenceOntologyTerm = effect.getEffect();
                                        }
                                        else if (effects.containsKey(effect.getEffect())) {
                                            // todo:  NPE if no projection exists for a given SnpEff ontology term (e.g. RARE_AMINO_ACID)
                                            sequenceOntologyTerm = effectProjections.get(effects.get(effect.getEffect())).getTarget().getName();
                                        }
                                        else if (regions.containsKey(effect.getEffect())) {
                                            sequenceOntologyTerm = regionProjections.get(regions.get(effect.getEffect())).getTarget().getName();
                                        }

                                        // todo: double-check ref, altAllele, transcript, geneName matches
                                        //   also only include those alt alleles found in samples/individuals of interest
                                        
                                        if (sequenceOntologyTerm != null) {
                                            consequences.add(new VariationConsequence(variation.getSpecies(),
                                                                                      variation.getReference(),
                                                                                      variation.getIdentifiers(),
                                                                                      variation.getReferenceAllele(),
                                                                                      altAllele,
                                                                                      sequenceOntologyTerm,
                                                                                      variation.getRegion(),
                                                                                      variation.getStart(),
                                                                                      variation.getEnd()));
                                        }
                                    }
                                    catch (IOException e)
                                    {
                                        //System.out.println("error parsing record at line number " + record.getLineNumber());
                                        //e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    // todo: not sure this is a valid comparison
                    private boolean sameVariation(final Variation variation, final VcfRecord record)
                    {
                        return variation.getRegion().equals(record.getChrom()) && (variation.getStart() == (record.getPos() - 1));
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