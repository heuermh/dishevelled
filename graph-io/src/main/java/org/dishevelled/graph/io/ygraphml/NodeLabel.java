/*

    dsh-graph-io  Directed graph readers and writers.
    Copyright (c) 2008-2011 held jointly by the individual authors.

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
 * Node label.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NodeLabel
{
    /** True if this node label is visible. */
    private final boolean visible;

    /** Alignment for this node label. */
    private final String alignment;

    /** Font family for this node label. */
    private final String fontFamily;

    /** Font size for this node label. */
    private final int fontSize;

    /** Font style for this node label. */
    private final String fontStyle;

    /** Text color for this node label. */
    private final String textColor;

    /** Model name for this node label. */
    private final String modelName;

    /** Model position for this node label. */
    private final String modelPosition;

    /** Auto size policy for this node label. */
    private final String autoSizePolicy;

    /** Text for this node label. */
    private final String text;


    /**
     * Create a new node label from the specified parameters.
     *
     * @param visible true if this node label is visible
     * @param alignment alignment for this node label, must not be null
     * @param fontFamily font family for this node label, must not be null
     * @param fontSize font size for this node label
     * @param fontStyle font style for this node label, must not be null
     * @param textColor text color for this node label, must not be null
     * @param modelName model name for this node label, must not be null
     * @param modelPosition model position for this node label, must not be null
     * @param autoSizePolicy auto size policy for this node label, must not be null
     * @param text text for this node label, must not be null
     */
    public NodeLabel(final boolean visible,
                     final String alignment,
                     final String fontFamily,
                     final int fontSize,
                     final String fontStyle,
                     final String textColor,
                     final String modelName,
                     final String modelPosition,
                     final String autoSizePolicy,
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
        if (autoSizePolicy == null)
        {
            throw new IllegalArgumentException("autoSizePolicy must not be null");
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
        this.autoSizePolicy = autoSizePolicy;
        this.text = text;
    }


    /**
     * Return true if this node label is visible.
     *
     * @return true if this node label is visible
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Return the alignment for this node label.
     * The alignment will not be null.
     *
     * @return the alignment for this node label
     */
    public String getAlignment()
    {
        return alignment;
    }

    /**
     * Return the font family for this node label.
     * The font family will not be null.
     *
     * @return the font family for this node label
     */
    public String getFontFamily()
    {
        return fontFamily;
    }

    /**
     * Return the font size for this node label.
     *
     * @return the font size for this node label
     */
    public int getFontSize()
    {
        return fontSize;
    }

    /**
     * Return the font style for this node label.
     * The font style will not be null.
     *
     * @return the font style for this node label
     */
    public String getFontStyle()
    {
        return fontStyle;
    }

    /**
     * Return the text color for this node label.
     * The text color will not be null.
     *
     * @return the text color for this node label
     */
    public String getTextColor()
    {
        return textColor;
    }

    /**
     * Return the model name for this node label.
     * The model name will not be null.
     *
     * @return the model name for this node label
     */
    public String getModelName()
    {
        return modelName;
    }

    /**
     * Return the model position for this node label.
     * The model position will not be null.
     *
     * @return the model position for this node label
     */
    public String getModelPosition()
    {
        return modelPosition;
    }

    /**
     * Return the auto size policy for this node label.
     * The auto size policy will not be null.
     *
     * @return the auto size policy for this node label
     */
    public String getAutoSizePolicy()
    {
        return autoSizePolicy;
    }

    /**
     * Return the text for this node label.
     * The text will not be null.
     *
     * @return the text for this node label
     */
    public String getText()
    {
        return text;
    }
}