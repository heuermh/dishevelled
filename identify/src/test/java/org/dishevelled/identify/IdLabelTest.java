/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2006 held jointly by the individual authors.

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
package org.dishevelled.identify;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdLabel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdLabelTest
    extends TestCase
{

    public void testConstructor()
    {
        IdLabel label0 = new IdLabel();
        IdLabel label1 = new IdLabel(null);
        IdLabel label2 = new IdLabel("name");
        IdLabel label3 = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        IdLabel label4 = new IdLabel(new ExampleWithNameProperty("name"));

        IdLabel label5 = new IdLabel(null, IconSize.DEFAULT_32X32);
        IdLabel label6 = new IdLabel("name", IconSize.DEFAULT_32X32);
        IdLabel label7 = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IconSize.DEFAULT_32X32);
        IdLabel label8 = new IdLabel(new ExampleWithNameProperty("name"), IconSize.DEFAULT_32X32);

        try
        {
            IdLabel label = new IdLabel(null, null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconSize()
    {
        assertNotNull("DEFAULT_ICON_SIZE not null", IdLabel.DEFAULT_ICON_SIZE);

        IdLabel label0 = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label0.getIconSize());
        label0.setIconSize(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, label0.getIconSize());

        IdLabel label1 = new IdLabel(null, IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, label1.getIconSize());
        label1.setIconSize(IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label1.getIconSize());

        try
        {
            label0.setIconSize(null);
            fail("setIconSize(null) expectedIllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // todo:  test property change
    }

    public void testIconState()
    {
        IdLabel label0 = new IdLabel(null);
        assertNotNull("label0 iconState not null", label0.getIconState());
        IconState oldIconState = label0.getIconState();
        label0.setIconState(IconState.DRAGGING);
        assertEquals(IconState.DRAGGING, label0.getIconState());
        label0.setIconState(oldIconState);
        assertEquals(oldIconState, label0.getIconState());

        IdLabel label1 = new IdLabel("name");
        assertNotNull("label1 iconState not null", label1.getIconState());
        oldIconState = label1.getIconState();
        label1.setIconState(IconState.DRAGGING);
        assertEquals(IconState.DRAGGING, label1.getIconState());
        label1.setIconState(oldIconState);
        assertEquals(oldIconState, label1.getIconState());

        IdLabel label2 = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertNotNull("label2 iconState not null", label2.getIconState());
        oldIconState = label2.getIconState();
        label2.setIconState(IconState.DRAGGING);
        assertEquals(IconState.DRAGGING, label2.getIconState());
        label2.setIconState(oldIconState);
        assertEquals(oldIconState, label2.getIconState());

        IdLabel label3 = new IdLabel(new ExampleWithNameProperty("name"));
        assertNotNull("label3 iconState not null", label3.getIconState());
        oldIconState = label3.getIconState();
        label3.setIconState(IconState.DRAGGING);
        assertEquals(IconState.DRAGGING, label3.getIconState());
        label3.setIconState(oldIconState);
        assertEquals(oldIconState, label3.getIconState());

        try
        {
            label0.setIconState(null);
            fail("setIconState(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // todo:  test property change
    }

    public void testText()
    {
        IdLabel label0 = new IdLabel();
        assertEquals("null", label0.getText());

        IdLabel label1 = new IdLabel(null);
        assertEquals("null", label1.getText());

        IdLabel label2 = new IdLabel("name");
        assertEquals("name", label2.getText());

        IdLabel label3 = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals("name", label3.getText());

        IdLabel label4 = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals("name", label4.getText());

        IdLabel label5 = new IdLabel();
        assertEquals("null", label5.getText());

        label5.setValue("name0");
        assertEquals("name0", label5.getText());

        label5.setValue(new ExampleIdentifiable("name1", TangoProject.TEXT_X_GENERIC));
        assertEquals("name1", label5.getText());

        label5.setValue(new ExampleWithNameProperty("name2"));
        assertEquals("name2", label5.getText());

        label5.setValue(null);
        assertEquals("null", label5.getText());
    }

    public void testIcon()
    {
        IdLabel label0 = new IdLabel();
        assertEquals(null, label0.getIcon());
        assertEquals(null, label0.getDisabledIcon());

        IdLabel label1 = new IdLabel(null);
        assertEquals(null, label1.getIcon());
        assertEquals(null, label1.getDisabledIcon());

        IdLabel label2 = new IdLabel("name");
        assertNotNull(label2.getIcon());
        assertNotNull(label2.getDisabledIcon());

        IdLabel label3 = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertNotNull(label3.getIcon());
        assertNotNull(label3.getDisabledIcon());

        IdLabel label4 = new IdLabel(new ExampleWithNameProperty("name"));
        assertNotNull(label3.getIcon());
        assertNotNull(label3.getDisabledIcon());

        IdLabel label5 = new IdLabel();
        assertEquals(null, label5.getIcon());
        assertEquals(null, label5.getDisabledIcon());

        label5.setValue("name");
        assertNotNull(label5.getIcon());
        assertNotNull(label5.getDisabledIcon());

        label5.setValue(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertNotNull(label5.getIcon());
        assertNotNull(label5.getDisabledIcon());

        label5.setValue(new ExampleWithNameProperty("name"));
        assertNotNull(label5.getIcon());
        assertNotNull(label5.getDisabledIcon());

        label5.setValue(null);
        assertEquals(null, label5.getIcon());
        assertEquals(null, label5.getDisabledIcon());
    }
}