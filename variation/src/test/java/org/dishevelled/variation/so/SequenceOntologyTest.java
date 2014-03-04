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

import static org.dishevelled.variation.so.SequenceOntology.countAssignments;
import static org.dishevelled.variation.so.SequenceOntology.indexByName;
import static org.dishevelled.variation.so.SequenceOntology.sequenceFeatures;
import static org.dishevelled.variation.so.SequenceOntology.sequenceVariants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import org.dishevelled.vocabulary.AbstractAssignable;
import org.dishevelled.vocabulary.Authority;
import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;
import org.dishevelled.vocabulary.Evidence;
import org.dishevelled.vocabulary.Mapping;
import org.dishevelled.vocabulary.Projection;

import org.junit.Test;

/**
 * Unit test for SequenceOntology.
 */
public final class SequenceOntologyTest
{

    @Test(expected=NullPointerException.class)
    public void testIndexByNameNullDomain()
    {
        indexByName(null);
    }

    @Test
    public void testIndexByName()
    {
        Map<String, Concept> svByName = indexByName(sequenceVariants());
        assertNotNull(svByName);
        assertNotNull(svByName.get("stop_gained"));
        assertEquals("stop_gained", svByName.get("stop_gained").getName());
    }

    @Test(expected=NullPointerException.class)
    public void testCountAssignmentsNullDomain()
    {
        countAssignments(null);
    }

    @Test
    public void testCountAssignmentsEmpty()
    {
        Map<Concept, Integer> assignments = countAssignments(sequenceVariants());
        for (Integer count : assignments.values())
        {
            assertEquals(Integer.valueOf(0), count);
        }
    }

    @Test
    public void testCountAssignments()
    {
        Domain sv = sequenceVariants();
        Authority authority = sv.getAuthority();
        Map<String, Concept> svByName = indexByName(sv);
        Concept stop_gained = svByName.get("stop_gained");
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("IAE", 1.0d, 1.0d));
        authority.createAssignment(stop_gained, new AbstractAssignable() {}, evidence);
        assertEquals(Integer.valueOf(1), countAssignments(sv).get(stop_gained));
    }

    @Test
    public void testSequenceFeatures()
    {
        Domain sf = sequenceFeatures();
        assertNotNull(sf);
        boolean found = false;
        for (Concept concept : sf.getConcepts())
        {
            if ("sequence_feature".equals(concept.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testSequenceVariants()
    {
        Domain sv = sequenceVariants();
        assertNotNull(sv);
        boolean found = false;
        for (Concept concept : sv.getConcepts())
        {
            if ("stop_lost".equals(concept.getName()))
            {
                found = true;
            }
        }
        assertTrue(found);
    }
}