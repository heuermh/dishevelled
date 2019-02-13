/*

    dsh-identify  Lightweight components for identifiable beans.
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
package org.dishevelled.identify;

import java.awt.Component;

import java.awt.event.ActionEvent;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * An extension of JToolBar that accepts identifiable actions
 * and provides actions for changing display properties and
 * icon sizes.
 *
 * @author  Michael Heuer
 */
public final class IdToolBar
    extends JToolBar
{
    /** Display icons action. */
    private final AbstractAction displayIcons = new AbstractAction("Display icons")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                displayIcons();
            }
        };

    /** Display text action. */
    private final AbstractAction displayText = new AbstractAction("Display text")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                displayText();
            }
        };

    /** Display icons and text action. */
    private final AbstractAction displayIconsAndText = new AbstractAction("Display icons and text")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                displayIconsAndText();
            }
        };

    /** Display. */
    private int display = DISPLAY_ICONS;

    /** Icon size. */
    private IconSize iconSize = DEFAULT_ICON_SIZE;

    /** Icon text direction. */
    private IconTextDirection iconTextDirection = DEFAULT_ICON_TEXT_DIRECTION;

    /** Display icons. */
    private static final int DISPLAY_ICONS = 0;

    /** Display text. */
    private static final int DISPLAY_TEXT = 1;

    /** Display icons and text. */
    private static final int DISPLAY_ICONS_AND_TEXT = 2;

    /** Default icon size, <code>IconSize.DEFAULT_16X16</code>. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_16X16;

    /** Default icon text direction, <code>IconTextDirection.LEFT_TO_RIGHT</code>. */
    public static final IconTextDirection DEFAULT_ICON_TEXT_DIRECTION = IconTextDirection.LEFT_TO_RIGHT;

    /** List of display actions. */
    private final List<AbstractAction> displayActions = Arrays.asList(new AbstractAction[] { displayIcons, displayText, displayIconsAndText });

    /** List of display menu items. */
    private final List<JCheckBoxMenuItem> displayMenuItems = new ArrayList<JCheckBoxMenuItem>(displayActions.size());

    /** Map of icon size actions keyed by icon size. */
    private final Map<IconSize, AbstractAction> iconSizeActions = new HashMap<IconSize, AbstractAction>();

    /** Map of icon size menu items keyed by icon size. */
    private final Map<IconSize, JCheckBoxMenuItem> iconSizeMenuItems = new HashMap<IconSize, JCheckBoxMenuItem>();

    /** Icon size menu item button group. */
    private final ButtonGroup iconSizeButtonGroup = new ButtonGroup();


    /**
     * Create a new tool bar.
     */
    public IdToolBar()
    {
        super();
        // clear gradient for Metal/Ocean L&F
        UIManager.put("MenuBar.gradient", null);
        UIManager.getDefaults().put("MenuBar.gradient", null);
        UIManager.getLookAndFeelDefaults().put("MenuBar.gradient", null);
        // todo: remove style in GTK L&F

        createDisplayMenuItems();
        displayIcons();
        setIconSize(iconSize);
    }

    /**
     * Create a new tool bar with the specified orientation.
     *
     * @param orientation orientation, must be either <code>HORIZONTAL</code>
     *    or <code>VERTICAL</code>
     */
    public IdToolBar(final int orientation)
    {
        super(orientation);
        // clear gradient for Metal/Ocean L&F
        UIManager.put("MenuBar.gradient", null);
        UIManager.getDefaults().put("MenuBar.gradient", null);
        UIManager.getLookAndFeelDefaults().put("MenuBar.gradient", null);

        createDisplayMenuItems();
        displayIcons();
        setIconSize(iconSize);
    }

    /**
     * Create a new tool bar with the specified name.
     *
     * @param name name of this tool bar
     */
    public IdToolBar(final String name)
    {
        super(name);
        createDisplayMenuItems();
        displayIcons();
        setIconSize(iconSize);
    }

    /**
     * Create a new tool bar with the specified name and orientation.
     *
     * @param name name of this tool bar
     * @param orientation orientation, must be either <code>HORIZONTAL</code>
     *    or <code>VERTICAL</code>
     */
    public IdToolBar(final String name, final int orientation)
    {
        super(name, orientation);
        createDisplayMenuItems();
        displayIcons();
        setIconSize(iconSize);
    }


    /**
     * Create the display menu items.
     */
    private void createDisplayMenuItems() {
        ButtonGroup buttonGroup = new ButtonGroup();
        for (AbstractAction displayAction : displayActions)
        {
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(displayAction);
            displayMenuItems.add(menuItem);
            buttonGroup.add(menuItem);
        }
    }

    /**
     * Add and return a new context menu button to this tool bar which raises the specified
     * context menu.  The context menu button will not be null.
     *
     * @param contextMenu context menu to add to this tool bar, must not be null
     * @return a new context menu button which raises the specified context menu
     */
    public ContextMenuButton add(final JPopupMenu contextMenu)
    {
        ContextMenuButton contextMenuButton = new ContextMenuButton(contextMenu);

        // tweak visual settings; might be L&F dependent
        contextMenuButton.setBorderPainted(false);
        contextMenuButton.setFocusPainted(false);

        super.add(contextMenuButton);
        return contextMenuButton;
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
        idButton.setIconSize(iconSize);
        switch (display)
        {
            case DISPLAY_ICONS:
                idButton.displayIcon();
                break;
            case DISPLAY_TEXT:
                idButton.displayText();
                break;
            case DISPLAY_ICONS_AND_TEXT:
                idButton.displayIconAndText();
                break;
            default:
                break;
        }

        if (IdentifyUtils.isGTKLookAndFeel())
        {
            idButton.setHorizontalAlignment(SwingConstants.LEFT);
            idButton.setHorizontalTextPosition(SwingConstants.TRAILING);
            idButton.setVerticalTextPosition(SwingConstants.CENTER);
        }
        else
        {
            // default to icon over centered text layout
            idButton.setHorizontalAlignment(SwingConstants.CENTER);
            idButton.setHorizontalTextPosition(SwingConstants.CENTER);
            idButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        // tweak visual settings; might be L&F dependent
        idButton.setBorderPainted(false);
        idButton.setFocusPainted(false);

        super.add(idButton);
        return idButton;
    }

    /**
     * Remove the identifiable button for the specified identifiable action from
     * this tool bar, if such exists.
     *
     * @param identifiableAction identifiable action to remove from this tool bar
     * @return the identifiable button removed from this tool bar, or null if no such button exists
     */
    public IdButton remove(final IdentifiableAction identifiableAction)
    {
        for (int i = 0; i < getComponentCount(); i++)
        {
            Component c = getComponent(i);
            if (c instanceof IdButton)
            {
                IdButton idButton = (IdButton) c;
                Action action = idButton.getAction();
                if (action != null && action.equals(identifiableAction))
                {
                    remove(idButton);
                    return idButton;
                }
            }
        }
        return null;
    }

    /**
     * Display icons only for the buttons in this tool bar.
     */
    public void displayIcons()
    {
        display = DISPLAY_ICONS;
        displayIcons.putValue(Action.SELECTED_KEY, Boolean.TRUE);

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
        display = DISPLAY_TEXT;
        displayText.putValue(Action.SELECTED_KEY, Boolean.TRUE);

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
        display = DISPLAY_ICONS_AND_TEXT;
        displayIconsAndText.putValue(Action.SELECTED_KEY, Boolean.TRUE);

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
     * Return the icon size for all identifiable buttons in this tool bar.
     *
     * @return the icon size for all identifiable buttons in this tool bar
     */
    public IconSize getIconSize()
    {
        return iconSize;
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
        Action iconSizeAction = createIconSizeAction(iconSize);
        iconSizeAction.putValue(Action.SELECTED_KEY, Boolean.TRUE);

        for (int i = 0, size = getComponentCount(); i < size; i++)
        {
            Component component = getComponentAtIndex(i);
            if (component instanceof IdButton)
            {
                IdButton idButton = (IdButton) component;
                idButton.setIconSize(iconSize);
            }
        }
    }

    /**
     * Return the icon text direction for all identifiable buttons in this tool bar.
     *
     * @return the icon text direction for all identifiable buttons in this tool bar
     */
    public IconTextDirection getIconTextDirection()
    {
        return iconTextDirection;
    }

    /**
     * Return an unmodifiable list of display actions.  The list will not be null.
     *
     * @return an unmodifiable list of display actions
     */
    public List<AbstractAction> getDisplayActions()
    {
        return displayActions;
    }

    /**
     * Return an unmodifiable list of check box menu items for the display actions.  The list
     * will not be null.
     *
     * @return an unmodifiable list of check box menu items for the display actions
     */
    public List<JCheckBoxMenuItem> getDisplayMenuItems()
    {
        return Collections.unmodifiableList(displayMenuItems);
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
        if (!iconSizeActions.containsKey(iconSize))
        {
            iconSizeActions.put(iconSize, new IconSizeAction(iconSize));
        }
        return iconSizeActions.get(iconSize);
    }

    /**
     * Create and return a new icon size menu item for this tool bar for the specified icon size.
     * The new icon size menu item will not be null.
     *
     * @param iconSize icon size for the new icon size menu item, must not be null
     * @return a new icon size menu item for the specified icon size
     */
    public JCheckBoxMenuItem createIconSizeMenuItem(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        if (!iconSizeMenuItems.containsKey(iconSize))
        {
            AbstractAction iconSizeAction = (AbstractAction) createIconSizeAction(iconSize);
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(iconSizeAction);
            iconSizeButtonGroup.add(menuItem);
            iconSizeMenuItems.put(iconSize, menuItem);
        }
        return iconSizeMenuItems.get(iconSize);
    }

    /**
     * Return an unmodifiable list of the default icon size actions.  The list will not be null.
     *
     * @return an unmodifiable list of the default icon size actions
     */
    public List<AbstractAction> getDefaultIconSizeActions()
    {
        List<AbstractAction> actions = new ArrayList<AbstractAction>(IconSize.VALUES.size());
        for (int i = 0, size = IconSize.VALUES.size(); i < size; i++)
        {
            AbstractAction iconSizeAction = (AbstractAction) createIconSizeAction((IconSize) IconSize.VALUES.get(i));
            actions.add(iconSizeAction);
        }
        return Collections.unmodifiableList(actions);
    }

    /**
     * Return an unmodifiable list of check box menu items for the default icon size actions.  The list
     * will not be null.
     *
     * @return an unmodifiable list of check box menu items for the default icon size actions
     */
    public List<JCheckBoxMenuItem> getDefaultIconSizeMenuItems()
    {
        List<JCheckBoxMenuItem> menuItems = new ArrayList<JCheckBoxMenuItem>(IconSize.VALUES.size());
        for (int i = 0, size = IconSize.VALUES.size(); i < size; i++)
        {
            JCheckBoxMenuItem iconSizeMenuItem = createIconSizeMenuItem((IconSize) IconSize.VALUES.get(i));
            menuItems.add(iconSizeMenuItem);
        }
        return Collections.unmodifiableList(menuItems);
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


        @Override
        public void actionPerformed(final ActionEvent event)
        {
            setIconSize(iconSize);
        }
    }
}
