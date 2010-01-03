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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.dishevelled.evolve.Selection;
import org.dishevelled.evolve.AbstractSelectionTest;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

/**
 * Unit test for RankBasedSelection.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RankBasedSelectionTest
    extends AbstractSelectionTest
{

    /** {@inheritDoc} */
    protected <T> Selection<T> createSelection()
    {
        return new RankBasedSelection<T>(1);
    }

    public void testConstructor()
    {
        Selection<String> selection0 = new RankBasedSelection<String>(1);
        Selection<String> selection1 = new RankBasedSelection<String>(99);
        Selection<String> selection2 = new RankBasedSelection<String>(Integer.MAX_VALUE);
        try
        {
            Selection<String> selection = new RankBasedSelection<String>(0);
            fail("ctr(0) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Selection<String> selection = new RankBasedSelection<String>(-1);
            fail("ctr(-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Selection<String> selection = new RankBasedSelection<String>(Integer.MIN_VALUE);
            fail("ctr(Integer.MIN_VALUE) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testRank()
    {
        RankBasedSelection<String> selection = new RankBasedSelection<String>(1);
        assertEquals(1, selection.getRank());
    }

    public void testOneIndividual()
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

    public void testTwoIndividuals()
    {
        Selection<String> selection = createSelection();
        Collection<String> population = new ArrayList<String>();
        WeightedMap<String> scores = new HashWeightedMap<String>();
        population.add("foo");
        population.add("bar");
        scores.put("foo", 0.0d);
        scores.put("bar", 1.0d);

        Collection<String> selected = selection.select(population, scores);
        assertNotNull(selected);
        assertEquals(2, selected.size());
        assertFalse(selected.contains("foo"));
        assertTrue(selected.contains("bar"));
    }

    public void testManyIndividualsRankOne()
    {
        Selection<String> selection = createSelection();
        Collection<String> population = new ArrayList<String>();
        WeightedMap<String> scores = new HashWeightedMap<String>();
        for (int i = 0; i < 100; i++)
        {
            String individual = "individual" + i;
            population.add(individual);
            scores.put(individual, 1.0d - (0.001d * i));
        }

        Collection<String> selected = selection.select(population, scores);
        assertNotNull(selected);
        assertEquals(population.size(), selected.size());
        for (String individual : selected)
        {
            assertEquals("individual0", individual);
        }
    }

    public void testManyIndividualsRankTwo()
    {
        Selection<String> selection = new RankBasedSelection<String>(2);
        Collection<String> population = new ArrayList<String>();
        WeightedMap<String> scores = new HashWeightedMap<String>();
        for (int i = 0; i < 100; i++)
        {
            String individual = "individual" + i;
            population.add(individual);
            scores.put(individual, 1.0d - (0.001d * i));
        }

        Collection<String> selected = selection.select(population, scores);
        assertNotNull(selected);
        assertEquals(population.size(), selected.size());
        for (String individual : selected)
        {
            assertTrue("individual0".equals(individual) || "individual1".equals(individual));
        }
    }

    public void testManyIndividualsRankGreaterThanPopulationSize()
    {
        Selection<String> selection = new RankBasedSelection<String>(999);
        Collection<String> population = new ArrayList<String>();
        WeightedMap<String> scores = new HashWeightedMap<String>();
        for (int i = 0; i < 100; i++)
        {
            String individual = "individual" + i;
            population.add(individual);
            scores.put(individual, 1.0d - (0.001d * i));
        }

        Collection<String> selected = selection.select(population, scores);
        assertNotNull(selected);
        assertEquals(population.size(), selected.size());
    }
}
