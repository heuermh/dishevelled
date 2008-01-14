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

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

/**
 * Static utility methods on directed graphs.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphUtils
{

    /**
     * Private no-arg constructor.
     */
    private GraphUtils()
    {
        // empty
    }


    /**
     * Create and return an unmodifiable graph decorator that decorates the specified graph.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @param graph graph to decorate, must not be null
     * @return an unmodifiable graph decorator that decorates the specified graph
     */
    public static <N, E> Graph<N, E> unmodifiableGraph(final Graph<N, E> graph)
    {
        return new UnmodifiableGraph<N, E>(graph);
    }

    /**
     * Unmodifiable graph decorator.
     */
    private static final class UnmodifiableGraph<N, E>
        extends AbstractGraphDecorator<N, E>
    {

        /**
         * Create a unmodifiable graph decorator that decorates the specified graph.
         *
         * @param graph graph to decorate, must not be null
         */
        private UnmodifiableGraph(final Graph<N, E> graph)
        {
            super(graph);
        }


        /** {@inheritDoc} */
        public void clear()
        {
            throw new UnsupportedOperationException("clear operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public Node<N, E> createNode(final N value)
        {
            throw new UnsupportedOperationException("createNode operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public void remove(final Node<N, E> node)
        {
            throw new UnsupportedOperationException("remove(Node) operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public Edge<N, E> createEdge(final Node<N, E> source, final Node<N, E> target, final E value)
        {
            throw new UnsupportedOperationException("createEdge operation not supported by this graph");
        }

        /** {@inheritDoc} */
        public void remove(final Edge<N, E> edge)
        {
            throw new UnsupportedOperationException("remove(Edge) operation not supported by this graph");
        }
    }
}