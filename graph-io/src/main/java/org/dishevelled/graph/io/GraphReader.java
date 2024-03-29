/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2013 held jointly by the individual authors.

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

import java.net.URL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.dishevelled.graph.Graph;

/**
 * Graph reader.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface GraphReader<N, E>
{

    /**
     * Read a graph from the specified file.
     *
     * @param file file to read from, must not be null
     * @return a graph read from the specified file
     * @throws IOException if an IO error occurs
     */
    Graph<N, E> read(File file) throws IOException;

    /**
     * Read a graph from the specified input stream.
     *
     * @param inputStream input stream to read from, must not be null
     * @return a graph read from the specified input stream
     * @throws IOException if an IO error occurs
     */
    Graph<N, E> read(InputStream inputStream) throws IOException;

    /**
     * Read a graph from the specified URL.
     *
     * @param url URL to read from, must not be null
     * @return a graph read from the specified URL
     * @throws IOException if an IO error occurs
     */
    Graph<N, E> read(URL url) throws IOException;
}