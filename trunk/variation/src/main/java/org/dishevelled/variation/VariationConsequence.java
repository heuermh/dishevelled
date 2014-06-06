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

    /** Alternate alleles. */
    private final String alternateAllele;

    /** Sequence Ontology (SO) term. */
    private final String sequenceOntologyTerm;

    /** Region or contig, using Ensembl-style names, e.g. <code>"1"</code>. */
    private final String region; // seq_region_name

    // todo: confirm 1-based; always on 1/+/positive/forward strand
    /** Variation start, using base-counted, one-start (a.k.a. one-based, fully-closed) coordinate system. */
    private final int start;

    /** Variation end, using base-counted, one-start (a.k.a. one-based, fully-closed) coordinate system. */
    private final int end;

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
     * @param sequenceOntologyTerm Sequence Ontology (SO) term, must not be null
     * @param region region, must not be null
     * @param start start, using base-counted, one start coordinate system
     * @param end end, using base-counted, one start coordinate system
     */
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
     * Return the reference allele for this variation consequence.
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
     * Return the Sequence Ontology (SO) term for this variation consequence.
     *
     * @return the Sequence Ontology (SO) term for this variation consequence
     */
    public String getSequenceOntologyTerm()
    {
        return sequenceOntologyTerm;
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
     * Return the start for this variation consequence.
     *
     * @return the start for this variation consequence
     */
    public int getStart()
    {
        return start;
    }

    /**
     * Return the end for this variation consequence.
     *
     * @return the end for this variation consequence
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
