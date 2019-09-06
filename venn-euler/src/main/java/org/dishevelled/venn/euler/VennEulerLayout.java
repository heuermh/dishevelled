/*

    dsh-venn-euler  Lightweight components for venn/euler diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.euler;

import org.dishevelled.venn.VennModel;
import org.dishevelled.venn.VennLayout;

import edu.uic.ncdm.venn.VennDiagram;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.util.List;
import java.util.ArrayList;

/**
 * VennEuler layout.
 */
public final class VennEulerLayout implements VennLayout
{
    private final VennDiagram vd;
    private final List<Shape> circles;
    private final List<Point2D> luneCenters;
    private final int size;
    private Rectangle2D boundingBox;
    private final String decodeBase;

    public VennEulerLayout(final VennDiagram vd, final Rectangle2D boundingBox)
    {
        this.vd = vd;
        this.boundingBox = boundingBox;
        this.size = vd.centers.length;
        this.circles = new ArrayList<Shape>(size);
        this.luneCenters = new ArrayList<Point2D>(vd.luneCenters.length);
        decodeBase = calcDecodeBase(vd.centers.length);
        createCircles();
    }

    private void createCircles()
    {
        // find the bounds of the circles
        double size = Math.min(boundingBox.getWidth(),boundingBox.getHeight());
        double mins = Double.POSITIVE_INFINITY;
        double maxs = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < vd.centers.length; i++)
        {
            double radius = vd.diameters[i] / 2;
            mins = Math.min(vd.centers[i][0] - radius, mins);
            mins = Math.min(vd.centers[i][1] - radius, mins);
            maxs = Math.max(vd.centers[i][0] + radius, maxs);
            maxs = Math.max(vd.centers[i][1] + radius, maxs);
        }

        // place the circles within the bounds
        for (int i = 0; i < vd.centers.length; i++)
        {
            double xi = (vd.centers[i][0] - mins) / (maxs - mins);
            double yi = (vd.centers[i][1] - mins) / (maxs - mins);
            double pi = vd.diameters[i] / (maxs - mins);
            double pointSize = (pi * size);
            double x = (xi * size);
            double y = (size - yi * size);

            circles.add(new Ellipse2D.Double(x - pointSize / 2, y - pointSize / 2, pointSize, pointSize));
        }

        // this is a dummy point to offset the array indices
        luneCenters.add(new Point2D.Double(0.0,0.0));

        // find the lune centers within the bounds 
        for (int i = 0; i < vd.luneCenters.length; i++ )
        {
            double xi = (vd.luneCenters[i][0] - mins) / (maxs - mins);
            double yi = (vd.luneCenters[i][1] - mins) / (maxs - mins);
            double x = (xi * size);
            double y = (size - yi * size);

            luneCenters.add(new Point2D.Double(x,y));
        }
    }

    public int size()
    {
        return size;
    }

    public Shape get(final int index) {
        try
        {
            return circles.get(index);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public Point2D luneCenter(final int index, final int... additional)
    {
        int[] indices = new int[additional.length + 1];
        indices[0] = index;
        System.arraycopy(additional, 0, indices, 1, additional.length);

        return luneCenters.get(decodeLuneCenter(indices));
    }

    public Rectangle2D boundingRectangle()
    {
        return boundingBox;
    }

    // Think of the index into the luneCenters list in binary, where each
    // true position in the integer represents a set that should be
    // intersected. This method converts an array of SET indices into 
    // a single integer which represents their intersection and serves
    // as an index into luneCenters.
    private int decodeLuneCenter(final int[] indices)
    {
        StringBuilder binary = new StringBuilder(decodeBase);
        for (int index : indices)
        {
            binary.setCharAt(index,'1');
        }
        String b = binary.toString();
        int x = Integer.parseInt(b, 2);
        return  x;
    }

    private String calcDecodeBase(final int num)
    {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < num; i++)
        {
            binary.append("0");
        }
        return binary.toString();
    }
}
