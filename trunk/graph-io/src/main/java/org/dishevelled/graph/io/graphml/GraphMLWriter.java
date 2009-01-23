/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2009 held jointly by the individual authors.

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
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Node;
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
    /** XML output factory. */
    private final XMLOutputFactory outputFactory;

    /** Node value handler. */
    private ValueHandler<N> nodeValueHandler;

    /** Edge value handler. */
    private ValueHandler<E> edgeValueHandler;


    /**
     * Create a new GraphML writer.
     */
    public GraphMLWriter()
    {
        outputFactory = XMLOutputFactory.newInstance();
    }


    /**
     * Set the node value handler for this GraphML writer to <code>nodeValueHandler</code>.
     *
     * @param nodeValueHandler node value handler
     */
    public void setNodeValueHandler(final ValueHandler<N> nodeValueHandler)
    {
        this.nodeValueHandler = nodeValueHandler;
    }

    /**
     * Set the edge value handler for this GraphML writer to <code>edgeValueHandler</code>.
     *
     * @param edgeValueHandler edge value handler
     */
    public void setEdgeValueHandler(final ValueHandler<E> edgeValueHandler)
    {
        this.edgeValueHandler = edgeValueHandler;
    }

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
        try
        {
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(file));
            write(graph, writer);
        }
        catch (XMLStreamException e)
        {
            //throw new IOException(e); jdk1.6+
            throw new IOException(e.getMessage());
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
        try
        {
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(outputStream);
            write(graph, writer);
        }
        catch (XMLStreamException e)
        {
            //throw new IOException(e);
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Write the specified graph to the specified XML stream writer.
     *
     * @param graph graph to write
     * @param writer XML stream writer to write to
     * @throws IOException if an error occurs
     * @throws XMLStreamException if an error occurs
     */
    private void write(final Graph<N, E> graph, final XMLStreamWriter writer)
        throws IOException, XMLStreamException
    {
        writer.writeStartDocument("1.0");
        //writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("graphml");
        writer.writeDefaultNamespace("http://graphml.graphdrawing.org/xmlns");
        writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        writer.writeAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
        if (nodeValueHandler != null)
        {
            writer.writeStartElement("key");
            writer.writeAttribute("id", "k0");
            writer.writeAttribute("for", "node");
            writer.writeEndElement(); // </key>
        }
        if (edgeValueHandler != null)
        {
            writer.writeStartElement("key");
            writer.writeAttribute("id", "k1");
            writer.writeAttribute("for", "edge");
            writer.writeEndElement(); // </key>
        }
        writer.writeStartElement("graph");
        writer.writeAttribute("id", "G");
        writer.writeAttribute("edgedefault", "directed");
        int n = 0;
        Map<Node<N, E>, String> nodeIds = new HashMap<Node<N, E>, String>(graph.nodeCount());
        for (Node<N, E> node : graph.nodes())
        {
            String nodeId = nodeIds.get(node);
            if (nodeId == null)
            {
                nodeId = "n" + n;
                n++;
                nodeIds.put(node, nodeId);
            }
            writer.writeStartElement("node");
            writer.writeAttribute("id", nodeId);
            if (nodeValueHandler != null)
            {
                writer.writeStartElement("data");
                writer.writeAttribute("key", "k0");
                nodeValueHandler.write(node.getValue(), writer);
                writer.writeEndElement(); // </data>
            }
            writer.writeEndElement(); // </node>
        }
        int e = 0;
        for (Edge<N, E> edge : graph.edges())
        {
            String edgeId = "e" + e;
            e++;
            writer.writeStartElement("edge");
            writer.writeAttribute("id", edgeId);
            writer.writeAttribute("source", nodeIds.get(edge.source()));
            writer.writeAttribute("target", nodeIds.get(edge.target()));
            if (edgeValueHandler != null)
            {
                writer.writeStartElement("data");
                writer.writeAttribute("key", "k1");
                edgeValueHandler.write(edge.getValue(), writer);
                writer.writeEndElement(); // </data>
            }
            writer.writeEndElement(); // </edge>
        }
        writer.writeEndElement(); // </graph>
        writer.writeEndElement(); // </graphml>
        writer.close();
    }

    /**
     * Value handler.
     *
     * @param <V> value type
     */
    interface ValueHandler<V>
    {

        /**
         * Write the specified value to the specified XML stream writer.
         *
         * @param value value to write
         * @param writer XML stream writer to write to, must not be null
         * @throws IOException if an error occurs
         * @throws XMLStreamException if an error occurs
         */
        void write(V value, XMLStreamWriter writer) throws IOException, XMLStreamException;
    }
}