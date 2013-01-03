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
package org.dishevelled.evolve.recombine;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.dishevelled.evolve.Recombination;

/**
 * Abstract sexual recombination function implementation.
 * Subclasses need only to implement the abstract individual-wise method
 * <code>I recombine(I individual0, I individual1)</code>.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 */
public abstract class SexualRecombination<I>
    implements Recombination<I>
{

    /** {@inheritDoc} */
    public final Collection<I> recombine(final Collection<I> population)
    {
        int size = population.size();
        List<I> populationAsList = new ArrayList<I>(population);
        Collection<I> recombined = new ArrayList<I>(population.size());

        for (int i = 1; i < size; i++)
        {
            I parent0 = populationAsList.get(i - 1);
            I parent1 = populationAsList.get(i);
            recombined.add(recombine(parent0, parent1));
        }
        recombined.add(recombine(populationAsList.get(size - 1), populationAsList.get(size - 1)));
        return recombined;
    }

    /**
     * Recombine the specified individuals sexually, returning a
     * new individual.
     *
     * @param individual0 individual to recombine sexually
     * @param individual1 individual to recombine sexually
     * @return a new individual recombined from the specified individuals
     */
    protected abstract I recombine(I individual0, I individual1);
}
