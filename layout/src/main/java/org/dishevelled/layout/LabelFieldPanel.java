/*

    dsh-layout  Layout managers for lightweight components.
    Copyright (c) 2003-2019 held jointly by the individual authors.

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
package org.dishevelled.layout;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import javax.swing.border.EmptyBorder;

/**
 * Label field panel.
 *
 * @author  Michael Heuer
 */
public class LabelFieldPanel
    extends JPanel
{
    /** True if a final component has been added to this label field panel. */
    private boolean hasFinal;

    /** Label field layout for this label field panel. */
    private final LabelFieldLayout layout;

    /** JDK &lt;= 1.5 MacOSX look and feel class name. */
    private static final String JDK15_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME = "apple.laf.AquaLookAndFeel";

    /** JDK &gt;= 1.6 MacOSX look and feel class name. */
    private static final String JDK16_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME = "com.apple.laf.AquaLookAndFeel";


    /**
     * Create a new label field panel.
     */
    public LabelFieldPanel()
    {
        super();
        layout = new LabelFieldLayout();
        super.setLayout(layout);
    }


    /**
     * Return the percentage of the width of this label field panel to
     * use for labels.
     *
     * @return the percentage of the width of this label field panel
     *    to use for labels
     */
    public final float getLabelPercent()
    {
        return layout.getLabelPercent();
    }

    /**
     * Set the percentage of the width of this label field panel to
     * use for labels to <code>labelPercent</code>. The
     * sum of the label and field percentages must be less
     * than <code>1.0f</code>.
     *
     * @param labelPercent the percentage of the width of this label field panel
     *    to use for labels, must be <code>&gt;= 0.0f</code> and <code>&lt;= 1.0f</code>
     */
    public final void setLabelPercent(final float labelPercent)
    {
        layout.setLabelPercent(labelPercent);
    }

    /**
     * Return the percentage of the width of this label field panel to
     * use for fields.
     *
     * @return the percentage of the width of this label field panel
     *    to use for fields
     */
    public final float getFieldPercent()
    {
        return layout.getFieldPercent();
    }

    /**
     * Set the percentage of the width of this label field panel to use
     * for fields to <code>fieldPercent</code>.  The sum of
     * the label and field percentages must be less than or
     * equal to <code>1.0f</code>.
     *
     * @param fieldPercent the percentage of the width of this label field panel
     *    to use for fields, must be <code>&gt;= 0.0f</code> and <code>&lt;= 1.0f</code>
     */
    public final void setFieldPercent(final float fieldPercent)
    {
        layout.setFieldPercent(fieldPercent);
    }

    /**
     * Add the specified label to this label field panel.
     *
     * @param text label text
     */
    public final void addLabel(final String text)
    {
        JLabel label = new JLabel(text);
        addLabel(label);
    }

    /**
     * Add the specified label to this label field panel.
     *
     * @param label label to add, must not be null
     */
    public final void addLabel(final JLabel label)
    {
        if (label == null)
        {
            throw new IllegalArgumentException("label must not be null");
        }
        super.add(label, layout.wideLabel());
        layout.nextLine();
    }

    /**
     * Add the specified field to this label field panel.
     *
     * @param field field to add, must not be null
     */
    public final void addField(final JComponent field)
    {
        if (field == null)
        {
            throw new IllegalArgumentException("field must not be null");
        }
        super.add(field, layout.wideField());
        layout.nextLine();
    }

    /**
     * Add the specified field to this label field panel.
     *
     * @param labelText label text
     * @param fieldText field text
     */
    public final void addField(final String labelText, final String fieldText)
    {
        addField(labelText, new JLabel(fieldText));
    }

    /**
     * Add the specified label and field to this label field panel.
     *
     * @param text label text
     * @param field field to add, must not be null
     */
    public final void addField(final String text, final JComponent field)
    {
        if (field == null)
        {
            throw new IllegalArgumentException("field must not be null");
        }
        JLabel label = new JLabel(text);
        label.setLabelFor(field);
        addField(label, field);
    }

    /**
     * Add the specified label and field to this label field panel.
     *
     * @param label label to add, must not be null
     * @param field field to add, must not be null
     */
    public final void addField(final JLabel label, final JComponent field)
    {
        if (label == null)
        {
            throw new IllegalArgumentException("label must not be null");
        }
        if (field == null)
        {
            throw new IllegalArgumentException("field must not be null");
        }
        if (field instanceof JLabel)
        {
            if (isMacOSXLookAndFeel())
            {
                label.setHorizontalAlignment(JLabel.RIGHT);
                super.add(label, layout.macLabelLabel());
            }
            else
            {
                super.add(label, layout.labelLabel());
            }
            super.add(field, layout.labelField());
        }
        else
        {
            if (isMacOSXLookAndFeel())
            {
                label.setHorizontalAlignment(JLabel.RIGHT);
                super.add(label, layout.macLabel());
            }
            else
            {
                super.add(label, layout.label());
            }
            super.add(field, layout.field());
        }
        layout.nextLine();
    }

    /**
     * Add spacing to this label field panel of at least the specified
     * number of pixels.
     *
     * @param spacing number of pixels, must be <code>&gt;= 0</code>
     */
    public final void addSpacing(final int spacing)
    {
        if (spacing < 0)
        {
            throw new IllegalArgumentException("spacing must be at least zero, was " + spacing);
        }
        super.add(Box.createVerticalStrut(spacing), layout.spacing());
        layout.nextLine();
    }

    /**
     * Add final spacing to this label field panel, that is spacing
     * that stretches vertically to cover space left at the bottom of
     * this container.  Only one final component may be added to this
     * label field panel.
     *
     * @throws IllegalStateException if a final component has already been
     *    added to this label field panel
     */
    public final void addFinalSpacing()
    {
        if (hasFinal)
        {
            throw new IllegalStateException("already added a final component to this label field panel");
        }
        super.add(Box.createGlue(), layout.finalSpacing());
        hasFinal = true;
    }

    /**
     * Add final spacing to this label field panel of at least the specified
     * number of pixels, that is spacing that stretches vertically to cover
     * space left at the bottom of this container.  Only one final component
     * may be added to this label field panel.
     *
     * @param spacing minimum number of pixels, must be <code>&gt;= 0</code>
     * @throws IllegalStateException if a final component has already been
     *    added to this label field panel
     */
    public final void addFinalSpacing(final int spacing)
    {
        if (hasFinal)
        {
            throw new IllegalStateException("already added a final component to this label field panel");
        }
        if (spacing < 0)
        {
            throw new IllegalArgumentException("spacing must be at least zero, was " + spacing);
        }
        super.add(Box.createVerticalStrut(spacing), layout.finalSpacing());
        hasFinal = true;
    }

    /**
     * Add the specified final field to this label field panel,
     * that is a field that stretches horizontally across both the
     * label and field columns and stretches vertically to cover space
     * left at the bottom of this container.  Only one final component
     * may be added to this label field panel.
     *
     * @param field field to add, must not be null
     * @throws IllegalStateException if a final component has already been
     *    added to this label field panel
     */
    public final void addFinalField(final JComponent field)
    {
        if (hasFinal)
        {
            throw new IllegalStateException("already added a final component to this label field panel");
        }
        if (field == null)
        {
            throw new IllegalArgumentException("field must not be null");
        }
        super.add(field, layout.finalWideField());
        hasFinal = true;
    }

    /**
     * Set the border for this button panel to an empty border the specified width.
     *
     * <p>This is a bound property.</p>
     *
     * @param width width, must be at least zero
     */
    public void setBorder(final int width)
    {
        if (width < 0)
        {
            throw new IllegalArgumentException("border width must be at least zero");
        }
        setBorder(new EmptyBorder(width, width, width, width));
    }

    /**
     * Set the border for this button panel to an empty border the specified dimensions.
     *
     * <p>This is a bound property.</p>
     *
     * @param top top, must be at least zero
     * @param left left, must be at least zero
     * @param bottom bottom, must be at least zero
     * @param right right, must be at least zero
     */
    public void setBorder(final int top, final int left, final int bottom, final int right)
    {
        if (top < 0)
        {
            throw new IllegalArgumentException("border top must be at least zero");
        }
        if (left < 0)
        {
            throw new IllegalArgumentException("border left must be at least zero");
        }
        if (bottom < 0)
        {
            throw new IllegalArgumentException("border bottom must be at least zero");
        }
        if (right < 0)
        {
            throw new IllegalArgumentException("border right must be at least zero");
        }
        setBorder(new EmptyBorder(top, left, bottom, right));
    }

    // override JPanel methods

    /** {@inheritDoc} */
    public final Component add(final Component component)
    {
        throw new UnsupportedOperationException("add operation not supported by LabelFieldPanel");
    }

    /** {@inheritDoc} */
    public final Component add(final String name, final Component component)
    {
        throw new UnsupportedOperationException("add operation not supported by LabelFieldPanel");
    }

    /** {@inheritDoc} */
    public final Component add(final Component component, final int index)
    {
        throw new UnsupportedOperationException("add operation not supported by LabelFieldPanel");
    }

    /** {@inheritDoc} */
    public final void add(final Component component, final Object constraints)
    {
        throw new UnsupportedOperationException("add operation not supported by LabelFieldPanel");
    }

    /** {@inheritDoc} */
    public final void add(final Component component, final Object constraints, final int index)
    {
        throw new UnsupportedOperationException("add operation not supported by LabelFieldPanel");
    }


    /**
     * Return true if the current look and feel is the MacOSX
     * look and feel, specifically <code>apple.laf.AquaLookAndFeel</code>.
     *
     * @see #MAC_OSX_LOOK_AND_FEEL_CLASS_NAME
     * @return true if the current look and feel is the MacOSX look and feel
     */
    private static boolean isMacOSXLookAndFeel()
    {
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        if (lookAndFeel == null)
        {
            return false;
        }
        return JDK15_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME.equals(lookAndFeel.getClass().getName())
            || JDK16_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME.equals(lookAndFeel.getClass().getName());
    }
}
