/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
package org.dishevelled.iconbundle;

import junit.framework.TestCase;

/**
 * Unit test case for IconState.
 *
 * @author  Michael Heuer
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
        assertTrue(IconState.VALUES.size() == 7);
        assertTrue(IconState.VALUES.contains(IconState.NORMAL));
        assertTrue(IconState.VALUES.contains(IconState.ACTIVE));
        assertTrue(IconState.VALUES.contains(IconState.MOUSEOVER));
        assertTrue(IconState.VALUES.contains(IconState.SELECTED));
        assertTrue(IconState.VALUES.contains(IconState.SELECTED_MOUSEOVER));
        assertTrue(IconState.VALUES.contains(IconState.DRAGGING));
        assertTrue(IconState.VALUES.contains(IconState.DISABLED));
        assertEquals(IconState.NORMAL.toString(), "normal");
        assertEquals(IconState.ACTIVE.toString(), "active");
        assertEquals(IconState.MOUSEOVER.toString(), "mouseover");
        assertEquals(IconState.SELECTED.toString(), "selected");
        assertEquals(IconState.DRAGGING.toString(), "dragging");
        assertEquals(IconState.DISABLED.toString(), "disabled");

        assertTrue(IconState.NORMAL.isNormal());
        assertFalse(IconState.NORMAL.isActive());
        assertFalse(IconState.NORMAL.isMouseover());
        assertFalse(IconState.NORMAL.isSelected());
        assertFalse(IconState.NORMAL.isDragging());
        assertFalse(IconState.NORMAL.isDisabled());

        assertFalse(IconState.ACTIVE.isNormal());
        assertTrue(IconState.ACTIVE.isActive());
        assertFalse(IconState.ACTIVE.isMouseover());
        assertFalse(IconState.ACTIVE.isSelected());
        assertFalse(IconState.ACTIVE.isDragging());
        assertFalse(IconState.ACTIVE.isDisabled());

        assertFalse(IconState.MOUSEOVER.isNormal());
        assertFalse(IconState.MOUSEOVER.isActive());
        assertTrue(IconState.MOUSEOVER.isMouseover());
        assertFalse(IconState.MOUSEOVER.isSelected());
        assertFalse(IconState.MOUSEOVER.isDragging());
        assertFalse(IconState.MOUSEOVER.isDisabled());

        assertFalse(IconState.SELECTED.isNormal());
        assertFalse(IconState.SELECTED.isActive());
        assertFalse(IconState.SELECTED.isMouseover());
        assertTrue(IconState.SELECTED.isSelected());
        assertFalse(IconState.SELECTED.isDragging());
        assertFalse(IconState.SELECTED.isDisabled());

        assertFalse(IconState.DRAGGING.isNormal());
        assertFalse(IconState.DRAGGING.isActive());
        assertFalse(IconState.DRAGGING.isMouseover());
        assertFalse(IconState.DRAGGING.isSelected());
        assertTrue(IconState.DRAGGING.isDragging());
        assertFalse(IconState.DRAGGING.isDisabled());

        assertFalse(IconState.DISABLED.isNormal());
        assertFalse(IconState.DISABLED.isActive());
        assertFalse(IconState.DISABLED.isMouseover());
        assertFalse(IconState.DISABLED.isSelected());
        assertFalse(IconState.DISABLED.isDragging());
        assertTrue(IconState.DISABLED.isDisabled());

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
