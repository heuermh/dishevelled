/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

import org.dishevelled.graph.io.AbstractGraphWriterTest;
import org.dishevelled.graph.io.GraphWriter;

/**
 * Unit test for GraphMLWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphMLWriterTest
    extends AbstractGraphWriterTest
{
    /** Empty graph. */
    private Graph<String, Double> emptyGraph;

    /** Full graph. */
    private Graph<String, Double> fullGraph;


    /** {@inheritDoc} */
    protected void setUp()
    {
        emptyGraph = GraphUtils.createGraph();
        fullGraph = GraphUtils.createGraph(5, 20);
        Node<String, Double> node0 = fullGraph.createNode("node0");
        Node<String, Double> node1 = fullGraph.createNode("node1");
        Node<String, Double> node2 = fullGraph.createNode("node2");
        Node<String, Double> node3 = fullGraph.createNode("node3");
        Node<String, Double> node4 = fullGraph.createNode("node4");
        fullGraph.createEdge(node0, node1, 1.0d);
        fullGraph.createEdge(node0, node2, 1.1d);
        fullGraph.createEdge(node0, node3, 1.2d);
        fullGraph.createEdge(node0, node4, 1.3d);
        fullGraph.createEdge(node1, node0, 1.4d);
        fullGraph.createEdge(node1, node2, 1.5d);
        fullGraph.createEdge(node1, node3, 1.6d);
        fullGraph.createEdge(node1, node4, 1.7d);
        fullGraph.createEdge(node2, node1, 1.8d);
        fullGraph.createEdge(node2, node0, 1.9d);
        fullGraph.createEdge(node2, node3, 2.0d);
        fullGraph.createEdge(node2, node4, 2.1d);
        fullGraph.createEdge(node3, node1, 2.2d);
        fullGraph.createEdge(node3, node2, 2.3d);
        fullGraph.createEdge(node3, node0, 2.4d);
        fullGraph.createEdge(node3, node4, 2.5d);
        fullGraph.createEdge(node4, node1, 2.6d);
        fullGraph.createEdge(node4, node2, 2.7d);
        fullGraph.createEdge(node4, node3, 2.8d);
        fullGraph.createEdge(node4, node0, 2.9d);
    }

    /** {@inheritDoc} */
    protected <N, E> GraphWriter<N, E> createGraphWriter()
    {
        return new GraphMLWriter<N, E>();
    }

    public void testConstructor()
    {
        GraphMLWriter<String, Double> writer = new GraphMLWriter<String, Double>();
        assertNotNull(writer);
    }

    public void testEmptyGraph() throws IOException
    {
        GraphMLWriter<String, Double> writer = new GraphMLWriter<String, Double>();
        File file = File.createTempFile("graphMLWriterTest", "xml");
        writer.write(emptyGraph, file);
        assertTrue(file.exists());
        //dump(file);
    }

    public void testFullGraph() throws IOException
    {
        GraphMLWriter<String, Double> writer = new GraphMLWriter<String, Double>();
        File file = File.createTempFile("graphMLWriterTest", "xml");
        writer.write(fullGraph, file);
        assertTrue(file.exists());
        //dump(file);
    }

    public void testFullGraphNullValueHandlers() throws IOException
    {
        GraphMLWriter<String, Double> writer = new GraphMLWriter<String, Double>();
        writer.setNodeValueHandler(null);
        writer.setEdgeValueHandler(null);
        File file = File.createTempFile("graphMLWriterTest", "xml");
        writer.write(fullGraph, file);
        assertTrue(file.exists());
        //dump(file);
    }

    public void testFullGraphDataElementValueHandlers() throws IOException
    {
        GraphMLWriter<String, Double> writer = new GraphMLWriter<String, Double>();
        writer.setNodeValueHandler(new GraphMLWriter.ValueHandler<String>()
            {
                /** {@inheritDoc} */
                public void write(final String value, final XMLStreamWriter writer) throws IOException, XMLStreamException
                {
                    writer.writeCharacters(value);
                }
            });
        writer.setEdgeValueHandler(new GraphMLWriter.ValueHandler<Double>()
            {
                /** {@inheritDoc} */
                public void write(final Double value, final XMLStreamWriter writer) throws IOException, XMLStreamException
                {
                    writer.writeCharacters(value.toString());
                }
            });
        File file = File.createTempFile("graphMLWriterTest", "xml");
        writer.write(fullGraph, file);
        assertTrue(file.exists());
        //dump(file);
    }

    private void dump(final File file)
    {
        java.io.BufferedReader reader = null;
        try
        {
            reader = new java.io.BufferedReader(new java.io.FileReader(file));
            while (reader.ready())
            {
                System.out.println(reader.readLine());
            }
        }
        catch (Exception e)
        {
            // ignore
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (Exception e)
            {
                // empty
            }
        }
    }
}