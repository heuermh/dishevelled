/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2011 held jointly by the individual authors.

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
package org.dishevelled.graph.io.ygraphml;

/**
 * Fill.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Fill
{
    /** Color for this fill. */
    private final String color;

    /** True if this fill is transparent. */
    private final boolean transparent;


    /**
     * Create a new fill from the specified parameters.
     *
     * @param color color for this fill, must not be null
     * @param transparent true if this fill is transparent
     */
    public Fill(final String color, final boolean transparent)
    {
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        this.color = color;
        this.transparent = transparent;
    }


    /**
     * Return the color for this fill.
     * The color will not be null.
     *
     * @return the color for this fill
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Return true if this fill is transparent.
     *
     * @return true if this fill is transparent
     */
    public boolean isTransparent()
    {
        return transparent;
    }
}