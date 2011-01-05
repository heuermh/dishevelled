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

/**
 * Unit test for AssignableSupport, used via delegation.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2.2.1 $ $Date: 2004/02/05 23:56:06 $
 */
public class AssignableSupportViaDelegationTest
    extends AbstractAssignableTest
{

    /** {@inheritDoc} */
    protected Assignable createAssignable()
    {
        return new ViaDelegation();
    }

    public void testNullAssignable()
    {
        try
        {
            Assignable a = new NullViaDelegation();
            fail("setAssignable(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    /** Mock assignable that improperly sets the assignable to null. */
    private class NullViaDelegation
        implements Assignable
    {

        /** Assignable support. */
        private final AssignableSupport assignableSupport;


        /**
         * Create a new mock assignable via delegation.
         */
        public NullViaDelegation()
        {
            assignableSupport = new AssignableSupport(null);
        }


        /** @see Assignable */
        public Set<Assignment> getAssignments()
        {
            return assignableSupport.getAssignments();
        }

        /** @see Assignable */
        public void addAssignment(final Assignment assignment)
        {
            assignableSupport.addAssignment(assignment);
        }
    }

    /** Mock assignable that uses AssignableSupport via delegation. */
    private class ViaDelegation
        implements Assignable
    {

        /** Assignable support. */
        private final AssignableSupport assignableSupport;


        /**
         * Create a new mock assignable via delegation.
         */
        public ViaDelegation()
        {
            assignableSupport = new AssignableSupport(this);
        }


        /** @see Assignable */
        public Set<Assignment> getAssignments()
        {
            return assignableSupport.getAssignments();
        }

        /** @see Assignable */
        public void addAssignment(final Assignment assignment)
        {
            assignableSupport.addAssignment(assignment);
        }
    }
}
