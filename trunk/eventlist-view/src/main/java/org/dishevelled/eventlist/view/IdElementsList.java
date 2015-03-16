/*

    dsh-eventlist-view  Views for event lists.
    Copyright (c) 2010-2015 held jointly by the individual authors.

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

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.identify.IdListCellRenderer;

/**
 * Identifiable elements list.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public class IdElementsList<E>
    extends ElementsList<E>
{
    /** Identifiable list cell renderer. */
    private final IdListCellRenderer listCellRenderer;

    /** Default icon size, {@link TangoProject#EXTRA_SMALL}. */
    public static final IconSize DEFAULT_ICON_SIZE = TangoProject.EXTRA_SMALL;


    /**
     * Create a new elements view with the specified model.
     *
     * @param model model, must not be null
     */
    public IdElementsList(final EventList<E> model)
    {
        super(model);
        listCellRenderer = new IdListCellRenderer(DEFAULT_ICON_SIZE);
        getList().setCellRenderer(listCellRenderer);
    }

    /**
     * Create a new elements view with the specified model.
     *
     * @param labelText label text
     * @param model model, must not be null
     */
    public IdElementsList(final String labelText, final EventList<E> model)
    {
        this(model);
        getLabel().setText(labelText);
    }



    /**
     * Return the icon size for this identifiable elements list.
     *
     * @return the icon size for this identifiable elements list
     */
    public final IconSize getIconSize()
    {
        return listCellRenderer.getIconSize();
    }

    /**
     * Set the icon size for this identifiable elements list to <code>iconSize</code>.
     * Defaults to {@link #DEFAULT_ICON_SIZE}.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconSize icon size for this identifiable elements list, must not be null
     */
    public final void setIconSize(final IconSize iconSize)
    {
        IconSize oldIconSize = listCellRenderer.getIconSize();
        listCellRenderer.setIconSize(iconSize);
        firePropertyChange("iconSize", oldIconSize, iconSize);
    }
}
