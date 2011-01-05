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
 * Border style.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BorderStyle
{
    /** Type for this border style. */
    private final String type;

    /** Width for this border style. */
    private final double width;

    /** Color for this border style. */
    private final String color;


    /**
     * Create a new border style from the specified parameters.
     *
     * @param type type for this border style, must not be null
     * @param width width for this border style
     * @param color color for this border style, must not be null
     */
    public BorderStyle(final String type, final double width, final String color)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("type must not be null");
        }
        if (color == null)
        {
            throw new IllegalArgumentException("color must not be null");
        }
        this.type = type;
        this.width = width;
        this.color = color;
    }


    /**
     * Return the type for this border style.
     * The type will not be null.
     *
     * @return the type for this border style
     */
    public String getType()
    {
        return type;
    }

    /**
     * Return the width for this border style.
     *
     * @return the width for this border style
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * Return the color for this border style.
     * The color will not be null.
     *
     * @return the color for this border style
     */
    public String getColor()
    {
        return color;
    }
}