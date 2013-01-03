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
package org.dishevelled.graph.io.ygraphml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.dishevelled.graph.Edge;
import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.io.GraphWriter;

/**
 * Graph writer for the yFiles extension to GraphML.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class YGraphMLWriter
    implements GraphWriter<ShapeNode, PolyLineEdge>
{
    /** XML output factory. */
    private final XMLOutputFactory outputFactory;


    /**
     * Create a new YGraphML writer.
     */
    public YGraphMLWriter()
    {
        outputFactory = XMLOutputFactory.newInstance();
    }


    /** {@inheritDoc} */
    public void write(final Graph<ShapeNode, PolyLineEdge> graph, final File file)
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
    public void write(final Graph<ShapeNode, PolyLineEdge> graph, final OutputStream outputStream)
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
            //throw new IOException(e); jdk 1.6+
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
    private void write(final Graph<ShapeNode, PolyLineEdge> graph, final XMLStreamWriter writer)
        throws IOException, XMLStreamException
    {
        writer.writeStartDocument("1.0");
        //writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("graphml");
        writer.writeDefaultNamespace("http://graphml.graphdrawing.org/xmlns");
        writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        writer.writeNamespace("y", "http://www.yworks.com/xml/graphml");
        writer.writeAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns/graphml http://www.yworks.com/xml/schema/graphml/1.0/ygraphml.xsd");

        writer.writeStartElement("key");
        writer.writeAttribute("id", "k0");
        writer.writeAttribute("for", "node");
        writer.writeAttribute("yfiles.type", "nodegraphics");
        writer.writeEndElement(); // </key>

        writer.writeStartElement("key");
        writer.writeAttribute("id", "k1");
        writer.writeAttribute("for", "edge");
        writer.writeAttribute("yfiles.type", "edgegraphics");
        writer.writeEndElement(); // </key>

        writer.writeStartElement("graph");
        writer.writeAttribute("id", "G");
        writer.writeAttribute("edgedefault", "directed");
        NumberFormat numberFormat = NumberFormat.getInstance();
        if (numberFormat instanceof DecimalFormat)
        {
            ((DecimalFormat) numberFormat).setMinimumFractionDigits(1);
        }
        int n = 0;
        Map<Node<ShapeNode, PolyLineEdge>, String> nodeIds = new HashMap<Node<ShapeNode, PolyLineEdge>, String>(graph.nodeCount());
        for (Node<ShapeNode, PolyLineEdge> node : graph.nodes())
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
            writer.writeStartElement("data");
            writer.writeAttribute("key", "k0");

            ShapeNode shapeNode = node.getValue();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "ShapeNode");

            Fill fill = shapeNode.getFill();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "Fill");
            writer.writeAttribute("color", fill.getColor());
            writer.writeAttribute("transparent", fill.isTransparent() ? "true" : "false");
            writer.writeEndElement(); // <y:Fill>

            BorderStyle borderStyle = shapeNode.getBorderStyle();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "BorderStyle");
            writer.writeAttribute("type", borderStyle.getType());
            writer.writeAttribute("width", numberFormat.format(borderStyle.getWidth()));
            writer.writeAttribute("color", borderStyle.getColor());
            writer.writeEndElement(); // <y:BorderStyle>

            NodeLabel nodeLabel = shapeNode.getNodeLabel();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "NodeLabel");
            writer.writeAttribute("visible", nodeLabel.isVisible() ? "true" : "false");
            writer.writeAttribute("alignment", nodeLabel.getAlignment());
            writer.writeAttribute("fontFamily", nodeLabel.getFontFamily());
            writer.writeAttribute("fontSize", String.valueOf(nodeLabel.getFontSize()));
            writer.writeAttribute("fontStyle", nodeLabel.getFontStyle());
            writer.writeAttribute("textColor", nodeLabel.getTextColor());
            writer.writeAttribute("modelName", nodeLabel.getModelName());
            writer.writeAttribute("modelPosition", nodeLabel.getModelPosition());
            writer.writeAttribute("autoSizePolicy", nodeLabel.getAutoSizePolicy());
            writer.writeCharacters(nodeLabel.getText());
            writer.writeEndElement(); // <y:NodeLabel>

            Shape shape = shapeNode.getShape();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "Shape");
            writer.writeAttribute("type", shape.getType());
            writer.writeEndElement(); // <y:Shape>

            writer.writeEndElement(); // <y:ShapeNode>
            writer.writeEndElement(); // </data>
            writer.writeEndElement(); // </node>
        }
        int e = 0;
        for (Edge<ShapeNode, PolyLineEdge> edge : graph.edges())
        {
            String edgeId = "e" + e;
            e++;
            writer.writeStartElement("edge");
            writer.writeAttribute("id", edgeId);
            writer.writeAttribute("source", nodeIds.get(edge.source()));
            writer.writeAttribute("target", nodeIds.get(edge.target()));
            writer.writeStartElement("data");
            writer.writeAttribute("key", "k1");

            PolyLineEdge polyLineEdge = edge.getValue();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "PolyLineEdge");

            LineStyle lineStyle = polyLineEdge.getLineStyle();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "LineStyle");
            writer.writeAttribute("type", lineStyle.getType());
            writer.writeAttribute("width", numberFormat.format(lineStyle.getWidth()));
            writer.writeAttribute("color", lineStyle.getColor());
            writer.writeEndElement(); // <y:LineStyle>

            Arrows arrows = polyLineEdge.getArrows();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "Arrows");
            writer.writeAttribute("source", arrows.getSource());
            writer.writeAttribute("target", arrows.getTarget());
            writer.writeEndElement(); // <y:Arrows>

            EdgeLabel edgeLabel = polyLineEdge.getEdgeLabel();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "EdgeLabel");
            writer.writeAttribute("visible", edgeLabel.isVisible() ? "true" : "false");
            writer.writeAttribute("alignment", edgeLabel.getAlignment());
            writer.writeAttribute("fontFamily", edgeLabel.getFontFamily());
            writer.writeAttribute("fontSize", String.valueOf(edgeLabel.getFontSize()));
            writer.writeAttribute("fontStyle", edgeLabel.getFontStyle());
            writer.writeAttribute("textColor", edgeLabel.getTextColor());
            writer.writeAttribute("modelName", edgeLabel.getModelName());
            writer.writeAttribute("modelPosition", edgeLabel.getModelPosition());
            writer.writeAttribute("preferredPlacement", edgeLabel.getPreferredPlacement());
            writer.writeAttribute("distance", numberFormat.format(edgeLabel.getDistance()));
            writer.writeAttribute("ratio", numberFormat.format(edgeLabel.getRatio()));
            writer.writeCharacters(edgeLabel.getText());
            writer.writeEndElement(); // <y:EdgeLabel>

            BendStyle bendStyle = polyLineEdge.getBendStyle();
            writer.writeStartElement("http://www.yworks.com/xml/graphml", "BendStyle");
            writer.writeAttribute("smoothed", bendStyle.isSmoothed() ? "true" : "false");
            writer.writeEndElement(); // <y:BendStyle>

            writer.writeEndElement(); // <y:PolyLineEdge>

            writer.writeEndElement(); // </data>
            writer.writeEndElement(); // </edge>
        }
        writer.writeEndElement(); // </graph>
        writer.writeEndElement(); // </graphml>
        writer.close();
   }
}
