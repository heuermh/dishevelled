/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2006 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.iconbundle.impl;

import java.io.File;
import java.io.InputStream;

import java.awt.Color;
import java.awt.Image;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;

import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

import java.net.URL;

import javax.imageio.ImageIO;

import javax.imageio.stream.ImageInputStream;

import javax.swing.UIManager;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * Icon bundle derived from a single base image in PNG format.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class PNGIconBundle
    implements IconBundle
{
    /** Base image. */
    private final BufferedImage baseImage;


    /**
     * Create a new PNG icon bundle from a base image that will
     * be read from the specified file.
     *
     * @param file file, must not be null
     */
    public PNGIconBundle(final File file)
    {
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }

        try
        {
            baseImage = ImageIO.read(file);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("could not create base image; " + e.getMessage());
        }
    }

    /**
     * Create a new PNG icon bundle from a base image that will
     * be read from the specified input stream.
     *
     * @param inputStream input stream, must not be null
     */
    public PNGIconBundle(final InputStream inputStream)
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException("inputStream must not be null");
        }

        try
        {
            baseImage = ImageIO.read(inputStream);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("could not create base image; " + e.getMessage());
        }
    }

    /**
     * Create a new PNG icon bundle from a base image that will
     * be read from the specified image input stream.
     *
     * @param imageInputStream image input stream, must not be null
     */
    public PNGIconBundle(final ImageInputStream imageInputStream)
    {
        if (imageInputStream == null)
        {
            throw new IllegalArgumentException("imageInputStream must not be null");
        }

        try
        {
            baseImage = ImageIO.read(imageInputStream);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("could not create base image; " + e.getMessage());
        }
    }

    /**
     * Create a new PNG icon bundle from a base image that will
     * be read from the specified URL.
     *
     * @param url URL, must not be null
     */
    public PNGIconBundle(final URL url)
    {
        if (url == null)
        {
            throw new IllegalArgumentException("url must not be null");
        }

        try
        {
            baseImage = ImageIO.read(url);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("could not create base image; " + e.getMessage());
        }
    }


    /** @see IconBundle */
    public Image getImage(final Component component,
                          final IconTextDirection direction,
                          final IconState state,
                          final IconSize size)
    {
        if (direction == null)
        {
            throw new IllegalArgumentException("direction must not be null");
        }
        if (state == null)
        {
            throw new IllegalArgumentException("state must not be null");
        }
        if (size == null)
        {
            throw new IllegalArgumentException("size must not be null");
        }

        int h = size.getHeight();
        int w = size.getWidth();

        BufferedImage image = new BufferedImage(w + 1, h + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        Rectangle2D bounds = new Rectangle2D.Double(0.0d, 0.0d,
                                                    (double) baseImage.getWidth(),
                                                    (double) baseImage.getHeight());

        double d = 1.0d;
        if (bounds.getWidth() >= bounds.getHeight())
        {
            d = w / bounds.getWidth();
        }
        else
        {
            d = h / bounds.getHeight();
        }

        AffineTransform at = new AffineTransform();

        // translate to center of icon size rect
        at.translate(w / 2.0d, h / 2.0d);
        // scale image to icon size rect
        at.scale(d, d);
        // translate center of image to center of icon size rect
        at.translate(-1 * bounds.getCenterX(), -1 * bounds.getCenterY());

        g.drawRenderedImage(baseImage, at);

        g.dispose();

        if (IconState.ACTIVE == state)
        {
            return IconBundleUtils.makeActive(image);
        }
        else if (IconState.MOUSEOVER == state)
        {
            return IconBundleUtils.makeMouseover(image);
        }
        else if (IconState.SELECTED == state)
        {
            Color selectionColor = UIManager.getColor("List.selectionBackground");
            return IconBundleUtils.makeSelected(image, selectionColor);
        }
        else if (IconState.DRAGGING == state)
        {
            return IconBundleUtils.makeDragging(image);
        }
        else if (IconState.DISABLED == state)
        {
            return IconBundleUtils.makeDisabled(image);
        }
        else
        {
            return image;
        }
    }
}