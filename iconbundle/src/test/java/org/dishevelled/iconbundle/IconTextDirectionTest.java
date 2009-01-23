/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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
 * Unit test case for IconTextDirection.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IconTextDirectionTest
    extends TestCase
{

    public void testIconTextDirection()
    {
        assertNotNull(IconTextDirection.LEFT_TO_RIGHT);
        assertNotNull(IconTextDirection.RIGHT_TO_LEFT);
        assertNotNull(IconTextDirection.VALUES);
        assertTrue(IconTextDirection.VALUES.size() == 2);
        assertTrue(IconTextDirection.VALUES.contains(IconTextDirection.LEFT_TO_RIGHT));
        assertTrue(IconTextDirection.VALUES.contains(IconTextDirection.RIGHT_TO_LEFT));
        assertEquals(IconTextDirection.LEFT_TO_RIGHT.toString(), "ltr");
        assertEquals(IconTextDirection.RIGHT_TO_LEFT.toString(), "rtl");

        try
        {
            IconTextDirection.VALUES.add(new Object());
            fail("expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            IconTextDirection.VALUES.remove(IconTextDirection.RIGHT_TO_LEFT);
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
