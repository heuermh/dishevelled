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
package org.dishevelled.cluster.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dishevelled.cluster.AbstractClusteringAlgorithm;
import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ExitStrategy;
import org.dishevelled.cluster.Similarity;

import org.dishevelled.functor.UnaryProcedure;

import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

/**
 * Connected components clustering algorithm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ConnectedComponents<E>
    extends AbstractClusteringAlgorithm<E>
{
    /** Cutoff similarity score. */
    private final double cutoff;

    /** Default cutoff similarity score, <code>0.5d</code>. */
    public static final double DEFAULT_CUTOFF = 0.5d;


    /**
     * Create a new connected components clustering algorithm with the
     * specified cutoff similarity score.  Values with a similarity score greater than or
     * equal to this cutoff will be clustered together.
     *
     * @param cutoff cutoff similarity score
     */
    public ConnectedComponents()
    {
        this(DEFAULT_CUTOFF);
    }

    /**
     * Create a new connected components clustering algorithm with the
     * default cutoff similarity score.
     *
     * @see #DEFAULT_CUTOFF
     */
    public ConnectedComponents(final double cutoff)
    {
        this.cutoff = cutoff;
    }


    /** {@inheritDoc} */
    public Set<Cluster<E>> cluster(final List<? extends E> values,
                                   final Similarity<E> similarity,
                                   final ExitStrategy<E> exitStrategy)
    {
        checkClusterParameters(values, similarity, exitStrategy);

        // initialize graph and node list
        int size = values.size();
        Graph<E, Double> graph = GraphUtils.createGraph(size, size*size);
        List<Node<E, Double>> nodes = new ArrayList<Node<E, Double>>(size);
        for (E value : values)
        {
            nodes.add(graph.createNode(value));
        }

        // add an edge where the similarity score is greater than or equal to the cutoff
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < i; j++)
            {
                Node<E, Double> source = nodes.get(i);
                Node<E, Double> target = nodes.get(j);
                double score = similarity.similarity(source.getValue(), target.getValue());
                if (score >= cutoff)
                {
                    graph.createEdge(source, target, score);
                }
            }
        }

        // find all connected components
        List<Node<E, Double>> remain = new ArrayList<Node<E, Double>>(nodes);
        Set<Cluster<E>> clusters = new HashSet<Cluster<E>>();
        while (!remain.isEmpty())
        {
            Collect<E, Double> collect = new Collect<E, Double>(remain);
            GraphUtils.bfs(graph, nodes.get(0), collect);
            clusters.add(collect.getCluster());
        }

        fireExitSucceeded();
        return clusters;
    }

    /**
     * Collect procedure.
     *
     * @param <N> node value type
     * @param <E> edge value type
     */
    private static class Collect<N, E> implements UnaryProcedure<Node<N, E>>
    {
        /** List of remaining nodes. */
        private final List<Node<N, E>> remain;

        /** List of cluster members. */
        private final List<N> members;


        /**
         * Create a new collect procedure.
         *
         * @param remain list of remaining nodes
         */
        Collect(final List<Node<N, E>> remain)
        {
            this.remain = remain;
            members = new ArrayList<N>(remain.size());
        }


        /** {@inheritDoc} */
        public void run(final Node<N, E> node)
        {
            remain.remove(node);
            members.add(node.getValue());
        }

        /**
         * Return the cluster of values this procedure collected.
         *
         * @return the cluster of values this procedure collected
         */
        Cluster<N> getCluster()
        {
            return new ClusterImpl<N>(members);
        }
    }
}