/*

    dsh-interpolate  Interpolation and easing functions.
    Copyright (c) 2009-2011 held jointly by the individual authors.

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
 * Ease-in-out cubic interpolation function.
 * <p><img src="../../../../images/ease-in-out-cubic.png" alt="ease-in-out cubic graph" /></p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EaseInOutCubic
    implements EasingFunction
{

    /** {@inheritDoc} */
    public String toString()
    {
        return "ease-in-out-cubic";
    }

    /** {@inheritDoc} */
    public Double evaluate(final Double value)
    {
        double v = 2.0d * value;
        if (v < 1.0d)
        {
            return 0.5d * Math.pow(v, 3.0d);
        }
        v -= 2.0d;
        return 0.5d * (Math.pow(v, 3.0d) + 2.0d);
    }
}