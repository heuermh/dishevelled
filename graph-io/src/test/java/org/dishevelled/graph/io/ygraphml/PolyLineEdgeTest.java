/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2011 held jointly by the individual authors.

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
 * Unit test for PolyLineEdge.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class PolyLineEdgeTest
    extends TestCase
{
    /** Instance of LineStyle. */
    private LineStyle lineStyle;

    /** Instance of Arrows. */
    private Arrows arrows;

    /** Instance of EdgeLabel. */
    private EdgeLabel edgeLabel;

    /** Instance of BendStyle. */
    private BendStyle bendStyle;


    /**
     * Create and return a new instance of LineStyle
     * for testing <code>lineStyle</code>.
     *
     * @return a new instance of LineStyle
     */
    private LineStyle createLineStyle()
    {
        return new LineStyle("type", 1.0d, "color");
    }

    /**
     * Create and return a new instance of Arrows
     * for testing <code>arrows</code>.
     *
     * @return a new instance of Arrows
     */
    private Arrows createArrows()
    {
        return new Arrows("source", "target");
    }

    /**
     * Create and return a new instance of EdgeLabel
     * for testing <code>edgeLabel</code>.
     *
     * @return a new instance of EdgeLabel
     */
    private EdgeLabel createEdgeLabel()
    {
        return new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor",
                             "modelName", "modelPosition", "preferredPlacement", 1.0d, 0.5d, "text");
    }

    /**
     * Create and return a new instance of BendStyle
     * for testing <code>bendStyle</code>.
     *
     * @return a new instance of BendStyle
     */
    private BendStyle createBendStyle()
    {
        return new BendStyle(true);
    }

    /** {@inheritDoc} */
    protected void setUp()
    {
        lineStyle = createLineStyle();
        arrows = createArrows();
        edgeLabel = createEdgeLabel();
        bendStyle = createBendStyle();
    }

    public void testConstructor()
    {
        PolyLineEdge polyLineEdge0 = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);

        try
        {
            PolyLineEdge polyLineEdge = new PolyLineEdge(null, arrows, edgeLabel, bendStyle);
            fail("ctr(null lineStyle) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            PolyLineEdge polyLineEdge = new PolyLineEdge(lineStyle, null, edgeLabel, bendStyle);
            fail("ctr(null arrows) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            PolyLineEdge polyLineEdge = new PolyLineEdge(lineStyle, arrows, null, bendStyle);
            fail("ctr(null edgeLabel) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            PolyLineEdge polyLineEdge = new PolyLineEdge(lineStyle, arrows, edgeLabel, null);
            fail("ctr(null bendStyle) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testLineStyle()
    {
        PolyLineEdge polyLineEdge = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);
        assertTrue(polyLineEdge.getLineStyle() != null);
        assertEquals(lineStyle, polyLineEdge.getLineStyle());
    }

    public void testArrows()
    {
        PolyLineEdge polyLineEdge = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);
        assertTrue(polyLineEdge.getArrows() != null);
        assertEquals(arrows, polyLineEdge.getArrows());
    }

    public void testEdgeLabel()
    {
        PolyLineEdge polyLineEdge = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);
        assertTrue(polyLineEdge.getEdgeLabel() != null);
        assertEquals(edgeLabel, polyLineEdge.getEdgeLabel());
    }

    public void testBendStyle()
    {
        PolyLineEdge polyLineEdge = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);
        assertTrue(polyLineEdge.getBendStyle() != null);
        assertEquals(bendStyle, polyLineEdge.getBendStyle());
    }

    public void testEquals()
    {
        PolyLineEdge polyLineEdge0 = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);
        PolyLineEdge polyLineEdge1 = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);

        assertFalse(polyLineEdge0.equals(null));
        assertFalse(polyLineEdge1.equals(null));
        assertFalse(polyLineEdge0.equals(new Object()));
        assertFalse(polyLineEdge1.equals(new Object()));
        assertTrue(polyLineEdge0.equals(polyLineEdge0));
        assertTrue(polyLineEdge1.equals(polyLineEdge1));
        assertFalse(polyLineEdge0 == polyLineEdge1);
        assertFalse(polyLineEdge0.equals(polyLineEdge1));
        assertFalse(polyLineEdge1.equals(polyLineEdge0));
    }

    public void testHashCode()
    {
        PolyLineEdge polyLineEdge0 = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);
        PolyLineEdge polyLineEdge1 = new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);

        assertEquals(polyLineEdge0.hashCode(), polyLineEdge0.hashCode());
        assertEquals(polyLineEdge1.hashCode(), polyLineEdge1.hashCode());
        if (polyLineEdge0.equals(polyLineEdge1))
        {
            assertEquals(polyLineEdge0.hashCode(), polyLineEdge1.hashCode());
            assertEquals(polyLineEdge1.hashCode(), polyLineEdge0.hashCode());
        }
        if (polyLineEdge1.equals(polyLineEdge0))
        {
            assertEquals(polyLineEdge0.hashCode(), polyLineEdge1.hashCode());
            assertEquals(polyLineEdge1.hashCode(), polyLineEdge0.hashCode());
        }
    }
}