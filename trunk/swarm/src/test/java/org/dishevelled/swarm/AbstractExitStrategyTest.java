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

import org.dishevelled.matrix.ObjectMatrix1D;
import org.dishevelled.matrix.ObjectMatrix2D;

import org.dishevelled.matrix.impl.SparseObjectMatrix1D;
import org.dishevelled.matrix.impl.SparseObjectMatrix2D;

/**
 * Abstract unit test for implementations of ExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractExitStrategyTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implemenation of ExitStrategy to test.
     *
     * @return a new instance of an implementation of ExitStrategy to test
     */
    protected abstract ExitStrategy createExitStrategy();

    public void testExitStrategy()
    {
        ObjectMatrix2D<Double> position = new SparseObjectMatrix2D<Double>(0, 0);
        ObjectMatrix2D<Double> velocity = new SparseObjectMatrix2D<Double>(0, 0);
        ObjectMatrix2D<Double> cognitiveMemory = new SparseObjectMatrix2D<Double>(0, 0);
        ObjectMatrix1D<Double> socialMemory = new SparseObjectMatrix1D<Double>(0);
        int epoch = 0;
        ExitStrategy exitStrategy = createExitStrategy();
        boolean evaluation = exitStrategy.evaluate(position, velocity, cognitiveMemory, socialMemory, epoch);
    }
}