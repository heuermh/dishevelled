/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2008 held jointly by the individual authors.

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
package org.dishevelled.cluster.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dishevelled.cluster.Cluster;

/**
 * Cluster implementation.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class ClusterImpl<E>
    implements Cluster<E>
{
    /** Members. */
    private final List<E> members;

    /** Exemplar. */
    private final E exemplar;


    /**
     * Create a new cluster implementation with the specified list of members
     * and no exemplar.
     *
     * @param members list of members, must not be null
     */
    ClusterImpl(final List<? extends E> members)
    {
        if (members == null)
        {
            throw new IllegalArgumentException("members must not be null");
        }
        this.members = Collections.unmodifiableList(new ArrayList<E>(members));
        this.exemplar = null;
    }

    /**
     * Create a new cluster implementation with the specified list of members
     * and specified exemplar.
     *
     * @param members list of members, must not be null
     * @param exemplar exemplar
     */
    ClusterImpl(final List<? extends E> members,
                final E exemplar)
    {
        if (members == null)
        {
            throw new IllegalArgumentException("members must not be null");
        }
        this.members = Collections.unmodifiableList(new ArrayList<E>(members));
        this.exemplar = exemplar;
    }


    /** {@inheritDoc} */
    public int size()
    {
        return members.size();
    }

    /** {@inheritDoc} */
    public boolean isSingleton()
    {
        return (size() == 1);
    }

    /** {@inheritDoc} */
    public E exemplar()
    {
        return exemplar;
    }

    /** {@inheritDoc} */
    public List<E> members()
    {
        return members;
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return members.iterator();
    }
}