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

import org.dishevelled.matrix.ObjectMatrix1D;
import org.dishevelled.matrix.ObjectMatrix2D;

/**
 * A memento class holding the particle position, particle velocity,
 * cognitive memory, and social memory matrices for a particle swarm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class ParticleSwarm
{
    /** Particle position matrix. */
    private final ObjectMatrix2D<Double> position;

    /** Particle velocity matrix. */
    private final ObjectMatrix2D<Double> velocity;

    /** Cognitive memory matrix. */
    private final ObjectMatrix2D<Double> cognitiveMemory;

    /** Social memory matrix. */
    private final ObjectMatrix1D<Double> socialMemory;


    /**
     * Create a new particle swarm.
     *
     * @param position particle position matrix, must not be null
     * @param velocity particle velocity matrix, must not be null
     * @param cognitiveMemory cognitive memory matrix, must not be null
     * @param socialMemory social memory matrix, must not be null
     */
    ParticleSwarm(final ObjectMatrix2D<Double> position,
                  final ObjectMatrix2D<Double> velocity,
                  final ObjectMatrix2D<Double> cognitiveMemory,
                  final ObjectMatrix1D<Double> socialMemory)
    {
        if (position == null)
        {
            throw new IllegalArgumentException("position must not be null");
        }
        if (velocity == null)
        {
            throw new IllegalArgumentException("velocity must not be null");
        }
        if (cognitiveMemory == null)
        {
            throw new IllegalArgumentException("cognitiveMemory must not be null");
        }
        if (socialMemory == null)
        {
            throw new IllegalArgumentException("socialMemory must not be null");
        }
        this.position = position;
        this.velocity = velocity;
        this.cognitiveMemory = cognitiveMemory;
        this.socialMemory = socialMemory;
    }


    /**
     * Return the particle position matrix for this particle swarm.  The particle position
     * matrix will not be null.
     *
     * @return the particle position matrix for this particle swarm
     */
    public ObjectMatrix2D<Double> getPosition()
    {
        return position;
    }

    /**
     * Return the particle velocity matrix for this particle swarm.  The particle velocity
     * matrix will not be null.
     *
     * @return the particle velocity matrix for this particle swarm
     */
    public ObjectMatrix2D<Double> getVelocity()
    {
        return velocity;
    }

    /**
     * Return the cognitive memory matrix for this particle swarm.  The cognitive memory
     * matrix will not be null.
     *
     * @return the cognitive memory matrix for this particle swarm
     */
    public ObjectMatrix2D<Double> getCognitiveMemory()
    {
        return cognitiveMemory;
    }

    /**
     * Return the social memory matrix for this particle swarm.  The social memory
     * matrix will not be null.
     *
     * @return the social memory matrix for this particle swarm
     */
    public ObjectMatrix1D<Double> getSocialMemory()
    {
        return socialMemory;
    }

    // todo:
    //   - optimize returns ParticleSwarm
    //   - exit strategy --> evaluate(ParticleSwarm, epoch)
    //   - fitness --> score(ParticleSwarm, ...)
    //   - matrices should be unmodifiable, on access
}
