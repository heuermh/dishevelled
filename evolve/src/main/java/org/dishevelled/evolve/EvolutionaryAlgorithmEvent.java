/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2008 held jointly by the individual authors.

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
import java.util.EventObject;

import org.dishevelled.weighted.WeightedMap;

/**
 * An event representing progress in an evolutionary algorithm function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EvolutionaryAlgorithmEvent<I>
    extends EventObject
{
    /** Population of individuals. */
    private Collection<I> population;

    /** Fitness scores. */
    private WeightedMap<I> scores;

    /** Time (in number of generations), for exit events. */
    private int time;

    /** Collection of recombined individuals, for recombined or mutated events. */
    private Collection<I> recombined;

    /** Collection of mutated individuals, for mutated events. */
    private Collection<I> mutated;

    /** Collection of selected individuals, for selected events. */
    private Collection<I> selected;

    /** Individual, for fitness calculated events. */
    private I individual;

    /** Score, for fitness calculated events. */
    private Double score;


    /**
     * Create a new evolutionary algorithm event with the specified evolutionary
     * algorithm function as the source of this event.
     *
     * @param source source of this event
     */
    private EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source)
    {
        super(source);
    }

    /**
     * Create a new evolutionary algorithm event with the specified parameters.
     *
     * @param source source of this event
     * @param population population of individuals, for exit events
     * @param scores fitness scores, for exit events
     * @param time time (in number of generations), for exit events
     */
    public EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source,
                                      final Collection<I> population,
                                      final WeightedMap<I> scores,
                                      final int time)
    {
        this(source);
        this.population = population;
        this.scores = scores;
        this.time = time;
    }

    /**
     * Create a new evolutionary algorithm event with the specified parameters.
     *
     * @param source source of this event
     * @param population population of individuals, for recombined events
     * @param recombined collection of recombined individuals, for recombined or mutated events
     * @param mutated collection of mutated individuals, for mutated events
     */
    public EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source,
                                      final Collection<I> population,
                                      final Collection<I> recombined,
                                      final Collection<I> mutated)
    {
        this(source);
        this.population = population;
        this.recombined = recombined;
        this.mutated = mutated;
    }

    /**
     * Create a new evolutionary algorithm event with the specified parameters.
     *
     * @param source source of this event
     * @param individual individual, for fitness calculated events
     * @param score score, for fitness calculated events
     */
    public EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source,
                                      final I individual,
                                      final Double score)
    {
        this(source);
        this.individual = individual;
        this.score = score;
    }

    /**
     * Create a new evolutionary algorithm event with the specified parameters.
     *
     * @param source source of this event
     * @param population population of individuals, for selected events
     * @param selected collection of selected individuals, for selected events
     * @param scores fitness scores, for selected events
     */
    public EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source,
                                      final Collection<I> population,
                                      final Collection<I> selected,
                                      final WeightedMap<I> scores)
    {
        this(source);
        this.population = population;
        this.selected = selected;
        this.scores = scores;
    }


    /**
     * Return the source of this event as an evolutionary algorithm function.
     *
     * @return the source of this event as an evolutionary algorithm function
     */
    public EvolutionaryAlgorithm<I> getEvolutionaryAlgorithm()
    {
        return (EvolutionaryAlgorithm<I>) super.getSource();
    }

    /**
     * Return the population of individuals.  May be null.
     *
     * @return the population of individuals
     */
    public Collection<I> getPopulation()
    {
        return population;
    }

    /**
     * Return the fitness scores.  May be null.
     *
     * @return the fitness scores
     */
    public WeightedMap<I> getScores()
    {
        return scores;
    }

    /**
     * Return the time (in number of generations), for exit events.  Defaults to <code>0</code>.
     *
     * @return the time (in number of generations), for exit events
     */
    public int getTime()
    {
        return time;
    }

    /**
     * Return the collection of recombined individuals, for recombined or mutated events.  May be null.
     *
     * @return the collection of recombined individuals, for recombined or mutated events
     */
    public Collection<I> getRecombined()
    {
        return recombined;
    }

    /**
     * Return the collection of mutated individuals, for mutated events.  May be null.
     *
     * @return the collection of mutated individuals, for mutated events
     */
    public Collection<I> getMutated()
    {
        return mutated;
    }

    /**
     * Return the individual, for fitness calculated events.  May be null.
     *
     * @return the individual, for fitness calculated events
     */
    public I getIndividual()
    {
        return individual;
    }

    /**
     * Return the score, for fitness calculated events.  May be null.
     *
     * @return the score, for fitness calculated events
     */
    public Double getScore()
    {
        return score;
    }

    /**
     * Return the collection of selected individuals, for selected events.  May be null.
     *
     * @return the collection of selected individuals, for selected events
     */
    public Collection<I> getSelected()
    {
        return selected;
    }
}
