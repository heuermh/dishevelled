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

/**
 * SnpEff effect.
 */
//EFF=Effect(Effect_Impact|Functional_Class|Codon_Change|Amino_Acid_change|Amino_Acid_length|Gene_Name|Gene_BioType|Coding|Transcript|Exon/Intron Rank|Genotype[|ERRORS|WARNINGS])
final class SnpEffEffect
{
    private final String effect;
    private final String effectImpact;
    private final String functionalClass;
    private final String codonChange;
    private final String aminoAcidChange;
    private final int aminoAcidLength;
    private final String geneName;
    private final String geneBioType;
    private final boolean coding;
    private final String transcript;
    private final int rank;
    private final int genotype;

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


    String getEffect()
    {
        return effect;
    }

    String getEffectImpact()
    {
        return effectImpact;
    }

    String getFunctionalClass()
    {
        return functionalClass;
    }

    String getCodonChange()
    {
        return codonChange;
    }

    String getAminoAcidChange()
    {
        return aminoAcidChange;
    }

    int getAminoAcidLength()
    {
        return aminoAcidLength;
    }

    String getGeneName()
    {
        return geneName;
    }

    String getGeneBioType()
    {
        return geneBioType;
    }

    boolean isCoding()
    {
        return coding;
    }

    String getTranscript()
    {
        return transcript;
    }

    int getRank()
    {
        return rank;
    }

    int getGenotype()
    {
        return genotype;
    }

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