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
package org.dishevelled.colorscheme;

import java.awt.Color;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of ColorFactory.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractColorFactoryTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of ColorFactory to test.
     *
     * @return a new instance of an implementation of ColorFactory to test
     */
    protected abstract ColorFactory createColorFactory();


    public void testCreateColorFactory()
    {
        assertNotNull(createColorFactory());
    }

    public void testCreateColor()
    {
        ColorFactory colorFactory = createColorFactory();
        assertNotNull(colorFactory.createColor(0, 0, 0, 0.0f));
    }

    public void testCreateColorRGBA()
    {
        ColorFactory colorFactory = createColorFactory();

        Color red = colorFactory.createColor(255, 0, 0, 1.0f);
        assertEquals(255, red.getRed());
        assertEquals(0, red.getGreen());
        assertEquals(0, red.getBlue());
        assertEquals(255, red.getAlpha());

        Color green = colorFactory.createColor(0, 255, 0, 1.0f);
        assertEquals(0, green.getRed());
        assertEquals(255, green.getGreen());
        assertEquals(0, green.getBlue());
        assertEquals(255, green.getAlpha());

        Color blue = colorFactory.createColor(0, 0, 255, 1.0f);
        assertEquals(0, blue.getRed());
        assertEquals(0, blue.getGreen());
        assertEquals(255, blue.getBlue());
        assertEquals(255, blue.getAlpha());

        Color transparent = colorFactory.createColor(0, 0, 0, 0.0f);
        assertEquals(0, transparent.getRed());
        assertEquals(0, transparent.getGreen());
        assertEquals(0, transparent.getBlue());
        assertEquals(0, transparent.getAlpha());
    }
}