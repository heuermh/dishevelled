/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.impl;

import java.awt.image.BufferedImage;

import org.apache.batik.transcoder.TranscoderOutput;

import org.apache.batik.transcoder.image.ImageTranscoder;

/**
 * Image transcoder that reads into an intermediate image
 * but does not write the image to the transcoder output.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class BufferedImageTranscoder
    extends ImageTranscoder
{
    /** Intermediate image. */
    private BufferedImage image;


    /**
     * Return the intermediate image created by this transcoder.
     *
     * @return the intermediate image created by this transcoder
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /** {@inheritDoc} */
    public BufferedImage createImage(final int width, final int height)
    {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    /** {@inheritDoc} */
    public void writeImage(final BufferedImage image, final TranscoderOutput output)
    {
        this.image = image;
    }
}
