/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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

import javax.swing.Action;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;

/**
 * Unit test for IdToolBar.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdToolBarTest
    extends TestCase
{

    public void testConstructor()
    {
        IdToolBar toolBar0 = new IdToolBar();
        assertNotNull(toolBar0);
        IdToolBar toolBar1 = new IdToolBar(null);
        assertNotNull(toolBar1);
        IdToolBar toolBar2 = new IdToolBar("");
        assertNotNull(toolBar2);
        IdToolBar toolBar3 = new IdToolBar("foo");
        assertNotNull(toolBar3);
        IdToolBar toolBar4 = new IdToolBar(IdToolBar.HORIZONTAL);
        assertNotNull(toolBar4);
        IdToolBar toolBar5 = new IdToolBar(IdToolBar.VERTICAL);
        assertNotNull(toolBar5);
        IdToolBar toolBar6 = new IdToolBar(null, IdToolBar.HORIZONTAL);
        assertNotNull(toolBar6);
        IdToolBar toolBar7 = new IdToolBar("", IdToolBar.HORIZONTAL);
        assertNotNull(toolBar7);
        IdToolBar toolBar8 = new IdToolBar("foo", IdToolBar.HORIZONTAL);
        assertNotNull(toolBar8);
    }

    public void testAddIdentifiableAction()
    {        
        IdToolBar toolBar = new IdToolBar();
        try
        {
            toolBar.add((IdentifiableAction) null);
            fail("add((IdentifiableAction) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testDefaultIconSizeActions()
    {
        IdToolBar toolBar = new IdToolBar();
        assertNotNull(toolBar.getDefaultIconSizeActions());
        assertEquals(IconSize.VALUES.size(), toolBar.getDefaultIconSizeActions().size());
    }

    public void testDisplayActions()
    {
        IdToolBar toolBar = new IdToolBar();
        assertNotNull(toolBar.getDisplayActions());
        assertEquals(3, toolBar.getDisplayActions().size());
    }

    public void testDisplayIcons()
    {
        IdToolBar toolBar = new IdToolBar();
        toolBar.displayIcons();
    }

    public void testDisplayText()
    {
        IdToolBar toolBar = new IdToolBar();
        toolBar.displayText();
    }

    public void testDisplayIconsAndText()
    {
        IdToolBar toolBar = new IdToolBar();
        toolBar.displayIconsAndText();
    }

    public void testIconSize()
    {
        IdToolBar toolBar = new IdToolBar();
        //for (IconSize iconSize : IconSize.VALUES)
        for (int i = 0, size = IconSize.VALUES.size(); i < size; i++)
        {
            toolBar.setIconSize((IconSize) IconSize.VALUES.get(i));
        }
        try
        {
            toolBar.setIconSize(null);
            fail("setIconSize(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateIconSizeAction()
    {
        IdToolBar toolBar = new IdToolBar();
        Action iconSizeAction = toolBar.createIconSizeAction(IconSize.DEFAULT_16X16);
        assertNotNull(iconSizeAction);
        try
        {
            toolBar.createIconSizeAction(null);
            fail("createIconSizeAction(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}