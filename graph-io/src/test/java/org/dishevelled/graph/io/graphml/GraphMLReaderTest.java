/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008 held jointly by the individual authors.

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

import java.io.InputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.stax.DoubleElementHandler;
import net.sf.stax.StringElementHandler;

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.io.AbstractGraphReaderTest;
import org.dishevelled.graph.io.GraphReader;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Unit test for GraphMLReader.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphMLReaderTest
    extends AbstractGraphReaderTest
{
    /** XML Reader. */
    private XMLReader xmlReader;


    /** {@inheritDoc} */
    protected void setUp()
        throws Exception
    {
        try
        {
            xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        }
        catch (SAXException e)
        {
            fail(e.getMessage());
        }
        catch (ParserConfigurationException e)
        {
            fail(e.getMessage());
        }
    }

    /** {@inheritDoc} */
    protected <N, E> GraphReader<N, E> createGraphReader()
    {
        return new GraphMLReader(xmlReader);
    }

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

    public void testConstructor()
    {
        GraphMLReader<String, Double> graphReader0 = new GraphMLReader<String, Double>(xmlReader);
        assertNotNull(graphReader0);

        try
        {
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testEmpty()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("empty.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            Graph<String, Double> graph = graphReader.read(inputStream);
            fail("read(empty.xml) expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testEmptyGraphml()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("emptyGraphml.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertTrue(graph.isEmpty());
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

    public void testEmptyGraph()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("emptyGraph.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertTrue(graph.isEmpty());
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

    public void testGraph0()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("graph0.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertFalse(graph.isEmpty());
            assertEquals(5, graph.nodeCount());
            assertEquals(20, graph.edgeCount());
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

    public void testGraph1()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("graph1.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertFalse(graph.isEmpty());
            assertEquals(5, graph.nodeCount());
            assertEquals(20, graph.edgeCount());
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

    public void testUnresolvedNodeId()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("unresolvedNodeId.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            Graph<String, Double> graph = graphReader.read(inputStream);
            fail("read(unresolvedNodeId.xml) expected IOException");
        }
        catch (IOException e)
        {
            // expected
        }
        finally
        {
            closeQuietly(inputStream);
        }
    }

    public void testIgnorableValues()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("ignorableValues.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertFalse(graph.isEmpty());
            assertEquals(5, graph.nodeCount());
            assertEquals(20, graph.edgeCount());
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

    public void testDataElementValues()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("dataElementValues.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);            
            graphReader.setNodeValueHandler(new StringElementHandler());
            graphReader.setEdgeValueHandler(new DoubleElementHandler());

            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertFalse(graph.isEmpty());
            assertEquals(5, graph.nodeCount());
            assertEquals(20, graph.edgeCount());

            for (String nodeValue : graph.nodeValues())
            {
                assertTrue(nodeValue.startsWith("Node"));
            }
            for (Double edgeValue : graph.edgeValues())
            {
                assertTrue(edgeValue >= 1.0d);
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

    public void testDataElementValuesNoValueHandlers()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("dataElementValues.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);

            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertFalse(graph.isEmpty());
            assertEquals(5, graph.nodeCount());
            assertEquals(20, graph.edgeCount());

            for (String nodeValue : graph.nodeValues())
            {
                assertEquals(null, nodeValue);
            }
            for (Double edgeValue : graph.edgeValues())
            {
                assertEquals(null, edgeValue);
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

    public void testDataElementValuesNullValueHandlers()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = getClass().getResourceAsStream("dataElementValues.xml");
            GraphMLReader<String, Double> graphReader = new GraphMLReader<String, Double>(xmlReader);
            graphReader.setNodeValueHandler(null);
            graphReader.setEdgeValueHandler(null);

            Graph<String, Double> graph = graphReader.read(inputStream);
            assertNotNull(graph);
            assertFalse(graph.isEmpty());
            assertEquals(5, graph.nodeCount());
            assertEquals(20, graph.edgeCount());

            for (String nodeValue : graph.nodeValues())
            {
                assertEquals(null, nodeValue);
            }
            for (Double edgeValue : graph.edgeValues())
            {
                assertEquals(null, edgeValue);
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
}