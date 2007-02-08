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
package org.dishevelled.swarm.exit;

import org.dishevelled.matrix.ObjectMatrix1D;
import org.dishevelled.matrix.ObjectMatrix2D;

import org.dishevelled.matrix.impl.SparseObjectMatrix1D;
import org.dishevelled.matrix.impl.SparseObjectMatrix2D;

import org.dishevelled.swarm.AbstractExitStrategyTest;
import org.dishevelled.swarm.ExitStrategy;

/**
 * Unit test for EpochLimitExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EpochLimitExitStrategyTest
    extends AbstractExitStrategyTest
{

    /** {@inheritDoc} */
    protected ExitStrategy createExitStrategy()
    {
        return new EpochLimitExitStrategy(100);
    }

    public void testConstructor()
    {
        EpochLimitExitStrategy epochLimitExitStrategy0 = new EpochLimitExitStrategy(0);
        EpochLimitExitStrategy epochLimitExitStrategy1 = new EpochLimitExitStrategy(1);
        EpochLimitExitStrategy epochLimitExitStrategy2 = new EpochLimitExitStrategy(Integer.MAX_VALUE);

        try
        {
            EpochLimitExitStrategy epochLimitExitStrategy = new EpochLimitExitStrategy(-1);
            fail("ctr(-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            EpochLimitExitStrategy epochLimitExitStrategy = new EpochLimitExitStrategy(Integer.MIN_VALUE);
            fail("ctr(Integer.MIN_VALUE) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testEpochLimitExitStrategy()
    {
        ObjectMatrix2D<Double> position = new SparseObjectMatrix2D<Double>(0, 0);
        ObjectMatrix2D<Double> velocity = new SparseObjectMatrix2D<Double>(0, 0);
        ObjectMatrix2D<Double> cognitiveMemory = new SparseObjectMatrix2D<Double>(0, 0);
        ObjectMatrix1D<Double> socialMemory = new SparseObjectMatrix1D<Double>(0);

        EpochLimitExitStrategy epochLimitExitStrategy0 = new EpochLimitExitStrategy(0);
        assertTrue(epochLimitExitStrategy0.evaluate(position, velocity, cognitiveMemory, socialMemory, 0));
        assertTrue(epochLimitExitStrategy0.evaluate(position, velocity, cognitiveMemory, socialMemory, 1));
        assertTrue(epochLimitExitStrategy0.evaluate(position, velocity, cognitiveMemory, socialMemory, Integer.MAX_VALUE));

        EpochLimitExitStrategy epochLimitExitStrategy1 = new EpochLimitExitStrategy(1);
        assertFalse(epochLimitExitStrategy1.evaluate(position, velocity, cognitiveMemory, socialMemory, 0));
        assertTrue(epochLimitExitStrategy1.evaluate(position, velocity, cognitiveMemory, socialMemory, 1));
        assertTrue(epochLimitExitStrategy1.evaluate(position, velocity, cognitiveMemory, socialMemory, Integer.MAX_VALUE));

        EpochLimitExitStrategy epochLimitExitStrategy2 = new EpochLimitExitStrategy(100);
        assertFalse(epochLimitExitStrategy2.evaluate(position, velocity, cognitiveMemory, socialMemory, 0));
        assertFalse(epochLimitExitStrategy2.evaluate(position, velocity, cognitiveMemory, socialMemory, 1));
        assertFalse(epochLimitExitStrategy2.evaluate(position, velocity, cognitiveMemory, socialMemory, 99));
        assertTrue(epochLimitExitStrategy2.evaluate(position, velocity, cognitiveMemory, socialMemory, 100));
        assertTrue(epochLimitExitStrategy2.evaluate(position, velocity, cognitiveMemory, socialMemory, Integer.MAX_VALUE));

        EpochLimitExitStrategy epochLimitExitStrategy3 = new EpochLimitExitStrategy(Integer.MAX_VALUE);
        assertFalse(epochLimitExitStrategy3.evaluate(position, velocity, cognitiveMemory, socialMemory, 0));
        assertFalse(epochLimitExitStrategy3.evaluate(position, velocity, cognitiveMemory, socialMemory, 1));
        assertFalse(epochLimitExitStrategy3.evaluate(position, velocity, cognitiveMemory, socialMemory, 99));
        assertFalse(epochLimitExitStrategy3.evaluate(position, velocity, cognitiveMemory, socialMemory, 100));
        assertTrue(epochLimitExitStrategy3.evaluate(position, velocity, cognitiveMemory, socialMemory, Integer.MAX_VALUE));
    }
}