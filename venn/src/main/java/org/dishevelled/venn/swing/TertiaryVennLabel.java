/*

    dsh-venn  Lightweight components for venn diagrams.
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
package org.dishevelled.venn.swing;

import java.util.List;
import java.util.Set;

import javax.swing.JLabel;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.venn.TertiaryVennModel;

import org.dishevelled.venn.model.TertiaryVennModelImpl;

/**
 * Tertiary venn diagram label.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TertiaryVennLabel<E>
    extends LabelFieldPanel
{
    /** Tertiary venn model. */
    private TertiaryVennModel<E> model;

    /** Label text for the first list view. */
    private String firstLabelText = DEFAULT_FIRST_LABEL_TEXT;

    /** Label text for the second list view. */
    private String secondLabelText = DEFAULT_SECOND_LABEL_TEXT;

    /** Label text for the third list view. */
    private String thirdLabelText = DEFAULT_THIRD_LABEL_TEXT;

    /** Label text for the intersection. */
    private String intersectionLabelText = DEFAULT_INTERSECTION_LABEL_TEXT;

    /** Label text for the union. */
    private String unionLabelText = DEFAULT_UNION_LABEL_TEXT;

    /** True if labels should display sizes. */
    private static final boolean SHOW_SIZES = true;

    /** Number of list elements to display. */
    private static final int LIST_ELEMENTS_TO_DISPLAY = 5;

    /** Label for the first list view. */
    private final JLabel firstLabel;

    /** Contents of the first list view. */
    private final JLabel first;

    /** Label for the second list view. */
    private final JLabel secondLabel;

    /** Contents of the second list view. */
    private final JLabel second;

    /** Label for the third list view. */
    private final JLabel thirdLabel;

    /** Contents of the third list view. */
    private final JLabel third;

    /** Label for the intersection. */
    private final JLabel intersectionLabel;

    /** Contents of the intersection. */
    private final JLabel intersection;

    /** Label for the union. */
    private final JLabel unionLabel;

    /** Contents of the union. */
    private final JLabel union;

    /** Default label text for the first list view, <code>"First set"</code>. */
    public static final String DEFAULT_FIRST_LABEL_TEXT = "First set";

    /** Default label text for the second list view, <code>"Second set"</code>. */
    public static final String DEFAULT_SECOND_LABEL_TEXT = "Second set";

    /** Default label text for the third list view, <code>"Third set"</code>. */
    public static final String DEFAULT_THIRD_LABEL_TEXT = "Third set";

    /** Default label text for the intersection, <code>"Intersection"</code>. */
    public static final String DEFAULT_INTERSECTION_LABEL_TEXT = "Intersection";

    /** Default label text for the union, <code>"Union"</code>. */
    public static final String DEFAULT_UNION_LABEL_TEXT = "Union";


    /**
     * Create a new tertiary venn label with the specified sets.
     *
     * @param firstLabelText label text for the first list view
     * @param first first set, must not be null
     * @param secondLabelText label text for the second list view
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third list view
     * @param third third set, must not be null
     */
    public TertiaryVennLabel(final String firstLabelText, final Set<? extends E> first,
                             final String secondLabelText, final Set<? extends E> second,
                             final String thirdLabelText, final Set<? extends E> third)
    {
        this(new TertiaryVennModelImpl<E>(first, second, third));
    }

    /**
     * Create a new tertiary venn label with the specified model.
     *
     * @param model model for this tertiary venn label, must not be null
     */
    public TertiaryVennLabel(final TertiaryVennModel<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        firstLabel = new JLabel();
        first = new JLabel();
        secondLabel = new JLabel();
        second = new JLabel();
        thirdLabel = new JLabel();
        third = new JLabel();
        intersectionLabel = new JLabel();
        intersection = new JLabel();
        unionLabel = new JLabel();
        union = new JLabel();

        installListeners();
        initLabels();
        layoutComponents();
    }


    /**
     * Install listeners.
     */
    private void installListeners()
    {
        model.first().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    firstLabel.setText(buildLabel(firstLabelText, event.getSourceList().size()));
                    first.setText(buildContent(event.getSourceList()));
                }
            });

        model.second().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    secondLabel.setText(buildLabel(secondLabelText, event.getSourceList().size()));
                    second.setText(buildContent(event.getSourceList()));
                }
            });

        model.third().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    thirdLabel.setText(buildLabel(thirdLabelText, event.getSourceList().size()));
                    third.setText(buildContent(event.getSourceList()));
                }
            });

        model.intersection().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    intersectionLabel.setText(buildLabel(intersectionLabelText, event.getSourceList().size()));
                    intersection.setText(buildContent(event.getSourceList()));
                }
            });

        model.union().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    unionLabel.setText(buildLabel(unionLabelText, event.getSourceList().size()));
                    union.setText(buildContent(event.getSourceList()));
                }
            });
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
        thirdLabel.setText(buildLabel(thirdLabelText, model.third().size()));
        third.setText(buildContent(model.third()));
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
        addField(thirdLabel, third);
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
     * @param list list
     * @return content text
     */
    private static <T> String buildContent(final List<T> list)
    {
        if (list.isEmpty())
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0).toString());
        for (int i = 1, size = Math.min(LIST_ELEMENTS_TO_DISPLAY, list.size()); i < size; i++)
        {
            sb.append(", ");
            sb.append(list.get(i).toString());
        }
        if (list.size() > LIST_ELEMENTS_TO_DISPLAY)
        {
            sb.append(", ...");
        }
        return sb.toString();
    }

    // todo:  model bound property?

    /**
     * Return the label text for the first list view.  Defaults to {@link #DEFAULT_FIRST_LABEL_TEXT}.
     *
     * @return the label text for the first list view
     */
    public String getFirstLabelText()
    {
        return firstLabelText;
    }

    /**
     * Set the label text for the first list view to <code>firstLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstLabelText label text for the first list view
     */
    public void setFirstLabelText(final String firstLabelText)
    {
        String oldFirstLabelText = this.firstLabelText;
        this.firstLabelText = firstLabelText;
        firstLabel.setText(buildLabel(this.firstLabelText, model.first().size()));
        firePropertyChange("firstLabelText", this.firstLabelText, oldFirstLabelText);
    }

    /**
     * Return the label text for the second list view.  Defaults to {@link #DEFAULT_SECOND_LABEL_TEXT}.
     *
     * @return the label text for the second list view
     */
    public String getSecondLabelText()
    {
        return secondLabelText;
    }

    /**
     * Set the label text for the second list view to <code>secondLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param secondLabelText label text for the second list view
     */
    public void setSecondLabelText(final String secondLabelText)
    {
        String oldSecondLabelText = this.secondLabelText;
        this.secondLabelText = secondLabelText;
        secondLabel.setText(buildLabel(this.secondLabelText, model.second().size()));
        firePropertyChange("secondLabelText", this.secondLabelText, oldSecondLabelText);
    }

    /**
     * Return the label text for the third list view.  Defaults to {@link #DEFAULT_THIRD_LABEL_TEXT}.
     *
     * @return the label text for the third list view
     */
    public String getThirdLabelText()
    {
        return thirdLabelText;
    }

    /**
     * Set the label text for the third list view to <code>thirdLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param thirdLabelText label text for the third list view
     */
    public void setThirdLabelText(final String thirdLabelText)
    {
        String oldThirdLabelText = this.thirdLabelText;
        this.thirdLabelText = thirdLabelText;
        thirdLabel.setText(buildLabel(this.thirdLabelText, model.third().size()));
        firePropertyChange("thirdLabelText", this.thirdLabelText, oldThirdLabelText);
    }

    /**
     * Return the label text for the intersection.  Defaults to {@link #DEFAULT_INTERSECTION_LABEL_TEXT}.
     *
     * @return the label text for the intersection
     */
    public String getIntersectionLabelText()
    {
        return intersectionLabelText;
    }

    /**
     * Set the label text for the intersection to <code>intersectionLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param intersectionLabelText label text for the intersection
     */
    public void setIntersectionLabelText(final String intersectionLabelText)
    {
        String oldIntersectionLabelText = this.intersectionLabelText;
        this.intersectionLabelText = intersectionLabelText;
        intersectionLabel.setText(buildLabel(this.intersectionLabelText, model.intersection().size()));
        firePropertyChange("intersectionLabelText", this.intersectionLabelText, oldIntersectionLabelText);
    }

    /**
     * Return the label text for the union.  Defaults to {@link #DEFAULT_UNION_LABEL_TEXT}.
     *
     * @return the label text for the union
     */
    public String getUnionLabelText()
    {
        return unionLabelText;
    }

    /**
     * Set the label text for the union to <code>unionLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param unionLabelText label text for the union
     */
    public void setUnionLabelText(final String unionLabelText)
    {
        String oldUnionLabelText = this.unionLabelText;
        this.unionLabelText = unionLabelText;
        unionLabel.setText(buildLabel(this.unionLabelText, model.union().size()));
        firePropertyChange("unionLabelText", this.unionLabelText, oldUnionLabelText);
    }
}