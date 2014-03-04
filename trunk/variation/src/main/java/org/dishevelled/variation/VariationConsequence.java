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
package org.dishevelled.variation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.base.Objects;

import com.google.common.collect.ImmutableList;

/**
 * Variation consequence.
 */
// flattens variation by alternate allele and variation consequence by consequence term
public final class VariationConsequence
{
    private final String species;
    private final String reference;
    private final List<String> identifiers; // e.g. dbSNP id
    private final String referenceAllele;
    private final String alternateAllele;
    private final String sequenceOntologyTerm;
    private final String region; // seq_region_name
    private final int start;
    private final int end;
    private final int hashCode;

    public VariationConsequence(final String species,
                                final String reference,
                                final List<String> identifiers,
                                final String referenceAllele,
                                final String alternateAllele,
                                final String sequenceOntologyTerm,
                                final String region,
                                final int start,
                                final int end)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(identifiers);
        checkNotNull(referenceAllele);
        checkNotNull(alternateAllele);
        checkNotNull(sequenceOntologyTerm);
        checkNotNull(region);

        this.species = species;
        this.reference = reference;
        this.identifiers = ImmutableList.copyOf(identifiers);
        this.referenceAllele = referenceAllele;
        this.alternateAllele = alternateAllele;
        this.sequenceOntologyTerm = sequenceOntologyTerm;
        this.region = region;
        this.start = start;
        this.end = end;

        hashCode = Objects.hashCode(this.species, this.reference, this.identifiers, this.referenceAllele, this.alternateAllele, this.sequenceOntologyTerm, this.region, this.start, this.end);
    }

    public String getSpecies()
    {
        return species;
    }

    public String getReference()
    {
        return reference;
    }

    public List<String> getIdentifiers()
    {
        return identifiers;
    }

    public String getReferenceAllele()
    {
        return referenceAllele;
    }

    public String getAlternateAllele()
    {
        return alternateAllele;
    }

    public String getSequenceOntologyTerm()
    {
        return sequenceOntologyTerm;
    }

    public String getRegion()
    {
        return region;
    }

    public int getStart()
    {
        return start;
    }

    public int getEnd()
    {
        return end;
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof VariationConsequence))
        {
            return false;
        }
        VariationConsequence variationConsequence = (VariationConsequence) o;

        return Objects.equal(species, variationConsequence.species)
            && Objects.equal(reference, variationConsequence.reference)
            && Objects.equal(identifiers, variationConsequence.identifiers)
            && Objects.equal(referenceAllele, variationConsequence.referenceAllele)
            && Objects.equal(alternateAllele, variationConsequence.alternateAllele)
            && Objects.equal(sequenceOntologyTerm, variationConsequence.sequenceOntologyTerm)
            && Objects.equal(region, variationConsequence.region)
            && Objects.equal(start, variationConsequence.start)
            && Objects.equal(end, variationConsequence.end);
    }
}