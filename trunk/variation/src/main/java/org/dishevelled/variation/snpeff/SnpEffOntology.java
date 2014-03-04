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
package org.dishevelled.variation.snpeff;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableSet;

import org.dishevelled.variation.so.SequenceOntology;

import org.dishevelled.vocabulary.Authority;
import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;
import org.dishevelled.vocabulary.Evidence;
import org.dishevelled.vocabulary.Mapping;
import org.dishevelled.vocabulary.Projection;

/**
 * Mappings between SnpEff terminologies and SequenceOntology.
 */
public final class SnpEffOntology
{
    private static final AtomicInteger id = new AtomicInteger(0);

    private SnpEffOntology()
    {
        // empty
    }

    public static Map<String, Concept> indexByName(final Domain domain)
    {
        return SequenceOntology.indexByName(domain);
    }

    public static Map<Concept, Projection> indexBySourceConcept(final Mapping mapping)
    {
        checkNotNull(mapping);
        Map<Concept, Projection> indexBySourceConcept = new HashMap<Concept, Projection>();
        for (Projection projection : mapping.getProjections())
        {
            indexBySourceConcept.put(projection.getSource(), projection);
        }
        return indexBySourceConcept;
    }

    public static Domain effects()
    {
        Authority snpEff = new Authority("snpEff");
        Domain effects = snpEff.createDomain("effects" + id.incrementAndGet());

        int accession = 0;
        effects.createConcept("NONE", "e" + accession++, null);
        effects.createConcept("CUSTOM", "e" + accession++, null); // not listed in SnpEff doc effects table
        effects.createConcept("UTR_3_DELETED", "e" + accession++, null);
        effects.createConcept("UTR_5_DELETED", "e" + accession++, null);
        effects.createConcept("START_GAINED", "e" + accession++, null);
        effects.createConcept("SPLICE_SITE_ACCEPTOR", "e" + accession++, null);
        effects.createConcept("SPLICE_SITE_BRANCH", "e" + accession++, null); // not listed in SnpEff doc effects table
        effects.createConcept("SPLICE_SITE_BRANCH_U12", "e" + accession++, null); // not listed in SnpEff doc effects table
        effects.createConcept("SPLICE_SITE_DONOR", "e" + accession++, null);
        effects.createConcept("START_LOST", "e" + accession++, null);
        effects.createConcept("SYNONYMOUS_START", "e" + accession++, null);
        effects.createConcept("EXON_DELETED", "e" + accession++, null);
        effects.createConcept("NON_SYNONYMOUS_CODING", "e" + accession++, null);
        effects.createConcept("SYNONYMOUS_CODING", "e" + accession++, null);
        effects.createConcept("FRAME_SHIFT", "e" + accession++, null);
        effects.createConcept("CODON_CHANGE", "e" + accession++, null);
        effects.createConcept("CODON_INSERTION", "e" + accession++, null);
        effects.createConcept("CODON_CHANGE_PLUS_CODON_INSERTION", "e" + accession++, null);
        effects.createConcept("CODON_DELETION", "e" + accession++, null);
        effects.createConcept("CODON_CHANGE_PLUS_CODON_DELETION", "e" + accession++, null);
        effects.createConcept("STOP_GAINED", "e" + accession++, null);
        effects.createConcept("SYNONYMOUS_STOP", "e" + accession++, null);
        effects.createConcept("STOP_LOST", "e" + accession++, null);
        effects.createConcept("INTRON_CONSERVED", "e" + accession++, null);
        effects.createConcept("INTERGENIC_CONSERVED", "e" + accession++, null);
        effects.createConcept("RARE_AMINO_ACID", "e" + accession++, null);
        effects.createConcept("NON_SYNONYMOUS_START", "e" + accession++, null);
        effects.createConcept("NON_SYNONYMOUS_STOP", "e" + accession++, null);  // not listed in SnpEff doc effects table

        return effects;
    }

