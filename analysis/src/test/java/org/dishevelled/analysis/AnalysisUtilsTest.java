/*

    dsh-analysis  Data analysis.
    Copyright (c) 2011-2012 held jointly by the individual authors.

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

import static org.junit.Assert.*;

import org.junit.Test;

import static org.dishevelled.analysis.AnalysisUtils.*;

import static org.dishevelled.collect.Lists.*;
import static org.dishevelled.collect.Maps.*;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.multimap.BinaryKeyMap;

import static org.dishevelled.graph.impl.GraphUtils.createGraph;

import static org.dishevelled.matrix.impl.SparseMatrixUtils.createSparseMatrix2D;

import static org.dishevelled.multimap.impl.BinaryKeyMaps.createBinaryKeyMap;

/**
 * Unit test for AnalysisUtils.
 */
public final class AnalysisUtilsTest
{
    private static final double TOLERANCE = 0.001d;

    // --> binary key map

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapNullGraph()
    {
        toBinaryKeyMap((Graph<String, Double>) null);
    }

    @Test
    public void testToBinaryKeyMapEmptyGraph()
    {
        Graph<String, Double> graph = createGraph();
        BinaryKeyMap<String, String, Double> binaryKeyMap = toBinaryKeyMap(graph);
        assertNotNull(binaryKeyMap);
        assertTrue(binaryKeyMap.isEmpty());
        assertTrue(binaryKeyMap.keySet().isEmpty());
    }

