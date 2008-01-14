/*

    dsh-graph  Directed graph interface and implementation.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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
package org.dishevelled.graph;

import junit.framework.TestCase;

import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;

/**
 * Abstract unit test for implementations of Graph.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractGraphTest
    extends TestCase
{

    /**
     * Create and return a new empty instance of an implementation of Graph to test.
     *
     * @return a new empty instance of an implementation of Graph to test
     */
    protected abstract <N, E> Graph<N, E> createEmptyGraph();

    /**
     * Create and return a new full instance of an implementation of Graph to test.  The graph
     * should have at least two nodes with the specified node value and one edge with
     * the specified edge value.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param nodeValue prototype node value
     * @param edgeValue prototype edge value
     * @return a new full instance of an implementation of Graph to test
     */
    protected abstract <N, E> Graph<N, E> createFullGraph(N nodeValue, E edgeValue);

    public void testCreateEmptyGraph()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph);
    }

    public void testCreateFullGraph()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph);
    }

    public void testNodeCount()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertEquals(0, emptyGraph.nodeCount());
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertTrue(fullGraph.nodeCount() > 0);
    }

    public void testNodes()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.nodes());
        assertTrue(emptyGraph.nodes().isEmpty());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodes().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.nodes());
        assertFalse(fullGraph.nodes().isEmpty());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodes().size());

        for (Node<String, String> node : fullGraph.nodes())
        {
            assertNotNull(node);
            assertEquals("node", node.getValue());
        }
    }

    public void testNodesIsImmutable()
    {
        // TODO
    }

    public void testNodeValues()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.nodeValues());
        assertTrue(emptyGraph.nodeValues().isEmpty());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeValues().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.nodeValues());
        assertFalse(fullGraph.nodeValues().isEmpty());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeValues().size());

        for (String nodeValue : fullGraph.nodeValues())
        {
            assertEquals("node", nodeValue);
        }
    }

    public void testNodeValuesIsImmutable()
    {
        // TODO
    }

    public void testNodeMap()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.nodeMap());
        assertTrue(emptyGraph.nodeMap().isEmpty());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap().size());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap().keySet().size());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap().entrySet().size());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap().values().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.nodeMap());
        assertFalse(fullGraph.nodeMap().isEmpty());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap().size());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap().keySet().size());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap().entrySet().size());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap().values().size());

        for (Node<String, String> node : fullGraph.nodes())
        {
            assertTrue(fullGraph.nodeMap().containsKey(node));
        }
    }

    // TODO: addl keyMap test methods:  is immutable, keySet is immutable, etc.

    public void testForEachNode()
    {
        UnaryProcedure<Node<String, String>> procedure = new UnaryProcedure<Node<String, String>>()
            {
                /** {@inheritDoc} */
                public void run(final Node<String, String> node)
                {
                    // empty
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachNode(procedure);
        try
        {
            emptyGraph.forEachNode(null);
            fail("forEachNode(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachNode(procedure);
        try
        {
            fullGraph.forEachNode(null);
            fail("fullGraph forEachNode(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testForEachNodeWithPredicate()
    {
        UnaryProcedure<Node<String, String>> procedure = new UnaryProcedure<Node<String, String>>()
            {
                /** {@inheritDoc} */
                public void run(final Node<String, String> node)
                {
                    // empty
                }
            };
        UnaryPredicate<Node<String, String>> predicate = new UnaryPredicate<Node<String, String>>()
            {
                /** {@inheritDoc} */
                public boolean test(final Node<String, String> node)
                {
                    return true;
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachNode(predicate, procedure);
        try
        {
            emptyGraph.forEachNode(predicate, null);
            fail("forEachNode(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachNode(null, procedure);
            fail("forEachNode(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachNode(null, null);
            fail("forEachNode(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachNode(predicate, procedure);
        try
        {
            fullGraph.forEachNode(predicate, null);
            fail("fullGraph forEachNode(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachNode(null, procedure);
            fail("fullGraph forEachNode(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachNode(null, null);
            fail("fullGraph forEachNode(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testForEachNodeValue()
    {
        UnaryProcedure<String> procedure = new UnaryProcedure<String>()
            {
                /** {@inheritDoc} */
                public void run(final String nodeValue)
                {
                    // empty
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachNodeValue(procedure);
        try
        {
            emptyGraph.forEachNodeValue(null);
            fail("forEachNodeValue(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachNodeValue(procedure);
        try
        {
            fullGraph.forEachNodeValue(null);
            fail("fullGraph forEachNodeValue(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testForEachNodeValueWithPredicate()
    {
        UnaryProcedure<String> procedure = new UnaryProcedure<String>()
            {
                /** {@inheritDoc} */
                public void run(final String nodeValue)
                {
                    // empty
                }
            };
        UnaryPredicate<String> predicate = new UnaryPredicate<String>()
            {
                /** {@inheritDoc} */
                public boolean test(final String nodeValue)
                {
                    return true;
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachNodeValue(predicate, procedure);
        try
        {
            emptyGraph.forEachNodeValue(predicate, null);
            fail("forEachNodeValue(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachNodeValue(null, procedure);
            fail("forEachNodeValue(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachNodeValue(null, null);
            fail("forEachNodeValue(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachNodeValue(predicate, procedure);
        try
        {
            fullGraph.forEachNodeValue(predicate, null);
            fail("fullGraph forEachNodeValue(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachNodeValue(null, procedure);
            fail("fullGraph forEachNodeValue(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachNodeValue(null, null);
            fail("fullGraph forEachNodeValue(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testEdgeCount()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertEquals(0, emptyGraph.edgeCount());
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertTrue(fullGraph.edgeCount() > 0);
    }

    public void testEdges()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.edges());
        assertTrue(emptyGraph.edges().isEmpty());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edges().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.edges());
        assertFalse(fullGraph.edges().isEmpty());
        assertEquals(fullGraph.edgeCount(), fullGraph.edges().size());

        for (Edge<String, String> edge : fullGraph.edges())
        {
            assertNotNull(edge);
            assertEquals("edge", edge.getValue());
        }
    }

    public void testEdgesIsImmutable()
    {
        // TODO
    }

    public void testEdgeValues()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.edgeValues());
        assertTrue(emptyGraph.edgeValues().isEmpty());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeValues().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.edgeValues());
        assertFalse(fullGraph.edgeValues().isEmpty());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeValues().size());

        for (String value : fullGraph.edgeValues())
        {
            assertEquals("edge", value);
        }
    }

    public void testEdgeValuesIsImmutable()
    {
        // TODO
    }

    public void testEdgeMap()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.edgeMap());
        assertTrue(emptyGraph.edgeMap().isEmpty());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap().size());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap().keySet().size());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap().entrySet().size());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap().values().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.edgeMap());
        assertFalse(fullGraph.edgeMap().isEmpty());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap().size());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap().keySet().size());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap().entrySet().size());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap().values().size());

        for (Edge<String, String> edge : fullGraph.edges())
        {
            assertTrue(fullGraph.edgeMap().containsKey(edge));
        }
    }

    // TODO: addl keyMap test methods:  is immutable, keySet is immutable, etc.

    public void testForEachEdge()
    {
        UnaryProcedure<Edge<String, String>> procedure = new UnaryProcedure<Edge<String, String>>()
            {
                /** {@inheritDoc} */
                public void run(final Edge<String, String> edge)
                {
                    // empty
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachEdge(procedure);
        try
        {
            emptyGraph.forEachEdge(null);
            fail("forEachEdge(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachEdge(procedure);
        try
        {
            fullGraph.forEachEdge(null);
            fail("fullGraph forEachEdge(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testForEachEdgeWithPredicate()
    {
        UnaryProcedure<Edge<String, String>> procedure = new UnaryProcedure<Edge<String, String>>()
            {
                /** {@inheritDoc} */
                public void run(final Edge<String, String> edge)
                {
                    // empty
                }
            };
        UnaryPredicate<Edge<String, String>> predicate = new UnaryPredicate<Edge<String, String>>()
            {
                /** {@inheritDoc} */
                public boolean test(final Edge<String, String> edge)
                {
                    return true;
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachEdge(predicate, procedure);
        try
        {
            emptyGraph.forEachEdge(predicate, null);
            fail("forEachEdge(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachEdge(null, procedure);
            fail("forEachEdge(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachEdge(null, null);
            fail("forEachEdge(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachEdge(predicate, procedure);
        try
        {
            fullGraph.forEachEdge(predicate, null);
            fail("fullGraph forEachEdge(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachEdge(null, procedure);
            fail("fullGraph forEachEdge(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachEdge(null, null);
            fail("fullGraph forEachEdge(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testForEachEdgeValue()
    {
        UnaryProcedure<String> procedure = new UnaryProcedure<String>()
            {
                /** {@inheritDoc} */
                public void run(final String edgeValue)
                {
                    // empty
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachEdgeValue(procedure);
        try
        {
            emptyGraph.forEachEdgeValue(null);
            fail("forEachEdgeValue(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachEdgeValue(procedure);
        try
        {
            fullGraph.forEachEdgeValue(null);
            fail("fullGraph forEachEdgeValue(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testForEachEdgeValueWithPredicate()
    {
        UnaryProcedure<String> procedure = new UnaryProcedure<String>()
            {
                /** {@inheritDoc} */
                public void run(final String edgeValue)
                {
                    // empty
                }
            };
        UnaryPredicate<String> predicate = new UnaryPredicate<String>()
            {
                /** {@inheritDoc} */
                public boolean test(final String edgeValue)
                {
                    return true;
                }
            };

        Graph<String, String> emptyGraph = createEmptyGraph();
        emptyGraph.forEachEdgeValue(predicate, procedure);
        try
        {
            emptyGraph.forEachEdgeValue(predicate, null);
            fail("forEachEdgeValue(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachEdgeValue(null, procedure);
            fail("forEachEdgeValue(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            emptyGraph.forEachEdgeValue(null, null);
            fail("forEachEdgeValue(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        fullGraph.forEachEdgeValue(predicate, procedure);
        try
        {
            fullGraph.forEachEdgeValue(predicate, null);
            fail("fullGraph forEachEdgeValue(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachEdgeValue(null, procedure);
            fail("fullGraph forEachEdgeValue(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            fullGraph.forEachEdgeValue(null, null);
            fail("fullGraph forEachEdgeValue(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testClear()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        try
        {
            emptyGraph.clear();
            assertEquals(0, emptyGraph.nodeCount());
            assertEquals(0, emptyGraph.edgeCount());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        try
        {
            fullGraph.clear();
            assertEquals(0, fullGraph.nodeCount());
            assertEquals(0, fullGraph.edgeCount());
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
    }

    public void testCreateNode()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        try
        {
            Node<String, String> node0 = emptyGraph.createNode(null);
            assertNotNull(node0);
            assertEquals(null, node0.getValue());
            assertEquals(1, emptyGraph.nodeCount());
            assertTrue(emptyGraph.nodes().contains(node0));
        }
        catch (IllegalArgumentException e)
        {
            // ok, implementations may throw IAE for null
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        try
        {
            Node<String, String> node1 = emptyGraph.createNode("node1");
            assertNotNull(node1);
            assertEquals("node1", node1.getValue());
            assertEquals(2, emptyGraph.nodeCount());
            assertTrue(emptyGraph.nodes().contains(node1));
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
    }

    public void testRemoveNode()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Node<String, String> node = fullGraph.nodes().iterator().next();
        int nodeCount = fullGraph.nodeCount();
        assertTrue(fullGraph.nodes().contains(node));
        try
        {            
            fullGraph.remove(node);
            assertEquals((nodeCount - 1), fullGraph.nodeCount());
            assertFalse(fullGraph.nodes().contains(node));
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }

        try
        {
            fullGraph.remove((Node<String, String>) null);
            fail("remove((Node) null) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // TODO:  test node not in graph
    }

    public void testCreateEdge()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        try
        {
            Node<String, String> node0 = emptyGraph.createNode("node0");
            Node<String, String> node1 = emptyGraph.createNode("node1");
            Edge<String, String> edge = emptyGraph.createEdge(node0, node1, null);
            assertNotNull(edge);
            assertEquals(node0, edge.source());
            assertEquals(node1, edge.target());
            assertEquals(null, edge.getValue());
            assertEquals(1, emptyGraph.edgeCount());
            assertTrue(emptyGraph.edges().contains(edge));
            emptyGraph.clear();
        }
        catch (IllegalArgumentException e)
        {
            // ok, implementations may throw IAE for null
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }

        try
        {
            Node<String, String> node0 = emptyGraph.createNode("node0");
            Node<String, String> node1 = emptyGraph.createNode("node1");
            Edge<String, String> edge = emptyGraph.createEdge(node0, node1, "edge");
            assertNotNull(edge);
            assertEquals(node0, edge.source());
            assertEquals(node1, edge.target());
            assertEquals("edge", edge.getValue());
            assertEquals(1, emptyGraph.edgeCount());
            assertTrue(emptyGraph.edges().contains(edge));
            emptyGraph.clear();
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }

        try
        {
            emptyGraph.createEdge(null, null, "edge");
            fail("create(null, null,) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Node<String, String> node = emptyGraph.createNode("node");
            emptyGraph.createEdge(null, node, "edge");
            fail("create(,null,) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Node<String, String> node = emptyGraph.createNode("node");
            emptyGraph.createEdge(node, null, "edge");
            fail("create(null,,) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // TODO:  test nodes not in graph
    }

    public void testRemoveEdge()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Edge<String, String> edge = fullGraph.edges().iterator().next();
        int edgeCount = fullGraph.edgeCount();
        assertTrue(fullGraph.edges().contains(edge));
        try
        {            
            fullGraph.remove(edge);
            assertEquals((edgeCount - 1), fullGraph.edgeCount());
            assertFalse(fullGraph.edges().contains(edge));
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }

        try
        {
            fullGraph.remove((Edge<String, String>) null);
            fail("remove((Edge) null) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // TODO:  test edge not in graph
    }
}