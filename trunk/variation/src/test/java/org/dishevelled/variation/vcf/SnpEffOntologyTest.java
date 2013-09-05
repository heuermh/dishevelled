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

import static org.dishevelled.variation.vcf.SnpEffOntology.effects;
import static org.dishevelled.variation.vcf.SnpEffOntology.effectToImpactMapping;
import static org.dishevelled.variation.vcf.SnpEffOntology.effectToRegionMapping;
import static org.dishevelled.variation.vcf.SnpEffOntology.effectToSequenceOntologyMapping;
import static org.dishevelled.variation.vcf.SnpEffOntology.impacts;
import static org.dishevelled.variation.vcf.SnpEffOntology.indexByName;
import static org.dishevelled.variation.vcf.SnpEffOntology.regions;
import static org.dishevelled.variation.vcf.SnpEffOntology.regionToEffectMapping;
import static org.dishevelled.variation.vcf.SnpEffOntology.regionToImpactMapping;
import static org.dishevelled.variation.vcf.SnpEffOntology.regionToSequenceOntologyMapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;
import org.dishevelled.vocabulary.Mapping;
import org.dishevelled.vocabulary.Projection;

import org.junit.Test;

/**
 * Unit test for SnpEffOntology.
 */
public final class SnpEffOntologyTest
{

    @Test(expected=NullPointerException.class)
    public void testIndexByNameNullDomain()
    {
        indexByName(null);
    }

    @Test
    public void testIndexByName()
    {
        Map<String, Concept> effectsByName = indexByName(effects());
        assertNotNull(effectsByName);
        assertNotNull(effectsByName.get("STOP_GAINED"));
        assertEquals("STOP_GAINED", effectsByName.get("STOP_GAINED").getName());
    }

    @Test
    public void testEffects()
    {
        Domain effects = effects();
        assertNotNull(effects);
        boolean found = false;
        for (Concept concept : effects.getConcepts())
        {
            if ("STOP_GAINED".equals(concept.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testRegions()
    {
        Domain regions = regions();
        assertNotNull(regions);
        boolean found = false;
        for (Concept concept : regions.getConcepts())
        {
            if ("INTRON".equals(concept.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testImpacts()
    {
        Domain impacts = impacts();
        assertNotNull(impacts);
        boolean found = false;
        for (Concept concept : impacts.getConcepts())
        {
            if ("LOW".equals(concept.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testEffectToRegionMapping()
    {
        Mapping effectToRegion = effectToRegionMapping();
        boolean found = false;
        for (Projection projection : effectToRegion.getProjections())
        {
            Concept source = projection.getSource();
            Concept target = projection.getTarget();
            if ("NONE".equals(source.getName()) && "NONE".equals(target.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testEffectToImpactMapping()
    {
        Mapping effectToImpact = effectToImpactMapping();
        assertNotNull(effectToImpact);
        boolean found = false;
        for (Projection projection : effectToImpact.getProjections())
        {
            Concept source = projection.getSource();
            Concept target = projection.getTarget();
            if ("STOP_GAINED".equals(source.getName()) && "HIGH".equals(target.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testRegionToEffectMapping()
    {
        Mapping regionToEffect = regionToEffectMapping();
        boolean found = false;
        for (Projection projection : regionToEffect.getProjections())
        {
            Concept source = projection.getSource();
            Concept target = projection.getTarget();
            if ("NONE".equals(source.getName()) && "NONE".equals(target.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testRegionToImpactMapping()
    {
        Mapping regionToImpact = regionToImpactMapping();
        boolean found = false;
        for (Projection projection : regionToImpact.getProjections())
        {
            Concept source = projection.getSource();
            Concept target = projection.getTarget();
            if ("TRANSCRIPT".equals(source.getName()) && "MODIFIER".equals(target.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testEffectToSequenceOntologyMapping()
    {
        Mapping effectToSequenceOntology = effectToSequenceOntologyMapping();
        assertNotNull(effectToSequenceOntology);
        boolean found = false;
        for (Projection projection : effectToSequenceOntology.getProjections())
        {
            Concept source = projection.getSource();
            Concept target = projection.getTarget();
            if ("CODON_CHANGE".equals(source.getName()) && "coding_sequence_variant".equals(target.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testRegionToSequenceOntologyMapping()
    {
        Mapping regionToSequenceOntology = regionToSequenceOntologyMapping();
        assertNotNull(regionToSequenceOntology);
        boolean found = false;
        for (Projection projection : regionToSequenceOntology.getProjections())
        {
            Concept source = projection.getSource();
            Concept target = projection.getTarget();
            if ("UPSTREAM".equals(source.getName()) && "upstream_gene_variant".equals(target.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }
}