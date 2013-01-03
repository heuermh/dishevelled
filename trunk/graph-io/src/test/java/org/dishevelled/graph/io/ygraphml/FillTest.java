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
 * Unit test for Fill.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FillTest
    extends TestCase
{

    public void testConstructor()
    {
        Fill fill0 = new Fill("color", true);

        try
        {
            Fill fill = new Fill(null, true);
            fail("ctr(null color) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testColor()
    {
        Fill fill = new Fill("color", true);
        assertTrue(fill.getColor() != null);
        assertEquals("color", fill.getColor());
    }

    public void testTransparent()
    {
        Fill fill = new Fill("color", true);
        assertEquals(true, fill.isTransparent());
    }

    public void testEquals()
    {
        Fill fill0 = new Fill("color", true);
        Fill fill1 = new Fill("color", true);

        assertFalse(fill0.equals(null));
        assertFalse(fill1.equals(null));
        assertFalse(fill0.equals(new Object()));
        assertFalse(fill1.equals(new Object()));
        assertTrue(fill0.equals(fill0));
        assertTrue(fill1.equals(fill1));
        assertFalse(fill0 == fill1);
        assertFalse(fill0.equals(fill1));
        assertFalse(fill1.equals(fill0));
    }

    public void testHashCode()
    {
        Fill fill0 = new Fill("color", true);
        Fill fill1 = new Fill("color", true);

        assertEquals(fill0.hashCode(), fill0.hashCode());
        assertEquals(fill1.hashCode(), fill1.hashCode());
        if (fill0.equals(fill1))
        {
            assertEquals(fill0.hashCode(), fill1.hashCode());
            assertEquals(fill1.hashCode(), fill0.hashCode());
        }
        if (fill1.equals(fill0))
        {
            assertEquals(fill0.hashCode(), fill1.hashCode());
            assertEquals(fill1.hashCode(), fill0.hashCode());
        }
    }
}