/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2013 held jointly by the individual authors.

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
 * Unit test for NodeLabel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NodeLabelTest
    extends TestCase
{

    public void testFake()
    {
        // empty
    }

    /*
    public void testConstructor()
    {
        // TODO:  enumerate all valid parameter combinations
        //    null/foo for 0:1, foo for 1, emptyFoos/singletonFoos/fullFoos for 0:*, singletonFoos/fullFoos for 1:*
        NodeLabel nodeLabel0 = new NodeLabel(...);

        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null visible) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null alignment) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null fontFamily) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null fontSize) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null fontStyle) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null textColor) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null modelName) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null modelPosition) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null autoSizePolicy) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            NodeLabel nodeLabel = new NodeLabel(null, ...);
            fail("ctr(null text) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testVisible()
    {
        NodeLabel nodeLabel = new NodeLabel(visible, ...);
        assertTrue(nodeLabel.getVisible() != null);
        assertEquals(visible, nodeLabel.getVisible());
    }

    public void testAlignment()
    {
        NodeLabel nodeLabel = new NodeLabel(alignment, ...);
        assertTrue(nodeLabel.getAlignment() != null);
        assertEquals(alignment, nodeLabel.getAlignment());
    }

    public void testFontFamily()
    {
        NodeLabel nodeLabel = new NodeLabel(fontFamily, ...);
        assertTrue(nodeLabel.getFontFamily() != null);
        assertEquals(fontFamily, nodeLabel.getFontFamily());
    }

    public void testFontSize()
    {
        NodeLabel nodeLabel = new NodeLabel(fontSize, ...);
        assertTrue(nodeLabel.getFontSize() != null);
        assertEquals(fontSize, nodeLabel.getFontSize());
    }

    public void testFontStyle()
    {
        NodeLabel nodeLabel = new NodeLabel(fontStyle, ...);
        assertTrue(nodeLabel.getFontStyle() != null);
        assertEquals(fontStyle, nodeLabel.getFontStyle());
    }

    public void testTextColor()
    {
        NodeLabel nodeLabel = new NodeLabel(textColor, ...);
        assertTrue(nodeLabel.getTextColor() != null);
        assertEquals(textColor, nodeLabel.getTextColor());
    }

    public void testModelName()
    {
        NodeLabel nodeLabel = new NodeLabel(modelName, ...);
        assertTrue(nodeLabel.getModelName() != null);
        assertEquals(modelName, nodeLabel.getModelName());
    }

    public void testModelPosition()
    {
        NodeLabel nodeLabel = new NodeLabel(modelPosition, ...);
        assertTrue(nodeLabel.getModelPosition() != null);
        assertEquals(modelPosition, nodeLabel.getModelPosition());
    }

    public void testAutoSizePolicy()
    {
        NodeLabel nodeLabel = new NodeLabel(autoSizePolicy, ...);
        assertTrue(nodeLabel.getAutoSizePolicy() != null);
        assertEquals(autoSizePolicy, nodeLabel.getAutoSizePolicy());
    }

    public void testText()
    {
        NodeLabel nodeLabel = new NodeLabel(text, ...);
        assertTrue(nodeLabel.getText() != null);
        assertEquals(text, nodeLabel.getText());
    }

    public void testEquals()
    {
        NodeLabel nodeLabel0 = new NodeLabel(...);
        NodeLabel nodeLabel1 = new NodeLabel(...);

        assertFalse(nodeLabel0.equals(null));
        assertFalse(nodeLabel1.equals(null));
        assertFalse(nodeLabel0.equals(new Object()));
        assertFalse(nodeLabel1.equals(new Object()));
        assertTrue(nodeLabel0.equals(nodeLabel0));
        assertTrue(nodeLabel1.equals(nodeLabel1));
        assertFalse(nodeLabel0 == nodeLabel1);
        assertFalse(nodeLabel0.equals(nodeLabel1));
        assertFalse(nodeLabel1.equals(nodeLabel0));
    }

    public void testHashCode()
    {
        NodeLabel nodeLabel0 = new NodeLabel(...);
        NodeLabel nodeLabel1 = new NodeLabel(...);

        assertEquals(nodeLabel0.hashCode(), nodeLabel0.hashCode());
        assertEquals(nodeLabel1.hashCode(), nodeLabel1.hashCode());
        if (nodeLabel0.equals(nodeLabel1))
        {
            assertEquals(nodeLabel0.hashCode(), nodeLabel1.hashCode());
            assertEquals(nodeLabel1.hashCode(), nodeLabel0.hashCode());
        }
        if (nodeLabel1.equals(nodeLabel0))
        {
            assertEquals(nodeLabel0.hashCode(), nodeLabel1.hashCode());
            assertEquals(nodeLabel1.hashCode(), nodeLabel0.hashCode());
        }
    }
    */
}