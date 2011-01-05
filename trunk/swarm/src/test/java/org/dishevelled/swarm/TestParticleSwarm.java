/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2011 held jointly by the individual authors.

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
package org.dishevelled.swarm;

import java.util.Iterator;

import org.dishevelled.swarm.exit.EpochLimitExitStrategy;

import org.dishevelled.swarm.fitness.RandomFitness;

/**
 * Test particle swarm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TestParticleSwarm
    implements ParticleSwarm
{
    /** Particle swarm implementation for this test particle swarm. */
    private final ParticleSwarm swarm;

    /** Number of particles for this test particle swarm. */
    private static final int PARTICLES = 100;

    /** Number of dimensions for this test particle swarm. */
    private static final int DIMENSIONS = 10;


    /**
     * Create a new test particle swarm.
     */
    public TestParticleSwarm()
    {
        ExitStrategy exitStrategy = new EpochLimitExitStrategy(10);
        Fitness fitness = new RandomFitness();
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        swarm = algorithm.optimize(PARTICLES, DIMENSIONS, exitStrategy, fitness);
    }


    /** {@inheritDoc} */
    public int getParticles()
    {
        return swarm.getParticles();
    }

    /** {@inheritDoc} */
    public int getDimensions()
    {
        return swarm.getDimensions();
    }

    /** {@inheritDoc} */
    public double[] getSocialMemory()
    {
        return swarm.getSocialMemory();
    }

    /** {@inheritDoc} */
    public Iterator<Particle> iterator()
    {
        return swarm.iterator();
    }
}
