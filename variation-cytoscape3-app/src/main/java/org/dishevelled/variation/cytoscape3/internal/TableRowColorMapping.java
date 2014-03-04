/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
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
package org.dishevelled.variation.cytoscape3.internal;

import static org.dishevelled.variation.so.SequenceOntology.indexByName;
import static org.dishevelled.variation.so.SequenceOntology.sequenceVariants;

import java.awt.Color;

import java.util.HashMap;
import java.util.Map;

import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;

/**
 * Mapping of Sequence Ontology (SO) consequence term to background color for table rows.
 *
 * @author  Michael Heuer
 */
final class TableRowColorMapping
{
    /** Map of colors keyed by concept. */
    private final Map<Concept, Color> colors;

    /** Map of concepts keyed by name. */
    private final Map<String, Concept> conceptsByName;


    /**
     * Create a new table row color mapping.
     */
    TableRowColorMapping()
    {
        Domain sv = sequenceVariants();
        conceptsByName = indexByName(sv);
        colors = new HashMap<Concept, Color>(conceptsByName.size());

        int alpha = 30;
        colors.put(conceptsByName.get("transcript_ablation"), new Color(255, 0, 0, alpha));
        colors.put(conceptsByName.get("stop_gained"), new Color(255, 0, 0, alpha));
        colors.put(conceptsByName.get("stop_lost"), new Color(255, 0, 0, alpha));

        colors.put(conceptsByName.get("splice_donor_variant"), new Color(255, 127, 80, alpha));
        colors.put(conceptsByName.get("splice_acceptor_variant"), new Color(255, 127, 80, alpha));
        colors.put(conceptsByName.get("splice_region_variant"), new Color(255, 127, 80, alpha));

        colors.put(conceptsByName.get("frameshift_variant"), new Color(255, 105, 180, alpha));
        colors.put(conceptsByName.get("inframe_insertion"), new Color(255, 105, 180, alpha));
        colors.put(conceptsByName.get("inframe_deletion"), new Color(255, 105, 180, alpha));
        colors.put(conceptsByName.get("transcript_amplification"), new Color(255, 105, 180, alpha));

        colors.put(conceptsByName.get("initiator_codon_variant"), new Color(255, 215, 0, alpha));
        colors.put(conceptsByName.get("missense_variant"), new Color(255, 215, 0, alpha));

        colors.put(conceptsByName.get("incomplete_terminal_codon_variant"), new Color(255, 0, 255, alpha));

        colors.put(conceptsByName.get("synonymous_variant"), new Color(118, 238, 0, alpha));
        colors.put(conceptsByName.get("stop_retained_variant"), new Color(118, 238, 0, alpha));

        colors.put(conceptsByName.get("coding_sequence_variant"), new Color(69, 139, 0, alpha));
        colors.put(conceptsByName.get("mature_miRNA_variant"), new Color(69, 139, 0, alpha));

        colors.put(conceptsByName.get("5_prime_UTR_variant"), new Color(122, 197, 205, alpha));
        colors.put(conceptsByName.get("3_prime_UTR_variant"), new Color(122, 197, 205, alpha));

        colors.put(conceptsByName.get("intron_variant"), new Color(2, 89, 156, alpha));

        colors.put(conceptsByName.get("NMD_transcript_variant"), new Color(255, 69, 0, alpha));

        colors.put(conceptsByName.get("non_coding_exon_variant"), new Color(50, 205, 50, alpha));
        colors.put(conceptsByName.get("nc_transcript_variant"), new Color(50, 205, 50, alpha));

        colors.put(conceptsByName.get("upstream_gene_variant"), new Color(162, 181, 205, alpha));
        colors.put(conceptsByName.get("downstream_gene_variant"), new Color(162, 181, 205, alpha));

        colors.put(conceptsByName.get("TFBS_ablation"), new Color(165, 42, 42, alpha));
        colors.put(conceptsByName.get("TFBS_amplification"), new Color(165, 42, 42, alpha));
        colors.put(conceptsByName.get("TF_binding_site_variant"), new Color(165, 42, 42, alpha));
        colors.put(conceptsByName.get("regulatory_region_variant"), new Color(165, 42, 42, alpha));
        colors.put(conceptsByName.get("regulatory_region_ablation"), new Color(165, 42, 42, alpha));
        colors.put(conceptsByName.get("regulatory_region_amplification"), new Color(165, 42, 42, alpha));

        colors.put(conceptsByName.get("intergenic_variant"), new Color(99, 99, 99, alpha));
    }


    /**
     * Return a mapped color for the specified concept, or null if no such mapping exists.
     *
     * @param concept concept
     * @return a mapped color for the specified concept, or null if no such mapping exists.
     */
    Color getColor(final Concept concept)
    {
        return colors.get(concept);
    }

    /**
     * Return a mapped color for the specified concept name, or null if no such mapping exists.
     *
     * @param conceptName concept name
     * @return a mapped color for the specified concept name, or null if no such mapping exists.
     */
    Color getColor(final String conceptName)
    {
        Concept concept = conceptsByName.get(conceptName);
        return concept == null ? null : colors.get(concept);
    }
}