/*

    dsh-venn  Lightweight components for venn diagrams.
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
package org.dishevelled.venn.swing;

import java.util.Iterator;
import java.util.Set;

import javax.swing.JLabel;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.venn.BinaryVennModel3;

import org.dishevelled.venn.model.BinaryVennModelImpl3;

/**
 * Binary venn diagram label 3.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennLabel3<E>
    extends LabelFieldPanel
{
    /** Binary venn model. */
    private BinaryVennModel3<E> model;

    /** Label text for the first set. */
    private String firstLabelText = DEFAULT_FIRST_LABEL_TEXT;

    /** Label text for the second set. */
    private String secondLabelText = DEFAULT_SECOND_LABEL_TEXT;

    /** Label text for the first only view. */
    private String firstOnlyLabelText = DEFAULT_FIRST_ONLY_LABEL_TEXT;

    /** Label text for the second only view. */
    private String secondOnlyLabelText = DEFAULT_SECOND_ONLY_LABEL_TEXT;

    /** Label text for the intersection view. */
    private String intersectionLabelText = DEFAULT_INTERSECTION_LABEL_TEXT;

    /** Label text for the union view. */
    private String unionLabelText = DEFAULT_UNION_LABEL_TEXT;

    /** True if labels should display sizes. */
    private static final boolean SHOW_SIZES = true;

    /** Number of set elements to display. */
    private static final int SET_ELEMENTS_TO_DISPLAY = 5;

    /** Label for the first set. */
    private final JLabel firstLabel = new JLabel();

    /** Contents of the first set. */
    private final JLabel first = new JLabel();

    /** Label for the second set. */
    private final JLabel secondLabel = new JLabel();

    /** Contents of the second set. */
    private final JLabel second = new JLabel();

    /** Label for the first only view. */
    private final JLabel firstOnlyLabel = new JLabel();

    /** Contents of the first only view. */
    private final JLabel firstOnly = new JLabel();

    /** Label for the second only view. */
    private final JLabel secondOnlyLabel = new JLabel();

    /** Contents for the second only view. */
    private final JLabel secondOnly = new JLabel();

    /** Label for the intersection view. */
    private final JLabel intersectionLabel = new JLabel();

    /** Contents of the intersection view. */
    private final JLabel intersection = new JLabel();

    /** Label for the union view. */
    private final JLabel unionLabel = new JLabel();

    /** Contents of the union view. */
    private final JLabel union = new JLabel();

    /** Default label text for the first set, <code>"First set"</code>. */
    public static final String DEFAULT_FIRST_LABEL_TEXT = "First set";

    /** Default label text for the second set, <code>"Second set"</code>. */
    public static final String DEFAULT_SECOND_LABEL_TEXT = "Second set";

    /** Default label text for the first only view, <code>"First only"</code>. */
    public static final String DEFAULT_FIRST_ONLY_LABEL_TEXT = "First only";

    /** Default label text for the second only view, <code>"Second only"</code>. */
    public static final String DEFAULT_SECOND_ONLY_LABEL_TEXT = "Second only";

    /** Default label text for the intersection view, <code>"Intersection"</code>. */
    public static final String DEFAULT_INTERSECTION_LABEL_TEXT = "Intersection";

    /** Default label text for the union view, <code>"Union"</code>. */
    public static final String DEFAULT_UNION_LABEL_TEXT = "Union";


    /**
     * Create a new empty binary venn label.
     */
    public BinaryVennLabel3()
    {
        super();
        model = new BinaryVennModelImpl3<E>();

        installListeners();
        initLabels();
        layoutComponents();
    }

    /**
     * Create a new binary venn label with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     */
    public BinaryVennLabel3(final String firstLabelText, final Set<? extends E> first,
        final String secondLabelText, final Set<? extends E> second)
    {
        super();
        model = new BinaryVennModelImpl3(first, second);
        this.firstLabelText = firstLabelText;
        this.secondLabelText = secondLabelText;

        installListeners();
        initLabels();
        layoutComponents();
    }

    /**
     * Create a new binary venn label with the specified model.
     *
     * @param model model for this binary venn label, must not be null
     */
    public BinaryVennLabel3(final BinaryVennModel3<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        installListeners();
        initLabels();
        layoutComponents();
    }


    /**
     * Install listeners.
     */
    private void installListeners()
    {
        // empty
    }

    /**
     * Initialize labels.
     */
    private void initLabels()
    {
        firstLabel.setText(buildLabel(firstLabelText, model.first().size()));
        first.setText(buildContent(model.first()));
        secondLabel.setText(buildLabel(secondLabelText, model.second().size()));
        second.setText(buildContent(model.second()));
        firstOnlyLabel.setText(buildLabel(firstOnlyLabelText, model.firstOnly().size()));
        firstOnly.setText(buildContent(model.firstOnly()));
        secondOnlyLabel.setText(buildLabel(secondOnlyLabelText, model.secondOnly().size()));
        secondOnly.setText(buildContent(model.secondOnly()));
        intersectionLabel.setText(buildLabel(intersectionLabelText, model.intersection().size()));
        intersection.setText(buildContent(model.intersection()));
        unionLabel.setText(buildLabel(unionLabelText, model.union().size()));
        union.setText(buildContent(model.union()));
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        addField(firstLabel, first);
        addField(secondLabel, second);
        addField(firstOnlyLabel, firstOnly);
        addField(secondOnlyLabel, secondOnly);
        addField(intersectionLabel, intersection);
        addField(unionLabel, union);
        addFinalSpacing();
    }

    /**
     * Build and return label text.
     *
     * @param labelText label text
     * @param size size
     * @return label text
     */
    private String buildLabel(final String labelText, final int size)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(labelText);
        if (SHOW_SIZES)
        {
            sb.append(" (");
            sb.append(size);
            sb.append(")");
        }
        sb.append(":");
        return sb.toString();
    }

    /**
     * Build and return content text.
     *
     * @param <T> value type
     * @param set set
     * @return content text
     */
    private static <T> String buildContent(final Set<T> set)
    {
        if (set.isEmpty())
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<T> iterator = set.iterator();
        sb.append(iterator.next().toString());
        int count = 1;
        while (iterator.hasNext())
        {
            sb.append(", ");
            sb.append(iterator.next().toString());
            if (count++ > SET_ELEMENTS_TO_DISPLAY)
            {
                break;
            }
        }
        if (set.size() > SET_ELEMENTS_TO_DISPLAY)
        {
            sb.append(", ...");
        }
        return sb.toString();
    }

    // todo:  model bound property?

    /**
     * Return the label text for the first set.  Defaults to {@link #DEFAULT_FIRST_LABEL_TEXT}.
     *
     * @return the label text for the first set
     */
    public String getFirstLabelText()
    {
        return firstLabelText;
    }

    /**
     * Set the label text for the first set to <code>firstLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstLabelText label text for the first set
     */
    public void setFirstLabelText(final String firstLabelText)
    {
        String oldFirstLabelText = this.firstLabelText;
        this.firstLabelText = firstLabelText;
        firstLabel.setText(buildLabel(this.firstLabelText, model.first().size()));
        firePropertyChange("firstLabelText", this.firstLabelText, oldFirstLabelText);
    }

    /**
     * Return the label text for the second set.  Defaults to {@link #DEFAULT_SECOND_LABEL_TEXT}.
     *
     * @return the label text for the second set
     */
    public String getSecondLabelText()
    {
        return secondLabelText;
    }

    /**
     * Set the label text for the second set to <code>secondLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param secondLabelText label text for the second set
     */
    public void setSecondLabelText(final String secondLabelText)
    {
        String oldSecondLabelText = this.secondLabelText;
        this.secondLabelText = secondLabelText;
        secondLabel.setText(buildLabel(this.secondLabelText, model.second().size()));
        firePropertyChange("secondLabelText", this.secondLabelText, oldSecondLabelText);
    }

    /**
     * Return the label text for the first only view.  Defaults to {@link #DEFAULT_FIRST_ONLY_LABEL_TEXT}.
     *
     * @return the label text for the first only view
     */
    public String getFirstOnlyLabelText()
    {
        return firstOnlyLabelText;
    }

    /**
     * Set the label text for the first only view to <code>firstOnlyLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstOnlyLabelText label text for the first only view
     */
    public void setFirstOnlyLabelText(final String firstOnlyLabelText)
    {
        String oldFirstOnlyLabelText = this.firstOnlyLabelText;
        this.firstOnlyLabelText = firstOnlyLabelText;
        firstOnlyLabel.setText(buildLabel(this.firstOnlyLabelText, model.firstOnly().size()));
        firePropertyChange("firstOnlyLabelText", this.firstOnlyLabelText, oldFirstOnlyLabelText);
    }

    /**
     * Return the label text for the second only view.  Defaults to {@link #DEFAULT_SECOND_ONLY_LABEL_TEXT}.
     *
     * @return the label text for the second only view
     */
    public String getSecondOnlyLabelText()
    {
        return secondOnlyLabelText;
    }

    /**
     * Set the label text for the second only view to <code>secondOnlyLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param secondOnlyLabelText label text for the second only view
     */
    public void setSecondOnlyLabelText(final String secondOnlyLabelText)
    {
        String oldSecondOnlyLabelText = this.secondOnlyLabelText;
        this.secondOnlyLabelText = secondOnlyLabelText;
        secondOnlyLabel.setText(buildLabel(this.secondOnlyLabelText, model.secondOnly().size()));
        firePropertyChange("secondOnlyLabelText", this.secondOnlyLabelText, oldSecondOnlyLabelText);
    }

    /**
     * Return the label text for the intersection view.  Defaults to {@link #DEFAULT_INTERSECTION_LABEL_TEXT}.
     *
     * @return the label text for the intersection view
     */
    public String getIntersectionLabelText()
    {
        return intersectionLabelText;
    }

    /**
     * Set the label text for the intersection view to <code>intersectionLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param intersectionLabelText label text for the intersection view
     */
    public void setIntersectionLabelText(final String intersectionLabelText)
    {
        String oldIntersectionLabelText = this.intersectionLabelText;
        this.intersectionLabelText = intersectionLabelText;
        intersectionLabel.setText(buildLabel(this.intersectionLabelText, model.intersection().size()));
        firePropertyChange("intersectionLabelText", this.intersectionLabelText, oldIntersectionLabelText);
    }

    /**
     * Return the label text for the union view.  Defaults to {@link #DEFAULT_UNION_LABEL_TEXT}.
     *
     * @return the label text for the union view
     */
    public String getUnionLabelText()
    {
        return unionLabelText;
    }

    /**
     * Set the label text for the union view to <code>unionLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param unionLabelText label text for the union view
     */
    public void setUnionLabelText(final String unionLabelText)
    {
        String oldUnionLabelText = this.unionLabelText;
        this.unionLabelText = unionLabelText;
        unionLabel.setText(buildLabel(this.unionLabelText, model.union().size()));
        firePropertyChange("unionLabelText", this.unionLabelText, oldUnionLabelText);
    }
}