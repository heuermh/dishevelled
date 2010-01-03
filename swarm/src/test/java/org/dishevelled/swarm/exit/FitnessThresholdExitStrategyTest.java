/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2010 held jointly by the individual authors.

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

import org.dishevelled.swarm.AbstractExitStrategyTest;
import org.dishevelled.swarm.ExitStrategy;
import org.dishevelled.swarm.Particle;
import org.dishevelled.swarm.ParticleSwarm;
import org.dishevelled.swarm.TestParticleSwarm;

/**
 * Unit test for FitnessThresholdExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessThresholdExitStrategyTest
    extends AbstractExitStrategyTest
{

    /** {@inheritDoc} */
    protected ExitStrategy createExitStrategy()
    {
        return new FitnessThresholdExitStrategy(0.9d);
    }

    public void testConstructor()
    {
        FitnessThresholdExitStrategy fitnessThresholdExitStrategy0 = new FitnessThresholdExitStrategy(0.0d);
        FitnessThresholdExitStrategy fitnessThresholdExitStrategy1 = new FitnessThresholdExitStrategy(1.0d);
        FitnessThresholdExitStrategy fitnessThresholdExitStrategy2 = new FitnessThresholdExitStrategy(-1.0d);
        FitnessThresholdExitStrategy fitnessThresholdExitStrategy3 = new FitnessThresholdExitStrategy(Double.MIN_VALUE);
        FitnessThresholdExitStrategy fitnessThresholdExitStrategy4 = new FitnessThresholdExitStrategy(Double.MAX_VALUE);
    }

    public void testFitnessThresholdExitStrategy()
    {
        ParticleSwarm swarm = new TestParticleSwarm();

        double maxFitness = -1 * Double.MAX_VALUE;
        for (Particle p : swarm)
        {
            double fitness = p.getFitness();
            if (fitness > maxFitness)
            {
                maxFitness = fitness;
            }
        }

        FitnessThresholdExitStrategy fitnessThresholdExitStrategy0 = new FitnessThresholdExitStrategy(maxFitness - 1.0d);
        assertTrue(fitnessThresholdExitStrategy0.evaluate(swarm, 0));

        FitnessThresholdExitStrategy fitnessThresholdExitStrategy1 = new FitnessThresholdExitStrategy(maxFitness);
        assertFalse(fitnessThresholdExitStrategy1.evaluate(swarm, 0));

        FitnessThresholdExitStrategy fitnessThresholdExitStrategy2 = new FitnessThresholdExitStrategy(maxFitness + 1.0d);
        assertFalse(fitnessThresholdExitStrategy2.evaluate(swarm, 0));
    }
}
