/*

    dsh-bitset  High performance bit set implementations.
    Copyright (c) 2011-2015 held jointly by the individual authors.

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
package org.dishevelled.bitset;

import static org.junit.Assert.assertNotNull;

import org.dishevelled.functor.UnaryProcedure;

import org.junit.Before;
import org.junit.Test;

/**
 * Abstract unit test for subclasses of AbstractBitSet.
 */
public abstract class AbstractBitSetTest {
    protected AbstractBitSet bitset;

    /**
     * Create and return a new instance of a subclass of AbstractBitSet to test.
     *
     * @return a new instance of a subclass of AbstractBitSet to test
     */
    protected abstract AbstractBitSet createBitSet();

    @Before
    public void setUp() {
        bitset = createBitSet();
    }

    @Test
    public void testCreateBitSet() {
        assertNotNull(bitset);
    }

    @Test(expected=NullPointerException.class)
    public void testIntersectsNullOther() {
        bitset.intersects(null);
    }

    /**
     * Count.
     */
    static final class Count implements UnaryProcedure<Long> {
        private long count = 0;

        @Override
        public void run(final Long index) {
            count++;
        }

        long count() {
            return count;
        }
    }
}
