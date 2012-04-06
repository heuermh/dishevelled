/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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
 * Unit test for BorderStyle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BorderStyleTest
    extends TestCase
{

    public void testConstructor()
    {
        BorderStyle borderStyle0 = new BorderStyle("type", 0.0d, "color");
        BorderStyle borderStyle1 = new BorderStyle("type", 1.0d, "color");
        BorderStyle borderStyle2 = new BorderStyle("type", -1.0d, "color");
        BorderStyle borderStyle3 = new BorderStyle("type", Double.NaN, "color");
        BorderStyle borderStyle4 = new BorderStyle("type", Double.MIN_VALUE, "color");
        BorderStyle borderStyle5 = new BorderStyle("type", Double.MAX_VALUE, "color");
        BorderStyle borderStyle6 = new BorderStyle("type", Double.MAX_VALUE * -1.0d, "color");

        try
        {
            BorderStyle borderStyle = new BorderStyle(null, 1.0d, "color");
            fail("ctr(null type) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            BorderStyle borderStyle = new BorderStyle("type", 1.0d, null);
            fail("ctr(null color) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testType()
    {
        BorderStyle borderStyle = new BorderStyle("type", 1.0d, "color");
        assertTrue(borderStyle.getType() != null);
        assertEquals("type", borderStyle.getType());
    }

    public void testWidth()
    {
        BorderStyle borderStyle = new BorderStyle("type", 1.0d, "color");
        assertEquals(1.0d, borderStyle.getWidth());
    }

    public void testColor()
    {
        BorderStyle borderStyle = new BorderStyle("type", 1.0d, "color");
        assertTrue(borderStyle.getColor() != null);
        assertEquals("color", borderStyle.getColor());
    }

    public void testEquals()
    {
        BorderStyle borderStyle0 = new BorderStyle("type", 1.0d, "color");
        BorderStyle borderStyle1 = new BorderStyle("type", 1.0d, "color");

        assertFalse(borderStyle0.equals(null));
        assertFalse(borderStyle1.equals(null));
        assertFalse(borderStyle0.equals(new Object()));
        assertFalse(borderStyle1.equals(new Object()));
        assertTrue(borderStyle0.equals(borderStyle0));
        assertTrue(borderStyle1.equals(borderStyle1));
        assertFalse(borderStyle0 == borderStyle1);
        assertFalse(borderStyle0.equals(borderStyle1));
        assertFalse(borderStyle1.equals(borderStyle0));
    }

    public void testHashCode()
    {
        BorderStyle borderStyle0 = new BorderStyle("type", 1.0d, "color");
        BorderStyle borderStyle1 = new BorderStyle("type", 1.0d, "color");

        assertEquals(borderStyle0.hashCode(), borderStyle0.hashCode());
        assertEquals(borderStyle1.hashCode(), borderStyle1.hashCode());
        if (borderStyle0.equals(borderStyle1))
        {
            assertEquals(borderStyle0.hashCode(), borderStyle1.hashCode());
            assertEquals(borderStyle1.hashCode(), borderStyle0.hashCode());
        }
        if (borderStyle1.equals(borderStyle0))
        {
            assertEquals(borderStyle0.hashCode(), borderStyle1.hashCode());
            assertEquals(borderStyle1.hashCode(), borderStyle0.hashCode());
        }
    }
}