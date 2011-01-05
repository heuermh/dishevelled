/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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
package org.dishevelled.identify;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdMenu.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdMenuTest
    extends TestCase
{

    public void testConstructor()
    {
        IdMenu menu0 = new IdMenu();
        IdMenu menu1 = new IdMenu(null);
        IdMenu menu2 = new IdMenu("name");
        IdMenu menu3 = new IdMenu(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        IdMenu menu4 = new IdMenu(new ExampleWithNameProperty("name"));

        IdMenu menu5 = new IdMenu(null, IconSize.DEFAULT_32X32);
        IdMenu menu6 = new IdMenu("name", IconSize.DEFAULT_32X32);
        IdMenu menu7 = new IdMenu(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IconSize.DEFAULT_32X32);
        IdMenu menu8 = new IdMenu(new ExampleWithNameProperty("name"), IconSize.DEFAULT_32X32);

        try
        {
            IdMenu menu = new IdMenu(null, null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconSize()
    {
        assertNotNull("DEFAULT_ICON_SIZE not null", IdMenu.DEFAULT_ICON_SIZE);

        IdMenu menu0 = new IdMenu(null);
        assertEquals(IdMenu.DEFAULT_ICON_SIZE, menu0.getIconSize());
        menu0.setIconSize(IconSize.DEFAULT_16X16);
        assertEquals(IconSize.DEFAULT_16X16, menu0.getIconSize());

        IdMenu menu1 = new IdMenu(null, IconSize.DEFAULT_16X16);
        assertEquals(IconSize.DEFAULT_16X16, menu1.getIconSize());
        menu1.setIconSize(IdMenu.DEFAULT_ICON_SIZE);
        assertEquals(IdMenu.DEFAULT_ICON_SIZE, menu1.getIconSize());

        try
        {
            menu0.setIconSize(null);
            fail("setIconSize(null) expectedIllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // todo:  test property change
    }
}