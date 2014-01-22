/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2014 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.interpolate;

import org.dishevelled.color.scheme.Interpolation;

/**
 * Log base 2 interpolation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Log2Interpolation
    implements Interpolation
{
    /** Natural log of 2.0d. */
    private static final double LOG_2 = Math.log(2.0d);

    /**
     * Return the log base 2 of the specified value.
     *
     * @param value value
     * @return the log base 2 of the specified value
     */
    private static double log2(final double value)
    {
        return Math.log(value) / LOG_2;
    }

    /**
     * Return the symmetric log base 2 of the specified value.
     *
     * @param value value
     * @return the symmetric log base 2 of the specified value
     */
    private static double symmetricLog2(final double value)
    {
        return (value == 0.0d) ? 0.0d : ((value < 0.0d) ? -1 * log2(-value) : log2(value));
    }

    /** {@inheritDoc} */
    public double interpolate(final double value,
                              final double sourceMinimum,
                              final double sourceMaximum,
                              final double targetMinimum,
                              final double targetMaximum)
    {
        if (value <= sourceMinimum)
        {
            return targetMinimum;
        }
        if (value >= sourceMaximum)
        {
            return targetMaximum;
        }
        // (log(x) - log(d0)) / (log(d1) - log(d0)) * (r1 - r0) + r0
        return (symmetricLog2(value) - symmetricLog2(sourceMinimum))
            / (symmetricLog2(sourceMaximum) - symmetricLog2(sourceMinimum))
            * (targetMaximum - targetMinimum) + targetMinimum;
    }
}