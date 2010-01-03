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
import java.util.Collections;

/**
 * A concept is a vocabulary word defined in a domain that has
 * relations to other concepts present in the same domain,
 * and has projections to other concepts in different domains
 * through a mapping across domains.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2.2.3 $ $Date: 2004/02/06 00:23:13 $
 */
public final class Concept
{
    /** Concept name. */
    private final String name;

    /** Concept accession. */
    private final String accession;

    /** Concept definition. */
    private final String definition;

    /** Set of in relations. */
    private final Set<Relation> inRelations;

    /** Set of out relations. */
    private final Set<Relation> outRelations;

    /** Set of in projections. */
    private final Set<Projection> inProjections;

    /** Set of out projections. */
    private final Set<Projection> outProjections;

    /** Set of assignments. */
    private final Set<Assignment> assignments;

    /** Domain. */
    private final Domain domain;


    /**
     * Create a new concept with the specified name, accession, and definition
     * in the specified domain.  <code>domain</code> and <code>accession</code>
     * must not be null.
     *
     * @param domain domain, must not be null
     * @param name concept name
     * @param accession concept accession, must not be null
     * @param definition concept definition
     */
    Concept(final Domain domain,
            final String name,
            final String accession,
            final String definition)
    {
        if (domain == null)
        {
            throw new IllegalArgumentException("domain must not be null");
        }
        if (accession == null)
        {
            throw new IllegalArgumentException("accession must not be null");
        }
        this.name = name;
        this.accession = accession;
        this.definition = definition;
        this.inRelations = new HashSet<Relation>();
        this.outRelations = new HashSet<Relation>();
        this.inProjections = new HashSet<Projection>();
        this.outProjections = new HashSet<Projection>();
        this.assignments = new HashSet<Assignment>();
        this.domain = domain;
    }


    /**
     * Return the name of this concept.
     *
     * @return the name of this concept
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the accession (an unique identifier) for this concept.
     * The accession will not be null.
     *
     * @return the accession for this concept
     */
    public String getAccession()
    {
        return accession;
    }

    /**
     * Return the definition of this concept.
     *
     * @return the definition of this concept
     */
    public String getDefinition()
    {
        return definition;
    }

    /**
     * Return the degree of this concept, that is the total
     * number of relations, projections, and assignments
     * linked to this concept.  The degree will be >= 0.
     *
     * @return the degree of this concept
     */
    public int degree()
    {
        return inRelations.size() + outRelations.size()
            + inProjections.size() + outProjections.size()
            + assignments.size();
    }

    /**
     * Return an unmodifiable set of in relations for this concept,
     * that is those relations that have this concept as
     * the target.  The set will not be null, but may
     * be empty.
     *
     * @return an unmodifiable set of in relations for this concept
     */
    public Set<Relation> inRelations()
    {
        return Collections.unmodifiableSet(inRelations);
    }

    /**
     * Return an unmodifiable set of out relations for this concept,
     * that is those relations that have this concept as
     * the source.  The set will not be null, but may be
     * empty.
     *
     * @return an unmodifiable set of out relations for this concept
     */
    public Set<Relation> outRelations()
    {
        return Collections.unmodifiableSet(outRelations);
    }

    /**
     * Return an unmodifiable set of all relations for this concept.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of all relations for this concept
     */
    public Set<Relation> getRelations()
    {
        final Set<Relation> s = new HashSet<Relation>(inRelations.size() + outRelations.size());
        s.addAll(inRelations);
        s.addAll(outRelations);
        return Collections.unmodifiableSet(s);
    }

    /**
     * Return an unmodifiable set of in projections for this concept,
     * that is those projections that have this concept as
     * the target.  The set will not be null, but may
     * be empty.
     *
     * @return an unmodifiable set of in projections for this concept
     */
    public Set<Projection> inProjections()
    {
        return Collections.unmodifiableSet(inProjections);
    }

    /**
     * Return an unmodifiable set of out projections for this concept,
     * that is those projections that have this concept as
     * the source.  The set will not be null, but may
     * be empty.
     *
     * @return an unmodifiable set of out projections for this concept
     */
    public Set<Projection> outProjections()
    {
        return Collections.unmodifiableSet(outProjections);
    }

    /**
     * Return an unmodifiable set of all projections for this concept.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of all projections for this concept
     */
    public Set<Projection> getProjections()
    {
        final Set<Projection> s = new HashSet<Projection>(inProjections.size() + outProjections.size());
        s.addAll(inProjections);
        s.addAll(outProjections);
        return Collections.unmodifiableSet(s);
    }

    /**
     * Return an unmodifiable set of assignments for this concept.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of assignments for this concept
     */
    public Set<Assignment> getAssignments()
    {
        return Collections.unmodifiableSet(assignments);
    }

    /**
     * Return the domain for this concept.
     * The domain will not be null.
     *
     * @return the domain for this concept
     */
    public Domain getDomain()
    {
        return domain;
    }

    /**
     * Add the specified in relation to this concept.
     *
     * @param relation in relation to add, must not be null
     */
    void addInRelation(final Relation relation)
    {
        if (relation == null)
        {
            throw new IllegalArgumentException("relation must not be null");
        }
        inRelations.add(relation);
    }

    /**
     * Add the specified out relation to this concept.
     *
     * @param relation out relation to add, must not be null
     */
    void addOutRelation(final Relation relation)
    {
        if (relation == null)
        {
            throw new IllegalArgumentException("relation must not be null");
        }
        outRelations.add(relation);
    }

    /**
     * Add the specified in projection to this concept.
     *
     * @param projection in projection to add, must not be null
     */
    void addInProjection(final Projection projection)
    {
        if (projection == null)
        {
            throw new IllegalArgumentException("projection must not be null");
        }
        inProjections.add(projection);
    }

    /**
     * Add the specified out projection to this concept.
     *
     * @param projection out projection to add, must not be null
     */
    void addOutProjection(final Projection projection)
    {
        if (projection == null)
        {
            throw new IllegalArgumentException("projection must not be null");
        }
        outProjections.add(projection);
    }

    /**
     * Add the specified assignment to this concept.
     *
     * @param assignment assignment to add, must not be null
     */
    void addAssignment(final Assignment assignment)
    {
        if (assignment == null)
        {
            throw new IllegalArgumentException("assignment must not be null");
        }
        assignments.add(assignment);
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("concept (name=");
        sb.append(name);
        sb.append(" accession=");
        sb.append(accession);
        sb.append(" definition=");
        sb.append(definition);
        sb.append(" degree=");
        sb.append(degree());
        sb.append(")");
        return sb.toString();
    }
}
