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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

/**
 * Synthetic variation service.
 */
final class SyntheticVariationService
    implements VariationService
{
    private final SyntheticGenome genome;
    private final Random random = new Random();

    SyntheticVariationService(final SyntheticGenome genome)
    {
        checkNotNull(genome);
        this.genome = genome;
    }

    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(genome.getSpecies().equals(feature.getSpecies()));
        checkArgument(genome.getReference().equals(feature.getReference()));

        List<Variation> variations = new ArrayList<Variation>();
        for (int i = 0, size = (feature.getEnd() - feature.getStart())/1000; i < size; i++)
        {
            String referenceAllele = "A";
            List<String> alternateAlleles = ImmutableList.of("T");
            int start = feature.getStart() + random.nextInt(feature.getEnd() - feature.getStart());
            variations.add(new Variation(genome.getSpecies(), genome.getReference(), null, referenceAllele, alternateAlleles, feature.getName(), start, start, 1));
        }
        return variations;
    }
}