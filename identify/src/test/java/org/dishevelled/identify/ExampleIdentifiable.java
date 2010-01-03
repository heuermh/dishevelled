/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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

import org.dishevelled.iconbundle.IconBundle;

/**
 * Example identifiable object.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Id$
 */
public final class ExampleIdentifiable
    implements Identifiable
{
    /** Name for this example identifiable. */
    private final String name;

    /** Icon bundle for this example identifiable. */
    private final IconBundle iconBundle;


    /**
     * Create a new example identifiable from the specified parameters.
     *
     * @param name name for this example identifiable, must not be null
     * @param iconBundle icon bundle for this example identifiable, must not be null
     */
    public ExampleIdentifiable(final String name, final IconBundle iconBundle)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("name must not be null");
        }
        if (iconBundle == null)
        {
            throw new IllegalArgumentException("iconBundle must not be null");
        }

        this.name = name;
        this.iconBundle = iconBundle;
    }


    /** {@inheritDoc} */
    public String getName()
    {
        return name;
    }

    /** {@inheritDoc} */
    public IconBundle getIconBundle()
    {
        return iconBundle;
    }
}