    @Test
    public void testToBinaryKeyMapNodesWithDegreeZeroAreNotPresent()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        graph.createEdge(node0, node0, 1.0d);
        BinaryKeyMap<String, String, Double> binaryKeyMap = toBinaryKeyMap(graph);
        assertEquals(1, binaryKeyMap.size());
        assertEquals(1.0d, binaryKeyMap.get("node0", "node0"), TOLERANCE);
        assertFalse(binaryKeyMap.containsKey("node0", "node1"));
        assertFalse(binaryKeyMap.containsKey("node1", "node0"));
        assertFalse(binaryKeyMap.containsKey("node1", "node1"));
    }

    @Test
    public void testToBinaryKeyMapGraph()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        graph.createEdge(node0, node1, 1.0d);
        graph.createEdge(node1, node0, 2.0d);
        BinaryKeyMap<String, String, Double> binaryKeyMap = toBinaryKeyMap(graph);
        assertEquals(2, binaryKeyMap.size());
        assertEquals(1.0d, binaryKeyMap.get("node0", "node1"), TOLERANCE);
        assertEquals(2.0d, binaryKeyMap.get("node1", "node0"), TOLERANCE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapNullMatrix()
    {
        toBinaryKeyMap((Matrix2D<Double>) null);
    }

    @Test
    public void testToBinaryKeyMapZeroSizeMatrix()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(0, 0);
        BinaryKeyMap<Long, Long, Double> binaryKeyMap = toBinaryKeyMap(matrix);
        assertNotNull(binaryKeyMap);
        assertTrue(binaryKeyMap.isEmpty());
        assertTrue(binaryKeyMap.keySet().isEmpty());
    }

    @Test
    public void testToBinaryKeyMapEmptyMatrix()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(10, 10);
        BinaryKeyMap<Long, Long, Double> binaryKeyMap = toBinaryKeyMap(matrix);
        assertNotNull(binaryKeyMap);
        assertTrue(binaryKeyMap.isEmpty());
        assertTrue(binaryKeyMap.keySet().isEmpty());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapUnbalancedMatrix()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(10, 20);
        toBinaryKeyMap(matrix);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapNullKeys()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        toBinaryKeyMap(matrix, null);
    }

    @Test
    public void testToBinaryKeyMapIncompleteKeyMapping()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        matrix.set(0, 1, 1.0d);
        matrix.set(1, 0, 2.0d);
        matrix.set(2, 3, 3.0d);
        matrix.set(3, 2, 4.0d);

        UnaryFunction<Long, String> incompleteMapping = new UnaryFunction<Long, String>()
            {
                @Override
                public String evaluate(final Long value)
                {
                    if (value < 2)
                    {
                        return value.toString();
                    }
                    return null;
                }
            };
        BinaryKeyMap<String, String, Double> binaryKeyMap = toBinaryKeyMap(matrix, incompleteMapping);
        assertEquals(1.0d, binaryKeyMap.get("0", "1"), TOLERANCE);
        assertEquals(2.0d, binaryKeyMap.get("1", "0"), TOLERANCE);
        assertFalse(binaryKeyMap.containsKey("2", "3"));
        assertFalse(binaryKeyMap.containsKey("3", "2"));
    }

    @Test
    public void testToBinaryKeyMapMatrix()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        matrix.set(0, 1, 1.0d);
        matrix.set(1, 0, 2.0d);
        matrix.set(2, 3, 3.0d);
        matrix.set(3, 2, 4.0d);

        UnaryFunction<Long, String> mapping = new UnaryFunction<Long, String>()
            {
                @Override
                public String evaluate(final Long value)
                {
                    return value.toString();
                }
            };
        BinaryKeyMap<String, String, Double> binaryKeyMap = toBinaryKeyMap(matrix, mapping);
        assertEquals(1.0d, binaryKeyMap.get("0", "1"), TOLERANCE);
        assertEquals(2.0d, binaryKeyMap.get("1", "0"), TOLERANCE);
        assertEquals(3.0d, binaryKeyMap.get("2", "3"), TOLERANCE);
        assertEquals(4.0d, binaryKeyMap.get("3", "2"), TOLERANCE);
        assertFalse(binaryKeyMap.containsKey("0", "0"));
        assertFalse(binaryKeyMap.containsKey("0", "3"));
        assertFalse(binaryKeyMap.containsKey("3", "0"));
    }

    // --> graph

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphNullBinaryKeyMap()
    {
        toGraph((BinaryKeyMap<String, String, Double>) null);
    }

    @Test
    public void testToGraphEmptyBinaryKeyMap()
    {
        BinaryKeyMap<String, String, Double>binaryKeyMap = createBinaryKeyMap(); 
        Graph<String, Double> graph = toGraph(binaryKeyMap);
        assertTrue(graph.isEmpty());
        assertTrue(graph.nodes().isEmpty());
        assertTrue(graph.edges().isEmpty());
    }

    @Test
    public void testToGraphBinaryKeyMap()
    {
        BinaryKeyMap<String, String, Double>binaryKeyMap = createBinaryKeyMap(); 
        binaryKeyMap.put("node0", "node1", 1.0d);
        binaryKeyMap.put("node1", "node0", 2.0d);
        binaryKeyMap.put("node2", "node2", null);
        Graph<String, Double> graph = toGraph(binaryKeyMap);
        assertFalse(graph.isEmpty());
        assertEquals(3, graph.nodeCount());
        assertEquals(2, graph.edgeCount());
        for (Edge<String, Double> edge : graph.edges())
        {
            if ("node0".equals(edge.source().getValue()))
            {
                assertEquals("node1", edge.target().getValue());
                assertEquals(1.0d, edge.getValue(), TOLERANCE);
            }
            else if ("node1".equals(edge.source().getValue()))
            {
                assertEquals("node0", edge.target().getValue());
                assertEquals(2.0d, edge.getValue(), TOLERANCE);
            }
            else
            {
                fail("only expected source node values node0 and node1");
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphBinaryKeyMapNullPredicate()
    {
        BinaryKeyMap<String, String, Double>binaryKeyMap = createBinaryKeyMap();
        toGraph(binaryKeyMap, null);
    }

    @Test
    public void testToGraphBinaryKeyMapPredicate()
    {
        BinaryKeyMap<String, String, Double>binaryKeyMap = createBinaryKeyMap(); 
        binaryKeyMap.put("node0", "node1", 1.0d);
        binaryKeyMap.put("node1", "node0", 2.0d);
        binaryKeyMap.put("node2", "node2", 3.0d);
        UnaryPredicate<Double> predicate = new UnaryPredicate<Double>()
            {
                @Override
                public boolean test(final Double value)
                {
                    return (value < 3.0d);
                }
            };

        Graph<String, Double> graph = toGraph(binaryKeyMap, predicate);
        assertFalse(graph.isEmpty());
        assertEquals(3, graph.nodeCount());
        assertEquals(2, graph.edgeCount());
        for (Edge<String, Double> edge : graph.edges())
        {
            if ("node0".equals(edge.source().getValue()))
            {
                assertEquals("node1", edge.target().getValue());
                assertEquals(1.0d, edge.getValue(), TOLERANCE);
            }
            else if ("node1".equals(edge.source().getValue()))
            {
                assertEquals("node0", edge.target().getValue());
                assertEquals(2.0d, edge.getValue(), TOLERANCE);
            }
            else
            {
                fail("only expected source node values node0 and node1");
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphNullMatrix()
    {
        toGraph((Matrix2D<Double>) null);
    }

    @Test
    public void testToGraphZeroSizeMatrix()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(0, 0);
        Graph<Long, Double> graph = toGraph(matrix);
        assertNotNull(graph);
        assertTrue(graph.isEmpty());
    }

    @Test
    public void testToGraphEmptyMatrix()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(10, 10);
        Graph<Long, Double> graph = toGraph(matrix);
        assertNotNull(graph);
        assertEquals(10, graph.nodeCount());
        assertEquals(0, graph.edgeCount());
        assertTrue(graph.nodeValues().contains(0L));
        assertTrue(graph.nodeValues().contains(9L));
        assertFalse(graph.nodeValues().contains(10L));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphUnbalancedMatrix()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(10, 20);
        toGraph(matrix);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphMatrixTooLarge()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(Integer.MAX_VALUE + 1L, Integer.MAX_VALUE + 1L, 20, 0.75f);
        toGraph(matrix);
    }

    @Test
    public void testToGraphSelfEdgesOnly()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        matrix.setQuick(0, 0, 0.0d);
        matrix.setQuick(1, 1, 1.0d);
        matrix.setQuick(2, 2, 2.0d);
        matrix.setQuick(3, 3, 3.0d);
        Graph<Long, Double> graph = toGraph(matrix);
        assertEquals(4, graph.nodeCount());
        assertEquals(4, graph.edgeCount());
        for (Edge<Long, Double> edge : graph.edges())
        {
            assertEquals(edge.source(), edge.target());
            assertEquals(edge.target(), edge.source());
            assertEquals((double) edge.source().getValue(), edge.getValue(), TOLERANCE);
            assertEquals((double) edge.target().getValue(), edge.getValue(), TOLERANCE);
        }
    }

    @Test
    public void testToGraphDirectedEdges()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        matrix.setQuick(0, 1, 0.0d);
        matrix.setQuick(1, 0, 1.0d);
        Graph<Long, Double> graph = toGraph(matrix);
        assertEquals(4, graph.nodeCount());
        assertEquals(2, graph.edgeCount());
        for (Edge<Long, Double> edge : graph.edges())
        {
            if (edge.source().getValue().equals(0L))
            {
                assertTrue(edge.target().getValue().equals(1L));
                assertEquals(0.0d, edge.getValue(), TOLERANCE);
            }
            if (edge.source().getValue().equals(1L))
            {
                assertTrue(edge.target().getValue().equals(0L));
                assertEquals(1.0d, edge.getValue(), TOLERANCE);
            }
        }
    }        

    @Test
    public void testToGraphMatrixPredicate()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        matrix.setQuick(0, 1, 1.0d);
        matrix.setQuick(1, 0, 2.0d);
        matrix.setQuick(2, 2, 3.0d);

        UnaryFunction<Long, String> nodeValues = new UnaryFunction<Long, String>()
            {
                @Override
                public String evaluate(final Long value)
                {
                    return "node" + value.toString();
                }
            };
        UnaryPredicate<Double> predicate = new UnaryPredicate<Double>()
            {
                @Override
                public boolean test(final Double value)
                {
                    return (value < 3.0d);
                }
            };

        Graph<String, Double> graph = toGraph(matrix, nodeValues, predicate);
        assertFalse(graph.isEmpty());
        assertEquals(4, graph.nodeCount());
        assertEquals(2, graph.edgeCount());
        for (Edge<String, Double> edge : graph.edges())
        {
            if ("node0".equals(edge.source().getValue()))
            {
                assertEquals("node1", edge.target().getValue());
                assertEquals(1.0d, edge.getValue(), TOLERANCE);
            }
            else if ("node1".equals(edge.source().getValue()))
            {
                assertEquals("node0", edge.target().getValue());
                assertEquals(2.0d, edge.getValue(), TOLERANCE);
            }
            else
            {
                fail("only expected source node values node0 and node1");
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphNullNodeValues()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        toGraph(matrix, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphNullPredicate()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(4, 4);
        UnaryFunction<Long, String> nodeValues = new UnaryFunction<Long, String>()
            {
                @Override
                public String evaluate(final Long value)
                {
                    return value.toString();
                }
            };
        toGraph(matrix, nodeValues, null);
    }

    // --> sparse matrix

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullBinaryKeyMap()
    {
        List<String> emptyKeys = emptyList();
        toSparseMatrix((BinaryKeyMap<String, String, Double>) null, emptyKeys);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullKeys()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        toSparseMatrix(binaryKeyMap, (List<String>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixWithKeyIndicesNullBinaryKeyMap()
    {
        toSparseMatrix((BinaryKeyMap<String, String, Double>) null, new UnaryFunction<String, Long>()
                       {
                           public Long evaluate(final String value)
                           {
                               return Long.valueOf(0L);
                           }
                       });
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullKeyIndicesMap()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        toSparseMatrix(binaryKeyMap, (Map<String, Long>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullKeyIndices()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        toSparseMatrix(binaryKeyMap, (UnaryFunction<String, Long>) null);
    }

    @Test
    public void testToSparseMatrixEmptyBinaryKeyMap()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        List<String> keys = emptyList();
        Matrix2D<Double> matrix = toSparseMatrix(binaryKeyMap, keys);
        assertEquals(0, matrix.rows());
        assertEquals(0, matrix.columns());
        assertEquals(0, matrix.cardinality());
    }

    @Test
    public void testToSparseMatrixBinaryKeyMapEmptyKeys()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        binaryKeyMap.put("node0", "node1", 1.0d);
        binaryKeyMap.put("node1", "node0", 2.0d);
        List<String> keys = emptyList();
        Matrix2D<Double> matrix = toSparseMatrix(binaryKeyMap, keys);
        // or should this be an empty matrix?
        assertEquals(2, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(0, matrix.cardinality());
    }

    @Test
    public void testToSparseMatrixBinaryKeyMap()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        binaryKeyMap.put("node0", "node1", 1.0d);
        binaryKeyMap.put("node1", "node0", 2.0d);
        List<String> keys = asList("node0", "node1");
        Matrix2D<Double> matrix = toSparseMatrix(binaryKeyMap, keys);
        assertEquals(2, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
    }

    @Test
    public void testToSparseMatrixBinaryKeyMapKeyIndicesMap()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        binaryKeyMap.put("node0", "node1", 1.0d);
        binaryKeyMap.put("node1", "node0", 2.0d);
        binaryKeyMap.put("node2", "node0", 3.0d);
        binaryKeyMap.put("node0", "node2", 4.0d);
        Map<String, Long> keyIndices = createMap();
        keyIndices.put("node0", 0L);
        keyIndices.put("node1", 1L);

        Matrix2D<Double> matrix = toSparseMatrix(binaryKeyMap, keyIndices);
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
    }

    @Test
    public void testToSparseMatrixBinaryKeyMapKeyIndices()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        binaryKeyMap.put("node0", "node1", 1.0d);
        binaryKeyMap.put("node1", "node0", 2.0d);
        binaryKeyMap.put("node2", "node0", 3.0d);
        binaryKeyMap.put("node0", "node2", 4.0d);
        UnaryFunction<String, Long> keyIndices = new UnaryFunction<String, Long>()
            {
                @Override
                public Long evaluate(final String key)
                {
                    if ("node0".equals(key))
                    {
                        return Long.valueOf(0);
                    }
                    else if ("node1".equals(key))
                    {
                        return Long.valueOf(1);
                    }
                    return null;
                }
            };

        Matrix2D<Double> matrix = toSparseMatrix(binaryKeyMap, keyIndices);
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullGraph()
    {
        List<Node<String, Double>> emptyNodes = emptyList();
        toSparseMatrix((Graph<String, Double>) null, emptyNodes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullNodes()
    {
        Graph<String, Double> graph = createGraph();
        toSparseMatrix(graph, (List<Node<String, Double>>) null);
    }

    @Test
    public void testToSparseMatrixEmptyGraph()
    {
        Graph<String, Double> graph = createGraph();
        List<Node<String, Double>> emptyNodes = emptyList();
        Matrix2D<Double> matrix = toSparseMatrix(graph, emptyNodes);
        assertNotNull(matrix);
        assertEquals(0, matrix.rows());
        assertEquals(0, matrix.columns());
        assertTrue(matrix.isEmpty());
    }

    @Test
    public void testToSparseMatrixNoEdges()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        Matrix2D<Double> matrix = toSparseMatrix(graph, asList(node0, node1));
        assertEquals(2, matrix.rows());
        assertEquals(2, matrix.columns());
        assertTrue(matrix.isEmpty());
    }

    @Test
    public void testToSparseMatrixTwoNodesTwoEdges()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        graph.createEdge(node0, node1, 1.0d);
        graph.createEdge(node1, node0, 2.0d);
        Matrix2D<Double> matrix = toSparseMatrix(graph, asList(node0, node1));
        assertEquals(2, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
        assertFalse(matrix.isEmpty());
    }

    @Test
    public void testToSparseMatrixGraphNodes()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        Node<String, Double> node2 = graph.createNode("node2");
        graph.createEdge(node0, node1, 1.0d);
        graph.createEdge(node1, node0, 2.0d);
        graph.createEdge(node2, node0, 3.0d);
        graph.createEdge(node0, node2, 4.0d);

        Matrix2D<Double> matrix = toSparseMatrix(graph, asList(node0, node1));
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
        assertFalse(matrix.isEmpty());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullNodeIndicesMap()
    {
        Graph<String, Double> graph = createGraph();
        toSparseMatrix(graph, (Map<Node<String, Double>, Long>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullNodeIndices()
    {
        Graph<String, Double> graph = createGraph();
        toSparseMatrix(graph, (UnaryFunction<Node<String, Double>, Long>) null);
    }

    @Test
    public void testToSparseMatrixGraphNodeIndicesMap()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        Node<String, Double> node2 = graph.createNode("node2");
        graph.createEdge(node0, node1, 1.0d);
        graph.createEdge(node1, node0, 2.0d);
        graph.createEdge(node2, node0, 3.0d);
        graph.createEdge(node0, node2, 4.0d);
        Map<Node<String, Double>, Long> nodeIndices = createMap();
        nodeIndices.put(node0, 0L);
        nodeIndices.put(node1, 1L);

        Matrix2D<Double> matrix = toSparseMatrix(graph, nodeIndices);
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
        assertFalse(matrix.isEmpty());
    }

    @Test
    public void testToSparseMatrixGraphNodeIndices()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        Node<String, Double> node2 = graph.createNode("node2");
        graph.createEdge(node0, node1, 1.0d);
        graph.createEdge(node1, node0, 2.0d);
        graph.createEdge(node2, node0, 3.0d);
        graph.createEdge(node0, node2, 4.0d);
        UnaryFunction<Node<String, Double>, Long> nodeIndices = new UnaryFunction<Node<String, Double>, Long>()
            {
                @Override
                public Long evaluate(final Node<String, Double> node)
                {
                    if ("node0".equals(node.getValue()))
                    {
                        return Long.valueOf(0);
                    }
                    else if ("node1".equals(node.getValue()))
                    {
                        return Long.valueOf(1);
                    }
                    return null;
                }
            };

        Matrix2D<Double> matrix = toSparseMatrix(graph, nodeIndices);
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
        assertFalse(matrix.isEmpty());
    }
}