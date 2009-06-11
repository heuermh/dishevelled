/*

    dsh-interpolate  Interpolation and easing functions.
    Copyright (c) 2009 held jointly by the individual authors.

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
package org.dishevelled.interpolate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Easing interpolation functions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EasingFunctions
{
    /** Linear easing interpolation function. */
    public static final Linear LINEAR = new Linear();

    /** Ease-in quadratic interpolation function. */
    public static final EaseInQuadratic EASE_IN_QUADRATIC = new EaseInQuadratic();

    /** Ease-out quadratic interpolation function. */
    public static final EaseOutQuadratic EASE_OUT_QUADRATIC = new EaseOutQuadratic();

    /** Ease-in-out quadratic interpolation function. */
    public static final EaseInOutQuadratic EASE_IN_OUT_QUADRATIC = new EaseInOutQuadratic();

    /** Ease-in cubic interpolation function. */
    public static final EaseInCubic EASE_IN_CUBIC = new EaseInCubic();

    /** Ease-out cubic interpolation function. */
    public static final EaseOutCubic EASE_OUT_CUBIC = new EaseOutCubic();

    /** Ease-in-out cubic interpolation function. */
    public static final EaseInOutCubic EASE_IN_OUT_CUBIC = new EaseInOutCubic();

    /** Ease-in quartic interpolation function. */
    public static final EaseInQuartic EASE_IN_QUARTIC = new EaseInQuartic();

    /** Ease-out quartic interpolation function. */
    public static final EaseOutQuartic EASE_OUT_QUARTIC = new EaseOutQuartic();

    /** Ease-in-out quartic interpolation function. */
    public static final EaseInOutQuartic EASE_IN_OUT_QUARTIC = new EaseInOutQuartic();

    /** Ease-in quintic interpolation function. */
    public static final EaseInQuintic EASE_IN_QUINTIC = new EaseInQuintic();

    /** Ease-out quintic interpolation function. */
    public static final EaseOutQuintic EASE_OUT_QUINTIC = new EaseOutQuintic();

    /** Ease-in-out quintic interpolation function. */
    public static final EaseInOutQuintic EASE_IN_OUT_QUINTIC = new EaseInOutQuintic();

    /** Ease-in sine interpolation function. */
    public static final EaseInSine EASE_IN_SINE = new EaseInSine();

    /** Ease-out sine interpolation function. */
    public static final EaseOutSine EASE_OUT_SINE = new EaseOutSine();

    /** Ease-in-out sine interpolation function. */
    public static final EaseInOutSine EASE_IN_OUT_SINE = new EaseInOutSine();

    /** Ease-in exponential interpolation function. */
    public static final EaseInExponential EASE_IN_EXPONENTIAL = new EaseInExponential();

    /** Ease-out exponential interpolation function. */
    public static final EaseOutExponential EASE_OUT_EXPONENTIAL = new EaseOutExponential();

    /** Ease-in-out exponential interpolation function. */
    public static final EaseInOutExponential EASE_IN_OUT_EXPONENTIAL = new EaseInOutExponential();

    /** Ease-in circular interpolation function. */
    public static final EaseInCircular EASE_IN_CIRCULAR = new EaseInCircular();

    /** Ease-out circular interpolation function. */
    public static final EaseOutCircular EASE_OUT_CIRCULAR = new EaseOutCircular();

    /** Ease-in-out circular interpolation function. */
    public static final EaseInOutCircular EASE_IN_OUT_CIRCULAR = new EaseInOutCircular();

    /** Ease-in elastic interpolation function. */
    public static final EaseInElastic EASE_IN_ELASTIC = new EaseInElastic();

    /** Ease-out elastic interpolation function. */
    public static final EaseOutElastic EASE_OUT_ELASTIC = new EaseOutElastic();

    /** Ease-in-out elastic interpolation function. */
    public static final EaseInOutElastic EASE_IN_OUT_ELASTIC = new EaseInOutElastic();

    /** Ease-in back interpolation function. */
    public static final EaseInBack EASE_IN_BACK = new EaseInBack();

    /** Ease-out back interpolation function. */
    public static final EaseOutBack EASE_OUT_BACK = new EaseOutBack();

    /** Ease-in-out back interpolation function. */
    public static final EaseInOutBack EASE_IN_OUT_BACK = new EaseInOutBack();

    /** Ease-in bounce interpolation function. */
    public static final EaseInBounce EASE_IN_BOUNCE = new EaseInBounce();

    /** Ease-out bounce interpolation function. */
    public static final EaseOutBounce EASE_OUT_BOUNCE = new EaseOutBounce();

    /** Ease-in-out bounce interpolation function. */
    public static final EaseInOutBounce EASE_IN_OUT_BOUNCE = new EaseInOutBounce();

    /** Array of easing interpolation functions. */
    private static final AbstractEasingFunction[] values = new AbstractEasingFunction[] { LINEAR, EASE_IN_QUADRATIC, EASE_OUT_QUADRATIC,
            EASE_IN_OUT_QUADRATIC, EASE_IN_CUBIC, EASE_OUT_CUBIC, EASE_IN_OUT_CUBIC, EASE_IN_QUARTIC, EASE_OUT_QUARTIC, EASE_IN_OUT_QUARTIC,
            EASE_IN_QUINTIC, EASE_OUT_QUINTIC, EASE_IN_OUT_QUINTIC, EASE_IN_SINE, EASE_OUT_SINE, EASE_IN_OUT_SINE, EASE_IN_EXPONENTIAL,
            EASE_OUT_EXPONENTIAL, EASE_IN_OUT_EXPONENTIAL, EASE_IN_CIRCULAR, EASE_OUT_CIRCULAR, EASE_IN_OUT_CIRCULAR, EASE_IN_ELASTIC,
            EASE_OUT_ELASTIC, EASE_IN_OUT_ELASTIC, EASE_IN_BACK, EASE_OUT_BACK, EASE_IN_OUT_BACK, EASE_IN_BOUNCE, EASE_OUT_BOUNCE,
            EASE_IN_OUT_BOUNCE };

    /** List of easing interpolation functions. */
    public static final List<AbstractEasingFunction> VALUES = Arrays.asList(values);

    /** Map of easing interpolation functions keyed by name. */
    private static final Map<String, AbstractEasingFunction> KEYED_BY_NAME = new HashMap<String, AbstractEasingFunction>(32);

    static
    {
        for (AbstractEasingFunction function : VALUES)
        {
            KEYED_BY_NAME.put(function.getName(), function);
        }
    }


    /**
     * Private no-arg constructor.
     */
    private EasingFunctions()
    {
        // empty
    }


    /**
     * Return the easing interpolation function for the specified name, if any.
     *
     * @param name easing interpolation function name
     * @return the easing interpolation function for the specified name, or <code>null</code>
     *    if no such easing interpolation function exists
     */
    public static AbstractEasingFunction valueOf(final String name)
    {
        return KEYED_BY_NAME.get(name);
    }
}