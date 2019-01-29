/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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

import java.awt.Image;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.DefaultListCellRenderer;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * List cell renderer that displays the name property value
 * and appropriate icon from an icon bundle for a given bean.
 *
 * @author  Michael Heuer
 */
public class IdListCellRenderer
    extends StripeListCellRenderer
{
    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_16X16;

    /** Icon size. */
    private IconSize iconSize;

    /** ImageIcon wrapper for image from icon bundle. */
    private transient ImageIcon imageIcon;


    /**
     * Create a new list cell renderer for identifiable beans with
     * the default icon size.
     *
     * @see #DEFAULT_ICON_SIZE
     */
    public IdListCellRenderer()
    {
        this(DEFAULT_ICON_SIZE);
    }

    /**
     * Create a new list cell renderer for identifiable beans with
     * the specified icon size.
     *
     * @param iconSize icon size, must not be null
     */
    public IdListCellRenderer(final IconSize iconSize)
    {
        super();
        setIconSize(iconSize);
    }


    /**
     * Return the icon size for this list cell renderer.
     *
     * @return the icon size for this list cell renderer
     */
    public final IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this list cell renderer to <code>iconSize</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconSize icon size, must not be null
     */
    public final void setIconSize(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        IconSize oldIconSize = this.iconSize;
        this.iconSize = iconSize;
        firePropertyChange("iconSize", oldIconSize, this.iconSize);
    }

    @Override
    public Component getListCellRendererComponent(final JList list,
                                                  final Object value,
                                                  final int index,
                                                  final boolean isSelected,
                                                  final boolean hasFocus)
    {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);

        String name = IdentifyUtils.getNameFor(value);
        label.setText(name);

        IconBundle iconBundle = IdentifyUtils.getIconBundleFor(value);

        if (iconBundle == null)
        {
            label.setIcon(null);
        }
        else
        {
            IconTextDirection textDirection = IdentifyUtils.determineTextDirection(label);
            Image image = iconBundle.getImage(label, textDirection, IconState.NORMAL, iconSize);

            if (imageIcon == null)
            {
                imageIcon = new ImageIcon(image);
            }
            else
            {
                imageIcon.setImage(image);
            }

            label.setIcon(imageIcon);
        }

        return label;
    }
}
