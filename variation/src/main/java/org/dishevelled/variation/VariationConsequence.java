/*

    dsh-variation  Variation.
    Copyright (c) 2013-2015 held jointly by the individual authors.

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
 * Variation consequence, flattens variation by alternate allele and consequence term.
 *
 * @author  Michael Heuer
 */
@Immutable
public final class VariationConsequence
{
    /** Species, e.g. <code>"human"</code>. */
    private final String species;

    /** Reference, e.g. <code>"GRCh37"</code>. */
    private final String reference;

    /** List of identifers, e.g. <code>"rs193189309"</code>. */
    private final List<String> identifiers; // e.g. dbSNP id

    /** Reference allele. */
    private final String referenceAllele;

    /** Alternate allele. */
    private final String alternateAllele;

    /** Region or contig, using Ensembl-style names, e.g. <code>"1"</code>. */
    private final String region;

    /** Variation start, using interallele, zero-start (a.k.a. zero-alleled, closed-open) coordinate system. */
    private final long start;

    /** Variation end, using interallele, zero-start (a.k.a. zero-alleled, closed-open) coordinate system. */
    private final long end;

    /** Sequence Ontology (SO) term. */
    private final String sequenceOntologyTerm;

    /** Cached hash code. */
    private final int hashCode;


    /**
     * Create a new variation consequence.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param identifiers list of identifiers, must not be null
     * @param referenceAllele reference allele, must not be null
     * @param alternateAllele alternate allele, must not be null
     * @param region region, must not be null
     * @param start start, using base-counted, one start coordinate system
     * @param end end, using base-counted, one start coordinate system
     * @param sequenceOntologyTerm Sequence Ontology (SO) term, must not be null
     */
    public VariationConsequence(final String species,
                                final String reference,
                                final List<String> identifiers,
                                final String referenceAllele,
                                final String alternateAllele,
                                final String region,
                                final long start,
                                final long end,
                                final String sequenceOntologyTerm)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(identifiers);
        checkNotNull(referenceAllele);
        checkNotNull(alternateAllele);
        checkNotNull(region);
        checkNotNull(sequenceOntologyTerm);

        this.species = species;
        this.reference = reference;
        this.identifiers = ImmutableList.copyOf(identifiers);
        this.referenceAllele = referenceAllele;
        this.alternateAllele = alternateAllele;
        this.region = region;
        this.start = start;
        this.end = end;
        this.sequenceOntologyTerm = sequenceOntologyTerm;

        hashCode = Objects.hashCode(this.species, this.reference, this.identifiers, this.referenceAllele, this.alternateAllele, this.region, this.start, this.end, this.sequenceOntologyTerm);
    }


    /**
     * Return the species for this variation consequence.
     *
     * @return the species for this variation consequence
     */
    public String getSpecies()
    {
        return species;
    }

    /**
     * Return the reference for this variation consequence.
     *
     * @return the reference for this variation consequence
     */
    public String getReference()
    {
        return reference;
    }

    /**
     * Return the list of identifiers for this variation consequence.
     *
     * @return the list of identifiers for this variation consequence
     */
    public List<String> getIdentifiers()
    {
        return identifiers;
    }

    /**
     * Return the reference allele for this variation consequence.  The bases representing the
     * reference allele start at position {@link #start()}.
     *
     * @return the reference allele for this variation consequence
     */
    public String getReferenceAllele()
    {
        return referenceAllele;
    }

    /**
     * Return the alternate allele for this variation consequence.
     *
     * @return the alternate allele for this variation consequence
     */
    public String getAlternateAllele()
    {
        return alternateAllele;
    }

    /**
     * Return the region for this variation consequence.
     *
     * @return the region for this variation consequence
     */
    public String getRegion()
    {
        return region;
    }

    /**
     * Return the start of this variation consequence using interbase, zero-start (a.k.a. zero-alleled,
     * closed-open) coordinate system.
     *
     * <p>This corresponds to the first base of the string of bases representing the reference
     * allele. Variations spanning the join of circular genomes are represented as two variations,
     * one on each side of the join (position <code>0</code>).</p>
     *
     * @return the start of this variation consequence
     */
    public long getStart()
    {
        return start;
    }

    /**
     * Return the end (exclusive) of this variation consequence using interbase, zero-start (a.k.a. zero-alleled,
     * closed-open) coordinate system.
     *
     * <p>Results in <code>[start, end)</code> closed-open interval.  This is typically calculated by
     * <code>start + referenceAllele.length</code>.</p>
     *
     * @return the end of this variation consequence
     */
    public long getEnd()
    {
        return end;
    }

    /**
     * Return the Sequence Ontology (SO) term for this variation consequence.
     *
     * @return the Sequence Ontology (SO) term for this variation consequence
     */
    public String getSequenceOntologyTerm()
    {
        return sequenceOntologyTerm;
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
            && Objects.equal(region, variationConsequence.region)
            && Objects.equal(start, variationConsequence.start)
            && Objects.equal(end, variationConsequence.end)
            && Objects.equal(sequenceOntologyTerm, variationConsequence.sequenceOntologyTerm);
    }
}
