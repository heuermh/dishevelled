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

import java.net.URL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.stax.SAX2StAXAdaptor;
import net.sf.stax.StAXContentHandler;
import net.sf.stax.StAXContentHandlerBase;
import net.sf.stax.StAXContext;
import net.sf.stax.StAXDelegationContext;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;

import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

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

    /** Optional node value handler.  */
    private StAXContentHandler nodeValueHandler;

    /** Optional edge value handler. */
    private StAXContentHandler edgeValueHandler;


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


    /**
     * Set the node value handler for this GraphML reader to <code>nodeValueHandler</code>.
     * <p>
     * The specified node value handler will be delegated to with the XML subtree within the
     * <code>&lt;node&gt;</code> element, typically with one or more <code>&lt;data&gt;</code>
     * elements.
     * </p>
     *
     * @param nodeValueHandler node value handler
     */
    public void setNodeValueHandler(final StAXContentHandler nodeValueHandler)
    {
        this.nodeValueHandler = nodeValueHandler;
    }

    /**
     * Set the edge value handler for this GraphML reader to <code>edgeValueHandler</code>.
     * <p>
     * The specified edge value handler will be delegated to with the XML subtree within the
     * <code>&lt;edge&gt;</code> element, typically with one or more <code>&lt;data&gt;</code>
     * elements.
     * </p>
     *
     * @param edgeValueHandler edge value handler
     */
    public void setEdgeValueHandler(final StAXContentHandler edgeValueHandler)
    {
        this.edgeValueHandler = edgeValueHandler;
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
            //throw new IOException(e); jdk 1.6+
            throw new IOException(e.getMessage());
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
        /** Graph. */
        private final Graph<N, E> graph = GraphUtils.createGraph();

        /** Nodes keyed by id. */
        private final Map<String, Node<N, E>> nodes = new HashMap<String, Node<N, E>>();

        /** List of deferred edges. */
        private final List<EdgePlaceholder> deferredEdges = new ArrayList<EdgePlaceholder>();

        /** Node handler. */
        private final NodeHandler nodeHandler = new NodeHandler();

        /** Edge handler. */
        private final EdgeHandler edgeHandler = new EdgeHandler();


        /** {@inheritDoc} */
        public void startElement(final String nsURI,
                                 final String localName,
                                 final String qName,
                                 final Attributes attrs,
                                 final StAXDelegationContext dctx)
            throws SAXException
        {
            if ("node".equals(qName))
            {
                dctx.delegate(nodeHandler);
            }
            else if ("edge".equals(qName))
            {
                dctx.delegate(edgeHandler);
            }
        }

        /** {@inheritDoc} */
        public Object endTree(final StAXContext context)
            throws SAXException
        {
            for (EdgePlaceholder edge : deferredEdges)
            {
                Node<N, E> source = nodes.get(edge.getSourceId());
                Node<N, E> target = nodes.get(edge.getTargetId());
                if (source != null && target != null)
                {
                    graph.createEdge(source, target, edge.getValue());
                }
                else
                {
                    throw new SAXException("could not resolve deferred edge, id=" + edge.getEdgeId()
                                           + " source=" + edge.getSourceId() + " target=" + edge.getTargetId());
                }
            }
            return graph;
        }

        /**
         * Return the graph for this graph element handler.
         *
         * @return the graph for this graph element handler
         */
        Graph<N, E> getGraph()
        {
            return graph;
        }

        /**
         * Node element handler.
         */
        private class NodeHandler
            extends StAXContentHandlerBase
        {
            /** Node id. */
            private String id;

            /** Node value. */
            private N value;


            /** {@inheritDoc} */
            public void startTree(final StAXContext context)
                throws SAXException
            {
                id = null;
                value = null;
            }

            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("node".equals(qName))
                {
                    id = attrs.getValue("id");
                }
                else
                {
                    if (nodeValueHandler != null)
                    {
                        dctx.delegate(nodeValueHandler);
                    }
                }
            }

            /** {@inheritDoc} */
            public void endElement(final String nsURI,
                                   final String localName,
                                   final String qName,
                                   final Object result,
                                   final StAXContext context)
                throws SAXException
            {
                if ("node".equals(qName))
                {
                    Node<N, E> node = graph.createNode(value);
                    nodes.put(id, node);
                }
                else
                {
                    value = (N) result;
                }
            }
        }

        /**
         * Edge element handler.
         */
        private class EdgeHandler
            extends StAXContentHandlerBase
        {
            /** Edge id. */
            private String edgeId;

            /** Source id. */
            private String sourceId;

            /** Target id. */
            private String targetId;

            /** Edge value. */
            private E value;


            /** {@inheritDoc} */
            public void startTree(final StAXContext context)
                throws SAXException
            {
                edgeId = null;
                sourceId = null;
                targetId = null;
                value = null;
            }

            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("edge".equals(qName))
                {
                    edgeId = attrs.getValue("id");
                    sourceId = attrs.getValue("source");
                    targetId = attrs.getValue("target");
                }
                else
                {
                    if (edgeValueHandler != null)
                    {
                        dctx.delegate(edgeValueHandler);
                    }
                }
            }

            /** {@inheritDoc} */
            public void endElement(final String nsURI,
                                   final String localName,
                                   final String qName,
                                   final Object result,
                                   final StAXContext context)
                throws SAXException
            {
                if ("edge".equals(qName))
                {
                    Node<N, E> source = nodes.get(sourceId);
                    Node<N, E> target = nodes.get(targetId);
                    if (source != null && target != null)
                    {
                        graph.createEdge(source, target, value);
                    }
                    else
                    {
                        deferredEdges.add(new EdgePlaceholder(edgeId, sourceId, targetId, value));
                    }
                }
                else
                {
                    value = (E) result;
                }
            }
        }

        /**
         * Edge placeholder.
         */
        private class EdgePlaceholder
        {
            /** Edge id. */
            private final String edgeId;

            /** Source id. */
            private final String sourceId;

            /** Target id. */
            private final String targetId;

            /** Edge value. */
            private final E value;


            /**
             * Create a new edge placeholder.
             *
             * @param edgeId edge id
             * @param sourceId source id
             * @param targetId target id
             * @param value edge value
             */
            EdgePlaceholder(final String edgeId, final String sourceId, final String targetId, final E value)
            {
                this.edgeId = edgeId;
                this.sourceId = sourceId;
                this.targetId = targetId;
                this.value = value;
            }

            /**
             * Return the edge id.
             *
             * @return the edge id
             */
            String getEdgeId()
            {
                return edgeId;
            }

            /**
             * Return the source id.
             *
             * @return the source id
             */
            String getSourceId()
            {
                return sourceId;
            }

            /**
             * Return the target id.
             *
             * @return the target id
             */
            String getTargetId()
            {
                return targetId;
            }

            /**
             * Return the edge value.
             *
             * @return the edge value
             */
            E getValue()
            {
                return value;
            }
        }
    }
}