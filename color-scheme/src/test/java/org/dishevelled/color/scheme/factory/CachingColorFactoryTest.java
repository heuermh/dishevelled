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
package org.dishevelled.color.scheme.factory;

import java.awt.Color;

import org.dishevelled.color.scheme.AbstractColorFactoryTest;
import org.dishevelled.color.scheme.ColorFactory;
import org.dishevelled.color.scheme.factory.CachingColorFactory;

/**
 * Unit test for CachingColorFactory.
 *
 * @author  Michael Heuer
 */
public final class CachingColorFactoryTest
    extends AbstractColorFactoryTest
{

    @Override
    protected ColorFactory createColorFactory()
    {
        return new CachingColorFactory();
    }

    public void testCreateColorCachesValues()
    {
        ColorFactory colorFactory = new CachingColorFactory();

        Color red = colorFactory.createColor(255, 0, 0, 1.0f);
        assertSame(red, colorFactory.createColor(255, 0, 0, 1.0f));
        assertNotSame(red, colorFactory.createColor(255, 0, 0, 0.0f));
        assertNotSame(red, colorFactory.createColor(0, 255, 0, 1.0f));
        assertNotSame(red, colorFactory.createColor(0, 0, 255, 1.0f));
    }
}
