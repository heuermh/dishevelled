/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
 * Continuous divergent color scheme.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ContinuousDivergentColorScheme
    implements ColorScheme
{
    /** Name. */
    private final String name;

    /** List of colors. */
    private final List<Color> colors;

    /** List of anchors. */
    private final List<Double> anchors;

    /** Minimum value. */
    private double minimumValue;

    /** Maximum value. */
    private double maximumValue;

    /** Zero value. */
    private double zeroValue;

    /** Color factory. */
    private final ColorFactory colorFactory;

    /** Interpolation. */
    private final Interpolation interpolation;


    /**
     * Create a new continuous divergent color scheme.
     *
     * @param name name
     * @param colors list of colors, must not be null and must contain at least two colors
     * @param minimumValue minimum value
     * @param zeroValue zero value
     * @param maximumValue maximum value
     * @param colorFactory color factory, must not be null
     * @param interpolation interpolation, must not be null
     */
    public ContinuousDivergentColorScheme(final String name,
                                          final List<Color> colors,
                                          final double minimumValue,
                                          final double zeroValue,
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
            throw new IllegalArgumentException("interpolation must not be null");
        }
        this.name = name;
        this.colors = new ArrayList<Color>(colors);

        this.minimumValue = minimumValue;
        this.zeroValue = zeroValue;
        this.maximumValue = maximumValue;
        this.colorFactory = colorFactory;
        this.interpolation = interpolation;

        anchors = new ArrayList<Double>(colors.size() - 1);
        recalculateAnchors();
    }


    /**
     * Recalculate anchors.
     */
    private void recalculateAnchors()
    {
        if (!anchors.isEmpty())
        {
            anchors.clear();
        }

        // ick.
        double z = zeroValue;
        double mn = minimumValue;
        double mx = maximumValue;
        double x = (z - mn);
        double y = (mx - z);
        int c = colors.size();
        int d = (c / 2);

        if ((c % 2) == 0)
        {
            anchors.add(Math.min(mn, z));
            for (int i = 1; i < d; i++)
            {
                anchors.add((2 * i * x) / (c - 1)); // Math.min?
            }
            for (int i = d + 1; i < c; i++)
            {
                anchors.add(((2 * (i - d) - 1) * y) / (c - 1) + z); // Math.max?
            }
            anchors.add(Math.max(mx, z));
        }
        else
        {
            anchors.add(Math.min(mn, z));
            for (int i = 1; i < d; i++)
            {
                anchors.add((2 * i * x) / (c - 1));
            }
            anchors.add(z);
            for (int i = d + 1; i < (c - 1); i++)
            {
                anchors.add(((2 * (i - d) - 1) * y) / (c - 1) + z);
            }
            anchors.add(Math.max(mx, z));
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
    public double getMinimumValue()
    {
        return minimumValue;
    }

    /** {@inheritDoc} */
    public void setMinimumValue(final double minimumValue)
    {
        this.minimumValue = minimumValue;
        recalculateAnchors();
    }

    /** {@inheritDoc} */
    public double getMaximumValue()
    {
        return maximumValue;
    }

    /** {@inheritDoc} */
    public void setMaximumValue(final double maximumValue)
    {
        this.maximumValue = maximumValue;
        recalculateAnchors();
    }

    /** {@inheritDoc} */
    public double getZeroValue()
    {
        return zeroValue;
    }

    /** {@inheritDoc} */
    public void setZeroValue(final double zeroValue)
    {
        this.zeroValue = zeroValue;
        recalculateAnchors();
    }

    /** {@inheritDoc} */
    public ColorFactory getColorFactory()
    {
        return colorFactory;
    }

    /** {@inheritDoc} */
    public void setColorFactory(final ColorFactory colorFactory)
    {
        throw new UnsupportedOperationException("setMaximumValue operation not supported by this color scheme");
    }

    /** {@inheritDoc} */
    public Interpolation getInterpolation()
    {
        return interpolation;
    }

    /** {@inheritDoc} */
    public void setInterpolation(final Interpolation interpolation)
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
                int r = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor,
                                                        (double) lowerColor.getRed(), (double) upperColor.getRed());
                int g = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor,
                                                        (double) lowerColor.getGreen(), (double) upperColor.getGreen());
                int b = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor,
                                                        (double) lowerColor.getBlue(), (double) upperColor.getBlue());
                int a = (int) interpolation.interpolate(value, lowerAnchor, upperAnchor,
                                                        (double) lowerColor.getAlpha(), (double) upperColor.getAlpha());
                return colorFactory.createColor(r, g, b, a);
            }
        }
        return getMaximumColor();
    }
}