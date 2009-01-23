/*

    dsh-identify  Lightweight components for identifiable beans.
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
package org.dishevelled.identify;

import java.awt.Component;

import javax.swing.JList;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdListCellRenderer.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdListCellRendererTest
    extends TestCase
{

    public void testConstructor()
    {
        IdListCellRenderer listCellRenderer0 = new IdListCellRenderer();
        IdListCellRenderer listCellRenderer1 = new IdListCellRenderer(IconSize.DEFAULT_16X16);

        try
        {
            IdListCellRenderer listCellRenderer = new IdListCellRenderer(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconSize()
    {
        assertNotNull("DEFAULT_ICON_SIZE not null", IdListCellRenderer.DEFAULT_ICON_SIZE);

        IdListCellRenderer listCellRenderer0 = new IdListCellRenderer();
        assertEquals(IdListCellRenderer.DEFAULT_ICON_SIZE, listCellRenderer0.getIconSize());
        listCellRenderer0.setIconSize(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, listCellRenderer0.getIconSize());

        IdListCellRenderer listCellRenderer1 = new IdListCellRenderer(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, listCellRenderer1.getIconSize());
        listCellRenderer1.setIconSize(IdListCellRenderer.DEFAULT_ICON_SIZE);
        assertEquals(IdListCellRenderer.DEFAULT_ICON_SIZE, listCellRenderer1.getIconSize());

        try
        {
            listCellRenderer0.setIconSize(null);
            fail("setIconSize(null) expectedIllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // todo:  test property change
    }

    public void testIdListCellRenderer()
    {
        JList list = new JList();
        IdListCellRenderer listCellRenderer = new IdListCellRenderer();
        IconBundle oldDefaultIconBundle = IdentifyUtils.getInstance().getDefaultIconBundle();
        IdentifyUtils.getInstance().setDefaultIconBundle(TangoProject.TEXT_X_GENERIC);

        Component c0 = listCellRenderer.getListCellRendererComponent(list, null, 0, true, true);
        assertNotNull("c0 not null", c0);
        Component c1 = listCellRenderer.getListCellRendererComponent(list, null, 0, false, true);
        assertNotNull("c1 not null", c1);
        Component c2 = listCellRenderer.getListCellRendererComponent(list, null, 0, true, false);
        assertNotNull("c2 not null", c2);
        Component c3 = listCellRenderer.getListCellRendererComponent(list, null, 0, false, false);
        assertNotNull("c3 not null", c3);

        Component c4 = listCellRenderer.getListCellRendererComponent(list, "foo", 0, true, true);
        assertNotNull("c4 not null", c4);
        Component c5 = listCellRenderer.getListCellRendererComponent(list, "foo", 0, false, true);
        assertNotNull("c5 not null", c5);
        Component c6 = listCellRenderer.getListCellRendererComponent(list, "foo", 0, true, false);
        assertNotNull("c6 not null", c6);
        Component c7 = listCellRenderer.getListCellRendererComponent(list, "foo", 0, false, false);
        assertNotNull("c7 not null", c7);

        ExampleIdentifiable exampleIdentifiable = new ExampleIdentifiable("example", TangoProject.TEXT_X_GENERIC);
        Component c8 = listCellRenderer.getListCellRendererComponent(list, exampleIdentifiable, 0, true, true);
        assertNotNull("c8 not null", c8);
        Component c9 = listCellRenderer.getListCellRendererComponent(list, exampleIdentifiable, 0, false, true);
        assertNotNull("c9 not null", c9);
        Component c10 = listCellRenderer.getListCellRendererComponent(list, exampleIdentifiable, 0, true, false);
        assertNotNull("c10 not null", c10);
        Component c11 = listCellRenderer.getListCellRendererComponent(list, exampleIdentifiable, 0, false, false);
        assertNotNull("c11 not null", c11);

        ExampleWithNameProperty exampleWithNameProperty = new ExampleWithNameProperty("example");
        Component c12 = listCellRenderer.getListCellRendererComponent(list, exampleWithNameProperty, 0, true, true);
        assertNotNull("c12 not null", c12);
        Component c13 = listCellRenderer.getListCellRendererComponent(list, exampleWithNameProperty, 0, false, true);
        assertNotNull("c13 not null", c13);
        Component c14 = listCellRenderer.getListCellRendererComponent(list, exampleWithNameProperty, 0, true, false);
        assertNotNull("c14 not null", c14);
        Component c15 = listCellRenderer.getListCellRendererComponent(list, exampleWithNameProperty, 0, false, false);
        assertNotNull("c15 not null", c15);

        Component c16 = listCellRenderer.getListCellRendererComponent(list, null, 0, true, true);
        assertNotNull("c16 not null", c16);
        Component c17 = listCellRenderer.getListCellRendererComponent(list, null, 0, false, true);
        assertNotNull("c17 not null", c17);
        Component c18 = listCellRenderer.getListCellRendererComponent(list, null, 0, true, false);
        assertNotNull("c18 not null", c18);
        Component c19 = listCellRenderer.getListCellRendererComponent(list, null, 0, false, false);
        assertNotNull("c19 not null", c19);

        IdentifyUtils.getInstance().setDefaultIconBundle(oldDefaultIconBundle);
    }
}