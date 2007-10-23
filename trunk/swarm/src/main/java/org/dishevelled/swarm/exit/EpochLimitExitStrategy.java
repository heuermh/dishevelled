/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2007 held jointly by the individual authors.

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
package org.dishevelled.swarm.exit;

import org.dishevelled.swarm.ExitStrategy;
import org.dishevelled.swarm.ParticleSwarm;

/**
 * Epoch limit exit strategy.  Exits as soon as the epoch meets or exceeds a set epoch limit.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EpochLimitExitStrategy
    implements ExitStrategy
{
    /** Epoch limit for this epoch limit exit strategy. */
    private final int epochLimit;


    /**
     * Create a new epoch limit exit strategy with the specified epoch limit.
     *
     * @param epochLimit epoch limit for this epoch limit exit strategy, must be &gt;= 0
     */
    public EpochLimitExitStrategy(final int epochLimit)
    {
        if (epochLimit < 0)
        {
            throw new IllegalArgumentException("epochLimit must be >= 0");
        }
        this.epochLimit = epochLimit;
    }


    /** {@inheritDoc} */
    public boolean evaluate(final ParticleSwarm swarm, final int epoch)
    {
        return (epoch >= epochLimit);
    }
}