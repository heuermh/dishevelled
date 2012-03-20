
/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
package org.dishevelled.venn.layouter;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.Iterator;

import org.dishevelled.venn.VennModel;
//import org.dishevelled.venn.BinaryVennModel;
//import org.dishevelled.venn.TernaryVennModel;
//import org.dishevelled.venn.QuaternaryVennModel;

//import org.dishevelled.venn.BinaryVennLayout;
//import org.dishevelled.venn.TernaryVennLayout;
//import org.dishevelled.venn.QuaternaryVennLayout;
import org.dishevelled.venn.VennLayout;
import org.dishevelled.venn.VennLayouter;

/**
 * Venn layout algorithm.
 *
 * @author  Michael Heuer
 */
public final class VennLayouterImpl implements VennLayouter
{

    /** {@inheritDoc} */
    public VennLayout layout(final VennModel<?> model,
                             final Rectangle2D boundingRectangle,
                             final PerformanceHint performanceHint)
    {
        return null;
    }

    /** {@inheritDoc} */
    public Iterator<VennLayout> layout(final VennModel<?> model,
                                       final Rectangle2D boundingRectangle,
                                       final ScoringFunction scoringFunction,
                                       final ScorePredicate scorePredicate,
                                       final PerformanceHint performanceHint)
    {
        return null;
    }

    /*
    public BinaryVennLayout layout(final BinaryVennModel<?> model,
                                   final Rectangle2D boundingRectangle,
                                   final PerformanceHint performanceHint)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        if (performanceHint == null)
        {
            throw new IllegalArgumentException("performanceHint must not be null");
        }

        double w = boundingRectangle.getWidth();
        double h = boundingRectangle.getHeight();
        double cx = w / 2.0d;
        double cy = h / 2.0d;
        double d = Math.min(h, 3.0d * w / 5.0d);
        double r = d / 2.0d;
        double x = cx - 5.0d * d / 6.0d;
        double y = cy - d / 2.0d;

        Ellipse2D firstShape = new Ellipse2D.Double(x, y, d, d);
        Ellipse2D secondShape = new Ellipse2D.Double(x + 2.0d * d / 3.0d, y, d, d);
        Point2D firstOnlyCenter = new Point2D.Double(cx - r, cy);
        Point2D secondOnlyCenter = new Point2D.Double(cx + r, cy);
        Point2D intersectionCenter = new Point2D.Double(cx, cy);

        return new BinaryVennLayoutImpl(firstShape, secondShape,
                                        firstOnlyCenter, secondOnlyCenter, intersectionCenter, boundingRectangle);
    }

    public TernaryVennLayout layout(final TernaryVennModel<?> model,
                                    final Rectangle2D boundingRectangle,
                                    final PerformanceHint performanceHint)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        if (performanceHint == null)
        {
            throw new IllegalArgumentException("performanceHint must not be null");
        }
        return null;
    }

    public QuaternaryVennLayout layout(final QuaternaryVennModel<?> model,
                                       final Rectangle2D boundingRectangle,
                                       final PerformanceHint performanceHint)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        if (performanceHint == null)
        {
            throw new IllegalArgumentException("performanceHint must not be null");
        }
        return null;
    }
    */
}