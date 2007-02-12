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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.EventListenerProxy;
import java.util.Random;

import junit.framework.TestCase;

// todo:  circular package dependency!
import org.dishevelled.swarm.exit.EpochLimitExitStrategy;

// todo:  circular package dependency!
import org.dishevelled.swarm.fitness.RandomFitness;

/**
 * Unit test for ParticleSwarmOptimizationAlgorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ParticleSwarmOptimizationAlgorithmTest
    extends TestCase
{

    public void testConstructor()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertNotNull("algorithm not null", algorithm);
    }

    public void testParticleSwarmOptimizationAlgorithm()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertNotNull("random not null", algorithm.getRandom());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_COGNITIVE_WEIGHT, algorithm.getCognitiveWeight());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_INERTIA_WEIGHT, algorithm.getInertiaWeight());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MINIMUM_POSITION, algorithm.getMinimumPosition());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MAXIMUM_POSITION, algorithm.getMaximumPosition());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MINIMUM_VELOCITY, algorithm.getMinimumVelocity());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MAXIMUM_VELOCITY, algorithm.getMaximumVelocity());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_SOCIAL_WEIGHT, algorithm.getSocialWeight());
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        assertEquals(0, algorithm.getParticleSwarmOptimizationAlgorithmListenerCount());
        assertEquals(0, algorithm.getPropertyChangeListenerCount("random"));
        assertEquals(0, algorithm.getPropertyChangeListenerCount("cognitiveWeight"));
        assertEquals(0, algorithm.getPropertyChangeListenerCount("inertiaWeight"));
        assertEquals(0, algorithm.getPropertyChangeListenerCount("minimumPosition"));
        assertEquals(0, algorithm.getPropertyChangeListenerCount("maximumPosition"));
        assertEquals(0, algorithm.getPropertyChangeListenerCount("minimumVelocity"));
        assertEquals(0, algorithm.getPropertyChangeListenerCount("maximumVelocity"));
        assertEquals(0, algorithm.getPropertyChangeListenerCount("socialWeight"));
        assertNotNull(algorithm.getPropertyChangeListeners());
        assertNotNull(algorithm.getParticleSwarmOptimizationAlgorithmListeners());
        assertNotNull(algorithm.getPropertyChangeListeners("random"));
        assertNotNull(algorithm.getPropertyChangeListeners("cognitiveWeight"));
        assertNotNull(algorithm.getPropertyChangeListeners("inertiaWeight"));
        assertNotNull(algorithm.getPropertyChangeListeners("minimumPosition"));
        assertNotNull(algorithm.getPropertyChangeListeners("maximumPosition"));
        assertNotNull(algorithm.getPropertyChangeListeners("minimumVelocity"));
        assertNotNull(algorithm.getPropertyChangeListeners("maximumVelocity"));
        assertNotNull(algorithm.getPropertyChangeListeners("socialWeight"));
        assertEquals(0, algorithm.getPropertyChangeListeners().length);
        assertEquals(0, algorithm.getParticleSwarmOptimizationAlgorithmListeners().length);
        assertEquals(0, algorithm.getPropertyChangeListeners("random").length);
        assertEquals(0, algorithm.getPropertyChangeListeners("cognitiveWeight").length);
        assertEquals(0, algorithm.getPropertyChangeListeners("inertiaWeight").length);
        assertEquals(0, algorithm.getPropertyChangeListeners("minimumPosition").length);
        assertEquals(0, algorithm.getPropertyChangeListeners("maximumPosition").length);
        assertEquals(0, algorithm.getPropertyChangeListeners("minimumVelocity").length);
        assertEquals(0, algorithm.getPropertyChangeListeners("maximumVelocity").length);
        assertEquals(0, algorithm.getPropertyChangeListeners("socialWeight").length);
    }

    public void testRandom()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertNotNull("random not null", algorithm.getRandom());
        Random random = new Random();
        algorithm.setRandom(random);
        assertEquals(random, algorithm.getRandom());

        try
        {
            algorithm.setRandom(null);
            fail("setRandom(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCognitiveWeight()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_COGNITIVE_WEIGHT, algorithm.getCognitiveWeight());
        algorithm.setCognitiveWeight(1.0d);
        assertEquals(1.0d, algorithm.getCognitiveWeight());
    }

    public void testInertiaWeight()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_INERTIA_WEIGHT, algorithm.getInertiaWeight());
        algorithm.setInertiaWeight(1.0d);
        assertEquals(1.0d, algorithm.getInertiaWeight());
    }

    public void testMinimumPosition()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MINIMUM_POSITION, algorithm.getMinimumPosition());
        algorithm.setMinimumPosition(-1.0d);
        assertEquals(-1.0d, algorithm.getMinimumPosition());
    }

    public void testMaximumPosition()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MAXIMUM_POSITION, algorithm.getMaximumPosition());
        algorithm.setMaximumPosition(1.0d);
        assertEquals(1.0d, algorithm.getMaximumPosition());
    }

    public void testMinimumVelocity()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MINIMUM_VELOCITY, algorithm.getMinimumVelocity());
        algorithm.setMinimumVelocity(-1.0d);
        assertEquals(-1.0d, algorithm.getMinimumVelocity());
    }

    public void testMaximumVelocity()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MAXIMUM_VELOCITY, algorithm.getMaximumVelocity());
        algorithm.setMaximumVelocity(1.0d);
        assertEquals(1.0d, algorithm.getMaximumVelocity());
    }

    public void testSocialWeight()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_SOCIAL_WEIGHT, algorithm.getSocialWeight());
        algorithm.setSocialWeight(1.0d);
        assertEquals(1.0d, algorithm.getSocialWeight());
    }

    public void testPropertyChangeListeners()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        PropertyChangeListener listener = new PropertyChangeListener()
            {
                /** {@inheritDoc} */
                public void propertyChange(final PropertyChangeEvent event)
                {
                    // empty
                }
            };
        algorithm.addPropertyChangeListener(null);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        algorithm.removePropertyChangeListener(null);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        algorithm.addPropertyChangeListener(listener);
        assertEquals(1, algorithm.getPropertyChangeListenerCount());
        assertEquals(listener, algorithm.getPropertyChangeListeners()[0]);
        algorithm.removePropertyChangeListener(listener);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        algorithm.removePropertyChangeListener(listener);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        algorithm.addPropertyChangeListener("socialWeight", null);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        algorithm.removePropertyChangeListener("socialWeight", null);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        algorithm.addPropertyChangeListener("socialWeight", listener);
        assertEquals(1, algorithm.getPropertyChangeListenerCount());
        assertEquals(listener, ((EventListenerProxy) algorithm.getPropertyChangeListeners()[0]).getListener());
        assertEquals(1, algorithm.getPropertyChangeListenerCount("socialWeight"));
        assertEquals(listener, algorithm.getPropertyChangeListeners("socialWeight")[0]);
        algorithm.removePropertyChangeListener("socialWeight", listener);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        assertEquals(0, algorithm.getPropertyChangeListenerCount("socialWeight"));
        algorithm.removePropertyChangeListener("socialWeight", listener);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
        assertEquals(0, algorithm.getPropertyChangeListenerCount("socialWeight"));
        algorithm.removePropertyChangeListener(null, listener);
        algorithm.removePropertyChangeListener("invalidPropertyName", null);
        algorithm.removePropertyChangeListener("invalidPropertyName", listener);
        assertEquals(0, algorithm.getPropertyChangeListenerCount());
    }

    public void testParticleSwarmOptimizationAlgorithmListeners()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        ParticleSwarmOptimizationAlgorithmListener listener = new ParticleSwarmOptimizationAlgorithmAdapter();
        assertEquals(0, algorithm.getParticleSwarmOptimizationAlgorithmListenerCount());
        algorithm.addParticleSwarmOptimizationAlgorithmListener(null);
        assertEquals(0, algorithm.getParticleSwarmOptimizationAlgorithmListenerCount());
        algorithm.removeParticleSwarmOptimizationAlgorithmListener(null);
        assertEquals(0, algorithm.getParticleSwarmOptimizationAlgorithmListenerCount());
        algorithm.addParticleSwarmOptimizationAlgorithmListener(listener);
        assertEquals(1, algorithm.getParticleSwarmOptimizationAlgorithmListenerCount());
        assertEquals(listener, algorithm.getParticleSwarmOptimizationAlgorithmListeners()[0]);
        algorithm.removeParticleSwarmOptimizationAlgorithmListener(listener);
        assertEquals(0, algorithm.getParticleSwarmOptimizationAlgorithmListenerCount());
        algorithm.removeParticleSwarmOptimizationAlgorithmListener(listener);
        assertEquals(0, algorithm.getParticleSwarmOptimizationAlgorithmListenerCount());
    }

    public void testOptimize()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        ExitStrategy exitStrategy = new EpochLimitExitStrategy(10);
        Fitness fitness = new RandomFitness();
        ParticleSwarmOptimizationAlgorithmListener listener = new ParticleSwarmOptimizationAlgorithmAdapter();
        algorithm.addParticleSwarmOptimizationAlgorithmListener(listener);

        ParticleSwarm swarm = algorithm.optimize(100, 10, exitStrategy, fitness);
        assertNotNull("swarm not null");
        assertEquals(100, swarm.getParticles());
        assertEquals(10, swarm.getDimensions());
        for (Particle particle : swarm)
        {
            assertNotNull("particle not null", particle);
            assertEquals(10, particle.getDimensions());
            // todo:
            //    what might reasonable assertions here be?
            particle.getFitness();
            particle.getPosition();
            particle.getVelocity();
            particle.getCognitiveMemory();
        }

        algorithm.removeParticleSwarmOptimizationAlgorithmListener(listener);

        try
        {
            algorithm.optimize(0, 10, exitStrategy, fitness);
            fail("particles < 1 expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            algorithm.optimize(10, 0, exitStrategy, fitness);
            fail("dimensions < 1 expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            algorithm.optimize(10, 10, null, fitness);
            fail("optimize(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            algorithm.optimize(10, 10, exitStrategy, null);
            fail("optimize(,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
