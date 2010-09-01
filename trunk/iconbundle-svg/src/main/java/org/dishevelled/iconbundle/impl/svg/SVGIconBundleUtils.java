/*

    dsh-iconbundle-svg  SVG icon bundle implementation.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.impl.svg;

import java.awt.image.BufferedImage;

import java.net.URL;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscoderException;

import org.apache.batik.transcoder.image.ImageTranscoder;

/**
 * Static utility methods for implementing SVG icon bundles.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SVGIconBundleUtils
{

    /**
     * Private no-arg constructor.
     */
    private SVGIconBundleUtils()
    {
        // empty
    }


    /**
     * Read the specified SVG URL and render it to a BufferedImage
     * of the specified width and height.
     *
     * @param url url, must not be null
     * @param width width
     * @param height height
     * @return the specified SVG URL rendered to a BufferedImage of the
     *    specified width and height
     */
    public static BufferedImage readSVG(final URL url, final int width, final int height)
    {
        if (url == null)
        {
            throw new IllegalArgumentException("url must not be null");
        }
        BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
        transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, new Float(width));
        transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, new Float(height));

        try
        {
            transcoder.transcode(new TranscoderInput(url.toString()), new TranscoderOutput());
        }
        catch (TranscoderException e)
        {
            // ignore
        }

        BufferedImage image = transcoder.getImage();
        transcoder = null;

        return image;
    }
}