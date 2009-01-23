/*

    dsh-piccolo-transition  Transitions implemented as Piccolo2D activities.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.activities.PInterpolatingActivity;

import edu.umd.cs.piccolo.util.PUtil;

/**
 * Dim transition.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class DimTransition
    extends PInterpolatingActivity
{
    /** Node for this dim transition. */
    private final PNode node;

    /** Transparency at the time this transition starts. */
    private float source;

    /** Transparency at the time this transition finishes. */
    private final float dest;

    /** Default transparency at the time this transition finishes. */
    public static final float DEFAULT_DESTINATION_TRANSPARENCY = 0.5f;


    /**
     * Create a new dim transition for the specified node.
     *
     * @param node node, must not be null
     * @param duration amount of time that this transition should take to complete,
     *    <code>-1</code> for infinite
     */
    public DimTransition(final PNode node, final long duration)
    {
        this(node, duration, DEFAULT_DESTINATION_TRANSPARENCY);
    }

    /**
     * Create a new dim transition for the specified node.
     *
     * @param node node, must not be null
     * @param duration amount of time that this transition should take to complete,
     *    <code>-1</code> for infinite
     * @param dest transparency at the time this transition finishes, must be between
     *    <code>0.0f</code> and <code>1.0f</code>, inclusive
     */
    public DimTransition(final PNode node, final long duration, final float dest)
    {
        super(duration, PUtil.DEFAULT_ACTIVITY_STEP_RATE);
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if ((dest < 0.0f) || (dest > 1.0f))
        {
            throw new IllegalArgumentException("dest must be between 0.0f and 1.0f, inclusive");
        }
        this.node = node;
        this.dest = dest;
    }


    /** {@inheritDoc} */
    protected void activityStarted()
    {
        source = node.getTransparency();
        super.activityStarted();
    }

    /** {@inheritDoc} */
    public void setRelativeTargetValue(final float zeroToOne)
    {
        float current = source + (zeroToOne * (dest - source));
        if (current < source)
        {
            node.setTransparency(current);
        }
    }

    /**
     * Return the transparency at the time this transition finishes.  Defaults
     * to <code>0.5f</code>.
     *
     * @see #DEFAULT_DESTINATION_TRANSPARENCY
     * @return the transparency at the time this transition finishes
     */
    public float getDestinationTransparency()
    {
        return dest;
    }
}