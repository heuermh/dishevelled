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
package org.dishevelled.evolve;

import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

/**
 * Abstract unit test for EvolutionaryAlgorithmEvent.
 *
 * @author  Michael Heuer
 * @version $Revision: 320 $ $Date: 2007-10-23 14:06:38 -0500 (Tue, 23 Oct 2007) $
 */
public abstract class AbstractEvolutionaryAlgorithmEventTest
    extends TestCase
{

    protected abstract <T> EvolutionaryAlgorithm<T> createEvolutionaryAlgorithm();

    public void testEvolutionaryAlgorithmEvent()
    {
        EvolutionaryAlgorithm<String> ea = createEvolutionaryAlgorithm();
        EvolutionaryAlgorithmEvent<String> event;        

        // exit events
        int time = 0;
        String individual = "foo";
        Double score = Double.valueOf(0.0d);
        Collection<String> population = Collections.singleton(individual);
        WeightedMap<String> scores = new HashWeightedMap<String>();
        scores.put(individual, score);

        event = new EvolutionaryAlgorithmEvent<String>(ea, population, scores, time);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(population, event.getPopulation());
        assertEquals(scores, event.getScores());
        assertEquals(time, event.getTime());

        // fitness calculated events
        event = new EvolutionaryAlgorithmEvent<String>(ea, individual, score);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(individual, event.getIndividual());
        assertEquals(score, event.getScore());

        // recombined events
        Collection<String> recombined = Collections.emptyList();

        event = new EvolutionaryAlgorithmEvent<String>(ea, population, recombined, (Collection<String>) null);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(population, event.getPopulation());
        assertEquals(recombined, event.getRecombined());
        assertEquals(null, event.getMutated());

        // mutated events
        Collection<String> mutated = Collections.emptyList();

        event = new EvolutionaryAlgorithmEvent<String>(ea, null, recombined, mutated);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(null, event.getPopulation());
        assertEquals(recombined, event.getRecombined());
        assertEquals(mutated, event.getMutated());

        // selected events
        Collection<String> selected = Collections.emptyList();

        event = new EvolutionaryAlgorithmEvent<String>(ea, population, selected, scores);

        assertNotNull(event);
        assertEquals(ea, event.getSource());
        assertEquals(ea, event.getEvolutionaryAlgorithm());
        assertEquals(population, event.getPopulation());
        assertEquals(selected, event.getSelected());
        assertEquals(scores, event.getScores());
    }
}
