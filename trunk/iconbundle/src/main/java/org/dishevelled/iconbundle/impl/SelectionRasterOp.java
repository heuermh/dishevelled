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

import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Selection RasterOp.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class SelectionRasterOp
    extends AbstractRasterOp
{
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


    /** @see RasterOp */
    public WritableRaster filter(final Raster src, final WritableRaster dest)
    {
        float[] pixel = new float[4];
        float[] selection = selectionColor.getColorComponents(new float[4]);

        for (int x = 0, w = src.getWidth(); x < w; x++)
        {
            for (int y = 0, h = src.getHeight(); y < h; y++)
            {
                pixel = src.getPixel(x, y, pixel);
                pixel[0] = Math.min(pixel[0], selection[0] * 255);
                pixel[1] = Math.min(pixel[1], selection[1] * 255);
                pixel[2] = Math.min(pixel[2], selection[2] * 255);

                dest.setPixel(x, y, pixel);
            }
        }

        return dest;
    }
}