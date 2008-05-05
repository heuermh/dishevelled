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
    }

    public void testDfs()
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

        GraphUtils.dfs(graph, node0, procedure);

        try
        {
            GraphUtils.dfs(null, node0, procedure);
            fail("dfs(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.dfs(graph, null, procedure);
            fail("dfs(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.dfs(graph, node0, null);
            fail("dfs(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, Integer> graph1 = new GraphImpl<String, Integer>();
        Node<String, Integer> node2 = graph1.createNode("node2");
        try
        {
            GraphUtils.dfs(graph, node2, procedure);
            fail("dfs(,node2,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testBfs()
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

        GraphUtils.bfs(graph, node0, procedure);

        try
        {
            GraphUtils.bfs(null, node0, procedure);
            fail("bfs(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.bfs(graph, null, procedure);
            fail("bfs(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.bfs(graph, node0, null);
            fail("bfs(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, Integer> graph1 = new GraphImpl<String, Integer>();
        Node<String, Integer> node2 = graph1.createNode("node2");
        try
        {
            GraphUtils.bfs(graph, node2, procedure);
            fail("bfs(,node2,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testUndirectedBfs()
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

        GraphUtils.undirectedBfs(graph, node0, procedure);

        try
        {
            GraphUtils.undirectedBfs(null, node0, procedure);
            fail("undirectedBfs(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.undirectedBfs(graph, null, procedure);
            fail("undirectedBfs(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            GraphUtils.undirectedBfs(graph, node0, null);
            fail("undirectedBfs(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Graph<String, Integer> graph1 = new GraphImpl<String, Integer>();
        Node<String, Integer> node2 = graph1.createNode("node2");
        try
        {
            GraphUtils.undirectedBfs(graph, node2, procedure);
            fail("undirectedBfs(,node2,) expected IllegalArgumentException");
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