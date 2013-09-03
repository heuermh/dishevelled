/*

    dsh-identify  Lightweight components for identifiable beans.
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
package org.dishevelled.identify;

import java.awt.ComponentOrientation;

import java.awt.event.ActionEvent;

import java.util.Locale;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconTextDirection;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdToggleButton.
 *
 * @author  Michael Heuer
 */
public final class IdToggleButtonTest
    extends TestCase
{
    /** Action. */
    private IdentifiableAction action;


    @Override
    protected void setUp()
    {
        action = new IdentifiableAction("name", TangoProject.TEXT_X_GENERIC)
            {
                @Override
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };
    }

    public void testConstructor()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        IdToggleButton button1 = new IdToggleButton(action, TangoProject.SMALL);

        try
        {
            IdToggleButton nullAction = new IdToggleButton(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IdToggleButton nullAction = new IdToggleButton(null, TangoProject.SMALL);
            fail("ctr(null, TangoProject.SMALL) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IdToggleButton nullSize = new IdToggleButton(action, null);
            fail("ctr(action, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    public void testIconSize()
    {
        assertNotNull("DEFAULT_ICON_SIZE not null", IdToggleButton.DEFAULT_ICON_SIZE);

        IdToggleButton button0 = new IdToggleButton(action);
        assertEquals(IdToggleButton.DEFAULT_ICON_SIZE, button0.getIconSize());
        button0.setIconSize(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, button0.getIconSize());

        IdToggleButton button1 = new IdToggleButton(action, IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, button1.getIconSize());
        button1.setIconSize(IdToggleButton.DEFAULT_ICON_SIZE);
        assertEquals(IdToggleButton.DEFAULT_ICON_SIZE, button1.getIconSize());

        try
        {
            button0.setIconSize(null);
            fail("setIconSize(null) expectedIllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // todo:  test property change
    }

    public void testTextDirection()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        ComponentOrientation orientation = button0.getComponentOrientation();

        if (orientation.isLeftToRight())
        {
            assertEquals(IconTextDirection.LEFT_TO_RIGHT, button0.getIconTextDirection());
        }
        else
        {
            assertEquals(IconTextDirection.RIGHT_TO_LEFT, button0.getIconTextDirection());
        }

        // us english is left-to-right
        ComponentOrientation leftToRight = ComponentOrientation.getOrientation(Locale.US);

        IdToggleButton button1 = new IdToggleButton(action);
        button1.setComponentOrientation(leftToRight);
        assertEquals(IconTextDirection.LEFT_TO_RIGHT, button1.getIconTextDirection());

        IdToggleButton button2 = new IdToggleButton(action);
        button2.applyComponentOrientation(leftToRight);
        assertEquals(IconTextDirection.LEFT_TO_RIGHT, button2.getIconTextDirection());

        // arabic is right-to-left
        ComponentOrientation rightToLeft = ComponentOrientation.getOrientation(new Locale("ar"));

        IdToggleButton button3 = new IdToggleButton(action);
        button3.setComponentOrientation(rightToLeft);
        assertEquals(IconTextDirection.RIGHT_TO_LEFT, button3.getIconTextDirection());

        IdToggleButton button4 = new IdToggleButton(action);
        button4.applyComponentOrientation(rightToLeft);
        assertEquals(IconTextDirection.RIGHT_TO_LEFT, button4.getIconTextDirection());

        IdToggleButton button5 = new IdToggleButton(action);
        button5.setComponentOrientation(null);

        try
        {
            IdToggleButton button = new IdToggleButton(action);
            button.applyComponentOrientation(null);
            fail("applyComponentOrientation(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testText()
    {
        IdToggleButton button = new IdToggleButton(action);
        assertEquals(action.getName(), button.getText());

        // todo:  test text changes when name for action changes
    }

    public void testIcon()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        assertNotNull("button0 icon not null", button0.getIcon());

        IdToggleButton button1 = new IdToggleButton(action, TangoProject.SMALL);
        assertNotNull("button1 icon not null", button1.getIcon());

        // todo:  test icon changes when icon bundle for action changes
    }

    public void testPressedIcon()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        assertNotNull("button0 pressedIcon not null", button0.getPressedIcon());

        IdToggleButton button1 = new IdToggleButton(action, TangoProject.SMALL);
        assertNotNull("button1 pressedIcon not null", button1.getPressedIcon());
    }

    public void testSelectedIcon()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        assertNotNull("button0 selectedIcon not null", button0.getSelectedIcon());

        IdToggleButton button1 = new IdToggleButton(action, TangoProject.SMALL);
        assertNotNull("button1 selectedIcon not null", button1.getSelectedIcon());
    }

    public void testRolloverIcon()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        assertNotNull("button0 rolloverIcon not null", button0.getRolloverIcon());

        IdToggleButton button1 = new IdToggleButton(action, TangoProject.SMALL);
        assertNotNull("button1 rolloverIcon not null", button1.getRolloverIcon());
    }

    public void testRolloverSelectedIcon()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        assertNotNull("button0 rolloverSelectedIcon not null", button0.getRolloverSelectedIcon());

        IdToggleButton button1 = new IdToggleButton(action, TangoProject.SMALL);
        assertNotNull("button1 rolloverSelectedIcon not null", button1.getRolloverSelectedIcon());
    }

    public void testDisabledIcon()
    {
        IdToggleButton button0 = new IdToggleButton(action);
        assertNotNull("button0 disabledIcon not null", button0.getDisabledIcon());

        IdToggleButton button1 = new IdToggleButton(action, TangoProject.SMALL);
        assertNotNull("button1 disabledIcon not null", button1.getDisabledIcon());
    }

    public void testDisplayIcon()
    {
        IdToggleButton button = new IdToggleButton(action);
        assertNotNull(button.getIcon());
        assertEquals(action.getName(), button.getText());

        button.displayIcon();
        assertNotNull(button.getIcon());
        assertEquals(null, button.getText());
    }

    public void testDisplayText()
    {
        IdToggleButton button = new IdToggleButton(action);
        assertNotNull(button.getIcon());
        assertEquals(action.getName(), button.getText());

        button.displayText();
        assertNull(button.getIcon());
        assertEquals(action.getName(), button.getText());
    }

    public void testDisplayIconAndText()
    {
        IdToggleButton button = new IdToggleButton(action);
        assertNotNull(button.getIcon());
        assertEquals(action.getName(), button.getText());

        button.displayIconAndText();
        assertNotNull(button.getIcon());
        assertEquals(action.getName(), button.getText());

        button.displayIcon();
        assertNotNull(button.getIcon());
        assertEquals(null, button.getText());

        button.displayIconAndText();
        assertNotNull(button.getIcon());
        assertEquals(action.getName(), button.getText());

        button.displayText();
        assertNull(button.getIcon());
        assertEquals(action.getName(), button.getText());

        button.displayIconAndText();
        assertNotNull(button.getIcon());
        assertEquals(action.getName(), button.getText());
    }

    // todo:  bounds tests
}