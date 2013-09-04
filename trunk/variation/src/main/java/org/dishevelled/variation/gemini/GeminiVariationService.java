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
import org.dishevelled.variation.VariationService;

/**
 * GEMINI command line variation service.
 */
final class GeminiVariationService implements VariationService
{
    private final String species;
    private final String reference;
    private final String databaseName;

    //@Inject
    GeminiVariationService(final String species, final String reference, final String databaseName)
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

        String region = feature.getName() + ":" + feature.getStart() + "-" + feature.getEnd();
        ProcessBuilder processBuilder = new ProcessBuilder("gemini", "region", "--reg", region, "--columns", "variant_id, rs_ids, ref, alt, chrom, start, end", databaseName);

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
                String variantId = tokens[0];
                // todo: rs_ids is a comma-separated list of dbSNP ids
                String identifier = tokens[1] == "null" ? null : tokens[1];
                String ref = tokens[2];
                // todo: might have to collapse multiple rows with same ref?
                List<String> alt = ImmutableList.of(tokens[3]);
                String name = tokens[4];
                int start = Integer.parseInt(tokens[5]);
                int end = Integer.parseInt(tokens[6]);
                //int strand = Integer.parseInt(tokens[7]);
                int strand = 1;

                variations.add(new Variation(species, reference, identifier, ref, alt, name, start, end, strand));
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
        return variations;
    }

    @Override
    public String toString()
    {
        return "GEMINI variations (" + species + " " + reference + " " + databaseName + ")";
    }
}
