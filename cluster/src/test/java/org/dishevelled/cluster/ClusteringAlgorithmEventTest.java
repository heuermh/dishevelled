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
package org.dishevelled.cluster;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Unit test for ClusteringAlgorithmEvent.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ClusteringAlgorithmEventTest
    extends TestCase
{

    public void testConstructor()
    {
        TestClusteringAlgorithm<String> clusteringAlgorithm = new TestClusteringAlgorithm<String>();
        ClusteringAlgorithmEvent<String> event0 = new ClusteringAlgorithmEvent<String>(clusteringAlgorithm);

        try
        {
            ClusteringAlgorithmEvent<String> event = new ClusteringAlgorithmEvent<String>(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testClusteringAlgorithm()
    {
        TestClusteringAlgorithm<String> clusteringAlgorithm = new TestClusteringAlgorithm<String>();
        ClusteringAlgorithmEvent<String> event = new ClusteringAlgorithmEvent<String>(clusteringAlgorithm);
        assertEquals(clusteringAlgorithm, event.getSource());
        assertEquals(clusteringAlgorithm, event.getClusteringAlgorithm());
    }

    public void testSimilarityCalculatedValues()
    {
        TestClusteringAlgorithm<String> clusteringAlgorithm = new TestClusteringAlgorithm<String>();
        ClusteringAlgorithmEvent<String> event0 = new ClusteringAlgorithmEvent<String>(clusteringAlgorithm);
        assertEquals(null, event0.getValue1());
        assertEquals(null, event0.getValue2());
        assertTrue(Double.isNaN(event0.getSimilarity()));

        ClusteringAlgorithmEvent<String> event1 = new ClusteringAlgorithmEvent<String>(clusteringAlgorithm, null, null, Double.NaN);
        assertEquals(null, event1.getValue1());
        assertEquals(null, event1.getValue2());
        assertTrue(Double.isNaN(event1.getSimilarity()));

        String value1 = "value1";
        String value2 = "value2";
        double similarity = Double.valueOf(0.5d);
        ClusteringAlgorithmEvent<String> event2 = new ClusteringAlgorithmEvent<String>(clusteringAlgorithm, value1, value2, similarity);
        assertEquals(value1, event2.getValue1());
        assertEquals(value2, event2.getValue2());
        assertEquals(similarity, event2.getSimilarity());
    }

    /**
     * Test clustering algorithm.
     */
    private static class TestClusteringAlgorithm<E>
        extends AbstractClusteringAlgorithm<E>
    {

        /** {@inheritDoc} */
        public Set<Cluster<E>> cluster(final List<? extends E> values,
                                       final Similarity<E> similarity,
                                       final ExitStrategy<E> exitStrategy)
            throws ClusteringAlgorithmException
        {
            return Collections.<Cluster<E>>emptySet();
        }
    }
}