/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2007 held jointly by the individual authors.

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
 * Test case for Relation.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3 $ $Date: 2003/12/02 03:41:15 $
 */
public class RelationTest
    extends TestCase
{

    /**
     * Create a new relation.
     */
    private Relation createRelation(String name)
    {
        Authority authority = new Authority("test authority");
        Domain domain = authority.createDomain("test domain");
        Concept source = domain.createConcept("source", "source", "source");
        Concept target = domain.createConcept("target", "target", "target");
        Relation relation = domain.createRelation(name, source, target);
        return relation;
    }

    public void testRelation()
    {
        Relation r = createRelation("relation");

        assertNotNull("r not null", r);
        assertNotNull("name not null", r.getName());
        assertNotNull("r toString not null", r.toString());
        assertEquals("name equals relation", "relation", r.getName());
        assertNotNull("source not null", r.getSource());
        assertNotNull("target not null", r.getTarget());
        assertNotNull("domain not null", r.getDomain());

        Domain domain = r.getDomain();
        Concept source = r.getSource();
        Concept target = r.getTarget();

        assertTrue("domain contains source", domain.getConcepts().contains(source));
        assertTrue("domain contains target", domain.getConcepts().contains(target));
        assertEquals("source domain equals domain", domain, source.getDomain());
        assertEquals("target domain equals domain", domain, target.getDomain());

        assertTrue("domain contains r", domain.getRelations().contains(r));
        assertTrue("source outRelations contains r", source.outRelations().contains(r));
        assertTrue("source inRelations does not contain r", !source.inRelations().contains(r));
        assertTrue("source relations contains r", source.getRelations().contains(r));
        assertTrue("target outRelations does not contain r", !target.outRelations().contains(r));
        assertTrue("target inRelations contains r", target.inRelations().contains(r));
        assertTrue("target relations contains r", target.getRelations().contains(r));
    }

    public void testConstructor()
    {
        Authority authority = new Authority("test authority");
        Domain domain = authority.createDomain("test domain");
        Concept source = domain.createConcept("source", "source", "source");
        Concept target = domain.createConcept("target", "target", "target");

        Relation r0 = new Relation(domain, "name", source, target);
        Relation r1 = new Relation(domain, null, source, target);

        try
        {
            Relation r2 = new Relation(null, "name", source, target);
            fail("ctr(null,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Relation r3 = new Relation(domain, "name", null, target);
            fail("ctr(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Relation r4 = new Relation(domain, "name", source, null);
            fail("ctr(,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
