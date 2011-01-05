/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2011 held jointly by the individual authors.

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
package org.dishevelled.evolve.mutate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.dishevelled.evolve.Mutation;
import org.dishevelled.evolve.AbstractMutationTest;

/**
 * Unit test for ProportionalMutation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ProportionalMutationTest
    extends AbstractMutationTest
{

    /** {@inheritDoc} */
    protected <T> Mutation<T> createMutation()
    {
        return new ProportionalMutation<T>();
    }

    public void testEmptyProportionalMutation()
    {
        Mutation<String> mutation = createMutation();
        Collection<String> recombined = new ArrayList<String>();
        recombined.add("foo");
        recombined.add("bar");
        recombined.add("baz");

        Collection<String> mutated = mutation.mutate(recombined);
        assertNotNull(mutated);
        assertEquals(3, mutated.size());
        assertTrue(mutated.contains("foo"));
        assertTrue(mutated.contains("bar"));
        assertTrue(mutated.contains("baz"));
    }

    public void testAdd()
    {
        ProportionalMutation<Integer> pm = new ProportionalMutation<Integer>();
        IndividualWiseMutation<Integer> mutation = new NullIndividualWiseMutation<Integer>();

        pm.add(mutation, 1.0d);

        Collection<Integer> recombined = Collections.singleton(Integer.valueOf(0));
        Collection<Integer> mutated = pm.mutate(recombined);

        try
        {
            pm.add(null, 1.0d);
            fail("add(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
