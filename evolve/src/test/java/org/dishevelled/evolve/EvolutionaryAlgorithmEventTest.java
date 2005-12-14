/*

    dsh-evolve  Simple framework for evolutionary algorithms.
    Copyright (c) 2005 held jointly by the individual authors.

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
package org.dishevelled.evolve;

import java.util.Set;
import java.util.Collections;

import junit.framework.TestCase;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

/**
 * Unit test for EvolutionaryAlgorithmEvent.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EvolutionaryAlgorithmEventTest
    extends TestCase
{

    public void testEvolutionaryAlgorithmEvent()
    {
        EvolutionaryAlgorithm<Integer> ea = new EvolutionaryAlgorithm<Integer>();
        EvolutionaryAlgorithmEvent<Integer> event;        

        // exit events
        int time = 0;
        WeightedMap<Integer> population = new HashWeightedMap<Integer>();

        event = new EvolutionaryAlgorithmEvent<Integer>(ea, population, time);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(population, event.getPopulation());
        assertEquals(time, event.getTime());

        // fitness calculated events
        Integer individual = Integer.valueOf(0);
        Double score = Double.valueOf(0.0d);

        event = new EvolutionaryAlgorithmEvent<Integer>(ea, individual, score);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(individual, event.getIndividual());
        assertEquals(score, event.getScore());

        // recombined events
        Set<Integer> parents = Collections.singleton(individual);
        Set<Integer> recombined = Collections.singleton(individual);

        event = new EvolutionaryAlgorithmEvent<Integer>(ea, parents, recombined, null);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(parents, event.getParents());
        assertEquals(recombined, event.getRecombined());
        assertEquals(null, event.getMutated());

        // mutated events
        Set<Integer> mutated = Collections.singleton(individual);

        event = new EvolutionaryAlgorithmEvent<Integer>(ea, null, recombined, mutated);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(null, event.getParents());
        assertEquals(recombined, event.getRecombined());
        assertEquals(mutated, event.getMutated());

        // selected events
        WeightedMap<Integer> parentPopulation = new HashWeightedMap<Integer>();
        WeightedMap<Integer> childPopulation = new HashWeightedMap<Integer>();

        event = new EvolutionaryAlgorithmEvent<Integer>(ea, parentPopulation, childPopulation, population);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(parentPopulation, event.getParentPopulation());
        assertEquals(childPopulation, event.getChildPopulation());
        assertEquals(population, event.getPopulation());
    }
}