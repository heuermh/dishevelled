/*

    dsh-graph  Directed graph interface and implementation.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
            try
            {
                node.setValue("new value");
                assertEquals("new value", node.getValue());
                node.setValue("node");
            }
            catch (UnsupportedOperationException e)
            {
                // ok, setValue operation is optional
            }
            assertNotNull(node.inEdges());
            assertNotNull(node.outEdges());
            assertEquals(node.degree(), node.inEdges().size() + node.outEdges().size());
        }
    }

    public void testNodesIsImmutable()
    {
        Graph<String, String> graph = createFullGraph("node", "edge");
        Node<String, String> node = graph.nodes().iterator().next();
        Collection<Node<String, String>> fullNodes = Collections.singleton(node);
        try
        {
            graph.nodes().clear();
            fail("graph nodes clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodes().add(node);
            fail("graph nodes add(node) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodes().remove(node);
            fail("graph nodes remove(node) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodes().addAll(fullNodes);
            fail("graph nodes addAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodes().removeAll(fullNodes);
            fail("graph nodes removeAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodes().retainAll(fullNodes);
            fail("graph nodes retainAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<Node<String, String>> nodes = graph.nodes().iterator();
            if (nodes.hasNext())
            {
                nodes.next();
                nodes.remove();
                fail("graph nodes iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
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
        Graph<String, String> graph = createFullGraph("node", "edge");
        String nodeValue = "node";
        Collection<String> fullNodeValues = Collections.singleton(nodeValue);
        try
        {
            graph.nodeValues().clear();
            fail("graph nodeValues clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodeValues().add(nodeValue);
            fail("graph nodeValues add(nodeValue) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodeValues().remove(nodeValue);
            fail("graph nodeValues remove(nodeValue) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodeValues().addAll(fullNodeValues);
            fail("graph nodeValues addAll(fullNodeValues) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodeValues().removeAll(fullNodeValues);
            fail("graph nodeValues removeAll(fullNodeValues) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.nodeValues().retainAll(fullNodeValues);
            fail("graph nodeValues retainAll(fullNodeValues) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<String> nodeValues = graph.nodeValues().iterator();
            if (nodeValues.hasNext())
            {
                nodeValues.next();
                nodeValues.remove();
                fail("graph nodeValues iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testNodeMap()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.nodeMap(null));
        assertTrue(emptyGraph.nodeMap(null).isEmpty());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap(null).size());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap(null).keySet().size());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap(null).entrySet().size());
        assertEquals(emptyGraph.nodeCount(), emptyGraph.nodeMap(null).values().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.nodeMap(null));
        assertFalse(fullGraph.nodeMap(null).isEmpty());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap(null).size());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap(null).keySet().size());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap(null).entrySet().size());
        assertEquals(fullGraph.nodeCount(), fullGraph.nodeMap(null).values().size());

        for (Node<String, String> node : fullGraph.nodes())
        {
            assertTrue(fullGraph.nodeMap(null).containsKey(node));
        }
    }

    public void testEmptyNodeMapKeysAreImmutable()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        Map<Node<String, String>, Integer> nodeMap = emptyGraph.nodeMap(null);
        Set<Node<String, String>> nodeKeys = nodeMap.keySet();
        assertNotNull(nodeKeys);
        assertTrue(nodeKeys.isEmpty());

        Node<String, String> node = null; // null node isn't the best test, but...
        Collection<Node<String, String>> fullNodes = Collections.singleton(node);
        try
        {
            nodeKeys.clear();
            fail("nodeKeys clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.add(node);
            fail("nodeKeys add(node) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.remove(node);
            fail("nodeKeys remove(node) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.addAll(fullNodes);
            fail("nodeKeys addAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.removeAll(fullNodes);
            fail("nodeKeys removeAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.retainAll(fullNodes);
            fail("nodeKeys retainAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<Node<String, String>> nodes = nodeKeys.iterator();
            if (nodes.hasNext())
            {
                nodes.next();
                nodes.remove();
                fail("nodeKeys iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testFullNodeMapKeysAreImmutable()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Node<String, String>, Integer> nodeMap = fullGraph.nodeMap(null);
        Set<Node<String, String>> nodeKeys = nodeMap.keySet();        
        assertNotNull(nodeKeys);
        assertFalse(nodeKeys.isEmpty());
        assertEquals(fullGraph.nodes().size(), nodeKeys.size());

        Node<String, String> node = fullGraph.nodes().iterator().next();
        Collection<Node<String, String>> fullNodes = Collections.singleton(node);
        try
        {
            nodeKeys.clear();
            fail("nodeKeys clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.add(node);
            fail("nodeKeys add(node) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.remove(node);
            fail("nodeKeys remove(node) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.addAll(fullNodes);
            fail("nodeKeys addAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.removeAll(fullNodes);
            fail("nodeKeys removeAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            nodeKeys.retainAll(fullNodes);
            fail("nodeKeys retainAll(fullNodes) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<Node<String, String>> nodes = nodeKeys.iterator();
            if (nodes.hasNext())
            {
                nodes.next();
                nodes.remove();
                fail("nodeKeys iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testNodeMapDefaultValue()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Node<String, String>, Integer> nodeMap0 = fullGraph.nodeMap(null);
        for (Integer value : nodeMap0.values())
        {
            assertEquals(null, value);
        }
        Integer defaultValue = Integer.valueOf(99);
        Map<Node<String, String>, Integer> nodeMap1 = fullGraph.nodeMap(defaultValue);
        for (Integer value : nodeMap1.values())
        {
            assertEquals(defaultValue, value);
        }
    }

    public void testNodeMapPut()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Node<String, String>, Integer> nodeMap = fullGraph.nodeMap(null);
        Node<String, String> node = fullGraph.nodes().iterator().next();
        assertEquals((Integer) null, nodeMap.get(node));
        nodeMap.put(node, 1);
        assertEquals(Integer.valueOf(1), nodeMap.get(node));

        // TODO:  put(not a node) throws IllegalArgumentException; doc in nodeMap method
    }

    public void testNodeMapRemove()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Node<String, String>, Double> nodeMap = fullGraph.nodeMap(null);

        // TODO:  remove(node) sets value to null
        // TODO:  remove(not a node) throws IAE or fails silently; doc in nodeMap method
    }

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

        UnaryProcedure<Object> superProcedure = new UnaryProcedure<Object>()
        {
            /** {@inheritDoc} */
            public void run(final Object nodeValue)
            {
                // empty
            }
        };
        emptyGraph.forEachNodeValue(superProcedure);
        fullGraph.forEachNodeValue(superProcedure);
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
            try
            {
                edge.setValue("new value");
                assertEquals("new value", edge.getValue());
                edge.setValue("edge");
            }
            catch (UnsupportedOperationException e)
            {
                // ok, setValue operation is optional
            }
        }
    }

    public void testEdgesIsImmutable()
    {
        Graph<String, String> graph = createFullGraph("node", "edge");
        Edge<String, String> edge = graph.edges().iterator().next();
        Collection<Edge<String, String>> fullEdges = Collections.singleton(edge);
        try
        {
            graph.edges().clear();
            fail("graph edges clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edges().add(edge);
            fail("graph edges add(edge) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edges().remove(edge);
            fail("graph edges remove(edge) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edges().addAll(fullEdges);
            fail("graph edges addAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edges().removeAll(fullEdges);
            fail("graph edges removeAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edges().retainAll(fullEdges);
            fail("graph edges retainAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<Edge<String, String>> edges = graph.edges().iterator();
            if (edges.hasNext())
            {
                edges.next();
                edges.remove();
                fail("graph edges iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
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
        Graph<String, String> graph = createFullGraph("node", "edge");
        String edgeValue = "edge";
        Collection<String> fullEdgeValues = Collections.singleton(edgeValue);
        try
        {
            graph.edgeValues().clear();
            fail("graph edgeValues clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edgeValues().add(edgeValue);
            fail("graph edgeValues add(edgeValue) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edgeValues().remove(edgeValue);
            fail("graph edgeValues remove(edgeValue) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edgeValues().addAll(fullEdgeValues);
            fail("graph edgeValues addAll(fullEdgeValues) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edgeValues().removeAll(fullEdgeValues);
            fail("graph edgeValues removeAll(fullEdgeValues) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            graph.edgeValues().retainAll(fullEdgeValues);
            fail("graph edgeValues retainAll(fullEdgeValues) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<String> edgeValues = graph.edgeValues().iterator();
            if (edgeValues.hasNext())
            {
                edgeValues.next();
                edgeValues.remove();
                fail("graph edgeValues iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testEdgeMap()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        assertNotNull(emptyGraph.edgeMap(null));
        assertTrue(emptyGraph.edgeMap(null).isEmpty());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap(null).size());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap(null).keySet().size());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap(null).entrySet().size());
        assertEquals(emptyGraph.edgeCount(), emptyGraph.edgeMap(null).values().size());

        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        assertNotNull(fullGraph.edgeMap(null));
        assertFalse(fullGraph.edgeMap(null).isEmpty());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap(null).size());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap(null).keySet().size());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap(null).entrySet().size());
        assertEquals(fullGraph.edgeCount(), fullGraph.edgeMap(null).values().size());

        for (Edge<String, String> edge : fullGraph.edges())
        {
            assertTrue(fullGraph.edgeMap(null).containsKey(edge));
        }
    }

    public void testEmptyEdgeMapKeysAreImmutable()
    {
        Graph<String, String> emptyGraph = createEmptyGraph();
        Map<Edge<String, String>, Integer> edgeMap = emptyGraph.edgeMap(null);
        Set<Edge<String, String>> edgeKeys = edgeMap.keySet();        
        assertNotNull(edgeKeys);
        assertTrue(edgeKeys.isEmpty());

        Edge<String, String> edge = null; // null edge isn't the best test, but...
        Collection<Edge<String, String>> fullEdges = Collections.singleton(edge);
        try
        {
            edgeKeys.clear();
            fail("edgeKeys clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.add(edge);
            fail("nodeKeys add(edge) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.remove(edge);
            fail("edgeKeys remove(edge) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.addAll(fullEdges);
            fail("edgeKeys addAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.removeAll(fullEdges);
            fail("edgeKeys removeAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.retainAll(fullEdges);
            fail("edgeKeys retainAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<Edge<String, String>> edges = edgeKeys.iterator();
            if (edges.hasNext())
            {
                edges.next();
                edges.remove();
                fail("edgeKeys iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testFullEdgeMapKeysAreImmutable()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Edge<String, String>, Integer> edgeMap = fullGraph.edgeMap(null);
        Set<Edge<String, String>> edgeKeys = edgeMap.keySet();        
        assertNotNull(edgeKeys);
        assertFalse(edgeKeys.isEmpty());
        assertEquals(fullGraph.edges().size(), edgeKeys.size());

        Edge<String, String> edge = fullGraph.edges().iterator().next();
        Collection<Edge<String, String>> fullEdges = Collections.singleton(edge);
        try
        {
            edgeKeys.clear();
            fail("edgeKeys clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.add(edge);
            fail("nodeKeys add(edge) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.remove(edge);
            fail("edgeKeys remove(edge) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.addAll(fullEdges);
            fail("edgeKeys addAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.removeAll(fullEdges);
            fail("edgeKeys removeAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            edgeKeys.retainAll(fullEdges);
            fail("edgeKeys retainAll(fullEdges) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<Edge<String, String>> edges = edgeKeys.iterator();
            if (edges.hasNext())
            {
                edges.next();
                edges.remove();
                fail("edgeKeys iterator remove() expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testEdgeMapDefaultValue()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Edge<String, String>, Integer> edgeMap0 = fullGraph.edgeMap(null);
        for (Integer value : edgeMap0.values())
        {
            assertEquals(null, value);
        }
        Integer defaultValue = Integer.valueOf(99);
        Map<Edge<String, String>, Integer> edgeMap1 = fullGraph.edgeMap(defaultValue);
        for (Integer value : edgeMap1.values())
        {
            assertEquals(defaultValue, value);
        }
    }

    public void testEdgeMapPut()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Edge<String, String>, Integer> edgeMap = fullGraph.edgeMap(null);
        Edge<String, String> edge = fullGraph.edges().iterator().next();
        assertEquals((Integer) null, edgeMap.get(edge));
        edgeMap.put(edge, 1);
        assertEquals(Integer.valueOf(1), edgeMap.get(edge));

        // TODO:  put(not a edge) throws ??
    }

    public void testEdgeMapRemove()
    {
        Graph<String, String> fullGraph = createFullGraph("node", "edge");
        Map<Edge<String, String>, Double> edgeMap = fullGraph.edgeMap(null);

        // TODO:  remove(edge) throws ??
        // TODO:  remove(not an edge) ok ?
    }

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

        UnaryProcedure<Object> superProcedure = new UnaryProcedure<Object>()
        {
            /** {@inheritDoc} */
            public void run(final Object edgeValue)
            {
                // empty
            }
        };
        emptyGraph.forEachEdgeValue(superProcedure);
        fullGraph.forEachEdgeValue(superProcedure);
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

        Graph<String, String> otherGraph = createFullGraph("node", "edge");
        Node<String, String> otherNode = otherGraph.nodes().iterator().next();
        try
        {
            fullGraph.remove(otherNode);
            fail("remove(otherNode) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
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
            fail("createEdge(null, null,) expected IllegalArgumentException");
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
            fail("createEdge(,null,) expected IllegalArgumentException");
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
            fail("createEdge(null,,) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, String> otherGraph = createFullGraph("node", "edge");
        Node<String, String> otherNode = otherGraph.nodes().iterator().next();
        try
        {
            Node<String, String> node = emptyGraph.createNode("node");
            emptyGraph.createEdge(node, otherNode, "edge");
            fail("createEdge(,otherNode) expected IllegalArgumentException");
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
            emptyGraph.createEdge(otherNode, node, "edge");
            fail("createEdge(otherNode,) expected IllegalArgumentException");
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
            emptyGraph.createEdge(otherNode, otherNode, "edge");
            fail("createEdge(otherNode, otherNode) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
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

        Graph<String, String> otherGraph = createFullGraph("node", "edge");
        Edge<String, String> otherEdge = otherGraph.edges().iterator().next();
        try
        {
            fullGraph.remove(otherEdge);
            fail("remove(otherEdge) expected IllegalArgumentException");
        }
        catch (UnsupportedOperationException e)
        {
            // ok, implementations may not support operation
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}