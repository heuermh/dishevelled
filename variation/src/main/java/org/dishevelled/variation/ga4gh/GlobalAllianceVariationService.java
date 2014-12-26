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
package org.dishevelled.variation.ga4gh;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

import org.ga4gh.models.Variant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global Alliance for Genomics and Health (GA4GH) API variation service.
 *
 * @author  Michael Heuer
 */
public final class GlobalAllianceVariationService implements VariationService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(GlobalAllianceVariationService.class);


    /**
     * Create a new Global Alliance for Genomics and Health (GA4GH) API variation service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     */
    public GlobalAllianceVariationService(final String species,
                                          final String reference)
    {
        checkNotNull(species);
        checkNotNull(reference);
        this.species = species;
        this.reference = reference;
    }


    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));

        List<Variation> variations = Lists.newArrayList();
        // todo
        return variations;
    }

    Variation convert(final Variant variant)
    {
        checkNotNull(variant);
        List<String> identifiers = toStringList(variant.getNames());
        String ref = toString(variant.getReferenceBases());
        List<String> alt = toStringList(variant.getAlternateBases());
        String region = toString(variant.getReferenceName());
        long start = variant.getStart() + 1L; // GA4GH Variant is 0-based, closed-open interval
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
