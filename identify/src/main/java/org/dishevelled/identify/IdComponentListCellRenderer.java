/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2005 held jointly by the individual authors.

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

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.ListCellRenderer;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.dishevelled.iconbundle.IconState;

/**
 * A list cell renderer built from an IdComponent.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IdComponentListCellRenderer
    extends IdComponent
    implements ListCellRenderer
{
    /** Empty no focus border. */
    private static Border noFocusBorder;


    /**
     * Create a new list cell renderer.
     */
    public IdComponentListCellRenderer()
    {
        super();
        setOpaque(true);

        if (noFocusBorder == null)
        {
            noFocusBorder = new EmptyBorder(1, 1, 1, 1);
        }

        getTextLabel().setBorder(noFocusBorder);
    }


    /** @see javax.swing.ListCellRenderer */
    public Component getListCellRendererComponent(final JList list,
                                                  final Object value,
                                                  final int index,
                                                  final boolean isSelected,
                                                  final boolean cellHasFocus)
    {
        setValue(value);

        setBackground(list.getBackground());

        JLabel text = getTextLabel();

        text.setOpaque(true);
        text.setFont(list.getFont());
        text.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        text.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setIconState(isSelected ? IconState.SELECTED : IconState.NORMAL);
        text.setBorder(cellHasFocus ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);

        setEnabled(list.isEnabled());

        return this;
    }


    //
    //  override property change notification
    //


    // TODO:
    // is there anyway to override text and icon labels' property change notification?

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
