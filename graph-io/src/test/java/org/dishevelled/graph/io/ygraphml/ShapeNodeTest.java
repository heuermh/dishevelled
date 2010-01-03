/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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
package org.dishevelled.graph.io.ygraphml;

import junit.framework.TestCase;

/**
 * Unit test for ShapeNode.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ShapeNodeTest
    extends TestCase
{
    /** Instance of Fill. */
    private Fill fill;

    /** Instance of NodeLabel. */
    private NodeLabel nodeLabel;

    /** Instance of BorderStyle. */
    private BorderStyle borderStyle;

    /** Instance of Shape. */
    private Shape shape;


    /**
     * Create and return a new instance of Fill
     * for testing <code>fill</code>.
     *
     * @return a new instance of Fill
     */
    private Fill createFill()
    {
        return new Fill("color", true);
    }

    /**
     * Create and return a new instance of NodeLabel
     * for testing <code>nodeLabel</code>.
     *
     * @return a new instance of NodeLabel
     */
    private NodeLabel createNodeLabel()
    {
        return new NodeLabel(true, "alignment", "fontFamily", 10, "fontStyle",
                             "textColor", "modelName", "modelPosition", "autoSizePolicy", "text");
    }

    /**
     * Create and return a new instance of BorderStyle
     * for testing <code>borderStyle</code>.
     *
     * @return a new instance of BorderStyle
     */
    private BorderStyle createBorderStyle()
    {
        return new BorderStyle("type", 1.0d, "color");
    }

    /**
     * Create and return a new instance of Shape
     * for testing <code>shape</code>.
     *
     * @return a new instance of Shape
     */
    private Shape createShape()
    {
        return new Shape("type");
    }

    /** {@inheritDoc} */
    protected void setUp()
    {
        fill = createFill();
        nodeLabel = createNodeLabel();
        borderStyle = createBorderStyle();
        shape = createShape();
    }

    public void testConstructor()
    {
        ShapeNode shapeNode0 = new ShapeNode(fill, nodeLabel, borderStyle, shape);

        try
        {
            ShapeNode shapeNode = new ShapeNode(null, nodeLabel, borderStyle, shape);
            fail("ctr(null fill) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ShapeNode shapeNode = new ShapeNode(fill, null, borderStyle, shape);
            fail("ctr(null nodeLabel) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ShapeNode shapeNode = new ShapeNode(fill, nodeLabel, null, shape);
            fail("ctr(null borderStyle) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ShapeNode shapeNode = new ShapeNode(fill, nodeLabel, borderStyle, null);
            fail("ctr(null shape) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testFill()
    {
        ShapeNode shapeNode = new ShapeNode(fill, nodeLabel, borderStyle, shape);
        assertTrue(shapeNode.getFill() != null);
        assertEquals(fill, shapeNode.getFill());
    }

    public void testNodeLabel()
    {
        ShapeNode shapeNode = new ShapeNode(fill, nodeLabel, borderStyle, shape);
        assertTrue(shapeNode.getNodeLabel() != null);
        assertEquals(nodeLabel, shapeNode.getNodeLabel());
    }

    public void testBorderStyle()
    {
        ShapeNode shapeNode = new ShapeNode(fill, nodeLabel, borderStyle, shape);
        assertTrue(shapeNode.getBorderStyle() != null);
        assertEquals(borderStyle, shapeNode.getBorderStyle());
    }

    public void testShape()
    {
        ShapeNode shapeNode = new ShapeNode(fill, nodeLabel, borderStyle, shape);
        assertTrue(shapeNode.getShape() != null);
        assertEquals(shape, shapeNode.getShape());
    }

    public void testEquals()
    {
        ShapeNode shapeNode0 = new ShapeNode(fill, nodeLabel, borderStyle, shape);
        ShapeNode shapeNode1 = new ShapeNode(fill, nodeLabel, borderStyle, shape);

        assertFalse(shapeNode0.equals(null));
        assertFalse(shapeNode1.equals(null));
        assertFalse(shapeNode0.equals(new Object()));
        assertFalse(shapeNode1.equals(new Object()));
        assertTrue(shapeNode0.equals(shapeNode0));
        assertTrue(shapeNode1.equals(shapeNode1));
        assertFalse(shapeNode0 == shapeNode1);
        assertFalse(shapeNode0.equals(shapeNode1));
        assertFalse(shapeNode1.equals(shapeNode0));
    }

    public void testHashCode()
    {
        ShapeNode shapeNode0 = new ShapeNode(fill, nodeLabel, borderStyle, shape);
        ShapeNode shapeNode1 = new ShapeNode(fill, nodeLabel, borderStyle, shape);

        assertEquals(shapeNode0.hashCode(), shapeNode0.hashCode());
        assertEquals(shapeNode1.hashCode(), shapeNode1.hashCode());
        if (shapeNode0.equals(shapeNode1))
        {
            assertEquals(shapeNode0.hashCode(), shapeNode1.hashCode());
            assertEquals(shapeNode1.hashCode(), shapeNode0.hashCode());
        }
        if (shapeNode1.equals(shapeNode0))
        {
            assertEquals(shapeNode0.hashCode(), shapeNode1.hashCode());
            assertEquals(shapeNode1.hashCode(), shapeNode0.hashCode());
        }
    }
}