/*

    dsh-graph-io  Directed graph readers and writers.
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
package org.dishevelled.graph.io.graphml;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.io.GraphWriter;

/**
 * GraphML writer.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphMLWriter<N, E>
    implements GraphWriter<N, E>
{

    /** {@inheritDoc} */
    public void write(final Graph<N, E> graph, final File file)
        throws IOException
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }
    }

    /** {@inheritDoc} */
    public void write(final Graph<N, E> graph, final OutputStream outputStream)
        throws IOException
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("graph must not be null");
        }
        if (outputStream == null)
        {
            throw new IllegalArgumentException("outputStream must not be null");
        }
    }
}