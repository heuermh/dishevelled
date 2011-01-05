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
 * Unit test for BendStyle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BendStyleTest
    extends TestCase
{

    public void testConstructor()
    {
        BendStyle bendStyle0 = new BendStyle(true);
        BendStyle bendStyle1 = new BendStyle(false);
    }

    public void testSmoothed()
    {
        BendStyle bendStyle = new BendStyle(true);
        assertTrue(bendStyle.isSmoothed());
    }

    public void testEquals()
    {
        BendStyle bendStyle0 = new BendStyle(true);
        BendStyle bendStyle1 = new BendStyle(true);

        assertFalse(bendStyle0.equals(null));
        assertFalse(bendStyle1.equals(null));
        assertFalse(bendStyle0.equals(new Object()));
        assertFalse(bendStyle1.equals(new Object()));
        assertTrue(bendStyle0.equals(bendStyle0));
        assertTrue(bendStyle1.equals(bendStyle1));
        assertFalse(bendStyle0 == bendStyle1);
        assertFalse(bendStyle0.equals(bendStyle1));
        assertFalse(bendStyle1.equals(bendStyle0));
    }

    public void testHashCode()
    {
        BendStyle bendStyle0 = new BendStyle(true);
        BendStyle bendStyle1 = new BendStyle(true);

        assertEquals(bendStyle0.hashCode(), bendStyle0.hashCode());
        assertEquals(bendStyle1.hashCode(), bendStyle1.hashCode());
        if (bendStyle0.equals(bendStyle1))
        {
            assertEquals(bendStyle0.hashCode(), bendStyle1.hashCode());
            assertEquals(bendStyle1.hashCode(), bendStyle0.hashCode());
        }
        if (bendStyle1.equals(bendStyle0))
        {
            assertEquals(bendStyle0.hashCode(), bendStyle1.hashCode());
            assertEquals(bendStyle1.hashCode(), bendStyle0.hashCode());
        }
    }
}