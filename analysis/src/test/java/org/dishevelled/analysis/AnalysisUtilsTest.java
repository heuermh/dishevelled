/*

    dsh-analysis  Data analysis.
    Copyright (c) 2011-2014 held jointly by the individual authors.

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

import static org.dishevelled.analysis.AnalysisUtils.toBinaryKeyMap;
import static org.dishevelled.analysis.AnalysisUtils.toBitMatrix;
import static org.dishevelled.analysis.AnalysisUtils.toGraph;
import static org.dishevelled.analysis.AnalysisUtils.toHashBasedTable;
import static org.dishevelled.analysis.AnalysisUtils.toImmutableTable;
import static org.dishevelled.analysis.AnalysisUtils.toSparseMatrix2D;

import static org.dishevelled.collect.Lists.asList;
import static org.dishevelled.collect.Lists.emptyList;
import static org.dishevelled.collect.Maps.createMap;

import static org.dishevelled.graph.impl.GraphUtils.createGraph;

import static org.dishevelled.matrix.impl.SparseMatrixUtils.createSparseMatrix2D;

import static org.dishevelled.multimap.impl.BinaryKeyMaps.createBinaryKeyMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import org.dishevelled.analysis.AnalysisUtils.AcceptAll;
import org.dishevelled.analysis.AnalysisUtils.AcceptNonNull;

import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.matrix.BitMatrix2D;
import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.multimap.BinaryKeyMap;

import org.dishevelled.multimap.impl.BinaryKeyMaps;

import org.junit.Test;

/**
 * Unit test for AnalysisUtils.
 */
public final class AnalysisUtilsTest
{
    private static final double TOLERANCE = 0.001d;

    private final UnaryPredicate<Integer> acceptAll = new AcceptAll<Integer>();
    private final UnaryPredicate<Integer> acceptNonNull = new AcceptNonNull<Integer>();

    private final BinaryFunction<Long, Long, String> addExpr = new BinaryFunction<Long, Long, String>()
        {
            @Override
            public String evaluate(final Long source, final Long target)
            {
                return source + "+" + target;
            }
        };

    private final UnaryFunction<Long, String> incompleteMapping = new UnaryFunction<Long, String>()
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

    private final UnaryFunction<Long, String> mapping = new UnaryFunction<Long, String>()
        {
            @Override
            public String evaluate(final Long value)
            {
                return value.toString();
            }
        };

    private final BinaryFunction<String, String, Integer> length = new BinaryFunction<String, String, Integer>()
        {
            @Override
            public Integer evaluate(final String source, final String target)
            {
                return source.length() + target.length();
            }
        };

    private final UnaryFunction<String, Long> indices = new UnaryFunction<String, Long>()
        {
            @Override
            public Long evaluate(final String value)
            {
                if ("foo".equals(value))
                {
                    return 0L;
                }
                else if ("bar".equals(value))
                {
                    return 1L;
                }
                else if ("baz".equals(value))
                {
                    return 2L;
                }
                return -1L;
            }
        };

    private final UnaryFunction<Node<String, Integer>, Long> nodeIndicesFn = new UnaryFunction<Node<String, Integer>, Long>()
        {
            @Override
            public Long evaluate(final Node<String, Integer> node)
            {
                String value = node.getValue();
                if ("foo".equals(value))
                {
                    return 0L;
                }
                else if ("bar".equals(value))
                {
                    return 1L;
                }
                else if ("baz".equals(value))
                {
                    return 2L;
                }
                return -1L;
            }
        };


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

