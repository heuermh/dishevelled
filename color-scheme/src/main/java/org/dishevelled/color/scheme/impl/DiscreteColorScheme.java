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
import java.util.Collections;
import java.util.List;

import org.dishevelled.color.scheme.ColorFactory;
import org.dishevelled.color.scheme.ColorScheme;

/**
 * Discrete color scheme.
 *
 * @author  Michael Heuer
 */
public final class DiscreteColorScheme
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
     * Create a new discrete color scheme.
     *
     * @param name name
     * @param colors list of colors, must not be null and must contain at least two colors
     * @param minimumValue minimum value
     * @param maximumValue maximum value
     * @param colorFactory color factory, must not be null
     */
    public DiscreteColorScheme(final String name,
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

        // discrete color scheme requires one fewer anchor than colors
        anchors = new ArrayList<Double>(colors.size() - 1);
        double anchorLength = (Math.abs(maximumValue - minimumValue) / colors.size());
        for (int i = 1; i < colors.size(); i++)
        {
            anchors.add(Double.valueOf(i * anchorLength));
        }
    }


    /**
     * Return the name of this discrete color scheme.
     *
     * @return the name of this discrete color scheme
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the number of colors in this discrete color scheme.
     *
     * @since 3.1
     * @return the number of colors in this discrete color scheme
     */
    public int getSize()
    {
        return colors.size();
    }

    /**
     * Return an unmodifiable list of colors in this discrete color scheme.
     *
     * @since 3.1
     * @return an unmodifiable list of colors in this discrete color scheme
     */
    public List<Color> getColors()
    {
        return Collections.unmodifiableList(colors);
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
        for (int i = 0; i < anchors.size(); i++)
        {
            if (value <= anchors.get(i))
            {
                return colors.get(i);
            }
        }
        return getMaximumColor();
    }
}
