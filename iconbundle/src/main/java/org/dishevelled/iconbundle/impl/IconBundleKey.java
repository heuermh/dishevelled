/*

    dsh-iconbundle  Bundles of variants for icon images.
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
package org.dishevelled.iconbundle.impl;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * A compound key of icon text direction, icon state,
 * and icon size.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class IconBundleKey
{
    /** Hash start. */
    private static final int HASH_START = 17;

    /** Hash factor. */
    private static final int HASH_FACTOR = 37;

    /** Icon text direction. */
    private final IconTextDirection direction;

    /** Icon state. */
    private final IconState state;

    /** Icon size. */
    private final IconSize size;


    /**
     * Create a new compound key with the specified
     * icon text direction, icon state, and icon size.
     *
     * @param direction icon text direction, must not be null
     * @param state icon state, must not be null
     * @param size icon size, must not be null
     */
    public IconBundleKey(final IconTextDirection direction,
                         final IconState state,
                         final IconSize size)
    {
        if (direction == null)
        {
            throw new IllegalArgumentException("direction must not be null");
        }
        if (state == null)
        {
            throw new IllegalArgumentException("state must not be null");
        }
        if (size == null)
        {
            throw new IllegalArgumentException("size must not be null");
        }

        this.direction = direction;
        this.state = state;
        this.size = size;
    }


    /**
     * Return the icon text direction of this key.
     *
     * @return the icon text direction
     */
    public IconTextDirection getDirection()
    {
        return direction;
    }

    /**
     * Return the icon size of this key.
     *
     * @return the icon size
     */
    public IconSize getSize()
    {
        return size;
    }

    /**
     * Return the icon state of this key.
     *
     * @return the icon state
     */
    public IconState getState()
    {
        return state;
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        int result = HASH_START;
        result = HASH_FACTOR * result + direction.hashCode();
        result = HASH_FACTOR * result + state.hashCode();
        result = HASH_FACTOR * result + size.hashCode();
        return result;
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof IconBundleKey))
        {
            return false;
        }

        IconBundleKey key = (IconBundleKey) o;

        return (direction.equals(key.getDirection()))
            && (state.equals(key.getState()))
            && (size.equals(key.getSize()));
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("key(");
        sb.append(direction);
        sb.append(", ");
        sb.append(state);
        sb.append(", ");
        sb.append(size);
        sb.append(")");
        return sb.toString();
    }
}
