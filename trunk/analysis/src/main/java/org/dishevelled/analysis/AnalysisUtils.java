/*

    dsh-analysis  Data analysis.
    Copyright (c) 2011 held jointly by the individual authors.

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
import java.util.Map;

import static org.dishevelled.collect.Maps.createMap;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import static org.dishevelled.graph.impl.GraphUtils.createGraph;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.TernaryProcedure;

import org.dishevelled.matrix.Matrix2D;

import static org.dishevelled.matrix.impl.SparseMatrixUtils.createSparseMatrix2D;

import org.dishevelled.multimap.BinaryKey;
import org.dishevelled.multimap.BinaryKeyMap;

import static org.dishevelled.multimap.impl.BinaryKeyMaps.createBinaryKeyMap;

/**
 * Static utility methods for data analysis.
 *
 * @author  Michael Heuer
 */
public final class AnalysisUtils
{

    /**
     * Private no-arg constructor.
     */
    private AnalysisUtils()
    {
        // empty
    }


    /**
     * Convert the specified graph to a sparse matrix.
     *
     * @param <N> graph node type
     * @param <E> graph edge type and sparse matrix type
     * @param graph graph to convert, must not be null
     * @param nodes nodes, must not be null
     * @return the specified graph converted to a sparse matrix
     */
    public static <N, E> Matrix2D<E> toSparseMatrix(final Graph<N, E> graph, final List<Node<N, E>> nodes)
    {
        return toSparseMatrix(graph, new NodeIndices<N, E>(nodes));
    }

    /**
     * Node indices mapping function.
     *
     * @param <N> graph node type
     * @param <E> graph edge type
     */
    private static class NodeIndices<N, E> implements UnaryFunction<Node<N, E>, Long>
    {
        /** List of nodes. */
        private final List<Node<N, E>> nodes;


        /**
         * Create a new node indices mapping function with the specified list of nodes.
         *
         * @param nodes list of nodes, must not be null
         */
        NodeIndices(final List<Node<N, E>> nodes)
        {
            if (nodes == null)
            {
                throw new IllegalArgumentException("nodes must not be null");
            }
            this.nodes = nodes;
        }


        @Override
        public Long evaluate(final Node<N, E> node)
        {
            return Long.valueOf(nodes.indexOf(node));
        }
    }

