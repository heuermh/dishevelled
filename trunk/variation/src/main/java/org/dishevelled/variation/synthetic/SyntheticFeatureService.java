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
 */
public final class SyntheticFeatureService
    implements FeatureService
{
    private final SyntheticGenome genome;
    private final Random random = new Random();
    private final LoadingCache<String, Feature> features;

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
                    int length = genome.getLengths().get(name) == null ? 100000 : genome.getLengths().get(name);
                    int start = random.nextInt(length);
                    int end = start + random.nextInt(Math.min(length - start, 100000));

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