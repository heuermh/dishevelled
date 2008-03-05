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

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.impl.GraphUtils;

import org.dishevelled.observable.graph.ObservableGraph;

import org.dishevelled.observable.graph.impl.ObservableGraphUtils;

/**
 * Unit test for GraphChangeEvent.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class GraphChangeEventTest
    extends TestCase
{

    public void testConstructor()
    {
        Graph<String, String> graph = GraphUtils.createGraph();
        // another circular package dependency
        ObservableGraph<String, String> observableGraph = ObservableGraphUtils.observableGraph(graph);
        GraphChangeEvent<String, String> event0 = new GraphChangeEvent<String, String>(observableGraph);
        assertNotNull(event0);
        assertEquals(observableGraph, event0.getObservableGraph());

        try
        {
            GraphChangeEvent<String, String> event = new GraphChangeEvent<String, String>(null);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}