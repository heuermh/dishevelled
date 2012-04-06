/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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
 * Poly line edge.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class PolyLineEdge
{
    /** Line style for this poly line edge. */
    private final LineStyle lineStyle;

    /** Arrows for this poly line edge. */
    private final Arrows arrows;

    /** Edge label for this poly line edge. */
    private final EdgeLabel edgeLabel;

    /** Bend style for this poly line edge. */
    private final BendStyle bendStyle;


    /**
     * Create a new poly line edge from the specified parameters.
     *
     * @param lineStyle line style for this poly line edge, must not be null
     * @param arrows arrows for this poly line edge, must not be null
     * @param edgeLabel edge label for this poly line edge, must not be null
     * @param bendStyle bend style for this poly line edge, must not be null
     */
    public PolyLineEdge(final LineStyle lineStyle,
                        final Arrows arrows,
                        final EdgeLabel edgeLabel,
                        final BendStyle bendStyle)
    {
        if (lineStyle == null)
        {
            throw new IllegalArgumentException("lineStyle must not be null");
        }
        if (arrows == null)
        {
            throw new IllegalArgumentException("arrows must not be null");
        }
        if (edgeLabel == null)
        {
            throw new IllegalArgumentException("edgeLabel must not be null");
        }
        if (bendStyle == null)
        {
            throw new IllegalArgumentException("bendStyle must not be null");
        }
        this.lineStyle = lineStyle;
        this.arrows = arrows;
        this.edgeLabel = edgeLabel;
        this.bendStyle = bendStyle;
    }


    /**
     * Return the line style for this poly line edge.
     * The line style will not be null.
     *
     * @return the line style for this poly line edge
     */
    public LineStyle getLineStyle()
    {
        return lineStyle;
    }

    /**
     * Return the arrows for this poly line edge.
     * The arrows will not be null.
     *
     * @return the arrows for this poly line edge
     */
    public Arrows getArrows()
    {
        return arrows;
    }

    /**
     * Return the edge label for this poly line edge.
     * The edge label will not be null.
     *
     * @return the edge label for this poly line edge
     */
    public EdgeLabel getEdgeLabel()
    {
        return edgeLabel;
    }

    /**
     * Return the bend style for this poly line edge.
     * The bend style will not be null.
     *
     * @return the bend style for this poly line edge
     */
    public BendStyle getBendStyle()
    {
        return bendStyle;
    }
}