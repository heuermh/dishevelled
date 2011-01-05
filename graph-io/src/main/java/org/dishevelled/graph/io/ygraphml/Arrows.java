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
 * Arrows.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Arrows
{
    /** Source for this arrows. */
    private final String source;

    /** Target for this arrows. */
    private final String target;


    /**
     * Create a new arrows from the specified parameters.
     *
     * @param source source for this arrows, must not be null
     * @param target target for this arrows, must not be null
     */
    public Arrows(final String source, final String target)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target must not be null");
        }
        this.source = source;
        this.target = target;
    }


    /**
     * Return the source for this arrows.
     * The source will not be null.
     *
     * @return the source for this arrows
     */
    public String getSource()
    {
        return source;
    }

    /**
     * Return the target for this arrows.
     * The target will not be null.
     *
     * @return the target for this arrows
     */
    public String getTarget()
    {
        return target;
    }
}