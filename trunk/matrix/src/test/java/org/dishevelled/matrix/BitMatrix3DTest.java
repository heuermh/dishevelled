/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2011 held jointly by the individual authors.

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
package org.dishevelled.matrix;

import junit.framework.TestCase;

/**
 * Unit test for BitMatrix3D.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BitMatrix3DTest
    extends TestCase
{

    public void testConstructor()
    {
        BitMatrix3D bm0 = new BitMatrix3D(0, 0, 0);
        BitMatrix3D bm1 = new BitMatrix3D(1, 0, 0);
        BitMatrix3D bm2 = new BitMatrix3D(0, 1, 0);
        BitMatrix3D bm3 = new BitMatrix3D(0, 0, 1);
        BitMatrix3D bm4 = new BitMatrix3D(1, 1, 1);
        BitMatrix3D bm5 = new BitMatrix3D(100, 100, 100);

        try
        {
            BitMatrix3D bm = new BitMatrix3D(-1, 0, 0);
            fail("ctr(-1,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            BitMatrix3D bm = new BitMatrix3D(0, -1, 0);
            fail("ctr(,-1,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            BitMatrix3D bm = new BitMatrix3D(0, 0, -1);
            fail("ctr(,,-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSize()
    {
        // TODO
    }

    public void testCardinality()
    {
        // TODO
    }

    public void testGetSetClearFlip()
    {
        // TODO
    }

    public void testIntersects()
    {
        // TODO
    }

    public void testIntersectsParams()
    {
        // TODO
    }

    public void testAnd()
    {
        // TODO
    }

    public void testAndParams()
    {
        // TODO
    }

    public void testAndNot()
    {
        // TODO
    }

    public void testAndNotParams()
    {
        // TODO
    }

    public void testOr()
    {
        // TODO
    }

    public void testOrParams()
    {
        // TODO
    }

    public void testXor()
    {
        // TODO
    }

    public void testXorParams()
    {
        // TODO
    }
}
