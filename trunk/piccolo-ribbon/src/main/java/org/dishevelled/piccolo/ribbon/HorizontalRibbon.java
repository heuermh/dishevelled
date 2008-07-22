/*

    dsh-piccolo-ribbon  Piccolo ribbon nodes and supporting classes.
    Copyright (c) 2006-2008 held jointly by the individual authors.

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
package org.dishevelled.piccolo.ribbon;

import java.awt.Graphics2D;
import java.awt.TexturePaint;

import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;

/**
 * Horizontal ribbon.
 *
 * <p>
 * Displays an image textured/repeated horizontally for the entire
 * width of a Piccolo camera view.  The anchor for the textured image can
 * be moved left or right using a cursor position and distance unit to
 * indicate scrolling or movement.
 * </p>
 *
 * <p>
 * Animate the ribbon by calling the <code>animate()</code> method
 * at regular intervals, using either the Swing timer
 * <pre>
 * final HorizontalRibbon ribbon = new HorizontalRibbon(...);
 * Timer t = new Timer(100, new ActionListener()
 *   {
 *     public void actionPerformed(final ActionEvent event)
 *     {
 *       ribbon.animate();
 *     }
 *   });
 * t.setRepeats(true);
 * t.start();
 * </pre>
 * or the Piccolo activity framework
 * <pre>
 * final PRoot root = ...;
 * final HorizontalRibbon ribbon = new HorizontalRibbon(...);
 * PActivity activity = new PActivity()
 *   {
 *    protected void activityStep(final long elapsedTime)
 *    {
 *      ribbon.animate();
 *    }
 *   };
 * root.addActivity(activity);
 * </pre>
 * </p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class HorizontalRibbon
    extends PNode
{
    /** Minimum x coordinate. */
    private static final double MINIMUM_X_COORDINATE = (1.0d - (Double.MAX_VALUE / 2.0d));

    /** Maximum width. */
    private static final double MAXIMUM_WIDTH = Double.MAX_VALUE;

    /** Default cursor. */
    static final double DEFAULT_CURSOR = 0.0d;

    /** Default distance unit. */
    static final double DEFAULT_DISTANCE = 1.0d;

    /** State. */
    private State state;

    /** Cursor position for this horizontal ribbon. */
    private double cursor;

    /** Distance unit for this horizontal ribbon. */
    private double distance;

    /** Cached intersection rectangle. */
    private transient Rectangle2D intersection = new Rectangle2D.Double(0.0d, 0.0d, 0.0d, 0.0d);


    /**
     * Create a new horizontal ribbon for the specified image.
     *
     * @param image image for this horizontal ribbon, must not be null
     */
    public HorizontalRibbon(final BufferedImage image)
    {
        this(image, DEFAULT_CURSOR, DEFAULT_DISTANCE);
    }

    /**
     * Create a new horizontal ribbon for the specified image
     * with the specified cursor and distance unit.
     *
     * @param image image for this horizontal ribbon, must not be null
     * @param cursor cursor position for this horizontal ribbon
     * @param distance distance unit for this horizontal ribbon
     */
    public HorizontalRibbon(final BufferedImage image, final double cursor, final double distance)
    {
        super();

        if (image == null)
        {
            throw new IllegalArgumentException("image must not be null");
        }
        state = State.NOT_MOVING;

        this.cursor = cursor;
        this.distance = distance;

        setPaint(new TexturePaint(image, new Rectangle2D.Double(cursor, 0.0d, image.getWidth(), image.getHeight())));
        setBounds(MINIMUM_X_COORDINATE, 0.0d, MAXIMUM_WIDTH, image.getHeight());
    }


    /**
     * Set this horizontal ribbon not to move when animating.
     */
    public void pause()
    {
        state = State.NOT_MOVING;
    }

    /**
     * Return true if this horizontal ribbon is not moving when animating.
     *
     * @return true if this horizontal ribbon is not moving when animating
     */
    public boolean isNotMoving()
    {
        return state.isNotMoving();
    }

    /**
     * Set this horizontal ribbon to move left when animating.
     */
    public void moveLeft()
    {
        state = State.MOVING_LEFT;
    }

    /**
     * Return true if this horizontal ribbon is moving left when animating.
     *
     * @return true if this horizontal ribbon is moving left when animating
     */
    public boolean isMovingLeft()
    {
        return state.isMovingLeft();
    }

    /**
     * Set this horizontal ribbon to move right when animating.
     */
    public void moveRight()
    {
        state = State.MOVING_RIGHT;
    }

    /**
     * Return true if this horizontal ribbon is moving right when animating.
     *
     * @return true if this horizontal ribbon is moving right when animating
     */
    public boolean isMovingRight()
    {
        return state.isMovingRight();
    }

    /**
     * Advance this horizontal ribbon one animation frame.
     */
    public void advance()
    {
        switch (state)
        {
        case MOVING_LEFT:
            //cursor -= distance * (1.0d / lastScaleFactor);
            cursor -= distance;
            updateTexturePaint();
            break;
        case MOVING_RIGHT:
            //cursor += distance * (1.0d / lastScaleFactor);
            cursor += distance;
            updateTexturePaint();
            break;
        default:
            break;
        }
    }

    /**
     * Return the distance unit in user space coordinates for this
     * horizontal ribbon.  Each call to <code>advance()</code> will
     * increment or decrement the cursor position for this horizontal
     * ribbon by the factor of this distance unit and the last scale
     * factor recorded by the <code>paint(PPaintContext)</code> method.
     *
     * @see #advance
     * @return the distance unit for this horizontal ribbon
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * Set the distance unit in user space coordinates for this
     * horizontal ribbon to <code>distance</code>.
     *
     * <p>This is a bound property.</b>
     *
     * @param distance distance unit for this horizontal ribbon
     */
    public void setDistance(final double distance)
    {
        double oldDistance = this.distance;
        this.distance = distance;
        firePropertyChange(-1, "distance", oldDistance, this.distance);
    }

    /**
     * Return the cursor for this horizontal ribbon.
     *
     * @return the cursor for this horizontal ribbon
     */
    double getCursor()
    {
        return cursor;
    }

    /**
     * Update the texture paint for this horizontal ribbon
     * based on its cursor position.
     */
    private void updateTexturePaint()
    {
        TexturePaint texturePaint = (TexturePaint) getPaint();
        BufferedImage image = texturePaint.getImage();
        Rectangle2D anchor = texturePaint.getAnchorRect();
        anchor.setRect(cursor, anchor.getY(), anchor.getWidth(), anchor.getHeight());
        setPaint(new TexturePaint(image, anchor));
    }

    /** {@inheritDoc} */
    protected void paint(final PPaintContext paintContext)
    {
        Graphics2D g = paintContext.getGraphics();

        Rectangle2D localClip = paintContext.getLocalClip();
        PBounds bounds = getBoundsReference();
        Rectangle2D.intersect(localClip, bounds, intersection);

        g.setPaint(getPaint());
        g.fill(intersection);
    }


    /**
     * State.
     */
    private enum State
    {
        /**
         * When animating, this horizontal ribbon does not move.
         */
        NOT_MOVING,

        /**
         * When animating, this horizontal ribbon moves left.
         */
        MOVING_LEFT,

        /**
         * When animating, this horizontal ribbon moves right.
         */
        MOVING_RIGHT;


        /**
         * Return true if this state is NOT_MOVING.
         *
         * @return true if this state is NOT_MOVING
         */
        boolean isNotMoving()
        {
            return (this == NOT_MOVING);
        }

        /**
         * Return true if this state is MOVING_LEFT.
         *
         * @return true if this state is MOVING_LEFT
         */
        boolean isMovingLeft()
        {
            return (this == MOVING_LEFT);
        }

        /**
         * Return true if this state is MOVING_RIGHT.
         *
         * @return true if this state is MOVING_RIGHT
         */
        boolean isMovingRight()
        {
            return (this == MOVING_RIGHT);
        }
    };
}