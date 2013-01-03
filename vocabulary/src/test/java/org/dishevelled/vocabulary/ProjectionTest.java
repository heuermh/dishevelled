/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2013 held jointly by the individual authors.

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

import java.util.Set;
import java.util.HashSet;

import junit.framework.TestCase;

/**
 * Unit test for Projection.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3 $ $Date: 2003/12/02 03:41:15 $
 */
public class ProjectionTest
    extends TestCase
{

    /**
     * Create a new projection.
     */
    private Projection createProjection(String name)
    {
        Authority authority = new Authority("test authority");
        Domain sourceDomain = authority.createDomain("source domain");
        Domain targetDomain = authority.createDomain("target domain");
        Concept sourceConcept = sourceDomain.createConcept("source", "source", "source");
        Concept targetConcept = targetDomain.createConcept("target", "target", "target");
        Mapping mapping = authority.createMapping(sourceDomain, targetDomain);
        Projection projection = mapping.createProjection(name, sourceConcept, targetConcept, null);
        return projection;
    }

    public void testProjection()
    {
        Projection p = createProjection("projection");

        assertNotNull("p not null", p);
        assertNotNull("name not null", p.getName());
        assertNotNull("p toString not null", p.toString());
        assertEquals("name equals projection", "projection", p.getName());
        assertNotNull("evidence not null", p.getEvidence());
        assertNotNull("source not null", p.getSource());
        assertNotNull("target not null", p.getTarget());
        assertNotNull("mapping not null", p.getMapping());

        Mapping mapping = p.getMapping();
        Domain sourceDomain = mapping.getSource();
        Domain targetDomain = mapping.getTarget();
        Concept sourceConcept = p.getSource();
        Concept targetConcept = p.getTarget();

        assertTrue("sourceDomain contains sourceConcept", sourceDomain.getConcepts().contains(sourceConcept));
        assertTrue("targetDomain contains targetConcept", targetDomain.getConcepts().contains(targetConcept));
        assertEquals("sourceConcept domain equals sourceDomain", sourceDomain, sourceConcept.getDomain());
        assertEquals("targetConcept domain equals targetDomain", targetDomain, targetConcept.getDomain());

        assertTrue("mapping contains p", mapping.getProjections().contains(p));
        assertTrue("sourceConcept outProjections contains p", sourceConcept.outProjections().contains(p));
        assertTrue("sourceConcept inProjections does not contain p", !sourceConcept.inProjections().contains(p));
        assertTrue("sourceConcept projections contains p", sourceConcept.getProjections().contains(p));
        assertTrue("targetConcept outProjections does not contain p", !targetConcept.outProjections().contains(p));
        assertTrue("targetConcept inProjections contains p", targetConcept.inProjections().contains(p));
        assertTrue("targetConcept projections contains p", targetConcept.getProjections().contains(p));
    }

    public void testConstructor()
    {
        Authority authority = new Authority("test authority");
        Domain sourceDomain = authority.createDomain("source domain");
        Domain targetDomain = authority.createDomain("target domain");
        Concept sourceConcept = sourceDomain.createConcept("source", "source", "source");
        Concept targetConcept = targetDomain.createConcept("target", "target", "target");
        Mapping mapping = authority.createMapping(sourceDomain, targetDomain);

        Evidence evidence0 = new Evidence("evidence0", 1.0d, 1.0d);
        Set<Evidence> evidence = new HashSet<Evidence>();
        evidence.add(evidence0);

        Projection p0 = new Projection(mapping, "name", sourceConcept, targetConcept, evidence);
        Projection p1 = new Projection(mapping, "name", sourceConcept, targetConcept, new HashSet<Evidence>());
        Projection p2 = new Projection(mapping, "name", sourceConcept, targetConcept, null);
        Projection p3 = new Projection(mapping, null, sourceConcept, targetConcept, evidence);

       try
        {
            Projection p4 = new Projection(null, "name", sourceConcept, targetConcept, evidence);
            fail("ctr(null,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

       try
        {
            Projection p5 = new Projection(mapping, "name", null, targetConcept, evidence);
            fail("ctr(,,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

       try
        {
            Projection p6 = new Projection(mapping, "name", sourceConcept, null, evidence);
            fail("ctr(,,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
