/*

    dsh-eventlist-view  Views for event lists.
    Copyright (c) 2010-2019 held jointly by the individual authors.

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
package org.dishevelled.eventlist.view;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.gui.TableFormat;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdTableCellRenderer;

/**
 * Identifiable elements table.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public class IdElementsTable<E>
    extends ElementsTable<E>
{
    /** Identifiable table cell renderer. */
    private final IdTableCellRenderer tableCellRenderer = new IdTableCellRenderer(DEFAULT_ICON_SIZE);

    /** Default icon size, {@link TangoProject#EXTRA_SMALL}. */
    public static final IconSize DEFAULT_ICON_SIZE = TangoProject.EXTRA_SMALL;


    /**
     * Create a new identifiable elements table view with the specified model, table format, and column class.
     *
     * @param model model, must not be null
     * @param tableFormat table format, must not be null
     * @param columnClass column class, must not be null
     */
    public IdElementsTable(final EventList<E> model,
                           final TableFormat<E> tableFormat,
                           final Class<? extends E> columnClass)
    {
        super(model, tableFormat);
        if (columnClass == null)
        {
            throw new IllegalArgumentException("columnClass must not be null");
        }
        getTable().setDefaultRenderer(columnClass, tableCellRenderer);
        updateRowHeight();
    }

    /**
     * Create a new identifiable elements table view with the specified label text, model, table format,
     * and column class.
     *
     * @param labelText label text
     * @param model model, must not be null
     * @param tableFormat table format, must not be null
     * @param columnClass column class, must not be null
     */
    public IdElementsTable(final String labelText,
                           final EventList<E> model,
                           final TableFormat<E> tableFormat,
                           final Class<? extends E> columnClass)
    {
        super(labelText, model, tableFormat);
        if (columnClass == null)
        {
            throw new IllegalArgumentException("columnClass must not be null");
        }
        getTable().setDefaultRenderer(columnClass, tableCellRenderer);
        updateRowHeight();
    }


    /**
     * Return the icon size for this identifiable elements table.
     *
     * @return the icon size for this identifiable elements table
     */
    public final IconSize getIconSize()
    {
        return tableCellRenderer.getIconSize();
    }

    /**
     * Set the icon size for this identifiable elements table to <code>iconSize</code>.
     * Defaults to {@link #DEFAULT_ICON_SIZE}.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconSize icon size for this identifiable elements table, must not be null
     */
    public final void setIconSize(final IconSize iconSize)
    {
        IconSize oldIconSize = tableCellRenderer.getIconSize();
        tableCellRenderer.setIconSize(iconSize);
        updateRowHeight();
        firePropertyChange("iconSize", oldIconSize, iconSize);
    }

    /**
     * Update row height, called when the icon size changes.
     *
     * @since 2.1
     */
    protected void updateRowHeight()
    {
        getTable().setRowHeight(getIconSize().getHeight() + 4);
    }
}
