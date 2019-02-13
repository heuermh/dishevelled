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

import java.awt.ComponentOrientation;

import javax.swing.ImageIcon;
import javax.swing.JMenu;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * An extension of JMenu that displays the name property value
 * and appropriate icon from an icon bundle for a given bean.
 *
 * @author  Michael Heuer
 */
public final class IdMenu
    extends JMenu
{
    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_16X16;

    /** Default icon text direction. */
    private static final IconTextDirection DEFAULT_ICON_TEXT_DIRECTION = IconTextDirection.LEFT_TO_RIGHT;

    /** Icon size. */
    private IconSize iconSize = DEFAULT_ICON_SIZE;

    /** Icon text direction. */
    private IconTextDirection iconTextDirection = DEFAULT_ICON_TEXT_DIRECTION;

    /** Bound value property. */
    private Object value;


    /**
     * Create a new menu.
     */
    public IdMenu()
    {
        this(null);
    }

    /**
     * Create a new menu with the specified value.
     *
     * @param value value
     */
    public IdMenu(final Object value)
    {
        super();
        setValue(value);
    }

    /**
     * Create a new menu item with the specified value and icon size.
     *
     * @param value value
     * @param iconSize icon size, must not be null
     */
    public IdMenu(final Object value, final IconSize iconSize)
    {
        super();
        setValue(value);
        // rebuilds again
        setIconSize(iconSize);
    }


    /**
     * Return the value for this menu.
     *
     * @return the value for this menu
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Set the value for this menu to <code>value</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param value value for this menu
     */
    public void setValue(final Object value)
    {
        Object oldValue = this.value;
        this.value = value;
        firePropertyChange("value", oldValue, this.value);
        rebuild();
    }

    /**
     * Return the icon size for this menu.
     *
     * @return the icon size for this menu
     */
    public IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this menu to <code>iconSize</code>.
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
            rebuild();
        }
    }

    /**
     * Return the icon text direction for this menu.
     *
     * @return the icon text direction for this menu
     */
    public IconTextDirection getIconTextDirection()
    {
        return iconTextDirection;
    }

    @Override
    public void setComponentOrientation(final ComponentOrientation orientation)
    {
        ComponentOrientation oldOrientation = getComponentOrientation();

        if (!oldOrientation.equals(orientation))
        {
            if (orientation != null)
            {
                iconTextDirection = orientation.isLeftToRight()
                    ? IconTextDirection.LEFT_TO_RIGHT : IconTextDirection.RIGHT_TO_LEFT;

                rebuild();
            }
        }

        super.setComponentOrientation(orientation);
    }

    @Override
    public void applyComponentOrientation(final ComponentOrientation orientation)
    {
        ComponentOrientation oldOrientation = getComponentOrientation();

        if (!oldOrientation.equals(orientation))
        {
            if (orientation != null)
            {
                iconTextDirection = orientation.isLeftToRight()
                    ? IconTextDirection.LEFT_TO_RIGHT : IconTextDirection.RIGHT_TO_LEFT;

                rebuild();
            }
        }

        super.applyComponentOrientation(orientation);
    }

    /**
     * Rebuild the text and icon properties of this menu
     * with the name property and icon bundle image of
     * <code>getValue()</code>, respectively.
     */
    private void rebuild()
    {
        String name = IdentifyUtils.getNameFor(value);
        setText(name);
        IconBundle bndl = IdentifyUtils.getIconBundleFor(value);

        if (bndl == null)
        {
            setIcon(null);
        }
        else
        {
            setIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.NORMAL, iconSize)));
            setPressedIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.ACTIVE, iconSize)));
            setSelectedIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.SELECTED, iconSize)));
            setRolloverIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.MOUSEOVER, iconSize)));
            setRolloverSelectedIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.SELECTED, iconSize)));
            //setDisabledIcon(new ImageIcon(bndl.getImage(this, iconTextDirection, IconState.DISABLED, iconSize)));
        }
    }
}
