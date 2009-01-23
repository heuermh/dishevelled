/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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

import javax.swing.AbstractAction;

import org.dishevelled.iconbundle.IconBundle;

/**
 * Abstract identifiable action.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class IdentifiableAction
    extends AbstractAction
    implements Identifiable
{
    /** Icon bundle. */
    private IconBundle iconBundle;


    /**
     * Create a new abstract identifiable action with the specified name
     * and icon bundle.
     *
     * @param name name for this identifiable action
     * @param iconBundle icon bundle for this identifiable action, must
     *    not be null
     */
    protected IdentifiableAction(final String name, final IconBundle iconBundle)
    {
        super(name);
        putValue(SHORT_DESCRIPTION, name);
        setIconBundle(iconBundle);
    }


    /**
     * Set the icon bundle for this identifiable action to <code>iconBundle</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconBundle icon bundle for this identifiable action, must not be null
     */
    public final void setIconBundle(final IconBundle iconBundle)
    {
        if (iconBundle == null)
        {
            throw new IllegalArgumentException("iconBundle must not be null");
        }
        IconBundle oldIconBundle = this.iconBundle;
        this.iconBundle = iconBundle;
        firePropertyChange("iconBundle", oldIconBundle, this.iconBundle);
    }

    /** {@inheritDoc} */
    public final String getName()
    {
        return (String) getValue(NAME);
    }

    /** {@inheritDoc} */
    public final IconBundle getIconBundle()
    {
        return iconBundle;
    }
}
