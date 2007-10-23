/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007 held jointly by the individual authors.

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

import java.util.Random;

import org.dishevelled.cluster.Similarity;
import org.dishevelled.cluster.AbstractSimilarityTest;

/**
 * Unit test for RandomSimilarity.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RandomSimilarityTest
    extends AbstractSimilarityTest
{

    /** {@inheritDoc} */
    protected <T> Similarity<T> createSimilarity()
    {
        return new RandomSimilarity<T>();
    }

    public void testConstructor()
    {
        Similarity similarity0 = new RandomSimilarity();
        Similarity similarity1 = new RandomSimilarity(new Random());

        try
        {
            Similarity similarity = new RandomSimilarity(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSimilarity()
    {
        Similarity<String> similarity = new RandomSimilarity<String>();
        for (int i = 0; i < 100; i++)
        {
            double value = similarity.similarity("foo", "bar");
            assertTrue(value >= 0.0d);
            assertTrue(value < 1.0d);
        }
    }
}