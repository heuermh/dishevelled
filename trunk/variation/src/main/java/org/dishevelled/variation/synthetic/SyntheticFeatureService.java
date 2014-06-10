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

import java.util.Random;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.google.inject.Inject;

import org.apache.commons.lang.StringUtils;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;

/**
 * Synthetic feature service.
 *
 * @author  Michael Heuer
 */
public final class SyntheticFeatureService
    implements FeatureService
{
    /** Synthetic genome. */
    private final SyntheticGenome genome;

    /** Source of randomness. */
    private final Random random = new Random();

    /** Cache of features keyed by identifier. */
    private final LoadingCache<String, Feature> features;


    /**
     * Create a new synthetic feature service.
     *
     * @param genome synthetic genome, must not be null
     */
    @Inject
    public SyntheticFeatureService(final SyntheticGenome genome)
    {
        checkNotNull(genome);
        this.genome = genome;
        features = CacheBuilder.newBuilder().build(new CacheLoader<String, Feature>()
            {
                @Override
                public Feature load(final String identifier)
                {
                    String name = genome.getNames().get(random.nextInt(genome.getNames().size()));
                    long length = genome.getLengths().get(name) == null ? 100000L : genome.getLengths().get(name);
                    long start = Double.valueOf(random.nextDouble() * length).longValue();
                    long end = start + Double.valueOf(random.nextDouble() * Math.min(length - start, 100000L)).longValue();

                    return new Feature(genome.getSpecies(), genome.getReference(), identifier, name, start, end, 1);
                }
            });
    }


    @Override
    public Feature feature(final String species, final String reference, final String identifier)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(identifier);
        checkArgument(genome.getSpecies().equals(species));
        checkArgument(genome.getReference().equals(reference));

        if (StringUtils.isBlank(identifier))
        {
            return null;
        }
        if (!identifier.startsWith("ENS")) // todo: add regex ENSG, ENSMUSG, etc.
        {
            return null;
        }
        return features.getUnchecked(identifier);
    }

    @Override
    public String toString()
    {
        return "Synthetic features (" + genome.getSpecies() + " " + genome.getReference() + ")";
    }
}
