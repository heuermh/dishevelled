/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2013 held jointly by the individual authors.

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
 * Shape.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Shape
{
    /** Type for this shape. */
    private final String type;


    /**
     * Create a new shape from the specified parameters.
     *
     * @param type type for this shape, must not be null
     */
    public Shape(final String type)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("type must not be null");
        }
        this.type = type;
    }


    /**
     * Return the type for this shape.
     * The type will not be null.
     *
     * @return the type for this shape
     */
    public String getType()
    {
        return type;
    }
}