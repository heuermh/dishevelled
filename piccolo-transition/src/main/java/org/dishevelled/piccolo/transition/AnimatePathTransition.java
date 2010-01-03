/*

    dsh-piccolo-transition  Transitions implemented as Piccolo2D activities.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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
package org.dishevelled.piccolo.transition;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.activities.PInterpolatingActivity;

import edu.umd.cs.piccolo.util.PUtil;

import org.apache.batik.ext.awt.geom.PathLength;

/**
 * Animate path transition.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class AnimatePathTransition
    extends PInterpolatingActivity
{
    /** Node for this animate path transition. */
    private final PNode node;

    /** Path. */
    private final Path2D path;

    /** Path length. */
    private final PathLength pathLength;


    /**
     * Create a new animate path transition for the specified node and
     * the specified path.
     *
     * @param node node, must not be null
     * @param path path, must not be null
     * @param duration amount of time that this transition should take to complete,
     *    <code>-1</code> for infinite
     */
    public AnimatePathTransition(final PNode node, final Path2D path, final long duration)
    {
        super(duration, PUtil.DEFAULT_ACTIVITY_STEP_RATE);
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if (path == null)
        {
            throw new IllegalArgumentException("path must not be null");
        }
        this.node = node;
        this.path = path;
        pathLength = new PathLength(this.path);
    }


    /** {@inheritDoc} */
    public void setRelativeTargetValue(final float zeroToOne)
    {
        float totalLength = pathLength.lengthOfPath();
        float length = totalLength * zeroToOne;
        Point2D point = pathLength.pointAtLength(length);
        node.setOffset(point.getX(), point.getY());
    }

    /**
     * Return the path for this animate path transition.  The path
     * will not be null.
     *
     * @return the path for this animate path transition
     */
    public Path2D getPath()
    {
        return path;
    }
}