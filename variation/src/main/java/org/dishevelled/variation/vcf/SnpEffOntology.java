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
package org.dishevelled.variation.vcf;

import java.util.Map;
import java.util.Set;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableSet;

import org.dishevelled.variation.so.SequenceOntology;

import org.dishevelled.vocabulary.Authority;
import org.dishevelled.vocabulary.Assignment;
import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;
import org.dishevelled.vocabulary.Evidence;
import org.dishevelled.vocabulary.Mapping;
import org.dishevelled.vocabulary.Relation;

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

    public static Domain effects()
    {
        Authority snpEff = new Authority("snpEff");
        Domain effects = snpEff.createDomain("effects" + id.incrementAndGet());

        int accession = -1;
        effects.createConcept("NONE", "e" + accession++, null);
        effects.createConcept("UTR_3_DELETED", "e" + accession++, null);
        effects.createConcept("UTR_5_DELETED", "e" + accession++, null);
        effects.createConcept("START_GAINED", "e" + accession++, null);
        effects.createConcept("SPLICE_SITE_ACCEPTOR", "e" + accession++, null);
        effects.createConcept("SPLICE_SITE_DONOR", "e" + accession++, null);
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

        return effects;
    }

    public static Domain regions()
    {
        Authority snpEff = new Authority("snpEff");
        Domain regions = snpEff.createDomain("regions" + id.incrementAndGet());

        int accession = -1;
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

        return regions;
    }

    public static Domain impacts()
    {
        Authority snpEff = new Authority("snpEff");
        Domain impacts = snpEff.createDomain("impacts" + id.incrementAndGet());

        int accession = -1;
        // are these actually mixed case in output?
        impacts.createConcept("High", "i" + accession++, null);
        impacts.createConcept("Moderate", "i" + accession++, null);
        impacts.createConcept("Low", "i" + accession++, null);
        impacts.createConcept("Modifier", "i" + accession++, null);

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
        mapping.createProjection("is_a", effectsByName.get("NONE"), impactsByName.get("Modifier"), evidence);
        // ...

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
        mapping.createProjection("is_a", regionsByName.get("NONE"), impactsByName.get("Modifier"), evidence);
        // ...

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
        // ...

        return mapping;
    }
}