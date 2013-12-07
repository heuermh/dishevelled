/*

    dsh-piccolo-venn-examples  Piccolo2D venn diagram node examples.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.venn.examples;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import java.awt.image.BufferedImage;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Use strict Java2D rendering of Areas to compare with PArea rendering.
 */
public final class AreaTest
{
    private static final Paint AREA_PAINT = Color.WHITE;
    private static final Paint FIRST_PAINT = new Color(30, 30, 30, 50);
    private static final Paint SECOND_PAINT = new Color(5, 37, 255, 50);
    private static final Paint THIRD_PAINT = new Color(255, 100, 5, 50);
    private static final Stroke STROKE = new BasicStroke(0.5f);
    private static final Paint STROKE_PAINT = new Color(20, 20, 20);

    private static void writeBinaryImage()
    {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double first = new Ellipse2D.Double(0.0d, 0.0d, 128.0d, 128.0d);
        Ellipse2D.Double second = new Ellipse2D.Double(((2.0d * 128.0d) / 3.0d), 0.0d, 128.0d, 128.0d);

        Area f = new Area(first);
        Area s = new Area(second);

        Area intersection = new Area();
        intersection.add(f);
        intersection.intersect(s);

        graphics.setPaint(FIRST_PAINT);
        graphics.fill(first);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(first);

        graphics.setPaint(SECOND_PAINT);
        graphics.fill(second);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(second);

        graphics.setPaint(AREA_PAINT);
        graphics.fill(intersection);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(intersection);

        try
        {
            ImageIO.write(image, "png", new File("binary.jpg"));
        }
        catch (IOException e)
        {
            // ignore
        }
        graphics.dispose();
    }

    private static void writeTernaryImage()
    {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double first = new Ellipse2D.Double(0.0d, 0.0d, 128.0d, 128.0d);
        Ellipse2D.Double second = new Ellipse2D.Double(((2.0d * 128.0d) / 3.0d), 0.0d, 128.0d, 128.0d);
        Ellipse2D.Double third = new Ellipse2D.Double(128.0d / 3.0d, (2.0d * 128.0d) / 3.0d, 128.0d, 128.0d);

        Area f = new Area(first);
        Area s = new Area(second);
        Area t = new Area(third);

        Area firstOnly = new Area();
        firstOnly.add(f);
        firstOnly.subtract(s);
        firstOnly.subtract(t);

        Area secondOnly = new Area();
        secondOnly.add(s);
        secondOnly.subtract(f);
        secondOnly.subtract(t);

        Area thirdOnly = new Area();
        thirdOnly.add(t);
        thirdOnly.subtract(f);
        thirdOnly.subtract(s);

        Area firstSecond = new Area();
        firstSecond.add(f);
        firstSecond.intersect(s);
        firstSecond.subtract(t);

        Area firstThird = new Area();
        firstThird.add(f);
        firstThird.intersect(t);
        firstThird.subtract(s);

        Area secondThird = new Area();
        secondThird.add(s);
        secondThird.intersect(t);
        secondThird.subtract(f);

        Area intersection = new Area();
        intersection.add(f);
        intersection.intersect(s);
        intersection.intersect(t);

        graphics.setPaint(FIRST_PAINT);
        graphics.fill(first);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(first);

        graphics.setPaint(SECOND_PAINT);
        graphics.fill(second);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(second);

        graphics.setPaint(THIRD_PAINT);
        graphics.fill(third);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(third);

        graphics.setPaint(AREA_PAINT);
        graphics.fill(firstSecond);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(firstSecond);

        graphics.setPaint(AREA_PAINT);
        graphics.fill(firstThird);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(firstThird);

        graphics.setPaint(AREA_PAINT);
        graphics.fill(secondThird);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(secondThird);

        graphics.setPaint(AREA_PAINT);
        graphics.fill(intersection);
        graphics.setStroke(STROKE);
        graphics.setPaint(STROKE_PAINT);
        graphics.draw(intersection);

        try
        {
            ImageIO.write(image, "png", new File("ternary.jpg"));
        }
        catch (IOException e)
        {
            // ignore
        }
        graphics.dispose();
    }

    public static void main(final String[] args)
    {
        writeBinaryImage();
        writeTernaryImage();
    }
}