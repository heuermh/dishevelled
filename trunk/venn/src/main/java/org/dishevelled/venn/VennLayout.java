/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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
package org.dishevelled.venn;

import java.awt.Shape;

import java.awt.geom.Point2D;

import java.awt.geom.Rectangle2D;

/**
 * Result of a venn diagram layout operation.
 *
 * @author  Michael Heuer
 */
public interface VennLayout
{

    /**
     * Return the number of shapes in this venn layout.
     *
     * @return the number of shapes in this venn layout
     */
    int size();

    /**
     * Return the shape at the specified index in this venn layout.
     *
     * @param index index
     * @return the shape at the specified index in this venn layout
     * @throws IndexOutOfBoundsException if <code>index</code> is out of bounds
     */
    Shape get(int index);

    /**
     * Return the lune center of the intersecting area defined by the specified indices.
     *
     * @param index first index
     * @param additional variable number of additional indices, if any
     * @return the lune center of the intersecting area defined by the specified indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    Point2D luneCenter(int index, int... additional);

    /**
     * Return the bounding rectangle of this venn layout.
     *
     * @return the bounding rectangle of this venn layout
     */
    Rectangle2D boundingRectangle();
}