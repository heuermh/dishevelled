/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009 held jointly by the individual authors.

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
package org.dishevelled.colorscheme.impl;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import org.dishevelled.colorscheme.ColorFactory;
import org.dishevelled.colorscheme.ColorScheme;
import org.dishevelled.colorscheme.Interpolation;

/**
 * Continuous color scheme.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
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

    /** Interpolation. */
    private final Interpolation interpolation;


    /**
     * Create a new continuous color scheme.
     */
    public ContinuousColorScheme(final String name,
                               final List<Color> colors,
                               final double minimumValue,
                               final double maximumValue,
                               final ColorFactory colorFactory,
                               final Interpolation interpolation)
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
        if (interpolation == null)
        {
            throw new IllegalArgumentException("colorFactory must not be null");
        }
        this.name = name;
        this.colors = new ArrayList<Color>(colors);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.colorFactory = colorFactory;
        this.interpolation = interpolation;
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

    /** {@inheritDoc} */
    public final double getMinimumValue()
    {
        return minimumValue;
    }

    /** {@inheritDoc} */
    public final void setMinimumValue(final double minimumValue)
    {
        throw new UnsupportedOperationException("setMinimumValue operation not supported by this color scheme");
    }

    /** {@inheritDoc} */
    public final double getMaximumValue()
    {
        return maximumValue;
    }

    /** {@inheritDoc} */
    public final void setMaximumValue(final double maximumValue)
    {
        throw new UnsupportedOperationException("setMaximumValue operation not supported by this color scheme");
    }

    /** {@inheritDoc} */
    public final double getZeroValue()
    {
        return Double.NaN;
    }

    /** {@inheritDoc} */
    public final void setZeroValue(final double zeroValue)
    {
        throw new UnsupportedOperationException("setZeroValue operation not supported by this color scheme");
    }

    /** {@inheritDoc} */
    public final ColorFactory getColorFactory()
    {
        return colorFactory;
    }

    /** {@inheritDoc} */
    public void setColorFactory(final ColorFactory colorFactory)
    {
        throw new UnsupportedOperationException("setMaximumValue operation not supported by this color scheme");
    }

    /** {@inheritDoc} */
    public final Interpolation getInterpolation()
    {
        return interpolation;
    }

    /** {@inheritDoc} */
    public final void setInterpolation(final Interpolation interpolation)
    {
        throw new UnsupportedOperationException("setInterpolation operation not supported by this color scheme");
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

    /** {@inheritDoc} */
    public final Color getColor(final double value)
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
                int r = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor, (double) lowerColor.getRed(), (double) upperColor.getRed());
                int g = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor, (double) lowerColor.getGreen(), (double) upperColor.getGreen());
                int b = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor, (double) lowerColor.getBlue(), (double) upperColor.getBlue());
                int a = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor, (double) lowerColor.getAlpha(), (double) upperColor.getAlpha());
                return colorFactory.createColor(r, g, b, a);
            }
        }
        return getMaximumColor();
    }
}