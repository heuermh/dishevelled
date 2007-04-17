/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007 held jointly by the individual authors.

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
package org.dishevelled.cluster.exitstrategy;

import java.util.List;
import java.util.Set;

import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ExitStrategy;

/**
 * Iteration limit exit strategy.  Exits as soon as the number of
 * iterations (number of times the <code>execute()</code> method
 * is called) meets or exceeds a set iteration limit.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IterationLimitExitStrategy<E>
    implements ExitStrategy<E>
{
    /** Current iteration. */
    private int iteration;

    /** Iteration limit. */
    private final int iterationLimit;


    /**
     * Create a new iteration limit exit strategy with the specified iteration limit.
     *
     * @param iterationLimit iteration limit, must be at least zero
     */
    public IterationLimitExitStrategy(final int iterationLimit)
    {
        if (iterationLimit < 0)
        {
            throw new IllegalArgumentException("iterationLimit must be at least zero, was " + iterationLimit);
        }
        this.iterationLimit = iterationLimit;
        iteration = 0;
    }


    /** {@inheritDoc} */
    public boolean evaluate(final List<? extends E> values,
                            final Set<Cluster<E>> clusters)
    {
        if (iteration >= iterationLimit)
        {
            return true;
        }
        iteration++;
        return false;
    }
}