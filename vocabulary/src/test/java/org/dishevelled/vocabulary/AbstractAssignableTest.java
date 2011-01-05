/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2011 held jointly by the individual authors.

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

import junit.framework.TestCase;

/**
 * Abstract unit test for the Assignable interface.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2.2.1 $ $Date: 2004/02/05 23:56:06 $
 */
public abstract class AbstractAssignableTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of Assignable to test.
     *
     * @return a new instance of an implementation of Assignable
     */
    protected abstract Assignable createAssignable();

    public void testAbstractAssignable()
    {
        Assignable assignable = createAssignable();
        Assignable invalidAssignable = createAssignable();

        Authority authority = new Authority("test authority");
        Domain domain = authority.createDomain("test domain");
        Concept concept = domain.createConcept("name", "accession", "description");
        Set<Evidence> evidence = null;
        Assignment assignment = authority.createAssignment(concept, assignable, evidence);
        Assignment invalidAssignment = authority.createAssignment(concept, invalidAssignable, evidence);

        assertNotNull("assignments not null", assignable.getAssignments());
        assignable.addAssignment(assignment);
        assertTrue(assignable.getAssignments().contains(assignment));

        try
        {
            assignable.addAssignment(null);
            fail("addAssignment(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            assignable.addAssignment(invalidAssignment);
            fail("addAssignment(invalidAssignment) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
