/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008 held jointly by the individual authors.

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
 * Unit test for LineStyle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class LineStyleTest
    extends TestCase
{

    public void testConstructor()
    {
        LineStyle lineStyle0 = new LineStyle("type", 0.0d, "color");
        LineStyle lineStyle1 = new LineStyle("type", 1.0d, "color");
        LineStyle lineStyle2 = new LineStyle("type", -1.0d, "color");
        LineStyle lineStyle3 = new LineStyle("type", Double.NaN, "color");
        LineStyle lineStyle4 = new LineStyle("type", Double.MIN_VALUE, "color");
        LineStyle lineStyle5 = new LineStyle("type", Double.MAX_VALUE, "color");
        LineStyle lineStyle6 = new LineStyle("type", Double.MAX_VALUE * -1.0d, "color");

        try
        {
            LineStyle lineStyle = new LineStyle(null, 1.0d, "color");
            fail("ctr(null type) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            LineStyle lineStyle = new LineStyle("type", 1.0d, null);
            fail("ctr(null color) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testType()
    {
        LineStyle lineStyle = new LineStyle("type", 1.0d, "color");
        assertTrue(lineStyle.getType() != null);
        assertEquals("type", lineStyle.getType());
    }

    public void testWidth()
    {
        LineStyle lineStyle = new LineStyle("type", 1.0d, "color");
        assertEquals(1.0d, lineStyle.getWidth());
    }

    public void testColor()
    {
        LineStyle lineStyle = new LineStyle("type", 1.0d, "color");
        assertTrue(lineStyle.getColor() != null);
        assertEquals("color", lineStyle.getColor());
    }

    public void testEquals()
    {
        LineStyle lineStyle0 = new LineStyle("type", 1.0d, "color");
        LineStyle lineStyle1 = new LineStyle("type", 1.0d, "color");

        assertFalse(lineStyle0.equals(null));
        assertFalse(lineStyle1.equals(null));
        assertFalse(lineStyle0.equals(new Object()));
        assertFalse(lineStyle1.equals(new Object()));
        assertTrue(lineStyle0.equals(lineStyle0));
        assertTrue(lineStyle1.equals(lineStyle1));
        assertFalse(lineStyle0 == lineStyle1);
        assertFalse(lineStyle0.equals(lineStyle1));
        assertFalse(lineStyle1.equals(lineStyle0));
    }

    public void testHashCode()
    {
        LineStyle lineStyle0 = new LineStyle("type", 1.0d, "color");
        LineStyle lineStyle1 = new LineStyle("type", 1.0d, "color");

        assertEquals(lineStyle0.hashCode(), lineStyle0.hashCode());
        assertEquals(lineStyle1.hashCode(), lineStyle1.hashCode());
        if (lineStyle0.equals(lineStyle1))
        {
            assertEquals(lineStyle0.hashCode(), lineStyle1.hashCode());
            assertEquals(lineStyle1.hashCode(), lineStyle0.hashCode());
        }
        if (lineStyle1.equals(lineStyle0))
        {
            assertEquals(lineStyle0.hashCode(), lineStyle1.hashCode());
            assertEquals(lineStyle1.hashCode(), lineStyle0.hashCode());
        }
    }
}