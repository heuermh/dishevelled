/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2012 held jointly by the individual authors.

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

/**
 * Particle swarm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ParticleSwarm
    extends Iterable<Particle>
{

    /**
     * Return the number of particles in this particle swarm.
     *
     * @return the number of particles in this particle swarm
     */
    int getParticles();

    /**
     * Return the number of dimensions for this particle swarm.
     *
     * @return the number of dimensions for this particle swarm
     */
    int getDimensions();

    /**
     * Return a view of the social memory matrix for this particle swarm.  The length
     * of the array will be equal to the number of dimensions for this particle
     * swarm.
     *
     * @return a view of the social memory matrix for this particle swarm
     */
    double[] getSocialMemory();

    // from Iterable:
    //    Iterator<Particle> iterator();
}