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
    /** Population, for exit or selected events. */
    private WeightedMap<I> population;

    /** Time (in number of generations), for exit events. */
    private int time;

    /** Parents, for recombined events. */
    private Set<I> parents;

    /** Recombined, for recombined or mutated events. */
    private Set<I> recombined;

    /** Mutated, for mutated events. */
    private Set<I> mutated;

    /** Individual, for fitness calculated events. */
    private I individual;

    /** Score, for fitness calculated events. */
    private Double score;

    /** Parent population, for selected events. */
    private WeightedMap<I> parentPopulation;

    /** Child population, for selected events. */
    private WeightedMap<I> childPopulation;


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
     * @param population population, for exit events
     * @param time time (in number of generations), for exit events
     */
    public EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source,
                                      final WeightedMap<I> population,
                                      final int time)
    {
        this(source);
        this.population = population;
        this.time = time;
    }

    /**
     * Create a new evolutionary algorithm event with the specified parameters.
     *
     * @param source source of this event
     * @param parents parents, for recombined events
     * @param recombined recombined, for recombined or mutated events
     * @param mutated mutated, for mutated events
     */
    public EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source,
                                      final Set<I> parents,
                                      final Set<I> recombined,
                                      final Set<I> mutated)
    {
        this(source);
        this.parents = parents;
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
     * @param parentPopulation parent population, for selected events
     * @param childPopulation child population, for selected events
     * @param population population, for selected events
     */
    public EvolutionaryAlgorithmEvent(final EvolutionaryAlgorithm<I> source,
                                      final WeightedMap<I> parentPopulation,
                                      final WeightedMap<I> childPopulation,
                                      final WeightedMap<I> population)
    {
        this(source);
        this.parentPopulation = parentPopulation;
        this.childPopulation = childPopulation;
        this.population = population;
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
     * Return the population, for exit or selected events.  May be null.
     *
     * @return the population, for exit or selected events
     */
    public WeightedMap<I> getPopulation()
    {
        return population;
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
     * Return parents, for recombined events.  May be null.
     *
     * @return parents, for recombined events
     */
    public Set<I> getParents()
    {
        return parents;
    }

    /**
     * Return recombined, for recombined or mutated events.  May be null.
     *
     * @return recombined, for recombined or mutated events
     */
    public Set<I> getRecombined()
    {
        return recombined;
    }

    /**
     * Return mutated, for mutated events.  May be null.
     *
     * @return mutated, for mutated events
     */
    public Set<I> getMutated()
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
     * Return the parent population, for selected events.  May be null.
     *
     * @return the parent population, for selected events
     */
    public WeightedMap<I> getParentPopulation()
    {
        return parentPopulation;
    }

    /**
     * Return the child population, for selected events.  May be null.
     *
     * @return the child population, for selected events
     */
    public WeightedMap<I> getChildPopulation()
    {
        return childPopulation;
    }
}
