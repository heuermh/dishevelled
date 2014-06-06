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

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Objects;

import com.google.common.collect.ImmutableList;

/**
 * Variation.
 *
 * @author  Michael Heuer
 */
@Immutable
public final class Variation
{
    /** Species, e.g. <code>"human"</code>. */
    private final String species;

    /** Reference, e.g. <code>"GRCh37"</code>. */
    private final String reference;

    /** List of identifers, e.g. <code>"rs193189309"</code>. */
    private final List<String> identifiers; // e.g. dbSNP id

    /** Reference allele. */
    private final String referenceAllele;

    /** List of alternate alleles. */
    private final List<String> alternateAlleles;

    /** Region or contig, using Ensembl-style names, e.g. <code>"1"</code>. */
    private final String region;

    // todo: confirm 1-based; always on 1/+/positive/forward strand
    /** Variation start, using base-counted, one-start (a.k.a. one-based, fully-closed) coordinate system. */
    private final int start;

    /** Variation end, using base-counted, one-start (a.k.a. one-based, fully-closed) coordinate system. */
    private final int end;

    /** Cached hash code. */
    private final int hashCode;


    /**
     * Create a new variation.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param identifiers list of identifiers, must not be null
     * @param referenceAllele reference allele, must not be null
     * @param alternateAlleles list of alternate alleles, must not be null
     * @param region region, must not be null
     * @param start start, using base-counted, one start coordinate system
     * @param end end, using base-counted, one start coordinate system
     */
    public Variation(final String species,
                     final String reference,
                     final List<String> identifiers,
                     final String referenceAllele,
                     final List<String> alternateAlleles,
                     final String region,
                     final int start,
                     final int end)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(identifiers);
        checkNotNull(referenceAllele);
        checkNotNull(alternateAlleles);
        checkNotNull(region);

        this.species = species;
        this.reference = reference;
        this.identifiers = ImmutableList.copyOf(identifiers);
        this.referenceAllele = referenceAllele;
        this.alternateAlleles = ImmutableList.copyOf(alternateAlleles);
        this.region = region;
        this.start = start;
        this.end = end;

        hashCode = Objects.hashCode(this.species, this.reference, this.identifiers, this.referenceAllele, this.alternateAlleles, this.region, this.start, this.end);
    }


    /**
     * Return the species for this variation.
     *
     * @return the species for this variation
     */
    public String getSpecies()
    {
        return species;
    }

    /**
     * Return the reference for this variation.
     *
     * @return the reference for this variation
     */
    public String getReference()
    {
        return reference;
    }

    /**
     * Return the list of identifiers for this variation.
     *
     * @return the list of identifiers for this variation
     */
    public List<String> getIdentifiers()
    {
        return identifiers;
    }

    /**
     * Return the reference allele for this variation.
     *
     * @return the reference allele for this variation
     */
    public String getReferenceAllele()
    {
        return referenceAllele;
    }

    /**
     * Return the list of alternate alleles for this variation.
     *
     * @return the list of alternate alleles for this variation
     */
    public List<String> getAlternateAlleles()
    {
        return alternateAlleles;
    }

    /**
     * Return the region for this variation.
     *
     * @return the region for this variation
     */
    public String getRegion()
    {
        return region;
    }

    /**
     * Return the start for this variation.
     *
     * @return the start for this variation
     */
    public int getStart()
    {
        return start;
    }

    /**
     * Return the end for this variation.
     *
     * @return the end for this variation
     */
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
        if (!(o instanceof Variation))
        {
            return false;
        }
        Variation variation = (Variation) o;

        return Objects.equal(species, variation.species)
            && Objects.equal(reference, variation.reference)
            && Objects.equal(identifiers, variation.identifiers)
            && Objects.equal(referenceAllele, variation.referenceAllele)
            && Objects.equal(alternateAlleles, variation.alternateAlleles)
            && Objects.equal(region, variation.region)
            && Objects.equal(start, variation.start)
            && Objects.equal(end, variation.end);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if (identifiers.size() == 1)
        {
            sb.append(identifiers.get(0));
        }
        else if (identifiers.size() > 1)
        {
            sb.append(identifiers);
        }
        sb.append(" ");
        sb.append(region);
        sb.append(":");
        sb.append(start);
        sb.append("-");
        sb.append(end);
        sb.append(" ");
        sb.append(referenceAllele);
        sb.append(">");
        if (alternateAlleles.size() == 1)
        {
            sb.append(alternateAlleles.get(0));
        }
        else
        {
            sb.append(alternateAlleles);
        }
        return sb.toString();
    }
}
