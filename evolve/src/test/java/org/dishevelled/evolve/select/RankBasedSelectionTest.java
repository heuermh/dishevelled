/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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
package org.dishevelled.evolve.select;

import junit.framework.TestCase;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

import org.dishevelled.evolve.Selection;
import org.dishevelled.evolve.AbstractSelectionTest;

/**
 * Unit test for RankBasedSelection.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RankBasedSelectionTest
    extends AbstractSelectionTest
{

    /** @see AbstractSelectionTest */
    protected <T> Selection<T> createSelection()
    {
        return new RankBasedSelection<T>(1);
    }

    public void testRankBasedSelection()
    {
        RankBasedSelection<TestIndividual> selection = new RankBasedSelection<TestIndividual>(1);
        assertNotNull(selection);
        assertEquals(1, selection.getRank());
        selection.setRank(2);
        assertEquals(2, selection.getRank());

        WeightedMap<TestIndividual> parents = new HashWeightedMap<TestIndividual>();
        WeightedMap<TestIndividual> children = new HashWeightedMap<TestIndividual>();
        for (int i = 0; i < 10; i++)
        {
            parents.put(new TestIndividual(i), Double.valueOf((double) (i / 10.0d)));
            children.put(new TestIndividual(i), Double.valueOf((double) (i / 10.0d)));
        }

        WeightedMap<TestIndividual> result = selection.select(parents, children);

        assertNotNull(result);
        assertEquals(10, result.size());
    }
}