    public static Domain regions()
    {
        Authority snpEff = new Authority("snpEff");
        Domain regions = snpEff.createDomain("regions" + id.incrementAndGet());

        int accession = 0;
        regions.createConcept("NONE", "r" + accession++, null);
        regions.createConcept("CDS", "r" + accession++, null);
        regions.createConcept("GENE", "r" + accession++, null);
        regions.createConcept("TRANSCRIPT", "r" + accession++, null);
        regions.createConcept("EXON", "r" + accession++, null);
        regions.createConcept("INTRON", "r" + accession++, null);
        regions.createConcept("DOWNSTREAM", "r" + accession++, null);
        regions.createConcept("INTRAGENIC", "r" + accession++, null);
        regions.createConcept("INTERGENIC", "r" + accession++, null);
        regions.createConcept("UPSTREAM", "r" + accession++, null);
        regions.createConcept("UTR_3_PRIME", "r" + accession++, null);
        regions.createConcept("UTR_5_PRIME", "r" + accession++, null);
        regions.createConcept("REGULATION", "r" + accession++, null); // not listed in SnpEff doc effects table

        return regions;
    }

    public static Domain impacts()
    {
        Authority snpEff = new Authority("snpEff");
        Domain impacts = snpEff.createDomain("impacts" + id.incrementAndGet());

        int accession = 0;
        impacts.createConcept("HIGH", "i" + accession++, null);
        impacts.createConcept("MODERATE", "i" + accession++, null);
        impacts.createConcept("LOW", "i" + accession++, null);
        impacts.createConcept("MODIFIER", "i" + accession++, null);

        return impacts;
    }

    public static Mapping effectToRegionMapping()
    {
        Domain effects = effects();
        Domain regions = regions();
        Map<String, Concept> effectsByName = indexByName(effects);
        Map<String, Concept> regionsByName = indexByName(regions);

        Authority authority = effects.getAuthority();
        Mapping mapping = authority.createMapping(effects, regions);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("EV-AS-NAS", 1.0d, 1.0d));
        mapping.createProjection("is_a", effectsByName.get("NONE"), regionsByName.get("NONE"), evidence);
        // ...

