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
package org.dishevelled.swarm.fitness;

import org.dishevelled.swarm.Fitness;
import org.dishevelled.swarm.AbstractFitnessTest;

/**
 * Unit test for UniformFitness.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UniformFitnessTest
    extends AbstractFitnessTest
{

    /** {@inheritDoc} */
    protected Fitness createFitness()
    {
        return new UniformFitness();
    }

    public void testConstructor()
    {
        UniformFitness uniformFitness0 = new UniformFitness();
        UniformFitness uniformFitness1 = new UniformFitness(0.0d);
        UniformFitness uniformFitness2 = new UniformFitness(1.0d);
        UniformFitness uniformFitness3 = new UniformFitness(-1.0d);
        UniformFitness uniformFitness4 = new UniformFitness(Double.MIN_VALUE);
        UniformFitness uniformFitness5 = new UniformFitness(Double.MAX_VALUE);
    }

    public void testUniformFitness()
    {
        UniformFitness uniformFitness0 = new UniformFitness();
        assertNotNull("uniformFitness0 not null", uniformFitness0);
        assertEquals(UniformFitness.DEFAULT_SCORE, uniformFitness0.score(0.0d));
        assertEquals(UniformFitness.DEFAULT_SCORE, uniformFitness0.score(1.0d));
        assertEquals(UniformFitness.DEFAULT_SCORE, uniformFitness0.score(-1.0d));
        assertEquals(UniformFitness.DEFAULT_SCORE, uniformFitness0.score(Double.MIN_VALUE));
        assertEquals(UniformFitness.DEFAULT_SCORE, uniformFitness0.score(Double.MAX_VALUE));

        UniformFitness uniformFitness1 = new UniformFitness(1.0d);
        assertNotNull("uniformFitness1 not null", uniformFitness1);
        assertEquals(1.0d, uniformFitness1.score(0.0d));
        assertEquals(1.0d, uniformFitness1.score(1.0d));
        assertEquals(1.0d, uniformFitness1.score(-1.0d));
        assertEquals(1.0d, uniformFitness1.score(Double.MIN_VALUE));
        assertEquals(1.0d, uniformFitness1.score(Double.MAX_VALUE));
    }
}