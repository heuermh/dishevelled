/*

    dsh-variation  Variation.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.interval.tree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.dishevelled.variation.interval.Interval;

/**
 * Unit test for CenteredIntervalTree.
 */
public final class CenteredIntervalTreeTest
{

    @Test
    public void testIntersectClosedToClosedOpen()
    {
        Interval database = Interval.closed(28915184, 28985156);
        //Interval database = Interval.open(28915184, 28985156);
        //Interval query = Interval.closed(28957714, 28957715);
        Interval query = Interval.closedOpen(28957714, 28957715);
        //Interval query = Interval.open(28957714, 28957715);
        //Interval query = Interval.openClosed(28957714, 28957715);
        //Interval query = Interval.singleton(28957714);
        //Interval query = Interval.all();

        int count = 0;
        for (Interval interval : new CenteredIntervalTree(database).intersect(query))
        {
            assertEquals(database, interval);
            count++;
        }
        assertEquals(1, count);
    }
}