/*

    dsh-analysis  Data analysis.
    Copyright (c) 2011-2013 held jointly by the individual authors.

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
package org.dishevelled.analysis;

import java.util.List;

import org.apache.commons.math.random.JDKRandomGenerator;
import org.apache.commons.math.random.RandomGenerator;

import org.dishevelled.collect.Lists;

import org.dishevelled.functor.BinaryFunction;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

import org.dishevelled.multimap.BinaryKeyMap;

import org.dishevelled.multimap.impl.BinaryKeyMaps;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.WeightedMaps;

/**
 * Graph generators.
 *
 * @author  Michael Heuer
 */
public final class GraphGenerators
{


    public static <N, E> Graph<N, E> connectCompletely(final Graph<N, E> graph, final E edgeValue)
    {
        return connectCompletely(graph, new Scalar<N, E>(edgeValue));
    }

    public static <N, E> Graph<N, E> connectCompletely(final Graph<N, E> graph, final BinaryFunction<N, N, E> edgeValues)
    {
        int n = graph.nodeCount();
        List<Node<N, E>> nodes = Lists.asImmutableList(graph.nodes());
        BinaryKeyMap<Node<N, E>, Node<N, E>, Edge<N, E>> edges = BinaryKeyMaps.createBinaryKeyMap(n * n);
        for (Edge<N, E> edge : graph.edges())
        {
            edges.put(edge.source(), edge.target(), edge);
        }
        for (int i = 0; i < n; i++)
        {
            Node<N, E> source = nodes.get(i);
            for (int j = 0; j < n; j++)
            {
                Node<N, E> target = nodes.get(j);
                if (!source.equals(target) && !edges.containsKey(source, target))
                {
                    E edgeValue = edgeValues.evaluate(source.getValue(), target.getValue());
                    graph.createEdge(source, target, edgeValue);
                }
            }
        }
        return graph;
    }



    public static <N, E> Graph<N, E> connectCompletely(final List<N> nodeValues, final E edgeValue)
    {
        return connectCompletely(nodeValues, new Scalar<N, E>(edgeValue));
    }

    public static <N, E> Graph<N, E> connectCompletely(final List<N> nodeValues, final BinaryFunction<N, N, E> edgeValues)
    {
        int n = nodeValues.size();
        Graph<N, E> graph = GraphUtils.createGraph(n, n * n);
        List<Node<N, E>> nodes = Lists.createList(n);
        for (N nodeValue : nodeValues)
        {
            nodes.add(graph.createNode(nodeValue));
        }
        for (int i = 0; i < n; i++)
        {
            Node<N, E> source = nodes.get(i);
            for (int j = 0; j < n; j++)
            {
                Node<N, E> target = nodes.get(j);
                if (!source.equals(target))
                {
                    E edgeValue = edgeValues.evaluate(source.getValue(), target.getValue());
                    graph.createEdge(source, target, edgeValue);
                }
            }
        }
        return graph;
    }



    public static <N, E> Graph<N, E> connectRandomly(final Graph<N, E> graph, final int edgeCount, final E edgeValue)
    {
        return connectRandomly(graph, edgeCount, new Scalar<N, E>(edgeValue));
    }

    public static <N, E> Graph<N, E> connectRandomly(final Graph<N, E> graph, final int edgeCount, final E edgeValue, final RandomGenerator random)
    {
        return connectRandomly(graph, edgeCount, new Scalar<N, E>(edgeValue), random);
    }

    public static <N, E> Graph<N, E> connectRandomly(final Graph<N, E> graph, final int edgeCount, final BinaryFunction<N, N, E> edgeValues)
    {
        return connectRandomly(graph, edgeCount, edgeValues, new JDKRandomGenerator());
    }

    public static <N, E> Graph<N, E> connectRandomly(final Graph<N, E> graph, final int edgeCount, final BinaryFunction<N, N, E> edgeValues, final RandomGenerator random)
    {
        List<Node<N, E>> nodes = Lists.asImmutableList(graph.nodes());
        for (int i = graph.edgeCount(); i < edgeCount; i++)
        {
            Node<N, E> source = nodes.get(random.nextInt(nodes.size()));
            Node<N, E> target = nodes.get(random.nextInt(nodes.size()));
            E edgeValue = edgeValues.evaluate(source.getValue(), target.getValue());
            graph.createEdge(source, target, edgeValue);
        }
        return graph;
    }



    public static <N, E> Graph<N, E> connectRandomly(final List<N> nodeValues, final int edgeCount, final E edgeValue)
    {
        return connectRandomly(nodeValues, edgeCount, new Scalar<N, E>(edgeValue));
    }

    public static <N, E> Graph<N, E> connectRandomly(final List<N> nodeValues, final int edgeCount, final E edgeValue, final RandomGenerator random)
    {
        return connectRandomly(nodeValues, edgeCount, new Scalar<N, E>(edgeValue), random);
    }

    public static <N, E> Graph<N, E> connectRandomly(final List<N> nodeValues, final int edgeCount, final BinaryFunction<N, N, E> edgeValues)
    {
        return connectRandomly(nodeValues, edgeCount, edgeValues, new JDKRandomGenerator());
    }

