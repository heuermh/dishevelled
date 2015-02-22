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
package org.dishevelled.variation.vcf;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.compress.Readers.reader;

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

import org.nmdp.ngs.variant.vcf.VcfReader;
import org.nmdp.ngs.variant.vcf.VcfRecord;
import org.nmdp.ngs.variant.vcf.VcfStreamAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VCF file variation service.
 *
 * @author  Michael Heuer
 */
public final class VcfVariationService implements VariationService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** VCF file. */
    private final File file;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(VcfVariationService.class);


    /**
     * Create a new VCF file variation service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param file VCF file, must not be null
     */
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
            VcfReader.stream(reader(file), new VcfStreamAdapter()
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
                            long position = record.getPos();
                            long start = position - 1L;
                            long end = start + ref.length();

                            // todo: only add variation if sample matches some query and any genotype is not reference; modify alt alleles if so
                            variations.add(new Variation(species, reference, identifiers, ref, alt, region, start, end));
                        }
                    }
                });
        }
        catch (IOException e)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("unable to find variations for region {}:{}-{}:{} for species {}, caught {}", feature.getRegion(), feature.getStart(), feature.getEnd(), feature.getStrand(), species, e.getMessage());
            }
        }
        return variations;
    }
}
