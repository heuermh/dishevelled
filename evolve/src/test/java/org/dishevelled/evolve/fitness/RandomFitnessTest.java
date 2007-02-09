/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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
package org.dishevelled.evolve.fitness;

import java.util.Random;

import junit.framework.TestCase;

import org.dishevelled.evolve.Fitness;

/**
 * Unit test for RandomFitness.
 *
 * @author  Michael Heuer
 * @version $Revision: 225 $ $Date: 2007-01-08 23:25:51 -0600 (Mon, 08 Jan 2007) $
 */
public final class RandomFitnessTest
    extends TestCase
{

    public void testConstructor()
    {
        Fitness<Integer> fitness0 = new RandomFitness<Integer>();
        Fitness<Integer> fitness1 = new RandomFitness<Integer>(new Random());

        try
        {
            Fitness<Integer> fitness = new RandomFitness<Integer>(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testRandomFitness()
    {
        Fitness<Integer> fitness = new RandomFitness<Integer>();
        double score = fitness.score(Integer.valueOf(1));
        assertTrue(score >= 0.0d);
        assertTrue(score < 1.0d);
    }
}
