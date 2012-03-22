/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
package org.dishevelled.venn;

import java.awt.geom.Rectangle2D;

import junit.framework.TestCase;

// move to junit 4, reimplement with mocks when the time comes
//import org.dishevelled.venn.model.BinaryVennModelImpl;
//import org.dishevelled.venn.model.TernaryVennModelImpl;
//import org.dishevelled.venn.model.QuaternaryVennModelImpl;

//import static org.dishevelled.venn.VennLayouter.PerformanceHint.OPTIMIZE_FOR_SPEED;
//import static org.dishevelled.venn.VennLayouter.PerformanceHint.OPTIMIZE_FOR_CORRECTNESS;

/**
 * Abstract unit test for implementations of VennLayouter.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractVennLayouterTest
    extends TestCase
{
    protected VennLayouter vennLayouter;
    protected Rectangle2D boundingRectangle;

    protected abstract VennLayouter createVennLayouter();


    public void setUp()
    {
        vennLayouter = createVennLayouter();
        boundingRectangle = new Rectangle2D.Double(0.0d, 0.0d, 1000.0d, 1000.0d);
    }

    public void testCreateVennLayouter()
    {
        assertNotNull(vennLayouter);
    }

    /*
    public void testLayoutBinaryNullModel()
    {
        try
        {
            vennLayout.layout((BinaryVennModel<String>) null, boundingRectangle, OPTIMIZE_FOR_SPEED);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutBinaryNullBoundingRectangle()
    {
        try
        {
            vennLayout.layout(new BinaryVennModelImpl<String>(), null, OPTIMIZE_FOR_SPEED);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutBinaryNullPerformanceHint()
    {
        try
        {
            vennLayout.layout(new BinaryVennModelImpl<String>(), boundingRectangle, null);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutTernaryNullModel()
    {
        try
        {
            vennLayout.layout((TernaryVennModel<String>) null, boundingRectangle, OPTIMIZE_FOR_SPEED);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutTernaryNullBoundingRectangle()
    {
        try
        {
            vennLayout.layout(new TernaryVennModelImpl<String>(), null, OPTIMIZE_FOR_SPEED);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutTernaryNullPerformanceHint()
    {
        try
        {
            vennLayout.layout(new TernaryVennModelImpl<String>(), boundingRectangle, null);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutQuaternaryNullModel()
    {
        try
        {
            vennLayout.layout((QuaternaryVennModel<String>) null, boundingRectangle, OPTIMIZE_FOR_SPEED);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutQuaternaryNullBoundingRectangle()
    {
        try
        {
            vennLayout.layout(new QuaternaryVennModelImpl<String>(), null, OPTIMIZE_FOR_SPEED);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLayoutQuaternaryNullPerformanceHint()
    {
        try
        {
            vennLayout.layout(new QuaternaryVennModelImpl<String>(), boundingRectangle, null);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
    */
}
