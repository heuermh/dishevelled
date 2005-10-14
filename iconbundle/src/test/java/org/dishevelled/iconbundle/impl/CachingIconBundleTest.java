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
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.BasicStroke;

import java.awt.geom.Rectangle2D;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;
import org.dishevelled.iconbundle.AbstractIconBundleTest;

/**
 * Unit test for CachingIconBundle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CachingIconBundleTest
    extends AbstractIconBundleTest
{
    /** Shape. */
    private Shape shape = new Rectangle2D.Double(0.0d, 0.0d, 100.0d, 200.0d);

    /** Stroke. */
    private Stroke stroke = new BasicStroke(4.0f);

    /** Fill paint. */
    private Paint fillPaint = Color.BLUE;

    /** Stroke paint. */
    private Paint strokePaint = Color.BLACK;


    /** @see AbstractIconBundleTest */
    public IconBundle createIconBundle()
    {
        IconBundle ib = new ShapeIconBundle(shape, stroke, fillPaint, strokePaint);
        return new CachingIconBundle(ib);
    }

    public void testConstructor()
    {
        IconBundle ib0 = new ShapeIconBundle(shape, stroke, fillPaint, strokePaint);
        IconBundle ib1 = new CachingIconBundle(ib0);

        try
        {
            IconBundle ib2 = new CachingIconBundle(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testInvalidateCache()
    {
        IconBundle ib0 = new ShapeIconBundle(shape, stroke, fillPaint, strokePaint);
        CachingIconBundle ib1 = new CachingIconBundle(ib0);

        doTestAllVariants(ib1, null, IconTextDirection.VALUES, IconState.VALUES, IconSize.VALUES);

        ib1.invalidateCache();

        doTestAllVariants(ib1, null, IconTextDirection.VALUES, IconState.VALUES, IconSize.VALUES);
    }
}