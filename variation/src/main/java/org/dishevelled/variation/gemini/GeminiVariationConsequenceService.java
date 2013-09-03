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
package org.dishevelled.variation.gemini;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequenceService;

/**
 * GEMINI command line variation consequence service.
 */
final class GeminiVariationConsequenceService implements VariationConsequenceService
{
    private final String species;
    private final String reference;
    private final String databaseName;

    //@Inject
    GeminiVariationConsequenceService(final String species, final String reference, final String databaseName)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(databaseName);

        this.species = species;
        this.reference = reference;
        this.databaseName = databaseName;
    }


    @Override
    public List<VariationConsequence> consequences(final Variation variation)
    {
        checkNotNull(variation);
        checkArgument(species.equals(variation.getSpecies()));
        checkArgument(reference.equals(variation.getReference()));

        // todo:  requires GEMINI variant_id primary key as identifier
        ProcessBuilder processBuilder = new ProcessBuilder("gemini", "query", "-q", "select v.rs_ids, v.ref, v.alt, vi.impact, v.chrom, v.start, v.end from variants v, variant_impacts vi where vi.variant_id = v.variant_id and v.variant_id=" + variation.getIdentifier(), databaseName);

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
                // todo: rs_ids is a comma-separated list of dbSNP ids
                String identifier = tokens[0] == "null" ? null : tokens[0];
                String ref = tokens[1];
                String alt = tokens[2];
                // todo: impact may need to be mapped to sequence ontology
                String sequenceOntologyTerm = tokens[3];
                String name = tokens[4];
                int start = Integer.parseInt(tokens[5]);
                int end = Integer.parseInt(tokens[6]);
                //int strand = Integer.parseInt(tokens[7]);
                int strand = 1;

                variationConsequences.add(new VariationConsequence(species, reference, identifier, ref, alt, sequenceOntologyTerm, name, start, end, strand));
            }
        }
        catch (IOException e)
        {
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