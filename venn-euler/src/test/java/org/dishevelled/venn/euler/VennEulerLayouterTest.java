/*

    dsh-venn-euler  Lightweight components for venn/euler diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.euler;

import static org.dishevelled.venn.VennLayouter.PerformanceHint.*;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.dishevelled.venn.VennModel;
import org.dishevelled.venn.VennLayout;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for VennEulerLayouter.
 */
public class VennEulerLayouterTest
{
    VennEulerLayouter layouter;

    @Before
    public void setup()
    {
        layouter = new VennEulerLayouter();
    }

    @Test(expected=NullPointerException.class)
    public void testLayoutNullModel()
    {
        VennLayout vl = layouter.layout(null, new Rectangle(100, 200), OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=NullPointerException.class)
    public void testLayoutNullRect()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        null, OPTIMIZE_FOR_CORRECTNESS);
    }

    @Test
    public void testLayoutNullPerfHint()
    {
        // we don't use performance hints, so null should be OK
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        new Rectangle(100, 200), null);
        assertNotNull(vl);
    }

    @Test
    public void testLayout()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(new int[] { 1, 2 }, new int[] { 2, 3 }),
                                        new Rectangle(100, 200), OPTIMIZE_FOR_SPEED);
        assertNotNull(vl);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutModelTooSmall()
    {
        VennLayout vl = layouter.layout(new FakeVennModel(),
                                        new Rectangle(100, 200), OPTIMIZE_FOR_SPEED);
    }
}
