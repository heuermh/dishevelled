/*

    dsh-evolve-examples  Examples for the evolve library.
    Copyright (c) 2007 held jointly by the individual authors.

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
package org.dishevelled.evolve.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.dishevelled.evolve.EvolutionaryAlgorithm;
import org.dishevelled.evolve.EvolutionaryAlgorithmAdapter;
import org.dishevelled.evolve.EvolutionaryAlgorithmEvent;
import org.dishevelled.evolve.EvolutionaryAlgorithmListener;
import org.dishevelled.evolve.ExitStrategy;
import org.dishevelled.evolve.Fitness;
import org.dishevelled.evolve.Recombination;
import org.dishevelled.evolve.Selection;

import org.dishevelled.evolve.exit.FitnessThresholdExitStrategy;

import org.dishevelled.evolve.impl.EvolutionaryAlgorithmImpl;

import org.dishevelled.evolve.mutate.IndividualWiseMutation;
import org.dishevelled.evolve.mutate.NullIndividualWiseMutation;
import org.dishevelled.evolve.mutate.ProportionalMutation;

import org.dishevelled.evolve.recombine.NullRecombination;

import org.dishevelled.evolve.select.FitnessProportionalSelection;

/**
 * Guess the number evolve example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GuessTheNumber
    implements Runnable
{
    /** Number to guess. */
    private int number;

    /** Number of individuals. */
    private int individuals;

    /** Maximum number to guess. */
    private int maxNumber;

    /** Source of randomness. */
    private final Random random;

    private final ExitStrategy<Integer> exitStrategy;

    /** Null recombination. */
    private final Recombination<Integer> recombination;

    /** Mutation. */
    private final ProportionalMutation<Integer> mutation;

    /** Fitness. */
    private final Fitness<Integer> fitness;

    /** Selection. */
    private final Selection<Integer> selection;

    private final boolean verbose = true;

    /**
     * Create a new guess the number example.
     */
    public GuessTheNumber()
    {
        random = new Random();
        maxNumber = 1000;
        number = random.nextInt(maxNumber);
        individuals = 10;

        exitStrategy = new FitnessThresholdExitStrategy<Integer>(0.99d);

        recombination = new NullRecombination<Integer>();

        mutation = new ProportionalMutation<Integer>();
        mutation.add(new NullIndividualWiseMutation<Integer>(), 0.8d);
        mutation.add(new IndividualWiseMutation<Integer>()
                     {
                         /** {@inheritDoc} */
                         public Integer mutate(final Integer i)
                         {
                             int j = i.intValue();
                             j += random.nextInt((int) (maxNumber / 4.0d));
                             j -= random.nextInt((int) (maxNumber / 4.0d));
                             j = Math.max(0, j);
                             j = Math.min(maxNumber, j);
                             return Integer.valueOf(j);
                         }
                     }, 0.2d);

        fitness = new Fitness<Integer>()
            {
                /** {@inheritDoc} */
                public Double score(final Integer i)
                {
                    return (1.0d / (Math.abs(number - i) + 1.0d));
                }
            };

        selection = new FitnessProportionalSelection<Integer>();
    }

    public void setNumber(final int number)
    {
        this.number = number;
    }

    public int getNumber()
    {
        return number;
    }

    public void setIndividuals(final int individuals)
    {
        this.individuals = individuals;
    }

    public int getIndividuals()
    {
        return individuals;
    }

    public void run()
    {
        EvolutionaryAlgorithm<Integer> ea = new EvolutionaryAlgorithmImpl<Integer>();
        EvolutionaryAlgorithmListener<Integer> logger = new LoggingEvolutionaryAlgorithmListener<Integer>();
        if (verbose)
        {
            ea.addEvolutionaryAlgorithmListener(logger);
        }
        try
        {
            Collection<Integer> population = new ArrayList<Integer>(individuals);
            for (int i = 0; i < individuals; i++)
            {
                population.add(random.nextInt(maxNumber));
            }
            Collection<Integer> evolved = ea.evolve(population, exitStrategy, recombination, mutation, fitness, selection);
            System.out.println("number to guess=" + number);
            System.out.println("done.\nevolved=" + evolved);
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        finally
        {
            if (verbose)
            {
                ea.removeEvolutionaryAlgorithmListener(logger);
            }
        }
    }

    private class LoggingEvolutionaryAlgorithmListener<I>
        extends EvolutionaryAlgorithmAdapter<I>
    {
        public void exitFailed(final EvolutionaryAlgorithmEvent<I> event)
        {
            int time = event.getTime();
            List population = new ArrayList(event.getPopulation());
            Collections.sort(population);
            System.out.println("time=" + time + " population=" + population);
        }
    }

    public static void main(final String[] args)
    {
        new GuessTheNumber().run();
    }
}