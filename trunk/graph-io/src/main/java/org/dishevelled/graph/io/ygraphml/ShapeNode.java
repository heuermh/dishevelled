/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2009 held jointly by the individual authors.

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
 * Shape node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ShapeNode
{
    /** Fill for this shape node. */
    private final Fill fill;

    /** Node label for this shape node. */
    private final NodeLabel nodeLabel;

    /** Border style for this shape node. */
    private final BorderStyle borderStyle;

    /** Shape for this shape node. */
    private final Shape shape;


    /**
     * Create a new shape node from the specified parameters.
     *
     * @param fill fill for this shape node, must not be null
     * @param nodeLabel node label for this shape node, must not be null
     * @param borderStyle border style for this shape node, must not be null
     * @param shape shape for this shape node, must not be null
     */
    public ShapeNode(final Fill fill,
                     final NodeLabel nodeLabel,
                     final BorderStyle borderStyle,
                     final Shape shape)
    {
        if (fill == null)
        {
            throw new IllegalArgumentException("fill must not be null");
        }
        if (nodeLabel == null)
        {
            throw new IllegalArgumentException("nodeLabel must not be null");
        }
        if (borderStyle == null)
        {
            throw new IllegalArgumentException("borderStyle must not be null");
        }
        if (shape == null)
        {
            throw new IllegalArgumentException("shape must not be null");
        }
        this.fill = fill;
        this.nodeLabel = nodeLabel;
        this.borderStyle = borderStyle;
        this.shape = shape;
    }


    /**
     * Return the fill for this shape node.
     * The fill will not be null.
     *
     * @return the fill for this shape node
     */
    public Fill getFill()
    {
        return fill;
    }

    /**
     * Return the node label for this shape node.
     * The node label will not be null.
     *
     * @return the node label for this shape node
     */
    public NodeLabel getNodeLabel()
    {
        return nodeLabel;
    }

    /**
     * Return the border style for this shape node.
     * The border style will not be null.
     *
     * @return the border style for this shape node
     */
    public BorderStyle getBorderStyle()
    {
        return borderStyle;
    }

    /**
     * Return the shape for this shape node.
     * The shape will not be null.
     *
     * @return the shape for this shape node
     */
    public Shape getShape()
    {
        return shape;
    }
}