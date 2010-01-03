/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2010 held jointly by the individual authors.

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

/**
 * A class that can be used via delegation to provide Assignable support.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.4.2.1 $ $Date: 2004/02/05 22:39:51 $
 */
public class AssignableSupport
{
    /** Assignable entity. */
    private Object assignable;

    /** Set of assignments. */
    private final Set<Assignment> assignments;


    /**
     * Create a new support class meant to be subclassed.  The
     * subclass should call <code>setAssignable(this)</code> before
     * calling any of the Assignable methods.
     */
    protected AssignableSupport()
    {
        assignments = new HashSet<Assignment>();
    }

    /**
     * Create a new support class that adds Assignable support
     * to the specified entity.
     *
     * @param assignable assignable, must not be null
     */
    public AssignableSupport(final Object assignable)
    {
        this();
        setAssignable(assignable);
    }


    /**
     * Set the assignable entity for this support class to <code>assignable</code>.
     * Subclasses should call this method before any of the Assignable
     * methods.
     *
     * @param assignable assignable, must not be null
     */
    protected void setAssignable(final Object assignable)
    {
        if (assignable == null)
        {
            throw new IllegalArgumentException("assignable must not be null");
        }
        this.assignable = assignable;
    }

    /** {@inheritDoc} */
    public Set<Assignment> getAssignments()
    {
        return assignments;
    }

    /** {@inheritDoc} */
    public void addAssignment(final Assignment assignment)
    {
        if (assignment == null)
        {
            throw new IllegalArgumentException("assignment must not be null");
        }
        if (!assignment.getAssignable().equals(assignable))
        {
            throw new IllegalArgumentException("assignment's assignable must equal this");
        }
        assignments.add(assignment);
    }
}
