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
package org.dishevelled.swarm;

import java.util.Random;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import javax.swing.event.EventListenerList;

/**
 * Particle swarm optimization (PSO) algorithm function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ParticleSwarmOptimizationAlgorithm
{
    /** Listener list. */
    private final EventListenerList listenerList;

    /** Property change support. */
    private final PropertyChangeSupport propertyChangeSupport;

    /** Source of randomness. */
    private Random random;

    /** Inertia weight. */
    private double inertiaWeight;

    /** Cognitive weight. */
    private double cognitiveWeight;

    /** Social weight. */
    private double socialWeight;

    /** Minimum position. */
    private double minimumPosition;

    /** Maximum position. */
    private double maximumPosition;

    /** Minimum velocity. */
    private double minimumVelocity;

    /** Maximum velocity. */
    private double maximumVelocity;

    /** Default inertia weight. */
    public static final double DEFAULT_INERTIA_WEIGHT = 0.1d;

    /** Default cognitive weight. */
    public static final double DEFAULT_COGNITIVE_WEIGHT = 0.2d;

    /** Default social weight. */
    public static final double DEFAULT_SOCIAL_WEIGHT = 0.2d;

    /** Default minimum position. */
    public static final double DEFAULT_MINIMUM_POSITION = 0.0d;

    /** Default maximum position. */
    public static final double DEFAULT_MAXIMUM_POSITION = 1.0d;

    /** Default minimum velocity. */
    public static final double DEFAULT_MINIMUM_VELOCITY = -0.5d;

    /** Default maximum velocity. */
    public static final double DEFAULT_MAXIMUM_VELOCITY = 0.5d;


    /**
     * Create a new particle swarm optimization algorithm function
     * with default parameters.
     */
    public ParticleSwarmOptimizationAlgorithm()
    {
        listenerList = new EventListenerList();
        propertyChangeSupport = new PropertyChangeSupport(this);

        this.random = new Random();
        this.inertiaWeight = DEFAULT_INERTIA_WEIGHT;
        this.cognitiveWeight = DEFAULT_COGNITIVE_WEIGHT;
        this.socialWeight = DEFAULT_SOCIAL_WEIGHT;
        this.minimumPosition = DEFAULT_MINIMUM_POSITION;
        this.maximumPosition = DEFAULT_MAXIMUM_POSITION;
        this.minimumVelocity = DEFAULT_MINIMUM_VELOCITY;
        this.maximumVelocity = DEFAULT_MAXIMUM_VELOCITY;
    }


    /**
     * Optimize a particle swarm of the specified size over the specified
     * number of dimensions given the specified exit strategy and fitness
     * functions.
     *
     * <p>Returns a 2D matrix of particle positions with the specified number
     * of particles as rows and the specified number of dimensions as columns.</p>
     *
     * @param particles number of particles, must be <code>&gt;= 1</code>
     * @param dimensions number of dimensions, must be <code>&gt;= 1</code>
     * @param exitStrategy exit strategy function, must not be null
     * @param fitness fitness function, must not be null
     * @return 2D matrix of particle positions with <code>particles</code>
     *    rows and <code>dimensions</code> columns
     */
    public ParticleSwarm optimize(final int particles,
                                  final int dimensions,
                                  final ExitStrategy exitStrategy,
                                  final Fitness fitness)
    {
        if (exitStrategy == null)
        {
            throw new IllegalArgumentException("exitStrategy must not be null");
        }
        if (fitness == null)
        {
            throw new IllegalArgumentException("fitness must not be null");
        }
        ParticleSwarmImpl swarm = new ParticleSwarmImpl(particles, dimensions);

        double range = Math.abs(maximumPosition - minimumPosition);
        for (int particle = 0; particle < particles; particle++)
        {
            for (int dimension = 0; dimension < dimensions; dimension++)
            {
                double r = (random.nextDouble() * range) + minimumPosition;
                swarm.setPosition(particle, dimension, r);
                swarm.setFitness(particle, -1 * Double.MAX_VALUE);
            }
        }

        int epoch = 0;
        while (!exitStrategy.evaluate(swarm, epoch))
        {
            fireExitFailed(swarm, epoch);
            for (int particle = 0; particle < particles; particle++)
            {
                for (int dimension = 0; dimension < dimensions; dimension++)
                {
                    // eq. 1
                    //
                    // v (t + 1) = w * v (t) + n * r * (p - x (t)) + n * r * (p    - x (t))
                    //  i               i       1   1    i   i        2   2    best   i
                    double r1 = random.nextDouble();
                    double r2 = random.nextDouble();
                    double x = swarm.getPosition(particle, dimension);
                    double v = swarm.getVelocity(particle, dimension);
                    double p = swarm.getCognitiveMemory(particle, dimension);
                    double s = swarm.getSocialMemory(dimension);

                    v = inertiaWeight * v + cognitiveWeight * r1 * (p - x) + socialWeight * r2 * (s - x);
                    v = Math.max(v, minimumVelocity);
                    v = Math.min(v, maximumVelocity);
                    swarm.setVelocity(particle, dimension, v);
                    fireVelocityCalculated(particle, dimension, v);

                    // eq. 2
                    //
                    // x (t + 1) = x (t) + v (t + 1)
                    //  i           i       i
                    x = x + v;
                    x = Math.max(x, minimumPosition);
                    x = Math.min(x, maximumPosition);
                    swarm.setPosition(particle, dimension, x);
                    firePositionUpdated(particle, dimension, x);
                }
                double fx = fitness.score(swarm.getPosition(particle));
                swarm.setFitness(particle, fx);
                fireFitnessCalculated(particle, fx);

                double fp = fitness.score(swarm.getCognitiveMemory(particle));
                double fs = fitness.score(swarm.getSocialMemory());

                if (fx > fp)
                {
                    swarm.updateCognitiveMemory(particle);
                    //fireCognitiveMemoryUpdated(particle, dimension, x);
                }
                if (fx > fs)
                {
                    swarm.updateSocialMemory(particle);
                    //fireSocialMemoryUpdated(particle, dimension, x);
                }
            }
            epoch++;
        }
        fireExitSucceeded(swarm, epoch);
        return swarm;
    }

    /**
     * Return the source of randomness for this particle swarm optimization algorithm.
     * The source of randomness will not be null.
     *
     * @return the source of randomness for this particle swarm optimization algorithm
     */
    public Random getRandom()
    {
        return random;
    }

    /**
     * Set the source of randomness for this particle swarm
     * optimization algorithm to <code>random</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param random source of randomness for this particle swarm
     *    optimization algorithm, must not be null
     */
    public void setRandom(final Random random)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        Random oldRandom = this.random;
        this.random = random;
        propertyChangeSupport.firePropertyChange("random", oldRandom, this.random);
    }

    /**
     * Return the inertia weight for this particle swarm optimization algorithm.
     * Defaults to <code>0.1d</code>.
     *
     * @see #DEFAULT_INERTIA_WEIGHT
     * @return the inertia weight for this particle swarm optimization algorithm
     */
    public double getInertiaWeight()
    {
        return inertiaWeight;
    }

    /**
     * Set the inertia weight for this particle swarm optimization algorithm to <code>inertiaWeight</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param inertiaWeight inertia weight for this particle swarm optimization algorithm
     */
    public void setInertiaWeight(final double inertiaWeight)
    {
        double oldInertiaWeight = this.inertiaWeight;
        this.inertiaWeight = inertiaWeight;
        propertyChangeSupport.firePropertyChange("inertiaWeight", oldInertiaWeight, this.inertiaWeight);
    }

    /**
     * Return the cognitive weight for this particle swarm optimization algorithm.
     * Defaults to <code>0.2d</code>.
     *
     * @see #DEFAULT_COGNITIVE_WEIGHT
     * @return the cognitive weight for this particle swarm optimization algorithm
     */
    public double getCognitiveWeight()
    {
        return cognitiveWeight;
    }

    /**
     * Set the cognitive weight for this particle swarm optimization algorithm to <code>cognitiveWeight</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param cognitiveWeight cognitive weight for this particle swarm optimization algorithm
     */
    public void setCognitiveWeight(final double cognitiveWeight)
    {
        double oldCognitiveWeight = this.cognitiveWeight;
        this.cognitiveWeight = cognitiveWeight;
        propertyChangeSupport.firePropertyChange("cognitiveWeight", oldCognitiveWeight, this.cognitiveWeight);
    }

    /**
     * Return the social weight for this particle swarm optimization algorithm.
     * Defaults to <code>0.2d</code>.
     *
     * @see #DEFAULT_SOCIAL_WEIGHT
     * @return the social weight for this particle swarm optimization algorithm
     */
    public double getSocialWeight()
    {
        return socialWeight;
    }

    /**
     * Set the social weight for this particle swarm optimization algorithm to <code>socialWeight</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param socialWeight social weight for this particle swarm optimization algorithm
     */
    public void setSocialWeight(final double socialWeight)
    {
        double oldSocialWeight = this.socialWeight;
        this.socialWeight = socialWeight;
        propertyChangeSupport.firePropertyChange("socialWeight", oldSocialWeight, this.socialWeight);
    }

    /**
     * Return the minimum position for this particle swarm optimization algorithm.
     * Defaults to <code>0.0d</code>.
     *
     * @see #DEFAULT_MINIMUM_POSITION
     * @return the minimum position for this particle swarm optimization algorithm
     */
    public double getMinimumPosition()
    {
        return minimumPosition;
    }

    /**
     * Set the minimum position for this particle swarm optimization algorithm to <code>minimumPosition</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param minimumPosition minimum position for this particle swarm optimization algorithm
     */
    public void setMinimumPosition(final double minimumPosition)
    {
        double oldMinimumPosition = this.minimumPosition;
        this.minimumPosition = minimumPosition;
        propertyChangeSupport.firePropertyChange("minimumPosition", oldMinimumPosition, this.minimumPosition);
    }

    /**
     * Return the maximum position for this particle swarm optimization algorithm.
     * Defaults to <code>1.0d</code>.
     *
     * @see #DEFAULT_MAXIMUM_POSITION
     * @return the maximum position for this particle swarm optimization algorithm
     */
    public double getMaximumPosition()
    {
        return maximumPosition;
    }

    /**
     * Set the maximum position for this particle swarm optimization algorithm to <code>maximumPosition</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param maximumPosition maximum position for this particle swarm optimization algorithm
     */
    public void setMaximumPosition(final double maximumPosition)
    {
        double oldMaximumPosition = this.maximumPosition;
        this.maximumPosition = maximumPosition;
        propertyChangeSupport.firePropertyChange("maximumPosition", oldMaximumPosition, this.maximumPosition);
    }

    /**
     * Return the minimum velocity for this particle swarm optimization algorithm.
     * Defaults to <code>-0.5d</code>.
     *
     * @see #DEFAULT_MINIMUM_VELOCITY
     * @return the minimum velocity for this particle swarm optimization algorithm
     */
    public double getMinimumVelocity()
    {
        return minimumVelocity;
    }

    /**
     * Set the minimum velocity for this particle swarm optimization algorithm to <code>minimumVelocity</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param minimumVelocity maximum velocity for this particle swarm optimization algorithm
     */
    public void setMinimumVelocity(final double minimumVelocity)
    {
        double oldMinimumVelocity = this.minimumVelocity;
        this.minimumVelocity = minimumVelocity;
        propertyChangeSupport.firePropertyChange("minimumVelocity", oldMinimumVelocity, this.minimumVelocity);
    }

    /**
     * Return the maximum velocity for this particle swarm optimization algorithm.
     * Defaults to <code>Double.MAX_VALUE</code>.
     *
     * @see #DEFAULT_MAXIMUM_VELOCITY
     * @return the maximum velocity for this particle swarm optimization algorithm
     */
    public double getMaximumVelocity()
    {
        return maximumVelocity;
    }

    /**
     * Set the maximum velocity for this particle swarm optimization algorithm to <code>maximumVelocity</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param maximumVelocity maximum velocity for this particle swarm optimization algorithm
     */
    public void setMaximumVelocity(final double maximumVelocity)
    {
        double oldMaximumVelocity = this.maximumVelocity;
        this.maximumVelocity = maximumVelocity;
        propertyChangeSupport.firePropertyChange("maximumVelocity", oldMaximumVelocity, this.maximumVelocity);
    }

    /**
     * Add the specified particle swarm optimization algorithm listener.
     *
     * @param l particle swarm optimization algorithm listener to add
     */
    public void addParticleSwarmOptimizationAlgorithmListener(final ParticleSwarmOptimizationAlgorithmListener l)
    {
        listenerList.add(ParticleSwarmOptimizationAlgorithmListener.class, l);
    }

    /**
     * Remove the specified particle swarm optimization algorithm listener.
     *
     * @param l particle swarm optimization algorithm listener to add
     */
    public void removeParticleSwarmOptimizationAlgorithmListener(final ParticleSwarmOptimizationAlgorithmListener l)
    {
        listenerList.remove(ParticleSwarmOptimizationAlgorithmListener.class, l);
    }

    /**
     * Return the number of particle swarm optimization algorithm listeners registered to this
     * particle swarm optimization algorithm.
     *
     * @return the number of particle swarm optimization algorithm listeners registered to this
     *    particle swarm optimization algorithm
     */
    public int getParticleSwarmOptimizationAlgorithmListenerCount()
    {
        return listenerList.getListenerCount(ParticleSwarmOptimizationAlgorithmListener.class);
    }

    /**
     * Return an array of particle swarm optimization algorithm listeners registered to this particle
     * swarm optimization algorithm.  The returned array may be empty but will
     * not be null.
     *
     * @return an array of particle swarm optimization algorithm listeners registered to this particle
     *    swarm optimization algorithm
     */
    public ParticleSwarmOptimizationAlgorithmListener[] getParticleSwarmOptimizationAlgorithmListeners()
    {
        return (ParticleSwarmOptimizationAlgorithmListener[])
            listenerList.getListeners(ParticleSwarmOptimizationAlgorithmListener.class);
    }

    /**
     * Add a property change listener to this particle swarm optimization algorithm.
     * The listener is registered for all properties.
     *
     * @param listener property change listener to add
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Add a property change listener for the specified property
     * to this particle swarm optimization algorithm.  The listener will be invoked only when a call
     * on firePropertyChange names that specific property.
     *
     * @param propertyName specific property name
     * @param listener property change listener to add
     */
    public void addPropertyChangeListener(final String propertyName,
                                          final PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a property change listener from this particle swarm optimization algorithm.
     * This removes a listener that was registered for all properties.
     *
     * @param listener property change listener to remove
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Remove a property change listener for the specified property from
     * this particle swarm optimization algorithm.
     *
     * @param propertyName specific property name
     * @param listener property change listener to remove
     */
    public void removePropertyChangeListener(final String propertyName,
                                             final PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Return the number of property change listeners registered to this
     * particle swarm optimization algorithm.
     *
     * @return the number of property change listeners registered to this
     *    particle swarm optimization algorithm
     */
    public int getPropertyChangeListenerCount()
    {
        return propertyChangeSupport.getPropertyChangeListeners().length;
    }

    /**
     * Return the number of property change listeners registered to this
     * particle swarm optimization algorithm for the specified property.
     *
     * @param propertyName property name
     * @return the number of property change listeners registered to this
     *    particle swarm optimization algorithm for the specified property
     */
    public int getPropertyChangeListenerCount(final String propertyName)
    {
        return propertyChangeSupport.getPropertyChangeListeners(propertyName).length;
    }

    /**
     * Return an array of property change listeners registered to this particle
     * swarm optimization algorithm.  The returned array may be empty but will
     * not be null.
     *
     * @return an array of property change listeners registered to this particle
     *    swarm optimization algorithm
     */
    public PropertyChangeListener[] getPropertyChangeListeners()
    {
        return propertyChangeSupport.getPropertyChangeListeners();
    }

    /**
     * Return an array of property change listeners registered to this particle
     * swarm optimization algorithm for the specified property.  The returned array
     * may be empty but will not be null.
     *
     * @param propertyName property name
     * @return an array of property change listeners registered to this particle
     *    swarm optimization algorithm
     */
    public PropertyChangeListener[] getPropertyChangeListeners(final String propertyName)
    {
        return propertyChangeSupport.getPropertyChangeListeners(propertyName);
    }

    /**
     * Fire an exit failed event to all registered listeners.
     *
     * @param swarm particle swarm, must not be null
     * @param epoch epoch
     */
    private void fireExitFailed(final ParticleSwarm swarm, final int epoch)
    {
        Object[] listeners = listenerList.getListenerList();
        ParticleSwarmOptimizationAlgorithmEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ParticleSwarmOptimizationAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new ParticleSwarmOptimizationAlgorithmEvent(this, swarm, epoch);
                }
                ((ParticleSwarmOptimizationAlgorithmListener) listeners[i + 1]).exitFailed(e);
            }
        }
    }

    /**
     * Fire an exit succeeded event to all registered listeners.
     *
     * @param swarm particle swarm, must not be null
     * @param epoch epoch
     */
    private void fireExitSucceeded(final ParticleSwarm swarm, final int epoch)
    {
        Object[] listeners = listenerList.getListenerList();
        ParticleSwarmOptimizationAlgorithmEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ParticleSwarmOptimizationAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new ParticleSwarmOptimizationAlgorithmEvent(this, swarm, epoch);
                }
                ((ParticleSwarmOptimizationAlgorithmListener) listeners[i + 1]).exitSucceeded(e);
            }
        }
    }

    /**
     * Fire a velocity calculated event to all registered listeners.
     *
     * @param particle particle
     * @param dimension dimension
     * @param velocity velocity
     */
    private void fireVelocityCalculated(final int particle, final int dimension, final double velocity)
    {
        Object[] listeners = listenerList.getListenerList();
        ParticleSwarmOptimizationAlgorithmEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ParticleSwarmOptimizationAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new ParticleSwarmOptimizationAlgorithmEvent(this, particle, dimension, velocity);
                }
                ((ParticleSwarmOptimizationAlgorithmListener) listeners[i + 1]).velocityCalculated(e);
            }
        }
    }

    /**
     * Fire a position updated event to all registered listeners.
     *
     * @param particle particle
     * @param dimension dimension
     * @param position position
     */
    private void firePositionUpdated(final int particle, final int dimension, final double position)
    {
        Object[] listeners = listenerList.getListenerList();
        ParticleSwarmOptimizationAlgorithmEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ParticleSwarmOptimizationAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new ParticleSwarmOptimizationAlgorithmEvent(this, particle, dimension, position);
                }
                ((ParticleSwarmOptimizationAlgorithmListener) listeners[i + 1]).positionUpdated(e);
            }
        }
    }

    /**
     * Fire a fitness calculated event to all registered listeners.
     *
     * @param particle particle
     * @param fitness fitness
     */
    private void fireFitnessCalculated(final int particle,
                                       final double fitness)
    {
        Object[] listeners = listenerList.getListenerList();
        ParticleSwarmOptimizationAlgorithmEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ParticleSwarmOptimizationAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new ParticleSwarmOptimizationAlgorithmEvent(this, particle, 0, 0.0d, fitness);
                }
                ((ParticleSwarmOptimizationAlgorithmListener) listeners[i + 1]).fitnessCalculated(e);
            }
        }
    }

    /**
     * Fire a cognitive memory updated event to all registered listeners.
     *
     * @param particle particle
     * @param dimension dimension
     * @param position position
     */
    private void fireCognitiveMemoryUpdated(final int particle, final int dimension, final double position)
    {
        Object[] listeners = listenerList.getListenerList();
        ParticleSwarmOptimizationAlgorithmEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ParticleSwarmOptimizationAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new ParticleSwarmOptimizationAlgorithmEvent(this, particle, dimension, position);
                }
                ((ParticleSwarmOptimizationAlgorithmListener) listeners[i + 1]).cognitiveMemoryUpdated(e);
            }
        }
    }

    /**
     * Fire a social memory updated event to all registered listeners.
     *
     * @param particle particle
     * @param dimension dimension
     * @param position position
     */
    private void fireSocialMemoryUpdated(final int particle, final int dimension, final double position)
    {
        Object[] listeners = listenerList.getListenerList();
        ParticleSwarmOptimizationAlgorithmEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ParticleSwarmOptimizationAlgorithmListener.class)
            {
                if (e == null)
                {
                    e = new ParticleSwarmOptimizationAlgorithmEvent(this, particle, dimension, position);
                }
                ((ParticleSwarmOptimizationAlgorithmListener) listeners[i + 1]).socialMemoryUpdated(e);
            }
        }
    }
}
