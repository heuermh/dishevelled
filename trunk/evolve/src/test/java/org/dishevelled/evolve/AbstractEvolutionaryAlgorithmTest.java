/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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
package org.dishevelled.evolve;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of EvolutionaryAlgorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractEvolutionaryAlgorithmTest
    extends TestCase
{

    /**
     * Create and return a new instance of EvolutionaryAlgorithm to test.
     *
     * @param <I> individual type
     * @return a new instance of EvolutionaryAlgorithm to test
     */
    protected abstract <I> EvolutionaryAlgorithm<I> createEvolutionaryAlgorithm();

    public void testCreateEvolutionaryAlgorithm()
    {
        EvolutionaryAlgorithm<String> ea = createEvolutionaryAlgorithm();
        assertNotNull(ea);
    }

    public void testEvolutionaryAlgorithmListeners()
    {
        EvolutionaryAlgorithm<String> ea = createEvolutionaryAlgorithm();
        assertNotNull(ea);

        EvolutionaryAlgorithmListener<String> listener0 = new EvolutionaryAlgorithmAdapter<String>();
        EvolutionaryAlgorithmListener<String> listener1 = new EvolutionaryAlgorithmAdapter<String>();

        assertNotNull(listener0);
        assertNotNull(listener1);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(0, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(0, ea.getEvolutionaryAlgorithmListenerCount());

        ea.addEvolutionaryAlgorithmListener(listener0);
        ea.addEvolutionaryAlgorithmListener(listener1);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(2, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(2, ea.getEvolutionaryAlgorithmListenerCount());
        assertEquals(listener1, ea.getEvolutionaryAlgorithmListeners()[0]);
        assertEquals(listener0, ea.getEvolutionaryAlgorithmListeners()[1]);

        ea.removeEvolutionaryAlgorithmListener(listener1);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(1, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(1, ea.getEvolutionaryAlgorithmListenerCount());
        assertEquals(listener0, ea.getEvolutionaryAlgorithmListeners()[0]);

        ea.removeEvolutionaryAlgorithmListener(listener0);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(0, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(0, ea.getEvolutionaryAlgorithmListenerCount());

        ea.removeEvolutionaryAlgorithmListener(listener0);

        assertNotNull(ea.getEvolutionaryAlgorithmListeners());
        assertEquals(0, ea.getEvolutionaryAlgorithmListeners().length);
        assertEquals(0, ea.getEvolutionaryAlgorithmListenerCount());
    }
}