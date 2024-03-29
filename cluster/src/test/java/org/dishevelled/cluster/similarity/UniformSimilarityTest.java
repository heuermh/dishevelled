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

import org.dishevelled.cluster.Similarity;
import org.dishevelled.cluster.AbstractSimilarityTest;

/**
 * Unit test for UniformSimilarity.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UniformSimilarityTest
    extends AbstractSimilarityTest
{

    /** {@inheritDoc} */
    protected <T> Similarity<T> createSimilarity()
    {
        return new UniformSimilarity<T>();
    }

    public void testConstructor()
    {
        Similarity similarity0 = new UniformSimilarity();
        Similarity similarity1 = new UniformSimilarity(0.0d);
        Similarity similarity2 = new UniformSimilarity(1.0d);
        Similarity similarity3 = new UniformSimilarity(-1.0d);
        Similarity similarity4 = new UniformSimilarity(100.0d);
        Similarity similarity5 = new UniformSimilarity(-100.0d);
        Similarity similarity6 = new UniformSimilarity(Double.MIN_VALUE);
        Similarity similarity7 = new UniformSimilarity(Double.MAX_VALUE);
        Similarity similarity8 = new UniformSimilarity(Double.POSITIVE_INFINITY);
        Similarity similarity9 = new UniformSimilarity(Double.NEGATIVE_INFINITY);
        Similarity similarity10 = new UniformSimilarity(Double.NaN);
        Similarity similarity11 = new UniformSimilarity(UniformSimilarity.DEFAULT_SIMILARITY);
    }

    public void testSimilarity()
    {
        Similarity<String> similarity0 = new UniformSimilarity<String>();
        for (int i = 0; i < 100; i++)
        {
            double value = similarity0.similarity("foo", "bar");
            assertEquals(UniformSimilarity.DEFAULT_SIMILARITY, value, 0.1d);
        }

        Similarity<String> similarity1 = new UniformSimilarity<String>(1.0d);
        for (int i = 0; i < 100; i++)
        {
            double value = similarity1.similarity("foo", "bar");
            assertEquals(1.0d, value, 0.1d);
        }
    }
}