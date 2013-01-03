/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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

import java.awt.Color;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Selection RasterOp.
 *
 * @author  Michael Heuer
 */
final class SelectionRasterOp
    extends AbstractRasterOp
{
    /** Pixel array size. */
    private static final int PIXEL_ARRAY_SIZE = 4;

    /** White color component. */
    private static final int WHITE = 255;

    /** Selection color. */
    private final Color selectionColor;


    /**
     * Create a new selection RasterOp with the specified
     * selection color.
     *
     * @param selectionColor selection color
     */
    public SelectionRasterOp(final Color selectionColor)
    {
        this.selectionColor = selectionColor;
    }


    /** {@inheritDoc} */
    public WritableRaster filter(final Raster src, final WritableRaster dest)
    {
        if (selectionColor != null)
        {
            float[] pixel = new float[PIXEL_ARRAY_SIZE];
            float[] selection = selectionColor.getColorComponents(new float[PIXEL_ARRAY_SIZE]);

            for (int x = 0, w = src.getWidth(); x < w; x++)
            {
                for (int y = 0, h = src.getHeight(); y < h; y++)
                {
                    pixel = src.getPixel(x, y, pixel);
                    pixel[0] = Math.min(pixel[0], selection[0] * WHITE);
                    pixel[1] = Math.min(pixel[1], selection[1] * WHITE);
                    pixel[2] = Math.min(pixel[2], selection[2] * WHITE);

                    dest.setPixel(x, y, pixel);
                }
            }
        }
        return dest;
    }
}
