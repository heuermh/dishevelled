/*

    dsh-layout  Layout managers for lightweight components.
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
package org.dishevelled.layout;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Button panel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ButtonPanel
    extends JPanel
{

    /**
     * Create a new button panel.
     */
    public ButtonPanel()
    {
        super();
        setLayout(new ButtonLayout(this));
    }


    /**
     * Add spacing.
     */
    private void addSpacing()
    {
        int buttonCount = 0;
        //for (Component component : getComponents())
        for (int i = 0, size = getComponentCount(); i < size; i++)
        {
            Component component = getComponent(i);
            if (component instanceof JButton)
            {
                buttonCount++;
            }
        }
        if (buttonCount == 0)
        {
            super.add(Box.createHorizontalGlue());
            super.add(Box.createHorizontalGlue());
        }
        else
        {
            int buttonSpacing = 10; // todo:  look up in L&F docs
            super.add(Box.createHorizontalStrut(buttonSpacing));
        }
    }

    /**
     * Add the specified button to this button panel.
     *
     * @param button button to add
     */
    public JButton add(final JButton button)
    {
        addSpacing();
        return (JButton) super.add(button);
    }

    /**
     * Create a new JButton for the specified action and add it to this button panel.
     *
     * @param action action
     * @return a new JButton for the specified action
     */
    public JButton add(final Action action)
    {
        addSpacing();
        JButton button = new JButton(action);
        return (JButton) super.add(button);
    }

    // other considerations:
    // minimum button width
    // set the default button
    // adjust left-to-right orientation of default button based on L&F
}