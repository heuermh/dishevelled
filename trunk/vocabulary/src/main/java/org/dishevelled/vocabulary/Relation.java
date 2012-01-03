/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2012 held jointly by the individual authors.

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

/**
 * A relation is a binary directional link between
 * a source and a target concept present in the same domain.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2003/12/02 03:41:15 $
 */
public final class Relation
{
    /** Relation name. */
    private final String name;

    /** Source concept. */
    private final Concept source;

    /** Target concept. */
    private final Concept target;

    /** Domain. */
    private final Domain domain;


    /**
     * Create a new relation in the specified domain
     * between concepts <code>source</code> and
     * <code>target</code> with the specified name.  All of
     * <code>domain</code>, <code>source</code>, and <code>target</code>
     * must not be null.
     *
     * @param domain domain, must not be null
     * @param name relation name
     * @param source source concept, must not be null
     * @param target target concept, must not be null
     */
    Relation(final Domain domain,
             final String name,
             final Concept source,
             final Concept target)
    {

        if (domain == null)
        {
            throw new IllegalArgumentException("domain must not be null");
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
        this.domain = domain;
        this.source.addOutRelation(this);
        this.target.addInRelation(this);
    }


    /**
     * Return the name of this relation.
     *
     * @return the name of this relation
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the source concept for this relation.
     * The source concept will not be null.
     *
     * @return the source concept for this relation
     */
    public Concept getSource()
    {
        return source;
    }

    /**
     * Return the target concept for this relation.
     * The target concept will not be null.
     *
     * @return the target concept for this relation
     */
    public Concept getTarget()
    {
        return target;
    }

    /**
     * Return the domain for this relation.
     * The domain will not be null.
     *
     * @return the domain for this relation
     */
    public Domain getDomain()
    {
        return domain;
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("relation (name=");
        sb.append(name);
        sb.append(" source=");
        sb.append(source.toString());
        sb.append(" target=");
        sb.append(target.toString());
        sb.append(")");
        return sb.toString();
    }
}
