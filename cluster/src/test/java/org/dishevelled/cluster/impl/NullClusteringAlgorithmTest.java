/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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
package org.dishevelled.cluster.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.dishevelled.cluster.AbstractClusteringAlgorithmTest;
import org.dishevelled.cluster.ClusteringAlgorithm;
import org.dishevelled.cluster.ClusteringAlgorithmException;
import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ExitStrategy;
import org.dishevelled.cluster.Similarity;

import org.dishevelled.cluster.exit.IterationLimitExitStrategy;

import org.dishevelled.cluster.similarity.UniformSimilarity;

/**
 * Unit test for NullClusteringAlgorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NullClusteringAlgorithmTest
    extends AbstractClusteringAlgorithmTest
{

    /** {@inheritDoc} */
    protected <T> ClusteringAlgorithm<T> createClusteringAlgorithm()
    {
        return new NullClusteringAlgorithm<T>();
    }

    public void testSingleValue()
        throws ClusteringAlgorithmException
    {
        List<String> values = Arrays.asList(new String[] { "foo" });
        ClusteringAlgorithm<String> algo = new NullClusteringAlgorithm<String>();
        Similarity<String> similarity = new UniformSimilarity(1.0d);
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy(99);

        Set<Cluster<String>> clusters = algo.cluster(values, similarity, exitStrategy);
        assertNotNull(clusters);
        assertEquals(1, clusters.size());
        assertTrue(clusters.iterator().next().members().contains("foo"));
    }

    public void testTwoSimilarValues()
        throws ClusteringAlgorithmException
    {
        List<String> values = Arrays.asList(new String[] { "foo", "bar" });
        ClusteringAlgorithm<String> algo = new NullClusteringAlgorithm<String>();
        Similarity<String> similarity = new UniformSimilarity(1.0d);
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy(99);

        Set<Cluster<String>> clusters = algo.cluster(values, similarity, exitStrategy);
        assertNotNull(clusters);
        assertEquals(1, clusters.size());
        Cluster<String> cluster = clusters.iterator().next();
        assertTrue(cluster.members().contains("foo"));
        assertTrue(cluster.members().contains("bar"));
    }

    public void testTwoDissimilarValues()
        throws ClusteringAlgorithmException
    {
        List<String> values = Arrays.asList(new String[] { "foo", "bar" });
        ClusteringAlgorithm<String> algo = new NullClusteringAlgorithm<String>();
        Similarity<String> similarity = new UniformSimilarity(0.0d);
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy(99);

        Set<Cluster<String>> clusters = algo.cluster(values, similarity, exitStrategy);
        assertNotNull(clusters);
        assertEquals(1, clusters.size());
        Cluster<String> cluster = clusters.iterator().next();
        assertTrue(cluster.members().contains("foo"));
        assertTrue(cluster.members().contains("bar"));
    }

    public void testSeveralSimilarValues()
        throws ClusteringAlgorithmException
    {
        List<String> values = Arrays.asList(new String[] { "foo", "bar", "baz", "qux" });
        ClusteringAlgorithm<String> algo = new NullClusteringAlgorithm<String>();
        Similarity<String> similarity = new UniformSimilarity(1.0d);
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy(99);

        Set<Cluster<String>> clusters = algo.cluster(values, similarity, exitStrategy);
        assertNotNull(clusters);
        assertEquals(1, clusters.size());
        Cluster<String> cluster = clusters.iterator().next();
        assertTrue(cluster.members().contains("foo"));
        assertTrue(cluster.members().contains("bar"));
        assertTrue(cluster.members().contains("baz"));
        assertTrue(cluster.members().contains("qux"));
    }

    public void testSeveralDissimilarValues()
        throws ClusteringAlgorithmException
    {
        List<String> values = Arrays.asList(new String[] { "foo", "bar", "baz", "qux" });
        ClusteringAlgorithm<String> algo = new NullClusteringAlgorithm<String>();
        Similarity<String> similarity = new UniformSimilarity(0.0d);
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy(99);

        Set<Cluster<String>> clusters = algo.cluster(values, similarity, exitStrategy);
        assertNotNull(clusters);
        assertEquals(1, clusters.size());
        Cluster<String> cluster = clusters.iterator().next();
        assertTrue(cluster.members().contains("foo"));
        assertTrue(cluster.members().contains("bar"));
        assertTrue(cluster.members().contains("baz"));
        assertTrue(cluster.members().contains("qux"));
    }
}