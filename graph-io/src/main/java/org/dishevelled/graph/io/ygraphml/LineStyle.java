/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008 held jointly by the individual authors.

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
 * Line style.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class LineStyle
{
    /** Type for this line style. */
    private final String type;

    /** Width for this line style. */
    private final double width;

    /** Color for this line style. */
    private final String color;


    /**
     * Create a new line style from the specified parameters.
     *
     * @param type type for this line style, must not be null
     * @param width width for this line style
     * @param color color for this line style, must not be null
     */
    public LineStyle(final String type, final double width, final String color)
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
     * Return the type for this line style.
     * The type will not be null.
     *
     * @return the type for this line style
     */
    public String getType()
    {
        return type;
    }

    /**
     * Return the width for this line style.
     *
     * @return the width for this line style
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * Return the color for this line style.
     * The color will not be null.
     *
     * @return the color for this line style
     */
    public String getColor()
    {
        return color;
    }
}