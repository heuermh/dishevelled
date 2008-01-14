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

import org.dishevelled.graph.Edge;
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

    public void testDfs()
    {
        Graph<String, Integer> graph = new GraphImpl<String, Integer>();
        Node<String, Integer> node0 = graph.createNode("node0");
        Node<String, Integer> node1 = graph.createNode("node1");
        Edge<String, Integer> edge = graph.createEdge(node0, node1, 0);
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
    }

    public void testBfs()
    {
        Graph<String, Integer> graph = new GraphImpl<String, Integer>();
        Node<String, Integer> node0 = graph.createNode("node0");
        Node<String, Integer> node1 = graph.createNode("node1");
        Edge<String, Integer> edge = graph.createEdge(node0, node1, 0);
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