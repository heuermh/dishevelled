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
import java.awt.Color;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;

/**
 * Stripe list cell renderer.
 *
 * @since 1.2
 * @author  Michael Heuer
 */
public class StripeListCellRenderer extends DefaultListCellRenderer {
    /** Background color for odd rows. */
    private Color oddRowBackgroundColor = DEFAULT_ODD_ROW_BACKGROUND_COLOR;

    /** Default background color for odd rows, <code>2a5703, 5% alpha</code>. */
    public static final Color DEFAULT_ODD_ROW_BACKGROUND_COLOR = new Color(42, 87, 3, 12); // 2a5703, 5% alpha

    /**
     * Return the background color for odd rows. Defaults to DEFAULT_ODD_ROW_BACKGROUND_COLOR.
     *
     * @return the background color for odd rows
     */
    public final Color getOddRowBackgroundColor()
    {
        return oddRowBackgroundColor;
    }

    /**
     * Set the background color for odd rows to <code>oddRowBackgroundColor</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param oddRowBackgroundColor background color for odd rows, must not be null
     */
    public final void setOddRowBackgroundColor(final Color oddRowBackgroundColor)
    {
        if (oddRowBackgroundColor == null)
        {
            throw new IllegalArgumentException("oddRowBackgroundColor must not be null");
        }
        Color oldOddRowBackgroundColor = this.oddRowBackgroundColor;
        this.oddRowBackgroundColor = oddRowBackgroundColor;
        firePropertyChange("oddRowBackgroundColor", oldOddRowBackgroundColor, this.oddRowBackgroundColor);
    }

    @Override
    public Component getListCellRendererComponent(final JList list, final Object value, final int index, boolean isSelected, boolean hasFocus)
    {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);

        if (isSelected)
        {
            label.setForeground(UIManager.getColor("List.selectionForeground"));
            label.setBackground(UIManager.getColor("List.selectionBackground"));
        }
        else
        {
            label.setForeground(UIManager.getColor("List.foreground"));

            if (index % 2 == 1)
            {
                label.setBackground(oddRowBackgroundColor);
            }
            else
            {
                label.setBackground(UIManager.getColor("List.background"));
            }
        }
        return label;
    }

    /**
     * Install a stripe list cell renderer for the specified list.
     *
     * @param <T> list element type
     * @param list list, must not be null
     */
    public static <T> void install(final JList<T> list)
    {
        if (list == null)
        {
            throw new IllegalArgumentException("list must not be null");
        }
        StripeListCellRenderer renderer = new StripeListCellRenderer();
        list.setCellRenderer(renderer);
    }
}
