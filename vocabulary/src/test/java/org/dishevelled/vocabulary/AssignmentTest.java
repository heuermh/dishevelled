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
import java.util.HashSet;

import junit.framework.TestCase;

/**
 * Unit test for Assignment.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2003/11/19 08:51:10 $
 */
public class AssignmentTest
    extends TestCase
{

    /**
     * Create a new assignment.
     */
    private Assignment createAssignment()
    {
        Authority authority = new Authority("test authority");
        Domain domain = authority.createDomain("test domain");
        Concept concept = domain.createConcept("concept", "concept", "concept");
        Assignable assignable = new TestAssignable();
        Assignment assignment = authority.createAssignment(concept, assignable, null);
        return assignment;
    }

    public void testAssignment()
    {
        Assignment a = createAssignment();

        assertNotNull("a not null", a);
        assertNotNull("a toString not null", a.toString());
        assertNotNull("evidence not null", a.getEvidence());
        assertNotNull("concept not null", a.getConcept());
        assertNotNull("assignable not null", a.getAssignable());
        assertNotNull("authority not null", a.getAuthority());

        Concept concept = a.getConcept();
        Assignable assignable = a.getAssignable();
        Authority authority = a.getAuthority();

        assertTrue("concept assignments contains a", concept.getAssignments().contains(a));
        assertTrue("assignable assignments contains a", assignable.getAssignments().contains(a));
        assertTrue("authority contains a", authority.getAssignments().contains(a));

        Evidence evidence1 = new Evidence("evidence1", 1.0d, 1.0d);
        a.addEvidence(evidence1);
        assertTrue("a evidence contains evidence1", a.getEvidence().contains(evidence1));
    }

    public void testConstructor()
    {
        Authority authority = new Authority("test authority");
        Domain domain = authority.createDomain("test domain");
        Concept concept = domain.createConcept("concept", "concept", "concept");
        Assignable assignable = new TestAssignable();

        Evidence evidence0 = new Evidence("evidence0", 1.0d, 1.0d);
        Set<Evidence> evidence = new HashSet<Evidence>();
        evidence.add(evidence0);

        Assignment sa0 = new Assignment(authority, concept, assignable, evidence);
        Assignment sa1 = new Assignment(authority, concept, assignable, new HashSet<Evidence>());
        Assignment sa2 = new Assignment(authority, concept, assignable, null);

        try
        {
            Assignment sa3 = new Assignment((Authority) null, concept, assignable, evidence);
            fail("ctr(null,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Assignment sa4 = new Assignment(authority, (Concept) null, assignable, evidence);
            fail("ctr(,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Assignment sa5 = new Assignment(authority, concept, (Assignable) null, evidence);
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
