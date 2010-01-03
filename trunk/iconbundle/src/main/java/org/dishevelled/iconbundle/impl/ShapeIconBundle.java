/*

    dsh-iconbundle  Bundles of variants for icon images.
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
package org.dishevelled.iconbundle.impl;

import java.awt.Color;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;

import javax.swing.UIManager;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * Shape icon bundle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ShapeIconBundle
    implements IconBundle
{
    /** Shape. */
    private final Shape shape;

    /** Stroke. */
    private final Stroke stroke;

    /** Paint. */
    private final Paint fillPaint;

    /** Stroke paint. */
    private final Paint strokePaint;


    /**
     * Create a new shape icon bundle with the specified shape,
     * stroke, and paints.
     *
     * @param shape shape, must not be null
     * @param stroke stroke
     * @param fillPaint fill paint
     * @param strokePaint stroke paint
     */
    public ShapeIconBundle(final Shape shape,
                           final Stroke stroke,
                           final Paint fillPaint,
                           final Paint strokePaint)
    {
        if (shape == null)
        {
            throw new IllegalArgumentException("shape must not be null");
        }
        if (stroke == null)
        {
            throw new IllegalArgumentException("stroke must not be null");
        }
        if (fillPaint == null)
        {
            throw new IllegalArgumentException("fillPaint must not be null");
        }
        if (strokePaint == null)
        {
            throw new IllegalArgumentException("strokePaint must not be null");
        }

        this.shape = shape;
        this.stroke = stroke;
        this.fillPaint = fillPaint;
        this.strokePaint = strokePaint;
    }


    /** {@inheritDoc} */
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

        Rectangle2D bounds = shape.getBounds2D();

        double d = 1.0d;
        if (bounds.getWidth() >= bounds.getHeight())
        {
            d = w / bounds.getWidth();
        }
        else
        {
            d = h / bounds.getHeight();
        }

        // translate to center of icon size rect
        g.translate(w / 2.0d, h / 2.0d);
        // scale shape to icon size rect
        g.scale(d, d);
        // translate center of shape to center of icon size rect
        g.translate(-1 * bounds.getCenterX(), -1 * bounds.getCenterY());

        g.setPaint(fillPaint);
        g.fill(shape);

        g.setStroke(stroke);
        g.setPaint(strokePaint);
        g.draw(shape);

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
