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

import junit.framework.TestCase;

/**
 * Unit test for ParticleSwarmOptimizationAlgorithmEvent.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ParticleSwarmOptimizationAlgorithmEventTest
    extends TestCase
{

    public void testConstructor()
    {
        ParticleSwarmOptimizationAlgorithm source = new ParticleSwarmOptimizationAlgorithm();
        ParticleSwarm swarm = new TestParticleSwarm();

        ParticleSwarmOptimizationAlgorithmEvent event0 = new ParticleSwarmOptimizationAlgorithmEvent(source, swarm, 0);
        ParticleSwarmOptimizationAlgorithmEvent event2 = new ParticleSwarmOptimizationAlgorithmEvent(source, swarm, 1);
        ParticleSwarmOptimizationAlgorithmEvent event3 = new ParticleSwarmOptimizationAlgorithmEvent(source, swarm, -1);
        ParticleSwarmOptimizationAlgorithmEvent event4 = new ParticleSwarmOptimizationAlgorithmEvent(source, swarm, Integer.MIN_VALUE);
        ParticleSwarmOptimizationAlgorithmEvent event5 = new ParticleSwarmOptimizationAlgorithmEvent(source, swarm, Integer.MAX_VALUE);

        ParticleSwarmOptimizationAlgorithmEvent event10 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event11 = new ParticleSwarmOptimizationAlgorithmEvent(source, 1, 0, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event12 = new ParticleSwarmOptimizationAlgorithmEvent(source, -1, 0, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event13 = new ParticleSwarmOptimizationAlgorithmEvent(source, Integer.MAX_VALUE, 0, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event14 = new ParticleSwarmOptimizationAlgorithmEvent(source, Integer.MIN_VALUE, 0, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event15 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 1, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event16 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, -1, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event17 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, Integer.MAX_VALUE, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event18 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, Integer.MIN_VALUE, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event19 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 1.0d);
        ParticleSwarmOptimizationAlgorithmEvent event20 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, -1.0d);
        ParticleSwarmOptimizationAlgorithmEvent event21 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, Double.MAX_VALUE);
        ParticleSwarmOptimizationAlgorithmEvent event22 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, Double.MIN_VALUE);

        ParticleSwarmOptimizationAlgorithmEvent event23 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event24 = new ParticleSwarmOptimizationAlgorithmEvent(source, 1, 0, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event25 = new ParticleSwarmOptimizationAlgorithmEvent(source, -1, 0, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event26 = new ParticleSwarmOptimizationAlgorithmEvent(source, Integer.MAX_VALUE, 0, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event27 = new ParticleSwarmOptimizationAlgorithmEvent(source, Integer.MIN_VALUE, 0, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event28 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 1, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event29 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, -1, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event30 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, Integer.MAX_VALUE, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event31 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, Integer.MIN_VALUE, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event32 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 1.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event33 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, -1.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event34 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, Double.MAX_VALUE, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event35 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, Double.MIN_VALUE, 0.0d);
        ParticleSwarmOptimizationAlgorithmEvent event36 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 0.0d, 1.0d);
        ParticleSwarmOptimizationAlgorithmEvent event37 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 0.0d, -1.0d);
        ParticleSwarmOptimizationAlgorithmEvent event38 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 0.0d, Double.MAX_VALUE);
        ParticleSwarmOptimizationAlgorithmEvent event39 = new ParticleSwarmOptimizationAlgorithmEvent(source, 0, 0, 0.0d, Double.MIN_VALUE);

        try
        {
            ParticleSwarmOptimizationAlgorithmEvent event = new ParticleSwarmOptimizationAlgorithmEvent(null, swarm, 0);
            fail("ctr(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ParticleSwarmOptimizationAlgorithmEvent event = new ParticleSwarmOptimizationAlgorithmEvent(null, 0, 0, 0.0d);
            fail("ctr(null,,, 0.0d) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ParticleSwarmOptimizationAlgorithmEvent event = new ParticleSwarmOptimizationAlgorithmEvent(null, 0, 0, 0.0d, 0.0d);
            fail("ctr(null,, 0.0d, 0.0d) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testParticleSwarmOptimizationAlgorithmEvent()
    {
        ParticleSwarmOptimizationAlgorithm source = new ParticleSwarmOptimizationAlgorithm();
        ParticleSwarm swarm = new TestParticleSwarm();

        ParticleSwarmOptimizationAlgorithmEvent event0 = new ParticleSwarmOptimizationAlgorithmEvent(source, swarm, 0);
        assertNotNull("event0 not null", event0);
        assertEquals(source, event0.getSource());
        assertEquals(source, event0.getParticleSwarmOptimizationAlgorithm());
        assertEquals(swarm, event0.getParticleSwarm());
        assertEquals(0, event0.getEpoch());
        assertEquals(ParticleSwarmOptimizationAlgorithmEvent.DEFAULT_PARTICLE, event0.getParticle());
        assertEquals(ParticleSwarmOptimizationAlgorithmEvent.DEFAULT_DIMENSION, event0.getDimension());
        assertEquals(ParticleSwarmOptimizationAlgorithmEvent.DEFAULT_VALUE, event0.getValue());
        assertEquals(ParticleSwarmOptimizationAlgorithmEvent.DEFAULT_FITNESS, event0.getFitness());

        ParticleSwarmOptimizationAlgorithmEvent event1 = new ParticleSwarmOptimizationAlgorithmEvent(source, 1, 2, 3.0d);
        assertNotNull("event1 not null", event1);
        assertEquals(source, event1.getSource());
        assertEquals(source, event1.getParticleSwarmOptimizationAlgorithm());
        assertEquals(null, event1.getParticleSwarm());
        assertEquals(ParticleSwarmOptimizationAlgorithmEvent.DEFAULT_EPOCH, event1.getEpoch());
        assertEquals(1, event1.getParticle());
        assertEquals(2, event1.getDimension());
        assertEquals(3.0d, event1.getValue());        
        assertEquals(ParticleSwarmOptimizationAlgorithmEvent.DEFAULT_FITNESS, event1.getFitness());

        ParticleSwarmOptimizationAlgorithmEvent event2 = new ParticleSwarmOptimizationAlgorithmEvent(source, 1, 2, 3.0d, 4.0d);
        assertNotNull("event2 not null", event2);
        assertEquals(source, event2.getSource());
        assertEquals(source, event2.getParticleSwarmOptimizationAlgorithm());
        assertEquals(null, event2.getParticleSwarm());
        assertEquals(ParticleSwarmOptimizationAlgorithmEvent.DEFAULT_EPOCH, event2.getEpoch());
        assertEquals(1, event2.getParticle());
        assertEquals(2, event2.getDimension());
        assertEquals(3.0d, event2.getValue());        
        assertEquals(4.0d, event2.getFitness());
    }
}