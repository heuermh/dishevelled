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
 * Unit test for EqualitySimilarity.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EqualitySimilarityTest
    extends AbstractSimilarityTest
{

    /** {@inheritDoc} */
    protected <T> Similarity<T> createSimilarity()
    {
        return new EqualitySimilarity<T>();
    }

    public void testConstructor()
    {
        Similarity similarity0 = new EqualitySimilarity();
        Similarity similarity1 = new EqualitySimilarity(0.0d, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity2 = new EqualitySimilarity(1.0d, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity3 = new EqualitySimilarity(-1.0d, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity4 = new EqualitySimilarity(100.0d, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity5 = new EqualitySimilarity(-100.0d, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity6 = new EqualitySimilarity(Double.MIN_VALUE, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity7 = new EqualitySimilarity(Double.MAX_VALUE, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity8 = new EqualitySimilarity(Double.POSITIVE_INFINITY, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity9 = new EqualitySimilarity(Double.NEGATIVE_INFINITY, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity10 = new EqualitySimilarity(Double.NaN, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
        Similarity similarity11 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, 0.0d);
        Similarity similarity12 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, 1.0d);
        Similarity similarity13 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, -1.0d);
        Similarity similarity14 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, 100.0d);
        Similarity similarity15 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, -100.0d);
        Similarity similarity16 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, Double.MIN_VALUE);
        Similarity similarity17 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, Double.MAX_VALUE);
        Similarity similarity18 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, Double.POSITIVE_INFINITY);
        Similarity similarity19 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, Double.NEGATIVE_INFINITY);
        Similarity similarity20 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, Double.NaN);
        Similarity similarity21 = new EqualitySimilarity(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY);
    }

    public void testSimilarityEqual()
    {
        Similarity<String> similarity = new EqualitySimilarity();
        String foo0 = "foo";
        String foo1 = "foo";
        assertEquals(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, similarity.similarity(foo0, foo1), 0.1d);
        assertEquals(EqualitySimilarity.DEFAULT_EQUAL_SIMILARITY, similarity.similarity(foo1, foo0), 0.1d);
    }

    public void testSimilarityNotEqual()
    {
        Similarity<String> similarity = new EqualitySimilarity();
        String foo = "foo";
        String bar = "bar";
        assertEquals(EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY, similarity.similarity(foo, bar), 0.1d);
        assertEquals(EqualitySimilarity.DEFAULT_NOT_EQUAL_SIMILARITY, similarity.similarity(bar, foo), 0.1d);
    }
}