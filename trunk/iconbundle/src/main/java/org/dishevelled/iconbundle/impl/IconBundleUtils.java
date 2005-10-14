/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2005 held jointly by the individual authors.

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

import java.awt.Color;
import java.awt.Image;

import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.RescaleOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * IconBundle static utility class.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class IconBundleUtils
{

    /**
     * Private constructor.
     */
    private IconBundleUtils()
    {
        // empty
    }


    /**
     * Make the specified source image active.
     *
     * @param src source image
     * @return filtered source image
     */
    public static Image makeActive(final BufferedImage src)
    {
        BufferedImage dest = new BufferedImage(src.getColorModel(), src.copyData(null), src.isAlphaPremultiplied(), null);

        ACTIVE_OP.filter(src, dest);

        return dest;
    }

    /**
     * Make the specified source image disabled.
     *
     * @param src source image
     * @return filtered source image
     */
    public static Image makeDisabled(final BufferedImage src)
    {
        BufferedImage dest = new BufferedImage(src.getColorModel(), src.copyData(null), src.isAlphaPremultiplied(), null);

        DISABLED_SATURATION_OP.filter(src, dest);
        DISABLED_PATTERN_OP.filter(src.getRaster(), dest.getRaster());

        return dest;
    }

    /**
     * Make the specified source image dragging.
     *
     * @param src source image
     * @return filtered source image
     */
    public static Image makeDragging(final BufferedImage src)
    {
        BufferedImage dest = new BufferedImage(src.getColorModel(), src.copyData(null), src.isAlphaPremultiplied(), null);

        DRAGGING_OP.filter(src, dest);

        return dest;
    }

    /**
     * Make the specified source image mouseover.
     *
     * @param src source image
     * @return filtered source image
     */
    public static Image makeMouseover(final BufferedImage src)
    {
        BufferedImage dest = new BufferedImage(src.getColorModel(), src.copyData(null), src.isAlphaPremultiplied(), null);

        MOUSEOVER_OP.filter(src, dest);

        return dest;
    }

    /**
     * Make the specified source image selected.
     *
     * @param src source image
     * @param selectionColor selection color
     * @return filtered source image
     */
    public static Image makeSelected(final BufferedImage src, final Color selectionColor)
    {
        BufferedImage dest = new BufferedImage(src.getColorModel(), src.copyData(null), src.isAlphaPremultiplied(), null);

        RasterOp selectionOp = new SelectionRasterOp(selectionColor);
        selectionOp.filter(src.getRaster(), dest.getRaster());

        selectionOp = null;

        return dest;
    }


    /**
     * RescaleOp for saturating active images.
     */
    private static RescaleOp ACTIVE_OP = new RescaleOp(new float[] { 0.8f, 0.8f, 0.8f, 1.0f },
                                                       new float[] { 0.0f, 0.0f, 0.0f, 0.0f },
                                                       null);

    /**
     * RescaleOp for saturating mouseover images.
     */
    private static RescaleOp MOUSEOVER_OP = new RescaleOp(new float[] { 1.2f, 1.2f, 1.2f, 1.0f },
                                                          new float[] { 0.0f, 0.0f, 0.0f, 0.0f },
                                                          null);

    /**
     * RescaleOp for creating translucent dragging images.
     */
    private static RescaleOp DRAGGING_OP = new RescaleOp(new float[] { 1.0f, 1.0f, 1.0f, 0.2f },
                                                         new float[] { 0.0f, 0.0f, 0.0f, 0.0f },
                                                         null);

    /**
     * RescaleOp for desaturating disabled images.
     */
    private static RescaleOp DISABLED_SATURATION_OP = new RescaleOp(new float[] { 0.8f, 0.8f, 0.8f, 1.0f },
                                                                    new float[] { 0.0f, 0.0f, 0.0f, 0.0f },
                                                                    null);

    /** Intensity factor for red. */
    private static final float RED_FACTOR = 0.30f;

    /** Intensity factor for green. */
    private static final float GREEN_FACTOR = 0.59f;

    /** Intensity factor for blue. */
    private static final float BLUE_FACTOR = 0.11f;

    /**
     * Calculate a measure of intensity for the specified RGB values.
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     * @return a measure of intensity for the specified RGB values
     */
    private static float calculateIntensity(final float r, final float g, final float b)
    {
        return (float) (r * RED_FACTOR + g * GREEN_FACTOR + b * BLUE_FACTOR);
    }


    /**
     * RasterOp for creating the pattern overlay for disabled images.
     */
    private static RasterOp DISABLED_PATTERN_OP = new AbstractRasterOp()
        {

            /** @see RasterOp */
            public WritableRaster filter(final Raster src, final WritableRaster dest)
            {
                float intensity;
                float[] pixel = new float[4];

                for (int x = 0, w = src.getWidth(); x < w; x++)
                {
                    for (int y = 0, h = src.getHeight(); y < h; y++)
                    {
                        pixel = src.getPixel(x, y, pixel);
                        intensity = calculateIntensity(pixel[0], pixel[1], pixel[2]);

                        if (((x + y) % 2) == 0)
                        {
                            pixel[0] = (intensity / 2) + 127;
                            pixel[1] = (intensity / 2) + 127;
                            pixel[2] = (intensity / 2) + 127;
                        }
                        else
                        {
                            pixel[0] *= 0.7f;
                            pixel[1] *= 0.7f;
                            pixel[2] *= 0.7f;
                        }

                        dest.setPixel(x, y, pixel);
                    }
                }

                return dest;
            }
        };
}