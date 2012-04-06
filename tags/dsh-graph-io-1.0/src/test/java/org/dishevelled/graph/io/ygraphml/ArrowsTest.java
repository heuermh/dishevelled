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
 * Unit test for Arrows.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ArrowsTest
    extends TestCase
{

    public void testConstructor()
    {
        Arrows arrows0 = new Arrows("source", "target");

        try
        {
            Arrows arrows = new Arrows(null, "target");
            fail("ctr(null source) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Arrows arrows = new Arrows("source", null);
            fail("ctr(null target) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSource()
    {
        Arrows arrows = new Arrows("source", "target");
        assertTrue(arrows.getSource() != null);
        assertEquals("source", arrows.getSource());
    }

    public void testTarget()
    {
        Arrows arrows = new Arrows("source", "target");
        assertTrue(arrows.getTarget() != null);
        assertEquals("target", arrows.getTarget());
    }

    public void testEquals()
    {
        Arrows arrows0 = new Arrows("source", "target");
        Arrows arrows1 = new Arrows("source", "target");

        assertFalse(arrows0.equals(null));
        assertFalse(arrows1.equals(null));
        assertFalse(arrows0.equals(new Object()));
        assertFalse(arrows1.equals(new Object()));
        assertTrue(arrows0.equals(arrows0));
        assertTrue(arrows1.equals(arrows1));
        assertFalse(arrows0 == arrows1);
        assertFalse(arrows0.equals(arrows1));
        assertFalse(arrows1.equals(arrows0));
    }

    public void testHashCode()
    {
        Arrows arrows0 = new Arrows("source", "target");
        Arrows arrows1 = new Arrows("source", "target");

        assertEquals(arrows0.hashCode(), arrows0.hashCode());
        assertEquals(arrows1.hashCode(), arrows1.hashCode());
        if (arrows0.equals(arrows1))
        {
            assertEquals(arrows0.hashCode(), arrows1.hashCode());
            assertEquals(arrows1.hashCode(), arrows0.hashCode());
        }
        if (arrows1.equals(arrows0))
        {
            assertEquals(arrows0.hashCode(), arrows1.hashCode());
            assertEquals(arrows1.hashCode(), arrows0.hashCode());
        }
    }
}