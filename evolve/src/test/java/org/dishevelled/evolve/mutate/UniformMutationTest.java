/*

    dsh-evolve  Simple framework for evolutionary algorithms.
    Copyright (c) 2005-2006 held jointly by the individual authors.

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
package org.dishevelled.evolve.mutate;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;

import org.dishevelled.evolve.Mutation;
import org.dishevelled.evolve.AbstractMutationTest;

/**
 * Unit test for UniformMutation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UniformMutationTest
    extends AbstractMutationTest
{

    /** @see AbstractMutationTest */
    protected <T> Mutation<T> createMutation()
    {
        IndividualWiseMutation<T> mutation = new NullIndividualWiseMutation<T>();
        return new UniformMutation<T>(mutation);
    }

    /** @see AbstractMutationTest */
    protected <T> Map<Set<T>, Set<T>> getExpectedValues(final T t)
    {
        Set<T> recombined = Collections.singleton(t);
        Set<T> mutated = Collections.singleton(t);

        Map<Set<T>, Set<T>> expectedValues = new HashMap<Set<T>, Set<T>>();
        expectedValues.put(recombined, mutated);

        return expectedValues;
    }

    public void testConstructor()
    {
        try
        {
            Mutation<Integer> mutation = new UniformMutation<Integer>(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}