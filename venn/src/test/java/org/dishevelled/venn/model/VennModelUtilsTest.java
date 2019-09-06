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
package org.dishevelled.venn.model;

import java.util.Collections;
import java.util.Set;

import junit.framework.TestCase;

import org.dishevelled.bitset.ImmutableBitSet;

/**
 * Unit test for VennModelUtils.
 *
 * @author  Michael Heuer
 */
public final class VennModelUtilsTest
    extends TestCase
{

    public void testToImmutableBitSet()
    {
        ImmutableBitSet bitSet = VennModelUtils.toImmutableBitSet(0, 2, 4);
        assertTrue(bitSet.getQuick(0));
        assertFalse(bitSet.getQuick(1));
        assertTrue(bitSet.getQuick(2));
        assertFalse(bitSet.getQuick(3));
        assertTrue(bitSet.getQuick(4));
    }

    public void testToImmutableBitSetIndexOnly()
    {
        ImmutableBitSet bitSet = VennModelUtils.toImmutableBitSet(0);
        assertTrue(bitSet.getQuick(0));
    }

    public void testToImmutableBitSetNullAdditional()
    {
        ImmutableBitSet bitSet = VennModelUtils.toImmutableBitSet(0, null);
        assertTrue(bitSet.getQuick(0));
    }

    public void testToImmutableBitSetNullIndices()
    {
        try
        {
            VennModelUtils.toImmutableBitSet((Set<Integer>) null);
            fail("toImmutableBitSet(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testToImmutableBitSetEmptyIndices()
    {
        try
        {
            Set<Integer> empty = Collections.emptySet();
            VennModelUtils.toImmutableBitSet(empty);
            fail("toImmutableBitSet(empty) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
