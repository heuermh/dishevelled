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

import java.util.Set;

import org.piccolo2d.PNode;

import org.piccolo2d.nodes.PText;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

import org.dishevelled.venn.TernaryVennModel3;

import org.dishevelled.venn.model.TernaryVennModelImpl3;

/**
 * Abstract ternary venn diagram node.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTernaryVennNode<E>
    extends PNode
{
    /** Ternary venn model. */
    private TernaryVennModel3<E> model;

    /** Label text for the first set. */
    private String firstLabelText = DEFAULT_FIRST_LABEL_TEXT;

    /** Label text for the second set. */
    private String secondLabelText = DEFAULT_SECOND_LABEL_TEXT;

    /** Label text for the third set. */
    private String thirdLabelText = DEFAULT_THIRD_LABEL_TEXT;

    /** Label text for the first only view. */
    private String firstOnlyLabelText = DEFAULT_FIRST_ONLY_LABEL_TEXT;

    /** Label text for the second only view. */
    private String secondOnlyLabelText = DEFAULT_SECOND_ONLY_LABEL_TEXT;

    /** Label text for the third only view. */
    private String thirdOnlyLabelText = DEFAULT_THIRD_ONLY_LABEL_TEXT;

    /** Label text for the first second view. */
    private String firstSecondLabelText = DEFAULT_FIRST_SECOND_LABEL_TEXT;

    /** Label text for the first third view. */
    private String firstThirdLabelText = DEFAULT_FIRST_THIRD_LABEL_TEXT;

    /** Label text for the second third view. */
    private String secondThirdLabelText = DEFAULT_SECOND_THIRD_LABEL_TEXT;

    /** Label text for the intersection view. */
    private String intersectionLabelText = DEFAULT_INTERSECTION_LABEL_TEXT;

    /** Label text for the union view. */
    private String unionLabelText = DEFAULT_UNION_LABEL_TEXT;

    /** True if labels should display sizes. */
    private boolean displaySizes = true;

    /** Label for the first set. */
    private final PText firstLabel = new PText();

    /** Label for the second set. */
    private final PText secondLabel = new PText();

    /** Label for the third set. */
    private final PText thirdLabel = new PText();

    /** Label for the first only view. */
    private final PText firstOnlyLabel = new PText();

    /** Label for the second only view. */
    private final PText secondOnlyLabel = new PText();

    /** Label for the third only view. */
    private final PText thirdOnlyLabel = new PText();

    /** Label for the first second view. */
    private final PText firstSecondLabel = new PText();

    /** Label for the first third view. */
    private final PText firstThirdLabel = new PText();

    /** Label for the second third view. */
    private final PText secondThirdLabel = new PText();

    /** Label for the intersection view. */
    private final PText intersectionLabel = new PText();

    /** Label for the union view. */
    private final PText unionLabel = new PText();

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

    /** Default label text for the third set, <code>"Third set"</code>. */
    public static final String DEFAULT_THIRD_LABEL_TEXT = "Third set";

    /** Default label text for the first only view, <code>"First only"</code>. */
    public static final String DEFAULT_FIRST_ONLY_LABEL_TEXT = "First only";

    /** Default label text for the second only view, <code>"Second only"</code>. */
    public static final String DEFAULT_SECOND_ONLY_LABEL_TEXT = "Second only";

    /** Default label text for the third only view, <code>"Third only"</code>. */
    public static final String DEFAULT_THIRD_ONLY_LABEL_TEXT = "Third only";

    /** Default label text for the first second view, <code>"First and second only"</code>. */
    public static final String DEFAULT_FIRST_SECOND_LABEL_TEXT = "First and second only";

    /** Default label text for the first third view, <code>"First and third only"</code>. */
    public static final String DEFAULT_FIRST_THIRD_LABEL_TEXT = "First and third only";

    /** Default label text for the second third view, <code>"Second and third only"</code>. */
    public static final String DEFAULT_SECOND_THIRD_LABEL_TEXT = "Second and third only";

    /** Default label text for the intersection view, <code>"Intersection"</code>. */
    public static final String DEFAULT_INTERSECTION_LABEL_TEXT = "Intersection";

    /** Default label text for the union view, <code>"Union"</code>. */
    public static final String DEFAULT_UNION_LABEL_TEXT = "Union";


    /**
     * Create a new empty abstract ternary venn diagram node.
     */
    protected AbstractTernaryVennNode()
    {
        super();
        model = new TernaryVennModelImpl3<E>();

        installListeners();
        updateLabels();
    }

    /**
     * Create a new abstract ternary venn diagram node with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third set
     * @param third third set, must not be null
     */
    protected AbstractTernaryVennNode(final String firstLabelText, final Set<? extends E> first,
                                       final String secondLabelText, final Set<? extends E> second,
                                       final String thirdLabelText, final Set<? extends E> third)
    {
        super();
        model = new TernaryVennModelImpl3<E>(first, second, third);
        this.firstLabelText = firstLabelText;
        this.secondLabelText = secondLabelText;
        this.thirdLabelText = thirdLabelText;
        this.firstOnlyLabelText = firstLabelText + " only";
        this.secondOnlyLabelText = secondLabelText + " only";
        this.thirdOnlyLabelText = thirdLabelText + " only";
        this.firstSecondLabelText = firstLabelText + " and " + secondLabelText + " only";
        this.firstThirdLabelText = firstLabelText + " and " + thirdLabelText + " only";
        this.secondThirdLabelText = secondLabelText + " and " + thirdLabelText + " only";

        installListeners();
        updateLabels();
    }

    /**
     * Create a new abstract ternary venn diagram node with the specified model.
     *
     * @param model model for this abstract ternary venn diagram node, must not be null
     */
    protected AbstractTernaryVennNode(final TernaryVennModel3<E> model)
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
        model.third().addSetChangeListener(update);
    }

    /**
     * Uninstall listeners.
     */
    private void uninstallListeners()
    {
        model.first().removeSetChangeListener(update);
        model.second().removeSetChangeListener(update);
        model.third().removeSetChangeListener(update);
    }

    /**
     * Update labels.
     */
    private void updateLabels()
    {
        firstLabel.setText(buildLabel(firstLabelText, model.first().size()));
        secondLabel.setText(buildLabel(secondLabelText, model.second().size()));
        thirdLabel.setText(buildLabel(thirdLabelText, model.third().size()));
        firstOnlyLabel.setText(buildLabel(firstOnlyLabelText, model.firstOnly().size()));
        secondOnlyLabel.setText(buildLabel(secondOnlyLabelText, model.secondOnly().size()));
        thirdOnlyLabel.setText(buildLabel(thirdOnlyLabelText, model.thirdOnly().size()));
        firstSecondLabel.setText(buildLabel(firstSecondLabelText, model.firstSecond().size()));
        firstThirdLabel.setText(buildLabel(firstThirdLabelText, model.firstThird().size()));
        secondThirdLabel.setText(buildLabel(secondThirdLabelText, model.secondThird().size()));
        intersectionLabel.setText(buildLabel(intersectionLabelText, model.intersection().size()));
        unionLabel.setText(buildLabel(unionLabelText, model.union().size()));
    }

    /**
     * Update contents.
     */
    protected abstract void updateContents();

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
     * Return the model for this ternary venn label.  The model will not be null.
     *
     * @return the model for this ternary venn label
     */
    public final TernaryVennModel3<E> getModel()
    {
        return model;
    }

    /**
     * Set the model for this ternary venn label to <code>model</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param model model for this ternary venn label, must not be null
     */
    public final void setModel(final TernaryVennModel3<E> model)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        TernaryVennModel3<E> oldModel = this.model;
        uninstallListeners();
        this.model = model;
        installListeners();
        updateLabels();
        firePropertyChange(-1, "model", oldModel, this.model);
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
        firePropertyChange(-1, "displaySizes", oldDisplaySizes, this.displaySizes);
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
     * Return the label text for the third set.  Defaults to {@link #DEFAULT_THIRD_LABEL_TEXT}.
     *
     * @return the label text for the third set
     */
    public final String getThirdLabelText()
    {
        return thirdLabelText;
    }

    /**
     * Set the label text for the third set to <code>thirdLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param thirdLabelText label text for the third set
     */
    public final void setThirdLabelText(final String thirdLabelText)
    {
        String oldThirdLabelText = this.thirdLabelText;
        this.thirdLabelText = thirdLabelText;
        thirdLabel.setText(buildLabel(this.thirdLabelText, model.third().size()));
        firePropertyChange(-1, "thirdLabelText", this.thirdLabelText, oldThirdLabelText);
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
     * Return the label text for the third only view.  Defaults to {@link #DEFAULT_THIRD_ONLY_LABEL_TEXT}.
     *
     * @return the label text for the third only view
     */
    public final String getThirdOnlyLabelText()
    {
        return thirdOnlyLabelText;
    }

    /**
     * Set the label text for the third only view to <code>thirdOnlyLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param thirdOnlyLabelText label text for the third only view
     */
    public final void setThirdOnlyLabelText(final String thirdOnlyLabelText)
    {
        String oldThirdOnlyLabelText = this.thirdOnlyLabelText;
        this.thirdOnlyLabelText = thirdOnlyLabelText;
        thirdOnlyLabel.setText(buildLabel(this.thirdOnlyLabelText, model.thirdOnly().size()));
        firePropertyChange(-1, "thirdOnlyLabelText", this.thirdOnlyLabelText, oldThirdOnlyLabelText);
    }

    /**
     * Return the label text for the first second view.  Defaults to {@link #DEFAULT_FIRST_SECOND_LABEL_TEXT}.
     *
     * @return the label text for the first second view
     */
    public final String getFirstSecondLabelText()
    {
        return firstSecondLabelText;
    }

    /**
     * Set the label text for the first second view to <code>firstSecondLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstSecondLabelText label text for the first second view
     */
    public final void setFirstSecondLabelText(final String firstSecondLabelText)
    {
        String oldFirstSecondLabelText = this.firstSecondLabelText;
        this.firstSecondLabelText = firstSecondLabelText;
        firstSecondLabel.setText(buildLabel(this.firstSecondLabelText, model.firstSecond().size()));
        firePropertyChange(-1, "firstSecondLabelText", this.firstSecondLabelText, oldFirstSecondLabelText);
    }

    /**
     * Return the label text for the first third view.  Defaults to {@link #DEFAULT_FIRST_THIRD_LABEL_TEXT}.
     *
     * @return the label text for the first third view
     */
    public final String getFirstThirdLabelText()
    {
        return firstThirdLabelText;
    }

    /**
     * Set the label text for the first third view to <code>firstThirdLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstThirdLabelText label text for the first third view
     */
    public final void setFirstThirdLabelText(final String firstThirdLabelText)
    {
        String oldFirstThirdLabelText = this.firstThirdLabelText;
        this.firstThirdLabelText = firstThirdLabelText;
        firstThirdLabel.setText(buildLabel(this.firstThirdLabelText, model.firstThird().size()));
        firePropertyChange(-1, "firstThirdLabelText", this.firstThirdLabelText, oldFirstThirdLabelText);
    }

    /**
     * Return the label text for the second third view.  Defaults to {@link #DEFAULT_SECOND_THIRD_LABEL_TEXT}.
     *
     * @return the label text for the second third view
     */
    public final String getSecondThirdLabelText()
    {
        return secondThirdLabelText;
    }

    /**
     * Set the label text for the second third view to <code>secondThirdLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param secondThirdLabelText label text for the second third view
     */
    public final void setSecondThirdLabelText(final String secondThirdLabelText)
    {
        String oldSecondThirdLabelText = this.secondThirdLabelText;
        this.secondThirdLabelText = secondThirdLabelText;
        secondThirdLabel.setText(buildLabel(this.secondThirdLabelText, model.secondThird().size()));
        firePropertyChange(-1, "secondThirdLabelText", this.secondThirdLabelText, oldSecondThirdLabelText);
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
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstLabelText(String)} and {@link setDisplaySizes(boolean)}
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
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second set
     */
    public final PText getSecondLabel()
    {
        return secondLabel;
    }

    /**
     * Return the label for the third set.  The text for the returned PText
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setThirdLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the third set
     */
    public final PText getThirdLabel()
    {
        return thirdLabel;
    }

    /**
     * Return the label for the first only view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstOnlyLabelText(String)} and {@link setDisplaySizes(boolean)}
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
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondOnlyLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second only view
     */
    public final PText getSecondOnlyLabel()
    {
        return secondOnlyLabel;
    }

    /**
     * Return the label for the third only view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setThirdOnlyLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the third only view
     */
    public final PText getThirdOnlyLabel()
    {
        return thirdOnlyLabel;
    }

    /**
     * Return the label for the first second view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstSecondLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first second view
     */
    public final PText getFirstSecondLabel()
    {
        return firstSecondLabel;
    }

    /**
     * Return the label for the first third view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstThirdLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first third view
     */
    public final PText getFirstThirdLabel()
    {
        return firstThirdLabel;
    }

    /**
     * Return the label for the second third view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondThirdLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second third view
     */
    public final PText getSecondThirdLabel()
    {
        return secondThirdLabel;
    }

    /**
     * Return the label for the intersection view.  The text for the returned PText
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setIntersectionLabelText(String)} and {@link setDisplaySizes(boolean)}
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
     * should not be changed, as the text is synchronized to the ternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setUnionLabelText(String)} and {@link setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the union view
     */
    public final PText getUnionLabel()
    {
        return unionLabel;
    }
}