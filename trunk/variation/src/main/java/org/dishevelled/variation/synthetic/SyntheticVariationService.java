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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.google.common.collect.ImmutableList;

import com.google.inject.Inject;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

/**
 * Synthetic variation service.
 *
 * @author  Michael Heuer
 */
public final class SyntheticVariationService
    implements VariationService
{
    /** Synthetic genome. */
    private final SyntheticGenome genome;

    /** Source of randomness. */
    private final Random random = new Random();

    /** Cache of variations keyed by feature. */
    private final LoadingCache<Feature, List<Variation>> variations;

    /** List of valid symbols. */
    private static final List<String> ALPHABET = ImmutableList.of("A", "T", "C", "G");


    /**
     * Create a new synthetic variation service.
     *
     * @param genome synthetic genome
     */
    @Inject
    public SyntheticVariationService(final SyntheticGenome genome)
    {
        checkNotNull(genome);
        this.genome = genome;
        this.variations = CacheBuilder.newBuilder().build(new CacheLoader<Feature, List<Variation>>()
            {
                @Override
                public List<Variation> load(final Feature feature)
                {
                    List<Variation> variations = new ArrayList<Variation>();
                    for (int i = 0, size = (feature.getEnd() - feature.getStart()) / 1000; i < size; i++)
                    {
                        String referenceAllele = sample();
                        List<String> alternateAlleles = new ArrayList<String>();
                        alternateAlleles.add(sample(referenceAllele));
                        if (random.nextDouble() < 0.3)
                        {
                            alternateAlleles.add(sample(referenceAllele, alternateAlleles));
                        }
                        if (random.nextDouble() < 0.1)
                        {
                            alternateAlleles.add(sample(referenceAllele, alternateAlleles));
                        }
                        int start = feature.getStart() + random.nextInt(feature.getEnd() - feature.getStart());
                        int end = start + referenceAllele.length();
                        variations.add(new Variation(genome.getSpecies(), genome.getReference(), Collections.<String>emptyList(), referenceAllele, alternateAlleles, feature.getRegion(), start, end));
                    }
                    return variations;
                }
            });
    }

    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(genome.getSpecies().equals(feature.getSpecies()));
        checkArgument(genome.getReference().equals(feature.getReference()));
        return variations.getUnchecked(feature);
    }

    private String sample()
    {
        return ALPHABET.get(random.nextInt(ALPHABET.size()));
    }

    private String sample(final String ref)
    {
        String alt = sample();
        while (ref.equals(alt))
        {
            alt = sample();
        }
        return alt;
    }

    private String sample(final String ref, final List<String> existing)
    {
        String alt = sample(ref);
        while (ref.equals(alt) || existing.contains(alt))
        {
            alt = sample(ref);
        }
        return alt;
    }

    @Override
    public String toString()
    {
        return "Synthetic variations (" + genome.getSpecies() + " " + genome.getReference() + ")";
    }
}