    public static <N, E> Graph<N, E> connectRandomly(final List<N> nodeValues, final int edgeCount, final BinaryFunction<N, N, E> edgeValues, final RandomGenerator random)
    {
        int n = nodeValues.size();
        Graph<N, E> graph = GraphUtils.createGraph(n, edgeCount);
        List<Node<N, E>> nodes = Lists.createList(n);
        for (N nodeValue : nodeValues)
        {
            nodes.add(graph.createNode(nodeValue));
        }
        for (int i = 0; i < edgeCount; i++)
        {
            Node<N, E> source = nodes.get(random.nextInt(n));
            Node<N, E> target = nodes.get(random.nextInt(n));
            E edgeValue = edgeValues.evaluate(source.getValue(), target.getValue());
            graph.createEdge(source, target, edgeValue);
        }
        return graph;
    }



    public static <N, E> Graph<N, E> connectPreferentially(final Graph<N, E> graph, final int edgeCount, final E edgeValue)
    {
        return connectPreferentially(graph, edgeCount, new Scalar<N, E>(edgeValue));
    }

    public static <N, E> Graph<N, E> connectPreferentially(final Graph<N, E> graph, final int edgeCount, final BinaryFunction<N, N, E> edgeValues)
    {
        // weight nodes by degree
        WeightedMap<Node<N, E>> weightedNodes = WeightedMaps.createWeightedMap(graph.nodeCount());
        for (Node<N, E> node : graph.nodes())
        {
            weightedNodes.put(node, Double.valueOf(node.degree()));
        }

        // store existing edges to prevent creating duplicates
        BinaryKeyMap<Node<N, E>, Node<N, E>, Edge<N, E>> edges = BinaryKeyMaps.createBinaryKeyMap(edgeCount);
        for (Edge<N, E> edge : graph.edges())
        {
            edges.put(edge.source(), edge.target(), edge);
        }

        // connect preferentially until edgeCount is reached
        while (edges.size() < edgeCount)
        {
            Node<N, E> source = weightedNodes.sample();
            Node<N, E> target = weightedNodes.sample();
            // avoid self-edges
            if (!source.equals(target) && !edges.containsKey(source, target))
            {
                E edgeValue = edgeValues.evaluate(source.getValue(), target.getValue());
                edges.put(source, target, graph.createEdge(source, target, edgeValue));
                // update weights
                weightedNodes.put(source, Double.valueOf(source.degree()));
                weightedNodes.put(target, Double.valueOf(target.degree()));
            }
        }
        return graph;
    }



    public static <N, E> Graph<N, E> connectPreferentially(final List<N> nodeValues, final int edgeCount, final E edgeValue)
    {
        return connectPreferentially(nodeValues, edgeCount, new Scalar<N, E>(edgeValue));
    }

    public static <N, E> Graph<N, E> connectPreferentially(final List<N> nodeValues, final int edgeCount, final BinaryFunction<N, N, E> edgeValues)
    {
        int n = nodeValues.size();
        Graph<N, E> graph = GraphUtils.createGraph(n, edgeCount);
        List<Node<N, E>> nodes = Lists.createList(n);
        for (N nodeValue : nodeValues)
        {
            nodes.add(graph.createNode(nodeValue));
        }

        // initialize graph to node degree 1
        BinaryKeyMap<Node<N, E>, Node<N, E>, Edge<N, E>> edges = BinaryKeyMaps.createBinaryKeyMap(edgeCount);
        for (int i = 0; i < n; i += 2)
        {
            Node<N, E> source = nodes.get(i);
            Node<N, E> target = (i + 1 < n) ? nodes.get(i) : source;
            E edgeValue = edgeValues.evaluate(source.getValue(), target.getValue());
            edges.put(source, target, graph.createEdge(source, target, edgeValue));
        }

        // weight nodes by degree
        WeightedMap<Node<N, E>> weightedNodes = WeightedMaps.createWeightedMap(n);
        for (Node<N, E> node : graph.nodes())
        {
            weightedNodes.put(node, Double.valueOf(node.degree()));
        }

        // connect preferentially until edgeCount is reached
        while (edges.size() < edgeCount)
        {
            Node<N, E> source = weightedNodes.sample();
            Node<N, E> target = weightedNodes.sample();
            if (!source.equals(target) && !edges.containsKey(source, target))
            {
                E edgeValue = edgeValues.evaluate(source.getValue(), target.getValue());
                edges.put(source, target, graph.createEdge(source, target, edgeValue));
                weightedNodes.put(source, Double.valueOf(source.degree()));
                weightedNodes.put(target, Double.valueOf(target.degree()));
            }
        }
        return graph;
    }


    /** Scalar function. */
    private static class Scalar<N, E> implements BinaryFunction<N, N, E>
    {
        /** Value. */
        private final E value;

        /**
         * Create a new scalar function with the specified value.
         *
         * @param value value
         */
        private Scalar(final E value)
        {
            this.value = value;
        }

        @Override
        public E evaluate(final N ignore0, final N ignore1)
        {
            return value;
        }
    }
}