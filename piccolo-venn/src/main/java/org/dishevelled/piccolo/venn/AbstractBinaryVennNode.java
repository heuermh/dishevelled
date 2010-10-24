/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
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
package org.dishevelled.piccolo.venn;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.piccolo2d.PNode;

import org.piccolo2d.nodes.PText;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

import org.dishevelled.venn.BinaryVennModel;

import org.dishevelled.venn.model.BinaryVennModelImpl;

/**
 * Abstract binary venn diagram node.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractBinaryVennNode<E>
    extends AbstractVennNode<E>
{
    /** Binary venn model. */
    private BinaryVennModel<E> model;

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

    /** Label for the first set. */
    private final PText firstLabel = new PText();

    /** Label for the second set. */
    private final PText secondLabel = new PText();

    /** Label for the first only view. */
    private final PText firstOnlyLabel = new PText();

    /** Label for the second only view. */
    private final PText secondOnlyLabel = new PText();

    /** Label for the intersection view. */
    private final PText intersectionLabel = new PText();

    /** Label for the union view. */
    private final PText unionLabel = new PText();

    /** List of labels. */
    private final List<PText> labels = Arrays.asList(new PText[] { firstLabel, secondLabel, firstOnlyLabel,
                                                                   secondOnlyLabel, intersectionLabel, unionLabel });

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
     * Create a new empty abstract binary venn diagram node.
     */
    protected AbstractBinaryVennNode()
    {
        super();
        model = new BinaryVennModelImpl<E>();

        installListeners();
        updateLabels();
    }

    /**
     * Create a new abstract binary venn diagram node with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     */
    protected AbstractBinaryVennNode(final String firstLabelText, final Set<? extends E> first,
        final String secondLabelText, final Set<? extends E> second)
    {
        super();
        model = new BinaryVennModelImpl<E>(first, second);
        this.firstLabelText = firstLabelText;
        this.secondLabelText = secondLabelText;
        this.firstOnlyLabelText = firstLabelText + " only";
        this.secondOnlyLabelText = secondLabelText + " only";

        installListeners();
        updateLabels();
    }

    /**
     * Create a new abstract binary venn diagram node with the specified model.
     *
     * @param model model for this abstract binary venn diagram node, must not be null
     */
    protected AbstractBinaryVennNode(final BinaryVennModel<E> model)
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


    /** {@inheritDoc} */
    public final Iterable<PText> labels()
    {
        return labels;
    }

    /**
     * Return the model for this binary venn label.  The model will not be null.
     *
     * @return the model for this binary venn label
     */
    public final BinaryVennModel<E> getModel()
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
    public final void setModel(final BinaryVennModel<E> model)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        BinaryVennModel<E> oldModel = this.model;
        uninstallListeners();
        this.model = model;
        installListeners();
        updateLabels();
        firePropertyChange(-1, "model", oldModel, this.model);
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
        firePropertyChange(-1, "firstLabelText", this.firstLabelText, oldFirstLabelText);
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
        firePropertyChange(-1, "secondLabelText", this.secondLabelText, oldSecondLabelText);
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
        firePropertyChange(-1, "firstOnlyLabelText", this.firstOnlyLabelText, oldFirstOnlyLabelText);
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
        firePropertyChange(-1, "secondOnlyLabelText", this.secondOnlyLabelText, oldSecondOnlyLabelText);
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
        firePropertyChange(-1, "intersectionLabelText", this.intersectionLabelText, oldIntersectionLabelText);
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
        firePropertyChange(-1, "unionLabelText", this.unionLabelText, oldUnionLabelText);
    }

    /**
     * Return the label for the first set.  The text for the returned PText
     * should not be changed, as the text is synchronized to the binary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first set
     */
    public final PText getFirstLabel()
    {
        return firstLabel;
    }

    /**
     * Return the label for the second set.  The text for the returned PText
     * should not be changed, as the text is synchronized to the binary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second set
     */
    public final PText getSecondLabel()
    {
        return secondLabel;
    }

    /**
     * Return the label for the first only view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the binary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstOnlyLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first only view
     */
    public final PText getFirstOnlyLabel()
    {
        return firstOnlyLabel;
    }

    /**
     * Return the label for the second only view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the binary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondOnlyLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second only view
     */
    public final PText getSecondOnlyLabel()
    {
        return secondOnlyLabel;
    }

    /**
     * Return the label for the intersection view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the binary
     * venn model backing this venn diagram.  Use methods
     * {@link #setIntersectionLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the intersection view
     */
    public final PText getIntersectionLabel()
    {
        return intersectionLabel;
    }

    /**
     * Return the label for the union view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the binary
     * venn model backing this venn diagram.  Use methods
     * {@link #setUnionLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the union view
     */
    public final PText getUnionLabel()
    {
        return unionLabel;
    }
}