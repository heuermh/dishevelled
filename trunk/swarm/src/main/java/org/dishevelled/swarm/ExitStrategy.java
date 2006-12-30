/*

    dsh-swarm  Simple framework for particle swarm optimization algorithms.
    Copyright (c) 2006 held jointly by the individual authors.

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
 * An exit strategy function for a particle swarm optimization algorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ExitStrategy
{

    /**
     * Return <code>true</code> if the specified particle swarm has met the
     * criteria of this exit strategy function.
     *
     * @param position particle position
     * @param velocity particle velocity
     * @param cognitiveMemory cognitive or individual memory
     * @param socialMemory social or swarm memory
     * @param epoch epoch
     * @return true if the specified particle swarm has met the criteria of
     *    this exit strategy function
     */
    boolean evaluate(ObjectMatrix2D<Double> position,
                     ObjectMatrix2D<Double> velocity,
                     ObjectMatrix2D<Double> cognitiveMemory,
                     ObjectMatrix1D<Double> socialMemory,
                     int epoch);
}