/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2015 held jointly by the individual authors.

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
package org.dishevelled.venn.model;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import org.dishevelled.venn.VennModel;

/**
 * Venn diagram model builder.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public final class VennModelBuilder<E>
{
    /** List of sets. */
    private final List<Set<E>> sets = Lists.newArrayList();


    /**
     * Create a new venn model builder.
     */
    public VennModelBuilder()
    {
        // empty
    }


    /**
     * Clear the list of sets for this venn model builder.
     *
     * @return this venn model builder
     */
    public VennModelBuilder<E> clear()
    {
        sets.clear();
        return this;
    }

    /**
     * Return this venn model builder configured with the specified set.  Multiple
     * calls to this method add to the list of sets for this venn model builder.
     *
     * @param set set for this venn model builder, must not be null
     * @return this venn model builder configured with the specified set
     */
    public VennModelBuilder<E> withSet(final Set<E> set)
    {
        if (set == null)
        {
            throw new IllegalArgumentException("set must not be null");
        }
        sets.add(set);
        return this;
    }

    /**
     * Return this venn model builder configured with the specified sets.  Multiple
     * calls to this method add to the list of sets for this venn model builder.
     *
     * @param sets variable number of sets for this venn model builder, must not be null
     * @return this venn model builder configured with the specified sets
     */
    public VennModelBuilder<E> withSets(final Set<E>... sets)
    {
        if (sets == null)
        {
            throw new IllegalArgumentException("sets must not be null");
        }
        for (Set<E> set : sets)
        {
            this.sets.add(set);
        }
        return this;
    }

    /**
     * Build and return a new venn model configured from the list of sets for this builder.
     *
     * @return a new venn model configured from the list of sets for this builder
     */
    public VennModel<E> build()
    {
        return VennModels.createVennModel(sets);
    }
}