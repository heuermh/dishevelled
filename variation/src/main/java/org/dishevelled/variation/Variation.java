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
package org.dishevelled.variation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Variation.
 */
public final class Variation
{
    private final String species;
    private final String reference;
    private final String identifier; // id
    private final String referenceAllele;
    private final List<String> alternateAlleles;
    private final String name; // seq_region_name
    private final int start;
    private final int end;
    private final int strand;

    public Variation(final String species,
                     final String reference,
                     final String identifier,
                     final String referenceAllele,
                     final List<String> alternateAlleles,
                     final String name,
                     final int start,
                     final int end,
                     final int strand)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(referenceAllele);
        checkNotNull(alternateAlleles);
        checkNotNull(name);

        this.species = species;
        this.reference = reference;
        this.identifier = identifier;
        this.referenceAllele = referenceAllele;
        this.alternateAlleles = ImmutableList.copyOf(alternateAlleles);
        this.name = name;
        this.start = start;
        this.end = end;
        this.strand = strand;
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

    public String getReferenceAllele()
    {
        return referenceAllele;
    }

    public List<String> getAlternateAlleles()
    {
        return alternateAlleles;
    }

    public String getName()
    {
        return name;
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
}