/*

    dsh-venn  Lightweight components for venn diagrams.
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
package org.dishevelled.venn;

import static org.junit.Assert.assertNotNull;

import java.awt.geom.Rectangle2D;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.dishevelled.venn.model.BinaryVennModelImpl;
import org.dishevelled.venn.model.TernaryVennModelImpl;
import org.dishevelled.venn.model.QuaternaryVennModelImpl;

import static org.dishevelled.venn.VennLayouter.PerformanceHint.OPTIMIZE_FOR_SPEED;
import static org.dishevelled.venn.VennLayouter.PerformanceHint.OPTIMIZE_FOR_CORRECTNESS;

/**
 * Abstract unit test for implementations of VennLayouter.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractVennLayouterTest
{
    protected VennLayouter vennLayouter;
    protected Rectangle2D boundingRectangle;
    @Mock
    protected VennModel<String> model;
    @Mock
    protected BinaryVennModel<String> binaryModel;
    @Mock
    protected TernaryVennModel<String> ternaryModel;
    @Mock
    protected QuaternaryVennModel<String> quaternaryModel;

    /**
     * Create and return a new instance of VennLayouter to test.
     *
     * @return a new instance of VennLayouter to test
     */
    protected abstract VennLayouter createVennLayouter();


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        vennLayouter = createVennLayouter();
        boundingRectangle = new Rectangle2D.Double(0.0d, 0.0d, 1000.0d, 1000.0d);
    }

    @Test
    public void testCreateVennLayouter()
    {
        assertNotNull(vennLayouter);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutNullModel()
    {
        vennLayouter.layout((VennModel<String>) null, boundingRectangle, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutNullBoundingRectangle()
    {
        vennLayouter.layout(model, null, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutNullPerformanceHint()
    {
        vennLayouter.layout(model, boundingRectangle, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutBinaryNullModel()
    {
        vennLayouter.layout((BinaryVennModel<String>) null, boundingRectangle, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutBinaryNullBoundingRectangle()
    {
        vennLayouter.layout(binaryModel, null, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutBinaryNullPerformanceHint()
    {
        vennLayouter.layout(binaryModel, boundingRectangle, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutTernaryNullModel()
    {
        vennLayouter.layout((TernaryVennModel<String>) null, boundingRectangle, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutTernaryNullBoundingRectangle()
    {
        vennLayouter.layout(ternaryModel, null, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutTernaryNullPerformanceHint()
    {
        vennLayouter.layout(ternaryModel, boundingRectangle, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutQuaternaryNullModel()
    {
        vennLayouter.layout((QuaternaryVennModel<String>) null, boundingRectangle, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutQuaternaryNullBoundingRectangle()
    {
        vennLayouter.layout(quaternaryModel, null, OPTIMIZE_FOR_SPEED);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLayoutQuaternaryNullPerformanceHint()
    {
        vennLayouter.layout(quaternaryModel, boundingRectangle, null);
    }
}
