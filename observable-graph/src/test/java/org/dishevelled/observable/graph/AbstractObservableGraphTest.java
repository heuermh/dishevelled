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
package org.dishevelled.observable.graph;

import org.dishevelled.graph.AbstractGraphTest;
import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.observable.graph.event.GraphChangeEvent;
import org.dishevelled.observable.graph.event.GraphChangeListener;
import org.dishevelled.observable.graph.event.GraphChangeVetoException;
import org.dishevelled.observable.graph.event.VetoableGraphChangeEvent;
import org.dishevelled.observable.graph.event.VetoableGraphChangeListener;

/**
 * Abstract unit test for implementations of ObservableGraph.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableGraphTest
    extends AbstractGraphTest
{

    /** {@inheritDoc} */
    protected final <N, E> Graph<N, E> createEmptyGraph()
    {
        return createEmptyObservableGraph();
    }

    /** {@inheritDoc} */
    protected final <N, E> Graph<N, E> createFullGraph(final N nodeValue, final E edgeValue)
    {
        return createFullObservableGraph(nodeValue, edgeValue);
    }

    /**
     * Create and return a new empty instance of ObservableGraph to test.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @return a new empty instance of ObservableGraph to test
     */
    protected abstract <N, E> ObservableGraph<N, E> createEmptyObservableGraph();

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
    protected abstract <N, E> ObservableGraph<N, E> createFullObservableGraph(N nodeValue, E edgeValue);

    public void testCreateEmptyObservableGraph()
    {
        ObservableGraph<String, String> emptyGraph = createEmptyObservableGraph();
        assertNotNull(emptyGraph);
    }

    public void testCreateFullObservableGraph()
    {
        ObservableGraph<String, String> fullGraph = createFullObservableGraph("node", "edge");
        assertNotNull(fullGraph);
    }

    public void testListeners()
    {
        ObservableGraph<String, String> graph = createEmptyObservableGraph();
        Listener<String, String> listener = new Listener<String, String>();
        graph.addGraphChangeListener(listener);

        assertEquals(1, graph.getGraphChangeListenerCount());
        assertNotNull(graph.getGraphChangeListeners());
        assertEquals(1, graph.getGraphChangeListeners().length);
        assertEquals(listener, graph.getGraphChangeListeners()[0]);

        graph.removeGraphChangeListener(listener);
        assertEquals(0, graph.getGraphChangeListenerCount());
        assertNotNull(graph.getGraphChangeListeners());
        assertEquals(0, graph.getGraphChangeListeners().length);

        NeverVetoListener<String, String> neverVetoListener = new NeverVetoListener<String, String>();
        graph.addVetoableGraphChangeListener(neverVetoListener);

        assertEquals(1, graph.getVetoableGraphChangeListenerCount());
        assertNotNull(graph.getVetoableGraphChangeListeners());
        assertEquals(1, graph.getVetoableGraphChangeListeners().length);
        assertEquals(neverVetoListener, graph.getVetoableGraphChangeListeners()[0]);

        graph.removeVetoableGraphChangeListener(neverVetoListener);
        assertEquals(0, graph.getVetoableGraphChangeListenerCount());
        assertNotNull(graph.getVetoableGraphChangeListeners());
        assertEquals(0, graph.getVetoableGraphChangeListeners().length);
    }

    public void testFireGraphWillChange()
        throws Exception
    {
        ObservableGraph<String, String> graph = createEmptyObservableGraph();
        NeverVetoListener<String, String> neverVetoListener = new NeverVetoListener<String, String>();
        graph.addVetoableGraphChangeListener(neverVetoListener);

        if (graph instanceof AbstractObservableGraph)
        {
            ((AbstractObservableGraph) graph).fireGraphWillChange();

            assertNotNull(neverVetoListener.getEvent());
            assertSame(graph, neverVetoListener.getEvent().getObservableGraph());
        }
    }

    public void testClear()
    {
        ObservableGraph<String, String> fullGraph = createFullObservableGraph("node", "edge");
        Listener<String, String> listener = new Listener<String, String>();
        NeverVetoListener<String, String> neverVetoListener = new NeverVetoListener<String, String>();
        fullGraph.addGraphChangeListener(listener);
        fullGraph.addVetoableGraphChangeListener(neverVetoListener);

        try
        {
            assertFalse(fullGraph.isEmpty());
            fullGraph.clear();
            assertTrue(fullGraph.isEmpty());

            assertNotNull(listener.getEvent());
            assertSame(fullGraph, listener.getEvent().getObservableGraph());

            assertNotNull(neverVetoListener.getEvent());
            assertSame(fullGraph, neverVetoListener.getEvent().getObservableGraph());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            fullGraph.removeGraphChangeListener(listener);
            fullGraph.removeVetoableGraphChangeListener(neverVetoListener);
        }
    }

    public void testClearVeto()
    {
        ObservableGraph<String, String> fullGraph = createFullObservableGraph("node", "edge");
        AlwaysVetoListener<String, String> alwaysVetoListener = new AlwaysVetoListener<String, String>();
        fullGraph.addVetoableGraphChangeListener(alwaysVetoListener);

        try
        {
            assertFalse(fullGraph.isEmpty());
            fullGraph.clear();
            assertFalse(fullGraph.isEmpty());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            fullGraph.removeVetoableGraphChangeListener(alwaysVetoListener);
        }
    }

    public void testCreateNode()
    {
        ObservableGraph<String, String> emptyGraph = createEmptyObservableGraph();
        Listener<String, String> listener = new Listener<String, String>();
        NeverVetoListener<String, String> neverVetoListener = new NeverVetoListener<String, String>();
        emptyGraph.addGraphChangeListener(listener);
        emptyGraph.addVetoableGraphChangeListener(neverVetoListener);

        try
        {
            assertTrue(emptyGraph.isEmpty());
            Node<String, String> node = emptyGraph.createNode("node");
            assertNotNull(node);
            assertFalse(emptyGraph.isEmpty());

            assertNotNull(listener.getEvent());
            assertSame(emptyGraph, listener.getEvent().getObservableGraph());
            assertSame(node, listener.getEvent().getNode());

            assertNotNull(neverVetoListener.getEvent());
            assertSame(emptyGraph, neverVetoListener.getEvent().getObservableGraph());
            assertEquals(node.getValue(), neverVetoListener.getEvent().getNodeValue());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            emptyGraph.removeGraphChangeListener(listener);
            emptyGraph.removeVetoableGraphChangeListener(neverVetoListener);
        }
    }

    public void testCreateNodeVeto()
    {
        ObservableGraph<String, String> emptyGraph = createEmptyObservableGraph();
        AlwaysVetoListener<String, String> alwaysVetoListener = new AlwaysVetoListener<String, String>();
        emptyGraph.addVetoableGraphChangeListener(alwaysVetoListener);

        try
        {
            assertTrue(emptyGraph.isEmpty());
            Node<String, String> node = emptyGraph.createNode("node");
            // TODO:  explicitly document this behaviour
            assertEquals(null, node);
            assertTrue(emptyGraph.isEmpty());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            emptyGraph.removeVetoableGraphChangeListener(alwaysVetoListener);
        }
    }

    public void testRemoveNode()
    {
        ObservableGraph<String, String> fullGraph = createFullObservableGraph("node", "edge");
        Listener<String, String> listener = new Listener<String, String>();
        NeverVetoListener<String, String> neverVetoListener = new NeverVetoListener<String, String>();
        fullGraph.addGraphChangeListener(listener);
        fullGraph.addVetoableGraphChangeListener(neverVetoListener);

        try
        {
            assertFalse(fullGraph.nodes().isEmpty());
            Node<String, String> node = fullGraph.nodes().iterator().next();
            fullGraph.remove(node);
            assertFalse(fullGraph.nodes().contains(node));

            assertNotNull(listener.getEvent());
            assertSame(fullGraph, listener.getEvent().getObservableGraph());
            assertSame(node, listener.getEvent().getNode());

            assertNotNull(neverVetoListener.getEvent());
            assertSame(fullGraph, neverVetoListener.getEvent().getObservableGraph());
            assertSame(node, neverVetoListener.getEvent().getNode());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            fullGraph.removeGraphChangeListener(listener);
            fullGraph.removeVetoableGraphChangeListener(neverVetoListener);
        }
    }

    public void testRemoveNodeVeto()
    {
        ObservableGraph<String, String> fullGraph = createFullObservableGraph("node", "edge");
        AlwaysVetoListener<String, String> alwaysVetoListener = new AlwaysVetoListener<String, String>();
        fullGraph.addVetoableGraphChangeListener(alwaysVetoListener);

        try
        {
            assertFalse(fullGraph.nodes().isEmpty());
            Node<String, String> node = fullGraph.nodes().iterator().next();
            fullGraph.remove(node);
            assertTrue(fullGraph.nodes().contains(node));
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            fullGraph.removeVetoableGraphChangeListener(alwaysVetoListener);
        }
    }

    public void testCreateEdge()
    {
        ObservableGraph<String, String> emptyGraph = createEmptyObservableGraph();
        Listener<String, String> listener = new Listener<String, String>();
        NeverVetoListener<String, String> neverVetoListener = new NeverVetoListener<String, String>();
        emptyGraph.addGraphChangeListener(listener);
        emptyGraph.addVetoableGraphChangeListener(neverVetoListener);

        try
        {
            assertTrue(emptyGraph.isEmpty());
            Node<String, String> source = emptyGraph.createNode("source");
            Node<String, String> target = emptyGraph.createNode("target");
            assertTrue(emptyGraph.edges().isEmpty());
            Edge<String, String> edge = emptyGraph.createEdge(source, target, "edge");
            assertNotNull(edge);
            assertFalse(emptyGraph.isEmpty());
            assertFalse(emptyGraph.edges().isEmpty());

            assertNotNull(listener.getEvent());
            assertSame(emptyGraph, listener.getEvent().getObservableGraph());
            assertSame(edge, listener.getEvent().getEdge());

            assertNotNull(neverVetoListener.getEvent());
            assertSame(emptyGraph, neverVetoListener.getEvent().getObservableGraph());
            assertEquals(source, neverVetoListener.getEvent().getSourceNode());
            assertEquals(target, neverVetoListener.getEvent().getTargetNode());
            assertEquals(edge.getValue(), neverVetoListener.getEvent().getEdgeValue());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            emptyGraph.removeGraphChangeListener(listener);
            emptyGraph.removeVetoableGraphChangeListener(neverVetoListener);
        }
    }

    public void testCreateEdgeVeto()
    {
        ObservableGraph<String, String> emptyGraph = createEmptyObservableGraph();
        AlwaysVetoListener<String, String> alwaysVetoListener = new AlwaysVetoListener<String, String>();
        emptyGraph.addVetoableGraphChangeListener(alwaysVetoListener);

        try
        {
            assertTrue(emptyGraph.isEmpty());
            Node<String, String> source = emptyGraph.createNode("source");
            Node<String, String> target = emptyGraph.createNode("target");
            assertTrue(emptyGraph.edges().isEmpty());
            Edge<String, String> edge = emptyGraph.createEdge(source, target, "edge");
            // TODO:  explicitly document this behaviour
            assertEquals(null, edge);
            assertTrue(emptyGraph.edges().isEmpty());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            emptyGraph.removeVetoableGraphChangeListener(alwaysVetoListener);
        }
    }

    public void testRemoveEdge()
    {
        ObservableGraph<String, String> fullGraph = createFullObservableGraph("node", "edge");
        Listener<String, String> listener = new Listener<String, String>();
        NeverVetoListener<String, String> neverVetoListener = new NeverVetoListener<String, String>();
        fullGraph.addGraphChangeListener(listener);
        fullGraph.addVetoableGraphChangeListener(neverVetoListener);

        try
        {
            assertFalse(fullGraph.edges().isEmpty());
            Edge<String, String> edge = fullGraph.edges().iterator().next();
            fullGraph.remove(edge);
            assertFalse(fullGraph.edges().contains(edge));

            assertNotNull(listener.getEvent());
            assertSame(fullGraph, listener.getEvent().getObservableGraph());
            assertSame(edge, listener.getEvent().getEdge());

            assertNotNull(neverVetoListener.getEvent());
            assertSame(fullGraph, neverVetoListener.getEvent().getObservableGraph());
            assertSame(edge, neverVetoListener.getEvent().getEdge());
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            fullGraph.removeGraphChangeListener(listener);
            fullGraph.removeVetoableGraphChangeListener(neverVetoListener);
        }
    }

    public void testRemoveEdgeVeto()
    {
        ObservableGraph<String, String> fullGraph = createFullObservableGraph("node", "edge");
        AlwaysVetoListener<String, String> alwaysVetoListener = new AlwaysVetoListener<String, String>();
        fullGraph.addVetoableGraphChangeListener(alwaysVetoListener);

        try
        {
            assertFalse(fullGraph.edges().isEmpty());
            Edge<String, String> edge = fullGraph.edges().iterator().next();
            fullGraph.remove(edge);
            assertTrue(fullGraph.edges().contains(edge));
        }
        catch (UnsupportedOperationException e)
        {
            // ok
        }
        finally
        {
            fullGraph.removeVetoableGraphChangeListener(alwaysVetoListener);
        }
    }

    /**
     * Listener.
     */
    protected class Listener<N, E>
        implements GraphChangeListener<N, E> {

        /** Last event heard, if any. */
        private GraphChangeEvent<N, E> event;


        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        GraphChangeEvent<N, E> getEvent()
        {
            return event;
        }

        /** {@inheritDoc} */
        public void edgeCreated(GraphChangeEvent<N, E> event) {
            this.event = event;
        }

        /** {@inheritDoc} */
        public void edgeRemoved(GraphChangeEvent<N, E> event) {
            this.event = event;
        }

        /** {@inheritDoc} */
        public void graphCleared(GraphChangeEvent<N, E> event) {
            this.event = event;
        }

        /** {@inheritDoc} */
        public void nodeCreated(GraphChangeEvent<N, E> event) {
            this.event = event;
        }

        /** {@inheritDoc} */
        public void nodeRemoved(GraphChangeEvent<N, E> event) {
            this.event = event;
        }
    }

    /**
     * Always veto listener.
     */
    protected class AlwaysVetoListener<N, E>
        implements VetoableGraphChangeListener<N, E>
    {

        /** {@inheritDoc} */
        public void graphWillChange(final VetoableGraphChangeEvent<N, E> event)
            throws GraphChangeVetoException
        {
            throw new GraphChangeVetoException();
        }
    }

    /**
     * Never veto listener.
     */
    protected class NeverVetoListener<N, E>
        implements VetoableGraphChangeListener<N, E>
    {
        /** Last event heard, if any. */
        private VetoableGraphChangeEvent<N, E> event;


        /** {@inheritDoc} */
        public void graphWillChange(final VetoableGraphChangeEvent<N, E> event)
            throws GraphChangeVetoException
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        VetoableGraphChangeEvent<N, E> getEvent()
        {
            return event;
        }
    }
}