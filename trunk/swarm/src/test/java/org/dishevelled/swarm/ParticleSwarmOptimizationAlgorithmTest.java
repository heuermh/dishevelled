/*

    dsh-swarm  Simple framework for particle swarm optimization algorithms.
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

import junit.framework.TestCase;

import org.dishevelled.matrix.ObjectMatrix1D;
import org.dishevelled.matrix.ObjectMatrix2D;

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
        ParticleSwarmOptimizationAlgorithm psoa = new ParticleSwarmOptimizationAlgorithm();

        assertNotNull("psoa not null", psoa);
        assertNotNull("random not null", psoa.getRandom());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_COGNITIVE_WEIGHT, psoa.getCognitiveWeight());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_INERTIA_WEIGHT, psoa.getInertiaWeight());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MAXIMUM_POSITION, psoa.getMaximumPosition());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_MAXIMUM_VELOCITY, psoa.getMaximumVelocity());
        assertEquals(ParticleSwarmOptimizationAlgorithm.DEFAULT_SOCIAL_WEIGHT, psoa.getSocialWeight());
        assertEquals(0, psoa.getPropertyChangeListenerCount());
        assertEquals(0, psoa.getPropertyChangeListenerCount("random"));
        assertEquals(0, psoa.getPropertyChangeListenerCount("cognitiveWeight"));
        assertEquals(0, psoa.getPropertyChangeListenerCount("inertiaWeight"));
        assertEquals(0, psoa.getPropertyChangeListenerCount("maximumPosition"));
        assertEquals(0, psoa.getPropertyChangeListenerCount("maximumVelocity"));
        assertEquals(0, psoa.getPropertyChangeListenerCount("socialWeight"));
        assertNotNull(psoa.getPropertyChangeListeners());
        assertNotNull(psoa.getPropertyChangeListeners("random"));
        assertNotNull(psoa.getPropertyChangeListeners("cognitiveWeight"));
        assertNotNull(psoa.getPropertyChangeListeners("inertiaWeight"));
        assertNotNull(psoa.getPropertyChangeListeners("maximumPosition"));
        assertNotNull(psoa.getPropertyChangeListeners("maximumVelocity"));
        assertNotNull(psoa.getPropertyChangeListeners("socialWeight"));
        assertEquals(0, psoa.getPropertyChangeListeners().length);
        assertEquals(0, psoa.getPropertyChangeListeners("random").length);
        assertEquals(0, psoa.getPropertyChangeListeners("cognitiveWeight").length);
        assertEquals(0, psoa.getPropertyChangeListeners("inertiaWeight").length);
        assertEquals(0, psoa.getPropertyChangeListeners("maximumPosition").length);
        assertEquals(0, psoa.getPropertyChangeListeners("maximumVelocity").length);
        assertEquals(0, psoa.getPropertyChangeListeners("socialWeight").length);
    }

    public void testOptimize()
    {
        ParticleSwarmOptimizationAlgorithm psoa = new ParticleSwarmOptimizationAlgorithm();
        ExitStrategy exitStrategy = new ExitStrategy()
            {
                /** {@inheritDoc} */
                public boolean evaluate(final ObjectMatrix2D<Double> position,
                                        final ObjectMatrix2D<Double> velocity,
                                        final ObjectMatrix2D<Double> cognitiveMemory,
                                        final ObjectMatrix1D<Double> socialMemory,
                                        final int epoch)
                {
                    return false;
                }
            };
        Fitness fitness = new Fitness()
            {
                /** {@inheritDoc} */
                public double score(final double position)
                {
                    return position;
                }
            };

        try
        {
            psoa.optimize(0, 10, exitStrategy, fitness);
            fail("particles < 1 expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            psoa.optimize(10, 0, exitStrategy, fitness);
            fail("dimensions < 1 expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            psoa.optimize(10, 10, null, fitness);
            fail("optimize(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            psoa.optimize(10, 10, exitStrategy, null);
            fail("optimize(,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}