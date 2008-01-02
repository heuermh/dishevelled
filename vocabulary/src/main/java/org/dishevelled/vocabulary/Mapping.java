/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2008 held jointly by the individual authors.

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
 * A mapping is a binary directional link between
 * a source and a target domain containing
 * projections of concepts from the source domain
 * to concepts in the target domain.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3.2.5 $ $Date: 2004/02/06 21:53:17 $
 */
public final class Mapping
{
    /** Source domain. */
    private final Domain source;

    /** Target domain. */
    private final Domain target;

    /** Set of projections. */
    private final Set<Projection> projections;

    /** Authority. */
    private final Authority authority;


    /**
     * Create a new mapping with the specified authority,
     * source domain, and target domain.  All of <code>authority</code>,
     * <code>source</code>, and <code>target</code> must not be null.
     *
     * @param authority authority, must not be null
     * @param source source domain, must not be null
     * @param target target domain, must not be null
     */
    Mapping(final Authority authority,
            final Domain source,
            final Domain target)
    {
        if (authority == null)
        {
            throw new IllegalArgumentException("authority must not be null");
        }
        if (source == null)
        {
            throw new IllegalArgumentException("source must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target must not be null");
        }
        this.source = source;
        this.target = target;
        this.projections = new HashSet<Projection>();
        this.authority = authority;
        source.addOutMapping(this);
        target.addInMapping(this);
    }


    /**
     * Return the source domain for this mapping.  The source
     * domain will not be null.
     *
     * @return the source domain for this mapping
     */
    public Domain getSource()
    {
        return source;
    }

    /**
     * Return the target domain for this mapping.  The target
     * domain will not be null.
     *
     * @return the target domain for this mapping
     */
    public Domain getTarget()
    {
        return target;
    }

    /**
     * Return an unmodifiable set of all projections in this mapping.
     * The set will not be null, but may be empty.
     *
     * @return an unmodifiable set of all projections in this mapping
     */
    public Set<Projection> getProjections()
    {
        return Collections.unmodifiableSet(projections);
    }

    /**
     * Return the authority for this mapping.  The authority
     * will not be null.
     *
     * @return the authority for this mapping
     */
    public Authority getAuthority()
    {
        return authority;
    }

    /**
     * Add the specified projection to this mapping.
     *
     * @param projection projection to add, must not be null
     */
    void addProjection(final Projection projection)
    {
        if (projection == null)
        {
            throw new IllegalArgumentException("projection must not be null");
        }
        projections.add(projection);
    }

    /**
     * Create and return a new projection in this mapping between
     * concepts <code>source</code> and <code>target</code>
     * with the specified name.  The
     * source concept must be within the source domain of
     * this mapping, and the target domain must be within
     * the target domain of this mapping.  Only one projection
     * may exist between the same source and target with
     * the same name within this mapping.  The source and
     * target concepts must not be null.
     *
     * @param name projection name
     * @param source projection source concept, must not be null
     * @param target projection target concept, must not be null
     * @param evidence set of evidence
     * @return a new projection in this mapping between concepts
     *    <code>source</code> and <code>target</code> with the specified
     *    name
     */
    public Projection createProjection(final String name,
                                       final Concept source,
                                       final Concept target,
                                       final Set<Evidence> evidence)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target must not be null");
        }
        if (!(source.getDomain().equals(this.source)))
        {
            throw new IllegalArgumentException("source concept not in source domain");
        }
        if (!(target.getDomain().equals(this.target)))
        {
            throw new IllegalArgumentException("target concept not in target domain");
        }
        for (Projection p : projections)
        {
            if ((name == null) && (p.getName() == null)
                && source.equals(p.getSource()) && target.equals(p.getTarget()))
            {
                throw new IllegalArgumentException("combination of name, source, target must be unique");
            }
            if ((name != null) && name.equals(p.getName())
                && source.equals(p.getSource()) && target.equals(p.getTarget()))
            {
                throw new IllegalArgumentException("combination of name, source, target must be unique");
            }
        }
        final Set<Evidence> evidenceCopy = (evidence == null) ? Collections.<Evidence>emptySet() : evidence;
        final Projection p = new Projection(this,
                                            name,
                                            source,
                                            target,
                                            evidenceCopy);
        addProjection(p);
        return p;
    }
}
