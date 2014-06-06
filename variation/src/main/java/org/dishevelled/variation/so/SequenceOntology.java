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
package org.dishevelled.variation.so;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.atomic.AtomicInteger;

import org.dishevelled.vocabulary.Authority;
import org.dishevelled.vocabulary.Assignment;
import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;
import org.dishevelled.vocabulary.Relation;

/**
 * Subset of the sequence ontology used by Ensembl Variation Effect Predictor (VEP).
 *
 * @author  Michael Heuer
 */
public final class SequenceOntology
{
    /** Internal id. */
    private static final AtomicInteger id = new AtomicInteger(0);


    /**
     * Private no-arg constructor.
     */
    private SequenceOntology()
    {
        // empty
    }


    /**
     * Index the specified domain by name.
     *
     * @param domain domain to index, must not be null
     * @return the specified domain indexed by name
     */
    public static Map<String, Concept> indexByName(final Domain domain)
    {
        checkNotNull(domain);
        Map<String, Concept> indexByName = new HashMap<String, Concept>(domain.getConcepts().size());
        for (Concept concept : domain.getConcepts())
        {
            indexByName.put(concept.getName(), concept);
        }
        return indexByName;
    }

    /**
     * Count assignments in the specified domain.
     *
     * @param domain domain, must not be null
     * @return map of assignment counts keyed by term
     */
    public static Map<Concept, Integer> countAssignments(final Domain domain)
    {
        checkNotNull(domain);
        Map<Concept, Integer> count = new HashMap<Concept, Integer>(domain.getConcepts().size());
        for (Concept concept : domain.getConcepts())
        {
            count.put(concept, Integer.valueOf(0));
        }
        for (Assignment assignment : domain.getAuthority().getAssignments())
        {
            depthFirstToRoot(assignment.getConcept(), count);
        }
        return count;
    }

    /**
     * Depth first search.
     *
     * @param concept concept
     * @param count map of assignment counts keyed by term
     */
    private static void depthFirstToRoot(final Concept concept, final Map<Concept, Integer> count)
    {
        for (Relation outRelation : concept.outRelations())
        {
            depthFirstToRoot(outRelation.getTarget(), count);
        }
        count.put(concept, count.get(concept) + 1);
    }

    /**
     * Create and return a new domain of Sequence Ontology sequence feature terms.
     *
     * @return a new domain of Sequence Ontology sequence feature terms
     */
    public static Domain sequenceFeatures()
    {
        Authority so = new Authority("sequenceOntology");
        Domain sf = so.createDomain("sequenceFeatures" + id.incrementAndGet());

        Concept complex_structural_variation = sf.createConcept("complex_structural_variation", "SO:0001784", null);
        Concept copy_number_gain = sf.createConcept("copy_number_gain", "SO:0001742", null);
        Concept copy_number_loss = sf.createConcept("copy_number_loss", "SO:0001743", null);
        Concept copy_number_variation = sf.createConcept("copy_number_variation", "SO:0001019", null);
        Concept deletion = sf.createConcept("deletion", "SO:0000159", null);
        Concept duplication = sf.createConcept("duplication", "SO:1000035", null);
        Concept insertion = sf.createConcept("insertion", "SO:0000667", null);
        Concept interchromosomal_breakpoint = sf.createConcept("interchromosomal_breakpoint", "SO:0001873", null);
        Concept intrachromosomal_breakpoint = sf.createConcept("intrachromosomal_breakpoint", "SO:0001874", null);
        Concept inversion = sf.createConcept("inversion", "SO:1000036", null);
        Concept rearrangement_breakpoint = sf.createConcept("rearrangement_breakpoint", "SO:0001872", null);
        Concept sequence_feature = sf.createConcept("sequence_feature", "SO:0000110", null);
        Concept sequence_alteration = sf.createConcept("sequence_alteration", "SO:0001059", null);
        Concept structural_alteration = sf.createConcept("structural_alteration", "SO:0001785", null);
        Concept tandem_duplication = sf.createConcept("tandem_duplication", "SO:1000173", null);
        Concept translocation = sf.createConcept("translocation", "SO:0000199", null);

        sf.createRelation("is_a", complex_structural_variation, structural_alteration);
        sf.createRelation("is_a", copy_number_gain, copy_number_variation);
        sf.createRelation("is_a", copy_number_loss, copy_number_variation);
        sf.createRelation("is_a", copy_number_variation, sequence_alteration);
        sf.createRelation("is_a", deletion, sequence_alteration);
        sf.createRelation("is_a", duplication, insertion);
        sf.createRelation("is_a", insertion, sequence_alteration);
        sf.createRelation("is_a", interchromosomal_breakpoint, rearrangement_breakpoint);
        sf.createRelation("is_a", intrachromosomal_breakpoint, rearrangement_breakpoint);
        sf.createRelation("is_a", rearrangement_breakpoint, structural_alteration);
        sf.createRelation("is_a", inversion, sequence_alteration);
        sf.createRelation("is_a", tandem_duplication, duplication);
        sf.createRelation("is_a", translocation, sequence_alteration);
        sf.createRelation("is_a", structural_alteration, sequence_alteration);
        sf.createRelation("is_a", sequence_alteration, sequence_feature);

        return sf;
    }

