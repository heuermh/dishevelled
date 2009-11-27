/*

    dsh-layout  Layout managers for lightweight components.
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
package org.dishevelled.layout;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import junit.framework.TestCase;

/**
 * Unit test for ButtonPanel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ButtonPanelTest
    extends TestCase
{

    public void testConstructor()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        assertNotNull(buttonPanel);
    }

    public void testLayout()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        assertTrue(buttonPanel.getLayout() instanceof ButtonLayout);
        BorderLayout borderLayout = new BorderLayout();
        buttonPanel.setLayout(borderLayout);
        assertEquals(borderLayout, buttonPanel.getLayout());
    }

    public void testAddJButton()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        JButton button0 = new JButton("button0");
        JButton button1 = new JButton("button1");
        JButton button2 = new JButton("button2");
        assertEquals(button0, buttonPanel.add(button0));
        assertEquals(button1, buttonPanel.add(button1));
        assertEquals(button2, buttonPanel.add(button2));
        //List<Component> components = Arrays.asList(buttonPanel.getComponents());
        List components = Arrays.asList(buttonPanel.getComponents());
        assertTrue(components.contains(button0));
        assertTrue(components.contains(button1));
        assertTrue(components.contains(button2));
    }

    public void testAddAction()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        Action action0 = new AbstractAction("action0")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    // empty
                }
            };
        Action action1 = new AbstractAction("action1")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    // empty
                }
            };
        Action action2 = new AbstractAction("action2")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    // empty
                }
            };
        JButton button0 = buttonPanel.add(action0);
        assertNotNull(button0);
        JButton button1 = buttonPanel.add(action1);
        assertNotNull(button1);
        JButton button2 = buttonPanel.add(action2);
        assertNotNull(button2);
        //List<Component> components = Arrays.asList(buttonPanel.getComponents());
        List components = Arrays.asList(buttonPanel.getComponents());
        assertTrue(components.contains(button0));
        assertTrue(components.contains(button1));
        assertTrue(components.contains(button2));
    }
}