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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;

import com.google.common.collect.ImmutableList;

import com.google.common.io.Files;

import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequenceService;

/**
 * VEP-annotated VCF file variation consequence service.
 */
public final class VepVcfVariationConsequenceService implements VariationConsequenceService
{
    private final String species;
    private final String reference;
    private final File file;


    public VepVcfVariationConsequenceService(final String species, final String reference, final File file)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(file);

        this.species = species;
        this.reference = reference;
        this.file = file;
    }


    @Override
    public List<VariationConsequence> consequences(final Variation variation)
    {
        checkNotNull(variation);
        checkArgument(species.equals(variation.getSpecies()));
        checkArgument(reference.equals(variation.getReference()));

        // ick.  re-streams file every time
        final List<VariationConsequence> consequences = new ArrayList<VariationConsequence>();
        try
        {
            // todo: need to transparently handle vcf.gz files
            VcfReader.stream(Files.newReaderSupplier(file, Charsets.UTF_8), new VcfStreamListener()
                {
                    @Override
                    public void record(final VcfRecord record)
                    {
                        if (sameVariation(variation, record))
                        {
                            List<String> identifiers = ImmutableList.copyOf(record.getId());
                            String ref = record.getRef();
                            List<String> alt = ImmutableList.copyOf(record.getAlt());
                            String region = record.getChrom();
                            int position = record.getPos();
                            int start = position - 1;
                            int end = start + ref.length();

                            /*

                              format is included in Description field
                              these are the default columns plus --canonical command line argument
                              consequence terms are separated by '&' character

##INFO=<ID=CSQ,Number=.,Type=String,Description="Consequence type as predicted by VEP. Format: Allele|Gene|Feature|Feature_type|Consequence|cDNA_position|CDS_position|Protein_position|Amino_acids|Codons|Existing_variation|DISTANCE|CANONICAL">
                             */

                            // pull VEP CSQ from info, then for each canonical transcript alt/consequence term pair, add a new variation consequence
                            if (record.getInfo().containsKey("CSQ"))
                            {
                                String[] csqTokens = record.getInfo().get("CSQ").split(",");
                                for (String csqToken : csqTokens)
                                {
                                    try
                                    {
                                        VepConsequence consequence = VepConsequence.parse(csqToken); // might also need to provide the INFO meta line
                                        if (consequence.isCanonical())
                                        {
                                            // note: vep reports the alleles differently; e.g. for ref=T alt=TA vep reports allele=A
                                            //String altAllele = alt.get(effect.getGenotype());
                                            String altAllele = consequence.getAllele();
                                            for (String sequenceOntologyTerm : consequence.getConsequenceTerms())
                                            {
                                                consequences.add(new VariationConsequence(variation.getSpecies(),
                                                                                          variation.getReference(),
                                                                                          variation.getIdentifiers(),
                                                                                          variation.getReferenceAllele(),
                                                                                          altAllele,
                                                                                          sequenceOntologyTerm,
                                                                                          variation.getRegion(),
                                                                                          variation.getStart(),
                                                                                          variation.getEnd()));
                                            }
                                        }
                                    }
                                    catch (IOException e)
                                    {
                                        //System.out.println("error parsing record at line number " + record.getLineNumber());
                                        //e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    // todo: not sure this is a valid comparison
                    private boolean sameVariation(final Variation variation, final VcfRecord record)
                    {
                        return variation.getRegion().equals(record.getChrom()) && (variation.getStart() == (record.getPos() - 1));
                    }
                });
        }
        catch (IOException e)
        {
            // todo
        }
        return consequences;
    }
}