/*

    dsh-color-scheme-view  Views for color schemes.
    Copyright (c) 2019 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.view;

import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import java.util.List;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import com.google.common.collect.ImmutableList;

import org.dishevelled.color.scheme.ColorFactory;
import org.dishevelled.color.scheme.factory.DefaultColorFactory;
import org.dishevelled.color.scheme.impl.DiscreteColorScheme;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for DiscreteColorSchemeList.
 *
 * @author  Michael Heuer
 */
public final class DiscreteColorSchemeListTest
{
    private EventList<DiscreteColorScheme> colorSchemes;

    @Before
    public void setUp()
    {
        List<Color> colors = ImmutableList.of(Color.WHITE, Color.GRAY, Color.BLACK);
        ColorFactory colorFactory = new DefaultColorFactory();
        DiscreteColorScheme colorScheme = new DiscreteColorScheme("color-scheme", colors, 0.0d, 1.0d, colorFactory);        
        colorSchemes = GlazedLists.eventList(ImmutableList.of(colorScheme));
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(new DiscreteColorSchemeList(colorSchemes));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorNullColorSchemes()
    {
        new DiscreteColorSchemeList(null);
    }
}
