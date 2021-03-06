/*

    dsh-eventlist-view  Views for event lists.
    Copyright (c) 2010-2019 held jointly by the individual authors.

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
package org.dishevelled.eventlist.view;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JLabel;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

/**
 * Elements label.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public final class ElementsLabel<E>
    extends AbstractEventListView<E>
{
    /** Elements label. */
    private final JLabel elements;

    /** Number of elements to display. */
    private int elementsToDisplay = DEFAULT_ELEMENTS_TO_DISPLAY;

    /** Separator between elements. */
    private String separator = DEFAULT_SEPARATOR;

    /** More elements indicator. */
    private String indicator = DEFAULT_INDICATOR;

    /** Default number of elements to display, <code>3</code>. */
    public static final int DEFAULT_ELEMENTS_TO_DISPLAY = 3;

    /** Default separator between elements, <code>, </code>. */
    public static final String DEFAULT_SEPARATOR = ", ";

    /** Default more elements indicator, <code>...</code>. */
    public static final String DEFAULT_INDICATOR = "...";

    /** Listener. */
    private final ListEventListener<E> listener = new ListEventListener<E>()
        {
            @Override
            public void listChanged(final ListEvent<E> event)
            {
                updateLabelText();
            }
        };


    /**
     * Create a new elements label with the specified model.
     *
     * @param model model, must not be null
     */
    public ElementsLabel(final EventList<E> model)
    {
        super(model);
        elements = new JLabel();
        updateLabelText();
        addListeners();
        setLayout(new BorderLayout());
        add("West", elements);
    }



    /**
     * Return the label for this elements label.  The text for the returned JLabel
     * should not be changed, as the text is synchronized to the event
     * list backing this elements label.  Use methods {@link #setSeparator(String)},
     * {@link #setElementsToDisplay(int)}, and {@link #setIndicator(String)} to set the
     * separator between elements, number of elements to display, and more
     * elements indicator respectively.
     *
     * @return the label for this elements label
     */
    public JLabel getElementsLabel()
    {
        return elements;
    }

    /**
     * Return the number of elements to display.
     *
     * @return the number of elements to display
     */
    public int getElementsToDisplay()
    {
        return elementsToDisplay;
    }

    /**
     * Set the number of elements to display to <code>elementsToDisplay</code>.  Defaults
     * to {@link #DEFAULT_ELEMENTS_TO_DISPLAY}.
     *
     * <p>This is a bound property.</p>
     *
     * @param elementsToDisplay number of elements to display, must be at least zero
     */
    public void setElementsToDisplay(final int elementsToDisplay)
    {
        if (elementsToDisplay < 0)
        {
            throw new IllegalArgumentException("elementsToDisplay must be at least zero");
        }
        int oldElementsToDisplay = this.elementsToDisplay;
        this.elementsToDisplay = elementsToDisplay;
        firePropertyChange("elementsToDisplay", oldElementsToDisplay, this.elementsToDisplay);
        updateLabelText();
    }

    /**
     * Return the separator between elements.
     *
     * @return the separator between elements
     */
    public String getSeparator()
    {
        return separator;
    }

    /**
     * Set the separator between elements to <code>separator</code>.  Defaults
     * to {@link #DEFAULT_SEPARATOR}.
     *
     * <p>This is a bound property.</p>
     *
     * @param separator separator between elements
     */
    public void setSeparator(final String separator)
    {
        String oldSeparator = this.separator;
        this.separator = separator;
        firePropertyChange("separator", oldSeparator, this.separator);
        updateLabelText();
    }

    /**
     * Return the more elements indicator.
     *
     * @return the more elements indicator
     */
    public String getIndicator()
    {
        return indicator;
    }

    /**
     * Set the more elements indicator to <code>indicator</code>.  Defaults
     * to {@link #DEFAULT_INDICATOR}.
     *
     * <p>This is a bound property.</p>
     *
     * @param indicator more elements indicator
     */
    public void setIndicator(final String indicator)
    {
        String oldIndicator = this.indicator;
        this.indicator = indicator;
        firePropertyChange("indicator", oldIndicator, this.indicator);
        updateLabelText();
    }

    @Override
    protected void cut(final List<E> toCut)
    {
        // empty
    }

    @Override
    protected void copy(final List<E> toCopy)
    {
        // empty
    }

    @Override
    public void add()
    {
        // empty
    }

    @Override
    public void paste()
    {
        // empty
    }

    /**
     * Update label text.
     */
    private void updateLabelText()
    {
        StringBuilder sb = new StringBuilder();
        if (!isEmpty())
        {
            sb.append(getModel().get(0));
            for (int i = 1, size = Math.min(elementsToDisplay, getModel().size()); i < size; i++)
            {
                if (separator != null)
                {
                    sb.append(separator);
                }
                sb.append(getModel().get(i));
            }
            if ((elementsToDisplay < getModel().size()) && (indicator != null))
            {
                sb.append(indicator);
            }
        }
        elements.setText(sb.toString());
    }

    /**
     * Add listeners.
     */
    private void addListeners()
    {
        getModel().addListEventListener(listener);
    }

    /**
     * Remove listeners.
     */
    private void removeListeners()
    {
        getModel().removeListEventListener(listener);
    }

    /**
     * Release the resources consumed by this elements label so that it may eventually be garbage collected.
     */
    public void dispose()
    {
        super.dispose();
        removeListeners();
    }
}
