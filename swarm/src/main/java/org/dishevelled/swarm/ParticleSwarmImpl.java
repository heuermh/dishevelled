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
package org.dishevelled.swarm;

import java.util.Iterator;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix3D;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix3D;

/**
 * Particle swarm implementation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class ParticleSwarmImpl
    implements ParticleSwarm
{
    /** Number of particles for this particle swarm. */
    private final int particles;

    /** Number of dimensions for this particle swarm. */
    private final int dimensions;

    /** 3D double matrix containing particle position, velocity, and cognitive memory. */
    private final DoubleMatrix3D swarm;

    /** 1D double matrix of particle fitness. */
    private final DoubleMatrix1D fitness;

    /** 1D double matrix of dimension social memory. */
    private final DoubleMatrix1D socialMemory;

    /** Array of doubles. */
    // todo:  might not be safe to reuse the array, if client
    //    holds a reference and then calls one of the other methods
    private final double[] array;

    /** Number of slices for the swarm matrix. */
    private static final int SLICES = 3;

    /** Slice index for particle position in the swarm matrix. */
    private static final int POSITION_SLICE = 0;

    /** Slice index for particle velocity in the swarm matrix. */
    private static final int VELOCITY_SLICE = 1;

    /** Slice index for particle cognitive memory in the swarm matrix. */
    private static final int COGNITIVE_MEMORY_SLICE = 2;


    /**
     * Create a new particle swarm implementation with the specified
     * number of particles and number of dimensions.
     *
     * @param particles number of particles for this particle swarm
     *    implementation, must be &gt;= 1
     * @param dimensions number of dimensions for this particle swarm
     *    implementation, must be &gt;= 1
     */
    ParticleSwarmImpl(final int particles, final int dimensions)
    {
        if (particles < 1)
        {
            throw new IllegalArgumentException("particles must be at least one");
        }
        if (dimensions < 1)
        {
            throw new IllegalArgumentException("dimensions must be at least one");
        }
        this.particles = particles;
        this.dimensions = dimensions;
        swarm = new DenseDoubleMatrix3D(SLICES, particles, dimensions);
        fitness = new DenseDoubleMatrix1D(particles);
        socialMemory = new DenseDoubleMatrix1D(dimensions);
        array = new double[dimensions];
    }


    /** {@inheritDoc} */
    public int getParticles()
    {
        return particles;
    }

    /** {@inheritDoc} */
    public int getDimensions()
    {
        return dimensions;
    }

    /**
     * Return a view of the position matrix for the specified particle.
     *
     * @param particle particle
     * @return a view of the position matrix for the specified particle
     */
    double[] getPosition(final int particle)
    {
        swarm.viewSlice(POSITION_SLICE).viewRow(particle).toArray(array);
        return array;
    }

    /**
     * Return the position for the specified particle and dimension.
     *
     * @param particle particle
     * @param dimension dimension
     * @return the position for the specified particle and dimension
     */
    double getPosition(final int particle, final int dimension)
    {
        return swarm.getQuick(POSITION_SLICE, particle, dimension);
    }

    /**
     * Set the position for the specified particle and dimension to <code>position</code>.
     *
     * @param particle particle
     * @param dimension dimension
     * @param position position
     */
    void setPosition(final int particle, final int dimension, final double position)
    {
        swarm.setQuick(POSITION_SLICE, particle, dimension, position);
    }

    /**
     * Return a view of the velocity matrix for the specified particle.
     *
     * @param particle particle
     * @return a view of the velocity matrix for the specified particle
     */
    double[] getVelocity(final int particle)
    {
        swarm.viewSlice(VELOCITY_SLICE).viewRow(particle).toArray(array);
        return array;
    }

    /**
     * Return the velocity for the specified particle and dimension.
     *
     * @param particle particle
     * @param dimension dimension
     * @return the velocity for the specified particle and dimension
     */
    double getVelocity(final int particle, final int dimension)
    {
        return swarm.getQuick(VELOCITY_SLICE, particle, dimension);
    }

    /**
     * Set the velocity for the specified particle and dimension to <code>velocity</code>.
     *
     * @param particle particle
     * @param dimension dimension
     * @param velocity velocity
     */
    void setVelocity(final int particle, final int dimension, final double velocity)
    {
        swarm.setQuick(VELOCITY_SLICE, particle, dimension, velocity);
    }

    /**
     * Return a view of the cognitive memory matrix for the specified particle.
     *
     * @param particle particle
     * @return a view of the cognitive memory matrix for the specified particle
     */
    double[] getCognitiveMemory(final int particle)
    {
        swarm.viewSlice(COGNITIVE_MEMORY_SLICE).viewRow(particle).toArray(array);
        return array;
    }

    /**
     * Return the cognitive memory for the specified particle and dimension.
     *
     * @param particle particle
     * @param dimension dimension
     * @return the cognitive memory for the specified particle and dimension
     */
    double getCognitiveMemory(final int particle, final int dimension)
    {
        return swarm.getQuick(COGNITIVE_MEMORY_SLICE, particle, dimension);
    }

    /** {@inheritDoc} */
    public double[] getSocialMemory()
    {
        socialMemory.toArray(array);
        return array;
    }

    /**
     * Return the social memory for this particle swarm for the specified dimension.
     *
     * @param dimension dimension
     * @return the social memory for this particle swarm for the specified dimension
     */
    double getSocialMemory(final int dimension)
    {
        return socialMemory.getQuick(dimension);
    }

    /**
     * Return the fitness for the specified particle.
     *
     * @param particle particle
     * @return the fitness for the specified particle
     */
    double getFitness(final int particle)
    {
        return fitness.getQuick(particle);
    }

    /**
     * Set the fitness for the specified particle to <code>fitness</code>.
     *
     * @param particle particle
     * @param fitness fitness
     */
    void setFitness(final int particle, final double fitness)
    {
        this.fitness.setQuick(particle, fitness);
    }

    /**
     * Update the cognitive memory for the specified particle.
     *
     * @param particle particle
     */
    void updateCognitiveMemory(final int particle)
    {
        for (int dimension = 0; dimension < dimensions; dimension++)
        {
            double value = swarm.getQuick(POSITION_SLICE, particle, dimension);
            swarm.setQuick(COGNITIVE_MEMORY_SLICE, particle, dimension, value);
        }
    }

    /**
     * Update the social memory for this particle swarm from the position
     * of the specified particle.
     *
     * @param particle particle
     */
    void updateSocialMemory(final int particle)
    {
        for (int dimension = 0; dimension < dimensions; dimension++)
        {
            double value = swarm.getQuick(POSITION_SLICE, particle, dimension);
            socialMemory.setQuick(dimension, value);
        }
    }

    /** {@inheritDoc} */
    public Iterator<Particle> iterator()
    {
        return new ParticleIterator();
    }

    /**
     * Particle iterator.
     */
    private class ParticleIterator
        implements Iterator<Particle>
    {
        /** Index of particle to be returned by subsequent call to next. */
        private int index = 0;


        /** {@inheritDoc} */
        public boolean hasNext()
        {
            return (index < particles);
        }

        /** {@inheritDoc} */
        public Particle next()
        {
            Particle particle = new ParticleImpl(index);
            index++;
            return particle;
        }

        /** {@inheritDoc} */
        public void remove()
        {
            throw new UnsupportedOperationException("remove operation not supported by this iterator");
        }
    }

    /**
     * Particle implementation.
     */
    private class ParticleImpl
        implements Particle
    {
        /** Index for this particle implementation. */
        private final int index;


        /**
         * Create a new particle implementation with the specified index.
         *
         * @param index index for this particle implementation
         */
        ParticleImpl(final int index)
        {
            this.index = index;
        }


        /** {@inheritDoc} */
        public int getDimensions()
        {
            return ParticleSwarmImpl.this.getDimensions();
        }

        /** {@inheritDoc} */
        public double getFitness()
        {
            return ParticleSwarmImpl.this.getFitness(index);
        }

        /** {@inheritDoc} */
        public double[] getPosition()
        {
            return ParticleSwarmImpl.this.getPosition(index);
        }

        /** {@inheritDoc} */
        public double[] getVelocity()
        {
            return ParticleSwarmImpl.this.getVelocity(index);
        }

        /** {@inheritDoc} */
        public double[] getCognitiveMemory()
        {
            return ParticleSwarmImpl.this.getCognitiveMemory(index);
        }
    }
}
