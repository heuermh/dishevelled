/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008 held jointly by the individual authors.

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
package org.dishevelled.graph.io.ygraphml;

/**
 * Edge label.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EdgeLabel
{
    /** True if this edge label is visible. */
    private final boolean visible;

    /** Alignment for this edge label. */
    private final String alignment;

    /** Font family for this edge label. */
    private final String fontFamily;

    /** Font size for this edge label. */
    private final int fontSize;

    /** Font style for this edge label. */
    private final String fontStyle;

    /** Text color for this edge label. */
    private final String textColor;

    /** Model name for this edge label. */
    private final String modelName;

    /** Model position for this edge label. */
    private final String modelPosition;

    /** Preferred placement for this edge label. */
    private final String preferredPlacement;

    /** Distance for this edge label. */
    private final double distance;

    /** Ratio for this edge label. */
    private final double ratio;

    /** Text for this edge label. */
    private final String text;


    /**
     * Create a new edge label from the specified parameters.
     *
     * @param visible true if this edge label is visible
     * @param alignment alignment for this edge label, must not be null
     * @param fontFamily font family for this edge label, must not be null
     * @param fontSize font size for this edge label
     * @param fontStyle font style for this edge label, must not be null
     * @param textColor text color for this edge label, must not be null
     * @param modelName model name for this edge label, must not be null
     * @param modelPosition model position for this edge label, must not be null
     * @param preferredPlacement preferred placement for this edge label, must not be null
     * @param distance distance for this edge label
     * @param ratio ratio for this edge label
     * @param text text for this edge label, must not be null
     */
    public EdgeLabel(final boolean visible,
                     final String alignment,
                     final String fontFamily,
                     final int fontSize,
                     final String fontStyle,
                     final String textColor,
                     final String modelName,
                     final String modelPosition,
                     final String preferredPlacement,
                     final double distance,
                     final double ratio,
                     final String text)
    {
        if (alignment == null)
        {
            throw new IllegalArgumentException("alignment must not be null");
        }
        if (fontFamily == null)
        {
            throw new IllegalArgumentException("fontFamily must not be null");
        }
        if (fontStyle == null)
        {
            throw new IllegalArgumentException("fontStyle must not be null");
        }
        if (textColor == null)
        {
            throw new IllegalArgumentException("textColor must not be null");
        }
        if (modelName == null)
        {
            throw new IllegalArgumentException("modelName must not be null");
        }
        if (modelPosition == null)
        {
            throw new IllegalArgumentException("modelPosition must not be null");
        }
        if (preferredPlacement == null)
        {
            throw new IllegalArgumentException("preferredPlacement must not be null");
        }
        if (text == null)
        {
            throw new IllegalArgumentException("text must not be null");
        }
        this.visible = visible;
        this.alignment = alignment;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.textColor = textColor;
        this.modelName = modelName;
        this.modelPosition = modelPosition;
        this.preferredPlacement = preferredPlacement;
        this.distance = distance;
        this.ratio = ratio;
        this.text = text;
    }


    /**
     * Return true if this edge label is visible.
     *
     * @return true if this edge label is visible
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Return the alignment for this edge label.
     * The alignment will not be null.
     *
     * @return the alignment for this edge label
     */
    public String getAlignment()
    {
        return alignment;
    }

    /**
     * Return the font family for this edge label.
     * The font family will not be null.
     *
     * @return the font family for this edge label
     */
    public String getFontFamily()
    {
        return fontFamily;
    }

    /**
     * Return the font size for this edge label.
     *
     * @return the font size for this edge label
     */
    public int getFontSize()
    {
        return fontSize;
    }

    /**
     * Return the font style for this edge label.
     * The font style will not be null.
     *
     * @return the font style for this edge label
     */
    public String getFontStyle()
    {
        return fontStyle;
    }

    /**
     * Return the text color for this edge label.
     * The text color will not be null.
     *
     * @return the text color for this edge label
     */
    public String getTextColor()
    {
        return textColor;
    }

    /**
     * Return the model name for this edge label.
     * The model name will not be null.
     *
     * @return the model name for this edge label
     */
    public String getModelName()
    {
        return modelName;
    }

    /**
     * Return the model position for this edge label.
     * The model position will not be null.
     *
     * @return the model position for this edge label
     */
    public String getModelPosition()
    {
        return modelPosition;
    }

    /**
     * Return the preferred placement for this edge label.
     * The preferred placement will not be null.
     *
     * @return the preferred placement for this edge label
     */
    public String getPreferredPlacement()
    {
        return preferredPlacement;
    }

    /**
     * Return the distance for this edge label.
     *
     * @return the distance for this edge label
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * Return the ratio for this edge label.
     *
     * @return the ratio for this edge label
     */
    public double getRatio()
    {
        return ratio;
    }

    /**
     * Return the text for this edge label.
     * The text will not be null.
     *
     * @return the text for this edge label
     */
    public String getText()
    {
        return text;
    }
}