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

import com.google.common.base.Charsets;

import com.google.common.collect.ImmutableList;

import com.google.common.io.Files;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

/**
 * VCF file variation service.
 */
public final class VcfVariationService implements VariationService
{
    private final String species;
    private final String reference;
    private final File file;


    public VcfVariationService(final String species, final String reference, final File file)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(file);

        this.species = species;
        this.reference = reference;
        this.file = file;
    }


    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));

        // ick.  re-streams file every time
        final List<Variation> variations = new ArrayList<Variation>();
        try
        {
            // todo: need to transparently handle vcf.gz files
            VcfReader.stream(Files.newReaderSupplier(file, Charsets.UTF_8), new VcfStreamListener()
                {
                    @Override
                    public void record(final VcfRecord record)
                    {
                        if (feature.getRegion().equals(record.getChrom()) && feature.getStart() <= record.getPos() && feature.getEnd() >= record.getPos())
                        {
                            List<String> identifiers = ImmutableList.copyOf(record.getId());
                            String ref = record.getRef();
                            List<String> alt = ImmutableList.copyOf(record.getAlt());
                            String region = record.getChrom();
                            int position = record.getPos();
                            int start = position - 1;
                            int end = start + ref.length();

                            // todo: only add variation if sample matches some query and any genotype is not reference; modify alt alleles if so
                            variations.add(new Variation(species, reference, identifiers, ref, alt, region, start, end));
                        }
                    }
                });
        }
        catch (IOException e)
        {
            // todo
        }
        return variations;
    }
}