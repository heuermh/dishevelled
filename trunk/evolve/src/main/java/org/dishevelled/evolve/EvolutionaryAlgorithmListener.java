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

import java.util.EventListener;

/**
 * A listener that receives notification of progress
 * in an evolutionary algorithm function.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface EvolutionaryAlgorithmListener<I>
    extends EventListener
{

    /**
     * Notify this listener of an exit failed event.
     * The specified event object will provide a reference
     * to the population and the time (in number of generations).
     *
     * @see EvolutionaryAlgorithmEvent#getPopulation
     * @see EvolutionaryAlgorithmEvent#getTime
     * @param e exit failed event
     */
    void exitFailed(EvolutionaryAlgorithmEvent<I> e);

    /**
     * Notify this listener of an exit succeeded event.
     * The specified event object will provide a reference
     * to the population and the time (in number of generations).
     *
     * @see EvolutionaryAlgorithmEvent#getPopulation
     * @see EvolutionaryAlgorithmEvent#getTime
     * @param e exit succeeded event
     */
    void exitSucceeded(EvolutionaryAlgorithmEvent<I> e);

    /**
     * Notify this listener of a recombined event.
     * The specified event object will provide a reference
     * to parents and recombined.
     *
     * @see EvolutionaryAlgorithmEvent#getParents
     * @see EvolutionaryAlgorithmEvent#getRecombined
     * @param e recombined event
     */
    void recombined(EvolutionaryAlgorithmEvent<I> e);

    /**
     * Notify this listener of a mutated event.
     * The specified event object will provide a reference
     * to recombined and mutated.
     *
     * @see EvolutionaryAlgorithmEvent#getRecombined
     * @see EvolutionaryAlgorithmEvent#getMutated
     * @param e mutated event
     */
    void mutated(EvolutionaryAlgorithmEvent<I> e);

    /**
     * Notify this listener of a fitness calculated event.
     * The specified event object will provide a reference
     * to the individual and its fitness score.
     *
     * @see EvolutionaryAlgorithmEvent#getIndividual
     * @see EvolutionaryAlgorithmEvent#getScore
     * @param e fitness calculated event
     */
    void fitnessCalculated(EvolutionaryAlgorithmEvent<I> e);

    /**
     * Notify this listener of a selected event.
     * The specified event object will provide a reference
     * to the parent population, the child population, and
     * the selected population.
     *
     * @see EvolutionaryAlgorithmEvent#getParentPopulation
     * @see EvolutionaryAlgorithmEvent#getChildPopulation
     * @see EvolutionaryAlgorithmEvent#getPopulation
     * @param e selected event
     */
    void selected(EvolutionaryAlgorithmEvent<I> e);
}
