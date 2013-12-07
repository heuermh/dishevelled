/*

    dsh-venn  Lightweight components for venn diagrams.
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
package org.dishevelled.venn;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of QuaternaryVennLayout.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractQuaternaryVennLayoutTest
    extends TestCase
{
    protected QuaternaryVennLayout quaternaryVennLayout;
    protected abstract QuaternaryVennLayout createQuaternaryVennLayout();

    public void setUp()
    {
        quaternaryVennLayout = createQuaternaryVennLayout();
    }

    public void testCreateQuaternaryVennLayout()
    {
        assertNotNull(createQuaternaryVennLayout());
    }

    public void testQuaternaryVennLayout()
    {
        assertEquals(4, quaternaryVennLayout.size());
        assertNotNull(quaternaryVennLayout.get(0));
        assertNotNull(quaternaryVennLayout.get(1));
        assertNotNull(quaternaryVennLayout.get(2));
        assertNotNull(quaternaryVennLayout.get(3));
        assertNotNull(quaternaryVennLayout.firstShape());
        assertNotNull(quaternaryVennLayout.secondShape());
        assertNotNull(quaternaryVennLayout.thirdShape());
        assertNotNull(quaternaryVennLayout.fourthShape());
        assertEquals(quaternaryVennLayout.firstShape(), quaternaryVennLayout.get(0));
        assertEquals(quaternaryVennLayout.secondShape(), quaternaryVennLayout.get(1));
        assertEquals(quaternaryVennLayout.thirdShape(), quaternaryVennLayout.get(2));
        assertEquals(quaternaryVennLayout.fourthShape(), quaternaryVennLayout.get(3));
        assertNotNull(quaternaryVennLayout.luneCenter(0));
        assertNotNull(quaternaryVennLayout.luneCenter(1));
        assertNotNull(quaternaryVennLayout.luneCenter(2));
        assertNotNull(quaternaryVennLayout.luneCenter(3));
        assertNotNull(quaternaryVennLayout.luneCenter(0, 1));
        assertNotNull(quaternaryVennLayout.luneCenter(0, 2));
        assertNotNull(quaternaryVennLayout.luneCenter(0, 3));
        assertNotNull(quaternaryVennLayout.luneCenter(1, 2));
        assertNotNull(quaternaryVennLayout.luneCenter(1, 3));
        assertNotNull(quaternaryVennLayout.luneCenter(2, 3));
        assertNotNull(quaternaryVennLayout.luneCenter(0, 1, 2));
        assertNotNull(quaternaryVennLayout.luneCenter(0, 1, 3));
        assertNotNull(quaternaryVennLayout.luneCenter(0, 2, 3));
        assertNotNull(quaternaryVennLayout.luneCenter(1, 2, 3));
        assertNotNull(quaternaryVennLayout.luneCenter(0, 1, 2, 3));
        assertNotNull(quaternaryVennLayout.firstOnlyLuneCenter());
        assertNotNull(quaternaryVennLayout.secondOnlyLuneCenter());
        assertNotNull(quaternaryVennLayout.thirdOnlyLuneCenter());
        assertNotNull(quaternaryVennLayout.fourthOnlyLuneCenter());
        assertNotNull(quaternaryVennLayout.intersectionLuneCenter());
        assertEquals(quaternaryVennLayout.firstOnlyLuneCenter(), quaternaryVennLayout.luneCenter(0));
        assertEquals(quaternaryVennLayout.secondOnlyLuneCenter(), quaternaryVennLayout.luneCenter(1));
        assertEquals(quaternaryVennLayout.thirdOnlyLuneCenter(), quaternaryVennLayout.luneCenter(2));
        assertEquals(quaternaryVennLayout.fourthOnlyLuneCenter(), quaternaryVennLayout.luneCenter(3));
        assertEquals(quaternaryVennLayout.firstSecondLuneCenter(), quaternaryVennLayout.luneCenter(0, 1));
        assertEquals(quaternaryVennLayout.firstThirdLuneCenter(), quaternaryVennLayout.luneCenter(0, 2));
        assertEquals(quaternaryVennLayout.firstFourthLuneCenter(), quaternaryVennLayout.luneCenter(0, 3));
        assertEquals(quaternaryVennLayout.secondThirdLuneCenter(), quaternaryVennLayout.luneCenter(1, 2));
        assertEquals(quaternaryVennLayout.secondFourthLuneCenter(), quaternaryVennLayout.luneCenter(1, 3));
        assertEquals(quaternaryVennLayout.thirdFourthLuneCenter(), quaternaryVennLayout.luneCenter(2, 3));
        assertEquals(quaternaryVennLayout.firstSecondThirdLuneCenter(), quaternaryVennLayout.luneCenter(0, 1, 2));
        assertEquals(quaternaryVennLayout.firstSecondFourthLuneCenter(), quaternaryVennLayout.luneCenter(0, 1, 3));
        assertEquals(quaternaryVennLayout.firstThirdFourthLuneCenter(), quaternaryVennLayout.luneCenter(0, 2, 3));
        assertEquals(quaternaryVennLayout.secondThirdFourthLuneCenter(), quaternaryVennLayout.luneCenter(1, 2, 3));
        assertEquals(quaternaryVennLayout.intersectionLuneCenter(), quaternaryVennLayout.luneCenter(0, 1, 2, 3));
        assertNotNull(quaternaryVennLayout.boundingRectangle());
    }
}
