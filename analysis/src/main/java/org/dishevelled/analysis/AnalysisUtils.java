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
import java.util.Set;

import static org.dishevelled.collect.Maps.createMap;
import static org.dishevelled.collect.Sets.createSet;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import static org.dishevelled.graph.impl.GraphUtils.createGraph;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;
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


    // --> binary key map


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
        int e = Math.max(16, graph.edgeCount());
        final BinaryKeyMap<N, N, E> binaryKeyMap = createBinaryKeyMap(e);
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
     * Convert the specified matrix to a binary key map with long indices as keys.
     *
     * @param <E> matrix type and binary key map value type
     * @param matrix matrix to convert, must not be null
     * @return the specified matrix converted to a binary key map with long indices as keys
     */
    public static <E> BinaryKeyMap<Long, Long, E> toBinaryKeyMap(final Matrix2D<E> matrix)
    {
        return toBinaryKeyMap(matrix, IDENTITY);
    }

    /**
     * Convert the specified matrix to a binary key map.
     *
     * @param <N> binary key map key type
     * @param <E> matrix type and binary key map value type
     * @param matrix matrix to convert, must not be null
     * @param keys mapping of keys by long indices, must not be null
     * @return the specified matrix converted to a binary key map
     */
    public static <N, E> BinaryKeyMap<N, N, E> toBinaryKeyMap(final Matrix2D<E> matrix, final UnaryFunction<Long, N> keys)
    {
        if (matrix == null)
        {
            throw new IllegalArgumentException("matrix must not be null");
        }
        if (matrix.rows() != matrix.columns())
        {
            throw new IllegalArgumentException("matrix must be balanced, rows=" + matrix.rows() + ", columns=" + matrix.columns());
        }
        if (matrix.cardinality() > Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException("binary key map size is limited to " + Integer.MAX_VALUE);
        }
        if (keys == null)
        {
            throw new IllegalArgumentException("keys must not be null");
        }
        int e = Math.max(16, (int) matrix.cardinality());
        final BinaryKeyMap<N, N, E> binaryKeyMap = createBinaryKeyMap(e);
        matrix.forEach(new TernaryProcedure<Long, Long, E>()
                       {
                           @Override
                           public void run(final Long row, final Long column, final E value)
                           {
                               N firstKey = keys.evaluate(row);
                               N secondKey = keys.evaluate(column);

                               if (firstKey != null && secondKey != null && value != null)
                               {
                                  // todo:  provide "merge strategy" for multiple mappings
                                   binaryKeyMap.put(firstKey, secondKey, value);
                               }
                           }
                       });
        return binaryKeyMap;
    }


    // --> graph


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
        return toGraph(binaryKeyMap, new AcceptAll<E>());
    }

    /**
     * Convert the specified binary key map to a graph, adding edges for values accepted
     * by the specified predicate.  The first key value will be the source node and the second
     * key value the target node in edges in the returned graph.
     *
     * @param <N> binary key map key type and graph node type
     * @param <E> binary key map value type and graph edge type
     * @param binaryKeyMap binary key map to convert, must not be null
     * @param predicate binary key map value predicate, must not be null
     * @return the specified binary key map converted to a graph
     */
    public static <N, E> Graph<N, E> toGraph(final BinaryKeyMap<N, N, E> binaryKeyMap, final UnaryPredicate<E> predicate)
    {
        if (binaryKeyMap == null)
        {
            throw new IllegalArgumentException("binaryKeyMap must not be null");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        int n = Math.max(16, binaryKeyMap.keySet().size());
        int e = Math.max(16, binaryKeyMap.size());
        Map<N, Node<N, E>> nodes = createMap(n);
        Graph<N, E> graph = createGraph(n, e);
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
            if (value != null && predicate.test(value))
            {
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
        return toGraph(matrix, IDENTITY);
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
        return toGraph(matrix, nodeValues, new AcceptAll<E>());
    }

    /**
     * Convert the specified matrix to a graph, adding edges for values accepted by the specified predicate.
     *
     * @param <N> graph node type
     * @param <E> matrix type and graph edge type
     * @param matrix matrix to convert, must not be null
     * @param nodeValues mapping of node values by long indices, must not be null
     * @param predicate matrix value predicate, must not be null
     * @return the specified matrix converted to a graph
     */
    public static <N, E> Graph<N, E> toGraph(final Matrix2D<E> matrix,
                                             final UnaryFunction<Long, N> nodeValues,
                                             final UnaryPredicate<E> predicate)
    {
        if (matrix == null)
        {
            throw new IllegalArgumentException("matrix must not be null");
        }
        if (matrix.rows() != matrix.columns())
        {
            throw new IllegalArgumentException("matrix must be balanced, rows=" + matrix.rows() + ", columns=" + matrix.columns());
        }
        if (matrix.rows() > Integer.MAX_VALUE)
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
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        int n = Math.max(16, (int) matrix.rows());
        int e = Math.max(16, (int) matrix.cardinality());
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
                               if (value != null && predicate.test(value))
                               {
                                   graph.createEdge(nodes.get(row), nodes.get(column), value);
                               }
                           }
                       });
        return graph;
    }


    // --> sparse matrix


    /**
     * Convert the specified binary key map to a sparse matrix.
     *
     * @param <N> binary key map key type
     * @param <E> binary key map value type and sparse matrix type
     * @param binaryKeyMap binary key map to convert, must not be null
     * @param keys list of keys, must not be null
     * @return the specified binary key map converted to a sparse matrix
     */
    public static <N, E> Matrix2D<E> toSparseMatrix(final BinaryKeyMap<N, N, E> binaryKeyMap, final List<N> keys)
    {
        return toSparseMatrix(binaryKeyMap, new IndexOfKey<N>(keys));
    }

    /**
     * Convert the specified binary key map to a sparse matrix.
     *
     * @param <N> binary key map key type
     * @param <E> binary key map value type and sparse matrix type
     * @param binaryKeyMap binary key map to convert, must not be null
     * @param keyIndices map of long indices by keys, must not be null
     * @return the specified binary key map converted to a sparse matrix
     */
    public static <N, E> Matrix2D<E> toSparseMatrix(final BinaryKeyMap<N, N, E> binaryKeyMap, final Map<N, Long> keyIndices)
    {
        return toSparseMatrix(binaryKeyMap, new KeyIndices<N>(keyIndices));
    }

    /**
     * Convert the specified binary key map to a sparse matrix.
     *
     * @param <N> binary key map key type
     * @param <E> binary key map value type and sparse matrix type
     * @param binaryKeyMap binary key map to convert, must not be null
     * @param keyIndices mapping of long indices by keys, must not be null
     * @return the specified binary key map converted to a sparse matrix
     */
    public static <N, E> Matrix2D<E> toSparseMatrix(final BinaryKeyMap<N, N, E> binaryKeyMap, final UnaryFunction<N, Long> keyIndices)
    {
        if (binaryKeyMap == null)
        {
            throw new IllegalArgumentException("binaryKeyMap must not be null");
        }
        if (keyIndices == null)
        {
            throw new IllegalArgumentException("keyIndices must not be null");
        }
        Set<N> uniqueKeys = createSet(binaryKeyMap.size() * 2);
        for (BinaryKey<N, N> key : binaryKeyMap.keySet())
        {
            uniqueKeys.add(key.getFirstKey());
            uniqueKeys.add(key.getSecondKey());
        }
        long n = uniqueKeys.size();
        int e = binaryKeyMap.size();
        Matrix2D<E> matrix = createSparseMatrix2D(n, n, e, 0.75f);
        for (Map.Entry<BinaryKey<N, N>, E> entry : binaryKeyMap.entrySet())
        {
            N source = entry.getKey().getFirstKey();
            N target = entry.getKey().getSecondKey();
            Long sourceIndex = keyIndices.evaluate(source);
            Long targetIndex = keyIndices.evaluate(target);

            if (sourceIndex != null && targetIndex != null)
            {
                // todo:  provide "merge strategy" for multiple "edges"
                matrix.set(sourceIndex, targetIndex, entry.getValue());
            }
        }
        return matrix;
    }

    /**
     * Convert the specified graph to a sparse matrix.
     *
     * @param <N> graph node type
     * @param <E> graph edge type and sparse matrix type
     * @param graph graph to convert, must not be null
     * @param nodes list of nodes, must not be null
     * @return the specified graph converted to a sparse matrix
     */
    public static <N, E> Matrix2D<E> toSparseMatrix(final Graph<N, E> graph, final List<Node<N, E>> nodes)
    {
        return toSparseMatrix(graph, new IndexOfNode<N, E>(nodes));
    }

    /**
     * Convert the specified graph to a sparse matrix.
     *
     * @param <N> graph node type
     * @param <E> graph edge type and sparse matrix type
     * @param graph graph to convert, must not be null
     * @param nodeIndices map of long indicies by nodes, must not be null
     * @return the specified graph converted to a sparse matrix
     */
    public static <N, E> Matrix2D<E> toSparseMatrix(final Graph<N, E> graph, final Map<Node<N, E>, Long> nodeIndices)
    {
        return toSparseMatrix(graph, new NodeIndices<N, E>(nodeIndices));
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
                                  Long sourceIndex = nodeIndices.evaluate(edge.source());
                                  Long targetIndex = nodeIndices.evaluate(edge.target());

                                  if (sourceIndex != null && targetIndex != null)
                                  {
                                      // todo:  provide "merge strategy" for multiple edges
                                      matrix.set(sourceIndex, targetIndex, edge.getValue());
                                  }
                              }
                          });
        return matrix;
    }


    // helper classes


    /** Identity mapping function. */
    private static final UnaryFunction<Long, Long> IDENTITY = new UnaryFunction<Long, Long>()
    {
        @Override
        public Long evaluate(final Long value)
        {
            return value;
        }
    };

    /**
     * Accept all values predicate.
     *
     * @param <E> value type
     */
    private static class AcceptAll<E> implements UnaryPredicate<E>
    {
        @Override
        public boolean test(final E value)
        {
            return true;
        }
    }

    /**
     * Index of key mapping function.
     *
     * @param <N> key type
     */
    private static class IndexOfKey<N> implements UnaryFunction<N, Long>
    {
        /** List of keys. */
        private final List<N> keys;


        /**
         * Create a new index of key mapping function with the specified list of keys.
         *
         * @param keys list of keys, must not be null
         */
        IndexOfKey(final List<N> keys)
        {
            if (keys == null)
            {
                throw new IllegalArgumentException("keys must not be null");
            }
            this.keys = keys;
        }


        @Override
        public Long evaluate(final N key)
        {
            return Long.valueOf(keys.indexOf(key));
        }
    }

    /**
     * Index of node mapping function.
     *
     * @param <N> graph node type
     * @param <E> graph edge type
     */
    private static class IndexOfNode<N, E> implements UnaryFunction<Node<N, E>, Long>
    {
        /** List of nodes. */
        private final List<Node<N, E>> nodes;


        /**
         * Create a new index of node mapping function with the specified list of nodes.
         *
         * @param nodes list of nodes, must not be null
         */
        IndexOfNode(final List<Node<N, E>> nodes)
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
     * Key indices mapping function.
     *
     * @param <N> key type
     */
    private static class KeyIndices<N> implements UnaryFunction<N, Long>
    {
        /** Map of keys to long indices. */
        private final Map<N, Long> keyIndices;


        /**
         * Create a new key indicies mapping function with the specified map of keys to long indices.
         *
         * @param keyIndices map of keys to long indices, must not be null
         */
        KeyIndices(final Map<N, Long> keyIndices)
        {
            if (keyIndices == null)
            {
                throw new IllegalArgumentException("keyIndices must not be null");
            }
            this.keyIndices = keyIndices;
        }


        @Override
        public Long evaluate(final N key)
        {
            return keyIndices.get(key);
        }
    }

    /**
     * Node indices mapping function.
     *
     * @param <N> graph node type
     * @param <E> graph edge type
     */
    private static class NodeIndices<N, E> implements UnaryFunction<Node<N, E>, Long>
    {
        /** Map of nodes to long indices. */
        private final Map<Node<N, E>, Long> nodeIndices;


        /**
         * Create a new node indices mapping function with the specified map of nodes to long indices.
         *
         * @param nodeIndices map of nodes to long indices, must not be null
         */
        NodeIndices(final Map<Node<N, E>, Long> nodeIndices)
        {
            if (nodeIndices == null)
            {
                throw new IllegalArgumentException("nodeIndices must not be null");
            }
            this.nodeIndices = nodeIndices;
        }


        @Override
        public Long evaluate(final Node<N, E> node)
        {
            return nodeIndices.get(node);
        }
    }
}