/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.iconbundle;

import junit.framework.TestCase;

/**
 * Unit test case for IconState.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IconStateTest
    extends TestCase
{

    public void testIconState()
    {
        assertNotNull(IconState.NORMAL);
        assertNotNull(IconState.ACTIVE);
        assertNotNull(IconState.MOUSEOVER);
        assertNotNull(IconState.SELECTED);
        assertNotNull(IconState.DRAGGING);
        assertNotNull(IconState.DISABLED);
        assertNotNull(IconState.VALUES);
        assertTrue(IconState.VALUES.size() == 6);
        assertTrue(IconState.VALUES.contains(IconState.NORMAL));
        assertTrue(IconState.VALUES.contains(IconState.ACTIVE));
        assertTrue(IconState.VALUES.contains(IconState.MOUSEOVER));
        assertTrue(IconState.VALUES.contains(IconState.SELECTED));
        assertTrue(IconState.VALUES.contains(IconState.DRAGGING));
        assertTrue(IconState.VALUES.contains(IconState.DISABLED));
        assertEquals(IconState.NORMAL.toString(), "normal");
        assertEquals(IconState.ACTIVE.toString(), "active");
        assertEquals(IconState.MOUSEOVER.toString(), "mouseover");
        assertEquals(IconState.SELECTED.toString(), "selected");
        assertEquals(IconState.DRAGGING.toString(), "dragging");
        assertEquals(IconState.DISABLED.toString(), "disabled");

        try
        {
            IconState.VALUES.add(new Object());
            fail("expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            IconState.VALUES.remove(IconState.NORMAL);
            fail("expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testSerialization()
    {
        // TODO:
        // implement
    }
}