    /**
     * Create and return a new domain of Sequence Ontology sequence variant terms.
     *
     * @return a new domain of Sequence Ontology sequence variant terms
     */
    public static Domain sequenceVariants()
    {
        Authority so = new Authority("sequenceOntology");
        Domain sv = so.createDomain("sequenceVariants" + id.incrementAndGet());

        Concept _3_prime_UTR_variant = sv.createConcept("3_prime_UTR_variant", "SO:0001624", null);
        Concept _5_prime_UTR_variant = sv.createConcept("5_prime_UTR_variant", "SO:0001623", null);
        Concept coding_sequence_variant = sv.createConcept("coding_sequence_variant", "SO:0001580", null);
        Concept downstream_gene_variant = sv.createConcept("downstream_gene_variant", "SO:0001632", null);
        Concept exon_variant = sv.createConcept("exon_variant", "SO:0001791", null);
        Concept feature_ablation = sv.createConcept("feature_ablation", "SO:0001879", null);
        Concept feature_amplification = sv.createConcept("feature_amplification", "SO:0001880", null);
        Concept feature_elongation = sv.createConcept("feature_elongation", "SO:0001907", null);
        Concept feature_truncation = sv.createConcept("feature_truncation", "SO:0001906", null);
        Concept feature_variant = sv.createConcept("feature_variant", "SO:0001878", null);
        Concept frameshift_variant = sv.createConcept("frameshift_variant", "SO:0001589", null);
        Concept gene_variant = sv.createConcept("gene_variant", "SO:0001564", null);
        Concept incomplete_terminal_codon_variant = sv.createConcept("incomplete_terminal_codon_variant", "SO:0001626", null);
        Concept inframe_deletion = sv.createConcept("inframe_deletion", "SO:0001822", null);
        Concept inframe_indel = sv.createConcept("inframe_indel", "SO:0001820", null);
        Concept inframe_insertion = sv.createConcept("inframe_insertion", "SO:0001821", null);
        Concept inframe_variant = sv.createConcept("inframe_variant", "SO:0001650", null);
        Concept initiator_codon_variant = sv.createConcept("initiator_codon_variant", "SO:0001582", null);
        Concept intergenic_variant = sv.createConcept("intergenic_variant", "SO:0001628", null);
        Concept internal_feature_elongation = sv.createConcept("internal_feature_elongation", "SO:0001908", null);
        Concept intron_variant = sv.createConcept("intron_variant", "SO:0001627", null);
        Concept mature_miRNA_variant = sv.createConcept("mature_miRNA_variant", "SO:0001620", null);
        Concept missense_variant = sv.createConcept("missense_variant", "SO:0001583", null);
        Concept nc_transcript_variant = sv.createConcept("nc_transcript_variant", "SO:0001619", null);
        Concept _NMD_transcript_variant = sv.createConcept("NMD_transcript_variant", "SO:0001621", null);
        Concept non_coding_exon_variant = sv.createConcept("non_coding_exon_variant", "SO:0001792", null);
        Concept protein_altering_variant = sv.createConcept("protein_altering_variant", "SO:0001818", null);
        Concept regulatory_region_ablation = sv.createConcept("regulatory_region_ablation", "SO:0001894", null);
        Concept regulatory_region_amplification = sv.createConcept("regulatory_region_amplification", "SO:0001891", null);
        Concept regulatory_region_variant = sv.createConcept("regulatory_region_variant", "SO:0001566", null);
        Concept sequence_variant = sv.createConcept("sequence_variant", "SO:0001060", null);
        Concept splice_acceptor_variant = sv.createConcept("splice_acceptor_variant", "SO:0001574", null);
        Concept splice_donor_variant = sv.createConcept("splice_donor_variant", "SO:0001575", null);
        Concept splice_region_variant = sv.createConcept("splice_region_variant", "SO:0001630", null);
        Concept splice_site_variant = sv.createConcept("splice_site_variant", "SO:0001629", null);
        Concept splicing_variant = sv.createConcept("splicing_variant", "SO:0001568", null);
        Concept stop_gained = sv.createConcept("stop_gained", "SO:0001587", null);
        Concept stop_lost = sv.createConcept("stop_lost", "SO:0001578", null);
        Concept stop_retained_variant = sv.createConcept("stop_retained_variant", "SO:0001567", null);
        Concept structural_variant = sv.createConcept("structural_variant", "SO:0001537", null);
        Concept synonymous_variant = sv.createConcept("synonymous_variant", "SO:0001819", null);
        Concept terminator_codon_variant = sv.createConcept("terminator_codon_variant", "SO:0001590", null);
        Concept _TF_binding_site_variant = sv.createConcept("TF_binding_site_variant", "SO:0001782", null);
        Concept _TFBS_ablation = sv.createConcept("TFBS_ablation", "SO:0001895", null);
        Concept _TFBS_amplification = sv.createConcept("TFBS_amplification", "SO:0001892", null);
        Concept transcript_ablation = sv.createConcept("transcript_ablation", "SO:0001893", null);
        Concept transcript_amplification = sv.createConcept("transcript_amplification", "SO:0001889", null);
        Concept transcript_variant = sv.createConcept("transcript_variant", "SO:0001576", null);
        Concept upstream_gene_variant = sv.createConcept("upstream_gene_variant", "SO:0001631", null);
        Concept _UTR_variant = sv.createConcept("UTR_variant", "SO:0001622", null);

        sv.createRelation("is_a", transcript_ablation, feature_ablation);
        sv.createRelation("is_a", splice_donor_variant, splice_site_variant);
        sv.createRelation("is_a", splice_acceptor_variant, splice_site_variant);
        sv.createRelation("is_a", splice_site_variant, intron_variant);
        sv.createRelation("is_a", intron_variant, transcript_variant);
        sv.createRelation("is_a", transcript_variant, gene_variant);
        sv.createRelation("is_a", stop_gained, inframe_variant);
        sv.createRelation("is_a", stop_gained, feature_truncation);
        sv.createRelation("is_a", feature_truncation, feature_variant);
        sv.createRelation("is_a", frameshift_variant, protein_altering_variant);
        sv.createRelation("is_a", inframe_indel, inframe_variant);
        sv.createRelation("is_a", stop_lost, inframe_variant);
        sv.createRelation("is_a", stop_lost, terminator_codon_variant);
        sv.createRelation("is_a", terminator_codon_variant, coding_sequence_variant);
        sv.createRelation("is_a", stop_lost, feature_elongation);
        sv.createRelation("is_a", initiator_codon_variant, inframe_variant);
        sv.createRelation("is_a", inframe_insertion, inframe_indel);
        sv.createRelation("is_a", internal_feature_elongation, feature_elongation);
        sv.createRelation("is_a", feature_elongation, feature_variant);
        sv.createRelation("is_a", inframe_deletion, inframe_indel);
        sv.createRelation("is_a", inframe_deletion, feature_truncation);
        sv.createRelation("is_a", missense_variant, inframe_variant);
        sv.createRelation("is_a", transcript_amplification, feature_amplification);
        sv.createRelation("is_a", splice_region_variant, splicing_variant);
        sv.createRelation("is_a", splicing_variant, gene_variant);
        sv.createRelation("is_a", incomplete_terminal_codon_variant, inframe_variant);
        sv.createRelation("is_a", inframe_variant, protein_altering_variant);
        sv.createRelation("is_a", protein_altering_variant, coding_sequence_variant);
        sv.createRelation("is_a", stop_retained_variant, synonymous_variant);
        sv.createRelation("is_a", synonymous_variant, coding_sequence_variant);
        sv.createRelation("is_a", coding_sequence_variant, exon_variant);
        sv.createRelation("is_a", mature_miRNA_variant, nc_transcript_variant);
        sv.createRelation("is_a", nc_transcript_variant, transcript_variant);
        sv.createRelation("is_a", _3_prime_UTR_variant, _UTR_variant);
        sv.createRelation("is_a", _5_prime_UTR_variant, _UTR_variant);
        sv.createRelation("is_a", _UTR_variant, transcript_variant);
        sv.createRelation("is_a", _NMD_transcript_variant, transcript_variant);
        sv.createRelation("is_a", non_coding_exon_variant, exon_variant);
        sv.createRelation("is_a", exon_variant, transcript_variant);
        sv.createRelation("is_a", gene_variant, feature_variant);
        sv.createRelation("is_a", upstream_gene_variant, feature_variant);
        sv.createRelation("is_a", downstream_gene_variant, feature_variant);
        sv.createRelation("is_a", _TFBS_ablation, regulatory_region_ablation);
        sv.createRelation("is_a", regulatory_region_ablation, feature_ablation);
        sv.createRelation("is_a", feature_ablation, structural_variant);
        sv.createRelation("is_a", _TFBS_amplification, regulatory_region_amplification);
        sv.createRelation("is_a", regulatory_region_amplification, feature_amplification);
        sv.createRelation("is_a", feature_amplification, structural_variant);
        sv.createRelation("is_a", _TF_binding_site_variant, regulatory_region_variant);
        sv.createRelation("is_a", regulatory_region_variant, feature_variant);
        sv.createRelation("is_a", intergenic_variant, feature_variant);
        sv.createRelation("is_a", feature_variant, structural_variant);
        sv.createRelation("is_a", structural_variant, sequence_variant);

        return sv;
    }
}
