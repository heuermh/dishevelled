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

import org.dishevelled.graph.AbstractGraphTest;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;


/**
 * Unit test for GraphImpl.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphImplTest
    extends AbstractGraphTest
{

    /** @{inheritDoc} */
    protected <N, E> Graph<N, E> createEmptyGraph()
    {
        return new GraphImpl<N, E>();
    }

    /** @{inheritDoc} */
    protected <N, E> Graph<N, E> createFullGraph(final N nodeValue, final E edgeValue)
    {
        Graph<N, E> graph = new GraphImpl<N, E>();
        Node<N, E> node0 = graph.createNode(nodeValue);
        Node<N, E> node1 = graph.createNode(nodeValue);
        graph.createEdge(node0, node1, edgeValue);
        return graph;
    }

    public void testConstructor()
    {
        Graph<String, Integer> graph0 = new GraphImpl<String, Integer>();
        assertNotNull(graph0);

        Graph<String, Integer> graph1 = new GraphImpl<String, Integer>(16, 16);
        assertNotNull(graph1);

        try
        {
            Graph<String, Integer> graph = new GraphImpl<String, Integer>(-1, 16);
            fail("ctr(-1,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Graph<String, Integer> graph = new GraphImpl<String, Integer>(16, -1);
            fail("ctr(,-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Graph<String, Integer> graph = new GraphImpl<String, Integer>(-1, -1);
            fail("ctr(-1,-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}