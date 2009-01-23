/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2009 held jointly by the individual authors.

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
package org.dishevelled.vocabulary;

import junit.framework.TestCase;

/**
 * Unit test for Concept.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3.2.1 $ $Date: 2004/02/05 23:56:06 $
 */
public class ConceptTest
    extends TestCase
{

    /**
     * Create a new concept with the specified
     * name, accession, and definition.
     */
    private Concept createConcept(final String name, final String accession, final String description)
    {
        Authority authority = new Authority("test authority");
        Domain domain = authority.createDomain("test domain");
        Concept concept = domain.createConcept(name, accession, description);
        return concept;
    }

    public void testConcept()
    {
        Concept c = createConcept("concept name", "concept accession", "concept definition");

        assertNotNull("c not null", c);
        assertNotNull("name not null", c.getName());
        assertNotNull("c toString not null", c.toString());
        assertEquals("name equals concept name", "concept name", c.getName());
        assertNotNull("accession not null", c.getAccession());
        assertEquals("accession equals concept accession", "concept accession", c.getAccession());
        assertNotNull("definition not null", c.getDefinition());
        assertEquals("definition equals concept definition", "concept definition", c.getDefinition());

        assertTrue("degree >= 0, was " + c.degree(), c.degree() >= 0);
        assertNotNull("inRelations not null", c.inRelations());
        assertNotNull("outRelations not null", c.outRelations());
        assertNotNull("relations not null", c.getRelations());
        assertNotNull("inProjections not null", c.inProjections());
        assertNotNull("outProjections not null", c.outProjections());
        assertNotNull("projections not null", c.getProjections());
        assertNotNull("assignments not null", c.getAssignments());
        assertNotNull("domain not null", c.getDomain());

        Domain domain = c.getDomain();

        Concept source = c;
        Concept target0 = domain.createConcept("target0", "target0", "target0");
        Concept target1 = domain.createConcept("target1", "target1", "target1");

        Relation relation0 = domain.createRelation("relation0", source, target0);

        assertTrue("outRelations contains relation0", source.outRelations().contains(relation0));
        assertTrue("inRelations does not contain relation0", !source.inRelations().contains(relation0));
        assertTrue("relations contains relation0", source.getRelations().contains(relation0));
        assertEquals("relation0 source concept equals source", source, relation0.getSource());
        assertEquals("relation0 target concept equals target0", target0, relation0.getTarget());
        assertTrue("degree >= 1, was " + source.degree(), source.degree() >= 1);

        Relation relation1 = domain.createRelation("relation1", source, target1);

        assertTrue("outRelations contains relation1", source.outRelations().contains(relation1));
        assertTrue("inRelations does not contain relation1", !source.inRelations().contains(relation1));
        assertTrue("relations contains relation1", source.getRelations().contains(relation1));
        assertEquals("relation1 source concept equals source", source, relation1.getSource());
        assertEquals("relation1 target concept equals target1", target1, relation1.getTarget());
        assertTrue("degree >= 2, was " + source.degree(), source.degree() >= 2);

        Concept target = c;
        Concept source0 = domain.createConcept("source0", "source0", "source0");
        Concept source1 = domain.createConcept("source1", "source1", "source1");

        Relation relation2 = domain.createRelation("relation2", source0, target);

        assertTrue("outRelations does not contain relation2", !target.outRelations().contains(relation2));
        assertTrue("inRelations does not contain relation2", target.inRelations().contains(relation2));
        assertTrue("relations contains relation2", target.getRelations().contains(relation2));
        assertEquals("relation2 source concept equals source0", source0, relation2.getSource());
        assertEquals("relation2 target concept equals target", target, relation2.getTarget());
        assertTrue("degree >= 3, was " + target.degree(), target.degree() >= 3);

        Relation relation3 = domain.createRelation("relation3", source1, target);

        assertTrue("outRelations does not contain relation3", !target.outRelations().contains(relation3));
        assertTrue("inRelations does not contain relation3", target.inRelations().contains(relation3));
        assertTrue("relations contains relation3", target.getRelations().contains(relation3));
        assertEquals("relation3 source concept equals source1", source1, relation3.getSource());
        assertEquals("relation3 target concept equals target", target, relation3.getTarget());
        assertTrue("degree >= 4, was " + target.degree(), target.degree() >= 4);

        // test projections

        Authority authority = domain.getAuthority();

        Domain sourceDomain = domain;
        Domain targetDomain0 = authority.createDomain("targetDomain0");
        Domain targetDomain1 = authority.createDomain("targetDomain1");
        Mapping mapping0 = authority.createMapping(sourceDomain, targetDomain0);
        Mapping mapping1 = authority.createMapping(sourceDomain, targetDomain1);

        source = c;
        Concept targetConcept0 = targetDomain0.createConcept("targetConcept0", "targetConcept0", "targetConcept0");
        Concept targetConcept1 = targetDomain1.createConcept("targetConcept1", "targetConcept1", "targetConcept1");

        Projection projection0 = mapping0.createProjection("projection0", source, targetConcept0, null);

        assertTrue("outProjections contains projection0", source.outProjections().contains(projection0));
        assertTrue("inProjections does not contain projection0", !source.inProjections().contains(projection0));
        assertTrue("projections contains projection0", source.getProjections().contains(projection0));
        assertEquals("projection0 source concept equals source", source, projection0.getSource());
        assertEquals("projection0 target concept equals targetConcept0", targetConcept0, projection0.getTarget());
        assertTrue("degree >= 5, was " + source.degree(), source.degree() >= 5);

        Projection projection1 = mapping1.createProjection("projection1", source, targetConcept1, null);

        assertTrue("outProjections contains projection1", source.outProjections().contains(projection1));
        assertTrue("inProjections does not contain projection1", !source.inProjections().contains(projection1));
        assertTrue("projections contains projection1", source.getProjections().contains(projection1));
        assertEquals("projection1 source concept equals source", source, projection1.getSource());
        assertEquals("projection1 target concept equals targetConcept1", targetConcept1, projection1.getTarget());
        assertTrue("degree >= 6, was " + source.degree(), source.degree() >= 6);

        Domain targetDomain = domain;
        Domain sourceDomain0 = targetDomain0;
        Domain sourceDomain1 = targetDomain1;
        Mapping mapping2 = authority.createMapping(sourceDomain0, targetDomain);
        Mapping mapping3 = authority.createMapping(sourceDomain1, targetDomain);

        target = c;
        Concept sourceConcept0 = targetDomain0.createConcept("sourceConcept0", "sourceConcept0", "sourceConcept0");
        Concept sourceConcept1 = targetDomain1.createConcept("sourceConcept1", "sourceConcept1", "sourceConcept1");

        Projection projection2 = mapping2.createProjection("projection2", sourceConcept0, target, null);

        assertTrue("outProjections does not contain projection2", !target.outProjections().contains(projection2));
        assertTrue("inProjections contains projection2", target.inProjections().contains(projection2));
        assertTrue("projections contains projection2", target.getProjections().contains(projection2));
        assertEquals("projection2 source concept equals sourceConcept0", sourceConcept0, projection2.getSource());
        assertEquals("projection2 target concept equals target", target, projection2.getTarget());
        assertTrue("degree >= 7, was " + target.degree(), target.degree() >= 7);

        Projection projection3 = mapping3.createProjection("projection3", sourceConcept1, target, null);

        assertTrue("outProjections does not contain projection3", !target.outProjections().contains(projection3));
        assertTrue("inProjections contains projection3", target.inProjections().contains(projection3));
        assertTrue("projections contains projection3", target.getProjections().contains(projection3));
        assertEquals("projection3 source concept equals sourceConcept1", sourceConcept1, projection3.getSource());
        assertEquals("projection3 target concept equals target", target, projection3.getTarget());
        assertTrue("degree >= 8, was " + target.degree(), target.degree() >= 8);

        // test assignments

        TestAssignable assignable0 = new TestAssignable();
        TestAssignable assignable1 = new TestAssignable();

        Assignment assignment0 = authority.createAssignment(c, assignable0, null);

        assertTrue("assignments contains assignment0", c.getAssignments().contains(assignment0));
        assertEquals("assignment0 concept equals c", c, assignment0.getConcept());
        assertTrue("degree >= 9, was " + c.degree(), c.degree() >= 9);

        Assignment assignment1 = authority.createAssignment(c, assignable1, null);

        assertTrue("assignments contains assignment0", c.getAssignments().contains(assignment0));
        assertEquals("assignment0 concept equals c", c, assignment0.getConcept());
        assertTrue("degree >= 9, was " + c.degree(), c.degree() >= 9);

        try
        {
            c.addInRelation(null);
            fail("addInRelation(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            c.addOutRelation(null);
            fail("addOutRelation(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            c.addInProjection(null);
            fail("addInProjection(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            c.addOutProjection(null);
            fail("addOutProjection(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            c.addAssignment(null);
            fail("addAssignment(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testConstructor()
    {
        Authority auth = new Authority("test authority");
        Domain domain = auth.createDomain("test domain");

        Concept c0 = new Concept(domain, "name", "accession", "description");
        Concept c1 = new Concept(domain, null, "accession", "description");
        Concept c2 = new Concept(domain, "name", "accession", null);

        try
        {
            Concept c3 = new Concept(null, "name", "accession", "description");
            fail("ctr(null,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Concept c4 = new Concept(domain, "name", null, "description");
            fail("ctr(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    /** Mock assignable class. */
    private class TestAssignable
        extends AbstractAssignable
    {
        // empty
    }
}
