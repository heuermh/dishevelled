/*

    dsh-color-scheme  Color schemes.
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
package org.dishevelled.color.scheme.impl;

import java.awt.Color;

import java.util.List;

import junit.framework.TestCase;

import static org.dishevelled.collect.Lists.*;

import org.dishevelled.color.scheme.ColorFactory;
import org.dishevelled.color.scheme.ColorScheme;
import org.dishevelled.color.scheme.factory.DefaultColorFactory;
import org.dishevelled.color.scheme.impl.ContinuousColorScheme;
import org.dishevelled.color.scheme.interpolate.Interpolations;



/**
 * Unit test for ContinuousColorScheme.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ContinuousColorSchemeTest
    extends TestCase
{

    public void testConstructor()
    {
        List<Color> emptyColors = emptyList();
        List<Color> singletonColor = asList(Color.WHITE);
        List<Color> colors = asList(Color.WHITE, Color.GRAY, Color.BLACK);
        ColorFactory colorFactory = new DefaultColorFactory();
        new ContinuousColorScheme("color-scheme", colors, 0.0d, 1.0d, colorFactory, Interpolations.LINEAR);
        new ContinuousColorScheme("color-scheme", colors, -1.0d, 1.0d, colorFactory, Interpolations.LINEAR);
        new ContinuousColorScheme("color-scheme", colors, 1.0d, 99.0d, colorFactory, Interpolations.LINEAR);
        new ContinuousColorScheme("color-scheme", colors, -99.0d, -1.0d, colorFactory, Interpolations.LINEAR);
        new ContinuousColorScheme("color-scheme", colors, -1 * Double.MAX_VALUE, Double.MAX_VALUE, colorFactory, Interpolations.LINEAR);
        //new ContinuousColorScheme("color-scheme", colors, Double.NaN, 1.0d, colorFactory, Interpolations.LINEAR);
        //new ContinuousColorScheme("color-scheme", colors, 0.0d, Double.NaN, colorFactory, Interpolations.LINEAR);
        new ContinuousColorScheme(null, colors, 0.0d, 1.0d, colorFactory, Interpolations.LINEAR);

        try
        {
            new ContinuousColorScheme("color-scheme", null, 0.0d, 1.0d, colorFactory, Interpolations.LINEAR);
            fail("ctr(,null,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new ContinuousColorScheme("color-scheme", emptyColors, 0.0d, 1.0d, colorFactory, Interpolations.LINEAR);
            fail("ctr(,emptyColors,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new ContinuousColorScheme("color-scheme", singletonColor, 0.0d, 1.0d, colorFactory, Interpolations.LINEAR);
            fail("ctr(,singletonColor,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new ContinuousColorScheme("color-scheme", colors, 0.0d, 1.0d, null, Interpolations.LINEAR);
            fail("ctr(,,,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new ContinuousColorScheme("color-scheme", colors, 0.0d, 1.0d, colorFactory, null);
            fail("ctr(,,,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testTwoColorScheme()
    {
        ColorScheme colorScheme = new ContinuousColorScheme("name", asList(Color.BLACK, Color.WHITE), 0.0d, 1.0d, new DefaultColorFactory(), Interpolations.LINEAR);
        assertNotNull(colorScheme);
        //assertEquals("name", colorScheme.getName());
        assertEquals(Color.BLACK, colorScheme.getColor(-1.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(2.0d));
        // within bounds
        assertEquals(new Color(63, 63, 63), colorScheme.getColor(0.25d));
        assertEquals(new Color(191, 191, 191), colorScheme.getColor(0.75d));
        // at bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(1.0d));
    }

    public void testEvenColorScheme()
    {
        ColorScheme colorScheme = new ContinuousColorScheme("name", asList(Color.BLACK, Color.RED, Color.BLUE, Color.WHITE), 0.0d, 1.0d, new DefaultColorFactory(), Interpolations.LINEAR);
        assertNotNull(colorScheme);
        //assertEquals("name", colorScheme.getName());
        // out of bounds
        assertEquals(Color.BLACK, colorScheme.getColor(-1.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(2.0d));
        // within bounds
        assertEquals(new Color(95, 0, 0), colorScheme.getColor(0.125d));
        assertEquals(new Color(223, 0, 31), colorScheme.getColor(0.375d));
        assertEquals(new Color(31, 0, 223), colorScheme.getColor(0.625d));
        assertEquals(new Color(159, 159, 255), colorScheme.getColor(0.875d));
        // at bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.0d));
        assertEquals(Color.RED, colorScheme.getColor((1.0d / 3.0d)));
        assertEquals(Color.BLUE, colorScheme.getColor((2.0d / 3.0d)));
        assertEquals(Color.WHITE, colorScheme.getColor(1.0d));
    }

    public void testOddColorScheme()
    {
        ColorScheme colorScheme = new ContinuousColorScheme("name", asList(Color.BLACK, Color.RED, Color.WHITE), 0.0d, 1.0d, new DefaultColorFactory(), Interpolations.LINEAR);
        assertNotNull(colorScheme);
        //assertEquals("name", colorScheme.getName());
        // out of bounds
        assertEquals(Color.BLACK, colorScheme.getColor(-1.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(2.0d));
        // within bounds
        assertEquals(new Color(127, 0, 0), colorScheme.getColor(0.25d));
        assertEquals(new Color(255, 0, 0), colorScheme.getColor(0.50d));
        assertEquals(new Color(255, 127, 127), colorScheme.getColor(0.75d));
        // at bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.0d));
        assertEquals(Color.RED, colorScheme.getColor(0.50d));
        assertEquals(Color.WHITE, colorScheme.getColor(1.0d));
    }

    public void testEvenContinuousColorScheme()
    {
        List<Color> colors = asList(Color.WHITE, Color.BLACK);
        ColorFactory colorFactory = new DefaultColorFactory();
        ContinuousColorScheme colorScheme = new ContinuousColorScheme("color-scheme", colors, 0.0d, 1.0d, colorFactory, Interpolations.LINEAR);
        assertNotNull(colorScheme);
        assertEquals("color-scheme", colorScheme.getName());
        assertEquals(0.0d, colorScheme.getMinimumValue(), 0.1d);
        assertEquals(1.0d, colorScheme.getMaximumValue(), 0.1d);
        assertEquals(colorFactory, colorScheme.getColorFactory());
        assertEquals(Interpolations.LINEAR, colorScheme.getInterpolation());
        assertEquals(Color.WHITE, colorScheme.getColor(-99.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.0d));
        //assertEquals(Color.WHITE, colorScheme.getColor(0.25d));
        //assertEquals(Color.WHITE, colorScheme.getColor(0.50d));
        //assertEquals(Color.BLACK, colorScheme.getColor(0.75d));
        assertEquals(Color.BLACK, colorScheme.getColor(1.0d));
        assertEquals(Color.BLACK, colorScheme.getColor(99.0d));
    }

    public void testOddContinuousColorScheme()
    {
        List<Color> colors = asList(Color.WHITE, Color.RED, Color.BLACK);
        ColorFactory colorFactory = new DefaultColorFactory();
        ContinuousColorScheme colorScheme = new ContinuousColorScheme("color-scheme", colors, 0.0d, 1.0d, colorFactory, Interpolations.LINEAR);
        assertNotNull(colorScheme);
        assertEquals("color-scheme", colorScheme.getName());
        assertEquals(0.0d, colorScheme.getMinimumValue(), 0.1d);
        assertEquals(1.0d, colorScheme.getMaximumValue(), 0.1d);
        assertEquals(colorFactory, colorScheme.getColorFactory());
        assertEquals(Interpolations.LINEAR, colorScheme.getInterpolation());
        assertEquals(Color.WHITE, colorScheme.getColor(-99.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.0d));
        //assertEquals(Color.WHITE, colorScheme.getColor(0.33d));
        //assertEquals(Color.RED, colorScheme.getColor(0.34d));
        //assertEquals(Color.RED, colorScheme.getColor(0.50d));
        //assertEquals(Color.RED, colorScheme.getColor(0.66d));
        //assertEquals(Color.BLACK, colorScheme.getColor(0.67d));
        //assertEquals(Color.BLACK, colorScheme.getColor(0.75d));
        assertEquals(Color.BLACK, colorScheme.getColor(1.0d));
        assertEquals(Color.BLACK, colorScheme.getColor(99.0d));
    }
}