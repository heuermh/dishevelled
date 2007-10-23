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

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * An assignment is a binary directional link between
 * a concept and a so-called &quot;assignable&quot;
 * entity, supported by evidence.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2.2.3 $ $Date: 2004/02/06 00:23:13 $
 */
public final class Assignment
{
    /** Set of evidence. */
    private final Set<Evidence> evidence;

    /** Concept. */
    private final Concept concept;

    /** Assignable. */
    private final Assignable assignable;

    /** Authority. */
    private final Authority authority;


    /**
     * Create a new assignment maintained by the specified
     * authority of <code>concept</code> to <code>assignable</code>,
     * supported by <code>evidence</code>.  None of <code>authority</code>,
     * <code>concept</code>, or <code>assignable</code> may be null.
     *
     * @param authority authority, must not be null
     * @param concept assignment source concept, must not be null
     * @param assignable assignment target assignable, must not be null
     * @param evidence set of evidence
     */
    Assignment(final Authority authority,
               final Concept concept,
               final Assignable assignable,
               final Set<Evidence> evidence)
    {
        if (authority == null)
        {
            throw new IllegalArgumentException("authority must not be null");
        }
        if (concept == null)
        {
            throw new IllegalArgumentException("concept must not be null");
        }
        if (assignable == null)
        {
            throw new IllegalArgumentException("assignable must not be null");
        }
        this.concept = concept;
        this.authority = authority;
        this.assignable = assignable;
        if (evidence != null)
        {
            this.evidence = new HashSet<Evidence>(evidence);
        }
        else
        {
            this.evidence = new HashSet<Evidence>();
        }
        this.concept.addAssignment(this);
        this.assignable.addAssignment(this);
    }


    /**
     * Return an unmodifiable set of evidence supporting this assignment.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of evidence supporting this assignment
     */
    public Set<Evidence> getEvidence()
    {
        return Collections.unmodifiableSet(evidence);
    }

    /**
     * Return the concept for this assignment.
     * The concept will not be null.
     *
     * @return the concept for this assignment
     */
    public Concept getConcept()
    {
        return concept;
    }

    /**
     * Return the &quot;assignable&quot; entity for this assignment.
     * The assignable entity will not be null.
     *
     * @return the &quot;assignable&quot; entity for this assignment
     */
    public Assignable getAssignable()
    {
        return assignable;
    }

    /**
     * Return the authority for this assignment.
     * The authority will not be null.
     *
     * @return the authority for this assignment
     */
    public Authority getAuthority()
    {
        return authority;
    }

    /**
     * Add the specified evidence supporting this assignment.
     *
     * @param evidence evidence to add, must not be null
     */
    public void addEvidence(final Evidence evidence)
    {
        if (evidence == null)
        {
            throw new IllegalArgumentException("evidence must not be null");
        }
        this.evidence.add(evidence);
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("assignment (concept=");
        sb.append(concept.toString());
        sb.append(" assignable=");
        sb.append(assignable.toString());
        sb.append(" evidence=");
        sb.append(evidence.toString());
        sb.append(")");
        return sb.toString();
    }
}
