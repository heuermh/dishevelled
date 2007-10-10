/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2007 held jointly by the individual authors.

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
package org.dishevelled.swarm.exit;

import org.dishevelled.swarm.ExitStrategy;
import org.dishevelled.swarm.Particle;
import org.dishevelled.swarm.ParticleSwarm;

/**
 * Fitness floor exit strategy.  Exits as soon as every particle has
 * a fitness score above a set lower bound.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessFloorExitStrategy
    implements ExitStrategy
{
    /** Lower bound for this fitness floor exit strategy. */
    private final double lowerBound;


    /**
     * Create a new fitness threshold exit strategy with the specified lower bound.
     *
     * @param lowerBound lower bound for this fitness floor exit strategy
     */
    public FitnessFloorExitStrategy(final double lowerBound)
    {
        this.lowerBound = lowerBound;
    }


    /** {@inheritDoc} */
    public boolean evaluate(final ParticleSwarm swarm, final int epoch)
    {
        for (Particle particle : swarm)
        {
            if (particle.getFitness() <= lowerBound)
            {
                return false;
            }
        }
        return true;
    }
}