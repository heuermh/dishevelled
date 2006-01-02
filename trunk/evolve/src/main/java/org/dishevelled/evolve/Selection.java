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
package org.dishevelled.evolve;

import org.dishevelled.weighted.WeightedMap;

/**
 * A selection function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Selection<I>
{

    /**
     * Select individuals from the specified population of child individuals
     * given the parent generation as a reference.  Each of the child and parent
     * populations are provided in the form of a weighted map of individuals to
     * fitness scores.
     *
     * @param parents parent generation, given as a reference
     * @param children population of child individuals
     * @return a weighted map of the individuals selected from the specified
     *    population of child individuals to their fitness scores
     */
    WeightedMap<I> select(WeightedMap<I> parents, WeightedMap<I> children);
}