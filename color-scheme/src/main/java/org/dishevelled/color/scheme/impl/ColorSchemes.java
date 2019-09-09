/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.impl;

import java.awt.Color;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParserFactory;

import net.sf.stax.SAX2StAXAdaptor;
import net.sf.stax.StAXContentHandlerBase;
import net.sf.stax.StAXContext;
import net.sf.stax.StAXDelegationContext;

import org.apache.commons.io.IOUtils;

import org.dishevelled.color.scheme.ColorFactory;
import org.dishevelled.color.scheme.ColorScheme;
import org.dishevelled.color.scheme.factory.DefaultColorFactory;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;

/**
 * Color schemes.
 *
 * @author  Michael Heuer
 */
public final class ColorSchemes
{
    /** Color scheme pattern. */
    private static final Pattern COLOR_SCHEME = Pattern.compile("^([a-z]+)-([a-z0-9-]+)-([0-9]+)$");

    /** Color factory. */
    private static final ColorFactory COLOR_FACTORY = new DefaultColorFactory();

    /** Default alpha, 1.0f. @since 3.1. */
    static final float DEFAULT_ALPHA = 1.0f;


    /**
     * Private no-arg constructor.
     */
    private ColorSchemes()
    {
        // empty
    }


    /**
     * Parse the specified value into a color scheme.
     *
     * @param value value to parse
     * @return the specified value parsed into a color scheme
     */
    public static ColorScheme parseColorScheme(final String value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("value must not be null");
        }
        Matcher matcher = COLOR_SCHEME.matcher(value);
        if (!matcher.matches())
        {
            throw new IllegalArgumentException("color scheme " + value + " not valid format");
        }
        String type = matcher.group(1);
        String name = matcher.group(2);
        int colors = Integer.parseInt(matcher.group(3));
        if ("discrete".equals(type))
        {
            return getDiscreteColorScheme(name, colors);
        }
        else if ("continuous".equals(type))
        {
            return getContinuousColorScheme(name, colors);
        }
        else
        {
            throw new IllegalArgumentException("color scheme type " + type + " not valid type");
        }
    }

    /**
     * Validate alpha is in range [0.0, 1.0].
     *
     * @param alpha alpha to validate
     * @throws IllegalArgumentException if alpha is not in range [0.0, 1.0]
     */
    static void validateAlpha(final float alpha) {
        if (alpha < 0.0 || alpha > 1.0)
        {
            throw new IllegalArgumentException("alpha " + alpha + " not in range [0.0, 1.0]");
        }
    }

    /**
     * Create and return a discrete color scheme with the specified name and number of colors, if any.
     * The color schemes are defined in a simple XML format and read from the color-scheme library classpath.
     *
     * @since 2.1
     * @param name name
     * @param colors number of colors
     * @return a discrete color scheme with the specified name and number of colors, or
     *    <code>null</code> if no such discrete color scheme exists
     */
    public static ColorScheme getDiscreteColorScheme(final String name,
                                                     final int colors)
    {
        return getDiscreteColorScheme(name, colors, DEFAULT_ALPHA);
    }

    /**
     * Create and return a discrete color scheme with the specified name, number of colors, and alpha,
     * if any. The color schemes are defined in a simple XML format and read from the color-scheme library
     * classpath.
     *
     * @since 3.1
     * @param name name
     * @param colors number of colors
     * @param alpha alpha, in range [0.0, 1.0]
     * @return a discrete color scheme with the specified name and number of colors, or
     *    <code>null</code> if no such discrete color scheme exists
     */
    public static ColorScheme getDiscreteColorScheme(final String name,
                                                     final int colors,
                                                     final float alpha)
    {
        validateAlpha(alpha);
        InputStream inputStream = null;
        ColorScheme colorScheme = null;
        try
        {
            inputStream = ColorSchemes.class.getResourceAsStream(name + "-" + colors + ".xml");
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            InputSource inputSource = new InputSource(inputStream);
            ColorSchemeHandler colorSchemeHandler = new ColorSchemeHandler(alpha);
            ContentHandler contentHandler = new SAX2StAXAdaptor(colorSchemeHandler);
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(inputSource);
            if ("divergent".equals(colorSchemeHandler.getType()))
            {
                colorScheme = new DiscreteDivergentColorScheme(colorSchemeHandler.getName(),
                                                               colorSchemeHandler.getColors(), 0.0d, 0.5d, 1.0d,
                                                               COLOR_FACTORY);
            }
            else
            {
                colorScheme = new DiscreteColorScheme(colorSchemeHandler.getName(),
                                                      colorSchemeHandler.getColors(), 0.0d, 1.0d,
                                                      COLOR_FACTORY);
            }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
        return colorScheme;
    }

    /**
     * Create and return a continuous color scheme with the specified name and number of colors, if any.
     * The color schemes are defined in a simple XML format and read from the color-scheme library classpath.
     *
     * @since 2.1
     * @param name name
     * @param colors number of colors
     * @return a continuous color scheme with the specified name and number of colors, or
     *    <code>null</code> if no such continuous color scheme exists
     */
    public static ColorScheme getContinuousColorScheme(final String name,
                                                       final int colors)
    {
        return getContinuousColorScheme(name, colors, DEFAULT_ALPHA);
    }

    /**
     * Create and return a continuous color scheme with the specified name, number of colors, and alpha,
     * if any. The color schemes are defined in a simple XML format and read from the color-scheme library
     * classpath.
     *
     * @since 3.1
     * @param name name
     * @param colors number of colors
     * @param alpha, alpha in range [0.0, 1.0]
     * @return a continuous color scheme with the specified name and number of colors, or
     *    <code>null</code> if no such continuous color scheme exists
     */
    public static ColorScheme getContinuousColorScheme(final String name,
                                                       final int colors,
                                                       final float alpha)
    {
        validateAlpha(alpha);
        InputStream inputStream = null;
        ColorScheme colorScheme = null;
        try
        {
            inputStream = ColorSchemes.class.getResourceAsStream(name + "-" + colors + ".xml");
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            InputSource inputSource = new InputSource(inputStream);
            ColorSchemeHandler colorSchemeHandler = new ColorSchemeHandler(alpha);
            ContentHandler contentHandler = new SAX2StAXAdaptor(colorSchemeHandler);
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(inputSource);
            if ("divergent".equals(colorSchemeHandler.getType()))
            {
                colorScheme = new ContinuousDivergentColorScheme(colorSchemeHandler.getName(),
                                                                 colorSchemeHandler.getColors(), 0.0d, 0.5d, 1.0d,
                                                                 COLOR_FACTORY);
            }
            else
            {
                colorScheme = new ContinuousColorScheme(colorSchemeHandler.getName(),
                                                        colorSchemeHandler.getColors(), 0.0d, 1.0d,
                                                        COLOR_FACTORY);
            }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
        return colorScheme;
    }

    /**
     * Color scheme handler.
     */
    private static final class ColorSchemeHandler
        extends StAXContentHandlerBase
    {
        /** Name. */
        private String name;

        /** Type. */
        private String type;

        /** List of colors. */
        private final List<Color> colors = new ArrayList<Color>();

        /** Color handler. */
        private final ColorHandler colorHandler;


        /**
         * Create a new color scheme handler with the specified alpha.
         *
         * @param alpha alpha
         */
        ColorSchemeHandler(final float alpha)
        {
            colorHandler = new ColorHandler(alpha);
        }


        @Override
        public void startElement(final String nsURI,
                                 final String localName,
                                 final String qName,
                                 final Attributes attrs,
                                 final StAXDelegationContext dctx)
            throws SAXException
        {
            if ("color".equals(qName))
            {
                dctx.delegate(colorHandler);
            }
            else if ("color-scheme".equals(qName))
            {
                name = attrs.getValue("name");
                type = attrs.getValue("type");
            }
        }

        @Override
        public void endElement(final String nsURI,
                               final String localName,
                               final String qName,
                               final Object result,
                               final StAXContext context)
            throws SAXException
        {
            if ("color".equals(qName))
            {
                Color color = (Color) result;
                colors.add(color);
            }
        }

        /**
         * Return the name for this color scheme handler.
         *
         * @return the name for this color scheme handler
         */
        String getName()
        {
            return name;
        }

        /**
         * Return the type for this color scheme handler.
         *
         * @return the type for this color scheme handler
         */
        String getType()
        {
            return type;
        }

        /**
         * Return the list of colors for this color scheme handler.
         *
         * @return the list of colors for this color scheme handler
         */
        List<Color> getColors()
        {
            return colors;
        }
    }

    /**
     * Color handler.
     */
    private static final class ColorHandler
        extends StAXContentHandlerBase
    {
        /** Color. */
        private Color color;

        /** Alpha. */
        private final float alpha;


        /**
         * Create a new color handler with the specified alpha.
         *
         * @param alpha alpha
         */
        ColorHandler(final float alpha)
        {
            this.alpha = alpha;
        }


        @Override
        public void startElement(final String nsURI,
                                 final String localName,
                                 final String qName,
                                 final Attributes attrs,
                                 final StAXDelegationContext dctx)
            throws SAXException
        {
            int r = Integer.parseInt(attrs.getValue("red"));
            int g = Integer.parseInt(attrs.getValue("green"));
            int b = Integer.parseInt(attrs.getValue("blue"));
            //float a = Float.parseFloat(attrs.getValue("alpha"));
            this.color = COLOR_FACTORY.createColor(r, g, b, alpha);
        }

        @Override
        public Object endTree(final StAXContext context)
            throws SAXException
        {
            return color;
        }
    }
}
