/*

    dsh-identify  Lightweight components for identifiable beans.
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
package org.dishevelled.identify;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.util.Locale;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

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
        label0.setIconSize(IconSize.DEFAULT_16X16);
        assertEquals(IconSize.DEFAULT_16X16, label0.getIconSize());

        IdLabel label1 = new IdLabel(null, IconSize.DEFAULT_16X16);
        assertEquals(IconSize.DEFAULT_16X16, label1.getIconSize());
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
        assertNotNull("DEFAULT_ICON_STATE not null", IdLabel.DEFAULT_ICON_STATE);

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

        IdLabel label1 = new IdLabel(null);
        assertEquals(null, label1.getIcon());

        IdLabel label2 = new IdLabel("name");
        assertNotNull(label2.getIcon());

        IdLabel label3 = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertNotNull(label3.getIcon());

        IdLabel label4 = new IdLabel(new ExampleWithNameProperty("name"));
        assertNotNull(label3.getIcon());

        IdLabel label5 = new IdLabel();
        assertEquals(null, label5.getIcon());

        label5.setValue("name");
        assertNotNull(label5.getIcon());

        label5.setValue(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertNotNull(label5.getIcon());

        label5.setValue(new ExampleWithNameProperty("name"));
        assertNotNull(label5.getIcon());

        label5.setValue(null);
        assertEquals(null, label5.getIcon());
    }

    public void testDisabledIcon()
    {
        IdLabel label0 = new IdLabel();
        assertEquals(null, label0.getDisabledIcon());

        IdLabel label1 = new IdLabel(null);
        assertEquals(null, label1.getDisabledIcon());

        IdLabel label2 = new IdLabel("name");
        assertNotNull(label2.getDisabledIcon());

        IdLabel label3 = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertNotNull(label3.getDisabledIcon());

        IdLabel label4 = new IdLabel(new ExampleWithNameProperty("name"));
        assertNotNull(label3.getDisabledIcon());

        IdLabel label5 = new IdLabel();
        assertEquals(null, label5.getDisabledIcon());

        label5.setValue("name");
        assertNotNull(label5.getDisabledIcon());

        label5.setValue(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertNotNull(label5.getDisabledIcon());

        label5.setValue(new ExampleWithNameProperty("name"));
        assertNotNull(label5.getDisabledIcon());

        label5.setValue(null);
        assertEquals(null, label5.getDisabledIcon());
    }

    public void testEnabledDisabled()
    {
        IdLabel label = new IdLabel();

        // icon state should be default on construction
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());

        // disable --> DISABLED icon state
        label.setEnabled(false);
        assertEquals(IconState.DISABLED, label.getIconState());

        // re-enable --> previous icon state (default)
        label.setEnabled(true);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());


        // set icon state to something other than default
        label.setIconState(IconState.DRAGGING);
        assertEquals(IconState.DRAGGING, label.getIconState());

        // disable --> DISABLED icon state
        label.setEnabled(false);
        assertEquals(IconState.DISABLED, label.getIconState());

        // re-enable --> previous icon state (dragging)
        label.setEnabled(true);
        assertEquals(IconState.DRAGGING, label.getIconState());


        // set icon state back to default
        label.setIconState(IdLabel.DEFAULT_ICON_STATE);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());

        // disable --> DISABLED icon state
        label.setEnabled(false);
        assertEquals(IconState.DISABLED, label.getIconState());

        // set icon state to something other than disabled, doesn't effect enabled status
        label.setIconState(IconState.MOUSEOVER);
        assertEquals(IconState.MOUSEOVER, label.getIconState());
        assertFalse(label.isEnabled());

        // re-enable --> previous icon state (default)
        label.setEnabled(true);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());


        // set icon state back to default
        label.setIconState(IdLabel.DEFAULT_ICON_STATE);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());

        // set icon state to disabled
        label.setIconState(IconState.DISABLED);
        assertEquals(IconState.DISABLED, label.getIconState());
        // icon state disabled doesn't mean component is disabled
        assertTrue(label.isEnabled());

        // disable --> DISABLED icon state
        label.setEnabled(false);
        assertEquals(IconState.DISABLED, label.getIconState());
        assertFalse(label.isEnabled());

        // re-enable --> previous icon state (disabled)
        label.setEnabled(true);
        assertEquals(IconState.DISABLED, label.getIconState());
        assertTrue(label.isEnabled());


        // set icon state back to default
        label.setIconState(IdLabel.DEFAULT_ICON_STATE);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());

        // set icon state to disabled twice
        label.setIconState(IconState.DISABLED);
        assertEquals(IconState.DISABLED, label.getIconState());

        label.setIconState(IconState.DISABLED);
        assertEquals(IconState.DISABLED, label.getIconState());

        // disable --> DISABLED icon state
        label.setEnabled(false);
        assertEquals(IconState.DISABLED, label.getIconState());
        assertFalse(label.isEnabled());

        // re-enable --> previous icon state (disabled)
        label.setEnabled(true);
        assertEquals(IconState.DISABLED, label.getIconState());
        assertTrue(label.isEnabled());


        // set icon state back to default
        label.setIconState(IdLabel.DEFAULT_ICON_STATE);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());

        // disable twice
        label.setEnabled(false);
        assertEquals(IconState.DISABLED, label.getIconState());

        label.setEnabled(false);
        assertEquals(IconState.DISABLED, label.getIconState());

        // re-enable twice
        label.setEnabled(true);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());

        label.setEnabled(true);
        assertEquals(IdLabel.DEFAULT_ICON_STATE, label.getIconState());
    }

    public void testTextDirection()
    {
        IdLabel label0 = new IdLabel();
        ComponentOrientation orientation = label0.getComponentOrientation();

        if (orientation.isLeftToRight())
        {
            assertEquals(IconTextDirection.LEFT_TO_RIGHT, label0.getIconTextDirection());
        }
        else
        {
            assertEquals(IconTextDirection.RIGHT_TO_LEFT, label0.getIconTextDirection());
        }

        // us english is left-to-right
        ComponentOrientation leftToRight = ComponentOrientation.getOrientation(Locale.US);

        IdLabel label1 = new IdLabel();
        label1.setComponentOrientation(leftToRight);
        assertEquals(IconTextDirection.LEFT_TO_RIGHT, label1.getIconTextDirection());

        IdLabel label2 = new IdLabel();
        label2.applyComponentOrientation(leftToRight);
        assertEquals(IconTextDirection.LEFT_TO_RIGHT, label2.getIconTextDirection());

        // arabic is right-to-left
        ComponentOrientation rightToLeft = ComponentOrientation.getOrientation(new Locale("ar"));

        IdLabel label3 = new IdLabel();
        label3.setComponentOrientation(rightToLeft);
        assertEquals(IconTextDirection.RIGHT_TO_LEFT, label3.getIconTextDirection());

        IdLabel label4 = new IdLabel();
        label4.applyComponentOrientation(rightToLeft);
        assertEquals(IconTextDirection.RIGHT_TO_LEFT, label4.getIconTextDirection());

        IdLabel label5 = new IdLabel();
        label5.setComponentOrientation(null);

        try
        {
            IdLabel label = new IdLabel();
            label.applyComponentOrientation(null);
            fail("applyComponentOrientation(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testBounds0000()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0001()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0002()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0003()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0004()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0005()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0006()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0007()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0008()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0009()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0010()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0011()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0012()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0013()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0014()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0015()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0016()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds());
    }

    public void testBounds0017()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds());
    }

    public void testBounds0018()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0019()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0020()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0021()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0022()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0023()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0024()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0025()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0026()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0027()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0028()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0029()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0030()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0031()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0032()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0033()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0034()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0035()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getBounds(new Rectangle());
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getBounds(new Rectangle()));
    }

    public void testBounds0036()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0037()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0038()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0039()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0040()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0041()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0042()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0043()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0044()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0045()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0046()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0047()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0048()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0049()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0050()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0051()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0052()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getHeight());
    }

    public void testBounds0053()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getHeight();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getHeight());
    }

    public void testBounds0054()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0055()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0056()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0057()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0058()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0059()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0060()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0061()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0062()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0063()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0064()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0065()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0066()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0067()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0068()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0069()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0070()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMaximumSize());
    }

    public void testBounds0071()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMaximumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMaximumSize());
    }

    public void testBounds0072()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0073()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0074()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0075()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0076()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0077()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0078()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0079()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0080()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0081()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0082()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0083()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0084()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0085()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0086()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0087()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0088()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getMinimumSize());
    }

    public void testBounds0089()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getMinimumSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getMinimumSize());
    }

    public void testBounds0090()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0091()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0092()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0093()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0094()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0095()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0096()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0097()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0098()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0099()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0100()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0101()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0102()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0103()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0104()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0105()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0106()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getPreferredSize());
    }

    public void testBounds0107()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getPreferredSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getPreferredSize());
    }

    public void testBounds0108()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0109()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0110()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0111()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0112()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0113()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0114()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0115()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0116()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0117()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0118()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0119()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0120()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0121()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0122()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0123()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0124()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getSize());
    }

    public void testBounds0125()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Dimension oldValue = label.getSize();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getSize());
    }

    public void testBounds0126()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0127()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0128()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0129()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0130()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0131()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0132()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0133()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0134()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0135()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0136()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0137()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0138()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0139()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0140()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0141()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0142()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getVisibleRect());
    }

    public void testBounds0143()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        Rectangle oldValue = label.getVisibleRect();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getVisibleRect());
    }

    public void testBounds0144()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0145()
    {
        IdLabel label = new IdLabel();
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0146()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0147()
    {
        IdLabel label = new IdLabel(null);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0148()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0149()
    {
        IdLabel label = new IdLabel(null, IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0150()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0151()
    {
        IdLabel label = new IdLabel("name");
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0152()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0153()
    {
        IdLabel label = new IdLabel("name", IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0154()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0155()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0156()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0157()
    {
        IdLabel label = new IdLabel(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0158()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0159()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"));
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }

    public void testBounds0160()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_64X64);
        assertSmallerOrSameSize(oldValue, label.getWidth());
    }

    public void testBounds0161()
    {
        IdLabel label = new IdLabel(new ExampleWithNameProperty("name"), IdLabel.DEFAULT_ICON_SIZE);
        assertEquals(IdLabel.DEFAULT_ICON_SIZE, label.getIconSize());
        int oldValue = label.getWidth();
        label.setIconSize(IconSize.DEFAULT_16X16);
        assertSameSizeOrLarger(oldValue, label.getWidth());
    }


    /**
     * Assert <code>rect0</code> is smaller than or the same size as <code>rect1</code>.
     *
     * @param rect0 first rectangle
     * @param rect1 second rectangle
     */
    private void assertSmallerOrSameSize(final Rectangle rect0, final Rectangle rect1)
    {
        assertTrue((rect0.getHeight() * rect0.getWidth()) <= (rect1.getHeight() * rect1.getWidth()));
    }

    /**
     * Assert <code>dimension0</code> is smaller than or the same size as <code>dimension1</code>.
     *
     * @param dimension0 first dimension
     * @param dimension1 second dimension
     */
    private void assertSmallerOrSameSize(final Dimension dimension0, final Dimension dimension1)
    {
        assertTrue((dimension0.getHeight() * dimension0.getWidth()) <= (dimension1.getHeight() * dimension1.getWidth()));
    }

    /**
     * Assert <code>length0</code> is smaller than or the same as <code>length1</code>.
     *
     * @param length0 first length
     * @param length1 second length
     */
    private void assertSameSizeOrLarger(final int length0, final int length1)
    {
        assertTrue(length0 <= length1);
    }

    /**
     * Assert <code>rect0</code> is the same size or larger than <code>rect1</code>.
     *
     * @param rect0 first rectangle
     * @param rect1 second rectangle
     */
    private void assertSameSizeOrLarger(final Rectangle rect0, final Rectangle rect1)
    {
        assertTrue((rect0.getHeight() * rect0.getWidth()) >= (rect1.getHeight() * rect1.getWidth()));
    }

    /**
     * Assert <code>dimension0</code> is the same size or larger than <code>dimension1</code>.
     *
     * @param dimension0 first dimension
     * @param dimension1 second dimension
     */
    private void assertSameSizeOrLarger(final Dimension dimension0, final Dimension dimension1)
    {
        assertTrue((dimension0.getHeight() * dimension0.getWidth()) >= (dimension1.getHeight() * dimension1.getWidth()));
    }

    /**
     * Assert <code>length0</code> is the same or larger than <code>length1</code>.
     *
     * @param length0 first length
     * @param length1 second length
     */
    private void assertSmallerOrSameSize(final int length0, final int length1)
    {
        assertTrue(length0 >= length1);
    }
}