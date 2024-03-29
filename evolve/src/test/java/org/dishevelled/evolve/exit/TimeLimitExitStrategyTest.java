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
package org.dishevelled.evolve.exit;

import java.util.Collection;
import java.util.Collections;

import org.dishevelled.evolve.AbstractExitStrategyTest;
import org.dishevelled.evolve.ExitStrategy;

import org.dishevelled.weighted.HashWeightedMap;
import org.dishevelled.weighted.WeightedMap;

/**
 * Unit test for TimeLimitExitStrategy.
 *
 * @author  Michael Heuer
 */
public final class TimeLimitExitStrategyTest
    extends AbstractExitStrategyTest
{
    /** Time limit. */
    private static final int TIME_LIMIT = 10;


    /** {@inheritDoc} */
    protected <T> ExitStrategy<T> createExitStrategy()
    {
        return new TimeLimitExitStrategy<T>(TIME_LIMIT);
    }

    public void testConstructor()
    {
        try
        {
            ExitStrategy<Integer> exit = new TimeLimitExitStrategy<Integer>(0);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ExitStrategy<Integer> exit = new TimeLimitExitStrategy<Integer>(-1);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testTimeLimitExitStrategy()
    {
        ExitStrategy<String> exitStrategy = createExitStrategy();
        Collection<String> population = Collections.singleton("foo");
        WeightedMap<String> scores = new HashWeightedMap<String>();
        scores.put("foo", 0.0d);

        assertFalse(exitStrategy.evaluate(population, scores, TIME_LIMIT - 1));
        assertTrue(exitStrategy.evaluate(population, scores, TIME_LIMIT));
        assertTrue(exitStrategy.evaluate(population, scores, TIME_LIMIT + 1));
    }
}
