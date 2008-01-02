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
package org.dishevelled.evolve.exit;

import java.util.Collection;

import org.dishevelled.weighted.WeightedMap;

import org.dishevelled.evolve.ExitStrategy;

/**
 * Time limit exit strategy.  Exits as soon as
 * the time (in number of generations) meets or exceeds a set
 * time limit.
 *
 * @param <I> individual type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TimeLimitExitStrategy<I>
    implements ExitStrategy<I>
{
    /** Time limit. */
    private final int timeLimit;


    /**
     * Create a new time limit exit strategy with the specified time limit.
     *
     * @param timeLimit time limit for this time limit exit strategy, must be
     *    <code>&gt;= 1</code>
     */
    public TimeLimitExitStrategy(final int timeLimit)
    {
        if (timeLimit < 1)
        {
            throw new IllegalArgumentException("timeLimit must be >= 1");
        }

        this.timeLimit = timeLimit;
    }


    /** {@inheritDoc} */
    public boolean evaluate(final Collection<I> population, final WeightedMap<I> scores, final int time)
    {
        return (time >= timeLimit);
    }
}
