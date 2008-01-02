/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
 * Unit test case for IconSize.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IconSizeTest
    extends TestCase
{

    public void testIconSize()
    {
        assertNotNull(IconSize.DEFAULT_16X16);
        assertNotNull(IconSize.DEFAULT_24X24);
        assertNotNull(IconSize.DEFAULT_32X32);
        assertNotNull(IconSize.DEFAULT_48X48);
        assertNotNull(IconSize.DEFAULT_64X64);
        assertNotNull(IconSize.DEFAULT_96X96);
        assertNotNull(IconSize.DEFAULT_128X128);
        assertNotNull(IconSize.VALUES);
        assertTrue(IconSize.VALUES.size() == 7);
        assertTrue(IconSize.VALUES.contains(IconSize.DEFAULT_16X16));
        assertTrue(IconSize.VALUES.contains(IconSize.DEFAULT_24X24));
        assertTrue(IconSize.VALUES.contains(IconSize.DEFAULT_32X32));
        assertTrue(IconSize.VALUES.contains(IconSize.DEFAULT_48X48));
        assertTrue(IconSize.VALUES.contains(IconSize.DEFAULT_64X64));
        assertTrue(IconSize.VALUES.contains(IconSize.DEFAULT_96X96));
        assertTrue(IconSize.VALUES.contains(IconSize.DEFAULT_128X128));
        assertTrue(IconSize.DEFAULT_16X16.getWidth() == 16);
        assertTrue(IconSize.DEFAULT_16X16.getHeight() == 16);
        assertEquals(IconSize.DEFAULT_16X16.toString(), "16x16");
        assertTrue(IconSize.DEFAULT_24X24.getWidth() == 24);
        assertTrue(IconSize.DEFAULT_24X24.getHeight() == 24);
        assertEquals(IconSize.DEFAULT_24X24.toString(), "24x24");
        assertTrue(IconSize.DEFAULT_32X32.getWidth() == 32);
        assertTrue(IconSize.DEFAULT_32X32.getHeight() == 32);
        assertEquals(IconSize.DEFAULT_32X32.toString(), "32x32");
        assertTrue(IconSize.DEFAULT_48X48.getWidth() == 48);
        assertTrue(IconSize.DEFAULT_48X48.getHeight() == 48);
        assertEquals(IconSize.DEFAULT_48X48.toString(), "48x48");
        assertTrue(IconSize.DEFAULT_64X64.getWidth() == 64);
        assertTrue(IconSize.DEFAULT_64X64.getHeight() == 64);
        assertEquals(IconSize.DEFAULT_64X64.toString(), "64x64");
        assertTrue(IconSize.DEFAULT_96X96.getWidth() == 96);
        assertTrue(IconSize.DEFAULT_96X96.getHeight() == 96);
        assertEquals(IconSize.DEFAULT_96X96.toString(), "96x96");
        assertTrue(IconSize.DEFAULT_128X128.getWidth() == 128);
        assertTrue(IconSize.DEFAULT_128X128.getHeight() == 128);
        assertEquals(IconSize.DEFAULT_128X128.toString(), "128x128");

        try
        {
            IconSize.VALUES.add(new Object());
            fail("expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            IconSize.VALUES.remove(IconSize.DEFAULT_16X16);
            fail("expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testCustomSize()
    {
        IconSize customSize = new IconSize(99, 101) { };

        assertNotNull(customSize);
        assertTrue(customSize.getWidth() == 99);
        assertTrue(customSize.getHeight() == 101);
        assertEquals(customSize.toString(), "99x101");

        // note that custom sizes are not included in the VALUES list

        assertNotNull(customSize.VALUES);
        assertTrue(customSize.VALUES.size() == 7);
        assertTrue(customSize.VALUES.contains(IconSize.DEFAULT_16X16));
        assertTrue(customSize.VALUES.contains(IconSize.DEFAULT_24X24));
        assertTrue(customSize.VALUES.contains(IconSize.DEFAULT_32X32));
        assertTrue(customSize.VALUES.contains(IconSize.DEFAULT_48X48));
        assertTrue(customSize.VALUES.contains(IconSize.DEFAULT_64X64));
        assertTrue(customSize.VALUES.contains(IconSize.DEFAULT_96X96));
        assertTrue(customSize.VALUES.contains(IconSize.DEFAULT_128X128));
        assertTrue("VALUES does not contain customSize",
                   customSize.VALUES.contains(customSize) == false);

        try
        {
            customSize.VALUES.add(new Object());
            fail("expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            customSize.VALUES.remove(IconSize.DEFAULT_16X16);
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

    public void testCustomSizeSerialization()
    {
        // TODO:
        // implement
    }
}
