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

import java.awt.Image;
import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import javax.swing.tree.DefaultTreeCellRenderer;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * A tree cell renderer that displays the name property value
 * and appropriate icon from an icon bundle for a given bean.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IdTreeCellRenderer
    extends DefaultTreeCellRenderer
{
    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_16X16;

    /** Icon size. */
    private IconSize iconSize;

    /** ImageIcon wrapper for image from icon bundle. */
    private transient ImageIcon imageIcon;


    /**
     * Create a new tree cell renderer for identifiable beans with
     * the default icon size.
     *
     * @see #DEFAULT_ICON_SIZE
     */
    public IdTreeCellRenderer()
    {
        this(DEFAULT_ICON_SIZE);
    }

    /**
     * Create a new tree cell renderer for identifiable beans with
     * the specified icon size.
     *
     * @param iconSize icon size, must not be null
     */
    public IdTreeCellRenderer(final IconSize iconSize)
    {
        super();
        setIconSize(iconSize);
    }


    /**
     * Return the icon size for this tree cell renderer.
     *
     * @return the icon size for this tree cell renderer
     */
    public final IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this tree cell renderer to <code>iconSize</code>.
     *
     * @param iconSize icon size, must not be null
     */
    public final void setIconSize(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        this.iconSize = iconSize;
    }

    /** @see DefaultTreeCellRenderer */
    public Component getTreeCellRendererComponent(final JTree tree,
                                                  final Object value,
                                                  final boolean isSelected,
                                                  final boolean isExpanded,
                                                  final boolean isLeaf,
                                                  final int row,
                                                  final boolean hasFocus)
    {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus);

        String name = IdentifyUtils.getNameFor(value);
        label.setText(name);

        IconBundle iconBundle = IdentifyUtils.getIconBundleFor(value);

        if (iconBundle == null)
        {
            label.setIcon(null);
        }
        else
        {
            IconState state = determineState(label, isSelected, hasFocus);
            IconTextDirection textDirection = determineTextDirection(label);
            Image image = iconBundle.getImage(label, textDirection, state, iconSize);

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

    /**
     * Determine an icon state from the specified label and flags.
     *
     * @param label label
     * @param isSelected true if the specifed cell is selected
     * @param hasFocus true if the specified cell has focus
     * @return an icon state for the specified label and flags
     */
    private final IconState determineState(final JLabel label, final boolean isSelected, final boolean hasFocus)
    {
        if (isSelected)
        {
            return IconState.SELECTED;
        }
        else
        {
            if (hasFocus)
            {
                return IconState.MOUSEOVER;
            }
            else
            {
                return IconState.NORMAL;
            }
        }
    }

    /**
     * Determine a text direction from the component orientation of
     * the specified label.
     *
     * @param label label
     * @return a text direction for the component orientation of
     *    the specified label
     */
    private final IconTextDirection determineTextDirection(final JLabel label)
    {
        ComponentOrientation componentOrientation = label.getComponentOrientation();
        return componentOrientation.isLeftToRight() ? IconTextDirection.LEFT_TO_RIGHT : IconTextDirection.RIGHT_TO_LEFT;
    }
}
