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
package org.dishevelled.variation.gemini;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.variation.gemini.GeminiUtils.isValidIdentifier;
import static org.dishevelled.variation.gemini.GeminiUtils.variantId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequenceService;

import org.dishevelled.variation.snpeff.SnpEffOntology;

import org.dishevelled.variation.so.SequenceOntology;

import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Mapping;
import org.dishevelled.vocabulary.Projection;

/**
 * GEMINI command line variation consequence service.
 *
 * @author  Michael Heuer
 */
public final class GeminiVariationConsequenceService implements VariationConsequenceService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** GEMINI database name. */
    private final String databaseName;

    /** Map of sequence variant terms keyed by name. */
    private final Map<String, Concept> sequenceVariants;

    /** Map of effect terms keyed by name. */
    private final Map<String, Concept> effects;

    /** Map of effect projections keyed by effect. */
    private final Map<Concept, Projection> effectProjections;

    /** Map of region terms keyed by name. */
    private final Map<String, Concept> regions;

    /** Map of region projections keyed by region. */
    private final Map<Concept, Projection> regionProjections;


    /**
     * Create a new GEMINI command line variation consequence service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param databaseName GEMINI database name, must not be null
     */
    public GeminiVariationConsequenceService(final String species, final String reference, final String databaseName)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(databaseName);

        this.species = species;
        this.reference = reference;
        this.databaseName = databaseName;

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

        // requires GEMINI variant_id primary key as identifier
        int variantId = -1;
        for (String identifier : variation.getIdentifiers())
        {
            if (isValidIdentifier(identifier))
            {
                variantId = variantId(identifier);
                break;
            }
        }
        if (variantId == -1)
        {
            throw new IllegalArgumentException("variation must contain a GEMINI variant_id identifier");
        }

        ProcessBuilder processBuilder = new ProcessBuilder("gemini", "query", "-q", "select v.rs_ids, v.ref, v.alt, vi.impact, v.chrom, v.start, v.end from variants v, variant_impacts vi where vi.variant_id = v.variant_id and v.variant_id=" + variantId, databaseName);

        BufferedReader reader = null;
        List<VariationConsequence> variationConsequences = new ArrayList<VariationConsequence>();
        try
        {
            Process process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while (reader.ready())
            {
                String line = reader.readLine();
                if (line == null)
                {
                    break;
                }
                String[] tokens = line.split("\t");
                // rs_ids is a comma-separated list of dbSNP ids
                List<String> identifiers = tokens[0] == "null" ? Collections.<String>emptyList() : ImmutableList.copyOf(tokens[0].split(","));
                String ref = tokens[1];
                String alt = tokens[2];
                String effect = tokens[3];

                // note: gemini puts chr on the region name
                String region = tokens[4].replace("chr", "");

                long start = Long.parseLong(tokens[5]);
                long end = Long.parseLong(tokens[6]);

                // map from SnpEff ontology to Sequence Ontology if necessary
                // todo: consider moving this to SnpEffOntology
                String sequenceOntologyTerm = null;
                if (sequenceVariants.containsKey(effect)) {
                    sequenceOntologyTerm = effect;
                }
                else if (effects.containsKey(effect)) {
                    sequenceOntologyTerm = effectProjections.get(effects.get(effect)).getTarget().getName();
                }
                else if (regions.containsKey(effect)) {
                    sequenceOntologyTerm = regionProjections.get(regions.get(effect)).getTarget().getName();
                }

                if (sequenceOntologyTerm != null) {
                    variationConsequences.add(new VariationConsequence(species, reference, identifiers, ref, alt, sequenceOntologyTerm, region, start, end));
                }
            }
        }
        catch (IOException e)
        {
            // ignore
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return variationConsequences;
    }

    @Override
    public String toString()
    {
        return "GEMINI consequences (" + species + " " + reference + " " + databaseName + ")";
    }
}
