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
import java.io.InputStream;
import java.io.IOException;

import java.net.URL;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.stax.StAXContentHandlerBase;
import net.sf.stax.StAXContext;
import net.sf.stax.StAXDelegationContext;
import net.sf.stax.StringElementHandler;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.io.GraphReader;

import org.dishevelled.graph.io.graphml.GraphMLReader;

/**
 * Graph reader for the yFiles extension to GraphML.  The XML schema is
 * <a href="http://www.yworks.com/xml/schema/graphml/1.0/ygraphml.xsd">
 * http://www.yworks.com/xml/schema/graphml/1.0/ygraphml.xsd</a>.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class YGraphMLReader
    implements GraphReader<ShapeNode, PolyLineEdge>
{
    /** GraphML reader to delegate to. */
    private final GraphMLReader<ShapeNode, PolyLineEdge> reader;


    /**
     * Create a new YGraphML reader.
     */
    public YGraphMLReader()
    {
        XMLReader xmlReader = null;
        try
        {
            xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        }
        catch (SAXException e)
        {
            // ...
        }
        catch (ParserConfigurationException e)
        {
            // ...
        }
        reader = new GraphMLReader<ShapeNode, PolyLineEdge>(xmlReader);
        reader.setNodeValueHandler(new ShapeNodeHandler());
        reader.setEdgeValueHandler(new PolyLineEdgeHandler());
    }


    /** {@inheritDoc} */
    public Graph<ShapeNode, PolyLineEdge> read(final File file)
        throws IOException
    {
        return reader.read(file);
    }

    /** {@inheritDoc} */
    public Graph<ShapeNode, PolyLineEdge> read(final InputStream inputStream)
        throws IOException
    {
        return reader.read(inputStream);
    }

    /** {@inheritDoc} */
    public Graph<ShapeNode, PolyLineEdge> read(final URL url)
        throws IOException
    {
        return reader.read(url);
    }

    /**
     * <code>&lt;y:ShapeNode&gt;</code> element handler.
     */
    private class ShapeNodeHandler
        extends StAXContentHandlerBase
    {
        /** Fill. */
        private Fill fill;

        /** Node label. */
        private NodeLabel nodeLabel;

        /** Border style. */
        private BorderStyle borderStyle;

        /** Shape. */
        private Shape shape;

        /** Fill handler. */
        private final FillHandler fillHandler = new FillHandler();

        /** Node label handler. */
        private final NodeLabelHandler nodeLabelHandler = new NodeLabelHandler();

        /** Border style handler. */
        private final BorderStyleHandler borderStyleHandler = new BorderStyleHandler();

        /** Shape handler. */
        private final ShapeHandler shapeHandler = new ShapeHandler();


        /** {@inheritDoc} */
        public void startElement(final String nsURI,
                                 final String localName,
                                 final String qName,
                                 final Attributes attrs,
                                 final StAXDelegationContext dctx)
            throws SAXException
        {
            // TODO:  this doesn't seem right, nsURI & localName should be populated
            //    maybe namespace resolution isn't turned on in the default SAX parser?
            if ("y:Fill".equals(qName))
            {
                dctx.delegate(fillHandler);
            }
            else if ("y:NodeLabel".equals(qName))
            {
                dctx.delegate(nodeLabelHandler);
            }
            else if ("y:BorderStyle".equals(qName))
            {
                dctx.delegate(borderStyleHandler);
            }
            else if ("y:Shape".equals(qName))
            {
                dctx.delegate(shapeHandler);
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
            if ("y:Fill".equals(qName))
            {
                fill = (Fill) result;
            }
            else if ("y:NodeLabel".equals(qName))
            {
                nodeLabel = (NodeLabel) result;
            }
            else if ("y:BorderStyle".equals(qName))
            {
                borderStyle = (BorderStyle) result;
            }
            else if ("y:Shape".equals(qName))
            {
                shape = (Shape) result;
            }
        }

        /** {@inheritDoc} */
        public Object endTree(final StAXContext context)
            throws SAXException
        {
            return new ShapeNode(fill, nodeLabel, borderStyle, shape);
        }

        /**
         * <code>&lt;y:BorderStyle&gt;</code> element handler.
         */
        private class FillHandler
            extends StAXContentHandlerBase
        {
            /** Color attribute. */
            private String color;

            /** Transparent attribute. */
            private boolean transparent;


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:Fill".equals(qName))
                {
                    color = attrs.getValue("color");
                    transparent = "true".equals(attrs.getValue("transparent"));
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new Fill(color, transparent);
            }
        }

        /**
         * <code>&lt;y:BorderStyle&gt;</code> element handler.
         */
        private class NodeLabelHandler
            extends StAXContentHandlerBase
        {
            /** Visible attribute. */
            private boolean visible;

            /** Alignment attribute. */
            private String alignment;

            /** Font family attribute. */
            private String fontFamily;

            /** Font size attribute. */
            private int fontSize;

            /** Font style attribute. */
            private String fontStyle;

            /** Text color attribute. */
            private String textColor;

            /** Model name attribute. */
            private String modelName;

            /** Model position attribute. */
            private String modelPosition;

            /** Auto size policy attribute. */
            private String autoSizePolicy;

            /** Nested text element. */
            private String text;

            /** Text handler. */
            private StringElementHandler textHandler = new StringElementHandler();


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:NodeLabel".equals(qName))
                {
                    visible = "true".equals(attrs.getValue("visible"));
                    alignment = attrs.getValue("alignment");
                    fontFamily = attrs.getValue("fontFamily");
                    fontSize = Integer.valueOf(attrs.getValue("fontSize"));
                    fontStyle = attrs.getValue("fontStyle");
                    textColor = attrs.getValue("textColor");
                    modelName = attrs.getValue("modelName");
                    modelPosition = attrs.getValue("modelPosition");
                    autoSizePolicy = attrs.getValue("autoSizePolicy");
                    dctx.delegate(textHandler);
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
                if ("y:NodeLabel".equals(qName))
                {
                    text = (String) result;
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new NodeLabel(visible, alignment, fontFamily, fontSize, fontStyle, textColor,
                                     modelName, modelPosition, autoSizePolicy, text);
            }
        }

        /**
         * <code>&lt;y:BorderStyle&gt;</code> element handler.
         */
        private class BorderStyleHandler
            extends StAXContentHandlerBase
        {
            /** Type attribute. */
            private String type;

            /** Width attribute. */
            private double width;

            /** Color attribute. */
            private String color;


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:BorderStyle".equals(qName))
                {
                    type = attrs.getValue("type");
                    width = Double.valueOf(attrs.getValue("width"));
                    color = attrs.getValue("color");
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new BorderStyle(type, width, color);
            }
        }

        /**
         * <code>&lt;y:Shape&gt;</code> element handler.
         */
        private class ShapeHandler
            extends StAXContentHandlerBase
        {
            /** Type attribute. */
            private String type;


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:Shape".equals(qName))
                {
                    type = attrs.getValue("type");
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new Shape(type);
            }
        }
    }

    /**
     * <code>&lt;y:PolyLineEdge&gt;</code> element handler.
     */
    private class PolyLineEdgeHandler
        extends StAXContentHandlerBase
    {
        /** Line style. */
        private LineStyle lineStyle;

        /** Arrows. */
        private Arrows arrows;

        /** Edge label. */
        private EdgeLabel edgeLabel;

        /** Bend style. */
        private BendStyle bendStyle;

        /** Line style handler. */
        private final LineStyleHandler lineStyleHandler = new LineStyleHandler();

        /** Arrows handler. */
        private final ArrowsHandler arrowsHandler = new ArrowsHandler();

        /** Edge label handler. */
        private final EdgeLabelHandler edgeLabelHandler = new EdgeLabelHandler();

        /** Bend style handler. */
        private final BendStyleHandler bendStyleHandler = new BendStyleHandler();


        /** {@inheritDoc} */
        public void startElement(final String nsURI,
                                 final String localName,
                                 final String qName,
                                 final Attributes attrs,
                                 final StAXDelegationContext dctx)
            throws SAXException
        {
            if ("y:LineStyle".equals(qName))
            {
                dctx.delegate(lineStyleHandler);
            }
            else if ("y:Arrows".equals(qName))
            {
                dctx.delegate(arrowsHandler);
            }
            else if ("y:EdgeLabel".equals(qName))
            {
                dctx.delegate(edgeLabelHandler);
            }
            else if ("y:BendStyle".equals(qName))
            {
                dctx.delegate(bendStyleHandler);
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
            if ("y:LineStyle".equals(qName))
            {
                lineStyle = (LineStyle) result;
            }
            else if ("y:Arrows".equals(qName))
            {
                arrows = (Arrows) result;
            }
            else if ("y:EdgeLabel".equals(qName))
            {
                edgeLabel = (EdgeLabel) result;
            }
            else if ("y:BendStyle".equals(qName))
            {
                bendStyle = (BendStyle) result;
            }
        }

        /** {@inheritDoc} */
        public Object endTree(final StAXContext context)
            throws SAXException
        {
            return new PolyLineEdge(lineStyle, arrows, edgeLabel, bendStyle);
        }

        /**
         * <code>&lt;y:LineStyle&gt;</code> element handler.
         */
        private class LineStyleHandler
            extends StAXContentHandlerBase
        {
            /** Type attribute. */
            private String type;

            /** Width attribute. */
            private double width;

            /** Color attribute. */
            private String color;


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:LineStyle".equals(qName))
                {
                    type = attrs.getValue("type");
                    width = Double.valueOf(attrs.getValue("width"));
                    color = attrs.getValue("color");
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new LineStyle(type, width, color);
            }
        }

        /**
         * <code>&lt;y:Arrows&gt;</code> element handler.
         */
        private class ArrowsHandler
            extends StAXContentHandlerBase
        {
            /** Source attribute. */
            private String source;

            /** Target attribute. */
            private String target;


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:Arrows".equals(qName))
                {
                    source = attrs.getValue("source");
                    target = attrs.getValue("target");
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new Arrows(source, target);
            }
        }

        /**
         * <code>&lt;y:EdgeLabel&gt;</code> element handler.
         */
        private class EdgeLabelHandler
            extends StAXContentHandlerBase
        {
            /** Visible attribute. */
            private boolean visible;

            /** Alignment attribute. */
            private String alignment;

            /** Font family attribute. */
            private String fontFamily;

            /** Font size attribute. */
            private int fontSize;

            /** Font style attribute. */
            private String fontStyle;

            /** Text color attribute. */
            private String textColor;

            /** Model name attribute. */
            private String modelName;

            /** Model position attribute. */
            private String modelPosition;

            /** Preferred placement attribute. */
            private String preferredPlacement;

            /** Distance attribute. */
            private double distance;

            /** Ratio attribute. */
            private double ratio;

            /** Nested text element. */
            private String text;

            /** Text handler. */
            private StringElementHandler textHandler = new StringElementHandler();


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:EdgeLabel".equals(qName))
                {
                    visible = "true".equals(attrs.getValue("visible"));
                    alignment = attrs.getValue("alignment");
                    fontFamily = attrs.getValue("fontFamily");
                    fontSize = Integer.valueOf(attrs.getValue("fontSize"));
                    fontStyle = attrs.getValue("fontStyle");
                    textColor = attrs.getValue("textColor");
                    modelName = attrs.getValue("modelName");
                    modelPosition = attrs.getValue("modelPosition");
                    preferredPlacement = attrs.getValue("preferredPlacement");
                    distance = Double.valueOf(attrs.getValue("distance"));
                    ratio = Double.valueOf(attrs.getValue("ratio"));
                    dctx.delegate(textHandler);
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
                if ("y:EdgeLabel".equals(qName))
                {
                    text = (String) result;
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new EdgeLabel(visible, alignment, fontFamily, fontSize, fontStyle, textColor,
                                     modelName, modelPosition, preferredPlacement, distance, ratio, text);
            }
        }

        /**
         * <code>&lt;y:BendStyle&gt;</code> element handler.
         */
        private class BendStyleHandler
            extends StAXContentHandlerBase
        {
            /** Smoothed attribute. */
            private boolean smoothed;


            /** {@inheritDoc} */
            public void startElement(final String nsURI,
                                     final String localName,
                                     final String qName,
                                     final Attributes attrs,
                                     final StAXDelegationContext dctx)
                throws SAXException
            {
                if ("y:BendStyle".equals(qName))
                {
                    smoothed = "true".equals(attrs.getValue("smoothed"));
                }
            }

            /** {@inheritDoc} */
            public Object endTree(final StAXContext context)
                throws SAXException
            {
                return new BendStyle(smoothed);
            }
        }
    }
}