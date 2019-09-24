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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import java.text.NumberFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import org.dishevelled.identify.StripeListCellRenderer;

/**
 * List cell renderer for colors.
 *
 * @author  Michael Heuer
 */
public class ColorListCellRenderer extends StripeListCellRenderer
{
    /** Default icon width, <code>64</code>. */
    public static final int DEFAULT_ICON_WIDTH = 64;

    /** Default icon height, <code>14</code>. */
    public static final int DEFAULT_ICON_HEIGHT = 14;

    /** Icon width. */
    private int iconWidth = DEFAULT_ICON_WIDTH;

    /** Icon height. */
    private int iconHeight = DEFAULT_ICON_HEIGHT;

    /** RBG color components. */
    private static final float[] RGBA = new float[4];


    /**
     * Create a new color list cell renderer.
     */
    public ColorListCellRenderer()
    {
        super();
    }

    /**
     * Create a new color list cell renderer.
     *
     * @param iconWidth icon width, must be at least zero
     * @param iconHeight icon height, must be at least zero
     */
    public ColorListCellRenderer(final int iconWidth, final int iconHeight)
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

        if (value instanceof Color)
        {
            Color colorScheme = (Color) value;
            label.setText(getNameFor(colorScheme));
            label.setIcon(getIconFor(colorScheme));
        }
        return label;
    }

    /**
     * Create and return a name for the specified color. Defaults to
     * <code>#FFFFFF rgb(255, 255, 255)</code>.
     *
     * @param color color, must not be null
     * @return a name for the specified color
     */
    protected String getNameFor(final Color color)
    {
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        return toHex(color) + " " + toRgb(color);
    }

    /**
     * Format the specified color as <code>rgb(255, 255, 255)</code>.
     *
     * @param color color to format, must not be null
     * @return the specified color formatted as <code>rgb(255, 255, 255)</code>
     */
    protected final String toRgb(final Color color)
    {
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        return String.format("rgb(%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Format the specified color as <code>rgb(255, 255, 255, 1.0)</code>.
     *
     * @param color color to format, must not be null
     * @return the specified color formatted as <code>rgb(255, 255, 255, 1.0)</code>
     */
    protected final String toIntegerRgba(final Color color)
    {
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        color.getRGBComponents(RGBA);
        StringBuilder sb = new StringBuilder();
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(4);
        sb.append("rgba(");
        sb.append(color.getRed());
        sb.append(", ");
        sb.append(color.getGreen());
        sb.append(", ");
        sb.append(color.getBlue());
        sb.append(", ");
        sb.append(format.format(RGBA[3]));
        sb.append(")");
        return sb.toString();
    }

    /**
     * Format the specified color as <code>rgba(1.0, 1.0, 1.0, 1.0)</code>.
     *
     * @param color color to format, must not be null
     * @return the specified color formatted as <code>rgba(1.0, 1.0, 1.0, 1.0)</code>
     */
    protected final String toFloatRgba(final Color color)
    {
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        color.getRGBComponents(RGBA);
        StringBuilder sb = new StringBuilder();
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(4);
        sb.append("rgba(");
        sb.append(format.format(RGBA[0]));
        sb.append(", ");
        sb.append(format.format(RGBA[1]));
        sb.append(", ");
        sb.append(format.format(RGBA[2]));
        sb.append(", ");
        sb.append(format.format(RGBA[3]));
        sb.append(")");
        return sb.toString();
    }

    /**
     * Format the specified color as <code>#FFFFFF</code>.
     *
     * @param color color to format, must not be null
     * @return the specified color formatted as <code>#FFFFFF</code>
     */
    protected final String toHex(final Color color)
    {
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Create and return an icon for the specified color.
     *
     * @param color color, must not be null
     * @return an icon for the specified color
     */
    protected Icon getIconFor(final Color color)
    {
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        int w = getIconWidth();
        int h = getIconHeight();

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setPaint(color);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return new ImageIcon(image);
    }

    /**
     * Return the icon width for this color list cell renderer.
     *
     * @return the icon width for this color list cell renderer
     */
    public final int getIconWidth()
    {
        return iconWidth;
    }

    /**
     * Set the icon width for this color list cell renderer to <code>iconWidth</code>.
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
     * Return the icon height for this color list cell renderer.
     *
     * @return the icon height for this color list cell renderer
     */
    public final int getIconHeight()
    {
        return iconHeight;
    }

    /**
     * Set the icon height for this color list cell renderer to <code>iconHeight</code>.
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
