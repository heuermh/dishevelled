/*

    dsh-color-scheme  Color schemes.
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
package org.dishevelled.color.scheme.impl;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import org.dishevelled.color.scheme.ColorFactory;
import org.dishevelled.color.scheme.ColorScheme;

/**
 * Continuous color scheme.
 *
 * @author  Michael Heuer
 */
public final class ContinuousColorScheme
    implements ColorScheme
{
    /** Name. */
    private final String name;

    /** List of colors. */
    private final List<Color> colors;

    /** List of anchors. */
    private final List<Double> anchors;

    /** Minimum value. */
    private final double minimumValue;

    /** Maximum value. */
    private final double maximumValue;

    /** Color factory. */
    private final ColorFactory colorFactory;


    /**
     * Create a new continuous color scheme.
     *
     * @param name name
     * @param colors list of colors, must not be null and must contain at least two colors
     * @param minimumValue minimum value
     * @param maximumValue maximum value
     * @param colorFactory color factory, must not be null
     */
    public ContinuousColorScheme(final String name,
                                 final List<Color> colors,
                                 final double minimumValue,
                                 final double maximumValue,
                                 final ColorFactory colorFactory)
    {
        if (colors == null)
        {
            throw new IllegalArgumentException("colors must not be null");
        }
        if (colors.size() < 2)
        {
            throw new IllegalArgumentException("colors must contain at least two colors");
        }
        if (colorFactory == null)
        {
            throw new IllegalArgumentException("colorFactory must not be null");
        }
        this.name = name;
        this.colors = new ArrayList<Color>(colors);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.colorFactory = colorFactory;

        // continuous color scheme requires one anchor for each color
        anchors = new ArrayList<Double>(colors.size());
        double anchorLength = (Math.abs(maximumValue - minimumValue) / (colors.size() - 1));
        for (int i = 0; i < colors.size(); i++)
        {
            anchors.add(Double.valueOf(i * anchorLength));
        }
    }


    /**
     * Return the name of this continuous color scheme.
     *
     * @return the name of this continuous color scheme
     */
    public String getName()
    {
        return name;
    }

    @Override
    public double getMinimumValue()
    {
        return minimumValue;
    }

    @Override
    public void setMinimumValue(final double minimumValue)
    {
        throw new UnsupportedOperationException("setMinimumValue operation not supported by this color scheme");
    }

    @Override
    public double getMaximumValue()
    {
        return maximumValue;
    }

    @Override
    public void setMaximumValue(final double maximumValue)
    {
        throw new UnsupportedOperationException("setMaximumValue operation not supported by this color scheme");
    }

    @Override
    public double getZeroValue()
    {
        return Double.NaN;
    }

    @Override
    public void setZeroValue(final double zeroValue)
    {
        throw new UnsupportedOperationException("setZeroValue operation not supported by this color scheme");
    }

    @Override
    public ColorFactory getColorFactory()
    {
        return colorFactory;
    }

    @Override
    public void setColorFactory(final ColorFactory colorFactory)
    {
        throw new UnsupportedOperationException("setMaximumValue operation not supported by this color scheme");
    }

    /**
     * Return the minimum color for this color scheme.
     *
     * @return the minimum color for this color scheme
     */
    private Color getMinimumColor()
    {
        return colors.get(0);
    }

    /**
     * Return the minimum anchor for this color scheme.
     *
     * @return the minimum anchor for this color scheme
     */
    private double getMinimumAnchor()
    {
        return anchors.get(0);
    }

    /**
     * Return the maximum color for this color scheme.
     *
     * @return the maximum color for this color scheme
     */
    private Color getMaximumColor()
    {
        return colors.get(colors.size() - 1);
    }

    @Override
    public Color getColor(final double value)
    {
        if (value < getMinimumAnchor())
        {
            return getMinimumColor();
        }
        Color lowerColor = getMinimumColor();
        double lowerAnchor = getMinimumAnchor();
        for (int i = 0; i < anchors.size(); i++)
        {
            double upperAnchor = anchors.get(i);
            if (value == upperAnchor)
            {
                return colors.get(i);
            }
            else if (value < upperAnchor)
            {
                Color upperColor = colors.get(i);
                lowerAnchor = anchors.get(i - 1);
                lowerColor = colors.get(i - 1);
                int r = (int) interpolate(value, lowerAnchor, upperAnchor,
                                         (double) lowerColor.getRed(), (double) upperColor.getRed());
                int g = (int) interpolate(value, lowerAnchor, upperAnchor,
                                          (double) lowerColor.getGreen(), (double) upperColor.getGreen());
                int b = (int) interpolate(value, lowerAnchor, upperAnchor,
                                          (double) lowerColor.getBlue(), (double) upperColor.getBlue());
                int a = (int) interpolate(value, lowerAnchor, upperAnchor,
                                          (double) lowerColor.getAlpha(), (double) upperColor.getAlpha());
                return colorFactory.createColor(r, g, b, ((float) a) / 255.0f);
            }
        }
        return getMaximumColor();
    }

    /**
     * Interpolate the specified value from the specified source range to the specified target range.
     *
     * @param value value to interpolate
     * @param sourceMinimum source range minimum value
     * @param sourceMaximum source range maximum value
     * @param targetMinimum target range minimum value
     * @param targetMaximum target range maximum value
     * @return the specified value interpolated from the specified source range to the specified target range
     */
    private static double interpolate(final double value,
                                      final double sourceMinimum,
                                      final double sourceMaximum,
                                      final double targetMinimum,
                                      final double targetMaximum)
    {
        return targetMinimum + (targetMaximum - targetMinimum)
            * ((value - sourceMinimum) / (sourceMaximum - sourceMinimum));
    }
}