        BinaryKeyMap<String, String, Double> binaryKeyMap = toBinaryKeyMap(matrix, mapping);
        assertEquals(1.0d, binaryKeyMap.get("0", "1"), TOLERANCE);
        assertEquals(2.0d, binaryKeyMap.get("1", "0"), TOLERANCE);
        assertEquals(3.0d, binaryKeyMap.get("2", "3"), TOLERANCE);
        assertEquals(4.0d, binaryKeyMap.get("3", "2"), TOLERANCE);
        assertFalse(binaryKeyMap.containsKey("0", "0"));
        assertFalse(binaryKeyMap.containsKey("0", "3"));
        assertFalse(binaryKeyMap.containsKey("3", "0"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapBitMatrixBinaryFunctionNullBitMatrix()
    {
        toBinaryKeyMap((BitMatrix2D) null, addExpr);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapBitMatrixBinaryFunctionNullBinaryFunction()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(4, 4);
        bitMatrix.set(1, 2, true);
        toBinaryKeyMap(bitMatrix, (BinaryFunction<Long, Long, String>) null);
    }

    @Test
    public void testToBinaryKeyMapBitMatrixBinaryFunction()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(4, 4);
        bitMatrix.set(1, 2, true);
        BinaryKeyMap<Long, Long, String> binaryKeyMap = toBinaryKeyMap(bitMatrix, addExpr);
        assertEquals("1+2", binaryKeyMap.get(1L, 2L));
        assertNull(binaryKeyMap.get(1L, 3L));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapUnbalancedBitMatrix()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(4, 3);
        bitMatrix.set(1, 2, true);
        toBinaryKeyMap(bitMatrix, addExpr);
    }

    @Test
    public void testToBinaryKeyMapBitMatrixUnaryFunctionBinaryFunction()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(4, 4);
        bitMatrix.set(1, 2, true);

        BinaryKeyMap<String, String, Integer> binaryKeyMap = toBinaryKeyMap(bitMatrix, mapping, length);
        assertEquals(Integer.valueOf(2), binaryKeyMap.get("1", "2"));
        assertNull(binaryKeyMap.get("1", "3"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapBitMatrixUnaryFunctionBinaryFunctionNullBitMatrix()
    {
        toBinaryKeyMap((BitMatrix2D) null, mapping, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapBitMatrixUnaryFunctionBinaryFunctionNullUnaryFunction()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(4, 4);
        bitMatrix.set(1, 2, true);
        toBinaryKeyMap(bitMatrix, null, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapBitMatrixUnaryFunctionBinaryFunctionNullBinaryFunction()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(4, 4);
        bitMatrix.set(1, 2, true);
        toBinaryKeyMap(bitMatrix, mapping, null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBinaryKeyMapNullTable()
    {
        toBinaryKeyMap((Table<String, String, Integer>) null);
    }

    @Test
    public void testToBinaryKeyMapTable()
    {
        ImmutableTable<String, String, Integer> table = new ImmutableTable.Builder<String, String, Integer>()
            .put("foo", "bar", 42)
            .put("bar", "baz", 42)
            .put("baz", "foo", 42)
            .build();

        BinaryKeyMap<String, String, Integer> binaryKeyMap = toBinaryKeyMap(table);
        assertNotNull(binaryKeyMap);
        assertEquals(Integer.valueOf(42), binaryKeyMap.get("baz", "foo"));        
    }

    // --> bitMatrix

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixNullBinaryKeyMap()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        toBitMatrix((BinaryKeyMap<String, String, Integer>) null, keys, acceptAll);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixNullList()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        toBitMatrix(binaryKeyMap, (List<String>) null, acceptAll);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixNullUnaryPredicate()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        toBitMatrix(binaryKeyMap, keys, null);
    }

    @Test
    public void testToBitMatrix()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        BitMatrix2D bitMatrix = toBitMatrix(binaryKeyMap, keys, acceptAll);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }

    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixKeyIndicesNullBinaryKeyMap()
    {
        Map<String, Long> keyIndices = new HashMap<String, Long>();
        keyIndices.put("foo", 0L);
        keyIndices.put("bar", 1L);
        keyIndices.put("baz", 2L);
        toBitMatrix((BinaryKeyMap<String, String, Integer>) null, keyIndices, acceptAll);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixKeyIndicesNullMap()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        toBitMatrix(binaryKeyMap, (Map<String, Long>) null, acceptAll);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixKeyIndicesNullUnaryPredicate()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        Map<String, Long> keyIndices = new HashMap<String, Long>();
        keyIndices.put("foo", 0L);
        keyIndices.put("bar", 1L);
        keyIndices.put("baz", 2L);
        toBitMatrix(binaryKeyMap, keyIndices, null);
    }

    @Test
    public void testToBitMatrixKeyIndices()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        Map<String, Long> keyIndices = new HashMap<String, Long>();
        keyIndices.put("foo", 0L);
        keyIndices.put("bar", 1L);
        keyIndices.put("baz", 2L);
        BitMatrix2D bitMatrix = toBitMatrix(binaryKeyMap, keyIndices, acceptAll);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixUnaryFunctionNullBinaryKeyMap()
    {
        toBitMatrix((BinaryKeyMap<String, String, Integer>) null, indices, acceptAll);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixUnaryFunctionNullUnaryFunction()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        toBitMatrix(binaryKeyMap, (UnaryFunction<String, Long>) null, acceptAll);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixUnaryFunctionNullUnaryPredicate()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        toBitMatrix(binaryKeyMap, indices, null);
    }

    @Test
    public void testToBitMatrixUnaryFunction()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        BitMatrix2D bitMatrix = toBitMatrix(binaryKeyMap, indices, acceptAll);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphNullGraph()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        List<Node<String, Integer>> nodes = ImmutableList.of(foo, bar, baz);
        toBitMatrix((Graph<String, Integer>) null, nodes, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphNullList()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        toBitMatrix(graph, (List<Node<String, Integer>>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphNullUnaryPredicate()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        List<Node<String, Integer>> nodes = ImmutableList.of(foo, bar, baz);
        toBitMatrix(graph, nodes, null);
    }

    @Test
    public void testToBitMatrixGraph()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        List<Node<String, Integer>> nodes = ImmutableList.of(foo, bar, baz);
        BitMatrix2D bitMatrix = toBitMatrix(graph, nodes, acceptAll);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }


    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphNodeIndicesNullGraph()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        Map<Node<String, Integer>, Long> nodeIndices = new HashMap<Node<String, Integer>, Long>();
        nodeIndices.put(foo, 0L);
        nodeIndices.put(bar, 1L);
        nodeIndices.put(baz, 2L);
        toBitMatrix((Graph<String, Integer>) null, nodeIndices, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphNodeIndicesNullList()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        toBitMatrix(graph, (Map<Node<String, Integer>, Long>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphNodeIndicesNullUnaryPredicate()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        Map<Node<String, Integer>, Long> nodeIndices = new HashMap<Node<String, Integer>, Long>();
        nodeIndices.put(foo, 0L);
        nodeIndices.put(bar, 1L);
        nodeIndices.put(baz, 2L);
        toBitMatrix(graph, nodeIndices, null);
    }

    @Test
    public void testToBitMatrixGraphNodeIndices()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        Map<Node<String, Integer>, Long> nodeIndices = new HashMap<Node<String, Integer>, Long>();
        nodeIndices.put(foo, 0L);
        nodeIndices.put(bar, 1L);
        nodeIndices.put(baz, 2L);
        BitMatrix2D bitMatrix = toBitMatrix(graph, nodeIndices, acceptAll);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }


    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphUnaryFunctionNullGraph()
    {
        toBitMatrix((Graph<String, Integer>) null, nodeIndicesFn, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphUnaryFunctionNullUnaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        toBitMatrix(graph, (UnaryFunction<Node<String, Integer>, Long>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixGraphUnaryFunctionNullUnaryPredicate()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        toBitMatrix(graph, nodeIndicesFn, null);
    }

    @Test
    public void testToBitMatrixGraphUnaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        BitMatrix2D bitMatrix = toBitMatrix(graph, nodeIndicesFn, acceptAll);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixArrayTableNullArrayTable()
    {
        toBitMatrix((ArrayTable<String, String, Integer>) null);
    }

    @Test
    public void testToBitMatrixArrayTable()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        BitMatrix2D bitMatrix = toBitMatrix(table);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixArrayTableUnaryPredicateNullArrayTable()
    {
        toBitMatrix((ArrayTable<String, String, Integer>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixArrayTableUnaryPredicateNullUnaryPredicate()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        toBitMatrix(table, (UnaryPredicate<Integer>) null);
    }

    @Test
    public void testToBitMatrixArrayTableUnaryPredicate()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        BitMatrix2D bitMatrix = toBitMatrix(table, acceptNonNull);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }


    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableNullTable()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        toBitMatrix((Table<String, String, Integer>) null, keys, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableNullList()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        toBitMatrix(table, (List<String>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableNullUnaryPredicate()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        toBitMatrix(table, keys, (UnaryPredicate<Integer>) null);
    }

    @Test
    public void testToBitMatrixTable()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        BitMatrix2D bitMatrix = toBitMatrix(table, keys, acceptNonNull);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }


    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableKeyIndicesNullTable()
    {
        Map<String, Long> keyIndices = new HashMap<String, Long>();
        keyIndices.put("foo", 0L);
        keyIndices.put("bar", 1L);
        keyIndices.put("baz", 2L);
        toBitMatrix((Table<String, String, Integer>) null, keyIndices, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableKeyIndicesNullMap()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        toBitMatrix(table, (Map<String, Long>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableKeyIndicesNullUnaryPredicate()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        Map<String, Long> keyIndices = new HashMap<String, Long>();
        keyIndices.put("foo", 0L);
        keyIndices.put("bar", 1L);
        keyIndices.put("baz", 2L);
        toBitMatrix(table, keyIndices, (UnaryPredicate<Integer>) null);
    }

    @Test
    public void testToBitMatrixTableKeyIndices()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        Map<String, Long> keyIndices = new HashMap<String, Long>();
        keyIndices.put("foo", 0L);
        keyIndices.put("bar", 1L);
        keyIndices.put("baz", 2L);
        BitMatrix2D bitMatrix = toBitMatrix(table, keyIndices, acceptNonNull);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }


    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableUnaryFunctionNullTable()
    {
        toBitMatrix((Table<String, String, Integer>) null, indices, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableUnaryFunctionNullUnaryFunction()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        toBitMatrix(table, (UnaryFunction<String, Long>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixTableUnaryFunctionNullUnaryPredicate()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        toBitMatrix(table, indices, (UnaryPredicate<Integer>) null);
    }

    @Test
    public void testToBitMatrixTableUnaryFunction()
    {
        List<String> keys = ImmutableList.of("foo", "bar", "baz");
        ArrayTable<String, String, Integer> table = ArrayTable.create(keys, keys);
        table.put("foo", "bar", 42);
        BitMatrix2D bitMatrix = toBitMatrix(table, indices, acceptNonNull);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixMatrixNullMatrix()
    {
        toBitMatrix((Matrix2D<Integer>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToBitMatrixMatrixNullUnaryPredicate()
    {
        Matrix2D<Integer> matrix = createSparseMatrix2D(4, 4);
        matrix.set(0L, 1L, 42);
        toBitMatrix(matrix, (UnaryPredicate<Integer>) null);
    }

    @Test
    public void testToBitMatrixMatrix()
    {
        Matrix2D<Integer> matrix = createSparseMatrix2D(4, 4);
        matrix.set(0L, 1L, 42);
        BitMatrix2D bitMatrix = toBitMatrix(matrix, acceptNonNull);
        assertEquals(1, bitMatrix.cardinality());
        assertTrue(bitMatrix.getQuick(0, 1));
        assertFalse(bitMatrix.getQuick(0, 2));
    }

    // --> table

    @Test(expected=IllegalArgumentException.class)
    public void testToImmutableTableNullBinaryKeyMap()
    {
        toImmutableTable((BinaryKeyMap<String, String, Integer>) null);
    }

    @Test
    public void testToImmutableTable()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        ImmutableTable<String, String, Integer> table = toImmutableTable(binaryKeyMap);
        assertEquals(1, table.size());
        assertEquals(Integer.valueOf(42), table.get("foo", "bar"));
        assertNull(table.get("foo", "baz"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToImmutableTableGraphNullGraph()
    {
        toImmutableTable((Graph<String, Integer>) null);
    }

    @Test
    public void testToImmutableTableGraph()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        ImmutableTable<String, String, Integer> table = toImmutableTable(graph);
        assertEquals(1, table.size());
        assertEquals(Integer.valueOf(42), table.get("foo", "bar"));
        assertNull(table.get("foo", "baz"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToHashBasedTableNullBinaryKeyMap()
    {
        toHashBasedTable((BinaryKeyMap<String, String, Integer>) null);
    }

    @Test
    public void testToHashBasedTable()
    {
        BinaryKeyMap<String, String, Integer> binaryKeyMap = BinaryKeyMaps.createBinaryKeyMap();
        binaryKeyMap.put("foo", "bar", 42);
        HashBasedTable<String, String, Integer> table = toHashBasedTable(binaryKeyMap);
        assertEquals(1, table.size());
        assertEquals(Integer.valueOf(42), table.get("foo", "bar"));
        assertNull(table.get("foo", "baz"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToHashBasedTableGraphNullGraph()
    {
        toHashBasedTable((Graph<String, Integer>) null);
    }

    @Test
    public void testToHashBasedTableGraph()
    {
        Graph<String, Integer> graph = createGraph();
        Node<String, Integer> foo = graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> baz = graph.createNode("baz");
        graph.createEdge(foo, bar, 42);
        HashBasedTable<String, String, Integer> table = toHashBasedTable(graph);
        assertEquals(1, table.size());
        assertEquals(Integer.valueOf(42), table.get("foo", "bar"));
        assertNull(table.get("foo", "baz"));
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
        toGraph(matrix, (UnaryFunction<Long, String>) null);
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

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphTableNullTable()
    {
        toGraph((Table<String, String, Integer>) null);
    }

    @Test
    public void testToGraphTable()
    {
        ImmutableTable<String, String, Integer> table = new ImmutableTable.Builder<String, String, Integer>()
            .put("foo", "bar", 42)
            .put("baz", "foo", 24)
            .build();

        Graph<String, Integer> graph = toGraph(table);
        assertEquals(3, graph.nodeCount());
        assertEquals(2, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            if ("foo".equals(edge.source().getValue()))
            {
                assertEquals(Integer.valueOf(42), edge.getValue());
            }
            else if ("baz".equals(edge.source().getValue()))
            {
                assertEquals(Integer.valueOf(24), edge.getValue());
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphTableUnaryPredicateNullTable()
    {
        toGraph((Table<String, String, Integer>) null, acceptAll);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphTableUnaryPredicateNullUnaryPredicate()
    {
        ImmutableTable<String, String, Integer> table = new ImmutableTable.Builder<String, String, Integer>()
            .put("foo", "bar", 42)
            .put("baz", "foo", 24)
            .build();

        toGraph(table, (UnaryPredicate<Integer>) null);
    }

    @Test
    public void testToGraphTableUnaryPredicate()
    {
        ImmutableTable<String, String, Integer> table = new ImmutableTable.Builder<String, String, Integer>()
            .put("foo", "bar", 42)
            .put("baz", "foo", 24)
            .build();

        Graph<String, Integer> graph = toGraph(table, acceptAll);
        assertEquals(3, graph.nodeCount());
        assertEquals(2, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            if ("foo".equals(edge.source().getValue()))
            {
                assertEquals(Integer.valueOf(42), edge.getValue());
            }
            else if ("baz".equals(edge.source().getValue()))
            {
                assertEquals(Integer.valueOf(24), edge.getValue());
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphBitMatrixNullBitMatrix()
    {
        BinaryFunction<Long, Long, Integer> edgeValues = new BinaryFunction<Long, Long, Integer>()
            {
                @Override
                public Integer evaluate(final Long source, final Long target)
                {
                    if (0L == source.longValue() && 1L == target.longValue())
                    {
                        return 42;
                    }
                    return null;
                }
            };
        toGraph((BitMatrix2D) null, edgeValues);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphBitMatrixNullBinaryFunction()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(3, 3);
        bitMatrix.set(0L, 1L, true);
        toGraph(bitMatrix, (BinaryFunction<Long, Long, Integer>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphBitMatrixUnbalancedBitMatrix()
    {
        BinaryFunction<Long, Long, Integer> edgeValues = new BinaryFunction<Long, Long, Integer>()
            {
                @Override
                public Integer evaluate(final Long source, final Long target)
                {
                    if (0L == source.longValue() && 1L == target.longValue())
                    {
                        return 42;
                    }
                    return null;
                }
            };

        BitMatrix2D bitMatrix = new BitMatrix2D(3, 4);
        bitMatrix.set(0L, 1L, true);
        toGraph(bitMatrix, edgeValues);
    }

    @Test
    public void testToGraphBitMatrix()
    {
        BinaryFunction<Long, Long, Integer> edgeValues = new BinaryFunction<Long, Long, Integer>()
            {
                @Override
                public Integer evaluate(final Long source, final Long target)
                {
                    if (0L == source.longValue() && 1L == target.longValue())
                    {
                        return 42;
                    }
                    return null;
                }
            };

        BitMatrix2D bitMatrix = new BitMatrix2D(3, 3);
        bitMatrix.set(0L, 1L, true);
        Graph<Long, Integer> graph = toGraph(bitMatrix, edgeValues);
        assertEquals(2, graph.nodeCount()); // won't create node for idx 2L since there aren't any true values for it
        assertEquals(1, graph.edgeCount());
        assertEquals(Integer.valueOf(42), graph.edgeValues().iterator().next());
    }


    @Test(expected=IllegalArgumentException.class)
    public void testToGraphBitMatrixUnaryFunctionNullBitMatrix()
    {
        UnaryFunction<Long, String> nodeValues = new UnaryFunction<Long, String>()
            {
                @Override
                public String evaluate(final Long value)
                {
                    return String.valueOf(value);
                }
            };

        toGraph((BitMatrix2D) null, nodeValues, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphBitMatrixUnaryFunctionNullUnaryFunction()
    {
        BitMatrix2D bitMatrix = new BitMatrix2D(3, 3);
        bitMatrix.set(0L, 1L, true);
        toGraph(bitMatrix, (UnaryFunction<Long, String>) null, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphBitMatrixUnaryFunctionNullBinaryFunction()
    {
        UnaryFunction<Long, String> nodeValues = new UnaryFunction<Long, String>()
            {
                @Override
                public String evaluate(final Long value)
                {
                    return String.valueOf(value);
                }
            };

        BitMatrix2D bitMatrix = new BitMatrix2D(3, 3);
        bitMatrix.set(0L, 1L, true);
        toGraph(bitMatrix, nodeValues, (BinaryFunction<String, String, Integer>) null);
    }

    @Test
    public void testToGraphBitMatrixUnaryFunction()
    {
        UnaryFunction<Long, String> nodeValues = new UnaryFunction<Long, String>()
            {
                @Override
                public String evaluate(final Long value)
                {
                    if (0L == value.longValue())
                    {
                        return "foo";
                    }
                    else if (1L == value.longValue())
                    {
                        return "bar";
                    }
                    else if (2L == value.longValue())
                    {
                        return "garply";
                    }
                    return null;
                }
            };

        BitMatrix2D bitMatrix = new BitMatrix2D(3, 3);
        bitMatrix.set(0L, 1L, true);
        bitMatrix.set(2L, 0L, true);

        Graph<String, Integer> graph = toGraph(bitMatrix, nodeValues, length);
        assertEquals(3, graph.nodeCount());
        assertEquals(2, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            if ("foo".equals(edge.source().getValue()))
            {
                assertEquals(Integer.valueOf(6), edge.getValue());
            }
            else if ("garply".equals(edge.source().getValue()))
            {
                assertEquals(Integer.valueOf(9), edge.getValue());
            }
        }        
    }

    // --> sparse matrix

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullBinaryKeyMap()
    {
        List<String> emptyKeys = emptyList();
        toSparseMatrix2D((BinaryKeyMap<String, String, Double>) null, emptyKeys);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullKeys()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        toSparseMatrix2D(binaryKeyMap, (List<String>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixWithKeyIndicesNullBinaryKeyMap()
    {
        toSparseMatrix2D((BinaryKeyMap<String, String, Double>) null, new UnaryFunction<String, Long>()
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
        toSparseMatrix2D(binaryKeyMap, (Map<String, Long>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullKeyIndices()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        toSparseMatrix2D(binaryKeyMap, (UnaryFunction<String, Long>) null);
    }

    @Test
    public void testToSparseMatrixEmptyBinaryKeyMap()
    {
        BinaryKeyMap<String, String, Double> binaryKeyMap = createBinaryKeyMap();
        List<String> keys = emptyList();
        Matrix2D<Double> matrix = toSparseMatrix2D(binaryKeyMap, keys);
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
        Matrix2D<Double> matrix = toSparseMatrix2D(binaryKeyMap, keys);
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
        Matrix2D<Double> matrix = toSparseMatrix2D(binaryKeyMap, keys);
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

        Matrix2D<Double> matrix = toSparseMatrix2D(binaryKeyMap, keyIndices);
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

        Matrix2D<Double> matrix = toSparseMatrix2D(binaryKeyMap, keyIndices);
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
        toSparseMatrix2D((Graph<String, Double>) null, emptyNodes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullNodes()
    {
        Graph<String, Double> graph = createGraph();
        toSparseMatrix2D(graph, (List<Node<String, Double>>) null);
    }

    @Test
    public void testToSparseMatrixEmptyGraph()
    {
        Graph<String, Double> graph = createGraph();
        List<Node<String, Double>> emptyNodes = emptyList();
        Matrix2D<Double> matrix = toSparseMatrix2D(graph, emptyNodes);
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
        Matrix2D<Double> matrix = toSparseMatrix2D(graph, asList(node0, node1));
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
        Matrix2D<Double> matrix = toSparseMatrix2D(graph, asList(node0, node1));
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

        Matrix2D<Double> matrix = toSparseMatrix2D(graph, asList(node0, node1));
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
        toSparseMatrix2D(graph, (Map<Node<String, Double>, Long>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullNodeIndices()
    {
        Graph<String, Double> graph = createGraph();
        toSparseMatrix2D(graph, (UnaryFunction<Node<String, Double>, Long>) null);
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

        Matrix2D<Double> matrix = toSparseMatrix2D(graph, nodeIndices);
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

        Matrix2D<Double> matrix = toSparseMatrix2D(graph, nodeIndices);
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(2, matrix.cardinality());
        assertEquals(1.0d, matrix.get(0, 1), TOLERANCE);
        assertEquals(2.0d, matrix.get(1, 0), TOLERANCE);
        assertFalse(matrix.isEmpty());
    }
}
