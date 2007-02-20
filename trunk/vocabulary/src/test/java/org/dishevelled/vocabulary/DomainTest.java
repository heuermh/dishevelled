/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.vocabulary;

import junit.framework.TestCase;

/**
 * Unit test for Domain.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2003/12/02 03:41:15 $
 */
public class DomainTest
    extends TestCase
{

    /**
     * Create a new domain with the specified name.
     */
    private Domain createDomain(String name)
    {
        Authority authority = new Authority("test authority");
        Domain domain = authority.createDomain(name);
        return domain;
    }

    public void testDomain()
    {
        Domain d = createDomain("test domain");

        assertNotNull("d not null", d);
        assertNotNull("name not null", d.getName());
        assertNotNull("d toString not null", d.toString());
        assertEquals("name equals test domain", "test domain", d.getName());

        assertNotNull("concepts not null", d.getConcepts());
        assertNotNull("relations not null", d.getRelations());
        assertNotNull("inMappings not null", d.inMappings());
        assertNotNull("outMappings not null", d.outMappings());
        assertNotNull("authority not null", d.getAuthority());

        Concept concept0 = d.createConcept("concept0", "concept0", "concept0");

        assertTrue("concepts contains concept0", d.getConcepts().contains(concept0));
        assertEquals("concept0 domain equals d", d, concept0.getDomain());

        Concept concept1 = d.createConcept("concept1", "concept1", "concept1");

        assertTrue("concepts contains concept1", d.getConcepts().contains(concept1));
        assertEquals("concept1 domain equals d", d, concept1.getDomain());

        Concept concept2 = d.createConcept("concept2", "concept2", "concept2");

        assertTrue("concepts contains concept2", d.getConcepts().contains(concept2));
        assertEquals("concept2 domain equals d", d, concept2.getDomain());

        Relation relation0 = d.createRelation("relation0", concept0, concept1);

        assertTrue("relations contains relation0", d.getRelations().contains(relation0));
        assertEquals("relation0 domain equals d", d, relation0.getDomain());

        Relation relation1 = d.createRelation("relation1", concept1, concept2);

        assertTrue("relations contains relation1", d.getRelations().contains(relation1));
        assertEquals("relation1 domain equals d", d, relation1.getDomain());

        Authority authority = d.getAuthority();

        Domain source = d;
        Domain target0 = authority.createDomain("target0");
        Mapping mapping0 = authority.createMapping(source, target0);

        assertTrue("outMappings contains mapping0", source.outMappings().contains(mapping0));
        assertTrue("inMappings does not contain mapping0", !source.inMappings().contains(mapping0));
        assertTrue("mappings contains mapping0", source.getMappings().contains(mapping0));
        assertEquals("mapping0 source domain equals source", source, mapping0.getSource());
        assertEquals("mapping0 target domain equals target0", target0, mapping0.getTarget());

        Domain target1 = authority.createDomain("target1");
        Mapping mapping1 = authority.createMapping(source, target1);

        assertTrue("outMappings contains mapping1", source.outMappings().contains(mapping1));
        assertTrue("inMappings does not contain mapping1", !source.inMappings().contains(mapping1));
        assertTrue("mappings contains mapping1", source.getMappings().contains(mapping1));
        assertEquals("mapping1 source domain equals source", source, mapping1.getSource());
        assertEquals("mapping1 target domain equals target1", target1, mapping1.getTarget());

        Domain target = d;
        Domain source0 = authority.createDomain("source0");
        Mapping mapping2 = authority.createMapping(source0, target);

        assertTrue("inMappings contains mapping2", target.inMappings().contains(mapping2));
        assertTrue("outMappings does not contain mapping2", !target.outMappings().contains(mapping2));
        assertTrue("mappings contains mapping2", target.getMappings().contains(mapping2));
        assertEquals("mapping2 source domain equals source0", source0, mapping2.getSource());
        assertEquals("mapping2 target domain equals target", target, mapping2.getTarget());

        Domain source1 = authority.createDomain("source1");
        Mapping mapping3 = authority.createMapping(source1, target);

        assertTrue("inMappings contains mapping3", target.inMappings().contains(mapping3));
        assertTrue("outMappings does not contain mapping3", !target.outMappings().contains(mapping3));
        assertTrue("mappings contains mapping3", target.getMappings().contains(mapping3));
        assertEquals("mapping3 source domain equals source1", source1, mapping3.getSource());
        assertEquals("mapping3 target domain equals target", target, mapping3.getTarget());

        try
        {
            d.addConcept(null);
            fail("addConcept(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            d.addRelation(null);
            fail("addRelation(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            d.addInMapping(null);
            fail("addInMapping(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            d.addOutMapping(null);
            fail("addOutMapping(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testConstructor()
    {
        Authority auth = new Authority("test authority");
        Domain d0 = new Domain(auth, "name");
        Domain d1 = new Domain(auth, null);

        try
        {
            Domain d2 = new Domain(null, "name");
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateConcept()
    {
        Domain d = createDomain("test domain");

        Concept c0 = d.createConcept("concept0", "concept0", "concept0");
        Concept c1 = d.createConcept(null, "concept1", "concept1");
        Concept c2 = d.createConcept("concept2", "concept2", null);

        try
        {
            Concept c3 = d.createConcept("concept3", null, "concept3");
            fail("createConcept(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Concept c4 = d.createConcept("concept4", "concept0", "concept4");
            fail("createConcept(,concept0,) expected IllegalArgumentException, " +
                 "concept0 accession not unique");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateRelation()
    {
        Domain d = createDomain("test domain");

        Concept source = d.createConcept("source", "source", "source");
        Concept target = d.createConcept("target", "target", "target");

        Relation r0 = d.createRelation("relation0", source, target);
        Relation r1 = d.createRelation("relation1", source, target);
        Relation r2 = d.createRelation("", source, target);
        Relation r3 = d.createRelation(null, source, target);

        try
        {
            Relation r4 = d.createRelation("relation4", null, target);
            fail("createRelation(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Relation r5 = d.createRelation("relation5", source, null);
            fail("createRelation(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Relation r6 = d.createRelation("relation0", source, target);
            fail("createRelation(relation0,,) expected IllegalArgumentException, " +
                 "combination of name, source, target not unique");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Relation r7 = d.createRelation(null, source, target);
            fail("createRelation(null,,) expected IllegalArgumentException, " +
                 "combination of name, source, target not unique");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
