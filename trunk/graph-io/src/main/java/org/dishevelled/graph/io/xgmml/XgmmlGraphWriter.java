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
package org.dishevelled.graph.io.xgmml;

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
 * XGMML graph writer.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 */
public final class XgmmlGraphWriter<N, E>
    implements GraphWriter<N, E>
{
    /** XML output factory. */
    private final XMLOutputFactory outputFactory;

    /** Map of node value handlers keyed by name. */
    private final Map<String, ValueHandler<N>> nodeValueHandlers;

    /** Map of edge value handlers keyed by name. */
    private final Map<String, ValueHandler<E>> edgeValueHandlers;


    /**
     * Create a new XGMML graph writer.
     */
    public XgmmlGraphWriter()
    {
        outputFactory = XMLOutputFactory.newInstance();
        nodeValueHandlers = new HashMap<String, ValueHandler<N>>();
        edgeValueHandlers = new HashMap<String, ValueHandler<E>>();
    }


    /**
     * Add the specified node value handler for the specified attribute name.
     *
     * @param name attribute name, must not be null
     * @param nodeValueHandler node value handler to add, must not be null
     */
    public void addNodeValueHandler(final String name, final ValueHandler<N> nodeValueHandler)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("name must not be null");
        }
        if (nodeValueHandler == null)
        {
            throw new IllegalArgumentException("nodeValueHandler must not be null");
        }
        nodeValueHandlers.put(name, nodeValueHandler);
    }

    /**
     * Add the specified edge value handler for the specified attribute name.
     *
     * @param name attribute name, must not be null
     * @param edgeValueHandler edge value handler to add, must not be null
     */
    public void addEdgeValueHandler(final String name, final ValueHandler<E> edgeValueHandler)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("name must not be null");
        }
        if (edgeValueHandler == null)
        {
            throw new IllegalArgumentException("edgeValueHandler must not be null");
        }
        edgeValueHandlers.put(name, edgeValueHandler);
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
        writer.writeStartElement("graph");
        writer.writeDefaultNamespace("http://www.cs.rpi.edu/XGMML");
        writer.writeNamespace("dc", "http://purl.org/dc/elements/1.1/");
        writer.writeNamespace("xlink", "http://www.w3.org/1999/xlink");
        writer.writeNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        //writer.writeAttribute("xsi:schemaLocation", "http://www.cs.rpi.edu/XGMML http://www.cs.rpi.edu/research/groups/pb/punin/public_html/XGMML/xgmml.xsd");
        writer.writeAttribute("label", "graph0");
        writer.writeAttribute("directed", "1");

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
            writer.writeAttribute("label", nodeId);
            for (Map.Entry<String, ValueHandler<N>> entry : nodeValueHandlers.entrySet())
            {
                writer.writeStartElement("att");
                writer.writeAttribute("name", entry.getKey());
                ValueHandler<N> nodeValueHandler = entry.getValue();
                writer.writeAttribute("type", nodeValueHandler.getType());
                writer.writeAttribute("value", nodeValueHandler.getValue(node.getValue()));
                writer.writeEndElement(); // </att>
            }
            writer.writeEndElement(); // </node>
        }
        int e = 0;
        for (Edge<N, E> edge : graph.edges())
        {
            String edgeId = "e" + e;
            e++;
            writer.writeStartElement("edge");
            writer.writeAttribute("label", edgeId);
            writer.writeAttribute("source", nodeIds.get(edge.source()));
            writer.writeAttribute("target", nodeIds.get(edge.target()));
            for (Map.Entry<String, ValueHandler<E>> entry : edgeValueHandlers.entrySet())
            {
                writer.writeStartElement("att");
                writer.writeAttribute("name", entry.getKey());
                ValueHandler<E> edgeValueHandler = entry.getValue();
                writer.writeAttribute("type", edgeValueHandler.getType());
                writer.writeAttribute("value", edgeValueHandler.getValue(edge.getValue()));
                writer.writeEndElement(); // </att>
            }
            writer.writeEndElement(); // </edge>
        }
        writer.writeEndElement(); // </graph>
        writer.close();
    }

    /**
     * Value handler.
     *
     * @param <V> value type
     */
    public interface ValueHandler<V>
    {

        /**
         * Return the type for this value handler.
         *
         * @param value value
         * @return the type for this value handler
         */
        String getType();

        /**
         * Convert the specified value to a string.
         *
         * @param value value
         * @return the specified value converted to a string
         */
        String getValue(V value);
    }
}