/*

    dsh-swarm  Simple framework for particle swarm optimization algorithms.
    Copyright (c) 2006 held jointly by the individual authors.

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
package org.dishevelled.swarm;

import java.util.EventObject;

import org.dishevelled.matrix.ObjectMatrix1D;
import org.dishevelled.matrix.ObjectMatrix2D;

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
    /** Particle position. */
    private final ObjectMatrix2D<Double> position;

    /** Particle velocity. */
    private final ObjectMatrix2D<Double> velocity;

    /** Cognitive or individual memory. */
    private final ObjectMatrix2D<Double> cognitiveMemory;

    /** Social or swarm memory. */
    private final ObjectMatrix1D<Double> socialMemory;

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
     * @param source source of this event
     * @param position particle position
     * @param velocity particle velocity
     * @param cognitiveMemory cognitive or individual memory
     * @param socialMemory social or swarm memory
     * @param epoch epoch
     */
    ParticleSwarmOptimizationAlgorithmEvent(final ParticleSwarmOptimizationAlgorithm source,
                                            final ObjectMatrix2D<Double> position,
                                            final ObjectMatrix2D<Double> velocity,
                                            final ObjectMatrix2D<Double> cognitiveMemory,
                                            final ObjectMatrix1D<Double> socialMemory,
                                            final int epoch)
    {
        super(source);

        this.position = position;
        this.velocity = velocity;
        this.cognitiveMemory = cognitiveMemory;
        this.socialMemory = socialMemory;
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
     * @param source source of this event
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

        this.position = null;
        this.velocity = null;
        this.cognitiveMemory = null;
        this.socialMemory = null;
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
     * @param source source of this event
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

        this.position = null;
        this.velocity = null;
        this.cognitiveMemory = null;
        this.socialMemory = null;
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
     * Return the 2D particle position matrix for this event, if any.  The matrix may be null.
     *
     * @return the 2D particle position matrix for this event, if any.
     */
    public ObjectMatrix2D<Double> getPosition()
    {
        // return ObjectMatrixUtils.unmodifiableObjectMatrix2D(position);
        return position;
    }

    /**
     * Return the 2D particle velocity matrix for this event, if any.  The matrix may be null.
     *
     * @return the 2D particle velocity matrix for this event, if any.
     */
    public ObjectMatrix2D<Double> getVelocity()
    {
        // return ObjectMatrixUtils.unmodifiableObjectMatrix2D(velocity);
        return velocity;
    }

    /**
     * Return the 2D cognitive memory matrix for this event, if any.  The matrix may be null.
     *
     * @return the 2D cognitive memory matrix for this event, if any.
     */
    public ObjectMatrix2D<Double> getCognitiveMemory()
    {
        // return ObjectMatrixUtils.unmodifiableObjectMatrix2D(cognitiveMemory);
        return cognitiveMemory;
    }

    /**
     * Return the 1D social memory matrix for this event, if any.  The matrix may be null.
     *
     * @return the 1D social memory matrix for this event, if any.
     */
    public ObjectMatrix1D<Double> getSocialMemory()
    {
        // return ObjectMatrixUtils.unmodifiableObjectMatrix1D(socialMemory);
        return socialMemory;
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