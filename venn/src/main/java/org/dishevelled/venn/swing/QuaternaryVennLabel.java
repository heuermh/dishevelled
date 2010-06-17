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

import org.dishevelled.venn.QuaternaryVennModel;

/**
 * Quaternary venn diagram label.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennLabel<E>
    extends AbstractQuaternaryVennDiagram<E>
{
    /** Number of elements to display. */
    private int elementsToDisplay = DEFAULT_ELEMENTS_TO_DISPLAY;

    /** Contents of the first set. */
    private final JLabel first = new JLabel();

    /** Contents of the second set. */
    private final JLabel second = new JLabel();

    /** Contents of the third set. */
    private final JLabel third = new JLabel();

    /** Contents of the fourth set. */
    private final JLabel fourth = new JLabel();

    /** Contents of the first only view. */
    private final JLabel firstOnly = new JLabel();

    /** Contents for the second only view. */
    private final JLabel secondOnly = new JLabel();

    /** Contents for the third only view. */
    private final JLabel thirdOnly = new JLabel();

    /** Contents for the fourth only view. */
    private final JLabel fourthOnly = new JLabel();

    /** Contents for the first second view. */
    private final JLabel firstSecond = new JLabel();

    /** Contents for the first third view. */
    private final JLabel firstThird = new JLabel();

    /** Contents for the second third view. */
    private final JLabel secondThird = new JLabel();

    /** Contents for the first fourth view. */
    private final JLabel firstFourth = new JLabel();

    /** Contents for the second fourth view. */
    private final JLabel secondFourth = new JLabel();

    /** Contents for the third fourth view. */
    private final JLabel thirdFourth = new JLabel();

    /** Contents for the first second third view. */
    private final JLabel firstSecondThird = new JLabel();

    /** Contents for the first second fourth view. */
    private final JLabel firstSecondFourth = new JLabel();

    /** Contents for the first third fourth view. */
    private final JLabel firstThirdFourth = new JLabel();

    /** Contents for the second third fourth view. */
    private final JLabel secondThirdFourth = new JLabel();

    /** Contents of the intersection view. */
    private final JLabel intersection = new JLabel();

    /** Contents of the union view. */
    private final JLabel union = new JLabel();

    /** Default number of set elements to display, <code>5</code>. */
    private static final int DEFAULT_ELEMENTS_TO_DISPLAY = 5;


    /**
     * Create a new empty quaternary venn label.
     */
    public QuaternaryVennLabel()
    {
        super();
        updateContents();
        layoutComponents();
    }

    /**
     * Create a new quaternary venn label with the specified sets.
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
    public QuaternaryVennLabel(final String firstLabelText, final Set<? extends E> first,
                                final String secondLabelText, final Set<? extends E> second,
                                final String thirdLabelText, final Set<? extends E> third,
                                final String fourthLabelText, final Set<? extends E> fourth)
    {
        super(firstLabelText, first, secondLabelText, second, thirdLabelText, third, fourthLabelText, fourth);
        updateContents();
        layoutComponents();
    }

    /**
     * Create a new quaternary venn label with the specified model.
     *
     * @param model model for this quaternary venn label, must not be null
     */
    public QuaternaryVennLabel(final QuaternaryVennModel<E> model)
    {
        super(model);
        updateContents();
        layoutComponents();
    }


    /** {@inheritDoc} */
    protected void updateContents()
    {
        first.setText(buildContents(getModel().first()));
        second.setText(buildContents(getModel().second()));
        third.setText(buildContents(getModel().third()));
        fourth.setText(buildContents(getModel().third()));
        firstOnly.setText(buildContents(getModel().firstOnly()));
        secondOnly.setText(buildContents(getModel().secondOnly()));
        thirdOnly.setText(buildContents(getModel().thirdOnly()));
        fourthOnly.setText(buildContents(getModel().fourthOnly()));
        firstSecond.setText(buildContents(getModel().firstSecond()));
        firstThird.setText(buildContents(getModel().firstThird()));
        secondThird.setText(buildContents(getModel().secondThird()));
        firstFourth.setText(buildContents(getModel().firstFourth()));
        secondFourth.setText(buildContents(getModel().secondFourth()));
        thirdFourth.setText(buildContents(getModel().thirdFourth()));
        firstSecondThird.setText(buildContents(getModel().firstSecondThird()));
        firstSecondFourth.setText(buildContents(getModel().firstSecondFourth()));
        firstThirdFourth.setText(buildContents(getModel().firstThirdFourth()));
        secondThirdFourth.setText(buildContents(getModel().secondThirdFourth()));
        intersection.setText(buildContents(getModel().intersection()));
        union.setText(buildContents(getModel().union()));
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        addField(getFirstLabel(), first);
        addField(getSecondLabel(), second);
        addField(getThirdLabel(), third);
        addField(getFourthLabel(), fourth);
        addField(getFirstOnlyLabel(), firstOnly);
        addField(getSecondOnlyLabel(), secondOnly);
        addField(getThirdOnlyLabel(), thirdOnly);
        addField(getFourthOnlyLabel(), fourthOnly);
        addField(getFirstSecondLabel(), firstSecond);
        addField(getFirstThirdLabel(), firstThird);
        addField(getSecondThirdLabel(), secondThird);
        addField(getSecondFourthLabel(), secondFourth);
        addField(getFirstFourthLabel(), firstFourth);
        addField(getThirdFourthLabel(), thirdFourth);
        addField(getFirstSecondThirdLabel(), firstSecondThird);
        addField(getFirstSecondFourthLabel(), firstSecondFourth);
        addField(getFirstThirdFourthLabel(), firstThirdFourth);
        addField(getSecondThirdFourthLabel(), secondThirdFourth);
        addField(getIntersectionLabel(), intersection);
        addField(getUnionLabel(), union);
        addFinalSpacing();
    }

    /**
     * Build and return content text.
     *
     * @param set set
     * @return content text
     */
    private String buildContents(final Set<E> set)
    {
        if (set.isEmpty())
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<E> iterator = set.iterator();
        sb.append(iterator.next().toString());
        int count = 1;
        while (iterator.hasNext())
        {
            sb.append(", ");
            sb.append(iterator.next().toString());
            if (count++ > elementsToDisplay)
            {
                break;
            }
        }
        if (set.size() > elementsToDisplay)
        {
            sb.append(", ...");
        }
        return sb.toString();
    }


    /**
     * Return the number of set elements to display.  Defaults to {@link #DEFAULT_ELEMENTS_TO_DISPLAY}.
     *
     * @return the number of set elements to display
     */
    public int getElementsToDisplay()
    {
        return elementsToDisplay;
    }

    /**
     * Set the number of set elements to display to <code>elementsToDisplay</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param elementsToDisplay number of elements to display
     */
    public void setElementsToDisplay(final int elementsToDisplay)
    {
        int oldElementsToDisplay = this.elementsToDisplay;
        this.elementsToDisplay = elementsToDisplay;
        firePropertyChange("elementsToDisplay", oldElementsToDisplay, this.elementsToDisplay);
    }

    /**
     * Return the contents of the first set.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first set
     */
    public JLabel getFirst()
    {
        return first;
    }

    /**
     * Return the contents of the second set.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the second set
     */
    public JLabel getSecond()
    {
        return second;
    }

    /**
     * Return the contents of the third set.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the third set
     */
    public JLabel getThird()
    {
        return third;
    }

    /**
     * Return the contents of the fourth set.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the fourth set
     */
    public JLabel getFourth()
    {
        return fourth;
    }

    /**
     * Return the contents of the first only view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first only view
     */
    public JLabel getFirstOnly()
    {
        return firstOnly;
    }

    /**
     * Return the contents of the second only view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the second only view
     */
    public JLabel getSecondOnly()
    {
        return secondOnly;
    }

    /**
     * Return the contents of the third only view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the third only view
     */
    public JLabel getThirdOnly()
    {
        return thirdOnly;
    }

    /**
     * Return the contents of the fourth only view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the fourth only view
     */
    public JLabel getFourthOnly()
    {
        return fourthOnly;
    }

    /**
     * Return the contents of the first second view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first second view
     */
    public JLabel getFirstSecond()
    {
        return firstSecond;
    }

    /**
     * Return the contents of the second third view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the second third view
     */
    public JLabel getSecondThird()
    {
        return secondThird;
    }

    /**
     * Return the contents of the first third view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first third view
     */
    public JLabel getFirstThird()
    {
        return firstThird;
    }

    /**
     * Return the contents of the first fourth view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first fourth view
     */
    public JLabel getFirstFourth()
    {
        return firstFourth;
    }

    /**
     * Return the contents of the second fourth view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the second fourth view
     */
    public JLabel getSecondFourth()
    {
        return secondFourth;
    }

    /**
     * Return the contents of the third fourth view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the third fourth view
     */
    public JLabel getThirdFourth()
    {
        return thirdFourth;
    }

    /**
     * Return the contents of the first second third view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first second third view
     */
    public JLabel getFirstSecondThird()
    {
        return firstSecondThird;
    }

    /**
     * Return the contents of the first second fourth view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first second fourth view
     */
    public JLabel getFirstSecondFourth()
    {
        return firstSecondFourth;
    }

    /**
     * Return the contents of the first third fourth view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the first third fourth view
     */
    public JLabel getFirstThirdFourth()
    {
        return firstThirdFourth;
    }

    /**
     * Return the contents of the second third fourth view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the second third fourth view
     */
    public JLabel getSecondThirdFourth()
    {
        return secondThirdFourth;
    }

    /**
     * Return the contents of the intersection view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the intersection view
     */
    public JLabel getIntersection()
    {
        return intersection;
    }

    /**
     * Return the contents of the union view.  The text for the returned JLabel
     * should not be changed, as the current text is synchronized to the
     * quaternary venn model backing this venn diagram.
     *
     * @return the contents of the union view
     */
    public JLabel getUnion()
    {
        return union;
    }
}