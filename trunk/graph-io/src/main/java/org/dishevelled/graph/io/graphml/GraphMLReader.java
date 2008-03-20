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

import java.net.URL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.stax.SAX2StAXAdaptor;
import net.sf.stax.StAXContentHandlerBase;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXParseException;

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.io.GraphReader;

/**
 * GraphML reader.
 *
 * @param <N> node value type
 * @param <E> edge value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GraphMLReader<N, E>
    implements GraphReader<N, E>
{
    /** XML reader. */
    private final XMLReader xmlReader;


    /**
     * Create a new GraphML reader with the specified XML reader.
     *
     * @param xmlReader XML reader, must not be null
     */
    public GraphMLReader(final XMLReader xmlReader)
    {
        if (xmlReader == null)
        {
            throw new IllegalArgumentException("xmlReader must not be null");
        }
        this.xmlReader = xmlReader;
    }


    /** {@inheritDoc} */
    public Graph<N, E> read(final File file)
        throws IOException
    {
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }
        BufferedInputStream inputStream = null;
        try
        {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            return read(inputStream);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
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
    }

    /** {@inheritDoc} */
    public Graph<N, E> read(final InputStream inputStream)
        throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException("inputStream must not be null");
        }
        InputSource inputSource = new InputSource(inputStream);
        GraphHandler graphHandler = new GraphHandler();
        ContentHandler contentHandler = new SAX2StAXAdaptor(graphHandler);
        xmlReader.setContentHandler(contentHandler);
        try
        {
            xmlReader.parse(inputSource);
            return graphHandler.getGraph();
        }
        catch (SAXException e)
        {
            throw new IOException(e);
        }
    }

    /** {@inheritDoc} */
    public Graph<N, E> read(final URL url)
        throws IOException
    {
        if (url == null)
        {
            throw new IllegalArgumentException("url must not be null");
        }
        InputStream inputStream = null;
        try
        {
            inputStream = url.openStream();
            return read(inputStream);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
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
    }

    /**
     * Graph element handler.
     */
    private class GraphHandler
        extends StAXContentHandlerBase
    {

        /**
         * Return the graph for this graph element handler.
         *
         * @return the graph for this graph element handler
         */
        Graph<N, E> getGraph()
        {
            return null;
        }
    }
}