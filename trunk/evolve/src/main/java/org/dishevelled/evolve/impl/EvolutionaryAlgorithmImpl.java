/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2009 held jointly by the individual authors.

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

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.dishevelled.evolve.EvolutionaryAlgorithm;
import org.dishevelled.evolve.EvolutionaryAlgorithmEvent;
import org.dishevelled.evolve.EvolutionaryAlgorithmListener;
import org.dishevelled.evolve.ExitStrategy;
import org.dishevelled.evolve.Fitness;
import org.dishevelled.evolve.Mutation;
import org.dishevelled.evolve.Recombination;
import org.dishevelled.evolve.Selection;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

/**
 * Implementation of an evolutionary algorithm function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision: 320 $ $Date: 2007-10-23 14:06:38 -0500 (Tue, 23 Oct 2007) $
 */
public final class EvolutionaryAlgorithmImpl<I>
    implements EvolutionaryAlgorithm<I>
{
    /** Default hash map load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /** Listener list. */
    private EventListenerList listenerList = new EventListenerList();


    /**
     * Create a new evolutionary algorithm function implementation.
     */
    public EvolutionaryAlgorithmImpl()
    {
        // empty
    }


    /** {@inheritDoc} */
    public Collection<I> evolve(final Collection<I> individuals,
                                final ExitStrategy<I> exitStrategy,
                                final Recombination<I> recombination,
                                final Mutation<I> mutation,
                                final Fitness<I> fitness,
                                final Selection<I> selection)
    {
        checkParameters(individuals, exitStrategy, recombination, mutation, fitness, selection);

        // initialize population with individuals
        Collection<I> population = new ArrayList<I>(individuals);
        // initialize fitness scores to 0.0d
        WeightedMap<I> scores = new HashWeightedMap<I>(population.size(), DEFAULT_LOAD_FACTOR);
        for (I i : individuals)
        {
            scores.put(i, Double.valueOf(0.0d));
        }

        int time = 0;
        while (!exitStrategy.evaluate(population, scores, time))
        {
            fireExitFailed(population, scores, time);

            // recombine population
            Collection<I> recombined = recombination.recombine(population);
            fireRecombined(population, recombined);

            // mutate amongst themselves
            Collection<I> mutated = mutation.mutate(recombined);
            fireMutated(recombined, mutated);

            // evalute fitness
            for (I i : mutated)
            {
                Double score = fitness.score(i);
                scores.put(i, score);
                fireFitnessCalculated(i, score);
            }

            // select individuals for next generation
            population = selection.select(mutated, scores);
            fireSelected(mutated, population, scores);

            time++;
        }

        // exit strategy condition(s) met, return successful population
        fireExitSucceeded(population, scores, time);
        return population;
    }


    /**
     * Check that the specified parameters are valid.
     *
     * @param individuals collection of individuals, must not be null and must contain
     *    at least one individual
     * @param exitStrategy exit strategy function, must not be null
     * @param recombination recombination function, must not be null
     * @param mutation mutation function, must not be null
     * @param fitness fitness function, must not be null
     * @param selection selection function, must not be null
     */
    private void checkParameters(final Collection<I> individuals,
                                 final ExitStrategy<I> exitStrategy,
                                 final Recombination<I> recombination,
                                 final Mutation<I> mutation,
                                 final Fitness<I> fitness,
                                 final Selection<I> selection)
    {
        // check for null or otherwise illegal parameters
        if (individuals == null)
        {
            throw new IllegalArgumentException("individuals must not be null");
        }
        if (individuals.size() == 0)
        {
            throw new IllegalArgumentException("individuals must not be empty");
        }
        if (exitStrategy == null)
        {
            throw new IllegalArgumentException("exitStrategy must not be null");
        }
        if (recombination == null)
        {
            throw new IllegalArgumentException("recombination must not be null");
        }
        if (mutation == null)
        {
            throw new IllegalArgumentException("mutation must not be null");
        }
        if (fitness == null)
        {
            throw new IllegalArgumentException("fitness must not be null");
        }
        if (selection == null)
        {
            throw new IllegalArgumentException("selection must not be null");
        }
    }

   /**
     * Add the specified evolutionary algorithm listener.
     *
     * @param l evolutionary algorithm listener to add
     */
    /** {@inheritDoc} */
    public void addEvolutionaryAlgorithmListener(final EvolutionaryAlgorithmListener<I> l)
    {
        listenerList.add(EvolutionaryAlgorithmListener.class, l);
    }

    /** {@inheritDoc} */
    public void removeEvolutionaryAlgorithmListener(final EvolutionaryAlgorithmListener<I> l)
    {
        listenerList.remove(EvolutionaryAlgorithmListener.class, l);
    }

    /** {@inheritDoc} */
    public int getEvolutionaryAlgorithmListenerCount()
    {
        return listenerList.getListenerCount(EvolutionaryAlgorithmListener.class);
    }

    /** {@inheritDoc} */
    public EvolutionaryAlgorithmListener<I>[] getEvolutionaryAlgorithmListeners()
    {
        return (EvolutionaryAlgorithmListener<I>[]) listenerList.getListeners(EvolutionaryAlgorithmListener.class);
    }

    /**
     * Fire an exit failed event to all registered listeners.
     *
     * @param population population
     * @param scores fitness scores
     * @param time time
     */
    private void fireExitFailed(final Collection<I> population, final WeightedMap<I> scores, final int time)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, population, scores, time);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).exitFailed(e);
            }
        }
    }

    /**
     * Fire an exit succeeded event to all registered listeners.
     *
     * @param population population
     * @param scores fitness scores
     * @param time time
     */
    private void fireExitSucceeded(final Collection<I> population, final WeightedMap<I> scores, final int time)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, population, scores, time);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).exitSucceeded(e);
            }
        }
    }

    /**
     * Fire a recombined event to all registered listeners.
     *
     * @param population population
     * @param recombined recombined
     */
    private void fireRecombined(final Collection<I> population, final Collection<I> recombined)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, population, recombined, (Collection<I>) null);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).recombined(e);
            }
        }
    }

    /**
     * Fire a mutated event to all registered listeners.
     *
     * @param recombined recombined
     * @param mutated mutated
     */
    private void fireMutated(final Collection<I> recombined, final Collection<I> mutated)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, null, recombined, mutated);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).mutated(e);
            }
        }
    }

    /**
     * Fire a fitness calculated event to all registered listeners.
     *
     * @param individual individual
     * @param score score
     */
    private void fireFitnessCalculated(final I individual, final Double score)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, individual, score);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).fitnessCalculated(e);
            }
        }
    }

    /**
     * Fire a selected event to all registered listeners.
     *
     * @param population population
     * @param selected selected
     * @param scores fitness scores
     */
    private void fireSelected(final Collection<I> population,
                              final Collection<I> selected,
                              final WeightedMap<I> scores)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, population, selected, scores);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).selected(e);
            }
        }
    }
}
