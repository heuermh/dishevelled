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
package org.dishevelled.color.scheme;

import java.awt.Color;

/**
 * Color scheme.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ColorScheme
{

    /**
     * Return the minimum potential value for this color scheme.  Defaults to <code>0.0d</code>.
     *
     * @return the minimum potential value for this color scheme
     */
    double getMinimumValue();

    /**
     * Set the minimum potential value for this color scheme to <code>minimumValue</code> (optional operation).
     *
     * @param minimumValue minimum potential value
     * @throws UnsupportedOperationException if this operation is not supported by this color scheme
     */
    void setMinimumValue(double minimumValue);

    /**
     * Return the maximum potential value for this color scheme.  Defaults to <code>1.0d</code>.
     *
     * @return the maximum potential value for this color scheme
     */
    double getMaximumValue();

    /**
     * Set the maximum potential value for this color scheme to <code>maximumValue</code> (optional operation).
     *
     * @param maximumValue maximum potential value
     * @throws UnsupportedOperationException if this operation is not supported by this color scheme
     */
    void setMaximumValue(double maximumValue);

    /**
     * Return the zero value for this color scheme.
     *
     * @return the zero value for this color scheme
     */
    double getZeroValue();

    /**
     * Set the zero value for this color scheme to <code>zeroValue</code> (optional operation).
     *
     * @param zeroValue zero value
     * @throws UnsupportedOperationException if this operation is not supported by this color scheme
     */
    void setZeroValue(double zeroValue);

    /**
     * Return the color factory for this color scheme.
     *
     * @return the color factory for this color scheme
     */
    ColorFactory getColorFactory();

    /**
     * Set the color factory for this color scheme to <code>colorFactory</code> (optional operation).
     *
     * @param colorFactory color factory, must not be null
     * @throws UnsupportedOperationException if this operation is not supported by this color scheme
     */
    void setColorFactory(ColorFactory colorFactory);

    /**
     * Return the interpolation method for this color scheme.  The interpolation method will not be null.
     *
     * @return the interpolation method for this color scheme
     */
    Interpolation getInterpolation();

    /**
     * Set the interpolation method for this color scheme to <code>interpolation</code> (optional operation).
     *
     * @param interpolation interpolation method, must not be null
     * @throws UnsupportedOperationException if this operation is not supported by this color scheme
     */
    void setInterpolation(Interpolation interpolation);

    /**
     * Return a color appropriate for the specified value.
     *
     * @param value value
     * @return a color appropriate for the specified value
     */
    Color getColor(double value);
}