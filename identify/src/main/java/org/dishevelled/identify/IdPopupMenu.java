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

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.dishevelled.iconbundle.IconSize;

/**
 * An extension of JPopupMenu that supports identifiable actions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IdPopupMenu
    extends JPopupMenu
{

    /**
     * {@inheritDoc}
     *
     * <p>If the specified action is an instance of {@link IdentifiableAction}, an
     * {@link IdMenuItem} is created and added to this menu.</p>
     */
    public JMenuItem add(final Action action)
    {
        if (action instanceof IdentifiableAction)
        {
            IdentifiableAction identifiableAction = (IdentifiableAction) action;
            return super.add(new IdMenuItem(identifiableAction));
        }
        else
        {
            return super.add(action);
        }
    }

    /**
     * Create a new menu item for the specified identifiable action with the specified icon size
     * and append it to the end of this menu.
     *
     * @param action identifiable action to add
     * @param iconSize icon size, must not be null
     * @return the new menu item for the specified identifiable action with the specified icon size
     */
    public IdMenuItem add(final IdentifiableAction action, final IconSize iconSize)
    {
        IdMenuItem menuItem = new IdMenuItem(action, iconSize);
        super.add(menuItem);
        return menuItem;
    }
}