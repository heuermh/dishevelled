/*

    dsh-thumbnail  Implementation of the freedesktop.org thumbnail specification.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.thumbnail;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;

import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import javax.imageio.stream.ImageOutputStream;

import com.sun.imageio.plugins.png.PNGMetadata;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Thumbnail image and metadata.
 *
 * @author  Michael Heuer
 */
final class Thumbnail
{
    /** URI for the original image. */
    private final URI uri;

    /** Modification time for the original image. */
    private final long modificationTime;

    /** Width of the original image in pixels. */
    private final int width;

    /** Height of the original image in pixels. */
    private final int height;

    /** Thumbnail image. */
    private final BufferedImage image;


    /**
     * Create a new thumbnail.
     *
     * @param uri URI for the original image
     * @param modificationTime modification time for the original image
     * @param width width of the original image in pixels
     * @param height height of the original image in pixels
     * @param image thumbnail image
     */
    Thumbnail(final URI uri, final long modificationTime, final int width, final int height, final BufferedImage image)
    {
        this.uri = uri;
        this.modificationTime = modificationTime;
        this.width = width;
        this.height = height;
        this.image = image;
    }


    /**
     * Return the URI for the original image.
     *
     * @return the URI for the original image
     */
    URI getURI()
    {
        return uri;
    }

    /**
     * Return the modification time for the original image in seconds since 01 January 1970.
     *
     * @return the modification time for the original image in seconds since 01 January 1970
     */
    long getModificationTime()
    {
        return modificationTime;
    }

    /**
     * Return the width of the original image in pixels.
     *
     * @return the width of the original image in pixels
     */
    int getWidth()
    {
        return width;
    }

    /**
     * Return the height of the original image in pixels.
     *
     * @return the height of the original image in pixels
     */
    int getHeight()
    {
        return height;
    }

    /**
     * Return the thumbnail image.
     *
     * @return the thumbnail image
     */
    BufferedImage getImage()
    {
        return image;
    }

    /**
     * Read the specified file and return a thumbnail.
     *
     * @param file file to read, must not be null and must be readable
     * @return a thumbnail read from the specified file
     * @throws IOException if an I/O error occurs
     */
    static Thumbnail read(final File file) throws IOException
    {
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }
        if (!file.canRead())
        {
            throw new IllegalArgumentException("file must be readable");
        }

        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);
            ImageReader imageReader = ImageIO.getImageReadersByFormatName("png").next();
            imageReader.setInput(ImageIO.createImageInputStream(inputStream), true);
            IIOMetadata metadata = imageReader.getImageMetadata(0);

            // this cast helps getting the contents
            PNGMetadata pngMetadata = (PNGMetadata) metadata;
            NodeList childNodes = pngMetadata.getStandardTextNode().getChildNodes();

            URI uri = null; // default values?
            long modificationTime = 0L;
            int width = 0;
            int height = 0;
            BufferedImage image = null;

            for (int i = 0; i < childNodes.getLength(); i++)
            {
                Node node = childNodes.item(i);
                String keyword = node.getAttributes().getNamedItem("keyword").getNodeValue();
                String value = node.getAttributes().getNamedItem("value").getNodeValue();
                if ("Thumb::URI".equals(keyword))
                {
                    try
                    {
                        uri = new URI(value);
                    }
                    catch (URISyntaxException e)
                    {
                        // ignore
                    }
                }
                else if ("Thumb::MTime".equals(keyword))
                {
                    modificationTime = Long.parseLong(value);
                }
                else if ("Thumb::Image::Width".equals(keyword))
                {
                    width = Integer.parseInt(value);
                }
                else if ("Thumb::Image::Height".equals(keyword))
                {
                    height = Integer.parseInt(value);
                }
            }
            image = imageReader.read(0);
            return new Thumbnail(uri, modificationTime, width, height, image);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
    }

    /**
     * Create and return a new metadata node with the specified keyword and value.
     *
     * @param keyword keyword
     * @param value value
     * @return a new metadata node with the specified keyword and value
     */
    private IIOMetadataNode createMetadataNode(final String keyword, final String value)
    {
        IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
        textEntry.setAttribute("keyword", keyword);
        textEntry.setAttribute("value", value);

        IIOMetadataNode text = new IIOMetadataNode("tEXt");
        text.appendChild(textEntry);
        return text;
    }

    /**
     * Write this thumbnail to the specified file.
     *
     * @param file file to write to, must not be null and must be writeable
     * @throws IOException if an I/O error occurs
     */
    void write(final File file) throws IOException
    {
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }
        if (!file.canWrite())
        {
            throw new IllegalArgumentException("file must be writeable");
        }

        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB);
        IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
        root.appendChild(createMetadataNode("Thumb::URI", uri.toString()));
        root.appendChild(createMetadataNode("Thumb::MTime", String.valueOf(modificationTime)));

        if (width > 0)
        {
            root.appendChild(createMetadataNode("Thumb::Image::Width", String.valueOf(width)));
        }
        if (height > 0)
        {
            root.appendChild(createMetadataNode("Thumb::Image::Height", String.valueOf(height)));
        }
        metadata.mergeTree("javax_imageio_png_1.0", root);

        ImageOutputStream imageOutputStream = null;
        try
        {
            imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream(file));
            writer.setOutput(imageOutputStream);
            writer.write(metadata, new IIOImage(image, null, metadata), writeParam);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            try
            {
                imageOutputStream.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
    }
}