/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.cluster.similarity;

import java.util.Arrays;
import java.util.Collections;

import org.dishevelled.functor.BinaryFunction;

import org.dishevelled.cluster.AbstractSimilarityTest;
import org.dishevelled.cluster.Similarity;

/**
 * Unit test for CompositeSimilarity.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CompositeSimilarityTest
    extends AbstractSimilarityTest
{

    /** {@inheritDoc} */
    protected <T> Similarity<T> createSimilarity()
    {
        Similarity<T> child = new UniformSimilarity<T>();
        BinaryFunction<Double, Double, Double> aggr = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Double.valueOf(1.0d);
                }
            };
        return new CompositeSimilarity(Collections.singletonList(child), aggr);
    }

    public void testConstructor()
    {
        Similarity<String> child0 = new UniformSimilarity<String>();
        Similarity<String> child1 = new UniformSimilarity<String>();
        Similarity<String> child2 = new UniformSimilarity<String>();
        BinaryFunction<Double, Double, Double> aggr = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Double.valueOf(1.0d);
                }
            };

        CompositeSimilarity<String> compositeSimilarity0 = new CompositeSimilarity(Collections.singletonList(child0), aggr);
        CompositeSimilarity<String> compositeSimilarity1 = new CompositeSimilarity(Arrays.asList(new Similarity[] { child0, child1, child2 }), aggr);

        try
        {
            CompositeSimilarity<String> compositeSimilarity = new CompositeSimilarity(null, aggr);
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositeSimilarity<String> compositeSimilarity = new CompositeSimilarity(Collections.singletonList(child0), null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositeSimilarity<String> compositeSimilarity = new CompositeSimilarity(Collections.<Similarity<String>>emptyList(), aggr);
            fail("ctr(emptyList(),) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSimilarity()
    {
        Similarity<String> zero = new UniformSimilarity<String>(0.0d);
        Similarity<String> one = new UniformSimilarity<String>(1.0d);
        Similarity<String> two = new UniformSimilarity<String>(2.0d);
        BinaryFunction<Double, Double, Double> min = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Math.min(d1, d2);
                }
            };
        BinaryFunction<Double, Double, Double> max = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Math.max(d1, d2);
                }
            };

        CompositeSimilarity<String> compositeSimilarity1 = new CompositeSimilarity(Collections.singletonList(zero), min);
        assertEquals(0.0d, compositeSimilarity1.similarity("foo", "bar"), 0.1d);
        assertEquals(0.0d, compositeSimilarity1.similarity("bar", "baz"), 0.1d);

        CompositeSimilarity<String> compositeSimilarity2 = new CompositeSimilarity(Collections.singletonList(zero), max);
        assertEquals(0.0d, compositeSimilarity2.similarity("foo", "bar"), 0.1d);
        assertEquals(0.0d, compositeSimilarity2.similarity("bar", "baz"), 0.1d);

        CompositeSimilarity<String> compositeSimilarity4 = new CompositeSimilarity(Collections.singletonList(one), min);
        assertEquals(1.0d, compositeSimilarity4.similarity("foo", "bar"), 0.1d);
        assertEquals(1.0d, compositeSimilarity4.similarity("bar", "baz"), 0.1d);

        CompositeSimilarity<String> compositeSimilarity5 = new CompositeSimilarity(Collections.singletonList(one), max);
        assertEquals(1.0d, compositeSimilarity5.similarity("foo", "bar"), 0.1d);
        assertEquals(1.0d, compositeSimilarity5.similarity("bar", "baz"), 0.1d);

        CompositeSimilarity<String> compositeSimilarity7 = new CompositeSimilarity(Collections.singletonList(two), min);
        assertEquals(2.0d, compositeSimilarity7.similarity("foo", "bar"), 0.1d);
        assertEquals(2.0d, compositeSimilarity7.similarity("bar", "baz"), 0.1d);

        CompositeSimilarity<String> compositeSimilarity8 = new CompositeSimilarity(Collections.singletonList(two), max);
        assertEquals(2.0d, compositeSimilarity8.similarity("foo", "bar"), 0.1d);
        assertEquals(2.0d, compositeSimilarity8.similarity("bar", "baz"), 0.1d);

        CompositeSimilarity<String> compositeSimilarity10 = new CompositeSimilarity(Arrays.asList(new Similarity[] { zero, one, two }), min);
        assertEquals(0.0d, compositeSimilarity10.similarity("foo", "bar"), 0.1d);
        assertEquals(0.0d, compositeSimilarity10.similarity("bar", "baz"), 0.1d);

        CompositeSimilarity<String> compositeSimilarity11 = new CompositeSimilarity(Arrays.asList(new Similarity[] { zero, one, two }), max);
        assertEquals(2.0d, compositeSimilarity11.similarity("foo", "bar"), 0.1d);
        assertEquals(2.0d, compositeSimilarity11.similarity("bar", "baz"), 0.1d);
    }
}