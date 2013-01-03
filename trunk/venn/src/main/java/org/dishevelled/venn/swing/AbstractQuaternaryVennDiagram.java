/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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

import org.dishevelled.venn.QuaternaryVennModel;

import org.dishevelled.venn.model.QuaternaryVennModelImpl;

/**
 * Abstract quaternary venn diagram.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractQuaternaryVennDiagram<E>
    extends LabelFieldPanel
{
    /** Quaternary venn model. */
    private QuaternaryVennModel<E> model;

    /** Label text for the first set. */
    private String firstLabelText = DEFAULT_FIRST_LABEL_TEXT;

    /** Label text for the second set. */
    private String secondLabelText = DEFAULT_SECOND_LABEL_TEXT;

    /** Label text for the third set. */
    private String thirdLabelText = DEFAULT_THIRD_LABEL_TEXT;

    /** Label text for the fourth set. */
    private String fourthLabelText = DEFAULT_FOURTH_LABEL_TEXT;

    /** Label text for the first only view. */
    private String firstOnlyLabelText = DEFAULT_FIRST_ONLY_LABEL_TEXT;

    /** Label text for the second only view. */
    private String secondOnlyLabelText = DEFAULT_SECOND_ONLY_LABEL_TEXT;

    /** Label text for the third only view. */
    private String thirdOnlyLabelText = DEFAULT_THIRD_ONLY_LABEL_TEXT;

    /** Label text for the fourth only view. */
    private String fourthOnlyLabelText = DEFAULT_FOURTH_ONLY_LABEL_TEXT;

    /** Label text for the first second view. */
    private String firstSecondLabelText = DEFAULT_FIRST_SECOND_LABEL_TEXT;

    /** Label text for the first third view. */
    private String firstThirdLabelText = DEFAULT_FIRST_THIRD_LABEL_TEXT;

    /** Label text for the second third view. */
    private String secondThirdLabelText = DEFAULT_SECOND_THIRD_LABEL_TEXT;

    /** Label text for the first fourth view. */
    private String firstFourthLabelText = DEFAULT_FIRST_FOURTH_LABEL_TEXT;

    /** Label text for the second fourth view. */
    private String secondFourthLabelText = DEFAULT_SECOND_FOURTH_LABEL_TEXT;

    /** Label text for the third fourth view. */
    private String thirdFourthLabelText = DEFAULT_THIRD_FOURTH_LABEL_TEXT;

    /** Label text for the first second third view. */
    private String firstSecondThirdLabelText = DEFAULT_FIRST_SECOND_THIRD_LABEL_TEXT;

    /** Label text for the first second fourth view. */
    private String firstSecondFourthLabelText = DEFAULT_FIRST_SECOND_FOURTH_LABEL_TEXT;

    /** Label text for the first third fourth view. */
    private String firstThirdFourthLabelText = DEFAULT_FIRST_THIRD_FOURTH_LABEL_TEXT;

    /** Label text for the second third fourth view. */
    private String secondThirdFourthLabelText = DEFAULT_SECOND_THIRD_FOURTH_LABEL_TEXT;

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

    /** Label for the third set. */
    private final JLabel thirdLabel = new JLabel();

    /** Label for the fourth set. */
    private final JLabel fourthLabel = new JLabel();

    /** Label for the first only view. */
    private final JLabel firstOnlyLabel = new JLabel();

    /** Label for the second only view. */
    private final JLabel secondOnlyLabel = new JLabel();

    /** Label for the third only view. */
    private final JLabel thirdOnlyLabel = new JLabel();

    /** Label for the fourth only view. */
    private final JLabel fourthOnlyLabel = new JLabel();

    /** Label for the first second view. */
    private final JLabel firstSecondLabel = new JLabel();

    /** Label for the first third view. */
    private final JLabel firstThirdLabel = new JLabel();

    /** Label for the second third view. */
    private final JLabel secondThirdLabel = new JLabel();

    /** Label for the first fourth view. */
    private final JLabel firstFourthLabel = new JLabel();

    /** Label for the second fourth view. */
    private final JLabel secondFourthLabel = new JLabel();

    /** Label for the third fourth view. */
    private final JLabel thirdFourthLabel = new JLabel();

    /** Label for the first second third view. */
    private final JLabel firstSecondThirdLabel = new JLabel();

    /** Label for the first second fourth view. */
    private final JLabel firstSecondFourthLabel = new JLabel();

    /** Label for the first third fourth view. */
    private final JLabel firstThirdFourthLabel = new JLabel();

    /** Label for the second third fourth view. */
    private final JLabel secondThirdFourthLabel = new JLabel();

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

    /** Default label text for the third set, <code>"Third set"</code>. */
    public static final String DEFAULT_THIRD_LABEL_TEXT = "Third set";

    /** Default label text for the fourth set, <code>"Fourth set"</code>. */
    public static final String DEFAULT_FOURTH_LABEL_TEXT = "Fourth set";

    /** Default label text for the first only view, <code>"First only"</code>. */
    public static final String DEFAULT_FIRST_ONLY_LABEL_TEXT = "First only";

    /** Default label text for the second only view, <code>"Second only"</code>. */
    public static final String DEFAULT_SECOND_ONLY_LABEL_TEXT = "Second only";

    /** Default label text for the third only view, <code>"Third only"</code>. */
    public static final String DEFAULT_THIRD_ONLY_LABEL_TEXT = "Third only";

    /** Default label text for the fourth only view, <code>"Fourth only"</code>. */
    public static final String DEFAULT_FOURTH_ONLY_LABEL_TEXT = "Fourth only";

    /** Default label text for the first second view, <code>"First and second only"</code>. */
    public static final String DEFAULT_FIRST_SECOND_LABEL_TEXT = "First and second only";

    /** Default label text for the first third view, <code>"First and third only"</code>. */
    public static final String DEFAULT_FIRST_THIRD_LABEL_TEXT = "First and third only";

    /** Default label text for the second third view, <code>"Second and third only"</code>. */
    public static final String DEFAULT_SECOND_THIRD_LABEL_TEXT = "Second and third only";

    /** Default label text for the first fourth view, <code>"First and fourth only"</code>. */
    public static final String DEFAULT_FIRST_FOURTH_LABEL_TEXT = "First and fourth only";

    /** Default label text for the second fourth view, <code>"Second and fourth only"</code>. */
    public static final String DEFAULT_SECOND_FOURTH_LABEL_TEXT = "Second and fourth only";

    /** Default label text for the third fourth view, <code>"Third and fourth only"</code>. */
    public static final String DEFAULT_THIRD_FOURTH_LABEL_TEXT = "Third and fourth only";

    /** Default label text for the first second third view, <code>"First, second, and third only"</code>. */
    public static final String DEFAULT_FIRST_SECOND_THIRD_LABEL_TEXT = "First, second, and third only";

    /** Default label text for the first second fourth view, <code>"First, second, and fourth only"</code>. */
    public static final String DEFAULT_FIRST_SECOND_FOURTH_LABEL_TEXT = "First, second, and fourth only";

    /** Default label text for the first third fourth view, <code>"First, third, and fourth only"</code>. */
    public static final String DEFAULT_FIRST_THIRD_FOURTH_LABEL_TEXT = "First, third, and fourth only";

    /** Default label text for the second third fourth view, <code>"Second, third, and fourth only"</code>. */
    public static final String DEFAULT_SECOND_THIRD_FOURTH_LABEL_TEXT = "Second, third, and fourth only";

    /** Default label text for the intersection view, <code>"Intersection"</code>. */
    public static final String DEFAULT_INTERSECTION_LABEL_TEXT = "Intersection";

    /** Default label text for the union view, <code>"Union"</code>. */
    public static final String DEFAULT_UNION_LABEL_TEXT = "Union";


    /**
     * Create a new empty abstract quaternary venn diagram.
     */
    protected AbstractQuaternaryVennDiagram()
    {
        super();
        model = new QuaternaryVennModelImpl<E>();

        installListeners();
        updateLabels();
    }

    /**
     * Create a new abstract quaternary venn diagram with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third set
     * @param third third set, must not be null
     * @param fourthLabelText label text for the fourth set
     * @param fourth fourth set, must not be null
     */
    protected AbstractQuaternaryVennDiagram(final String firstLabelText, final Set<? extends E> first,
                                            final String secondLabelText, final Set<? extends E> second,
                                            final String thirdLabelText, final Set<? extends E> third,
                                            final String fourthLabelText, final Set<? extends E> fourth)
    {
        this(firstLabelText,
             secondLabelText,
             thirdLabelText,
             fourthLabelText,
             new QuaternaryVennModelImpl<E>(first, second, third, fourth));
    }

    /**
     * Create a new abstract quaternary venn diagram with the specified model.
     *
     * @param model model for this abstract quaternary venn diagram, must not be null
     */
    protected AbstractQuaternaryVennDiagram(final QuaternaryVennModel<E> model)
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
     * Create a new abstract quaternary venn diagram with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param secondLabelText label text for the second set
     * @param thirdLabelText label text for the third set
     * @param fourthLabelText label text for the fourth set
     * @param model model for this abstract quaternary venn diagram, must not be null
     */
    protected AbstractQuaternaryVennDiagram(final String firstLabelText,
                                            final String secondLabelText,
                                            final String thirdLabelText,
                                            final String fourthLabelText,
                                            final QuaternaryVennModel<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;
        this.firstLabelText = firstLabelText;
        this.secondLabelText = secondLabelText;
        this.thirdLabelText = thirdLabelText;
        this.fourthLabelText = fourthLabelText;
        this.firstOnlyLabelText = firstLabelText + " only";
        this.secondOnlyLabelText = secondLabelText + " only";
        this.thirdOnlyLabelText = thirdLabelText + " only";
        this.fourthOnlyLabelText = fourthLabelText + " only";
        this.firstSecondLabelText = firstLabelText + " and " + secondLabelText + " only";
        this.firstThirdLabelText = firstLabelText + " and " + thirdLabelText + " only";
        this.secondThirdLabelText = secondLabelText + " and " + thirdLabelText + " only";
        this.firstFourthLabelText = firstLabelText + " and " + fourthLabelText + " only";
        this.secondFourthLabelText = secondLabelText + " and " + fourthLabelText + " only";
        this.thirdFourthLabelText = thirdLabelText + " and " + fourthLabelText + " only";
        this.firstSecondThirdLabelText = firstLabelText + ", " + secondLabelText + ", and " + thirdLabelText + " only";
        this.firstSecondFourthLabelText = firstLabelText + ", " + secondLabelText + ", and " + fourthLabelText + " only";
        this.firstThirdFourthLabelText = firstLabelText + ", " + thirdLabelText + ", and " + fourthLabelText + " only";
        this.secondThirdFourthLabelText = secondLabelText + ", " + thirdLabelText + ", and " + fourthLabelText + " only";

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
        model.fourth().addSetChangeListener(update);
    }

    /**
     * Uninstall listeners.
     */
    private void uninstallListeners()
    {
        model.first().removeSetChangeListener(update);
        model.second().removeSetChangeListener(update);
        model.third().removeSetChangeListener(update);
        model.fourth().removeSetChangeListener(update);
    }

    /**
     * Update labels.
     */
    private void updateLabels()
    {
        firstLabel.setText(buildLabel(firstLabelText, model.first().size()));
        secondLabel.setText(buildLabel(secondLabelText, model.second().size()));
        thirdLabel.setText(buildLabel(thirdLabelText, model.third().size()));
        fourthLabel.setText(buildLabel(fourthLabelText, model.fourth().size()));
        firstOnlyLabel.setText(buildLabel(firstOnlyLabelText, model.firstOnly().size()));
        secondOnlyLabel.setText(buildLabel(secondOnlyLabelText, model.secondOnly().size()));
        thirdOnlyLabel.setText(buildLabel(thirdOnlyLabelText, model.thirdOnly().size()));
        fourthOnlyLabel.setText(buildLabel(fourthOnlyLabelText, model.fourthOnly().size()));
        firstSecondLabel.setText(buildLabel(firstSecondLabelText, model.firstSecond().size()));
        firstThirdLabel.setText(buildLabel(firstThirdLabelText, model.firstThird().size()));
        secondThirdLabel.setText(buildLabel(secondThirdLabelText, model.secondThird().size()));
        firstFourthLabel.setText(buildLabel(firstFourthLabelText, model.firstFourth().size()));
        secondFourthLabel.setText(buildLabel(secondFourthLabelText, model.secondFourth().size()));
        thirdFourthLabel.setText(buildLabel(thirdFourthLabelText, model.thirdFourth().size()));
        firstSecondThirdLabel.setText(buildLabel(firstSecondThirdLabelText, model.firstSecondThird().size()));
        firstSecondFourthLabel.setText(buildLabel(firstSecondFourthLabelText, model.firstSecondFourth().size()));
        firstThirdFourthLabel.setText(buildLabel(firstThirdFourthLabelText, model.firstThirdFourth().size()));
        secondThirdFourthLabel.setText(buildLabel(secondThirdFourthLabelText, model.secondThirdFourth().size()));
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
     * Return the model for this quaternary venn label.  The model will not be null.
     *
     * @return the model for this quaternary venn label
     */
    public final QuaternaryVennModel<E> getModel()
    {
        return model;
    }

    /**
     * Set the model for this quaternary venn label to <code>model</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param model model for this quaternary venn label, must not be null
     */
    public final void setModel(final QuaternaryVennModel<E> model)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        QuaternaryVennModel<E> oldModel = this.model;
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
        firePropertyChange("thirdLabelText", this.thirdLabelText, oldThirdLabelText);
    }

    /**
     * Return the label text for the fourth set.  Defaults to {@link #DEFAULT_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the fourth set
     */
    public final String getFourthLabelText()
    {
        return fourthLabelText;
    }

    /**
     * Set the label text for the fourth set to <code>fourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param fourthLabelText label text for the fourth set
     */
    public final void setFourthLabelText(final String fourthLabelText)
    {
        String oldFourthLabelText = this.fourthLabelText;
        this.fourthLabelText = fourthLabelText;
        fourthLabel.setText(buildLabel(this.fourthLabelText, model.fourth().size()));
        firePropertyChange("fourthLabelText", this.fourthLabelText, oldFourthLabelText);
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
        firePropertyChange("thirdOnlyLabelText", this.thirdOnlyLabelText, oldThirdOnlyLabelText);
    }

    /**
     * Return the label text for the fourth only view.  Defaults to {@link #DEFAULT_FOURTH_ONLY_LABEL_TEXT}.
     *
     * @return the label text for the fourth only view
     */
    public final String getFourthOnlyLabelText()
    {
        return fourthOnlyLabelText;
    }

    /**
     * Set the label text for the fourth only view to <code>fourthOnlyLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param fourthOnlyLabelText label text for the fourth only view
     */
    public final void setFourthOnlyLabelText(final String fourthOnlyLabelText)
    {
        String oldFourthOnlyLabelText = this.fourthOnlyLabelText;
        this.fourthOnlyLabelText = fourthOnlyLabelText;
        fourthOnlyLabel.setText(buildLabel(this.fourthOnlyLabelText, model.fourthOnly().size()));
        firePropertyChange("fourthOnlyLabelText", this.fourthOnlyLabelText, oldFourthOnlyLabelText);
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
        firePropertyChange("firstSecondLabelText", this.firstSecondLabelText, oldFirstSecondLabelText);
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
        firePropertyChange("firstThirdLabelText", this.firstThirdLabelText, oldFirstThirdLabelText);
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
        firePropertyChange("secondThirdLabelText", this.secondThirdLabelText, oldSecondThirdLabelText);
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

    /**
     * Return the label for the first set.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first set
     */
    public final JLabel getFirstLabel()
    {
        return firstLabel;
    }

    /**
     * Return the label for the second set.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second set
     */
    public final JLabel getSecondLabel()
    {
        return secondLabel;
    }

    /**
     * Return the label for the third set.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setThirdLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the third set
     */
    public final JLabel getThirdLabel()
    {
        return thirdLabel;
    }

    /**
     * Return the label for the fourth set.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setThirdLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the fourth set
     */
    public final JLabel getFourthLabel()
    {
        return fourthLabel;
    }

    /**
     * Return the label for the first only view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstOnlyLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first only view
     */
    public final JLabel getFirstOnlyLabel()
    {
        return firstOnlyLabel;
    }

    /**
     * Return the label for the second only view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondOnlyLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second only view
     */
    public final JLabel getSecondOnlyLabel()
    {
        return secondOnlyLabel;
    }

    /**
     * Return the label for the third only view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setThirdOnlyLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the third only view
     */
    public final JLabel getThirdOnlyLabel()
    {
        return thirdOnlyLabel;
    }

    /**
     * Return the label for the fourth only view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setThirdOnlyLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the fourth only view
     */
    public final JLabel getFourthOnlyLabel()
    {
        return fourthOnlyLabel;
    }

    /**
     * Return the label for the first second view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstSecondLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first second view
     */
    public final JLabel getFirstSecondLabel()
    {
        return firstSecondLabel;
    }

    /**
     * Return the label for the first third view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstThirdLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first third view
     */
    public final JLabel getFirstThirdLabel()
    {
        return firstThirdLabel;
    }

    /**
     * Return the label for the second third view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondThirdLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second third view
     */
    public final JLabel getSecondThirdLabel()
    {
        return secondThirdLabel;
    }

    /**
     * Return the label for the intersection view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setIntersectionLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the intersection view
     */
    public final JLabel getIntersectionLabel()
    {
        return intersectionLabel;
    }

    /**
     * Return the label for the union view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setUnionLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the union view
     */
    public final JLabel getUnionLabel()
    {
        return unionLabel;
    }

    /**
     * Return the label text for the first fourth view.  Defaults to {@link #DEFAULT_FIRST_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the first fourth view
     */
    public final String getFirstFourthLabelText()
    {
        return firstFourthLabelText;
    }

    /**
     * Set the label text for the first fourth view to <code>firstFourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstFourthLabelText label text for the first fourth view
     */
    public final void setFirstFourthLabelText(final String firstFourthLabelText)
    {
        String oldFirstFourthLabelText = this.firstFourthLabelText;
        this.firstFourthLabelText = firstFourthLabelText;
        firstFourthLabel.setText(buildLabel(this.firstFourthLabelText, model.firstFourth().size()));
        firePropertyChange("firstFourthLabelText", this.firstFourthLabelText, oldFirstFourthLabelText);
    }

    /**
     * Return the label for the first fourth view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstFourthLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first fourth view
     */
    public final JLabel getFirstFourthLabel()
    {
        return firstFourthLabel;
    }

    /**
     * Return the label text for the second fourth view.  Defaults to {@link #DEFAULT_SECOND_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the second fourth view
     */
    public final String getSecondFourthLabelText()
    {
        return secondFourthLabelText;
    }

    /**
     * Set the label text for the second fourth view to <code>secondFourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param secondFourthLabelText label text for the second fourth view
     */
    public final void setSecondFourthLabelText(final String secondFourthLabelText)
    {
        String oldSecondFourthLabelText = this.secondFourthLabelText;
        this.secondFourthLabelText = secondFourthLabelText;
        secondFourthLabel.setText(buildLabel(this.secondFourthLabelText, model.secondFourth().size()));
        firePropertyChange("secondFourthLabelText", this.secondFourthLabelText, oldSecondFourthLabelText);
    }

    /**
     * Return the label for the second fourth view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondFourthLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second fourth view
     */
    public final JLabel getSecondFourthLabel()
    {
        return secondFourthLabel;
    }

    /**
     * Return the label text for the third fourth view.  Defaults to {@link #DEFAULT_THIRD_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the third fourth view
     */
    public final String getThirdFourthLabelText()
    {
        return thirdFourthLabelText;
    }

    /**
     * Set the label text for the third fourth view to <code>thirdFourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param thirdFourthLabelText label text for the third fourth view
     */
    public final void setThirdFourthLabelText(final String thirdFourthLabelText)
    {
        String oldThirdFourthLabelText = this.thirdFourthLabelText;
        this.thirdFourthLabelText = thirdFourthLabelText;
        thirdFourthLabel.setText(buildLabel(this.thirdFourthLabelText, model.thirdFourth().size()));
        firePropertyChange("thirdFourthLabelText", this.thirdFourthLabelText, oldThirdFourthLabelText);
    }

    /**
     * Return the label for the third fourth view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setThirdFourthLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the third fourth view
     */
    public final JLabel getThirdFourthLabel()
    {
        return thirdFourthLabel;
    }

    /**
     * Return the label text for the first second third view.  Defaults to
     * {@link #DEFAULT_FIRST_SECOND_THIRD_LABEL_TEXT}.
     *
     * @return the label text for the first second third view
     */
    public final String getFirstSecondThirdLabelText()
    {
        return firstSecondThirdLabelText;
    }

    /**
     * Set the label text for the first second third view to <code>firstSecondThirdLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstSecondThirdLabelText label text for the first second third view
     */
    public final void setFirstSecondThirdLabelText(final String firstSecondThirdLabelText)
    {
        String oldFirstSecondThirdLabelText = this.firstSecondThirdLabelText;
        this.firstSecondThirdLabelText = firstSecondThirdLabelText;
        firstSecondThirdLabel.setText(buildLabel(this.firstSecondThirdLabelText, model.firstSecondThird().size()));
        firePropertyChange("firstSecondThirdLabelText", this.firstSecondThirdLabelText, oldFirstSecondThirdLabelText);
    }

    /**
     * Return the label for the first second third view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstSecondThirdLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first second third view
     */
    public final JLabel getFirstSecondThirdLabel()
    {
        return firstSecondThirdLabel;
    }

    /**
     * Return the label text for the first second fourth view.  Defaults to
     * {@link #DEFAULT_FIRST_SECOND_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the first second fourth view
     */
    public final String getFirstSecondFourthLabelText()
    {
        return firstSecondFourthLabelText;
    }

    /**
     * Set the label text for the first second fourth view to <code>firstSecondFourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstSecondFourthLabelText label text for the first second fourth view
     */
    public final void setFirstSecondFourthLabelText(final String firstSecondFourthLabelText)
    {
        String oldFirstSecondFourthLabelText = this.firstSecondFourthLabelText;
        this.firstSecondFourthLabelText = firstSecondFourthLabelText;
        firstSecondFourthLabel.setText(buildLabel(this.firstSecondFourthLabelText, model.firstSecondFourth().size()));
        firePropertyChange("firstSecondFourthLabelText",
                           this.firstSecondFourthLabelText,
                           oldFirstSecondFourthLabelText);
    }

    /**
     * Return the label for the first second fourth view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstSecondFourthLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first second fourth view
     */
    public final JLabel getFirstSecondFourthLabel()
    {
        return firstSecondFourthLabel;
    }

    /**
     * Return the label text for the first third fourth view.  Defaults to
     * {@link #DEFAULT_FIRST_THIRD_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the first third fourth view
     */
    public final String getFirstThirdFourthLabelText()
    {
        return firstThirdFourthLabelText;
    }

    /**
     * Set the label text for the first third fourth view to <code>firstThirdFourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstThirdFourthLabelText label text for the first third fourth view
     */
    public final void setFirstThirdFourthLabelText(final String firstThirdFourthLabelText)
    {
        String oldFirstThirdFourthLabelText = this.firstThirdFourthLabelText;
        this.firstThirdFourthLabelText = firstThirdFourthLabelText;
        firstThirdFourthLabel.setText(buildLabel(this.firstThirdFourthLabelText, model.firstThirdFourth().size()));
        firePropertyChange("firstThirdFourthLabelText", this.firstThirdFourthLabelText, oldFirstThirdFourthLabelText);
    }

    /**
     * Return the label for the first third fourth view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setFirstThirdFourthLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the first third fourth view
     */
    public final JLabel getFirstThirdFourthLabel()
    {
        return firstThirdFourthLabel;
    }

    /**
     * Return the label text for the second third fourth view.  Defaults to
     * {@link #DEFAULT_SECOND_THIRD_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the second third fourth view
     */
    public final String getSecondThirdFourthLabelText()
    {
        return secondThirdFourthLabelText;
    }

    /**
     * Set the label text for the second third fourth view to <code>secondThirdFourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param secondThirdFourthLabelText label text for the second third fourth view
     */
    public final void setSecondThirdFourthLabelText(final String secondThirdFourthLabelText)
    {
        String oldSecondThirdFourthLabelText = this.secondThirdFourthLabelText;
        this.secondThirdFourthLabelText = secondThirdFourthLabelText;
        secondThirdFourthLabel.setText(buildLabel(this.secondThirdFourthLabelText, model.secondThirdFourth().size()));
        firePropertyChange("secondThirdFourthLabelText",
                           this.secondThirdFourthLabelText,
                           oldSecondThirdFourthLabelText);
    }

    /**
     * Return the label for the second third fourth view.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the quaternary
     * venn model backing this venn diagram.  Use methods
     * {@link #setSecondThirdFourthLabelText(String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the second third fourth view
     */
    public final JLabel getSecondThirdFourthLabel()
    {
        return secondThirdFourthLabel;
    }
}