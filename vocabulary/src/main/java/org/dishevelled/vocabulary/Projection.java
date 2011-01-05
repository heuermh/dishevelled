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
import java.util.HashSet;
import java.util.Collections;

/**
 * A projection is a binary directional link between a
 * source and a target concept from different domains
 * linked together through a mapping, supported by evidence.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2.2.3 $ $Date: 2004/02/06 00:23:13 $
 */
public final class Projection
{
    /** Projection name. */
    private final String name;

    /** Source concept. */
    private final Concept source;

    /** Source target. */
    private final Concept target;

    /** Mapping. */
    private final Mapping mapping;

    /** Set of evidence. */
    private final Set<Evidence> evidence;


    /**
     * Create a new projection in the specified mapping
     * between concepts <code>source</code> and
     * <code>target</code> with the specified name.  All of
     * <code>mapping</code>, <code>source</code>, and <code>target</code>
     * must not be null.
     *
     * @param mapping mapping, must not be null
     * @param name projection name
     * @param source source concept, must not be null
     * @param target target concept, must not be null
     * @param evidence set of evidence
     */
    Projection(final Mapping mapping,
               final String name,
               final Concept source,
               final Concept target,
               final Set<Evidence> evidence)
    {
        if (mapping == null)
        {
            throw new IllegalArgumentException("mapping must not be null");
        }
        if (source == null)
        {
            throw new IllegalArgumentException("source must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target must not be null");
        }
        this.name = name;
        this.source = source;
        this.target = target;
        this.mapping = mapping;
        if (evidence != null)
        {
            this.evidence = new HashSet<Evidence>(evidence);
        }
        else
        {
            this.evidence = new HashSet<Evidence>();
        }
        this.source.addOutProjection(this);
        this.target.addInProjection(this);
    }


    /**
     * Return the name of this projection.
     *
     * @return the name of this projection
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the source concept for this projection.
     * The source concept will not be null.
     *
     * @return the source concept for this projection
     */
    public Concept getSource()
    {
        return source;
    }

    /**
     * Return the target concept for this projection.
     * The target concept will not be null.
     *
     * @return the target concept for this projection
     */
    public Concept getTarget()
    {
        return target;
    }

    /**
     * Return the mapping for this projection.
     * The mapping will not be null.
     *
     * @return the mapping for this projection
     */
    public Mapping getMapping()
    {
        return mapping;
    }

    /**
     * Return an unmodifiable set of evidence supporting this projection.
     * The set will not be null but may be empty.
     *
     * @return an unmodifiable set of evidence supporting this projection
     */
    public Set<Evidence> getEvidence()
    {
        return Collections.unmodifiableSet(evidence);
    }
}
