/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2010 held jointly by the individual authors.

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
package org.dishevelled.evolve.select;

import java.util.Collection;
import java.util.Collections;

import org.dishevelled.evolve.Selection;
import org.dishevelled.evolve.AbstractSelectionTest;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

/**
 * Unit test for NullSelection.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NullSelectionTest
    extends AbstractSelectionTest
{

    /** {@inheritDoc} */
    protected <T> Selection<T> createSelection()
    {
        return new NullSelection<T>();
    }

    public void testNullSelection()
    {
        Selection<String> selection = createSelection();
        Collection<String> population = Collections.singleton("foo");
        WeightedMap<String> scores = new HashWeightedMap<String>();
        scores.put("foo", 1.0d);

        Collection<String> selected = selection.select(population, scores);
        assertNotNull(selected);
        assertEquals(1, selected.size());
        assertTrue(selected.contains("foo"));
    }
}
