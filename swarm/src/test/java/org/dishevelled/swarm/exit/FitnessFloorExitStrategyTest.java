/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2013 held jointly by the individual authors.

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
 * Unit test for FitnessFloorExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FitnessFloorExitStrategyTest
    extends AbstractExitStrategyTest
{

    /** {@inheritDoc} */
    protected ExitStrategy createExitStrategy()
    {
        return new FitnessFloorExitStrategy(0.9d);
    }

    public void testConstructor()
    {
        FitnessFloorExitStrategy fitnessFloorExitStrategy0 = new FitnessFloorExitStrategy(0.0d);
        FitnessFloorExitStrategy fitnessFloorExitStrategy1 = new FitnessFloorExitStrategy(1.0d);
        FitnessFloorExitStrategy fitnessFloorExitStrategy2 = new FitnessFloorExitStrategy(-1.0d);
        FitnessFloorExitStrategy fitnessFloorExitStrategy3 = new FitnessFloorExitStrategy(Double.MIN_VALUE);
        FitnessFloorExitStrategy fitnessFloorExitStrategy4 = new FitnessFloorExitStrategy(Double.MAX_VALUE);
    }

    public void testFitnessFloorExitStrategy()
    {
        ParticleSwarm swarm = new TestParticleSwarm();

        double minFitness = Double.MAX_VALUE;
        for (Particle p : swarm)
        {
            double fitness = p.getFitness();
            if (fitness < minFitness)
            {
                minFitness = fitness;
            }
        }

        FitnessFloorExitStrategy fitnessFloorExitStrategy0 = new FitnessFloorExitStrategy(minFitness - 1.0d);
        assertTrue(fitnessFloorExitStrategy0.evaluate(swarm, 0));

        FitnessFloorExitStrategy fitnessFloorExitStrategy1 = new FitnessFloorExitStrategy(minFitness);
        assertFalse(fitnessFloorExitStrategy1.evaluate(swarm, 0));

        FitnessFloorExitStrategy fitnessFloorExitStrategy2 = new FitnessFloorExitStrategy(minFitness + 1.0d);
        assertFalse(fitnessFloorExitStrategy2.evaluate(swarm, 0));
    }
}
