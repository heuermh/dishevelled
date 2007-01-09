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
package org.dishevelled.evolve;

import java.util.Set;
import java.util.Collections;

import junit.framework.TestCase;

import org.dishevelled.weighted.WeightedMap;

import org.dishevelled.evolve.exit.TimeLimitExitStrategy;

import org.dishevelled.evolve.fitness.UniformFitness;

import org.dishevelled.evolve.mutate.NullMutation;

import org.dishevelled.evolve.recombine.NullRecombination;

import org.dishevelled.evolve.select.NullSelection;

/**
 * Unit test for EvolutionaryAlgorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EvolutionaryAlgorithmTest
    extends TestCase
{

    public void testEvolutionaryAlgorithm()
    {
        EvolutionaryAlgorithm<Integer> ea = new EvolutionaryAlgorithm<Integer>();

        assertNotNull(ea);

        EvolutionaryAlgorithmListener<Integer> listener0 = new EvolutionaryAlgorithmAdapter<Integer>();
        EvolutionaryAlgorithmListener<Integer> listener1 = new EvolutionaryAlgorithmAdapter<Integer>();

        assertNotNull(listener0);
        assertNotNull(listener1);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(0, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(0, ea.getEvolutionaryAlgorithmListenerCount());

        ea.addEvolutionaryAlgorithmListener(listener0);
        ea.addEvolutionaryAlgorithmListener(listener1);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(2, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(2, ea.getEvolutionaryAlgorithmListenerCount());
        assertEquals(listener1, ea.getEvolutionaryAlgorithmListeners()[0]);
        assertEquals(listener0, ea.getEvolutionaryAlgorithmListeners()[1]);

        ea.removeEvolutionaryAlgorithmListener(listener1);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(1, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(1, ea.getEvolutionaryAlgorithmListenerCount());
        assertEquals(listener0, ea.getEvolutionaryAlgorithmListeners()[0]);

        ea.removeEvolutionaryAlgorithmListener(listener0);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(0, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(0, ea.getEvolutionaryAlgorithmListenerCount());

        ea.removeEvolutionaryAlgorithmListener(listener0);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(0, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(0, ea.getEvolutionaryAlgorithmListenerCount());

        Set<Integer> emptySet = Collections.<Integer>emptySet();
        Set<Integer> individuals = Collections.<Integer>singleton(Integer.valueOf(0));
        ExitStrategy<Integer> exitStrategy = new TimeLimitExitStrategy<Integer>(1);
        Recombination<Integer> recombination = new NullRecombination<Integer>();
        Mutation<Integer> mutation = new NullMutation<Integer>();
        Fitness<Integer> fitness = new UniformFitness<Integer>();
        Selection<Integer> selection = new NullSelection<Integer>();

        ea.addEvolutionaryAlgorithmListener(listener0);
        ea.addEvolutionaryAlgorithmListener(listener1);

        WeightedMap<Integer> result = ea.evolve(individuals, exitStrategy, recombination, mutation, fitness, selection);

        assertNotNull(result);

        try
        {
            ea.evolve(null, exitStrategy, recombination, mutation, fitness, selection);
            fail("evolve(null,,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ea.evolve(individuals, null, recombination, mutation, fitness, selection);
            fail("evolve(,null,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ea.evolve(individuals, exitStrategy, null, mutation, fitness, selection);
            fail("evolve(,,null,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ea.evolve(individuals, exitStrategy, recombination, null, fitness, selection);
            fail("evolve(,,,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ea.evolve(individuals, exitStrategy, recombination, mutation, null, selection);
            fail("evolve(,,,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ea.evolve(individuals, exitStrategy, recombination, mutation, fitness, null);
            fail("evolve(,,,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            ea.evolve(emptySet, exitStrategy, recombination, mutation, fitness, selection);
            fail("evolve(emptySet,,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }        
    }
}
