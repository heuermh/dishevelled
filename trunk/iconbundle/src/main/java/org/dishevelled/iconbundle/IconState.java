/*

    dsh-iconbundle  Bundles of variants for icon images.
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
package org.dishevelled.iconbundle;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import java.io.Serializable;

/**
 * Typesafe enumeration of icon states.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IconState
    implements Serializable
{
    /** State name. */
    private String name;


    /**
     * Create a new IconState with the specified name.
     *
     * @param name state name
     */
    private IconState(final String name)
    {
        this.name = name;
    }


    /**
     * Return the state name.
     *
     * @return the state name
     */
    public String toString()
    {
        return name;
    }

    /**
     * Return true if this icon state is <code>IconState.NORMAL</code>.
     *
     * @return true if this icon state is <code>IconState.NORMAL</code>
     */
    public boolean isNormal()
    {
        return (this == NORMAL);
    }

    /**
     * Return true if this icon state is <code>IconState.ACTIVE</code>.
     *
     * @return true if this icon state is <code>IconState.ACTIVE</code>
     */
    public boolean isActive()
    {
        return (this == ACTIVE);
    }

    /**
     * Return true if this icon state is <code>IconState.MOUSEOVER</code>.
     *
     * @return true if this icon state is <code>IconState.MOUSEOVER</code>
     */
    public boolean isMouseover()
    {
        return (this == MOUSEOVER);
    }

    /**
     * Return true if this icon state is <code>IconState.SELECTED</code>.
     *
     * @return true if this icon state is <code>IconState.SELECTED</code>
     */
    public boolean isSelected()
    {
        return (this == SELECTED);
    }

    /**
     * Return true if this icon state is <code>IconState.DRAGGING</code>.
     *
     * @return true if this icon state is <code>IconState.DRAGGING</code>
     */
    public boolean isDragging()
    {
        return (this == DRAGGING);
    }

    /**
     * Return true if this icon state is <code>IconState.DISABLED</code>.
     *
     * @return true if this icon state is <code>IconState.DISABLED</code>
     */
    public boolean isDisabled()
    {
        return (this == DISABLED);
    }


    /** Normal icon state. */
    public static final IconState NORMAL = new IconState("normal");

    /** Active icon state. */
    public static final IconState ACTIVE = new IconState("active");

    /** Mouseover icon state. */
    public static final IconState MOUSEOVER = new IconState("mouseover");

    /** Selected icon state. */
    public static final IconState SELECTED = new IconState("selected");

    /** Dragging icon state. */
    public static final IconState DRAGGING = new IconState("dragging");

    /** Disabled icon state. */
    public static final IconState DISABLED = new IconState("disabled");

    /**
     * Private array of enumeration values.
     */
    private static final IconState[] VALUES_ARRAY = {
                                                      NORMAL,
                                                      ACTIVE,
                                                      MOUSEOVER,
                                                      SELECTED,
                                                      DRAGGING,
                                                      DISABLED
                                                    };

    /**
     * Public list of enumeration values.
     */
    public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
}
