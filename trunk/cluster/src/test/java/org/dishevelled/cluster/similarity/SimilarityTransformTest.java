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

import org.dishevelled.cluster.AbstractSimilarityTest;
import org.dishevelled.cluster.Similarity;

import org.dishevelled.functor.UnaryFunction;

/**
 * Unit test for SimilarityTransform.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SimilarityTransformTest
    extends AbstractSimilarityTest
{

    /** {@inheritDoc} */
    protected <T> Similarity<T> createSimilarity()
    {
        Similarity<T> similarity = new UniformSimilarity<T>();
        UnaryFunction<Double, Double> transform = new UnaryFunction<Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return 1.0d;
                }
            };
        return new SimilarityTransform<T>(similarity, transform);
    }

    public void testConstructor()
    {
        Similarity similarity = new UniformSimilarity();
        UnaryFunction<Double, Double> transform = new UnaryFunction<Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return 1.0d;
                }
            };

        Similarity similarityTransform0 = new SimilarityTransform(similarity, transform);

        try
        {
            Similarity similarityTransform = new SimilarityTransform(null, transform);
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Similarity similarityTransform = new SimilarityTransform(similarity, null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Similarity similarityTransform = new SimilarityTransform(null, null);
            fail("ctr(null,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSimilarity()
    {
        Similarity<String> positiveSimilarity = new UniformSimilarity<String>(1.0d);
        Similarity<String> negativeSimilarity = new UniformSimilarity<String>(-1.0d);
        UnaryFunction<Double, Double> inverseTransform = new UnaryFunction<Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double value)
                {
                    return (-1.0d * value);
                }
            };

        Similarity<String> similarityTransform0 = new SimilarityTransform<String>(positiveSimilarity, inverseTransform);
        assertEquals(-1.0d, similarityTransform0.similarity("foo", "bar"));

        Similarity<String> similarityTransform1 = new SimilarityTransform<String>(negativeSimilarity, inverseTransform);
        assertEquals(1.0d, similarityTransform1.similarity("foo", "bar"));
    }
}