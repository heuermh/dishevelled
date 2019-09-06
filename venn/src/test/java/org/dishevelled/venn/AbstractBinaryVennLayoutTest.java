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
 * Abstract unit test for implementations of BinaryVennLayout.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractBinaryVennLayoutTest
    extends TestCase
{
    protected BinaryVennLayout binaryVennLayout;
    protected abstract BinaryVennLayout createBinaryVennLayout();

    public void setUp()
    {
        binaryVennLayout = createBinaryVennLayout();
    }

    public void testCreateBinaryVennLayout()
    {
        assertNotNull(createBinaryVennLayout());
    }

    public void testBinaryVennLayout()
    {
        assertEquals(2, binaryVennLayout.size());
        assertNotNull(binaryVennLayout.get(0));
        assertNotNull(binaryVennLayout.get(1));
        assertNotNull(binaryVennLayout.firstShape());
        assertNotNull(binaryVennLayout.secondShape());
        assertEquals(binaryVennLayout.firstShape(), binaryVennLayout.get(0));
        assertEquals(binaryVennLayout.secondShape(), binaryVennLayout.get(1));
        assertNotNull(binaryVennLayout.luneCenter(0));
        assertNotNull(binaryVennLayout.luneCenter(1));
        assertNotNull(binaryVennLayout.luneCenter(0, 1));
        assertNotNull(binaryVennLayout.firstOnlyLuneCenter());
        assertNotNull(binaryVennLayout.secondOnlyLuneCenter());
        assertNotNull(binaryVennLayout.intersectionLuneCenter());
        assertEquals(binaryVennLayout.firstOnlyLuneCenter(), binaryVennLayout.luneCenter(0));
        assertEquals(binaryVennLayout.secondOnlyLuneCenter(), binaryVennLayout.luneCenter(0));
        assertEquals(binaryVennLayout.intersectionLuneCenter(), binaryVennLayout.luneCenter(0, 1));
        assertNotNull(binaryVennLayout.boundingRectangle());
    }
}
