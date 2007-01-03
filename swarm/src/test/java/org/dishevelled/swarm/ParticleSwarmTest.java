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

import org.dishevelled.matrix.impl.SparseObjectMatrix1D;
import org.dishevelled.matrix.impl.SparseObjectMatrix2D;

/**
 * Unit test for ParticleSwarm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ParticleSwarmTest
    extends TestCase
{

    public void testConstructor()
    {
        int particles = 100;
        int dimensions = 10;
        ObjectMatrix2D<Double> position = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> velocity = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> cognitiveMemory = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix1D<Double> socialMemory = new SparseObjectMatrix1D<Double>(dimensions);
        ParticleSwarm swarm0 = new ParticleSwarm(position, velocity, cognitiveMemory, socialMemory);

        try
        {
            ParticleSwarm swarm = new ParticleSwarm(null, velocity, cognitiveMemory, socialMemory);
            fail("ctr(null,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ParticleSwarm swarm = new ParticleSwarm(position, null, cognitiveMemory, socialMemory);
            fail("ctr(,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ParticleSwarm swarm = new ParticleSwarm(position, velocity, null, socialMemory);
            fail("ctr(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ParticleSwarm swarm = new ParticleSwarm(position, velocity, cognitiveMemory, null);
            fail("ctr(,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testPosition()
    {
        int particles = 100;
        int dimensions = 10;
        ObjectMatrix2D<Double> position = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> velocity = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> cognitiveMemory = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix1D<Double> socialMemory = new SparseObjectMatrix1D<Double>(dimensions);
        ParticleSwarm swarm = new ParticleSwarm(position, velocity, cognitiveMemory, socialMemory);
        assertNotNull("swarm not null", swarm);
        assertNotNull("position not null", swarm.getPosition());
        assertEquals(particles, swarm.getPosition().rows());
        assertEquals(dimensions, swarm.getPosition().columns());
    }

    public void testVelocity()
    {
        int particles = 100;
        int dimensions = 10;
        ObjectMatrix2D<Double> position = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> velocity = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> cognitiveMemory = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix1D<Double> socialMemory = new SparseObjectMatrix1D<Double>(dimensions);
        ParticleSwarm swarm = new ParticleSwarm(position, velocity, cognitiveMemory, socialMemory);
        assertNotNull("swarm not null", swarm);
        assertNotNull("velocity not null", swarm.getVelocity());
        assertEquals(particles, swarm.getVelocity().rows());
        assertEquals(dimensions, swarm.getVelocity().columns());
    }

    public void testCognitiveMemory()
    {
        int particles = 100;
        int dimensions = 10;
        ObjectMatrix2D<Double> position = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> velocity = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> cognitiveMemory = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix1D<Double> socialMemory = new SparseObjectMatrix1D<Double>(dimensions);
        ParticleSwarm swarm = new ParticleSwarm(position, velocity, cognitiveMemory, socialMemory);
        assertNotNull("swarm not null", swarm);
        assertNotNull("cognitiveMemory not null", swarm.getCognitiveMemory());
        assertEquals(particles, swarm.getCognitiveMemory().rows());
        assertEquals(dimensions, swarm.getCognitiveMemory().columns());
    }

    public void testSocialMemory()
    {
        int particles = 100;
        int dimensions = 10;
        ObjectMatrix2D<Double> position = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> velocity = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix2D<Double> cognitiveMemory = new SparseObjectMatrix2D<Double>(particles, dimensions);
        ObjectMatrix1D<Double> socialMemory = new SparseObjectMatrix1D<Double>(dimensions);
        ParticleSwarm swarm = new ParticleSwarm(position, velocity, cognitiveMemory, socialMemory);
        assertNotNull("swarm not null", swarm);
        assertNotNull("socialMemory not null", swarm.getPosition());
        assertEquals(dimensions, swarm.getSocialMemory().size());
    }
}
