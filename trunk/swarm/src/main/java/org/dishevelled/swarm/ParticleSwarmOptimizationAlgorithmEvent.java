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

import java.util.EventObject;

/**
 * An event representing progress in a particle swarm optimization
 * algorithm function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ParticleSwarmOptimizationAlgorithmEvent
    extends EventObject
{
    /** Particle swarm. */
    private ParticleSwarm swarm;

    /** Epoch. */
    private final int epoch;

    /** Particle. */
    private final int particle;

    /** Dimension. */
    private final int dimension;

    /** Value, may be postion or velocity. */
    private final double value;

    /** Fitness score. */
    private final double fitness;

    /** Default epoch. */
    public static final int DEFAULT_EPOCH = -1;

    /** Default particle. */
    public static final int DEFAULT_PARTICLE = -1;

    /** Default dimension. */
    public static final int DEFAULT_DIMENSION = -1;

    /** Default value. */
    public static final double DEFAULT_VALUE = Double.NaN;

    /** Default fitness score. */
    public static final double DEFAULT_FITNESS = Double.NaN;


    /**
     * Create a new particle swarm optimization algorithm event with the
     * specified parameters.
     *
     * @param source source of this event, must not be null
     * @param swarm particle swarm, must not be null
     * @param epoch epoch
     */
    ParticleSwarmOptimizationAlgorithmEvent(final ParticleSwarmOptimizationAlgorithm source,
                                            final ParticleSwarm swarm,
                                            final int epoch)
    {
        super(source);

        if (swarm == null)
        {
            throw new IllegalArgumentException("swarm must not be null");
        }
        this.swarm = swarm;
        this.epoch = epoch;
        this.particle = DEFAULT_PARTICLE;
        this.dimension = DEFAULT_DIMENSION;
        this.value = DEFAULT_VALUE;
        this.fitness = DEFAULT_FITNESS;
    }

    /**
     * Create a new particle swarm optimization algorithm event with the
     * specified parameters.
     *
     * @param source source of this event, must not be null
     * @param particle particle
     * @param dimension dimension
     * @param value value, may be position or velocity
     */
    ParticleSwarmOptimizationAlgorithmEvent(final ParticleSwarmOptimizationAlgorithm source,
                                            final int particle,
                                            final int dimension,
                                            final double value)
    {
        super(source);

        this.swarm = null;
        this.epoch = DEFAULT_EPOCH;
        this.particle = particle;
        this.dimension = dimension;
        this.value = value;
        this.fitness = DEFAULT_FITNESS;
    }

    /**
     * Create a new particle swarm optimization algorithm event with the
     * specified parameters.
     *
     * @param source source of this event, must not be null
     * @param particle particle
     * @param dimension dimension
     * @param value value, may be position or velocity
     * @param fitness fitness score
     */
    ParticleSwarmOptimizationAlgorithmEvent(final ParticleSwarmOptimizationAlgorithm source,
                                            final int particle,
                                            final int dimension,
                                            final double value,
                                            final double fitness)
    {
        super(source);

        this.swarm = null;
        this.epoch = DEFAULT_EPOCH;
        this.particle = particle;
        this.dimension = dimension;
        this.value = value;
        this.fitness = fitness;
    }


    /**
     * Return the source of this event as a particle swarm optimization algorithm.
     *
     * @return the source of this event as a particle swarm optimization algorithm
     */
    public ParticleSwarmOptimizationAlgorithm getParticleSwarmOptimizationAlgorithm()
    {
        return (ParticleSwarmOptimizationAlgorithm) super.getSource();
    }

    /**
     * Return the particle swarm for this event, if any.  The particle swarm may be null.
     *
     * @return the particle swarm for this event, if any
     */
    public ParticleSwarm getParticleSwarm()
    {
        return swarm;
    }

    /**
     * Return the epoch for this event, if any.  Defaults to <code>-1</code>.
     *
     * @see #DEFAULT_EPOCH
     * @return the epoch for this event, if any
     */
    public int getEpoch()
    {
        return epoch;
    }

    /**
     * Return the particle for this event, if any.  Defaults to <code>-1</code>.
     *
     * @see #DEFAULT_PARTICLE
     * @return the particle for this event, if any
     */
    public int getParticle()
    {
        return particle;
    }

    /**
     * Return the dimension for this event, if any.  Defaults to <code>-1</code>.
     *
     * @see #DEFAULT_DIMENSION
     * @return the dimension for this event, if any
     */
    public int getDimension()
    {
        return dimension;
    }

    /**
     * Return the value for this event, if any.  The value may refer to
     * position or velocity.  Defaults to <code>Double.NaN</code>.
     *
     * @see #DEFAULT_VALUE
     * @return the value for this event, if any
     */
    public double getValue()
    {
        return value;
    }

    /**
     * Return the fitness score for this event, if any.  Defaults to <code>Double.NaN</code>.
     *
     * @see #DEFAULT_FITNESS
     * @return the fitness score for this event, if any
     */
    public double getFitness()
    {
        return fitness;
    }
}
