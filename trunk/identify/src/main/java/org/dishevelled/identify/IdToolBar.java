/*

    dsh-identify  Lightweight components for identifiable beans.
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
package org.dishevelled.identify;

import java.awt.Component;

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JToolBar;

import org.dishevelled.iconbundle.IconSize;

/**
 * An extension of JToolBar that accepts identifiable actions
 * and provides actions for changing display properties and
 * icon sizes.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdToolBar
    extends JToolBar
{
    /** Number of display actions, <code>3</code>. */
    static final int DISPLAY_ACTIONS = 3;

    /** List of default icon size actions. */
    private final List/*<Action>*/ defaultIconSizeActions;

    /** List of display actions. */
    private final List/*<Action>*/ displayActions;


    /**
     * Create a new tool bar.
     */
    public IdToolBar()
    {
        super();
        displayActions = new ArrayList/*<Action>*/(DISPLAY_ACTIONS);
        createDisplayActions();
        defaultIconSizeActions = new ArrayList/*<Action>*/(IconSize.VALUES.size());
        createDefaultIconSizeActions();
    }

    /**
     * Create a new tool bar with the specified orientation.
     *
     * @param orientation orientation, must be either <code>HORIZONTAL<code>
     *    or <code>VERTICAL</code>
     */
    public IdToolBar(final int orientation)
    {
        super(orientation);
        displayActions = new ArrayList/*<Action>*/(DISPLAY_ACTIONS);
        createDisplayActions();
        defaultIconSizeActions = new ArrayList/*<Action>*/(IconSize.VALUES.size());
        createDefaultIconSizeActions();
    }

    /**
     * Create a new tool bar with the specified name.
     *
     * @param name name of this tool bar
     */
    public IdToolBar(final String name)
    {
        super(name);
        displayActions = new ArrayList/*<Action>*/(DISPLAY_ACTIONS);
        createDisplayActions();
        defaultIconSizeActions = new ArrayList/*<Action>*/(IconSize.VALUES.size());
        createDefaultIconSizeActions();
    }

    /**
     * Create a new tool bar with the specified name and orientation.
     *
     * @param name name of this tool bar
     * @param orientation orientation, must be either <code>HORIZONTAL<code>
     *    or <code>VERTICAL</code>
     */
    public IdToolBar(final String name, final int orientation)
    {
        super(name, orientation);
        displayActions = new ArrayList/*<Action>*/(DISPLAY_ACTIONS);
        createDisplayActions();
        defaultIconSizeActions = new ArrayList/*<Action>*/(IconSize.VALUES.size());
        createDefaultIconSizeActions();
    }


    /**
     * Create the default icon size actions.
     */
    private void createDefaultIconSizeActions()
    {
        //for (IconSize iconSize : IconSize.VALUES)
        for (int i = 0, size = IconSize.VALUES.size(); i < size; i++)
        {
            defaultIconSizeActions.add(new IconSizeAction((IconSize) IconSize.VALUES.get(i)));
        }
    }

    /**
     * Create the display actions.
     */
    private void createDisplayActions()
    {
        displayActions.add(new DisplayIconsAction());
        displayActions.add(new DisplayTextAction());
        displayActions.add(new DisplayIconsAndTextAction());
    }

    /**
     * Add and return a new identifiable button to this tool bar which dispatches the specified
     * identifiable action.  The identifiable button will not be null.
     *
     * @param identifiableAction identifiable action to add to this tool bar, must not be null
     * @return a new identifiable button which dispatches the specified identifiable action
     */
    public IdButton add(final IdentifiableAction identifiableAction)
    {
        IdButton idButton = new IdButton(identifiableAction);
        // default to icon over centered text layout
        idButton.setHorizontalAlignment(IdButton.CENTER);
        idButton.setHorizontalTextPosition(IdButton.CENTER);
        idButton.setVerticalTextPosition(IdButton.BOTTOM);
        // tweak visual settings; might be L&F dependent
        //idButton.setBorderPainted(false);
        //idButton.setFocusPainted(false);
        super.add(idButton);
        return idButton;
    }

    /**
     * Display icons only for the buttons in this tool bar.
     */
    public void displayIcons()
    {
        for (int i = 0, size = getComponentCount(); i < size; i++)
        {
            Component component = getComponentAtIndex(i);
            if (component instanceof IdButton)
            {
                IdButton idButton = (IdButton) component;
                idButton.displayIcon();
            }
        }
    }

    /**
     * Display text only for the buttons in this tool bar.
     */
    public void displayText()
    {
        for (int i = 0, size = getComponentCount(); i < size; i++)
        {
            Component component = getComponentAtIndex(i);
            if (component instanceof IdButton)
            {
                IdButton idButton = (IdButton) component;
                idButton.displayText();
            }
        }
    }

    /**
     * Display icons and text for the buttons in this tool bar.
     */
    public void displayIconsAndText()
    {
        for (int i = 0, size = getComponentCount(); i < size; i++)
        {
            Component component = getComponentAtIndex(i);
            if (component instanceof IdButton)
            {
                IdButton idButton = (IdButton) component;
                idButton.displayIconAndText();
            }
        }
    }

    /**
     * Set the icon size for all identifiable buttons in this tool bar to <code>iconSize</code>.
     *
     * @param iconSize icon size for all identifiable buttons in this tool bar, must not be null
     */
    public void setIconSize(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        for (int i = 0, size = getComponentCount(); i < size; i++)
        {
            Component component = getComponentAtIndex(i);
            if (component instanceof IdButton)
            {
                IdButton button = (IdButton) component;
                button.setIconSize(iconSize);
            }
        }
    }

    /**
     * Return an unmodifiable list of display actions.  The list will not be null.
     *
     * @return an unmodifiable list of display actions
     */
    public List/*<Action>*/ getDisplayActions()
    {
        return Collections.unmodifiableList(displayActions);
    }

    /**
     * Create and return a new icon size action for this tool bar for the specified icon size.
     * The new icon size action will not be null.
     *
     * @param iconSize icon size for the new icon size action, must not be null
     * @return a new icon size action for the specified icon size
     */
    public Action createIconSizeAction(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        return new IconSizeAction(iconSize);
    }

    /**
     * Return an unmodifiable list of the default icon size actions.  The list will not be null.
     *
     * @return an unmodifiable list of the default icon size actions
     */
    public List/*<Action>*/ getDefaultIconSizeActions()
    {
        return Collections.unmodifiableList(defaultIconSizeActions);
    }

    /**
     * Display icons action.
     */
    private class DisplayIconsAction extends AbstractAction
    {
        /**
         * Create a new display icons action.
         */
        DisplayIconsAction()
        {
            super();
            putValue(Action.NAME, "Display icons");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            displayIcons();
        }
    }

    /**
     * Display text action.
     */
    private class DisplayTextAction extends AbstractAction
    {
        /**
         * Create a new display text action.
         */
        DisplayTextAction()
        {
            super();
            putValue(Action.NAME, "Display text");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            displayText();
        }
    }

    /**
     * Display icons and text action.
     */
    private class DisplayIconsAndTextAction extends AbstractAction
    {
        /**
         * Create a new display icons and text action.
         */
        DisplayIconsAndTextAction()
        {
            super();
            putValue(Action.NAME, "Display icons and text");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            displayIconsAndText();
        }
    }

    /**
     * Icon size action.
     */
    private class IconSizeAction extends AbstractAction
    {
        /** Icon size. */
        private final IconSize iconSize;


        /**
         * Create a new icon size action for the specified icon size.
         *
         * @param iconSize icon size, must not be null
         */
        IconSizeAction(final IconSize iconSize)
        {
            super();
            if (iconSize == null)
            {
                throw new IllegalArgumentException("iconSize must not be null");
            }
            this.iconSize = iconSize;
            putValue(Action.NAME, "Use icon size " + iconSize);
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            setIconSize(iconSize);
        }
    }
}