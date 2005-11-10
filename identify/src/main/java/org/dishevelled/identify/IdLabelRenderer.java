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

import javax.swing.Renderer;

import org.dishevelled.iconbundle.IconState;

/**
 * A renderer built from an IdLabel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IdLabelRenderer
    extends IdLabel
    implements Renderer
{
    /**
     * Cache of the previous state, for use in returning to the
     * proper state following de-selection.
     */
    private transient IconState previousState;


    /**
     * Create a new renderer with null value.
     */
    public IdLabelRenderer()
    {
        super();

        previousState = IconState.NORMAL;
    }


    /**
     * Create a new renderer with the specified value.
     *
     * @param value value
     */
    public IdLabelRenderer(final Object value)
    {
        super(value);

        previousState = IconState.NORMAL;
    }


    /** @see Renderer */
    public void setValue(final Object value, final boolean selected)
    {
        setValue(value);

        if (selected)
        {
            previousState = getIconState();
            setIconState(IconState.SELECTED);
        }
        else
        {
            setIconState(previousState);
        }
    }

    /** @see Renderer */
    public Component getComponent()
    {
        return this;
    }
}
