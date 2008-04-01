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
 * Unit test for Shape.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ShapeTest
    extends TestCase
{

    public void testConstructor()
    {
        Shape shape0 = new Shape("type");

        try
        {
            Shape shape = new Shape(null);
            fail("ctr(null type) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testType()
    {
        Shape shape = new Shape("type");
        assertTrue(shape.getType() != null);
        assertEquals("type", shape.getType());
    }

    public void testEquals()
    {
        Shape shape0 = new Shape("type");
        Shape shape1 = new Shape("type");

        assertFalse(shape0.equals(null));
        assertFalse(shape1.equals(null));
        assertFalse(shape0.equals(new Object()));
        assertFalse(shape1.equals(new Object()));
        assertTrue(shape0.equals(shape0));
        assertTrue(shape1.equals(shape1));
        assertFalse(shape0 == shape1);
        assertFalse(shape0.equals(shape1));
        assertFalse(shape1.equals(shape0));
    }

    public void testHashCode()
    {
        Shape shape0 = new Shape("type");
        Shape shape1 = new Shape("type");

        assertEquals(shape0.hashCode(), shape0.hashCode());
        assertEquals(shape1.hashCode(), shape1.hashCode());
        if (shape0.equals(shape1))
        {
            assertEquals(shape0.hashCode(), shape1.hashCode());
            assertEquals(shape1.hashCode(), shape0.hashCode());
        }
        if (shape1.equals(shape0))
        {
            assertEquals(shape0.hashCode(), shape1.hashCode());
            assertEquals(shape1.hashCode(), shape0.hashCode());
        }
    }
}