/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2011 held jointly by the individual authors.

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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of ClusteringAlgorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractClusteringAlgorithmTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of ClusteringAlgorithm to test.
     *
     * @param <T> clustering algorithm value type
     * @return a new instance of an implementation of ClusteringAlgorithm to test
     */
    protected abstract <T> ClusteringAlgorithm<T> createClusteringAlgorithm();

    public void testClusteringAlgorithm()
    {
        ClusteringAlgorithm<String> clusteringAlgorithm = createClusteringAlgorithm();
        assertNotNull("clusteringAlgorithm not null", clusteringAlgorithm);
    }

    public void testClustering() throws ClusteringAlgorithmException
    {
        ClusteringAlgorithm<String> clusteringAlgorithm = createClusteringAlgorithm();
        assertNotNull("clusteringAlgorithm not null", clusteringAlgorithm);

        List<String> emptyValues = Arrays.asList(new String[0]);
        List<String> nullValue = Arrays.asList(new String[] { "foo", "bar", "baz", null });
        List<String> fullValues = Arrays.asList(new String[] { "foo", "bar", "baz", "qux" });

        Similarity<String> similarity = new Similarity<String>()
            {
                /** {@inheritDoc} */
                public double similarity(final String value1, final String value2)
                {
                    return 1.0d;
                }
            };
        ExitStrategy<String> exitStrategy = new ExitStrategy<String>()
            {
                /** {@inheritDoc} */
                public boolean evaluate(final List<? extends String> values,
                                        final Set<Cluster<String>> clusters)
                {
                    return true;
                }
            };
        try
        {
            clusteringAlgorithm.cluster(null, similarity, exitStrategy);
            fail("cluster(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusteringAlgorithm.cluster(fullValues, null, exitStrategy);
            fail("cluster(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusteringAlgorithm.cluster(fullValues, similarity, null);
            fail("cluster(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusteringAlgorithm.cluster(emptyValues, similarity, exitStrategy);
            fail("cluster(emptyValues,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusteringAlgorithm.cluster(nullValue, similarity, exitStrategy);
            fail("cluster(nullValue,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testClusteringAlgorithmListeners()
    {
        ClusteringAlgorithm<String> clusteringAlgorithm = createClusteringAlgorithm();
        assertNotNull("clusteringAlgorithm not null", clusteringAlgorithm);

        ClusteringAlgorithmListener<String> listener = new ClusteringAlgorithmAdapter<String>();
        clusteringAlgorithm.addClusteringAlgorithmListener(listener);
        clusteringAlgorithm.removeClusteringAlgorithmListener(listener);
    }
}