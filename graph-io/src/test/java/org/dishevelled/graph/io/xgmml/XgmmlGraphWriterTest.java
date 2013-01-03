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
import java.io.IOException;

import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

import org.dishevelled.graph.io.AbstractGraphWriterTest;
import org.dishevelled.graph.io.GraphWriter;

import org.dishevelled.graph.io.xgmml.XgmmlGraphWriter.ValueHandler;

/**
 * Unit test for XgmmlGraphWriter.
 *
 * @author  Michael Heuer
 */
public final class XgmmlGraphWriterTest
    extends AbstractGraphWriterTest
{
    /** Empty graph. */
    private Graph<String, Double> emptyGraph;

    /** Full graph. */
    private Graph<String, Double> fullGraph;

    /** Double value handler. */
    private ValueHandler<Double> doubleValueHandler = new ValueHandler<Double>()
    {
        /** {@inheritDoc} */
        public String getType()
        {
            return "real";
        }

        /** {@inheritDoc} */
        public String getValue(final Double value)
        {
            return value.toString();
        }
    };

    /** String value handler. */
    private ValueHandler<String> stringValueHandler = new ValueHandler<String>()
    {
        /** {@inheritDoc} */
        public String getType()
        {
            return "string";
        }

        /** {@inheritDoc} */
        public String getValue(final String value)
        {
            return value;
        }
    };


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
        return new XgmmlGraphWriter();
    }

    public void testConstructor()
    {
        XgmmlGraphWriter<String, Double> writer = new XgmmlGraphWriter<String, Double>();
        assertNotNull(writer);
    }

    public void testAddNodeValueHandlerNullName()
    {
        XgmmlGraphWriter<String, Double> writer = new XgmmlGraphWriter<String, Double>();
        try
        {
            writer.addNodeValueHandler(null, stringValueHandler);
            fail("addNodeValueHandler(null, ) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAddNodeValueHandlerNullNodeValueHandler()
    {
        XgmmlGraphWriter<String, Double> writer = new XgmmlGraphWriter<String, Double>();
        try
        {
            writer.addNodeValueHandler("name", null);
            fail("addNodeValueHandler(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAddEdgeValueHandlerNullName()
    {
        XgmmlGraphWriter<String, Double> writer = new XgmmlGraphWriter<String, Double>();
        try
        {
            writer.addEdgeValueHandler(null, doubleValueHandler);
            fail("addEdgeValueHandler(null, ) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAddEdgeValueHandlerNullEdgeValueHandler()
    {
        XgmmlGraphWriter<String, Double> writer = new XgmmlGraphWriter<String, Double>();
        try
        {
            writer.addEdgeValueHandler("name", null);
            fail("addEdgeValueHandler(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testEmptyGraph() throws IOException
    {
        XgmmlGraphWriter<String, Double> writer = new XgmmlGraphWriter<String, Double>();
        writer.addNodeValueHandler("name", stringValueHandler);
        writer.addEdgeValueHandler("weight", doubleValueHandler);
        File file = File.createTempFile("xgmmlGraphWriterTest", "gr");
        writer.write(emptyGraph, file);
        assertTrue(file.exists());
        //dump(file);
    }

    public void testFullGraph() throws IOException
    {
        XgmmlGraphWriter<String, Double> writer = new XgmmlGraphWriter<String, Double>();
        writer.addNodeValueHandler("name", stringValueHandler);
        writer.addEdgeValueHandler("weight", doubleValueHandler);
        File file = File.createTempFile("xgmmlGraphWriterTest", "gr");
        writer.write(fullGraph, file);
        assertTrue(file.exists());
        dump(file);
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