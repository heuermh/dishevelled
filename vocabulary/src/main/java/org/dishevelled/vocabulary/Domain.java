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
 * A domain provides a named space in which to define concepts and
 * their relations to each other, and also to project concepts
 * in different domains to each other through a mapping across
 * domains.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3.2.4 $ $Date: 2004/02/06 00:23:13 $
 */
public final class Domain
{
    /** Domain name. */
    private final String name;

    /** Set of concepts. */
    private final Set<Concept> concepts;

    /** Set of relations. */
    private final Set<Relation> relations;

    /** Set of in mappings. */
    private final Set<Mapping> inMappings;

    /** Set of out mappings. */
    private final Set<Mapping> outMappings;

    /** Authority. */
    private final Authority authority;


    /**
     * Create a new domain with the specified
     * authority and name.  <code>authority</code> must
     * not be null.
     *
     * @param authority authority, must not be null
     * @param name domain name
     */
    Domain(final Authority authority, final String name)
    {
        if (authority == null)
        {
            throw new IllegalArgumentException("authority must not be null");
        }
        this.name = name;
        this.concepts = new HashSet<Concept>();
        this.relations = new HashSet<Relation>();
        this.inMappings = new HashSet<Mapping>();
        this.outMappings = new HashSet<Mapping>();
        this.authority = authority;
    }


    /**
     * Return the name of this domain.
     *
     * @return the name of this domain
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return an unmodifiable set of all concepts in this domain.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of all concepts in this domain
     */
    public Set<Concept> getConcepts()
    {
        return Collections.unmodifiableSet(concepts);
    }

    /**
     * Return an unmodifiable set of all relations in this domain.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of all relations in this domain
     */
    public Set<Relation> getRelations()
    {
        return Collections.unmodifiableSet(relations);
    }

    /**
     * Return an unmodifiable set of in mappings for this domain,
     * that is those mappings that have this domain as
     * the target.  The set will not be null, but may
     * be empty.
     *
     * @return an unmodifiable set of in mappings for this domain
     */
    public Set<Mapping> inMappings()
    {
        return Collections.unmodifiableSet(inMappings);
    }

    /**
     * Return an unmodifiable set of out mappings for this domain,
     * that is those mappings that have this domain as
     * the source.  The set will not be null, but may
     * be empty.
     *
     * @return an unmodifiable set of out mappings for this domain
     */
    public Set<Mapping> outMappings()
    {
        return Collections.unmodifiableSet(outMappings);
    }

    /**
     * Return an unmodifiable set of all mappings for this domain.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of all mappings for this domain
     */
    public Set<Mapping> getMappings()
    {
        final Set<Mapping> s = new HashSet<Mapping>(inMappings.size() + outMappings.size());
        s.addAll(inMappings);
        s.addAll(outMappings);
        return Collections.unmodifiableSet(s);
    }

    /**
     * Return the authority for this domain.
     * The authority will not be null.
     *
     * @return the authority for this domain
     */
    public Authority getAuthority()
    {
        return authority;
    }

    /**
     * Add the specified concept to this domain.
     *
     * @param concept concept to add, must not be null
     */
    void addConcept(final Concept concept)
    {
        if (concept == null)
        {
            throw new IllegalArgumentException("concept must not be null");
        }
        concepts.add(concept);
    }

    /**
     * Add the specified relation to this domain.
     *
     * @param relation relation to add, must not be null
     */
    void addRelation(final Relation relation)
    {
        if (relation == null)
        {
            throw new IllegalArgumentException("relation must not be null");
        }
        relations.add(relation);
    }

    /**
     * Add the specified in mapping to this domain.
     *
     * @param mapping in mapping to add, must not be null
     */
    void addInMapping(final Mapping mapping)
    {
        if (mapping == null)
        {
            throw new IllegalArgumentException("mapping must not be null");
        }
        inMappings.add(mapping);
    }

    /**
     * Add the specified out mapping to this domain.
     *
     * @param mapping out mapping to add, must not be null
     */
    void addOutMapping(final Mapping mapping)
    {
        if (mapping == null)
        {
            throw new IllegalArgumentException("mapping must not be null");
        }
        outMappings.add(mapping);
    }

    /**
     * Create and return a new concept in this domain with the specified
     * name, accession, and definition.
     * The specified accession must be unique within this domain
     * and may not be null.
     *
     * @param name concept name
     * @param accession concept accession, must be unique within this domain
     *    and may not be null
     * @param definition concept definition
     * @return a new concept in this domain with the specified
     *    name, accession, and definition
     */
    public Concept createConcept(final String name,
                                 final String accession,
                                 final String definition)
    {
        if (accession == null)
        {
            throw new IllegalArgumentException("accession must not be null");
        }
        for (Concept c : concepts)
        {
            if (accession.equals(c.getAccession()))
            {
                throw new IllegalArgumentException("accession must be unique");
            }
        }
        final Concept c = new Concept(this,
                                      name,
                                      accession,
                                      definition);
        addConcept(c);
        return c;
    }

    /**
     * Create and return a new relation in this domain between concepts
     * <code>source</code> and <code>target</code> with the
     * specified name.  Only one relation
     * may exist between the same source and target with the same
     * name within this domain.  The source and target concepts
     * must not be null.
     *
     * @param name relation name
     * @param source relation source concept, must not be null
     * @param target relation target concept, must not be null
     * @return a new relation in this domain between concepts <code>source</code>
     *    and <code>target</code> with the specified name
     */
    public Relation createRelation(final String name,
                                   final Concept source,
                                   final Concept target)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target must not be null");
        }
        for (Relation r : relations)
        {
            if ((name == null) && (r.getName() == null)
                && source.equals(r.getSource()) && (target.equals(r.getTarget())))
            {
                throw new IllegalArgumentException("combination of name, source, target must be unique");
            }

            if ((name != null) && name.equals(r.getName())
                && source.equals(r.getSource()) && target.equals(r.getTarget()))
            {
                throw new IllegalArgumentException("combination of name, source, target must be unique");
            }
        }
        final Relation r = new Relation(this,
                                        name,
                                        source,
                                        target);
        addRelation(r);
        return r;
    }
}
