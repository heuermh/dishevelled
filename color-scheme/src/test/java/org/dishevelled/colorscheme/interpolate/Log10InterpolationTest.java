/*

    dsh-color-scheme  Color schemes.
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
package org.dishevelled.colorscheme.interpolate;

import junit.framework.TestCase;

/**
 * Unit test for Log10Interpolation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class Log10InterpolationTest
    extends TestCase
{
    private static final double TOLERANCE = 0.001d;

    public void testConstructor()
    {
        assertNotNull(new Log10Interpolation());
    }

    public void testInterpolate()
    {
        Log10Interpolation interpolation = new Log10Interpolation();
        assertEquals(0.0d, interpolation.interpolate(0.0d, 0.0d, 1.0d, 0.0d, 10.0d), TOLERANCE);
        assertEquals(10.0d, interpolation.interpolate(1.0d, 0.0d, 1.0d, 0.0d, 10.0d), TOLERANCE);
    }
}
