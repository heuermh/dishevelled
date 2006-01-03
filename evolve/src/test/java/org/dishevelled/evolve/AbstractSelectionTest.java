/*

    dsh-evolve  Simple framework for evolutionary algorithms.
    Copyright (c) 2005 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.evolve;

import junit.framework.TestCase;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.HashWeightedMap;

/**
 * Abstract unit test for implementations of Selection.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractSelectionTest
    extends TestCase
{

    /**
     * Create and return a new instance of Selection to test.
     *
     * @return a new instance of Selection to test
     */
    protected abstract <T> Selection<T> createSelection();

    public void testSelection()
    {
        Selection<Integer> selection = createSelection();

        WeightedMap<Integer> parents = new HashWeightedMap<Integer>();
        WeightedMap<Integer> children = new HashWeightedMap<Integer>();

        Integer individual = Integer.valueOf(0);
        parents.put(individual, Double.valueOf(1.0d));
        children.put(individual, Double.valueOf(1.0d));

        try
        {
            selection.select(null, parents);
            fail("select(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            selection.select(parents, null);
            fail("select(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    /**
     * Test individual.
     */
    protected class TestIndividual
        implements Individual
    {
        /** Value. */
        private int value;

        /**
         * Create a new test individual.
         *
         * @param value value
         */
        public TestIndividual(final int value)
        {
            this.value = value;
        }


        /** @see Individual */
        public boolean equals(Object o)
        {
            return super.equals(o);
        }

        /** @see Individual */
        public int hashCode()
        {
            return super.hashCode();
        }

        /** @see Individual */
        public Individual shallowCopy()
        {
            TestIndividual copy = new TestIndividual(value);
            return copy;
        }
    }
}