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

/**
 * An evolutionary algorithm function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface EvolutionaryAlgorithm<I>
{

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
     *    specified exit strategy
     */
    Collection<I> evolve(Collection<I> individuals,
                         ExitStrategy<I> exitStrategy,
                         Recombination<I> recombination,
                         Mutation<I> mutation,
                         Fitness<I> fitness,
                         Selection<I> selection);

    /**
     * Add the specified evolutionary algorithm listener.
     *
     * @param l evolutionary algorithm listener to add
     */
    void addEvolutionaryAlgorithmListener(EvolutionaryAlgorithmListener<I> l);

    /**
     * Remove the specified evolutionary algorithm listener.
     *
     * @param l evolutionary algorithm listener to remove
     */
    void removeEvolutionaryAlgorithmListener(EvolutionaryAlgorithmListener<I> l);

    /**
     * Return the number of evolutionary algorithm listeners registered to this
     * evolutionary algorithm.
     *
     * @return the number of evolutionary algorithm listeners registered to this
     *    evolutionary algorithm
     */
    int getEvolutionaryAlgorithmListenerCount();

    /**
     * Return an array of evolutionary algorithm listeners registered to this
     * evolutionary algorithm.
     *
     * @return an array of evolutionary algorithm listeners registered to this
     *    evolutionary algorithm
     */
    EvolutionaryAlgorithmListener<I>[] getEvolutionaryAlgorithmListeners();
}
