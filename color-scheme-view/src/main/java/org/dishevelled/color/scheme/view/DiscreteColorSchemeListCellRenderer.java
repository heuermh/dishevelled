/*

    dsh-color-scheme-view  Views for color schemes.
    Copyright (c) 2019 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.view;

import java.awt.Component;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import org.dishevelled.color.scheme.impl.DiscreteColorScheme;

import org.dishevelled.identify.StripeListCellRenderer;

/**
 * List cell renderer for discrete color schemes.
 *
 * @author  Michael Heuer
 */
public class DiscreteColorSchemeListCellRenderer extends StripeListCellRenderer
{
    /** Default icon width, <code>64</code>. */
    public static final int DEFAULT_ICON_WIDTH = 64;

    /** Default icon height, <code>14</code>. */
    public static final int DEFAULT_ICON_HEIGHT = 14;

    /** Icon width. */
    private int iconWidth = DEFAULT_ICON_WIDTH;

    /** Icon height. */
    private int iconHeight = DEFAULT_ICON_HEIGHT;


    /**
     * Create a new discrete color scheme list cell renderer.
     */
    public DiscreteColorSchemeListCellRenderer()
    {
        super();
    }

    /**
     * Create a new discrete color scheme list cell renderer.
     *
     * @param iconWidth icon width, must be at least zero
     * @param iconHeight icon height, must be at least zero
     */
    public DiscreteColorSchemeListCellRenderer(final int iconWidth, final int iconHeight)
    {
        if (iconWidth < 0)
        {
            throw new IllegalArgumentException("iconWidth must be at least zero");
        }
        if (iconHeight < 0)
        {
            throw new IllegalArgumentException("iconHeight must be at least zero");
        }
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
    }


    @Override
    public final Component getListCellRendererComponent(final JList list, final Object value, final int index, boolean isSelected, boolean hasFocus)
    {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);

        if (value instanceof DiscreteColorScheme)
        {
            DiscreteColorScheme colorScheme = (DiscreteColorScheme) value;
            label.setText(getNameFor(colorScheme));
            label.setIcon(getIconFor(colorScheme));
        }
        return label;
    }

    /**
     * Create and return a name for the specified discrete color scheme.
     *
     * @param colorScheme discrete color scheme, must not be null
     * @return a name for the specified discrete color scheme
     */
    protected String getNameFor(final DiscreteColorScheme colorScheme)
    {
        if (colorScheme == null)
        {
            throw new IllegalArgumentException("colorScheme must not be null");
        }
        return colorScheme.getName() + " with " + colorScheme.getSize() + " colors"; // i18n
    }

    /**
     * Create and return an icon for the specified discrete color scheme.
     *
     * @param colorScheme discrete color scheme, must not be null
     * @return an icon for the specified discrete color scheme
     */
    protected Icon getIconFor(final DiscreteColorScheme colorScheme)
    {
        if (colorScheme == null)
        {
            throw new IllegalArgumentException("colorScheme must not be null");
        }

        int n = colorScheme.getSize();
        int w = getIconWidth();
        int d = w / n;
        int h = getIconHeight();

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        for (int i = 0; i < n; i++)
        {
            g.setPaint(colorScheme.getColors().get(i));
            g.drawRect(i * d, 0, (i + 1) * d, h);
        }
        g.dispose();
        return new ImageIcon(image);
    }

    /**
     * Return the icon width for this discrete color scheme list cell renderer.
     *
     * @return the icon width for this discrete color scheme list cell renderer
     */
    public final int getIconWidth()
    {
        return iconWidth;
    }

    /**
     * Set the icon width for this discrete color scheme list cell renderer to <code>iconWidth</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconWidth icon width, must not be null
     */
    public final void setIconWidth(final int iconWidth)
    {
        if (iconWidth < 0)
        {
            throw new IllegalArgumentException("iconWidth must be at least zero");
        }
        int oldIconWidth = this.iconWidth;
        this.iconWidth = iconWidth;
        firePropertyChange("iconWidth", this.iconWidth, oldIconWidth);
    }

    /**
     * Return the icon height for this discrete color scheme list cell renderer.
     *
     * @return the icon height for this discrete color scheme list cell renderer
     */
    public final int getIconHeight()
    {
        return iconHeight;
    }

    /**
     * Set the icon height for this discrete color scheme list cell renderer to <code>iconHeight</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconHeight icon height, must not be null
     */
    public final void setIconHeight(final int iconHeight)
    {
        if (iconHeight < 0)
        {
            throw new IllegalArgumentException("iconHeight must be at least zero");
        }
        int oldIconHeight = this.iconHeight;
        this.iconHeight = iconHeight;
        firePropertyChange("iconHeight", this.iconHeight, oldIconHeight);
    }
}
