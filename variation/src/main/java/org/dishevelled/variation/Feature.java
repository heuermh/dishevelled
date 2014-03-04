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

import com.google.common.base.Objects;

/**
 * Feature.
 */
public final class Feature
{
    private final String species;
    private final String reference;
    private final String identifier; // gene_id
    private final String region;
    private final int start;
    private final int end;
    private final int strand;
    private final int hashCode;

    public Feature(final String species,
                   final String reference,
                   final String identifier,
                   final String region,
                   final int start,
                   final int end,
                   final int strand)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(identifier);
        checkNotNull(region);

        this.species = species;
        this.reference = reference;
        this.identifier = identifier;
        this.region = region;
        this.start = start;
        this.end = end;
        this.strand = strand;

        hashCode = Objects.hashCode(this.species, this.reference, this.identifier, this.region, this.start, this.end, this.strand);
    }


    public String getSpecies()
    {
        return species;
    }

    public String getReference()
    {
        return reference;
    }

    public String getIdentifier()
    {
        return identifier;
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

    public int getStrand()
    {
        return strand;
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
        if (!(o instanceof Feature))
        {
            return false;
        }
        Feature feature = (Feature) o;

        return Objects.equal(species, feature.species)
            && Objects.equal(reference, feature.reference)
            && Objects.equal(identifier, feature.identifier)
            && Objects.equal(region, feature.region)
            && Objects.equal(start, feature.start)
            && Objects.equal(end, feature.end)
            && Objects.equal(strand, feature.strand);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(region);
        sb.append(":");
        sb.append(start);
        sb.append("-");
        sb.append(end);
        sb.append(":");
        sb.append(strand);
        return sb.toString();
    }
}