/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2012 held jointly by the individual authors.

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

import java.util.Arrays;
import java.util.TreeSet;
import java.util.Collections;

import junit.framework.TestCase;

/**
 * Unit test for Authority.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3.2.2 $ $Date: 2004/02/06 03:35:55 $
 */
public class AuthorityTest
    extends TestCase
{

    /**
     * Create a new authority with the specified name.
     */
    private Authority createAuthority(String name)
    {
        Authority authority = new Authority(name);
        return authority;
    }

    public void testAuthority()
    {
        Authority a = createAuthority("test authority");

        assertNotNull("a not null", a);
        assertNotNull("name not null", a.getName());
        assertNotNull("a toString not null", a.toString());
        assertEquals("name equals test authority", "test authority", a.getName());

        assertNotNull("domains not null", a.getDomains());
        assertNotNull("mappings not null", a.getMappings());
        assertNotNull("assignments not null", a.getAssignments());

        Domain domain0 = a.createDomain("domain0");

        assertTrue("domains contains domain0", a.getDomains().contains(domain0));
        assertEquals("domain0 authority equals a", a, domain0.getAuthority());

        Domain domain1 = a.createDomain("domain1");

        assertTrue("domains contains domain1", a.getDomains().contains(domain1));
        assertEquals("domain1 authority equals a", a, domain1.getAuthority());

        Mapping mapping = a.createMapping(domain0, domain1);

        assertTrue("mappings contains mapping", a.getMappings().contains(mapping));
        assertEquals("mapping authority equals a", a, mapping.getAuthority());

        Concept concept = domain0.createConcept("concept", "concept", "concept");
        Assignable assignable = new TestAssignable();
        Evidence evidence = new Evidence("test evidence", 1.0d, 1.0d);

        Assignment assignment = a.createAssignment(concept, assignable, Collections.singleton(evidence));

        assertTrue("assignments contains assignment", a.getAssignments().contains(assignment));
        assertEquals("assignment authority equals a", a, assignment.getAuthority());

        try
        {
            a.addDomain(null);
            fail("addDomain(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            a.addMapping(null);
            fail("addMapping(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            a.addAssignment(null);
            fail("addAssignment(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateDomain()
    {
        Authority a = createAuthority("test authority");

        Domain d0 = a.createDomain("domain0");
        Domain d1 = a.createDomain("domain1");

        try
        {
            Domain d2 = a.createDomain(null);
            fail("createDomain(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Domain d3 = a.createDomain("domain0");
            fail("createDomain(domain0) expected IllegalArgumentException, " +
                 "domain0 name not unique");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateMapping()
    {
        Authority a = createAuthority("test authority");

        Domain source = a.createDomain("source");
        Domain target = a.createDomain("target");

        Mapping m0 = a.createMapping(source, target);
        Mapping m1 = a.createMapping(source, target);
        Mapping m2 = a.createMapping(target, source);

        try
        {
            Mapping m3 = a.createMapping(null, target);
            fail("createMapping(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Mapping m4 = a.createMapping(source, null);
            fail("createMapping(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateAssignment()
    {
        Authority a = createAuthority("test authority");

        Domain domain = a.createDomain("test domain");
        Concept concept0 = domain.createConcept("concept0", "concept0", "concept0");
        Concept concept1 = domain.createConcept("concept1", "concept1", "concept1");
        Assignable assignable = new TestAssignable();
        Evidence evidence0 = new Evidence("evidence0", 1.0d, 1.0d);
        Evidence evidence1 = new Evidence("evidence1", 1.0d, 1.0d);

        Assignment a0 = a.createAssignment(concept0, assignable, new TreeSet<Evidence>(Arrays.asList(new Evidence[] { evidence0, evidence1 })));
        Assignment a1 = a.createAssignment(concept1, assignable, new TreeSet<Evidence>(Arrays.asList(new Evidence[] { evidence0, evidence1 })));
        Assignment a2 = a.createAssignment(concept0, assignable, Collections.singleton(evidence0));
        Assignment a3 = a.createAssignment(concept0, assignable, Collections.singleton(evidence1));
        Assignment a4 = a.createAssignment(concept0, assignable, new TreeSet<Evidence>());
        Assignment a5 = a.createAssignment(concept0, assignable, null);

        try
        {
            Assignment a6 = a.createAssignment(null, assignable, new TreeSet<Evidence>(Arrays.asList(new Evidence[] { evidence0, evidence1 })));
            fail("createAssignment(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Assignment a7 = a.createAssignment(concept0, null, new TreeSet<Evidence>(Arrays.asList(new Evidence[] { evidence0, evidence1 })));
            fail("createAssignment(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testConstructor()
    {
        try
        {
            Authority authority = new Authority(null);
            fail("ctr(null) expected IllegalArgumentException");
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
