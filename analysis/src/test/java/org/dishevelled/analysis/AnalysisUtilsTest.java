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

import java.util.Collections;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.dishevelled.analysis.AnalysisUtils.*;

import static org.dishevelled.collect.Lists.*;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.matrix.Matrix2D;

import static org.dishevelled.graph.impl.GraphUtils.createGraph;

import static org.dishevelled.matrix.impl.SparseMatrixUtils.createSparseMatrix2D;

/**
 * Unit test for AnalysisUtils.
 */
public final class AnalysisUtilsTest
{
    private static final double TOLERANCE = 0.001d;

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullGraph()
    {
        toSparseMatrix((Graph<String, Double>) null, Collections.<Node<String, Double>>emptyList());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToSparseMatrixNullNodes()
    {
        Graph<String, Double> graph = createGraph();
        toSparseMatrix(graph, null);
    }

    @Test
    public void testToSparseMatrixEmptyGraph()
    {
        Graph<String, Double> graph = createGraph();
        Matrix2D<Double> matrix = toSparseMatrix(graph, Collections.<Node<String, Double>>emptyList());
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
    public void testToSparseMatrixNoEdgesOnlyOneNodeInList()
    {
        Graph<String, Double> graph = createGraph();
        Node<String, Double> node0 = graph.createNode("node0");
        Node<String, Double> node1 = graph.createNode("node1");
        Matrix2D<Double> matrix = toSparseMatrix(graph, asList(node1));
        assertEquals(1, matrix.rows());
        assertEquals(1, matrix.columns());
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

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphNullMatrix()
    {
        toGraph(null);
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
    public void testToGraphMatrixTooLargeRows()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(Integer.MAX_VALUE + 1L, 20, 20, 0.75f);
        toGraph(matrix);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testToGraphMatrixTooLargeColumns()
    {
        Matrix2D<Double> matrix = createSparseMatrix2D(20, Integer.MAX_VALUE + 1L, 20, 0.75f);
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
}