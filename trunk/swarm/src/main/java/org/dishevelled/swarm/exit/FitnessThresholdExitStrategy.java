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
 * Fitness threshold exit strategy.  Exits as soon as at least one particle has
 * a fitness score above a set fitness threshold.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessThresholdExitStrategy
    implements ExitStrategy
{
    /** Fitness threshold for this fitness threshold exit strategy. */
    private final double fitnessThreshold;


    /**
     * Create a new fitness threshold exit strategy with the specified fitness threshold.
     *
     * @param fitnessThreshold fitness threshold for this fitness threshold exit strategy
     */
    public FitnessThresholdExitStrategy(final double fitnessThreshold)
    {
        this.fitnessThreshold = fitnessThreshold;
    }


    /** {@inheritDoc} */
    public boolean evaluate(final ParticleSwarm swarm, final int epoch)
    {
        for (Particle particle : swarm)
        {
            if (particle.getFitness() > fitnessThreshold)
            {
                return true;
            }
        }
        return false;
    }
}