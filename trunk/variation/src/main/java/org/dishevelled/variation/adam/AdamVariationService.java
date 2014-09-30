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
package org.dishevelled.variation.adam;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import com.google.common.io.Files;

import org.apache.commons.io.FileUtils;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import org.apache.hadoop.fs.Path;

import org.bdgenomics.formats.avro.Genotype;
import org.bdgenomics.formats.avro.Variant;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parquet.avro.AvroParquetReader;

/**
 * Implementation of variation service APIs via ADAM.
 */
public final class AdamVariationService implements VariationService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** ADAM parquet file or directory of files. */
    private final String filePath;

    /** Parquet <code>.part</code> file paths. */
    private final List<Path> paths;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(AdamVariationService.class);


    /**
     * Create a new ADAM variation service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param file ADAM parquet file or directory of files, must not be null
     */
    public AdamVariationService(final String species, final String reference, final String filePath)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(filePath);

        this.species = species;
        this.reference = reference;
        this.filePath = filePath;

        paths = new ArrayList<Path>();
        File root = new File(filePath);
        //logger.info("root " + root + " exists " + root.exists() + " is directory " + root.isDirectory());

        paths.add(new Path(new File(root, "part-r-00000.gz.parquet").toURI()));
        /*

          why don't these work?

        for (File file : FileUtils.listFiles(root, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE))
        {
            logger.info("found file " + file);
            if (file.getName().contains("part"))
            {
                logger.info("found part file " + file);
                paths.add(new Path(file.toURI()));
            }
        }

        for (File file : FileUtils.listFiles(root, new WildcardFileFilter("*part*"), TrueFileFilter.INSTANCE))
        {
            logger.info("found part file " + file);
            paths.add(new Path(file.toURI()));
        }

        for (File file : Files.fileTreeTraverser().children(root))
        {
            logger.info("found file " + file);
            if (file.getName().contains("part"))
            {
                logger.info("found part file " + file);
                paths.add(new Path(file.toURI()));
            }
        }
        */
    }


    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));

        // adam genotypes denormalize genotype --> variant relationship, must find unique variants
        final Set<Variation> variations = new HashSet<Variation>();
        try
        {
            //logger.info("paths = " + paths);
            for (Path path : paths)
            {
                AvroParquetReader<Genotype> parquetReader = new AvroParquetReader<Genotype>(path);
                while (true)
                {
                    Genotype genotype = parquetReader.read();
                    //logger.info("genotype = " + genotype);
                    if (genotype == null)
                    {
                        break;
                    }
                    //logger.info("variant = " + genotype.getVariant());
                    variations.add(convert(genotype.getVariant()));
                }
                parquetReader.close();
            }
        }
        catch (IOException e)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("unable to find variations for region {}:{}-{}:{} for species {}, caught {}", feature.getRegion(), feature.getStart(), feature.getEnd(), feature.getStrand(), species, e.getMessage());
            }
        }
        return ImmutableList.copyOf(variations);
    }


    /**
     * Convert the specified ADAM variant to a variation.
     *
     * @param variant variant to convert, must not be null
     * @return the specified ADAM variant converted to a variation
     * @throws IOException if an I/O error occurs
     */
    Variation convert(Variant variant) throws IOException
    {
        checkNotNull(variant);
        if (variant.getContig() == null)
        {
            throw new IOException("could not convert ADAM variant, contig must not be null");
        }
        String species = toString(variant.getContig().getSpecies());
        String reference = toString(variant.getContig().getAssembly());
        List<String> identifiers = Collections.emptyList();
        String ref = toString(variant.getReferenceAllele());
        List<String> alt = ImmutableList.of(toString(variant.getAlternateAllele()));
        String region = toString(variant.getContig().getContigName());
        // ADAM is 0-based, closed-open interval
        long start = variant.getStart() + 1L;
        long end = variant.getEnd();
        return new Variation(species, reference, identifiers, ref, alt, region, start, end);
    }

    static String toString(final CharSequence charSequence)
    {
        return charSequence == null ? "null" : charSequence.toString();
    }

    static List<String> toStringList(final List<CharSequence> charSequences)
    {
        if (charSequences == null || charSequences.isEmpty())
        {
            return Collections.emptyList();
        }
        List<String> strings = Lists.newArrayListWithExpectedSize(charSequences.size());
        for (CharSequence charSequence : charSequences)
        {
            strings.add(charSequence.toString());
        }
        return ImmutableList.copyOf(strings);
    }
}
