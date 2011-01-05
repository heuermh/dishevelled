/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * Context menu listener.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ContextMenuListener
    extends MouseAdapter
{
    /** Context menu. */
    private final JPopupMenu contextMenu;


    /**
     * Create a new context menu listener for the specified context menu.
     *
     * @param contextMenu context menu, must not be null
     */
    public ContextMenuListener(final JPopupMenu contextMenu)
    {
        if (contextMenu == null)
        {
            throw new IllegalArgumentException("contextMenu must not be null");
        }
        this.contextMenu = contextMenu;
    }


    /** {@inheritDoc} */
    public void mousePressed(final MouseEvent event)
    {
        if (event.isPopupTrigger())
        {
            showContextMenu(event);
        }
    }

    /** {@inheritDoc} */
    public void mouseReleased(final MouseEvent event)
    {
        if (event.isPopupTrigger())
        {
            showContextMenu(event);
        }
    }

    /**
     * Show context menu.
     *
     * @param event mouse event
     */
    private void showContextMenu(final MouseEvent event)
    {
        contextMenu.show(event.getComponent(), event.getX(), event.getY());
    }
}