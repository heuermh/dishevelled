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

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * Stripe table cell renderer.
 *
 * @since 1.2
 * @author  Michael Heuer
 */
public class StripeTableCellRenderer extends DefaultTableCellRenderer
{
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
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
    {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected)
        {
            label.setForeground(UIManager.getColor("Table.selectionForeground"));
            label.setBackground(UIManager.getColor("Table.selectionBackground"));
        }
        else
        {
            if (hasFocus)
            {
                label.setForeground(UIManager.getColor("Table.focusCellForeground"));
            }
            else
            {
                label.setForeground(UIManager.getColor("Table.foreground"));
            }
            if (row % 2 == 1)
            {
                label.setBackground(oddRowBackgroundColor);
            }
            else
            {
                if (hasFocus)
                {
                    label.setBackground(UIManager.getColor("Table.focusCellBackground"));
                }
                else
                {
                    label.setBackground(UIManager.getColor("Table.background"));
                }
            }
        }
        return label;
    }

    /**
     * Install a stripe table cell renderer for the specified table.
     *
     * @param table table, must not be null
     */
    public static void install(final JTable table)
    {
        if (table == null)
        {
            throw new IllegalArgumentException("table must not be null");
        }
        StripeTableCellRenderer renderer = new StripeTableCellRenderer();
        table.setDefaultRenderer(Boolean.class, renderer);
        table.setDefaultRenderer(Double.class, renderer);
        table.setDefaultRenderer(Float.class, renderer);
        table.setDefaultRenderer(Integer.class, renderer);
        table.setDefaultRenderer(List.class, renderer);
        table.setDefaultRenderer(Long.class, renderer);
        table.setDefaultRenderer(String.class, renderer);
    }
}
