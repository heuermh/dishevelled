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

import org.dishevelled.venn.BinaryVennModel;

import org.dishevelled.venn.model.BinaryVennModelImpl;

/**
 * Binary venn diagram label.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennLabel<E>
    extends LabelFieldPanel
{
    /** Binary venn model. */
    private BinaryVennModel<E> model;

    // todo:  list vs. set in this class?
    /** Label text for the first list. */
    private String list0LabelText = DEFAULT_LIST0_LABEL_TEXT;

    /** Label text for the second list. */
    private String list1LabelText = DEFAULT_LIST1_LABEL_TEXT;

    /** Label text for the intersection. */
    private String intersectionLabelText = DEFAULT_INTERSECTION_LABEL_TEXT;

    /** Label text for the union. */
    private String unionLabelText = DEFAULT_UNION_LABEL_TEXT;

    /** True if labels should display sizes. */
    private static final boolean SHOW_SIZES = true;

    /** Number of list elements to display. */
    private static final int LIST_ELEMENTS_TO_DISPLAY = 5;

    /** Label for the first list. */
    private final JLabel list0Label;

    /** Contents of the first list. */
    private final JLabel list0;

    /** Label for the second list. */
    private final JLabel list1Label;

    /** Contents of the second list. */
    private final JLabel list1;

    /** Label for the intersection. */
    private final JLabel intersectionLabel;

    /** Contents of the intersection. */
    private final JLabel intersection;

    /** Label for the union. */
    private final JLabel unionLabel;

    /** Contents of the union. */
    private final JLabel union;

    /** Default label text for the first list, <code>"First set"</code>. */
    public static final String DEFAULT_LIST0_LABEL_TEXT = "First set";

    /** Default label text for the second list, <code>"Second set"</code>. */
    public static final String DEFAULT_LIST1_LABEL_TEXT = "Second set";

    /** Default label text for the intersection, <code>"Intersection"</code>. */
    public static final String DEFAULT_INTERSECTION_LABEL_TEXT = "Intersection";

    /** Default label text for the union, <code>"Union"</code>. */
    public static final String DEFAULT_UNION_LABEL_TEXT = "Union";


    /**
     * Create a new binary venn label with the specified model.
     *
     * @param model model for this binary venn label, must not be null
     */
    public BinaryVennLabel(final BinaryVennModel<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        list0Label = new JLabel();
        list0 = new JLabel();
        list1Label = new JLabel();
        list1 = new JLabel();
        intersectionLabel = new JLabel();
        intersection = new JLabel();
        unionLabel = new JLabel();
        union = new JLabel();

        installListeners();
        initLabels();
        layoutComponents();
    }

    /**
     * Create a new binary venn label with the specified model.
     *
     * @param list0LabelText label text for the first list
     * @param set0 first set, must not be null
     * @param list1LabelText label text for the second list
     * @param set1 second set, must not be null
     */
    public BinaryVennLabel(final String list0LabelText, final Set<? extends E> set0,
            final String list1LabelText, final Set<? extends E> set1)
    {
        super();
        this.model = new BinaryVennModelImpl<E>(set0, set1);

        this.list0LabelText = list0LabelText;
        this.list1LabelText = list1LabelText;
        list0Label = new JLabel();
        list0 = new JLabel();
        list1Label = new JLabel();
        list1 = new JLabel();
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
        model.list0().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    list0Label.setText(buildLabel(list0LabelText, event.getSourceList().size()));
                    list0.setText(buildContent(event.getSourceList()));
                }
            });

        model.list1().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    list1Label.setText(buildLabel(list1LabelText, event.getSourceList().size()));
                    list1.setText(buildContent(event.getSourceList()));
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
        list0Label.setText(buildLabel(list0LabelText, model.list0().size()));
        list0.setText(buildContent(model.list0()));
        list1Label.setText(buildLabel(list1LabelText, model.list1().size()));
        list1.setText(buildContent(model.list1()));
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
        addField(list0Label, list0);
        addField(list1Label, list1);
        addField(intersectionLabel, intersection);
        addField(unionLabel, union);
        addFinalSpacing();
    }

    /**
     * Build and return label text.
     *
     * @param prefix prefix
     * @param size size
     * @param suffix suffix
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

    /**
     * Return the label text for the first list.  Defaults to {@link #DEFAULT_LIST0_LABEL_TEXT}.
     *
     * @return the label text for the first list
     */
    public String getList0LabelText()
    {
        return list0LabelText;
    }

    /**
     * Set the label text for the first list to <code>list0LabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param list0LabelText label text for the first list
     */
    public void setList0LabelText(final String list0LabelText)
    {
        String oldList0LabelText = this.list0LabelText;
        this.list0LabelText = list0LabelText;
        list0Label.setText(buildLabel(this.list0LabelText, model.list0().size()));
        firePropertyChange("list0LabelText", this.list0LabelText, oldList0LabelText);
    }

    /**
     * Return the label text for the second list.  Defaults to {@link #DEFAULT_LIST1_LABEL_TEXT}.
     *
     * @return the label text for the second list
     */
    public String getList1LabelText()
    {
        return list1LabelText;
    }

    /**
     * Set the label text for the second list to <code>list1LabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param list1LabelText label text for the second list
     */
    public void setList1LabelText(final String list1LabelText)
    {
        String oldList1LabelText = this.list1LabelText;
        this.list1LabelText = list1LabelText;
        list1Label.setText(buildLabel(this.list1LabelText, model.list1().size()));
        firePropertyChange("list1LabelText", this.list1LabelText, oldList1LabelText);
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