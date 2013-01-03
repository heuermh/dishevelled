/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2013 held jointly by the individual authors.

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
package org.dishevelled.evolve.recombine;

import java.util.ArrayList;
import java.util.Collection;

import org.dishevelled.evolve.Recombination;
import org.dishevelled.evolve.AbstractRecombinationTest;

/**
 * Unit test for SexualRecombination.
 *
 * @author  Michael Heuer
 */
public final class SexualRecombinationTest
    extends AbstractRecombinationTest
{

    /** {@inheritDoc} */
    protected <T> Recombination<T> createRecombination()
    {
        return new TestSexualRecombination<T>();
    }

    public void testOneParent()
    {
        Collection<Integer> population = new ArrayList<Integer>();
        population.add(Integer.valueOf(0));

        Recombination<Integer> recombination = new TestSexualRecombination<Integer>();
        Collection<Integer> recombined = recombination.recombine(population);

        assertNotNull(recombined);
        assertEquals(1, recombined.size());
        assertTrue(recombined.contains(Integer.valueOf(0)));
    }

    public void testTwoParents()
    {
        Collection<Integer> population = new ArrayList<Integer>();
        population.add(Integer.valueOf(0));
        population.add(Integer.valueOf(1));

        Recombination<Integer> recombination = new TestSexualRecombination<Integer>();
        Collection<Integer> recombined = recombination.recombine(population);

        assertNotNull(recombined);
        assertEquals(2, recombined.size());
        assertTrue(recombined.contains(Integer.valueOf(0)));
        assertTrue(recombined.contains(Integer.valueOf(1)));
    }

    public void testOddParentListSize()
    {
        Collection<Integer> population = new ArrayList<Integer>();
        population.add(Integer.valueOf(0));
        population.add(Integer.valueOf(1));
        population.add(Integer.valueOf(2));

        Recombination<Integer> recombination = new TestSexualRecombination<Integer>();
        Collection<Integer> recombined = recombination.recombine(population);

        assertNotNull(recombined);
        assertEquals(3, recombined.size());
        assertTrue(recombined.contains(Integer.valueOf(0)));
        assertTrue(recombined.contains(Integer.valueOf(1)));
        assertTrue(recombined.contains(Integer.valueOf(2)));
    }

    public void testEvenParentListSize()
    {
        Collection<Integer> population = new ArrayList<Integer>();
        population.add(Integer.valueOf(0));
        population.add(Integer.valueOf(1));
        population.add(Integer.valueOf(2));
        population.add(Integer.valueOf(3));

        Recombination<Integer> recombination = new TestSexualRecombination<Integer>();
        Collection<Integer> recombined = recombination.recombine(population);

        assertNotNull(recombined);
        assertEquals(4, recombined.size());
        assertTrue(recombined.contains(Integer.valueOf(0)));
        assertTrue(recombined.contains(Integer.valueOf(1)));
        assertTrue(recombined.contains(Integer.valueOf(2)));
        assertTrue(recombined.contains(Integer.valueOf(3)));
    }

    /**
     * Text sexual recombination.
     */
    private class TestSexualRecombination<T>
        extends SexualRecombination<T>
    {
        /** {@inheritDoc} */
        protected T recombine(final T individual0, final T individual1)
        {
            return individual0;
        }
    }
}
