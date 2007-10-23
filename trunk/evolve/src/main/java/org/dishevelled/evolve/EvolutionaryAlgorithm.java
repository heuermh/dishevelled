/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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

import java.util.Set;

import javax.swing.event.EventListenerList;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

/**
 * An evolutionary algorithm function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EvolutionaryAlgorithm<I>
{
    /** Default hash map load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /** Listener list. */
    private EventListenerList listenerList = new EventListenerList();


    /**
     * Create a new evolutionary algorithm function.
     */
    public EvolutionaryAlgorithm()
    {
        // empty
    }


    /**
     * Evolve the specified population of individuals, using all of the specified
     * exit strategy, recombination, mutation, fitness, and selection functions.
     *
     * @param individuals population of individuals, must not be null and must contain
     *    at least one individual
     * @param exitStrategy exit strategy function, must not be null
     * @param recombination recombination function, must not be null
     * @param mutation mutation function, must not be null
     * @param fitness fitness function, must not be null
     * @param selection selection function, must not be null
     *
     * @return evolved population of individuals that passes the conditions of the
     *    specified exit strategy, as a weighted map of individuals to fitness scores
     */
    public WeightedMap<I> evolve(final Set<I> individuals,
                                 final ExitStrategy<I> exitStrategy,
                                 final Recombination<I> recombination,
                                 final Mutation<I> mutation,
                                 final Fitness<I> fitness,
                                 final Selection<I> selection)
    {
        checkParameters(individuals, exitStrategy, recombination, mutation, fitness, selection);

        // initialize population with individuals
        WeightedMap<I> population = new HashWeightedMap<I>(individuals.size(), DEFAULT_LOAD_FACTOR);
        for (I i : individuals)
        {
            // start with fitness score of 0.0d
            population.put(i, Double.valueOf(0.0d));
        }

        int time = 0;
        while (!exitStrategy.evaluate(population, time))
        {
            fireExitFailed(population, time);

            // copy reference to parent generation
            WeightedMap<I> parents = population;

            // recombine parents into children
            Set<I> recombined = recombination.recombine(parents.keySet());
            fireRecombined(parents.keySet(), recombined);

            // mutate children amongst themselves
            Set<I> mutated = mutation.mutate(recombined);
            fireMutated(recombined, mutated);

            // create new weighted map for evaluating children
            WeightedMap<I> children = new HashWeightedMap<I>(parents.size(), DEFAULT_LOAD_FACTOR);

            // evalute fitness of children
            for (I i : mutated)
            {
                Double score = fitness.score(i);
                children.put(i, score);
                fireFitnessCalculated(i, score);
            }

            // select individuals for next generation from children, using parent generation as reference
            population = selection.select(parents, children);
            fireSelected(parents, children, population);

            time++;
        }

        fireExitSucceeded(population, time);

        // exit strategy condition(s) met, return successful population
        return population;
    }


    /**
     * Check that the specified parameters are valid.
     *
     * @param individuals set of individuals, must not be null and must contain
     *    at least one individual
     * @param exitStrategy exit strategy function, must not be null
     * @param recombination recombination function, must not be null
     * @param mutation mutation function, must not be null
     * @param fitness fitness function, must not be null
     * @param selection selection function, must not be null
     */
    private void checkParameters(final Set<I> individuals,
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
    public void addEvolutionaryAlgorithmListener(final EvolutionaryAlgorithmListener<I> l)
    {
        listenerList.add(EvolutionaryAlgorithmListener.class, l);
    }

    /**
     * Remove the specified evolutionary algorithm listener.
     *
     * @param l evolutionary algorithm listener to remove
     */
    public void removeEvolutionaryAlgorithmListener(final EvolutionaryAlgorithmListener<I> l)
    {
        listenerList.remove(EvolutionaryAlgorithmListener.class, l);
    }

    /**
     * Return the number of evolutionary algorithm listeners registered to this
     * evolutionary algorithm.
     *
     * @return the number of evolutionary algorithm listeners registered to this
     *    evolutionary algorithm
     */
    public int getEvolutionaryAlgorithmListenerCount()
    {
        return listenerList.getListenerCount(EvolutionaryAlgorithmListener.class);
    }

    /**
     * Return an array of evolutionary algorithm listeners registered to this
     * evolutionary algorithm.
     *
     * @return an array of evolutionary algorithm listeners registered to this
     *    evolutionary algorithm
     */
    public EvolutionaryAlgorithmListener<I>[] getEvolutionaryAlgorithmListeners()
    {
        return (EvolutionaryAlgorithmListener<I>[]) listenerList.getListeners(EvolutionaryAlgorithmListener.class);
    }

    /**
     * Fire an exit failed event to all registered listeners.
     *
     * @param population population
     * @param time time
     */
    private void fireExitFailed(final WeightedMap<I> population, final int time)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, population, time);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).exitFailed(e);
            }
        }
    }

    /**
     * Fire an exit succeeded event to all registered listeners.
     *
     * @param population population
     * @param time time
     */
    private void fireExitSucceeded(final WeightedMap<I> population, final int time)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, population, time);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).exitSucceeded(e);
            }
        }
    }

    /**
     * Fire a recombined event to all registered listeners.
     *
     * @param parents parents
     * @param recombined recombined
     */
    private void fireRecombined(final Set<I> parents, final Set<I> recombined)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, parents, recombined, null);
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
    private void fireMutated(final Set<I> recombined, final Set<I> mutated)
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
     * @param parentPopulation parent population
     * @param childPopulation child population
     * @param population population
     */
    private void fireSelected(final WeightedMap<I> parentPopulation,
                              final WeightedMap<I> childPopulation,
                              final WeightedMap<I> population)
    {
        Object[] listeners = listenerList.getListenerList();
        EvolutionaryAlgorithmEvent<I> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == EvolutionaryAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new EvolutionaryAlgorithmEvent<I>(this, parentPopulation, childPopulation, population);
                }

                ((EvolutionaryAlgorithmListener<I>) listeners[i + 1]).selected(e);
            }
        }
    }
}