        return mapping;
    }

    public static Mapping effectToImpactMapping()
    {
        Domain effects = effects();
        Domain impacts = impacts();
        Map<String, Concept> effectsByName = indexByName(effects);
        Map<String, Concept> impactsByName = indexByName(impacts);

        Authority authority = effects.getAuthority();
        Mapping mapping = authority.createMapping(effects, impacts);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("EV-AS-NAS", 1.0d, 1.0d));
        mapping.createProjection("is_a", effectsByName.get("SPLICE_SITE_ACCEPTOR"), impactsByName.get("HIGH"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SPLICE_SITE_DONOR"), impactsByName.get("HIGH"), evidence);
        mapping.createProjection("is_a", effectsByName.get("START_LOST"), impactsByName.get("HIGH"), evidence);
        mapping.createProjection("is_a", effectsByName.get("EXON_DELETED"), impactsByName.get("HIGH"), evidence);
        mapping.createProjection("is_a", effectsByName.get("FRAME_SHIFT"), impactsByName.get("HIGH"), evidence);
        mapping.createProjection("is_a", effectsByName.get("STOP_GAINED"), impactsByName.get("HIGH"), evidence);
        mapping.createProjection("is_a", effectsByName.get("STOP_LOST"), impactsByName.get("HIGH"), evidence);
        mapping.createProjection("is_a", effectsByName.get("RARE_AMINO_ACID"), impactsByName.get("HIGH"), evidence);

        mapping.createProjection("is_a", effectsByName.get("NON_SYNONYMOUS_CODING"), impactsByName.get("MODERATE"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_CHANGE"), impactsByName.get("MODERATE"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_INSERTION"), impactsByName.get("MODERATE"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_CHANGE_PLUS_CODON_INSERTION"), impactsByName.get("MODERATE"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_DELETION"), impactsByName.get("MODERATE"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_CHANGE_PLUS_CODON_DELETION"), impactsByName.get("MODERATE"), evidence);
        mapping.createProjection("is_a", effectsByName.get("UTR_5_DELETED"), impactsByName.get("MODERATE"), evidence);
        mapping.createProjection("is_a", effectsByName.get("UTR_3_DELETED"), impactsByName.get("MODERATE"), evidence);

        mapping.createProjection("is_a", effectsByName.get("SYNONYMOUS_START"), impactsByName.get("LOW"), evidence);
        mapping.createProjection("is_a", effectsByName.get("NON_SYNONYMOUS_START"), impactsByName.get("LOW"), evidence);
        mapping.createProjection("is_a", effectsByName.get("START_GAINED"), impactsByName.get("LOW"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SYNONYMOUS_CODING"), impactsByName.get("LOW"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SYNONYMOUS_STOP"), impactsByName.get("LOW"), evidence);

        mapping.createProjection("is_a", effectsByName.get("INTRON_CONSERVED"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", effectsByName.get("INTERGENIC_CONSERVED"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", effectsByName.get("NONE"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CUSTOM"), impactsByName.get("MODIFIER"), evidence);

        return mapping;
    }

    public static Mapping regionToEffectMapping()
    {
        Domain effects = effects();
        Domain regions = regions();
        Map<String, Concept> effectsByName = indexByName(effects);
        Map<String, Concept> regionsByName = indexByName(regions);

        Authority authority = effects.getAuthority();
        Mapping mapping = authority.createMapping(regions, effects);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("EV-AS-NAS", 1.0d, 1.0d));
        mapping.createProjection("is_a", regionsByName.get("NONE"), effectsByName.get("NONE"), evidence);
        // ...

        return mapping;
    }

    public static Mapping regionToImpactMapping()
    {
        Domain regions = regions();
        Domain impacts = impacts();
        Map<String, Concept> regionsByName = indexByName(regions);
        Map<String, Concept> impactsByName = indexByName(impacts);

        Authority authority = regions.getAuthority();
        Mapping mapping = authority.createMapping(regions, impacts);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("EV-AS-NAS", 1.0d, 1.0d));
        mapping.createProjection("is_a", regionsByName.get("UTR_5_PRIME"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("UTR_3_PRIME"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("REGULATION"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("UPSTREAM"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("DOWNSTREAM"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("GENE"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("TRANSCRIPT"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("EXON"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("INTRON"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("INTRAGENIC"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("INTERGENIC"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("NONE"), impactsByName.get("MODIFIER"), evidence);
        mapping.createProjection("is_a", regionsByName.get("CDS"), impactsByName.get("MODIFIER"), evidence);

        return mapping;
    }

    public static Mapping effectToSequenceOntologyMapping()
    {
        Domain effects = effects();
        Map<String, Concept> effectsByName = indexByName(effects);

        Domain sv = SequenceOntology.sequenceVariants();
        Map<String, Concept> svByName = indexByName(sv);

        Authority authority = effects.getAuthority();
        Mapping mapping = authority.createMapping(effects, sv);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("EV-AS-NAS", 1.0d, 1.0d));
        mapping.createProjection("is_a", effectsByName.get("CODON_CHANGE"), svByName.get("coding_sequence_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_CHANGE_PLUS_CODON_DELETION"), svByName.get("inframe_deletion"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_CHANGE_PLUS_CODON_INSERTION"), svByName.get("inframe_insertion"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_DELETION"), svByName.get("inframe_deletion"), evidence);
        mapping.createProjection("is_a", effectsByName.get("CODON_INSERTION"), svByName.get("inframe_insertion"), evidence);
        //mapping.createProjection("is_a", effectsByName.get("EXON_DELETED"), svByName.get("exon_lost"), evidence);  no such thing
        mapping.createProjection("is_a", effectsByName.get("FRAME_SHIFT"), svByName.get("frameshift_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("INTERGENIC_CONSERVED"), svByName.get("intergenic_variant"), evidence); // SnpEff docs incorrectly --> intergenic_region
        mapping.createProjection("is_a", effectsByName.get("INTRON_CONSERVED"), svByName.get("intron_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("NON_SYNONYMOUS_CODING"), svByName.get("missense_variant"), evidence); // SnpEff docs incorrectly --> missense
        mapping.createProjection("is_a", effectsByName.get("NON_SYNONYMOUS_START"), svByName.get("initiator_codon_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("NON_SYNONYMOUS_STOP"), svByName.get("stop_retained_variant"), evidence);
        //mapping.createProjection("is_a", effectsByName.get("RARE_AMINO_ACID"), svByName.get("non_conservative_missense_variant"), evidence);  no such thing
        //mapping.createProjection("is_a", effectsByName.get("REGULATION"), svByName.get("regulatory_region_variant"), evidence);  in region domain
        mapping.createProjection("is_a", effectsByName.get("SPLICE_SITE_ACCEPTOR"), svByName.get("splice_region_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SPLICE_SITE_BRANCH"), svByName.get("splice_region_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SPLICE_SITE_BRANCH_U12"), svByName.get("splice_region_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SPLICE_SITE_DONOR"), svByName.get("splice_region_variant"), evidence);
        //mapping.createProjection("is_a", effectsByName.get("START_GAINED"), svByName.get("start_gained"), evidence);  no such thing
        mapping.createProjection("is_a", effectsByName.get("START_LOST"), svByName.get("initiator_codon_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("STOP_LOST"), svByName.get("stop_lost"), evidence);
        mapping.createProjection("is_a", effectsByName.get("STOP_GAINED"), svByName.get("stop_gained"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SYNONYMOUS_CODING"), svByName.get("synonymous_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SYNONYMOUS_START"), svByName.get("initiator_codon_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("SYNONYMOUS_STOP"), svByName.get("stop_retained_variant"), evidence);
        mapping.createProjection("is_a", effectsByName.get("UTR_5_DELETED"), svByName.get("5_prime_UTR_variant"), evidence); // SnpEff docs incorrectly --> five_prime_UTR; this should be feature_truncation perhaps?

        return mapping;
    }

    public static Mapping regionToSequenceOntologyMapping()
    {
        Domain regions = regions();
        Map<String, Concept> regionsByName = indexByName(regions);

        Domain sv = SequenceOntology.sequenceVariants();
        Map<String, Concept> svByName = indexByName(sv);

        Authority authority = regions.getAuthority();
        Mapping mapping = authority.createMapping(regions, sv);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("EV-AS-NAS", 1.0d, 1.0d));
        mapping.createProjection("is_a", regionsByName.get("CDS"), svByName.get("coding_sequence_variant"), evidence); // SnpEff docs incorrectly --> cds
        mapping.createProjection("is_a", regionsByName.get("DOWNSTREAM"), svByName.get("downstream_gene_variant"), evidence);
        mapping.createProjection("is_a", regionsByName.get("EXON"), svByName.get("non_coding_exon_variant"), evidence);
        mapping.createProjection("is_a", regionsByName.get("GENE"), svByName.get("gene_variant"), evidence); // SnpEff docs incorrectly --> gene
        mapping.createProjection("is_a", regionsByName.get("INTERGENIC"), svByName.get("intergenic_variant"), evidence); // SnpEff docs incorrectly --> intergenic_region
        //mapping.createProjection("is_a", regionsByName.get("INTRAGENIC"), svByName.get("intragenic_variant"), evidence);  does not exist
        mapping.createProjection("is_a", regionsByName.get("INTRON"), svByName.get("intron_variant"), evidence);
        //mapping.createProjection("is_a", regionsByName.get("MICRO_RNA"), svByName.get("micro_rna"), evidence);  does not exist, mature_miRNA_variant perhaps?
        mapping.createProjection("is_a", regionsByName.get("REGULATION"), svByName.get("regulatory_region_variant"), evidence);
        mapping.createProjection("is_a", regionsByName.get("TRANSCRIPT"), svByName.get("nc_transcript_variant"), evidence);
        mapping.createProjection("is_a", regionsByName.get("UPSTREAM"), svByName.get("upstream_gene_variant"), evidence);
        mapping.createProjection("is_a", regionsByName.get("UTR_3_PRIME"), svByName.get("3_prime_UTR_variant"), evidence);
        mapping.createProjection("is_a", regionsByName.get("UTR_5_PRIME"), svByName.get("5_prime_UTR_variant"), evidence);

        return mapping;
    }
}