/*

    dsh-color-scheme  Color schemes.
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
package org.dishevelled.colorscheme;

/**
 * Interpolation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Interpolation
{

    /**
     * Interpolate the specified value from the specified source range to the specified target range.
     *
     * @param value value to interpolate
     * @param sourceMinimum source range minimum value
     * @param sourceMaximum source range maximum value
     * @param targetMinimum target range minimum value
     * @param targetMaximum target range maximum value
     * @return the specified value interpolated from the specified source range to the specified target range
     */
    double interpolate(double value,
                       double sourceMinimum,
                       double sourceMaximum,
                       double targetMinimum,
                       double targetMaximum);
}