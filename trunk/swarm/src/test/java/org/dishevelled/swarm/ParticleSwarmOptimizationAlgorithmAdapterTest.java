/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2008 held jointly by the individual authors.

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
 * Unit test for ParticleSwarmOptimizationAlgorithmAdapter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ParticleSwarmOptimizationAlgorithmAdapterTest
    extends TestCase
{

    public void testParticleSwarmOptimizationAlgorithmAdapter()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = new ParticleSwarmOptimizationAlgorithm();
        ParticleSwarmOptimizationAlgorithmEvent event = new ParticleSwarmOptimizationAlgorithmEvent(algorithm, 0, 0, 0.0d, 0.0d);
        ParticleSwarmOptimizationAlgorithmListener listener = new ParticleSwarmOptimizationAlgorithmAdapter();
        listener.exitFailed(event);
        listener.exitSucceeded(event);
        listener.velocityCalculated(event);
        listener.positionUpdated(event);
        listener.fitnessCalculated(event);
        listener.cognitiveMemoryUpdated(event);
        listener.socialMemoryUpdated(event);
    }
}