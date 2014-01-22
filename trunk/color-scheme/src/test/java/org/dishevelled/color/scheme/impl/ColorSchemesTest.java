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
package org.dishevelled.color.scheme.impl;

import org.dishevelled.color.scheme.ColorScheme;
import org.dishevelled.color.scheme.impl.ColorSchemes;
import org.dishevelled.color.scheme.interpolate.Interpolations;


import junit.framework.TestCase;

/**
 * Unit test for ColorSchemes.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ColorSchemesTest extends TestCase
{

    public void testParseColorScheme()
    {
        ColorScheme continuousWhBl2 = ColorSchemes.parseColorScheme("continuous-wh-bl-2");
        assertNotNull(continuousWhBl2);
        ColorScheme discreteWhBl2 = ColorSchemes.parseColorScheme("discrete-wh-bl-2");
        assertNotNull(discreteWhBl2);

        try
        {
            ColorSchemes.parseColorScheme(null);
            fail("parseColorScheme(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ColorSchemes.parseColorScheme("not-a-color-scheme");
            fail("parseColorScheme(not-a-color-scheme) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ColorSchemes.parseColorScheme("invalidtype-wh-bl-2");
            fail("parseColorScheme(invalidtype-wh-bl-2) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testGetDiscreteColorScheme()
    {
        ColorScheme discreteWhBl2 = ColorSchemes.getDiscreteColorScheme("wh-bl", 2, Interpolations.LINEAR);
        assertNotNull(discreteWhBl2);

        ColorScheme discreteRdBlGr3 = ColorSchemes.getDiscreteColorScheme("rd-bl-gr", 3, Interpolations.LINEAR);
        assertNotNull(discreteRdBlGr3);

        assertNull(ColorSchemes.getDiscreteColorScheme("wh-bl", 99, Interpolations.LINEAR));
    }

    public void testGetContinuousColorScheme()
    {
        ColorScheme continuousWhBl2 = ColorSchemes.getContinuousColorScheme("wh-bl", 2, Interpolations.valueOf("linear"));
        assertNotNull(continuousWhBl2);

        ColorScheme continuousRdBlGr3 = ColorSchemes.getContinuousColorScheme("rd-bl-gr", 3, Interpolations.valueOf("linear"));
        assertNotNull(continuousRdBlGr3);

        assertNull(ColorSchemes.getContinuousColorScheme("wh-bl", 99, Interpolations.valueOf("linear")));
    }
}