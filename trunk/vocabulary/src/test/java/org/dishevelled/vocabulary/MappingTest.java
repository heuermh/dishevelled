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

import java.util.Set;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.Collections;

import junit.framework.TestCase;

/**
 * Unit test for Mapping.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3.2.2 $ $Date: 2004/02/06 03:35:55 $
 */
public class MappingTest
    extends TestCase
{

    /**
     * Create a new mapping.
     */
    private Mapping createMapping()
    {
        Authority authority = new Authority("test authority");
        Domain sourceDomain = authority.createDomain("source domain");
        Domain targetDomain = authority.createDomain("target domain");
        Mapping mapping = authority.createMapping(sourceDomain, targetDomain);
        return mapping;
    }

    public void testMapping()
    {
        Mapping m = createMapping();

        assertNotNull("m not null", m);
        assertNotNull("m toString not null", m.toString());
        assertNotNull("source not null", m.getSource());
        assertNotNull("target not null", m.getTarget());
        assertNotNull("projections not null", m.getProjections());
        assertNotNull("authority not null", m.getAuthority());

        Domain sourceDomain = m.getSource();
        Domain targetDomain = m.getTarget();
        Authority authority = m.getAuthority();

        assertTrue("sourceDomain outMappings contains m", sourceDomain.outMappings().contains(m));
        assertTrue("sourceDomain inMappings does not contain m", !sourceDomain.inMappings().contains(m));
        assertTrue("sourceDomain mappings contains m", sourceDomain.getMappings().contains(m));
        assertTrue("targetDomain outMappings does not contain m", !targetDomain.outMappings().contains(m));
        assertTrue("targetDomain inMappings contains m", targetDomain.inMappings().contains(m));
        assertTrue("targetDomain mappings contains m", targetDomain.getMappings().contains(m));
        assertTrue("authority mappings contains m", authority.getMappings().contains(m));
    }

    public void testConstructor()
    {
        Authority authority = new Authority("test authority");
        Domain sourceDomain = authority.createDomain("source domain");
        Domain targetDomain = authority.createDomain("target domain");

        Mapping m0 = new Mapping(authority, sourceDomain, targetDomain);

        try
        {
            Mapping m1 = new Mapping(null, sourceDomain, targetDomain);
            fail("ctr(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Mapping m2 = new Mapping(authority, null, targetDomain);
            fail("ctr(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Mapping m3 = new Mapping(authority, sourceDomain, null);
            fail("ctr(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateProjection()
    {
        Mapping m = createMapping();

        Domain sourceDomain = m.getSource();
        Domain targetDomain = m.getTarget();

        Concept sourceConcept0 = sourceDomain.createConcept("source0", "source0", "source0");
        Concept sourceConcept1 = sourceDomain.createConcept("source1", "source1", "source1");
        Concept targetConcept0 = targetDomain.createConcept("target0", "target0", "target0");
        Concept targetConcept1 = targetDomain.createConcept("target1", "target1", "target1");

        Evidence e0 = new Evidence("evidence0", 1.0d, 1.0d);
        Evidence e1 = new Evidence("evidence1", 0.5d, 0.5d);

        Projection p0 = m.createProjection("projection0", sourceConcept0, targetConcept0, null);
        Projection p1 = m.createProjection("projection1", sourceConcept0, targetConcept0, null);
        Projection p2 = m.createProjection("", sourceConcept0, targetConcept0, null);
        Projection p3 = m.createProjection(null, sourceConcept0, targetConcept0, null);

        Projection p4 = m.createProjection("projection4", sourceConcept0, targetConcept0, new TreeSet<Evidence>(Arrays.asList(new Evidence[] { e0, e1 })));
        Projection p5 = m.createProjection("projection5", sourceConcept0, targetConcept0, Collections.singleton(e0));
        Projection p6 = m.createProjection("projection6", sourceConcept0, targetConcept0, Collections.singleton(e1));
        Projection p7 = m.createProjection("projection7", sourceConcept0, targetConcept0, new TreeSet<Evidence>());

        // source must be within source domain

        try
        {
            Projection p8 = m.createProjection("projection8", targetConcept0, targetConcept0, null);
            fail("createProjection(,targetConcept0,targetConcept0,) expected IllegalArgumentException, "
                 + "source must be within source domain");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Projection p9 = m.createProjection("projection9", targetConcept1, targetConcept0, null);
            fail("createProjection(,targetConcept1,targetConcept0,) expected IllegalArgumentException, "
                 + "source must be within source domain");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // target must be within target domain

        try
        {
            Projection p10 = m.createProjection("projection10", sourceConcept0, sourceConcept0, null);
            fail("createProjection(,sourceConcept0,sourceConcept0,) expected IllegalArgumentException, "
                 + "target must be within target domain");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Projection p11 = m.createProjection("projection11", sourceConcept0, sourceConcept1, null);
            fail("createProjection(,sourceConcept0,sourceConcept1,) expected IllegalArgumentException, "
                 + "target must be within target domain");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // name, source, target are unique

        try
        {
            Projection p12 = m.createProjection("projection0", sourceConcept0, targetConcept0, null);
            fail("createProjection(projection0,sourceConcept0,targetConcept0,) expected IllegalArgumentException, "
                 + "combination of name, source, target not unique");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }


        // source and target must not be null

        try
        {
            Projection p13 = m.createProjection("projection13", null, targetConcept0, null);
            fail("createProjection(,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Projection p14 = m.createProjection("projection14", sourceConcept0, null, null);
            fail("createProjection(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
