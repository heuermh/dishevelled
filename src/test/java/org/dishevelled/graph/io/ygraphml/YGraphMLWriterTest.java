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
package org.dishevelled.graph.io.ygraphml;

import java.io.File;
import java.io.IOException;

import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

import junit.framework.TestCase;

/**
 * Unit test for YGraphMLWriter.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class YGraphMLWriterTest extends TestCase
{
    /** Empty graph. */
    private Graph<ShapeNode, PolyLineEdge> emptyGraph;

    /** Full graph. */
    private Graph<ShapeNode, PolyLineEdge> fullGraph;


    /** {@inheritDoc} */
    protected void setUp()
    {
        emptyGraph = GraphUtils.createGraph();
        fullGraph = GraphUtils.createGraph(5, 20);

        Fill fill = new Fill("#000000", false);
        BorderStyle borderStyle = new BorderStyle("type", 1.0, "#000000");
        Shape shape = new Shape("type");

        NodeLabel nodeLabel0 = new NodeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "autoSizePolicy", "node0");
        ShapeNode shapeNode0 = new ShapeNode(fill, nodeLabel0, borderStyle, shape);
        NodeLabel nodeLabel1 = new NodeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "autoSizePolicy", "node0");
        ShapeNode shapeNode1 = new ShapeNode(fill, nodeLabel1, borderStyle, shape);
        NodeLabel nodeLabel2 = new NodeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "autoSizePolicy", "node0");
        ShapeNode shapeNode2 = new ShapeNode(fill, nodeLabel2, borderStyle, shape);
        NodeLabel nodeLabel3 = new NodeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "autoSizePolicy", "node0");
        ShapeNode shapeNode3 = new ShapeNode(fill, nodeLabel3, borderStyle, shape);
        NodeLabel nodeLabel4 = new NodeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "autoSizePolicy", "node0");
        ShapeNode shapeNode4 = new ShapeNode(fill, nodeLabel4, borderStyle, shape);

        Node<ShapeNode, PolyLineEdge> node0 = fullGraph.createNode(shapeNode0);
        Node<ShapeNode, PolyLineEdge> node1 = fullGraph.createNode(shapeNode1);
        Node<ShapeNode, PolyLineEdge> node2 = fullGraph.createNode(shapeNode2);
        Node<ShapeNode, PolyLineEdge> node3 = fullGraph.createNode(shapeNode3);
        Node<ShapeNode, PolyLineEdge> node4 = fullGraph.createNode(shapeNode4);

        LineStyle lineStyle = new LineStyle("type", 1.0, "#000000");
        Arrows arrows = new Arrows("source", "target");
        BendStyle bendStyle = new BendStyle(false);

        EdgeLabel edgeLabel0 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge0");
        PolyLineEdge polyLineEdge0 = new PolyLineEdge(lineStyle, arrows, edgeLabel0, bendStyle);
        EdgeLabel edgeLabel1 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge1");
        PolyLineEdge polyLineEdge1 = new PolyLineEdge(lineStyle, arrows, edgeLabel1, bendStyle);
        EdgeLabel edgeLabel2 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge2");
        PolyLineEdge polyLineEdge2 = new PolyLineEdge(lineStyle, arrows, edgeLabel2, bendStyle);
        EdgeLabel edgeLabel3 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge3");
        PolyLineEdge polyLineEdge3 = new PolyLineEdge(lineStyle, arrows, edgeLabel3, bendStyle);
        EdgeLabel edgeLabel4 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge4");
        PolyLineEdge polyLineEdge4 = new PolyLineEdge(lineStyle, arrows, edgeLabel4, bendStyle);
        EdgeLabel edgeLabel5 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge5");
        PolyLineEdge polyLineEdge5 = new PolyLineEdge(lineStyle, arrows, edgeLabel5, bendStyle);
        EdgeLabel edgeLabel6 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge6");
        PolyLineEdge polyLineEdge6 = new PolyLineEdge(lineStyle, arrows, edgeLabel6, bendStyle);
        EdgeLabel edgeLabel7 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge7");
        PolyLineEdge polyLineEdge7 = new PolyLineEdge(lineStyle, arrows, edgeLabel7, bendStyle);
        EdgeLabel edgeLabel8 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge8");
        PolyLineEdge polyLineEdge8 = new PolyLineEdge(lineStyle, arrows, edgeLabel8, bendStyle);
        EdgeLabel edgeLabel9 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge9");
        PolyLineEdge polyLineEdge9 = new PolyLineEdge(lineStyle, arrows, edgeLabel9, bendStyle);
        EdgeLabel edgeLabel10 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge10");
        PolyLineEdge polyLineEdge10 = new PolyLineEdge(lineStyle, arrows, edgeLabel10, bendStyle);
        EdgeLabel edgeLabel11 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge11");
        PolyLineEdge polyLineEdge11 = new PolyLineEdge(lineStyle, arrows, edgeLabel11, bendStyle);
        EdgeLabel edgeLabel12 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge12");
        PolyLineEdge polyLineEdge12 = new PolyLineEdge(lineStyle, arrows, edgeLabel12, bendStyle);
        EdgeLabel edgeLabel13 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge13");
        PolyLineEdge polyLineEdge13 = new PolyLineEdge(lineStyle, arrows, edgeLabel13, bendStyle);
        EdgeLabel edgeLabel14 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge14");
        PolyLineEdge polyLineEdge14 = new PolyLineEdge(lineStyle, arrows, edgeLabel14, bendStyle);
        EdgeLabel edgeLabel15 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge15");
        PolyLineEdge polyLineEdge15 = new PolyLineEdge(lineStyle, arrows, edgeLabel15, bendStyle);
        EdgeLabel edgeLabel16 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge16");
        PolyLineEdge polyLineEdge16 = new PolyLineEdge(lineStyle, arrows, edgeLabel16, bendStyle);
        EdgeLabel edgeLabel17 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge17");
        PolyLineEdge polyLineEdge17 = new PolyLineEdge(lineStyle, arrows, edgeLabel17, bendStyle);
        EdgeLabel edgeLabel18 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge18");
        PolyLineEdge polyLineEdge18 = new PolyLineEdge(lineStyle, arrows, edgeLabel18, bendStyle);
        EdgeLabel edgeLabel19 = new EdgeLabel(true, "alignment", "fontFamily", 10, "fontStyle", "textColor", "modelName", "modelPosition", "preferredPlacement", 1.0, 0.5, "edge19");
        PolyLineEdge polyLineEdge19 = new PolyLineEdge(lineStyle, arrows, edgeLabel19, bendStyle);

        fullGraph.createEdge(node0, node1, polyLineEdge0);
        fullGraph.createEdge(node0, node2, polyLineEdge1);
        fullGraph.createEdge(node0, node3, polyLineEdge2);
        fullGraph.createEdge(node0, node4, polyLineEdge3);
        fullGraph.createEdge(node1, node0, polyLineEdge4);
        fullGraph.createEdge(node1, node2, polyLineEdge5);
        fullGraph.createEdge(node1, node3, polyLineEdge6);
        fullGraph.createEdge(node1, node4, polyLineEdge7);
        fullGraph.createEdge(node2, node1, polyLineEdge8);
        fullGraph.createEdge(node2, node0, polyLineEdge9);
        fullGraph.createEdge(node2, node3, polyLineEdge10);
        fullGraph.createEdge(node2, node4, polyLineEdge11);
        fullGraph.createEdge(node3, node1, polyLineEdge12);
        fullGraph.createEdge(node3, node2, polyLineEdge13);
        fullGraph.createEdge(node3, node0, polyLineEdge14);
        fullGraph.createEdge(node3, node4, polyLineEdge15);
        fullGraph.createEdge(node4, node1, polyLineEdge16);
        fullGraph.createEdge(node4, node2, polyLineEdge17);
        fullGraph.createEdge(node4, node3, polyLineEdge18);
        fullGraph.createEdge(node4, node0, polyLineEdge19);
    }

    public void testEmptyGraph() throws IOException
    {
        YGraphMLWriter writer = new YGraphMLWriter();
        File file = File.createTempFile("yGraphMLWriterTest", "xml");
        writer.write(emptyGraph, file);
        assertTrue(file.exists());
        //dump(file);
    }

    public void testFullGraph() throws IOException
    {
        YGraphMLWriter writer = new YGraphMLWriter();
        File file = File.createTempFile("yGraphMLWriterTest", "xml");
        writer.write(fullGraph, file);
        assertTrue(file.exists());
        //dump(file);
    }

    public void testRoundTripEmptyGraph() throws IOException
    {
        YGraphMLWriter writer = new YGraphMLWriter();
        File file = File.createTempFile("yGraphMLWriterTest", "xml");
        writer.write(emptyGraph, file);
        assertTrue(file.exists());
        YGraphMLReader reader = new YGraphMLReader();
        Graph<ShapeNode, PolyLineEdge> graph = reader.read(file);
        assertNotNull(graph);
        assertEquals(emptyGraph.nodeCount(), graph.nodeCount());
        assertEquals(emptyGraph.edgeCount(), graph.edgeCount());
    }

    public void testRoundTripFullGraph() throws IOException
    {
        YGraphMLWriter writer = new YGraphMLWriter();
        File file = File.createTempFile("yGraphMLWriterTest", "xml");
        writer.write(fullGraph, file);
        assertTrue(file.exists());
        YGraphMLReader reader = new YGraphMLReader();
        Graph<ShapeNode, PolyLineEdge> graph = reader.read(file);
        assertNotNull(graph);
        assertEquals(fullGraph.nodeCount(), graph.nodeCount());
        assertEquals(fullGraph.edgeCount(), graph.edgeCount());
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
