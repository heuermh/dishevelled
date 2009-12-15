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

import java.awt.GridLayout;

import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import ca.odell.glazedlists.swing.EventListModel;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.venn.BinaryVennModel;

import org.dishevelled.venn.model.BinaryVennModelImpl;

/**
 * Binary venn diagram list.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennList<E>
    extends JPanel
{
    /** Binary venn model. */
    private BinaryVennModel<E> model;

    /** Label text for the first list view. */
    private String firstLabelText = DEFAULT_FIRST_LABEL_TEXT;

    /** Label text for the second list view. */
    private String secondLabelText = DEFAULT_SECOND_LABEL_TEXT;

    /** Label text for the intersection. */
    private String intersectionLabelText = DEFAULT_INTERSECTION_LABEL_TEXT;

    /** Label text for the union. */
    private String unionLabelText = DEFAULT_UNION_LABEL_TEXT;

    /** True if labels should display sizes. */
    private static final boolean SHOW_SIZES = true;

    /** Label for the first list view. */
    private final JLabel firstLabel;

    /** Contents of the first list view. */
    private final JList first;

    /** Label for the second list view. */
    private final JLabel secondLabel;

    /** Contents of the second list view. */
    private final JList second;

    /** Label for the intersection. */
    private final JLabel intersectionLabel;

    /** Contents of the intersection. */
    private final JList intersection;

    /** Label for the union. */
    private final JLabel unionLabel;

    /** Contents of the union. */
    private final JList union;

    /** Default label text for the first list view, <code>"First set"</code>. */
    public static final String DEFAULT_FIRST_LABEL_TEXT = "First set";

    /** Default label text for the second list view, <code>"Second set"</code>. */
    public static final String DEFAULT_SECOND_LABEL_TEXT = "Second set";

    /** Default label text for the intersection, <code>"Intersection"</code>. */
    public static final String DEFAULT_INTERSECTION_LABEL_TEXT = "Intersection";

    /** Default label text for the union, <code>"Union"</code>. */
    public static final String DEFAULT_UNION_LABEL_TEXT = "Union";


    /**
     * Create a new binary venn list with the specified sets.
     *
     * @param firstLabelText label text for the first list view
     * @param first first set, must not be null
     * @param secondLabelText label text for the second list view
     * @param second second set, must not be null
     */
    public BinaryVennList(final String firstLabelText, final Set<? extends E> first,
                          final String secondLabelText, final Set<? extends E> second)
    {
        this(new BinaryVennModelImpl<E>(first, second));
    }

    /**
     * Create a new binary venn list with the specified model.
     *
     * @param model model for this binary venn list, must not be null
     */
    public BinaryVennList(final BinaryVennModel<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        firstLabel = new JLabel();
        secondLabel = new JLabel();
        intersectionLabel = new JLabel();
        unionLabel = new JLabel();

        first = new JList(new EventListModel<E>(this.model.first()));
        second = new JList(new EventListModel<E>(this.model.second()));
        intersection = new JList(new EventListModel<E>(this.model.intersection()));
        union = new JList(new EventListModel<E>(this.model.union()));

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
                }
            });

        model.second().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    secondLabel.setText(buildLabel(secondLabelText, event.getSourceList().size()));
                }
            });

        model.intersection().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    intersectionLabel.setText(buildLabel(intersectionLabelText, event.getSourceList().size()));
                }
            });

        model.union().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    unionLabel.setText(buildLabel(unionLabelText, event.getSourceList().size()));
                }
            });
    }

    /**
     * Initialize labels.
     */
    private void initLabels()
    {
        firstLabel.setText(buildLabel(firstLabelText, model.first().size()));
        secondLabel.setText(buildLabel(secondLabelText, model.second().size()));
        intersectionLabel.setText(buildLabel(intersectionLabelText, model.intersection().size()));
        unionLabel.setText(buildLabel(unionLabelText, model.union().size()));
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new GridLayout(1, 4, 12, 0));

        LabelFieldPanel f = new LabelFieldPanel();
        f.addLabel(firstLabel);
        f.addFinalField(new JScrollPane(first));
        add(f);

        LabelFieldPanel s = new LabelFieldPanel();
        s.addLabel(secondLabel);
        s.addFinalField(new JScrollPane(second));
        add(s);

        LabelFieldPanel i = new LabelFieldPanel();
        i.addLabel(intersectionLabel);
        i.addFinalField(new JScrollPane(intersection));
        add(i);

        LabelFieldPanel u = new LabelFieldPanel();
        u.addLabel(unionLabel);
        u.addFinalField(new JScrollPane(union));
        add(u);
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
