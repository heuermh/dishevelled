/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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
package org.dishevelled.graph.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.dishevelled.graph.Graph;

/**
 * Graph writer.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface GraphWriter<N, E>
{

    /**
     * Write the specified graph to the specified file.
     *
     * @param graph graph to write, must not be null
     * @param file file to write to, must not be null
     * @throws IOException if an IO error occurs
     */
    void write(Graph<N, E> graph, File file) throws IOException;

    /**
     * Write the specified graph to the specified output stream.
     *
     * @param graph graph to write, must not be null
     * @param outputStream output stream to write to, must not be null
     * @throws IOException if an IO error occurs
     */
    void write(Graph<N, E> graph, OutputStream outputStream) throws IOException;
}