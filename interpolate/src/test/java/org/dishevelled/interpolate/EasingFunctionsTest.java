/*

    dsh-interpolate  Interpolation and easing functions.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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

import junit.framework.TestCase;

/**
 * Unit test for EasingFunctions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EasingFunctionsTest
    extends TestCase
{

    public void testValues()
    {
        assertNotNull(EasingFunctions.VALUES);
        assertFalse(EasingFunctions.VALUES.isEmpty());
        for (EasingFunction function : EasingFunctions.VALUES)
        {
            assertNotNull(function);
        }
    }

    public void testValueOf()
    {
        assertEquals(null, EasingFunctions.valueOf(null));
        assertEquals(null, EasingFunctions.valueOf(""));
        assertEquals(null, EasingFunctions.valueOf("not valid"));
        assertEquals(EasingFunctions.LINEAR, EasingFunctions.valueOf("linear"));
        assertEquals(EasingFunctions.EASE_IN_QUADRATIC, EasingFunctions.valueOf("ease-in-quadratic"));
        assertEquals(EasingFunctions.EASE_OUT_QUADRATIC, EasingFunctions.valueOf("ease-out-quadratic"));
        assertEquals(EasingFunctions.EASE_IN_OUT_QUADRATIC, EasingFunctions.valueOf("ease-in-out-quadratic"));
    }
}