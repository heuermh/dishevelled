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

import static org.dishevelled.variation.gemini.GeminiUtils.identifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

/**
 * GEMINI command line variation service.
 *
 * @author  Michael Heuer
 */
public final class GeminiVariationService implements VariationService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** GEMINI database name. */
    private final String databaseName;


    /**
     * Create a new GEMINI command line variation service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param databaseName GEMINI database name, must not be null
     */
    public GeminiVariationService(final String species, final String reference, final String databaseName)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(databaseName);

        this.species = species;
        this.reference = reference;
        this.databaseName = databaseName;
    }


    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));

        // note: gemini expects chr in the region name query
        String query = "chr" + feature.getRegion() + ":" + feature.getStart() + "-" + feature.getEnd();
        ProcessBuilder processBuilder = new ProcessBuilder("gemini", "region", "--reg", query, "--columns", "variant_id, rs_ids, ref, alt, chrom, start, end", databaseName);

        BufferedReader reader = null;
        List<Variation> variations = new ArrayList<Variation>();
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

                List<String> identifiers = new ArrayList<String>();

                // add variantId as new identifier
                int variantId = Integer.parseInt(tokens[0]);
                identifiers.add(identifier(variantId, databaseName));
                
                // rs_ids is a comma-separated list of dbSNP ids
                if (tokens[1] != "null")
                {
                    identifiers.addAll(ImmutableList.copyOf(tokens[1].split(",")));
                }

                String ref = tokens[2];
                // todo: might have to collapse multiple rows with same ref?
                List<String> alt = ImmutableList.of(tokens[3]);

                // note: gemini puts chr on the region name
                String region = tokens[4].replace("chr", "");

                int start = Integer.parseInt(tokens[5]);
                int end = Integer.parseInt(tokens[6]);

                variations.add(new Variation(species, reference, identifiers, ref, alt, region, start, end));
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
        return variations;
    }

    @Override
    public String toString()
    {
        return "GEMINI variations (" + species + " " + reference + " " + databaseName + ")";
    }
}
