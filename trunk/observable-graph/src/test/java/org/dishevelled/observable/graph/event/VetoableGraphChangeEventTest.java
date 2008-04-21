/*

    dsh-observable-graph  Observable decorators for graph interfaces.
    Copyright (c) 2008 held jointly by the individual authors.

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
package org.dishevelled.observable.graph.event;

import junit.framework.TestCase;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

import org.dishevelled.observable.graph.ObservableGraph;

import org.dishevelled.observable.graph.impl.ObservableGraphUtils;

/**
 * Unit test for VetoableGraphChangeEvent.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class VetoableGraphChangeEventTest
    extends TestCase
{

    public void testConstructor()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        // another circular package dependency
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        VetoableGraphChangeEvent<String, String> event0 = new VetoableGraphChangeEvent<String, String>(observableGraph);
        assertNotNull(event0);
        assertEquals(observableGraph, event0.getObservableGraph());
        assertEquals(null, event0.getNode());
        assertEquals(null, event0.getEdge());
        assertEquals(null, event0.getNodeValue());
        assertEquals(null, event0.getSourceNode());
        assertEquals(null, event0.getTargetNode());
        assertEquals(null, event0.getEdgeValue());

        VetoableGraphChangeEvent<String, String> event1 = new VetoableGraphChangeEvent<String, String>(observableGraph, (Node<String, String>) null);
        assertNotNull(event1);
        VetoableGraphChangeEvent<String, String> event2 = new VetoableGraphChangeEvent<String, String>(observableGraph, (Edge<String, String>) null);
        assertNotNull(event2);
        VetoableGraphChangeEvent<String, String> event3 = new VetoableGraphChangeEvent<String, String>(observableGraph, (String) null);
        assertNotNull(event3);
        VetoableGraphChangeEvent<String, String> event4 = new VetoableGraphChangeEvent<String, String>(observableGraph, null, null, null);
        assertNotNull(event4);

        try
        {
            VetoableGraphChangeEvent<String, String> event = new VetoableGraphChangeEvent<String, String>(null);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testNode()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        Node<String, String> node = observableGraph.createNode("foo");
        VetoableGraphChangeEvent<String, String> event = new VetoableGraphChangeEvent<String, String>(observableGraph, node);
        assertEquals(node, event.getNode());
    }

    public void testEdge()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        Node<String, String> node0 = observableGraph.createNode("foo");
        Node<String, String> node1 = observableGraph.createNode("bar");
        Edge<String, String> edge = observableGraph.createEdge(node0, node1, "baz");
        VetoableGraphChangeEvent<String, String> event = new VetoableGraphChangeEvent<String, String>(observableGraph, edge);
        assertEquals(edge, event.getEdge());
    }

    public void testNodeValue()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        VetoableGraphChangeEvent<String, String> event = new VetoableGraphChangeEvent<String, String>(observableGraph, "foo");
        assertEquals("foo", event.getNodeValue());
    }

    public void testSourceNode()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        Node<String, String> node0 = observableGraph.createNode("foo");
        Node<String, String> node1 = observableGraph.createNode("bar");
        VetoableGraphChangeEvent<String, String> event = new VetoableGraphChangeEvent<String, String>(observableGraph, node0, node1, "baz");
        assertEquals(node0, event.getSourceNode());
    }

    public void testTargetNode()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        Node<String, String> node0 = observableGraph.createNode("foo");
        Node<String, String> node1 = observableGraph.createNode("bar");
        VetoableGraphChangeEvent<String, String> event = new VetoableGraphChangeEvent<String, String>(observableGraph, node0, node1, "baz");
        assertEquals(node1, event.getTargetNode());
    }

    public void testEdgeValue()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        Node<String, String> node0 = observableGraph.createNode("foo");
        Node<String, String> node1 = observableGraph.createNode("bar");
        VetoableGraphChangeEvent<String, String> event = new VetoableGraphChangeEvent<String, String>(observableGraph, node0, node1, "baz");
        assertEquals("baz", event.getEdgeValue());
    }
}