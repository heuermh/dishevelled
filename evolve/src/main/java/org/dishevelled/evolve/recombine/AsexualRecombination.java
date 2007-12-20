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
package org.dishevelled.evolve.recombine;

import java.util.Collection;
import java.util.ArrayList;

import org.dishevelled.evolve.Recombination;

/**
 * Abstract asexual recombination function implementation.
 * Subclasses need only to implement the abstract individual-wise method
 * <code>I recombine(I)</code>.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AsexualRecombination<I>
    implements Recombination<I>
{

    /** {@inheritDoc} */
    public final Collection<I> recombine(final Collection<I> population)
    {
        Collection<I> recombined = new ArrayList<I>(population.size());

        for (I individual : population)
        {
            recombined.add(recombine(individual));
        }
        return recombined;
    }

    /**
     * Recombine the specified individual asexually, returning
     * a new individual.
     *
     * @param individual individual to recombine asexually
     * @return a new individual recombined from the specified individual
     */
    protected abstract I recombine(I individual);
}
