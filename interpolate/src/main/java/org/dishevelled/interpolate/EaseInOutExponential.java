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
 * Ease-in-out exponential interpolation function.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EaseInOutExponential
    extends AbstractEasingFunction
{

    /** {@inheritDoc} */
    public String getName()
    {
        return "ease-in-out-exponential";
    }

    /** {@inheritDoc} */
    public String getDescription()
    {
        return "Ease-in-out exponential interpolation function";
    }

    /** {@inheritDoc} */
    public Double evaluate(final Double value)
    {
        double v = 2.0d * value;
        if (v < 1.0d)
        {
            return 0.5 * Math.pow(2.0d, 10.0d * (v - 1.0d));
        }
        v -= 1.0d;
        return 0.5d * (-1.0d * Math.pow(2.0d, -10.0d * v) + 2.0d);
    }
}