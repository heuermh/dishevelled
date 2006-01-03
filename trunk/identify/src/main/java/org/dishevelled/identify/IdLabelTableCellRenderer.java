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

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;

import javax.swing.table.TableCellRenderer;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.dishevelled.iconbundle.IconState;

/**
 * A table cell renderer built from an IdLabel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IdLabelTableCellRenderer
    extends IdLabel
    implements TableCellRenderer
{
    /** Empty no focus border. */
    private static Border noFocusBorder;


    /**
     * Create a new table cell renderer.
     */
    public IdLabelTableCellRenderer()
    {
        super();

        if (noFocusBorder == null)
        {
            noFocusBorder = new EmptyBorder(1, 1, 1, 1);
        }

        setBorder(noFocusBorder);
    }


    /** @see TableCellRenderer */
    public Component getTableCellRendererComponent(final JTable table,
                                                   final Object value,
                                                   final boolean isSelected,
                                                   final boolean cellHasFocus,
                                                   final int row,
                                                   final int column)
    {
        setValue(value);

        setFont(table.getFont());
        setForeground(isSelected ? table.getForeground() : table.getSelectionForeground());
        setBackground(isSelected ? table.getBackground() : table.getSelectionBackground());
        setIconState(isSelected ? IconState.SELECTED : IconState.NORMAL);
        setBorder(cellHasFocus ? UIManager.getBorder("Table.focusCellHighlightBorder") : noFocusBorder);

        setEnabled(table.isEnabled());

        return this;
    }


    //
    //  override property change notification
    //


    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final byte oldValue,
                                   final byte newValue)
    {
        // empty
    }

    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final char oldValue,
                                   final char newValue)
    {
        // empty
    }

    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final short oldValue,
                                   final short newValue)
    {
        // empty
    }

    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final int oldValue,
                                   final int newValue)
    {
        // empty
    }

    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final long oldValue,
                                   final long newValue)
    {
        // empty
    }

    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final float oldValue,
                                   final float newValue)
    {
        // empty
    }

    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final double oldValue,
                                   final double newValue)
    {
        // empty
    }

    /** @see javax.swing.JLabel */
    public void firePropertyChange(final String propertyName,
                                   final boolean oldValue,
                                   final boolean newValue)
    {
        // empty
    }
}
