/*

    dsh-piccolo-ribbon  Piccolo2D ribbon nodes and supporting classes.
    Copyright (c) 2006-2010 held jointly by the individual authors.

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

import org.piccolo2d.PNode;

import org.piccolo2d.util.PBounds;
import org.piccolo2d.util.PPaintContext;

/**
 * Vertical ribbon.
 *
 * <p>
 * Displays an image textured/repeated vertically for the entire
 * height of a Piccolo2D camera view.  The anchor for the textured image can
 * be moved up or down using a cursor position and distance unit to
 * indicate scrolling or movement.
 * </p>
 *
 * <p>
 * Animate the ribbon by calling the <code>advance()</code> method
 * at regular intervals, using either the Swing timer
 * <pre>
 * final VerticalRibbon ribbon = new VerticalRibbon(...);
 * Timer t = new Timer(100, new ActionListener()
 *   {
 *     public void actionPerformed(final ActionEvent event)
 *     {
 *       ribbon.advance();
 *     }
 *   });
 * t.setRepeats(true);
 * t.start();
 * </pre>
 * or the Piccolo2D activity framework
 * <pre>
 * final PRoot root = ...;
 * final VerticalRibbon ribbon = new VerticalRibbon(...);
 * PActivity activity = new PActivity()
 *   {
 *    protected void activityStep(final long elapsedTime)
 *    {
 *      ribbon.advance();
 *    }
 *   };
 * root.addActivity(activity);
 * </pre>
 * </p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class VerticalRibbon
    extends PNode
{
    /** Minimum y coordinate. */
    private static final double MINIMUM_Y_COORDINATE = (1.0d - (Double.MAX_VALUE / 2.0d));

    /** Maximum height. */
    private static final double MAXIMUM_HEIGHT = Double.MAX_VALUE;

    /** Default cursor. */
    static final double DEFAULT_CURSOR = 0.0d;

    /** Default distance unit. */
    static final double DEFAULT_DISTANCE = 1.0d;

    /** State. */
    private State state;

    /** Cursor position for this vertical ribbon. */
    private double cursor;

    /** Distance unit for this vertical ribbon. */
    private double distance;

    /** Cached intersection rectangle. */
    private transient Rectangle2D intersection = new Rectangle2D.Double(0.0d, 0.0d, 0.0d, 0.0d);


    /**
     * Create a new vertical ribbon for the specified image.
     *
     * @param image image for this vertical ribbon, must not be null
     */
    public VerticalRibbon(final BufferedImage image)
    {
        this(image, DEFAULT_CURSOR, DEFAULT_DISTANCE);
    }

    /**
     * Create a new vertical ribbon for the specified image
     * with the specified cursor and distance unit.
     *
     * @param image image for this vertical ribbon, must not be null
     * @param cursor cursor position for this horizontal ribbon
     * @param distance distance unit for this horizontal ribbon
     */
    public VerticalRibbon(final BufferedImage image, final double cursor, final double distance)
    {
        super();

        if (image == null)
        {
            throw new IllegalArgumentException("image must not be null");
        }
        state = State.NOT_MOVING;

        this.cursor = cursor;
        this.distance = distance;

        setPaint(new TexturePaint(image, new Rectangle2D.Double(0.0d, cursor, image.getWidth(), image.getHeight())));
        setBounds(0.0d, MINIMUM_Y_COORDINATE, image.getWidth(), MAXIMUM_HEIGHT);
    }


    /**
     * Set this vertical ribbon not to move when animating.
     */
    public void pause()
    {
        state = State.NOT_MOVING;
    }

    /**
     * Return true if this vertical ribbon is not moving when animating.
     *
     * @return true if this vertical ribbon is not moving when animating
     */
    public boolean isNotMoving()
    {
        return state.isNotMoving();
    }

    /**
     * Set this vertical ribbon to move up when animating.
     */
    public void moveUp()
    {
        state = State.MOVING_UP;
    }

    /**
     * Return true if this vertical ribbon is moving up when animating.
     *
     * @return true if this vertical ribbon is moving up when animating
     */
    public boolean isMovingUp()
    {
        return state.isMovingUp();
    }

    /**
     * Set this vertical ribbon to move down when animating.
     */
    public void moveDown()
    {
        state = State.MOVING_DOWN;
    }

    /**
     * Return true if this vertical ribbon is moving down when animating.
     *
     * @return true if this vertical ribbon is moving down when animating
     */
    public boolean isMovingDown()
    {
        return state.isMovingDown();
    }

    /**
     * Advance this vertical ribbon one animation frame.
     */
    public void advance()
    {
        switch (state)
        {
        case MOVING_UP:
            //cursor -= distance * (1.0d / lastScaleFactor);
            cursor -= distance;
            updateTexturePaint();
            break;
        case MOVING_DOWN:
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
     * vertical ribbon.  Each call to <code>advance()</code> will
     * increment or decrement the cursor position for this vertical
     * ribbon by the factor of this distance unit and the last scale
     * factor recorded by the <code>paint(PPaintContext)</code> method.
     *
     * @see #advance
     * @return the distance unit for this vertical ribbon
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * Set the distance unit in user space coordinates for this
     * vertical ribbon to <code>distance</code>.
     *
     * <p>This is a bound property.</b>
     *
     * @param distance distance unit for this vertical ribbon
     */
    public void setDistance(final double distance)
    {
        double oldDistance = this.distance;
        this.distance = distance;
        firePropertyChange(-1, "distance", oldDistance, this.distance);
    }

    /**
     * Return the cursor for this vertical ribbon.
     *
     * @return the cursor for this vertical ribbon
     */
    double getCursor()
    {
        return cursor;
    }

    /**
     * Update the texture paint for this vertical ribbon
     * based on its cursor position.
     */
    private void updateTexturePaint()
    {
        TexturePaint texturePaint = (TexturePaint) getPaint();
        BufferedImage image = texturePaint.getImage();
        Rectangle2D anchor = texturePaint.getAnchorRect();
        anchor.setRect(anchor.getX(), cursor, anchor.getWidth(), anchor.getHeight());
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
         * When animating, this vertical ribbon does not move.
         */
        NOT_MOVING,

        /**
         * When animating, this vertical ribbon moves up.
         */
        MOVING_UP,

        /**
         * When animating, this vertical ribbon moves down.
         */
        MOVING_DOWN;


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
         * Return true if this state is MOVING_UP.
         *
         * @return true if this state is MOVING_UP
         */
        boolean isMovingUp()
        {
            return (this == MOVING_UP);
        }

        /**
         * Return true if this state is MOVING_DOWN.
         *
         * @return true if this state is MOVING_DOWN
         */
        boolean isMovingDown()
        {
            return (this == MOVING_DOWN);
        }
    };
}