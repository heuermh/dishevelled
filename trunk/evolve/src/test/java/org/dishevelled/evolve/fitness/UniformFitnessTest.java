/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2013 held jointly by the individual authors.

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
package org.dishevelled.evolve.fitness;

import org.dishevelled.evolve.Fitness;
import org.dishevelled.evolve.AbstractFitnessTest;

/**
 * Unit test for UniformFitness.
 *
 * @author  Michael Heuer
 */
public final class UniformFitnessTest
    extends AbstractFitnessTest
{

    /** {@inheritDoc} */
    protected <T> Fitness<T> createFitness()
    {
        return new UniformFitness<T>();
    }

    public void testConstructor()
    {
        Fitness<Integer> fitness0 = new UniformFitness<Integer>(2.0d);
        assertEquals(Double.valueOf(2.0d), fitness0.score(Integer.valueOf(0)));

        Fitness<Integer> fitness1 = new UniformFitness<Integer>(-2.0d);
        assertEquals(Double.valueOf(-2.0d), fitness1.score(Integer.valueOf(0)));

        Fitness<Integer> fitness2 = new UniformFitness<Integer>(0.0d);
        assertEquals(Double.valueOf(0.0d), fitness2.score(Integer.valueOf(0)));
    }
}
