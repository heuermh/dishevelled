/*

    dsh-venn  Lightweight components for venn diagrams.
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
package org.dishevelled.venn;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of TernaryVennLayout.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractTernaryVennLayoutTest
    extends TestCase
{
    protected TernaryVennLayout ternaryVennLayout;
    protected abstract TernaryVennLayout createTernaryVennLayout();

    public void setUp()
    {
        ternaryVennLayout = createTernaryVennLayout();
    }

    public void testCreateTernaryVennLayout()
    {
        assertNotNull(createTernaryVennLayout());
    }

    public void testTernaryVennLayout()
    {
        assertEquals(3, ternaryVennLayout.size());
        assertNotNull(ternaryVennLayout.get(0));
        assertNotNull(ternaryVennLayout.get(1));
        assertNotNull(ternaryVennLayout.get(2));
        assertNotNull(ternaryVennLayout.firstShape());
        assertNotNull(ternaryVennLayout.secondShape());
        assertNotNull(ternaryVennLayout.thirdShape());
        assertEquals(ternaryVennLayout.firstShape(), ternaryVennLayout.get(0));
        assertEquals(ternaryVennLayout.secondShape(), ternaryVennLayout.get(1));
        assertEquals(ternaryVennLayout.thirdShape(), ternaryVennLayout.get(2));
        assertNotNull(ternaryVennLayout.luneCenter(0));
        assertNotNull(ternaryVennLayout.luneCenter(1));
        assertNotNull(ternaryVennLayout.luneCenter(2));
        assertNotNull(ternaryVennLayout.luneCenter(0, 1));
        assertNotNull(ternaryVennLayout.luneCenter(0, 2));
        assertNotNull(ternaryVennLayout.luneCenter(1, 2));
        assertNotNull(ternaryVennLayout.luneCenter(0, 1, 2));
        assertNotNull(ternaryVennLayout.firstOnlyLuneCenter());
        assertNotNull(ternaryVennLayout.secondOnlyLuneCenter());
        assertNotNull(ternaryVennLayout.thirdOnlyLuneCenter());
        assertNotNull(ternaryVennLayout.firstSecondLuneCenter());
        assertNotNull(ternaryVennLayout.firstThirdLuneCenter());
        assertNotNull(ternaryVennLayout.secondThirdLuneCenter());
        assertNotNull(ternaryVennLayout.intersectionLuneCenter());
        assertEquals(ternaryVennLayout.firstOnlyLuneCenter(), ternaryVennLayout.luneCenter(0));
        assertEquals(ternaryVennLayout.secondOnlyLuneCenter(), ternaryVennLayout.luneCenter(1));
        assertEquals(ternaryVennLayout.thirdOnlyLuneCenter(), ternaryVennLayout.luneCenter(2));
        assertEquals(ternaryVennLayout.firstSecondLuneCenter(), ternaryVennLayout.luneCenter(0, 1));
        assertEquals(ternaryVennLayout.firstThirdLuneCenter(), ternaryVennLayout.luneCenter(0, 2));
        assertEquals(ternaryVennLayout.secondThirdLuneCenter(), ternaryVennLayout.luneCenter(1, 2));
        assertEquals(ternaryVennLayout.intersectionLuneCenter(), ternaryVennLayout.luneCenter(0, 1, 2));
        assertNotNull(ternaryVennLayout.boundingRectangle());
    }
}
