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
import org.dishevelled.graph.Graph;

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
}