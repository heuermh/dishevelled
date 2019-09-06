/*

    dsh-venn-euler  Lightweight components for venn/euler diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.euler;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.dishevelled.venn.VennModel;

/**
 * Fake venn model.
 */
class FakeVennModel implements VennModel<Integer>
{
    private final Set<Integer>[] sets;

    public FakeVennModel(final Set<Integer> ... sets)
    {
        this.sets = sets;
    }

    public FakeVennModel()
    {
        this.sets = new Set[]{};
    }

    public FakeVennModel(final int[] ... arraySets)
    {
        this.sets = new Set[arraySets.length];
        for (int i = 0; i < arraySets.length; i++)
        {
            this.sets[i] = makeSet(arraySets[i]);
	}
    }

    private Set<Integer> makeSet(final int[] s)
    {
        Set<Integer> set =  new HashSet<Integer>();
        for (int i : s)
        {
            set.add(i);
        }
        return set;
    }

    public int size()
    {
        return sets.length;
    }

    public Set<Integer> get(int index)
    {
        return sets[index]; 
    }

    public Set<Integer> union()
    {
        return Collections.emptySet();
    }

    public Set<Integer> intersection()
    {
        return Collections.emptySet();
    }

    public Set<Integer> exclusiveTo(final int index, final int... additional)
    {
        return Collections.emptySet();
    }
}
