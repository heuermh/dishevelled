/*

    dsh-layout-examples  Examples for the layout library.
    Copyright (c) 2008-2013 held jointly by the individual authors.

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
package org.dishevelled.layout.examples;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.dishevelled.layout.ButtonPanel;

/**
 * ButtonPanel example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ButtonPanelExample
    implements Runnable
{

    /** {@inheritDoc} */
    public void run()
    {
        final JFrame frame = new JFrame("Button panel example");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        panel.add("Center", createMainPanel());
        panel.add("South", createButtonPanel());
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 400, 400);
        frame.setVisible(true);
    }

    /**
     * Create and return the main panel.
     *
     * @return the main panel
     */
    private JPanel createMainPanel()
    {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Main panel"));
        return panel;
    }

    /**
     * Create and return the button panel.
     *
     * @return the button panel
     */
    private JPanel createButtonPanel()
    {
        Action up = new AbstractAction("Up")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };
        Action down = new AbstractAction("Down")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };
        Action left = new AbstractAction("Left")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };
        Action right = new AbstractAction("Right")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };
        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.add(up);
        buttonPanel.add(down);
        buttonPanel.add(left);
        buttonPanel.add(right);
        return buttonPanel;
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new ButtonPanelExample());
    }
}