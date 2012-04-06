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

import junit.framework.TestCase;

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.impl.GraphUtils;

/**
 * Abstract unit test for implementations of GraphWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractGraphWriterTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of GraphWriter to test.
     *
     * @param <N> node value type
     * @param <E> edge value type
     * @return a new instance of an implementation of GraphWriter to test
     */
    protected abstract <N, E> GraphWriter<N, E> createGraphWriter();


    public void testCreateGraphWriter()
    {
        GraphWriter<String, Double> graphWriter = createGraphWriter();
        assertNotNull(graphWriter);
    }

    public void testWriteToFile() throws IOException
    {
        GraphWriter<String, Double> graphWriter = createGraphWriter();
        Graph<String, Double> graph = GraphUtils.createGraph();
        File file = File.createTempFile("abstractGraphWriterTest", null);
        try
        {
            graphWriter.write(null, file);
            fail("write(null, file) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            graphWriter.write(graph, (File) null);
            fail("write(null, file) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            graphWriter.write(null, (File) null);
            fail("write(null, (File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testWriteToOutputStream()
    {
        // empty
    }
}