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

/**
 * Abstract bounce easing interpolation function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractBounceEasingFunction
    implements EasingFunction
{

    /**
     * Return the interpolated ease in value.
     *
     * @param value value to interpolate
     * @return the interpolated ease in value
     */
    protected final Double easeIn(final Double value)
    {
        return 1.0d - easeOut(1.0d - value);
    }

    /**
     * Return the interpolated ease out value.
     *
     * @param value value to interpolate
     * @return the interpolated ease out value
     */
    protected final Double easeOut(final Double value)
    {
        if (value < (1.0d / 2.75d))
        {
            return value * value * 7.5625d;
        }
        else if (value < (2.0d / 2.75d))
        {
            double v = value - (1.5d / 2.75d);
            return v * v * 7.5625d + 0.75d;
        }
        else if (value < (2.5d / 2.75d))
        {
            double v = value - (2.25d / 2.75d);
            return v * v * 7.5625d + 0.9375d;
        }
        double v = value - (2.625d / 2.75d);
        return v * v * 7.5625d + 0.984375d;
    }
}