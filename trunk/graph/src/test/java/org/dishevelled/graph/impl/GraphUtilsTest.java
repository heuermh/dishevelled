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
package org.dishevelled.graph.impl;

import junit.framework.TestCase;

import org.dishevelled.functor.UnaryProcedure;

import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

/**
 * Unit test for GraphUtils.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphUtilsTest
    extends TestCase
{

    public void testCreateGraph()
    {
        Graph<String, Integer> graph0 = GraphUtils.createGraph();
        assertNotNull(graph0);

        Graph<String, Integer> graph1 = GraphUtils.createGraph(16, 16);
        assertNotNull(graph1);

        Graph<String, Integer> emptyGraph = GraphUtils.createGraph();
        Graph<String, Integer> emptyCopy = new GraphImpl<String, Integer>(emptyGraph);
        assertNotNull(emptyCopy);
        assertTrue(emptyCopy.isEmpty());

        Graph<String, Integer> fullGraph = GraphUtils.createGraph();
        Node<String, Integer> node0 = fullGraph.createNode("foo");
        Node<String, Integer> node1 = fullGraph.createNode("bar");
        Node<String, Integer> node2 = fullGraph.createNode("baz");
        Node<String, Integer> node3 = fullGraph.createNode("qux");
        fullGraph.createEdge(node0, node1, Integer.valueOf(0));
        fullGraph.createEdge(node0, node2, Integer.valueOf(1));
        fullGraph.createEdge(node0, node3, Integer.valueOf(2));
        fullGraph.createEdge(node1, node0, Integer.valueOf(3));
        fullGraph.createEdge(node1, node2, Integer.valueOf(4));
        fullGraph.createEdge(node1, node3, Integer.valueOf(5));
        fullGraph.createEdge(node2, node0, Integer.valueOf(6));
        fullGraph.createEdge(node2, node1, Integer.valueOf(7));
        fullGraph.createEdge(node2, node3, Integer.valueOf(8));
        fullGraph.createEdge(node3, node0, Integer.valueOf(9));
        fullGraph.createEdge(node3, node1, Integer.valueOf(10));
        fullGraph.createEdge(node3, node2, Integer.valueOf(11));
        
        Graph<String, Integer> fullCopy = new GraphImpl<String, Integer>(fullGraph);
        assertNotNull(fullCopy);
        assertFalse(fullCopy.isEmpty());
        assertEquals(fullGraph.nodeCount(), fullCopy.nodeCount());
        assertEquals(fullGraph.edgeCount(), fullCopy.edgeCount());

        try
        {
            Graph<String, Integer> graph = GraphUtils.createGraph(-1, 16);
            fail("createGraph(-1,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Graph<String, Integer> graph = GraphUtils.createGraph(16, -1);
            fail("createGraph(,-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Graph<String, Integer> graph = GraphUtils.createGraph(-1, -1);
            fail("createGraph(-1,-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Graph<String, Integer> graph = GraphUtils.createGraph(null);
            fail("createGraph(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testDepthFirstSearch()
    {
        Graph<String, Integer> graph = new GraphImpl<String, Integer>();
        Node<String, Integer> node0 = graph.createNode("node0");
        Node<String, Integer> node1 = graph.createNode("node1");
        graph.createEdge(node0, node1, 0);
        UnaryProcedure<Node<String, Integer>> procedure = new UnaryProcedure<Node<String, Integer>>()
            {
                /** {@inheritDoc} */
                public void run(final Node<String, Integer> node)
                {
                    // empty
                }
            };

        GraphUtils.depthFirstSearch(graph, node0, procedure);

        try
        {
            GraphUtils.depthFirstSearch(null, node0, procedure);
            fail("depthFirstSearch(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.depthFirstSearch(graph, null, procedure);
            fail("depthFirstSearch(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.depthFirstSearch(graph, node0, null);
            fail("depthFirstSearch(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, Integer> graph1 = new GraphImpl<String, Integer>();
        Node<String, Integer> node2 = graph1.createNode("node2");
        try
        {
            GraphUtils.depthFirstSearch(graph, node2, procedure);
            fail("depthFirstSearch(,node2,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testBreadthFirstSearch()
    {
        Graph<String, Integer> graph = new GraphImpl<String, Integer>();
        Node<String, Integer> node0 = graph.createNode("node0");
        Node<String, Integer> node1 = graph.createNode("node1");
        graph.createEdge(node0, node1, 0);
        UnaryProcedure<Node<String, Integer>> procedure = new UnaryProcedure<Node<String, Integer>>()
            {
                /** {@inheritDoc} */
                public void run(final Node<String, Integer> node)
                {
                    // empty
                }
            };

        GraphUtils.breadthFirstSearch(graph, node0, procedure);

        try
        {
            GraphUtils.breadthFirstSearch(null, node0, procedure);
            fail("breadthFirstSearch(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.breadthFirstSearch(graph, null, procedure);
            fail("breadthFirstSearch(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.breadthFirstSearch(graph, node0, null);
            fail("breadthFirstSearch(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, Integer> graph1 = new GraphImpl<String, Integer>();
        Node<String, Integer> node2 = graph1.createNode("node2");
        try
        {
            GraphUtils.breadthFirstSearch(graph, node2, procedure);
            fail("breadthFirstSearch(,node2,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testUndirectedBreadthFirstSearch()
    {
        Graph<String, Integer> graph = new GraphImpl<String, Integer>();
        Node<String, Integer> node0 = graph.createNode("node0");
        Node<String, Integer> node1 = graph.createNode("node1");
        graph.createEdge(node0, node1, 0);
        UnaryProcedure<Node<String, Integer>> procedure = new UnaryProcedure<Node<String, Integer>>()
            {
                /** {@inheritDoc} */
                public void run(final Node<String, Integer> node)
                {
                    // empty
                }
            };

        GraphUtils.undirectedBreadthFirstSearch(graph, node0, procedure);

        try
        {
            GraphUtils.undirectedBreadthFirstSearch(null, node0, procedure);
            fail("undirectedBreadthFirstSearch(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.undirectedBreadthFirstSearch(graph, null, procedure);
            fail("undirectedBreadthFirstSearch(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.undirectedBreadthFirstSearch(graph, node0, null);
            fail("undirectedBreadthFirstSearch(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, Integer> graph1 = new GraphImpl<String, Integer>();
        Node<String, Integer> node2 = graph1.createNode("node2");
        try
        {
            GraphUtils.undirectedBreadthFirstSearch(graph, node2, procedure);
            fail("undirectedBreadthFirstSearch(,node2,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testUnmodifiableGraph()
    {
        // see GraphUtilsUnmodifiableGraphTest for add'l tests
        try
        {
            GraphUtils.unmodifiableGraph(null);
            fail("unmodifiableGraph(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}