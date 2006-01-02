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
package org.dishevelled.evolve.select;

import org.dishevelled.weighted.WeightedMap;

import org.dishevelled.evolve.Selection;
//import org.dishevelled.evolve.Individual;

/**
 * Null selection function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NullSelection<I>
    implements Selection<I>
{

    /** @see Selection */
    public WeightedMap<I> select(final WeightedMap<I> parents,
                                 final WeightedMap<I> children)
    {
        return children;
    }
}