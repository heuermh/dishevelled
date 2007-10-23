/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2007 held jointly by the individual authors.

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

import java.awt.image.BufferedImage;

import java.util.Locale;

import javax.swing.Action;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdMenuItem.
 *
 * @author  Michael Heuer
 * @version $Revision: 95 $ $Date: 2006-09-26 15:08:21 -0500 (Tue, 26 Sep 2006) $
 */
public final class IdMenuItemTest
    extends TestCase
{
    /** Action. */
    private IdentifiableAction action;


    /** @see TestCase */
    protected void setUp()
    {
        action = new IdentifiableAction("name", TangoProject.TEXT_X_GENERIC)
            {
                /** @see IdentifiableAction */
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };
    }

    public void testConstructor()
    {
        IdMenuItem menuItem0 = new IdMenuItem(action);
        IdMenuItem menuItem1 = new IdMenuItem(action, TangoProject.SMALL);

        try
        {
            IdMenuItem nullAction = new IdMenuItem(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IdMenuItem nullAction = new IdMenuItem(null, TangoProject.SMALL);
            fail("ctr(null, TangoProject.SMALL) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IdMenuItem nullSize = new IdMenuItem(action, null);
            fail("ctr(action, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    public void testIconSize()
    {
        assertNotNull("DEFAULT_ICON_SIZE not null", IdMenuItem.DEFAULT_ICON_SIZE);

        IdMenuItem menuItem0 = new IdMenuItem(action);
        assertEquals(IdMenuItem.DEFAULT_ICON_SIZE, menuItem0.getIconSize());
        menuItem0.setIconSize(IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, menuItem0.getIconSize());

        IdMenuItem menuItem1 = new IdMenuItem(action, IconSize.DEFAULT_32X32);
        assertEquals(IconSize.DEFAULT_32X32, menuItem1.getIconSize());
        menuItem1.setIconSize(IdMenuItem.DEFAULT_ICON_SIZE);
        assertEquals(IdMenuItem.DEFAULT_ICON_SIZE, menuItem1.getIconSize());

        try
        {
            menuItem0.setIconSize(null);
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
        IdMenuItem menuItem0 = new IdMenuItem(action);
        ComponentOrientation orientation = menuItem0.getComponentOrientation();

        if (orientation.isLeftToRight())
        {
            assertEquals(IconTextDirection.LEFT_TO_RIGHT, menuItem0.getIconTextDirection());
        }
        else
        {
            assertEquals(IconTextDirection.RIGHT_TO_LEFT, menuItem0.getIconTextDirection());
        }

        // us english is left-to-right
        ComponentOrientation leftToRight = ComponentOrientation.getOrientation(Locale.US);

        IdMenuItem menuItem1 = new IdMenuItem(action);
        menuItem1.setComponentOrientation(leftToRight);
        assertEquals(IconTextDirection.LEFT_TO_RIGHT, menuItem1.getIconTextDirection());

        IdMenuItem menuItem2 = new IdMenuItem(action);
        menuItem2.applyComponentOrientation(leftToRight);
        assertEquals(IconTextDirection.LEFT_TO_RIGHT, menuItem2.getIconTextDirection());

        // arabic is right-to-left
        ComponentOrientation rightToLeft = ComponentOrientation.getOrientation(new Locale("ar"));

        IdMenuItem menuItem3 = new IdMenuItem(action);
        menuItem3.setComponentOrientation(rightToLeft);
        assertEquals(IconTextDirection.RIGHT_TO_LEFT, menuItem3.getIconTextDirection());

        IdMenuItem menuItem4 = new IdMenuItem(action);
        menuItem4.applyComponentOrientation(rightToLeft);
        assertEquals(IconTextDirection.RIGHT_TO_LEFT, menuItem4.getIconTextDirection());

        IdMenuItem menuItem5 = new IdMenuItem(action);
        menuItem5.setComponentOrientation(null);

        try
        {
            IdMenuItem menuItem = new IdMenuItem(action);
            menuItem.applyComponentOrientation(null);
            fail("applyComponentOrientation(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testText()
    {
        IdMenuItem menuItem = new IdMenuItem(action);
        assertEquals(action.getName(), menuItem.getText());

        // todo:  test text changes when name for action changes
    }

    public void testIcon()
    {
        IdMenuItem menuItem0 = new IdMenuItem(action);
        assertNotNull("menuItem0 icon not null", menuItem0.getIcon());

        IdMenuItem menuItem1 = new IdMenuItem(action, TangoProject.SMALL);
        assertNotNull("menuItem1 icon not null", menuItem1.getIcon());

        // todo:  test icon changes when icon bundle for action changes
    }

    public void testPressedIcon()
    {
        IdMenuItem menuItem0 = new IdMenuItem(action);
        assertNotNull("menuItem0 pressedIcon not null", menuItem0.getPressedIcon());

        IdMenuItem menuItem1 = new IdMenuItem(action, TangoProject.SMALL);
        assertNotNull("menuItem1 pressedIcon not null", menuItem1.getPressedIcon());
    }

    public void testSelectedIcon()
    {
        IdMenuItem menuItem0 = new IdMenuItem(action);
        assertNotNull("menuItem0 selectedIcon not null", menuItem0.getSelectedIcon());

        IdMenuItem menuItem1 = new IdMenuItem(action, TangoProject.SMALL);
        assertNotNull("menuItem1 selectedIcon not null", menuItem1.getSelectedIcon());
    }

    public void testRolloverIcon()
    {
        IdMenuItem menuItem0 = new IdMenuItem(action);
        assertNotNull("menuItem0 rolloverIcon not null", menuItem0.getRolloverIcon());

        IdMenuItem menuItem1 = new IdMenuItem(action, TangoProject.SMALL);
        assertNotNull("menuItem1 rolloverIcon not null", menuItem1.getRolloverIcon());
    }

    public void testRolloverSelectedIcon()
    {
        IdMenuItem menuItem0 = new IdMenuItem(action);
        assertNotNull("menuItem0 rolloverSelectedIcon not null", menuItem0.getRolloverSelectedIcon());

        IdMenuItem menuItem1 = new IdMenuItem(action, TangoProject.SMALL);
        assertNotNull("menuItem1 rolloverSelectedIcon not null", menuItem1.getRolloverSelectedIcon());
    }

    public void testDisabledIcon()
    {
        IdMenuItem menuItem0 = new IdMenuItem(action);
        assertNotNull("menuItem0 disabledIcon not null", menuItem0.getDisabledIcon());

        IdMenuItem menuItem1 = new IdMenuItem(action, TangoProject.SMALL);
        assertNotNull("menuItem1 disabledIcon not null", menuItem1.getDisabledIcon());
    }

    // todo:  bounds tests
}