    /**
     * Convert the specified graph to a sparse matrix.
     *
     * @param <N> graph node type
     * @param <E> graph edge type and sparse matrix type
     * @param graph graph to convert, must not be null
     * @param nodeIndices mapping of long indices by nodes, must not be null
     * @return the specified graph converted to a sparse matrix
     */
    public static <N, E> Matrix2D<E> toSparseMatrix(final Graph<N, E> graph, final UnaryFunction<Node<N, E>, Long> nodeIndices)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        if (nodeIndices == null)
        {
            throw new IllegalArgumentException("nodeIndices must not be null");
        }
        long n = graph.nodeCount();
        int e = graph.edgeCount();
        final Matrix2D<E> matrix = createSparseMatrix2D(n, n, e, 0.75f);
        graph.forEachEdge(new UnaryProcedure<Edge<N, E>>()
                          {
                              @Override
                              public void run(final Edge<N, E> edge)
                              {
                                  // todo:  provide "merge strategy" for multiple edges
                                  matrix.set(nodeIndices.evaluate(edge.source()), nodeIndices.evaluate(edge.target()), edge.getValue());
                              }
                          });
        return matrix;
    }

    /** Index node values mapping function. */
    private static final UnaryFunction<Long, Long> INDEX_NODE_VALUES = new UnaryFunction<Long, Long>()
    {
        @Override
        public Long evaluate(final Long value)
        {
            return value;
        }
    };

    /**
     * Convert the specified graph to a binary key map.  Note that only nodes with degree
     * of at least one will be present in the returned binary key map.
     *
     * @param <N> graph node type and binary key map key type
     * @param <E> graph edge type and binary key map value type
     * @param graph graph to convert, must not be null
     * @return the specified graph converted to a binary key map
     */
    public static <N, E> BinaryKeyMap<N, N, E> toBinaryKeyMap(final Graph<N, E> graph)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        final BinaryKeyMap<N, N, E> binaryKeyMap = createBinaryKeyMap(graph.edgeCount());
        graph.forEachEdge(new UnaryProcedure<Edge<N, E>>()
                          {
                              @Override
                              public void run(final Edge<N, E> edge)
                              {
                                  // todo:  provide "merge strategy" for multiple edges
                                  binaryKeyMap.put(edge.source().getValue(), edge.target().getValue(), edge.getValue());
                              }
                          });
        return binaryKeyMap;
    }

    /**
     * Convert the specified binary key map to a graph.  The first key value will be the
     * source node and the second key value the target node in edges in the returned graph.
     *
     * @param <N> binary key map key type and graph node type
     * @param <E> binary key map value type and graph edge type
     * @param binaryKeyMap binary key map to convert, must not be null
     * @return the specified binary key map converted to a graph
     */
    public static <N, E> Graph<N, E> toGraph(final BinaryKeyMap<N, N, E> binaryKeyMap)
    {
        if (binaryKeyMap == null)
        {
            throw new IllegalArgumentException("binaryKeyMap must not be null");
        }
        int n = binaryKeyMap.keySet().size();
        int e = binaryKeyMap.size();
        final Map<N, Node<N, E>> nodes = createMap(n);
        final Graph<N, E> graph = createGraph(n, e);
        for (Map.Entry<BinaryKey<N, N>, E> entry : binaryKeyMap.entrySet())
        {
            N sourceValue = entry.getKey().getFirstKey();
            N targetValue = entry.getKey().getSecondKey();
            E value = entry.getValue();
            if (!nodes.containsKey(sourceValue))
            {
                nodes.put(sourceValue, graph.createNode(sourceValue));
            }
            if (!nodes.containsKey(targetValue))
            {
                nodes.put(targetValue, graph.createNode(targetValue));
            }
            if (value != null)
            {
                // todo:  add a predicate to choose whether an edge gets created (e.g. value cutoff)
                graph.createEdge(nodes.get(sourceValue), nodes.get(targetValue), value);
            }            
        }
        return graph;
    }

    /**
     * Convert the specified matrix to a graph with long indices as node values.
     *
     * @param <E> matrix type and graph edge type
     * @param matrix matrix to convert, must not be null
     * @return the specified matrix converted to a graph with long indices as node values
     */
    public static <E> Graph<Long, E> toGraph(final Matrix2D<E> matrix)
    {
        return toGraph(matrix, INDEX_NODE_VALUES);
    }

    /**
     * Convert the specified matrix to a graph.
     *
     * @param <N> graph node type
     * @param <E> matrix type and graph edge type
     * @param matrix matrix to convert, must not be null
     * @param nodeValues mapping of node values by long indices, must not be null
     * @return the specified matrix converted to a graph
     */
    public static <N, E> Graph<N, E> toGraph(final Matrix2D<E> matrix, final UnaryFunction<Long, N> nodeValues)
    {
        if (matrix == null)
        {
            throw new IllegalArgumentException("matrix must not be null");
        }
        if (matrix.rows() != matrix.columns())
        {
            throw new IllegalArgumentException("matrix must be balanced, rows=" + matrix.rows() + ", columns=" + matrix.columns());
        }
        if (matrix.rows() > Integer.MAX_VALUE || matrix.columns() > Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException("graph size in number of nodes is limited to " + Integer.MAX_VALUE);
        }
        if (matrix.cardinality() > Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException("graph size in number of edges is limited to " + Integer.MAX_VALUE);
        }
        if (nodeValues == null)
        {
            throw new IllegalArgumentException("nodeValues must not be null");
        }
        int n = (int) matrix.rows();
        int e = (int) matrix.cardinality();
        // todo:  compare to createLongNonBlockingMap
        final Map<Long, Node<N, E>> nodes = createMap(n);
        final Graph<N, E> graph = createGraph(n, e);
        matrix.forEach(new TernaryProcedure<Long, Long, E>()
                       {
                           @Override
                           public void run(final Long row, final Long column, final E value)
                           {
                               if (!nodes.containsKey(row))
                               {
                                   nodes.put(row, graph.createNode(nodeValues.evaluate(row)));
                               }
                               if (!nodes.containsKey(column))
                               {
                                   nodes.put(column, graph.createNode(nodeValues.evaluate(column)));
                               }
                               if (value != null)
                               {
                                   // todo:  add a predicate to choose whether an edge gets created (e.g. value cutoff)
                                   graph.createEdge(nodes.get(row), nodes.get(column), value);
                               }
                           }
                       });
        return graph;
    }

    /*

      notes, not necessarily correct

Graph --> Matrix2D and vice versa, or Matrix2D + Map --> Graph

consider moving distance/similarity, interpolation, and similar functions to a separate data analysis project, see LingPipe API for inspiration

extend collect or start something new to pull together matrix, graph, multimap and weightedmap (observable + glazed too?)

To go from Graph to Matrix2D

<N, E> Matrix2D<E> toSparseMatrix(final Graph2D<? extends N, ? extends E> graph);
<N, E> Matrix2D<E> toSparseMatrix(final Graph2D<? extends N, ? extends E> graph, final List<N> nodes);
<N, E> Matrix2D<E> toSparseMatrix(final Graph2D<? extends N, ? extends E> graph, final Map<N, Integer> nodes);
<N, E> Matrix2D<E> toSparseMatrix(final Graph2D<? extends N, ? extends E> graph, final UnaryFunction<N, Integer> nodes);

<N, E> Matrix2D<E> toNonBlockingMatrix(final Graph2D<? extends N, ? extends E> graph);

from Matrix2D to Graph

<N, E> Graph<N, E> toGraph(final Matrix2D<E> matrix);
<N, E> Graph<N, E> toGraph(final Matrix2D<E> matrix, final List<N> nodes);
<N, E> Graph<N, E> toGraph(final Matrix2D<E> matrix, final Map<Integer, N> nodes);
<N, E> Graph<N, E> toGraph(final Matrix2D<E> matrix, final UnaryFunction<Integer, N> nodes);


     */
}