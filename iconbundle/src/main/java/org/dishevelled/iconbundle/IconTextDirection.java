/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
package org.dishevelled.iconbundle;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import java.io.Serializable;

/**
 * Typesafe enumeration of icon text directions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IconTextDirection
    implements Serializable
{
    /** Text direction name. */
    private String name;


    /**
     * Create a new text direction with the specified name.
     *
     * @param name text direction name
     */
    private IconTextDirection(final String name)
    {
        this.name = name;
    }


    /**
     * Return the text direction name.
     *
     * @return the text direction name
     */
    public String toString()
    {
        return name;
    }

    /** Left-to-right horizontal text direction. */
    public static final IconTextDirection LEFT_TO_RIGHT = new IconTextDirection("ltr");

    /** Right-to-left horizontal text direction. */
    public static final IconTextDirection RIGHT_TO_LEFT = new IconTextDirection("rtl");

    /**
     * Private array of enumeration values.
     */
    private static final IconTextDirection[] VALUES_ARRAY = {
                                                              LEFT_TO_RIGHT,
                                                              RIGHT_TO_LEFT
                                                            };

    /**
     * Public list of enumeration values.
     */
    public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
}
