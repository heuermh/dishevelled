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
package org.dishevelled.swarm;

import junit.framework.TestCase;

/**
 * Abstract unit test for implemenations of Fitness.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractFitnessTest
    extends TestCase
{
    //protected static final double[] EMPTY_ARRAY = new double[0];
    protected static final double[] ZERO = new double[] { 0.0d };
    protected static final double[] ONE = new double[] { 1.0d };
    protected static final double[] NEGATIVE_ONE = new double[] { -1.0d };
    protected static final double[] MIN_VALUE = new double[] { Double.MIN_VALUE };
    protected static final double[] MAX_VALUE = new double[] { Double.MAX_VALUE };


    /**
     * Create and return a new instance of an implemenation of Fitness to test.
     *
     * @return a new instance of an implementation of Fitness to test
     */
    protected abstract Fitness createFitness();

    public void testFitness()
    {
        Fitness fitness = createFitness();
        assertNotNull("fitness not null", fitness);
        double score0 = fitness.score(ZERO);
        double score1 = fitness.score(ONE);
        double score2 = fitness.score(NEGATIVE_ONE);
        double score3 = fitness.score(MIN_VALUE);
        double score4 = fitness.score(MAX_VALUE);
    }
}