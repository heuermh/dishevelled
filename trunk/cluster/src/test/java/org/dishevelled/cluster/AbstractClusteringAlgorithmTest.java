/*

    dsh-cluster  Framework for cluster algorithms.
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

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of ClusterAlgorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractClusterAlgorithmTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of ClusterAlgorithm to test.
     *
     * @param <T> cluster algorithm value type
     * @return a new instance of an implementation of ClusterAlgorithm to test
     */
    protected abstract <T> ClusterAlgorithm<T> createClusterAlgorithm();

    public void testClusterAlgorithm()
    {
        ClusterAlgorithm<String> clusterAlgorithm = createClusterAlgorithm();
        assertNotNull("clusterAlgorithm not null", clusterAlgorithm);
    }

    public void testCluster() throws ClusterAlgorithmException
    {
        ClusterAlgorithm<String> clusterAlgorithm = createClusterAlgorithm();
        assertNotNull("clusterAlgorithm not null", clusterAlgorithm);

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
                public boolean evaluate()
                {
                    return true;
                }
            };
        try
        {
            clusterAlgorithm.cluster(null, similarity, exitStrategy);
            fail("cluster(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusterAlgorithm.cluster(fullValues, null, exitStrategy);
            fail("cluster(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusterAlgorithm.cluster(fullValues, similarity, null);
            fail("cluster(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusterAlgorithm.cluster(emptyValues, similarity, exitStrategy);
            fail("cluster(emptyValues,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            clusterAlgorithm.cluster(nullValue, similarity, exitStrategy);
            fail("cluster(nullValue,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testClusterAlgorithmListeners()
    {
        ClusterAlgorithm<String> clusterAlgorithm = createClusterAlgorithm();
        assertNotNull("clusterAlgorithm not null", clusterAlgorithm);

        ClusterAlgorithmListener<String> listener = new ClusterAlgorithmAdapter<String>();
        clusterAlgorithm.addClusterAlgorithmListener(listener);
        clusterAlgorithm.removeClusterAlgorithmListener(listener);
    }
}