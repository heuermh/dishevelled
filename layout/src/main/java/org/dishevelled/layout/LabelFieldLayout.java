/*

    dsh-layout  Layout manager(s) for lightweight components.
    Copyright (c) 2003-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.layout;

import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * A wrapper around GridBagLayout that simplifies things
 * for the common case of two columns, one of labels and
 * one of fields.
 *
 * <p>A simple example:
 * <pre>
 * JPanel panel = new JPanel();
 * LabelFieldLayout l = new LabelFieldLayout();
 * panel.setLayout(l);
 *
 * panel.add(new JLabel("Label label:"), l.labelLabel());
 * panel.add(new JLabel("Label field"), l.labelField());
 *
 * l.nextLine();
 * panel.add(new JLabel("Normal label:"), l.normalLabel());
 * panel.add(new JTextField("Normal field"), l.normalField());
 *
 * l.nextLine();
 * panel.add(Box.createVerticalStrut(12), l.spacing());
 *
 * l.nextLine();
 * panel.add(new JLabel("Wide label:"), l.wideLabel());
 *
 * l.nextLine();
 * JList list = new JList(new Object[] { "Final wide field A",
 *                                       "Final wide field B",
 *                                       "Final wide field C" });
 * panel.add(new JScrollPane(list), l.finalWideField());
 * </pre></p>
 *
 * <p>The labels are aligned in the left column and the fields are
 * aligned in the right column, except for a wide label or field
 * which stretches vertically across both columns (<code>wideLabel()</code>
 * or <code>wideField()</code>).  Rows containing fields that
 * are JLabels (<code>labelLabel()</code> and <code>labelField()</code>)
 * have less of a vertical gap than those containing other components
 * (<code>normalLabel()</code> and <code>normalField()</code>).</p>
 *
 * <p>The final line in a LabelFieldLayout is the one which stretches
 * vertically to cover space left at the bottom of a container.  Use a
 * final wide label
 * <pre>
 * panel.add(new JLabel("Final wide label", l.finalWideLabel()));
 * </pre>
 *
 * a final wide field
 * <pre>
 * JList list = new JList(new Object[] {"Final wide field A",
 *                                      "Final wide field B",
 *                                      "Final wide field C" });
 * panel.add(new JScrollPane(list), l.finalWideField());
 * </pre>
 *
 * or final spacing
 * <pre>
 * panel.add(Box.createGlue(), l.finalSpacing());
 * </pre>
 *
 * to allow the layout to adapt gracefully to container resize events.</p>
 *
 * <p>The width of the label and field columns can be specified using
 * the constructor <code>LabelFieldLayout(float, float)</code> or the
 * property setters <code>setLabelPercent(float)</code> and
 * <code>setFieldPercent(float)</code>.  The default configuration is
 * to split on thirds, with 33% of the container width for labels and 66%
 * for fields.</p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class LabelFieldLayout
    extends GridBagLayout
{
    /** Default inset size. */
    private static final int DEFAULT_INSET_SIZE = 6;

    /** Default label width, <code>33%</code>. */
    private static final float DEFAULT_LABEL_WIDTH = 0.33f;

    /** Default field width, <code>66%</code>. */
    private static final float DEFAULT_FIELD_WIDTH = 0.66f;

    /** Empty insets. */
    private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

    /** Label insets. */
    private static final Insets LABEL_INSETS = new Insets(0, 0, DEFAULT_INSET_SIZE, 0);

    /** Field insets. */
    private static final Insets FIELD_INSETS = new Insets(0, 0, DEFAULT_INSET_SIZE, 0);

    /** Private constraints. */
    private Constraints c = new Constraints();

    /** Percent of container width to use for labels. */
    private float labelPercent;

    /** Percent of container width to use for fields. */
    private float fieldPercent;


    /**
     * Create a new LabelFieldLayout in the default configuration,
     * split on thirds, with 33% of the container width for labels and 66%
     * for fields.
     */
    public LabelFieldLayout()
    {
        this(DEFAULT_LABEL_WIDTH, DEFAULT_FIELD_WIDTH);
    }

    /**
     * Create a new LabelFieldLayout with the specified percentages
     * of the container width to use for labels and fields respectively.
     * The sum of the label and field percentages must be less than or
     * equal to <code>1.0f</code>.
     *
     * @param labelPercent the percentage of the container width
     *    to use for labels, must be <code>&gt;= 0.0f</code>
     *   and <code>&lt;= 1.0f</code>
     * @param fieldPercent the percentage of the container width
     *    to use for fields, must be <code>&gt;= 0.0f</code>
     *   and <code>&lt;= 1.0f</code>
     */
    public LabelFieldLayout(final float labelPercent,
                            final float fieldPercent)
    {
        super();

        if ((labelPercent < 0.0f) || (labelPercent > 1.0f))
        {
            throw new IllegalArgumentException("labelPercent must be >= 0.0f and <= 1.0f");
        }
        if ((fieldPercent < 0.0f) || (fieldPercent > 1.0f))
        {
            throw new IllegalArgumentException("fieldPercent must be >= 0.0f and <= 1.0f");
        }
        if ((labelPercent + fieldPercent) > 1.0f)
        {
            throw new IllegalArgumentException("the sum of labelPercent and fieldPercent must be <= 1.0f");
        }

        c.gridy = 0;
        this.labelPercent = labelPercent;
        this.fieldPercent = fieldPercent;
    }


    /**
     * Increment the layout cursor to the next line.  Returns
     * this instance of LabelFieldLayout, for use in chaining
     * calls.
     *
     * @return this instance of LabelFieldLayout, for use
     *    in chaining method calls
     */
    public LabelFieldLayout nextLine()
    {
        c.gridy++;
        return this;
    }

    /**
     * Set the percentage of the container width to
     * use for labels to <code>labelPercent</code>. The
     * sum of the label and field percentages must be less
     * than <code>1.0f</code>.
     *
     * @param labelPercent the percentage of the container
     *    width to use for labels, must be <code>&gt;= 0.0f</code>
     *   and <code>&lt;= 1.0f</code>
     */
    public void setLabelPercent(final float labelPercent)
    {
        if ((labelPercent < 0.0f) || (labelPercent > 1.0f))
        {
            throw new IllegalArgumentException("labelPercent must be >= 0.0f and <= 1.0f");
        }
        if ((labelPercent + fieldPercent) > 1.0f)
        {
            throw new IllegalArgumentException("the sum of labelPercent and fieldPercent must be <= 1.0f");
        }

        this.labelPercent = labelPercent;
    }

    /**
     * Return the percentage of the container width to
     * use for labels.
     *
     * @return the percentage of the container width
     *    to use for labels
     */
    public float getLabelPercent()
    {
        return labelPercent;
    }

    /**
     * Set the percentage of the container width to use
     * for fields to <code>fieldPercent</code>.  The sum of
     * the label and field percentages must be less than or
     * equal to <code>1.0f</code>.
     *
     * @param fieldPercent the percentage of the container
     *    width to use for fields, must be <code>&gt;= 0.0f</code>
     *   and <code>&lt;= 1.0f</code>
     */
    public void setFieldPercent(final float fieldPercent)
    {
        if ((fieldPercent < 0.0f) || (fieldPercent > 1.0f))
        {
            throw new IllegalArgumentException("fieldPercent must be >= 0.0f and <= 1.0f");
        }
        if ((labelPercent + fieldPercent) > 1.0f)
        {
            throw new IllegalArgumentException("the sum of labelPercent and fieldPercent must be <= 1.0f");
        }

        this.fieldPercent = fieldPercent;
    }

    /**
     * Return the percentage of the container width to
     * use for fields.
     *
     * @return the percentage of the container width
     *    to use for fields
     */
    public float getFieldPercent()
    {
        return fieldPercent;
    }

    /**
     * Return constraints suitable for a label.
     *
     * @see #normalLabel
     * @return constraints suitable for a label.
     */
    public Constraints label()
    {
        return normalLabel();
    }

    /**
     * Return constraints suitable for a normal label, a label
     * for a field that is not a JLabel.
     *
     * @return constraints suitable for a normal label
     */
    public Constraints normalLabel()
    {
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = LABEL_INSETS;

        c.gridx = 0;
        c.weighty = 0;
        c.weightx = labelPercent;

        return c;
    }

    /**
     * Return constraints suitable for a label label, a label
     * for a field that is a JLabel.
     *
     * @return constraints suitable for a label label
     */
    public Constraints labelLabel()
    {
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = EMPTY_INSETS;

        c.gridx = 0;
        c.weighty = 0;
        c.weightx = labelPercent;

        return c;
    }

    /**
     * Return constraints suitable for a wide label, a label
     * that stretches horizontally across both the label and field
     * columns.
     *
     * @return constraints suitable for a wide label
     */
    public Constraints wideLabel()
    {
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = EMPTY_INSETS;

        c.gridx = 0;
        c.weighty = 0;
        c.weightx = 1.0f;

        return c;
    }

    /**
     * Return constraints suitable for a final wide label, a label
     * that stretches horizontally across both the label and field
     * columns and stretches vertically to cover space left at the
     * bottom of a container.
     *
     * @return constraints suitable for a final wide label
     */
    public Constraints finalWideLabel()
    {
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = EMPTY_INSETS;

        c.gridx = 0;
        c.weighty = 1.0f;
        c.weightx = 1.0f;

        return c;
    }

    /**
     * Return constraints suitable for a field.
     *
     * @see #normalField
     * @return constraints suitable for a field
     */
    public Constraints field()
    {
        return normalField();
    }

    /**
     * Return constraints suitable for a normal field, a field
     * that is not a JLabel.
     *
     * @return constraints suitable for a normal field
     */
    public Constraints normalField()
    {
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = FIELD_INSETS;

        c.gridx = 1;
        c.weighty = 0;
        c.weightx = fieldPercent;

        return c;
    }

    /**
     * Return constraints suitable for a label field, a field
     * that is a JLabel.
     *
     * @return constraints suitable for a label field
     */
    public Constraints labelField()
    {
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = EMPTY_INSETS;

        c.gridx = 1;
        c.weighty = 0;
        c.weightx = fieldPercent;

        return c;
    }

    /**
     * Return constraints suitable for a wide field, a field
     * that stretches horizontally across both the label and field
     * columns.
     *
     * @return constraints suitable for a wide field
     */
    public Constraints wideField()
    {
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = FIELD_INSETS;

        c.gridx = 0;
        c.weighty = 0;
        c.weightx = 1.0f;

        return c;
    }

    /**
     * Return constraints suitable for a final wide field, a field
     * that stretches horizontally across both the label and field
     * columns and stretches vertically to cover space left at the
     * bottom of a container.
     *
     * @return constraints suitable for a final wide field
     */
    public Constraints finalWideField()
    {
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = EMPTY_INSETS;

        c.gridx = 0;
        c.weighty = 1.0f;
        c.weightx = 1.0f;

        return c;
    }

    /**
     * Return constraints suitable for spacing.
     *
     * @return constraints suitable for spacing
     */
    public Constraints spacing()
    {
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = EMPTY_INSETS;

        c.gridx = 0;
        c.weighty = 0;
        c.weightx = 1.0f;

        return c;
    }

    /**
     * Return constraints suitable for spacing that stretches
     * vertically to cover space left at the bottom of a container.
     *
     * @return constraints suitable for spacing that stretches
     *    vertically to cover space left at the bottom of a container
     */
    public Constraints finalSpacing()
    {
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = EMPTY_INSETS;

        c.gridx = 0;
        c.weighty = 1.0f;
        c.weightx = 1.0f;

        return c;
    }

   /**
    * Constraints class that extends GridBagConstraints for
    * the single purpose of having the classname
    * <code>LabelFieldLayout.Constraints</code>.
    */
    public final class Constraints
        extends GridBagConstraints
    {
        // empty
    }
}
