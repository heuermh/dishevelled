/*

    dsh-piccolo-ease  Piccolo2D easing activities and supporting classes.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.piccolo.ease;

import org.piccolo2d.activities.PInterpolatingActivity;

import org.piccolo2d.util.PUtil;

import org.dishevelled.interpolate.EasingFunction;

/**
 * Easing activity.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class EasingActivity
    extends PInterpolatingActivity
{
    /** Easing function for this easing activity. */
    private final EasingFunction easingFunction;


    /**
     * Create a new easing activity with the specified easing function and
     * duration in milliseconds.
     *
     * @param easingFunction easing function, must not be null
     * @param duration duration in milliseconds, must be at least <code>1</code> ms
     */
    public EasingActivity(final EasingFunction easingFunction, final long duration)
    {
        super(duration, PUtil.DEFAULT_ACTIVITY_STEP_RATE);
        if (easingFunction == null)
        {
            throw new IllegalArgumentException("easingFunction must not be null");
        }
        if (duration < 1)
        {
            throw new IllegalArgumentException("duration must be at least 1 ms");
        }
        this.easingFunction = easingFunction;
        super.setSlowInSlowOut(false);
    }

    /**
     * Create a new easing activity with the specified easing function and
     * duration in milliseconds.
     *
     * @param easingFunction easing function, must not be null
     * @param duration duration in milliseconds, must be at least <code>1</code> ms
     * @param stepRate step rate in milliseconds
     * @param startTime start time in milliseconds, relative to <code>System.currentTimeMillis()</code>
     * @param loopCount number of times this easing activity should reschedule itself
     * @param mode defines how this easing activity interpolates between states
     */
    public EasingActivity(final EasingFunction easingFunction,
                          final long duration,
                          final long stepRate,
                          final long startTime,
                          final int loopCount,
                          final int mode)
    {
        super(duration, stepRate, startTime, loopCount, mode);
        if (easingFunction == null)
        {
            throw new IllegalArgumentException("easingFunction must not be null");
        }
        if (duration < 1)
        {
            throw new IllegalArgumentException("duration must be at least 1 ms");
        }
        this.easingFunction = easingFunction;
        super.setSlowInSlowOut(false);
    }


    /**
     * {@inheritDoc}
     *
     * <p>
     * Overridden to throw an UnsupportedOperationException.  Use the
     * easing function specified in this class' constructor instead.
     * </p>
     */
    public final void setSlowInSlowOut(final boolean slowInSlowOut)
    {
        throw new UnsupportedOperationException("slowInSlowOut is not supported by this activity, use easingFunction in ctr instead");
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The typical interval for interpolated activities is <code>0.0f</code> to
     * <code>1.0f</code>, inclusive.  An easing interpolation function allows for
     * overshooting this interval range in both directions, so the valid interval for
     * the specified value is <code>-1.0f</code> to <code>2.0f</code>, inclusive.
     * </p>
     */
    public void setRelativeTargetValue(final float value)
    {
        super.setRelativeTargetValue(value);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * If subclasses override this method, they must call <code>super.activityStarted()</code>.
     * </p>
     */
    protected void activityStarted()
    {
        super.activityStarted();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This method has been made final to prevent subclasses from overriding its behaviour.
     * </p>
     */
    protected final void activityStep(final long elapsed)
    {
        super.activityStep(elapsed);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * If subclasses override this method, they must call <code>super.activityFinished()</code>.
     * </p>
     */
    protected void activityFinished()
    {
        super.activityFinished();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Overridden to evaluate the easing function against the specified value.
     * </p>
     */
    protected final void setRelativeTargetValueAdjustingForMode(float value)
    {
        if (getMode() == DESTINATION_TO_SOURCE)
        {
            value = 1.0f - value;
        }
        else if (getMode() == SOURCE_TO_DESTINATION_TO_SOURCE)
        {
            value = (value <= 0.5f) ? 2.0f * value : 1.0f - ((value - 0.5f) * 2.0f);
        }
        float easedValue = easingFunction.evaluate(Double.valueOf(value)).floatValue();
        setRelativeTargetValue(easedValue);
    }
}