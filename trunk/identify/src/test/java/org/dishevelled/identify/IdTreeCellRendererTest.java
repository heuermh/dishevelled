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

import java.awt.Component;

import javax.swing.JTree;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdTreeCellRenderer.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdTreeCellRendererTest
    extends TestCase
{

    public void testConstructor()
    {
        IdTreeCellRenderer treeCellRenderer0 = new IdTreeCellRenderer();
        IdTreeCellRenderer treeCellRenderer1 = new IdTreeCellRenderer(IconSize.DEFAULT_16X16);

        try
        {
            IdTreeCellRenderer treeCellRenderer = new IdTreeCellRenderer(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconSize()
    {
        assertNotNull("DEFAULT_ICON_SIZE not null", IdTreeCellRenderer.DEFAULT_ICON_SIZE);

        IdTreeCellRenderer treeCellRenderer0 = new IdTreeCellRenderer();
        assertEquals(IdTreeCellRenderer.DEFAULT_ICON_SIZE, treeCellRenderer0.getIconSize());
        treeCellRenderer0.setIconSize(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, treeCellRenderer0.getIconSize());

        IdTreeCellRenderer treeCellRenderer1 = new IdTreeCellRenderer(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, treeCellRenderer1.getIconSize());
        treeCellRenderer1.setIconSize(IdTreeCellRenderer.DEFAULT_ICON_SIZE);
        assertEquals(IdTreeCellRenderer.DEFAULT_ICON_SIZE, treeCellRenderer1.getIconSize());

        // todo:  test property change
    }

    public void testIdTreeCellRenderer()
    {
        JTree tree = new JTree();
        IdTreeCellRenderer treeCellRenderer = new IdTreeCellRenderer();
        IconBundle oldDefaultIconBundle = IdentifyUtils.getInstance().getDefaultIconBundle();
        IdentifyUtils.getInstance().setDefaultIconBundle(TangoProject.TEXT_X_GENERIC);

        Component c01 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, true, 0, true);
        assertNotNull("c01 not null", c01);
        Component c02 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, true, true, 0, true);
        assertNotNull("c02 not null", c02);
        Component c03 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, false, true, 0, true);
        assertNotNull("c03 not null", c03);
        Component c04 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, false, 0, true);
        assertNotNull("c04 not null", c04);
        Component c05 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, true, 0, false);
        assertNotNull("c05 not null", c05);
        Component c06 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, false, true, 0, true);
        assertNotNull("c06 not null", c06);
        Component c07 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, false, false, 0, true);
        assertNotNull("c07 not null", c07);
        Component c08 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, false, 0, false);
        assertNotNull("c08 not null", c08);
        Component c09 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, false, false, 0, true);
        assertNotNull("c09 not null", c09);
        Component c10 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, false, false, 0, false);
        assertNotNull("c10 not null", c10);
        Component c11 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, false, false, 0, false);
        assertNotNull("c11 not null", c11);

        Component c12 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", true, true, true, 0, true);
        assertNotNull("c12 not null", c12);
        Component c13 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", false, true, true, 0, true);
        assertNotNull("c13 not null", c13);
        Component c14 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", true, false, true, 0, true);
        assertNotNull("c14 not null", c14);
        Component c15 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", true, true, false, 0, true);
        assertNotNull("c15 not null", c15);
        Component c16 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", true, true, true, 0, false);
        assertNotNull("c16 not null", c16);
        Component c17 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", false, false, true, 0, true);
        assertNotNull("c17 not null", c17);
        Component c18 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", true, false, false, 0, true);
        assertNotNull("c18 not null", c18);
        Component c19 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", true, true, false, 0, false);
        assertNotNull("c19 not null", c19);
        Component c20 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", false, false, false, 0, true);
        assertNotNull("c20 not null", c20);
        Component c21 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", true, false, false, 0, false);
        assertNotNull("c21 not null", c21);
        Component c22 = treeCellRenderer.getTreeCellRendererComponent(tree, "foo", false, false, false, 0, false);
        assertNotNull("c22 not null", c22);

        ExampleIdentifiable exampleIdentifiable = new ExampleIdentifiable("example", TangoProject.TEXT_X_GENERIC);
        Component c23 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, true, true, true, 0, true);
        assertNotNull("c23 not null", c23);
        Component c24 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, false, true, true, 0, true);
        assertNotNull("c24 not null", c24);
        Component c25 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, true, false, true, 0, true);
        assertNotNull("c25 not null", c25);
        Component c26 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, true, true, false, 0, true);
        assertNotNull("c26 not null", c26);
        Component c27 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, true, true, true, 0, false);
        assertNotNull("c27 not null", c27);
        Component c28 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, false, false, true, 0, true);
        assertNotNull("c28 not null", c28);
        Component c29 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, true, false, false, 0, true);
        assertNotNull("c29 not null", c29);
        Component c30 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, true, true, false, 0, false);
        assertNotNull("c30 not null", c30);
        Component c31 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, false, false, false, 0, true);
        assertNotNull("c31 not null", c31);
        Component c32 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, true, false, false, 0, false);
        assertNotNull("c32 not null", c32);
        Component c33 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleIdentifiable, false, false, false, 0, false);
        assertNotNull("c33 not null", c33);

        ExampleWithNameProperty exampleWithNameProperty = new ExampleWithNameProperty("example");
        Component c34 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, true, true, true, 0, true);
        assertNotNull("c34 not null", c34);
        Component c35 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, false, true, true, 0, true);
        assertNotNull("c35 not null", c35);
        Component c36 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, true, false, true, 0, true);
        assertNotNull("c36 not null", c36);
        Component c37 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, true, true, false, 0, true);
        assertNotNull("c37 not null", c37);
        Component c38 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, true, true, true, 0, false);
        assertNotNull("c38 not null", c38);
        Component c39 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, false, false, true, 0, true);
        assertNotNull("c39 not null", c39);
        Component c40 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, true, false, false, 0, true);
        assertNotNull("c40 not null", c40);
        Component c41 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, true, true, false, 0, false);
        assertNotNull("c41 not null", c41);
        Component c42 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, false, false, false, 0, true);
        assertNotNull("c42 not null", c42);
        Component c43 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, true, false, false, 0, false);
        assertNotNull("c43 not null", c43);
        Component c44 = treeCellRenderer.getTreeCellRendererComponent(tree, exampleWithNameProperty, false, false, false, 0, false);
        assertNotNull("c44 not null", c44);

        Component c45 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, true, 0, true);
        assertNotNull("c45 not null", c45);
        Component c46 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, true, true, 0, true);
        assertNotNull("c46 not null", c46);
        Component c47 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, false, true, 0, true);
        assertNotNull("c47 not null", c47);
        Component c48 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, false, 0, true);
        assertNotNull("c48 not null", c48);
        Component c49 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, true, 0, false);
        assertNotNull("c49 not null", c49);
        Component c50 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, false, true, 0, true);
        assertNotNull("c50 not null", c50);
        Component c51 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, false, false, 0, true);
        assertNotNull("c51 not null", c51);
        Component c52 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, true, false, 0, false);
        assertNotNull("c52 not null", c52);
        Component c53 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, false, false, 0, true);
        assertNotNull("c53 not null", c53);
        Component c54 = treeCellRenderer.getTreeCellRendererComponent(tree, null, true, false, false, 0, false);
        assertNotNull("c54 not null", c54);
        Component c55 = treeCellRenderer.getTreeCellRendererComponent(tree, null, false, false, false, 0, false);
        assertNotNull("c55 not null", c55);

        IdentifyUtils.getInstance().setDefaultIconBundle(oldDefaultIconBundle);
    }
}