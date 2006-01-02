/*

    dsh-evolve  Simple framework for evolutionary algorithms.
    Copyright (c) 2005-2006 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.evolve.recombine;

import java.util.Set;
import java.util.HashSet;

//import org.dishevelled.evolve.Individual;
import org.dishevelled.evolve.Recombination;

/**
 * Abstract asexual recombination function implementation.
 * Subclasses need only to implement the abstract individual-wise method
 * <code>I recombine(I)</code>.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AsexualRecombination<I>
    implements Recombination<I>
{

    /** @see Recombination */
    public final Set<I> recombine(final Set<I> parents)
    {
        Set<I> recombined = new HashSet<I>(parents.size());

        for (I parent : parents)
        {
            recombined.add(recombine(parent));
        }

        return recombined;
    }

    /**
     * Recombine the specified parent asexually, returning
     * a new individual.
     *
     * @param parent parent to recombine asexually
     * @return a new individual recombined from the specified parent
     */
    protected abstract I recombine(I parent);
}