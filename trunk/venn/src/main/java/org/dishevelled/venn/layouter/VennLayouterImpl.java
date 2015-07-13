/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2015 held jointly by the individual authors.

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

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.dishevelled.venn.VennModel;
import org.dishevelled.venn.BinaryVennLayout;
import org.dishevelled.venn.BinaryVennModel;
import org.dishevelled.venn.TernaryVennLayout;
import org.dishevelled.venn.TernaryVennModel;
import org.dishevelled.venn.QuaternaryVennLayout;
import org.dishevelled.venn.QuaternaryVennModel;
import org.dishevelled.venn.VennLayout;
import org.dishevelled.venn.VennLayouter;

import org.dishevelled.venn.layout.BinaryVennLayoutImpl;
import org.dishevelled.venn.layout.TernaryVennLayoutImpl;
import org.dishevelled.venn.layout.QuaternaryVennLayoutImpl;

/**
 * Venn layout algorithm.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public final class VennLayouterImpl<E> implements VennLayouter<E>
{

    /** {@inheritDoc} */
    public VennLayout layout(final VennModel<E> model,
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

        if (model instanceof BinaryVennModel)
        {
            return layout((BinaryVennModel<E>) model, boundingRectangle);
        }
        else if (model instanceof TernaryVennModel)
        {
            return layout((TernaryVennModel<E>) model, boundingRectangle);
        }
        else if (model instanceof QuaternaryVennModel)
        {
            return layout((QuaternaryVennModel<E>) model, boundingRectangle);
        }

        // todo: apply venn/euler algorithm

        return null;
    }

    /**
     * Layout the specified binary venn diagram within the specified bounding rectangle.
     *
     * @param model binary venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @return the result of the layout operation
     */
    BinaryVennLayout layout(final BinaryVennModel<E> model,
                            final Rectangle2D boundingRectangle)
    {
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

    /**
     * Layout the specified ternary venn diagram within the specified bounding rectangle.
     *
     * @param model ternary venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @return the result of the layout operation
     */
    TernaryVennLayout layout(final TernaryVennModel<E> model,
                             final Rectangle2D boundingRectangle)
    {
        double w = boundingRectangle.getWidth();
        double h = boundingRectangle.getHeight();
        double d = 5.0d / (3.0d * Math.min(w, h));

        Ellipse2D firstShape = new Ellipse2D.Double(0.0d, 0.0d, d, d);
        Ellipse2D secondShape = new Ellipse2D.Double(((2.0d * d) / 3.0d), 0.0d, d, d);
        Ellipse2D thirdShape = new Ellipse2D.Double(d / 3.0d, (2.0d * d) / 3.0d, d, d);

        Area f = new Area(firstShape);
        Area s = new Area(secondShape);
        Area t = new Area(thirdShape);

        Area firstOnly = new Area();
        firstOnly.add(f);
        firstOnly.subtract(s);
        firstOnly.subtract(t);
        Point2D firstOnlyCenter = centerOf(firstOnly);

        Area secondOnly = new Area();
        secondOnly.add(s);
        secondOnly.subtract(f);
        secondOnly.subtract(t);
        Point2D secondOnlyCenter = centerOf(secondOnly);

        Area thirdOnly = new Area();
        thirdOnly.add(t);
        thirdOnly.subtract(f);
        thirdOnly.subtract(s);
        Point2D thirdOnlyCenter = centerOf(thirdOnly);

        Area firstSecond = new Area();
        firstSecond.add(f);
        firstSecond.intersect(s);
        firstSecond.subtract(t);
        Point2D firstSecondCenter = centerOf(firstSecond);

        Area firstThird = new Area();
        firstThird.add(f);
        firstThird.intersect(t);
        firstThird.subtract(s);
        Point2D firstThirdCenter = centerOf(firstThird);

        Area secondThird = new Area();
        secondThird.add(s);
        secondThird.intersect(t);
        secondThird.subtract(f);
        Point2D secondThirdCenter = centerOf(secondThird);

        Area intersection = new Area();
        intersection.add(f);
        intersection.intersect(s);
        intersection.intersect(t);
        Point2D intersectionCenter = centerOf(intersection);

        return new TernaryVennLayoutImpl(firstShape, secondShape, thirdShape, firstOnlyCenter, secondOnlyCenter,
                                         thirdOnlyCenter, firstSecondCenter, firstThirdCenter, secondThirdCenter,
                                         intersectionCenter, boundingRectangle);
    }

    /**
     * Layout the specified quaternary venn diagram within the specified bounding rectangle.
     *
     * @param model quaternary venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @return the result of the layout operation
     */
    QuaternaryVennLayout layout(final QuaternaryVennModel<E> model,
                                final Rectangle2D boundingRectangle)
    {
        return null;
    }

    /**
     * Find and return the center of the specified area using its bounds rectangle.
     *
     * @param area area, must not be null
     * @return the center of the specified area using its bounds rectangle
     */
    static Point2D centerOf(final Area area)
    {
        if (area == null)
        {
            throw new IllegalArgumentException("area must not be null");
        }
        Rectangle2D rectangle = area.getBounds2D();
        return new Point2D.Double(rectangle.getCenterX(), rectangle.getCenterY());
    }
}