/*

    dsh-swarm  Framework for particle swarm optimization algorithms.
    Copyright (c) 2006-2010 held jointly by the individual authors.

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
 * Particle in a particle swarm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Particle
{

    /**
     * Return the number of dimensions for this particle.
     *
     * @return the number of dimensions for this particle
     */
    int getDimensions();

    /**
     * Return the fitness for this particle.
     *
     * @return the fitness for this particle
     */
    double getFitness();

    /**
     * Return a view of the position matrix for this particle.  The length of
     * the array will be equal to the number of dimensions for this particle.
     *
     * @return a view of the position matrix for this particle
     */
    double[] getPosition();

    /**
     * Return a view of the velocity matrix for this particle.  The length of
     * the array will be equal to the number of dimensions for this particle.
     *
     * @return a view of the velocity matrix for this particle
     */
    double[] getVelocity();

    /**
     * Return a view of the cognitive memory matrix for this particle.  The length
     * of the array will be equal to the number of dimensions for this particle.
     *
     * @return a view of the cognitive memory matrix for this particle
     */
    double[] getCognitiveMemory();
}