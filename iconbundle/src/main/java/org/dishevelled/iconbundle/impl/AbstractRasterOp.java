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

import java.awt.RenderingHints;

import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.WritableRaster;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Abstract implementation of RasterOp.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractRasterOp
    implements RasterOp
{

    /** {@inheritDoc} */
    public final WritableRaster createCompatibleDestRaster(final Raster src)
    {
        return null;
    }

    /** {@inheritDoc} */
    public final Rectangle2D getBounds2D(final Raster src)
    {
        return null;
    }

    /** {@inheritDoc} */
    public final Point2D getPoint2D(final Point2D srcPoint, final Point2D destPoint)
    {
        return srcPoint;
    }

    /** {@inheritDoc} */
    public final RenderingHints getRenderingHints()
    {
        return null;
    }
}
