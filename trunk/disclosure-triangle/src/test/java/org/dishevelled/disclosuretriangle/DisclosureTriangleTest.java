/*

    dsh-disclosure-triangle  Disclosure triangle container.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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
package org.dishevelled.disclosuretriangle;

import java.awt.Component;
import java.awt.Container;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.UIManager;

import junit.framework.TestCase;

/**
 * Unit test for DisclosureTriangle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class DisclosureTriangleTest
    extends TestCase
{

    public void testConstructor()
    {
        Container container = new Container();
        DisclosureTriangle disclosureTriangle0 = new DisclosureTriangle(container);

        try
        {
            DisclosureTriangle disclosureTriangle = new DisclosureTriangle(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testExpand()
    {        
        Container container = new Container();
        DisclosureTriangle disclosureTriangle = new DisclosureTriangle(container);
        assertTrue(disclosureTriangle.isCollapsed());
        disclosureTriangle.expand();
        assertFalse(disclosureTriangle.isCollapsed());
    }

    public void testCollapse()
    {
        Container container = new Container();
        DisclosureTriangle disclosureTriangle = new DisclosureTriangle(container);
        assertTrue(disclosureTriangle.isCollapsed());
        disclosureTriangle.expand();
        assertFalse(disclosureTriangle.isCollapsed());
        disclosureTriangle.collapse();
        assertTrue(disclosureTriangle.isCollapsed());
    }

    public void testLabelText()
    {
        Container container = new Container();
        DisclosureTriangle disclosureTriangle = new DisclosureTriangle(container);
        assertEquals(DisclosureTriangle.DEFAULT_LABEL_TEXT, disclosureTriangle.getLabelText());
        disclosureTriangle.setLabelText(null);
        assertEquals(null, disclosureTriangle.getLabelText());
        disclosureTriangle.setLabelText("foo");
        assertEquals("foo", disclosureTriangle.getLabelText());
    }

    public void testCollapseIcon()
    {
        Icon icon = new ImageIcon();
        Container container = new Container();
        DisclosureTriangle disclosureTriangle = new DisclosureTriangle(container);
        Icon treeExpandedIcon = UIManager.getIcon("Tree.expandedIcon");
        assertEquals(treeExpandedIcon, disclosureTriangle.getCollapseIcon());
        disclosureTriangle.setCollapseIcon(null);
        assertEquals(null, disclosureTriangle.getCollapseIcon());
        disclosureTriangle.setCollapseIcon(icon);
        assertEquals(icon, disclosureTriangle.getCollapseIcon());

        disclosureTriangle.expand();
        disclosureTriangle.setCollapseIcon(null);
        assertEquals(null, disclosureTriangle.getCollapseIcon());
        disclosureTriangle.setCollapseIcon(icon);
        assertEquals(icon, disclosureTriangle.getCollapseIcon());

        disclosureTriangle.collapse();
        disclosureTriangle.setCollapseIcon(null);
        assertEquals(null, disclosureTriangle.getCollapseIcon());
        disclosureTriangle.setCollapseIcon(icon);
        assertEquals(icon, disclosureTriangle.getCollapseIcon());
    }

    public void testExpandIcon()
    {
        Icon icon = new ImageIcon();
        Container container = new Container();
        DisclosureTriangle disclosureTriangle = new DisclosureTriangle(container);
        Icon treeCollapsedIcon = UIManager.getIcon("Tree.collapsedIcon");
        assertEquals(treeCollapsedIcon, disclosureTriangle.getExpandIcon());
        disclosureTriangle.setExpandIcon(null);
        assertEquals(null, disclosureTriangle.getExpandIcon());
        disclosureTriangle.setExpandIcon(icon);
        assertEquals(icon, disclosureTriangle.getExpandIcon());

        disclosureTriangle.expand();
        disclosureTriangle.setExpandIcon(null);
        assertEquals(null, disclosureTriangle.getExpandIcon());
        disclosureTriangle.setExpandIcon(icon);
        assertEquals(icon, disclosureTriangle.getExpandIcon());

        disclosureTriangle.collapse();
        disclosureTriangle.setExpandIcon(null);
        assertEquals(null, disclosureTriangle.getExpandIcon());
        disclosureTriangle.setExpandIcon(icon);
        assertEquals(icon, disclosureTriangle.getExpandIcon());
    }

    public void testRootPaneContainers()
    {
        Container container = new Container();
        DisclosureTriangle disclosureTriangle = new DisclosureTriangle(container);

        JFrame f = new JFrame("Frame");
        f.getContentPane().add(disclosureTriangle);
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        f.getContentPane().remove(disclosureTriangle);

        JDialog d = new JDialog(f, "Dialog");
        d.getContentPane().add(disclosureTriangle);
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        d.getContentPane().remove(disclosureTriangle);

        JWindow w = new JWindow(f);
        w.getContentPane().add(disclosureTriangle);
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        w.getContentPane().remove(disclosureTriangle);

        JDialog desktop = new JDialog(f, "Desktop");
        JDesktopPane desktopPane = new JDesktopPane();
        JInternalFrame internalFrame = new JInternalFrame("Internal frame");
        internalFrame.getContentPane().add(disclosureTriangle);
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);
        desktop.setContentPane(desktopPane);
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        disclosureTriangle.expand();
        disclosureTriangle.collapse();
        internalFrame.getContentPane().remove(disclosureTriangle);
    }

    public void testSimulatedMouseClicks()
    {
        Container container = new Container();
        DisclosureTriangle disclosureTriangle = new DisclosureTriangle(container);
        MouseEvent mouseEvent = new MouseEvent(disclosureTriangle, 0, 0L, 0, 0, 0, 0, false);
        Component[] children = disclosureTriangle.getComponents();
        for (int i = 0, size = children.length; i < size; i++)
        {
            Component c = children[i];
            if (c instanceof JLabel)
            {
                JLabel label = (JLabel) c;
                MouseListener listener = label.getMouseListeners()[0];
                assertTrue(disclosureTriangle.isCollapsed());
                listener.mouseClicked(mouseEvent);
                assertFalse(disclosureTriangle.isCollapsed());
                listener.mouseClicked(mouseEvent);
                assertTrue(disclosureTriangle.isCollapsed());
                listener.mouseClicked(mouseEvent);
                assertFalse(disclosureTriangle.isCollapsed());
                listener.mouseClicked(mouseEvent);
                assertTrue(disclosureTriangle.isCollapsed());
            }
        }
    }
}