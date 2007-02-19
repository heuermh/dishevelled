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
import java.util.Collections;

/**
 * An authority for a structured vocabulary domain, a
 * mapping between domains, and/or assignments to concepts.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.4.2.5 $ $Date: 2004/02/06 21:53:17 $
 */
public final class Authority
{
    /** Authority name. */
    private final String name;

    /** Set of domains. */
    private Set<Domain> domains;

    /** Set of mappings. */
    private Set<Mapping> mappings;

    /** Set of assignments. */
    private Set<Assignment> assignments;


    /**
     * Create a new authority with the specified name.
     *
     * @param name authority name, must not be null
     */
    public Authority(final String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("name must not be null");
        }
        this.name = name;
        domains = new HashSet<Domain>();
        mappings = new HashSet<Mapping>();
        assignments = new HashSet<Assignment>();
    }


    /**
     * Return the name of this authority.
     * The name will not be null.
     *
     * @return the name of this authority
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return an unmodifiable set of domains maintained by this
     * authority.  The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of domains maintained by this
     *    authority
     */
    public Set<Domain> getDomains()
    {
        return Collections.unmodifiableSet(domains);
    }

    /**
     * Return an unmodifiable set of mappings between domains
     * maintained by this authority.  The set will not be
     * null, but may be empty.
     *
     * @return an unmodifiable set of mappings between domains
     *    maintained by this authority
     */
    public Set<Mapping> getMappings()
    {
        return Collections.unmodifiableSet(mappings);
    }

    /**
     * Return an unmodifiable set of assignments maintained
     * by this authority.  The set will not be null,
     * but may be empty.
     *
     * @return an unmodifiable set of assignments maintained
     *    by this authority
     */
    public Set<Assignment> getAssignments()
    {
        return Collections.unmodifiableSet(assignments);
    }

    /**
     * Add the specified domain to this authority.
     *
     * @param domain domain to add, must not be null
     */
    void addDomain(final Domain domain)
    {
        if (domain == null)
        {
            throw new IllegalArgumentException("domain must not be null");
        }
        domains.add(domain);
    }

    /**
     * Add the specified mapping to this authority.
     *
     * @param mapping mapping to add, must not be null
     */
    void addMapping(final Mapping mapping)
    {
        if (mapping == null)
        {
            throw new IllegalArgumentException("mapping must not be null");
        }
        mappings.add(mapping);
    }

    /**
     * Add the specified assignment to this authority.
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

    /**
     * Create and return a new domain maintained by this authority with the specified name.
     * The specified name must be unique within this authority and may not be null.
     *
     * @param name domain name, must be unique within this authority and
     *    may not be null
     * @return a new domain maintained by this authority with the specified name
     */
    public Domain createDomain(final String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("name must not be null");
        }
        for (Domain d : domains)
        {
            if (name.equals(d.getName()))
            {
                throw new IllegalArgumentException("domain name must be unique: " + name);
            }
        }
        final Domain d = new Domain(this, name);
        addDomain(d);
        return d;
    }

    /**
     * Create and return a new mapping maintained by this authority between the specified domains.
     * The specified domains must not be null.
     *
     * @param source mapping source domain, must not be null
     * @param target mapping target domain, must not be null
     * @return a new mapping maintained by this authority between the specified domains
     */
    public Mapping createMapping(final Domain source,
                                 final Domain target)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target must not be null");
        }
        final Mapping m = new Mapping(this, source, target);
        addMapping(m);
        return m;
    }

    /**
     * Create and return a new assignment maintained by this authority of <code>concept</code>
     * to <code>assignable</code>, supported by <code>evidence</code>.
     * The specified concept and assignable must not be null.
     *
     * @param concept assignment source concept, must not be null
     * @param assignable assignment target assignable, must not be null
     * @param evidence set of evidence
     * @return a new assignment maintained by this authority of <code>concept</code>
     *    to <code>assignable</code>, supported by <code>evidence</code>
     */
    public Assignment createAssignment(final Concept concept,
                                       final Assignable assignable,
                                       final Set<Evidence> evidence)
    {
        if (concept == null)
        {
            throw new IllegalArgumentException("concept must not be null");
        }
        if (assignable == null)
        {
            throw new IllegalArgumentException("assignable must not be null");
        }
        final Set<Evidence> evidenceCopy = (evidence == null) ? Collections.<Evidence>emptySet() : evidence;
        final Assignment a = new Assignment(this,
                                            concept,
                                            assignable,
                                            evidenceCopy);
        addAssignment(a);
        return a;
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("authority ");
        sb.append(getName());
        sb.append(" (domains=");
        sb.append(domains.size());
        sb.append(" mappings=");
        sb.append(mappings.size());
        sb.append(" assignments=");
        sb.append(assignments.size());
        sb.append(")");
        return sb.toString();
    }
}
