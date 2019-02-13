/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2019 held jointly by the individual authors.

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

import javax.swing.JTable;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdTableCellRenderer.
 *
 * @author  Michael Heuer
 */
public final class IdTableCellRendererTest
    extends TestCase
{

    public void testConstructor()
    {
        IdTableCellRenderer tableCellRenderer0 = new IdTableCellRenderer();
        IdTableCellRenderer tableCellRenderer1 = new IdTableCellRenderer(IconSize.DEFAULT_16X16);

        try
        {
            IdTableCellRenderer tableCellRenderer = new IdTableCellRenderer(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconSize()
    {
        assertNotNull("DEFAULT_ICON_SIZE not null", IdTableCellRenderer.DEFAULT_ICON_SIZE);

        IdTableCellRenderer tableCellRenderer0 = new IdTableCellRenderer();
        assertEquals(IdTableCellRenderer.DEFAULT_ICON_SIZE, tableCellRenderer0.getIconSize());
        tableCellRenderer0.setIconSize(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, tableCellRenderer0.getIconSize());

        IdTableCellRenderer tableCellRenderer1 = new IdTableCellRenderer(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, tableCellRenderer1.getIconSize());
        tableCellRenderer1.setIconSize(IdTableCellRenderer.DEFAULT_ICON_SIZE);
        assertEquals(IdTableCellRenderer.DEFAULT_ICON_SIZE, tableCellRenderer1.getIconSize());

        // todo:  test property change
    }

    public void testIdTableCellRenderer()
    {
        JTable table = new JTable(new Object[][] {{ "Foo" }, { "Bar" }}, new Object[] { "Baz" });
        IdTableCellRenderer tableCellRenderer = new IdTableCellRenderer();
        IconBundle oldDefaultIconBundle = IdentifyUtils.getInstance().getDefaultIconBundle();
        IdentifyUtils.getInstance().setDefaultIconBundle(TangoProject.TEXT_X_GENERIC);

        Component c0 = tableCellRenderer.getTableCellRendererComponent(table, null, true, true, 0, 0);
        assertNotNull("c0 not null", c0);
        Component c1 = tableCellRenderer.getTableCellRendererComponent(table, null, false, true, 0, 0);
        assertNotNull("c1 not null", c1);
        Component c2 = tableCellRenderer.getTableCellRendererComponent(table, null, true, false, 0, 0);
        assertNotNull("c2 not null", c2);
        Component c3 = tableCellRenderer.getTableCellRendererComponent(table, null, false, false, 0, 0);
        assertNotNull("c3 not null", c3);

        Component c4 = tableCellRenderer.getTableCellRendererComponent(table, "foo", true, true, 0, 0);
        assertNotNull("c4 not null", c4);
        Component c5 = tableCellRenderer.getTableCellRendererComponent(table, "foo", false, true, 0, 0);
        assertNotNull("c5 not null", c5);
        Component c6 = tableCellRenderer.getTableCellRendererComponent(table, "foo", true, false, 0, 0);
        assertNotNull("c6 not null", c6);
        Component c7 = tableCellRenderer.getTableCellRendererComponent(table, "foo", false, false, 0, 0);
        assertNotNull("c7 not null", c7);

        ExampleIdentifiable exampleIdentifiable = new ExampleIdentifiable("example", TangoProject.TEXT_X_GENERIC);
        Component c8 = tableCellRenderer.getTableCellRendererComponent(table, exampleIdentifiable, true, true, 0, 0);
        assertNotNull("c8 not null", c8);
        Component c9 = tableCellRenderer.getTableCellRendererComponent(table, exampleIdentifiable, false, true, 0, 0);
        assertNotNull("c9 not null", c9);
        Component c10 = tableCellRenderer.getTableCellRendererComponent(table, exampleIdentifiable, true, false, 0, 0);
        assertNotNull("c10 not null", c10);
        Component c11 = tableCellRenderer.getTableCellRendererComponent(table, exampleIdentifiable, false, false, 0, 0);
        assertNotNull("c11 not null", c11);

        ExampleWithNameProperty exampleWithNameProperty = new ExampleWithNameProperty("example");
        Component c12 = tableCellRenderer.getTableCellRendererComponent(table, exampleWithNameProperty, true, true, 0, 0);
        assertNotNull("c12 not null", c12);
        Component c13 = tableCellRenderer.getTableCellRendererComponent(table, exampleWithNameProperty, false, true, 0, 0);
        assertNotNull("c13 not null", c13);
        Component c14 = tableCellRenderer.getTableCellRendererComponent(table, exampleWithNameProperty, true, false, 0, 0);
        assertNotNull("c14 not null", c14);
        Component c15 = tableCellRenderer.getTableCellRendererComponent(table, exampleWithNameProperty, false, false, 0, 0);
        assertNotNull("c15 not null", c15);

        Component c16 = tableCellRenderer.getTableCellRendererComponent(table, null, true, true, 0, 0);
        assertNotNull("c16 not null", c16);
        Component c17 = tableCellRenderer.getTableCellRendererComponent(table, null, false, true, 0, 0);
        assertNotNull("c17 not null", c17);
        Component c18 = tableCellRenderer.getTableCellRendererComponent(table, null, true, false, 0, 0);
        assertNotNull("c18 not null", c18);
        Component c19 = tableCellRenderer.getTableCellRendererComponent(table, null, false, false, 0, 0);
        assertNotNull("c19 not null", c19);

        IdentifyUtils.getInstance().setDefaultIconBundle(oldDefaultIconBundle);
    }
}
