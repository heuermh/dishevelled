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

import java.io.InputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.dishevelled.graph.Graph;

/**
 * Unit test for YGraphMLReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class YGraphMLReaderTest
    extends TestCase
{

    /**
     * Close the specified input stream quietly.
     *
     * @param inputStream input stream to close
     */
    private void closeQuietly(final InputStream inputStream)
    {
        try
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        catch (IOException e)
        {
            // ignore
        }
    }

    public void testEmptyGraph()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("emptyGraph.xml");
            YGraphMLReader reader = new YGraphMLReader();
            Graph<ShapeNode, PolyLineEdge> graph = reader.read(inputStream);
            assertNotNull(graph);
            assertEquals(0, graph.nodeCount());
            assertEquals(0, graph.edgeCount());
        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testGraphNoData()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("graph0.xml");
            YGraphMLReader reader = new YGraphMLReader();
            Graph<ShapeNode, PolyLineEdge> graph = reader.read(inputStream);
            assertNotNull(graph);
            assertEquals(5, graph.nodeCount());
            assertEquals(20, graph.edgeCount());
            for (ShapeNode shapeNode : graph.nodeValues())
            {
                assertEquals(null, shapeNode);
            }
            for (PolyLineEdge polyLineEdge : graph.edgeValues())
            {
                assertEquals(null, polyLineEdge);
            }
        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testGraphOneNode()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("graph1.xml");
            YGraphMLReader reader = new YGraphMLReader();
            Graph<ShapeNode, PolyLineEdge> graph = reader.read(inputStream);
            assertNotNull(graph);
            assertEquals(1, graph.nodeCount());
            ShapeNode shapeNode = graph.nodeValues().iterator().next();
            assertNotNull(shapeNode);
            assertEquals("#FFFFCC", shapeNode.getFill().getColor());
            assertFalse(shapeNode.getFill().isTransparent());
            assertEquals("line", shapeNode.getBorderStyle().getType());
            assertEquals(1.0d, shapeNode.getBorderStyle().getWidth());
            assertEquals("#000000", shapeNode.getBorderStyle().getColor());
            assertTrue(shapeNode.getNodeLabel().isVisible());
            assertEquals("center", shapeNode.getNodeLabel().getAlignment());
            assertEquals("Dialog", shapeNode.getNodeLabel().getFontFamily());
            assertEquals(12, shapeNode.getNodeLabel().getFontSize());
            assertEquals("plain", shapeNode.getNodeLabel().getFontStyle());
            assertEquals("#000000", shapeNode.getNodeLabel().getTextColor());
            assertEquals("internal", shapeNode.getNodeLabel().getModelName());
            assertEquals("c", shapeNode.getNodeLabel().getModelPosition());
            assertEquals("center", shapeNode.getNodeLabel().getAutoSizePolicy());
            assertEquals("text", shapeNode.getNodeLabel().getText());
            assertEquals("roundrectangle", shapeNode.getShape().getType());
        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testGraphTwoNodesOneEdge()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("graph2.xml");
            YGraphMLReader reader = new YGraphMLReader();
            Graph<ShapeNode, PolyLineEdge> graph = reader.read(inputStream);
            assertNotNull(graph);
            assertEquals(2, graph.nodeCount());
            ShapeNode shapeNode = graph.nodeValues().iterator().next();
            assertNotNull(shapeNode);
            assertEquals("#FFFFCC", shapeNode.getFill().getColor());
            assertFalse(shapeNode.getFill().isTransparent());
            assertEquals("line", shapeNode.getBorderStyle().getType());
            assertEquals(1.0d, shapeNode.getBorderStyle().getWidth());
            assertEquals("#000000", shapeNode.getBorderStyle().getColor());
            assertTrue(shapeNode.getNodeLabel().isVisible());
            assertEquals("center", shapeNode.getNodeLabel().getAlignment());
            assertEquals("Dialog", shapeNode.getNodeLabel().getFontFamily());
            assertEquals(12, shapeNode.getNodeLabel().getFontSize());
            assertEquals("plain", shapeNode.getNodeLabel().getFontStyle());
            assertEquals("#000000", shapeNode.getNodeLabel().getTextColor());
            assertEquals("internal", shapeNode.getNodeLabel().getModelName());
            assertEquals("c", shapeNode.getNodeLabel().getModelPosition());
            assertEquals("center", shapeNode.getNodeLabel().getAutoSizePolicy());
            assertTrue("node0".equals(shapeNode.getNodeLabel().getText())
                       || "node1".equals(shapeNode.getNodeLabel().getText()));
            assertEquals("roundrectangle", shapeNode.getShape().getType());

            PolyLineEdge polyLineEdge = graph.edgeValues().iterator().next();
            assertNotNull(polyLineEdge);
            assertEquals("line", polyLineEdge.getLineStyle().getType());
            assertEquals(1.0d, polyLineEdge.getLineStyle().getWidth());
            assertEquals("#000000", polyLineEdge.getLineStyle().getColor());
            assertEquals("none", polyLineEdge.getArrows().getSource());
            assertEquals("standard", polyLineEdge.getArrows().getTarget());
            assertTrue(polyLineEdge.getEdgeLabel().isVisible());
            assertEquals("center", polyLineEdge.getEdgeLabel().getAlignment());
            assertEquals("Dialog", polyLineEdge.getEdgeLabel().getFontFamily());
            assertEquals(12, polyLineEdge.getEdgeLabel().getFontSize());
            assertEquals("plain", polyLineEdge.getEdgeLabel().getFontStyle());
            assertEquals("#000000", polyLineEdge.getEdgeLabel().getTextColor());
            assertEquals("free", polyLineEdge.getEdgeLabel().getModelName());
            assertEquals("anywhere", polyLineEdge.getEdgeLabel().getModelPosition());
            assertEquals("target", polyLineEdge.getEdgeLabel().getPreferredPlacement());
            assertEquals(2.0d, polyLineEdge.getEdgeLabel().getDistance());
            assertEquals(0.5d, polyLineEdge.getEdgeLabel().getRatio());
            assertFalse(polyLineEdge.getBendStyle().isSmoothed());
        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }
}