/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2012 held jointly by the individual authors.

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
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

/**
 * Context menu button.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ContextMenuButton
    extends JButton
{
    /** Context menu. */
    private final JPopupMenu contextMenu;

    /** Mouse listener. */
    private final MouseListener listener = new MouseAdapter()
        {
            /** {@inheritDoc} */
            public void mousePressed(final MouseEvent event)
            {
                contextMenu.show(event.getComponent(), 0, event.getComponent().getHeight());
            }
        };

    /** Default image icon. */
    private static final ImageIcon DEFAULT_ICON = new ImageIcon(ContextMenuButton.class.getResource("context-menu.png"));

    /** GTK look and feel image icon. */
    private static final ImageIcon GTK_ICON = new ImageIcon(ContextMenuButton.class.getResource("context-menu-gtk.png"));


    /**
     * Create a new context menu button with the specified context menu.
     *
     * @param contextMenu context menu, must not be null
     */
    public ContextMenuButton(final JPopupMenu contextMenu)
    {
        super();
        if (contextMenu == null)
        {
            throw new IllegalArgumentException("contextMenu must not be null");
        }
        this.contextMenu = contextMenu;
        setIcon(IdentifyUtils.isGTKLookAndFeel() ? GTK_ICON : DEFAULT_ICON);
        setToolTipText("View menu");
        addMouseListener(listener);
    }


    /**
     * Release the resources consumed by this context menu button so that it may eventually be garbage collected.
     */
    public void dispose()
    {
        removeMouseListener(listener);
    }
}