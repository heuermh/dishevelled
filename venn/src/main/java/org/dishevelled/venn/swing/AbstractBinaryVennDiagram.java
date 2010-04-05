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

import java.util.Set;

import javax.swing.JLabel;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

import org.dishevelled.venn.BinaryVennModel3;

import org.dishevelled.venn.model.BinaryVennModelImpl3;

/**
 * Abstract binary venn diagram.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractBinaryVennDiagram<E>
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
    private boolean displaySizes = true;

    /** Label for the first set. */
    private final JLabel firstLabel = new JLabel();

    /** Label for the second set. */
    private final JLabel secondLabel = new JLabel();

    /** Label for the first only view. */
    private final JLabel firstOnlyLabel = new JLabel();

    /** Label for the second only view. */
    private final JLabel secondOnlyLabel = new JLabel();

    /** Label for the intersection view. */
    private final JLabel intersectionLabel = new JLabel();

    /** Label for the union view. */
    private final JLabel unionLabel = new JLabel();

    /** Update labels and contents. */
    private final SetChangeListener<E> update = new SetChangeListener<E>()
        {
            /** {@inheritDoc} */
            public void setChanged(final SetChangeEvent<E> event)
            {
                updateLabels();
                updateContents();
            }
        };

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
     * Create a new empty abstract binary venn diagram.
     */
    protected AbstractBinaryVennDiagram()
    {
        super();
        model = new BinaryVennModelImpl3<E>();

        installListeners();
        updateLabels();
    }

    /**
     * Create a new abstract binary venn diagram with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     */
    protected AbstractBinaryVennDiagram(final String firstLabelText, final Set<? extends E> first,
        final String secondLabelText, final Set<? extends E> second)
    {
        super();
        model = new BinaryVennModelImpl3<E>(first, second);
        this.firstLabelText = firstLabelText;
        this.secondLabelText = secondLabelText;
        this.firstOnlyLabelText = firstLabelText + " only";
        this.secondOnlyLabelText = secondLabelText + " only";

        installListeners();
        updateLabels();
    }

    /**
     * Create a new abstract binary venn diagram with the specified model.
     *
     * @param model model for this abstract binary venn diagram, must not be null
     */
    protected AbstractBinaryVennDiagram(final BinaryVennModel3<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        installListeners();
        updateLabels();
    }


    /**
     * Install listeners.
     */
    private void installListeners()
    {
        model.first().addSetChangeListener(update);
        model.second().addSetChangeListener(update);
    }

    /**
     * Uninstall listeners.
     */
    private void uninstallListeners()
    {
        model.first().removeSetChangeListener(update);
        model.second().removeSetChangeListener(update);
    }

    /**
     * Update labels.
     */
    private void updateLabels()
    {
        firstLabel.setText(buildLabel(firstLabelText, model.first().size()));
        secondLabel.setText(buildLabel(secondLabelText, model.second().size()));
        firstOnlyLabel.setText(buildLabel(firstOnlyLabelText, model.firstOnly().size()));
        secondOnlyLabel.setText(buildLabel(secondOnlyLabelText, model.secondOnly().size()));
        intersectionLabel.setText(buildLabel(intersectionLabelText, model.intersection().size()));
        unionLabel.setText(buildLabel(unionLabelText, model.union().size()));
    }

    /**
     * Update contents.
     */
    protected abstract void updateContents();

    /**
     * Return the label for the first set.
     *
     * @return the label for the first set
     */
    protected final JLabel getFirstLabel()
    {
        return firstLabel;
    }

    /**
     * Return the label for the second set.
     *
     * @return the label for the second set
     */
    protected final JLabel getSecondLabel()
    {
        return secondLabel;
    }

    /**
     * Return the label for the first only view.
     *
     * @return the label for the first only view
     */
    protected final JLabel getFirstOnlyLabel()
    {
        return firstOnlyLabel;
    }

    /**
     * Return the label for the second only view.
     *
     * @return the label for the second only view
     */
    protected final JLabel getSecondOnlyLabel()
    {
        return secondOnlyLabel;
    }

    /**
     * Return the label for the intersection view.
     *
     * @return the label for the intersection view
     */
    protected final JLabel getIntersectionLabel()
    {
        return intersectionLabel;
    }

    /**
     * Return the label for the union view.
     *
     * @return the label for the union view
     */
    protected final JLabel getUnionLabel()
    {
        return unionLabel;
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
        if (displaySizes)
        {
            sb.append(" (");
            sb.append(size);
            sb.append(")");
        }
        sb.append(":");
        return sb.toString();
    }


    /**
     * Return the model for this binary venn label.  The model will not be null.
     *
     * @return the model for this binary venn label
     */
    public final BinaryVennModel3<E> getModel()
    {
        return model;
    }

    /**
     * Set the model for this binary venn label to <code>model</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param model model for this binary venn label, must not be null
     */
    public final void setModel(final BinaryVennModel3<E> model)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        BinaryVennModel3<E> oldModel = this.model;
        uninstallListeners();
        this.model = model;
        installListeners();
        updateLabels();
        firePropertyChange("model", oldModel, this.model);
    }

    /**
     * Return true if labels should display sizes.  Defaults to <code>true</code>.
     *
     * @return true if labels should display sizes
     */
    public final boolean getDisplaySizes()
    {
        return displaySizes;
    }

    /**
     * Set to true if labels should display sizes.
     *
     * <p>This is a bound property.</p>
     *
     * @param displaySizes true if labels should display sizes
     */
    public final void setDisplaySizes(final boolean displaySizes)
    {
        boolean oldDisplaySizes = this.displaySizes;
        this.displaySizes = displaySizes;
        firePropertyChange("displaySizes", oldDisplaySizes, this.displaySizes);
    }

    /**
     * Return the label text for the first set.  Defaults to {@link #DEFAULT_FIRST_LABEL_TEXT}.
     *
     * @return the label text for the first set
     */
    public final String getFirstLabelText()
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
    public final void setFirstLabelText(final String firstLabelText)
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
    public final String getSecondLabelText()
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
    public final void setSecondLabelText(final String secondLabelText)
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
    public final String getFirstOnlyLabelText()
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
    public final void setFirstOnlyLabelText(final String firstOnlyLabelText)
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
    public final String getSecondOnlyLabelText()
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
    public final void setSecondOnlyLabelText(final String secondOnlyLabelText)
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
    public final String getIntersectionLabelText()
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
    public final void setIntersectionLabelText(final String intersectionLabelText)
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
    public final String getUnionLabelText()
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
    public final void setUnionLabelText(final String unionLabelText)
    {
        String oldUnionLabelText = this.unionLabelText;
        this.unionLabelText = unionLabelText;
        unionLabel.setText(buildLabel(this.unionLabelText, model.union().size()));
        firePropertyChange("unionLabelText", this.unionLabelText, oldUnionLabelText);
    }
}