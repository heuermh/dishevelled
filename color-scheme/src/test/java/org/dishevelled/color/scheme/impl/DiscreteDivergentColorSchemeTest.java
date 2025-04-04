/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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

import static java.util.Collections.emptyList;

import java.awt.Color;

import java.util.List;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;

import org.dishevelled.color.scheme.ColorFactory;
import org.dishevelled.color.scheme.ColorScheme;
import org.dishevelled.color.scheme.factory.DefaultColorFactory;
import org.dishevelled.color.scheme.impl.DiscreteDivergentColorScheme;

/**
 * Unit test for DiscreteDivergentColorScheme.
 *
 * @author  Michael Heuer
 */
public final class DiscreteDivergentColorSchemeTest
    extends TestCase
{

    public void testConstructor()
    {
        List<Color> emptyColors = emptyList();
        List<Color> singletonColor = ImmutableList.of(Color.WHITE);
        List<Color> colors = ImmutableList.of(Color.WHITE, Color.GRAY, Color.BLACK);
        ColorFactory colorFactory = new DefaultColorFactory();
        new DiscreteDivergentColorScheme("color-scheme", colors, 0.0d, 0.5d, 1.0d, colorFactory);
        new DiscreteDivergentColorScheme("color-scheme", colors, -1.0d, 0.0d, 1.0d, colorFactory);
        new DiscreteDivergentColorScheme("color-scheme", colors, 1.0d, 50.0d, 99.0d, colorFactory);
        new DiscreteDivergentColorScheme("color-scheme", colors, -99.0d, -50.0d, -1.0d, colorFactory);
        new DiscreteDivergentColorScheme("color-scheme", colors, -1 * Double.MAX_VALUE, 0.0d, Double.MAX_VALUE, colorFactory);
        //new DiscreteDivergentColorScheme("color-scheme", colors, Double.NaN, 0.5d, 1.0d, colorFactory);
        //new DiscreteDivergentColorScheme("color-scheme", colors, 0.0d, 0.5d, Double.NaN, colorFactory);
        new DiscreteDivergentColorScheme(null, colors, 0.0d, 0.5d, 1.0d, colorFactory);

        try
        {
            new DiscreteDivergentColorScheme("color-scheme", null, 0.0d, 0.5d, 1.0d, colorFactory);
            fail("ctr(,null,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new DiscreteDivergentColorScheme("color-scheme", emptyColors, 0.0d, 0.5d, 1.0d, colorFactory);
            fail("ctr(,emptyColors,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new DiscreteDivergentColorScheme("color-scheme", singletonColor, 0.0d, 0.5d, 1.0d, colorFactory);
            fail("ctr(,singletonColor,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new DiscreteDivergentColorScheme("color-scheme", colors, 0.0d, 0.5d, 1.0d, null);
            fail("ctr(,,,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testTwoColorScheme()
    {
        ColorScheme colorScheme = new DiscreteDivergentColorScheme("name", ImmutableList.of(Color.BLACK, Color.WHITE), 0.0d, 0.5d, 1.0d, new DefaultColorFactory());
        assertNotNull(colorScheme);
        //assertEquals("name", colorScheme.getName());
        // out of bounds
        assertEquals(Color.BLACK, colorScheme.getColor(-1.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(2.0d));
        // within bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.25));
        assertEquals(Color.WHITE, colorScheme.getColor(0.75));
        // at bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.50));
    }

    public void testEvenColorScheme()
    {
        ColorScheme colorScheme = new DiscreteDivergentColorScheme("name", ImmutableList.of(Color.BLACK, Color.RED, Color.BLUE, Color.WHITE), 0.0d, 0.5d, 1.0d, new DefaultColorFactory());
        assertNotNull(colorScheme);
        //assertEquals("name", colorScheme.getName());
        // out of bounds
        assertEquals(Color.BLACK, colorScheme.getColor(-1.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(2.0d));
        // within bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.125d));
        assertEquals(Color.RED, colorScheme.getColor(0.375d));
        assertEquals(Color.BLUE, colorScheme.getColor(0.625d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.875d));
        // at bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.25d));
        assertEquals(Color.RED, colorScheme.getColor(0.50d));
        assertEquals(Color.BLUE, colorScheme.getColor(0.75d));
        assertEquals(Color.WHITE, colorScheme.getColor(1.0d));
    }

    public void testOddColorScheme()
    {
        ColorScheme colorScheme = new DiscreteDivergentColorScheme("name", ImmutableList.of(Color.BLACK, Color.RED, Color.WHITE), 0.0d, 0.5d, 1.0d, new DefaultColorFactory());
        assertNotNull(colorScheme);
        //assertEquals("name", colorScheme.getName());
        // out of bounds
        assertEquals(Color.BLACK, colorScheme.getColor(-1.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(2.0d));
        // within bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.25d));
        assertEquals(Color.RED, colorScheme.getColor(0.50d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.75d));
        // at bounds
        assertEquals(Color.BLACK, colorScheme.getColor(0.33d));
        assertEquals(Color.RED, colorScheme.getColor(0.66d));
        assertEquals(Color.WHITE, colorScheme.getColor(1.0d));
    }

    public void testEvenDiscreteDivergentColorScheme()
    {
        List<Color> colors = ImmutableList.of(Color.WHITE, Color.BLACK);
        ColorFactory colorFactory = new DefaultColorFactory();
        DiscreteDivergentColorScheme colorScheme = new DiscreteDivergentColorScheme("color-scheme", colors, 0.0d, 0.5d, 1.0d, colorFactory);
        assertNotNull(colorScheme);
        assertEquals("color-scheme", colorScheme.getName());
        assertEquals(2, colorScheme.getSize());
        assertTrue(colorScheme.getColors().contains(Color.BLACK));
        assertTrue(colorScheme.getColors().contains(Color.WHITE));
        assertEquals(0.0d, colorScheme.getMinimumValue(), 0.1d);
        assertEquals(0.5d, colorScheme.getZeroValue(), 0.1d);
        assertEquals(1.0d, colorScheme.getMaximumValue(), 0.1d);
        assertEquals(colorFactory, colorScheme.getColorFactory());
        assertEquals(Color.WHITE, colorScheme.getColor(-99.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.25d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.50d));
        assertEquals(Color.BLACK, colorScheme.getColor(0.75d));
        assertEquals(Color.BLACK, colorScheme.getColor(1.0d));
        assertEquals(Color.BLACK, colorScheme.getColor(99.0d));
    }

    public void testOddDiscreteDivergentColorScheme()
    {
        List<Color> colors = ImmutableList.of(Color.WHITE, Color.RED, Color.BLACK);
        ColorFactory colorFactory = new DefaultColorFactory();
        DiscreteDivergentColorScheme colorScheme = new DiscreteDivergentColorScheme("color-scheme", colors, 0.0d, 0.5d, 1.0d, colorFactory);
        assertNotNull(colorScheme);
        assertEquals("color-scheme", colorScheme.getName());
        assertEquals(3, colorScheme.getSize());
        assertTrue(colorScheme.getColors().contains(Color.BLACK));
        assertTrue(colorScheme.getColors().contains(Color.RED));
        assertTrue(colorScheme.getColors().contains(Color.WHITE));
        assertEquals(0.0d, colorScheme.getMinimumValue(), 0.1d);
        assertEquals(1.0d, colorScheme.getMaximumValue(), 0.1d);
        assertEquals(colorFactory, colorScheme.getColorFactory());
        assertEquals(Color.WHITE, colorScheme.getColor(-99.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.0d));
        assertEquals(Color.WHITE, colorScheme.getColor(0.33d));
        assertEquals(Color.RED, colorScheme.getColor(0.34d));
        assertEquals(Color.RED, colorScheme.getColor(0.50d));
        assertEquals(Color.RED, colorScheme.getColor(0.66d));
        assertEquals(Color.BLACK, colorScheme.getColor(0.67d));
        assertEquals(Color.BLACK, colorScheme.getColor(0.75d));
        assertEquals(Color.BLACK, colorScheme.getColor(1.0d));
        assertEquals(Color.BLACK, colorScheme.getColor(99.0d));
    }
}
