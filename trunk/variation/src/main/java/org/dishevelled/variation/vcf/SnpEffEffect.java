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
package org.dishevelled.variation.vcf;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.annotation.concurrent.Immutable;

/**
 * SnpEff effect.
 *
 * @author  Michael Heuer
 */
//EFF=Effect(Effect_Impact|Functional_Class|Codon_Change|Amino_Acid_change|Amino_Acid_length|Gene_Name|Gene_BioType|Coding|Transcript|Exon/Intron Rank|Genotype[|ERRORS|WARNINGS])
@Immutable
final class SnpEffEffect
{
    /** Effect. */
    private final String effect;

    /** Effect impact. */
    private final String effectImpact;

    /** Functional class. */
    private final String functionalClass;

    /** Codon change. */
    private final String codonChange;

    /** Amino acid change. */
    private final String aminoAcidChange;

    /** Amino acid length. */
    private final int aminoAcidLength;

    /** Gene name. */
    private final String geneName;

    /** Gene biotype. */
    private final String geneBioType;

    /** True if coding. */
    private final boolean coding;

    /** Transcript. */
    private final String transcript;

    /** Rank. */
    private final int rank;

    /** Genotype. */
    private final int genotype;


    /**
     * Create a new SnpEff effect.
     *
     * @param effect effect
     * @param effectImpact effect impact
     * @param functionalClass functional class
     * @param codonChange codon change
     * @param aminoAcidChange amino acid change
     * @param aminoAcidLength amino acid length
     * @param geneName gene name
     * @param geneBioType gene biotype
     * @param coding true if coding
     * @param transcript transcript
     * @param rank rank
     * @param genotype genotype
     */
    private SnpEffEffect(final String effect,
                         final String effectImpact,
                         final String functionalClass,
                         final String codonChange,
                         final String aminoAcidChange,
                         final int aminoAcidLength,
                         final String geneName,
                         final String geneBioType,
                         final boolean coding,
                         final String transcript,
                         final int rank,
                         final int genotype)
    {
        checkNotNull(effect);

        this.effect = effect;
        this.effectImpact = effectImpact;
        this.functionalClass = functionalClass;
        this.codonChange = codonChange;
        this.aminoAcidChange = aminoAcidChange;
        this.aminoAcidLength = aminoAcidLength;
        this.geneName = geneName;
        this.geneBioType = geneBioType;
        this.coding = coding;
        this.transcript = transcript;
        this.rank = rank;
        this.genotype = genotype;
    }


    /**
     * Return the effect for this SnpEff effect.
     *
     * @return the effect for this SnpEff effect
     */
    String getEffect()
    {
        return effect;
    }

    /**
     * Return the effect impact for this SnpEff effect.
     *
     * @return the effect impact for this SnpEff effect
     */
    String getEffectImpact()
    {
        return effectImpact;
    }

    /**
     * Return the functional class for this SnpEff effect.
     *
     * @return the functional class for this SnpEff effect
     */
    String getFunctionalClass()
    {
        return functionalClass;
    }

    /**
     * Return the codon change for this SnpEff effect.
     *
     * @return the codon change for this SnpEff effect
     */
    String getCodonChange()
    {
        return codonChange;
    }

    /**
     * Return the amino acid change for this SnpEff effect.
     *
     * @return the amino acid change for this SnpEff effect
     */
    String getAminoAcidChange()
    {
        return aminoAcidChange;
    }

    /**
     * Return the amino acid length for this SnpEff effect.
     *
     * @return the amino acid length for this SnpEff effect
     */
    int getAminoAcidLength()
    {
        return aminoAcidLength;
    }

    /**
     * Return the gene name for this SnpEff effect.
     *
     * @return the gene name for this SnpEff effect
     */
    String getGeneName()
    {
        return geneName;
    }

    /**
     * Return the gene biotype for this SnpEff effect.
     *
     * @return the gene biotype for this SnpEff effect
     */
    String getGeneBioType()
    {
        return geneBioType;
    }

    /**
     * Return true if coding.
     *
     * @return true if coding
     */
    boolean isCoding()
    {
        return coding;
    }

    /**
     * Return the transcript for this SnpEff effect.
     *
     * @return the transcript for this SnpEff effect
     */
    String getTranscript()
    {
        return transcript;
    }

    /**
     * Return the rank for this SnpEff effect.
     *
     * @return the rank for this SnpEff effect
     */
    int getRank()
    {
        return rank;
    }

    /**
     * Return the genotype for this SnpEff effect.
     *
     * @return the genotype for this SnpEff effect
     */
    int getGenotype()
    {
        return genotype;
    }

    /**
     * Parse the specified token into a SnpEff effect.
     *
     * @param token token to parse
     * @return the SnpEff effect parsed from the specified token
     * @throws IOException if an I/O error occurs
     */
    static SnpEffEffect parse(final String token) throws IOException
    {
        // consider using guava Splitter
        String effect = token.substring(0, token.indexOf("("));
        String[] effectTokens = token.substring(token.indexOf("("), token.indexOf(")")).split("|");
        String effectImpact = effectTokens[0];
        String functionalClass = effectTokens[1];
        String codonChange = effectTokens[2];
        String aminoAcidChange = effectTokens[3];
        int aminoAcidLength = safeParseInt(effectTokens[4], -1);
        String geneName = effectTokens[5];
        String geneBioType = effectTokens[6];
        boolean coding = "CODING".equals(effectTokens[7]);
        String transcript = effectTokens[8];
        int rank = safeParseInt(effectTokens[9], -1);
        int genotype = safeParseInt(effectTokens[10], 0);

        return new SnpEffEffect(effect,
                                effectImpact,
                                functionalClass,
                                codonChange,
                                aminoAcidChange,
                                aminoAcidLength,
                                geneName,
                                geneBioType,
                                coding,
                                transcript,
                                rank,
                                genotype);
    }

    /**
     * Parse the specified value as an integer.
     *
     * @param value value to parse
     * @param defaultvalue default value
     * @return the specified value parsed as an interger, or <code>defaultValue</code>
     */
    static int safeParseInt(final String value, final int defaultValue)
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            // ignore
        }
        return defaultValue;
    }
}
