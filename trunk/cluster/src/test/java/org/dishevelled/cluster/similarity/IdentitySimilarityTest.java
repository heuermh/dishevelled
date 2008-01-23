/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2008 held jointly by the individual authors.

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
 * Unit test for IdentitySimilarity.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdentitySimilarityTest
    extends AbstractSimilarityTest
{

    /** {@inheritDoc} */
    protected <T> Similarity<T> createSimilarity()
    {
        return new IdentitySimilarity<T>();
    }

    public void testConstructor()
    {
        Similarity similarity0 = new IdentitySimilarity();
        Similarity similarity1 = new IdentitySimilarity(0.0d, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity2 = new IdentitySimilarity(1.0d, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity3 = new IdentitySimilarity(-1.0d, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity4 = new IdentitySimilarity(100.0d, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity5 = new IdentitySimilarity(-100.0d, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity6 = new IdentitySimilarity(Double.MIN_VALUE, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity7 = new IdentitySimilarity(Double.MAX_VALUE, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity8 = new IdentitySimilarity(Double.POSITIVE_INFINITY, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity9 = new IdentitySimilarity(Double.NEGATIVE_INFINITY, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity10 = new IdentitySimilarity(Double.NaN, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
        Similarity similarity11 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, 0.0d);
        Similarity similarity12 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, 1.0d);
        Similarity similarity13 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, -1.0d);
        Similarity similarity14 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, 100.0d);
        Similarity similarity15 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, -100.0d);
        Similarity similarity16 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, Double.MIN_VALUE);
        Similarity similarity17 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, Double.MAX_VALUE);
        Similarity similarity18 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, Double.POSITIVE_INFINITY);
        Similarity similarity19 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, Double.NEGATIVE_INFINITY);
        Similarity similarity20 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, Double.NaN);
        Similarity similarity21 = new IdentitySimilarity(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY);
    }

    public void testSimilarityIdentical()
    {
        Similarity<Object> similarity = new IdentitySimilarity();
        Object foo = new Object();
        assertEquals(IdentitySimilarity.DEFAULT_IDENTICAL_SIMILARITY, similarity.similarity(foo, foo), 0.1d);
    }

    public void testSimilarityNotIdentical()
    {
        Similarity<Object> similarity = new IdentitySimilarity();
        Object foo = new Object();
        Object bar = new Object();
        assertEquals(IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY, similarity.similarity(foo, bar), 0.1d);
        assertEquals(IdentitySimilarity.DEFAULT_NOT_IDENTICAL_SIMILARITY, similarity.similarity(bar, foo), 0.1d);
    }
}