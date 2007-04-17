/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007 held jointly by the individual authors.

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
package org.dishevelled.cluster.exitstrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ExitStrategy;
import org.dishevelled.cluster.AbstractExitStrategyTest;

/**
 * Unit test for IterationLimitExitStrategy.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IterationLimitExitStrategyTest
    extends AbstractExitStrategyTest
{

    /** {@inheritDoc} */
    protected <T> ExitStrategy<T> createExitStrategy()
    {
        return new IterationLimitExitStrategy<T>(1);
    }

    public void testConstructor()
    {
        ExitStrategy exitStrategy0 = new IterationLimitExitStrategy(0);
        ExitStrategy exitStrategy1 = new IterationLimitExitStrategy(1);
        ExitStrategy exitStrategy2 = new IterationLimitExitStrategy(100);
        ExitStrategy exitStrategy3 = new IterationLimitExitStrategy(Integer.MAX_VALUE);

        try
        {
            ExitStrategy exitStrategy = new IterationLimitExitStrategy(-1);
            fail("ctr(-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            ExitStrategy exitStrategy = new IterationLimitExitStrategy(Integer.MIN_VALUE);
            fail("ctr(Integer.MIN_VALUE) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testEvaluateZeroIterationLimit()
    {
        List<String> values = Arrays.asList(new String[] { "foo", "bar", "baz", "qux" });
        Set<Cluster<String>> clusters = Collections.<Cluster<String>>emptySet();
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy<String>(0);
        assertNotNull("exitStrategy not null", exitStrategy);
        assertTrue(exitStrategy.evaluate(values, clusters));
        assertTrue(exitStrategy.evaluate(values, clusters));
        assertTrue(exitStrategy.evaluate(values, clusters));
    }

    public void testEvaluateOneIterationLimit()
    {
        List<String> values = Arrays.asList(new String[] { "foo", "bar", "baz", "qux" });
        Set<Cluster<String>> clusters = Collections.<Cluster<String>>emptySet();
        ExitStrategy exitStrategy = new IterationLimitExitStrategy(1);
        assertNotNull("exitStrategy not null", exitStrategy);
        assertFalse(exitStrategy.evaluate(values, clusters));
        assertTrue(exitStrategy.evaluate(values, clusters));
        assertTrue(exitStrategy.evaluate(values, clusters));
    }

    public void testEvaluateOneHundredIterationLimit()
    {
        List<String> values = Arrays.asList(new String[] { "foo", "bar", "baz", "qux" });
        Set<Cluster<String>> clusters = Collections.<Cluster<String>>emptySet();
        ExitStrategy exitStrategy = new IterationLimitExitStrategy(100);
        assertNotNull("exitStrategy not null", exitStrategy);
        for (int i = 0; i < 100; i++)
        {
            assertFalse(exitStrategy.evaluate(values, clusters));
        }
        assertTrue(exitStrategy.evaluate(values, clusters));
        assertTrue(exitStrategy.evaluate(values, clusters));
    }
}