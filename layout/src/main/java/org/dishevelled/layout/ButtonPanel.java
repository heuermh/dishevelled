/*

    dsh-layout  Layout managers for lightweight components.
    Copyright (c) 2003-2019 held jointly by the individual authors.

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

/**
 * Button panel.
 *
 * @author  Michael Heuer
 */
public final class ButtonPanel
    extends JPanel
{
    /** Default button spacing, <code>10</code>. */
    public static final int DEFAULT_BUTTON_SPACING = 10;

    /** Default minimum button width, <code>32</code>. */
    public static final int DEFAULT_MINIMUM_BUTTON_WIDTH = 32;

    /** Button spacing. */
    private int buttonSpacing = DEFAULT_BUTTON_SPACING;

    /** Minimum button width. */
    private int minimumButtonWidth = DEFAULT_MINIMUM_BUTTON_WIDTH;


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
            super.add(Box.createHorizontalStrut(buttonSpacing));
        }
    }

    /**
     * Update layout.
     */
    private void updateLayout()
    {
        List buttons = new ArrayList();
        for (int i = 0, size = getComponentCount(); i < size; i++)
        {
            Component component = getComponent(i);
            if (component instanceof JButton)
            {
                buttons.add(component);
            }
        }
        removeAll();

        add(Box.createHorizontalGlue());
        add(Box.createHorizontalGlue());

        for (Iterator i = buttons.iterator(); i.hasNext(); )
        {
            JButton button = (JButton) i.next();
            add(button);

            if (i.hasNext())
            {
                add(Box.createHorizontalStrut(buttonSpacing));
            }
        }
    }

    /**
     * Add the specified button to this button panel.
     *
     * @param button button to add
     * @return a reference to the added button
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

    /**
     * Set the border for this button panel to an empty border the specified width.
     *
     * <p>This is a bound property.</p>
     *
     * @param width width, must be at least zero
     */
    public void setBorder(final int width)
    {
        if (width < 0)
        {
            throw new IllegalArgumentException("border width must be at least zero");
        }
        setBorder(new EmptyBorder(width, width, width, width));
    }

    /**
     * Set the border for this button panel to an empty border the specified dimensions.
     *
     * <p>This is a bound property.</p>
     *
     * @param top top, must be at least zero
     * @param left left, must be at least zero
     * @param bottom bottom, must be at least zero
     * @param right right, must be at least zero
     */
    public void setBorder(final int top, final int left, final int bottom, final int right)
    {
        if (top < 0)
        {
            throw new IllegalArgumentException("border top must be at least zero");
        }
        if (left < 0)
        {
            throw new IllegalArgumentException("border left must be at least zero");
        }
        if (bottom < 0)
        {
            throw new IllegalArgumentException("border bottom must be at least zero");
        }
        if (right < 0)
        {
            throw new IllegalArgumentException("border right must be at least zero");
        }
        setBorder(new EmptyBorder(top, left, bottom, right));
    }

    /**
     * Set the button spacing for this button panel to <code>buttonSpacing</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param buttonSpacing button spacing, must be at least zero
     */
    public void setButtonSpacing(final int buttonSpacing)
    {
        if (buttonSpacing < 0)
        {
            throw new IllegalArgumentException("buttonSpacing must be at least zero");
        }
        int oldButtonSpacing = this.buttonSpacing;
        this.buttonSpacing = buttonSpacing;
        firePropertyChange("buttonSpacing", oldButtonSpacing, this.buttonSpacing);

        updateLayout();
    }

    /**
     * Return the button spacing for this button panel.
     *
     * @return the button spacing for this button panel
     */
    public int getButtonSpacing()
    {
        return buttonSpacing;
    }

    /**
     * Set the minimum button width for all buttons on this button panel to <code>minimumButtonWidth</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param minimumButtonWidth minimum button width for all buttons on this button panel,
     *    must be at least zero
     */
    public void setMinimumButtonWidth(final int minimumButtonWidth)
    {
        if (minimumButtonWidth < 0)
        {
            throw new IllegalArgumentException("minimumButtonWidth must be at least zero");
        }
        int oldMinimumButtonWidth = this.minimumButtonWidth;
        this.minimumButtonWidth = minimumButtonWidth;
        firePropertyChange("minimumButtonWidth", oldMinimumButtonWidth, this.minimumButtonWidth);

        updateLayout();
    }

    /**
     * Return the minimum button width for this button panel.
     *
     * @return the minimum button width for this button panel
     */
    public int getMinimumButtonWidth()
    {
        return minimumButtonWidth;
    }
}
