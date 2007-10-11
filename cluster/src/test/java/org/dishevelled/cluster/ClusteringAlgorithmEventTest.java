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

    /**
     * Test clustering algorithm.
     */
    private class TestClusteringAlgorithm<E>
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