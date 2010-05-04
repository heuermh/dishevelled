/*

    dsh-color-scheme  Color schemes.
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
package org.dishevelled.colorscheme.interpolate;

import org.dishevelled.colorscheme.Interpolation;

/**
 * Log base 10 interpolation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Log10Interpolation
    implements Interpolation
{
    /** Natural log of 10.0d. */
    private static final double LOG_10 = Math.log(10.0d);

    private static double log10(final double value)
    {
        return Math.log(value) / LOG_10;
    }

    private static double symmetricLog10(final double value)
    {
        return (value == 0.0d) ? 0.0d : ((value < 0.0d) ? -1 * log10(-value) : log10(value));
    }

    /** {@inheritDoc} */
    public final double interpolate(final double value,
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
        return (symmetricLog10(value) - symmetricLog10(sourceMinimum)) / (symmetricLog10(sourceMaximum) - symmetricLog10(sourceMinimum)) * (targetMaximum - targetMinimum) + targetMinimum;
    }
}