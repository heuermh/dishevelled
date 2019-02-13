/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
import java.io.ObjectStreamException;

/**
 * Extensible typesafe enumeration of icon sizes.
 *
 * To create a non-default icon size, subclass IconSize
 * as follows:
 * <pre>
 *  IconSize CUSTOM_48X52 = new IconSize(48, 52) { }
 * </pre>
 * and optionally override readResolve to support serialization.
 *
 * @author  Michael Heuer
 */
public abstract class IconSize
    implements Serializable
{
    /** Icon width. */
    private final int width;

    /** Icon height. */
    private final int height;

    /** The string value of <code>width + "x" + height</code>. */
    private final String toStringValue;


    /**
     * Create a new icon size with the specified width
     * and height.
     *
     * @param width icon width
     * @param height icon height
     */
    protected IconSize(final int width, final int height)
    {
        this.width = width;
        this.height = height;
        toStringValue = width + "x" + height;
    }


    /**
     * Return the string value of <code>width + "x" + height</code>.
     *
     * @return the string value of the icon width and height
     */
    public final String toString()
    {
        return toStringValue;
    }

    /**
     * Return the icon width.
     *
     * @return the icon width
     */
    public final int getWidth()
    {
        return width;
    }

    /**
     * Return the icon height.
     *
     * @return the icon height
     */
    public final int getHeight()
    {
        return height;
    }

    /**
     * Override equals and make it final to prevent
     * subclasses from changing the implementation.
     *
     * @param o object
     * @return <code>super.equals(o)</code>
     */
    public final boolean equals(final Object o)
    {
        return super.equals(o);
    }

    /**
     * Override hashCode and make it final to prevent
     * subclasses from changing the implementation.
     *
     * @return <code>super.hashCode()</code>
     */
    public final int hashCode()
    {
        return super.hashCode();
    }


    /** Default 16x16 icon size. */
    public static final IconSize DEFAULT_16X16 = new IconSize(16, 16)
        {
            // empty
        };

    /** Default 24x24 icon size. */
    public static final IconSize DEFAULT_24X24 = new IconSize(24, 24)
        {
            // empty
        };

    /** Default 32x32 icon size. */
    public static final IconSize DEFAULT_32X32 = new IconSize(32, 32)
        {
            // empty
        };

    /** Default 48x48 icon size. */
    public static final IconSize DEFAULT_48X48 = new IconSize(48, 48)
        {
            // empty
        };

    /** Default 64x64 icon size. */
    public static final IconSize DEFAULT_64X64 = new IconSize(64, 64)
        {
            // empty
        };

    /** Default 96x96 icon size. */
    public static final IconSize DEFAULT_96X96 = new IconSize(96, 96)
        {
            // empty
        };

    /** Default 128x128 icon size. */
    public static final IconSize DEFAULT_128X128 = new IconSize(128, 128)
        {
            // empty
        };

    /**
     * Private array of default enumeration values.
     */
    private static final IconSize[] VALUES_ARRAY = {
                                                     DEFAULT_16X16,
                                                     DEFAULT_24X24,
                                                     DEFAULT_32X32,
                                                     DEFAULT_48X48,
                                                     DEFAULT_64X64,
                                                     DEFAULT_96X96,
                                                     DEFAULT_128X128
                                                    };

    /**
     * Public list of default enumeration values.
     */
    public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));


    /** Next ordinal -- necessary for serialization. */
    private static int nextOrdinal = 0;

    /** Ordinal -- necessary for serialization. */
    private final int ordinal = nextOrdinal++;

    /**
     * Override readResolve to return the proper static
     * final enumeration values.
     *
     * @return the proper static final enumeration value
     *
     * @throws ObjectStreamException if an error occurs
     */
    Object readResolve()
        throws ObjectStreamException
    {
        return VALUES_ARRAY[ordinal];
    }
}
