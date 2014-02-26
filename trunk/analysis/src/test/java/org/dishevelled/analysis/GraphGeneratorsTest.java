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

import static org.dishevelled.analysis.GraphGenerators.connectCompletely;
import static org.dishevelled.analysis.GraphGenerators.connectPreferentially;
import static org.dishevelled.analysis.GraphGenerators.connectRandomly;

import static org.dishevelled.graph.impl.GraphUtils.createGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

import org.apache.commons.math.random.JDKRandomGenerator;
import org.apache.commons.math.random.RandomGenerator;

import org.junit.Test;

import org.dishevelled.functor.BinaryFunction;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

/**
 * Unit test for GraphGenerators.
 */
public final class GraphGeneratorsTest
{
    private static final double TOLERANCE = 0.001d;

    private RandomGenerator randomGenerator = new JDKRandomGenerator();

    private BinaryFunction<String, String, Integer> length = new BinaryFunction<String, String, Integer>()
        {
            @Override
            public Integer evaluate(final String source, final String target)
            {
                return source.length() + target.length();
            }
        };

    @Test(expected=IllegalArgumentException.class)
    public void testConnectCompletelyNullGraph()
    {
        connectCompletely((Graph<String, Integer>) null, 42);
    }

    @Test
    public void testConnectCompletely()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("baz");
        connectCompletely(graph, 42);
        assertEquals(6, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectCompletelyBinaryFunctionNullGraph()
    {
        connectCompletely((Graph<String, Integer>) null, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectCompletelyBinaryFunctionNullBinaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        connectCompletely(graph, (BinaryFunction<String, String, Integer>) null);
    }

    @Test
    public void testConnectCompletelyBinaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectCompletely(graph, length);
        assertEquals(6, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test
    public void testConnectCompletelyBinaryFunctionExistingEdges()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> garply = graph.createNode("garply");
        graph.createEdge(bar, garply, 9);
        connectCompletely(graph, length);
        assertEquals(6, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectCompletelyNullNodeValues()
    {
        connectCompletely((List<String>) null, 42);
    }

    @Test
    public void testConnectCompletelyNodeValues()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "baz");
        Graph<String, Integer> graph = connectCompletely(nodeValues, 42);
        assertEquals(6, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectCompletelyNodeValuesBinaryFunctionNullNodeValues()
    {
        connectCompletely((List<String>) null, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectCompletelyNodeValuesBinaryFunctionNullBinaryFunction()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "baz");
        connectCompletely(nodeValues, null);
    }

    @Test
    public void testConnectCompletelyNodeValuesBinaryFunction()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectCompletely(nodeValues, length);
        assertEquals(6, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNullGraph()
    {
        connectRandomly((Graph<String, Integer>) null, 10, 42);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNegativeEdgeCount()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, -1, 42);
    }

    @Test
    public void testConnectRandomly()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, 42);
        assertEquals(4, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyRandomGeneratorNullGraph()
    {
        connectRandomly((Graph<String, Integer>) null, 4, 42, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyRandomGeneratorNegativeEdgeCount()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, -1, 42, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyRandomGeneratorNullRandomGenerator()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, 42, null);
    }

    @Test
    public void testConnectRandomlyRandomGenerator()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, 42, randomGenerator);
        assertEquals(4, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyBinaryFunctionNullGraph()
    {
        connectRandomly((Graph<String, Integer>) null, 4, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyBinaryFunctionNegativeEdgeCount()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, -1, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyBinaryFunctionNullBinaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, (BinaryFunction<String, String, Integer>) null);
    }

    @Test
    public void testConnectRandomlyBinaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, length);
        assertEquals(4, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyBinaryFunctionRandomGeneratorNullGraph()
    {
        connectRandomly((Graph<String, Integer>) null, 4, length, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyBinaryFunctionRandomGeneratorNegativeEdgeCount()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, -1, length, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyBinaryFunctionRandomGeneratorNullBinaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, (BinaryFunction<String, String, Integer>) null, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyBinaryFunctionRandomGeneratorNullRandomGenerator()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, length, null);
    }

    @Test
    public void testConnectRandomlyBinaryFunctionRandomGenerator()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectRandomly(graph, 4, length, randomGenerator);
        assertEquals(4, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesNullNodeValues()
    {
        connectRandomly((List<String>) null, 4, 42);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesNegativeEdgeCount()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectRandomly(nodeValues, -1, 42);
    }

    @Test
    public void testConnectRandomlyNodeValues()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectRandomly(nodeValues, 4, 42);
        assertEquals(4, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesRandomGeneratorNullNodeValues()
    {
        connectRandomly((List<String>) null, 4, 42, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesRandomGeneratorNegativeEdgeCount()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectRandomly(nodeValues, -1, 42, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesRandomGeneratorNullRandomGenerator()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectRandomly(nodeValues, 4, 42, null);
    }

    @Test
    public void testConnectRandomlyNodeValuesRandomGenerator()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectRandomly(nodeValues, 4, 42);
        assertEquals(4, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesBinaryFunctionNullNodeValues()
    {
        connectRandomly((List<String>) null, 4, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesBinaryFunctionNegativeEdgeCount()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectRandomly(nodeValues, -1, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesBinaryFunctionNullBinaryFunction()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectRandomly(nodeValues, 4, null);
    }

    @Test
    public void testConnectRandomlyNodeValuesBinaryFunction()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectRandomly(nodeValues, 4, length);
        assertEquals(4, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesBinaryFunctionRandomGeneratorNullNodeValues()
    {
        connectRandomly((List<String>) null, 4, length, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesBinaryFunctionRandomGeneratorNegativeEdgeCount()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectRandomly(nodeValues, -1, length, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesBinaryFunctionRandomGeneratorNullBinaryFunction()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectRandomly(nodeValues, 4, null, randomGenerator);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectRandomlyNodeValuesBinaryFunctionRandomGeneratorNullRandomGenerator()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectRandomly(nodeValues, 4, length, null);
    }

    @Test
    public void testConnectRandomlyNodeValuesBinaryFunctionRandomGenerator()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectRandomly(nodeValues, 4, length, randomGenerator);
        assertEquals(4, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyNullGraph()
    {
        connectPreferentially((Graph<String, Integer>) null, 4, 42);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyNegativeEdgeCount()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectPreferentially(graph, -1, 42);
    }

    @Test
    public void testConnectPreferentially()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectPreferentially(graph, 4, 42);
        assertEquals(4, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyBinaryFunctionNullGraph()
    {
        connectPreferentially((Graph<String, Integer>) null, 4, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyBinaryFunctionNegativeEdgeCount()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectPreferentially(graph, -1, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyBinaryFunctionNullBinaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectPreferentially(graph, 4, (BinaryFunction<String, String, Integer>) null);
    }

    @Test
    public void testConnectPreferentiallyBinaryFunction()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        graph.createNode("bar");
        graph.createNode("garply");
        connectPreferentially(graph, 4, length);
        assertEquals(4, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test
    public void testConnectPreferentiallyBinaryFunctionExistingEdges()
    {
        Graph<String, Integer> graph = createGraph();
        graph.createNode("foo");
        Node<String, Integer> bar = graph.createNode("bar");
        Node<String, Integer> garply = graph.createNode("garply");
        graph.createEdge(bar, garply, 9);
        connectPreferentially(graph, 4, length);
        assertEquals(4, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyNodeValuesNullNodeValues()
    {
        connectPreferentially((List<String>) null, 4, 42);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyNodeValuesNegativeEdgeCount()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectPreferentially(nodeValues, -1, 42);
    }

    @Test
    public void testConnectPreferentiallyNodeValues()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectPreferentially(nodeValues, 4, 42);
        assertEquals(4, graph.edgeCount());
        for (Integer edgeValue : graph.edgeValues())
        {
            assertEquals(Integer.valueOf(42), edgeValue);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyNodeValuesBinaryFunctionNullNodeValues()
    {
        connectPreferentially((List<String>) null, 4, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyNodeValuesBinaryFunctionNegativeEdgeCount()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectPreferentially(nodeValues, -1, length);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConnectPreferentiallyNodeValuesBinaryFunctionNullBinaryFunction()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        connectPreferentially(nodeValues, 4, null);
    }

    @Test
    public void testConnectPreferentiallyNodeValuesBinaryFunction()
    {
        List<String> nodeValues = ImmutableList.of("foo", "bar", "garply");
        Graph<String, Integer> graph = connectPreferentially(nodeValues, 4, length);
        assertEquals(4, graph.edgeCount());
        for (Edge<String, Integer> edge : graph.edges())
        {
            assertEquals(edge.source().getValue().length() + edge.target().getValue().length(), edge.getValue().intValue());
        }
    }
}
