/*

    dsh-interpolate  Interpolation and easing functions.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
 * Ease-in-out elastic interpolation function.
 * <p><img src="../../../../images/ease-in-out-elastic.png" alt="ease-in-out elastic graph" /></p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EaseInOutElastic
    implements EasingFunction
{
    /** <code>Math.PI * 2.0d</code>. */
    private static final double PI_TIMES_2 = Math.PI * 2.0d;


    /** {@inheritDoc} */
    public String toString()
    {
        return "ease-in-out-elastic";
    }

    /** {@inheritDoc} */
    public Double evaluate(final Double value)
    {
        double p = 0.3 * 1.5d;
        double s = p / 4.0d;
        double q = 2.0d * value;
        if (q < 1.0d)
        {
            q -= 1.0d;
            return -0.5d * (Math.pow(2.0d, 10.0d * q) * Math.sin((q - s) * (PI_TIMES_2) / p));
        }
        q -= 1.0d;
        return 0.5d * Math.pow(2.0, -10.0d * q) * Math.sin((q - s) * (PI_TIMES_2) / p) + 1.0d;
    }
}