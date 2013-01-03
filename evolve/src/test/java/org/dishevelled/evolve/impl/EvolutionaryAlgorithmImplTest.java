/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2013 held jointly by the individual authors.

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
package org.dishevelled.evolve.impl;

import java.util.Collection;
import java.util.Collections;

import org.dishevelled.evolve.AbstractEvolutionaryAlgorithmTest;
import org.dishevelled.evolve.EvolutionaryAlgorithm;
import org.dishevelled.evolve.EvolutionaryAlgorithmAdapter;
import org.dishevelled.evolve.EvolutionaryAlgorithmListener;
import org.dishevelled.evolve.ExitStrategy;
import org.dishevelled.evolve.Fitness;
import org.dishevelled.evolve.Mutation;
import org.dishevelled.evolve.Recombination;
import org.dishevelled.evolve.Selection;

import org.dishevelled.evolve.exit.TimeLimitExitStrategy;

import org.dishevelled.evolve.fitness.UniformFitness;

import org.dishevelled.evolve.mutate.NullMutation;

import org.dishevelled.evolve.recombine.NullRecombination;

import org.dishevelled.evolve.select.NullSelection;

/**
 * Unit test for EvolutionaryAlgorithmImpl.
 *
 * @author  Michael Heuer
 * @version $Revision: 320 $ $Date: 2007-10-23 14:06:38 -0500 (Tue, 23 Oct 2007) $
 */
public final class EvolutionaryAlgorithmImplTest
    extends AbstractEvolutionaryAlgorithmTest
{

    /** {@inheritDoc} */
    protected <I> EvolutionaryAlgorithm<I> createEvolutionaryAlgorithm()
    {
        return new EvolutionaryAlgorithmImpl<I>();
    }

    public void testEvolveParameters()
    {
        EvolutionaryAlgorithm<String> ea = createEvolutionaryAlgorithm();
        Collection<String> empty = Collections.emptyList();
        Collection<String> individuals = Collections.singleton("foo");
        ExitStrategy<String> exitStrategy = new TimeLimitExitStrategy<String>(1);
        Recombination<String> recombination = new NullRecombination<String>();
        Mutation<String> mutation = new NullMutation<String>();
        Fitness<String> fitness = new UniformFitness<String>();
        Selection<String> selection = new NullSelection<String>();

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
            ea.evolve(empty, exitStrategy, recombination, mutation, fitness, selection);
            fail("evolve(empty,,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testOneIndividualNoEvolution()
    {
        EvolutionaryAlgorithm<String> ea = createEvolutionaryAlgorithm();
        Collection<String> individuals = Collections.singleton("foo");
        ExitStrategy<String> exitStrategy = new TimeLimitExitStrategy<String>(1);
        Recombination<String> recombination = new NullRecombination<String>();
        Mutation<String> mutation = new NullMutation<String>();
        Fitness<String> fitness = new UniformFitness<String>();
        Selection<String> selection = new NullSelection<String>();
        EvolutionaryAlgorithmListener<String> listener = new EvolutionaryAlgorithmAdapter<String>();

        ea.addEvolutionaryAlgorithmListener(listener);
        Collection<String> evolved = ea.evolve(individuals, exitStrategy, recombination, mutation, fitness, selection);
        ea.removeEvolutionaryAlgorithmListener(listener);

        assertNotNull(evolved);
        assertEquals(1, evolved.size());
        assertTrue(evolved.contains("foo"));
    }
}
