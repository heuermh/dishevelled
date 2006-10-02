/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2006 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.identify;

import java.awt.ComponentOrientation;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * An extension of JMenuItem that displays the name property value
 * and appropriate icon from an icon bundle for an identifiable
 * action.
 *
 * @see IdentifiableAction
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdMenuItem
    extends JMenuItem
{
    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_16X16;

    /** Default icon text direction. */
    private static final IconTextDirection DEFAULT_ICON_TEXT_DIRECTION = IconTextDirection.LEFT_TO_RIGHT;

    /** Icon size. */
    private IconSize iconSize = DEFAULT_ICON_SIZE;

    /** Icon text direction. */
    private IconTextDirection iconTextDirection = DEFAULT_ICON_TEXT_DIRECTION;

    /** Dirty flag. */
    private transient boolean dirty = true;


    /**
     * Create a new menu item with the specified identifiable action.
     *
     * @param action identifiable action, must not be null
     */
    public IdMenuItem(final IdentifiableAction action)
    {
        super();

        if (action == null)
        {
            throw new IllegalArgumentException("action must not be null");
        }
        setAction(action);
    }

    /**
     * Create a new menu item with the specified identifiable action
     * and icon size.
     *
     * @param action identifiable action, must not be null
     * @param iconSize icon size, must not be null
     */
    public IdMenuItem(final IdentifiableAction action, final IconSize iconSize)
    {
        super();

        if (action == null)
        {
            throw new IllegalArgumentException("action must not be null");
        }
        setIconSize(iconSize);
        setAction(action);
    }


    /**
     * Return the icon size for this menu item.
     *
     * @return the icon size for this menu item
     */
    public IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this menu item to <code>iconSize</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconSize icon size, must not be null
     */
    public void setIconSize(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        IconSize oldIconSize = this.iconSize;
        this.iconSize = iconSize;

        if (!this.iconSize.equals(oldIconSize))
        {
            firePropertyChange("iconSize", oldIconSize, this.iconSize);
            setDirty(true);
        }
    }

    /**
     * Return the icon text direction for this menu item.
     *
     * @return the icon text direction for this menu item
     */
    IconTextDirection getIconTextDirection()
    {
        return iconTextDirection;
    }

    /** {@inheritDoc} */
    public void setComponentOrientation(final ComponentOrientation orientation)
    {
        ComponentOrientation oldOrientation = getComponentOrientation();

        if (!oldOrientation.equals(orientation))
        {
            if (orientation != null)
            {
                iconTextDirection = orientation.isLeftToRight() ?
                    IconTextDirection.LEFT_TO_RIGHT : IconTextDirection.RIGHT_TO_LEFT;

                setDirty(true);
            }
        }

        super.setComponentOrientation(orientation);
    }

    /** {@inheritDoc} */
    public void applyComponentOrientation(final ComponentOrientation orientation)
    {
        ComponentOrientation oldOrientation = getComponentOrientation();

        if (!oldOrientation.equals(orientation))
        {
            if (orientation != null)
            {
                iconTextDirection = orientation.isLeftToRight() ?
                    IconTextDirection.LEFT_TO_RIGHT : IconTextDirection.RIGHT_TO_LEFT;

                setDirty(true);
            }
        }

        super.applyComponentOrientation(orientation);
    }

    /** {@inheritDoc} */
    protected void configurePropertiesFromAction(final Action action)
    {
        super.configurePropertiesFromAction(action);

        if (isDirty())
        {
            rebuild();
        }
    }

    /**
     * Set the dirty flag to the logical OR of <code>dirty</code>
     * and the previous dirty state.
     *
     * @param dirty dirty flag
     */
    private void setDirty(final boolean dirty)
    {
        this.dirty = (this.dirty || dirty);
    }

    /**
     * Return true if a rebuild of the icons for this menu item is necessary.
     *
     * @return true if a rebuild of the icons for this menu item is necessary
     */
    private boolean isDirty()
    {
        return dirty;
    }

    /**
     * Rebuild the icons for this menu item from the icon bundle
     * provided by the identifiable action for this menu item.
     */
    private void rebuild()
    {
        Action action = getAction();
        if ((action != null) && (action instanceof IdentifiableAction))
        {
            IdentifiableAction identifiableAction = (IdentifiableAction) action;
            IconBundle bndl = identifiableAction.getIconBundle();
            setIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.NORMAL, iconSize)));
            setPressedIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.ACTIVE, iconSize)));
            setSelectedIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.SELECTED, iconSize)));
            setRolloverIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.MOUSEOVER, iconSize)));
            setRolloverSelectedIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.SELECTED, iconSize)));
            setDisabledIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.DISABLED, iconSize)));
        }
        dirty = false;
    }
}